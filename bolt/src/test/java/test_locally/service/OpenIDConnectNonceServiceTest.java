package test_locally.service;

import com.slack.api.app_backend.oauth.payload.VerificationCodePayload;
import com.slack.api.bolt.request.Request;
import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.request.builtin.OAuthCallbackRequest;
import com.slack.api.bolt.response.Response;
import com.slack.api.bolt.service.OpenIDConnectNonceService;
import com.slack.api.bolt.service.builtin.NullOpenIDConnectNonceService;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class OpenIDConnectNonceServiceTest {

    OpenIDConnectNonceService service = new OpenIDConnectNonceService() {
        @Override
        public String generateNewNonceValue() {
            return "valid-one";
        }

        @Override
        public void addNewNonceToDatastore(String nonce) {
        }

        @Override
        public boolean isAvailableInDatabase(String nonce) {
            return nonce != null && nonce.equals("123");
        }

        @Override
        public void deleteNonceFromDatastore(String nonce) {
        }
    };

    NullOpenIDConnectNonceService nullService = new NullOpenIDConnectNonceService();

    @Test
    public void generateNewNonceValue() {
        assertNotNull(service.generateNewNonceValue());
        assertNull(nullService.generateNewNonceValue());
    }

    @Test
    public void extractStateFromQueryString() {
        Map<String, List<String>> query = new HashMap<>();
        query.put("foo", Arrays.asList("bar", "baz"));
        query.put("nonce", Arrays.asList("123", "234"));
        RequestHeaders headers = new RequestHeaders(new HashMap<>());
        {
            String queryString = service.extractNonceFromQueryString(new OAuthCallbackRequest(query, "", new VerificationCodePayload(), headers));
            assertEquals("123", queryString);
        }
        {
            String queryString = nullService.extractNonceFromQueryString(new OAuthCallbackRequest(query, "", new VerificationCodePayload(), headers));
            assertEquals("123", queryString);
        }
    }

    @Test
    public void isValid() {
        Map<String, List<String>> query = new HashMap<>();
        query.put("foo", Arrays.asList("bar", "baz"));
        query.put("nonce", Arrays.asList("123", "234"));
        Map<String, List<String>> rawHeaders = new HashMap<>();
        RequestHeaders headers = new RequestHeaders(rawHeaders);
        VerificationCodePayload payload = new VerificationCodePayload();
        payload.setState("123");
        {
            boolean valid = service.isValid(new OAuthCallbackRequest(query, "", payload, headers));
            assertTrue(valid);
        }
        {
            boolean valid = nullService.isValid(new OAuthCallbackRequest(query, "", payload, headers));
            assertFalse(valid);
        }
    }

    @Test
    public void issueNewState() throws Exception {
        Request req = mock(Request.class);
        Response resp = mock(Response.class);
        {
            String nonce = service.issueNewNonce(req, resp);
            assertNotNull(nonce);
        }
        {
            String nonce = nullService.issueNewNonce(req, resp);
            assertNull(nonce);
        }
    }

    @Test
    public void consume() throws Exception {
        Request req = mock(Request.class);
        Response resp = mock(Response.class);
        service.consume(req, resp);
        nullService.consume(req, resp);
    }

}
