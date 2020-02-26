package test_locally.middleware;

import com.slack.api.bolt.middleware.MiddlewareChain;
import com.slack.api.bolt.middleware.builtin.SSLCheck;
import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.request.builtin.BlockActionRequest;
import com.slack.api.bolt.request.builtin.SSLCheckRequest;
import com.slack.api.bolt.response.Response;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class SSLCheckTest {

    final MiddlewareChain chain = req -> Response.error(404);

    @Test
    public void validRequest() throws Exception {
        SSLCheck middleware = new SSLCheck("expected");
        Map<String, List<String>> rawHeaders = new HashMap<>();
        RequestHeaders headers = new RequestHeaders(rawHeaders);
        SSLCheckRequest req = new SSLCheckRequest("token=expected&ssl_check=1", headers);
        Response resp = new Response();
        Response result = middleware.apply(req, resp, chain);
        assertEquals(200L, result.getStatusCode().longValue());
    }

    @Test
    public void invalidToken() throws Exception {
        SSLCheck middleware = new SSLCheck("expected");
        Map<String, List<String>> rawHeaders = new HashMap<>();
        RequestHeaders headers = new RequestHeaders(rawHeaders);
        SSLCheckRequest req = new SSLCheckRequest("token=invalid&ssl_check=1", headers);
        Response resp = new Response();
        Response result = middleware.apply(req, resp, chain);
        assertEquals(401L, result.getStatusCode().longValue());
    }

    @Test
    public void otherRequests() throws Exception {
        SSLCheck middleware = new SSLCheck("expected");
        Map<String, List<String>> rawHeaders = new HashMap<>();
        RequestHeaders headers = new RequestHeaders(rawHeaders);
        BlockActionRequest req = new BlockActionRequest("payload={}", "{\"team\":{},\"user\":{}}", headers);
        Response resp = new Response();
        Response result = middleware.apply(req, resp, chain);
        assertEquals(404L, result.getStatusCode().longValue());
    }
}
