package test_locally.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.slack.api.app_backend.oauth.payload.VerificationCodePayload;
import com.slack.api.bolt.request.Request;
import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.request.builtin.OAuthCallbackRequest;
import com.slack.api.bolt.response.Response;
import com.slack.api.bolt.service.OAuthStateService;

public class OAuthStateServiceTest {

    OAuthStateService service = new OAuthStateService() {
        @Override
        public void addNewStateToDatastore(String state) {
        }

        @Override
        public boolean isAvailableInDatabase(String state) {
            return state.equals("123");
        }

        @Override
        public void deleteStateFromDatastore(String state) {

        }
    };

    @Test
    public void getSessionCookieName() throws Exception {
        assertNotNull(service.getSessionCookieName());
    }

    @Test
    public void generateNewStateValue() throws Exception {
        assertNotNull(service.generateNewStateValue());
    }

    @Test
    public void generateSessionCookieValue() throws Exception {
        assertNotNull(service.generateSessionCookieValue(null, "foo"));
    }

    @Test
    public void extractStateFromQueryString() throws Exception {
        Map<String, List<String>> query = new HashMap<>();
        query.put("foo", Arrays.asList("bar", "baz"));
        query.put("state", Arrays.asList("123", "234"));
        RequestHeaders headers = new RequestHeaders(new HashMap<>());
        String queryString = service.extractStateFromQueryString(new OAuthCallbackRequest(query, "", new VerificationCodePayload(), headers));
        assertEquals("123", queryString);
    }

    @Test
    public void isValid() {
        Map<String, List<String>> query = new HashMap<>();
        query.put("foo", Arrays.asList("bar", "baz"));
        query.put("state", Arrays.asList("123", "234"));
        Map<String, List<String>> rawHeaders = new HashMap<>();
        rawHeaders.put("Cookie", Arrays.asList(
          "__cookie1=ABC",
          service.getSessionCookieName() + "=123"));
        RequestHeaders headers = new RequestHeaders(rawHeaders);
        VerificationCodePayload payload = new VerificationCodePayload();
        payload.setState("123");
        boolean valid = service.isValid(new OAuthCallbackRequest(query, "", payload, headers));
        assertTrue(valid);
    }

    @Test
    public void isValid_no_cookie() {
        Map<String, List<String>> query = new HashMap<>();
        query.put("foo", Arrays.asList("bar", "baz"));
        query.put("state", Arrays.asList("123", "234"));
        Map<String, List<String>> rawHeaders = new HashMap<>();
        rawHeaders.put("Cookie", Collections.emptyList());
        RequestHeaders headers = new RequestHeaders(rawHeaders);
        VerificationCodePayload payload = new VerificationCodePayload();
        payload.setState("123");
        boolean invalid = service.isValid(new OAuthCallbackRequest(query, "", payload, headers));
        assertFalse(invalid);
    }

    @Test
    public void isValid_different_param_value() {
        Map<String, List<String>> query = new HashMap<>();
        query.put("foo", Arrays.asList("bar", "baz"));
        query.put("state", Arrays.asList("234"));
        Map<String, List<String>> rawHeaders = new HashMap<>();
        rawHeaders.put("Cookie", Arrays.asList(service.getSessionCookieName() + "=123"));
        RequestHeaders headers = new RequestHeaders(rawHeaders);
        VerificationCodePayload payload = new VerificationCodePayload();
        payload.setState("234");
        boolean invalid = service.isValid(new OAuthCallbackRequest(query, "", payload, headers));
        assertFalse(invalid);
    }

    @Test
    public void isValid_multiple_cookies() {
        Map<String, List<String>> query = new HashMap<>();
        query.put("foo", Arrays.asList("bar", "baz"));
        query.put("state", Arrays.asList("123", "234"));
        Map<String, List<String>> rawHeaders = new HashMap<>();
        rawHeaders.put("Cookie", Arrays.asList(
                "__cookie1=abc; _cookie2=def; " + service.getSessionCookieName() + "=123"));
        RequestHeaders headers = new RequestHeaders(rawHeaders);
        VerificationCodePayload payload = new VerificationCodePayload();
        payload.setState("123");
        boolean valid = service.isValid(new OAuthCallbackRequest(query, "", payload, headers));
        assertTrue(valid);
    }

    @Test
    public void issueNewState() throws Exception {
        Request req = mock(Request.class);
        Response resp = mock(Response.class);
        String state = service.issueNewState(req, resp);
        assertNotNull(state);
    }

    @Test
    public void consume() throws Exception {
        Request req = mock(Request.class);
        Response resp = mock(Response.class);
        service.consume(req, resp);
    }

}
