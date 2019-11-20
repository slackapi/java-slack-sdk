package test_with_remote_apis.methods;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.SlackConfig;
import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.response.auth.AuthRevokeResponse;
import com.github.seratch.jslack.api.methods.response.auth.AuthTestResponse;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class auth_Test {

    Slack slack = Slack.getInstance(SlackTestConfig.get());

    @Test
    public void authRevoke() throws IOException, SlackApiException {
        AuthRevokeResponse response = slack.methods().authRevoke(req -> req.token("dummy").test(true));
        assertThat(response.isOk(), is(false));
        assertThat(response.getError(), is("invalid_auth"));
        assertThat(response.isRevoked(), is(false));
    }

    @Test
    public void authTest() throws IOException, SlackApiException {
        String token = System.getenv(Constants.SLACK_TEST_OAUTH_ACCESS_TOKEN);
        AuthTestResponse response = slack.methods().authTest(req -> req.token(token));
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
        assertThat(response.getUrl(), is(notNullValue()));
    }

    @Test
    public void authTest2() throws IOException, SlackApiException {
        String token = System.getenv(Constants.SLACK_TEST_OAUTH_ACCESS_TOKEN);
        AuthTestResponse response = slack.methods(token).authTest(req -> req);
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
        assertThat(response.getUrl(), is(notNullValue()));
    }

    @Test
    public void authTest_grid() throws IOException, SlackApiException {
        String token = System.getenv(Constants.SLACK_TEST_ADMIN_WORKSPACE_USER_OAUTH_ACCESS_TOKEN);
        AuthTestResponse response = slack.methods().authTest(req -> req.token(token));
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
        assertThat(response.getUrl(), is(notNullValue()));
    }

    @Test(expected = IllegalStateException.class)
    public void authTest_missingToken() throws IOException, SlackApiException {
        SlackConfig config = new SlackConfig();
        config.setTokenExistenceVerificationEnabled(true);
        Slack slack = Slack.getInstance(config);
        slack.methods().authTest(req -> req);
    }

}
