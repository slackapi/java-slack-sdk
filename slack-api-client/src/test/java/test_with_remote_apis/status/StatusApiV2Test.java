package test_with_remote_apis.status;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.status.v2.StatusApiException;
import com.slack.api.status.v2.StatusClient;
import com.slack.api.status.v2.model.CurrentStatus;
import com.slack.api.status.v2.model.SlackIssue;
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
public class StatusApiV2Test {

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

    @AfterClass
    public static void tearDown() throws InterruptedException {
        SlackTestConfig.awaitCompletion(testConfig);
    }

    @Test
    public void current() throws IOException, StatusApiException {
        CurrentStatus status = slack.status().current();
        assertThat(status, is(notNullValue()));
    }

    @Test
    public void current_error() throws IOException {
        SlackConfig config = new SlackConfig();
        config.setStatusEndpointUrlPrefix(StatusClient.ENDPOINT_URL_PREFIX + "dummy/");
        Slack slack = Slack.getInstance(config);
        try {
            slack.status().current();
            fail();
        } catch (StatusApiException e) {
            assertThat(e.getResponse().code(), is(404));
        }
    }

    @Test
    public void history() throws IOException, StatusApiException {
        List<SlackIssue> events = slack.status().history();
        assertThat(events, is(notNullValue()));
    }

}
