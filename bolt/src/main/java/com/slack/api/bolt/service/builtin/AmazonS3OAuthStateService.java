package com.slack.api.bolt.service.builtin;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import com.slack.api.bolt.Initializer;
import com.slack.api.bolt.service.OAuthStateService;
import com.slack.api.bolt.util.JsonOps;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * OAuthStateService implementation using Amazon S3.
 *
 * @see <a href="https://aws.amazon.com/s3/">Amazon S3</a>
 */
@Slf4j
public class AmazonS3OAuthStateService implements OAuthStateService {

    private final String bucketName;

    public AmazonS3OAuthStateService(String bucketName) {
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
    public void addNewStateToDatastore(String state) throws Exception {
        AmazonS3 s3 = this.createS3Client();
        String value = "" + (System.currentTimeMillis() + getExpirationInSeconds() * 1000);
        PutObjectResult putObjectResult = s3.putObject(bucketName, getKey(state), value);
        if (log.isDebugEnabled()) {
            log.debug("AWS S3 putObject result of state data - {}", JsonOps.toJsonString(putObjectResult));
        }
    }

    @Override
    public boolean isAvailableInDatabase(String state) {
        AmazonS3 s3 = this.createS3Client();
        S3Object s3Object = getObject(s3, getKey(state));
        if (s3Object == null) {
            return false;
        }
        String millisToExpire = null;
        try {
            millisToExpire = IOUtils.toString(s3Object.getObjectContent());
            return Long.valueOf(millisToExpire) > System.currentTimeMillis();
        } catch (IOException e) {
            log.error("Failed to load a state data for state: {}", state, e);
            return false;
        } catch (NumberFormatException ne) {
            log.error("Invalid state value detected - state: {}, millisToExpire: {}", state, millisToExpire);
            return false;
        }
    }

    @Override
    public void deleteStateFromDatastore(String state) throws Exception {
        AmazonS3 s3 = this.createS3Client();
        s3.deleteObject(bucketName, getKey(state));
    }

    protected AWSCredentials getCredentials() {
        return DefaultAWSCredentialsProviderChain.getInstance().getCredentials();
    }

    protected AmazonS3 createS3Client() {
        return AmazonS3ClientBuilder.defaultClient();
    }

    private String getKey(String state) {
        return "state/" + state;
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

}
