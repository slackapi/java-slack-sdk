package test_with_remote_apis.service.aws;

import com.slack.api.bolt.service.builtin.AmazonS3OAuthStateService;
import org.junit.Test;

import java.util.UUID;

public class AmazonS3OAuthStateServiceTest {

    @Test(expected = IllegalStateException.class)
    public void init() {
        String bucketName = "java-slack-sdk-test-dummy-bucket" + UUID.randomUUID();
        AmazonS3OAuthStateService service = new AmazonS3OAuthStateService(bucketName);
        service.initializer().accept(null);
    }
}
