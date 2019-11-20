package test_with_remote_apis.methods;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.response.apps.AppsUninstallResponse;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

// TODO: valid test
@Slf4j
public class apps_Test {

    Slack slack = Slack.getInstance(SlackTestConfig.get());

    @Test
    public void appsUninstall() throws IOException, SlackApiException {
        String token = System.getenv(Constants.SLACK_TEST_OAUTH_ACCESS_TOKEN);
        AppsUninstallResponse response = slack.methods().appsUninstall(req -> req
                .token(token));
        assertThat(response.getError(), is("not_allowed_token_type"));
        assertThat(response.isOk(), is(false));
    }

}
