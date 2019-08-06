package test_with_remote_apis.web_api;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.request.conversations.ConversationsCreateRequest;
import com.github.seratch.jslack.api.methods.request.conversations.ConversationsListRequest;
import com.github.seratch.jslack.api.methods.response.channels.ChannelsRepliesResponse;
import com.github.seratch.jslack.api.methods.response.chat.ChatPostMessageResponse;
import com.github.seratch.jslack.api.methods.response.conversations.*;
import com.github.seratch.jslack.api.methods.response.users.UsersListResponse;
import com.github.seratch.jslack.api.model.*;
import com.github.seratch.jslack.api.model.block.ContextBlock;
import com.github.seratch.jslack.api.model.block.ContextBlockElement;
import com.github.seratch.jslack.api.model.block.composition.MarkdownTextObject;
import com.github.seratch.jslack.api.model.block.composition.PlainTextObject;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import util.TestChannelGenerator;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@Slf4j
public class conversations_Test {

    Slack slack = Slack.getInstance(SlackTestConfig.get());
    String token = System.getenv(Constants.SLACK_TEST_OAUTH_ACCESS_TOKEN);

    @Test
    public void channelConversation() throws IOException, SlackApiException {

        {
            ConversationsListResponse listResponse = slack.methods().conversationsList(
                    ConversationsListRequest.builder()
                            .token(token)
                            .excludeArchived(true)
                            .limit(10)
                            .types(Arrays.asList(ConversationType.PUBLIC_CHANNEL))
                            .build());
            assertThat(listResponse.getError(), is(nullValue()));
            assertThat(listResponse.isOk(), is(true));
            assertThat(listResponse.getChannels(), is(notNullValue()));
            assertThat(listResponse.getResponseMetadata(), is(notNullValue()));
        }

        ConversationsCreateResponse createPublicResponse = slack.methods().conversationsCreate(r -> r
                .token(token)
                .name("test" + System.currentTimeMillis())
                .isPrivate(false));
        assertThat(createPublicResponse.getError(), is(nullValue()));
        assertThat(createPublicResponse.isOk(), is(true));
        assertThat(createPublicResponse.getChannel(), is(notNullValue()));
        assertThat(createPublicResponse.getChannel().isPrivate(), is(false));

        Conversation channel = createPublicResponse.getChannel();

        ConversationsCreateResponse createPrivateResponse = slack.methods().conversationsCreate(r -> r
                .token(token)
                .name("test" + System.currentTimeMillis())
                .isPrivate(true));
        assertThat(createPrivateResponse.getError(), is(nullValue()));
        assertThat(createPrivateResponse.isOk(), is(true));
        assertThat(createPrivateResponse.getChannel(), is(notNullValue()));
        assertThat(createPrivateResponse.getChannel().isPrivate(), is(true));

        {
            ConversationsArchiveResponse resp = slack.methods().conversationsArchive(r -> r
                    .token(token)
                    .channel(createPrivateResponse.getChannel().getId()));
            assertThat(resp.getError(), is(nullValue()));
        }
        {
            ConversationsUnarchiveResponse resp = slack.methods().conversationsUnarchive(r -> r
                    //.token(token)
                    .channel(createPrivateResponse.getChannel().getId()));
            assertThat(resp.getError(), is(notNullValue()));

            resp = slack.methods().conversationsUnarchive(r -> r
                    .token(token)
                    .channel(createPrivateResponse.getChannel().getId()));
            assertThat(resp.getError(), is(nullValue()));
        }
        {
            ConversationsArchiveResponse resp = slack.methods().conversationsArchive(r -> r
                    .token(token)
                    .channel(createPrivateResponse.getChannel().getId()));
            assertThat(resp.getError(), is(nullValue()));
        }

        {
            ChatPostMessageResponse postMessageResponse = slack.methods().chatPostMessage(r -> r
                    .token(token)
                    .channel(createPublicResponse.getChannel().getId())
                    .text("This is a test message posted by unit tests for jslack library")
                    .replyBroadcast(false));
            assertThat(postMessageResponse.getError(), is(nullValue()));
            assertThat(postMessageResponse.isOk(), is(true));

            ChatPostMessageResponse postThread1Response = slack.methods().chatPostMessage(r -> r
                    .token(token)
                    .channel(createPublicResponse.getChannel().getId())
                    .threadTs(postMessageResponse.getTs())
                    .text("[thread 1] This is a test message posted by unit tests for jslack library")
                    .replyBroadcast(false));
            assertThat(postThread1Response.getError(), is(nullValue()));
            assertThat(postThread1Response.isOk(), is(true));

            ChatPostMessageResponse postThread2Response = slack.methods().chatPostMessage(r -> r
                    .token(token)
                    .channel(createPublicResponse.getChannel().getId())
                    .threadTs(postMessageResponse.getTs())
                    .text("[thread 2] This is a test message posted by unit tests for jslack library")
                    .replyBroadcast(false));
            assertThat(postThread2Response.getError(), is(nullValue()));
            assertThat(postThread2Response.isOk(), is(true));

            ConversationsRepliesResponse repliesResponse = slack.methods().conversationsReplies(r -> r
                    .token(token)
                    .channel(createPublicResponse.getChannel().getId())
                    .ts(postMessageResponse.getTs())
                    .limit(1));
            assertThat(repliesResponse.getError(), is(nullValue()));
            assertThat(repliesResponse.isOk(), is(true));
            assertThat(repliesResponse.getResponseMetadata(), is(notNullValue()));
        }

        {
            ConversationsInfoResponse infoResponse = slack.methods().conversationsInfo(r -> r
                    .token(token)
                    .channel(channel.getId())
                    .includeLocale(true));
            assertThat(infoResponse.isOk(), is(true));
            Conversation fetchedConversation = infoResponse.getChannel();
            assertThat(fetchedConversation.isMember(), is(true));
            assertThat(fetchedConversation.isGeneral(), is(false));
            assertThat(fetchedConversation.isArchived(), is(false));
            assertThat(fetchedConversation.isIm(), is(false));
            assertThat(fetchedConversation.isMpim(), is(false));
            assertThat(fetchedConversation.isGroup(), is(false));

        }

        {
            ConversationsSetPurposeResponse setPurposeResponse = slack.methods().conversationsSetPurpose(r -> r
                    .token(token)
                    .channel(channel.getId())
                    .purpose("purpose"));
            assertThat(setPurposeResponse.getError(), is(nullValue()));
            assertThat(setPurposeResponse.isOk(), is(true));
            assertThat(setPurposeResponse.getChannel().getPurpose().getValue(), is("purpose"));
        }

        {
            ConversationsSetTopicResponse setTopicResponse = slack.methods().conversationsSetTopic(r -> r
                    .token(token)
                    .channel(channel.getId())
                    .topic("topic"));
            assertThat(setTopicResponse.getError(), is(nullValue()));
            assertThat(setTopicResponse.isOk(), is(true));
            assertThat(setTopicResponse.getChannel().getTopic().getValue(), is("topic"));
        }

        {
            ConversationsHistoryResponse historyResponse = slack.methods().conversationsHistory(r -> r
                    .token(token)
                    .channel(channel.getId())
                    .limit(2));
            assertThat(historyResponse.getError(), is(nullValue()));
            assertThat(historyResponse.isOk(), is(true));
            // The outcome depends on data
            // assertThat(historyResponse.isHasMore(), is(false));
            assertThat(historyResponse.getResponseMetadata(), is(notNullValue()));
        }

        {
            ConversationsMembersResponse membersResponse = slack.methods().conversationsMembers(r -> r
                    .token(token)
                    .channel(channel.getId())
                    .limit(2));
            assertThat(membersResponse.isOk(), is(true));
            assertThat(membersResponse.getMembers(), is(notNullValue()));
            assertThat(membersResponse.getMembers().isEmpty(), is(false));
            assertThat(membersResponse.getResponseMetadata(), is(notNullValue()));

            UsersListResponse usersListResponse = slack.methods().usersList(r -> r
                    .token(token));
            String invitee_ = null;
            for (User u : usersListResponse.getMembers()) {
                if (!"USLACKBOT".equals(u.getId())
                        && !membersResponse.getMembers().contains(u.getId())
                        && !u.isRestricted()
                        && !u.isUltraRestricted()) {
                    invitee_ = u.getId();
                }
            }
            String invitee = invitee_;

            ConversationsInviteResponse inviteResponse = slack.methods().conversationsInvite(r -> r
                    .token(token)
                    .channel(channel.getId())
                    .users(Arrays.asList(invitee)));
            assertThat(inviteResponse.getError(), is(nullValue()));
            assertThat(inviteResponse.isOk(), is(true));

            ConversationsKickResponse kickResponse = slack.methods().conversationsKick(r -> r
                    .token(token)
                    .channel(channel.getId())
                    .user(invitee));
            assertThat(kickResponse.getError(), is(nullValue()));
            assertThat(kickResponse.isOk(), is(true));
        }

        {
            ConversationsLeaveResponse leaveResponse = slack.methods().conversationsLeave(r -> r
                    .token(token)
                    .channel(channel.getId()));
            assertThat(leaveResponse.getError(), is(nullValue()));
            assertThat(leaveResponse.isOk(), is(true));
        }

        {
            ConversationsJoinResponse joinResponse = slack.methods().conversationsJoin(r -> r
                    .token(token)
                    .channel(channel.getId()));
            assertThat(joinResponse.getError(), is(nullValue()));
            assertThat(joinResponse.isOk(), is(true));
        }

        {
            ConversationsRenameResponse renameResponse = slack.methods().conversationsRename(r -> r
                    .token(token)
                    .channel(channel.getId())
                    .name(channel.getName() + "-1"));
            assertThat(renameResponse.getError(), is(nullValue()));
            assertThat(renameResponse.isOk(), is(true));
        }

        {
            ConversationsArchiveResponse archiveResponse = slack.methods().conversationsArchive(r -> r
                    .token(token)
                    .channel(channel.getId()));
            assertThat(archiveResponse.getError(), is(nullValue()));
            assertThat(archiveResponse.isOk(), is(true));
        }

        {
            ConversationsInfoResponse infoResponse = slack.methods().conversationsInfo(r -> r
                    .token(token)
                    .channel(channel.getId()));
            assertThat(infoResponse.getError(), is(nullValue()));
            assertThat(infoResponse.isOk(), is(true));
            Conversation fetchedChannel = infoResponse.getChannel();
            assertThat(fetchedChannel.isMember(), is(false));
            assertThat(fetchedChannel.isGeneral(), is(false));
            assertThat(fetchedChannel.isArchived(), is(true));
        }
    }

    @Test
    public void imConversation() throws IOException, SlackApiException {

        UsersListResponse usersListResponse = slack.methods().usersList(r -> r.token(token));
        List<User> users = usersListResponse.getMembers();
        String userId = users.get(0).getId();

        ConversationsOpenResponse openResponse = slack.methods().conversationsOpen(r -> r
                .token(token)
                .users(Arrays.asList(userId))
                .returnIm(true));
        assertThat(openResponse.getError(), is(nullValue()));
        assertThat(openResponse.isOk(), is(true));

        ConversationsMembersResponse membersResponse = slack.methods().conversationsMembers(r -> r
                .token(token)
                .channel(openResponse.getChannel().getId()));
        assertThat(membersResponse.getError(), is(nullValue()));
        assertThat(membersResponse.isOk(), is(true));
        assertThat(membersResponse.getMembers(), is(notNullValue()));
        assertThat(membersResponse.getMembers().isEmpty(), is(false));

        ConversationsCloseResponse closeResponse = slack.methods().conversationsClose(r ->
                r.token(token).channel(openResponse.getChannel().getId()));
        assertThat(closeResponse.isOk(), is(true));
    }

    String longText = "Slack was launched in August 2013. " +
            "In January 2015, Slack announced the acquisition of Screenhero.\n" +
            "\n" +
            "In March 2015, Slack announced that it had been hacked over the course of four days in February 2015, and that some number of users' data was compromised. " +
            "That data included email addresses, usernames, hashed passwords, and, in some cases, phone numbers and Skype IDs that users had associated with their accounts. " +
            "In response, Slack added two-factor authentication to their service.\n" +
            "\n" +
            "Slack used to offer compatibility with the non-proprietary IRC and XMPP messaging protocols, but announced in March 2018 that it would close the corresponding gateways by May 2018.\n" +
            "\n" +
            "In August 2018, Slack bought IP assets of Atlassian's two enterprise communications tools, HipChat and Stride.";

    @Test
    public void postLongText() throws IOException, SlackApiException {

        TestChannelGenerator channelGenerator = new TestChannelGenerator(token);

        Conversation channel = channelGenerator.createNewPublicChannel("test" + System.currentTimeMillis());

        // text
        {
            ChatPostMessageResponse postMessageResponse = slack.methods().chatPostMessage(r -> r
                    .token(token)
                    .channel(channel.getId())
                    .text(longText)
                    .replyBroadcast(false));
            assertThat(postMessageResponse.getError(), is(nullValue()));
            assertThat(postMessageResponse.isOk(), is(true));
        }

        // attachments
        {
            ChatPostMessageResponse postMessageResponse = slack.methods().chatPostMessage(r -> r
                    .token(token)
                    .channel(channel.getId())
                    .attachments(Arrays.asList(Attachment.builder().text(longText).build()))
                    .replyBroadcast(false));
            assertThat(postMessageResponse.getError(), is(nullValue()));
            assertThat(postMessageResponse.isOk(), is(true));
        }

        // blocks
        {
            ChatPostMessageResponse postMessageResponse = slack.methods().chatPostMessage(r -> r
                    .token(token)
                    .channel(channel.getId())
                    .blocks(Arrays.asList(
                            ContextBlock.builder().elements(Arrays.asList(
                                    (ContextBlockElement) PlainTextObject.builder().text(longText).build()
                            )).build(),
                            ContextBlock.builder().elements(Arrays.asList(
                                    (ContextBlockElement) MarkdownTextObject.builder().text(longText).build()
                            )).build()
                    ))
                    .replyBroadcast(false));
            assertThat(postMessageResponse.getError(), is(nullValue()));
            assertThat(postMessageResponse.isOk(), is(true));
        }

        channelGenerator.archiveChannel(channel);
    }

    @Test
    public void replies() throws IOException, SlackApiException {

        TestChannelGenerator channelGenerator = new TestChannelGenerator(token);
        Conversation channel = channelGenerator.createNewPublicChannel("test" + System.currentTimeMillis());

        try {
            String threadTs;
            // first message
            {
                ChatPostMessageResponse postMessageResponse = slack.methods().chatPostMessage(r -> r
                        .token(token)
                        .channel(channel.getId())
                        .text(longText)
                        .replyBroadcast(false));
                assertThat(postMessageResponse.getError(), is(nullValue()));
                assertThat(postMessageResponse.isOk(), is(true));
                threadTs = postMessageResponse.getMessage().getTs();
            }

            {
                for (int idx = 0; idx < 5; idx++) {
                    ChatPostMessageResponse postMessageResponse = slack.methods().chatPostMessage(r -> r
                            .token(token)
                            .channel(channel.getId())
                            .threadTs(threadTs)
                            .text("Say something at " + new Date())
                            .replyBroadcast(false));
                    assertThat(postMessageResponse.getError(), is(nullValue()));
                    assertThat(postMessageResponse.isOk(), is(true));
                }
            }

            // channels.replies
            {
                ChannelsRepliesResponse response = slack.methods().channelsReplies(r -> r
                        .token(token)
                        .threadTs(threadTs)
                        .channel(channel.getId()));
                assertThat(response.getError(), is(nullValue()));
                assertThat(response.isOk(), is(true));

                List<Message> messages = response.getMessages();
                Message firstMessage = messages.get(0);
                assertThat(firstMessage.getReplyUsersCount(), is(1));
                assertThat(firstMessage.getReplies().size(), is(5));
                assertThat(firstMessage.getReplyCount(), is(5));
                assertThat(firstMessage.getLatestReply(), is(messages.get(5).getTs()));
            }

            // conversations.replies
            {
                ConversationsRepliesResponse response = slack.methods().conversationsReplies(r -> r
                        .token(token)
                        .ts(threadTs)
                        .channel(channel.getId()));
                assertThat(response.getError(), is(nullValue()));
                assertThat(response.isOk(), is(true));

                List<Message> messages = response.getMessages();
                Message firstMessage = messages.get(0);
                assertThat(firstMessage.getReplyUsersCount(), is(1));
                assertThat(firstMessage.getReplies().size(), is(5));
                assertThat(firstMessage.getReplyCount(), is(5));
                assertThat(firstMessage.getLatestReply(), is(messages.get(5).getTs()));
            }

        } finally {
            channelGenerator.archiveChannel(channel);
        }
    }

    @Test
    public void longChannelName_public_ok() throws Exception {
        TestChannelGenerator channelGenerator = new TestChannelGenerator(token);
        String channelName = "test" + System.currentTimeMillis();
        while (channelName.length() < 80) {
            channelName += "_";
        }
        Conversation channel = channelGenerator.createNewPublicChannel(channelName);
        channelGenerator.archiveChannel(channel);
    }

    @Test
    public void longChannelName_private_ok() throws Exception {
        TestChannelGenerator channelGenerator = new TestChannelGenerator(token);
        String channelName = "secret-" + System.currentTimeMillis();
        while (channelName.length() < 80) {
            channelName += "_";
        }
        Conversation channel = channelGenerator.createNewPrivateChannel(channelName);
        channelGenerator.archiveChannel(channel);
    }

    @Test
    public void longChannelName_public_ng() throws Exception {
        String channelName = "test-" + System.currentTimeMillis();
        while (channelName.length() < 81) {
            channelName += "_";
        }
        ConversationsCreateResponse createPublicResponse = slack.methods().conversationsCreate(
                ConversationsCreateRequest.builder()
                        .token(token)
                        .name(channelName)
                        .isPrivate(false)
                        .build());
        assertThat(createPublicResponse.getError(), is("invalid_name_maxlength"));
    }

    @Test
    public void longChannelName_private_ng() throws Exception {
        String channelName = "test-" + System.currentTimeMillis();
        while (channelName.length() < 81) {
            channelName += "_";
        }
        ConversationsCreateResponse createPublicResponse = slack.methods().conversationsCreate(
                ConversationsCreateRequest.builder()
                        .token(token)
                        .name(channelName)
                        .isPrivate(true)
                        .build());
        assertThat(createPublicResponse.getError(), is("invalid_name_maxlength"));
    }

}
