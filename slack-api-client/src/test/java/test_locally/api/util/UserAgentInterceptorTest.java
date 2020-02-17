package test_locally.api.util;

import com.slack.api.util.http.UserAgentInterceptor;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserAgentInterceptorTest {

    @Test
    public void defaultUserAgent() {
        String userAgent = UserAgentInterceptor.buildDefaultUserAgent(Collections.emptyMap());
        String[] elements = userAgent.split(";");
        assertEquals(userAgent, 3, elements.length);
        assertTrue(elements[0], elements[0].trim().matches("slack-api-client/unknown"));
        assertTrue(elements[1], elements[1].trim().matches("[^/]+/.+"));
        assertTrue(elements[2], elements[2].trim().matches("[^/]+/.+"));
    }

}