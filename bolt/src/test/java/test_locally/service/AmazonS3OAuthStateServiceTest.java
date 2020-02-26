package test_locally.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.slack.api.bolt.service.builtin.AmazonS3OAuthStateService;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class AmazonS3OAuthStateServiceTest {

    @Test(expected = IllegalStateException.class)
    public void initializer_no_credentials() {
        AmazonS3 s3 = mock(AmazonS3.class);
        AmazonS3OAuthStateService service = new AmazonS3OAuthStateService("test-bucket") {
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
        AmazonS3OAuthStateService service = new AmazonS3OAuthStateService("test-bucket") {
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

        AmazonS3OAuthStateService service = new AmazonS3OAuthStateService("test-bucket") {
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

        AmazonS3OAuthStateService service = new AmazonS3OAuthStateService("test-bucket") {
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

        service.isAvailableInDatabase("foo");
        service.addNewStateToDatastore("foo");
        service.deleteStateFromDatastore("foo");
    }

}
