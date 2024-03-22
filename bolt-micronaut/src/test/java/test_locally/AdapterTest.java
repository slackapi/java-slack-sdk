package test_locally;

import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.micronaut.SlackAppMicronautAdapter;
import com.slack.api.bolt.request.Request;
import com.slack.api.bolt.response.Response;
import io.micronaut.core.convert.DefaultMutableConversionService;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.simple.SimpleHttpHeaders;
import io.micronaut.http.simple.SimpleHttpParameters;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
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
        SimpleHttpHeaders headers = new SimpleHttpHeaders(rawHeaders, new DefaultMutableConversionService());
        when(req.getHeaders()).thenReturn(headers);
        Map<CharSequence, List<String>> params = new HashMap<>();
        params.put("foo", Arrays.asList("bar", "baz"));
        SimpleHttpParameters parameters = new SimpleHttpParameters(params, new DefaultMutableConversionService());
        when(req.getParameters()).thenReturn(parameters);

        Request<?> slackRequest = adapter.toSlackRequest(req, "token=random&ssl_check=1");

        assertNotNull(slackRequest);
        assertEquals("token=random&ssl_check=1", slackRequest.getRequestBodyAsString());
        assertEquals("xxxxxxx", slackRequest.getHeaders().getFirstValue("X-Slack-Signature"));
        assertEquals("bar", slackRequest.getQueryString().get("foo").get(0));
    }

    @Test
    public void toSlackRequest_with_remote_address() throws UnknownHostException {
        AppConfig config = AppConfig.builder().build();
        SlackAppMicronautAdapter adapter = new SlackAppMicronautAdapter(config);

        HttpRequest<String> req = mock(HttpRequest.class);

        InetSocketAddress isa = new InetSocketAddress("127.0.0.1", 443);
        when(req.getRemoteAddress()).thenReturn(isa);

        Request<?> slackRequest = adapter.toSlackRequest(req, "token=random&ssl_check=1");

        assertNotNull(slackRequest);
        String ipAddress = slackRequest.getClientIpAddress();
        assertTrue(ipAddress.equals("127.0.0.1") || ipAddress.equals("0.0.0.0000000000001"));
    }

    @Test
    public void toMicronautResponse() {
        AppConfig config = AppConfig.builder().build();
        SlackAppMicronautAdapter adapter = new SlackAppMicronautAdapter(config);

        String body = "{\"text\":\"Hi there!\"}";
        Response slackResponse = Response.json(200, body);
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("Set-Cookie", Arrays.asList("foo=bar", "id=123"));
        slackResponse.setHeaders(headers);
        HttpResponse<String> response = adapter.toMicronautResponse(slackResponse);
        assertNotNull(response);
        assertEquals(200, response.getStatus().getCode());
        assertEquals(body.length(), response.getContentLength());
        assertEquals("application/json; charset=utf-8", response.getHeaders().getContentType().get());
    }

}
