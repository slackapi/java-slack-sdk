package test_locally.service;

import com.slack.api.app_backend.oauth.payload.VerificationCodePayload;
import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.request.builtin.OAuthCallbackRequest;
import com.slack.api.bolt.service.OAuthStateService;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

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
        rawHeaders.put("Cookie", Arrays.asList(service.getSessionCookieName() + "=123"));
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

}
