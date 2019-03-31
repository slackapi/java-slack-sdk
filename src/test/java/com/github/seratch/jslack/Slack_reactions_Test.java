package com.github.seratch.jslack;

import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.request.channels.ChannelsListRequest;
import com.github.seratch.jslack.api.methods.request.chat.ChatPostMessageRequest;
import com.github.seratch.jslack.api.methods.request.reactions.ReactionsAddRequest;
import com.github.seratch.jslack.api.methods.request.reactions.ReactionsGetRequest;
import com.github.seratch.jslack.api.methods.request.reactions.ReactionsListRequest;
import com.github.seratch.jslack.api.methods.request.reactions.ReactionsRemoveRequest;
import com.github.seratch.jslack.api.methods.request.users.UsersListRequest;
import com.github.seratch.jslack.api.methods.response.chat.ChatPostMessageResponse;
import com.github.seratch.jslack.api.methods.response.reactions.ReactionsAddResponse;
import com.github.seratch.jslack.api.methods.response.reactions.ReactionsGetResponse;
import com.github.seratch.jslack.api.methods.response.reactions.ReactionsListResponse;
import com.github.seratch.jslack.api.methods.response.reactions.ReactionsRemoveResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

@Slf4j
public class Slack_reactions_Test {

    Slack slack = new Slack();
    String token = System.getenv(Constants.SLACK_TEST_OAUTH_ACCESS_TOKEN);

    @Test
    public void test() throws IOException, SlackApiException {
        String channel = slack.methods().channelsList(ChannelsListRequest.builder().token(token).excludeArchived(true).build())
                .getChannels().get(0).getId();

        ChatPostMessageResponse postMessage = slack.methods().chatPostMessage(ChatPostMessageRequest.builder()
                .token(token)
                .channel(channel)
                .text("hello")
                .build());
        assertThat(postMessage.getError(), is(nullValue()));
        assertThat(postMessage.isOk(), is(true));

        String timestamp = postMessage.getTs();
        ReactionsAddResponse addResponse = slack.methods().reactionsAdd(ReactionsAddRequest.builder()
                .token(token)
                .name("smile")
                .channel(channel)
                .timestamp(timestamp)
                .build());
        assertThat(addResponse.getError(), is(nullValue()));
        assertThat(addResponse.isOk(), is(true));

        ReactionsGetResponse getResponse = slack.methods().reactionsGet(ReactionsGetRequest.builder()
                .token(token)
                .channel(channel)
                .timestamp(timestamp)
                .build());
        assertThat(getResponse.getError(), is(nullValue()));
        assertThat(getResponse.isOk(), is(true));

        ReactionsRemoveResponse removeResponse = slack.methods().reactionsRemove(ReactionsRemoveRequest.builder()
                .token(token)
                .name("smile")
                .channel(channel)
                .timestamp(timestamp)
                .build());
        assertThat(removeResponse.getError(), is(nullValue()));
        assertThat(removeResponse.isOk(), is(true));

    }

    @Test
    public void list() throws IOException, SlackApiException {
        String user = slack.methods().usersList(UsersListRequest.builder().token(token).build())
                .getMembers().get(0).getId();

        ReactionsListResponse response = slack.methods().reactionsList(ReactionsListRequest.builder()
                .token(token)
                .user(user)
                .build());
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
        assertThat(response.getItems(), is(notNullValue()));
    }

}