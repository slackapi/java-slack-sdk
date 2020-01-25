package com.github.seratch.jslack.lightning.service.builtin;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import com.github.seratch.jslack.lightning.service.OAuthStateService;
import com.github.seratch.jslack.lightning.util.JsonOps;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.UUID;

@Slf4j
public class AmazonS3OAuthStateService implements OAuthStateService {

    public static final long DEFAULT_EXPIRATION_IN_MILLIS = 10 * 60 * 1000; // default 10 min

    private final String bucketName;
    private final long millisToExpire;

    public AmazonS3OAuthStateService(String bucketName) {
        this(bucketName, DEFAULT_EXPIRATION_IN_MILLIS);
    }

    public AmazonS3OAuthStateService(String bucketName, long millisToExpire) {
        this.bucketName = bucketName;
        this.millisToExpire = millisToExpire;
    }

    @Override
    public String issueNewState() {
        String state = UUID.randomUUID().toString();
        AmazonS3 s3 = this.createS3Client();
        String value = "" + (System.currentTimeMillis() + millisToExpire);
        PutObjectResult putObjectResult = s3.putObject(bucketName, getKey(state), value);
        if (log.isDebugEnabled()) {
            log.debug("AWS S3 putObject result of state data - {}", JsonOps.toJsonString(putObjectResult));
        }
        return state;
    }

    @Override
    public boolean isValid(String state) {
        AmazonS3 s3 = this.createS3Client();
        S3Object s3Object = s3.getObject(bucketName, getKey(state));
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
    public void consume(String state) {
        AmazonS3 s3 = this.createS3Client();
        s3.deleteObject(bucketName, getKey(state));
    }

    private AmazonS3 createS3Client() {
        return AmazonS3ClientBuilder.defaultClient();
    }

    private String getKey(String state) {
        return "state/" + state;
    }

}
