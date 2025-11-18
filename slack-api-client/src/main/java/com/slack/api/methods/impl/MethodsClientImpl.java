package com.slack.api.methods.impl;

import com.google.gson.Gson;
import com.slack.api.RequestConfigurator;
import com.slack.api.methods.*;
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
import com.slack.api.methods.request.apps.permissions.AppsPermissionsInfoRequest;
import com.slack.api.methods.request.apps.permissions.AppsPermissionsRequestRequest;
import com.slack.api.methods.request.apps.permissions.resources.AppsPermissionsResourcesListRequest;
import com.slack.api.methods.request.apps.permissions.scopes.AppsPermissionsScopesListRequest;
import com.slack.api.methods.request.apps.permissions.users.AppsPermissionsUsersListRequest;
import com.slack.api.methods.request.apps.permissions.users.AppsPermissionsUsersRequestRequest;
import com.slack.api.methods.request.assistant.threads.AssistantThreadsSetStatusRequest;
import com.slack.api.methods.request.assistant.threads.AssistantThreadsSetSuggestedPromptsRequest;
import com.slack.api.methods.request.assistant.threads.AssistantThreadsSetTitleRequest;
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
import com.slack.api.methods.request.channels.*;
import com.slack.api.methods.request.chat.*;
import com.slack.api.methods.request.chat.scheduled_messages.ChatScheduledMessagesListRequest;
import com.slack.api.methods.request.conversations.*;
import com.slack.api.methods.request.conversations.canvases.ConversationsCanvasesCreateRequest;
import com.slack.api.methods.request.conversations.request_shared_invite.ConversationsRequestSharedInviteApproveRequest;
import com.slack.api.methods.request.conversations.request_shared_invite.ConversationsRequestSharedInviteDenyRequest;
import com.slack.api.methods.request.conversations.request_shared_invite.ConversationsRequestSharedInviteListRequest;
import com.slack.api.methods.request.dialog.DialogOpenRequest;
import com.slack.api.methods.request.dnd.*;
import com.slack.api.methods.request.emoji.EmojiListRequest;
import com.slack.api.methods.request.entity.EntityPresentDetailsRequest;
import com.slack.api.methods.request.files.*;
import com.slack.api.methods.request.files.comments.FilesCommentsAddRequest;
import com.slack.api.methods.request.files.comments.FilesCommentsDeleteRequest;
import com.slack.api.methods.request.files.comments.FilesCommentsEditRequest;
import com.slack.api.methods.request.files.remote.*;
import com.slack.api.methods.request.functions.FunctionsCompleteErrorRequest;
import com.slack.api.methods.request.functions.FunctionsCompleteSuccessRequest;
import com.slack.api.methods.request.groups.*;
import com.slack.api.methods.request.im.*;
import com.slack.api.methods.request.migration.MigrationExchangeRequest;
import com.slack.api.methods.request.mpim.*;
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
import com.slack.api.methods.request.slacklists.SlackListsCreateRequest;
import com.slack.api.methods.request.team.*;
import com.slack.api.methods.request.team.external_teams.TeamExternalTeamsDisconnectRequest;
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
import com.slack.api.methods.response.apps.permissions.AppsPermissionsInfoResponse;
import com.slack.api.methods.response.apps.permissions.AppsPermissionsRequestResponse;
import com.slack.api.methods.response.apps.permissions.resources.AppsPermissionsResourcesListResponse;
import com.slack.api.methods.response.apps.permissions.scopes.AppsPermissionsScopesListResponse;
import com.slack.api.methods.response.apps.permissions.users.AppsPermissionsUsersListResponse;
import com.slack.api.methods.response.apps.permissions.users.AppsPermissionsUsersRequestResponse;
import com.slack.api.methods.response.asssistant.threads.AssistantThreadsSetStatusResponse;
import com.slack.api.methods.response.asssistant.threads.AssistantThreadsSetSuggestedPromptsResponse;
import com.slack.api.methods.response.asssistant.threads.AssistantThreadsSetTitleResponse;
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
import com.slack.api.methods.response.channels.*;
import com.slack.api.methods.response.chat.*;
import com.slack.api.methods.response.chat.scheduled_messages.ChatScheduledMessagesListResponse;
import com.slack.api.methods.response.conversations.*;
import com.slack.api.methods.response.conversations.canvases.ConversationsCanvasesCreateResponse;
import com.slack.api.methods.response.conversations.request_shared_invite.ConversationsRequestSharedInviteApproveResponse;
import com.slack.api.methods.response.conversations.request_shared_invite.ConversationsRequestSharedInviteDenyResponse;
import com.slack.api.methods.response.conversations.request_shared_invite.ConversationsRequestSharedInviteListResponse;
import com.slack.api.methods.response.dialog.DialogOpenResponse;
import com.slack.api.methods.response.dnd.*;
import com.slack.api.methods.response.emoji.EmojiListResponse;
import com.slack.api.methods.response.entity.EntityPresentDetailsResponse;
import com.slack.api.methods.response.files.*;
import com.slack.api.methods.response.files.comments.FilesCommentsAddResponse;
import com.slack.api.methods.response.files.comments.FilesCommentsDeleteResponse;
import com.slack.api.methods.response.files.comments.FilesCommentsEditResponse;
import com.slack.api.methods.response.files.remote.*;
import com.slack.api.methods.response.functions.FunctionsCompleteErrorResponse;
import com.slack.api.methods.response.functions.FunctionsCompleteSuccessResponse;
import com.slack.api.methods.response.groups.*;
import com.slack.api.methods.response.im.*;
import com.slack.api.methods.response.migration.MigrationExchangeResponse;
import com.slack.api.methods.response.mpim.*;
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
import com.slack.api.methods.response.team.external_teams.TeamExternalTeamsDisconnectResponse;
import com.slack.api.methods.response.team.external_teams.TeamExternalTeamsListResponse;
import com.slack.api.methods.response.team.profile.TeamProfileGetResponse;
import com.slack.api.methods.response.tooling.tokens.ToolingTokensRotateResponse;
import com.slack.api.methods.response.usergroups.*;
import com.slack.api.methods.response.usergroups.users.UsergroupsUsersListResponse;
import com.slack.api.methods.response.usergroups.users.UsergroupsUsersUpdateResponse;
import com.slack.api.methods.response.users.UsersLookupByEmailResponse;
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
import com.slack.api.rate_limits.metrics.MetricsDatastore;
import com.slack.api.util.http.SlackHttpClient;
import com.slack.api.util.json.GsonFactory;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.util.*;

import static com.slack.api.methods.RequestFormBuilder.toForm;
import static com.slack.api.methods.RequestFormBuilder.toMultipartBody;
import static com.slack.api.methods.impl.TeamIdCache.METHOD_NAMES_TO_SKIP_TEAM_ID_CACHE_RESOLUTION;

@Slf4j
public class MethodsClientImpl implements MethodsClient {

    private String endpointUrlPrefix = MethodsClient.ENDPOINT_URL_PREFIX;

    private final String executorName;
    private final boolean statsEnabled;
    private final SlackHttpClient slackHttpClient;
    private final Optional<String> token;
    // for org-level installed apps
    private final Optional<String> teamId;
    private final MetricsDatastore metricsDatastore;
    private final TeamIdCache teamIdCache;

    public MethodsClientImpl(SlackHttpClient slackHttpClient) {
        this(slackHttpClient, null, null);
    }

    public MethodsClientImpl(SlackHttpClient slackHttpClient, String token) {
        this(slackHttpClient, token, null);
    }

    public MethodsClientImpl(SlackHttpClient slackHttpClient, String token, String teamId) {
        this.executorName = slackHttpClient.getConfig().getMethodsConfig().getExecutorName();
        this.statsEnabled = slackHttpClient.getConfig().getMethodsConfig().isStatsEnabled();
        this.slackHttpClient = slackHttpClient;
        this.token = Optional.ofNullable(token);
        this.teamId = Optional.ofNullable(teamId);
        this.metricsDatastore = slackHttpClient.getConfig().getMethodsConfig().getMetricsDatastore();
        this.teamIdCache = new TeamIdCache(this);
    }

    @Override
    public SlackHttpClient getSlackHttpClient() {
        return this.slackHttpClient;
    }

    @Override
    public String getEndpointUrlPrefix() {
        return endpointUrlPrefix;
    }

    @Override
    public void setEndpointUrlPrefix(String endpointUrlPrefix) {
        this.endpointUrlPrefix = endpointUrlPrefix;
    }

    // ----------------------------------------------------------------------------------
    // public methods
    // ----------------------------------------------------------------------------------

    @Override
    public AdminAppsApproveResponse adminAppsApprove(AdminAppsApproveRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_APPS_APPROVE, getToken(req), AdminAppsApproveResponse.class);
    }

    @Override
    public AdminAppsApproveResponse adminAppsApprove(RequestConfigurator<AdminAppsApproveRequest.AdminAppsApproveRequestBuilder> req) throws IOException, SlackApiException {
        return adminAppsApprove(req.configure(AdminAppsApproveRequest.builder()).build());
    }

    @Override
    public AdminAppsRestrictResponse adminAppsRestrict(AdminAppsRestrictRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_APPS_RESTRICT, getToken(req), AdminAppsRestrictResponse.class);
    }

    @Override
    public AdminAppsRestrictResponse adminAppsRestrict(RequestConfigurator<AdminAppsRestrictRequest.AdminAppsRestrictRequestBuilder> req) throws IOException, SlackApiException {
        return adminAppsRestrict(req.configure(AdminAppsRestrictRequest.builder()).build());
    }

    @Override
    public AdminAppsApprovedListResponse adminAppsApprovedList(AdminAppsApprovedListRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_APPS_APPROVED_LIST, getToken(req), AdminAppsApprovedListResponse.class);
    }

    @Override
    public AdminAppsApprovedListResponse adminAppsApprovedList(RequestConfigurator<AdminAppsApprovedListRequest.AdminAppsApprovedListRequestBuilder> req) throws IOException, SlackApiException {
        return adminAppsApprovedList(req.configure(AdminAppsApprovedListRequest.builder()).build());
    }

    @Override
    public AdminAppsRestrictedListResponse adminAppsRestrictedList(AdminAppsRestrictedListRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_APPS_RESTRICTED_LIST, getToken(req), AdminAppsRestrictedListResponse.class);
    }

    @Override
    public AdminAppsRestrictedListResponse adminAppsRestrictedList(RequestConfigurator<AdminAppsRestrictedListRequest.AdminAppsRestrictedListRequestBuilder> req) throws IOException, SlackApiException {
        return adminAppsRestrictedList(req.configure(AdminAppsRestrictedListRequest.builder()).build());
    }

    @Override
    public AdminAppsClearResolutionResponse adminAppsClearResolution(AdminAppsClearResolutionRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_APPS_CLEAR_RESOLUTION, getToken(req), AdminAppsClearResolutionResponse.class);
    }

    @Override
    public AdminAppsClearResolutionResponse adminAppsClearResolution(RequestConfigurator<AdminAppsClearResolutionRequest.AdminAppsClearResolutionRequestBuilder> req) throws IOException, SlackApiException {
        return adminAppsClearResolution(req.configure(AdminAppsClearResolutionRequest.builder()).build());
    }

    @Override
    public AdminAppsUninstallResponse adminAppsUninstall(AdminAppsUninstallRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_APPS_UNINSTALL, getToken(req), AdminAppsUninstallResponse.class);
    }

    @Override
    public AdminAppsUninstallResponse adminAppsUninstall(RequestConfigurator<AdminAppsUninstallRequest.AdminAppsUninstallRequestBuilder> req) throws IOException, SlackApiException {
        return adminAppsUninstall(req.configure(AdminAppsUninstallRequest.builder()).build());
    }

    @Override
    public AdminAppsActivitiesListResponse adminAppsActivitiesList(AdminAppsActivitiesListRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_APPS_ACTIVITIES_LIST, getToken(req), AdminAppsActivitiesListResponse.class);
    }

    @Override
    public AdminAppsActivitiesListResponse adminAppsActivitiesList(RequestConfigurator<AdminAppsActivitiesListRequest.AdminAppsActivitiesListRequestBuilder> req) throws IOException, SlackApiException {
        return adminAppsActivitiesList(req.configure(AdminAppsActivitiesListRequest.builder()).build());
    }

    @Override
    public AdminAppsConfigLookupResponse adminAppsConfigLookup(AdminAppsConfigLookupRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_APPS_CONFIG_LOOKUP, getToken(req), AdminAppsConfigLookupResponse.class);
    }

    @Override
    public AdminAppsConfigLookupResponse adminAppsConfigLookup(RequestConfigurator<AdminAppsConfigLookupRequest.AdminAppsConfigLookupRequestBuilder> req) throws IOException, SlackApiException {
        return adminAppsConfigLookup(req.configure(AdminAppsConfigLookupRequest.builder()).build());
    }

    @Override
    public AdminAppsConfigSetResponse adminAppsConfigSet(AdminAppsConfigSetRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_APPS_CONFIG_SET, getToken(req), AdminAppsConfigSetResponse.class);
    }

    @Override
    public AdminAppsConfigSetResponse adminAppsConfigSet(RequestConfigurator<AdminAppsConfigSetRequest.AdminAppsConfigSetRequestBuilder> req) throws IOException, SlackApiException {
        return adminAppsConfigSet(req.configure(AdminAppsConfigSetRequest.builder()).build());
    }

    @Override
    public AdminAppsRequestsCancelResponse adminAppsRequestsCancel(AdminAppsRequestsCancelRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_APPS_REQUESTS_CANCEL, getToken(req), AdminAppsRequestsCancelResponse.class);
    }

    @Override
    public AdminAppsRequestsCancelResponse adminAppsRequestsCancel(RequestConfigurator<AdminAppsRequestsCancelRequest.AdminAppsRequestsCancelRequestBuilder> req) throws IOException, SlackApiException {
        return adminAppsRequestsCancel(req.configure(AdminAppsRequestsCancelRequest.builder()).build());
    }

    @Override
    public AdminAppsRequestsListResponse adminAppsRequestsList(AdminAppsRequestsListRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_APPS_REQUESTS_LIST, getToken(req), AdminAppsRequestsListResponse.class);
    }

    @Override
    public AdminAppsRequestsListResponse adminAppsRequestsList(RequestConfigurator<AdminAppsRequestsListRequest.AdminAppsRequestsListRequestBuilder> req) throws IOException, SlackApiException {
        return adminAppsRequestsList(req.configure(AdminAppsRequestsListRequest.builder()).build());
    }

    @Override
    public AdminAuthPolicyAssignEntitiesResponse adminAuthPolicyAssignEntities(AdminAuthPolicyAssignEntitiesRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_AUTH_POLICY_ASSIGN_ENTITIES, getToken(req), AdminAuthPolicyAssignEntitiesResponse.class);
    }

    @Override
    public AdminAuthPolicyAssignEntitiesResponse adminAuthPolicyAssignEntities(RequestConfigurator<AdminAuthPolicyAssignEntitiesRequest.AdminAuthPolicyAssignEntitiesRequestBuilder> req) throws IOException, SlackApiException {
        return adminAuthPolicyAssignEntities(req.configure(AdminAuthPolicyAssignEntitiesRequest.builder()).build());
    }

    @Override
    public AdminAuthPolicyGetEntitiesResponse adminAuthPolicyGetEntities(AdminAuthPolicyGetEntitiesRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_AUTH_POLICY_GET_ENTITIES, getToken(req), AdminAuthPolicyGetEntitiesResponse.class);
    }

    @Override
    public AdminAuthPolicyGetEntitiesResponse adminAuthPolicyGetEntities(RequestConfigurator<AdminAuthPolicyGetEntitiesRequest.AdminAuthPolicyGetEntitiesRequestBuilder> req) throws IOException, SlackApiException {
        return adminAuthPolicyGetEntities(req.configure(AdminAuthPolicyGetEntitiesRequest.builder()).build());
    }

    @Override
    public AdminAuthPolicyRemoveEntitiesResponse adminAuthPolicyRemoveEntities(AdminAuthPolicyRemoveEntitiesRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_AUTH_POLICY_REMOVE_ENTITIES, getToken(req), AdminAuthPolicyRemoveEntitiesResponse.class);
    }

    @Override
    public AdminAuthPolicyRemoveEntitiesResponse adminAuthPolicyRemoveEntities(RequestConfigurator<AdminAuthPolicyRemoveEntitiesRequest.AdminAuthPolicyRemoveEntitiesRequestBuilder> req) throws IOException, SlackApiException {
        return adminAuthPolicyRemoveEntities(req.configure(AdminAuthPolicyRemoveEntitiesRequest.builder()).build());
    }

    @Override
    public AdminBarriersCreateResponse adminBarriersCreate(AdminBarriersCreateRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_BARRIERS_CREATE, getToken(req), AdminBarriersCreateResponse.class);
    }

    @Override
    public AdminBarriersCreateResponse adminBarriersCreate(RequestConfigurator<AdminBarriersCreateRequest.AdminBarriersCreateRequestBuilder> req) throws IOException, SlackApiException {
        return adminBarriersCreate(req.configure(AdminBarriersCreateRequest.builder()).build());
    }

    @Override
    public AdminBarriersDeleteResponse adminBarriersDelete(AdminBarriersDeleteRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_BARRIERS_DELETE, getToken(req), AdminBarriersDeleteResponse.class);
    }

    @Override
    public AdminBarriersDeleteResponse adminBarriersDelete(RequestConfigurator<AdminBarriersDeleteRequest.AdminBarriersDeleteRequestBuilder> req) throws IOException, SlackApiException {
        return adminBarriersDelete(req.configure(AdminBarriersDeleteRequest.builder()).build());
    }

    @Override
    public AdminBarriersListResponse adminBarriersList(AdminBarriersListRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_BARRIERS_LIST, getToken(req), AdminBarriersListResponse.class);
    }

    @Override
    public AdminBarriersListResponse adminBarriersList(RequestConfigurator<AdminBarriersListRequest.AdminBarriersListRequestBuilder> req) throws IOException, SlackApiException {
        return adminBarriersList(req.configure(AdminBarriersListRequest.builder()).build());
    }

    @Override
    public AdminBarriersUpdateResponse adminBarriersUpdate(AdminBarriersUpdateRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_BARRIERS_UPDATE, getToken(req), AdminBarriersUpdateResponse.class);
    }

    @Override
    public AdminBarriersUpdateResponse adminBarriersUpdate(RequestConfigurator<AdminBarriersUpdateRequest.AdminBarriersUpdateRequestBuilder> req) throws IOException, SlackApiException {
        return adminBarriersUpdate(req.configure(AdminBarriersUpdateRequest.builder()).build());
    }

    @Override
    public AdminConversationsSetTeamsResponse adminConversationsSetTeams(AdminConversationsSetTeamsRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_CONVERSATIONS_SET_TEAMS, getToken(req), AdminConversationsSetTeamsResponse.class);
    }

    @Override
    public AdminConversationsSetTeamsResponse adminConversationsSetTeams(RequestConfigurator<AdminConversationsSetTeamsRequest.AdminConversationsSetTeamsRequestBuilder> req) throws IOException, SlackApiException {
        return adminConversationsSetTeams(req.configure(AdminConversationsSetTeamsRequest.builder()).build());
    }

    @Override
    public AdminConversationsArchiveResponse adminConversationsArchive(AdminConversationsArchiveRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_CONVERSATIONS_ARCHIVE, getToken(req), AdminConversationsArchiveResponse.class);
    }

    @Override
    public AdminConversationsArchiveResponse adminConversationsArchive(RequestConfigurator<AdminConversationsArchiveRequest.AdminConversationsArchiveRequestBuilder> req) throws IOException, SlackApiException {
        return adminConversationsArchive(req.configure(AdminConversationsArchiveRequest.builder()).build());
    }

    @Override
    public AdminConversationsConvertToPrivateResponse adminConversationsConvertToPrivate(AdminConversationsConvertToPrivateRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_CONVERSATIONS_CONVERT_TO_PRIVATE, getToken(req), AdminConversationsConvertToPrivateResponse.class);
    }

    @Override
    public AdminConversationsConvertToPrivateResponse adminConversationsConvertToPrivate(RequestConfigurator<AdminConversationsConvertToPrivateRequest.AdminConversationsConvertToPrivateRequestBuilder> req) throws IOException, SlackApiException {
        return adminConversationsConvertToPrivate(req.configure(AdminConversationsConvertToPrivateRequest.builder()).build());
    }

    @Override
    public AdminConversationsCreateResponse adminConversationsCreate(AdminConversationsCreateRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_CONVERSATIONS_CREATE, getToken(req), AdminConversationsCreateResponse.class);
    }

    @Override
    public AdminConversationsCreateResponse adminConversationsCreate(RequestConfigurator<AdminConversationsCreateRequest.AdminConversationsCreateRequestBuilder> req) throws IOException, SlackApiException {
        return adminConversationsCreate(req.configure(AdminConversationsCreateRequest.builder()).build());
    }

    @Override
    public AdminConversationsDeleteResponse adminConversationsDelete(AdminConversationsDeleteRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_CONVERSATIONS_DELETE, getToken(req), AdminConversationsDeleteResponse.class);
    }

    @Override
    public AdminConversationsDeleteResponse adminConversationsDelete(RequestConfigurator<AdminConversationsDeleteRequest.AdminConversationsDeleteRequestBuilder> req) throws IOException, SlackApiException {
        return adminConversationsDelete(req.configure(AdminConversationsDeleteRequest.builder()).build());
    }

    @Override
    public AdminConversationsDisconnectSharedResponse adminConversationsDisconnectShared(AdminConversationsDisconnectSharedRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_CONVERSATIONS_DISCONNECT_SHARED, getToken(req), AdminConversationsDisconnectSharedResponse.class);
    }

    @Override
    public AdminConversationsDisconnectSharedResponse adminConversationsDisconnectShared(RequestConfigurator<AdminConversationsDisconnectSharedRequest.AdminConversationsDisconnectSharedRequestBuilder> req) throws IOException, SlackApiException {
        return adminConversationsDisconnectShared(req.configure(AdminConversationsDisconnectSharedRequest.builder()).build());
    }

    @Override
    public AdminConversationsGetConversationPrefsResponse adminConversationsGetConversationPrefs(AdminConversationsGetConversationPrefsRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_CONVERSATIONS_GET_CONVERSATION_PREFS, getToken(req), AdminConversationsGetConversationPrefsResponse.class);
    }

    @Override
    public AdminConversationsGetConversationPrefsResponse adminConversationsGetConversationPrefs(RequestConfigurator<AdminConversationsGetConversationPrefsRequest.AdminConversationsGetConversationPrefsRequestBuilder> req) throws IOException, SlackApiException {
        return adminConversationsGetConversationPrefs(req.configure(AdminConversationsGetConversationPrefsRequest.builder()).build());
    }

    @Override
    public AdminConversationsGetTeamsResponse adminConversationsGetTeams(AdminConversationsGetTeamsRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_CONVERSATIONS_GET_TEAMS, getToken(req), AdminConversationsGetTeamsResponse.class);
    }

    @Override
    public AdminConversationsGetTeamsResponse adminConversationsGetTeams(RequestConfigurator<AdminConversationsGetTeamsRequest.AdminConversationsGetTeamsRequestBuilder> req) throws IOException, SlackApiException {
        return adminConversationsGetTeams(req.configure(AdminConversationsGetTeamsRequest.builder()).build());
    }

    @Override
    public AdminConversationsInviteResponse adminConversationsInvite(AdminConversationsInviteRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_CONVERSATIONS_INVITE, getToken(req), AdminConversationsInviteResponse.class);
    }

    @Override
    public AdminConversationsInviteResponse adminConversationsInvite(RequestConfigurator<AdminConversationsInviteRequest.AdminConversationsInviteRequestBuilder> req) throws IOException, SlackApiException {
        return adminConversationsInvite(req.configure(AdminConversationsInviteRequest.builder()).build());
    }

    @Override
    public AdminConversationsRenameResponse adminConversationsRename(AdminConversationsRenameRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_CONVERSATIONS_RENAME, getToken(req), AdminConversationsRenameResponse.class);
    }

    @Override
    public AdminConversationsRenameResponse adminConversationsRename(RequestConfigurator<AdminConversationsRenameRequest.AdminConversationsRenameRequestBuilder> req) throws IOException, SlackApiException {
        return adminConversationsRename(req.configure(AdminConversationsRenameRequest.builder()).build());
    }

    @Override
    public AdminConversationsSearchResponse adminConversationsSearch(AdminConversationsSearchRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_CONVERSATIONS_SEARCH, getToken(req), AdminConversationsSearchResponse.class);
    }

    @Override
    public AdminConversationsSearchResponse adminConversationsSearch(RequestConfigurator<AdminConversationsSearchRequest.AdminConversationsSearchRequestBuilder> req) throws IOException, SlackApiException {
        return adminConversationsSearch(req.configure(AdminConversationsSearchRequest.builder()).build());
    }

    @Override
    public AdminConversationsSetConversationPrefsResponse adminConversationsSetConversationPrefs(AdminConversationsSetConversationPrefsRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_CONVERSATIONS_SET_CONVERSATION_PREFS, getToken(req), AdminConversationsSetConversationPrefsResponse.class);
    }

    @Override
    public AdminConversationsSetConversationPrefsResponse adminConversationsSetConversationPrefs(RequestConfigurator<AdminConversationsSetConversationPrefsRequest.AdminConversationsSetConversationPrefsRequestBuilder> req) throws IOException, SlackApiException {
        return adminConversationsSetConversationPrefs(req.configure(AdminConversationsSetConversationPrefsRequest.builder()).build());
    }

    @Override
    public AdminConversationsUnarchiveResponse adminConversationsUnarchive(AdminConversationsUnarchiveRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_CONVERSATIONS_UNARCHIVE, getToken(req), AdminConversationsUnarchiveResponse.class);
    }

    @Override
    public AdminConversationsUnarchiveResponse adminConversationsUnarchive(RequestConfigurator<AdminConversationsUnarchiveRequest.AdminConversationsUnarchiveRequestBuilder> req) throws IOException, SlackApiException {
        return adminConversationsUnarchive(req.configure(AdminConversationsUnarchiveRequest.builder()).build());
    }

    @Override
    public AdminConversationsGetCustomRetentionResponse adminConversationsGetCustomRetention(RequestConfigurator<AdminConversationsGetCustomRetentionRequest.AdminConversationsGetCustomRetentionRequestBuilder> req) throws IOException, SlackApiException {
        return adminConversationsGetCustomRetention(req.configure(AdminConversationsGetCustomRetentionRequest.builder()).build());
    }

    @Override
    public AdminConversationsGetCustomRetentionResponse adminConversationsGetCustomRetention(AdminConversationsGetCustomRetentionRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_CONVERSATIONS_GET_CUSTOM_RETENTION, getToken(req), AdminConversationsGetCustomRetentionResponse.class);
    }

    @Override
    public AdminConversationsRemoveCustomRetentionResponse adminConversationsRemoveCustomRetention(AdminConversationsRemoveCustomRetentionRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_CONVERSATIONS_REMOVE_CUSTOM_RETENTION, getToken(req), AdminConversationsRemoveCustomRetentionResponse.class);
    }

    @Override
    public AdminConversationsRemoveCustomRetentionResponse adminConversationsRemoveCustomRetention(RequestConfigurator<AdminConversationsRemoveCustomRetentionRequest.AdminConversationsRemoveCustomRetentionRequestBuilder> req) throws IOException, SlackApiException {
        return adminConversationsRemoveCustomRetention(req.configure(AdminConversationsRemoveCustomRetentionRequest.builder()).build());
    }

    @Override
    public AdminConversationsSetCustomRetentionResponse adminConversationsSetCustomRetention(AdminConversationsSetCustomRetentionRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_CONVERSATIONS_SET_CUSTOM_RETENTION, getToken(req), AdminConversationsSetCustomRetentionResponse.class);
    }

    @Override
    public AdminConversationsSetCustomRetentionResponse adminConversationsSetCustomRetention(RequestConfigurator<AdminConversationsSetCustomRetentionRequest.AdminConversationsSetCustomRetentionRequestBuilder> req) throws IOException, SlackApiException {
        return adminConversationsSetCustomRetention(req.configure(AdminConversationsSetCustomRetentionRequest.builder()).build());
    }

    @Override
    public AdminConversationsBulkArchiveResponse adminConversationsBulkArchive(AdminConversationsBulkArchiveRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_CONVERSATIONS_BULK_ARCHIVE, getToken(req), AdminConversationsBulkArchiveResponse.class);
    }

    @Override
    public AdminConversationsBulkArchiveResponse adminConversationsBulkArchive(RequestConfigurator<AdminConversationsBulkArchiveRequest.AdminConversationsBulkArchiveRequestBuilder> req) throws IOException, SlackApiException {
        return adminConversationsBulkArchive(req.configure(AdminConversationsBulkArchiveRequest.builder()).build());
    }

    @Override
    public AdminConversationsBulkDeleteResponse adminConversationsBulkDelete(AdminConversationsBulkDeleteRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_CONVERSATIONS_BULK_DELETE, getToken(req), AdminConversationsBulkDeleteResponse.class);
    }

    @Override
    public AdminConversationsBulkDeleteResponse adminConversationsBulkDelete(RequestConfigurator<AdminConversationsBulkDeleteRequest.AdminConversationsBulkDeleteRequestBuilder> req) throws IOException, SlackApiException {
        return adminConversationsBulkDelete(req.configure(AdminConversationsBulkDeleteRequest.builder()).build());
    }

    @Override
    public AdminConversationsBulkMoveResponse adminConversationsBulkMove(AdminConversationsBulkMoveRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_CONVERSATIONS_BULK_MOVE, getToken(req), AdminConversationsBulkMoveResponse.class);
    }

    @Override
    public AdminConversationsBulkMoveResponse adminConversationsBulkMove(RequestConfigurator<AdminConversationsBulkMoveRequest.AdminConversationsBulkMoveRequestBuilder> req) throws IOException, SlackApiException {
        return adminConversationsBulkMove(req.configure(AdminConversationsBulkMoveRequest.builder()).build());
    }

    @Override
    public AdminConversationsConvertToPublicResponse adminConversationsConvertToPublic(AdminConversationsConvertToPublicRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_CONVERSATIONS_CONVERT_TO_PUBLIC, getToken(req), AdminConversationsConvertToPublicResponse.class);
    }

    @Override
    public AdminConversationsConvertToPublicResponse adminConversationsConvertToPublic(RequestConfigurator<AdminConversationsConvertToPublicRequest.AdminConversationsConvertToPublicRequestBuilder> req) throws IOException, SlackApiException {
        return adminConversationsConvertToPublic(req.configure(AdminConversationsConvertToPublicRequest.builder()).build());
    }

    @Override
    public AdminConversationsLookupResponse adminConversationsLookup(AdminConversationsLookupRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_CONVERSATIONS_LOOKUP, getToken(req), AdminConversationsLookupResponse.class);
    }

    @Override
    public AdminConversationsLookupResponse adminConversationsLookup(RequestConfigurator<AdminConversationsLookupRequest.AdminConversationsLookupRequestBuilder> req) throws IOException, SlackApiException {
        return adminConversationsLookup(req.configure(AdminConversationsLookupRequest.builder()).build());
    }

    @Override
    public AdminConversationsEkmListOriginalConnectedChannelInfoResponse adminConversationsEkmListOriginalConnectedChannelInfo(AdminConversationsEkmListOriginalConnectedChannelInfoRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_CONVERSATIONS_EKM_LIST_ORIGINAL_CONNECTED_CHANNEL_INFO, getToken(req), AdminConversationsEkmListOriginalConnectedChannelInfoResponse.class);
    }

    @Override
    public AdminConversationsEkmListOriginalConnectedChannelInfoResponse adminConversationsEkmListOriginalConnectedChannelInfo(RequestConfigurator<AdminConversationsEkmListOriginalConnectedChannelInfoRequest.AdminConversationsEkmListOriginalConnectedChannelInfoRequestBuilder> req) throws IOException, SlackApiException {
        return adminConversationsEkmListOriginalConnectedChannelInfo(req.configure(AdminConversationsEkmListOriginalConnectedChannelInfoRequest.builder()).build());
    }

    @Override
    public AdminConversationsRestrictAccessAddGroupResponse adminConversationsRestrictAccessAddGroup(AdminConversationsRestrictAccessAddGroupRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_CONVERSATIONS_RESTRICT_ACCESS_ADD_GROUP, getToken(req), AdminConversationsRestrictAccessAddGroupResponse.class);
    }

    @Override
    public AdminConversationsRestrictAccessAddGroupResponse adminConversationsRestrictAccessAddGroup(RequestConfigurator<AdminConversationsRestrictAccessAddGroupRequest.AdminConversationsRestrictAccessAddGroupRequestBuilder> req) throws IOException, SlackApiException {
        return adminConversationsRestrictAccessAddGroup(req.configure(AdminConversationsRestrictAccessAddGroupRequest.builder()).build());
    }

    @Override
    public AdminConversationsRestrictAccessRemoveGroupResponse adminConversationsRestrictAccessRemoveGroup(AdminConversationsRestrictAccessRemoveGroupRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_CONVERSATIONS_RESTRICT_ACCESS_REMOVE_GROUP, getToken(req), AdminConversationsRestrictAccessRemoveGroupResponse.class);
    }

    @Override
    public AdminConversationsRestrictAccessRemoveGroupResponse adminConversationsRestrictAccessRemoveGroup(RequestConfigurator<AdminConversationsRestrictAccessRemoveGroupRequest.AdminConversationsRestrictAccessRemoveGroupRequestBuilder> req) throws IOException, SlackApiException {
        return adminConversationsRestrictAccessRemoveGroup(req.configure(AdminConversationsRestrictAccessRemoveGroupRequest.builder()).build());
    }

    @Override
    public AdminConversationsRestrictAccessListGroupsResponse adminConversationsRestrictAccessListGroups(AdminConversationsRestrictAccessListGroupsRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_CONVERSATIONS_RESTRICT_ACCESS_LIST_GROUPS, getToken(req), AdminConversationsRestrictAccessListGroupsResponse.class);
    }

    @Override
    public AdminConversationsRestrictAccessListGroupsResponse adminConversationsRestrictAccessListGroups(RequestConfigurator<AdminConversationsRestrictAccessListGroupsRequest.AdminConversationsRestrictAccessListGroupsRequestBuilder> req) throws IOException, SlackApiException {
        return adminConversationsRestrictAccessListGroups(req.configure(AdminConversationsRestrictAccessListGroupsRequest.builder()).build());
    }

    @Override
    public AdminConversationsWhitelistAddResponse adminConversationsWhitelistAdd(AdminConversationsWhitelistAddRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_CONVERSATIONS_WHITELIST_ADD, getToken(req), AdminConversationsWhitelistAddResponse.class);
    }

    @Override
    public AdminConversationsWhitelistAddResponse adminConversationsWhitelistAdd(RequestConfigurator<AdminConversationsWhitelistAddRequest.AdminConversationsWhitelistAddRequestBuilder> req) throws IOException, SlackApiException {
        return adminConversationsWhitelistAdd(req.configure(AdminConversationsWhitelistAddRequest.builder()).build());
    }

    @Override
    public AdminConversationsWhitelistRemoveResponse adminConversationsWhitelistRemove(AdminConversationsWhitelistRemoveRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_CONVERSATIONS_WHITELIST_REMOVE, getToken(req), AdminConversationsWhitelistRemoveResponse.class);
    }

    @Override
    public AdminConversationsWhitelistRemoveResponse adminConversationsWhitelistRemove(RequestConfigurator<AdminConversationsWhitelistRemoveRequest.AdminConversationsWhitelistRemoveRequestBuilder> req) throws IOException, SlackApiException {
        return adminConversationsWhitelistRemove(req.configure(AdminConversationsWhitelistRemoveRequest.builder()).build());
    }

    @Override
    public AdminConversationsWhitelistListGroupsLinkedToChannelResponse adminConversationsWhitelistListGroupsLinkedToChannel(
            AdminConversationsWhitelistListGroupsLinkedToChannelRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req),
                Methods.ADMIN_CONVERSATIONS_WHITELIST_LIST_GROUPS_LINKED_TO_CHANNEL, getToken(req),
                AdminConversationsWhitelistListGroupsLinkedToChannelResponse.class);
    }

    @Override
    public AdminConversationsWhitelistListGroupsLinkedToChannelResponse adminConversationsWhitelistListGroupsLinkedToChannel(
            RequestConfigurator<AdminConversationsWhitelistListGroupsLinkedToChannelRequest.AdminConversationsWhitelistListGroupsLinkedToChannelRequestBuilder> req)
            throws IOException, SlackApiException {
        return adminConversationsWhitelistListGroupsLinkedToChannel(
                req.configure(AdminConversationsWhitelistListGroupsLinkedToChannelRequest.builder()).build());
    }

    @Override
    public AdminEmojiAddResponse adminEmojiAdd(AdminEmojiAddRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_EMOJI_ADD, getToken(req), AdminEmojiAddResponse.class);
    }

    @Override
    public AdminEmojiAddResponse adminEmojiAdd(RequestConfigurator<AdminEmojiAddRequest.AdminEmojiAddRequestBuilder> req) throws IOException, SlackApiException {
        return adminEmojiAdd(req.configure(AdminEmojiAddRequest.builder()).build());
    }

    @Override
    public AdminEmojiAddAliasResponse adminEmojiAddAlias(AdminEmojiAddAliasRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_EMOJI_ADD_ALIAS, getToken(req), AdminEmojiAddAliasResponse.class);
    }

    @Override
    public AdminEmojiAddAliasResponse adminEmojiAddAlias(RequestConfigurator<AdminEmojiAddAliasRequest.AdminEmojiAddAliasRequestBuilder> req) throws IOException, SlackApiException {
        return adminEmojiAddAlias(req.configure(AdminEmojiAddAliasRequest.builder()).build());
    }

    @Override
    public AdminEmojiListResponse adminEmojiList(AdminEmojiListRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_EMOJI_LIST, getToken(req), AdminEmojiListResponse.class);
    }

    @Override
    public AdminEmojiListResponse adminEmojiList(RequestConfigurator<AdminEmojiListRequest.AdminEmojiListRequestBuilder> req) throws IOException, SlackApiException {
        return adminEmojiList(req.configure(AdminEmojiListRequest.builder()).build());
    }

    @Override
    public AdminEmojiRemoveResponse adminEmojiRemove(AdminEmojiRemoveRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_EMOJI_REMOVE, getToken(req), AdminEmojiRemoveResponse.class);
    }

    @Override
    public AdminEmojiRemoveResponse adminEmojiRemove(RequestConfigurator<AdminEmojiRemoveRequest.AdminEmojiRemoveRequestBuilder> req) throws IOException, SlackApiException {
        return adminEmojiRemove(req.configure(AdminEmojiRemoveRequest.builder()).build());
    }

    @Override
    public AdminEmojiRenameResponse adminEmojiRename(AdminEmojiRenameRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_EMOJI_RENAME, getToken(req), AdminEmojiRenameResponse.class);
    }

    @Override
    public AdminEmojiRenameResponse adminEmojiRename(RequestConfigurator<AdminEmojiRenameRequest.AdminEmojiRenameRequestBuilder> req) throws IOException, SlackApiException {
        return adminEmojiRename(req.configure(AdminEmojiRenameRequest.builder()).build());
    }

    @Override
    public AdminFunctionsListResponse adminFunctionsList(AdminFunctionsListRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_FUNCTIONS_LIST, getToken(req), AdminFunctionsListResponse.class);
    }

    @Override
    public AdminFunctionsListResponse adminFunctionsList(RequestConfigurator<AdminFunctionsListRequest.AdminFunctionsListRequestBuilder> req) throws IOException, SlackApiException {
        return adminFunctionsList(req.configure(AdminFunctionsListRequest.builder()).build());
    }

    @Override
    public AdminFunctionsPermissionsLookupResponse adminFunctionsPermissionsLookup(AdminFunctionsPermissionsLookupRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_FUNCTIONS_PERMISSIONS_LOOKUP, getToken(req), AdminFunctionsPermissionsLookupResponse.class);
    }

    @Override
    public AdminFunctionsPermissionsLookupResponse adminFunctionsPermissionsLookup(RequestConfigurator<AdminFunctionsPermissionsLookupRequest.AdminFunctionsPermissionsLookupRequestBuilder> req) throws IOException, SlackApiException {
        return adminFunctionsPermissionsLookup(req.configure(AdminFunctionsPermissionsLookupRequest.builder()).build());
    }

    @Override
    public AdminFunctionsPermissionsSetResponse adminFunctionsPermissionsSet(AdminFunctionsPermissionsSetRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_FUNCTIONS_PERMISSIONS_SET, getToken(req), AdminFunctionsPermissionsSetResponse.class);
    }

    @Override
    public AdminFunctionsPermissionsSetResponse adminFunctionsPermissionsSet(RequestConfigurator<AdminFunctionsPermissionsSetRequest.AdminFunctionsPermissionsSetRequestBuilder> req) throws IOException, SlackApiException {
        return adminFunctionsPermissionsSet(req.configure(AdminFunctionsPermissionsSetRequest.builder()).build());
    }

    @Override
    public AdminInviteRequestsApproveResponse adminInviteRequestsApprove(AdminInviteRequestsApproveRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_INVITE_REQUESTS_APPROVE, getToken(req), AdminInviteRequestsApproveResponse.class);
    }

    @Override
    public AdminInviteRequestsApproveResponse adminInviteRequestsApprove(RequestConfigurator<AdminInviteRequestsApproveRequest.AdminInviteRequestsApproveRequestBuilder> req) throws IOException, SlackApiException {
        return adminInviteRequestsApprove(req.configure(AdminInviteRequestsApproveRequest.builder()).build());
    }

    @Override
    public AdminInviteRequestsDenyResponse adminInviteRequestsDeny(AdminInviteRequestsDenyRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_INVITE_REQUESTS_DENY, getToken(req), AdminInviteRequestsDenyResponse.class);
    }

    @Override
    public AdminInviteRequestsDenyResponse adminInviteRequestsDeny(RequestConfigurator<AdminInviteRequestsDenyRequest.AdminInviteRequestsDenyRequestBuilder> req) throws IOException, SlackApiException {
        return adminInviteRequestsDeny(req.configure(AdminInviteRequestsDenyRequest.builder()).build());
    }

    @Override
    public AdminInviteRequestsListResponse adminInviteRequestsList(AdminInviteRequestsListRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_INVITE_REQUESTS_LIST, getToken(req), AdminInviteRequestsListResponse.class);
    }

    @Override
    public AdminInviteRequestsListResponse adminInviteRequestsList(RequestConfigurator<AdminInviteRequestsListRequest.AdminInviteRequestsListRequestBuilder> req) throws IOException, SlackApiException {
        return adminInviteRequestsList(req.configure(AdminInviteRequestsListRequest.builder()).build());
    }

    @Override
    public AdminInviteRequestsApprovedListResponse adminInviteRequestsApprovedList(AdminInviteRequestsApprovedListRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_INVITE_REQUESTS_APPROVED_LIST, getToken(req), AdminInviteRequestsApprovedListResponse.class);
    }

    @Override
    public AdminInviteRequestsApprovedListResponse adminInviteRequestsApprovedList(RequestConfigurator<AdminInviteRequestsApprovedListRequest.AdminInviteRequestsApprovedListRequestBuilder> req) throws IOException, SlackApiException {
        return adminInviteRequestsApprovedList(req.configure(AdminInviteRequestsApprovedListRequest.builder()).build());
    }

    @Override
    public AdminInviteRequestsDeniedListResponse adminInviteRequestsDeniedList(AdminInviteRequestsDeniedListRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_INVITE_REQUESTS_DENIED_LIST, getToken(req), AdminInviteRequestsDeniedListResponse.class);
    }

    @Override
    public AdminInviteRequestsDeniedListResponse adminInviteRequestsDeniedList(RequestConfigurator<AdminInviteRequestsDeniedListRequest.AdminInviteRequestsDeniedListRequestBuilder> req) throws IOException, SlackApiException {
        return adminInviteRequestsDeniedList(req.configure(AdminInviteRequestsDeniedListRequest.builder()).build());
    }

    @Override
    public AdminRolesListAssignmentsResponse adminRolesListAssignments(AdminRolesListAssignmentsRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_ROLES_LIST_ASSIGNMENTS, getToken(req), AdminRolesListAssignmentsResponse.class);
    }

    @Override
    public AdminRolesListAssignmentsResponse adminRolesListAssignments(RequestConfigurator<AdminRolesListAssignmentsRequest.AdminRolesListAssignmentsRequestBuilder> req) throws IOException, SlackApiException {
        return adminRolesListAssignments(req.configure(AdminRolesListAssignmentsRequest.builder()).build());
    }

    @Override
    public AdminRolesAddAssignmentsResponse adminRolesAddAssignments(AdminRolesAddAssignmentsRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_ROLES_ADD_ASSIGNMENTS, getToken(req), AdminRolesAddAssignmentsResponse.class);
    }

    @Override
    public AdminRolesAddAssignmentsResponse adminRolesAddAssignments(RequestConfigurator<AdminRolesAddAssignmentsRequest.AdminRolesAddAssignmentsRequestBuilder> req) throws IOException, SlackApiException {
        return adminRolesAddAssignments(req.configure(AdminRolesAddAssignmentsRequest.builder()).build());
    }

    @Override
    public AdminRolesRemoveAssignmentsResponse adminRolesRemoveAssignments(AdminRolesRemoveAssignmentsRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_ROLES_REMOVE_ASSIGNMENTS, getToken(req), AdminRolesRemoveAssignmentsResponse.class);
    }

    @Override
    public AdminRolesRemoveAssignmentsResponse adminRolesRemoveAssignments(RequestConfigurator<AdminRolesRemoveAssignmentsRequest.AdminRolesRemoveAssignmentsRequestBuilder> req) throws IOException, SlackApiException {
        return adminRolesRemoveAssignments(req.configure(AdminRolesRemoveAssignmentsRequest.builder()).build());
    }

    @Override
    public AdminTeamsAdminsListResponse adminTeamsAdminsList(AdminTeamsAdminsListRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_TEAMS_ADMINS_LIST, getToken(req), AdminTeamsAdminsListResponse.class);
    }

    @Override
    public AdminTeamsAdminsListResponse adminTeamsAdminsList(RequestConfigurator<AdminTeamsAdminsListRequest.AdminTeamsAdminsListRequestBuilder> req) throws IOException, SlackApiException {
        return adminTeamsAdminsList(req.configure(AdminTeamsAdminsListRequest.builder()).build());
    }

    @Override
    public AdminTeamsCreateResponse adminTeamsCreate(AdminTeamsCreateRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_TEAMS_CREATE, getToken(req), AdminTeamsCreateResponse.class);
    }

    @Override
    public AdminTeamsCreateResponse adminTeamsCreate(RequestConfigurator<AdminTeamsCreateRequest.AdminTeamsCreateRequestBuilder> req) throws IOException, SlackApiException {
        return adminTeamsCreate(req.configure(AdminTeamsCreateRequest.builder()).build());
    }

    @Override
    public AdminTeamsListResponse adminTeamsList(AdminTeamsListRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_TEAMS_LIST, getToken(req), AdminTeamsListResponse.class);
    }

    @Override
    public AdminTeamsListResponse adminTeamsList(RequestConfigurator<AdminTeamsListRequest.AdminTeamsListRequestBuilder> req) throws IOException, SlackApiException {
        return adminTeamsList(req.configure(AdminTeamsListRequest.builder()).build());
    }

    @Override
    public AdminTeamsOwnersListResponse adminTeamsOwnersList(AdminTeamsOwnersListRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_TEAMS_OWNERS_LIST, getToken(req), AdminTeamsOwnersListResponse.class);
    }

    @Override
    public AdminTeamsOwnersListResponse adminTeamsOwnersList(RequestConfigurator<AdminTeamsOwnersListRequest.AdminTeamsOwnersListRequestBuilder> req) throws IOException, SlackApiException {
        return adminTeamsOwnersList(req.configure(AdminTeamsOwnersListRequest.builder()).build());
    }

    @Override
    public AdminTeamsSettingsInfoResponse adminTeamsSettingsInfo(AdminTeamsSettingsInfoRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_TEAMS_SETTINGS_INFO, getToken(req), AdminTeamsSettingsInfoResponse.class);
    }

    @Override
    public AdminTeamsSettingsInfoResponse adminTeamsSettingsInfo(RequestConfigurator<AdminTeamsSettingsInfoRequest.AdminTeamsSettingsInfoRequestBuilder> req) throws IOException, SlackApiException {
        return adminTeamsSettingsInfo(req.configure(AdminTeamsSettingsInfoRequest.builder()).build());
    }

    @Override
    public AdminTeamsSettingsSetDefaultChannelsResponse adminTeamsSettingsSetDefaultChannels(AdminTeamsSettingsSetDefaultChannelsRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_TEAMS_SETTINGS_SET_DEFAULT_CHANNELS, getToken(req), AdminTeamsSettingsSetDefaultChannelsResponse.class);
    }

    @Override
    public AdminTeamsSettingsSetDefaultChannelsResponse adminTeamsSettingsSetDefaultChannels(RequestConfigurator<AdminTeamsSettingsSetDefaultChannelsRequest.AdminTeamsSettingsSetDefaultChannelsRequestBuilder> req) throws IOException, SlackApiException {
        return adminTeamsSettingsSetDefaultChannels(req.configure(AdminTeamsSettingsSetDefaultChannelsRequest.builder()).build());
    }

    @Override
    public AdminTeamsSettingsSetDescriptionResponse adminTeamsSettingsSetDescription(AdminTeamsSettingsSetDescriptionRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_TEAMS_SETTINGS_SET_DESCRIPTION, getToken(req), AdminTeamsSettingsSetDescriptionResponse.class);
    }

    @Override
    public AdminTeamsSettingsSetDescriptionResponse adminTeamsSettingsSetDescription(RequestConfigurator<AdminTeamsSettingsSetDescriptionRequest.AdminTeamsSettingsSetDescriptionRequestBuilder> req) throws IOException, SlackApiException {
        return adminTeamsSettingsSetDescription(req.configure(AdminTeamsSettingsSetDescriptionRequest.builder()).build());
    }

    @Override
    public AdminTeamsSettingsSetDiscoverabilityResponse adminTeamsSettingsSetDiscoverability(AdminTeamsSettingsSetDiscoverabilityRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_TEAMS_SETTINGS_SET_DISCOVERABILITY, getToken(req), AdminTeamsSettingsSetDiscoverabilityResponse.class);
    }

    @Override
    public AdminTeamsSettingsSetDiscoverabilityResponse adminTeamsSettingsSetDiscoverability(RequestConfigurator<AdminTeamsSettingsSetDiscoverabilityRequest.AdminTeamsSettingsSetDiscoverabilityRequestBuilder> req) throws IOException, SlackApiException {
        return adminTeamsSettingsSetDiscoverability(req.configure(AdminTeamsSettingsSetDiscoverabilityRequest.builder()).build());
    }

    @Override
    public AdminTeamsSettingsSetIconResponse adminTeamsSettingsSetIcon(AdminTeamsSettingsSetIconRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_TEAMS_SETTINGS_SET_ICON, getToken(req), AdminTeamsSettingsSetIconResponse.class);
    }

    @Override
    public AdminTeamsSettingsSetIconResponse adminTeamsSettingsSetIcon(RequestConfigurator<AdminTeamsSettingsSetIconRequest.AdminTeamsSettingsSetIconRequestBuilder> req) throws IOException, SlackApiException {
        return adminTeamsSettingsSetIcon(req.configure(AdminTeamsSettingsSetIconRequest.builder()).build());
    }

    @Override
    public AdminTeamsSettingsSetNameResponse adminTeamsSettingsSetName(AdminTeamsSettingsSetNameRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_TEAMS_SETTINGS_SET_NAME, getToken(req), AdminTeamsSettingsSetNameResponse.class);
    }

    @Override
    public AdminTeamsSettingsSetNameResponse adminTeamsSettingsSetName(RequestConfigurator<AdminTeamsSettingsSetNameRequest.AdminTeamsSettingsSetNameRequestBuilder> req) throws IOException, SlackApiException {
        return adminTeamsSettingsSetName(req.configure(AdminTeamsSettingsSetNameRequest.builder()).build());
    }

    @Override
    public AdminUsergroupsAddChannelsResponse adminUsergroupsAddChannels(AdminUsergroupsAddChannelsRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_USERGROUPS_ADD_CHANNELS, getToken(req), AdminUsergroupsAddChannelsResponse.class);
    }

    @Override
    public AdminUsergroupsAddChannelsResponse adminUsergroupsAddChannels(RequestConfigurator<AdminUsergroupsAddChannelsRequest.AdminUsergroupsAddChannelsRequestBuilder> req) throws IOException, SlackApiException {
        return adminUsergroupsAddChannels(req.configure(AdminUsergroupsAddChannelsRequest.builder()).build());
    }

    @Override
    public AdminUsergroupsAddTeamsResponse adminUsergroupsAddTeams(AdminUsergroupsAddTeamsRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_USERGROUPS_ADD_TEAMS, getToken(req), AdminUsergroupsAddTeamsResponse.class);
    }

    @Override
    public AdminUsergroupsAddTeamsResponse adminUsergroupsAddTeams(RequestConfigurator<AdminUsergroupsAddTeamsRequest.AdminUsergroupsAddTeamsRequestBuilder> req) throws IOException, SlackApiException {
        return adminUsergroupsAddTeams(req.configure(AdminUsergroupsAddTeamsRequest.builder()).build());
    }

    @Override
    public AdminUsergroupsListChannelsResponse adminUsergroupsListChannels(AdminUsergroupsListChannelsRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_USERGROUPS_LIST_CHANNELS, getToken(req), AdminUsergroupsListChannelsResponse.class);
    }

    @Override
    public AdminUsergroupsListChannelsResponse adminUsergroupsListChannels(RequestConfigurator<AdminUsergroupsListChannelsRequest.AdminUsergroupsListChannelsRequestBuilder> req) throws IOException, SlackApiException {
        return adminUsergroupsListChannels(req.configure(AdminUsergroupsListChannelsRequest.builder()).build());
    }

    @Override
    public AdminUsergroupsRemoveChannelsResponse adminUsergroupsRemoveChannels(AdminUsergroupsRemoveChannelsRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_USERGROUPS_REMOVE_CHANNELS, getToken(req), AdminUsergroupsRemoveChannelsResponse.class);
    }

    @Override
    public AdminUsergroupsRemoveChannelsResponse adminUsergroupsRemoveChannels(RequestConfigurator<AdminUsergroupsRemoveChannelsRequest.AdminUsergroupsRemoveChannelsRequestBuilder> req) throws IOException, SlackApiException {
        return adminUsergroupsRemoveChannels(req.configure(AdminUsergroupsRemoveChannelsRequest.builder()).build());
    }

    @Override
    public AdminUsersAssignResponse adminUsersAssign(AdminUsersAssignRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_USERS_ASSIGN, getToken(req), AdminUsersAssignResponse.class);
    }

    @Override
    public AdminUsersAssignResponse adminUsersAssign(RequestConfigurator<AdminUsersAssignRequest.AdminUsersAssignRequestBuilder> req) throws IOException, SlackApiException {
        return adminUsersAssign(req.configure(AdminUsersAssignRequest.builder()).build());
    }

    @Override
    public AdminUsersInviteResponse adminUsersInvite(AdminUsersInviteRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_USERS_INVITE, getToken(req), AdminUsersInviteResponse.class);
    }

    @Override
    public AdminUsersInviteResponse adminUsersInvite(RequestConfigurator<AdminUsersInviteRequest.AdminUsersInviteRequestBuilder> req) throws IOException, SlackApiException {
        return adminUsersInvite(req.configure(AdminUsersInviteRequest.builder()).build());
    }

    @Override
    public AdminUsersListResponse adminUsersList(AdminUsersListRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_USERS_LIST, getToken(req), AdminUsersListResponse.class);
    }

    @Override
    public AdminUsersListResponse adminUsersList(RequestConfigurator<AdminUsersListRequest.AdminUsersListRequestBuilder> req) throws IOException, SlackApiException {
        return adminUsersList(req.configure(AdminUsersListRequest.builder()).build());
    }

    @Override
    public AdminUsersRemoveResponse adminUsersRemove(AdminUsersRemoveRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_USERS_REMOVE, getToken(req), AdminUsersRemoveResponse.class);
    }

    @Override
    public AdminUsersRemoveResponse adminUsersRemove(RequestConfigurator<AdminUsersRemoveRequest.AdminUsersRemoveRequestBuilder> req) throws IOException, SlackApiException {
        return adminUsersRemove(req.configure(AdminUsersRemoveRequest.builder()).build());
    }

    @Override
    public AdminUsersSetAdminResponse adminUsersSetAdmin(AdminUsersSetAdminRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_USERS_SET_ADMIN, getToken(req), AdminUsersSetAdminResponse.class);
    }

    @Override
    public AdminUsersSetAdminResponse adminUsersSetAdmin(RequestConfigurator<AdminUsersSetAdminRequest.AdminUsersSetAdminRequestBuilder> req) throws IOException, SlackApiException {
        return adminUsersSetAdmin(req.configure(AdminUsersSetAdminRequest.builder()).build());
    }

    @Override
    public AdminUsersSetExpirationResponse adminUsersSetExpiration(AdminUsersSetExpirationRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_USERS_SET_EXPIRATION, getToken(req), AdminUsersSetExpirationResponse.class);
    }

    @Override
    public AdminUsersSetExpirationResponse adminUsersSetExpiration(RequestConfigurator<AdminUsersSetExpirationRequest.AdminUsersSetExpirationRequestBuilder> req) throws IOException, SlackApiException {
        return adminUsersSetExpiration(req.configure(AdminUsersSetExpirationRequest.builder()).build());
    }

    @Override
    public AdminUsersSetOwnerResponse adminUsersSetOwner(AdminUsersSetOwnerRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_USERS_SET_OWNER, getToken(req), AdminUsersSetOwnerResponse.class);
    }

    @Override
    public AdminUsersSetOwnerResponse adminUsersSetOwner(RequestConfigurator<AdminUsersSetOwnerRequest.AdminUsersSetOwnerRequestBuilder> req) throws IOException, SlackApiException {
        return adminUsersSetOwner(req.configure(AdminUsersSetOwnerRequest.builder()).build());
    }

    @Override
    public AdminUsersSetRegularResponse adminUsersSetRegular(AdminUsersSetRegularRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_USERS_SET_REGULAR, getToken(req), AdminUsersSetRegularResponse.class);
    }

    @Override
    public AdminUsersSetRegularResponse adminUsersSetRegular(RequestConfigurator<AdminUsersSetRegularRequest.AdminUsersSetRegularRequestBuilder> req) throws IOException, SlackApiException {
        return adminUsersSetRegular(req.configure(AdminUsersSetRegularRequest.builder()).build());
    }

    @Override
    public AdminUsersSessionResetResponse adminUsersSessionReset(AdminUsersSessionResetRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_USERS_SESSION_RESET, getToken(req), AdminUsersSessionResetResponse.class);
    }

    @Override
    public AdminUsersSessionResetResponse adminUsersSessionReset(RequestConfigurator<AdminUsersSessionResetRequest.AdminUsersSessionResetRequestBuilder> req) throws IOException, SlackApiException {
        return adminUsersSessionReset(req.configure(AdminUsersSessionResetRequest.builder()).build());
    }

    @Override
    public AdminUsersSessionResetBulkResponse adminUsersSessionResetBulk(AdminUsersSessionResetBulkRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_USERS_SESSION_RESET_BULK, getToken(req), AdminUsersSessionResetBulkResponse.class);
    }

    @Override
    public AdminUsersSessionResetBulkResponse adminUsersSessionResetBulk(RequestConfigurator<AdminUsersSessionResetBulkRequest.AdminUsersSessionResetBulkRequestBuilder> req) throws IOException, SlackApiException {
        return adminUsersSessionResetBulk(req.configure(AdminUsersSessionResetBulkRequest.builder()).build());
    }

    @Override
    public AdminUsersSessionGetSettingsResponse adminUsersSessionGetSettings(AdminUsersSessionGetSettingsRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_USERS_SESSION_GET_SETTINGS, getToken(req), AdminUsersSessionGetSettingsResponse.class);
    }

    @Override
    public AdminUsersSessionGetSettingsResponse adminUsersSessionGetSettings(RequestConfigurator<AdminUsersSessionGetSettingsRequest.AdminUsersSessionGetSettingsRequestBuilder> req) throws IOException, SlackApiException {
        return adminUsersSessionGetSettings(req.configure(AdminUsersSessionGetSettingsRequest.builder()).build());
    }

    @Override
    public AdminUsersSessionSetSettingsResponse adminUsersSessionSetSettings(AdminUsersSessionSetSettingsRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_USERS_SESSION_SET_SETTINGS, getToken(req), AdminUsersSessionSetSettingsResponse.class);
    }

    @Override
    public AdminUsersSessionSetSettingsResponse adminUsersSessionSetSettings(RequestConfigurator<AdminUsersSessionSetSettingsRequest.AdminUsersSessionSetSettingsRequestBuilder> req) throws IOException, SlackApiException {
        return adminUsersSessionSetSettings(req.configure(AdminUsersSessionSetSettingsRequest.builder()).build());
    }

    @Override
    public AdminUsersSessionClearSettingsResponse adminUsersSessionClearSettings(AdminUsersSessionClearSettingsRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_USERS_SESSION_CLEAR_SETTINGS, getToken(req), AdminUsersSessionClearSettingsResponse.class);
    }

    @Override
    public AdminUsersSessionClearSettingsResponse adminUsersSessionClearSettings(RequestConfigurator<AdminUsersSessionClearSettingsRequest.AdminUsersSessionClearSettingsRequestBuilder> req) throws IOException, SlackApiException {
        return adminUsersSessionClearSettings(req.configure(AdminUsersSessionClearSettingsRequest.builder()).build());
    }

    @Override
    public AdminUsersUnsupportedVersionsExportResponse adminUsersUnsupportedVersionsExport(AdminUsersUnsupportedVersionsExportRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_USERS_UNSUPPORTED_VERSIONS_EXPORT, getToken(req), AdminUsersUnsupportedVersionsExportResponse.class);
    }

    @Override
    public AdminUsersUnsupportedVersionsExportResponse adminUsersUnsupportedVersionsExport(RequestConfigurator<AdminUsersUnsupportedVersionsExportRequest.AdminUsersUnsupportedVersionsExportRequestBuilder> req) throws IOException, SlackApiException {
        return adminUsersUnsupportedVersionsExport(req.configure(AdminUsersUnsupportedVersionsExportRequest.builder()).build());
    }

    @Override
    public AdminWorkflowsCollaboratorsAddResponse adminWorkflowsCollaboratorsAdd(AdminWorkflowsCollaboratorsAddRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_WORKFLOWS_COLLABORATORS_ADD, getToken(req), AdminWorkflowsCollaboratorsAddResponse.class);
    }

    @Override
    public AdminWorkflowsCollaboratorsAddResponse adminWorkflowsCollaboratorsAdd(RequestConfigurator<AdminWorkflowsCollaboratorsAddRequest.AdminWorkflowsCollaboratorsAddRequestBuilder> req) throws IOException, SlackApiException {
        return adminWorkflowsCollaboratorsAdd(req.configure(AdminWorkflowsCollaboratorsAddRequest.builder()).build());
    }

    @Override
    public AdminWorkflowsCollaboratorsRemoveResponse adminWorkflowsCollaboratorsRemove(AdminWorkflowsCollaboratorsRemoveRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_WORKFLOWS_COLLABORATORS_REMOVE, getToken(req), AdminWorkflowsCollaboratorsRemoveResponse.class);
    }

    @Override
    public AdminWorkflowsCollaboratorsRemoveResponse adminWorkflowsCollaboratorsRemove(RequestConfigurator<AdminWorkflowsCollaboratorsRemoveRequest.AdminWorkflowsCollaboratorsRemoveRequestBuilder> req) throws IOException, SlackApiException {
        return adminWorkflowsCollaboratorsRemove(req.configure(AdminWorkflowsCollaboratorsRemoveRequest.builder()).build());
    }

    @Override
    public AdminWorkflowsPermissionsLookupResponse adminWorkflowsPermissionsLookup(AdminWorkflowsPermissionsLookupRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_WORKFLOWS_PERMISSIONS_LOOKUP, getToken(req), AdminWorkflowsPermissionsLookupResponse.class);
    }

    @Override
    public AdminWorkflowsPermissionsLookupResponse adminWorkflowsPermissionsLookup(RequestConfigurator<AdminWorkflowsPermissionsLookupRequest.AdminWorkflowsPermissionsLookupRequestBuilder> req) throws IOException, SlackApiException {
        return adminWorkflowsPermissionsLookup(req.configure(AdminWorkflowsPermissionsLookupRequest.builder()).build());
    }

    @Override
    public AdminWorkflowsSearchResponse adminWorkflowsSearch(AdminWorkflowsSearchRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_WORKFLOWS_SEARCH, getToken(req), AdminWorkflowsSearchResponse.class);
    }

    @Override
    public AdminWorkflowsSearchResponse adminWorkflowsSearch(RequestConfigurator<AdminWorkflowsSearchRequest.AdminWorkflowsSearchRequestBuilder> req) throws IOException, SlackApiException {
        return adminWorkflowsSearch(req.configure(AdminWorkflowsSearchRequest.builder()).build());
    }

    @Override
    public AdminWorkflowsUnpublishResponse adminWorkflowsUnpublish(AdminWorkflowsUnpublishRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_WORKFLOWS_UNPUBLISH, getToken(req), AdminWorkflowsUnpublishResponse.class);
    }

    @Override
    public AdminWorkflowsUnpublishResponse adminWorkflowsUnpublish(RequestConfigurator<AdminWorkflowsUnpublishRequest.AdminWorkflowsUnpublishRequestBuilder> req) throws IOException, SlackApiException {
        return adminWorkflowsUnpublish(req.configure(AdminWorkflowsUnpublishRequest.builder()).build());
    }

    @Override
    public AdminUsersSessionInvalidateResponse adminUsersSessionInvalidate(AdminUsersSessionInvalidateRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_USERS_SESSION_INVALIDATE, getToken(req), AdminUsersSessionInvalidateResponse.class);
    }

    @Override
    public AdminUsersSessionInvalidateResponse adminUsersSessionInvalidate(RequestConfigurator<AdminUsersSessionInvalidateRequest.AdminUsersSessionInvalidateRequestBuilder> req) throws IOException, SlackApiException {
        return adminUsersSessionInvalidate(req.configure(AdminUsersSessionInvalidateRequest.builder()).build());
    }

    @Override
    public AdminUsersSessionListResponse adminUsersSessionList(AdminUsersSessionListRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ADMIN_USERS_SESSION_LIST, getToken(req), AdminUsersSessionListResponse.class);
    }

    @Override
    public AdminUsersSessionListResponse adminUsersSessionList(RequestConfigurator<AdminUsersSessionListRequest.AdminUsersSessionListRequestBuilder> req) throws IOException, SlackApiException {
        return adminUsersSessionList(req.configure(AdminUsersSessionListRequest.builder()).build());
    }

    @Override
    public ApiTestResponse apiTest(ApiTestRequest req) throws IOException, SlackApiException {
        return postFormAndParseResponse(toForm(req), Methods.API_TEST, ApiTestResponse.class);
    }

    @Override
    public ApiTestResponse apiTest(RequestConfigurator<ApiTestRequest.ApiTestRequestBuilder> req) throws IOException, SlackApiException {
        return apiTest(req.configure(ApiTestRequest.builder()).build());
    }

    @Override
    public AppsUninstallResponse appsUninstall(AppsUninstallRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.APPS_UNINSTALL, getToken(req), AppsUninstallResponse.class);
    }

    @Override
    public AppsUninstallResponse appsUninstall(RequestConfigurator<AppsUninstallRequest.AppsUninstallRequestBuilder> req) throws IOException, SlackApiException {
        return appsUninstall(req.configure(AppsUninstallRequest.builder()).build());
    }

    @Override
    public AppsConnectionsOpenResponse appsConnectionsOpen(AppsConnectionsOpenRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.APPS_CONNECTIONS_OPEN, getToken(req), AppsConnectionsOpenResponse.class);
    }

    @Override
    public AppsConnectionsOpenResponse appsConnectionsOpen(RequestConfigurator<AppsConnectionsOpenRequest.AppsConnectionsOpenRequestBuilder> req) throws IOException, SlackApiException {
        return appsConnectionsOpen(req.configure(AppsConnectionsOpenRequest.builder()).build());
    }

    @Override
    public AppsEventAuthorizationsListResponse appsEventAuthorizationsList(AppsEventAuthorizationsListRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.APPS_EVENT_AUTHORIZATIONS_LIST, getToken(req), AppsEventAuthorizationsListResponse.class);
    }

    @Override
    public AppsEventAuthorizationsListResponse appsEventAuthorizationsList(RequestConfigurator<AppsEventAuthorizationsListRequest.AppsEventAuthorizationsListRequestBuilder> req) throws IOException, SlackApiException {
        return appsEventAuthorizationsList(req.configure(AppsEventAuthorizationsListRequest.builder()).build());
    }

    @Override
    public AppsManifestCreateResponse appsManifestCreate(AppsManifestCreateRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.APPS_MANIFEST_CREATE, getToken(req), AppsManifestCreateResponse.class);
    }

    @Override
    public AppsManifestCreateResponse appsManifestCreate(RequestConfigurator<AppsManifestCreateRequest.AppsManifestCreateRequestBuilder> req) throws IOException, SlackApiException {
        return appsManifestCreate(req.configure(AppsManifestCreateRequest.builder()).build());
    }

    @Override
    public AppsManifestDeleteResponse appsManifestDelete(AppsManifestDeleteRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.APPS_MANIFEST_DELETE, getToken(req), AppsManifestDeleteResponse.class);
    }

    @Override
    public AppsManifestDeleteResponse appsManifestDelete(RequestConfigurator<AppsManifestDeleteRequest.AppsManifestDeleteRequestBuilder> req) throws IOException, SlackApiException {
        return appsManifestDelete(req.configure(AppsManifestDeleteRequest.builder()).build());
    }

    @Override
    public AppsManifestExportResponse appsManifestExport(AppsManifestExportRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.APPS_MANIFEST_EXPORT, getToken(req), AppsManifestExportResponse.class);
    }

    @Override
    public AppsManifestExportResponse appsManifestExport(RequestConfigurator<AppsManifestExportRequest.AppsManifestExportRequestBuilder> req) throws IOException, SlackApiException {
        return appsManifestExport(req.configure(AppsManifestExportRequest.builder()).build());
    }

    @Override
    public AppsManifestUpdateResponse appsManifestUpdate(AppsManifestUpdateRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.APPS_MANIFEST_UPDATE, getToken(req), AppsManifestUpdateResponse.class);
    }

    @Override
    public AppsManifestUpdateResponse appsManifestUpdate(RequestConfigurator<AppsManifestUpdateRequest.AppsManifestUpdateRequestBuilder> req) throws IOException, SlackApiException {
        return appsManifestUpdate(req.configure(AppsManifestUpdateRequest.builder()).build());
    }

    @Override
    public AppsManifestValidateResponse appsManifestValidate(AppsManifestValidateRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.APPS_MANIFEST_VALIDATE, getToken(req), AppsManifestValidateResponse.class);
    }

    @Override
    public AppsManifestValidateResponse appsManifestValidate(RequestConfigurator<AppsManifestValidateRequest.AppsManifestValidateRequestBuilder> req) throws IOException, SlackApiException {
        return appsManifestValidate(req.configure(AppsManifestValidateRequest.builder()).build());
    }

    @Override
    public AppsPermissionsInfoResponse appsPermissionsInfo(AppsPermissionsInfoRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.APPS_PERMISSIONS_INFO, getToken(req), AppsPermissionsInfoResponse.class);
    }

    @Override
    public AppsPermissionsInfoResponse appsPermissionsInfo(RequestConfigurator<AppsPermissionsInfoRequest.AppsPermissionsInfoRequestBuilder> req) throws IOException, SlackApiException {
        return appsPermissionsInfo(req.configure(AppsPermissionsInfoRequest.builder()).build());
    }

    @Override
    public AppsPermissionsRequestResponse appsPermissionsRequest(AppsPermissionsRequestRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.APPS_PERMISSIONS_REQUEST, getToken(req), AppsPermissionsRequestResponse.class);
    }

    @Override
    public AppsPermissionsRequestResponse appsPermissionsRequest(RequestConfigurator<AppsPermissionsRequestRequest.AppsPermissionsRequestRequestBuilder> req) throws IOException, SlackApiException {
        return appsPermissionsRequest(req.configure(AppsPermissionsRequestRequest.builder()).build());
    }

    @Override
    public AppsPermissionsResourcesListResponse appsPermissionsResourcesList(AppsPermissionsResourcesListRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.APPS_PERMISSIONS_RESOURCES_LIST, getToken(req), AppsPermissionsResourcesListResponse.class);
    }

    @Override
    public AppsPermissionsScopesListResponse appsPermissionsScopesList(AppsPermissionsScopesListRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.APPS_PERMISSIONS_SCOPES_LIST, getToken(req), AppsPermissionsScopesListResponse.class);
    }

    @Override
    public AppsPermissionsUsersListResponse appsPermissionsUsersList(AppsPermissionsUsersListRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.APPS_PERMISSIONS_USERS_LIST, getToken(req), AppsPermissionsUsersListResponse.class);
    }

    @Override
    public AppsPermissionsUsersRequestResponse appsPermissionsUsersRequest(AppsPermissionsUsersRequestRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.APPS_PERMISSIONS_USERS_REQUEST, getToken(req), AppsPermissionsUsersRequestResponse.class);
    }

    @Override
    public AssistantThreadsSetStatusResponse assistantThreadsSetStatus(AssistantThreadsSetStatusRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ASSISTANT_THREADS_SET_STATUS, getToken(req), AssistantThreadsSetStatusResponse.class);
    }

    @Override
    public AssistantThreadsSetStatusResponse assistantThreadsSetStatus(RequestConfigurator<AssistantThreadsSetStatusRequest.AssistantThreadsSetStatusRequestBuilder> req) throws IOException, SlackApiException {
        return assistantThreadsSetStatus(req.configure(AssistantThreadsSetStatusRequest.builder()).build());
    }

    @Override
    public AssistantThreadsSetSuggestedPromptsResponse assistantThreadsSetSuggestedPrompts(AssistantThreadsSetSuggestedPromptsRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ASSISTANT_THREADS_SET_SUGGESTED_PROMPTS, getToken(req), AssistantThreadsSetSuggestedPromptsResponse.class);
    }

    @Override
    public AssistantThreadsSetSuggestedPromptsResponse assistantThreadsSetSuggestedPrompts(RequestConfigurator<AssistantThreadsSetSuggestedPromptsRequest.AssistantThreadsSetSuggestedPromptsRequestBuilder> req) throws IOException, SlackApiException {
        return assistantThreadsSetSuggestedPrompts(req.configure(AssistantThreadsSetSuggestedPromptsRequest.builder()).build());
    }

    @Override
    public AssistantThreadsSetTitleResponse assistantThreadsSetTitle(AssistantThreadsSetTitleRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ASSISTANT_THREADS_SET_TITLE, getToken(req), AssistantThreadsSetTitleResponse.class);
    }

    @Override
    public AssistantThreadsSetTitleResponse assistantThreadsSetTitle(RequestConfigurator<AssistantThreadsSetTitleRequest.AssistantThreadsSetTitleRequestBuilder> req) throws IOException, SlackApiException {
        return assistantThreadsSetTitle(req.configure(AssistantThreadsSetTitleRequest.builder()).build());
    }

    @Override
    public AuthRevokeResponse authRevoke(AuthRevokeRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.AUTH_REVOKE, getToken(req), AuthRevokeResponse.class);
    }

    @Override
    public AuthRevokeResponse authRevoke(RequestConfigurator<AuthRevokeRequest.AuthRevokeRequestBuilder> req) throws IOException, SlackApiException {
        return authRevoke(req.configure(AuthRevokeRequest.builder()).build());
    }

    @Override
    public AuthTestResponse authTest(AuthTestRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.AUTH_TEST, getToken(req), AuthTestResponse.class);
    }

    @Override
    public AuthTestResponse authTest(RequestConfigurator<AuthTestRequest.AuthTestRequestBuilder> req) throws IOException, SlackApiException {
        return authTest(req.configure(AuthTestRequest.builder()).build());
    }

    @Override
    public AuthTeamsListResponse authTeamsList(AuthTeamsListRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.AUTH_TEAMS_LIST, getToken(req), AuthTeamsListResponse.class);
    }

    @Override
    public AuthTeamsListResponse authTeamsList(RequestConfigurator<AuthTeamsListRequest.AuthTeamsListRequestBuilder> req) throws IOException, SlackApiException {
        return authTeamsList(req.configure(AuthTeamsListRequest.builder()).build());
    }

    @Override
    public BookmarksAddResponse bookmarksAdd(BookmarksAddRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.BOOKMARKS_ADD, getToken(req), BookmarksAddResponse.class);
    }

    @Override
    public BookmarksAddResponse bookmarksAdd(RequestConfigurator<BookmarksAddRequest.BookmarksAddRequestBuilder> req) throws IOException, SlackApiException {
        return bookmarksAdd(req.configure(BookmarksAddRequest.builder()).build());
    }

    @Override
    public BookmarksEditResponse bookmarksEdit(BookmarksEditRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.BOOKMARKS_EDIT, getToken(req), BookmarksEditResponse.class);
    }

    @Override
    public BookmarksEditResponse bookmarksEdit(RequestConfigurator<BookmarksEditRequest.BookmarksEditRequestBuilder> req) throws IOException, SlackApiException {
        return bookmarksEdit(req.configure(BookmarksEditRequest.builder()).build());
    }

    @Override
    public BookmarksListResponse bookmarksList(BookmarksListRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.BOOKMARKS_LIST, getToken(req), BookmarksListResponse.class);
    }

    @Override
    public BookmarksListResponse bookmarksList(RequestConfigurator<BookmarksListRequest.BookmarksListRequestBuilder> req) throws IOException, SlackApiException {
        return bookmarksList(req.configure(BookmarksListRequest.builder()).build());
    }

    @Override
    public BookmarksRemoveResponse bookmarksRemove(BookmarksRemoveRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.BOOKMARKS_REMOVE, getToken(req), BookmarksRemoveResponse.class);
    }

    @Override
    public BookmarksRemoveResponse bookmarksRemove(RequestConfigurator<BookmarksRemoveRequest.BookmarksRemoveRequestBuilder> req) throws IOException, SlackApiException {
        return bookmarksRemove(req.configure(BookmarksRemoveRequest.builder()).build());
    }

    @Override
    public BotsInfoResponse botsInfo(BotsInfoRequest req) throws IOException, SlackApiException {
        this.teamId.ifPresent(currentTeamId -> {
            if (req.getTeamId() == null) req.setTeamId(currentTeamId);
        });
        return postFormWithTokenAndParseResponse(toForm(req), Methods.BOTS_INFO, getToken(req), BotsInfoResponse.class);
    }

    @Override
    public BotsInfoResponse botsInfo(RequestConfigurator<BotsInfoRequest.BotsInfoRequestBuilder> req) throws IOException, SlackApiException {
        return botsInfo(req.configure(BotsInfoRequest.builder()).build());
    }

    @Override
    public CanvasesCreateResponse canvasesCreate(CanvasesCreateRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CANVASES_CREATE, getToken(req), CanvasesCreateResponse.class);
    }

    @Override
    public CanvasesCreateResponse canvasesCreate(RequestConfigurator<CanvasesCreateRequest.CanvasesCreateRequestBuilder> req) throws IOException, SlackApiException {
        return canvasesCreate(req.configure(CanvasesCreateRequest.builder()).build());
    }

    @Override
    public CanvasesEditResponse canvasesEdit(CanvasesEditRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CANVASES_EDIT, getToken(req), CanvasesEditResponse.class);
    }

    @Override
    public CanvasesEditResponse canvasesEdit(RequestConfigurator<CanvasesEditRequest.CanvasesEditRequestBuilder> req) throws IOException, SlackApiException {
        return canvasesEdit(req.configure(CanvasesEditRequest.builder()).build());
    }

    @Override
    public CanvasesDeleteResponse canvasesDelete(CanvasesDeleteRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CANVASES_DELETE, getToken(req), CanvasesDeleteResponse.class);
    }

    @Override
    public CanvasesDeleteResponse canvasesDelete(RequestConfigurator<CanvasesDeleteRequest.CanvasesDeleteRequestBuilder> req) throws IOException, SlackApiException {
        return canvasesDelete(req.configure(CanvasesDeleteRequest.builder()).build());
    }

    @Override
    public CanvasesAccessSetResponse canvasesAccessSet(CanvasesAccessSetRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CANVASES_ACCESS_SET, getToken(req), CanvasesAccessSetResponse.class);
    }

    @Override
    public CanvasesAccessSetResponse canvasesAccessSet(RequestConfigurator<CanvasesAccessSetRequest.CanvasesAccessSetRequestBuilder> req) throws IOException, SlackApiException {
        return canvasesAccessSet(req.configure(CanvasesAccessSetRequest.builder()).build());
    }

    @Override
    public CanvasesAccessDeleteResponse canvasesAccessDelete(CanvasesAccessDeleteRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CANVASES_ACCESS_DELETE, getToken(req), CanvasesAccessDeleteResponse.class);
    }

    @Override
    public CanvasesAccessDeleteResponse canvasesAccessDelete(RequestConfigurator<CanvasesAccessDeleteRequest.CanvasesAccessDeleteRequestBuilder> req) throws IOException, SlackApiException {
        return canvasesAccessDelete(req.configure(CanvasesAccessDeleteRequest.builder()).build());
    }

    @Override
    public CanvasesSectionsLookupResponse canvasesSectionsLookup(CanvasesSectionsLookupRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CANVASES_SECTIONS_LOOKUP, getToken(req), CanvasesSectionsLookupResponse.class);
    }

    @Override
    public CanvasesSectionsLookupResponse canvasesSectionsLookup(RequestConfigurator<CanvasesSectionsLookupRequest.CanvasesSectionsLookupRequestBuilder> req) throws IOException, SlackApiException {
        return canvasesSectionsLookup(req.configure(CanvasesSectionsLookupRequest.builder()).build());
    }

    @Override
    public CallsAddResponse callsAdd(CallsAddRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CALLS_ADD, getToken(req), CallsAddResponse.class);
    }

    @Override
    public CallsAddResponse callsAdd(RequestConfigurator<CallsAddRequest.CallsAddRequestBuilder> req) throws IOException, SlackApiException {
        return callsAdd(req.configure(CallsAddRequest.builder()).build());
    }

    @Override
    public CallsEndResponse callsEnd(CallsEndRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CALLS_END, getToken(req), CallsEndResponse.class);
    }

    @Override
    public CallsEndResponse callsEnd(RequestConfigurator<CallsEndRequest.CallsEndRequestBuilder> req) throws IOException, SlackApiException {
        return callsEnd(req.configure(CallsEndRequest.builder()).build());
    }

    @Override
    public CallsInfoResponse callsInfo(CallsInfoRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CALLS_INFO, getToken(req), CallsInfoResponse.class);
    }

    @Override
    public CallsInfoResponse callsInfo(RequestConfigurator<CallsInfoRequest.CallsInfoRequestBuilder> req) throws IOException, SlackApiException {
        return callsInfo(req.configure(CallsInfoRequest.builder()).build());
    }

    @Override
    public CallsUpdateResponse callsUpdate(CallsUpdateRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CALLS_UPDATE, getToken(req), CallsUpdateResponse.class);
    }

    @Override
    public CallsUpdateResponse callsUpdate(RequestConfigurator<CallsUpdateRequest.CallsUpdateRequestBuilder> req) throws IOException, SlackApiException {
        return callsUpdate(req.configure(CallsUpdateRequest.builder()).build());
    }

    @Override
    public CallsParticipantsAddResponse callsParticipantsAdd(CallsParticipantsAddRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CALLS_PARTICIPANTS_ADD, getToken(req), CallsParticipantsAddResponse.class);
    }

    @Override
    public CallsParticipantsAddResponse callsParticipantsAdd(RequestConfigurator<CallsParticipantsAddRequest.CallsParticipantsAddRequestBuilder> req) throws IOException, SlackApiException {
        return callsParticipantsAdd(req.configure(CallsParticipantsAddRequest.builder()).build());
    }

    @Override
    public CallsParticipantsRemoveResponse callsParticipantsRemove(CallsParticipantsRemoveRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CALLS_PARTICIPANTS_REMOVE, getToken(req), CallsParticipantsRemoveResponse.class);
    }

    @Override
    public CallsParticipantsRemoveResponse callsParticipantsRemove(RequestConfigurator<CallsParticipantsRemoveRequest.CallsParticipantsRemoveRequestBuilder> req) throws IOException, SlackApiException {
        return callsParticipantsRemove(req.configure(CallsParticipantsRemoveRequest.builder()).build());
    }

    @Override
    public ChannelsArchiveResponse channelsArchive(ChannelsArchiveRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CHANNELS_ARCHIVE, getToken(req), ChannelsArchiveResponse.class);
    }

    @Override
    public ChannelsArchiveResponse channelsArchive(RequestConfigurator<ChannelsArchiveRequest.ChannelsArchiveRequestBuilder> req) throws IOException, SlackApiException {
        return channelsArchive(req.configure(ChannelsArchiveRequest.builder()).build());
    }

    @Override
    public ChannelsCreateResponse channelsCreate(ChannelsCreateRequest req) throws IOException, SlackApiException {
        this.teamId.ifPresent(currentTeamId -> {
            if (req.getTeamId() == null) req.setTeamId(currentTeamId);
        });
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CHANNELS_CREATE, getToken(req), ChannelsCreateResponse.class);
    }

    @Override
    public ChannelsCreateResponse channelsCreate(RequestConfigurator<ChannelsCreateRequest.ChannelsCreateRequestBuilder> req) throws IOException, SlackApiException {
        return channelsCreate(req.configure(ChannelsCreateRequest.builder()).build());
    }

    @Override
    public ChannelsHistoryResponse channelsHistory(ChannelsHistoryRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CHANNELS_HISTORY, getToken(req), ChannelsHistoryResponse.class);
    }

    @Override
    public ChannelsHistoryResponse channelsHistory(RequestConfigurator<ChannelsHistoryRequest.ChannelsHistoryRequestBuilder> req) throws IOException, SlackApiException {
        return channelsHistory(req.configure(ChannelsHistoryRequest.builder()).build());
    }

    @Override
    public ChannelsRepliesResponse channelsReplies(ChannelsRepliesRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CHANNELS_REPLIES, getToken(req), ChannelsRepliesResponse.class);
    }

    @Override
    public ChannelsRepliesResponse channelsReplies(RequestConfigurator<ChannelsRepliesRequest.ChannelsRepliesRequestBuilder> req) throws IOException, SlackApiException {
        return channelsReplies(req.configure(ChannelsRepliesRequest.builder()).build());
    }

    @Override
    public ChannelsInfoResponse channelsInfo(ChannelsInfoRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CHANNELS_INFO, getToken(req), ChannelsInfoResponse.class);
    }

    @Override
    public ChannelsInfoResponse channelsInfo(RequestConfigurator<ChannelsInfoRequest.ChannelsInfoRequestBuilder> req) throws IOException, SlackApiException {
        return channelsInfo(req.configure(ChannelsInfoRequest.builder()).build());
    }

    @Override
    public ChannelsListResponse channelsList(ChannelsListRequest req) throws IOException, SlackApiException {
        this.teamId.ifPresent(currentTeamId -> {
            if (req.getTeamId() == null) req.setTeamId(currentTeamId);
        });
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CHANNELS_LIST, getToken(req), ChannelsListResponse.class);
    }

    @Override
    public ChannelsListResponse channelsList(RequestConfigurator<ChannelsListRequest.ChannelsListRequestBuilder> req) throws IOException, SlackApiException {
        return channelsList(req.configure(ChannelsListRequest.builder()).build());
    }

    @Override
    public ChannelsInviteResponse channelsInvite(ChannelsInviteRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CHANNELS_INVITE, getToken(req), ChannelsInviteResponse.class);
    }

    @Override
    public ChannelsInviteResponse channelsInvite(RequestConfigurator<ChannelsInviteRequest.ChannelsInviteRequestBuilder> req) throws IOException, SlackApiException {
        return channelsInvite(req.configure(ChannelsInviteRequest.builder()).build());
    }

    @Override
    public ChannelsJoinResponse channelsJoin(ChannelsJoinRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CHANNELS_JOIN, getToken(req), ChannelsJoinResponse.class);
    }

    @Override
    public ChannelsJoinResponse channelsJoin(RequestConfigurator<ChannelsJoinRequest.ChannelsJoinRequestBuilder> req) throws IOException, SlackApiException {
        return channelsJoin(req.configure(ChannelsJoinRequest.builder()).build());
    }

    @Override
    public ChannelsKickResponse channelsKick(ChannelsKickRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CHANNELS_KICK, getToken(req), ChannelsKickResponse.class);
    }

    @Override
    public ChannelsKickResponse channelsKick(RequestConfigurator<ChannelsKickRequest.ChannelsKickRequestBuilder> req) throws IOException, SlackApiException {
        return channelsKick(req.configure(ChannelsKickRequest.builder()).build());
    }

    @Override
    public ChannelsLeaveResponse channelsLeave(ChannelsLeaveRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CHANNELS_LEAVE, getToken(req), ChannelsLeaveResponse.class);
    }

    @Override
    public ChannelsLeaveResponse channelsLeave(RequestConfigurator<ChannelsLeaveRequest.ChannelsLeaveRequestBuilder> req) throws IOException, SlackApiException {
        return channelsLeave(req.configure(ChannelsLeaveRequest.builder()).build());
    }

    @Override
    public ChannelsMarkResponse channelsMark(ChannelsMarkRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CHANNELS_MARK, getToken(req), ChannelsMarkResponse.class);
    }

    @Override
    public ChannelsMarkResponse channelsMark(RequestConfigurator<ChannelsMarkRequest.ChannelsMarkRequestBuilder> req) throws IOException, SlackApiException {
        return channelsMark(req.configure(ChannelsMarkRequest.builder()).build());
    }

    @Override
    public ChannelsRenameResponse channelsRename(ChannelsRenameRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CHANNELS_RENAME, getToken(req), ChannelsRenameResponse.class);
    }

    @Override
    public ChannelsRenameResponse channelsRename(RequestConfigurator<ChannelsRenameRequest.ChannelsRenameRequestBuilder> req) throws IOException, SlackApiException {
        return channelsRename(req.configure(ChannelsRenameRequest.builder()).build());
    }

    @Override
    public ChannelsSetPurposeResponse channelsSetPurpose(ChannelsSetPurposeRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CHANNELS_SET_PURPOSE, getToken(req), ChannelsSetPurposeResponse.class);
    }

    @Override
    public ChannelsSetPurposeResponse channelsSetPurpose(RequestConfigurator<ChannelsSetPurposeRequest.ChannelsSetPurposeRequestBuilder> req) throws IOException, SlackApiException {
        return channelsSetPurpose(req.configure(ChannelsSetPurposeRequest.builder()).build());
    }

    @Override
    public ChannelsSetTopicResponse channelsSetTopic(ChannelsSetTopicRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CHANNELS_SET_TOPIC, getToken(req), ChannelsSetTopicResponse.class);
    }

    @Override
    public ChannelsSetTopicResponse channelsSetTopic(RequestConfigurator<ChannelsSetTopicRequest.ChannelsSetTopicRequestBuilder> req) throws IOException, SlackApiException {
        return channelsSetTopic(req.configure(ChannelsSetTopicRequest.builder()).build());
    }

    @Override
    public ChannelsUnarchiveResponse channelsUnarchive(ChannelsUnarchiveRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CHANNELS_UNARCHIVE, getToken(req), ChannelsUnarchiveResponse.class);
    }

    @Override
    public ChannelsUnarchiveResponse channelsUnarchive(RequestConfigurator<ChannelsUnarchiveRequest.ChannelsUnarchiveRequestBuilder> req) throws IOException, SlackApiException {
        return channelsUnarchive(req.configure(ChannelsUnarchiveRequest.builder()).build());
    }

    @Override
    public ChatAppendStreamResponse chatAppendStream(ChatAppendStreamRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CHAT_APPEND_STREAM, getToken(req), ChatAppendStreamResponse.class);
    }

    @Override
    public ChatAppendStreamResponse chatAppendStream(RequestConfigurator<ChatAppendStreamRequest.ChatAppendStreamRequestBuilder> req) throws IOException, SlackApiException {
        return chatAppendStream(req.configure(ChatAppendStreamRequest.builder()).build());
    }

    @Override
    public ChatDeleteResponse chatDelete(ChatDeleteRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CHAT_DELETE, getToken(req), ChatDeleteResponse.class);
    }

    @Override
    public ChatDeleteResponse chatDelete(RequestConfigurator<ChatDeleteRequest.ChatDeleteRequestBuilder> req) throws IOException, SlackApiException {
        return chatDelete(req.configure(ChatDeleteRequest.builder()).build());
    }

    @Override
    public ChatDeleteScheduledMessageResponse chatDeleteScheduledMessage(ChatDeleteScheduledMessageRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CHAT_DELETE_SCHEDULED_MESSAGE, getToken(req), ChatDeleteScheduledMessageResponse.class);
    }

    @Override
    public ChatDeleteScheduledMessageResponse chatDeleteScheduledMessage(RequestConfigurator<ChatDeleteScheduledMessageRequest.ChatDeleteScheduledMessageRequestBuilder> req) throws IOException, SlackApiException {
        return chatDeleteScheduledMessage(req.configure(ChatDeleteScheduledMessageRequest.builder()).build());
    }

    @Override
    public ChatGetPermalinkResponse chatGetPermalink(ChatGetPermalinkRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CHAT_GET_PERMALINK, getToken(req), ChatGetPermalinkResponse.class);
    }

    @Override
    public ChatGetPermalinkResponse chatGetPermalink(RequestConfigurator<ChatGetPermalinkRequest.ChatGetPermalinkRequestBuilder> req) throws IOException, SlackApiException {
        return chatGetPermalink(req.configure(ChatGetPermalinkRequest.builder()).build());
    }


    @Override
    public ChatMeMessageResponse chatMeMessage(ChatMeMessageRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CHAT_ME_MESSAGE, getToken(req), ChatMeMessageResponse.class);
    }

    @Override
    public ChatMeMessageResponse chatMeMessage(RequestConfigurator<ChatMeMessageRequest.ChatMeMessageRequestBuilder> req) throws IOException, SlackApiException {
        return chatMeMessage(req.configure(ChatMeMessageRequest.builder()).build());
    }

    @Override
    public ChatPostEphemeralResponse chatPostEphemeral(ChatPostEphemeralRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CHAT_POST_EPHEMERAL, getToken(req), ChatPostEphemeralResponse.class);
    }

    @Override
    public ChatPostEphemeralResponse chatPostEphemeral(RequestConfigurator<ChatPostEphemeralRequest.ChatPostEphemeralRequestBuilder> req) throws IOException, SlackApiException {
        return chatPostEphemeral(req.configure(ChatPostEphemeralRequest.builder()).build());
    }

    @Override
    public ChatPostMessageResponse chatPostMessage(ChatPostMessageRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CHAT_POST_MESSAGE, getToken(req), ChatPostMessageResponse.class);
    }

    @Override
    public ChatPostMessageResponse chatPostMessage(RequestConfigurator<ChatPostMessageRequest.ChatPostMessageRequestBuilder> req) throws IOException, SlackApiException {
        return chatPostMessage(req.configure(ChatPostMessageRequest.builder()).build());
    }

    @Override
    public ChatScheduleMessageResponse chatScheduleMessage(ChatScheduleMessageRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CHAT_SCHEDULE_MESSAGE, getToken(req), ChatScheduleMessageResponse.class);
    }

    @Override
    public ChatScheduleMessageResponse chatScheduleMessage(RequestConfigurator<ChatScheduleMessageRequest.ChatScheduleMessageRequestBuilder> req) throws IOException, SlackApiException {
        return chatScheduleMessage(req.configure(ChatScheduleMessageRequest.builder()).build());
    }

    @Override
    public ChatStartStreamResponse chatStartStream(ChatStartStreamRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CHAT_START_STREAM, getToken(req), ChatStartStreamResponse.class);
    }

    @Override
    public ChatStartStreamResponse chatStartStream(RequestConfigurator<ChatStartStreamRequest.ChatStartStreamRequestBuilder> req) throws IOException, SlackApiException {
        return chatStartStream(req.configure(ChatStartStreamRequest.builder()).build());
    }

    @Override
    public ChatStopStreamResponse chatStopStream(ChatStopStreamRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CHAT_STOP_STREAM, getToken(req), ChatStopStreamResponse.class);
    }

    @Override
    public ChatStopStreamResponse chatStopStream(RequestConfigurator<ChatStopStreamRequest.ChatStopStreamRequestBuilder> req) throws IOException, SlackApiException {
        return chatStopStream(req.configure(ChatStopStreamRequest.builder()).build());
    }

    @Override
    public ChatUpdateResponse chatUpdate(ChatUpdateRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CHAT_UPDATE, getToken(req), ChatUpdateResponse.class);
    }

    @Override
    public ChatUpdateResponse chatUpdate(RequestConfigurator<ChatUpdateRequest.ChatUpdateRequestBuilder> req) throws IOException, SlackApiException {
        return chatUpdate(req.configure(ChatUpdateRequest.builder()).build());
    }

    @Override
    public ChatUnfurlResponse chatUnfurl(ChatUnfurlRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CHAT_UNFURL, getToken(req), ChatUnfurlResponse.class);
    }

    @Override
    public ChatUnfurlResponse chatUnfurl(RequestConfigurator<ChatUnfurlRequest.ChatUnfurlRequestBuilder> req) throws IOException, SlackApiException {
        return chatUnfurl(req.configure(ChatUnfurlRequest.builder()).build());
    }

    @Override
    public ChatScheduledMessagesListResponse chatScheduledMessagesList(ChatScheduledMessagesListRequest req) throws IOException, SlackApiException {
        this.teamId.ifPresent(currentTeamId -> {
            if (req.getTeamId() == null) req.setTeamId(currentTeamId);
        });
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CHAT_SCHEDULED_MESSAGES_LIST, getToken(req), ChatScheduledMessagesListResponse.class);
    }

    @Override
    public ChatScheduledMessagesListResponse chatScheduledMessagesList(RequestConfigurator<ChatScheduledMessagesListRequest.ChatScheduledMessagesListRequestBuilder> req) throws IOException, SlackApiException {
        return chatScheduledMessagesList(req.configure(ChatScheduledMessagesListRequest.builder()).build());
    }

    @Override
    public ConversationsArchiveResponse conversationsArchive(ConversationsArchiveRequest req)
            throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CONVERSATIONS_ARCHIVE, getToken(req), ConversationsArchiveResponse.class);
    }

    @Override
    public ConversationsArchiveResponse conversationsArchive(RequestConfigurator<ConversationsArchiveRequest.ConversationsArchiveRequestBuilder> req) throws IOException, SlackApiException {
        return conversationsArchive(req.configure(ConversationsArchiveRequest.builder()).build());
    }

    @Override
    public ConversationsCloseResponse conversationsClose(ConversationsCloseRequest req)
            throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CONVERSATIONS_CLOSE, getToken(req), ConversationsCloseResponse.class);
    }

    @Override
    public ConversationsCloseResponse conversationsClose(RequestConfigurator<ConversationsCloseRequest.ConversationsCloseRequestBuilder> req) throws IOException, SlackApiException {
        return conversationsClose(req.configure(ConversationsCloseRequest.builder()).build());
    }

    @Override
    public ConversationsCreateResponse conversationsCreate(ConversationsCreateRequest req)
            throws IOException, SlackApiException {
        this.teamId.ifPresent(currentTeamId -> {
            if (req.getTeamId() == null) req.setTeamId(currentTeamId);
        });
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CONVERSATIONS_CREATE, getToken(req), ConversationsCreateResponse.class);
    }

    @Override
    public ConversationsCreateResponse conversationsCreate(RequestConfigurator<ConversationsCreateRequest.ConversationsCreateRequestBuilder> req) throws IOException, SlackApiException {
        return conversationsCreate(req.configure(ConversationsCreateRequest.builder()).build());
    }

    @Override
    public ConversationsHistoryResponse conversationsHistory(ConversationsHistoryRequest req)
            throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CONVERSATIONS_HISTORY, getToken(req), ConversationsHistoryResponse.class);
    }

    @Override
    public ConversationsHistoryResponse conversationsHistory(RequestConfigurator<ConversationsHistoryRequest.ConversationsHistoryRequestBuilder> req) throws IOException, SlackApiException {
        return conversationsHistory(req.configure(ConversationsHistoryRequest.builder()).build());
    }

    @Override
    public ConversationsInfoResponse conversationsInfo(ConversationsInfoRequest req)
            throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CONVERSATIONS_INFO, getToken(req), ConversationsInfoResponse.class);
    }

    @Override
    public ConversationsInfoResponse conversationsInfo(RequestConfigurator<ConversationsInfoRequest.ConversationsInfoRequestBuilder> req) throws IOException, SlackApiException {
        return conversationsInfo(req.configure(ConversationsInfoRequest.builder()).build());
    }

    @Override
    public ConversationsInviteResponse conversationsInvite(ConversationsInviteRequest req)
            throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CONVERSATIONS_INVITE, getToken(req), ConversationsInviteResponse.class);
    }

    @Override
    public ConversationsInviteResponse conversationsInvite(RequestConfigurator<ConversationsInviteRequest.ConversationsInviteRequestBuilder> req) throws IOException, SlackApiException {
        return conversationsInvite(req.configure(ConversationsInviteRequest.builder()).build());
    }

    @Override
    public ConversationsJoinResponse conversationsJoin(ConversationsJoinRequest req)
            throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CONVERSATIONS_JOIN, getToken(req), ConversationsJoinResponse.class);
    }

    @Override
    public ConversationsJoinResponse conversationsJoin(RequestConfigurator<ConversationsJoinRequest.ConversationsJoinRequestBuilder> req) throws IOException, SlackApiException {
        return conversationsJoin(req.configure(ConversationsJoinRequest.builder()).build());
    }

    @Override
    public ConversationsKickResponse conversationsKick(ConversationsKickRequest req)
            throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CONVERSATIONS_KICK, getToken(req), ConversationsKickResponse.class);
    }

    @Override
    public ConversationsKickResponse conversationsKick(RequestConfigurator<ConversationsKickRequest.ConversationsKickRequestBuilder> req) throws IOException, SlackApiException {
        return conversationsKick(req.configure(ConversationsKickRequest.builder()).build());
    }

    @Override
    public ConversationsLeaveResponse conversationsLeave(ConversationsLeaveRequest req)
            throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CONVERSATIONS_LEAVE, getToken(req), ConversationsLeaveResponse.class);
    }

    @Override
    public ConversationsLeaveResponse conversationsLeave(RequestConfigurator<ConversationsLeaveRequest.ConversationsLeaveRequestBuilder> req) throws IOException, SlackApiException {
        return conversationsLeave(req.configure(ConversationsLeaveRequest.builder()).build());
    }

    @Override
    public ConversationsListResponse conversationsList(ConversationsListRequest req)
            throws IOException, SlackApiException {
        this.teamId.ifPresent(currentTeamId -> {
            if (req.getTeamId() == null) req.setTeamId(currentTeamId);
        });
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CONVERSATIONS_LIST, getToken(req), ConversationsListResponse.class);
    }

    @Override
    public ConversationsListResponse conversationsList(RequestConfigurator<ConversationsListRequest.ConversationsListRequestBuilder> req) throws IOException, SlackApiException {
        return conversationsList(req.configure(ConversationsListRequest.builder()).build());
    }

    @Override
    public ConversationsMarkResponse conversationsMark(ConversationsMarkRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CONVERSATIONS_MARK, getToken(req), ConversationsMarkResponse.class);
    }

    @Override
    public ConversationsMarkResponse conversationsMark(RequestConfigurator<ConversationsMarkRequest.ConversationsMarkRequestBuilder> req) throws IOException, SlackApiException {
        return conversationsMark(req.configure(ConversationsMarkRequest.builder()).build());
    }

    @Override
    public ConversationsMembersResponse conversationsMembers(ConversationsMembersRequest req)
            throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CONVERSATIONS_MEMBERS, getToken(req), ConversationsMembersResponse.class);
    }

    @Override
    public ConversationsMembersResponse conversationsMembers(RequestConfigurator<ConversationsMembersRequest.ConversationsMembersRequestBuilder> req) throws IOException, SlackApiException {
        return conversationsMembers(req.configure(ConversationsMembersRequest.builder()).build());
    }

    @Override
    public ConversationsOpenResponse conversationsOpen(ConversationsOpenRequest req)
            throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CONVERSATIONS_OPEN, getToken(req), ConversationsOpenResponse.class);
    }

    @Override
    public ConversationsOpenResponse conversationsOpen(RequestConfigurator<ConversationsOpenRequest.ConversationsOpenRequestBuilder> req) throws IOException, SlackApiException {
        return conversationsOpen(req.configure(ConversationsOpenRequest.builder()).build());
    }

    @Override
    public ConversationsRenameResponse conversationsRename(ConversationsRenameRequest req)
            throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CONVERSATIONS_RENAME, getToken(req), ConversationsRenameResponse.class);
    }

    @Override
    public ConversationsRenameResponse conversationsRename(RequestConfigurator<ConversationsRenameRequest.ConversationsRenameRequestBuilder> req) throws IOException, SlackApiException {
        return conversationsRename(req.configure(ConversationsRenameRequest.builder()).build());
    }

    @Override
    public ConversationsRepliesResponse conversationsReplies(ConversationsRepliesRequest req)
            throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CONVERSATIONS_REPLIES, getToken(req), ConversationsRepliesResponse.class);
    }

    @Override
    public ConversationsRepliesResponse conversationsReplies(RequestConfigurator<ConversationsRepliesRequest.ConversationsRepliesRequestBuilder> req) throws IOException, SlackApiException {
        return conversationsReplies(req.configure(ConversationsRepliesRequest.builder()).build());
    }

    @Override
    public ConversationsSetPurposeResponse conversationsSetPurpose(ConversationsSetPurposeRequest req)
            throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CONVERSATIONS_SET_PURPOSE, getToken(req), ConversationsSetPurposeResponse.class);
    }

    @Override
    public ConversationsSetPurposeResponse conversationsSetPurpose(RequestConfigurator<ConversationsSetPurposeRequest.ConversationsSetPurposeRequestBuilder> req) throws IOException, SlackApiException {
        return conversationsSetPurpose(req.configure(ConversationsSetPurposeRequest.builder()).build());
    }

    @Override
    public ConversationsSetTopicResponse conversationsSetTopic(ConversationsSetTopicRequest req)
            throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CONVERSATIONS_SET_TOPIC, getToken(req), ConversationsSetTopicResponse.class);
    }

    @Override
    public ConversationsSetTopicResponse conversationsSetTopic(RequestConfigurator<ConversationsSetTopicRequest.ConversationsSetTopicRequestBuilder> req) throws IOException, SlackApiException {
        return conversationsSetTopic(req.configure(ConversationsSetTopicRequest.builder()).build());
    }

    @Override
    public ConversationsUnarchiveResponse conversationsUnarchive(ConversationsUnarchiveRequest req)
            throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CONVERSATIONS_UNARCHIVE, getToken(req), ConversationsUnarchiveResponse.class);
    }

    @Override
    public ConversationsUnarchiveResponse conversationsUnarchive(RequestConfigurator<ConversationsUnarchiveRequest.ConversationsUnarchiveRequestBuilder> req) throws IOException, SlackApiException {
        return conversationsUnarchive(req.configure(ConversationsUnarchiveRequest.builder()).build());
    }

    @Override
    public ConversationsExternalInvitePermissionsSetResponse conversationsExternalInvitePermissionsSet(ConversationsExternalInvitePermissionsSetRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CONVERSATIONS_EXTERNAL_INVITE_PERMISSIONS_SET, getToken(req), ConversationsExternalInvitePermissionsSetResponse.class);
    }

    @Override
    public ConversationsExternalInvitePermissionsSetResponse conversationsExternalInvitePermissionsSet(RequestConfigurator<ConversationsExternalInvitePermissionsSetRequest.ConversationsExternalInvitePermissionsSetRequestBuilder> req) throws IOException, SlackApiException {
        return conversationsExternalInvitePermissionsSet(req.configure(ConversationsExternalInvitePermissionsSetRequest.builder()).build());
    }

    @Override
    public ConversationsInviteSharedResponse conversationsInviteShared(ConversationsInviteSharedRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CONVERSATIONS_INVITE_SHARED, getToken(req), ConversationsInviteSharedResponse.class);
    }

    @Override
    public ConversationsInviteSharedResponse conversationsInviteShared(RequestConfigurator<ConversationsInviteSharedRequest.ConversationsInviteSharedRequestBuilder> req) throws IOException, SlackApiException {
        return conversationsInviteShared(req.configure(ConversationsInviteSharedRequest.builder()).build());
    }

    @Override
    public ConversationsAcceptSharedInviteResponse conversationsAcceptSharedInvite(ConversationsAcceptSharedInviteRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CONVERSATIONS_ACCEPT_SHARED_INVITE, getToken(req), ConversationsAcceptSharedInviteResponse.class);
    }

    @Override
    public ConversationsAcceptSharedInviteResponse conversationsAcceptSharedInvite(RequestConfigurator<ConversationsAcceptSharedInviteRequest.ConversationsAcceptSharedInviteRequestBuilder> req) throws IOException, SlackApiException {
        return conversationsAcceptSharedInvite(req.configure(ConversationsAcceptSharedInviteRequest.builder()).build());
    }

    @Override
    public ConversationsApproveSharedInviteResponse conversationsApproveSharedInvite(ConversationsApproveSharedInviteRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CONVERSATIONS_APPROVE_SHARED_INVITE, getToken(req), ConversationsApproveSharedInviteResponse.class);
    }

    @Override
    public ConversationsApproveSharedInviteResponse conversationsApproveSharedInvite(RequestConfigurator<ConversationsApproveSharedInviteRequest.ConversationsApproveSharedInviteRequestBuilder> req) throws IOException, SlackApiException {
        return conversationsApproveSharedInvite(req.configure(ConversationsApproveSharedInviteRequest.builder()).build());
    }

    @Override
    public ConversationsDeclineSharedInviteResponse conversationsDeclineSharedInvite(ConversationsDeclineSharedInviteRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CONVERSATIONS_DECLINE_SHARED_INVITE, getToken(req), ConversationsDeclineSharedInviteResponse.class);
    }

    @Override
    public ConversationsDeclineSharedInviteResponse conversationsDeclineSharedInvite(RequestConfigurator<ConversationsDeclineSharedInviteRequest.ConversationsDeclineSharedInviteRequestBuilder> req) throws IOException, SlackApiException {
        return conversationsDeclineSharedInvite(req.configure(ConversationsDeclineSharedInviteRequest.builder()).build());
    }

    @Override
    public ConversationsListConnectInvitesResponse conversationsListConnectInvites(ConversationsListConnectInvitesRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CONVERSATIONS_LIST_CONNECT_INVITES, getToken(req), ConversationsListConnectInvitesResponse.class);
    }

    @Override
    public ConversationsListConnectInvitesResponse conversationsListConnectInvites(RequestConfigurator<ConversationsListConnectInvitesRequest.ConversationsListConnectInvitesRequestBuilder> req) throws IOException, SlackApiException {
        return conversationsListConnectInvites(req.configure(ConversationsListConnectInvitesRequest.builder()).build());
    }

    @Override
    public ConversationsCanvasesCreateResponse conversationsCanvasesCreate(ConversationsCanvasesCreateRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CONVERSATIONS_CANVASES_CREATE, getToken(req), ConversationsCanvasesCreateResponse.class);
    }

    @Override
    public ConversationsCanvasesCreateResponse conversationsCanvasesCreate(RequestConfigurator<ConversationsCanvasesCreateRequest.ConversationsCanvasesCreateRequestBuilder> req) throws IOException, SlackApiException {
        return conversationsCanvasesCreate(req.configure(ConversationsCanvasesCreateRequest.builder()).build());
    }

    @Override
    public ConversationsRequestSharedInviteApproveResponse conversationsRequestSharedInviteApprove(ConversationsRequestSharedInviteApproveRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CONVERSATIONS_REQUEST_SHARED_INVITE_APPROVE, getToken(req), ConversationsRequestSharedInviteApproveResponse.class);
    }

    @Override
    public ConversationsRequestSharedInviteApproveResponse conversationsRequestSharedInviteApprove(RequestConfigurator<ConversationsRequestSharedInviteApproveRequest.ConversationsRequestSharedInviteApproveRequestBuilder> req) throws IOException, SlackApiException {
        return conversationsRequestSharedInviteApprove(req.configure(ConversationsRequestSharedInviteApproveRequest.builder()).build());
    }

    @Override
    public ConversationsRequestSharedInviteDenyResponse conversationsRequestSharedInviteDeny(ConversationsRequestSharedInviteDenyRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CONVERSATIONS_REQUEST_SHARED_INVITE_DENY, getToken(req), ConversationsRequestSharedInviteDenyResponse.class);
    }

    @Override
    public ConversationsRequestSharedInviteDenyResponse conversationsRequestSharedInviteDeny(RequestConfigurator<ConversationsRequestSharedInviteDenyRequest.ConversationsRequestSharedInviteDenyRequestBuilder> req) throws IOException, SlackApiException {
        return conversationsRequestSharedInviteDeny(req.configure(ConversationsRequestSharedInviteDenyRequest.builder()).build());
    }

    @Override
    public ConversationsRequestSharedInviteListResponse conversationsRequestSharedInviteList(ConversationsRequestSharedInviteListRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.CONVERSATIONS_REQUEST_SHARED_INVITE_LIST, getToken(req), ConversationsRequestSharedInviteListResponse.class);
    }

    @Override
    public ConversationsRequestSharedInviteListResponse conversationsRequestSharedInviteList(RequestConfigurator<ConversationsRequestSharedInviteListRequest.ConversationsRequestSharedInviteListRequestBuilder> req) throws IOException, SlackApiException {
        return conversationsRequestSharedInviteList(req.configure(ConversationsRequestSharedInviteListRequest.builder()).build());
    }

    @Override
    public DialogOpenResponse dialogOpen(DialogOpenRequest req)
            throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.DIALOG_OPEN, getToken(req), DialogOpenResponse.class);
    }

    @Override
    public DialogOpenResponse dialogOpen(RequestConfigurator<DialogOpenRequest.DialogOpenRequestBuilder> req) throws IOException, SlackApiException {
        return dialogOpen(req.configure(DialogOpenRequest.builder()).build());
    }

    @Override
    public DndEndDndResponse dndEndDnd(DndEndDndRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.DND_END_DND, getToken(req), DndEndDndResponse.class);
    }

    @Override
    public DndEndDndResponse dndEndDnd(RequestConfigurator<DndEndDndRequest.DndEndDndRequestBuilder> req) throws IOException, SlackApiException {
        return dndEndDnd(req.configure(DndEndDndRequest.builder()).build());
    }

    @Override
    public DndEndSnoozeResponse dndEndSnooze(DndEndSnoozeRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.DND_END_SNOOZE, getToken(req), DndEndSnoozeResponse.class);
    }

    @Override
    public DndEndSnoozeResponse dndEndSnooze(RequestConfigurator<DndEndSnoozeRequest.DndEndSnoozeRequestBuilder> req) throws IOException, SlackApiException {
        return dndEndSnooze(req.configure(DndEndSnoozeRequest.builder()).build());
    }

    @Override
    public DndInfoResponse dndInfo(DndInfoRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.DND_INFO, getToken(req), DndInfoResponse.class);
    }

    @Override
    public DndInfoResponse dndInfo(RequestConfigurator<DndInfoRequest.DndInfoRequestBuilder> req) throws IOException, SlackApiException {
        return dndInfo(req.configure(DndInfoRequest.builder()).build());
    }

    @Override
    public DndSetSnoozeResponse dndSetSnooze(DndSetSnoozeRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.DND_SET_SNOOZE, getToken(req), DndSetSnoozeResponse.class);
    }

    @Override
    public DndSetSnoozeResponse dndSetSnooze(RequestConfigurator<DndSetSnoozeRequest.DndSetSnoozeRequestBuilder> req) throws IOException, SlackApiException {
        return dndSetSnooze(req.configure(DndSetSnoozeRequest.builder()).build());
    }

    @Override
    public DndTeamInfoResponse dndTeamInfo(DndTeamInfoRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.DND_TEAM_INFO, getToken(req), DndTeamInfoResponse.class);
    }

    @Override
    public DndTeamInfoResponse dndTeamInfo(RequestConfigurator<DndTeamInfoRequest.DndTeamInfoRequestBuilder> req) throws IOException, SlackApiException {
        return dndTeamInfo(req.configure(DndTeamInfoRequest.builder()).build());
    }

    @Override
    public EmojiListResponse emojiList(EmojiListRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.EMOJI_LIST, getToken(req), EmojiListResponse.class);
    }

    @Override
    public EmojiListResponse emojiList(RequestConfigurator<EmojiListRequest.EmojiListRequestBuilder> req) throws IOException, SlackApiException {
        return emojiList(req.configure(EmojiListRequest.builder()).build());
    }

    @Override
    public EntityPresentDetailsResponse entityPresentDetails(EntityPresentDetailsRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.ENTITY_PRESENT_DETAILS, getToken(req), EntityPresentDetailsResponse.class);
    }

    @Override
    public EntityPresentDetailsResponse entityPresentDetails(RequestConfigurator<EntityPresentDetailsRequest.EntityPresentDetailsRequestBuilder> req) throws IOException, SlackApiException {
        return entityPresentDetails(req.configure(EntityPresentDetailsRequest.builder()).build());
    }

    @Override
    public FilesDeleteResponse filesDelete(FilesDeleteRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.FILES_DELETE, getToken(req), FilesDeleteResponse.class);
    }

    @Override
    public FilesDeleteResponse filesDelete(RequestConfigurator<FilesDeleteRequest.FilesDeleteRequestBuilder> req) throws IOException, SlackApiException {
        return filesDelete(req.configure(FilesDeleteRequest.builder()).build());
    }

    @Override
    public FilesInfoResponse filesInfo(FilesInfoRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.FILES_INFO, getToken(req), FilesInfoResponse.class);
    }

    @Override
    public FilesInfoResponse filesInfo(RequestConfigurator<FilesInfoRequest.FilesInfoRequestBuilder> req) throws IOException, SlackApiException {
        return filesInfo(req.configure(FilesInfoRequest.builder()).build());
    }

    @Override
    public FilesListResponse filesList(FilesListRequest req) throws IOException, SlackApiException {
        this.teamId.ifPresent(currentTeamId -> {
            if (req.getTeamId() == null) req.setTeamId(currentTeamId);
        });
        return postFormWithTokenAndParseResponse(toForm(req), Methods.FILES_LIST, getToken(req), FilesListResponse.class);
    }

    @Override
    public FilesListResponse filesList(RequestConfigurator<FilesListRequest.FilesListRequestBuilder> req) throws IOException, SlackApiException {
        return filesList(req.configure(FilesListRequest.builder()).build());
    }

    @Override
    public FilesRevokePublicURLResponse filesRevokePublicURL(FilesRevokePublicURLRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.FILES_REVOKE_PUBLIC_URL, getToken(req), FilesRevokePublicURLResponse.class);
    }

    @Override
    public FilesRevokePublicURLResponse filesRevokePublicURL(RequestConfigurator<FilesRevokePublicURLRequest.FilesRevokePublicURLRequestBuilder> req) throws IOException, SlackApiException {
        return filesRevokePublicURL(req.configure(FilesRevokePublicURLRequest.builder()).build());
    }

    @Override
    public FilesSharedPublicURLResponse filesSharedPublicURL(FilesSharedPublicURLRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.FILES_SHARED_PUBLIC_URL, getToken(req), FilesSharedPublicURLResponse.class);
    }

    @Override
    public FilesSharedPublicURLResponse filesSharedPublicURL(RequestConfigurator<FilesSharedPublicURLRequest.FilesSharedPublicURLRequestBuilder> req) throws IOException, SlackApiException {
        return filesSharedPublicURL(req.configure(FilesSharedPublicURLRequest.builder()).build());
    }

    @Override
    public FilesUploadResponse filesUpload(FilesUploadRequest req) throws IOException, SlackApiException {
        String warningMessage = "filesUpload may cause some issues like timeouts for relatively large files. " +
                "Our latest recommendation is to use filesUploadV2, which is mostly compatible and much stabler, instead.";
        log.warn(warningMessage);
        if (req.getFile() != null || req.getFileData() != null) {
            return postMultipartAndParseResponse(toMultipartBody(req), Methods.FILES_UPLOAD, getToken(req), FilesUploadResponse.class);
        } else {
            return postFormWithTokenAndParseResponse(toForm(req), Methods.FILES_UPLOAD, getToken(req), FilesUploadResponse.class);
        }
    }

    @Override
    public FilesUploadResponse filesUpload(RequestConfigurator<FilesUploadRequest.FilesUploadRequestBuilder> req) throws IOException, SlackApiException {
        return filesUpload(req.configure(FilesUploadRequest.builder()).build());
    }

    @Override
    public FilesGetUploadURLExternalResponse filesGetUploadURLExternal(FilesGetUploadURLExternalRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.FILES_GET_UPLOAD_URL_EXTERNAL, getToken(req), FilesGetUploadURLExternalResponse.class);
    }

    @Override
    public FilesGetUploadURLExternalResponse filesGetUploadURLExternal(RequestConfigurator<FilesGetUploadURLExternalRequest.FilesGetUploadURLExternalRequestBuilder> req) throws IOException, SlackApiException {
        return filesGetUploadURLExternal(req.configure(FilesGetUploadURLExternalRequest.builder()).build());
    }

    @Override
    public FilesCompleteUploadExternalResponse filesCompleteUploadExternal(FilesCompleteUploadExternalRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.FILES_COMPLETE_UPLOAD_EXTERNAL, getToken(req), FilesCompleteUploadExternalResponse.class);
    }

    @Override
    public FilesCompleteUploadExternalResponse filesCompleteUploadExternal(RequestConfigurator<FilesCompleteUploadExternalRequest.FilesCompleteUploadExternalRequestBuilder> req) throws IOException, SlackApiException {
        return filesCompleteUploadExternal(req.configure(FilesCompleteUploadExternalRequest.builder()).build());
    }

    @Override
    public FilesUploadV2Response filesUploadV2(FilesUploadV2Request req) throws IOException, SlackApiException, SlackFilesUploadV2Exception {
        try (FilesUploadV2Helper helper = new FilesUploadV2Helper(this)) {
            List<FilesCompleteUploadExternalRequest.FileDetails> files = new ArrayList<>();
            if (req.getUploadFiles() != null && req.getUploadFiles().size() > 0) {
                // upload multiple files
                for (FilesUploadV2Request.UploadFile uploadFile : req.getUploadFiles()) {
                    String filename = uploadFile.getFilename();
                    if (filename == null) {
                        if (uploadFile.getFile() != null && uploadFile.getFile().getName() != null) {
                            filename = uploadFile.getFile().getName();
                        } else {
                            filename = "Uploaded file";
                        }
                    }
                    uploadFile.setFilename(filename);
                    if (uploadFile.getTitle() == null) {
                        uploadFile.setTitle(filename);
                    }

                    String fileId = helper.uploadFile(req, uploadFile);

                    FilesCompleteUploadExternalRequest.FileDetails file = new FilesCompleteUploadExternalRequest.FileDetails();
                    file.setId(fileId);
                    file.setTitle(uploadFile.getTitle());

                    files.add(file);
                }
            } else {
                FilesUploadV2Request.UploadFile uploadFile = new FilesUploadV2Request.UploadFile();
                uploadFile.setFile(req.getFile());
                uploadFile.setFileData(req.getFileData());
                uploadFile.setContent(req.getContent());

                String filename = req.getFilename();
                if (filename == null) {
                    if (req.getFile() != null && req.getFile().getName() != null) {
                        filename = req.getFile().getName();
                    } else {
                        filename = "Uploaded file";
                    }
                }
                uploadFile.setFilename(filename);
                uploadFile.setTitle(req.getTitle() != null ? req.getTitle() : filename);

                uploadFile.setAltTxt(req.getAltTxt());
                uploadFile.setSnippetType(req.getSnippetType());

                String fileId = helper.uploadFile(req, uploadFile);

                FilesCompleteUploadExternalRequest.FileDetails file = new FilesCompleteUploadExternalRequest.FileDetails();
                file.setId(fileId);
                file.setTitle(uploadFile.getTitle());
                files.add(file);
            }
            return helper.completeUploads(req, files);
        }
    }

    @Override
    public FilesUploadV2Response filesUploadV2(RequestConfigurator<FilesUploadV2Request.FilesUploadV2RequestBuilder> req) throws IOException, SlackApiException {
        return filesUploadV2(req.configure(FilesUploadV2Request.builder()).build());
    }

    @Override
    public FilesCommentsAddResponse filesCommentsAdd(FilesCommentsAddRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.FILES_COMMENTS_ADD, getToken(req), FilesCommentsAddResponse.class);
    }

    @Override
    public FilesCommentsDeleteResponse filesCommentsDelete(FilesCommentsDeleteRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.FILES_COMMENTS_DELETE, getToken(req), FilesCommentsDeleteResponse.class);
    }

    @Override
    public FilesCommentsEditResponse filesCommentEdit(FilesCommentsEditRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.FILES_COMMENTS_EDIT, getToken(req), FilesCommentsEditResponse.class);
    }

    @Override
    public FilesRemoteAddResponse filesRemoteAdd(FilesRemoteAddRequest req) throws IOException, SlackApiException {
        return postMultipartAndParseResponse(RequestFormBuilder.toMultipartBody(req), Methods.FILES_REMOTE_ADD, getToken(req), FilesRemoteAddResponse.class);
    }

    @Override
    public FilesRemoteAddResponse filesRemoteAdd(RequestConfigurator<FilesRemoteAddRequest.FilesRemoteAddRequestBuilder> req) throws IOException, SlackApiException {
        return filesRemoteAdd(req.configure(FilesRemoteAddRequest.builder()).build());
    }

    @Override
    public FilesRemoteInfoResponse filesRemoteInfo(FilesRemoteInfoRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.FILES_REMOTE_INFO, getToken(req), FilesRemoteInfoResponse.class);
    }

    @Override
    public FilesRemoteInfoResponse filesRemoteInfo(RequestConfigurator<FilesRemoteInfoRequest.FilesRemoteInfoRequestBuilder> req) throws IOException, SlackApiException {
        return filesRemoteInfo(req.configure(FilesRemoteInfoRequest.builder()).build());
    }

    @Override
    public FilesRemoteListResponse filesRemoteList(FilesRemoteListRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.FILES_REMOTE_LIST, getToken(req), FilesRemoteListResponse.class);
    }

    @Override
    public FilesRemoteListResponse filesRemoteList(RequestConfigurator<FilesRemoteListRequest.FilesRemoteListRequestBuilder> req) throws IOException, SlackApiException {
        return filesRemoteList(req.configure(FilesRemoteListRequest.builder()).build());
    }

    @Override
    public FilesRemoteRemoveResponse filesRemoteRemove(FilesRemoteRemoveRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.FILES_REMOTE_REMOVE, getToken(req), FilesRemoteRemoveResponse.class);
    }

    @Override
    public FilesRemoteRemoveResponse filesRemoteRemove(RequestConfigurator<FilesRemoteRemoveRequest.FilesRemoteRemoveRequestBuilder> req) throws IOException, SlackApiException {
        return filesRemoteRemove(req.configure(FilesRemoteRemoveRequest.builder()).build());
    }

    @Override
    public FilesRemoteShareResponse filesRemoteShare(FilesRemoteShareRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.FILES_REMOTE_SHARE, getToken(req), FilesRemoteShareResponse.class);
    }

    @Override
    public FilesRemoteShareResponse filesRemoteShare(RequestConfigurator<FilesRemoteShareRequest.FilesRemoteShareRequestBuilder> req) throws IOException, SlackApiException {
        return filesRemoteShare(req.configure(FilesRemoteShareRequest.builder()).build());
    }

    @Override
    public FilesRemoteUpdateResponse filesRemoteUpdate(FilesRemoteUpdateRequest req) throws IOException, SlackApiException {
        return postMultipartAndParseResponse(toMultipartBody(req), Methods.FILES_REMOTE_UPDATE, getToken(req), FilesRemoteUpdateResponse.class);
    }

    @Override
    public FilesRemoteUpdateResponse filesRemoteUpdate(RequestConfigurator<FilesRemoteUpdateRequest.FilesRemoteUpdateRequestBuilder> req) throws IOException, SlackApiException {
        return filesRemoteUpdate(req.configure(FilesRemoteUpdateRequest.builder()).build());
    }

    @Override
    public FunctionsCompleteSuccessResponse functionsCompleteSuccess(FunctionsCompleteSuccessRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.FUNCTIONS_COMPLETE_SUCCESS, getToken(req), FunctionsCompleteSuccessResponse.class);
    }

    @Override
    public FunctionsCompleteSuccessResponse functionsCompleteSuccess(RequestConfigurator<FunctionsCompleteSuccessRequest.FunctionsCompleteSuccessRequestBuilder> req) throws IOException, SlackApiException {
        return functionsCompleteSuccess(req.configure(FunctionsCompleteSuccessRequest.builder()).build());
    }

    @Override
    public FunctionsCompleteErrorResponse functionsCompleteError(FunctionsCompleteErrorRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.FUNCTIONS_COMPLETE_ERROR, getToken(req), FunctionsCompleteErrorResponse.class);
    }

    @Override
    public FunctionsCompleteErrorResponse functionsCompleteError(RequestConfigurator<FunctionsCompleteErrorRequest.FunctionsCompleteErrorRequestBuilder> req) throws IOException, SlackApiException {
        return functionsCompleteError(req.configure(FunctionsCompleteErrorRequest.builder()).build());
    }

    @Override
    public GroupsArchiveResponse groupsArchive(GroupsArchiveRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.GROUPS_ARCHIVE, getToken(req), GroupsArchiveResponse.class);
    }

    @Override
    public GroupsArchiveResponse groupsArchive(RequestConfigurator<GroupsArchiveRequest.GroupsArchiveRequestBuilder> req) throws IOException, SlackApiException {
        return groupsArchive(req.configure(GroupsArchiveRequest.builder()).build());
    }

    @Override
    public GroupsCloseResponse groupsClose(GroupsCloseRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.GROUPS_CLOSE, getToken(req), GroupsCloseResponse.class);
    }

    @Override
    public GroupsCreateChildResponse groupsCreateChild(GroupsCreateChildRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.GROUPS_CREATE_CHILD, getToken(req), GroupsCreateChildResponse.class);
    }

    @Override
    public GroupsCreateChildResponse groupsCreateChild(RequestConfigurator<GroupsCreateChildRequest.GroupsCreateChildRequestBuilder> req) throws IOException, SlackApiException {
        return groupsCreateChild(req.configure(GroupsCreateChildRequest.builder()).build());
    }

    @Override
    public GroupsCreateResponse groupsCreate(GroupsCreateRequest req) throws IOException, SlackApiException {
        this.teamId.ifPresent(currentTeamId -> {
            if (req.getTeamId() == null) req.setTeamId(currentTeamId);
        });
        return postFormWithTokenAndParseResponse(toForm(req), Methods.GROUPS_CREATE, getToken(req), GroupsCreateResponse.class);
    }

    @Override
    public GroupsCreateResponse groupsCreate(RequestConfigurator<GroupsCreateRequest.GroupsCreateRequestBuilder> req) throws IOException, SlackApiException {
        return groupsCreate(req.configure(GroupsCreateRequest.builder()).build());
    }

    @Override
    public GroupsHistoryResponse groupsHistory(GroupsHistoryRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.GROUPS_HISTORY, getToken(req), GroupsHistoryResponse.class);
    }

    @Override
    public GroupsHistoryResponse groupsHistory(RequestConfigurator<GroupsHistoryRequest.GroupsHistoryRequestBuilder> req) throws IOException, SlackApiException {
        return groupsHistory(req.configure(GroupsHistoryRequest.builder()).build());
    }

    @Override
    public GroupsRepliesResponse groupsReplies(GroupsRepliesRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.GROUPS_REPLIES, getToken(req), GroupsRepliesResponse.class);
    }

    @Override
    public GroupsRepliesResponse groupsReplies(RequestConfigurator<GroupsRepliesRequest.GroupsRepliesRequestBuilder> req) throws IOException, SlackApiException {
        return groupsReplies(req.configure(GroupsRepliesRequest.builder()).build());
    }

    @Override
    public GroupsInfoResponse groupsInfo(GroupsInfoRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.GROUPS_INFO, getToken(req), GroupsInfoResponse.class);
    }

    @Override
    public GroupsInfoResponse groupsInfo(RequestConfigurator<GroupsInfoRequest.GroupsInfoRequestBuilder> req) throws IOException, SlackApiException {
        return groupsInfo(req.configure(GroupsInfoRequest.builder()).build());
    }

    @Override
    public GroupsInviteResponse groupsInvite(GroupsInviteRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.GROUPS_INVITE, getToken(req), GroupsInviteResponse.class);
    }

    @Override
    public GroupsInviteResponse groupsInvite(RequestConfigurator<GroupsInviteRequest.GroupsInviteRequestBuilder> req) throws IOException, SlackApiException {
        return groupsInvite(req.configure(GroupsInviteRequest.builder()).build());
    }

    @Override
    public GroupsKickResponse groupsKick(GroupsKickRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.GROUPS_KICK, getToken(req), GroupsKickResponse.class);
    }

    @Override
    public GroupsKickResponse groupsKick(RequestConfigurator<GroupsKickRequest.GroupsKickRequestBuilder> req) throws IOException, SlackApiException {
        return groupsKick(req.configure(GroupsKickRequest.builder()).build());
    }

    @Override
    public GroupsLeaveResponse groupsLeave(GroupsLeaveRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.GROUPS_LEAVE, getToken(req), GroupsLeaveResponse.class);
    }

    @Override
    public GroupsLeaveResponse groupsLeave(RequestConfigurator<GroupsLeaveRequest.GroupsLeaveRequestBuilder> req) throws IOException, SlackApiException {
        return groupsLeave(req.configure(GroupsLeaveRequest.builder()).build());
    }

    @Override
    public GroupsListResponse groupsList(GroupsListRequest req) throws IOException, SlackApiException {
        this.teamId.ifPresent(currentTeamId -> {
            if (req.getTeamId() == null) req.setTeamId(currentTeamId);
        });
        return postFormWithTokenAndParseResponse(toForm(req), Methods.GROUPS_LIST, getToken(req), GroupsListResponse.class);
    }

    @Override
    public GroupsListResponse groupsList(RequestConfigurator<GroupsListRequest.GroupsListRequestBuilder> req) throws IOException, SlackApiException {
        return groupsList(req.configure(GroupsListRequest.builder()).build());
    }

    @Override
    public GroupsMarkResponse groupsMark(GroupsMarkRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.GROUPS_MARK, getToken(req), GroupsMarkResponse.class);
    }

    @Override
    public GroupsMarkResponse groupsMark(RequestConfigurator<GroupsMarkRequest.GroupsMarkRequestBuilder> req) throws IOException, SlackApiException {
        return groupsMark(req.configure(GroupsMarkRequest.builder()).build());
    }

    @Override
    public GroupsOpenResponse groupsOpen(GroupsOpenRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.GROUPS_OPEN, getToken(req), GroupsOpenResponse.class);
    }

    @Override
    public GroupsOpenResponse groupsOpen(RequestConfigurator<GroupsOpenRequest.GroupsOpenRequestBuilder> req) throws IOException, SlackApiException {
        return groupsOpen(req.configure(GroupsOpenRequest.builder()).build());
    }

    @Override
    public GroupsRenameResponse groupsRename(GroupsRenameRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.GROUPS_RENAME, getToken(req), GroupsRenameResponse.class);
    }

    @Override
    public GroupsRenameResponse groupsRename(RequestConfigurator<GroupsRenameRequest.GroupsRenameRequestBuilder> req) throws IOException, SlackApiException {
        return groupsRename(req.configure(GroupsRenameRequest.builder()).build());
    }

    @Override
    public GroupsSetPurposeResponse groupsSetPurpose(GroupsSetPurposeRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.GROUPS_SET_PURPOSE, getToken(req), GroupsSetPurposeResponse.class);
    }

    @Override
    public GroupsSetPurposeResponse groupsSetPurpose(RequestConfigurator<GroupsSetPurposeRequest.GroupsSetPurposeRequestBuilder> req) throws IOException, SlackApiException {
        return groupsSetPurpose(req.configure(GroupsSetPurposeRequest.builder()).build());
    }

    @Override
    public GroupsSetTopicResponse groupsSetTopic(GroupsSetTopicRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.GROUPS_SET_TOPIC, getToken(req), GroupsSetTopicResponse.class);
    }

    @Override
    public GroupsSetTopicResponse groupsSetTopic(RequestConfigurator<GroupsSetTopicRequest.GroupsSetTopicRequestBuilder> req) throws IOException, SlackApiException {
        return groupsSetTopic(req.configure(GroupsSetTopicRequest.builder()).build());
    }

    @Override
    public GroupsUnarchiveResponse groupsUnarchive(GroupsUnarchiveRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.GROUPS_UNARCHIVE, getToken(req), GroupsUnarchiveResponse.class);
    }

    @Override
    public GroupsUnarchiveResponse groupsUnarchive(RequestConfigurator<GroupsUnarchiveRequest.GroupsUnarchiveRequestBuilder> req) throws IOException, SlackApiException {
        return groupsUnarchive(req.configure(GroupsUnarchiveRequest.builder()).build());
    }

    @Override
    public ImCloseResponse imClose(ImCloseRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.IM_CLOSE, getToken(req), ImCloseResponse.class);
    }

    @Override
    public ImCloseResponse imClose(RequestConfigurator<ImCloseRequest.ImCloseRequestBuilder> req) throws IOException, SlackApiException {
        return imClose(req.configure(ImCloseRequest.builder()).build());
    }

    @Override
    public ImHistoryResponse imHistory(ImHistoryRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.IM_HISTORY, getToken(req), ImHistoryResponse.class);
    }

    @Override
    public ImHistoryResponse imHistory(RequestConfigurator<ImHistoryRequest.ImHistoryRequestBuilder> req) throws IOException, SlackApiException {
        return imHistory(req.configure(ImHistoryRequest.builder()).build());
    }

    @Override
    public ImListResponse imList(ImListRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.IM_LIST, getToken(req), ImListResponse.class);
    }

    @Override
    public ImListResponse imList(RequestConfigurator<ImListRequest.ImListRequestBuilder> req) throws IOException, SlackApiException {
        return imList(req.configure(ImListRequest.builder()).build());
    }

    @Override
    public ImMarkResponse imMark(ImMarkRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.IM_MARK, getToken(req), ImMarkResponse.class);
    }

    @Override
    public ImMarkResponse imMark(RequestConfigurator<ImMarkRequest.ImMarkRequestBuilder> req) throws IOException, SlackApiException {
        return imMark(req.configure(ImMarkRequest.builder()).build());
    }

    @Override
    public ImOpenResponse imOpen(ImOpenRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.IM_OPEN, getToken(req), ImOpenResponse.class);
    }

    @Override
    public ImOpenResponse imOpen(RequestConfigurator<ImOpenRequest.ImOpenRequestBuilder> req) throws IOException, SlackApiException {
        return imOpen(req.configure(ImOpenRequest.builder()).build());
    }

    @Override
    public ImRepliesResponse imReplies(ImRepliesRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.IM_REPLIES, getToken(req), ImRepliesResponse.class);
    }

    @Override
    public ImRepliesResponse imReplies(RequestConfigurator<ImRepliesRequest.ImRepliesRequestBuilder> req) throws IOException, SlackApiException {
        return imReplies(req.configure(ImRepliesRequest.builder()).build());
    }

    @Override
    public MigrationExchangeResponse migrationExchange(MigrationExchangeRequest req) throws IOException, SlackApiException {
        this.teamId.ifPresent(currentTeamId -> {
            if (req.getTeamId() == null) req.setTeamId(currentTeamId);
        });
        return postFormWithTokenAndParseResponse(toForm(req), Methods.MIGRATION_EXCHANGE, getToken(req), MigrationExchangeResponse.class);
    }

    @Override
    public MigrationExchangeResponse migrationExchange(RequestConfigurator<MigrationExchangeRequest.MigrationExchangeRequestBuilder> req) throws IOException, SlackApiException {
        return migrationExchange(req.configure(MigrationExchangeRequest.builder()).build());
    }

    @Override
    public MpimCloseResponse mpimClose(MpimCloseRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.MPIM_CLOSE, getToken(req), MpimCloseResponse.class);
    }

    @Override
    public MpimCloseResponse mpimClose(RequestConfigurator<MpimCloseRequest.MpimCloseRequestBuilder> req) throws IOException, SlackApiException {
        return mpimClose(req.configure(MpimCloseRequest.builder()).build());
    }

    @Override
    public MpimHistoryResponse mpimHistory(MpimHistoryRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.MPIM_HISTORY, getToken(req), MpimHistoryResponse.class);
    }

    @Override
    public MpimHistoryResponse mpimHistory(RequestConfigurator<MpimHistoryRequest.MpimHistoryRequestBuilder> req) throws IOException, SlackApiException {
        return mpimHistory(req.configure(MpimHistoryRequest.builder()).build());
    }

    @Override
    public MpimListResponse mpimList(MpimListRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.MPIM_LIST, getToken(req), MpimListResponse.class);
    }

    @Override
    public MpimListResponse mpimList(RequestConfigurator<MpimListRequest.MpimListRequestBuilder> req) throws IOException, SlackApiException {
        return mpimList(req.configure(MpimListRequest.builder()).build());
    }

    @Override
    public MpimRepliesResponse mpimReplies(MpimRepliesRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.MPIM_REPLIES, getToken(req), MpimRepliesResponse.class);
    }

    @Override
    public MpimRepliesResponse mpimReplies(RequestConfigurator<MpimRepliesRequest.MpimRepliesRequestBuilder> req) throws IOException, SlackApiException {
        return mpimReplies(req.configure(MpimRepliesRequest.builder()).build());
    }

    @Override
    public MpimMarkResponse mpimMark(MpimMarkRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.MPIM_MARK, getToken(req), MpimMarkResponse.class);
    }

    @Override
    public MpimMarkResponse mpimMark(RequestConfigurator<MpimMarkRequest.MpimMarkRequestBuilder> req) throws IOException, SlackApiException {
        return mpimMark(req.configure(MpimMarkRequest.builder()).build());
    }

    @Override
    public MpimOpenResponse mpimOpen(MpimOpenRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.MPIM_OPEN, getToken(req), MpimOpenResponse.class);
    }

    @Override
    public MpimOpenResponse mpimOpen(RequestConfigurator<MpimOpenRequest.MpimOpenRequestBuilder> req) throws IOException, SlackApiException {
        return mpimOpen(req.configure(MpimOpenRequest.builder()).build());
    }

    @Override
    public OAuthAccessResponse oauthAccess(OAuthAccessRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        form.add("code", req.getCode());
        if (req.getRedirectUri() != null) {
            form.add("redirect_uri", req.getRedirectUri());
        }
        form.add("single_channel", req.isSingleChannel() ? "1" : "0");
        String authorizationHeader = Credentials.basic(req.getClientId(), req.getClientSecret());
        return postFormWithAuthorizationHeaderAndParseResponse(form, endpointUrlPrefix + Methods.OAUTH_ACCESS, authorizationHeader, OAuthAccessResponse.class);
    }

    @Override
    public OAuthAccessResponse oauthAccess(RequestConfigurator<OAuthAccessRequest.OAuthAccessRequestBuilder> req) throws IOException, SlackApiException {
        return oauthAccess(req.configure(OAuthAccessRequest.builder()).build());
    }

    @Override
    public OAuthV2AccessResponse oauthV2Access(OAuthV2AccessRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        if (req.getCode() != null) {
            form.add("code", req.getCode());
        }
        if (req.getRedirectUri() != null) {
            form.add("redirect_uri", req.getRedirectUri());
        }
        if (req.getGrantType() != null) {
            form.add("grant_type", req.getGrantType());
        }
        if (req.getRefreshToken() != null) {
            form.add("refresh_token", req.getRefreshToken());
        }
        String authorizationHeader = Credentials.basic(req.getClientId(), req.getClientSecret());
        return postFormWithAuthorizationHeaderAndParseResponse(form, endpointUrlPrefix + Methods.OAUTH_V2_ACCESS, authorizationHeader, OAuthV2AccessResponse.class);
    }

    @Override
    public OAuthV2AccessResponse oauthV2Access(RequestConfigurator<OAuthV2AccessRequest.OAuthV2AccessRequestBuilder> req) throws IOException, SlackApiException {
        return oauthV2Access(req.configure(OAuthV2AccessRequest.builder()).build());
    }

    @Override
    public OAuthV2ExchangeResponse oauthV2Exchange(OAuthV2ExchangeRequest req) throws IOException, SlackApiException {
        return postFormAndParseResponse(toForm(req), Methods.OAUTH_V2_EXCHANGE, OAuthV2ExchangeResponse.class);
    }

    @Override
    public OAuthV2ExchangeResponse oauthV2Exchange(RequestConfigurator<OAuthV2ExchangeRequest.OAuthV2ExchangeRequestBuilder> req) throws IOException, SlackApiException {
        return oauthV2Exchange(req.configure(OAuthV2ExchangeRequest.builder()).build());
    }

    @Override
    public OAuthTokenResponse oauthToken(OAuthTokenRequest req) throws IOException, SlackApiException {
        return postFormAndParseResponse(toForm(req), Methods.OAUTH_TOKEN, OAuthTokenResponse.class);
    }

    @Override
    public OAuthTokenResponse oauthToken(RequestConfigurator<OAuthTokenRequest.OAuthTokenRequestBuilder> req) throws IOException, SlackApiException {
        return oauthToken(req.configure(OAuthTokenRequest.builder()).build());
    }

    @Override
    public OpenIDConnectTokenResponse openIDConnectToken(OpenIDConnectTokenRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        if (req.getCode() != null) {
            form.add("code", req.getCode());
        }
        if (req.getRedirectUri() != null) {
            form.add("redirect_uri", req.getRedirectUri());
        }
        if (req.getGrantType() != null) {
            form.add("grant_type", req.getGrantType());
        }
        if (req.getRefreshToken() != null) {
            form.add("refresh_token", req.getRefreshToken());
        }
        String authorizationHeader = Credentials.basic(req.getClientId(), req.getClientSecret());
        return postFormWithAuthorizationHeaderAndParseResponse(form, endpointUrlPrefix + Methods.OPENID_CONNECT_TOKEN, authorizationHeader, OpenIDConnectTokenResponse.class);
    }

    @Override
    public OpenIDConnectTokenResponse openIDConnectToken(RequestConfigurator<OpenIDConnectTokenRequest.OpenIDConnectTokenRequestBuilder> req) throws IOException, SlackApiException {
        return openIDConnectToken(req.configure(OpenIDConnectTokenRequest.builder()).build());
    }

    @Override
    public OpenIDConnectUserInfoResponse openIDConnectUserInfo(OpenIDConnectUserInfoRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.OPENID_CONNECT_USER_INFO, getToken(req), OpenIDConnectUserInfoResponse.class);
    }

    @Override
    public OpenIDConnectUserInfoResponse openIDConnectUserInfo(RequestConfigurator<OpenIDConnectUserInfoRequest.OpenIDConnectUserInfoRequestBuilder> req) throws IOException, SlackApiException {
        return openIDConnectUserInfo(req.configure(OpenIDConnectUserInfoRequest.builder()).build());
    }

    @Override
    public PinsAddResponse pinsAdd(PinsAddRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.PINS_ADD, getToken(req), PinsAddResponse.class);
    }

    @Override
    public PinsAddResponse pinsAdd(RequestConfigurator<PinsAddRequest.PinsAddRequestBuilder> req) throws IOException, SlackApiException {
        return pinsAdd(req.configure(PinsAddRequest.builder()).build());
    }

    @Override
    public PinsListResponse pinsList(PinsListRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.PINS_LIST, getToken(req), PinsListResponse.class);
    }

    @Override
    public PinsListResponse pinsList(RequestConfigurator<PinsListRequest.PinsListRequestBuilder> req) throws IOException, SlackApiException {
        return pinsList(req.configure(PinsListRequest.builder()).build());
    }

    @Override
    public PinsRemoveResponse pinsRemove(PinsRemoveRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.PINS_REMOVE, getToken(req), PinsRemoveResponse.class);
    }

    @Override
    public PinsRemoveResponse pinsRemove(RequestConfigurator<PinsRemoveRequest.PinsRemoveRequestBuilder> req) throws IOException, SlackApiException {
        return pinsRemove(req.configure(PinsRemoveRequest.builder()).build());
    }

    @Override
    public ReactionsAddResponse reactionsAdd(ReactionsAddRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.REACTIONS_ADD, getToken(req), ReactionsAddResponse.class);
    }

    @Override
    public ReactionsAddResponse reactionsAdd(RequestConfigurator<ReactionsAddRequest.ReactionsAddRequestBuilder> req) throws IOException, SlackApiException {
        return reactionsAdd(req.configure(ReactionsAddRequest.builder()).build());
    }

    @Override
    public ReactionsGetResponse reactionsGet(ReactionsGetRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.REACTIONS_GET, getToken(req), ReactionsGetResponse.class);
    }

    @Override
    public ReactionsGetResponse reactionsGet(RequestConfigurator<ReactionsGetRequest.ReactionsGetRequestBuilder> req) throws IOException, SlackApiException {
        return reactionsGet(req.configure(ReactionsGetRequest.builder()).build());
    }

    @Override
    public ReactionsListResponse reactionsList(ReactionsListRequest req) throws IOException, SlackApiException {
        this.teamId.ifPresent(currentTeamId -> {
            if (req.getTeamId() == null) req.setTeamId(currentTeamId);
        });
        return postFormWithTokenAndParseResponse(toForm(req), Methods.REACTIONS_LIST, getToken(req), ReactionsListResponse.class);
    }

    @Override
    public ReactionsListResponse reactionsList(RequestConfigurator<ReactionsListRequest.ReactionsListRequestBuilder> req) throws IOException, SlackApiException {
        return reactionsList(req.configure(ReactionsListRequest.builder()).build());
    }

    @Override
    public ReactionsRemoveResponse reactionsRemove(ReactionsRemoveRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.REACTIONS_REMOVE, getToken(req), ReactionsRemoveResponse.class);
    }

    @Override
    public ReactionsRemoveResponse reactionsRemove(RequestConfigurator<ReactionsRemoveRequest.ReactionsRemoveRequestBuilder> req) throws IOException, SlackApiException {
        return reactionsRemove(req.configure(ReactionsRemoveRequest.builder()).build());
    }

    @Override
    public RemindersAddResponse remindersAdd(RemindersAddRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.REMINDERS_ADD, getToken(req), RemindersAddResponse.class);
    }

    @Override
    public RemindersAddResponse remindersAdd(RequestConfigurator<RemindersAddRequest.RemindersAddRequestBuilder> req) throws IOException, SlackApiException {
        return remindersAdd(req.configure(RemindersAddRequest.builder()).build());
    }

    @Override
    public RemindersCompleteResponse remindersComplete(RemindersCompleteRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.REMINDERS_COMPLETE, getToken(req), RemindersCompleteResponse.class);
    }

    @Override
    public RemindersCompleteResponse remindersComplete(RequestConfigurator<RemindersCompleteRequest.RemindersCompleteRequestBuilder> req) throws IOException, SlackApiException {
        return remindersComplete(req.configure(RemindersCompleteRequest.builder()).build());
    }

    @Override
    public RemindersDeleteResponse remindersDelete(RemindersDeleteRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.REMINDERS_DELETE, getToken(req), RemindersDeleteResponse.class);
    }

    @Override
    public RemindersDeleteResponse remindersDelete(RequestConfigurator<RemindersDeleteRequest.RemindersDeleteRequestBuilder> req) throws IOException, SlackApiException {
        return remindersDelete(req.configure(RemindersDeleteRequest.builder()).build());
    }

    @Override
    public RemindersInfoResponse remindersInfo(RemindersInfoRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.REMINDERS_INFO, getToken(req), RemindersInfoResponse.class);
    }

    @Override
    public RemindersInfoResponse remindersInfo(RequestConfigurator<RemindersInfoRequest.RemindersInfoRequestBuilder> req) throws IOException, SlackApiException {
        return remindersInfo(req.configure(RemindersInfoRequest.builder()).build());
    }

    @Override
    public RemindersListResponse remindersList(RemindersListRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.REMINDERS_LIST, getToken(req), RemindersListResponse.class);
    }

    @Override
    public RemindersListResponse remindersList(RequestConfigurator<RemindersListRequest.RemindersListRequestBuilder> req) throws IOException, SlackApiException {
        return remindersList(req.configure(RemindersListRequest.builder()).build());
    }

    @Override
    @Deprecated
    public RTMConnectResponse rtmConnect(RTMConnectRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.RTM_CONNECT, getToken(req), RTMConnectResponse.class);
    }

    @Override
    @Deprecated
    public RTMConnectResponse rtmConnect(RequestConfigurator<RTMConnectRequest.RTMConnectRequestBuilder> req) throws IOException, SlackApiException {
        return rtmConnect(req.configure(RTMConnectRequest.builder()).build());
    }

    @Override
    @Deprecated
    public RTMStartResponse rtmStart(RTMStartRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.RTM_START, getToken(req), RTMStartResponse.class);
    }

    @Override
    @Deprecated
    public RTMStartResponse rtmStart(RequestConfigurator<RTMStartRequest.RTMStartRequestBuilder> req) throws IOException, SlackApiException {
        return rtmStart(req.configure(RTMStartRequest.builder()).build());
    }

    @Override
    public SearchAllResponse searchAll(SearchAllRequest req) throws IOException, SlackApiException {
        this.teamId.ifPresent(currentTeamId -> {
            if (req.getTeamId() == null) req.setTeamId(currentTeamId);
        });
        return postFormWithTokenAndParseResponse(toForm(req), Methods.SEARCH_ALL, getToken(req), SearchAllResponse.class);
    }

    @Override
    public SearchAllResponse searchAll(RequestConfigurator<SearchAllRequest.SearchAllRequestBuilder> req) throws IOException, SlackApiException {
        return searchAll(req.configure(SearchAllRequest.builder()).build());
    }

    @Override
    public SearchMessagesResponse searchMessages(SearchMessagesRequest req) throws IOException, SlackApiException {
        this.teamId.ifPresent(currentTeamId -> {
            if (req.getTeamId() == null) req.setTeamId(currentTeamId);
        });
        return postFormWithTokenAndParseResponse(toForm(req), Methods.SEARCH_MESSAGES, getToken(req), SearchMessagesResponse.class);
    }

    @Override
    public SearchMessagesResponse searchMessages(RequestConfigurator<SearchMessagesRequest.SearchMessagesRequestBuilder> req) throws IOException, SlackApiException {
        return searchMessages(req.configure(SearchMessagesRequest.builder()).build());
    }

    @Override
    public SearchFilesResponse searchFiles(SearchFilesRequest req) throws IOException, SlackApiException {
        this.teamId.ifPresent(currentTeamId -> {
            if (req.getTeamId() == null) req.setTeamId(currentTeamId);
        });
        return postFormWithTokenAndParseResponse(toForm(req), Methods.SEARCH_FILES, getToken(req), SearchFilesResponse.class);
    }

    @Override
    public SearchFilesResponse searchFiles(RequestConfigurator<SearchFilesRequest.SearchFilesRequestBuilder> req) throws IOException, SlackApiException {
        return searchFiles(req.configure(SearchFilesRequest.builder()).build());
    }

    @Override
    @Deprecated // https://docs.slack.dev/changelog/2023-07-its-later-already-for-stars-and-reminders
    public StarsAddResponse starsAdd(StarsAddRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.STARS_ADD, getToken(req), StarsAddResponse.class);
    }

    @Override
    @Deprecated // https://docs.slack.dev/changelog/2023-07-its-later-already-for-stars-and-reminders
    public StarsAddResponse starsAdd(RequestConfigurator<StarsAddRequest.StarsAddRequestBuilder> req) throws IOException, SlackApiException {
        return starsAdd(req.configure(StarsAddRequest.builder()).build());
    }

    @Override
    @Deprecated // https://docs.slack.dev/changelog/2023-07-its-later-already-for-stars-and-reminders
    public StarsListResponse starsList(StarsListRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.STARS_LIST, getToken(req), StarsListResponse.class);
    }

    @Override
    @Deprecated // https://docs.slack.dev/changelog/2023-07-its-later-already-for-stars-and-reminders
    public StarsListResponse starsList(RequestConfigurator<StarsListRequest.StarsListRequestBuilder> req) throws IOException, SlackApiException {
        return starsList(req.configure(StarsListRequest.builder()).build());
    }

    @Override
    @Deprecated // https://docs.slack.dev/changelog/2023-07-its-later-already-for-stars-and-reminders
    public StarsRemoveResponse starsRemove(StarsRemoveRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.STARS_REMOVE, getToken(req), StarsRemoveResponse.class);
    }

    @Override
    @Deprecated // https://docs.slack.dev/changelog/2023-07-its-later-already-for-stars-and-reminders
    public StarsRemoveResponse starsRemove(RequestConfigurator<StarsRemoveRequest.StarsRemoveRequestBuilder> req) throws IOException, SlackApiException {
        return starsRemove(req.configure(StarsRemoveRequest.builder()).build());
    }

    @Override
    public SlackListsCreateResponse slackListsCreate(SlackListsCreateRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.SLACKLISTS_CREATE, getToken(req), SlackListsCreateResponse.class);
    }

    @Override
    public SlackListsCreateResponse slackListsCreate(RequestConfigurator<SlackListsCreateRequest.SlackListsCreateRequestBuilder> req) throws IOException, SlackApiException {
        return slackListsCreate(req.configure(SlackListsCreateRequest.builder()).build());
    }

    @Override
    public TeamAccessLogsResponse teamAccessLogs(TeamAccessLogsRequest req) throws IOException, SlackApiException {
        this.teamId.ifPresent(currentTeamId -> {
            if (req.getTeamId() == null) req.setTeamId(currentTeamId);
        });
        return postFormWithTokenAndParseResponse(toForm(req), Methods.TEAM_ACCESS_LOGS, getToken(req), TeamAccessLogsResponse.class);
    }

    @Override
    public TeamAccessLogsResponse teamAccessLogs(RequestConfigurator<TeamAccessLogsRequest.TeamAccessLogsRequestBuilder> req) throws IOException, SlackApiException {
        return teamAccessLogs(req.configure(TeamAccessLogsRequest.builder()).build());
    }

    @Override
    public TeamBillableInfoResponse teamBillableInfo(TeamBillableInfoRequest req) throws IOException, SlackApiException {
        this.teamId.ifPresent(currentTeamId -> {
            if (req.getTeamId() == null) req.setTeamId(currentTeamId);
        });
        return postFormWithTokenAndParseResponse(toForm(req), Methods.TEAM_BILLABLE_INFO, getToken(req), TeamBillableInfoResponse.class);
    }

    @Override
    public TeamBillableInfoResponse teamBillableInfo(RequestConfigurator<TeamBillableInfoRequest.TeamBillableInfoRequestBuilder> req) throws IOException, SlackApiException {
        return teamBillableInfo(req.configure(TeamBillableInfoRequest.builder()).build());
    }

    @Override
    public TeamInfoResponse teamInfo(TeamInfoRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.TEAM_INFO, getToken(req), TeamInfoResponse.class);
    }

    @Override
    public TeamInfoResponse teamInfo(RequestConfigurator<TeamInfoRequest.TeamInfoRequestBuilder> req) throws IOException, SlackApiException {
        return teamInfo(req.configure(TeamInfoRequest.builder()).build());
    }

    @Override
    public TeamIntegrationLogsResponse teamIntegrationLogs(TeamIntegrationLogsRequest req) throws IOException, SlackApiException {
        this.teamId.ifPresent(currentTeamId -> {
            if (req.getTeamId() == null) req.setTeamId(currentTeamId);
        });
        return postFormWithTokenAndParseResponse(toForm(req), Methods.TEAM_INTEGRATION_LOGS, getToken(req), TeamIntegrationLogsResponse.class);
    }

    @Override
    public TeamIntegrationLogsResponse teamIntegrationLogs(RequestConfigurator<TeamIntegrationLogsRequest.TeamIntegrationLogsRequestBuilder> req) throws IOException, SlackApiException {
        return teamIntegrationLogs(req.configure(TeamIntegrationLogsRequest.builder()).build());
    }

    @Override
    public TeamProfileGetResponse teamProfileGet(TeamProfileGetRequest req) throws IOException, SlackApiException {
        this.teamId.ifPresent(currentTeamId -> {
            if (req.getTeamId() == null) req.setTeamId(currentTeamId);
        });
        return postFormWithTokenAndParseResponse(toForm(req), Methods.TEAM_PROFILE_GET, getToken(req), TeamProfileGetResponse.class);
    }

    @Override
    public TeamProfileGetResponse teamProfileGet(RequestConfigurator<TeamProfileGetRequest.TeamProfileGetRequestBuilder> req) throws IOException, SlackApiException {
        return teamProfileGet(req.configure(TeamProfileGetRequest.builder()).build());
    }

    @Override
    public TeamBillingInfoResponse teamBillingInfo(TeamBillingInfoRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.TEAM_BILLING_INFO, getToken(req), TeamBillingInfoResponse.class);
    }

    @Override
    public TeamBillingInfoResponse teamBillingInfo(RequestConfigurator<TeamBillingInfoRequest.TeamBillingInfoRequestBuilder> req) throws IOException, SlackApiException {
        return teamBillingInfo(req.configure(TeamBillingInfoRequest.builder()).build());
    }

    @Override
    public TeamPreferencesListResponse teamPreferencesList(TeamPreferencesListRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.TEAM_PREFERENCES_LIST, getToken(req), TeamPreferencesListResponse.class);
    }

    @Override
    public TeamPreferencesListResponse teamPreferencesList(RequestConfigurator<TeamPreferencesListRequest.TeamPreferencesListRequestBuilder> req) throws IOException, SlackApiException {
        return teamPreferencesList(req.configure(TeamPreferencesListRequest.builder()).build());
    }

    @Override
    public TeamExternalTeamsListResponse teamExternalTeamsList(TeamExternalTeamsListRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.TEAM_EXTERNAL_TEAMS_LIST, getToken(req), TeamExternalTeamsListResponse.class);
    }

    @Override
    public TeamExternalTeamsListResponse teamExternalTeamsList(RequestConfigurator<TeamExternalTeamsListRequest.TeamExternalTeamsListRequestBuilder> req) throws IOException, SlackApiException {
        return teamExternalTeamsList(req.configure(TeamExternalTeamsListRequest.builder()).build());
    }

    @Override
    public TeamExternalTeamsDisconnectResponse teamExternalTeamsDisconnect(TeamExternalTeamsDisconnectRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.TEAM_EXTERNAL_TEAMS_DISCONNECT, getToken(req), TeamExternalTeamsDisconnectResponse.class);
    }

    @Override
    public TeamExternalTeamsDisconnectResponse teamExternalTeamsDisconnect(RequestConfigurator<TeamExternalTeamsDisconnectRequest.TeamExternalTeamsDisconnectRequestBuilder> req) throws IOException, SlackApiException {
        return teamExternalTeamsDisconnect(req.configure(TeamExternalTeamsDisconnectRequest.builder()).build());
    }

    @Override
    public ToolingTokensRotateResponse toolingTokensRotate(ToolingTokensRotateRequest req) throws IOException, SlackApiException {
        return postFormAndParseResponse(toForm(req), Methods.TOOLING_TOKENS_ROTATE, ToolingTokensRotateResponse.class);
    }

    @Override
    public ToolingTokensRotateResponse toolingTokensRotate(RequestConfigurator<ToolingTokensRotateRequest.ToolingTokensRotateRequestBuilder> req) throws IOException, SlackApiException {
        return toolingTokensRotate(req.configure(ToolingTokensRotateRequest.builder()).build());
    }

    @Override
    public UsergroupsCreateResponse usergroupsCreate(UsergroupsCreateRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.USERGROUPS_CREATE, getToken(req), UsergroupsCreateResponse.class);
    }

    @Override
    public UsergroupsCreateResponse usergroupsCreate(RequestConfigurator<UsergroupsCreateRequest.UsergroupsCreateRequestBuilder> req) throws IOException, SlackApiException {
        return usergroupsCreate(req.configure(UsergroupsCreateRequest.builder()).build());
    }

    @Override
    public UsergroupsDisableResponse usergroupsDisable(UsergroupsDisableRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.USERGROUPS_DISABLE, getToken(req), UsergroupsDisableResponse.class);
    }

    @Override
    public UsergroupsDisableResponse usergroupsDisable(RequestConfigurator<UsergroupsDisableRequest.UsergroupsDisableRequestBuilder> req) throws IOException, SlackApiException {
        return usergroupsDisable(req.configure(UsergroupsDisableRequest.builder()).build());
    }

    @Override
    public UsergroupsEnableResponse usergroupsEnable(UsergroupsEnableRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.USERGROUPS_ENABLE, getToken(req), UsergroupsEnableResponse.class);
    }

    @Override
    public UsergroupsEnableResponse usergroupsEnable(RequestConfigurator<UsergroupsEnableRequest.UsergroupsEnableRequestBuilder> req) throws IOException, SlackApiException {
        return usergroupsEnable(req.configure(UsergroupsEnableRequest.builder()).build());
    }

    @Override
    public UsergroupsListResponse usergroupsList(UsergroupsListRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.USERGROUPS_LIST, getToken(req), UsergroupsListResponse.class);
    }

    @Override
    public UsergroupsListResponse usergroupsList(RequestConfigurator<UsergroupsListRequest.UsergroupsListRequestBuilder> req) throws IOException, SlackApiException {
        return usergroupsList(req.configure(UsergroupsListRequest.builder()).build());
    }

    @Override
    public UsergroupsUpdateResponse usergroupsUpdate(UsergroupsUpdateRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.USERGROUPS_UPDATE, getToken(req), UsergroupsUpdateResponse.class);
    }

    @Override
    public UsergroupsUpdateResponse usergroupsUpdate(RequestConfigurator<UsergroupsUpdateRequest.UsergroupsUpdateRequestBuilder> req) throws IOException, SlackApiException {
        return usergroupsUpdate(req.configure(UsergroupsUpdateRequest.builder()).build());
    }

    @Override
    public UsergroupsUsersListResponse usergroupsUsersList(UsergroupsUsersListRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.USERGROUPS_USERS_LIST, getToken(req), UsergroupsUsersListResponse.class);
    }

    @Override
    public UsergroupsUsersListResponse usergroupsUsersList(RequestConfigurator<UsergroupsUsersListRequest.UsergroupsUsersListRequestBuilder> req) throws IOException, SlackApiException {
        return usergroupsUsersList(req.configure(UsergroupsUsersListRequest.builder()).build());
    }

    @Override
    public UsergroupsUsersUpdateResponse usergroupsUsersUpdate(UsergroupsUsersUpdateRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.USERGROUPS_USERS_UPDATE, getToken(req), UsergroupsUsersUpdateResponse.class);
    }

    @Override
    public UsergroupsUsersUpdateResponse usergroupsUsersUpdate(RequestConfigurator<UsergroupsUsersUpdateRequest.UsergroupsUsersUpdateRequestBuilder> req) throws IOException, SlackApiException {
        return usergroupsUsersUpdate(req.configure(UsergroupsUsersUpdateRequest.builder()).build());
    }

    @Override
    public UsersConversationsResponse usersConversations(UsersConversationsRequest req) throws IOException, SlackApiException {
        this.teamId.ifPresent(currentTeamId -> {
            if (req.getTeamId() == null) req.setTeamId(currentTeamId);
        });
        return postFormWithTokenAndParseResponse(toForm(req), Methods.USERS_CONVERSATIONS, getToken(req), UsersConversationsResponse.class);
    }

    @Override
    public UsersConversationsResponse usersConversations(RequestConfigurator<UsersConversationsRequest.UsersConversationsRequestBuilder> req) throws IOException, SlackApiException {
        return usersConversations(req.configure(UsersConversationsRequest.builder()).build());
    }

    @Override
    public UsersDeletePhotoResponse usersDeletePhoto(UsersDeletePhotoRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.USERS_DELETE_PHOTO, getToken(req), UsersDeletePhotoResponse.class);
    }

    @Override
    public UsersDeletePhotoResponse usersDeletePhoto(RequestConfigurator<UsersDeletePhotoRequest.UsersDeletePhotoRequestBuilder> req) throws IOException, SlackApiException {
        return usersDeletePhoto(req.configure(UsersDeletePhotoRequest.builder()).build());
    }

    @Override
    public UsersGetPresenceResponse usersGetPresence(UsersGetPresenceRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.USERS_GET_PRESENCE, getToken(req), UsersGetPresenceResponse.class);
    }

    @Override
    public UsersGetPresenceResponse usersGetPresence(RequestConfigurator<UsersGetPresenceRequest.UsersGetPresenceRequestBuilder> req) throws IOException, SlackApiException {
        return usersGetPresence(req.configure(UsersGetPresenceRequest.builder()).build());
    }

    @Override
    public UsersIdentityResponse usersIdentity(UsersIdentityRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.USERS_IDENTITY, getToken(req), UsersIdentityResponse.class);
    }

    @Override
    public UsersIdentityResponse usersIdentity(RequestConfigurator<UsersIdentityRequest.UsersIdentityRequestBuilder> req) throws IOException, SlackApiException {
        return usersIdentity(req.configure(UsersIdentityRequest.builder()).build());
    }

    @Override
    public UsersInfoResponse usersInfo(UsersInfoRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.USERS_INFO, getToken(req), UsersInfoResponse.class);
    }

    @Override
    public UsersInfoResponse usersInfo(RequestConfigurator<UsersInfoRequest.UsersInfoRequestBuilder> req) throws IOException, SlackApiException {
        return usersInfo(req.configure(UsersInfoRequest.builder()).build());
    }

    @Override
    public UsersListResponse usersList(UsersListRequest req) throws IOException, SlackApiException {
        this.teamId.ifPresent(currentTeamId -> {
            if (req.getTeamId() == null) req.setTeamId(currentTeamId);
        });
        return postFormWithTokenAndParseResponse(toForm(req), Methods.USERS_LIST, getToken(req), UsersListResponse.class);
    }

    @Override
    public UsersListResponse usersList(RequestConfigurator<UsersListRequest.UsersListRequestBuilder> req) throws IOException, SlackApiException {
        return usersList(req.configure(UsersListRequest.builder()).build());
    }

    @Override
    public UsersLookupByEmailResponse usersLookupByEmail(UsersLookupByEmailRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.USERS_LOOKUP_BY_EMAIL, getToken(req), UsersLookupByEmailResponse.class);
    }

    @Override
    public UsersLookupByEmailResponse usersLookupByEmail(RequestConfigurator<UsersLookupByEmailRequest.UsersLookupByEmailRequestBuilder> req) throws IOException, SlackApiException {
        return usersLookupByEmail(req.configure(UsersLookupByEmailRequest.builder()).build());
    }

    @Override
    public UsersSetActiveResponse usersSetActive(UsersSetActiveRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.USERS_SET_ACTIVE, getToken(req), UsersSetActiveResponse.class);
    }

    @Override
    public UsersSetActiveResponse usersSetActive(RequestConfigurator<UsersSetActiveRequest.UsersSetActiveRequestBuilder> req) throws IOException, SlackApiException {
        return usersSetActive(req.configure(UsersSetActiveRequest.builder()).build());
    }

    @Override
    public UsersSetPhotoResponse usersSetPhoto(UsersSetPhotoRequest req) throws IOException, SlackApiException {
        return postMultipartAndParseResponse(toMultipartBody(req), Methods.USERS_SET_PHOTO, getToken(req), UsersSetPhotoResponse.class);
    }

    @Override
    public UsersSetPhotoResponse usersSetPhoto(RequestConfigurator<UsersSetPhotoRequest.UsersSetPhotoRequestBuilder> req) throws IOException, SlackApiException {
        return usersSetPhoto(req.configure(UsersSetPhotoRequest.builder()).build());
    }

    @Override
    public UsersSetPresenceResponse usersSetPresence(UsersSetPresenceRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.USERS_SET_PRESENCE, getToken(req), UsersSetPresenceResponse.class);
    }

    @Override
    public UsersSetPresenceResponse usersSetPresence(RequestConfigurator<UsersSetPresenceRequest.UsersSetPresenceRequestBuilder> req) throws IOException, SlackApiException {
        return usersSetPresence(req.configure(UsersSetPresenceRequest.builder()).build());
    }

    @Override
    public UsersDiscoverableContactsLookupResponse usersDiscoverableContactsLookup(UsersDiscoverableContactsLookupRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.USERS_DISCOVERABLE_CONTACTS_LOOKUP, getToken(req), UsersDiscoverableContactsLookupResponse.class);
    }

    @Override
    public UsersDiscoverableContactsLookupResponse usersDiscoverableContactsLookup(RequestConfigurator<UsersDiscoverableContactsLookupRequest.UsersDiscoverableContactsLookupRequestBuilder> req) throws IOException, SlackApiException {
        return usersDiscoverableContactsLookup(req.configure(UsersDiscoverableContactsLookupRequest.builder()).build());
    }

    @Override
    public UsersProfileGetResponse usersProfileGet(UsersProfileGetRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.USERS_PROFILE_GET, getToken(req), UsersProfileGetResponse.class);
    }

    @Override
    public UsersProfileGetResponse usersProfileGet(RequestConfigurator<UsersProfileGetRequest.UsersProfileGetRequestBuilder> req) throws IOException, SlackApiException {
        return usersProfileGet(req.configure(UsersProfileGetRequest.builder()).build());
    }

    @Override
    public UsersProfileSetResponse usersProfileSet(UsersProfileSetRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.USERS_PROFILE_SET, getToken(req), UsersProfileSetResponse.class);
    }

    @Override
    public UsersProfileSetResponse usersProfileSet(RequestConfigurator<UsersProfileSetRequest.UsersProfileSetRequestBuilder> req) throws IOException, SlackApiException {
        return usersProfileSet(req.configure(UsersProfileSetRequest.builder()).build());
    }

    @Override
    public ViewsOpenResponse viewsOpen(ViewsOpenRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.VIEWS_OPEN, getToken(req), ViewsOpenResponse.class);
    }

    @Override
    public ViewsOpenResponse viewsOpen(RequestConfigurator<ViewsOpenRequest.ViewsOpenRequestBuilder> req) throws IOException, SlackApiException {
        return viewsOpen(req.configure(ViewsOpenRequest.builder()).build());
    }

    @Override
    public ViewsPushResponse viewsPush(ViewsPushRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.VIEWS_PUSH, getToken(req), ViewsPushResponse.class);
    }

    @Override
    public ViewsPushResponse viewsPush(RequestConfigurator<ViewsPushRequest.ViewsPushRequestBuilder> req) throws IOException, SlackApiException {
        return viewsPush(req.configure(ViewsPushRequest.builder()).build());
    }

    @Override
    public ViewsUpdateResponse viewsUpdate(ViewsUpdateRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.VIEWS_UPDATE, getToken(req), ViewsUpdateResponse.class);
    }

    @Override
    public ViewsUpdateResponse viewsUpdate(RequestConfigurator<ViewsUpdateRequest.ViewsUpdateRequestBuilder> req) throws IOException, SlackApiException {
        return viewsUpdate(req.configure(ViewsUpdateRequest.builder()).build());
    }

    @Override
    public ViewsPublishResponse viewsPublish(ViewsPublishRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.VIEWS_PUBLISH, getToken(req), ViewsPublishResponse.class);
    }

    @Override
    public ViewsPublishResponse viewsPublish(RequestConfigurator<ViewsPublishRequest.ViewsPublishRequestBuilder> req) throws IOException, SlackApiException {
        return viewsPublish(req.configure(ViewsPublishRequest.builder()).build());
    }

    @Override
    public WorkflowsStepCompletedResponse workflowsStepCompleted(WorkflowsStepCompletedRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.WORKFLOWS_STEP_COMPLETED, getToken(req), WorkflowsStepCompletedResponse.class);
    }

    @Override
    public WorkflowsStepCompletedResponse workflowsStepCompleted(RequestConfigurator<WorkflowsStepCompletedRequest.WorkflowsStepCompletedRequestBuilder> req) throws IOException, SlackApiException {
        return workflowsStepCompleted(req.configure(WorkflowsStepCompletedRequest.builder()).build());
    }

    @Override
    public WorkflowsStepFailedResponse workflowsStepFailed(WorkflowsStepFailedRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.WORKFLOWS_STEP_FAILED, getToken(req), WorkflowsStepFailedResponse.class);
    }

    @Override
    public WorkflowsStepFailedResponse workflowsStepFailed(RequestConfigurator<WorkflowsStepFailedRequest.WorkflowsStepFailedRequestBuilder> req) throws IOException, SlackApiException {
        return workflowsStepFailed(req.configure(WorkflowsStepFailedRequest.builder()).build());
    }

    @Override
    public WorkflowsUpdateStepResponse workflowsUpdateStep(WorkflowsUpdateStepRequest req) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(toForm(req), Methods.WORKFLOWS_UPDATE_STEP, getToken(req), WorkflowsUpdateStepResponse.class);
    }

    @Override
    public WorkflowsUpdateStepResponse workflowsUpdateStep(RequestConfigurator<WorkflowsUpdateStepRequest.WorkflowsUpdateStepRequestBuilder> req) throws IOException, SlackApiException {
        return workflowsUpdateStep(req.configure(WorkflowsUpdateStepRequest.builder()).build());
    }

    // ----------------------------------------------
    // OkHttp layer methods
    // ----------------------------------------------

    @Override
    public Response runPostForm(FormBody.Builder form, String methodName) throws IOException {
        return slackHttpClient.postForm(endpointUrlPrefix + methodName, form.build());
    }

    @Override
    public Response runPostFormWithToken(FormBody.Builder form, String methodName, String token) throws IOException {
        return slackHttpClient.postFormWithBearerHeader(endpointUrlPrefix + methodName, token, form.build());
    }

    @Override
    public Response runPostMultipart(MultipartBody.Builder form, String methodName, String token) throws IOException {
        return slackHttpClient.postMultipart(endpointUrlPrefix + methodName, token, form.build());
    }

    // ----------------------------------------------
    //  Methods to send requests and parse responses
    // ----------------------------------------------

    @Override
    public <T extends SlackApiTextResponse> T postFormAndParseResponse(
            RequestConfigurator<FormBody.Builder> form,
            String methodName,
            Class<T> clazz) throws IOException, SlackApiException {
        return postFormAndParseResponse(
                form.configure(new FormBody.Builder()),
                methodName,
                clazz
        );
    }

    @Override
    public <T extends SlackApiTextResponse> T postFormWithAuthorizationHeaderAndParseResponse(
            RequestConfigurator<FormBody.Builder> form,
            String methodName,
            String authorizationHeader,
            Class<T> clazz) throws IOException, SlackApiException {
        return postFormWithAuthorizationHeaderAndParseResponse(
                form.configure(new FormBody.Builder()),
                methodName,
                authorizationHeader,
                clazz
        );
    }

    @Override
    public AdminAnalyticsGetFileResponse adminAnalyticsGetFile(AdminAnalyticsGetFileRequest req) throws IOException, SlackApiException {
        Response response = postFormWithToken(toForm(req), Methods.ADMIN_ANALYTICS_GET_FILE, getToken(req));
        MediaType contentType = response.body().contentType();
        if (contentType != null && contentType.subtype().equals("json")) {
            // This response has a text response with error info
            String body = response.body().string();
            if (response.isSuccessful()) {
                try {
                    Gson gson = GsonFactory.createSnakeCase(slackHttpClient.getConfig());
                    AdminAnalyticsGetFileResponse apiResponse =
                            gson.fromJson(body, AdminAnalyticsGetFileResponse.class);
                    apiResponse.setHttpResponseHeaders(toLowerCasedKeyMap(response.headers()));
                    return apiResponse;
                } finally {
                    slackHttpClient.runHttpResponseListeners(response, body);
                }
            } else {
                throw new SlackApiException(slackHttpClient.getConfig(), response, body);
            }
        } else {
            // gzip data
            runListenersForBinaryResponse(response);
            AdminAnalyticsGetFileResponse apiResponse = new AdminAnalyticsGetFileResponse();
            apiResponse.setOk(true);
            apiResponse.setFileStream(response.body().byteStream());
            apiResponse.setHttpResponseHeaders(toLowerCasedKeyMap(response.headers()));
            return apiResponse;
        }
    }

    @Override
    public AdminAnalyticsGetFileResponse adminAnalyticsGetFile(RequestConfigurator<AdminAnalyticsGetFileRequest.AdminAnalyticsGetFileRequestBuilder> req) throws IOException, SlackApiException {
        return adminAnalyticsGetFile(req.configure(AdminAnalyticsGetFileRequest.builder()).build());
    }

    @Override
    public <T extends SlackApiTextResponse> T postFormWithTokenAndParseResponse(
            RequestConfigurator<FormBody.Builder> form,
            String endpoint,
            String token,
            Class<T> clazz) throws IOException, SlackApiException {
        return postFormWithTokenAndParseResponse(
                form.configure(new FormBody.Builder()),
                endpoint,
                token,
                clazz
        );
    }

    protected <T extends SlackApiTextResponse> T postFormAndParseResponse(
            FormBody.Builder form,
            String methodName,
            Class<T> clazz) throws IOException, SlackApiException {
        Response response = runPostForm(form, methodName);
        return parseJsonResponseAndRunListeners(null, methodName, response, clazz);
    }

    protected <T extends SlackApiTextResponse> T postFormWithAuthorizationHeaderAndParseResponse(
            FormBody.Builder form,
            String methodName,
            String authorizationHeader,
            Class<T> clazz) throws IOException, SlackApiException {
        Response response = slackHttpClient.postFormWithAuthorizationHeader(methodName, authorizationHeader, form.build());
        return parseJsonResponseAndRunListeners(null, methodName, response, clazz);
    }

    protected Response postFormWithToken(
            FormBody.Builder form,
            String methodName,
            String token) throws IOException {
        String teamId = this.teamId.orElse(null);
        if (statsEnabled && teamId == null
                && !METHOD_NAMES_TO_SKIP_TEAM_ID_CACHE_RESOLUTION.contains(methodName)) {
            teamId = teamIdCache.lookupOrResolve(token);
        }
        try {
            if (teamId != null) {
                String key = buildMethodNameAndSuffix(form, methodName);
                metricsDatastore.incrementAllCompletedCalls(executorName, teamId, methodName);
                metricsDatastore.addToLastMinuteRequests(executorName, teamId, key, System.currentTimeMillis());
            }
            return runPostFormWithToken(form, methodName, token);

        } catch (IOException e) {
            if (teamId != null) {
                metricsDatastore.incrementFailedCalls(executorName, teamId, methodName);
            }
            throw e;
        }
    }

    protected <T extends SlackApiTextResponse> T postFormWithTokenAndParseResponse(
            FormBody.Builder form,
            String methodName,
            String token,
            Class<T> clazz) throws IOException, SlackApiException {
        String teamId = this.teamId.orElse(null);
        if (statsEnabled && teamId == null
                && !METHOD_NAMES_TO_SKIP_TEAM_ID_CACHE_RESOLUTION.contains(methodName)) {
            teamId = teamIdCache.lookupOrResolve(token);
        }
        try {
            if (teamId != null) {
                String key = buildMethodNameAndSuffix(form, methodName);
                metricsDatastore.incrementAllCompletedCalls(executorName, teamId, methodName);
                metricsDatastore.addToLastMinuteRequests(executorName, teamId, key, System.currentTimeMillis());
            }
            Response response = runPostFormWithToken(form, methodName, token);
            T apiResponse = parseJsonResponseAndRunListeners(teamId, methodName, response, clazz);
            return apiResponse;

        } catch (IOException e) {
            if (teamId != null) {
                metricsDatastore.incrementFailedCalls(executorName, teamId, methodName);
            }
            throw e;
        } catch (SlackApiException e) {
            if (teamId != null) {
                metricsDatastore.incrementFailedCalls(executorName, teamId, methodName);
                if (e.getResponse().code() == 429) {
                    // rate limited
                    final String retryAfterSeconds = e.getResponse().header("Retry-After");
                    if (retryAfterSeconds != null) {
                        long secondsToWait = Long.valueOf(retryAfterSeconds);
                        long epochMillisToRetry = System.currentTimeMillis() + (secondsToWait * 1000L);
                        String key = buildMethodNameAndSuffix(form, methodName);
                        metricsDatastore.setRateLimitedMethodRetryEpochMillis(executorName, teamId, key, epochMillisToRetry);
                    }
                }
            }
            throw e;
        }
    }

    protected <T extends SlackApiTextResponse> T postMultipartAndParseResponse(
            MultipartBody.Builder form,
            String methodName,
            String token,
            Class<T> clazz) throws IOException, SlackApiException {
        String teamId = this.teamId.orElse(null);
        if (statsEnabled && teamId == null
                && !METHOD_NAMES_TO_SKIP_TEAM_ID_CACHE_RESOLUTION.contains(methodName)) {
            teamId = teamIdCache.lookupOrResolve(token);
        }
        try {
            form.setType(MultipartBody.FORM);
            if (teamId != null) {
                metricsDatastore.incrementAllCompletedCalls(executorName, teamId, methodName);
                metricsDatastore.addToLastMinuteRequests(executorName, teamId, methodName, System.currentTimeMillis());
            }
            Response response = runPostMultipart(form, methodName, token);
            T apiResponse = parseJsonResponseAndRunListeners(teamId, methodName, response, clazz, true);
            return apiResponse;

        } catch (IOException e) {
            if (teamId != null) {
                metricsDatastore.incrementFailedCalls(executorName, teamId, methodName);
            }
            throw e;
        } catch (SlackApiException e) {
            if (teamId != null) {
                metricsDatastore.incrementFailedCalls(executorName, teamId, methodName);
            }
            if (e.getResponse().code() == 429) {
                // rate limited
                final String retryAfterSeconds = e.getResponse().header("Retry-After");
                if (retryAfterSeconds != null) {
                    long secondsToWait = Long.valueOf(retryAfterSeconds);
                    long epochMillisToRetry = System.currentTimeMillis() + (secondsToWait * 1000L);
                    metricsDatastore.setRateLimitedMethodRetryEpochMillis(executorName, teamId, methodName, epochMillisToRetry);
                }
            }
            throw e;
        }
    }

    // ----------------------------------------------
    // Internal methods
    // ----------------------------------------------

    protected String getToken(SlackApiRequest request) {
        if (request.getToken() != null) {
            return request.getToken();
        }
        if (token.isPresent()) {
            return token.get();
        }
        if (slackHttpClient.getConfig().isTokenExistenceVerificationEnabled()) {
            String error = "Slack API token is supposed to be set in " + request.getClass().getSimpleName() + " but not found";
            throw new IllegalStateException(error);
        } else {
            return null;
        }
    }

    <T extends SlackApiTextResponse> T parseJsonResponseAndRunListeners(
            String teamId,
            String methodName,
            Response response,
            Class<T> clazz
    ) throws IOException, SlackApiException {
        return parseJsonResponseAndRunListeners(teamId, methodName, response, clazz, false);
    }

    <T extends SlackApiTextResponse> T parseJsonResponseAndRunListeners(
            String teamId,
            String methodName,
            Response response,
            Class<T> clazz,
            boolean isRequestBodyBinary
    ) throws IOException, SlackApiException {
        String body = response.body().string();
        if (response.isSuccessful()) {
            try {
                T apiResponse = GsonFactory.createSnakeCase(slackHttpClient.getConfig()).fromJson(body, clazz);
                if (teamId != null) {
                    if (apiResponse.isOk()) {
                        metricsDatastore.incrementSuccessfulCalls(executorName, teamId, methodName);
                    } else {
                        metricsDatastore.incrementUnsuccessfulCalls(executorName, teamId, methodName);
                    }
                }
                apiResponse.setHttpResponseHeaders(toLowerCasedKeyMap(response.headers()));
                return apiResponse;
            } finally {
                slackHttpClient.runHttpResponseListeners(response, body, isRequestBodyBinary);
            }
        } else {
            throw new SlackApiException(slackHttpClient.getConfig(), response, body);
        }
    }


    void runListenersForBinaryResponse(
            Response response) throws SlackApiException {
        if (response.isSuccessful()) {
            slackHttpClient.runHttpResponseListeners(response, "(binary data)");
        } else {
            throw new SlackApiException(slackHttpClient.getConfig(), response, "(binary data)");
        }
    }

    private String buildMethodNameAndSuffix(FormBody.Builder form, String methodName) {
        String key = methodName;
        if (methodName.equals(Methods.CHAT_POST_MESSAGE)) {
            key += "_" + channel(form.build());
        }
        return key;
    }

    private static String channel(FormBody form) {
        for (int idx = 0; idx < form.size(); idx++) {
            String key = form.name(idx);
            if (key.equals("channel")) {
                return form.value(idx);
            }
        }
        return null;
    }

    private static Map<String, List<String>> toLowerCasedKeyMap(Headers headers) {
        Map<String, List<String>> converted = new HashMap<>();
        Map<String, List<String>> map = headers.toMultimap();
        for (Map.Entry<String, List<String>> each : map.entrySet()) {
            converted.put(each.getKey().toLowerCase(Locale.ENGLISH), each.getValue());
        }
        return converted;
    }

}
