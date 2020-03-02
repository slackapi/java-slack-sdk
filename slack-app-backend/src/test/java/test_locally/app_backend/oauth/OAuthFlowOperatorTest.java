package test_locally.app_backend.oauth;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.app_backend.config.SlackAppConfig;
import com.slack.api.app_backend.oauth.OAuthFlowOperator;
import com.slack.api.app_backend.oauth.payload.VerificationCodePayload;
import com.slack.api.methods.response.oauth.OAuthAccessResponse;
import com.slack.api.methods.response.oauth.OAuthV2AccessResponse;
import org.junit.Test;
import util.MockSlackApiServer;

import static org.junit.Assert.assertNotNull;

public class OAuthFlowOperatorTest {

    MockSlackApiServer slackApiServer = new MockSlackApiServer();

    @Test
    public void test() throws Exception {
        slackApiServer.start();

        try {
            SlackConfig config = new SlackConfig();
            config.setMethodsEndpointUrlPrefix(slackApiServer.getMethodsEndpointPrefix());
            Slack slack = Slack.getInstance(config);
            SlackAppConfig slackAppConfig = SlackAppConfig.builder()
                    .clientId("123.123")
                    .clientSecret("secret")
                    .redirectUri("https://www.example.com/callback")
                    .build();
            OAuthFlowOperator operator = new OAuthFlowOperator(slack, slackAppConfig);
            VerificationCodePayload payload = new VerificationCodePayload();
            payload.setState("xxx");
            payload.setCode("yyy");

            OAuthAccessResponse v1resp = operator.callOAuthAccessMethod(payload);
            assertNotNull(v1resp);

            OAuthV2AccessResponse v2resp = operator.callOAuthV2AccessMethod(payload);
            assertNotNull(v2resp);
        } finally {
            slackApiServer.stop();
        }
    }
}
