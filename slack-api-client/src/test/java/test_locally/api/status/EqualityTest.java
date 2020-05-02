package test_locally.api.status;

import com.slack.api.status.v1.LegacyStatusApiException;
import com.slack.api.status.v2.StatusApiException;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EqualityTest {

    @Test
    public void test() {
        Request request = new Request.Builder()
                .url("https://www.example.com/")
                .build();
        Response response = new Response.Builder()
                .request(request)
                .code(200)
                .protocol(Protocol.HTTP_1_1)
                .message("")
                .build();

        assertEquals(new LegacyStatusApiException(response, ""), new LegacyStatusApiException(response, ""));
        assertEquals(new StatusApiException(response, ""), new StatusApiException(response, ""));
    }
}
