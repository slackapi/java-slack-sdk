package test_locally.app_backend.events;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.handler.*;
import com.slack.api.app_backend.events.payload.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class EventHandlersTest {

    @Test
    public void handlers() {
        List<EventHandler<?>> handlers = Arrays.asList(
                new ImCloseHandler() {
                    @Override
                    public void handle(ImClosePayload payload) {
                    }
                },
                new ChannelLeftHandler() {
                    @Override
                    public void handle(ChannelLeftPayload payload) {
                    }
                },
                new AppRequestedHandler() {
                    @Override
                    public void handle(AppRequestedPayload payload) {
                    }
                },
                new SubteamSelfRemovedHandler() {
                    @Override
                    public void handle(SubteamSelfRemovedPayload payload) {
                    }
                },
                new GroupDeletedHandler() {
                    @Override
                    public void handle(GroupDeletedPayload payload) {
                    }
                },
                new GridMigrationFinishedHandler() {
                    @Override
                    public void handle(GridMigrationFinishedPayload payload) {
                    }
                },
                new ScopeDeniedHandler() {
                    @Override
                    public void handle(ScopeDeniedPayload payload) {
                    }
                },
                new ImCreatedHandler() {
                    @Override
                    public void handle(ImCreatedPayload payload) {
                    }
                },
                new FileDeletedHandler() {
                    @Override
                    public void handle(FileDeletedPayload payload) {
                    }
                },
                new StarRemovedHandler() {
                    @Override
                    public void handle(StarRemovedPayload payload) {
                    }
                },
                new ChannelHistoryChangedHandler() {
                    @Override
                    public void handle(ChannelHistoryChangedPayload payload) {
                    }
                },
                new UserResourceRemovedHandler() {
                    @Override
                    public void handle(UserResourceRemovedPayload payload) {
                    }
                },
                new UserResourceDeniedHandler() {
                    @Override
                    public void handle(UserResourceDeniedPayload payload) {
                    }
                },
                new FileChangeHandler() {
                    @Override
                    public void handle(FileChangePayload payload) {
                    }
                },
                new SubteamCreatedHandler() {
                    @Override
                    public void handle(SubteamCreatedPayload payload) {
                    }
                },
                new GroupOpenHandler() {
                    @Override
                    public void handle(GroupOpenPayload payload) {
                    }
                },
                new LinkSharedHandler() {
                    @Override
                    public void handle(LinkSharedPayload payload) {
                    }
                },
                new InviteRequestedHandler() {
                    @Override
                    public void handle(InviteRequestedPayload payload) {
                    }
                },
                new DndUpdatedHandler() {
                    @Override
                    public void handle(DndUpdatedPayload payload) {
                    }
                },
                new GroupRenameHandler() {
                    @Override
                    public void handle(GroupRenamePayload payload) {
                    }
                },
                new SubteamSelfAddedHandler() {
                    @Override
                    public void handle(SubteamSelfAddedPayload payload) {
                    }
                },
                new PinRemovedHandler() {
                    @Override
                    public void handle(PinRemovedPayload payload) {
                    }
                },
                new SubteamUpdatedHandler() {
                    @Override
                    public void handle(SubteamUpdatedPayload payload) {
                    }
                },
                new ResourcesRemovedHandler() {
                    @Override
                    public void handle(ResourcesRemovedPayload payload) {
                    }
                },
                new ChannelArchiveHandler() {
                    @Override
                    public void handle(ChannelArchivePayload payload) {
                    }
                },
                new AppMentionHandler() {
                    @Override
                    public void handle(AppMentionPayload payload) {
                    }
                },
                new ChannelRenameHandler() {
                    @Override
                    public void handle(ChannelRenamePayload payload) {
                    }
                },
                new TeamDomainChangeHandler() {
                    @Override
                    public void handle(TeamDomainChangePayload payload) {
                    }
                },
                new TokensRevokedHandler() {
                    @Override
                    public void handle(TokensRevokedPayload payload) {
                    }
                },
                new AppHomeOpenedHandler() {
                    @Override
                    public void handle(AppHomeOpenedPayload payload) {
                    }
                },
                new FilePublicHandler() {
                    @Override
                    public void handle(FilePublicPayload payload) {
                    }
                },
                new EmailDomainChangedHandler() {
                    @Override
                    public void handle(EmailDomainChangedPayload payload) {
                    }
                },
                new ChannelCreatedHandler() {
                    @Override
                    public void handle(ChannelCreatedPayload payload) {
                    }
                },
                new MessageRepliedHandler() {
                    @Override
                    public void handle(MessageRepliedPayload payload) {
                    }
                },
                new ChannelUnarchiveHandler() {
                    @Override
                    public void handle(ChannelUnarchivePayload payload) {
                    }
                },
                new GroupUnarchiveHandler() {
                    @Override
                    public void handle(GroupUnarchivePayload payload) {
                    }
                },
                new UserChangeHandler() {
                    @Override
                    public void handle(UserChangePayload payload) {
                    }
                },
                new DndUpdatedUserHandler() {
                    @Override
                    public void handle(DndUpdatedUserPayload payload) {
                    }
                },
                new AppRateLimitedHandler() {
                    @Override
                    public void handle(AppRateLimitedPayload payload) {
                    }
                },
                new ResourcesAddedHandler() {
                    @Override
                    public void handle(ResourcesAddedPayload payload) {
                    }
                },
                new ReactionRemovedHandler() {
                    @Override
                    public void handle(ReactionRemovedPayload payload) {
                    }
                },
                new ScopeGrantedHandler() {
                    @Override
                    public void handle(ScopeGrantedPayload payload) {
                    }
                },
                new GridMigrationStartedHandler() {
                    @Override
                    public void handle(GridMigrationStartedPayload payload) {
                    }
                },
                new PinAddedHandler() {
                    @Override
                    public void handle(PinAddedPayload payload) {
                    }
                },
                new StarAddedHandler() {
                    @Override
                    public void handle(StarAddedPayload payload) {
                    }
                },
                new UserResourceGrantedHandler() {
                    @Override
                    public void handle(UserResourceGrantedPayload payload) {
                    }
                },
                new MemberJoinedChannelHandler() {
                    @Override
                    public void handle(MemberJoinedChannelPayload payload) {
                    }
                },
                new TeamRenameHandler() {
                    @Override
                    public void handle(TeamRenamePayload payload) {
                    }
                },
                new GroupHistoryChangedHandler() {
                    @Override
                    public void handle(GroupHistoryChangedPayload payload) {
                    }
                },
                new GroupArchiveHandler() {
                    @Override
                    public void handle(GroupArchivePayload payload) {
                    }
                },
                new GroupCloseHandler() {
                    @Override
                    public void handle(GroupClosePayload payload) {
                    }
                },
                new GroupLeftHandler() {
                    @Override
                    public void handle(GroupLeftPayload payload) {
                    }
                },
                new EmojiChangedHandler() {
                    @Override
                    public void handle(EmojiChangedPayload payload) {
                    }
                },
                new SubteamMembersChangedHandler() {
                    @Override
                    public void handle(SubteamMembersChangedPayload payload) {
                    }
                },
                new ImHistoryChangedHandler() {
                    @Override
                    public void handle(ImHistoryChangedPayload payload) {
                    }
                },
                new FileUnsharedHandler() {
                    @Override
                    public void handle(FileUnsharedPayload payload) {
                    }
                },
                new MemberLeftChannelHandler() {
                    @Override
                    public void handle(MemberLeftChannelPayload payload) {
                    }
                },
                new FileCreatedHandler() {
                    @Override
                    public void handle(FileCreatedPayload payload) {
                    }
                },
                new FileSharedHandler() {
                    @Override
                    public void handle(FileSharedPayload payload) {
                    }
                },
                new TeamJoinHandler() {
                    @Override
                    public void handle(TeamJoinPayload payload) {
                    }
                },
                new ChannelDeletedHandler() {
                    @Override
                    public void handle(ChannelDeletedPayload payload) {
                    }
                },
                new ReactionAddedHandler() {
                    @Override
                    public void handle(ReactionAddedPayload payload) {
                    }
                },
                new ImOpenHandler() {
                    @Override
                    public void handle(ImOpenPayload payload) {
                    }
                },
                new CallRejectedHandler() {
                    @Override
                    public void handle(CallRejectedPayload payload) {
                    }
                },
                new TeamAccessGrantedHandler() {
                    @Override
                    public void handle(TeamAccessGrantedPayload payload) {
                    }
                },
                new TeamAccessRevokedHandler() {
                    @Override
                    public void handle(TeamAccessRevokedPayload payload) {
                    }
                },
                new WorkflowStepExecuteHandler() {
                    @Override
                    public void handle(WorkflowStepExecutePayload payload) {
                    }
                },
                new WorkflowDeletedHandler() {
                    @Override
                    public void handle(WorkflowDeletedPayload payload) {
                    }
                },
                new WorkflowPublishedHandler() {
                    @Override
                    public void handle(WorkflowPublishedPayload payload) {
                    }
                },
                new WorkflowUnpublishedHandler() {
                    @Override
                    public void handle(WorkflowPublishedPayload payload) {
                    }
                },
                new WorkflowStepDeletedHandler() {
                    @Override
                    public void handle(WorkflowStepDeletedPayload payload) {
                    }
                },
                new ChannelIdChangedHandler() {
                    @Override
                    public void handle(ChannelIdChangedPayload payload) {
                    }
                },
                // Slack Connect
                new SharedChannelInviteAcceptedHandler() {
                    @Override
                    public void handle(SharedChannelInviteAcceptedPayload payload) {
                    }
                },
                new SharedChannelInviteApprovedHandler() {
                    @Override
                    public void handle(SharedChannelInviteApprovedPayload payload) {
                    }
                },
                new SharedChannelInviteReceivedHandler() {
                    @Override
                    public void handle(SharedChannelInviteReceivedPayload payload) {
                    }
                },
                new SharedChannelInviteDeclinedHandler() {
                    @Override
                    public void handle(SharedChannelInviteDeclinedPayload payload) {
                    }
                }
        );

        for (EventHandler<?> handler : handlers) {
            assertNotNull(handler.getEventType());
            assertNull(handler.getEventSubtype());
        }
    }

    @Test
    public void messageHandlers_with_subtype() {
        List<EventHandler<?>> handlers = Arrays.asList(
                new MessageChangedHandler() {
                    @Override
                    public void handle(MessageChangedPayload payload) {
                    }
                },
                new MessageMeHandler() {
                    @Override
                    public void handle(MessageMePayload payload) {
                    }
                },
                new MessageDeletedHandler() {
                    @Override
                    public void handle(MessageDeletedPayload payload) {
                    }
                },
                new MessageFileShareHandler() {
                    @Override
                    public void handle(MessageFileSharePayload payload) {
                    }
                },
                new MessageBotHandler() {
                    @Override
                    public void handle(MessageBotPayload payload) {
                    }
                },
                new MessageThreadBroadcastHandler() {
                    @Override
                    public void handle(MessageThreadBroadcastPayload payload) {
                    }
                },
                new MessageEkmAccessDeniedHandler() {
                    @Override
                    public void handle(MessageEkmAccessDeniedPayload payload) {
                    }
                },
                new MessageGroupTopicHandler() {
                    @Override
                    public void handle(MessageGroupTopicPayload payload) {
                    }
                },
                new MessageChannelTopicHandler() {
                    @Override
                    public void handle(MessageChannelTopicPayload payload) {
                    }
                },
                new MessageChannelJoinHandler() {
                    @Override
                    public void handle(MessageChannelJoinPayload payload) {
                    }
                },
                new MessageChannelPostingPermissionsHandler() {
                    @Override
                    public void handle(MessageChannelPostingPermissionsPayload payload) {
                    }
                },
                new MessageChannelArchiveHandler() {
                    @Override
                    public void handle(MessageChannelArchivePayload payload) {
                    }
                },
                new MessageChannelUnarchiveHandler() {
                    @Override
                    public void handle(MessageChannelUnarchivePayload payload) {
                    }
                },
                new MessageChannelLeaveHandler() {
                    @Override
                    public void handle(MessageChannelLeavePayload payload) {
                    }
                },
                new MessageChannelNameHandler() {
                    @Override
                    public void handle(MessageChannelNamePayload payload) {
                    }
                },
                new MessageChannelPurposeHandler() {
                    @Override
                    public void handle(MessageChannelPurposePayload payload) {
                    }
                }
        );
        for (EventHandler<?> handler : handlers) {
            assertNotNull(handler.getEventType());
            assertNotNull(handler.getEventSubtype());
        }
    }

}
