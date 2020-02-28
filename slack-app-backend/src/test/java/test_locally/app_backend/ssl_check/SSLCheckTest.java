package test_locally.app_backend.ssl_check;

import com.slack.api.app_backend.ssl_check.SSLCheckPayloadDetector;
import com.slack.api.app_backend.ssl_check.SSLCheckPayloadParser;
import com.slack.api.app_backend.ssl_check.payload.SSLCheckPayload;
import org.junit.Test;

import static org.junit.Assert.*;

public class SSLCheckTest {

    @Test
    public void testSSLCheckPayloadParser() {
        SSLCheckPayloadParser parser = new SSLCheckPayloadParser();
        SSLCheckPayload payload = parser.parse("token=something&ssl_check=1");
        assertEquals("something", payload.getToken());
        assertEquals("1", payload.getSslCheck());
    }

    @Test
    public void testSSLCheckPayloadDetector() {
        SSLCheckPayloadDetector detector = new SSLCheckPayloadDetector();
        assertTrue(detector.isSSLCheckRequest("token=something&ssl_check=1"));
        assertFalse(detector.isSSLCheckRequest("{}"));
        assertFalse(detector.isSSLCheckRequest("payload={}"));
    }
}
