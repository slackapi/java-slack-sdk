package com.github.seratch.jslack;

import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.request.channels.*;
import com.github.seratch.jslack.api.methods.request.chat.*;
import com.github.seratch.jslack.api.methods.request.conversations.ConversationsHistoryRequest;
import com.github.seratch.jslack.api.methods.response.channels.*;
import com.github.seratch.jslack.api.methods.response.chat.*;
import com.github.seratch.jslack.api.methods.response.conversations.ConversationsHistoryResponse;
import com.github.seratch.jslack.api.model.Channel;
import com.github.seratch.jslack.api.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

@Slf4j
public class Slack_channels_chat_Test {

    Slack slack = Slack.getInstance();

    @Test
    public void channels_threading() throws IOException, SlackApiException {
        String token = System.getenv(Constants.SLACK_TEST_OAUTH_ACCESS_TOKEN);
        ChannelsListResponse channels = slack.methods().channelsList(ChannelsListRequest.builder()
                .token(token)
                .limit(2)
                .excludeArchived(true)
                .build());
        assertThat(channels.isOk(), is(true));
        assertThat(channels.getResponseMetadata(), is(notNullValue()));

        String channelId = channels.getChannels().get(0).getId();

        ChatPostMessageResponse firstMessageCreation = slack.methods().chatPostMessage(ChatPostMessageRequest.builder()
                .channel(channelId)
                .token(token)
                .text("[thread] This is a test message posted by unit tests for jslack library")
                .replyBroadcast(false)
                .build());
        assertThat(firstMessageCreation.isOk(), is(true));

        ChatPostMessageResponse reply1 = slack.methods().chatPostMessage(ChatPostMessageRequest.builder()
                .channel(channelId)
                .token(token)
                .asUser(false)
                .text("replied")
                .threadTs(firstMessageCreation.getTs())
                //.replyBroadcast(true)
                .build());
        assertThat(reply1.isOk(), is(true));

        ChatGetPermalinkResponse permalink = slack.methods().chatGetPermalink(ChatGetPermalinkRequest.builder()
                .token(token)
                .channel(channelId)
                .messageTs(reply1.getTs())
                .build());
        assertThat(permalink.isOk(), is(true));
        assertThat(permalink.getPermalink(), is(notNullValue()));

        ChatPostMessageResponse reply2 = slack.methods().chatPostMessage(ChatPostMessageRequest.builder()
                .channel(channelId)
                .token(token)
                .asUser(true)
                .text("replied to " + permalink.getPermalink())
                .threadTs(reply1.getTs())
                .unfurlLinks(true)
                .replyBroadcast(true)
                .build());
        assertThat(reply2.isOk(), is(true));

        // Ideally, this message must contain an attachment which shows the preview for reply1
        // however, in this timing, Slack API doesn't return any attachments.
        assertThat(reply2.getMessage().getAttachments(), is(nullValue()));

        // via channels.history
        {
            ChannelsHistoryResponse history = slack.methods().channelsHistory(ChannelsHistoryRequest.builder()
                    .token(token)
                    .channel(channelId)
                    .build());
            assertThat(history.isOk(), is(true));

            Message firstReply = history.getMessages().get(1);
            assertThat(firstReply.getType(), is("message"));
            assertThat(firstReply.getSubtype(), is("bot_message"));
            assertThat(firstReply.getAttachments(), is(nullValue()));
            assertThat(firstReply.getRoot(), is(nullValue()));

            Message latestMessage = history.getMessages().get(0);
            assertThat(latestMessage.getType(), is("message"));
            assertThat(latestMessage.getSubtype(), is("thread_broadcast"));

            // NOTE: the following assertions can fail due to Slack API's unstable response
            // this message must contain an attachment which shows the preview for reply1
            // TODO: as of 2018/05, these assertions fail.
//            assertThat(latestMessage.getAttachments(), is(notNullValue()));
//            assertThat(latestMessage.getAttachments().size(), is(1));
//            assertThat(latestMessage.getRoot(), is(notNullValue()));
//            assertThat(latestMessage.getRoot().getReplies().size(), is(2));
//            assertThat(latestMessage.getRoot().getReplyCount(), is(2));
        }

        // via conversations.history
        {
            ConversationsHistoryResponse history = slack.methods().conversationsHistory(ConversationsHistoryRequest.builder()
                    .token(token)
                    .channel(channelId)
                    .build());
            assertThat(history.isOk(), is(true));

            Message firstReply = history.getMessages().get(1);
            assertThat(firstReply.getType(), is("message"));
            assertThat(firstReply.getSubtype(), is("bot_message"));
            assertThat(firstReply.getAttachments(), is(nullValue()));
            assertThat(firstReply.getRoot(), is(nullValue()));

            Message latestMessage = history.getMessages().get(0);
            assertThat(latestMessage.getType(), is("message"));
            assertThat(latestMessage.getSubtype(), is("thread_broadcast"));

            // NOTE: the following assertions can fail due to Slack API's unstable response
            // this message must contain an attachment which shows the preview for reply1
            // TODO: as of August 2018, these assertions fail.
//            assertThat(latestMessage.getAttachments(), is(notNullValue()));
//            assertThat(latestMessage.getAttachments().size(), is(1));
//            assertThat(latestMessage.getRoot(), is(notNullValue()));
//            assertThat(latestMessage.getRoot().getReplies().size(), is(2));
//            assertThat(latestMessage.getRoot().getReplyCount(), is(2));
        }
    }

    @Test
    public void chat_getPermalink() throws IOException, SlackApiException {
        String token = System.getenv(Constants.SLACK_TEST_OAUTH_ACCESS_TOKEN);
        ChannelsListResponse channels = slack.methods().channelsList(ChannelsListRequest.builder()
                .token(token)
                .excludeArchived(true)
                .build());
        assertThat(channels.isOk(), is(true));

        String channelId = channels.getChannels().get(0).getId();

        ChatPostMessageResponse postResponse = slack.methods().chatPostMessage(ChatPostMessageRequest.builder()
                .channel(channelId)
                .token(token)
                .text("Hi, this is a test message from jSlack library's unit tests")
                .linkNames(true)
                .build());
        assertThat(postResponse.isOk(), is(true));

        ChatGetPermalinkResponse permalink = slack.methods().chatGetPermalink(ChatGetPermalinkRequest.builder()
                .token(token)
                .channel(channelId)
                .messageTs(postResponse.getTs())
                .build());
        assertThat(permalink.isOk(), is(true));
        assertThat(permalink.getPermalink(), is(notNullValue()));
    }

    @Test
    public void channels_chat() throws IOException, SlackApiException {
        String token = System.getenv(Constants.SLACK_TEST_OAUTH_ACCESS_TOKEN);

        {
            ChannelsListResponse response = slack.methods().channelsList(
                    ChannelsListRequest.builder().token(token).build());
            assertThat(response.isOk(), is(true));
            assertThat(response.getChannels(), is(notNullValue()));
        }

        ChannelsCreateResponse creationResponse = slack.methods().channelsCreate(
                ChannelsCreateRequest.builder().token(token).name("test" + System.currentTimeMillis()).build());
        assertThat(creationResponse.isOk(), is(true));
        assertThat(creationResponse.getChannel(), is(notNullValue()));

        Channel channel = creationResponse.getChannel();

        {
            ChannelsInfoResponse response = slack.methods().channelsInfo(
                    ChannelsInfoRequest.builder().token(token).channel(channel.getId()).build());
            assertThat(response.isOk(), is(true));
            Channel fetchedChannel = response.getChannel();
            assertThat(fetchedChannel.isMember(), is(true));
            assertThat(fetchedChannel.isGeneral(), is(false));
            assertThat(fetchedChannel.isArchived(), is(false));
        }

        {
            ChannelsSetPurposeResponse response = slack.methods().channelsSetPurpose(
                    ChannelsSetPurposeRequest.builder().token(token).channel(channel.getId()).purpose("purpose").build());
            assertThat(response.isOk(), is(true));
        }

        {
            ChannelsSetTopicResponse response = slack.methods().channelsSetTopic(
                    ChannelsSetTopicRequest.builder().token(token).channel(channel.getId()).topic("topic").build());
            assertThat(response.isOk(), is(true));
        }

        {
            ChannelsHistoryResponse response = slack.methods().channelsHistory(
                    ChannelsHistoryRequest.builder().token(token).channel(channel.getId()).count(10).build());
            assertThat(response.isOk(), is(true));
        }

        {
            ChannelsHistoryResponse history = slack.methods().channelsHistory(
                    ChannelsHistoryRequest.builder().token(token).channel(channel.getId()).count(1).build());
            String threadTs = history.getMessages().get(0).getTs();
            ChannelsRepliesResponse response = slack.methods().channelsReplies(
                    ChannelsRepliesRequest.builder().token(token).channel(channel.getId()).threadTs(threadTs).build());
            assertThat(response.isOk(), is(true));
        }

        {
            ChatUnfurlResponse unfurlResponse = slack.methods().chatUnfurl(ChatUnfurlRequest.builder()
                    .token(token)
                    .channel(channel.getId())
                    .unfurls("http://www.example.com/")
                    .build());
            // TODO: valid test
            assertThat(unfurlResponse.isOk(), is(false));
            assertThat(unfurlResponse.getError(), is("missing_ts"));
        }

        {
            ChannelsKickResponse response = slack.methods().channelsKick(ChannelsKickRequest.builder()
                    .token(token)
                    .channel(channel.getId())
                    .user(channel.getMembers().get(0))
                    .build());
            // TODO: valid test
            assertThat(response.isOk(), is(false));
            assertThat(response.getError(), is("cant_kick_self"));
        }

        {
            ChannelsInviteResponse response = slack.methods().channelsInvite(ChannelsInviteRequest.builder()
                    .token(token)
                    .channel(channel.getId())
                    .user(channel.getMembers().get(0))
                    .build());
            // TODO: valid test
            assertThat(response.isOk(), is(false));
            assertThat(response.getError(), is("cant_invite_self"));
        }

        {
            ChatMeMessageResponse response = slack.methods().chatMeMessage(ChatMeMessageRequest.builder()
                    .channel(channel.getId())
                    .token(token)
                    .text("Hello World! via chat.meMessage API")
                    .build());
            assertThat(response.isOk(), is(true));
        }

        {
            ChatPostMessageResponse postResponse = slack.methods().chatPostMessage(ChatPostMessageRequest.builder()
                    .channel(channel.getId())
                    .token(token)
                    .text("@seratch Hello World! via chat.postMessage API")
                    .linkNames(true)
                    .build());
            assertThat(postResponse.isOk(), is(true));

            ChatPostMessageResponse replyResponse1 = slack.methods().chatPostMessage(ChatPostMessageRequest.builder()
                    .channel(channel.getId())
                    .token(token)
                    .text("@seratch Replied via chat.postMessage API")
                    .linkNames(true)
                    .threadTs(postResponse.getTs())
                    //.replyBroadcast(false)
                    .build());
            assertThat(replyResponse1.isOk(), is(true));

            ChatPostMessageResponse replyResponse2 = slack.methods().chatPostMessage(ChatPostMessageRequest.builder()
                    .channel(channel.getId())
                    .token(token)
                    .text("@seratch Replied via chat.postMessage API")
                    .linkNames(true)
                    .threadTs(postResponse.getTs())
                    .replyBroadcast(true)
                    .build());
            assertThat(replyResponse2.isOk(), is(true));

            ChatUpdateResponse updateResponse = slack.methods().chatUpdate(ChatUpdateRequest.builder()
                    .channel(channel.getId())
                    .token(token)
                    .ts(postResponse.getTs())
                    .text("Updated text")
                    .linkNames(false)
                    .build());
            assertThat(updateResponse.isOk(), is(true));

            ChatDeleteResponse deleteResponse = slack.methods().chatDelete(ChatDeleteRequest.builder()
                    .token(token)
                    .channel(channel.getId())
                    .ts(postResponse.getMessage().getTs())
                    .build());
            assertThat(deleteResponse.isOk(), is(true));
        }
        {
            ChannelsLeaveResponse response = slack.methods().channelsLeave(ChannelsLeaveRequest.builder()
                    .token(token)
                    .channel(channel.getId())
                    .build());
            assertThat(response.isOk(), is(true));
        }
        {
            ChannelsJoinResponse response = slack.methods().channelsJoin(ChannelsJoinRequest.builder()
                    .token(token)
                    .name(channel.getName())
                    .build());
            assertThat(response.isOk(), is(true));
        }

        {
            ChannelsRenameResponse response = slack.methods().channelsRename(ChannelsRenameRequest.builder()
                    .token(token)
                    .channel(channel.getId())
                    .name(channel.getName() + "-1")
                    .build());
            assertThat(response.isOk(), is(true));
        }

        {
            ChannelsArchiveResponse response = slack.methods().channelsArchive(
                    ChannelsArchiveRequest.builder().token(token).channel(channel.getId()).build());
            assertThat(response.isOk(), is(true));
        }

        {
            ChannelsInfoResponse response = slack.methods().channelsInfo(
                    ChannelsInfoRequest.builder().token(token).channel(channel.getId()).build());
            assertThat(response.isOk(), is(true));
            Channel fetchedChannel = response.getChannel();
            assertThat(fetchedChannel.isMember(), is(false));
            assertThat(fetchedChannel.isGeneral(), is(false));
            assertThat(fetchedChannel.isArchived(), is(true));
        }
    }
}