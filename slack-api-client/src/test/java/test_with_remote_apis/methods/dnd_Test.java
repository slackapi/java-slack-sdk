package test_with_remote_apis.methods;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.dnd.*;
import com.slack.api.model.User;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class dnd_Test {

    String token = System.getenv(Constants.SLACK_SDK_TEST_USER_TOKEN);
    Slack slack = Slack.getInstance(SlackTestConfig.get());

    @Test
    public void dnd() throws IOException, SlackApiException {
        List<User> members = slack.methods().usersList(r -> r.token(token).presence(true)).getMembers();
        {
            String user = members.get(0).getId();
            DndInfoResponse response = slack.methods().dndInfo(r -> r.token(token).user(user));
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
            assertThat(response.getNextDndStartTs(), is(notNullValue()));
        }

        {
            List<String> users = new ArrayList<>();
            for (User member : members) {
                users.add(member.getId());
            }
            DndTeamInfoResponse response = slack.methods().dndTeamInfo(r -> r.token(token).users(users));
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
            assertThat(response.getUsers(), is(notNullValue()));
        }
    }

    @Test
    public void dndEndDnd() throws Exception {
        {
            DndEndDndResponse response = slack.methods().dndEndDnd(r -> r
                    //.token(token)
            );
            assertThat(response.getError(), is(notNullValue()));
            assertThat(response.isOk(), is(false));
        }
        {
            DndEndDndResponse response = slack.methods().dndEndDnd(r -> r
                    .token(token));
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
        }
    }

    @Test
    public void dndEndSnooze() throws Exception {
        {
            DndEndSnoozeResponse response = slack.methods().dndEndSnooze(r -> r
                    //.token(token)
            );
            assertThat(response.getError(), is(notNullValue()));
            assertThat(response.isOk(), is(false));
        }
        {
            DndEndSnoozeResponse response = slack.methods().dndEndSnooze(r -> r
                    .token(token));
            assertThat(response.getError(), is("snooze_not_active"));
            assertThat(response.isOk(), is(false));
        }

        {
            DndSetSnoozeResponse response = slack.methods().dndSetSnooze(r -> r
                    //.token(token)
                    .numMinutes(10));
            assertThat(response.getError(), is(notNullValue()));
            assertThat(response.isOk(), is(false));
        }
        {
            DndSetSnoozeResponse response = slack.methods().dndSetSnooze(r -> r
                    .token(token)
                    .numMinutes(10));
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
        }
        {
            DndEndSnoozeResponse response = slack.methods().dndEndSnooze(r -> r
                    .token(token));
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
        }
    }

}
