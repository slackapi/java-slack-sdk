package test_locally.api.util;

import com.slack.api.util.http.UserAgentInterceptor;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserAgentInterceptorTest {

    @Test
    public void defaultUserAgent() {
        // e.g., "Java-Slack-SDK; slack-api-client/1.0.0-M3-SNAPSHOT; OpenJDK 64-Bit Server VM/11.0.5; Mac OS X/10.15.3;"
        String userAgent = UserAgentInterceptor.buildDefaultUserAgent(Collections.emptyMap());
        String[] elements = userAgent.split(";");
        assertEquals(userAgent, 4, elements.length);
        assertTrue(elements[0], elements[0].trim().equals("Java-Slack-SDK"));
        assertTrue(elements[1], elements[1].trim().matches("slack-api-client/.+"));
        assertTrue(elements[2], elements[2].trim().matches("[^/]+/.+"));
        assertTrue(elements[3], elements[3].trim().matches("[^/]+/.+"));
    }

}