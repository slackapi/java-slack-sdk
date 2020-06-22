package test_locally.api.methods;

import com.slack.api.methods.MethodsCompletionException;
import com.slack.api.methods.SlackApiException;
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

        assertEquals(new MethodsCompletionException(null, null, null), new MethodsCompletionException(null, null, null));
        assertEquals(new SlackApiException(response, ""), new SlackApiException(response, ""));
    }
}
