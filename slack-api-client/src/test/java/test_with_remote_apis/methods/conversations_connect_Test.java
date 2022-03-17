package test_with_remote_apis.methods;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.response.conversations.*;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class conversations_connect_Test {

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

    @AfterClass
    public static void tearDown() throws InterruptedException {
        SlackTestConfig.awaitCompletion(testConfig);
    }

    @Test
    public void approval() throws Exception {
        MethodsClient sender = slack.methods(System.getenv(Constants.SLACK_SDK_TEST_CONNECT_INVITE_SENDER_BOT_TOKEN));
        MethodsClient receiver = slack.methods(System.getenv(Constants.SLACK_SDK_TEST_CONNECT_INVITE_RECEIVER_BOT_TOKEN));

        String channelName = "slack-connect-test-" + System.currentTimeMillis();

        ConversationsCreateResponse channelCreation = sender.conversationsCreate(r -> r.name(channelName));
        assertThat(channelCreation.getError(), is(nullValue()));
        String channelId = channelCreation.getChannel().getId();

        try {
            ConversationsInviteSharedResponse invitation = sender.conversationsInviteShared(r -> r
                    .channel(channelId)
                    .externalLimited(false)
                    .userIds(Arrays.asList(System.getenv(Constants.SLACK_SDK_TEST_CONNECT_INVITE_RECEIVER_BOT_USER_ID)))
            );
            assertThat(invitation.getError(), is(nullValue()));

            String connectTeamId = receiver.authTest(r -> r).getTeamId();
            ConversationsListConnectInvitesResponse invites = receiver.conversationsListConnectInvites(r -> r
                    .count(10).teamId(connectTeamId));
            assertThat(invites.getError(), is(nullValue()));

            ConversationsAcceptSharedInviteResponse acceptance = receiver.conversationsAcceptSharedInvite(r -> r
                    .channelName(channelName)
                    .inviteId(invitation.getInviteId())
            );
            assertThat(acceptance.getError(), is(nullValue()));

            ConversationsApproveSharedInviteResponse approval = receiver.conversationsApproveSharedInvite(r -> r.inviteId(invitation.getInviteId()));
            if (approval.getError() != null && approval.getError().equals("invalid_action")) {
                // auto-approval is enabled in this workspace / org
            } else {
                assertThat(approval.getError(), is(nullValue()));
            }

        } finally {
            ConversationsArchiveResponse archive = sender.conversationsArchive(r -> r.channel(channelId));
            assertThat(archive.getError(), is(nullValue()));
        }
    }

    @Test
    public void decline() throws Exception {
        MethodsClient sender = slack.methods(System.getenv(Constants.SLACK_SDK_TEST_CONNECT_INVITE_SENDER_BOT_TOKEN));
        MethodsClient receiver = slack.methods(System.getenv(Constants.SLACK_SDK_TEST_CONNECT_INVITE_RECEIVER_BOT_TOKEN));

        String channelName = "slack-connect-test-" + System.currentTimeMillis();

        ConversationsCreateResponse channelCreation = sender.conversationsCreate(r -> r.name(channelName));
        assertThat(channelCreation.getError(), is(nullValue()));
        String channelId = channelCreation.getChannel().getId();

        try {
            ConversationsInviteSharedResponse invitation = sender.conversationsInviteShared(r -> r
                    .channel(channelId)
                    .externalLimited(false)
                    .userIds(Arrays.asList(System.getenv(Constants.SLACK_SDK_TEST_CONNECT_INVITE_RECEIVER_BOT_USER_ID)))
            );
            assertThat(invitation.getError(), is(nullValue()));

            ConversationsAcceptSharedInviteResponse acceptance = receiver.conversationsAcceptSharedInvite(r -> r
                    .channelName(channelName)
                    .inviteId(invitation.getInviteId())
            );
            assertThat(acceptance.getError(), is(nullValue()));

            ConversationsDeclineSharedInviteResponse decline = receiver.conversationsDeclineSharedInvite(r -> r
                    .inviteId(invitation.getInviteId())
            );
            assertThat(decline.getError(), is(nullValue()));

        } finally {
            ConversationsArchiveResponse archive = sender.conversationsArchive(r -> r.channel(channelId));
            assertThat(archive.getError(), is(nullValue()));
        }
    }
}
