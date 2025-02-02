package test_locally.service;

import com.slack.api.bolt.service.builtin.AmazonS3OAuthStateService;
import org.junit.Test;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.util.UUID;
import java.nio.charset.StandardCharsets;

import static junit.framework.TestCase.assertFalse;
import static org.mockito.Mockito.*;

public class AmazonS3OAuthStateServiceTest {

    static S3Client setupMocks(S3Client s3) {
        when(s3.headBucket((HeadBucketRequest) notNull()))
                .thenReturn(HeadBucketResponse.builder().build());
        when(s3.getObject((GetObjectRequest) notNull(), (ResponseTransformer<GetObjectResponse, ResponseBytes<GetObjectResponse>>) notNull()))
                .thenReturn(ResponseBytes.fromByteArray(GetObjectResponse.builder().build(), "42".getBytes(StandardCharsets.UTF_8)));
        when(s3.putObject((PutObjectRequest) notNull(), (RequestBody) notNull()))
                .thenReturn(PutObjectResponse.builder().build());
        when(s3.deleteObject((DeleteObjectRequest) notNull()))
                .thenReturn(DeleteObjectResponse.builder().build());
        return s3;
    }

    @Test(expected = IllegalStateException.class)
    public void initializer_no_credentials() {
        S3Client s3 = setupMocks(mock(S3Client.class));
        AmazonS3OAuthStateService service = new AmazonS3OAuthStateService("test-bucket") {
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

        AmazonS3OAuthStateService service = new AmazonS3OAuthStateService("test-bucket") {
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

        AmazonS3OAuthStateService service = new AmazonS3OAuthStateService("test-bucket") {
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

        String uuid = UUID.randomUUID().toString();
        assertFalse(service.isAvailableInDatabase(uuid));
        service.addNewStateToDatastore(uuid);
        service.deleteStateFromDatastore(uuid);
    }

}
