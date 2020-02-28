package test_locally.middleware;

import com.slack.api.app_backend.SlackSignature;
import com.slack.api.bolt.middleware.MiddlewareChain;
import com.slack.api.bolt.middleware.builtin.LegacyRequestVerification;
import com.slack.api.bolt.middleware.builtin.RequestVerification;
import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.request.builtin.BlockActionRequest;
import com.slack.api.bolt.response.Response;
import org.junit.Test;

import java.net.URLEncoder;
import java.util.Arrays;
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
