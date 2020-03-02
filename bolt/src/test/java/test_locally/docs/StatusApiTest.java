package test_locally.docs;

import com.slack.api.Slack;
import com.slack.api.status.v2.StatusApiException;
import com.slack.api.status.v2.model.CurrentStatus;
import com.slack.api.status.v2.model.SlackIssue;

import java.io.IOException;
import java.util.List;

public class StatusApiTest {

    public void current() throws IOException, StatusApiException {
        Slack slack = Slack.getInstance();
        CurrentStatus status = slack.status().current();
    }

    public void history() throws IOException, StatusApiException {
        Slack slack = Slack.getInstance();
        List<SlackIssue> events = slack.status().history();
    }

}
