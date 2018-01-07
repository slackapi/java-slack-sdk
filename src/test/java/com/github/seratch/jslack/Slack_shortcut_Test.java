package com.github.seratch.jslack;

import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.response.chat.ChatPostMessageResponse;
import com.github.seratch.jslack.api.methods.response.reactions.ReactionsAddResponse;
import com.github.seratch.jslack.api.methods.response.search.SearchAllResponse;
import com.github.seratch.jslack.api.model.Attachment;
import com.github.seratch.jslack.api.model.Message;
import com.github.seratch.jslack.shortcut.Shortcut;
import com.github.seratch.jslack.shortcut.model.ApiToken;
import com.github.seratch.jslack.shortcut.model.ChannelId;
import com.github.seratch.jslack.shortcut.model.ChannelName;
import com.github.seratch.jslack.shortcut.model.ReactionName;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

@Slf4j
public class Slack_shortcut_Test {

    Slack slack = Slack.getInstance();
    ApiToken token = ApiToken.of(System.getenv(Constants.SLACK_TEST_OAUTH_ACCESS_TOKEN));

    @Test
    public void chatOps() throws IOException, SlackApiException {
        Shortcut shortcut = slack.shortcut(token);
        ChannelName channelName = ChannelName.of("general");

        Optional<ChannelId> channelId = shortcut.findChannelIdByName(channelName);
        assertThat(channelId.isPresent(), is(true));

        Optional<ChannelName> maybeChannelName = shortcut.findChannelNameById(channelId.get());
        assertThat(maybeChannelName.isPresent(), is(true));
        assertThat(maybeChannelName.get(), is(channelName));

        List<Message> messages = shortcut.findRecentMessagesByName(channelName);
        assertThat(messages, is(notNullValue()));

        SearchAllResponse searchResult = shortcut.search("hello");
        assertThat(searchResult, is(notNullValue()));

        ReactionName reactionName = ReactionName.of("smile");
        ReactionsAddResponse addReaction = shortcut.addReaction(messages.get(0), reactionName);
        assertThat(addReaction.isOk(), is(true));
    }

    @Test
    public void postMessage() throws IOException, SlackApiException {
        Shortcut shortcut = slack.shortcut(token);
        Attachment attachment = Attachment.builder().text("text").footer("footer").build();
        List<Attachment> attachments = Arrays.asList(attachment);

        {
            ChatPostMessageResponse response = shortcut.post(ChannelName.of("general"), "hello, hello!");
            assertThat(response.isOk(), is(true));
        }

        {
            ChatPostMessageResponse response = shortcut.postAsBot(ChannelName.of("general"), "hello, hello!");
            assertThat(response.isOk(), is(true));
        }

        {
            ChatPostMessageResponse response = shortcut.post(ChannelName.of("general"), "Hi, these are my attachments!", attachments);
            assertThat(response.isOk(), is(true));
        }

        {
            ChatPostMessageResponse response = shortcut.postAsBot(ChannelName.of("general"), "Hi, these are my attachments!", attachments);
            assertThat(response.isOk(), is(true));
        }
    }

}