package test_locally.api.audit;

import com.slack.api.audit.AuditApiException;
import okhttp3.Response;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertNotNull;

public class ExceptionsTest {

    @Test
    public void createAuditApiException() {
        Response response = Mockito.mock(Response.class);
        AuditApiException exception = new AuditApiException(response, "");
        assertNotNull(exception);
    }
}
