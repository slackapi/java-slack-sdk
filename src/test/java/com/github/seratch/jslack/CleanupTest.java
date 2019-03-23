package com.github.seratch.jslack;

import com.github.seratch.jslack.api.methods.request.channels.ChannelsArchiveRequest;
import com.github.seratch.jslack.api.methods.request.channels.ChannelsListRequest;
import com.github.seratch.jslack.api.methods.request.conversations.ConversationsArchiveRequest;
import com.github.seratch.jslack.api.methods.request.conversations.ConversationsListRequest;
import com.github.seratch.jslack.api.methods.response.channels.ChannelsArchiveResponse;
import com.github.seratch.jslack.api.methods.response.conversations.ConversationsArchiveResponse;
import com.github.seratch.jslack.api.model.Channel;
import com.github.seratch.jslack.api.model.Conversation;
import com.github.seratch.jslack.api.model.ConversationType;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

@Slf4j
public class CleanupTest {

    Slack slack = Slack.getInstance();

    @Ignore
    @Test
    public void deleteUnnecessaryPublicChannels() throws Exception {
        String token = System.getenv(Constants.SLACK_TEST_OAUTH_ACCESS_TOKEN);
        for (Channel channel : slack.methods().channelsList(ChannelsListRequest.builder()
                .token(token)
                .excludeArchived(true)
                .limit(1000)
                .build()).getChannels()) {

            log.info(channel.toString());

            if (channel.getName().startsWith("test") && !channel.isGeneral()) {
                ChannelsArchiveResponse resp = slack.methods().channelsArchive(ChannelsArchiveRequest.builder()
                        .token(token).channel(channel.getId()).build());
                assertThat(resp.getError(), is(nullValue()));
            }
        }
    }

    @Ignore
    @Test
    public void deleteUnnecessaryPrivateChannels() throws Exception {
        String token = System.getenv(Constants.SLACK_TEST_OAUTH_ACCESS_TOKEN);
        for (Conversation channel : slack.methods().conversationsList(ConversationsListRequest.builder()
                .token(token)
                .excludeArchived(true)
                .limit(1000)
                .types(Arrays.asList(ConversationType.PRIVATE_CHANNEL))
                .build()).getChannels()) {

            log.info(channel.toString());

            if ((channel.getName().startsWith("test") || channel.getName().startsWith("secret-"))
                    && !channel.isGeneral()) {
                ConversationsArchiveResponse resp = slack.methods().conversationsArchive(ConversationsArchiveRequest.builder()
                        .token(token)
                        .channel(channel.getId())
                        .build());
                assertThat(resp.getError(), is(nullValue()));
            }
        }
    }

}