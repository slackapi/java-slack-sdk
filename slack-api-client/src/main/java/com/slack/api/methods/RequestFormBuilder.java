package com.slack.api.methods;

import com.google.gson.Gson;
import com.slack.api.methods.request.admin.analytics.AdminAnalyticsGetFileRequest;
import com.slack.api.methods.request.admin.apps.*;
import com.slack.api.methods.request.admin.auth.policy.AdminAuthPolicyAssignEntitiesRequest;
import com.slack.api.methods.request.admin.auth.policy.AdminAuthPolicyGetEntitiesRequest;
import com.slack.api.methods.request.admin.auth.policy.AdminAuthPolicyRemoveEntitiesRequest;
import com.slack.api.methods.request.admin.barriers.AdminBarriersCreateRequest;
import com.slack.api.methods.request.admin.barriers.AdminBarriersDeleteRequest;
import com.slack.api.methods.request.admin.barriers.AdminBarriersListRequest;
import com.slack.api.methods.request.admin.barriers.AdminBarriersUpdateRequest;
import com.slack.api.methods.request.admin.conversations.*;
import com.slack.api.methods.request.admin.conversations.ekm.AdminConversationsEkmListOriginalConnectedChannelInfoRequest;
import com.slack.api.methods.request.admin.conversations.restrict_access.AdminConversationsRestrictAccessAddGroupRequest;
import com.slack.api.methods.request.admin.conversations.restrict_access.AdminConversationsRestrictAccessListGroupsRequest;
import com.slack.api.methods.request.admin.conversations.restrict_access.AdminConversationsRestrictAccessRemoveGroupRequest;
import com.slack.api.methods.request.admin.conversations.whitelist.AdminConversationsWhitelistAddRequest;
import com.slack.api.methods.request.admin.conversations.whitelist.AdminConversationsWhitelistListGroupsLinkedToChannelRequest;
import com.slack.api.methods.request.admin.conversations.whitelist.AdminConversationsWhitelistRemoveRequest;
import com.slack.api.methods.request.admin.emoji.*;
import com.slack.api.methods.request.admin.invite_requests.*;
import com.slack.api.methods.request.admin.teams.AdminTeamsAdminsListRequest;
import com.slack.api.methods.request.admin.teams.AdminTeamsCreateRequest;
import com.slack.api.methods.request.admin.teams.AdminTeamsListRequest;
import com.slack.api.methods.request.admin.teams.owners.AdminTeamsOwnersListRequest;
import com.slack.api.methods.request.admin.teams.settings.*;
import com.slack.api.methods.request.admin.usergroups.AdminUsergroupsAddChannelsRequest;
import com.slack.api.methods.request.admin.usergroups.AdminUsergroupsAddTeamsRequest;
import com.slack.api.methods.request.admin.usergroups.AdminUsergroupsListChannelsRequest;
import com.slack.api.methods.request.admin.usergroups.AdminUsergroupsRemoveChannelsRequest;
import com.slack.api.methods.request.admin.users.*;
import com.slack.api.methods.request.api.ApiTestRequest;
import com.slack.api.methods.request.apps.AppsUninstallRequest;
import com.slack.api.methods.request.apps.connections.AppsConnectionsOpenRequest;
import com.slack.api.methods.request.apps.event.authorizations.AppsEventAuthorizationsListRequest;
import com.slack.api.methods.request.apps.permissions.AppsPermissionsInfoRequest;
import com.slack.api.methods.request.apps.permissions.AppsPermissionsRequestRequest;
import com.slack.api.methods.request.apps.permissions.resources.AppsPermissionsResourcesListRequest;
import com.slack.api.methods.request.apps.permissions.scopes.AppsPermissionsScopesListRequest;
import com.slack.api.methods.request.apps.permissions.users.AppsPermissionsUsersListRequest;
import com.slack.api.methods.request.apps.permissions.users.AppsPermissionsUsersRequestRequest;
import com.slack.api.methods.request.auth.AuthRevokeRequest;
import com.slack.api.methods.request.auth.AuthTestRequest;
import com.slack.api.methods.request.auth.teams.AuthTeamsListRequest;
import com.slack.api.methods.request.bots.BotsInfoRequest;
import com.slack.api.methods.request.calls.CallsAddRequest;
import com.slack.api.methods.request.calls.CallsEndRequest;
import com.slack.api.methods.request.calls.CallsInfoRequest;
import com.slack.api.methods.request.calls.CallsUpdateRequest;
import com.slack.api.methods.request.calls.participants.CallsParticipantsAddRequest;
import com.slack.api.methods.request.calls.participants.CallsParticipantsRemoveRequest;
import com.slack.api.methods.request.channels.*;
import com.slack.api.methods.request.chat.*;
import com.slack.api.methods.request.chat.scheduled_messages.ChatScheduledMessagesListRequest;
import com.slack.api.methods.request.conversations.*;
import com.slack.api.methods.request.dialog.DialogOpenRequest;
import com.slack.api.methods.request.dnd.*;
import com.slack.api.methods.request.emoji.EmojiListRequest;
import com.slack.api.methods.request.files.*;
import com.slack.api.methods.request.files.comments.FilesCommentsAddRequest;
import com.slack.api.methods.request.files.comments.FilesCommentsDeleteRequest;
import com.slack.api.methods.request.files.comments.FilesCommentsEditRequest;
import com.slack.api.methods.request.files.remote.*;
import com.slack.api.methods.request.groups.*;
import com.slack.api.methods.request.im.*;
import com.slack.api.methods.request.migration.MigrationExchangeRequest;
import com.slack.api.methods.request.mpim.*;
import com.slack.api.methods.request.oauth.OAuthAccessRequest;
import com.slack.api.methods.request.oauth.OAuthTokenRequest;
import com.slack.api.methods.request.oauth.OAuthV2ExchangeRequest;
import com.slack.api.methods.request.openid.connect.OpenIDConnectUserInfoRequest;
import com.slack.api.methods.request.pins.PinsAddRequest;
import com.slack.api.methods.request.pins.PinsListRequest;
import com.slack.api.methods.request.pins.PinsRemoveRequest;
import com.slack.api.methods.request.reactions.ReactionsAddRequest;
import com.slack.api.methods.request.reactions.ReactionsGetRequest;
import com.slack.api.methods.request.reactions.ReactionsListRequest;
import com.slack.api.methods.request.reactions.ReactionsRemoveRequest;
import com.slack.api.methods.request.reminders.*;
import com.slack.api.methods.request.rtm.RTMConnectRequest;
import com.slack.api.methods.request.rtm.RTMStartRequest;
import com.slack.api.methods.request.search.SearchAllRequest;
import com.slack.api.methods.request.search.SearchFilesRequest;
import com.slack.api.methods.request.search.SearchMessagesRequest;
import com.slack.api.methods.request.stars.StarsAddRequest;
import com.slack.api.methods.request.stars.StarsListRequest;
import com.slack.api.methods.request.stars.StarsRemoveRequest;
import com.slack.api.methods.request.team.TeamAccessLogsRequest;
import com.slack.api.methods.request.team.TeamBillableInfoRequest;
import com.slack.api.methods.request.team.TeamInfoRequest;
import com.slack.api.methods.request.team.TeamIntegrationLogsRequest;
import com.slack.api.methods.request.team.profile.TeamProfileGetRequest;
import com.slack.api.methods.request.usergroups.*;
import com.slack.api.methods.request.usergroups.users.UsergroupsUsersListRequest;
import com.slack.api.methods.request.usergroups.users.UsergroupsUsersUpdateRequest;
import com.slack.api.methods.request.users.*;
import com.slack.api.methods.request.users.profile.UsersProfileGetRequest;
import com.slack.api.methods.request.users.profile.UsersProfileSetRequest;
import com.slack.api.methods.request.views.ViewsOpenRequest;
import com.slack.api.methods.request.views.ViewsPublishRequest;
import com.slack.api.methods.request.views.ViewsPushRequest;
import com.slack.api.methods.request.views.ViewsUpdateRequest;
import com.slack.api.methods.request.workflows.WorkflowsStepCompletedRequest;
import com.slack.api.methods.request.workflows.WorkflowsStepFailedRequest;
import com.slack.api.methods.request.workflows.WorkflowsUpdateStepRequest;
import com.slack.api.methods.response.openid.connect.OpenIDConnectUserInfoResponse;
import com.slack.api.model.Attachment;
import com.slack.api.model.ConversationType;
import com.slack.api.util.json.GsonFactory;
import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.joining;

@Slf4j
public class RequestFormBuilder {

    private static final Gson GSON = GsonFactory.createSnakeCase();

    private RequestFormBuilder() {
    }

    public static FormBody.Builder toForm(AdminAnalyticsGetFileRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("date", req.getDate(), form);
        setIfNotNull("type", req.getType(), form);
        setIfNotNull("metadata_only", req.getMetadataOnly(), form);
        return form;
    }

    public static FormBody.Builder toForm(AdminUsersSessionResetRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("user_id", req.getUserId(), form);
        setIfNotNull("mobile_only", req.isMobileOnly(), form);
        setIfNotNull("web_only", req.isWebOnly(), form);
        return form;
    }

    public static FormBody.Builder toForm(AdminUsersSessionInvalidateRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("session_id", req.getSessionId(), form);
        setIfNotNull("team_id", req.getTeamId(), form);
        return form;
    }

    public static FormBody.Builder toForm(AdminUsersSessionListRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("cursor", req.getCursor(), form);
        setIfNotNull("limit", req.getLimit(), form);
        setIfNotNull("team_id", req.getTeamId(), form);
        setIfNotNull("user_id", req.getUserId(), form);
        return form;
    }

    public static FormBody.Builder toForm(AdminUsersSessionGetSettingsRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        if (req.getUserIds() != null) {
            setIfNotNull("user_ids", req.getUserIds().stream().collect(joining(",")), form);
        }
        return form;
    }

    public static FormBody.Builder toForm(AdminUsersSessionSetSettingsRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        if (req.getUserIds() != null) {
            setIfNotNull("user_ids", req.getUserIds().stream().collect(joining(",")), form);
        }
        setIfNotNull("desktop_app_browser_quit", req.getDesktopAppBrowserQuit(), form);
        setIfNotNull("duration", req.getDuration(), form);
        return form;
    }

    public static FormBody.Builder toForm(AdminUsersSessionClearSettingsRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        if (req.getUserIds() != null) {
            setIfNotNull("user_ids", req.getUserIds().stream().collect(joining(",")), form);
        }
        return form;
    }

    public static FormBody.Builder toForm(AdminAppsApproveRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("app_id", req.getAppId(), form);
        setIfNotNull("request_id", req.getRequestId(), form);
        setIfNotNull("team_id", req.getTeamId(), form);
        return form;
    }

    public static FormBody.Builder toForm(AdminAppsApprovedListRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("cursor", req.getCursor(), form);
        setIfNotNull("limit", req.getLimit(), form);
        setIfNotNull("enterprise_id", req.getEnterpriseId(), form);
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

    public static FormBody.Builder toForm(AdminAppsRestrictedListRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("cursor", req.getCursor(), form);
        setIfNotNull("limit", req.getLimit(), form);
        setIfNotNull("enterprise_id", req.getEnterpriseId(), form);
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

    public static FormBody.Builder toForm(AdminAppsClearResolutionRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("app_id", req.getAppId(), form);
        setIfNotNull("enterprise_id", req.getEnterpriseId(), form);
        setIfNotNull("team_id", req.getTeamId(), form);
        return form;
    }

    public static FormBody.Builder toForm(AdminAppsUninstallRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("app_id", req.getAppId(), form);
        setIfNotNull("enterprise_id", req.getEnterpriseId(), form);
        if (req.getTeamIds() != null) {
            setIfNotNull("team_ids", req.getTeamIds().stream().collect(joining(",")), form);
        }
        return form;
    }

    public static FormBody.Builder toForm(AdminAuthPolicyAssignEntitiesRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("entity_type", req.getEntityType(), form);
        setIfNotNull("policy_name", req.getPolicyName(), form);
        if (req.getEntityIds() != null) {
            setIfNotNull("entity_ids", req.getEntityIds().stream().collect(joining(",")), form);
        }
        return form;
    }

    public static FormBody.Builder toForm(AdminAuthPolicyGetEntitiesRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("entity_type", req.getEntityType(), form);
        setIfNotNull("policy_name", req.getPolicyName(), form);
        setIfNotNull("cursor", req.getCursor(), form);
        setIfNotNull("limit", req.getLimit(), form);
        return form;
    }

    public static FormBody.Builder toForm(AdminAuthPolicyRemoveEntitiesRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("entity_type", req.getEntityType(), form);
        setIfNotNull("policy_name", req.getPolicyName(), form);
        if (req.getEntityIds() != null) {
            setIfNotNull("entity_ids", req.getEntityIds().stream().collect(joining(",")), form);
        }
        return form;
    }

    public static FormBody.Builder toForm(AdminBarriersCreateRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        if (req.getBarrieredFromUsergroupIds() != null) {
            setIfNotNull("barriered_from_usergroup_ids", req.getBarrieredFromUsergroupIds().stream().collect(joining(",")), form);
        }
        setIfNotNull("primary_usergroup_id", req.getPrimaryUsergroupId(), form);
        if (req.getRestrictedSubjects() != null) {
            setIfNotNull("restricted_subjects", req.getRestrictedSubjects().stream().collect(joining(",")), form);
        }
        return form;
    }

    public static FormBody.Builder toForm(AdminBarriersDeleteRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("barrier_id", req.getBarrierId(), form);
        return form;
    }

    public static FormBody.Builder toForm(AdminBarriersListRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("cursor", req.getCursor(), form);
        setIfNotNull("limit", req.getLimit(), form);
        return form;
    }

    public static FormBody.Builder toForm(AdminBarriersUpdateRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("barrier_id", req.getBarrierId(), form);
        if (req.getBarrieredFromUsergroupIds() != null) {
            setIfNotNull("barriered_from_usergroup_ids", req.getBarrieredFromUsergroupIds().stream().collect(joining(",")), form);
        }
        setIfNotNull("primary_usergroup_id", req.getPrimaryUsergroupId(), form);
        if (req.getRestrictedSubjects() != null) {
            setIfNotNull("restricted_subjects", req.getRestrictedSubjects().stream().collect(joining(",")), form);
        }
        return form;
    }

    public static FormBody.Builder toForm(AdminConversationsSetTeamsRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel_id", req.getChannelId(), form);
        setIfNotNull("org_channel", req.getOrgChannel(), form);
        if (req.getTargetTeamIds() != null) {
            setIfNotNull("target_team_ids", req.getTargetTeamIds().stream().collect(joining(",")), form);
        }
        setIfNotNull("team_id", req.getTeamId(), form);
        return form;
    }

    public static FormBody.Builder toForm(AdminConversationsArchiveRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel_id", req.getChannelId(), form);
        return form;
    }

    public static FormBody.Builder toForm(AdminConversationsConvertToPrivateRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel_id", req.getChannelId(), form);
        return form;
    }

    public static FormBody.Builder toForm(AdminConversationsCreateRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("is_private", req.getIsPrivate(), form);
        setIfNotNull("name", req.getName(), form);
        setIfNotNull("description", req.getDescription(), form);
        setIfNotNull("org_wide", req.getOrgWide(), form);
        setIfNotNull("team_id", req.getTeamId(), form);
        return form;
    }

    public static FormBody.Builder toForm(AdminConversationsDeleteRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel_id", req.getChannelId(), form);
        return form;
    }

    public static FormBody.Builder toForm(AdminConversationsDisconnectSharedRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel_id", req.getChannelId(), form);
        if (req.getLeavingTeamIds() != null) {
            setIfNotNull("leaving_team_ids", req.getLeavingTeamIds().stream().collect(joining(",")), form);
        }
        return form;
    }

    public static FormBody.Builder toForm(AdminConversationsGetConversationPrefsRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel_id", req.getChannelId(), form);
        return form;
    }

    public static FormBody.Builder toForm(AdminConversationsGetTeamsRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel_id", req.getChannelId(), form);
        setIfNotNull("cursor", req.getCursor(), form);
        setIfNotNull("limit", req.getLimit(), form);
        return form;
    }

    public static FormBody.Builder toForm(AdminConversationsInviteRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel_id", req.getChannelId(), form);
        if (req.getUserIds() != null) {
            setIfNotNull("user_ids", req.getUserIds().stream().collect(joining(",")), form);
        }
        return form;
    }

    public static FormBody.Builder toForm(AdminConversationsRenameRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel_id", req.getChannelId(), form);
        setIfNotNull("name", req.getName(), form);
        return form;
    }

    public static FormBody.Builder toForm(AdminConversationsSearchRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("cursor", req.getCursor(), form);
        setIfNotNull("limit", req.getLimit(), form);
        setIfNotNull("query", req.getQuery(), form);
        if (req.getSearchChannelTypes() != null) {
            setIfNotNull("search_channel_types", req.getSearchChannelTypes().stream().collect(joining(",")), form);
        }
        setIfNotNull("sort", req.getSort(), form);
        setIfNotNull("sort_dir", req.getSortDir(), form);
        if (req.getTeamIds() != null) {
            setIfNotNull("team_ids", req.getTeamIds().stream().collect(joining(",")), form);
        }
        return form;
    }

    public static FormBody.Builder toForm(AdminConversationsSetConversationPrefsRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel_id", req.getChannelId(), form);
        if (req.getPrefsAsString() != null) {
            setIfNotNull("prefs", req.getPrefsAsString(), form);
        } else if (req.getPrefs() != null) {
            setIfNotNull("prefs", req.getPrefs().toValue(), form);
        }
        return form;
    }

    public static FormBody.Builder toForm(AdminConversationsUnarchiveRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel_id", req.getChannelId(), form);
        return form;
    }

    public static FormBody.Builder toForm(AdminConversationsEkmListOriginalConnectedChannelInfoRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        if (req.getChannelIds() != null) {
            setIfNotNull("channel_ids", req.getChannelIds().stream().collect(joining(",")), form);
        }
        setIfNotNull("cursor", req.getCursor(), form);
        setIfNotNull("limit", req.getLimit(), form);
        if (req.getTeamIds() != null) {
            setIfNotNull("team_ids", req.getTeamIds().stream().collect(joining(",")), form);
        }
        return form;
    }

    public static FormBody.Builder toForm(AdminConversationsRestrictAccessAddGroupRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel_id", req.getChannelId(), form);
        setIfNotNull("group_id", req.getGroupId(), form);
        setIfNotNull("team_id", req.getTeamId(), form);
        return form;
    }

    public static FormBody.Builder toForm(AdminConversationsRestrictAccessRemoveGroupRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel_id", req.getChannelId(), form);
        setIfNotNull("group_id", req.getGroupId(), form);
        setIfNotNull("team_id", req.getTeamId(), form);
        return form;
    }

    public static FormBody.Builder toForm(AdminConversationsRestrictAccessListGroupsRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel_id", req.getChannelId(), form);
        setIfNotNull("team_id", req.getTeamId(), form);
        return form;
    }

    public static FormBody.Builder toForm(AdminConversationsWhitelistAddRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel_id", req.getChannelId(), form);
        setIfNotNull("group_id", req.getGroupId(), form);
        setIfNotNull("team_id", req.getTeamId(), form);
        return form;
    }

    public static FormBody.Builder toForm(AdminConversationsWhitelistRemoveRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel_id", req.getChannelId(), form);
        setIfNotNull("group_id", req.getGroupId(), form);
        setIfNotNull("team_id", req.getTeamId(), form);
        return form;
    }

    public static FormBody.Builder toForm(AdminConversationsWhitelistListGroupsLinkedToChannelRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel_id", req.getChannelId(), form);
        setIfNotNull("team_id", req.getTeamId(), form);
        return form;
    }

    public static FormBody.Builder toForm(AdminEmojiAddRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("name", req.getName(), form);
        setIfNotNull("url", req.getUrl(), form);
        return form;
    }

    public static FormBody.Builder toForm(AdminEmojiAddAliasRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("alias_for", req.getAliasFor(), form);
        setIfNotNull("name", req.getName(), form);
        return form;
    }

    public static FormBody.Builder toForm(AdminEmojiListRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("cursor", req.getCursor(), form);
        setIfNotNull("limit", req.getLimit(), form);
        return form;
    }

    public static FormBody.Builder toForm(AdminEmojiRemoveRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("name", req.getName(), form);
        return form;
    }

    public static FormBody.Builder toForm(AdminEmojiRenameRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("name", req.getName(), form);
        setIfNotNull("new_name", req.getNewName(), form);
        return form;
    }

    public static FormBody.Builder toForm(AdminInviteRequestsApproveRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("invite_request_id", req.getInviteRequestId(), form);
        setIfNotNull("team_id", req.getTeamId(), form);
        return form;
    }

    public static FormBody.Builder toForm(AdminInviteRequestsDenyRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("invite_request_id", req.getInviteRequestId(), form);
        setIfNotNull("team_id", req.getTeamId(), form);
        return form;
    }

    public static FormBody.Builder toForm(AdminInviteRequestsListRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("cursor", req.getCursor(), form);
        setIfNotNull("limit", req.getLimit(), form);
        setIfNotNull("team_id", req.getTeamId(), form);
        return form;
    }

    public static FormBody.Builder toForm(AdminInviteRequestsApprovedListRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("cursor", req.getCursor(), form);
        setIfNotNull("limit", req.getLimit(), form);
        setIfNotNull("team_id", req.getTeamId(), form);
        return form;
    }

    public static FormBody.Builder toForm(AdminInviteRequestsDeniedListRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("cursor", req.getCursor(), form);
        setIfNotNull("limit", req.getLimit(), form);
        setIfNotNull("team_id", req.getTeamId(), form);
        return form;
    }

    public static FormBody.Builder toForm(AdminTeamsAdminsListRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("cursor", req.getCursor(), form);
        setIfNotNull("limit", req.getLimit(), form);
        setIfNotNull("team_id", req.getTeamId(), form);
        return form;
    }

    public static FormBody.Builder toForm(AdminTeamsOwnersListRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("cursor", req.getCursor(), form);
        setIfNotNull("limit", req.getLimit(), form);
        setIfNotNull("team_id", req.getTeamId(), form);
        return form;
    }

    public static FormBody.Builder toForm(AdminTeamsCreateRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("team_domain", req.getTeamDomain(), form);
        setIfNotNull("team_name", req.getTeamName(), form);
        setIfNotNull("team_description", req.getTeamDescription(), form);
        setIfNotNull("team_discoverability", req.getTeamDiscoverability(), form);
        return form;
    }

    public static FormBody.Builder toForm(AdminTeamsListRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("cursor", req.getCursor(), form);
        setIfNotNull("limit", req.getLimit(), form);
        return form;
    }

    public static FormBody.Builder toForm(AdminTeamsSettingsInfoRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("team_id", req.getTeamId(), form);
        return form;
    }

    public static FormBody.Builder toForm(AdminTeamsSettingsSetDescriptionRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("team_id", req.getTeamId(), form);
        setIfNotNull("description", req.getDescription(), form);
        return form;
    }

    public static FormBody.Builder toForm(AdminTeamsSettingsSetDefaultChannelsRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("team_id", req.getTeamId(), form);
        setIfNotNull("channel_ids", req.getChannelIds().stream().collect(joining(",")), form);
        return form;
    }

    public static FormBody.Builder toForm(AdminTeamsSettingsSetDiscoverabilityRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("team_id", req.getTeamId(), form);
        setIfNotNull("discoverability", req.getDiscoverability(), form);
        return form;
    }

    public static FormBody.Builder toForm(AdminTeamsSettingsSetIconRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("team_id", req.getTeamId(), form);
        setIfNotNull("image_url", req.getImageUrl(), form);
        return form;
    }

    public static FormBody.Builder toForm(AdminTeamsSettingsSetNameRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("team_id", req.getTeamId(), form);
        setIfNotNull("name", req.getName(), form);
        return form;
    }

    public static FormBody.Builder toForm(AdminUsergroupsAddChannelsRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel_ids", req.getChannelIds().stream().collect(joining(",")), form);
        setIfNotNull("team_id", req.getTeamId(), form);
        setIfNotNull("usergroup_id", req.getUsergroupId(), form);
        return form;
    }

    public static FormBody.Builder toForm(AdminUsergroupsAddTeamsRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("team_ids", req.getTeamIds().stream().collect(joining(",")), form);
        setIfNotNull("usergroup_id", req.getUsergroupId(), form);
        setIfNotNull("auto_provision", req.getAutoProvision(), form);
        return form;
    }

    public static FormBody.Builder toForm(AdminUsergroupsListChannelsRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("team_id", req.getTeamId(), form);
        setIfNotNull("usergroup_id", req.getUsergroupId(), form);
        setIfNotNull("include_num_members", req.getIncludeNumMembers(), form);
        return form;
    }

    public static FormBody.Builder toForm(AdminUsergroupsRemoveChannelsRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel_ids", req.getChannelIds().stream().collect(joining(",")), form);
        setIfNotNull("usergroup_id", req.getUsergroupId(), form);
        return form;
    }

    public static FormBody.Builder toForm(AdminUsersAssignRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("team_id", req.getTeamId(), form);
        setIfNotNull("user_id", req.getUserId(), form);
        return form;
    }

    public static FormBody.Builder toForm(AdminUsersInviteRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel_ids", req.getChannelIds().stream().collect(joining(",")), form);
        setIfNotNull("email", req.getEmail(), form);
        setIfNotNull("team_id", req.getTeamId(), form);
        setIfNotNull("custom_message", req.getCustomMessage(), form);
        setIfNotNull("email_password_policy_enabled", req.getEmailPasswordPolicyEnabled(), form);
        setIfNotNull("guest_expiration_ts", req.getGuestExpirationTs(), form);
        setIfNotNull("is_restricted", req.isRestricted(), form);
        setIfNotNull("is_ultra_restricted", req.isUltraRestricted(), form);
        setIfNotNull("real_name", req.getRealName(), form);
        setIfNotNull("resend", req.isResend(), form);
        return form;
    }

    public static FormBody.Builder toForm(AdminUsersListRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("team_id", req.getTeamId(), form);
        setIfNotNull("cursor", req.getCursor(), form);
        setIfNotNull("limit", req.getLimit(), form);
        return form;
    }

    public static FormBody.Builder toForm(AdminUsersRemoveRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("team_id", req.getTeamId(), form);
        setIfNotNull("user_id", req.getUserId(), form);
        return form;
    }

    public static FormBody.Builder toForm(AdminUsersSetAdminRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("team_id", req.getTeamId(), form);
        setIfNotNull("user_id", req.getUserId(), form);
        return form;
    }

    public static FormBody.Builder toForm(AdminUsersSetExpirationRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("team_id", req.getTeamId(), form);
        setIfNotNull("user_id", req.getUserId(), form);
        setIfNotNull("expiration_ts", req.getExpirationTs(), form);
        return form;
    }

    public static FormBody.Builder toForm(AdminUsersSetOwnerRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("team_id", req.getTeamId(), form);
        setIfNotNull("user_id", req.getUserId(), form);
        return form;
    }

    public static FormBody.Builder toForm(AdminUsersSetRegularRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("team_id", req.getTeamId(), form);
        setIfNotNull("user_id", req.getUserId(), form);
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

    public static FormBody.Builder toForm(AppsConnectionsOpenRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        return form;
    }

    public static FormBody.Builder toForm(AppsEventAuthorizationsListRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("event_context", req.getEventContext(), form);
        setIfNotNull("cursor", req.getCursor(), form);
        setIfNotNull("limit", req.getLimit(), form);
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

    public static FormBody.Builder toForm(AuthTeamsListRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("cursor", req.getCursor(), form);
        setIfNotNull("limit", req.getLimit(), form);
        setIfNotNull("include_icon", req.getIncludeIcon(), form);
        return form;
    }

    public static FormBody.Builder toForm(BotsInfoRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("bot", req.getBot(), form);
        setIfNotNull("team_id", req.getTeamId(), form);
        return form;
    }

    public static FormBody.Builder toForm(CallsAddRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("external_unique_id", req.getExternalUniqueId(), form);
        setIfNotNull("join_url", req.getJoinUrl(), form);
        setIfNotNull("created_by", req.getCreatedBy(), form);
        setIfNotNull("date_start", req.getDateStart(), form);
        setIfNotNull("desktop_app_join_url", req.getDesktopAppJoinUrl(), form);
        setIfNotNull("external_display_id", req.getExternalDisplayId(), form);
        setIfNotNull("title", req.getTitle(), form);
        if (req.getUsers() != null && req.getUsers().size() > 0) {
            String usersJson = GSON.toJson(req.getUsers());
            setIfNotNull("users", usersJson, form);
        }
        return form;
    }

    public static FormBody.Builder toForm(CallsEndRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("id", req.getId(), form);
        setIfNotNull("duration", req.getDuration(), form);
        return form;
    }

    public static FormBody.Builder toForm(CallsInfoRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("id", req.getId(), form);
        return form;
    }

    public static FormBody.Builder toForm(CallsUpdateRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("id", req.getId(), form);
        setIfNotNull("desktop_app_join_url", req.getDesktopAppJoinUrl(), form);
        setIfNotNull("join_url", req.getJoinUrl(), form);
        setIfNotNull("title", req.getTitle(), form);
        return form;
    }

    public static FormBody.Builder toForm(CallsParticipantsAddRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("id", req.getId(), form);
        if (req.getUsers() != null && req.getUsers().size() > 0) {
            String usersJson = GSON.toJson(req.getUsers());
            setIfNotNull("users", usersJson, form);
        }
        return form;
    }

    public static FormBody.Builder toForm(CallsParticipantsRemoveRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("id", req.getId(), form);
        if (req.getUsers() != null && req.getUsers().size() > 0) {
            String usersJson = GSON.toJson(req.getUsers());
            setIfNotNull("users", usersJson, form);
        }
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
        setIfNotNull("team_id", req.getTeamId(), form);
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
        setIfNotNull("team_id", req.getTeamId(), form);
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
        setIfNotNull("as_user", req.isAsUser(), form);
        return form;
    }

    public static FormBody.Builder toForm(ChatDeleteScheduledMessageRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("scheduled_message_id", req.getScheduledMessageId(), form);
        setIfNotNull("as_user", req.isAsUser(), form);
        return form;
    }

    public static FormBody.Builder toForm(ChatMeMessageRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        // We don't use #setTextAndWarnIfMissing for this because text here is required
        setIfNotNull("text", req.getText(), form);
        return form;
    }

    public static FormBody.Builder toForm(ChatScheduleMessageRequest req) {
        warnIfEitherTextOrAttachmentFallbackIsMissing(
                "chat.scheduleMessage",
                req.getText(),
                req.getAttachments(),
                req.getAttachmentsAsString()
        );
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("post_at", req.getPostAt(), form);
        setIfNotNull("text", req.getText(), form);
        setIfNotNull("as_user", req.isAsUser(), form);

        if (req.getBlocksAsString() != null) {
            form.add("blocks", req.getBlocksAsString());
        } else if (req.getBlocks() != null) {
            String json = GSON.toJson(req.getBlocks());
            form.add("blocks", json);
        }
        if (req.getBlocksAsString() != null && req.getBlocks() != null) {
            log.warn("Although you set both blocksAsString and blocks, only blocksAsString was used.");
        }

        if (req.getAttachmentsAsString() != null) {
            form.add("attachments", req.getAttachmentsAsString());
        } else if (req.getAttachments() != null) {
            String json = GSON.toJson(req.getAttachments());
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

    public static FormBody.Builder toForm(ChatScheduledMessagesListRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("cursor", req.getCursor(), form);
        setIfNotNull("latest", req.getLatest(), form);
        setIfNotNull("limit", req.getLimit(), form);
        setIfNotNull("oldest", req.getOldest(), form);
        setIfNotNull("team_id", req.getTeamId(), form);
        return form;
    }

    public static FormBody.Builder toForm(ChatPostEphemeralRequest req) {
        warnIfEitherTextOrAttachmentFallbackIsMissing(
                "chat.postEphemeral",
                req.getText(),
                req.getAttachments(),
                req.getAttachmentsAsString()
        );
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("text", req.getText(), form);
        setIfNotNull("user", req.getUser(), form);
        setIfNotNull("as_user", req.isAsUser(), form);

        if (req.getBlocksAsString() != null) {
            form.add("blocks", req.getBlocksAsString());
        } else if (req.getBlocks() != null) {
            String json = GSON.toJson(req.getBlocks());
            form.add("blocks", json);
        }
        if (req.getBlocksAsString() != null && req.getBlocks() != null) {
            log.warn("Although you set both blocksAsString and blocks, only blocksAsString was used.");
        }

        if (req.getAttachmentsAsString() != null) {
            form.add("attachments", req.getAttachmentsAsString());
        } else if (req.getAttachments() != null) {
            String json = GSON.toJson(req.getAttachments());
            form.add("attachments", json);
        }
        setIfNotNull("thread_ts", req.getThreadTs(), form);
        setIfNotNull("icon_emoji", req.getIconEmoji(), form);
        setIfNotNull("icon_url", req.getIconUrl(), form);
        setIfNotNull("username", req.getUsername(), form);
        setIfNotNull("link_names", req.isLinkNames(), form);
        setIfNotNull("parse", req.getParse(), form);
        return form;
    }

    public static FormBody.Builder toForm(ChatPostMessageRequest req) {
        warnIfEitherTextOrAttachmentFallbackIsMissing(
                "chat.postMessage",
                req.getText(),
                req.getAttachments(),
                req.getAttachmentsAsString()
        );
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
            String json = GSON.toJson(req.getBlocks());
            form.add("blocks", json);
        }
        if (req.getBlocksAsString() != null && req.getBlocks() != null) {
            log.warn("Although you set both blocksAsString and blocks, only blocksAsString was used.");
        }

        if (req.getAttachmentsAsString() != null) {
            form.add("attachments", req.getAttachmentsAsString());
        } else if (req.getAttachments() != null) {
            String json = GSON.toJson(req.getAttachments());
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
        warnIfEitherTextOrAttachmentFallbackIsMissing(
                "chat.update",
                req.getText(),
                req.getAttachments(),
                req.getAttachmentsAsString()
        );
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("ts", req.getTs(), form);
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("text", req.getText(), form);
        setIfNotNull("parse", req.getParse(), form);
        setIfNotNull("link_names", req.isLinkNames(), form);

        if (req.getBlocksAsString() != null) {
            form.add("blocks", req.getBlocksAsString());
        } else if (req.getBlocks() != null) {
            String json = GSON.toJson(req.getBlocks());
            form.add("blocks", json);
        }
        if (req.getBlocksAsString() != null && req.getBlocks() != null) {
            log.warn("Although you set both blocksAsString and blocks, only blocksAsString was used.");
        }

        if (req.getAttachmentsAsString() != null) {
            form.add("attachments", req.getAttachmentsAsString());
        } else if (req.getAttachments() != null) {
            String json = GSON.toJson(req.getAttachments());
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
            String json = GSON.toJson(req.getUnfurls());
            setIfNotNull("unfurls", json, form);
        }
        setIfNotNull("user_auth_required", req.isUserAuthRequired(), form);
        setIfNotNull("user_auth_message", req.getUserAuthMessage(), form);
        setIfNotNull("user_auth_blocks", req.getUserAuthBlocks(), form);
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
        setIfNotNull("team_id", req.getTeamId(), form);
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
        setIfNotNull("team_id", req.getTeamId(), form);
        return form;
    }

    public static FormBody.Builder toForm(ConversationsMarkRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("ts", req.getTs(), form);
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

    public static FormBody.Builder toForm(ConversationsInviteSharedRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        if (req.getEmails() != null) {
            setIfNotNull("emails", req.getEmails().stream().collect(joining(",")), form);
        }
        if (req.getUserIds() != null) {
            setIfNotNull("user_ids", req.getUserIds().stream().collect(joining(",")), form);
        }
        return form;
    }

    public static FormBody.Builder toForm(ConversationsAcceptSharedInviteRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel_name", req.getChannelName(), form);
        setIfNotNull("channel_id", req.getChannelId(), form);
        setIfNotNull("free_trial_accept", req.getFreeTrialAccept(), form);
        setIfNotNull("invite_id", req.getInviteId(), form);
        setIfNotNull("is_private", req.getIsPrivate(), form);
        setIfNotNull("team_id", req.getTeamId(), form);
        return form;
    }

    public static FormBody.Builder toForm(ConversationsApproveSharedInviteRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("invite_id", req.getInviteId(), form);
        setIfNotNull("target_team", req.getTargetTeam(), form);
        return form;
    }

    public static FormBody.Builder toForm(ConversationsDeclineSharedInviteRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("invite_id", req.getInviteId(), form);
        setIfNotNull("target_team", req.getTargetTeam(), form);
        return form;
    }

    public static FormBody.Builder toForm(ConversationsListConnectInvitesRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("count", req.getCount(), form);
        setIfNotNull("cursor", req.getCursor(), form);
        setIfNotNull("team_id", req.getTeamId(), form);
        return form;
    }

    public static FormBody.Builder toForm(DialogOpenRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("trigger_id", req.getTriggerId(), form);
        if (req.getDialogAsString() != null) {
            setIfNotNull("dialog", req.getDialogAsString(), form);
        } else if (req.getDialog() != null) {
            String json = GSON.toJson(req.getDialog());
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
        setIfNotNull("team_id", req.getTeamId(), form);
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
        if (req.getThreadTs() != null) {
            setIfNotNull("thread_ts", req.getThreadTs(), form);
        }
        return form;
    }

    public static MultipartBody.Builder toMultipartBody(FilesUploadRequest req) {
        MultipartBody.Builder form = new MultipartBody.Builder();

        if (req.getFileData() != null) {
            if (req.getFilename() == null) {
                req.setFilename("uploaded_file"); // filename is required for multipart/form-data
            }
            RequestBody file = RequestBody.create(req.getFileData(), MultipartBody.FORM);
            form.addFormDataPart("file", req.getFilename(), file);
        } else if (req.getFile() != null) {
            RequestBody file = RequestBody.create(req.getFile(), MultipartBody.FORM);
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
        setIfNotNull("team_id", req.getTeamId(), form);
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
        setIfNotNull("team_id", req.getTeamId(), form);
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
        setIfNotNull("team_id", req.getTeamId(), form);
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

    public static FormBody.Builder toForm(OAuthV2ExchangeRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("client_id", req.getClientId(), form);
        setIfNotNull("client_secret", req.getClientSecret(), form);
        return form;
    }

    public static FormBody.Builder toForm(OpenIDConnectUserInfoRequest req) {
        FormBody.Builder form = new FormBody.Builder();
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
        setIfNotNull("team_id", req.getTeamId(), form);
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
        setIfNotNull("team_id", req.getTeamId(), form);
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
        setIfNotNull("team_id", req.getTeamId(), form);
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
        setIfNotNull("team_id", req.getTeamId(), form);
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
        setIfNotNull("team_id", req.getTeamId(), form);
        return form;
    }

    public static FormBody.Builder toForm(TeamBillableInfoRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("user", req.getUser(), form);
        setIfNotNull("team_id", req.getTeamId(), form);
        return form;
    }

    public static FormBody.Builder toForm(TeamInfoRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("team", req.getTeam(), form);
        return form;
    }

    public static FormBody.Builder toForm(TeamIntegrationLogsRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("service_id", req.getServiceId(), form);
        setIfNotNull("user", req.getUser(), form);
        setIfNotNull("change_type", req.getChangeType(), form);
        setIfNotNull("count", req.getCount(), form);
        setIfNotNull("page", req.getPage(), form);
        setIfNotNull("team_id", req.getTeamId(), form);
        return form;
    }

    public static FormBody.Builder toForm(TeamProfileGetRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("visibility", req.getVisibility(), form);
        setIfNotNull("team_id", req.getTeamId(), form);
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

    public static FormBody.Builder toForm(UsergroupsUsersListRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("usergroup", req.getUsergroup(), form);
        setIfNotNull("include_disabled", req.isIncludeDisabled(), form);
        return form;
    }

    public static FormBody.Builder toForm(UsergroupsUsersUpdateRequest req) {
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
        setIfNotNull("team_id", req.getTeamId(), form);
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
        setIfNotNull("team_id", req.getTeamId(), form);
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
            setIfNotNull("profile", GSON.toJson(req.getProfile()), form);
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
            setIfNotNull("view", GSON.toJson(req.getView()), form);
        }
        return form;
    }

    public static FormBody.Builder toForm(ViewsPushRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("trigger_id", req.getTriggerId(), form);
        if (req.getViewAsString() != null) {
            setIfNotNull("view", req.getViewAsString(), form);
        } else {
            setIfNotNull("view", GSON.toJson(req.getView()), form);
        }
        return form;
    }

    public static FormBody.Builder toForm(ViewsUpdateRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        if (req.getViewAsString() != null) {
            setIfNotNull("view", req.getViewAsString(), form);
        } else {
            setIfNotNull("view", GSON.toJson(req.getView()), form);
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
            setIfNotNull("view", GSON.toJson(req.getView()), form);
        }
        setIfNotNull("hash", req.getHash(), form);
        return form;
    }

    public static FormBody.Builder toForm(WorkflowsStepCompletedRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("workflow_step_execute_id", req.getWorkflowStepExecuteId(), form);
        if (req.getOutputsAsString() != null) {
            setIfNotNull("outputs", req.getOutputsAsString(), form);
        } else if (req.getOutputs() != null) {
            setIfNotNull("outputs", GSON.toJson(req.getOutputs()), form);
        }
        return form;
    }

    public static FormBody.Builder toForm(WorkflowsStepFailedRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("workflow_step_execute_id", req.getWorkflowStepExecuteId(), form);
        setIfNotNull("error", GSON.toJson(req.getError()), form);
        return form;
    }

    public static FormBody.Builder toForm(WorkflowsUpdateStepRequest req) {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("workflow_step_edit_id", req.getWorkflowStepEditId(), form);
        setIfNotNull("step_image_url", req.getStepImageUrl(), form);
        setIfNotNull("step_name", req.getStepName(), form);
        if (req.getOutputsAsString() != null) {
            setIfNotNull("inputs", req.getInputsAsString(), form);
        } else if (req.getOutputs() != null) {
            setIfNotNull("inputs", GSON.toJson(req.getInputs()), form);
        }
        if (req.getOutputsAsString() != null) {
            setIfNotNull("outputs", req.getOutputsAsString(), form);
        } else if (req.getOutputs() != null) {
            setIfNotNull("outputs", GSON.toJson(req.getOutputs()), form);
        }
        return form;
    }

    // ----------------------------------------------------------------------------------
    // internal methods
    // ----------------------------------------------------------------------------------

    private static final String TEXT_FALLBACK_WARN_MESSAGE_TEMPLATE =
            "The `{}` argument is missing in the request payload for a {} call - It's a best practice to always provide a {} argument when posting a message. The `{}` is used in places where content cannot be rendered such as: system push notifications, assistive technology such as screen readers, etc.";

    private static void warnIfAttachmentWithoutFallbackDetected(String endpointName, List<Attachment> attachments) {
        boolean fallbackMissing = false;
        for (Attachment a : attachments) {
            if (a.getFallback() == null || a.getFallback().trim().length() == 0) {
                fallbackMissing = true;
                break;
            }
        }
        if (fallbackMissing) {
            log.warn(TEXT_FALLBACK_WARN_MESSAGE_TEMPLATE,
                    "fallback", endpointName, "fallback", "fallback");
        }
    }

    private static void warnIfEitherTextOrAttachmentFallbackIsMissing(
            String endpointName,
            String text,
            List<Attachment> attachments,
            String attachmentsAsString) {

        if (attachments != null && attachments.size() > 0) {
            // attachments
            warnIfAttachmentWithoutFallbackDetected(endpointName, attachments);
        } else if (attachmentsAsString != null && attachmentsAsString.trim().length() > 0) {
            // attachments
            warnIfAttachmentWithoutFallbackDetected(
                    endpointName,
                    Arrays.asList(GSON.fromJson(attachmentsAsString, Attachment[].class))
            );
        } else if (text == null || text.trim().isEmpty()) {
            // only text or text + blocks
            log.warn(TEXT_FALLBACK_WARN_MESSAGE_TEMPLATE,
                    "text", endpointName, "text", "text");
        }
    }

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
