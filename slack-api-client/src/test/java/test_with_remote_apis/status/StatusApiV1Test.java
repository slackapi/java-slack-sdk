package test_with_remote_apis.status;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.status.v1.LegacyStatusApiException;
import com.slack.api.status.v1.LegacyStatusClient;
import com.slack.api.status.v1.model.LegacyCurrentStatus;
import com.slack.api.status.v1.model.LegacySlackIssue;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

@Slf4j
public class StatusApiV1Test {

    static Slack slack = Slack.getInstance();

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
            assertThat(e.getResponse().code(), is(404));
        }
    }

    @Test
    public void history() throws IOException, LegacyStatusApiException {
        List<LegacySlackIssue> events = slack.statusLegacy().history();
        assertThat(events, is(notNullValue()));
    }

}
