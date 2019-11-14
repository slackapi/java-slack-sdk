package test_with_remote_apis.status;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.status.v2.StatusApiException;
import com.github.seratch.jslack.api.status.v2.model.CurrentStatus;
import com.github.seratch.jslack.api.status.v2.model.SlackIssue;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@Slf4j
public class StatusApiV2Test {

    Slack slack = Slack.getInstance(SlackTestConfig.get());

    @Test
    public void current() throws IOException, StatusApiException {
        CurrentStatus status = slack.status().current();
        assertThat(status, is(notNullValue()));
    }

    @Test
    public void history() throws IOException, StatusApiException {
        List<SlackIssue> events = slack.status().history();
        assertThat(events, is(notNullValue()));
    }

}
