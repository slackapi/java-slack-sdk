package test_with_remote_apis.methods;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.apps.AppsUninstallResponse;
import com.slack.api.methods.response.apps.event.authorizations.AppsEventAuthorizationsListResponse;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class apps_Test {

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

    String appLevelToken = System.getenv(Constants.SLACK_SDK_TEST_APP_TOKEN);
    String botToken = System.getenv(Constants.SLACK_SDK_TEST_BOT_TOKEN);
    String userToken = System.getenv(Constants.SLACK_SDK_TEST_USER_TOKEN);

    @AfterClass
    public static void tearDown() throws InterruptedException {
        SlackTestConfig.awaitCompletion(testConfig);
    }

    @Test
    public void appsUninstall_bot() throws IOException, SlackApiException {
        AppsUninstallResponse response = slack.methods().appsUninstall(req -> req.token(botToken));
        assertThat(response.getError(), is(notNullValue()));
    }

    @Ignore
    @Test
    public void appsUninstall_user() throws IOException, SlackApiException {
        AppsUninstallResponse response = slack.methods().appsUninstall(req -> req.token(userToken));
        assertThat(response.getError(), is("not_allowed_token_type"));
        assertThat(response.isOk(), is(false));
    }

    @Ignore
    @Test
    public void appsUninstall_async_user() throws Exception {
        AppsUninstallResponse response = slack.methodsAsync().appsUninstall(req -> req.token(userToken)).get();
        assertThat(response.getError(), is("not_allowed_token_type"));
        assertThat(response.isOk(), is(false));
    }

    @Test
    public void appsEventAuthorizationsList() throws ExecutionException, InterruptedException {
        AppsEventAuthorizationsListResponse response = slack.methodsAsync(appLevelToken).appsEventAuthorizationsList(req -> req
                .eventContext("dummy")
                .cursor("dummy-cursor")
                .limit(120)
        ).get();
        assertThat(response.getError(), is(notNullValue()));
    }

}
