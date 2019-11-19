package test_with_remote_apis.web_api;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.methods.response.team.TeamAccessLogsResponse;
import com.github.seratch.jslack.api.methods.response.team.TeamBillableInfoResponse;
import com.github.seratch.jslack.api.methods.response.team.TeamInfoResponse;
import com.github.seratch.jslack.api.methods.response.team.TeamIntegrationLogsResponse;
import com.github.seratch.jslack.api.methods.response.team.profile.TeamProfileGetResponse;
import com.github.seratch.jslack.api.model.User;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class team_Test {

    Slack slack = Slack.getInstance(SlackTestConfig.get());
    String token = System.getenv(Constants.SLACK_TEST_OAUTH_ACCESS_TOKEN);

    @Test
    public void teamAccessLogs() throws Exception {
        TeamAccessLogsResponse response = slack.methods().teamAccessLogs(r -> r.token(token));
        if (response.isOk()) {
            // when you pay for this team
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
        } else {
            // when you don't pay for this team
            assertThat(response.isOk(), is(false));
            assertThat(response.getError(), is("paid_only"));
        }
    }

    @Test
    public void teamBillableInfo() throws Exception {
        List<User> users = slack.methods().usersList(r -> r.token(token)).getMembers();
        User user = null;
        for (User u : users) {
            if (!u.isBot() && !"USLACKBOT".equals(u.getId())) {
                user = u;
                break;
            }
        }
        String userId = user.getId();
        TeamBillableInfoResponse response = slack.methods().teamBillableInfo(r -> r
                .token(token)
                .user(userId));
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
    }

    @Test
    public void teamInfo() throws Exception {
        TeamInfoResponse response = slack.methods().teamInfo(r -> r.token(token));
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
    }

    @Test
    public void teamIntegrationLogs() throws Exception {
        String user = slack.methods().usersList(r -> r.token(token)).getMembers().get(0).getId();
        TeamIntegrationLogsResponse response = slack.methods().teamIntegrationLogs(r -> r
                .token(token)
                .user(user));
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
    }

    @Test
    public void teamProfileGet() throws Exception {
        TeamProfileGetResponse response = slack.methods().teamProfileGet(r -> r.token(token));
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
    }

}
