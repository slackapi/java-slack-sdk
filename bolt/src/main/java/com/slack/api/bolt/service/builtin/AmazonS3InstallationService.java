package com.slack.api.bolt.service.builtin;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import com.slack.api.bolt.Initializer;
import com.slack.api.bolt.model.Bot;
import com.slack.api.bolt.model.Installer;
import com.slack.api.bolt.model.builtin.DefaultBot;
import com.slack.api.bolt.model.builtin.DefaultInstaller;
import com.slack.api.bolt.service.InstallationService;
import com.slack.api.bolt.util.JsonOps;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Optional;

@Slf4j
public class AmazonS3InstallationService implements InstallationService {

    private final String bucketName;
    private boolean historicalDataEnabled;

    public AmazonS3InstallationService(String bucketName) {
        this.bucketName = bucketName;
    }

    @Override
    public Initializer initializer() {
        return (app) -> {
            // The first access to S3 tends to be slow on AWS Lambda.
            AWSCredentials credentials = getCredentials();
            if (credentials == null || credentials.getAWSAccessKeyId() == null) {
                throw new IllegalStateException("AWS credentials not found");
            }
            if (log.isDebugEnabled()) {
                log.debug("AWS credentials loaded (access key id: {})", credentials.getAWSAccessKeyId());
            }
            boolean bucketExists = createS3Client().doesBucketExistV2(bucketName);
            if (!bucketExists) {
                throw new IllegalStateException("Failed to access the Amazon S3 bucket (name: " + bucketName + ")");
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
    public void saveInstallerAndBot(Installer i) throws Exception {
        AmazonS3 s3 = this.createS3Client();
        if (isHistoricalDataEnabled()) {
            save(s3, getInstallerKey(i) + "-latest", JsonOps.toJsonString(i), "AWS S3 putObject result of Installer data - {}, {}");
            save(s3, getBotKey(i) + "-latest", JsonOps.toJsonString(i.toBot()), "AWS S3 putObject result of Bot data - {}, {}");
            save(s3, getInstallerKey(i) + "-" + i.getInstalledAt(), JsonOps.toJsonString(i), "AWS S3 putObject result of Installer data - {}, {}");
            save(s3, getBotKey(i) + "-" + i.getInstalledAt(), JsonOps.toJsonString(i.toBot()), "AWS S3 putObject result of Bot data - {}, {}");
        } else {
            save(s3, getInstallerKey(i), JsonOps.toJsonString(i), "AWS S3 putObject result of Installer data - {}, {}");
            save(s3, getBotKey(i), JsonOps.toJsonString(i.toBot()), "AWS S3 putObject result of Bot data - {}, {}");
        }
    }

    @Override
    public void saveBot(Bot bot) throws Exception {
        AmazonS3 s3 = this.createS3Client();
        String keyPrefix = getBotKey(bot.getEnterpriseId(), bot.getTeamId());
        if (isHistoricalDataEnabled()) {
            save(s3, keyPrefix + "-latest", JsonOps.toJsonString(bot), "AWS S3 putObject result of Bot data - {}, {}");
            save(s3, keyPrefix + "-" + bot.getInstalledAt(), JsonOps.toJsonString(bot), "AWS S3 putObject result of Bot data - {}, {}");
        } else {
            save(s3, keyPrefix, JsonOps.toJsonString(bot), "AWS S3 putObject result of Bot data - {}, {}");
        }
    }

    private void save(AmazonS3 s3, String s3Key, String json, String logMessage) {
        PutObjectResult botPutResult = s3.putObject(bucketName, s3Key, json);
        if (log.isDebugEnabled()) {
            log.debug(logMessage, s3Key, JsonOps.toJsonString(botPutResult));
        }
    }

    @Override
    public void deleteBot(Bot bot) throws Exception {
        AmazonS3 s3 = this.createS3Client();
        String key = getBotKey(bot.getEnterpriseId(), bot.getTeamId());
        if (isHistoricalDataEnabled()) {
            key = key + "-latest";
        }
        s3.deleteObject(bucketName, key);
    }

    @Override
    public void deleteInstaller(Installer installer) throws Exception {
        AmazonS3 s3 = this.createS3Client();
        String key = getInstallerKey(installer);
        if (isHistoricalDataEnabled()) {
            key = key + "-latest";
        }
        s3.deleteObject(bucketName, key);
    }

    @Override
    public Bot findBot(String enterpriseId, String teamId) {
        AmazonS3 s3 = this.createS3Client();
        if (enterpriseId != null) {
            // try finding org-level bot token first - teamId is intentionally null here
            String fullKey = getBotKey(enterpriseId, null);
            if (isHistoricalDataEnabled()) {
                fullKey = fullKey + "-latest";
            }
            if (getObjectMetadata(s3, fullKey) != null) {
                S3Object s3Object = getObject(s3, fullKey);
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
            S3Object nonGridObject = getObject(s3, nonGridKey);
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
        S3Object s3Object = getObject(s3, fullKey);
        try {
            return toBot(s3Object);
        } catch (IOException e) {
            log.error("Failed to load Bot data for enterprise_id: {}, team_id: {}", enterpriseId, teamId);
            return null;
        }
    }

    @Override
    public Installer findInstaller(String enterpriseId, String teamId, String userId) {
        AmazonS3 s3 = this.createS3Client();
        if (enterpriseId != null) {
            // try finding org-level user token first - teamId is intentionally null here
            String fullKey = getInstallerKey(enterpriseId, null, userId);
            if (isHistoricalDataEnabled()) {
                fullKey = fullKey + "-latest";
            }
            if (getObjectMetadata(s3, fullKey) != null) {
                S3Object s3Object = getObject(s3, fullKey);
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
            S3Object nonGridObject = getObject(s3, nonGridKey);
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
        S3Object s3Object = getObject(s3, fullKey);
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
        AmazonS3 s3 = this.createS3Client();
        deleteAllObjectsMatchingPrefix(s3, "installer/"
                + Optional.ofNullable(enterpriseId).orElse("none")
                + "-"
                + Optional.ofNullable(teamId).orElse("none"));
        deleteAllObjectsMatchingPrefix(s3, "bot/"
                + Optional.ofNullable(enterpriseId).orElse("none")
                + "-"
                + Optional.ofNullable(teamId).orElse("none"));
    }

    private void deleteAllObjectsMatchingPrefix(AmazonS3 s3, String prefix) {
        for (S3ObjectSummary obj : s3.listObjects(bucketName, prefix).getObjectSummaries()) {
            s3.deleteObject(bucketName, obj.getKey());
        }
    }

    private ObjectMetadata getObjectMetadata(AmazonS3 s3, String fullKey) {
        try {
            return s3.getObjectMetadata(bucketName, fullKey);
        } catch (AmazonS3Exception e) {
            if (log.isDebugEnabled()) {
                log.debug("Amazon S3 object metadata not found (key: {}, AmazonS3Exception: {})", fullKey, e.toString());
            }
            return null;
        }
    }

    private S3Object getObject(AmazonS3 s3, String fullKey) {
        try {
            return s3.getObject(bucketName, fullKey);
        } catch (AmazonS3Exception e) {
            if (log.isDebugEnabled()) {
                log.debug("Amazon S3 object metadata not found (key: {}, AmazonS3Exception: {})", fullKey, e.toString());
            }
            return null;
        }
    }

    private Bot toBot(S3Object s3Object) throws IOException {
        if (s3Object == null) {
            return null;
        }
        String json = IOUtils.toString(s3Object.getObjectContent());
        return JsonOps.fromJson(json, DefaultBot.class);
    }

    private Installer toInstaller(S3Object s3Object) throws IOException {
        if (s3Object == null) {
            return null;
        }
        String json = IOUtils.toString(s3Object.getObjectContent());
        return JsonOps.fromJson(json, DefaultInstaller.class);
    }

    protected AWSCredentials getCredentials() {
        return DefaultAWSCredentialsProviderChain.getInstance().getCredentials();
    }

    protected AmazonS3 createS3Client() {
        return AmazonS3ClientBuilder.defaultClient();
    }

    private String getInstallerKey(Installer i) {
        return getInstallerKey(i.getEnterpriseId(), i.getTeamId(), i.getInstallerUserId());
    }

    private String getInstallerKey(String enterpriseId, String teamId, String userId) {
        return "installer/"
                + Optional.ofNullable(enterpriseId).orElse("none")
                + "-"
                + Optional.ofNullable(teamId).orElse("none")
                + "-"
                + userId;
    }

    private String getBotKey(Installer i) {
        return getBotKey(i.getEnterpriseId(), i.getTeamId());
    }

    private String getBotKey(String enterpriseId, String teamId) {
        return "bot/"
                + Optional.ofNullable(enterpriseId).orElse("none")
                + "-"
                + Optional.ofNullable(teamId).orElse("none");
    }
}
