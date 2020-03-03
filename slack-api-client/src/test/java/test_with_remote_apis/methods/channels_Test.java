package test_with_remote_apis.methods;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.channels.*;
import com.slack.api.methods.request.chat.*;
import com.slack.api.methods.request.chat.scheduled_messages.ChatScheduledMessagesListRequest;
import com.slack.api.methods.request.conversations.ConversationsHistoryRequest;
import com.slack.api.methods.response.channels.*;
import com.slack.api.methods.response.chat.*;
import com.slack.api.methods.response.chat.scheduled_messages.ChatScheduledMessagesListResponse;
import com.slack.api.methods.response.conversations.ConversationsHistoryResponse;
import com.slack.api.model.Attachment;
import com.slack.api.model.Channel;
import com.slack.api.model.Message;
import com.slack.api.model.block.ImageBlock;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.composition.PlainTextObject;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

@Slf4j
public class channels_Test {

    String botToken = System.getenv(Constants.SLACK_SDK_TEST_BOT_TOKEN);
    String userToken = System.getenv(Constants.SLACK_SDK_TEST_USER_TOKEN);

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

    @AfterClass
    public static void tearDown() throws InterruptedException {
        SlackTestConfig.awaitCompletion(testConfig);
    }

    private String randomChannelId = null;

    void loadRandomChannelId() throws IOException, SlackApiException {
        if (randomChannelId == null) {
            ChannelsListResponse channelsListResponse = slack.methods().channelsList(r ->
                    r.token(botToken).excludeArchived(true).limit(100));
            assertThat(channelsListResponse.getError(), is(nullValue()));
            for (Channel channel : channelsListResponse.getChannels()) {
                if (channel.getName().equals("random")) {
                    randomChannelId = channel.getId();
                    break;
                }
            }
        }
    }

    @Test
    public void channels_latest() throws Exception {
        loadRandomChannelId();

        ChannelsHistoryResponse history = slack.methods().channelsHistory(req -> req
                .token(botToken)
                .channel(randomChannelId)
                .count(20));
        assertThat(history.isOk(), is(true));
        assertThat(history.getLatest(), is(nullValue()));

        ChannelsHistoryResponse latestHistory = slack.methods().channelsHistory(req -> req
                .token(botToken)
                .channel(randomChannelId)
                .latest(history.getMessages().get(0).getTs())
                .inclusive(true)
                .count(1));
        assertThat(latestHistory.isOk(), is(true));
        assertThat(latestHistory.getLatest(), is(notNullValue()));
    }

    @Test
    public void channels_threading() throws IOException, SlackApiException {
        loadRandomChannelId();

        ChatPostMessageResponse firstMessageCreation = slack.methods().chatPostMessage(req -> req
                .channel(randomChannelId)
                .token(botToken)
                .text("[thread] This is a test message posted by unit tests for Java Slack SDK library")
                .replyBroadcast(false));
        assertThat(firstMessageCreation.getError(), is(nullValue()));
        assertThat(firstMessageCreation.isOk(), is(true));

        ChatPostMessageResponse reply1 = slack.methods().chatPostMessage(req -> req
                        .channel(randomChannelId)
                        .token(userToken)
                        .asUser(false)
                        .text("replied")
                        .iconEmoji(":smile:")
                        .threadTs(firstMessageCreation.getTs())
                //.replyBroadcast(true)
        );
        assertThat(reply1.getError(), is(nullValue()));
        assertThat(reply1.isOk(), is(true));

        ChatGetPermalinkResponse permalink = slack.methods().chatGetPermalink(req -> req
                .token(botToken)
                .channel(randomChannelId)
                .messageTs(reply1.getTs()));
        assertThat(permalink.getError(), is(nullValue()));
        assertThat(permalink.isOk(), is(true));
        assertThat(permalink.getPermalink(), is(notNullValue()));

        ChatPostMessageResponse reply2 = slack.methods().chatPostMessage(req -> req
                .channel(randomChannelId)
                .token(botToken)
                .asUser(true)
                .text("replied to " + permalink.getPermalink())
                .threadTs(reply1.getTs())
                .unfurlLinks(true)
                .replyBroadcast(true));
        assertThat(reply2.isOk(), is(true));

        // Ideally, this message must contain an attachment which shows the preview for reply1
        // however, in this timing, Slack API doesn't return any attachments.
        assertThat(reply2.getMessage().getAttachments(), is(nullValue()));

        // via channels.history
        {
            ChannelsHistoryResponse history = slack.methods().channelsHistory(req -> req
                    .token(botToken)
                    .channel(randomChannelId)
                    .count(20));
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
            ConversationsHistoryResponse history = slack.methods().conversationsHistory(req -> req
                    .token(botToken)
                    .channel(randomChannelId)
                    .limit(20));
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

    @Ignore
    @Test
    public void channels_chat() throws IOException, SlackApiException {

        {
            ChannelsListResponse response = slack.methods().channelsList(r -> r.token(botToken));
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.getChannels(), is(notNullValue()));
        }

        ChannelsCreateResponse creationResponse = slack.methods().channelsCreate(
                ChannelsCreateRequest.builder().token(botToken).name("test" + System.currentTimeMillis()).build());
        assertThat(creationResponse.getError(), is(nullValue()));
        assertThat(creationResponse.isOk(), is(true));
        assertThat(creationResponse.getChannel(), is(notNullValue()));

        Channel channel = creationResponse.getChannel();

        try {
            {
                ChannelsInfoResponse response = slack.methods().channelsInfo(
                        ChannelsInfoRequest.builder().token(botToken).channel(channel.getId()).build());
                assertThat(response.isOk(), is(true));
                Channel fetchedChannel = response.getChannel();
                assertThat(fetchedChannel.isMember(), is(true));
                assertThat(fetchedChannel.isGeneral(), is(false));
                assertThat(fetchedChannel.isArchived(), is(false));
            }

            {
                ChannelsSetPurposeResponse response = slack.methods().channelsSetPurpose(
                        ChannelsSetPurposeRequest.builder().token(botToken).channel(channel.getId()).purpose("purpose").build());
                assertThat(response.getError(), is(nullValue()));
                assertThat(response.isOk(), is(true));
            }

            {
                ChannelsSetTopicResponse response = slack.methods().channelsSetTopic(
                        ChannelsSetTopicRequest.builder().token(botToken).channel(channel.getId()).topic("topic").build());
                assertThat(response.getError(), is(nullValue()));
                assertThat(response.isOk(), is(true));
            }

            {
                ChannelsHistoryResponse response = slack.methods().channelsHistory(
                        ChannelsHistoryRequest.builder().token(botToken).channel(channel.getId()).count(10).build());
                assertThat(response.getError(), is(nullValue()));
                assertThat(response.isOk(), is(true));
            }

            {
                ChannelsHistoryResponse history = slack.methods().channelsHistory(
                        ChannelsHistoryRequest.builder().token(botToken).channel(channel.getId()).count(1).build());
                String threadTs = history.getMessages().get(0).getTs();
                ChannelsRepliesResponse response = slack.methods().channelsReplies(
                        ChannelsRepliesRequest.builder().token(botToken).channel(channel.getId()).threadTs(threadTs).build());
                assertThat(response.getError(), is(nullValue()));
                assertThat(response.isOk(), is(true));
            }

            {
                ChannelsKickResponse response = slack.methods().channelsKick(ChannelsKickRequest.builder()
                        .token(userToken)
                        .channel(channel.getId())
                        .user(channel.getMembers().get(0))
                        .build());
                assertThat(response.getError(), is(nullValue()));
            }

            {
                ChannelsInviteResponse response = slack.methods().channelsInvite(ChannelsInviteRequest.builder()
                        .token(userToken)
                        .channel(channel.getId())
                        .user(channel.getMembers().get(0))
                        .build());
                assertThat(response.getError(), is(notNullValue()));
            }

            {
                ChatMeMessageResponse response = slack.methods().chatMeMessage(ChatMeMessageRequest.builder()
                        .channel(channel.getId())
                        .token(botToken)
                        .text("Hello World! via chat.meMessage API")
                        .build());
                assertThat(response.getError(), is(nullValue()));
                assertThat(response.isOk(), is(true));
            }

            //--------------Edited test---------
            Message lastMessage = slack.methods().conversationsHistory(
                    ConversationsHistoryRequest.builder()
                            .token(botToken)
                            .channel(channel.getId())
                            .limit(1)
                            .build()
            ).getMessages().get(0);

            ChatUpdateResponse updateMessage = slack.methods().chatUpdate(ChatUpdateRequest.builder()
                    .channel(channel.getId())
                    .token(botToken)
                    .ts(lastMessage.getTs())
                    .text("Updated text" + lastMessage.getText())
                    .build());
            assertThat(updateMessage.getError(), is(nullValue()));
            assertThat(updateMessage.isOk(), is(true));

            ConversationsHistoryResponse conversationsHistoryResponse = slack.methods().conversationsHistory(
                    ConversationsHistoryRequest.builder()
                            .token(botToken)
                            .channel(channel.getId())
                            .limit(1)
                            .build()
            );
            assertFalse(conversationsHistoryResponse.getMessages().isEmpty());
            assertNotNull(conversationsHistoryResponse.getMessages().get(0).getEdited());
            assertNotEquals(conversationsHistoryResponse.getMessages().get(0).getEdited().getTs(), lastMessage.getTs());
            //--------------------

            {
                ChatPostMessageResponse postResponse = slack.methods().chatPostMessage(ChatPostMessageRequest.builder()
                        .channel(channel.getId())
                        .token(botToken)
                        .text("@seratch Hello World! via chat.postMessage API")
                        .linkNames(true)
                        .build());
                assertThat(postResponse.getError(), is(nullValue()));
                assertThat(postResponse.isOk(), is(true));

                ChatPostMessageResponse replyResponse1 = slack.methods().chatPostMessage(ChatPostMessageRequest.builder()
                        .channel(channel.getId())
                        .token(botToken)
                        .text("@seratch Replied via chat.postMessage API")
                        .linkNames(true)
                        .threadTs(postResponse.getTs())
                        //.replyBroadcast(false)
                        .build());
                assertThat(replyResponse1.getError(), is(nullValue()));
                assertThat(replyResponse1.isOk(), is(true));

                ChatPostMessageResponse replyResponse2 = slack.methods().chatPostMessage(ChatPostMessageRequest.builder()
                        .channel(channel.getId())
                        .token(botToken)
                        .text("@seratch Replied via chat.postMessage API")
                        .linkNames(true)
                        .threadTs(postResponse.getTs())
                        .replyBroadcast(true)
                        .build());
                assertThat(replyResponse2.getError(), is(nullValue()));
                assertThat(replyResponse2.isOk(), is(true));

                ChatUpdateResponse updateResponse = slack.methods().chatUpdate(ChatUpdateRequest.builder()
                        .channel(channel.getId())
                        .token(botToken)
                        .ts(postResponse.getTs())
                        .text("Updated text")
                        .linkNames(false)
                        .build());
                assertThat(updateResponse.getError(), is(nullValue()));
                assertThat(updateResponse.isOk(), is(true));

                ChannelsMarkResponse markResponse = slack.methods().channelsMark(ChannelsMarkRequest.builder()
                        //.token(token)
                        .channel(channel.getId())
                        .ts(updateMessage.getTs())
                        .build());
                assertThat(markResponse.getError(), is("invalid_auth"));
                assertThat(markResponse.isOk(), is(false));

                markResponse = slack.methods().channelsMark(ChannelsMarkRequest.builder()
                        .token(botToken)
                        .channel(channel.getId())
                        .ts(updateMessage.getTs())
                        .build());
                assertThat(markResponse.getError(), is(nullValue()));
                assertThat(markResponse.isOk(), is(true));

                ChatDeleteResponse deleteResponse = slack.methods().chatDelete(ChatDeleteRequest.builder()
                        .token(botToken)
                        .channel(channel.getId())
                        .ts(postResponse.getMessage().getTs())
                        .build());
                assertThat(deleteResponse.getError(), is(nullValue()));
                assertThat(deleteResponse.isOk(), is(true));
            }

            // scheduled messages
            {
                ChatScheduledMessagesListResponse listResponse = slack.methods().chatScheduledMessagesList(
                        ChatScheduledMessagesListRequest.builder()
                                .token(botToken)
                                .limit(10)
                                .channel(channel.getId())
                                .build());
                assertThat(listResponse.getError(), is(nullValue()));
                int initialScheduledMessageCount = listResponse.getScheduledMessages().size();

                int postAt = (int) ((new Date().getTime() / 1000) + 180);

                ChatScheduleMessageResponse postResponse = slack.methods().chatScheduleMessage(
                        ChatScheduleMessageRequest.builder()
                                .token(botToken)
                                .channel(channel.getId())
                                .text("Something is happening!")
                                .postAt(postAt) // will be posted in 3 minutes
                                .build());
                assertThat(postResponse.getError(), is(nullValue()));

                assertNumOfScheduledMessages(botToken, channel, initialScheduledMessageCount + 1);
                deleteScheduledMessage(botToken, channel, postResponse);

                postResponse = slack.methods().chatScheduleMessage(
                        ChatScheduleMessageRequest.builder()
                                .token(botToken)
                                .channel(channel.getId())
                                .attachments(Arrays.asList(Attachment.builder().text("something is happening!").build()))
                                .postAt(postAt) // will be posted in 3 minutes
                                .build());
                assertThat(postResponse.getError(), is(nullValue()));

                assertNumOfScheduledMessages(botToken, channel, initialScheduledMessageCount + 1);
                deleteScheduledMessage(botToken, channel, postResponse);

                postResponse = slack.methods().chatScheduleMessage(
                        ChatScheduleMessageRequest.builder()
                                .token(botToken)
                                .channel(channel.getId())
                                .blocks(Arrays.asList((LayoutBlock) ImageBlock.builder()
                                        .blockId("123")
                                        .altText("alt")
                                        .title(PlainTextObject.builder().text("title").build())
                                        .imageUrl("https://a.slack-edge.com/4a5c4/marketing/img/meta/slack_hash_256.png")
                                        .build()))
                                .postAt(postAt) // will be posted in 3 minutes
                                .build());
                assertThat(postResponse.getError(), is(nullValue()));

                deleteScheduledMessage(botToken, channel, postResponse);
                assertNumOfScheduledMessages(botToken, channel, initialScheduledMessageCount);
            }

            {
                ChannelsLeaveResponse response = slack.methods().channelsLeave(ChannelsLeaveRequest.builder()
                        .token(botToken)
                        .channel(channel.getId())
                        .build());
                assertThat(response.getError(), is(nullValue()));
                assertThat(response.isOk(), is(true));
            }
            {
                ChannelsJoinResponse response = slack.methods().channelsJoin(ChannelsJoinRequest.builder()
                        .token(botToken)
                        .name(channel.getName())
                        .build());
                assertThat(response.getError(), is(nullValue()));
                assertThat(response.isOk(), is(true));
            }

            {
                ChannelsRenameResponse response = slack.methods().channelsRename(ChannelsRenameRequest.builder()
                        .token(botToken)
                        .channel(channel.getId())
                        .name(channel.getName() + "-1")
                        .build());
                assertThat(response.getError(), is(nullValue()));
                assertThat(response.isOk(), is(true));
            }

        } finally {
            {
                ChannelsArchiveResponse response = slack.methods().channelsArchive(ChannelsArchiveRequest.builder()
                        .token(userToken)
                        .channel(channel.getId())
                        .build());
                assertThat(response.getError(), is(nullValue()));
                assertThat(response.isOk(), is(true));
            }

            {
                ChannelsUnarchiveResponse response = slack.methods().channelsUnarchive(ChannelsUnarchiveRequest.builder()
                        .token(userToken)
                        //.channel(channel.getId())
                        .build());
                assertThat(response.getError(), is(notNullValue()));

                response = slack.methods().channelsUnarchive(ChannelsUnarchiveRequest.builder()
                        .token(userToken) // intentional
                        .channel(channel.getId())
                        .build());
                assertThat(response.getError(), is(nullValue()));
                assertThat(response.isOk(), is(true));
            }

            {
                ChannelsArchiveResponse response = slack.methods().channelsArchive(ChannelsArchiveRequest.builder()
                        .token(userToken)
                        .channel(channel.getId())
                        .build());
                assertThat(response.getError(), is(nullValue()));
                assertThat(response.isOk(), is(true));
            }

            {
                ChannelsInfoResponse response = slack.methods().channelsInfo(
                        ChannelsInfoRequest.builder().token(botToken).channel(channel.getId()).build());
                assertThat(response.isOk(), is(true));
                Channel fetchedChannel = response.getChannel();
                assertThat(fetchedChannel.isMember(), is(false));
                assertThat(fetchedChannel.isGeneral(), is(false));
                assertThat(fetchedChannel.isArchived(), is(true));
            }
        }
    }

    private void assertNumOfScheduledMessages(String token, Channel channel, int i) throws IOException, SlackApiException {
        ChatScheduledMessagesListResponse listResponse;
        listResponse = slack.methods().chatScheduledMessagesList(
                ChatScheduledMessagesListRequest.builder()
                        .token(token)
                        .limit(10)
                        .channel(channel.getId())
                        .build());
        assertThat(listResponse.getError(), is(nullValue()));
        assertThat(listResponse.getScheduledMessages().size(), is(i));
    }

    private ChatDeleteScheduledMessageResponse deleteScheduledMessage(String token, Channel channel, ChatScheduleMessageResponse postResponse) throws IOException, SlackApiException {
        ChatDeleteScheduledMessageResponse deleteResponse = slack.methods().chatDeleteScheduledMessage(
                ChatDeleteScheduledMessageRequest.builder()
                        .token(token)
                        .channel(channel.getId())
                        .scheduledMessageId(postResponse.getScheduledMessageId())
                        .build());
        assertThat(deleteResponse.getError(), is(nullValue()));
        return deleteResponse;
    }

}
