package com.slack.api.methods;

import com.slack.api.RequestConfigurator;
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
import com.slack.api.methods.request.admin.functions.AdminFunctionsListRequest;
import com.slack.api.methods.request.admin.functions.AdminFunctionsPermissionsLookupRequest;
import com.slack.api.methods.request.admin.functions.AdminFunctionsPermissionsSetRequest;
import com.slack.api.methods.request.admin.invite_requests.*;
import com.slack.api.methods.request.admin.roles.AdminRolesAddAssignmentsRequest;
import com.slack.api.methods.request.admin.roles.AdminRolesListAssignmentsRequest;
import com.slack.api.methods.request.admin.roles.AdminRolesRemoveAssignmentsRequest;
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
import com.slack.api.methods.request.admin.users.unsupported_versions.AdminUsersUnsupportedVersionsExportRequest;
import com.slack.api.methods.request.admin.workflows.*;
import com.slack.api.methods.request.api.ApiTestRequest;
import com.slack.api.methods.request.apps.AppsUninstallRequest;
import com.slack.api.methods.request.apps.connections.AppsConnectionsOpenRequest;
import com.slack.api.methods.request.apps.event.authorizations.AppsEventAuthorizationsListRequest;
import com.slack.api.methods.request.apps.manifest.*;
import com.slack.api.methods.request.auth.AuthRevokeRequest;
import com.slack.api.methods.request.auth.AuthTestRequest;
import com.slack.api.methods.request.auth.teams.AuthTeamsListRequest;
import com.slack.api.methods.request.bookmarks.BookmarksAddRequest;
import com.slack.api.methods.request.bookmarks.BookmarksEditRequest;
import com.slack.api.methods.request.bookmarks.BookmarksListRequest;
import com.slack.api.methods.request.bookmarks.BookmarksRemoveRequest;
import com.slack.api.methods.request.bots.BotsInfoRequest;
import com.slack.api.methods.request.calls.CallsAddRequest;
import com.slack.api.methods.request.calls.CallsEndRequest;
import com.slack.api.methods.request.calls.CallsInfoRequest;
import com.slack.api.methods.request.calls.CallsUpdateRequest;
import com.slack.api.methods.request.calls.participants.CallsParticipantsAddRequest;
import com.slack.api.methods.request.calls.participants.CallsParticipantsRemoveRequest;
import com.slack.api.methods.request.canvases.CanvasesCreateRequest;
import com.slack.api.methods.request.canvases.CanvasesDeleteRequest;
import com.slack.api.methods.request.canvases.CanvasesEditRequest;
import com.slack.api.methods.request.canvases.access.CanvasesAccessDeleteRequest;
import com.slack.api.methods.request.canvases.access.CanvasesAccessSetRequest;
import com.slack.api.methods.request.canvases.sections.CanvasesSectionsLookupRequest;
import com.slack.api.methods.request.chat.*;
import com.slack.api.methods.request.chat.scheduled_messages.ChatScheduledMessagesListRequest;
import com.slack.api.methods.request.conversations.*;
import com.slack.api.methods.request.conversations.canvases.ConversationsCanvasesCreateRequest;
import com.slack.api.methods.request.dialog.DialogOpenRequest;
import com.slack.api.methods.request.dnd.*;
import com.slack.api.methods.request.emoji.EmojiListRequest;
import com.slack.api.methods.request.files.*;
import com.slack.api.methods.request.files.remote.*;
import com.slack.api.methods.request.functions.FunctionsCompleteErrorRequest;
import com.slack.api.methods.request.functions.FunctionsCompleteSuccessRequest;
import com.slack.api.methods.request.migration.MigrationExchangeRequest;
import com.slack.api.methods.request.oauth.OAuthAccessRequest;
import com.slack.api.methods.request.oauth.OAuthTokenRequest;
import com.slack.api.methods.request.oauth.OAuthV2AccessRequest;
import com.slack.api.methods.request.oauth.OAuthV2ExchangeRequest;
import com.slack.api.methods.request.openid.connect.OpenIDConnectTokenRequest;
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
import com.slack.api.methods.request.team.*;
import com.slack.api.methods.request.team.external_teams.TeamExternalTeamsListRequest;
import com.slack.api.methods.request.team.profile.TeamProfileGetRequest;
import com.slack.api.methods.request.tooling.tokens.ToolingTokensRotateRequest;
import com.slack.api.methods.request.usergroups.*;
import com.slack.api.methods.request.usergroups.users.UsergroupsUsersListRequest;
import com.slack.api.methods.request.usergroups.users.UsergroupsUsersUpdateRequest;
import com.slack.api.methods.request.users.*;
import com.slack.api.methods.request.users.discoverable_contacts.UsersDiscoverableContactsLookupRequest;
import com.slack.api.methods.request.users.profile.UsersProfileGetRequest;
import com.slack.api.methods.request.users.profile.UsersProfileSetRequest;
import com.slack.api.methods.request.views.ViewsOpenRequest;
import com.slack.api.methods.request.views.ViewsPublishRequest;
import com.slack.api.methods.request.views.ViewsPushRequest;
import com.slack.api.methods.request.views.ViewsUpdateRequest;
import com.slack.api.methods.request.workflows.WorkflowsStepCompletedRequest;
import com.slack.api.methods.request.workflows.WorkflowsStepFailedRequest;
import com.slack.api.methods.request.workflows.WorkflowsUpdateStepRequest;
import com.slack.api.methods.response.admin.analytics.AdminAnalyticsGetFileResponse;
import com.slack.api.methods.response.admin.apps.*;
import com.slack.api.methods.response.admin.auth.policy.AdminAuthPolicyAssignEntitiesResponse;
import com.slack.api.methods.response.admin.auth.policy.AdminAuthPolicyGetEntitiesResponse;
import com.slack.api.methods.response.admin.auth.policy.AdminAuthPolicyRemoveEntitiesResponse;
import com.slack.api.methods.response.admin.barriers.AdminBarriersCreateResponse;
import com.slack.api.methods.response.admin.barriers.AdminBarriersDeleteResponse;
import com.slack.api.methods.response.admin.barriers.AdminBarriersListResponse;
import com.slack.api.methods.response.admin.barriers.AdminBarriersUpdateResponse;
import com.slack.api.methods.response.admin.conversations.*;
import com.slack.api.methods.response.admin.conversations.ekm.AdminConversationsEkmListOriginalConnectedChannelInfoResponse;
import com.slack.api.methods.response.admin.conversations.restrict_access.AdminConversationsRestrictAccessAddGroupResponse;
import com.slack.api.methods.response.admin.conversations.restrict_access.AdminConversationsRestrictAccessListGroupsResponse;
import com.slack.api.methods.response.admin.conversations.restrict_access.AdminConversationsRestrictAccessRemoveGroupResponse;
import com.slack.api.methods.response.admin.conversations.whitelist.AdminConversationsWhitelistAddResponse;
import com.slack.api.methods.response.admin.conversations.whitelist.AdminConversationsWhitelistListGroupsLinkedToChannelResponse;
import com.slack.api.methods.response.admin.conversations.whitelist.AdminConversationsWhitelistRemoveResponse;
import com.slack.api.methods.response.admin.emoji.*;
import com.slack.api.methods.response.admin.functions.AdminFunctionsListResponse;
import com.slack.api.methods.response.admin.functions.AdminFunctionsPermissionsLookupResponse;
import com.slack.api.methods.response.admin.functions.AdminFunctionsPermissionsSetResponse;
import com.slack.api.methods.response.admin.invite_requests.*;
import com.slack.api.methods.response.admin.roles.AdminRolesAddAssignmentsResponse;
import com.slack.api.methods.response.admin.roles.AdminRolesListAssignmentsResponse;
import com.slack.api.methods.response.admin.roles.AdminRolesRemoveAssignmentsResponse;
import com.slack.api.methods.response.admin.teams.AdminTeamsAdminsListResponse;
import com.slack.api.methods.response.admin.teams.AdminTeamsCreateResponse;
import com.slack.api.methods.response.admin.teams.AdminTeamsListResponse;
import com.slack.api.methods.response.admin.teams.owners.AdminTeamsOwnersListResponse;
import com.slack.api.methods.response.admin.teams.settings.*;
import com.slack.api.methods.response.admin.usergroups.AdminUsergroupsAddChannelsResponse;
import com.slack.api.methods.response.admin.usergroups.AdminUsergroupsAddTeamsResponse;
import com.slack.api.methods.response.admin.usergroups.AdminUsergroupsListChannelsResponse;
import com.slack.api.methods.response.admin.usergroups.AdminUsergroupsRemoveChannelsResponse;
import com.slack.api.methods.response.admin.users.*;
import com.slack.api.methods.response.admin.users.unsupported_versions.AdminUsersUnsupportedVersionsExportResponse;
import com.slack.api.methods.response.admin.workflows.*;
import com.slack.api.methods.response.api.ApiTestResponse;
import com.slack.api.methods.response.apps.AppsUninstallResponse;
import com.slack.api.methods.response.apps.connections.AppsConnectionsOpenResponse;
import com.slack.api.methods.response.apps.event.authorizations.AppsEventAuthorizationsListResponse;
import com.slack.api.methods.response.apps.manifest.*;
import com.slack.api.methods.response.auth.AuthRevokeResponse;
import com.slack.api.methods.response.auth.AuthTestResponse;
import com.slack.api.methods.response.auth.teams.AuthTeamsListResponse;
import com.slack.api.methods.response.bookmarks.BookmarksAddResponse;
import com.slack.api.methods.response.bookmarks.BookmarksEditResponse;
import com.slack.api.methods.response.bookmarks.BookmarksListResponse;
import com.slack.api.methods.response.bookmarks.BookmarksRemoveResponse;
import com.slack.api.methods.response.bots.BotsInfoResponse;
import com.slack.api.methods.response.calls.CallsAddResponse;
import com.slack.api.methods.response.calls.CallsEndResponse;
import com.slack.api.methods.response.calls.CallsInfoResponse;
import com.slack.api.methods.response.calls.CallsUpdateResponse;
import com.slack.api.methods.response.calls.participants.CallsParticipantsAddResponse;
import com.slack.api.methods.response.calls.participants.CallsParticipantsRemoveResponse;
import com.slack.api.methods.response.canvases.CanvasesCreateResponse;
import com.slack.api.methods.response.canvases.CanvasesDeleteResponse;
import com.slack.api.methods.response.canvases.CanvasesEditResponse;
import com.slack.api.methods.response.canvases.access.CanvasesAccessDeleteResponse;
import com.slack.api.methods.response.canvases.access.CanvasesAccessSetResponse;
import com.slack.api.methods.response.canvases.sections.CanvasesSectionsLookupResponse;
import com.slack.api.methods.response.chat.*;
import com.slack.api.methods.response.chat.scheduled_messages.ChatScheduledMessagesListResponse;
import com.slack.api.methods.response.conversations.*;
import com.slack.api.methods.response.conversations.canvases.ConversationsCanvasesCreateResponse;
import com.slack.api.methods.response.dialog.DialogOpenResponse;
import com.slack.api.methods.response.dnd.*;
import com.slack.api.methods.response.emoji.EmojiListResponse;
import com.slack.api.methods.response.files.*;
import com.slack.api.methods.response.files.remote.*;
import com.slack.api.methods.response.functions.FunctionsCompleteErrorResponse;
import com.slack.api.methods.response.functions.FunctionsCompleteSuccessResponse;
import com.slack.api.methods.response.migration.MigrationExchangeResponse;
import com.slack.api.methods.response.oauth.OAuthAccessResponse;
import com.slack.api.methods.response.oauth.OAuthTokenResponse;
import com.slack.api.methods.response.oauth.OAuthV2AccessResponse;
import com.slack.api.methods.response.oauth.OAuthV2ExchangeResponse;
import com.slack.api.methods.response.openid.connect.OpenIDConnectTokenResponse;
import com.slack.api.methods.response.openid.connect.OpenIDConnectUserInfoResponse;
import com.slack.api.methods.response.pins.PinsAddResponse;
import com.slack.api.methods.response.pins.PinsListResponse;
import com.slack.api.methods.response.pins.PinsRemoveResponse;
import com.slack.api.methods.response.reactions.ReactionsAddResponse;
import com.slack.api.methods.response.reactions.ReactionsGetResponse;
import com.slack.api.methods.response.reactions.ReactionsListResponse;
import com.slack.api.methods.response.reactions.ReactionsRemoveResponse;
import com.slack.api.methods.response.reminders.*;
import com.slack.api.methods.response.rtm.RTMConnectResponse;
import com.slack.api.methods.response.rtm.RTMStartResponse;
import com.slack.api.methods.response.search.SearchAllResponse;
import com.slack.api.methods.response.search.SearchFilesResponse;
import com.slack.api.methods.response.search.SearchMessagesResponse;
import com.slack.api.methods.response.stars.StarsAddResponse;
import com.slack.api.methods.response.stars.StarsListResponse;
import com.slack.api.methods.response.stars.StarsRemoveResponse;
import com.slack.api.methods.response.team.*;
import com.slack.api.methods.response.team.external_teams.TeamExternalTeamsListResponse;
import com.slack.api.methods.response.team.profile.TeamProfileGetResponse;
import com.slack.api.methods.response.tooling.tokens.ToolingTokensRotateResponse;
import com.slack.api.methods.response.usergroups.*;
import com.slack.api.methods.response.usergroups.users.UsergroupsUsersListResponse;
import com.slack.api.methods.response.usergroups.users.UsergroupsUsersUpdateResponse;
import com.slack.api.methods.response.users.*;
import com.slack.api.methods.response.users.discoverable_contacts.UsersDiscoverableContactsLookupResponse;
import com.slack.api.methods.response.users.profile.UsersProfileGetResponse;
import com.slack.api.methods.response.users.profile.UsersProfileSetResponse;
import com.slack.api.methods.response.views.ViewsOpenResponse;
import com.slack.api.methods.response.views.ViewsPublishResponse;
import com.slack.api.methods.response.views.ViewsPushResponse;
import com.slack.api.methods.response.views.ViewsUpdateResponse;
import com.slack.api.methods.response.workflows.WorkflowsStepCompletedResponse;
import com.slack.api.methods.response.workflows.WorkflowsStepFailedResponse;
import com.slack.api.methods.response.workflows.WorkflowsUpdateStepResponse;

import java.util.concurrent.CompletableFuture;

/**
 * Async Slack API Methods client.
 * <p>
 *
 * @see <a href="https://api.slack.com/methods">Slack API Methods</a>
 */
public interface AsyncMethodsClient {

    MethodsClient underlying();

    // ------------------------------
    // admin.analytics
    // ------------------------------

    CompletableFuture<AdminAnalyticsGetFileResponse> adminAnalyticsGetFile(AdminAnalyticsGetFileRequest req);

    CompletableFuture<AdminAnalyticsGetFileResponse> adminAnalyticsGetFile(RequestConfigurator<AdminAnalyticsGetFileRequest.AdminAnalyticsGetFileRequestBuilder> req);

    // ------------------------------
    // admin.apps
    // ------------------------------

    CompletableFuture<AdminAppsApproveResponse> adminAppsApprove(AdminAppsApproveRequest req);

    CompletableFuture<AdminAppsApproveResponse> adminAppsApprove(RequestConfigurator<AdminAppsApproveRequest.AdminAppsApproveRequestBuilder> req);

    CompletableFuture<AdminAppsRestrictResponse> adminAppsRestrict(AdminAppsRestrictRequest req);

    CompletableFuture<AdminAppsRestrictResponse> adminAppsRestrict(RequestConfigurator<AdminAppsRestrictRequest.AdminAppsRestrictRequestBuilder> req);

    CompletableFuture<AdminAppsApprovedListResponse> adminAppsApprovedList(AdminAppsApprovedListRequest req);

    CompletableFuture<AdminAppsApprovedListResponse> adminAppsApprovedList(RequestConfigurator<AdminAppsApprovedListRequest.AdminAppsApprovedListRequestBuilder> req);

    CompletableFuture<AdminAppsRestrictedListResponse> adminAppsRestrictedList(AdminAppsRestrictedListRequest req);

    CompletableFuture<AdminAppsRestrictedListResponse> adminAppsRestrictedList(RequestConfigurator<AdminAppsRestrictedListRequest.AdminAppsRestrictedListRequestBuilder> req);

    CompletableFuture<AdminAppsClearResolutionResponse> adminAppsClearResolution(AdminAppsClearResolutionRequest req);

    CompletableFuture<AdminAppsClearResolutionResponse> adminAppsClearResolution(RequestConfigurator<AdminAppsClearResolutionRequest.AdminAppsClearResolutionRequestBuilder> req);

    CompletableFuture<AdminAppsUninstallResponse> adminAppsUninstall(AdminAppsUninstallRequest req);

    CompletableFuture<AdminAppsUninstallResponse> adminAppsUninstall(RequestConfigurator<AdminAppsUninstallRequest.AdminAppsUninstallRequestBuilder> req);

    CompletableFuture<AdminAppsActivitiesListResponse> adminAppsActivitiesList(AdminAppsActivitiesListRequest req);

    CompletableFuture<AdminAppsActivitiesListResponse> adminAppsActivitiesList(RequestConfigurator<AdminAppsActivitiesListRequest.AdminAppsActivitiesListRequestBuilder> req);

    CompletableFuture<AdminAppsConfigLookupResponse> adminAppsConfigLookup(AdminAppsConfigLookupRequest req);

    CompletableFuture<AdminAppsConfigLookupResponse> adminAppsConfigLookup(RequestConfigurator<AdminAppsConfigLookupRequest.AdminAppsConfigLookupRequestBuilder> req);

    CompletableFuture<AdminAppsConfigSetResponse> adminAppsConfigSet(AdminAppsConfigSetRequest req);

    CompletableFuture<AdminAppsConfigSetResponse> adminAppsConfigSet(RequestConfigurator<AdminAppsConfigSetRequest.AdminAppsConfigSetRequestBuilder> req);

    // ------------------------------
    // admin.apps.requests
    // ------------------------------

    CompletableFuture<AdminAppsRequestsCancelResponse> adminAppsRequestsCancel(AdminAppsRequestsCancelRequest req);

    CompletableFuture<AdminAppsRequestsCancelResponse> adminAppsRequestsCancel(RequestConfigurator<AdminAppsRequestsCancelRequest.AdminAppsRequestsCancelRequestBuilder> req);

    CompletableFuture<AdminAppsRequestsListResponse> adminAppsRequestsList(AdminAppsRequestsListRequest req);

    CompletableFuture<AdminAppsRequestsListResponse> adminAppsRequestsList(RequestConfigurator<AdminAppsRequestsListRequest.AdminAppsRequestsListRequestBuilder> req);

    // ------------------------------
    // admin.auth.policy
    // ------------------------------

    CompletableFuture<AdminAuthPolicyAssignEntitiesResponse> adminAuthPolicyAssignEntities(AdminAuthPolicyAssignEntitiesRequest req);

    CompletableFuture<AdminAuthPolicyAssignEntitiesResponse> adminAuthPolicyAssignEntities(RequestConfigurator<AdminAuthPolicyAssignEntitiesRequest.AdminAuthPolicyAssignEntitiesRequestBuilder> req);

    CompletableFuture<AdminAuthPolicyGetEntitiesResponse> adminAuthPolicyGetEntities(AdminAuthPolicyGetEntitiesRequest req);

    CompletableFuture<AdminAuthPolicyGetEntitiesResponse> adminAuthPolicyGetEntities(RequestConfigurator<AdminAuthPolicyGetEntitiesRequest.AdminAuthPolicyGetEntitiesRequestBuilder> req);

    CompletableFuture<AdminAuthPolicyRemoveEntitiesResponse> adminAuthPolicyRemoveEntities(AdminAuthPolicyRemoveEntitiesRequest req);

    CompletableFuture<AdminAuthPolicyRemoveEntitiesResponse> adminAuthPolicyRemoveEntities(RequestConfigurator<AdminAuthPolicyRemoveEntitiesRequest.AdminAuthPolicyRemoveEntitiesRequestBuilder> req);

    // ------------------------------
    // admin.barriers
    // ------------------------------

    CompletableFuture<AdminBarriersCreateResponse> adminBarriersCreate(AdminBarriersCreateRequest req);

    CompletableFuture<AdminBarriersCreateResponse> adminBarriersCreate(RequestConfigurator<AdminBarriersCreateRequest.AdminBarriersCreateRequestBuilder> req);

    CompletableFuture<AdminBarriersDeleteResponse> adminBarriersDelete(AdminBarriersDeleteRequest req);

    CompletableFuture<AdminBarriersDeleteResponse> adminBarriersDelete(RequestConfigurator<AdminBarriersDeleteRequest.AdminBarriersDeleteRequestBuilder> req);

    CompletableFuture<AdminBarriersListResponse> adminBarriersList(AdminBarriersListRequest req);

    CompletableFuture<AdminBarriersListResponse> adminBarriersList(RequestConfigurator<AdminBarriersListRequest.AdminBarriersListRequestBuilder> req);

    CompletableFuture<AdminBarriersUpdateResponse> adminBarriersUpdate(AdminBarriersUpdateRequest req);

    CompletableFuture<AdminBarriersUpdateResponse> adminBarriersUpdate(RequestConfigurator<AdminBarriersUpdateRequest.AdminBarriersUpdateRequestBuilder> req);

    // ------------------------------
    // admin.conversations.restrictAccess
    // ------------------------------

    CompletableFuture<AdminConversationsRestrictAccessAddGroupResponse> adminConversationsRestrictAccessAddGroup(
            AdminConversationsRestrictAccessAddGroupRequest req);

    CompletableFuture<AdminConversationsRestrictAccessAddGroupResponse> adminConversationsRestrictAccessAddGroup(
            RequestConfigurator<AdminConversationsRestrictAccessAddGroupRequest.AdminConversationsRestrictAccessAddGroupRequestBuilder> req);

    CompletableFuture<AdminConversationsRestrictAccessRemoveGroupResponse> adminConversationsRestrictAccessRemoveGroup(
            AdminConversationsRestrictAccessRemoveGroupRequest req);

    CompletableFuture<AdminConversationsRestrictAccessRemoveGroupResponse> adminConversationsRestrictAccessRemoveGroup(
            RequestConfigurator<AdminConversationsRestrictAccessRemoveGroupRequest.AdminConversationsRestrictAccessRemoveGroupRequestBuilder> req);

    CompletableFuture<AdminConversationsRestrictAccessListGroupsResponse> adminConversationsRestrictAccessListGroups(
            AdminConversationsRestrictAccessListGroupsRequest req);

    CompletableFuture<AdminConversationsRestrictAccessListGroupsResponse> adminConversationsRestrictAccessListGroups(
            RequestConfigurator<AdminConversationsRestrictAccessListGroupsRequest.AdminConversationsRestrictAccessListGroupsRequestBuilder> req);

    // ------------------------------
    // admin.conversations
    // ------------------------------

    CompletableFuture<AdminConversationsSetTeamsResponse> adminConversationsSetTeams(AdminConversationsSetTeamsRequest req);

    CompletableFuture<AdminConversationsSetTeamsResponse> adminConversationsSetTeams(RequestConfigurator<AdminConversationsSetTeamsRequest.AdminConversationsSetTeamsRequestBuilder> req);

    CompletableFuture<AdminConversationsArchiveResponse> adminConversationsArchive(AdminConversationsArchiveRequest req);

    CompletableFuture<AdminConversationsArchiveResponse> adminConversationsArchive(RequestConfigurator<AdminConversationsArchiveRequest.AdminConversationsArchiveRequestBuilder> req);

    CompletableFuture<AdminConversationsConvertToPrivateResponse> adminConversationsConvertToPrivate(AdminConversationsConvertToPrivateRequest req);

    CompletableFuture<AdminConversationsConvertToPrivateResponse> adminConversationsConvertToPrivate(RequestConfigurator<AdminConversationsConvertToPrivateRequest.AdminConversationsConvertToPrivateRequestBuilder> req);

    CompletableFuture<AdminConversationsCreateResponse> adminConversationsCreate(AdminConversationsCreateRequest req);

    CompletableFuture<AdminConversationsCreateResponse> adminConversationsCreate(RequestConfigurator<AdminConversationsCreateRequest.AdminConversationsCreateRequestBuilder> req);

    CompletableFuture<AdminConversationsDeleteResponse> adminConversationsDelete(AdminConversationsDeleteRequest req);

    CompletableFuture<AdminConversationsDeleteResponse> adminConversationsDelete(RequestConfigurator<AdminConversationsDeleteRequest.AdminConversationsDeleteRequestBuilder> req);

    CompletableFuture<AdminConversationsDisconnectSharedResponse> adminConversationsDisconnectShared(AdminConversationsDisconnectSharedRequest req);

    CompletableFuture<AdminConversationsDisconnectSharedResponse> adminConversationsDisconnectShared(RequestConfigurator<AdminConversationsDisconnectSharedRequest.AdminConversationsDisconnectSharedRequestBuilder> req);

    CompletableFuture<AdminConversationsGetConversationPrefsResponse> adminConversationsGetConversationPrefs(AdminConversationsGetConversationPrefsRequest req);

    CompletableFuture<AdminConversationsGetConversationPrefsResponse> adminConversationsGetConversationPrefs(RequestConfigurator<AdminConversationsGetConversationPrefsRequest.AdminConversationsGetConversationPrefsRequestBuilder> req);

    CompletableFuture<AdminConversationsGetTeamsResponse> adminConversationsGetTeams(AdminConversationsGetTeamsRequest req);

    CompletableFuture<AdminConversationsGetTeamsResponse> adminConversationsGetTeams(RequestConfigurator<AdminConversationsGetTeamsRequest.AdminConversationsGetTeamsRequestBuilder> req);

    CompletableFuture<AdminConversationsInviteResponse> adminConversationsInvite(AdminConversationsInviteRequest req);

    CompletableFuture<AdminConversationsInviteResponse> adminConversationsInvite(RequestConfigurator<AdminConversationsInviteRequest.AdminConversationsInviteRequestBuilder> req);

    CompletableFuture<AdminConversationsRenameResponse> adminConversationsRename(AdminConversationsRenameRequest req);

    CompletableFuture<AdminConversationsRenameResponse> adminConversationsRename(RequestConfigurator<AdminConversationsRenameRequest.AdminConversationsRenameRequestBuilder> req);

    CompletableFuture<AdminConversationsSearchResponse> adminConversationsSearch(AdminConversationsSearchRequest req);

    CompletableFuture<AdminConversationsSearchResponse> adminConversationsSearch(RequestConfigurator<AdminConversationsSearchRequest.AdminConversationsSearchRequestBuilder> req);

    CompletableFuture<AdminConversationsSetConversationPrefsResponse> adminConversationsSetConversationPrefs(AdminConversationsSetConversationPrefsRequest req);

    CompletableFuture<AdminConversationsSetConversationPrefsResponse> adminConversationsSetConversationPrefs(RequestConfigurator<AdminConversationsSetConversationPrefsRequest.AdminConversationsSetConversationPrefsRequestBuilder> req);

    CompletableFuture<AdminConversationsUnarchiveResponse> adminConversationsUnarchive(AdminConversationsUnarchiveRequest req);

    CompletableFuture<AdminConversationsUnarchiveResponse> adminConversationsUnarchive(RequestConfigurator<AdminConversationsUnarchiveRequest.AdminConversationsUnarchiveRequestBuilder> req);

    CompletableFuture<AdminConversationsGetCustomRetentionResponse> adminConversationsGetCustomRetention(RequestConfigurator<AdminConversationsGetCustomRetentionRequest.AdminConversationsGetCustomRetentionRequestBuilder> req);

    CompletableFuture<AdminConversationsGetCustomRetentionResponse> adminConversationsGetCustomRetention(AdminConversationsGetCustomRetentionRequest req);

    CompletableFuture<AdminConversationsRemoveCustomRetentionResponse> adminConversationsRemoveCustomRetention(AdminConversationsRemoveCustomRetentionRequest req);

    CompletableFuture<AdminConversationsRemoveCustomRetentionResponse> adminConversationsRemoveCustomRetention(RequestConfigurator<AdminConversationsRemoveCustomRetentionRequest.AdminConversationsRemoveCustomRetentionRequestBuilder> req);

    CompletableFuture<AdminConversationsSetCustomRetentionResponse> adminConversationsSetCustomRetention(AdminConversationsSetCustomRetentionRequest req);

    CompletableFuture<AdminConversationsSetCustomRetentionResponse> adminConversationsSetCustomRetention(RequestConfigurator<AdminConversationsSetCustomRetentionRequest.AdminConversationsSetCustomRetentionRequestBuilder> req);

    CompletableFuture<AdminConversationsBulkArchiveResponse> adminConversationsBulkArchive(AdminConversationsBulkArchiveRequest req);

    CompletableFuture<AdminConversationsBulkArchiveResponse> adminConversationsBulkArchive(RequestConfigurator<AdminConversationsBulkArchiveRequest.AdminConversationsBulkArchiveRequestBuilder> req);

    CompletableFuture<AdminConversationsBulkDeleteResponse> adminConversationsBulkDelete(AdminConversationsBulkDeleteRequest req);

    CompletableFuture<AdminConversationsBulkDeleteResponse> adminConversationsBulkDelete(RequestConfigurator<AdminConversationsBulkDeleteRequest.AdminConversationsBulkDeleteRequestBuilder> req);

    CompletableFuture<AdminConversationsBulkMoveResponse> adminConversationsBulkMove(AdminConversationsBulkMoveRequest req);

    CompletableFuture<AdminConversationsBulkMoveResponse> adminConversationsBulkMove(RequestConfigurator<AdminConversationsBulkMoveRequest.AdminConversationsBulkMoveRequestBuilder> req);

    CompletableFuture<AdminConversationsConvertToPublicResponse> adminConversationsConvertToPublic(AdminConversationsConvertToPublicRequest req);

    CompletableFuture<AdminConversationsConvertToPublicResponse> adminConversationsConvertToPublic(RequestConfigurator<AdminConversationsConvertToPublicRequest.AdminConversationsConvertToPublicRequestBuilder> req);

    CompletableFuture<AdminConversationsLookupResponse> adminConversationsLookup(AdminConversationsLookupRequest req);

    CompletableFuture<AdminConversationsLookupResponse> adminConversationsLookup(RequestConfigurator<AdminConversationsLookupRequest.AdminConversationsLookupRequestBuilder> req);

    // ------------------------------
    // admin.conversations.ekm
    // ------------------------------

    CompletableFuture<AdminConversationsEkmListOriginalConnectedChannelInfoResponse> adminConversationsEkmListOriginalConnectedChannelInfo(AdminConversationsEkmListOriginalConnectedChannelInfoRequest req);

    CompletableFuture<AdminConversationsEkmListOriginalConnectedChannelInfoResponse> adminConversationsEkmListOriginalConnectedChannelInfo(RequestConfigurator<AdminConversationsEkmListOriginalConnectedChannelInfoRequest.AdminConversationsEkmListOriginalConnectedChannelInfoRequestBuilder> req);

    // ------------------------------
    // admin.conversations.whitelist
    // ------------------------------

    @Deprecated
    CompletableFuture<AdminConversationsWhitelistAddResponse> adminConversationsWhitelistAdd(
            AdminConversationsWhitelistAddRequest req);

    @Deprecated
    CompletableFuture<AdminConversationsWhitelistAddResponse> adminConversationsWhitelistAdd(
            RequestConfigurator<AdminConversationsWhitelistAddRequest.AdminConversationsWhitelistAddRequestBuilder> req);

    @Deprecated
    CompletableFuture<AdminConversationsWhitelistRemoveResponse> adminConversationsWhitelistRemove(
            AdminConversationsWhitelistRemoveRequest req);

    @Deprecated
    CompletableFuture<AdminConversationsWhitelistRemoveResponse> adminConversationsWhitelistRemove(
            RequestConfigurator<AdminConversationsWhitelistRemoveRequest.AdminConversationsWhitelistRemoveRequestBuilder> req);

    @Deprecated
    CompletableFuture<AdminConversationsWhitelistListGroupsLinkedToChannelResponse> adminConversationsWhitelistListGroupsLinkedToChannel(
            AdminConversationsWhitelistListGroupsLinkedToChannelRequest req);

    @Deprecated
    CompletableFuture<AdminConversationsWhitelistListGroupsLinkedToChannelResponse> adminConversationsWhitelistListGroupsLinkedToChannel(
            RequestConfigurator<AdminConversationsWhitelistListGroupsLinkedToChannelRequest.AdminConversationsWhitelistListGroupsLinkedToChannelRequestBuilder> req);

    // ------------------------------
    // admin.emoji
    // ------------------------------

    CompletableFuture<AdminEmojiAddResponse> adminEmojiAdd(AdminEmojiAddRequest req);

    CompletableFuture<AdminEmojiAddResponse> adminEmojiAdd(RequestConfigurator<AdminEmojiAddRequest.AdminEmojiAddRequestBuilder> req);

    CompletableFuture<AdminEmojiAddAliasResponse> adminEmojiAddAlias(AdminEmojiAddAliasRequest req);

    CompletableFuture<AdminEmojiAddAliasResponse> adminEmojiAddAlias(RequestConfigurator<AdminEmojiAddAliasRequest.AdminEmojiAddAliasRequestBuilder> req);

    CompletableFuture<AdminEmojiListResponse> adminEmojiList(AdminEmojiListRequest req);

    CompletableFuture<AdminEmojiListResponse> adminEmojiList(RequestConfigurator<AdminEmojiListRequest.AdminEmojiListRequestBuilder> req);

    CompletableFuture<AdminEmojiRemoveResponse> adminEmojiRemove(AdminEmojiRemoveRequest req);

    CompletableFuture<AdminEmojiRemoveResponse> adminEmojiRemove(RequestConfigurator<AdminEmojiRemoveRequest.AdminEmojiRemoveRequestBuilder> req);

    CompletableFuture<AdminEmojiRenameResponse> adminEmojiRename(AdminEmojiRenameRequest req);

    CompletableFuture<AdminEmojiRenameResponse> adminEmojiRename(RequestConfigurator<AdminEmojiRenameRequest.AdminEmojiRenameRequestBuilder> req);

    // ------------------------------
    // admin.functions
    // ------------------------------

    CompletableFuture<AdminFunctionsListResponse> adminFunctionsList(AdminFunctionsListRequest req);

    CompletableFuture<AdminFunctionsListResponse> adminFunctionsList(RequestConfigurator<AdminFunctionsListRequest.AdminFunctionsListRequestBuilder> req);

    CompletableFuture<AdminFunctionsPermissionsLookupResponse> adminFunctionsPermissionsLookup(AdminFunctionsPermissionsLookupRequest req);

    CompletableFuture<AdminFunctionsPermissionsLookupResponse> adminFunctionsPermissionsLookup(RequestConfigurator<AdminFunctionsPermissionsLookupRequest.AdminFunctionsPermissionsLookupRequestBuilder> req);

    CompletableFuture<AdminFunctionsPermissionsSetResponse> adminFunctionsPermissionsSet(AdminFunctionsPermissionsSetRequest req);

    CompletableFuture<AdminFunctionsPermissionsSetResponse> adminFunctionsPermissionsSet(RequestConfigurator<AdminFunctionsPermissionsSetRequest.AdminFunctionsPermissionsSetRequestBuilder> req);

    // ------------------------------
    // admin.inviteRequests
    // ------------------------------

    CompletableFuture<AdminInviteRequestsApproveResponse> adminInviteRequestsApprove(AdminInviteRequestsApproveRequest req);

    CompletableFuture<AdminInviteRequestsApproveResponse> adminInviteRequestsApprove(RequestConfigurator<AdminInviteRequestsApproveRequest.AdminInviteRequestsApproveRequestBuilder> req);

    CompletableFuture<AdminInviteRequestsDenyResponse> adminInviteRequestsDeny(AdminInviteRequestsDenyRequest req);

    CompletableFuture<AdminInviteRequestsDenyResponse> adminInviteRequestsDeny(RequestConfigurator<AdminInviteRequestsDenyRequest.AdminInviteRequestsDenyRequestBuilder> req);

    CompletableFuture<AdminInviteRequestsListResponse> adminInviteRequestsList(AdminInviteRequestsListRequest req);

    CompletableFuture<AdminInviteRequestsListResponse> adminInviteRequestsList(RequestConfigurator<AdminInviteRequestsListRequest.AdminInviteRequestsListRequestBuilder> req);

    CompletableFuture<AdminInviteRequestsApprovedListResponse> adminInviteRequestsApprovedList(AdminInviteRequestsApprovedListRequest req);

    CompletableFuture<AdminInviteRequestsApprovedListResponse> adminInviteRequestsApprovedList(RequestConfigurator<AdminInviteRequestsApprovedListRequest.AdminInviteRequestsApprovedListRequestBuilder> req);

    CompletableFuture<AdminInviteRequestsDeniedListResponse> adminInviteRequestsDeniedList(AdminInviteRequestsDeniedListRequest req);

    CompletableFuture<AdminInviteRequestsDeniedListResponse> adminInviteRequestsDeniedList(RequestConfigurator<AdminInviteRequestsDeniedListRequest.AdminInviteRequestsDeniedListRequestBuilder> req);

    // ------------------------------
    // admin.roles
    // ------------------------------

    CompletableFuture<AdminRolesListAssignmentsResponse> adminRolesListAssignments(AdminRolesListAssignmentsRequest req);

    CompletableFuture<AdminRolesListAssignmentsResponse> adminRolesListAssignments(RequestConfigurator<AdminRolesListAssignmentsRequest.AdminRolesListAssignmentsRequestBuilder> req);

    CompletableFuture<AdminRolesAddAssignmentsResponse> adminRolesAddAssignments(AdminRolesAddAssignmentsRequest req);

    CompletableFuture<AdminRolesAddAssignmentsResponse> adminRolesAddAssignments(RequestConfigurator<AdminRolesAddAssignmentsRequest.AdminRolesAddAssignmentsRequestBuilder> req);

    CompletableFuture<AdminRolesRemoveAssignmentsResponse> adminRolesRemoveAssignments(AdminRolesRemoveAssignmentsRequest req);

    CompletableFuture<AdminRolesRemoveAssignmentsResponse> adminRolesRemoveAssignments(RequestConfigurator<AdminRolesRemoveAssignmentsRequest.AdminRolesRemoveAssignmentsRequestBuilder> req);

    // ------------------------------
    // admin.teams.admins
    // ------------------------------

    CompletableFuture<AdminTeamsAdminsListResponse> adminTeamsAdminsList(AdminTeamsAdminsListRequest req);

    CompletableFuture<AdminTeamsAdminsListResponse> adminTeamsAdminsList(RequestConfigurator<AdminTeamsAdminsListRequest.AdminTeamsAdminsListRequestBuilder> req);

    // ------------------------------
    // admin.teams
    // ------------------------------

    CompletableFuture<AdminTeamsCreateResponse> adminTeamsCreate(AdminTeamsCreateRequest req);

    CompletableFuture<AdminTeamsCreateResponse> adminTeamsCreate(RequestConfigurator<AdminTeamsCreateRequest.AdminTeamsCreateRequestBuilder> req);

    CompletableFuture<AdminTeamsListResponse> adminTeamsList(AdminTeamsListRequest req);

    CompletableFuture<AdminTeamsListResponse> adminTeamsList(RequestConfigurator<AdminTeamsListRequest.AdminTeamsListRequestBuilder> req);

    // ------------------------------
    // admin.teams.owners
    // ------------------------------

    CompletableFuture<AdminTeamsOwnersListResponse> adminTeamsOwnersList(AdminTeamsOwnersListRequest req);

    CompletableFuture<AdminTeamsOwnersListResponse> adminTeamsOwnersList(RequestConfigurator<AdminTeamsOwnersListRequest.AdminTeamsOwnersListRequestBuilder> req);

    // ------------------------------
    // admin.teams.settings
    // ------------------------------

    CompletableFuture<AdminTeamsSettingsInfoResponse> adminTeamsSettingsInfo(AdminTeamsSettingsInfoRequest req);

    CompletableFuture<AdminTeamsSettingsInfoResponse> adminTeamsSettingsInfo(RequestConfigurator<AdminTeamsSettingsInfoRequest.AdminTeamsSettingsInfoRequestBuilder> req);

    CompletableFuture<AdminTeamsSettingsSetDefaultChannelsResponse> adminTeamsSettingsSetDefaultChannels(AdminTeamsSettingsSetDefaultChannelsRequest req);

    CompletableFuture<AdminTeamsSettingsSetDefaultChannelsResponse> adminTeamsSettingsSetDefaultChannels(RequestConfigurator<AdminTeamsSettingsSetDefaultChannelsRequest.AdminTeamsSettingsSetDefaultChannelsRequestBuilder> req);

    CompletableFuture<AdminTeamsSettingsSetDescriptionResponse> adminTeamsSettingsSetDescription(AdminTeamsSettingsSetDescriptionRequest req);

    CompletableFuture<AdminTeamsSettingsSetDescriptionResponse> adminTeamsSettingsSetDescription(RequestConfigurator<AdminTeamsSettingsSetDescriptionRequest.AdminTeamsSettingsSetDescriptionRequestBuilder> req);

    CompletableFuture<AdminTeamsSettingsSetDiscoverabilityResponse> adminTeamsSettingsSetDiscoverability(AdminTeamsSettingsSetDiscoverabilityRequest req);

    CompletableFuture<AdminTeamsSettingsSetDiscoverabilityResponse> adminTeamsSettingsSetDiscoverability(RequestConfigurator<AdminTeamsSettingsSetDiscoverabilityRequest.AdminTeamsSettingsSetDiscoverabilityRequestBuilder> req);

    CompletableFuture<AdminTeamsSettingsSetIconResponse> adminTeamsSettingsSetIcon(AdminTeamsSettingsSetIconRequest req);

    CompletableFuture<AdminTeamsSettingsSetIconResponse> adminTeamsSettingsSetIcon(RequestConfigurator<AdminTeamsSettingsSetIconRequest.AdminTeamsSettingsSetIconRequestBuilder> req);

    CompletableFuture<AdminTeamsSettingsSetNameResponse> adminTeamsSettingsSetName(AdminTeamsSettingsSetNameRequest req);

    CompletableFuture<AdminTeamsSettingsSetNameResponse> adminTeamsSettingsSetName(RequestConfigurator<AdminTeamsSettingsSetNameRequest.AdminTeamsSettingsSetNameRequestBuilder> req);

    // ------------------------------
    // admin.usergroups
    // ------------------------------

    CompletableFuture<AdminUsergroupsAddChannelsResponse> adminUsergroupsAddChannels(AdminUsergroupsAddChannelsRequest req);

    CompletableFuture<AdminUsergroupsAddChannelsResponse> adminUsergroupsAddChannels(RequestConfigurator<AdminUsergroupsAddChannelsRequest.AdminUsergroupsAddChannelsRequestBuilder> req);

    CompletableFuture<AdminUsergroupsAddTeamsResponse> adminUsergroupsAddTeams(AdminUsergroupsAddTeamsRequest req);

    CompletableFuture<AdminUsergroupsAddTeamsResponse> adminUsergroupsAddTeams(RequestConfigurator<AdminUsergroupsAddTeamsRequest.AdminUsergroupsAddTeamsRequestBuilder> req);

    CompletableFuture<AdminUsergroupsListChannelsResponse> adminUsergroupsListChannels(AdminUsergroupsListChannelsRequest req);

    CompletableFuture<AdminUsergroupsListChannelsResponse> adminUsergroupsListChannels(RequestConfigurator<AdminUsergroupsListChannelsRequest.AdminUsergroupsListChannelsRequestBuilder> req);

    CompletableFuture<AdminUsergroupsRemoveChannelsResponse> adminUsergroupsRemoveChannels(AdminUsergroupsRemoveChannelsRequest req);

    CompletableFuture<AdminUsergroupsRemoveChannelsResponse> adminUsergroupsRemoveChannels(RequestConfigurator<AdminUsergroupsRemoveChannelsRequest.AdminUsergroupsRemoveChannelsRequestBuilder> req);

    // ------------------------------
    // admin.users
    // ------------------------------

    CompletableFuture<AdminUsersAssignResponse> adminUsersAssign(AdminUsersAssignRequest req);

    CompletableFuture<AdminUsersAssignResponse> adminUsersAssign(RequestConfigurator<AdminUsersAssignRequest.AdminUsersAssignRequestBuilder> req);

    CompletableFuture<AdminUsersInviteResponse> adminUsersInvite(AdminUsersInviteRequest req);

    CompletableFuture<AdminUsersInviteResponse> adminUsersInvite(RequestConfigurator<AdminUsersInviteRequest.AdminUsersInviteRequestBuilder> req);

    CompletableFuture<AdminUsersListResponse> adminUsersList(AdminUsersListRequest req);

    CompletableFuture<AdminUsersListResponse> adminUsersList(RequestConfigurator<AdminUsersListRequest.AdminUsersListRequestBuilder> req);

    CompletableFuture<AdminUsersRemoveResponse> adminUsersRemove(AdminUsersRemoveRequest req);

    CompletableFuture<AdminUsersRemoveResponse> adminUsersRemove(RequestConfigurator<AdminUsersRemoveRequest.AdminUsersRemoveRequestBuilder> req);

    CompletableFuture<AdminUsersSetAdminResponse> adminUsersSetAdmin(AdminUsersSetAdminRequest req);

    CompletableFuture<AdminUsersSetAdminResponse> adminUsersSetAdmin(RequestConfigurator<AdminUsersSetAdminRequest.AdminUsersSetAdminRequestBuilder> req);

    CompletableFuture<AdminUsersSetExpirationResponse> adminUsersSetExpiration(AdminUsersSetExpirationRequest req);

    CompletableFuture<AdminUsersSetExpirationResponse> adminUsersSetExpiration(RequestConfigurator<AdminUsersSetExpirationRequest.AdminUsersSetExpirationRequestBuilder> req);

    CompletableFuture<AdminUsersSetOwnerResponse> adminUsersSetOwner(AdminUsersSetOwnerRequest req);

    CompletableFuture<AdminUsersSetOwnerResponse> adminUsersSetOwner(RequestConfigurator<AdminUsersSetOwnerRequest.AdminUsersSetOwnerRequestBuilder> req);

    CompletableFuture<AdminUsersSetRegularResponse> adminUsersSetRegular(AdminUsersSetRegularRequest req);

    CompletableFuture<AdminUsersSetRegularResponse> adminUsersSetRegular(RequestConfigurator<AdminUsersSetRegularRequest.AdminUsersSetRegularRequestBuilder> req);

    // ------------------------------
    // admin.users.session
    // ------------------------------

    CompletableFuture<AdminUsersSessionInvalidateResponse> adminUsersSessionInvalidate(AdminUsersSessionInvalidateRequest req);

    CompletableFuture<AdminUsersSessionInvalidateResponse> adminUsersSessionInvalidate(RequestConfigurator<AdminUsersSessionInvalidateRequest.AdminUsersSessionInvalidateRequestBuilder> req);

    CompletableFuture<AdminUsersSessionListResponse> adminUsersSessionList(AdminUsersSessionListRequest req);

    CompletableFuture<AdminUsersSessionListResponse> adminUsersSessionList(RequestConfigurator<AdminUsersSessionListRequest.AdminUsersSessionListRequestBuilder> req);

    CompletableFuture<AdminUsersSessionResetResponse> adminUsersSessionReset(AdminUsersSessionResetRequest req);

    CompletableFuture<AdminUsersSessionResetResponse> adminUsersSessionReset(RequestConfigurator<AdminUsersSessionResetRequest.AdminUsersSessionResetRequestBuilder> req);

    CompletableFuture<AdminUsersSessionResetBulkResponse> adminUsersSessionResetBulk(AdminUsersSessionResetBulkRequest req);

    CompletableFuture<AdminUsersSessionResetBulkResponse> adminUsersSessionResetBulk(RequestConfigurator<AdminUsersSessionResetBulkRequest.AdminUsersSessionResetBulkRequestBuilder> req);

    CompletableFuture<AdminUsersSessionGetSettingsResponse> adminUsersSessionGetSettings(AdminUsersSessionGetSettingsRequest req);

    CompletableFuture<AdminUsersSessionGetSettingsResponse> adminUsersSessionGetSettings(RequestConfigurator<AdminUsersSessionGetSettingsRequest.AdminUsersSessionGetSettingsRequestBuilder> req);

    CompletableFuture<AdminUsersSessionSetSettingsResponse> adminUsersSessionSetSettings(AdminUsersSessionSetSettingsRequest req);

    CompletableFuture<AdminUsersSessionSetSettingsResponse> adminUsersSessionSetSettings(RequestConfigurator<AdminUsersSessionSetSettingsRequest.AdminUsersSessionSetSettingsRequestBuilder> req);

    CompletableFuture<AdminUsersSessionClearSettingsResponse> adminUsersSessionClearSettings(AdminUsersSessionClearSettingsRequest req);

    CompletableFuture<AdminUsersSessionClearSettingsResponse> adminUsersSessionClearSettings(RequestConfigurator<AdminUsersSessionClearSettingsRequest.AdminUsersSessionClearSettingsRequestBuilder> req);

    // ------------------------------
    // admin.users.unsupportedVersions
    // ------------------------------

    CompletableFuture<AdminUsersUnsupportedVersionsExportResponse> adminUsersUnsupportedVersionsExport(AdminUsersUnsupportedVersionsExportRequest req);

    CompletableFuture<AdminUsersUnsupportedVersionsExportResponse> adminUsersUnsupportedVersionsExport(RequestConfigurator<AdminUsersUnsupportedVersionsExportRequest.AdminUsersUnsupportedVersionsExportRequestBuilder> req);

    // ------------------------------
    // admin.workflows
    // ------------------------------

    CompletableFuture<AdminWorkflowsCollaboratorsAddResponse> adminWorkflowsCollaboratorsAdd(AdminWorkflowsCollaboratorsAddRequest req);

    CompletableFuture<AdminWorkflowsCollaboratorsAddResponse> adminWorkflowsCollaboratorsAdd(RequestConfigurator<AdminWorkflowsCollaboratorsAddRequest.AdminWorkflowsCollaboratorsAddRequestBuilder> req);

    CompletableFuture<AdminWorkflowsCollaboratorsRemoveResponse> adminWorkflowsCollaboratorsRemove(AdminWorkflowsCollaboratorsRemoveRequest req);

    CompletableFuture<AdminWorkflowsCollaboratorsRemoveResponse> adminWorkflowsCollaboratorsRemove(RequestConfigurator<AdminWorkflowsCollaboratorsRemoveRequest.AdminWorkflowsCollaboratorsRemoveRequestBuilder> req);

    CompletableFuture<AdminWorkflowsPermissionsLookupResponse> adminWorkflowsPermissionsLookup(AdminWorkflowsPermissionsLookupRequest req);

    CompletableFuture<AdminWorkflowsPermissionsLookupResponse> adminWorkflowsPermissionsLookup(RequestConfigurator<AdminWorkflowsPermissionsLookupRequest.AdminWorkflowsPermissionsLookupRequestBuilder> req);

    CompletableFuture<AdminWorkflowsSearchResponse> adminWorkflowsSearch(AdminWorkflowsSearchRequest req);

    CompletableFuture<AdminWorkflowsSearchResponse> adminWorkflowsSearch(RequestConfigurator<AdminWorkflowsSearchRequest.AdminWorkflowsSearchRequestBuilder> req);

    CompletableFuture<AdminWorkflowsUnpublishResponse> adminWorkflowsUnpublish(AdminWorkflowsUnpublishRequest req);

    CompletableFuture<AdminWorkflowsUnpublishResponse> adminWorkflowsUnpublish(RequestConfigurator<AdminWorkflowsUnpublishRequest.AdminWorkflowsUnpublishRequestBuilder> req);

    // ------------------------------
    // api
    // ------------------------------

    CompletableFuture<ApiTestResponse> apiTest(ApiTestRequest req);

    CompletableFuture<ApiTestResponse> apiTest(RequestConfigurator<ApiTestRequest.ApiTestRequestBuilder> req);

    // ------------------------------
    // apps
    // ------------------------------

    CompletableFuture<AppsUninstallResponse> appsUninstall(AppsUninstallRequest req);

    CompletableFuture<AppsUninstallResponse> appsUninstall(RequestConfigurator<AppsUninstallRequest.AppsUninstallRequestBuilder> req);

    // ------------------------------
    // apps.connections
    // ------------------------------

    CompletableFuture<AppsConnectionsOpenResponse> appsConnectionsOpen(AppsConnectionsOpenRequest req);

    CompletableFuture<AppsConnectionsOpenResponse> appsConnectionsOpen(RequestConfigurator<AppsConnectionsOpenRequest.AppsConnectionsOpenRequestBuilder> req);

    // ------------------------------
    // apps.event.authorizations
    // ------------------------------

    CompletableFuture<AppsEventAuthorizationsListResponse> appsEventAuthorizationsList(AppsEventAuthorizationsListRequest req);

    CompletableFuture<AppsEventAuthorizationsListResponse> appsEventAuthorizationsList(RequestConfigurator<AppsEventAuthorizationsListRequest.AppsEventAuthorizationsListRequestBuilder> req);

    // ------------------------------
    // apps.manifest
    // ------------------------------

    CompletableFuture<AppsManifestCreateResponse> appsManifestCreate(AppsManifestCreateRequest req);

    CompletableFuture<AppsManifestCreateResponse> appsManifestCreate(RequestConfigurator<AppsManifestCreateRequest.AppsManifestCreateRequestBuilder> req);


    CompletableFuture<AppsManifestDeleteResponse> appsManifestDelete(AppsManifestDeleteRequest req);

    CompletableFuture<AppsManifestDeleteResponse> appsManifestDelete(RequestConfigurator<AppsManifestDeleteRequest.AppsManifestDeleteRequestBuilder> req);

    CompletableFuture<AppsManifestExportResponse> appsManifestExport(AppsManifestExportRequest req);

    CompletableFuture<AppsManifestExportResponse> appsManifestExport(RequestConfigurator<AppsManifestExportRequest.AppsManifestExportRequestBuilder> req);

    CompletableFuture<AppsManifestUpdateResponse> appsManifestUpdate(AppsManifestUpdateRequest req);

    CompletableFuture<AppsManifestUpdateResponse> appsManifestUpdate(RequestConfigurator<AppsManifestUpdateRequest.AppsManifestUpdateRequestBuilder> req);

    CompletableFuture<AppsManifestValidateResponse> appsManifestValidate(AppsManifestValidateRequest req);

    CompletableFuture<AppsManifestValidateResponse> appsManifestValidate(RequestConfigurator<AppsManifestValidateRequest.AppsManifestValidateRequestBuilder> req);

    // ------------------------------
    // auth
    // ------------------------------

    CompletableFuture<AuthRevokeResponse> authRevoke(AuthRevokeRequest req);

    CompletableFuture<AuthRevokeResponse> authRevoke(RequestConfigurator<AuthRevokeRequest.AuthRevokeRequestBuilder> req);

    CompletableFuture<AuthTestResponse> authTest(AuthTestRequest req);

    CompletableFuture<AuthTestResponse> authTest(RequestConfigurator<AuthTestRequest.AuthTestRequestBuilder> req);

    // ------------------------------
    // auth.teams
    // ------------------------------

    CompletableFuture<AuthTeamsListResponse> authTeamsList(AuthTeamsListRequest req);

    CompletableFuture<AuthTeamsListResponse> authTeamsList(RequestConfigurator<AuthTeamsListRequest.AuthTeamsListRequestBuilder> req);

    // ------------------------------
    // bookmarks
    // ------------------------------

    CompletableFuture<BookmarksAddResponse> bookmarksAdd(BookmarksAddRequest req);

    CompletableFuture<BookmarksAddResponse> bookmarksAdd(RequestConfigurator<BookmarksAddRequest.BookmarksAddRequestBuilder> req);

    CompletableFuture<BookmarksEditResponse> bookmarksEdit(BookmarksEditRequest req);

    CompletableFuture<BookmarksEditResponse> bookmarksEdit(RequestConfigurator<BookmarksEditRequest.BookmarksEditRequestBuilder> req);

    CompletableFuture<BookmarksListResponse> bookmarksList(BookmarksListRequest req);

    CompletableFuture<BookmarksListResponse> bookmarksList(RequestConfigurator<BookmarksListRequest.BookmarksListRequestBuilder> req);

    CompletableFuture<BookmarksRemoveResponse> bookmarksRemove(BookmarksRemoveRequest req);

    CompletableFuture<BookmarksRemoveResponse> bookmarksRemove(RequestConfigurator<BookmarksRemoveRequest.BookmarksRemoveRequestBuilder> req);

    // ------------------------------
    // bots
    // ------------------------------

    CompletableFuture<BotsInfoResponse> botsInfo(BotsInfoRequest req);

    CompletableFuture<BotsInfoResponse> botsInfo(RequestConfigurator<BotsInfoRequest.BotsInfoRequestBuilder> req);

    // ------------------------------
    // calls
    // ------------------------------

    CompletableFuture<CallsAddResponse> callsAdd(CallsAddRequest req);

    CompletableFuture<CallsAddResponse> callsAdd(RequestConfigurator<CallsAddRequest.CallsAddRequestBuilder> req);

    CompletableFuture<CallsEndResponse> callsEnd(CallsEndRequest req);

    CompletableFuture<CallsEndResponse> callsEnd(RequestConfigurator<CallsEndRequest.CallsEndRequestBuilder> req);

    CompletableFuture<CallsInfoResponse> callsInfo(CallsInfoRequest req);

    CompletableFuture<CallsInfoResponse> callsInfo(RequestConfigurator<CallsInfoRequest.CallsInfoRequestBuilder> req);

    CompletableFuture<CallsUpdateResponse> callsUpdate(CallsUpdateRequest req);

    CompletableFuture<CallsUpdateResponse> callsUpdate(RequestConfigurator<CallsUpdateRequest.CallsUpdateRequestBuilder> req);

    // ------------------------------
    // calls.participants
    // ------------------------------

    CompletableFuture<CallsParticipantsAddResponse> callsParticipantsAdd(CallsParticipantsAddRequest req);

    CompletableFuture<CallsParticipantsAddResponse> callsParticipantsAdd(RequestConfigurator<CallsParticipantsAddRequest.CallsParticipantsAddRequestBuilder> req);

    CompletableFuture<CallsParticipantsRemoveResponse> callsParticipantsRemove(CallsParticipantsRemoveRequest req);

    CompletableFuture<CallsParticipantsRemoveResponse> callsParticipantsRemove(RequestConfigurator<CallsParticipantsRemoveRequest.CallsParticipantsRemoveRequestBuilder> req);

    // ------------------------------
    // canvases
    // ------------------------------

    CompletableFuture<CanvasesCreateResponse> canvasesCreate(CanvasesCreateRequest req);

    CompletableFuture<CanvasesCreateResponse> canvasesCreate(RequestConfigurator<CanvasesCreateRequest.CanvasesCreateRequestBuilder> req);

    CompletableFuture<CanvasesEditResponse> canvasesEdit(CanvasesEditRequest req);

    CompletableFuture<CanvasesEditResponse> canvasesEdit(RequestConfigurator<CanvasesEditRequest.CanvasesEditRequestBuilder> req);

    CompletableFuture<CanvasesDeleteResponse> canvasesDelete(CanvasesDeleteRequest req);

    CompletableFuture<CanvasesDeleteResponse> canvasesDelete(RequestConfigurator<CanvasesDeleteRequest.CanvasesDeleteRequestBuilder> req);

    CompletableFuture<CanvasesAccessSetResponse> canvasesAccessSet(CanvasesAccessSetRequest req);

    CompletableFuture<CanvasesAccessSetResponse> canvasesAccessSet(RequestConfigurator<CanvasesAccessSetRequest.CanvasesAccessSetRequestBuilder> req);

    CompletableFuture<CanvasesAccessDeleteResponse> canvasesAccessDelete(CanvasesAccessDeleteRequest req);

    CompletableFuture<CanvasesAccessDeleteResponse> canvasesAccessDelete(RequestConfigurator<CanvasesAccessDeleteRequest.CanvasesAccessDeleteRequestBuilder> req);

    CompletableFuture<CanvasesSectionsLookupResponse> canvasesSectionsLookup(CanvasesSectionsLookupRequest req);

    CompletableFuture<CanvasesSectionsLookupResponse> canvasesSectionsLookup(RequestConfigurator<CanvasesSectionsLookupRequest.CanvasesSectionsLookupRequestBuilder> req);

    // ------------------------------
    // chat
    // ------------------------------

    CompletableFuture<ChatGetPermalinkResponse> chatGetPermalink(ChatGetPermalinkRequest req);

    CompletableFuture<ChatGetPermalinkResponse> chatGetPermalink(RequestConfigurator<ChatGetPermalinkRequest.ChatGetPermalinkRequestBuilder> req);

    CompletableFuture<ChatDeleteResponse> chatDelete(ChatDeleteRequest req);

    CompletableFuture<ChatDeleteResponse> chatDelete(RequestConfigurator<ChatDeleteRequest.ChatDeleteRequestBuilder> req);

    CompletableFuture<ChatDeleteScheduledMessageResponse> chatDeleteScheduledMessage(ChatDeleteScheduledMessageRequest req);

    CompletableFuture<ChatDeleteScheduledMessageResponse> chatDeleteScheduledMessage(RequestConfigurator<ChatDeleteScheduledMessageRequest.ChatDeleteScheduledMessageRequestBuilder> req);

    CompletableFuture<ChatMeMessageResponse> chatMeMessage(ChatMeMessageRequest req);

    CompletableFuture<ChatMeMessageResponse> chatMeMessage(RequestConfigurator<ChatMeMessageRequest.ChatMeMessageRequestBuilder> req);

    CompletableFuture<ChatPostEphemeralResponse> chatPostEphemeral(ChatPostEphemeralRequest req);

    CompletableFuture<ChatPostEphemeralResponse> chatPostEphemeral(RequestConfigurator<ChatPostEphemeralRequest.ChatPostEphemeralRequestBuilder> req);

    CompletableFuture<ChatPostMessageResponse> chatPostMessage(ChatPostMessageRequest req);

    CompletableFuture<ChatPostMessageResponse> chatPostMessage(RequestConfigurator<ChatPostMessageRequest.ChatPostMessageRequestBuilder> req);

    CompletableFuture<ChatScheduleMessageResponse> chatScheduleMessage(ChatScheduleMessageRequest req);

    CompletableFuture<ChatScheduleMessageResponse> chatScheduleMessage(RequestConfigurator<ChatScheduleMessageRequest.ChatScheduleMessageRequestBuilder> req);

    CompletableFuture<ChatUpdateResponse> chatUpdate(ChatUpdateRequest req);

    CompletableFuture<ChatUpdateResponse> chatUpdate(RequestConfigurator<ChatUpdateRequest.ChatUpdateRequestBuilder> req);

    CompletableFuture<ChatUnfurlResponse> chatUnfurl(ChatUnfurlRequest req);

    CompletableFuture<ChatUnfurlResponse> chatUnfurl(RequestConfigurator<ChatUnfurlRequest.ChatUnfurlRequestBuilder> req);

    // ------------------------------
    // chat.scheduledMessages
    // ------------------------------

    CompletableFuture<ChatScheduledMessagesListResponse> chatScheduledMessagesList(ChatScheduledMessagesListRequest req);

    CompletableFuture<ChatScheduledMessagesListResponse> chatScheduledMessagesList(RequestConfigurator<ChatScheduledMessagesListRequest.ChatScheduledMessagesListRequestBuilder> req);

    // ------------------------------
    // conversations
    // ------------------------------

    CompletableFuture<ConversationsArchiveResponse> conversationsArchive(ConversationsArchiveRequest req);

    CompletableFuture<ConversationsArchiveResponse> conversationsArchive(RequestConfigurator<ConversationsArchiveRequest.ConversationsArchiveRequestBuilder> req);

    CompletableFuture<ConversationsCloseResponse> conversationsClose(ConversationsCloseRequest req);

    CompletableFuture<ConversationsCloseResponse> conversationsClose(RequestConfigurator<ConversationsCloseRequest.ConversationsCloseRequestBuilder> req);

    CompletableFuture<ConversationsCreateResponse> conversationsCreate(ConversationsCreateRequest req);

    CompletableFuture<ConversationsCreateResponse> conversationsCreate(RequestConfigurator<ConversationsCreateRequest.ConversationsCreateRequestBuilder> req);

    CompletableFuture<ConversationsHistoryResponse> conversationsHistory(ConversationsHistoryRequest req);

    CompletableFuture<ConversationsHistoryResponse> conversationsHistory(RequestConfigurator<ConversationsHistoryRequest.ConversationsHistoryRequestBuilder> req);

    CompletableFuture<ConversationsInfoResponse> conversationsInfo(ConversationsInfoRequest req);

    CompletableFuture<ConversationsInfoResponse> conversationsInfo(RequestConfigurator<ConversationsInfoRequest.ConversationsInfoRequestBuilder> req);

    CompletableFuture<ConversationsInviteResponse> conversationsInvite(ConversationsInviteRequest req);

    CompletableFuture<ConversationsInviteResponse> conversationsInvite(RequestConfigurator<ConversationsInviteRequest.ConversationsInviteRequestBuilder> req);

    CompletableFuture<ConversationsJoinResponse> conversationsJoin(ConversationsJoinRequest req);

    CompletableFuture<ConversationsJoinResponse> conversationsJoin(RequestConfigurator<ConversationsJoinRequest.ConversationsJoinRequestBuilder> req);

    CompletableFuture<ConversationsKickResponse> conversationsKick(ConversationsKickRequest req);

    CompletableFuture<ConversationsKickResponse> conversationsKick(RequestConfigurator<ConversationsKickRequest.ConversationsKickRequestBuilder> req);

    CompletableFuture<ConversationsLeaveResponse> conversationsLeave(ConversationsLeaveRequest req);

    CompletableFuture<ConversationsLeaveResponse> conversationsLeave(RequestConfigurator<ConversationsLeaveRequest.ConversationsLeaveRequestBuilder> req);

    CompletableFuture<ConversationsListResponse> conversationsList(ConversationsListRequest req);

    CompletableFuture<ConversationsListResponse> conversationsList(RequestConfigurator<ConversationsListRequest.ConversationsListRequestBuilder> req);

    CompletableFuture<ConversationsMarkResponse> conversationsMark(ConversationsMarkRequest req);

    CompletableFuture<ConversationsMarkResponse> conversationsMark(RequestConfigurator<ConversationsMarkRequest.ConversationsMarkRequestBuilder> req);

    CompletableFuture<ConversationsMembersResponse> conversationsMembers(ConversationsMembersRequest req);

    CompletableFuture<ConversationsMembersResponse> conversationsMembers(RequestConfigurator<ConversationsMembersRequest.ConversationsMembersRequestBuilder> req);

    CompletableFuture<ConversationsOpenResponse> conversationsOpen(ConversationsOpenRequest req);

    CompletableFuture<ConversationsOpenResponse> conversationsOpen(RequestConfigurator<ConversationsOpenRequest.ConversationsOpenRequestBuilder> req);

    CompletableFuture<ConversationsRenameResponse> conversationsRename(ConversationsRenameRequest req);

    CompletableFuture<ConversationsRenameResponse> conversationsRename(RequestConfigurator<ConversationsRenameRequest.ConversationsRenameRequestBuilder> req);

    CompletableFuture<ConversationsRepliesResponse> conversationsReplies(ConversationsRepliesRequest req);

    CompletableFuture<ConversationsRepliesResponse> conversationsReplies(RequestConfigurator<ConversationsRepliesRequest.ConversationsRepliesRequestBuilder> req);

    CompletableFuture<ConversationsSetPurposeResponse> conversationsSetPurpose(ConversationsSetPurposeRequest req);

    CompletableFuture<ConversationsSetPurposeResponse> conversationsSetPurpose(RequestConfigurator<ConversationsSetPurposeRequest.ConversationsSetPurposeRequestBuilder> req);

    CompletableFuture<ConversationsSetTopicResponse> conversationsSetTopic(ConversationsSetTopicRequest req);

    CompletableFuture<ConversationsSetTopicResponse> conversationsSetTopic(RequestConfigurator<ConversationsSetTopicRequest.ConversationsSetTopicRequestBuilder> req);

    CompletableFuture<ConversationsUnarchiveResponse> conversationsUnarchive(ConversationsUnarchiveRequest req);

    CompletableFuture<ConversationsUnarchiveResponse> conversationsUnarchive(RequestConfigurator<ConversationsUnarchiveRequest.ConversationsUnarchiveRequestBuilder> req);

    CompletableFuture<ConversationsCanvasesCreateResponse> conversationsCanvasesCreate(ConversationsCanvasesCreateRequest req);

    CompletableFuture<ConversationsCanvasesCreateResponse> conversationsCanvasesCreate(RequestConfigurator<ConversationsCanvasesCreateRequest.ConversationsCanvasesCreateRequestBuilder> req);

    // -------------
    // Slack Connect

    CompletableFuture<ConversationsInviteSharedResponse> conversationsInviteShared(ConversationsInviteSharedRequest req);

    CompletableFuture<ConversationsInviteSharedResponse> conversationsInviteShared(RequestConfigurator<ConversationsInviteSharedRequest.ConversationsInviteSharedRequestBuilder> req);

    CompletableFuture<ConversationsAcceptSharedInviteResponse> conversationsAcceptSharedInvite(ConversationsAcceptSharedInviteRequest req);

    CompletableFuture<ConversationsAcceptSharedInviteResponse> conversationsAcceptSharedInvite(RequestConfigurator<ConversationsAcceptSharedInviteRequest.ConversationsAcceptSharedInviteRequestBuilder> req);

    CompletableFuture<ConversationsApproveSharedInviteResponse> conversationsApproveSharedInvite(ConversationsApproveSharedInviteRequest req);

    CompletableFuture<ConversationsApproveSharedInviteResponse> conversationsApproveSharedInvite(RequestConfigurator<ConversationsApproveSharedInviteRequest.ConversationsApproveSharedInviteRequestBuilder> req);

    CompletableFuture<ConversationsDeclineSharedInviteResponse> conversationsDeclineSharedInvite(ConversationsDeclineSharedInviteRequest req);

    CompletableFuture<ConversationsDeclineSharedInviteResponse> conversationsDeclineSharedInvite(RequestConfigurator<ConversationsDeclineSharedInviteRequest.ConversationsDeclineSharedInviteRequestBuilder> req);

    CompletableFuture<ConversationsListConnectInvitesResponse> conversationsListConnectInvites(ConversationsListConnectInvitesRequest req);

    CompletableFuture<ConversationsListConnectInvitesResponse> conversationsListConnectInvites(RequestConfigurator<ConversationsListConnectInvitesRequest.ConversationsListConnectInvitesRequestBuilder> req);

    // ------------------------------
    // dialog
    // ------------------------------

    CompletableFuture<DialogOpenResponse> dialogOpen(DialogOpenRequest req);

    CompletableFuture<DialogOpenResponse> dialogOpen(RequestConfigurator<DialogOpenRequest.DialogOpenRequestBuilder> req);

    // ------------------------------
    // dnd
    // ------------------------------

    CompletableFuture<DndEndDndResponse> dndEndDnd(DndEndDndRequest req);

    CompletableFuture<DndEndDndResponse> dndEndDnd(RequestConfigurator<DndEndDndRequest.DndEndDndRequestBuilder> req);

    CompletableFuture<DndEndSnoozeResponse> dndEndSnooze(DndEndSnoozeRequest req);

    CompletableFuture<DndEndSnoozeResponse> dndEndSnooze(RequestConfigurator<DndEndSnoozeRequest.DndEndSnoozeRequestBuilder> req);

    CompletableFuture<DndInfoResponse> dndInfo(DndInfoRequest req);

    CompletableFuture<DndInfoResponse> dndInfo(RequestConfigurator<DndInfoRequest.DndInfoRequestBuilder> req);

    CompletableFuture<DndSetSnoozeResponse> dndSetSnooze(DndSetSnoozeRequest req);

    CompletableFuture<DndSetSnoozeResponse> dndSetSnooze(RequestConfigurator<DndSetSnoozeRequest.DndSetSnoozeRequestBuilder> req);

    CompletableFuture<DndTeamInfoResponse> dndTeamInfo(DndTeamInfoRequest req);

    CompletableFuture<DndTeamInfoResponse> dndTeamInfo(RequestConfigurator<DndTeamInfoRequest.DndTeamInfoRequestBuilder> req);

    // ------------------------------
    // emoji
    // ------------------------------

    CompletableFuture<EmojiListResponse> emojiList(EmojiListRequest req);

    CompletableFuture<EmojiListResponse> emojiList(RequestConfigurator<EmojiListRequest.EmojiListRequestBuilder> req);

    // ------------------------------
    // files
    // ------------------------------

    CompletableFuture<FilesDeleteResponse> filesDelete(FilesDeleteRequest req);

    CompletableFuture<FilesDeleteResponse> filesDelete(RequestConfigurator<FilesDeleteRequest.FilesDeleteRequestBuilder> req);

    CompletableFuture<FilesInfoResponse> filesInfo(FilesInfoRequest req);

    CompletableFuture<FilesInfoResponse> filesInfo(RequestConfigurator<FilesInfoRequest.FilesInfoRequestBuilder> req);

    CompletableFuture<FilesListResponse> filesList(FilesListRequest req);

    CompletableFuture<FilesListResponse> filesList(RequestConfigurator<FilesListRequest.FilesListRequestBuilder> req);

    CompletableFuture<FilesRevokePublicURLResponse> filesRevokePublicURL(FilesRevokePublicURLRequest req);

    CompletableFuture<FilesRevokePublicURLResponse> filesRevokePublicURL(RequestConfigurator<FilesRevokePublicURLRequest.FilesRevokePublicURLRequestBuilder> req);

    CompletableFuture<FilesSharedPublicURLResponse> filesSharedPublicURL(FilesSharedPublicURLRequest req);

    CompletableFuture<FilesSharedPublicURLResponse> filesSharedPublicURL(RequestConfigurator<FilesSharedPublicURLRequest.FilesSharedPublicURLRequestBuilder> req);

    @Deprecated
        // https://api.slack.com/changelog/2024-04-a-better-way-to-upload-files-is-here-to-stay
    CompletableFuture<FilesUploadResponse> filesUpload(FilesUploadRequest req);

    @Deprecated
        // https://api.slack.com/changelog/2024-04-a-better-way-to-upload-files-is-here-to-stay
    CompletableFuture<FilesUploadResponse> filesUpload(RequestConfigurator<FilesUploadRequest.FilesUploadRequestBuilder> req);

    CompletableFuture<FilesGetUploadURLExternalResponse> filesGetUploadURLExternal(FilesGetUploadURLExternalRequest req);

    CompletableFuture<FilesGetUploadURLExternalResponse> filesGetUploadURLExternal(RequestConfigurator<FilesGetUploadURLExternalRequest.FilesGetUploadURLExternalRequestBuilder> req);

    CompletableFuture<FilesCompleteUploadExternalResponse> filesCompleteUploadExternal(FilesCompleteUploadExternalRequest req);

    CompletableFuture<FilesCompleteUploadExternalResponse> filesCompleteUploadExternal(RequestConfigurator<FilesCompleteUploadExternalRequest.FilesCompleteUploadExternalRequestBuilder> req);

    CompletableFuture<FilesUploadV2Response> filesUploadV2(FilesUploadV2Request req);

    CompletableFuture<FilesUploadV2Response> filesUploadV2(RequestConfigurator<FilesUploadV2Request.FilesUploadV2RequestBuilder> req);

    // ------------------------------
    // files.remote
    // ------------------------------

    CompletableFuture<FilesRemoteAddResponse> filesRemoteAdd(FilesRemoteAddRequest req);

    CompletableFuture<FilesRemoteAddResponse> filesRemoteAdd(RequestConfigurator<FilesRemoteAddRequest.FilesRemoteAddRequestBuilder> req);

    CompletableFuture<FilesRemoteInfoResponse> filesRemoteInfo(FilesRemoteInfoRequest req);

    CompletableFuture<FilesRemoteInfoResponse> filesRemoteInfo(RequestConfigurator<FilesRemoteInfoRequest.FilesRemoteInfoRequestBuilder> req);

    CompletableFuture<FilesRemoteListResponse> filesRemoteList(FilesRemoteListRequest req);

    CompletableFuture<FilesRemoteListResponse> filesRemoteList(RequestConfigurator<FilesRemoteListRequest.FilesRemoteListRequestBuilder> req);

    CompletableFuture<FilesRemoteRemoveResponse> filesRemoteRemove(FilesRemoteRemoveRequest req);

    CompletableFuture<FilesRemoteRemoveResponse> filesRemoteRemove(RequestConfigurator<FilesRemoteRemoveRequest.FilesRemoteRemoveRequestBuilder> req);

    CompletableFuture<FilesRemoteShareResponse> filesRemoteShare(FilesRemoteShareRequest req);

    CompletableFuture<FilesRemoteShareResponse> filesRemoteShare(RequestConfigurator<FilesRemoteShareRequest.FilesRemoteShareRequestBuilder> req);

    CompletableFuture<FilesRemoteUpdateResponse> filesRemoteUpdate(FilesRemoteUpdateRequest req);

    CompletableFuture<FilesRemoteUpdateResponse> filesRemoteUpdate(RequestConfigurator<FilesRemoteUpdateRequest.FilesRemoteUpdateRequestBuilder> req);

    // ------------------------------
    // functions
    // ------------------------------

    CompletableFuture<FunctionsCompleteSuccessResponse> functionsCompleteSuccess(FunctionsCompleteSuccessRequest req);

    CompletableFuture<FunctionsCompleteSuccessResponse> functionsCompleteSuccess(RequestConfigurator<FunctionsCompleteSuccessRequest.FunctionsCompleteSuccessRequestBuilder> req);

    CompletableFuture<FunctionsCompleteErrorResponse> functionsCompleteError(FunctionsCompleteErrorRequest req);

    CompletableFuture<FunctionsCompleteErrorResponse> functionsCompleteError(RequestConfigurator<FunctionsCompleteErrorRequest.FunctionsCompleteErrorRequestBuilder> req);

    // ------------------------------
    // migration
    // ------------------------------

    CompletableFuture<MigrationExchangeResponse> migrationExchange(MigrationExchangeRequest req);

    CompletableFuture<MigrationExchangeResponse> migrationExchange(RequestConfigurator<MigrationExchangeRequest.MigrationExchangeRequestBuilder> req);

    // ------------------------------
    // oauth
    // ------------------------------

    CompletableFuture<OAuthAccessResponse> oauthAccess(OAuthAccessRequest req);

    CompletableFuture<OAuthAccessResponse> oauthAccess(RequestConfigurator<OAuthAccessRequest.OAuthAccessRequestBuilder> req);

    CompletableFuture<OAuthV2AccessResponse> oauthV2Access(OAuthV2AccessRequest req);

    CompletableFuture<OAuthV2AccessResponse> oauthV2Access(RequestConfigurator<OAuthV2AccessRequest.OAuthV2AccessRequestBuilder> req);

    CompletableFuture<OAuthV2ExchangeResponse> oauthV2Exchange(OAuthV2ExchangeRequest req);

    CompletableFuture<OAuthV2ExchangeResponse> oauthV2Exchange(RequestConfigurator<OAuthV2ExchangeRequest.OAuthV2ExchangeRequestBuilder> req);

    CompletableFuture<OAuthTokenResponse> oauthToken(OAuthTokenRequest req);

    CompletableFuture<OAuthTokenResponse> oauthToken(RequestConfigurator<OAuthTokenRequest.OAuthTokenRequestBuilder> req);

    // ------------------------------
    // openid.connect
    // ------------------------------

    CompletableFuture<OpenIDConnectTokenResponse> openIDConnectToken(OpenIDConnectTokenRequest req);

    CompletableFuture<OpenIDConnectTokenResponse> openIDConnectToken(RequestConfigurator<OpenIDConnectTokenRequest.OpenIDConnectTokenRequestBuilder> req);

    CompletableFuture<OpenIDConnectUserInfoResponse> openIDConnectUserInfo(OpenIDConnectUserInfoRequest req);

    CompletableFuture<OpenIDConnectUserInfoResponse> openIDConnectUserInfo(RequestConfigurator<OpenIDConnectUserInfoRequest.OpenIDConnectUserInfoRequestBuilder> req);

    // ------------------------------
    // pins
    // ------------------------------

    CompletableFuture<PinsAddResponse> pinsAdd(PinsAddRequest req);

    CompletableFuture<PinsAddResponse> pinsAdd(RequestConfigurator<PinsAddRequest.PinsAddRequestBuilder> req);

    CompletableFuture<PinsListResponse> pinsList(PinsListRequest req);

    CompletableFuture<PinsListResponse> pinsList(RequestConfigurator<PinsListRequest.PinsListRequestBuilder> req);

    CompletableFuture<PinsRemoveResponse> pinsRemove(PinsRemoveRequest req);

    CompletableFuture<PinsRemoveResponse> pinsRemove(RequestConfigurator<PinsRemoveRequest.PinsRemoveRequestBuilder> req);

    // ------------------------------
    // reactions
    // ------------------------------

    CompletableFuture<ReactionsAddResponse> reactionsAdd(ReactionsAddRequest req);

    CompletableFuture<ReactionsAddResponse> reactionsAdd(RequestConfigurator<ReactionsAddRequest.ReactionsAddRequestBuilder> req);

    CompletableFuture<ReactionsGetResponse> reactionsGet(ReactionsGetRequest req);

    CompletableFuture<ReactionsGetResponse> reactionsGet(RequestConfigurator<ReactionsGetRequest.ReactionsGetRequestBuilder> req);

    CompletableFuture<ReactionsListResponse> reactionsList(ReactionsListRequest req);

    CompletableFuture<ReactionsListResponse> reactionsList(RequestConfigurator<ReactionsListRequest.ReactionsListRequestBuilder> req);

    CompletableFuture<ReactionsRemoveResponse> reactionsRemove(ReactionsRemoveRequest req);

    CompletableFuture<ReactionsRemoveResponse> reactionsRemove(RequestConfigurator<ReactionsRemoveRequest.ReactionsRemoveRequestBuilder> req);

    // ------------------------------
    // reminders
    // ------------------------------

    CompletableFuture<RemindersAddResponse> remindersAdd(RemindersAddRequest req);

    CompletableFuture<RemindersAddResponse> remindersAdd(RequestConfigurator<RemindersAddRequest.RemindersAddRequestBuilder> req);

    CompletableFuture<RemindersCompleteResponse> remindersComplete(RemindersCompleteRequest req);

    CompletableFuture<RemindersCompleteResponse> remindersComplete(RequestConfigurator<RemindersCompleteRequest.RemindersCompleteRequestBuilder> req);

    CompletableFuture<RemindersDeleteResponse> remindersDelete(RemindersDeleteRequest req);

    CompletableFuture<RemindersDeleteResponse> remindersDelete(RequestConfigurator<RemindersDeleteRequest.RemindersDeleteRequestBuilder> req);

    CompletableFuture<RemindersInfoResponse> remindersInfo(RemindersInfoRequest req);

    CompletableFuture<RemindersInfoResponse> remindersInfo(RequestConfigurator<RemindersInfoRequest.RemindersInfoRequestBuilder> req);

    CompletableFuture<RemindersListResponse> remindersList(RemindersListRequest req);

    CompletableFuture<RemindersListResponse> remindersList(RequestConfigurator<RemindersListRequest.RemindersListRequestBuilder> req);

    // ------------------------------
    // rtm
    // ------------------------------

    @Deprecated
        // https://api.slack.com/changelog/2024-04-discontinuing-new-creation-of-classic-slack-apps-and-custom-bots
    CompletableFuture<RTMConnectResponse> rtmConnect(RTMConnectRequest req);

    @Deprecated
        // https://api.slack.com/changelog/2024-04-discontinuing-new-creation-of-classic-slack-apps-and-custom-bots
    CompletableFuture<RTMConnectResponse> rtmConnect(RequestConfigurator<RTMConnectRequest.RTMConnectRequestBuilder> req);

    @Deprecated
    CompletableFuture<RTMStartResponse> rtmStart(RTMStartRequest req);

    @Deprecated
    CompletableFuture<RTMStartResponse> rtmStart(RequestConfigurator<RTMStartRequest.RTMStartRequestBuilder> req);

    // ------------------------------
    // search
    // ------------------------------

    CompletableFuture<SearchAllResponse> searchAll(SearchAllRequest req);

    CompletableFuture<SearchAllResponse> searchAll(RequestConfigurator<SearchAllRequest.SearchAllRequestBuilder> req);

    CompletableFuture<SearchMessagesResponse> searchMessages(SearchMessagesRequest req);

    CompletableFuture<SearchMessagesResponse> searchMessages(RequestConfigurator<SearchMessagesRequest.SearchMessagesRequestBuilder> req);

    CompletableFuture<SearchFilesResponse> searchFiles(SearchFilesRequest req);

    CompletableFuture<SearchFilesResponse> searchFiles(RequestConfigurator<SearchFilesRequest.SearchFilesRequestBuilder> req);

    // ------------------------------
    // stars
    // ------------------------------

    @Deprecated
        // https://api.slack.com/changelog/2023-07-its-later-already-for-stars-and-reminders
    CompletableFuture<StarsAddResponse> starsAdd(StarsAddRequest req);

    @Deprecated
        // https://api.slack.com/changelog/2023-07-its-later-already-for-stars-and-reminders
    CompletableFuture<StarsAddResponse> starsAdd(RequestConfigurator<StarsAddRequest.StarsAddRequestBuilder> req);

    @Deprecated
        // https://api.slack.com/changelog/2023-07-its-later-already-for-stars-and-reminders
    CompletableFuture<StarsListResponse> starsList(StarsListRequest req);

    @Deprecated
        // https://api.slack.com/changelog/2023-07-its-later-already-for-stars-and-reminders
    CompletableFuture<StarsListResponse> starsList(RequestConfigurator<StarsListRequest.StarsListRequestBuilder> req);

    @Deprecated
        // https://api.slack.com/changelog/2023-07-its-later-already-for-stars-and-reminders
    CompletableFuture<StarsRemoveResponse> starsRemove(StarsRemoveRequest req);

    @Deprecated
        // https://api.slack.com/changelog/2023-07-its-later-already-for-stars-and-reminders
    CompletableFuture<StarsRemoveResponse> starsRemove(RequestConfigurator<StarsRemoveRequest.StarsRemoveRequestBuilder> req);

    // ------------------------------
    // team
    // ------------------------------

    CompletableFuture<TeamAccessLogsResponse> teamAccessLogs(TeamAccessLogsRequest req);

    CompletableFuture<TeamAccessLogsResponse> teamAccessLogs(RequestConfigurator<TeamAccessLogsRequest.TeamAccessLogsRequestBuilder> req);

    CompletableFuture<TeamBillableInfoResponse> teamBillableInfo(TeamBillableInfoRequest req);

    CompletableFuture<TeamBillableInfoResponse> teamBillableInfo(RequestConfigurator<TeamBillableInfoRequest.TeamBillableInfoRequestBuilder> req);

    CompletableFuture<TeamInfoResponse> teamInfo(TeamInfoRequest req);

    CompletableFuture<TeamInfoResponse> teamInfo(RequestConfigurator<TeamInfoRequest.TeamInfoRequestBuilder> req);

    CompletableFuture<TeamIntegrationLogsResponse> teamIntegrationLogs(TeamIntegrationLogsRequest req);

    CompletableFuture<TeamIntegrationLogsResponse> teamIntegrationLogs(RequestConfigurator<TeamIntegrationLogsRequest.TeamIntegrationLogsRequestBuilder> req);

    CompletableFuture<TeamProfileGetResponse> teamProfileGet(TeamProfileGetRequest req);

    CompletableFuture<TeamProfileGetResponse> teamProfileGet(RequestConfigurator<TeamProfileGetRequest.TeamProfileGetRequestBuilder> req);

    CompletableFuture<TeamBillingInfoResponse> teamBillingInfo(TeamBillingInfoRequest req);

    CompletableFuture<TeamBillingInfoResponse> teamBillingInfo(RequestConfigurator<TeamBillingInfoRequest.TeamBillingInfoRequestBuilder> req);

    CompletableFuture<TeamPreferencesListResponse> teamPreferencesList(TeamPreferencesListRequest req);

    CompletableFuture<TeamPreferencesListResponse> teamPreferencesList(RequestConfigurator<TeamPreferencesListRequest.TeamPreferencesListRequestBuilder> req);

    CompletableFuture<TeamExternalTeamsListResponse> teamExternalTeamsList(TeamExternalTeamsListRequest req);

    CompletableFuture<TeamExternalTeamsListResponse> teamExternalTeamsList(RequestConfigurator<TeamExternalTeamsListRequest.TeamExternalTeamsListRequestBuilder> req);

    // ------------------------------
    // tooling.tokens
    // ------------------------------

    CompletableFuture<ToolingTokensRotateResponse> toolingTokensRotate(ToolingTokensRotateRequest req);

    CompletableFuture<ToolingTokensRotateResponse> toolingTokensRotate(RequestConfigurator<ToolingTokensRotateRequest.ToolingTokensRotateRequestBuilder> req);

    // ------------------------------
    // usergroups
    // ------------------------------

    CompletableFuture<UsergroupsCreateResponse> usergroupsCreate(UsergroupsCreateRequest req);

    CompletableFuture<UsergroupsCreateResponse> usergroupsCreate(RequestConfigurator<UsergroupsCreateRequest.UsergroupsCreateRequestBuilder> req);

    CompletableFuture<UsergroupsDisableResponse> usergroupsDisable(UsergroupsDisableRequest req);

    CompletableFuture<UsergroupsDisableResponse> usergroupsDisable(RequestConfigurator<UsergroupsDisableRequest.UsergroupsDisableRequestBuilder> req);

    CompletableFuture<UsergroupsEnableResponse> usergroupsEnable(UsergroupsEnableRequest req);

    CompletableFuture<UsergroupsEnableResponse> usergroupsEnable(RequestConfigurator<UsergroupsEnableRequest.UsergroupsEnableRequestBuilder> req);

    CompletableFuture<UsergroupsListResponse> usergroupsList(UsergroupsListRequest req);

    CompletableFuture<UsergroupsListResponse> usergroupsList(RequestConfigurator<UsergroupsListRequest.UsergroupsListRequestBuilder> req);

    CompletableFuture<UsergroupsUpdateResponse> usergroupsUpdate(UsergroupsUpdateRequest req);

    CompletableFuture<UsergroupsUpdateResponse> usergroupsUpdate(RequestConfigurator<UsergroupsUpdateRequest.UsergroupsUpdateRequestBuilder> req);

    CompletableFuture<UsergroupsUsersListResponse> usergroupsUsersList(UsergroupsUsersListRequest req);

    CompletableFuture<UsergroupsUsersListResponse> usergroupsUsersList(RequestConfigurator<UsergroupsUsersListRequest.UsergroupsUsersListRequestBuilder> req);

    CompletableFuture<UsergroupsUsersUpdateResponse> usergroupsUsersUpdate(UsergroupsUsersUpdateRequest req);

    CompletableFuture<UsergroupsUsersUpdateResponse> usergroupsUsersUpdate(RequestConfigurator<UsergroupsUsersUpdateRequest.UsergroupsUsersUpdateRequestBuilder> req);

    // ------------------------------
    // users
    // ------------------------------

    CompletableFuture<UsersConversationsResponse> usersConversations(UsersConversationsRequest req);

    CompletableFuture<UsersConversationsResponse> usersConversations(RequestConfigurator<UsersConversationsRequest.UsersConversationsRequestBuilder> req);

    CompletableFuture<UsersDeletePhotoResponse> usersDeletePhoto(UsersDeletePhotoRequest req);

    CompletableFuture<UsersDeletePhotoResponse> usersDeletePhoto(RequestConfigurator<UsersDeletePhotoRequest.UsersDeletePhotoRequestBuilder> req);

    CompletableFuture<UsersGetPresenceResponse> usersGetPresence(UsersGetPresenceRequest req);

    CompletableFuture<UsersGetPresenceResponse> usersGetPresence(RequestConfigurator<UsersGetPresenceRequest.UsersGetPresenceRequestBuilder> req);

    CompletableFuture<UsersIdentityResponse> usersIdentity(UsersIdentityRequest req);

    CompletableFuture<UsersIdentityResponse> usersIdentity(RequestConfigurator<UsersIdentityRequest.UsersIdentityRequestBuilder> req);

    CompletableFuture<UsersInfoResponse> usersInfo(UsersInfoRequest req);

    CompletableFuture<UsersInfoResponse> usersInfo(RequestConfigurator<UsersInfoRequest.UsersInfoRequestBuilder> req);

    CompletableFuture<UsersListResponse> usersList(UsersListRequest req);

    CompletableFuture<UsersListResponse> usersList(RequestConfigurator<UsersListRequest.UsersListRequestBuilder> req);

    CompletableFuture<UsersLookupByEmailResponse> usersLookupByEmail(UsersLookupByEmailRequest req);

    CompletableFuture<UsersLookupByEmailResponse> usersLookupByEmail(RequestConfigurator<UsersLookupByEmailRequest.UsersLookupByEmailRequestBuilder> req);

    CompletableFuture<UsersSetActiveResponse> usersSetActive(UsersSetActiveRequest req);

    CompletableFuture<UsersSetActiveResponse> usersSetActive(RequestConfigurator<UsersSetActiveRequest.UsersSetActiveRequestBuilder> req);

    CompletableFuture<UsersSetPhotoResponse> usersSetPhoto(UsersSetPhotoRequest req);

    CompletableFuture<UsersSetPhotoResponse> usersSetPhoto(RequestConfigurator<UsersSetPhotoRequest.UsersSetPhotoRequestBuilder> req);

    CompletableFuture<UsersSetPresenceResponse> usersSetPresence(UsersSetPresenceRequest req);

    CompletableFuture<UsersSetPresenceResponse> usersSetPresence(RequestConfigurator<UsersSetPresenceRequest.UsersSetPresenceRequestBuilder> req);

    CompletableFuture<UsersDiscoverableContactsLookupResponse> usersDiscoverableContactsLookup(UsersDiscoverableContactsLookupRequest req);

    CompletableFuture<UsersDiscoverableContactsLookupResponse> usersDiscoverableContactsLookup(RequestConfigurator<UsersDiscoverableContactsLookupRequest.UsersDiscoverableContactsLookupRequestBuilder> req);

    // ------------------------------
    // users.profile
    // ------------------------------

    CompletableFuture<UsersProfileGetResponse> usersProfileGet(UsersProfileGetRequest req);

    CompletableFuture<UsersProfileGetResponse> usersProfileGet(RequestConfigurator<UsersProfileGetRequest.UsersProfileGetRequestBuilder> req);

    CompletableFuture<UsersProfileSetResponse> usersProfileSet(UsersProfileSetRequest req);

    CompletableFuture<UsersProfileSetResponse> usersProfileSet(RequestConfigurator<UsersProfileSetRequest.UsersProfileSetRequestBuilder> req);

    // ------------------------------
    // views
    // ------------------------------

    CompletableFuture<ViewsOpenResponse> viewsOpen(ViewsOpenRequest req);

    CompletableFuture<ViewsOpenResponse> viewsOpen(RequestConfigurator<ViewsOpenRequest.ViewsOpenRequestBuilder> req);

    CompletableFuture<ViewsPushResponse> viewsPush(ViewsPushRequest req);

    CompletableFuture<ViewsPushResponse> viewsPush(RequestConfigurator<ViewsPushRequest.ViewsPushRequestBuilder> req);

    CompletableFuture<ViewsUpdateResponse> viewsUpdate(ViewsUpdateRequest req);

    CompletableFuture<ViewsUpdateResponse> viewsUpdate(RequestConfigurator<ViewsUpdateRequest.ViewsUpdateRequestBuilder> req);

    CompletableFuture<ViewsPublishResponse> viewsPublish(ViewsPublishRequest req);

    CompletableFuture<ViewsPublishResponse> viewsPublish(RequestConfigurator<ViewsPublishRequest.ViewsPublishRequestBuilder> req);

    // ------------------------------
    // workflows
    // ------------------------------

    CompletableFuture<WorkflowsStepCompletedResponse> workflowsStepCompleted(WorkflowsStepCompletedRequest req);

    CompletableFuture<WorkflowsStepCompletedResponse> workflowsStepCompleted(RequestConfigurator<WorkflowsStepCompletedRequest.WorkflowsStepCompletedRequestBuilder> req);

    CompletableFuture<WorkflowsStepFailedResponse> workflowsStepFailed(WorkflowsStepFailedRequest req);

    CompletableFuture<WorkflowsStepFailedResponse> workflowsStepFailed(RequestConfigurator<WorkflowsStepFailedRequest.WorkflowsStepFailedRequestBuilder> req);

    CompletableFuture<WorkflowsUpdateStepResponse> workflowsUpdateStep(WorkflowsUpdateStepRequest req);

    CompletableFuture<WorkflowsUpdateStepResponse> workflowsUpdateStep(RequestConfigurator<WorkflowsUpdateStepRequest.WorkflowsUpdateStepRequestBuilder> req);

}
