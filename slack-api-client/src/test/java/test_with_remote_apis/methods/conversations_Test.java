package test_with_remote_apis.methods;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.conversations.ConversationsCreateRequest;
import com.slack.api.methods.request.conversations.ConversationsListRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.methods.response.conversations.*;
import com.slack.api.methods.response.users.UsersListResponse;
import com.slack.api.model.*;
import com.slack.api.model.block.ContextBlock;
import com.slack.api.model.block.ContextBlockElement;
import com.slack.api.model.block.DividerBlock;
import com.slack.api.model.block.SectionBlock;
import com.slack.api.model.block.composition.MarkdownTextObject;
import com.slack.api.model.block.composition.PlainTextObject;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import util.TestChannelGenerator;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class conversations_Test {

    String userToken = System.getenv(Constants.SLACK_SDK_TEST_USER_TOKEN);
    String botToken = System.getenv(Constants.SLACK_SDK_TEST_BOT_TOKEN);

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

    @BeforeClass
    public static void setUp() throws Exception {
        SlackTestConfig.initializeRawJSONDataFiles("conversations.*");
    }

    @AfterClass
    public static void tearDown() throws InterruptedException {
        SlackTestConfig.awaitCompletion(testConfig);
    }

    @Test
    public void channelConversation() throws Exception {

        {
            ConversationsListResponse listResponse = slack.methods().conversationsList(
                    ConversationsListRequest.builder()
                            .token(botToken)
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
                .token(botToken)
                .name("test" + System.currentTimeMillis())
                .isPrivate(false));
        assertThat(createPublicResponse.getError(), is(nullValue()));
        assertThat(createPublicResponse.isOk(), is(true));
        assertThat(createPublicResponse.getChannel(), is(notNullValue()));
        assertThat(createPublicResponse.getChannel().isPrivate(), is(false));

        Conversation channel = createPublicResponse.getChannel();

        ConversationsCreateResponse createPrivateResponse = slack.methods().conversationsCreate(r -> r
                .token(botToken)
                .name("test" + System.currentTimeMillis())
                .isPrivate(true));
        assertThat(createPrivateResponse.getError(), is(nullValue()));
        assertThat(createPrivateResponse.isOk(), is(true));
        assertThat(createPrivateResponse.getChannel(), is(notNullValue()));
        assertThat(createPrivateResponse.getChannel().isPrivate(), is(true));

        {
            ConversationsArchiveResponse resp = slack.methods().conversationsArchive(r -> r
                    .token(botToken)
                    .channel(createPrivateResponse.getChannel().getId()));
            assertThat(resp.getError(), is(nullValue()));
        }
        {
            ConversationsUnarchiveResponse resp = slack.methods().conversationsUnarchive(r -> r
                    //.token(token)
                    .channel(createPrivateResponse.getChannel().getId()));
            assertThat(resp.getError(), is(notNullValue()));

            resp = slack.methods().conversationsUnarchive(r -> r
                    .token(botToken)
                    .channel(createPrivateResponse.getChannel().getId()));
            assertThat(resp.getError(), is(nullValue()));
        }
        {
            ConversationsArchiveResponse resp = slack.methods().conversationsArchive(r -> r
                    .token(botToken)
                    .channel(createPrivateResponse.getChannel().getId()));
            assertThat(resp.getError(), is(nullValue()));
        }

        {
            ChatPostMessageResponse postMessageResponse = slack.methods().chatPostMessage(r -> r
                    .token(botToken)
                    .channel(createPublicResponse.getChannel().getId())
                    .text("This is a test message posted by unit tests for Java Slack SDK library")
                    .replyBroadcast(false));
            assertThat(postMessageResponse.getError(), is(nullValue()));
            assertThat(postMessageResponse.isOk(), is(true));

            ChatPostMessageResponse postThread1Response = slack.methods().chatPostMessage(r -> r
                    .token(botToken)
                    .channel(createPublicResponse.getChannel().getId())
                    .threadTs(postMessageResponse.getTs())
                    .text("[thread 1] This is a test message posted by unit tests for Java Slack SDK library")
                    .replyBroadcast(false));
            assertThat(postThread1Response.getError(), is(nullValue()));
            assertThat(postThread1Response.isOk(), is(true));

            ChatPostMessageResponse postThread2Response = slack.methods().chatPostMessage(r -> r
                    .token(botToken)
                    .channel(createPublicResponse.getChannel().getId())
                    .threadTs(postMessageResponse.getTs())
                    .text("[thread 2] This is a test message posted by unit tests for Java Slack SDK library")
                    .replyBroadcast(false));
            assertThat(postThread2Response.getError(), is(nullValue()));
            assertThat(postThread2Response.isOk(), is(true));

            ConversationsRepliesResponse repliesResponse = slack.methods().conversationsReplies(r -> r
                    .token(botToken)
                    .channel(createPublicResponse.getChannel().getId())
                    .ts(postMessageResponse.getTs())
                    .limit(1));
            assertThat(repliesResponse.getError(), is(nullValue()));
            assertThat(repliesResponse.isOk(), is(true));
            assertThat(repliesResponse.getResponseMetadata(), is(notNullValue()));

            ConversationsMarkResponse markResponse = slack.methods().conversationsMark(r -> r
                    .token(botToken)
                    .channel(postMessageResponse.getChannel())
                    .ts(postMessageResponse.getTs())
            );
            assertThat(markResponse.getError(), is(nullValue()));
        }

        {
            ConversationsInfoResponse infoResponse = slack.methods().conversationsInfo(r -> r
                    .token(botToken)
                    .channel(channel.getId())
                    .includeNumMembers(true)
                    .includeLocale(true));
            assertThat(infoResponse.isOk(), is(true));
            Conversation fetchedConversation = infoResponse.getChannel();
            assertThat(fetchedConversation.isMember(), is(true));
            assertThat(fetchedConversation.isGeneral(), is(false));
            assertThat(fetchedConversation.isArchived(), is(false));
            assertThat(fetchedConversation.isIm(), is(false));
            assertThat(fetchedConversation.isMpim(), is(false));
            assertThat(fetchedConversation.isGroup(), is(false));
            assertThat(fetchedConversation.getLocale(), is(notNullValue()));
            assertThat(fetchedConversation.getNumOfMembers(), is(notNullValue()));
        }

        {
            ConversationsInfoResponse infoResponse = slack.methods().conversationsInfo(r -> r
                    .token(botToken)
                    .channel(channel.getId())
                    .includeNumMembers(false)
                    .includeLocale(false));
            assertThat(infoResponse.isOk(), is(true));
            Conversation fetchedConversation = infoResponse.getChannel();
            assertThat(fetchedConversation.getLocale(), is(nullValue()));
            assertThat(fetchedConversation.getNumOfMembers(), is(nullValue()));
        }

        {
            ConversationsSetPurposeResponse setPurposeResponse = slack.methods().conversationsSetPurpose(r -> r
                    .token(botToken)
                    .channel(channel.getId())
                    .purpose("purpose"));
            assertThat(setPurposeResponse.getError(), is(nullValue()));
            assertThat(setPurposeResponse.isOk(), is(true));
            assertThat(setPurposeResponse.getChannel().getPurpose().getValue(), is("purpose"));
        }

        {
            ConversationsSetTopicResponse setTopicResponse = slack.methods().conversationsSetTopic(r -> r
                    .token(botToken)
                    .channel(channel.getId())
                    .topic("topic"));
            assertThat(setTopicResponse.getError(), is(nullValue()));
            assertThat(setTopicResponse.isOk(), is(true));
            assertThat(setTopicResponse.getChannel().getTopic().getValue(), is("topic"));
        }

        {
            ConversationsHistoryResponse historyResponse = slack.methods().conversationsHistory(r -> r
                    .token(botToken)
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
                    .token(botToken)
                    .channel(channel.getId())
                    .limit(2));
            assertThat(membersResponse.isOk(), is(true));
            assertThat(membersResponse.getMembers(), is(notNullValue()));
            assertThat(membersResponse.getMembers().isEmpty(), is(false));
            assertThat(membersResponse.getResponseMetadata(), is(notNullValue()));

            // Using async client to avoid an exception due to rate limited errors
            UsersListResponse usersListResponse = slack.methodsAsync().usersList(r -> r
                    .token(botToken)).get();
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
                    .token(botToken)
                    .force(true)
                    .channel(channel.getId())
                    .users(Arrays.asList(invitee)));
            assertThat(inviteResponse.getError(), is(nullValue()));
            assertThat(inviteResponse.isOk(), is(true));

            ConversationsKickResponse kickResponse = slack.methods().conversationsKick(r -> r
                    .token(userToken)
                    .channel(channel.getId())
                    .user(invitee));
            assertThat(kickResponse.getError(), is(nullValue()));
            assertThat(kickResponse.isOk(), is(true));
        }

        {
            ConversationsLeaveResponse leaveResponse = slack.methods().conversationsLeave(r -> r
                    .token(botToken)
                    .channel(channel.getId()));
            assertThat(leaveResponse.getError(), is(nullValue()));
            assertThat(leaveResponse.isOk(), is(true));
        }

        {
            ConversationsJoinResponse joinResponse = slack.methods().conversationsJoin(r -> r
                    .token(botToken)
                    .channel(channel.getId()));
            assertThat(joinResponse.getError(), is(nullValue()));
            assertThat(joinResponse.isOk(), is(true));
        }

        {
            ConversationsRenameResponse renameResponse = slack.methods().conversationsRename(r -> r
                    .token(botToken)
                    .channel(channel.getId())
                    .name(channel.getName() + "-1"));
            assertThat(renameResponse.getError(), is(nullValue()));
            assertThat(renameResponse.isOk(), is(true));
        }

        {
            ConversationsArchiveResponse archiveResponse = slack.methods().conversationsArchive(r -> r
                    .token(botToken)
                    .channel(channel.getId()));
            assertThat(archiveResponse.getError(), is(nullValue()));
            assertThat(archiveResponse.isOk(), is(true));
        }

        {
            ConversationsInfoResponse infoResponse = slack.methods().conversationsInfo(r -> r
                    .token(botToken)
                    .channel(channel.getId()));
            assertThat(infoResponse.getError(), is(nullValue()));
            assertThat(infoResponse.isOk(), is(true));
            Conversation fetchedChannel = infoResponse.getChannel();
            // TODO: Fix this; it started failing in May 2023
            // assertThat(fetchedChannel.isMember(), is(false));
            assertThat(fetchedChannel.isGeneral(), is(false));
            assertThat(fetchedChannel.isArchived(), is(true));
        }
    }

    @Test
    public void imConversation() throws Exception {

        // Using async client to avoid an exception due to rate limited errors
        UsersListResponse usersListResponse = slack.methodsAsync().usersList(r -> r.token(botToken)).get();
        List<User> users = usersListResponse.getMembers();
        String userId = users.get(0).getId();

        ConversationsOpenResponse openResponse = slack.methods().conversationsOpen(r -> r
                .token(botToken)
                .users(Arrays.asList(userId))
                .returnIm(true));
        assertThat(openResponse.getError(), is(nullValue()));
        assertThat(openResponse.isOk(), is(true));

        ConversationsMembersResponse membersResponse = slack.methods().conversationsMembers(r -> r
                .token(botToken)
                .channel(openResponse.getChannel().getId()));
        assertThat(membersResponse.getError(), is(nullValue()));
        assertThat(membersResponse.isOk(), is(true));
        assertThat(membersResponse.getMembers(), is(notNullValue()));
        assertThat(membersResponse.getMembers().isEmpty(), is(false));

        ConversationsCloseResponse closeResponse = slack.methods().conversationsClose(r ->
                r.token(botToken).channel(openResponse.getChannel().getId()));
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
        TestChannelGenerator channelGenerator = new TestChannelGenerator(testConfig, botToken);
        Conversation channel = channelGenerator.createNewPublicChannel("test" + System.currentTimeMillis());

        // text
        {
            ChatPostMessageResponse postMessageResponse = slack.methods().chatPostMessage(r -> r
                    .token(botToken)
                    .channel(channel.getId())
                    .text(longText)
                    .replyBroadcast(false));
            assertThat(postMessageResponse.getError(), is(nullValue()));
            assertThat(postMessageResponse.isOk(), is(true));
        }

        // attachments
        {
            ChatPostMessageResponse postMessageResponse = slack.methods().chatPostMessage(r -> r
                    .token(botToken)
                    .channel(channel.getId())
                    .attachments(Arrays.asList(Attachment.builder().text(longText).build()))
                    .replyBroadcast(false));
            assertThat(postMessageResponse.getError(), is(nullValue()));
            assertThat(postMessageResponse.isOk(), is(true));
        }

        // blocks
        {
            ChatPostMessageResponse postMessageResponse = slack.methods().chatPostMessage(r -> r
                    .token(botToken)
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
        TestChannelGenerator channelGenerator = new TestChannelGenerator(testConfig, botToken);
        Conversation channel = channelGenerator.createNewPublicChannel("test" + System.currentTimeMillis());

        try {
            String threadTs;
            // first message
            {
                ChatPostMessageResponse postMessageResponse = slack.methods().chatPostMessage(r -> r
                        .token(botToken)
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
                            .token(botToken)
                            .channel(channel.getId())
                            .threadTs(threadTs)
                            .text("Say something at " + new Date())
                            .replyBroadcast(false));
                    assertThat(postMessageResponse.getError(), is(nullValue()));
                    assertThat(postMessageResponse.isOk(), is(true));
                }
            }

            // conversations.replies
            {
                ConversationsRepliesResponse response = slack.methods().conversationsReplies(r -> r
                        .token(botToken)
                        .ts(threadTs)
                        .channel(channel.getId()));
                assertThat(response.getError(), is(nullValue()));
                assertThat(response.isOk(), is(true));

                List<Message> messages = response.getMessages();
                Message firstMessage = messages.get(0);
                assertThat(firstMessage.getReplyUsersCount(), is(1));
                // NOTE: As of April 2020, this field is no longer available
                // assertThat(firstMessage.getReplies().size(), is(5));
                assertThat(firstMessage.getReplies(), is(nullValue()));
                assertThat(firstMessage.getReplyCount(), is(5));
                assertThat(firstMessage.getLatestReply(), is(messages.get(5).getTs()));
            }

        } finally {
            channelGenerator.archiveChannel(channel);
        }
    }

    @Test
    public void longChannelName_public_ok() throws Exception {
        TestChannelGenerator channelGenerator = new TestChannelGenerator(testConfig, botToken);
        String channelName = "test" + System.currentTimeMillis();
        while (channelName.length() < 80) {
            channelName += "_";
        }
        Conversation channel = channelGenerator.createNewPublicChannel(channelName);
        channelGenerator.archiveChannel(channel);
    }

    @Test
    public void longChannelName_private_ok() throws Exception {
        TestChannelGenerator channelGenerator = new TestChannelGenerator(testConfig, botToken);
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
                        .token(botToken)
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
                        .token(botToken)
                        .name(channelName)
                        .isPrivate(true)
                        .build());
        assertThat(createPublicResponse.getError(), is("invalid_name_maxlength"));
    }

    @Test
    public void blocks_attachments_in_thread_replies() throws Exception {
        TestChannelGenerator channelGenerator = new TestChannelGenerator(testConfig, botToken);
        Conversation channel = channelGenerator.createNewPublicChannel("test" + System.currentTimeMillis());
        try {
            ChatPostMessageResponse thread = slack.methods(botToken)
                    .chatPostMessage(r -> r.channel(channel.getId()).text("Replies in :thread:"));
            assertThat(thread.getError(), is(nullValue()));

            String threadTs = thread.getTs();
            ChatPostMessageResponse reply = slack.methods(botToken).chatPostMessage(r -> r
                    .channel(channel.getId())
                    .threadTs(threadTs)
                    .text("Hi there!")
                    .blocks(Arrays.asList(
                            DividerBlock.builder()
                                    .blockId("b1")
                                    .build(),
                            SectionBlock.builder()
                                    .blockId("b2")
                                    .text(PlainTextObject.builder().text("Hi there!").build())
                                    .build()
                    ))
            );
            assertThat(reply.getError(), is(nullValue()));
            ChatPostMessageResponse reply2 = slack.methods(botToken).chatPostMessage(r -> r
                    .channel(channel.getId())
                    .threadTs(threadTs)
                    .text("Hi there!")
                    .attachments(Arrays.asList(Attachment.builder()
                            .callbackId("callback")
                            .text("Hi there!")
                            .footer("This is a test")
                            .build()
                    ))
            );
            assertThat(reply2.getError(), is(nullValue()));

            ConversationsRepliesResponse replies = slack.methods(botToken).conversationsReplies(r -> r
                    .channel(channel.getId())
                    .ts(threadTs)
            );
            assertThat(replies.getError(), is(nullValue()));
        } finally {
            channelGenerator.archiveChannel(channel);
        }
    }

    @Test
    public void listIm() throws Exception {
        ConversationsListResponse response = slack.methods(botToken).conversationsList(r -> r
                .types(Arrays.asList(ConversationType.IM))
                .limit(20)
        );
        assertThat(response.getError(), is(nullValue()));
    }

}
