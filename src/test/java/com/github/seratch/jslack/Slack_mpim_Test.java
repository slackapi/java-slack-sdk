package com.github.seratch.jslack;

import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.request.mpim.*;
import com.github.seratch.jslack.api.methods.request.users.UsersListRequest;
import com.github.seratch.jslack.api.methods.response.mpim.*;
import com.github.seratch.jslack.api.methods.response.users.UsersListResponse;
import com.github.seratch.jslack.api.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static java.util.stream.Collectors.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

@Slf4j
public class Slack_mpim_Test {

    Slack slack = Slack.getInstance();
    String token = System.getenv(Constants.SLACK_TEST_OAUTH_ACCESS_TOKEN);

    // TODO: fix this tests
    @Ignore
    @Test
    public void operations() throws IOException, SlackApiException {
        MpimListResponse listResponse = slack.methods().mpimList(MpimListRequest.builder().token(token).build());
        assertThat(listResponse.isOk(), is(true));

        UsersListResponse usersListResponse = slack.methods().usersList(UsersListRequest.builder().token(token).presence(true).build());
        List<User> users = usersListResponse.getMembers();
        List<String> userIds = users.stream()
                .filter(u -> u.isDeleted() == false && u.isRestricted() == false)
                .map(u -> u.getId()).collect(toList());

        MpimOpenResponse openResponse = slack.methods().mpimOpen(MpimOpenRequest.builder().token(token).users(userIds).build());
        assertThat(openResponse.isOk(), is(true));

        String channelId = openResponse.getGroup().getId();

        MpimMarkResponse markResponse = slack.methods().mpimMark(MpimMarkRequest.builder().token(token).channel(channelId).build());
        assertThat(markResponse.isOk(), is(true));

        MpimHistoryResponse historyResponse = slack.methods().mpimHistory(MpimHistoryRequest.builder().token(token).channel(channelId).build());
        assertThat(historyResponse.isOk(), is(true));

        MpimCloseResponse closeResponse = slack.methods().mpimClose(MpimCloseRequest.builder().token(token).channel(channelId).build());
        assertThat(closeResponse.isOk(), is(true));
    }

}