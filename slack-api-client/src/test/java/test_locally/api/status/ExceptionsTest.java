package test_locally.api.status;

import com.slack.api.status.v1.LegacyStatusApiException;
import com.slack.api.status.v2.StatusApiException;
import okhttp3.Response;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertNotNull;

public class ExceptionsTest {

    @Test
    public void v1() {
        Response response = Mockito.mock(Response.class);
        LegacyStatusApiException e = new LegacyStatusApiException(response, "");
        assertNotNull(e);
    }

    @Test
    public void v2() {
        Response response = Mockito.mock(Response.class);
        StatusApiException e = new StatusApiException(response, "");
        assertNotNull(e);
    }
}
