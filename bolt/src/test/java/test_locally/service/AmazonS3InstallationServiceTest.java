package test_locally.service;

import com.slack.api.bolt.model.builtin.DefaultBot;
import com.slack.api.bolt.model.builtin.DefaultInstaller;
import com.slack.api.bolt.service.builtin.AmazonS3InstallationService;
import org.junit.Test;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class AmazonS3InstallationServiceTest {

    static S3Client setupMocks(S3Client s3) {
        when(s3.headBucket((HeadBucketRequest) notNull()))
            .thenReturn(HeadBucketResponse.builder().build());
        when(s3.headObject((HeadObjectRequest) notNull()))
            .thenReturn(HeadObjectResponse.builder().build());
        when(s3.getObject((GetObjectRequest) notNull(), (ResponseTransformer<GetObjectResponse, ResponseBytes<GetObjectResponse>>) notNull()))
            .thenReturn(ResponseBytes.fromByteArray(GetObjectResponse.builder().build(), "{}".getBytes(StandardCharsets.UTF_8)));
        when(s3.putObject((PutObjectRequest) notNull(), (RequestBody) notNull()))
            .thenReturn(PutObjectResponse.builder().build());
        return s3;
    }

    @Test(expected = IllegalStateException.class)
    public void initializer_no_credentials() {
        S3Client s3 = setupMocks(mock(S3Client.class));
        AmazonS3InstallationService service = new AmazonS3InstallationService("test-bucket") {
            @Override
            protected AwsCredentials createCredentials(AwsCredentialsProvider provider) {
                return null;
            }

            @Override
            protected S3Client createS3Client() {
                return s3;
            }
        };
        service.initializer().accept(null);
    }

    @Test
    public void initializer() {
        AwsCredentials credentials = mock(AwsCredentials.class);
        when(credentials.accessKeyId()).thenReturn("valid key");
        S3Client s3 = setupMocks(mock(S3Client.class));

        AmazonS3InstallationService service = new AmazonS3InstallationService("test-bucket") {
            @Override
            protected AwsCredentials createCredentials(AwsCredentialsProvider provider) {
                return credentials;
            }

            @Override
            protected S3Client createS3Client() {
                return s3;
            }
        };
        service.initializer().accept(null);
    }

    @Test
    public void operations() throws Exception {
        AwsCredentials credentials = mock(AwsCredentials.class);
        when(credentials.accessKeyId()).thenReturn("valid key");
        S3Client s3 = setupMocks(mock(S3Client.class));

        AmazonS3InstallationService service = new AmazonS3InstallationService("test-bucket") {
            @Override
            protected AwsCredentials createCredentials(AwsCredentialsProvider provider) {
                return credentials;
            }

            @Override
            protected S3Client createS3Client() {
                return s3;
            }
        };
        service.initializer().accept(null);

        service.saveInstallerAndBot(new DefaultInstaller());
        service.deleteBot(new DefaultBot());
        service.deleteInstaller(new DefaultInstaller());
        service.findBot("E123", "T123");
        service.findInstaller("E123", "T123", "U123");
    }

    @Test
    public void operations_historical_data_enabled() throws Exception {
        AwsCredentials credentials = mock(AwsCredentials.class);
        when(credentials.accessKeyId()).thenReturn("valid key");
        S3Client s3 = setupMocks(mock(S3Client.class));

        AmazonS3InstallationService service = new AmazonS3InstallationService("test-bucket") {
            @Override
            protected AwsCredentials createCredentials(AwsCredentialsProvider provider) {
                return credentials;
            }

            @Override
            protected S3Client createS3Client() {
                return s3;
            }
        };
        service.setHistoricalDataEnabled(true);
        service.initializer().accept(null);

        service.saveInstallerAndBot(new DefaultInstaller());
        service.deleteBot(new DefaultBot());
        service.deleteInstaller(new DefaultInstaller());
        service.findBot("E123", "T123");
        service.findInstaller("E123", "T123", "U123");
    }

    static class MyService extends AmazonS3InstallationService {
        public MyService(String bucketName) {
            super(bucketName);
        }

        public S3Client s3() {
            return createS3Client();
        }
    }

    @Test
    public void s3() {
        MyService service = new MyService("test-bucket");
        try {
            S3Client s3 = service.s3();
            assertNotNull(s3);
        } catch (Exception ignored) {
        }
    }

}
