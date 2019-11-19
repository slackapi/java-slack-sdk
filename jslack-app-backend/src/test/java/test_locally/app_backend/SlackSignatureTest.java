package test_locally.app_backend;

import com.github.seratch.jslack.app_backend.SlackSignature;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class SlackSignatureTest {

    @Test
    public void test() {
        // https://api.slack.com/docs/verifying-requests-from-slack
        SlackSignature.Generator generator = new SlackSignature.Generator("8f742231b10e8888abcd99yyyzzz85a5");
        String timestamp = "1531420618";
        String requestBody = "token=xyzz0WbapA4vBCDEFasx0q6G&team_id=T1DC2JH3J&team_domain=testteamnow&channel_id=G8PSS9T3V&channel_name=foobar&user_id=U2CERLKJA&user_name=roadrunner&command=%2Fwebhook-collect&text=&response_url=https%3A%2F%2Fhooks.slack.com%2Fcommands%2FT1DC2JH3J%2F397700885554%2F96rGlfmibIGlgcZRskXaIFfN&trigger_id=398738663015.47445629121.803a0bc887a14d10d2c447fce8b6703c";
        String generatedValue = generator.generate(timestamp, requestBody);
        assertThat(generatedValue, is("v0=a2114d57b48eac39b9ad189dd8316235a7b4a8d21a10bd27519666489c69b503"));
    }

    @Test
    public void timestampVerification_OK() {
        String request = "" + (System.currentTimeMillis() / 1000);
        long now = System.currentTimeMillis();
        boolean result = SlackSignature.TimestampVerifier.isValidTimestamp(request, now);
        assertTrue("request: " + request + " now: " + now, result);
    }

    @Test
    public void timestampVerification_NG() {
        String request = "" + ((System.currentTimeMillis() / 1000) - (60 * 5 + 1));
        long now = System.currentTimeMillis();
        boolean result = SlackSignature.TimestampVerifier.isValidTimestamp(request, now);
        assertFalse("request: " + request + " now: " + now, result);
    }

    @Test
    public void timestampVerification_OK_currentTimeMillis() {
        String request = "" + (System.currentTimeMillis() / 1000);
        boolean result = SlackSignature.TimestampVerifier.isValidTimestamp(request);
        assertTrue("request: " + request, result);
    }

    @Test
    public void timestampVerification_NG_currentTimeMillis() {
        String request = "" + ((System.currentTimeMillis() / 1000) - (60 * 5 + 1));
        boolean result = SlackSignature.TimestampVerifier.isValidTimestamp(request);
        assertFalse("request: " + request, result);
    }

}
