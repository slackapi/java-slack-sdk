package com.slack.api.bolt.service.builtin;

import com.slack.api.bolt.Initializer;
import com.slack.api.bolt.service.OAuthStateService;
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

import java.net.URI;
import java.nio.charset.StandardCharsets;

/**
 * OAuthStateService implementation using Amazon S3.
 *
 * @see <a href="https://aws.amazon.com/s3/">Amazon S3</a>
 */
@Slf4j
public class AmazonS3OAuthStateService implements OAuthStateService {

    private final String bucketName;

    private final AwsCredentialsProvider credentialsProvider;
    private final Region region;
    private final URI endpointOverride;

    public AmazonS3OAuthStateService(String bucketName) {
        this(bucketName, DefaultCredentialsProvider.create());
    }

    public AmazonS3OAuthStateService(String bucketName, AwsCredentialsProvider credentialsProvider) {
        this(bucketName, credentialsProvider, null, null);
    }

    public AmazonS3OAuthStateService(
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
    public void addNewStateToDatastore(String state) throws Exception {
        PutObjectResponse putObjectResult;
        try (S3Client s3 = this.createS3Client()) {
            String value = "" + (System.currentTimeMillis() + getExpirationInSeconds() * 1000);
            putObjectResult = s3.putObject(
                    PutObjectRequest.builder().bucket(bucketName).key(getKey(state)).build(),
                    RequestBody.fromString(value)
            );
        }
        if (log.isDebugEnabled()) {
            log.debug("AWS S3 putObject result of state data - {}", putObjectResult.toString());
        }
    }

    @Override
    public boolean isAvailableInDatabase(String state) {
        S3Client s3 = this.createS3Client();
        ResponseBytes<GetObjectResponse> s3Object = getObject(s3, getKey(state));
        if (s3Object == null) {
            return false;
        }
        String millisToExpire = null;
        try {
            millisToExpire = s3Object.asString(StandardCharsets.UTF_8);
            return Long.parseLong(millisToExpire) > System.currentTimeMillis();
        } catch (NumberFormatException ne) {
            log.error("Invalid state value detected - state: {}, millisToExpire: {}", state, millisToExpire);
            return false;
        } catch (Exception e) {
            log.error("Failed to load a state data for state: {}", state, e);
            return false;
        }
    }

    @Override
    public void deleteStateFromDatastore(String state) throws Exception {
        DeleteObjectResponse deleteObjectResponse;
        try (S3Client s3 = this.createS3Client()) {
            deleteObjectResponse = s3.deleteObject(DeleteObjectRequest.builder().bucket(bucketName).key(getKey(state)).build());
        }
        if (log.isDebugEnabled()) {
            log.debug("AWS S3 deleteObject result of state data - {}", deleteObjectResponse.toString());
        }
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

    private String getKey(String state) {
        return "state/" + state;
    }

    private ResponseBytes<GetObjectResponse> getObject(S3Client s3, String fullKey) {
        try {
            return s3.getObject(
                    GetObjectRequest.builder().bucket(bucketName).key(fullKey).build(),
                    ResponseTransformer.toBytes()
            );
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug("Amazon S3 object metadata not found (key: {}, Exception: {})", fullKey, e.toString());
            }
            return null;
        }
    }

}
