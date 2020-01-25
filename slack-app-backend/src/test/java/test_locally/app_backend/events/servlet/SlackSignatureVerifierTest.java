package test_locally.app_backend.events.servlet;

import com.github.seratch.jslack.app_backend.SlackSignature;
import com.github.seratch.jslack.app_backend.events.servlet.SlackSignatureVerifier;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

// https://api.slack.com/docs/verifying-requests-from-slack
public class SlackSignatureVerifierTest {

    @Test
    public void validServletRequest() {
        SlackSignature.Generator generator = new SlackSignature.Generator("8f742231b10e8888abcd99yyyzzz85a5");
        Long currentTimeInMilliSeconds = System.currentTimeMillis();
        String requestTimestamp = Long.toString(currentTimeInMilliSeconds / 1000);
        String requestBody = "token=xyzz0WbapA4vBCDEFasx0q6G&team_id=T1DC2JH3J&team_domain=testteamnow&channel_id=G8PSS9T3V&channel_name=foobar&user_id=U2CERLKJA&user_name=roadrunner&command=%2Fwebhook-collect&text=&response_url=https%3A%2F%2Fhooks.slack.com%2Fcommands%2FT1DC2JH3J%2F397700885554%2F96rGlfmibIGlgcZRskXaIFfN&trigger_id=398738663015.47445629121.803a0bc887a14d10d2c447fce8b6703c";

        String validSignature = generator.generate(requestTimestamp, requestBody);
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        when(httpServletRequest.getHeaderNames()).thenReturn(mock(Enumeration.class));
        when(httpServletRequest.getHeader(SlackSignature.HeaderNames.X_SLACK_REQUEST_TIMESTAMP)).thenReturn(requestTimestamp);
        when(httpServletRequest.getHeader(SlackSignature.HeaderNames.X_SLACK_SIGNATURE)).thenReturn(validSignature);

        SlackSignatureVerifier verifier = new SlackSignatureVerifier(generator);
        assertThat(verifier.isValid(httpServletRequest, requestBody), is(true));
    }

    @Test
    public void inValidServletRequest() {
        SlackSignature.Generator generator = new SlackSignature.Generator("8f742231b10e8888abcd99yyyzzz85a5");
        String timestamp = "1531420618";
        String requestBody = "token=xyzz0WbapA4vBCDEFasx0q6G&team_id=T1DC2JH3J&team_domain=testteamnow&channel_id=G8PSS9T3V&channel_name=foobar&user_id=U2CERLKJA&user_name=roadrunner&command=%2Fwebhook-collect&text=&response_url=https%3A%2F%2Fhooks.slack.com%2Fcommands%2FT1DC2JH3J%2F397700885554%2F96rGlfmibIGlgcZRskXaIFfN&trigger_id=398738663015.47445629121.803a0bc887a14d10d2c447fce8b6703c";
        SlackSignatureVerifier verifier = new SlackSignatureVerifier(generator);
        String validSignature = "v0=a2114d57b48eac39b9ad189dd8316235a7b4a8d21a10bd27519666489c69b503";
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        when(httpServletRequest.getHeaderNames()).thenReturn(mock(Enumeration.class));
        when(httpServletRequest.getHeader(SlackSignature.HeaderNames.X_SLACK_REQUEST_TIMESTAMP)).thenReturn(timestamp);
        when(httpServletRequest.getHeader(SlackSignature.HeaderNames.X_SLACK_SIGNATURE)).thenReturn(validSignature);
        assertThat(verifier.isValid(httpServletRequest, requestBody), is(false));
    }

}
