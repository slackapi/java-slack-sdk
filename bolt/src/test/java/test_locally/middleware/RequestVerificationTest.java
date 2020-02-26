package test_locally.middleware;

import com.slack.api.app_backend.SlackSignature;
import com.slack.api.bolt.middleware.MiddlewareChain;
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

public class RequestVerificationTest {

    final String secret = "foo-bar-baz";
    final SlackSignature.Generator generator = new SlackSignature.Generator(secret);
    final SlackSignature.Verifier verifier = new SlackSignature.Verifier(generator);

    final MiddlewareChain chain = req -> Response.error(404);

    @Test
    public void validRequest() throws Exception {
        String payload = "{\"token\":\"expected\",\"team\":{},\"user\":{}}";
        String requestBody = "payload=" + URLEncoder.encode(payload, "UTF-8");
        RequestVerification middleware = new RequestVerification(verifier);
        Map<String, List<String>> rawHeaders = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        rawHeaders.put(SlackSignature.HeaderNames.X_SLACK_REQUEST_TIMESTAMP, Arrays.asList(timestamp));
        rawHeaders.put(SlackSignature.HeaderNames.X_SLACK_SIGNATURE, Arrays.asList(generator.generate(timestamp, requestBody)));
        RequestHeaders headers = new RequestHeaders(rawHeaders);
        BlockActionRequest req = new BlockActionRequest(requestBody, payload, headers);
        Response resp = new Response();
        Response result = middleware.apply(req, resp, chain);
        assertEquals(404L, result.getStatusCode().longValue());
    }

    @Test
    public void invalidRequest_expired() throws Exception {
        String payload = "{\"token\":\"expected\",\"team\":{},\"user\":{}}";
        String requestBody = "payload=" + URLEncoder.encode(payload, "UTF-8");
        RequestVerification middleware = new RequestVerification(verifier);
        Map<String, List<String>> rawHeaders = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000 - 360);
        rawHeaders.put(SlackSignature.HeaderNames.X_SLACK_REQUEST_TIMESTAMP, Arrays.asList(timestamp));
        rawHeaders.put(SlackSignature.HeaderNames.X_SLACK_SIGNATURE, Arrays.asList(generator.generate(timestamp, requestBody)));
        RequestHeaders headers = new RequestHeaders(rawHeaders);
        BlockActionRequest req = new BlockActionRequest(requestBody, payload, headers);
        Response resp = new Response();
        Response result = middleware.apply(req, resp, chain);
        assertEquals(401L, result.getStatusCode().longValue());
    }

    @Test
    public void invalidRequest_invalid() throws Exception {
        String payload = "{\"token\":\"expected\",\"team\":{},\"user\":{}}";
        String requestBody = "payload=" + URLEncoder.encode(payload, "UTF-8");
        RequestVerification middleware = new RequestVerification(verifier);
        Map<String, List<String>> rawHeaders = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        rawHeaders.put(SlackSignature.HeaderNames.X_SLACK_REQUEST_TIMESTAMP, Arrays.asList(timestamp));
        rawHeaders.put(SlackSignature.HeaderNames.X_SLACK_SIGNATURE, Arrays.asList(generator.generate(timestamp, requestBody)));
        RequestHeaders headers = new RequestHeaders(rawHeaders);
        BlockActionRequest req = new BlockActionRequest("something different", payload, headers);
        Response resp = new Response();
        Response result = middleware.apply(req, resp, chain);
        assertEquals(401L, result.getStatusCode().longValue());
    }
}
