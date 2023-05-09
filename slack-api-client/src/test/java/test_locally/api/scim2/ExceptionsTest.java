package test_locally.api.scim2;

import com.slack.api.scim2.SCIM2ApiException;
import okhttp3.Response;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertNotNull;

public class ExceptionsTest {

    @Test
    public void test() {
        Response response = Mockito.mock(Response.class);
        SCIM2ApiException exception = new SCIM2ApiException(response, "");
        assertNotNull(exception);
    }
}
