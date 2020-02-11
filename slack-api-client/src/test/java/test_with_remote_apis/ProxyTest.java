package test_with_remote_apis;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.audit.response.SchemasResponse;
import com.slack.api.methods.response.auth.AuthTestResponse;
import com.slack.api.rtm.RTMClient;
import com.slack.api.scim.SCIMApiException;
import com.slack.api.scim.response.ServiceProviderConfigsGetResponse;
import com.slack.api.status.v2.model.CurrentStatus;
import com.slack.api.util.http.SlackHttpClient;
import config.Constants;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class ProxyTest {

    static String proxyUrl = "http://localhost:8888";

    static SlackConfig config = new SlackConfig();

    static {
        config.setProxyUrl(proxyUrl);
    }

    static Slack slack = Slack.getInstance(config);

    static String botToken = System.getenv(Constants.SLACK_SDK_TEST_BOT_TOKEN);
    static String rtmBotToken = System.getenv(Constants.SLACK_SDK_TEST_CLASSIC_APP_BOT_TOKEN);
    static String scimToken = System.getenv(Constants.SLACK_SDK_TEST_GRID_ORG_ADMIN_USER_TOKEN);
    static String auditLogsToken = System.getenv(Constants.SLACK_SDK_TEST_GRID_ORG_ADMIN_USER_TOKEN);

    @Ignore
    @Test
    public void methods() throws Exception {
        AuthTestResponse apiResponse = slack.methods().authTest(r -> r.token(botToken));
        assertThat(apiResponse.getError(), is(nullValue()));
    }

    @Ignore
    @Test
    public void rtm() throws Exception {
        SlackHttpClient httpClient = new SlackHttpClient();
        Slack slack = Slack.getInstance(config, httpClient);
        final AtomicBoolean received = new AtomicBoolean(false);
        try (RTMClient rtm = slack.rtmConnect(rtmBotToken)) { // slack-msgs.com
            rtm.addMessageHandler((msg) -> {
                log.info("Got a message - {}", msg);
                received.set(true);
            });
            rtm.addErrorHandler((e) -> {
                log.error("Got an error - {}", e.getMessage(), e);
            });
            rtm.connect();
            Thread.sleep(1000L);
            rtm.sendMessage("foo");
        }
        assertThat(received.get(), is(true));
    }

    @Ignore
    @Test
    public void audit() throws Exception {
        if (auditLogsToken != null) {
            SchemasResponse response = slack.audit(auditLogsToken).getSchemas();
            assertThat(response, is(notNullValue()));
        }
    }

    @Ignore
    @Test
    public void scim() throws IOException, SCIMApiException {
        if (scimToken != null) {
            ServiceProviderConfigsGetResponse response = slack.scim(scimToken).getServiceProviderConfigs(req -> req);
            assertThat(response.getAuthenticationSchemes(), is(notNullValue()));
        }
    }

    @Ignore
    @Test
    public void status() throws Exception {
        CurrentStatus current = slack.status().current();
        assertThat(current, is(notNullValue()));
    }
}
