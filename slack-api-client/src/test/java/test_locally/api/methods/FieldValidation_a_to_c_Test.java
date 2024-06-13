package test_locally.api.methods;

import com.slack.api.methods.response.api.ApiTestResponse;
import com.slack.api.methods.response.apps.AppsUninstallResponse;
import com.slack.api.methods.response.auth.AuthRevokeResponse;
import com.slack.api.methods.response.auth.AuthTestResponse;
import com.slack.api.methods.response.auth.teams.AuthTeamsListResponse;
import com.slack.api.methods.response.bots.BotsInfoResponse;
import com.slack.api.methods.response.calls.CallsAddResponse;
import com.slack.api.methods.response.calls.CallsEndResponse;
import com.slack.api.methods.response.calls.CallsInfoResponse;
import com.slack.api.methods.response.calls.CallsUpdateResponse;
import com.slack.api.methods.response.calls.participants.CallsParticipantsAddResponse;
import com.slack.api.methods.response.calls.participants.CallsParticipantsRemoveResponse;
import com.slack.api.methods.response.channels.*;
import com.slack.api.methods.response.chat.*;
import com.slack.api.methods.response.chat.scheduled_messages.ChatScheduledMessagesListResponse;
import com.slack.api.methods.response.conversations.*;
import com.slack.api.model.Channel;
import com.slack.api.model.Conversation;
import com.slack.api.model.Message;
import com.slack.api.util.json.GsonFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;
import util.FileReader;

import java.io.IOException;

import static test_locally.api.util.FieldVerification.verifyIfAllGettersReturnNonNull;
import static test_locally.api.util.FieldVerification.verifyIfAllGettersReturnNonNullRecursively;

// disabled these tests because it no longer brings lots of value; that said, if you need these again, you can turn them on again
@Ignore
@Slf4j
public class FieldValidation_a_to_c_Test {

    private FileReader reader = new FileReader("../json-logs/samples/api/");

    private <T> T parse(String path, Class<T> clazz) throws IOException {
        return GsonFactory.createSnakeCase().fromJson(reader.readWholeAsString(path + ".json"), clazz);
    }

    @Test
    public void api_test() throws Exception {
        ApiTestResponse obj = parse("api.test", ApiTestResponse.class);
        verifyIfAllGettersReturnNonNull(obj);
    }

    @Test
    public void apps_uninstall() throws Exception {
        AppsUninstallResponse obj = parse("apps.uninstall", AppsUninstallResponse.class);
        verifyIfAllGettersReturnNonNull(obj);
    }

    @Test
    public void auth_revoke() throws Exception {
        AuthRevokeResponse obj = parse("auth.revoke", AuthRevokeResponse.class);
        verifyIfAllGettersReturnNonNull(obj);
    }

    @Test
    public void auth_teams_list() throws Exception {
        AuthTeamsListResponse obj = parse("auth.teams.list", AuthTeamsListResponse.class);
        verifyIfAllGettersReturnNonNull(obj);
    }

    @Test
    public void auth_test() throws Exception {
        AuthTestResponse obj = parse("auth.test", AuthTestResponse.class);
        verifyIfAllGettersReturnNonNull(obj);
    }

    @Test
    public void bots_info() throws Exception {
        BotsInfoResponse obj = parse("bots.info", BotsInfoResponse.class);
        verifyIfAllGettersReturnNonNull(obj);
        verifyIfAllGettersReturnNonNullRecursively(obj.getBot());
    }

    @Test
    public void calls_add() throws Exception {
        CallsAddResponse obj = parse("calls.add", CallsAddResponse.class);
        verifyIfAllGettersReturnNonNull(obj, "getResponseMetadata", "getWarning");
    }

    @Test
    public void calls_end() throws Exception {
        CallsEndResponse obj = parse("calls.end", CallsEndResponse.class);
        verifyIfAllGettersReturnNonNull(obj, "getResponseMetadata", "getWarning");
    }

    @Test
    public void calls_info() throws Exception {
        CallsInfoResponse obj = parse("calls.info", CallsInfoResponse.class);
        verifyIfAllGettersReturnNonNull(obj, "getResponseMetadata", "getWarning");
    }

    @Test
    public void calls_update() throws Exception {
        CallsUpdateResponse obj = parse("calls.update", CallsUpdateResponse.class);
        verifyIfAllGettersReturnNonNull(obj, "getResponseMetadata", "getWarning");
    }

    @Test
    public void calls_participants_add() throws Exception {
        CallsParticipantsAddResponse obj = parse("calls.participants.add", CallsParticipantsAddResponse.class);
        verifyIfAllGettersReturnNonNull(obj, "getResponseMetadata", "getWarning");
    }

    @Test
    public void calls_participants_remove() throws Exception {
        CallsParticipantsRemoveResponse obj = parse("calls.participants.remove", CallsParticipantsRemoveResponse.class);
        verifyIfAllGettersReturnNonNull(obj, "getResponseMetadata", "getWarning");
    }

    private void validateChannel(Channel channel) throws Exception {
        verifyIfAllGettersReturnNonNullRecursively(channel,
                "getEnterpriseId",
                "getUser",
                "getPriority",
                "getRoot",
                "getIsMoved",
                "getPendingShared",
                "getNumMembers",
                "getUnreadCount",
                "getUnreadCountDisplay",
                "getLatest",
                // latest
                "getTeam",
                "getMetadata",
                "getAttachments",
                "getBlocks",
                "getTopic",
                "getClientMsgId",
                "getThreadTs",
                "getReactions",
                "getIcons",
                "getFiles",
                "getBotId",
                "getBotLink",
                "getXFiles",
                "getUsername",
                "getLastRead"
        );
    }

    private void validateMessage(Message message) throws Exception {
        verifyIfAllGettersReturnNonNullRecursively(message,
                "getUnreadCount",
                "getFile",
                "getFiles",
                "getXFiles",
                "getIsMoved",
                "getPendingShared",
                "getChannel",
                "getClientMsgId",
                "getPinnedTo",
                "getReactions",
                "getParentUserId",
                "getBotLink",
                "getInviter",
                "getItemType",
                "getImage\\d+$",
                "getComment",
                "getItem",
                "getLastRead",
                "getTopic",
                "getPurpose",
                "getEdited",
                "getReplies",
                "getReplyCount",
                "getReplyUsers",
                "getReplyUsersCount",
                "getLatestReply",
                "getRoot",
                // conversations
                "getUser",
                "getBotProfile",
                "getTeam",
                "getAttachments",
                "getMetadata",
                "getBlocks",
                "getIcons",
                "getUsername",
                "getSubtype",
                "getRoom"
        );
    }

    @Test
    public void channels() throws Exception {
        String prefix = "channels.";
        {
            ChannelsArchiveResponse obj = parse(prefix + "archive", ChannelsArchiveResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
        }
        {
            ChannelsCreateResponse obj = parse(prefix + "create", ChannelsCreateResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
            verifyIfAllGettersReturnNonNullRecursively(obj.getChannel(),
                    "getEnterpriseId",
                    "getLatest",
                    "getUser",
                    "getIsMoved",
                    "getPendingShared",
                    "getNumMembers");
        }
        {
            ChannelsHistoryResponse obj = parse(prefix + "history", ChannelsHistoryResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
            verifyIfAllGettersReturnNonNullRecursively(obj.getMessages().get(0),
                    "getUnreadCount",
                    "getMetadata",
                    "getFile",
                    "getIsMoved",
                    "getPendingShared",
                    "getChannel",
                    "getClientMsgId",
                    "getPinnedTo",
                    "getReactions",
                    "getParentUserId",
                    "getBotLink",
                    "getInviter",
                    "getItemType",
                    "getImage\\d+$",
                    "getComment",
                    "getItem",
                    "getUsername",
                    "getLastRead",
                    "getReplies",
                    "getSubtype",
                    "getTopic",
                    "getPurpose",
                    "getFiles",
                    "getXFiles",
                    "getAppId",
                    "getUser", // MessageRoot
                    "getTeam", // MessageRoot
                    "getBotProfile", // MessageRoot
                    "getRoom"
            );
        }
        {
            ChannelsInfoResponse obj = parse(prefix + "info", ChannelsInfoResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
            validateChannel(obj.getChannel());
        }
        {
            ChannelsInviteResponse obj = parse(prefix + "invite", ChannelsInviteResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
            validateChannel(obj.getChannel());
        }
        {
            ChannelsJoinResponse obj = parse(prefix + "join", ChannelsJoinResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
            validateChannel(obj.getChannel());
        }
        {
            ChannelsKickResponse obj = parse(prefix + "kick", ChannelsKickResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
        }
        {
            ChannelsLeaveResponse obj = parse(prefix + "leave", ChannelsLeaveResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
        }
        {
            ChannelsListResponse obj = parse(prefix + "list", ChannelsListResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
            validateChannel(obj.getChannels().get(0));
        }
        {
            ChannelsMarkResponse obj = parse(prefix + "mark", ChannelsMarkResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
        }
        {
            ChannelsRenameResponse obj = parse(prefix + "rename", ChannelsRenameResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
            validateChannel(obj.getChannel());
        }
        {
            ChannelsRepliesResponse obj = parse(prefix + "replies", ChannelsRepliesResponse.class);
            verifyIfAllGettersReturnNonNull(obj, "getWarning", "getThreadInfo");
        }
        {
            ChannelsSetPurposeResponse obj = parse(prefix + "setPurpose", ChannelsSetPurposeResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
        }
        {
            ChannelsSetTopicResponse obj = parse(prefix + "setTopic", ChannelsSetTopicResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
        }
        {
            ChannelsUnarchiveResponse obj = parse(prefix + "unarchive", ChannelsUnarchiveResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
        }
    }

    @Test
    public void chat_post() throws Exception {
        String prefix = "chat.";
        {
            ChatPostEphemeralResponse obj = parse(prefix + "postEphemeral", ChatPostEphemeralResponse.class);
            verifyIfAllGettersReturnNonNull(obj, "getWarning", "getChannel", "getDeprecatedArgument");
        }
        {
            ChatPostMessageResponse obj = parse(prefix + "postMessage", ChatPostMessageResponse.class);
            verifyIfAllGettersReturnNonNull(obj, "getDeprecatedArgument", "getWarning", "getErrors");
            validateMessage(obj.getMessage());
            verifyIfAllGettersReturnNonNull(obj.getResponseMetadata());
        }
    }

    @Test
    public void chat_update() throws Exception {
        String prefix = "chat.";
        ChatUpdateResponse obj = parse(prefix + "update", ChatUpdateResponse.class);
        verifyIfAllGettersReturnNonNull(obj, "getDeprecatedArgument", "getWarning");
        verifyIfAllGettersReturnNonNullRecursively(obj.getMessage(),
                "getTs",
                "getAttachments",
                "getIcons",
                "getUnreadCount",
                "getFile",
                "getFiles",
                "getXFiles",
                "getIsMoved",
                "getPendingShared",
                "getChannel",
                "getClientMsgId",
                "getPinnedTo",
                "getReactions",
                "getParentUserId",
                "getBotLink",
                "getInviter",
                "getItemType",
                "getImage\\d+$",
                "getComment",
                "getItem",
                "getLastRead",
                "getTopic",
                "getPurpose",
                "getEdited",
                "getReplies",
                "getReplyCount",
                "getReplyUsers",
                "getReplyUsersCount",
                "getLatestReply",
                "getRoot",
                "getThreadTs",
                "getUsername",
                "getSubtype",
                "getLastInviteStatusByUser",
                "getParticipantsEvents",
                "getKnocks"
        );
    }

    @Test
    public void chat_delete() throws Exception {
        String prefix = "chat.";
        ChatDeleteResponse obj = parse(prefix + "delete", ChatDeleteResponse.class);
        verifyIfAllGettersReturnNonNull(obj, "getDeprecatedArgument", "getWarning");
    }

    @Test
    public void chat_scheduled_message() throws Exception {
        String prefix = "chat.";
        {
            ChatDeleteScheduledMessageResponse obj = parse(prefix + "deleteScheduledMessage", ChatDeleteScheduledMessageResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
        }
        {
            ChatScheduledMessagesListResponse obj = parse(prefix + "scheduledMessages.list", ChatScheduledMessagesListResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
        }
        {
            ChatScheduleMessageResponse obj = parse(prefix + "scheduleMessage", ChatScheduleMessageResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
            verifyIfAllGettersReturnNonNull(obj.getMessage(), "getAttachments");
        }
    }

    @Test
    public void chat_others() throws Exception {
        String prefix = "chat.";
        {
            ChatGetPermalinkResponse obj = parse(prefix + "getPermalink", ChatGetPermalinkResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
        }
        {
            ChatMeMessageResponse obj = parse(prefix + "meMessage", ChatMeMessageResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
        }
        {
            ChatUnfurlResponse obj = parse(prefix + "unfurl", ChatUnfurlResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
        }
    }

    void validateConversation(Conversation conversation) throws Exception {
        verifyIfAllGettersReturnNonNullRecursively(conversation,
                "getParentConversation",
                "getConversationHostId",
                "getInternalTeamIds",
                "getConnectedTeamIds",
                "getLocale",
                "getEnterpriseId",
                "getUser",
                "getNumOfMembers",
                "getPriority",
                "getIsMoved",
                "getPendingShared",
                "getUnreadCount",
                "getUnreadCountDisplay",
                "getLatest",
                "getLastRead",
                "getDateConnected",
                "getSharedTeamIds",
                "getPreviousNames",
                "getPendingConnectedTeamIds",
                "getConnectedLimitedTeamIds",
                "getIsUserDeleted",
                "getProperties",
                "getChannelEmailAddresses"
        );
    }

    @Test
    public void conversations_queries() throws Exception {
        String prefix = "conversations.";
        {
            ConversationsHistoryResponse obj = parse(prefix + "history", ConversationsHistoryResponse.class);
            verifyIfAllGettersReturnNonNull(obj, "getWarning", "getLatest");
        }
        {
            ConversationsInfoResponse obj = parse(prefix + "info", ConversationsInfoResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
            validateConversation(obj.getChannel());
        }
        {
            ConversationsListResponse obj = parse(prefix + "list", ConversationsListResponse.class);
            verifyIfAllGettersReturnNonNull(obj, "getWarning");
            validateConversation(obj.getChannels().get(0));
        }
        {
            ConversationsMembersResponse obj = parse(prefix + "members", ConversationsMembersResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
        }
        {
            ConversationsRepliesResponse obj = parse(prefix + "replies", ConversationsRepliesResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
            validateMessage(obj.getMessages().get(0));
        }
    }

    @Test
    public void conversations_commands() throws Exception {
        String prefix = "conversations.";
        {
            ConversationsArchiveResponse obj = parse(prefix + "archive", ConversationsArchiveResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
        }
        {
            ConversationsCloseResponse obj = parse(prefix + "close", ConversationsCloseResponse.class);
            verifyIfAllGettersReturnNonNull(obj, "getNoOp", "getWarning", "getAlreadyClosed");
        }
        {
            ConversationsCreateResponse obj = parse(prefix + "create", ConversationsCreateResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
            validateConversation(obj.getChannel());
        }
        {
            ConversationsInviteResponse obj = parse(prefix + "invite", ConversationsInviteResponse.class);
            verifyIfAllGettersReturnNonNull(obj, "getErrors", "getWarning");
            validateConversation(obj.getChannel());
        }
        {
            ConversationsJoinResponse obj = parse(prefix + "join", ConversationsJoinResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
            validateConversation(obj.getChannel());
        }
        {
            ConversationsKickResponse obj = parse(prefix + "kick", ConversationsKickResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
        }
        {
            ConversationsLeaveResponse obj = parse(prefix + "leave", ConversationsLeaveResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
        }
        {
            ConversationsMarkResponse obj = parse(prefix + "mark", ConversationsMarkResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
        }
        {
            ConversationsOpenResponse obj = parse(prefix + "open", ConversationsOpenResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
            verifyIfAllGettersReturnNonNullRecursively(obj.getChannel(),
                    "getEdited",
                    "getName",
                    "getNameNormalized",
                    "getTopic",
                    "getPurpose",
                    "getCreator",
                    "getUpdated",
                    "getUnlinked",
                    "getSharedTeamIds",
                    "getPreviousNames",
                    "getPendingConnectedTeamIds",
                    "getConnectedLimitedTeamIds",
                    "getParentConversation",
                    "getConversationHostId",
                    "getInternalTeamIds",
                    "getConnectedTeamIds",
                    "getLocale",
                    "getEnterpriseId",
                    "getRoot",
                    "getUser",
                    "getTeam",
                    "getAttachments",
                    "getBlocks",
                    "getClientMsgId",
                    "getReactions",
                    "getIcons",
                    "getFiles",
                    "getXFiles",
                    "getBotLink",
                    "getNumOfMembers",
                    "getIsMoved",
                    "getPendingShared",
                    "getLastRead",
                    "getDateConnected",
                    "getThreadTs",
                    "getUsername",
                    "getParentUserId",
                    "getBotProfile",
                    "getIsUserDeleted",
                    "getProperties",
                    "getChannelEmailAddresses"
            );
        }
        {
            ConversationsRenameResponse obj = parse(prefix + "rename", ConversationsRenameResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
            validateConversation(obj.getChannel());
        }
        {
            ConversationsSetPurposeResponse obj = parse(prefix + "setPurpose", ConversationsSetPurposeResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
            validateConversation(obj.getChannel());
        }
        {
            ConversationsSetTopicResponse obj = parse(prefix + "setTopic", ConversationsSetTopicResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
            validateConversation(obj.getChannel());
        }
        {
            ConversationsUnarchiveResponse obj = parse(prefix + "unarchive", ConversationsUnarchiveResponse.class);
            verifyIfAllGettersReturnNonNull(obj);
        }
    }

}
