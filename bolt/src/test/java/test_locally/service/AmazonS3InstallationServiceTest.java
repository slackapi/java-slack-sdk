package test_locally.service;

import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.slack.api.bolt.model.builtin.DefaultBot;
import com.slack.api.bolt.model.builtin.DefaultInstaller;
import com.slack.api.bolt.service.builtin.AmazonS3InstallationService;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class AmazonS3InstallationServiceTest {

    @Test(expected = IllegalStateException.class)
    public void initializer_no_credentials() {
        AmazonS3 s3 = mock(AmazonS3.class);
        AmazonS3InstallationService service = new AmazonS3InstallationService("test-bucket") {
            @Override
            protected AWSCredentials getCredentials() {
                return null;
            }

            @Override
            protected AmazonS3 createS3Client() {
                return s3;
            }
        };
        service.initializer().accept(null);
    }

    @Test(expected = IllegalStateException.class)
    public void initializer_no_bucket() {
        AWSCredentials credentials = mock(AWSCredentials.class);
        AmazonS3 s3 = mock(AmazonS3.class);
        AmazonS3InstallationService service = new AmazonS3InstallationService("test-bucket") {
            @Override
            protected AWSCredentials getCredentials() {
                return credentials;
            }

            @Override
            protected AmazonS3 createS3Client() {
                return s3;
            }
        };
        service.initializer().accept(null);
    }

    @Test
    public void initializer() {
        AWSCredentials credentials = mock(AWSCredentials.class);
        when(credentials.getAWSAccessKeyId()).thenReturn("valid key");
        AmazonS3 s3 = mock(AmazonS3.class);
        when(s3.doesBucketExistV2(anyString())).thenReturn(true);

        AmazonS3InstallationService service = new AmazonS3InstallationService("test-bucket") {
            @Override
            protected AWSCredentials getCredentials() {
                return credentials;
            }

            @Override
            protected AmazonS3 createS3Client() {
                return s3;
            }
        };
        service.initializer().accept(null);
    }

    @Test
    public void operations() throws Exception {
        AWSCredentials credentials = mock(AWSCredentials.class);
        when(credentials.getAWSAccessKeyId()).thenReturn("valid key");
        AmazonS3 s3 = mock(AmazonS3.class);
        when(s3.doesBucketExistV2(anyString())).thenReturn(true);

        AmazonS3InstallationService service = new AmazonS3InstallationService("test-bucket") {
            @Override
            protected AWSCredentials getCredentials() {
                return credentials;
            }

            @Override
            protected AmazonS3 createS3Client() {
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
        AWSCredentials credentials = mock(AWSCredentials.class);
        when(credentials.getAWSAccessKeyId()).thenReturn("valid key");
        AmazonS3 s3 = mock(AmazonS3.class);
        when(s3.doesBucketExistV2(anyString())).thenReturn(true);

        AmazonS3InstallationService service = new AmazonS3InstallationService("test-bucket") {
            @Override
            protected AWSCredentials getCredentials() {
                return credentials;
            }

            @Override
            protected AmazonS3 createS3Client() {
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

        public AWSCredentials credentials() {
            return getCredentials();
        }

        public AmazonS3 s3() {
            return createS3Client();
        }
    }

    @Test
    public void credentials() {
        MyService service = new MyService("test-bucket");
        try {
            AWSCredentials credentials = service.credentials();
            assertNotNull(credentials);
        } catch (SdkClientException ignored) {
        }
    }

    @Test
    public void s3() {
        MyService service = new MyService("test-bucket");
        try {
            AmazonS3 s3 = service.s3();
            assertNotNull(s3);
        } catch (SdkClientException ignored) {
        }
    }

}
