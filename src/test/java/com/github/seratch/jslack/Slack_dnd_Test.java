package com.github.seratch.jslack;

import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.request.dnd.*;
import com.github.seratch.jslack.api.methods.request.users.UsersListRequest;
import com.github.seratch.jslack.api.methods.response.dnd.*;
import com.github.seratch.jslack.api.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import testing.Constants;
import testing.SlackTestConfig;

import java.io.IOException;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

@Slf4j
public class Slack_dnd_Test {

    String token = System.getenv(Constants.SLACK_TEST_OAUTH_ACCESS_TOKEN);
    Slack slack = Slack.getInstance(SlackTestConfig.get());

    @Test
    public void dnd() throws IOException, SlackApiException {
        List<User> members = slack.methods().usersList(UsersListRequest.builder().token(token).presence(true).build()).getMembers();
        {
            String user = members.get(0).getId();
            DndInfoResponse response = slack.methods().dndInfo(DndInfoRequest.builder().token(token).user(user).build());
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
            assertThat(response.getNextDndStartTs(), is(notNullValue()));
        }

        {
            List<String> users = members.stream().map(m -> m.getId()).collect(toList());
            DndTeamInfoResponse response = slack.methods().dndTeamInfo(DndTeamInfoRequest.builder().token(token).users(users).build());
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
            assertThat(response.getUsers(), is(notNullValue()));
        }
    }

    @Test
    public void dndEndDnd() throws Exception {
        {
            DndEndDndResponse response = slack.methods().dndEndDnd(DndEndDndRequest.builder()
                    //.token(token)
                    .build());
            assertThat(response.getError(), is(notNullValue()));
            assertThat(response.isOk(), is(false));
        }
        {
            DndEndDndResponse response = slack.methods().dndEndDnd(DndEndDndRequest.builder()
                    .token(token)
                    .build());
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
        }
    }

    @Test
    public void dndEndSnooze() throws Exception {
        {
            DndEndSnoozeResponse response = slack.methods().dndEndSnooze(DndEndSnoozeRequest.builder()
                    //.token(token)
                    .build());
            assertThat(response.getError(), is(notNullValue()));
            assertThat(response.isOk(), is(false));
        }
        {
            DndEndSnoozeResponse response = slack.methods().dndEndSnooze(DndEndSnoozeRequest.builder()
                    .token(token)
                    .build());
            assertThat(response.getError(), is("snooze_not_active"));
            assertThat(response.isOk(), is(false));
        }

        {
            DndSetSnoozeResponse response = slack.methods().dndSetSnooze(DndSetSnoozeRequest.builder()
                    //.token(token)
                    .numMinutes(10)
                    .build());
            assertThat(response.getError(), is(notNullValue()));
            assertThat(response.isOk(), is(false));
        }
        {
            DndSetSnoozeResponse response = slack.methods().dndSetSnooze(DndSetSnoozeRequest.builder()
                    .token(token)
                    .numMinutes(10)
                    .build());
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
        }
        {
            DndEndSnoozeResponse response = slack.methods().dndEndSnooze(DndEndSnoozeRequest.builder()
                    .token(token)
                    .build());
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
        }
    }

}