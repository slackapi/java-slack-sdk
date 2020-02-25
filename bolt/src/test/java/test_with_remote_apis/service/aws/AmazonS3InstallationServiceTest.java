package test_with_remote_apis.service.aws;

import com.slack.api.bolt.service.builtin.AmazonS3InstallationService;
import org.junit.Test;

import java.util.UUID;

public class AmazonS3InstallationServiceTest {

    @Test(expected = IllegalStateException.class)
    public void init() {
        String bucketName = "java-slack-sdk-test-dummy-bucket" + UUID.randomUUID();
        AmazonS3InstallationService service = new AmazonS3InstallationService(bucketName);
        service.initializer().accept(null);
    }
}
