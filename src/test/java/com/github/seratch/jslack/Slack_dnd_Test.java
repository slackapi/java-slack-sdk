package com.github.seratch.jslack;

import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.request.dnd.DndInfoRequest;
import com.github.seratch.jslack.api.methods.request.dnd.DndTeamInfoRequest;
import com.github.seratch.jslack.api.methods.request.users.UsersListRequest;
import com.github.seratch.jslack.api.methods.response.dnd.DndInfoResponse;
import com.github.seratch.jslack.api.methods.response.dnd.DndTeamInfoResponse;
import com.github.seratch.jslack.api.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static java.util.stream.Collectors.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

@Slf4j
public class Slack_dnd_Test {

    Slack slack = Slack.getInstance();

    @Test
    public void dnd() throws IOException, SlackApiException {
        String token = System.getenv(Constants.SLACK_TEST_OAUTH_ACCESS_TOKEN);

        List<User> members = slack.methods().usersList(UsersListRequest.builder().token(token).presence(true).build()).getMembers();
        {
            String user = members.get(0).getId();
            DndInfoResponse response = slack.methods().dndInfo(DndInfoRequest.builder().token(token).user(user).build());
            assertThat(response.isOk(), is(true));
            assertThat(response.getNextDndStartTs(), is(notNullValue()));
        }

        {
            List<String> users = members.stream().map(m -> m.getId()).collect(toList());
            DndTeamInfoResponse response = slack.methods().dndTeamInfo(DndTeamInfoRequest.builder().token(token).users(users).build());
            assertThat(response.isOk(), is(true));
            assertThat(response.getUsers(), is(notNullValue()));
        }
    }

}