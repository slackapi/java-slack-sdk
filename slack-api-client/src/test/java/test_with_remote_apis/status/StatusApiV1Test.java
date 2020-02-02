package test_with_remote_apis.status;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.status.v1.LegacyStatusApiException;
import com.slack.api.status.v1.LegacyStatusClient;
import com.slack.api.status.v1.model.LegacyCurrentStatus;
import com.slack.api.status.v1.model.LegacySlackIssue;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

@Slf4j
public class StatusApiV1Test {

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

    @AfterClass
    public static void tearDown() throws InterruptedException {
        SlackTestConfig.awaitCompletion(testConfig);
    }

    @Test
    public void current() throws IOException, LegacyStatusApiException {
        LegacyCurrentStatus status = slack.statusLegacy().current();
        assertThat(status, is(notNullValue()));
    }

    @Test
    public void current_error() throws IOException {
        SlackConfig config = new SlackConfig();
        config.setLegacyStatusEndpointUrlPrefix(LegacyStatusClient.ENDPOINT_URL_PREFIX + "dummy/");
        Slack slack = Slack.getInstance(config);
        try {
            slack.statusLegacy().current();
            fail();
        } catch (LegacyStatusApiException e) {
            assertThat(e.getMessage(), is("status: 404, message: Not Found"));
        }
    }

    @Test
    public void history() throws IOException, LegacyStatusApiException {
        List<LegacySlackIssue> events = slack.statusLegacy().history();
        assertThat(events, is(notNullValue()));
    }

}
