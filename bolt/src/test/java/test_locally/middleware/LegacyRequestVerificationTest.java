package test_locally.middleware;

import com.slack.api.bolt.middleware.MiddlewareChain;
import com.slack.api.bolt.middleware.builtin.LegacyRequestVerification;
import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.request.builtin.BlockActionRequest;
import com.slack.api.bolt.request.builtin.SSLCheckRequest;
import com.slack.api.bolt.request.builtin.SlashCommandRequest;
import com.slack.api.bolt.response.Response;
import org.junit.Test;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class LegacyRequestVerificationTest {

    final MiddlewareChain chain = req -> Response.error(200);

    @Test
    public void validRequest() throws Exception {
        String payload = "{\"token\":\"expected\",\"team\":{},\"user\":{}}";
        String requestBody = "payload=" + URLEncoder.encode(payload, "UTF-8");
        LegacyRequestVerification middleware = new LegacyRequestVerification("expected");
        Map<String, List<String>> rawHeaders = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        RequestHeaders headers = new RequestHeaders(rawHeaders);
        BlockActionRequest req = new BlockActionRequest(requestBody, payload, headers);
        Response resp = new Response();
        Response result = middleware.apply(req, resp, chain);
        assertEquals(200L, result.getStatusCode().longValue());
    }

    @Test
    public void validRequest_ssl_check() throws Exception {
        String requestBody = "token=valid&ssl_check=1";
        LegacyRequestVerification middleware = new LegacyRequestVerification("valid");
        Map<String, List<String>> rawHeaders = new HashMap<>();
        RequestHeaders headers = new RequestHeaders(rawHeaders);
        SSLCheckRequest req = new SSLCheckRequest(requestBody, headers);
        Response resp = new Response();
        Response result = middleware.apply(req, resp, chain);
        assertEquals(200L, result.getStatusCode().longValue());
    }

    String slashCommandPayload = "token=gIkuvaNzQIHg97ATvDxqgjtO" +
            "&team_id=T0001" +
            "&team_domain=example" +
            "&enterprise_id=E0001" +
            "&enterprise_name=Globular%20Construct%20Inc" +
            "&channel_id=C2147483705" +
            "&channel_name=test" +
            "&user_id=U2147483697" +
            "&user_name=Steve" +
            "&command=/weather" +
            "&text=94070" +
            "&response_url=https://hooks.slack.com/commands/1234/5678" +
            "&trigger_id=13345224609.738474920.8088930838d88f008e0";

    @Test
    public void validRequest_command() throws Exception {
        String requestBody = slashCommandPayload;
        LegacyRequestVerification middleware = new LegacyRequestVerification("gIkuvaNzQIHg97ATvDxqgjtO");
        Map<String, List<String>> rawHeaders = new HashMap<>();
        RequestHeaders headers = new RequestHeaders(rawHeaders);
        SlashCommandRequest req = new SlashCommandRequest(requestBody, headers);
        Response resp = new Response();
        Response result = middleware.apply(req, resp, chain);
        assertEquals(200L, result.getStatusCode().longValue());
    }

    @Test
    public void invalidRequest_invalid() throws Exception {
        String payload = "{\"token\":\"invalid\",\"team\":{},\"user\":{}}";
        String requestBody = "payload=" + URLEncoder.encode(payload, "UTF-8");
        LegacyRequestVerification middleware = new LegacyRequestVerification("expected");
        Map<String, List<String>> rawHeaders = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        RequestHeaders headers = new RequestHeaders(rawHeaders);
        BlockActionRequest req = new BlockActionRequest(requestBody, payload, headers);
        Response resp = new Response();
        Response result = middleware.apply(req, resp, chain);
        assertEquals(401L, result.getStatusCode().longValue());
    }
}
