package com.slack.api.bolt.service.builtin;

import com.slack.api.bolt.Initializer;
import com.slack.api.bolt.model.Bot;
import com.slack.api.bolt.model.Installer;
import com.slack.api.bolt.model.builtin.DefaultBot;
import com.slack.api.bolt.model.builtin.DefaultInstaller;
import com.slack.api.bolt.service.InstallationService;
import com.slack.api.bolt.util.JsonOps;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;

/**
 * InstallationService implementation using Amazon S3.
 *
 * @see <a href="https://aws.amazon.com/s3/">Amazon S3</a>
 */
@Slf4j
public class AmazonS3InstallationService implements InstallationService {

    private final String bucketName;

    private final AwsCredentialsProvider credentialsProvider;
    private final Region region;
    private final URI endpointOverride;

    private boolean historicalDataEnabled;

    public AmazonS3InstallationService(String bucketName) {
        this(bucketName, DefaultCredentialsProvider.create(), null, null);
    }

    public AmazonS3InstallationService(String bucketName, AwsCredentialsProvider credentialsProvider) {
        this(bucketName, credentialsProvider, null, null);
    }

    public AmazonS3InstallationService(
            String bucketName,
            AwsCredentialsProvider credentialsProvider,
            Region region,
            String endpointOverride
    ) {
        this.bucketName = bucketName;
        this.credentialsProvider = credentialsProvider;
        this.region = (region != null || System.getenv("AWS_REGION") == null) ? region : Region.of(System.getenv("AWS_REGION"));
        this.endpointOverride = (endpointOverride != null && !endpointOverride.isEmpty()) ? URI.create(endpointOverride) : null;
    }

    @Override
    public Initializer initializer() {
        return (app) -> {
            // The first access to S3 tends to be slow on AWS Lambda.
            AwsCredentials credentials = createCredentials(this.credentialsProvider);
            if (credentials == null || credentials.accessKeyId() == null) {
                throw new IllegalStateException("AWS credentials not found");
            }
            if (log.isDebugEnabled()) {
                log.debug("AWS credentials loaded (access key id: {})", credentials.accessKeyId());
            }
            boolean bucketExists = false;
            Exception ex = null;
            try (S3Client s3 = createS3Client()) {
                bucketExists = s3.headBucket(HeadBucketRequest.builder().bucket(bucketName).build()) != null;
            } catch (Exception e) { // NoSuchBucketException etc.
                ex = e;
            }
            if (!bucketExists) {
                String error = ex != null ? ex.getClass().getName() + ":" + ex.getMessage() : "-";
                String message = "Failed to access the Amazon S3 bucket (name: " + bucketName + ", error: " + error + ")";
                throw new IllegalStateException(message);
            }
        };
    }

    @Override
    public boolean isHistoricalDataEnabled() {
        return historicalDataEnabled;
    }

    @Override
    public void setHistoricalDataEnabled(boolean isHistoricalDataEnabled) {
        this.historicalDataEnabled = isHistoricalDataEnabled;
    }

    @Override
    public void saveInstallerAndBot(Installer installer) throws Exception {
        try (S3Client s3 = this.createS3Client()) {
            if (isHistoricalDataEnabled()) {
                save(s3, getInstallerKey(installer) + "-latest", JsonOps.toJsonString(installer), "AWS S3 putObject result of Installer data - {}, {}");
                save(s3, getBotKey(installer) + "-latest", JsonOps.toJsonString(installer.toBot()), "AWS S3 putObject result of Bot data - {}, {}");
                save(s3, getInstallerKey(installer) + "-" + installer.getInstalledAt(), JsonOps.toJsonString(installer), "AWS S3 putObject result of Installer data - {}, {}");
                save(s3, getBotKey(installer) + "-" + installer.getInstalledAt(), JsonOps.toJsonString(installer.toBot()), "AWS S3 putObject result of Bot data - {}, {}");
            } else {
                save(s3, getInstallerKey(installer), JsonOps.toJsonString(installer), "AWS S3 putObject result of Installer data - {}, {}");
                save(s3, getBotKey(installer), JsonOps.toJsonString(installer.toBot()), "AWS S3 putObject result of Bot data - {}, {}");
            }
        }
    }

    @Override
    public void saveBot(Bot bot) throws Exception {
        try (S3Client s3 = this.createS3Client()) {
            String keyPrefix = getBotKey(bot.getEnterpriseId(), bot.getTeamId());
            if (isHistoricalDataEnabled()) {
                save(s3, keyPrefix + "-latest", JsonOps.toJsonString(bot), "AWS S3 putObject result of Bot data - {}, {}");
                save(s3, keyPrefix + "-" + bot.getInstalledAt(), JsonOps.toJsonString(bot), "AWS S3 putObject result of Bot data - {}, {}");
            } else {
                save(s3, keyPrefix, JsonOps.toJsonString(bot), "AWS S3 putObject result of Bot data - {}, {}");
            }
        }
    }

    private void save(S3Client s3, String s3Key, String json, String logMessage) {
        PutObjectResponse botPutResult = s3.putObject(
                PutObjectRequest.builder().bucket(bucketName).key(s3Key).build(),
                RequestBody.fromString(json)
        );
        if (log.isDebugEnabled()) {
            log.debug(logMessage, s3Key, botPutResult.toString());
        }
    }

    @Override
    public void deleteBot(Bot bot) throws Exception {
        try (S3Client s3 = this.createS3Client()) {
            String key = getBotKey(bot.getEnterpriseId(), bot.getTeamId());
            if (isHistoricalDataEnabled()) {
                key = key + "-latest";
            }
            if (log.isDebugEnabled()) {
                log.debug("Going to delete an object (bucket: {}, key: {})", bucketName, key);
            }
            s3.deleteObject(DeleteObjectRequest.builder().bucket(bucketName).key(key).build());
        }
    }

    @Override
    public void deleteInstaller(Installer installer) throws Exception {
        try (S3Client s3 = this.createS3Client()) {
            String key = getInstallerKey(installer);
            if (isHistoricalDataEnabled()) {
                key = key + "-latest";
            }
            if (log.isDebugEnabled()) {
                log.debug("Going to delete an object (bucket: {}, key: {})", bucketName, key);
            }
            s3.deleteObject(DeleteObjectRequest.builder().bucket(bucketName).key(key).build());
        }
    }

    @Override
    public Bot findBot(String enterpriseId, String teamId) {
        S3Client s3 = this.createS3Client();
        if (enterpriseId != null) {
            // try finding org-level bot token first - teamId is intentionally null here
            String fullKey = getBotKey(enterpriseId, null);
            if (isHistoricalDataEnabled()) {
                fullKey = fullKey + "-latest";
            }
            if (getObjectMetadata(s3, fullKey) != null) {
                ResponseBytes<GetObjectResponse> s3Object = getObject(s3, fullKey);
                try {
                    return toBot(s3Object);
                } catch (IOException e) {
                    log.error("Failed to load org-level Bot installation for enterprise_id: {}", enterpriseId);
                }
            }
            // not found - going to find workspace level installation
        }
        String fullKey = getBotKey(enterpriseId, teamId);
        if (isHistoricalDataEnabled()) {
            fullKey = fullKey + "-latest";
        }
        if (getObjectMetadata(s3, fullKey) == null && enterpriseId != null) {
            String nonGridKey = getBotKey(null, teamId);
            if (isHistoricalDataEnabled()) {
                nonGridKey = nonGridKey + "-latest";
            }
            ResponseBytes<GetObjectResponse> nonGridObject = getObject(s3, nonGridKey);
            if (nonGridObject != null) {
                try {
                    Bot bot = toBot(nonGridObject);
                    bot.setEnterpriseId(enterpriseId); // the workspace seems to be in a Grid org now
                    save(s3, fullKey, JsonOps.toJsonString(bot), "AWS S3 putObject result of Bot data - {}");
                    return bot;
                } catch (Exception e) {
                    log.error("Failed to save a new Bot data for enterprise_id: {}, team_id: {}", enterpriseId, teamId);
                }
            }
        }
        ResponseBytes<GetObjectResponse> s3Object = getObject(s3, fullKey);
        try {
            return toBot(s3Object);
        } catch (IOException e) {
            log.error("Failed to load Bot data for enterprise_id: {}, team_id: {}", enterpriseId, teamId);
            return null;
        }
    }

    @Override
    public Installer findInstaller(String enterpriseId, String teamId, String userId) {
        S3Client s3 = this.createS3Client();
        if (enterpriseId != null) {
            // try finding org-level user token first - teamId is intentionally null here
            String fullKey = getInstallerKey(enterpriseId, null, userId);
            if (isHistoricalDataEnabled()) {
                fullKey = fullKey + "-latest";
            }
            if (getObjectMetadata(s3, fullKey) != null) {
                ResponseBytes<GetObjectResponse> s3Object = getObject(s3, fullKey);
                try {
                    return toInstaller(s3Object);
                } catch (IOException e) {
                    log.error("Failed to load org-level installation for enterprise_id: {}, user_id: {}", enterpriseId, userId);
                }
            }
            // not found - going to find workspace level installation
        }
        String fullKey = getInstallerKey(enterpriseId, teamId, userId);
        if (isHistoricalDataEnabled()) {
            fullKey = fullKey + "-latest";
        }
        if (getObjectMetadata(s3, fullKey) == null && enterpriseId != null) {
            String nonGridKey = getInstallerKey(null, teamId, userId);
            if (isHistoricalDataEnabled()) {
                nonGridKey = nonGridKey + "-latest";
            }
            ResponseBytes<GetObjectResponse> nonGridObject = getObject(s3, nonGridKey);
            if (nonGridObject != null) {
                try {
                    Installer installer = toInstaller(nonGridObject);
                    installer.setEnterpriseId(enterpriseId); // the workspace seems to be in a Grid org now
                    saveInstallerAndBot(installer);
                    return installer;
                } catch (Exception e) {
                    log.error("Failed to save a new Installer data for enterprise_id: {}, team_id: {}, user_id: {}",
                            enterpriseId, teamId, userId);
                }
            }
        }
        ResponseBytes<GetObjectResponse> s3Object = getObject(s3, fullKey);
        try {
            return toInstaller(s3Object);
        } catch (Exception e) {
            log.error("Failed to save a new Installer data for enterprise_id: {}, team_id: {}, user_id: {}",
                    enterpriseId, teamId, userId);
            return null;
        }
    }

    @Override
    public void deleteAll(String enterpriseId, String teamId) {
        S3Client s3 = this.createS3Client();
        deleteAllObjectsMatchingPrefix(s3, "installer/"
                + Optional.ofNullable(enterpriseId).orElse("none")
                + "-"
                + Optional.ofNullable(teamId).orElse("none"));
        deleteAllObjectsMatchingPrefix(s3, "bot/"
                + Optional.ofNullable(enterpriseId).orElse("none")
                + "-"
                + Optional.ofNullable(teamId).orElse("none"));
    }

    private void deleteAllObjectsMatchingPrefix(S3Client s3, String prefix) {
        for (S3Object obj : s3.listObjectsV2(ListObjectsV2Request.builder().bucket(bucketName).prefix(prefix).build()).contents()) {
            if (log.isDebugEnabled()) {
                log.debug("Going to delete an object (bucket: {}, key: {})", bucketName, obj.key());
            }
            s3.deleteObject(DeleteObjectRequest.builder().bucket(bucketName).key(obj.key()).build());
        }
    }

    private Map<String, String> getObjectMetadata(S3Client s3, String fullKey) {
        try {
            return s3.headObject(HeadObjectRequest.builder().bucket(bucketName).key(fullKey).build()).metadata();
        } catch (S3Exception e) {
            if (log.isDebugEnabled()) {
                log.debug("Amazon S3 object metadata not found (key: {}, S3Exception: {})", fullKey, e.toString());
            }
            return null;
        }
    }

    private ResponseBytes<GetObjectResponse> getObject(S3Client s3, String fullKey) {
        try {
            return s3.getObject(
                    GetObjectRequest.builder().bucket(bucketName).key(fullKey).build(),
                    ResponseTransformer.toBytes()
            );
        } catch (S3Exception e) {
            if (log.isDebugEnabled()) {
                log.debug("Amazon S3 object metadata not found (key: {}, S3Exception: {})", fullKey, e.toString());
            }
            return null;
        }
    }

    private Bot toBot(ResponseBytes<GetObjectResponse> s3Object) throws IOException {
        if (s3Object == null) {
            return null;
        }
        String json = s3Object.asString(StandardCharsets.UTF_8);
        return JsonOps.fromJson(json, DefaultBot.class);
    }

    private Installer toInstaller(ResponseBytes<GetObjectResponse> s3Object) throws IOException {
        if (s3Object == null) {
            return null;
        }
        String json = s3Object.asString(StandardCharsets.UTF_8);
        return JsonOps.fromJson(json, DefaultInstaller.class);
    }

    protected AwsCredentials createCredentials(AwsCredentialsProvider provider) {
        return provider.resolveCredentials();
    }

    protected S3Client createS3Client() {
        return S3Client.builder()
                .credentialsProvider(this.credentialsProvider)
                .region(this.region)
                .endpointOverride(this.endpointOverride)
                .build();
    }

    private String getInstallerKey(Installer installer) {
        return getInstallerKey(installer.getEnterpriseId(),
                installer.getTeamId(),
                installer.getInstallerUserId());
    }

    private String getInstallerKey(String enterpriseId, String teamId, String userId) {
        return "installer/"
                + Optional.ofNullable(enterpriseId).orElse("none")
                + "-"
                + Optional.ofNullable(teamId).orElse("none")
                + "-"
                + userId;
    }

    private String getBotKey(Installer installer) {
        return getBotKey(installer.getEnterpriseId(),
               installer.getTeamId());
    }

    private String getBotKey(String enterpriseId, String teamId) {
        return "bot/"
                + Optional.ofNullable(enterpriseId).orElse("none")
                + "-"
                + Optional.ofNullable(teamId).orElse("none");
    }
}
