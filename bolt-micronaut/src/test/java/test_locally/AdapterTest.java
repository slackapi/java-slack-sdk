package test_locally;

import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.micronaut.SlackAppMicronautAdapter;
import com.slack.api.bolt.request.Request;
import com.slack.api.bolt.response.Response;
import io.micronaut.core.convert.DefaultConversionService;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.simple.SimpleHttpHeaders;
import io.micronaut.http.simple.SimpleHttpParameters;
import org.junit.Test;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AdapterTest {

    @Test
    public void toSlackRequest() {
        AppConfig config = AppConfig.builder().build();
        SlackAppMicronautAdapter adapter = new SlackAppMicronautAdapter(config);

        HttpRequest<String> req = mock(HttpRequest.class);
        Map<String, String> rawHeaders = new HashMap<>();
        rawHeaders.put("X-Slack-Signature", "xxxxxxx");
        SimpleHttpHeaders headers = new SimpleHttpHeaders(rawHeaders, new DefaultConversionService());
        when(req.getHeaders()).thenReturn(headers);
        Map<CharSequence, List<String>> params = new HashMap<>();
        params.put("foo", Arrays.asList("bar", "baz"));
        SimpleHttpParameters parameters = new SimpleHttpParameters(params, new DefaultConversionService());
        when(req.getParameters()).thenReturn(parameters);

        LinkedHashMap<String, String> body = new LinkedHashMap<>();
        body.put("token", "random");
        body.put("ssl_check", "1");

        Request<?> slackRequest = adapter.toSlackRequest(req, body);

        assertNotNull(slackRequest);
    }

    @Test
    public void toMicronautResponse() {
        AppConfig config = AppConfig.builder().build();
        SlackAppMicronautAdapter adapter = new SlackAppMicronautAdapter(config);

        String body = "{\"text\":\"Hi there!\"}";
        Response slackResponse = Response.json(200, body);
        HttpResponse<String> response = adapter.toMicronautResponse(slackResponse);
        assertNotNull(response);
        assertEquals(200, response.getStatus().getCode());
        assertEquals(body.length(), response.getContentLength());
        assertEquals("application/json", response.getHeaders().getContentType().get());
    }

}
