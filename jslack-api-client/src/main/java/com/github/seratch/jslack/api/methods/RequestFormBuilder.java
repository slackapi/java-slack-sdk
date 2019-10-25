package com.github.seratch.jslack.api.methods;

import com.github.seratch.jslack.api.methods.request.admin.apps.AdminAppsApproveRequest;
import com.github.seratch.jslack.api.methods.request.admin.apps.AdminAppsRequestsListRequest;
import com.github.seratch.jslack.api.methods.request.admin.apps.AdminAppsRestrictRequest;
import com.github.seratch.jslack.api.methods.request.admin.users.AdminUsersSessionResetRequest;
import com.github.seratch.jslack.api.methods.request.api.ApiTestRequest;
import com.github.seratch.jslack.api.methods.request.apps.AppsUninstallRequest;
import com.github.seratch.jslack.api.methods.request.apps.permissions.AppsPermissionsInfoRequest;
import com.github.seratch.jslack.api.methods.request.apps.permissions.AppsPermissionsRequestRequest;
import com.github.seratch.jslack.api.methods.request.apps.permissions.resources.AppsPermissionsResourcesListRequest;
import com.github.seratch.jslack.api.methods.request.apps.permissions.scopes.AppsPermissionsScopesListRequest;
import com.github.seratch.jslack.api.methods.request.apps.permissions.users.AppsPermissionsUsersListRequest;
import com.github.seratch.jslack.api.methods.request.apps.permissions.users.AppsPermissionsUsersRequestRequest;
import com.github.seratch.jslack.api.methods.request.auth.AuthRevokeRequest;
import com.github.seratch.jslack.api.methods.request.auth.AuthTestRequest;
import com.github.seratch.jslack.api.methods.request.bots.BotsInfoRequest;
import com.github.seratch.jslack.api.methods.request.channels.*;
import com.github.seratch.jslack.api.methods.request.chat.*;
import com.github.seratch.jslack.api.methods.request.chat.scheduled_messages.ChatScheduleMessagesListRequest;
import com.github.seratch.jslack.api.methods.request.conversations.*;
import com.github.seratch.jslack.api.methods.request.dialog.DialogOpenRequest;
import com.github.seratch.jslack.api.methods.request.dnd.*;
import com.github.seratch.jslack.api.methods.request.emoji.EmojiListRequest;
import com.github.seratch.jslack.api.methods.request.files.*;
import com.github.seratch.jslack.api.methods.request.files.comments.FilesCommentsAddRequest;
import com.github.seratch.jslack.api.methods.request.files.comments.FilesCommentsDeleteRequest;
import com.github.seratch.jslack.api.methods.request.files.comments.FilesCommentsEditRequest;
import com.github.seratch.jslack.api.methods.request.files.remote.*;
import com.github.seratch.jslack.api.methods.request.groups.*;
import com.github.seratch.jslack.api.methods.request.im.*;
import com.github.seratch.jslack.api.methods.request.migration.MigrationExchangeRequest;
import com.github.seratch.jslack.api.methods.request.mpim.*;
import com.github.seratch.jslack.api.methods.request.oauth.OAuthAccessRequest;
import com.github.seratch.jslack.api.methods.request.oauth.OAuthTokenRequest;
import com.github.seratch.jslack.api.methods.request.pins.PinsAddRequest;
import com.github.seratch.jslack.api.methods.request.pins.PinsListRequest;
import com.github.seratch.jslack.api.methods.request.pins.PinsRemoveRequest;
import com.github.seratch.jslack.api.methods.request.reactions.ReactionsAddRequest;
import com.github.seratch.jslack.api.methods.request.reactions.ReactionsGetRequest;
import com.github.seratch.jslack.api.methods.request.reactions.ReactionsListRequest;
import com.github.seratch.jslack.api.methods.request.reactions.ReactionsRemoveRequest;
import com.github.seratch.jslack.api.methods.request.reminders.*;
import com.github.seratch.jslack.api.methods.request.rtm.RTMConnectRequest;
import com.github.seratch.jslack.api.methods.request.rtm.RTMStartRequest;
import com.github.seratch.jslack.api.methods.request.search.SearchAllRequest;
import com.github.seratch.jslack.api.methods.request.search.SearchFilesRequest;
import com.github.seratch.jslack.api.methods.request.search.SearchMessagesRequest;
import com.github.seratch.jslack.api.methods.request.stars.StarsAddRequest;
import com.github.seratch.jslack.api.methods.request.stars.StarsListRequest;
import com.github.seratch.jslack.api.methods.request.stars.StarsRemoveRequest;
import com.github.seratch.jslack.api.methods.request.team.TeamAccessLogsRequest;
import com.github.seratch.jslack.api.methods.request.team.TeamBillableInfoRequest;
import com.github.seratch.jslack.api.methods.request.team.TeamInfoRequest;
import com.github.seratch.jslack.api.methods.request.team.TeamIntegrationLogsRequest;
import com.github.seratch.jslack.api.methods.request.team.profile.TeamProfileGetRequest;
import com.github.seratch.jslack.api.methods.request.usergroups.*;
import com.github.seratch.jslack.api.methods.request.usergroups.users.UsergroupUsersListRequest;
import com.github.seratch.jslack.api.methods.request.usergroups.users.UsergroupUsersUpdateRequest;
import com.github.seratch.jslack.api.methods.request.users.*;
import com.github.seratch.jslack.api.methods.request.users.profile.UsersProfileGetRequest;
import com.github.seratch.jslack.api.methods.request.users.profile.UsersProfileSetRequest;
import com.github.seratch.jslack.api.methods.request.views.ViewsOpenRequest;
import com.github.seratch.jslack.api.methods.request.views.ViewsPublishRequest;
import com.github.seratch.jslack.api.methods.request.views.ViewsPushRequest;
import com.github.seratch.jslack.api.methods.request.views.ViewsUpdateRequest;
import com.github.seratch.jslack.api.model.ConversationType;
import com.github.seratch.jslack.common.json.GsonFactory;
import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.joining;

@Slf4j
public class RequestFormBuilder {

    private RequestFormBuilder() {
    }

    public static FormBody.Builder toForm(AdminUsersSessionResetRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("user_id", req.getUserId(), form);
        setIfNotNull("mobile_only", req.isMobileOnly(), form);
        setIfNotNull("web_only", req.isWebOnly(), form);
        return form;
    }

    public static FormBody.Builder toForm(AdminAppsApproveRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("app_id", req.getAppId(), form);
        setIfNotNull("request_id", req.getRequestId(), form);
        setIfNotNull("team_id", req.getTeamId(), form);
        return form;
    }

    public static FormBody.Builder toForm(AdminAppsRestrictRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("app_id", req.getAppId(), form);
        setIfNotNull("request_id", req.getRequestId(), form);
        setIfNotNull("team_id", req.getTeamId(), form);
        return form;
    }

    public static FormBody.Builder toForm(AdminAppsRequestsListRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("cursor", req.getCursor(), form);
        setIfNotNull("limit", req.getLimit(), form);
        setIfNotNull("team_id", req.getTeamId(), form);
        return form;
    }

    public static FormBody.Builder toForm(ApiTestRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("foo", req.getFoo(), form);
        setIfNotNull("error", req.getError(), form);
        return form;
    }

    public static FormBody.Builder toForm(AppsUninstallRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("client_id", req.getClientId(), form);
        setIfNotNull("client_secret", req.getClientSecret(), form);
        return form;
    }

    public static FormBody.Builder toForm(AppsPermissionsRequestRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("trigger_id", req.getTriggerId(), form);
        if (req.getScopes() != null) {
            setIfNotNull("scopes", req.getScopes().stream().collect(joining(",")), form);
        }
        return form;
    }

    public static FormBody.Builder toForm(AppsPermissionsInfoRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        return form;
    }

    public static FormBody.Builder toForm(AppsPermissionsResourcesListRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("cursor", req.getCursor(), form);
        setIfNotNull("limit", req.getLimit(), form);
        return form;
    }

    public static FormBody.Builder toForm(AppsPermissionsScopesListRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        return form;
    }

    public static FormBody.Builder toForm(AppsPermissionsUsersListRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("cursor", req.getCursor(), form);
        setIfNotNull("limit", req.getLimit(), form);
        return form;
    }

    public static FormBody.Builder toForm(AppsPermissionsUsersRequestRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("trigger_id", req.getTriggerId(), form);
        if (req.getScopes() != null) {
            setIfNotNull("scopes", req.getScopes().stream().collect(joining(",")), form);
        }
        setIfNotNull("user", req.getUser(), form);
        return form;
    }

    public static FormBody.Builder toForm(AuthRevokeRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("test", req.isTest(), form);
        return form;
    }

    public static FormBody.Builder toForm(AuthTestRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        return form;
    }

    public static FormBody.Builder toForm(BotsInfoRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("bot", req.getBot(), form);
        return form;
    }

    public static FormBody.Builder toForm(ChannelsArchiveRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        return form;
    }

    public static FormBody.Builder toForm(ChannelsCreateRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("name", req.getName(), form);
        setIfNotNull("validate", req.isValidate(), form);
        return form;
    }

    public static FormBody.Builder toForm(ChannelsHistoryRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("latest", req.getLatest(), form);
        setIfNotNull("oldest", req.getOldest(), form);
        setIfNotNull("inclusive", req.isInclusive(), form);
        setIfNotNull("count", req.getCount(), form);
        setIfNotNull("unreads", req.isUnreads(), form);
        return form;
    }

    public static FormBody.Builder toForm(ChannelsRepliesRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("thread_ts", req.getThreadTs(), form);
        return form;
    }

    public static FormBody.Builder toForm(ChannelsInfoRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("include_locale", req.isIncludeLocale(), form);
        return form;
    }

    public static FormBody.Builder toForm(ChannelsListRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("limit", req.getLimit(), form);
        setIfNotNull("cursor", req.getCursor(), form);
        setIfNotNull("exclude_members", req.isExcludeMembers(), form);
        setIfNotNull("exclude_archived", req.isExcludeArchived(), form);
        return form;
    }

    public static FormBody.Builder toForm(ChannelsInviteRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("user", req.getUser(), form);
        return form;
    }

    public static FormBody.Builder toForm(ChannelsJoinRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("name", req.getName(), form);
        setIfNotNull("validate", req.isValidate(), form);
        return form;
    }

    public static FormBody.Builder toForm(ChannelsKickRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("user", req.getUser(), form);
        return form;
    }

    public static FormBody.Builder toForm(ChannelsLeaveRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        return form;
    }

    public static FormBody.Builder toForm(ChannelsMarkRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("ts", req.getTs(), form);
        return form;
    }

    public static FormBody.Builder toForm(ChannelsRenameRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("name", req.getName(), form);
        setIfNotNull("validate", req.isValidate(), form);
        return form;
    }

    public static FormBody.Builder toForm(ChannelsSetPurposeRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("purpose", req.getPurpose(), form);
        return form;
    }

    public static FormBody.Builder toForm(ChannelsSetTopicRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("topic", req.getTopic(), form);
        return form;
    }

    public static FormBody.Builder toForm(ChannelsUnarchiveRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        return form;
    }

    public static FormBody.Builder toForm(ChatGetPermalinkRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("message_ts", req.getMessageTs(), form);
        return form;
    }

    public static FormBody.Builder toForm(ChatDeleteRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("ts", req.getTs(), form);
        return form;
    }

    public static FormBody.Builder toForm(ChatDeleteScheduledMessageRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("scheduled_message_id", req.getScheduledMessageId(), form);
        return form;
    }

    public static FormBody.Builder toForm(ChatMeMessageRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("text", req.getText(), form);
        return form;
    }

    public static FormBody.Builder toForm(ChatScheduleMessageRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("post_at", req.getPostAt(), form);
        setIfNotNull("text", req.getText(), form);
        setIfNotNull("as_user", req.isAsUser(), form);

        if (req.getBlocksAsString() != null) {
            form.add("blocks", req.getBlocksAsString());
        } else if (req.getBlocks() != null) {
            String json = GsonFactory.createSnakeCase().toJson(req.getBlocks());
            form.add("blocks", json);
        }
        if (req.getBlocksAsString() != null && req.getBlocks() != null) {
            log.warn("Although you set both blocksAsString and blocks, only blocksAsString was used.");
        }

        if (req.getAttachmentsAsString() != null) {
            form.add("attachments", req.getAttachmentsAsString());
        } else if (req.getAttachments() != null) {
            String json = GsonFactory.createSnakeCase().toJson(req.getAttachments());
            form.add("attachments", json);
        }
        setIfNotNull("link_names", req.isLinkNames(), form);
        setIfNotNull("parse", req.getParse(), form);
        setIfNotNull("reply_broadcast", req.isReplyBroadcast(), form);
        setIfNotNull("thread_ts", req.getThreadTs(), form);
        setIfNotNull("unfurl_links", req.isUnfurlLinks(), form);
        setIfNotNull("unfurl_media", req.isUnfurlMedia(), form);
        return form;
    }

    public static FormBody.Builder toForm(ChatScheduleMessagesListRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("cursor", req.getCursor(), form);
        setIfNotNull("latest", req.getLatest(), form);
        setIfNotNull("limit", req.getLimit(), form);
        setIfNotNull("oldest", req.getOldest(), form);
        return form;
    }

    public static FormBody.Builder toForm(ChatPostEphemeralRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("text", req.getText(), form);
        setIfNotNull("user", req.getUser(), form);
        setIfNotNull("as_user", req.isAsUser(), form);

        if (req.getBlocksAsString() != null) {
            form.add("blocks", req.getBlocksAsString());
        } else if (req.getBlocks() != null) {
            String json = GsonFactory.createSnakeCase().toJson(req.getBlocks());
            form.add("blocks", json);
        }
        if (req.getBlocksAsString() != null && req.getBlocks() != null) {
            log.warn("Although you set both blocksAsString and blocks, only blocksAsString was used.");
        }

        if (req.getAttachmentsAsString() != null) {
            form.add("attachments", req.getAttachmentsAsString());
        } else if (req.getAttachments() != null) {
            String json = GsonFactory.createSnakeCase().toJson(req.getAttachments());
            form.add("attachments", json);
        }
        setIfNotNull("link_names", req.isLinkNames(), form);
        setIfNotNull("parse", req.getParse(), form);
        return form;
    }

    public static FormBody.Builder toForm(ChatPostMessageRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("thread_ts", req.getThreadTs(), form);
        setIfNotNull("text", req.getText(), form);
        setIfNotNull("parse", req.getParse(), form);
        setIfNotNull("link_names", req.isLinkNames(), form);
        setIfNotNull("mrkdwn", req.isMrkdwn(), form);

        if (req.getBlocksAsString() != null) {
            form.add("blocks", req.getBlocksAsString());
        } else if (req.getBlocks() != null) {
            String json = GsonFactory.createSnakeCase().toJson(req.getBlocks());
            form.add("blocks", json);
        }
        if (req.getBlocksAsString() != null && req.getBlocks() != null) {
            log.warn("Although you set both blocksAsString and blocks, only blocksAsString was used.");
        }

        if (req.getAttachmentsAsString() != null) {
            form.add("attachments", req.getAttachmentsAsString());
        } else if (req.getAttachments() != null) {
            String json = GsonFactory.createSnakeCase().toJson(req.getAttachments());
            form.add("attachments", json);
        }
        setIfNotNull("unfurl_links", req.isUnfurlLinks(), form);
        setIfNotNull("unfurl_media", req.isUnfurlMedia(), form);
        setIfNotNull("username", req.getUsername(), form);
        setIfNotNull("as_user", req.isAsUser(), form);
        setIfNotNull("icon_url", req.getIconUrl(), form);
        setIfNotNull("icon_emoji", req.getIconEmoji(), form);
        setIfNotNull("thread_ts", req.getThreadTs(), form);
        setIfNotNull("reply_broadcast", req.isReplyBroadcast(), form);
        return form;
    }

    public static FormBody.Builder toForm(ChatUpdateRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("ts", req.getTs(), form);
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("text", req.getText(), form);
        setIfNotNull("parse", req.getParse(), form);
        setIfNotNull("link_names", req.isLinkNames(), form);

        if (req.getBlocksAsString() != null) {
            form.add("blocks", req.getBlocksAsString());
        } else if (req.getBlocks() != null) {
            String json = GsonFactory.createSnakeCase().toJson(req.getBlocks());
            form.add("blocks", json);
        }
        if (req.getBlocksAsString() != null && req.getBlocks() != null) {
            log.warn("Although you set both blocksAsString and blocks, only blocksAsString was used.");
        }

        if (req.getAttachmentsAsString() != null) {
            form.add("attachments", req.getAttachmentsAsString());
        } else if (req.getAttachments() != null) {
            String json = GsonFactory.createSnakeCase().toJson(req.getAttachments());
            form.add("attachments", json);
        }
        setIfNotNull("as_user", req.isAsUser(), form);
        return form;
    }

    public static FormBody.Builder toForm(ChatUnfurlRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("ts", req.getTs(), form);
        setIfNotNull("channel", req.getChannel(), form);
        if (req.getRawUnfurls() != null) {
            setIfNotNull("unfurls", req.getRawUnfurls(), form);
        } else {
            String json = GsonFactory.createSnakeCase().toJson(req.getUnfurls());
            setIfNotNull("unfurls", json, form);
        }
        setIfNotNull("user_auth_required", req.isUserAuthRequired(), form);
        setIfNotNull("user_auth_message", req.getUserAuthMessage(), form);
        setIfNotNull("user_auth_url", req.getUserAuthUrl(), form);
        return form;
    }

    public static FormBody.Builder toForm(ConversationsArchiveRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        return form;
    }

    public static FormBody.Builder toForm(ConversationsCloseRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        return form;
    }

    public static FormBody.Builder toForm(ConversationsCreateRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("name", req.getName(), form);
        setIfNotNull("is_private", req.isPrivate(), form);
        return form;
    }

    public static FormBody.Builder toForm(ConversationsHistoryRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("cursor", req.getCursor(), form);
        setIfNotNull("latest", req.getLatest(), form);
        setIfNotNull("limit", req.getLimit(), form);
        setIfNotNull("oldest", req.getOldest(), form);
        setIfNotNull("inclusive", req.isInclusive(), form);
        return form;
    }

    public static FormBody.Builder toForm(ConversationsInfoRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("include_locale", req.isIncludeLocale(), form);
        setIfNotNull("include_num_members", req.isIncludeNumMembers(), form);
        return form;
    }

    public static FormBody.Builder toForm(ConversationsInviteRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        if (req.getUsers() != null) {
            setIfNotNull("users", req.getUsers().stream().collect(joining(",")), form);
        }
        return form;
    }

    public static FormBody.Builder toForm(ConversationsJoinRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        return form;
    }

    public static FormBody.Builder toForm(ConversationsKickRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("user", req.getUser(), form);
        return form;
    }

    public static FormBody.Builder toForm(ConversationsLeaveRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        return form;
    }

    public static FormBody.Builder toForm(ConversationsListRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("cursor", req.getCursor(), form);
        setIfNotNull("exclude_archived", req.isExcludeArchived(), form);
        setIfNotNull("limit", req.getLimit(), form);

        if (req.getTypes() != null) {
            List<String> typeValues = new ArrayList<>();
            for (ConversationType type : req.getTypes()) {
                typeValues.add(type.value());
            }
            setIfNotNull("types", typeValues.stream().collect(joining(",")), form);
        }
        return form;
    }

    public static FormBody.Builder toForm(ConversationsMembersRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("cursor", req.getCursor(), form);
        setIfNotNull("limit", req.getLimit(), form);
        return form;
    }

    public static FormBody.Builder toForm(ConversationsOpenRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("return_im", req.isReturnIm(), form);
        if (req.getUsers() != null) {
            setIfNotNull("users", req.getUsers().stream().collect(joining(",")), form);
        }
        return form;
    }

    public static FormBody.Builder toForm(ConversationsRenameRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("name", req.getName(), form);
        return form;
    }

    public static FormBody.Builder toForm(ConversationsRepliesRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("inclusive", req.isInclusive(), form);
        setIfNotNull("ts", req.getTs(), form);
        setIfNotNull("cursor", req.getCursor(), form);
        setIfNotNull("limit", req.getLimit(), form);
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("oldest", req.getOldest(), form);
        setIfNotNull("latest", req.getLatest(), form);
        return form;
    }

    public static FormBody.Builder toForm(ConversationsSetPurposeRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("purpose", req.getPurpose(), form);
        return form;
    }

    public static FormBody.Builder toForm(ConversationsSetTopicRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("topic", req.getTopic(), form);
        return form;
    }

    public static FormBody.Builder toForm(ConversationsUnarchiveRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        return form;
    }

    public static FormBody.Builder toForm(DialogOpenRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("trigger_id", req.getTriggerId(), form);
        if (req.getDialogAsString() != null) {
            setIfNotNull("dialog", req.getDialogAsString(), form);
        } else if (req.getDialog() != null) {
            String json = GsonFactory.createSnakeCase().toJson(req.getDialog());
            setIfNotNull("dialog", json, form);
        }
        return form;
    }

    public static FormBody.Builder toForm(DndEndDndRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        return form;
    }

    public static FormBody.Builder toForm(DndEndSnoozeRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        return form;
    }

    public static FormBody.Builder toForm(DndInfoRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("user", req.getUser(), form);
        return form;
    }

    public static FormBody.Builder toForm(DndSetSnoozeRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("num_minutes", req.getNumMinutes(), form);
        return form;
    }

    public static FormBody.Builder toForm(DndTeamInfoRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        if (req.getUsers() != null) {
            setIfNotNull("users", req.getUsers().stream().collect(joining(",")), form);
        }
        return form;
    }

    public static FormBody.Builder toForm(EmojiListRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        return form;
    }

    public static FormBody.Builder toForm(FilesDeleteRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("file", req.getFile(), form);
        return form;
    }

    public static FormBody.Builder toForm(FilesInfoRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("file", req.getFile(), form);
        setIfNotNull("count", req.getCount(), form);
        setIfNotNull("page", req.getPage(), form);
        return form;
    }

    public static FormBody.Builder toForm(FilesListRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("user", req.getUser(), form);
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("ts_from", req.getTsFrom(), form);
        setIfNotNull("ts_to", req.getTsTo(), form);
        if (req.getTypes() != null) {
            setIfNotNull("types", req.getTypes().stream().collect(joining(",")), form);
        }
        setIfNotNull("count", req.getCount(), form);
        setIfNotNull("page", req.getPage(), form);
        setIfNotNull("show_files_hidden_by_limit", req.isShowFilesHiddenByLimit(), form);
        return form;
    }

    public static FormBody.Builder toForm(FilesRevokePublicURLRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("file", req.getFile(), form);
        return form;
    }

    public static FormBody.Builder toForm(FilesSharedPublicURLRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("file", req.getFile(), form);
        return form;
    }

    public static FormBody.Builder toForm(FilesUploadRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("content", req.getContent(), form);
        setIfNotNull("filetype", req.getFiletype(), form);
        setIfNotNull("filename", req.getFilename(), form);
        setIfNotNull("title", req.getTitle(), form);
        setIfNotNull("initial_comment", req.getInitialComment(), form);
        if (req.getChannels() != null) {
            setIfNotNull("channels", req.getChannels().stream().collect(joining(",")), form);
        }
        return form;
    }

    public static MultipartBody.Builder toMultipartBody(FilesUploadRequest req) {
        MultipartBody.Builder form = new MultipartBody.Builder();

        if (req.getFileData() != null) {
            RequestBody file = RequestBody.create(MultipartBody.FORM, req.getFileData());
            form.addFormDataPart("file", req.getFilename(), file);
        } else if (req.getFile() != null) {
            RequestBody file = RequestBody.create(MultipartBody.FORM, req.getFile());
            form.addFormDataPart("file", req.getFilename(), file);
        }

        setIfNotNull("filetype", req.getFiletype(), form);
        setIfNotNull("filename", req.getFilename(), form);
        setIfNotNull("title", req.getTitle(), form);
        setIfNotNull("initial_comment", req.getInitialComment(), form);
        if (req.getChannels() != null) {
            setIfNotNull("channels", req.getChannels().stream().collect(joining(",")), form);
        }
        setIfNotNull("thread_ts", req.getThreadTs(), form);
        return form;
    }

    public static FormBody.Builder toForm(FilesCommentsAddRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("file", req.getFile(), form);
        setIfNotNull("comment", req.getComment(), form);
        return form;
    }

    public static FormBody.Builder toForm(FilesCommentsDeleteRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("file", req.getFile(), form);
        setIfNotNull("id", req.getId(), form);
        return form;
    }

    public static FormBody.Builder toForm(FilesCommentsEditRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("file", req.getFile(), form);
        setIfNotNull("comment", req.getComment(), form);
        setIfNotNull("id", req.getId(), form);
        return form;
    }

    public static MultipartBody.Builder toMultipartBody(FilesRemoteAddRequest req) {
        MultipartBody.Builder form = new MultipartBody.Builder();
        setIfNotNull("external_id", req.getExternalId(), form);
        setIfNotNull("external_url", req.getExternalUrl(), form);
        setIfNotNull("title", req.getTitle(), form);
        setIfNotNull("filetype", req.getFiletype(), form);
        if (req.getIndexableFileContents() != null) {
            RequestBody indexableFileContents = RequestBody.create(req.getFiletype() != null ? MediaType.parse(req.getFiletype()) : null, req.getIndexableFileContents());
            form.addFormDataPart("indexable_file_contents", req.getTitle(), indexableFileContents);
        }
        if (req.getPreviewImage() != null) {
            RequestBody previewImage = RequestBody.create(req.getFiletype() != null ? MediaType.parse(req.getFiletype()) : null, req.getPreviewImage());
            form.addFormDataPart("preview_image", req.getTitle(), previewImage);
        }
        return form;
    }

    public static FormBody.Builder toForm(FilesRemoteInfoRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("external_id", req.getExternalId(), form);
        setIfNotNull("file", req.getFile(), form);
        return form;
    }

    public static FormBody.Builder toForm(FilesRemoteListRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("cursor", req.getCursor(), form);
        setIfNotNull("limit", req.getLimit(), form);
        setIfNotNull("ts_from", req.getTsFrom(), form);
        setIfNotNull("ts_to", req.getTsTo(), form);
        return form;
    }

    public static FormBody.Builder toForm(FilesRemoteRemoveRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("external_id", req.getExternalId(), form);
        setIfNotNull("file", req.getFile(), form);
        return form;
    }

    public static FormBody.Builder toForm(FilesRemoteShareRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("external_id", req.getExternalId(), form);
        setIfNotNull("file", req.getFile(), form);
        if (req.getChannels() != null) {
            setIfNotNull("channels", req.getChannels().stream().collect(joining(",")), form);
        } else {
            throw new IllegalArgumentException("channels parameter is required for files.remote.share API");
        }
        return form;
    }

    public static MultipartBody.Builder toMultipartBody(FilesRemoteUpdateRequest req) {
        MultipartBody.Builder form = new MultipartBody.Builder();
        setIfNotNull("external_id", req.getExternalId(), form);
        setIfNotNull("external_url", req.getExternalUrl(), form);
        setIfNotNull("title", req.getTitle(), form);
        setIfNotNull("filetype", req.getFiletype(), form);
        if (req.getIndexableFileContents() != null) {
            RequestBody indexableFileContents = RequestBody.create(req.getFiletype() != null ? MediaType.parse(req.getFiletype()) : null, req.getIndexableFileContents());
            form.addFormDataPart("indexable_file_contents", null, indexableFileContents);
        }
        if (req.getPreviewImage() != null) {
            RequestBody previewImage = RequestBody.create(req.getFiletype() != null ? MediaType.parse(req.getFiletype()) : null, req.getPreviewImage());
            form.addFormDataPart("preview_image", null, previewImage);
        }
        return form;
    }

    public static FormBody.Builder toForm(GroupsArchiveRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        return form;
    }

    public static FormBody.Builder toForm(GroupsCloseRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        return form;
    }

    public static FormBody.Builder toForm(GroupsCreateChildRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        return form;
    }

    public static FormBody.Builder toForm(GroupsCreateRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("name", req.getName(), form);
        setIfNotNull("validate", req.isValidate(), form);
        return form;
    }

    public static FormBody.Builder toForm(GroupsHistoryRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("latest", req.getLatest(), form);
        setIfNotNull("oldest", req.getOldest(), form);
        setIfNotNull("inclusive", req.isInclusive(), form);
        setIfNotNull("count", req.getCount(), form);
        setIfNotNull("unreads", req.isUnreads(), form);
        return form;
    }

    public static FormBody.Builder toForm(GroupsRepliesRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("thread_ts", req.getThreadTs(), form);
        return form;
    }

    public static FormBody.Builder toForm(GroupsInfoRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("include_locale", req.isIncludeLocale(), form);
        return form;
    }

    public static FormBody.Builder toForm(GroupsInviteRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("user", req.getUser(), form);
        return form;
    }

    public static FormBody.Builder toForm(GroupsKickRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("user", req.getUser(), form);
        return form;
    }

    public static FormBody.Builder toForm(GroupsLeaveRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        return form;
    }

    public static FormBody.Builder toForm(GroupsListRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("exclude_archived", req.isExcludeArchived(), form);
        setIfNotNull("exclude_members", req.isExcludeMembers(), form);
        return form;
    }

    public static FormBody.Builder toForm(GroupsMarkRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("ts", req.getTs(), form);
        return form;
    }

    public static FormBody.Builder toForm(GroupsOpenRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        return form;
    }

    public static FormBody.Builder toForm(GroupsRenameRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("name", req.getName(), form);
        return form;
    }

    public static FormBody.Builder toForm(GroupsSetPurposeRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("purpose", req.getPurpose(), form);
        return form;
    }

    public static FormBody.Builder toForm(GroupsSetTopicRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("topic", req.getTopic(), form);
        return form;
    }

    public static FormBody.Builder toForm(GroupsUnarchiveRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        return form;
    }

    public static FormBody.Builder toForm(ImCloseRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        return form;
    }

    public static FormBody.Builder toForm(ImHistoryRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("latest", req.getLatest(), form);
        setIfNotNull("oldest", req.getOldest(), form);
        setIfNotNull("inclusive", req.isInclusive(), form);
        setIfNotNull("count", req.getCount(), form);
        setIfNotNull("unreads", req.isUnreads(), form);
        return form;
    }

    public static FormBody.Builder toForm(ImListRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("cursor", req.getCursor(), form);
        setIfNotNull("limit", req.getLimit(), form);
        return form;
    }

    public static FormBody.Builder toForm(ImMarkRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("ts", req.getTs(), form);
        return form;
    }

    public static FormBody.Builder toForm(ImOpenRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("user", req.getUser(), form);
        setIfNotNull("return_im", req.isReturnIm(), form);
        setIfNotNull("include_locale", req.isIncludeLocale(), form);
        return form;
    }

    public static FormBody.Builder toForm(ImRepliesRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("thread_ts", req.getThreadTs(), form);
        return form;
    }

    public static FormBody.Builder toForm(MigrationExchangeRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("to_old", req.isToOld(), form);
        if (req.getUsers() != null) {
            setIfNotNull("users", req.getUsers().stream().collect(joining(",")), form);
        }
        return form;
    }

    public static FormBody.Builder toForm(MpimCloseRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        return form;
    }

    public static FormBody.Builder toForm(MpimHistoryRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("latest", req.getLatest(), form);
        setIfNotNull("oldest", req.getOldest(), form);
        setIfNotNull("inclusive", req.isInclusive(), form);
        setIfNotNull("count", req.getCount(), form);
        setIfNotNull("unreads", req.isUnreads(), form);
        return form;
    }

    public static FormBody.Builder toForm(MpimListRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        return form;
    }

    public static FormBody.Builder toForm(MpimRepliesRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("thread_ts", req.getThreadTs(), form);
        return form;
    }

    public static FormBody.Builder toForm(MpimMarkRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("ts", req.getTs(), form);
        return form;
    }

    public static FormBody.Builder toForm(MpimOpenRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        if (req.getUsers() != null) {
            setIfNotNull("users", req.getUsers().stream().collect(joining(",")), form);
        }
        return form;
    }

    public static FormBody.Builder toForm(OAuthAccessRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("client_id", req.getClientId(), form);
        setIfNotNull("client_secret", req.getClientSecret(), form);
        setIfNotNull("code", req.getCode(), form);
        setIfNotNull("redirect_uri", req.getRedirectUri(), form);
        setIfNotNull("single_channel", req.isSingleChannel(), form);
        return form;
    }

    public static FormBody.Builder toForm(OAuthTokenRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("client_id", req.getClientId(), form);
        setIfNotNull("client_secret", req.getClientSecret(), form);
        setIfNotNull("code", req.getCode(), form);
        setIfNotNull("redirect_uri", req.getRedirectUri(), form);
        setIfNotNull("single_channel", req.isSingleChannel(), form);
        return form;
    }

    public static FormBody.Builder toForm(PinsAddRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("file", req.getFile(), form);
        setIfNotNull("file_comment", req.getFileComment(), form);
        setIfNotNull("timestamp", req.getTimestamp(), form);
        return form;
    }

    public static FormBody.Builder toForm(PinsListRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        return form;
    }

    public static FormBody.Builder toForm(PinsRemoveRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("file", req.getFile(), form);
        setIfNotNull("file_comment", req.getFileComment(), form);
        setIfNotNull("timestamp", req.getTimestamp(), form);
        return form;
    }

    public static FormBody.Builder toForm(ReactionsAddRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("name", req.getName(), form);
        setIfNotNull("file", req.getFile(), form);
        setIfNotNull("file_comment", req.getFileComment(), form);
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("timestamp", req.getTimestamp(), form);
        return form;
    }

    public static FormBody.Builder toForm(ReactionsGetRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("file", req.getFile(), form);
        setIfNotNull("file_comment", req.getFileComment(), form);
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("timestamp", req.getTimestamp(), form);
        setIfNotNull("full", req.isFull(), form);
        return form;
    }

    public static FormBody.Builder toForm(ReactionsListRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("user", req.getUser(), form);
        setIfNotNull("full", req.isFull(), form);
        setIfNotNull("count", req.getCount(), form);
        setIfNotNull("page", req.getPage(), form);
        return form;
    }

    public static FormBody.Builder toForm(ReactionsRemoveRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("name", req.getName(), form);
        setIfNotNull("file", req.getFile(), form);
        setIfNotNull("file_comment", req.getFileComment(), form);
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("timestamp", req.getTimestamp(), form);
        return form;
    }

    public static FormBody.Builder toForm(RemindersAddRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("text", req.getText(), form);
        setIfNotNull("time", req.getTime(), form);
        setIfNotNull("user", req.getUser(), form);
        return form;
    }

    public static FormBody.Builder toForm(RemindersCompleteRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("reminder", req.getReminder(), form);
        return form;
    }

    public static FormBody.Builder toForm(RemindersDeleteRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("reminder", req.getReminder(), form);
        return form;
    }

    public static FormBody.Builder toForm(RemindersInfoRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("reminder", req.getReminder(), form);
        return form;
    }

    public static FormBody.Builder toForm(RemindersListRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        return form;
    }

    public static FormBody.Builder toForm(RTMConnectRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("batch_presence_aware", req.isBatchPresenceAware(), form);
        setIfNotNull("presence_sub", req.isPresenceSub(), form);
        return form;
    }

    public static FormBody.Builder toForm(RTMStartRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("include_locale", req.isIncludeLocale(), form);
        setIfNotNull("batch_presence_aware", req.isBatchPresenceAware(), form);
        setIfNotNull("no_latest", req.isNoLatest(), form);
        setIfNotNull("no_unreads", req.isNoUnreads(), form);
        setIfNotNull("presence_sub", req.isPresenceSub(), form);
        setIfNotNull("simple_latest", req.isSimpleLatest(), form);
        setIfNotNull("mpim_aware", req.isMpimAware(), form);
        return form;
    }

    public static FormBody.Builder toForm(SearchAllRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("query", req.getQuery(), form);
        setIfNotNull("sort", req.getSort(), form);
        setIfNotNull("sort_dir", req.getSortDir(), form);
        setIfNotNull("highlight", req.isHighlight(), form);
        setIfNotNull("count", req.getCount(), form);
        setIfNotNull("page", req.getPage(), form);
        return form;
    }

    public static FormBody.Builder toForm(SearchMessagesRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("query", req.getQuery(), form);
        setIfNotNull("sort", req.getSort(), form);
        setIfNotNull("sort_dir", req.getSortDir(), form);
        setIfNotNull("highlight", req.isHighlight(), form);
        setIfNotNull("count", req.getCount(), form);
        setIfNotNull("page", req.getPage(), form);
        return form;
    }

    public static FormBody.Builder toForm(SearchFilesRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("query", req.getQuery(), form);
        setIfNotNull("sort", req.getSort(), form);
        setIfNotNull("sort_dir", req.getSortDir(), form);
        setIfNotNull("highlight", req.isHighlight(), form);
        setIfNotNull("count", req.getCount(), form);
        setIfNotNull("page", req.getPage(), form);
        return form;
    }

    public static FormBody.Builder toForm(StarsAddRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("file", req.getFile(), form);
        setIfNotNull("file_comment", req.getFileComment(), form);
        setIfNotNull("timestamp", req.getTimestamp(), form);
        return form;
    }

    public static FormBody.Builder toForm(StarsListRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("count", req.getCount(), form);
        setIfNotNull("page", req.getPage(), form);
        return form;
    }

    public static FormBody.Builder toForm(StarsRemoveRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("file", req.getFile(), form);
        setIfNotNull("file_comment", req.getFileComment(), form);
        setIfNotNull("timestamp", req.getTimestamp(), form);
        return form;
    }

    public static FormBody.Builder toForm(TeamAccessLogsRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("before", req.getBefore(), form);
        setIfNotNull("count", req.getCount(), form);
        setIfNotNull("page", req.getPage(), form);
        return form;
    }

    public static FormBody.Builder toForm(TeamBillableInfoRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("user", req.getUser(), form);
        return form;
    }

    public static FormBody.Builder toForm(TeamInfoRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        return form;
    }

    public static FormBody.Builder toForm(TeamIntegrationLogsRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("service_id", req.getServiceId(), form);
        setIfNotNull("user", req.getUser(), form);
        setIfNotNull("change_type", req.getChangeType(), form);
        setIfNotNull("count", req.getCount(), form);
        setIfNotNull("page", req.getPage(), form);
        return form;
    }

    public static FormBody.Builder toForm(TeamProfileGetRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("visibility", req.getVisibility(), form);
        return form;
    }

    public static FormBody.Builder toForm(UsergroupsCreateRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("name", req.getName(), form);
        setIfNotNull("handle", req.getHandle(), form);
        setIfNotNull("description", req.getDescription(), form);
        if (req.getChannels() != null) {
            setIfNotNull("channels", req.getChannels().stream().collect(joining(",")), form);
        }
        setIfNotNull("include_count", req.isIncludeCount(), form);
        return form;
    }

    public static FormBody.Builder toForm(UsergroupsDisableRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("usergroup", req.getUsergroup(), form);
        setIfNotNull("include_count", req.isIncludeCount(), form);
        return form;
    }

    public static FormBody.Builder toForm(UsergroupsEnableRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("usergroup", req.getUsergroup(), form);
        setIfNotNull("include_count", req.isIncludeCount(), form);
        return form;
    }

    public static FormBody.Builder toForm(UsergroupsListRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("include_disabled", req.isIncludeDisabled(), form);
        setIfNotNull("include_count", req.isIncludeCount(), form);
        setIfNotNull("include_users", req.isIncludeUsers(), form);
        return form;
    }

    public static FormBody.Builder toForm(UsergroupsUpdateRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("usergroup", req.getUsergroup(), form);
        setIfNotNull("name", req.getName(), form);
        setIfNotNull("handle", req.getHandle(), form);
        setIfNotNull("description", req.getDescription(), form);
        if (req.getChannels() != null) {
            setIfNotNull("channels", req.getChannels().stream().collect(joining(",")), form);
        }
        setIfNotNull("include_count", req.isIncludeCount(), form);
        return form;
    }

    public static FormBody.Builder toForm(UsergroupUsersListRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("usergroup", req.getUsergroup(), form);
        setIfNotNull("include_disabled", req.isIncludeDisabled(), form);
        return form;
    }

    public static FormBody.Builder toForm(UsergroupUsersUpdateRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("usergroup", req.getUsergroup(), form);
        if (req.getUsers() != null) {
            setIfNotNull("users", req.getUsers().stream().collect(joining(",")), form);
        }
        setIfNotNull("include_count", req.isIncludeCount(), form);
        return form;
    }

    public static FormBody.Builder toForm(UsersConversationsRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("user", req.getUser(), form);
        setIfNotNull("cursor", req.getCursor(), form);
        setIfNotNull("exclude_archived", req.isExcludeArchived(), form);
        setIfNotNull("limit", req.getLimit(), form);

        if (req.getTypes() != null) {
            List<String> typeValues = new ArrayList<>();
            for (ConversationType type : req.getTypes()) {
                typeValues.add(type.value());
            }
            setIfNotNull("types", typeValues.stream().collect(joining(",")), form);
        }
        return form;
    }

    public static FormBody.Builder toForm(UsersDeletePhotoRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        return form;
    }

    public static FormBody.Builder toForm(UsersGetPresenceRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("user", req.getUser(), form);
        return form;
    }

    public static FormBody.Builder toForm(UsersIdentityRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        return form;
    }

    public static FormBody.Builder toForm(UsersInfoRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("user", req.getUser(), form);
        setIfNotNull("include_locale", req.isIncludeLocale(), form);
        return form;
    }

    public static FormBody.Builder toForm(UsersListRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("cursor", req.getCursor(), form);
        setIfNotNull("limit", req.getLimit(), form);
        setIfNotNull("include_locale", req.isIncludeLocale(), form);
        setIfNotNull("presence", req.isPresence(), form);
        return form;
    }

    public static FormBody.Builder toForm(UsersLookupByEmailRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("email", req.getEmail(), form);
        return form;
    }

    public static FormBody.Builder toForm(UsersSetActiveRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        return form;
    }

    public static MultipartBody.Builder toMultipartBody(UsersSetPhotoRequest req) {
        MultipartBody.Builder form = new MultipartBody.Builder();
        if (req.getImageData() != null) {
            RequestBody image = RequestBody.create(MediaType.parse("imageData/*"), req.getImageData());
            form.addFormDataPart("image", "image", image);
        } else if (req.getImage() != null) {
            RequestBody image = RequestBody.create(MediaType.parse("imageData/*"), req.getImage());
            form.addFormDataPart("image", "image", image);
        }
        setIfNotNull("crop_x", req.getCropX(), form);
        setIfNotNull("crop_y", req.getCropY(), form);
        setIfNotNull("crop_w", req.getCropW(), form);
        return form;
    }

    public static FormBody.Builder toForm(UsersSetPresenceRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("presence", req.getPresence(), form);
        return form;
    }

    public static FormBody.Builder toForm(UsersProfileGetRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("user", req.getUser(), form);
        setIfNotNull("include_labels", req.isIncludeLabels(), form);
        return form;
    }

    public static FormBody.Builder toForm(UsersProfileSetRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("user", req.getUser(), form);
        if (req.getProfile() != null) {
            setIfNotNull("profile", GsonFactory.createSnakeCase().toJson(req.getProfile()), form);
        } else {
            setIfNotNull("name", req.getName(), form);
            setIfNotNull("value", req.getValue(), form);
        }
        return form;
    }

    public static FormBody.Builder toForm(ViewsOpenRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("trigger_id", req.getTriggerId(), form);
        if (req.getViewAsString() != null) {
            setIfNotNull("view", req.getViewAsString(), form);
        } else {
            setIfNotNull("view", GsonFactory.createSnakeCase().toJson(req.getView()), form);
        }
        return form;
    }

    public static FormBody.Builder toForm(ViewsPushRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("trigger_id", req.getTriggerId(), form);
        if (req.getViewAsString() != null) {
            setIfNotNull("view", req.getViewAsString(), form);
        } else {
            setIfNotNull("view", GsonFactory.createSnakeCase().toJson(req.getView()), form);
        }
        return form;
    }

    public static FormBody.Builder toForm(ViewsUpdateRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        if (req.getViewAsString() != null) {
            setIfNotNull("view", req.getViewAsString(), form);
        } else {
            setIfNotNull("view", GsonFactory.createSnakeCase().toJson(req.getView()), form);
        }
        setIfNotNull("external_id", req.getExternalId(), form);
        setIfNotNull("hash", req.getHash(), form);
        setIfNotNull("view_id", req.getViewId(), form);
        return form;
    }

    public static FormBody.Builder toForm(ViewsPublishRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("user_id", req.getUserId(), form);
        if (req.getViewAsString() != null) {
            setIfNotNull("view", req.getViewAsString(), form);
        } else {
            setIfNotNull("view", GsonFactory.createSnakeCase().toJson(req.getView()), form);
        }
        setIfNotNull("hash", req.getHash(), form);
        return form;
    }

    // ----------------------------------------------------------------------------------
    // internal methods
    // ----------------------------------------------------------------------------------

    private static void setIfNotNull(String name, Object value, FormBody.Builder form) {
        if (value != null) {
            if (value instanceof Boolean) {
                String numValue = ((Boolean) value) ? "1" : "0";
                form.add(name, numValue);
            } else {
                form.add(name, String.valueOf(value));
            }
        }
    }

    private static void setIfNotNull(String name, Object value, MultipartBody.Builder form) {
        if (value != null) {
            if (value instanceof Boolean) {
                String numValue = ((Boolean) value) ? "1" : "0";
                form.addFormDataPart(name, numValue);
            } else {
                form.addFormDataPart(name, String.valueOf(value));
            }
        }
    }

}
