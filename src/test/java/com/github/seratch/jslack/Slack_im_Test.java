package com.github.seratch.jslack;

import com.github.seratch.jslack.api.methods.request.chat.ChatPostMessageRequest;
import com.github.seratch.jslack.api.methods.request.im.*;
import com.github.seratch.jslack.api.methods.request.users.UsersListRequest;
import com.github.seratch.jslack.api.methods.response.chat.ChatPostMessageResponse;
import com.github.seratch.jslack.api.methods.response.im.*;
import com.github.seratch.jslack.api.methods.response.users.UsersListResponse;
import com.github.seratch.jslack.api.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

@Slf4j
public class Slack_im_Test {

    Slack slack = Slack.getInstance();
    String token = System.getenv(Constants.SLACK_TEST_OAUTH_ACCESS_TOKEN);

    @Test
    public void operations() throws Exception {
        ImListResponse listResponse = slack.methods().imList(ImListRequest.builder()
                .token(token)
                .limit(2)
                .build());
        assertThat(listResponse.isOk(), is(true));
        assertThat(listResponse.getResponseMetadata(), is(notNullValue()));

        UsersListResponse usersListResponse = slack.methods().usersList(UsersListRequest.builder()
                .token(token)
                .presence(true)
                .build());
        List<User> users = usersListResponse.getMembers();
        String userId = users.get(0).getId();

        ImOpenResponse openResponse = slack.methods().imOpen(ImOpenRequest.builder()
                .token(token)
                .user(userId)
                .build());
        assertThat(openResponse.isOk(), is(true));

        String channelId = openResponse.getChannel().getId();

        ImMarkResponse markResponse = slack.methods().imMark(ImMarkRequest.builder()
                .token(token)
                .channel(channelId)
                .build());
        assertThat(markResponse.isOk(), is(true));

        ChatPostMessageResponse firstMessageResponse = slack.methods().chatPostMessage(ChatPostMessageRequest.builder()
                .token(token)
                .channel(channelId)
                .text("Hi!").build());
        assertThat(firstMessageResponse.isOk(), is(true));

        ChatPostMessageResponse threadReplyResponse = slack.methods().chatPostMessage(ChatPostMessageRequest.builder()
                .token(token)
                .channel(channelId)
                .threadTs(firstMessageResponse.getTs())
                .text("Hi!").build());
        assertThat(threadReplyResponse.isOk(), is(true));

        ImRepliesResponse repliesResponse = slack.methods().imReplies(ImRepliesRequest.builder()
                .token(token)
                .channel(channelId)
                .threadTs(threadReplyResponse.getMessage().getThreadTs())
                .build());
        assertThat(repliesResponse.isOk(), is(true));

        ImHistoryResponse historyResponse = slack.methods().imHistory(ImHistoryRequest.builder()
                .token(token)
                .channel(channelId)
                .build());
        assertThat(historyResponse.isOk(), is(true));

        ImCloseResponse closeResponse = slack.methods().imClose(ImCloseRequest.builder()
                .token(token)
                .channel(channelId)
                .build());
        assertThat(closeResponse.isOk(), is(true));
    }

}