package test_with_remote_apis.web_api;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.status.v1.LegacyStatusApiException;
import com.github.seratch.jslack.api.status.v1.model.LegacyCurrentStatus;
import com.github.seratch.jslack.api.status.v1.model.LegacySlackIssue;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@Slf4j
public class status_v1_Test {

    Slack slack = Slack.getInstance(SlackTestConfig.get());

    @Test
    public void current() throws IOException, LegacyStatusApiException {
        LegacyCurrentStatus status = slack.statusLegacy().current();
        assertThat(status, is(notNullValue()));
    }

    @Test
    public void history() throws IOException, LegacyStatusApiException {
        List<LegacySlackIssue> events = slack.statusLegacy().history();
        assertThat(events, is(notNullValue()));
    }

}
