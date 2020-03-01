package test_locally.api.util.http;

import com.slack.api.SlackConfig;
import com.slack.api.util.http.listener.HttpResponseListener;
import com.slack.api.util.http.listener.ResponsePrettyPrintingListener;
import okhttp3.Response;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class ResponsePrettyPrintingListenerTest {

    @Test
    public void test() {
        ResponsePrettyPrintingListener listener = new ResponsePrettyPrintingListener();
        SlackConfig config = new SlackConfig();
        config.setPrettyResponseLoggingEnabled(true);
        Response response = mock(Response.class);
        String responseBody = "{\"text\":\"Hello!\", \"numbers\":[1,2,3]}";
        HttpResponseListener.State state = new HttpResponseListener.State(config, response, responseBody);
        listener.accept(state);
    }
}
