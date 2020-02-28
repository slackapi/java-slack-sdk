package test_locally.api.scim;

import com.slack.api.scim.SCIMApiException;
import okhttp3.Response;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertNotNull;

public class ExceptionsTest {

    @Test
    public void test() {
        Response response = Mockito.mock(Response.class);
        SCIMApiException exception = new SCIMApiException(response, "");
        assertNotNull(exception);
    }
}
