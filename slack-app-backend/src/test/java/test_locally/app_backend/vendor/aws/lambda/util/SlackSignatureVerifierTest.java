package test_locally.app_backend.vendor.aws.lambda.util;

import com.slack.api.app_backend.SlackSignature;
import com.slack.api.app_backend.vendor.aws.lambda.request.ApiGatewayRequest;
import com.slack.api.app_backend.vendor.aws.lambda.util.SlackSignatureVerifier;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

// https://api.slack.com/docs/verifying-requests-from-slack
public class SlackSignatureVerifierTest {

    @Test
    public void valid() {
        SlackSignature.Generator generator = new SlackSignature.Generator("8f742231b10e8888abcd99yyyzzz85a5");
        String timestamp = "1531420618";
        String requestBody = "token=xyzz0WbapA4vBCDEFasx0q6G&team_id=T1DC2JH3J&team_domain=testteamnow&channel_id=G8PSS9T3V&channel_name=foobar&user_id=U2CERLKJA&user_name=roadrunner&command=%2Fwebhook-collect&text=&response_url=https%3A%2F%2Fhooks.slack.com%2Fcommands%2FT1DC2JH3J%2F397700885554%2F96rGlfmibIGlgcZRskXaIFfN&trigger_id=398738663015.47445629121.803a0bc887a14d10d2c447fce8b6703c";
        SlackSignatureVerifier verifier = new SlackSignatureVerifier(generator);

        ApiGatewayRequest request = new ApiGatewayRequest();
        request.setBody(requestBody);
        request.setHeaders(new HashMap<>());
        request.getHeaders().put("X-Slack-Request-Timestamp", timestamp);
        String validSignature = "v0=a2114d57b48eac39b9ad189dd8316235a7b4a8d21a10bd27519666489c69b503";
        request.getHeaders().put("X-Slack-Signature", validSignature);

        assertThat(verifier.isValid(request, 1531420618 * 1000), is(true));
    }

    @Test
    public void invalid() {
        SlackSignature.Generator generator = new SlackSignature.Generator("8f742231b10e8888abcd99yyyzzz85a5");
        String timestamp = "1531420620"; // invalid timestamp
        String requestBody = "token=xyzz0WbapA4vBCDEFasx0q6G&team_id=T1DC2JH3J&team_domain=testteamnow&channel_id=G8PSS9T3V&channel_name=foobar&user_id=U2CERLKJA&user_name=roadrunner&command=%2Fwebhook-collect&text=&response_url=https%3A%2F%2Fhooks.slack.com%2Fcommands%2FT1DC2JH3J%2F397700885554%2F96rGlfmibIGlgcZRskXaIFfN&trigger_id=398738663015.47445629121.803a0bc887a14d10d2c447fce8b6703c";
        SlackSignatureVerifier verifier = new SlackSignatureVerifier(generator);

        ApiGatewayRequest request = new ApiGatewayRequest();
        request.setBody(requestBody);
        request.setHeaders(new HashMap<>());
        request.getHeaders().put("X-Slack-Request-Timestamp", timestamp);

        String invalidSignature = "v0=a2114d57b48eac39b9ad189dd8316235a7b4a8d21a10bd27519666489c69b503";
        request.getHeaders().put("X-Slack-Signature", invalidSignature);

        assertThat(verifier.isValid(request, 1531420618 * 1000), is(false));
    }

}
