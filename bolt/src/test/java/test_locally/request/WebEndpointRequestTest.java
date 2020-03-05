package test_locally.request;

import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.request.WebEndpointRequest;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class WebEndpointRequestTest {

    @Test
    public void test() {
        Map<String, List<String>> rawHeaders = new HashMap<>();
        String signature = "v0=a2114d57b48eac39b9ad189dd8316235a7b4a8d21a10bd27519666489c69b503";
        String query = "foo=bar";
        String remoteAddress = "127.0.0.1";
        rawHeaders.put("X-Slack-Signature", Arrays.asList(signature));
        RequestHeaders headers = new RequestHeaders(rawHeaders);
        WebEndpointRequest request = new WebEndpointRequest(query, null, headers);
        request.setClientIpAddress(remoteAddress);

        assertEquals(remoteAddress, request.getClientIpAddress());
        assertEquals(query, request.getQueryString());
        assertEquals(1, request.getHeaders().getNames().size());
        assertEquals("x-slack-signature", request.getHeaders().getNames().iterator().next());
        assertEquals(signature, request.getHeaders().getFirstValue("X-Slack-Signature"));
    }
}
