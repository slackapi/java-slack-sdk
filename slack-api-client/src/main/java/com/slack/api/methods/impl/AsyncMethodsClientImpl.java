package com.slack.api.methods.impl;

import com.slack.api.RequestConfigurator;
import com.slack.api.SlackConfig;
import com.slack.api.methods.AsyncMethodsClient;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiRequest;
import com.slack.api.methods.request.admin.apps.*;
import com.slack.api.methods.request.admin.conversations.AdminConversationsSetTeamsRequest;
import com.slack.api.methods.request.admin.emoji.*;
import com.slack.api.methods.request.admin.invite_requests.*;
import com.slack.api.methods.request.admin.teams.AdminTeamsAdminsListRequest;
import com.slack.api.methods.request.admin.teams.AdminTeamsCreateRequest;
import com.slack.api.methods.request.admin.teams.AdminTeamsListRequest;
import com.slack.api.methods.request.admin.teams.owners.AdminTeamsOwnersListRequest;
import com.slack.api.methods.request.admin.teams.settings.*;
import com.slack.api.methods.request.admin.users.*;
import com.slack.api.methods.request.api.ApiTestRequest;
import com.slack.api.methods.request.apps.AppsUninstallRequest;
import com.slack.api.methods.request.auth.AuthRevokeRequest;
import com.slack.api.methods.request.auth.AuthTestRequest;
import com.slack.api.methods.request.bots.BotsInfoRequest;
import com.slack.api.methods.request.chat.*;
import com.slack.api.methods.request.chat.scheduled_messages.ChatScheduledMessagesListRequest;
import com.slack.api.methods.request.conversations.*;
import com.slack.api.methods.request.dialog.DialogOpenRequest;
import com.slack.api.methods.request.dnd.*;
import com.slack.api.methods.request.emoji.EmojiListRequest;
import com.slack.api.methods.request.files.*;
import com.slack.api.methods.request.files.remote.*;
import com.slack.api.methods.request.migration.MigrationExchangeRequest;
import com.slack.api.methods.request.oauth.OAuthAccessRequest;
import com.slack.api.methods.request.oauth.OAuthTokenRequest;
import com.slack.api.methods.request.oauth.OAuthV2AccessRequest;
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
import com.slack.api.methods.response.admin.apps.*;
import com.slack.api.methods.response.admin.conversations.AdminConversationsSetTeamsResponse;
import com.slack.api.methods.response.admin.emoji.*;
import com.slack.api.methods.response.admin.invite_requests.*;
import com.slack.api.methods.response.admin.teams.AdminTeamsAdminsListResponse;
import com.slack.api.methods.response.admin.teams.AdminTeamsCreateResponse;
import com.slack.api.methods.response.admin.teams.AdminTeamsListResponse;
import com.slack.api.methods.response.admin.teams.owners.AdminTeamsOwnersListResponse;
import com.slack.api.methods.response.admin.teams.settings.*;
import com.slack.api.methods.response.admin.users.*;
import com.slack.api.methods.response.api.ApiTestResponse;
import com.slack.api.methods.response.apps.AppsUninstallResponse;
import com.slack.api.methods.response.auth.AuthRevokeResponse;
import com.slack.api.methods.response.auth.AuthTestResponse;
import com.slack.api.methods.response.bots.BotsInfoResponse;
import com.slack.api.methods.response.channels.UsersLookupByEmailResponse;
import com.slack.api.methods.response.chat.*;
import com.slack.api.methods.response.chat.scheduled_messages.ChatScheduledMessagesListResponse;
import com.slack.api.methods.response.conversations.*;
import com.slack.api.methods.response.dialog.DialogOpenResponse;
import com.slack.api.methods.response.dnd.*;
import com.slack.api.methods.response.emoji.EmojiListResponse;
import com.slack.api.methods.response.files.*;
import com.slack.api.methods.response.files.remote.*;
import com.slack.api.methods.response.migration.MigrationExchangeResponse;
import com.slack.api.methods.response.oauth.OAuthAccessResponse;
import com.slack.api.methods.response.oauth.OAuthTokenResponse;
import com.slack.api.methods.response.oauth.OAuthV2AccessResponse;
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
import com.slack.api.methods.response.team.TeamAccessLogsResponse;
import com.slack.api.methods.response.team.TeamBillableInfoResponse;
import com.slack.api.methods.response.team.TeamInfoResponse;
import com.slack.api.methods.response.team.TeamIntegrationLogsResponse;
import com.slack.api.methods.response.team.profile.TeamProfileGetResponse;
import com.slack.api.methods.response.usergroups.*;
import com.slack.api.methods.response.usergroups.users.UsergroupsUsersListResponse;
import com.slack.api.methods.response.usergroups.users.UsergroupsUsersUpdateResponse;
import com.slack.api.methods.response.users.*;
import com.slack.api.methods.response.users.profile.UsersProfileGetResponse;
import com.slack.api.methods.response.users.profile.UsersProfileSetResponse;
import com.slack.api.methods.response.views.ViewsOpenResponse;
import com.slack.api.methods.response.views.ViewsPublishResponse;
import com.slack.api.methods.response.views.ViewsPushResponse;
import com.slack.api.methods.response.views.ViewsUpdateResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static com.slack.api.methods.Methods.*;

@Slf4j
public class AsyncMethodsClientImpl implements AsyncMethodsClient {

    private final String token;
    private final MethodsClientImpl methods;
    private final AsyncRateLimitExecutor executor;

    public AsyncMethodsClientImpl(String token, MethodsClientImpl clientImpl, SlackConfig config) {
        this.token = token;
        this.methods = clientImpl;
        this.executor = AsyncRateLimitExecutor.getOrCreate(clientImpl, config);
    }

    private String token(SlackApiRequest req) {
        if (req.getToken() != null) {
            return req.getToken();
        } else {
            return this.token;
        }
    }

    private Map<String, String> toMap(SlackApiRequest req) {
        Map<String, String> params = new HashMap<>();
        params.put("token", token(req));
        return params;
    }

    // ----------------------------------------------------------------------------------
    // public methods
    // ----------------------------------------------------------------------------------

    @Override
    public MethodsClient underlying() {
        return this.methods;
    }

    @Override
    public CompletableFuture<AdminAppsApproveResponse> adminAppsApprove(AdminAppsApproveRequest req) {
        return executor.execute(ADMIN_APPS_APPROVE, toMap(req), () -> methods.adminAppsApprove(req));
    }

    @Override
    public CompletableFuture<AdminAppsApproveResponse> adminAppsApprove(RequestConfigurator<AdminAppsApproveRequest.AdminAppsApproveRequestBuilder> req) {
        return adminAppsApprove(req.configure(AdminAppsApproveRequest.builder()).build());
    }

    @Override
    public CompletableFuture<AdminAppsRestrictResponse> adminAppsRestrict(AdminAppsRestrictRequest req) {
        return executor.execute(ADMIN_APPS_RESTRICT, toMap(req), () -> methods.adminAppsRestrict(req));
    }

    @Override
    public CompletableFuture<AdminAppsRestrictResponse> adminAppsRestrict(RequestConfigurator<AdminAppsRestrictRequest.AdminAppsRestrictRequestBuilder> req) {
        return adminAppsRestrict(req.configure(AdminAppsRestrictRequest.builder()).build());
    }

    @Override
    public CompletableFuture<AdminAppsApprovedListResponse> adminAppsApprovedList(AdminAppsApprovedListRequest req) {
        return executor.execute(ADMIN_APPS_APPROVED_LIST, toMap(req), () -> methods.adminAppsApprovedList(req));
    }

    @Override
    public CompletableFuture<AdminAppsApprovedListResponse> adminAppsApprovedList(RequestConfigurator<AdminAppsApprovedListRequest.AdminAppsApprovedListRequestBuilder> req) {
        return adminAppsApprovedList(req.configure(AdminAppsApprovedListRequest.builder()).build());
    }

    @Override
    public CompletableFuture<AdminAppsRestrictedListResponse> adminAppsRestrictedList(AdminAppsRestrictedListRequest req) {
        return executor.execute(ADMIN_APPS_RESTRICTED_LIST, toMap(req), () -> methods.adminAppsRestrictedList(req));
    }

    @Override
    public CompletableFuture<AdminAppsRestrictedListResponse> adminAppsRestrictedList(RequestConfigurator<AdminAppsRestrictedListRequest.AdminAppsRestrictedListRequestBuilder> req) {
        return adminAppsRestrictedList(req.configure(AdminAppsRestrictedListRequest.builder()).build());
    }

    @Override
    public CompletableFuture<AdminAppsRequestsListResponse> adminAppsRequestsList(AdminAppsRequestsListRequest req) {
        return executor.execute(ADMIN_APPS_REQUESTS_LIST, toMap(req), () -> methods.adminAppsRequestsList(req));
    }

    @Override
    public CompletableFuture<AdminAppsRequestsListResponse> adminAppsRequestsList(RequestConfigurator<AdminAppsRequestsListRequest.AdminAppsRequestsListRequestBuilder> req) {
        return adminAppsRequestsList(req.configure(AdminAppsRequestsListRequest.builder()).build());
    }

    @Override
    public CompletableFuture<AdminConversationsSetTeamsResponse> adminConversationsSetTeams(AdminConversationsSetTeamsRequest req) {
        return executor.execute(ADMIN_CONVERSATIONS_SET_TEAMS, toMap(req), () -> methods.adminConversationsSetTeams(req));
    }

    @Override
    public CompletableFuture<AdminConversationsSetTeamsResponse> adminConversationsSetTeams(RequestConfigurator<AdminConversationsSetTeamsRequest.AdminConversationsSetTeamsRequestBuilder> req) {
        return adminConversationsSetTeams(req.configure(AdminConversationsSetTeamsRequest.builder()).build());
    }

    @Override
    public CompletableFuture<AdminEmojiAddResponse> adminEmojiAdd(AdminEmojiAddRequest req) {
        return executor.execute(ADMIN_EMOJI_ADD, toMap(req), () -> methods.adminEmojiAdd(req));
    }

    @Override
    public CompletableFuture<AdminEmojiAddResponse> adminEmojiAdd(RequestConfigurator<AdminEmojiAddRequest.AdminEmojiAddRequestBuilder> req) {
        return adminEmojiAdd(req.configure(AdminEmojiAddRequest.builder()).build());
    }

    @Override
    public CompletableFuture<AdminEmojiAddAliasResponse> adminEmojiAddAlias(AdminEmojiAddAliasRequest req) {
        return executor.execute(ADMIN_EMOJI_ADD_ALIAS, toMap(req), () -> methods.adminEmojiAddAlias(req));
    }

    @Override
    public CompletableFuture<AdminEmojiAddAliasResponse> adminEmojiAddAlias(RequestConfigurator<AdminEmojiAddAliasRequest.AdminEmojiAddAliasRequestBuilder> req) {
        return adminEmojiAddAlias(req.configure(AdminEmojiAddAliasRequest.builder()).build());
    }

    @Override
    public CompletableFuture<AdminEmojiListResponse> adminEmojiList(AdminEmojiListRequest req) {
        return executor.execute(ADMIN_EMOJI_LIST, toMap(req), () -> methods.adminEmojiList(req));
    }

    @Override
    public CompletableFuture<AdminEmojiListResponse> adminEmojiList(RequestConfigurator<AdminEmojiListRequest.AdminEmojiListRequestBuilder> req) {
        return adminEmojiList(req.configure(AdminEmojiListRequest.builder()).build());
    }

    @Override
    public CompletableFuture<AdminEmojiRemoveResponse> adminEmojiRemove(AdminEmojiRemoveRequest req) {
        return executor.execute(ADMIN_EMOJI_REMOVE, toMap(req), () -> methods.adminEmojiRemove(req));
    }

    @Override
    public CompletableFuture<AdminEmojiRemoveResponse> adminEmojiRemove(RequestConfigurator<AdminEmojiRemoveRequest.AdminEmojiRemoveRequestBuilder> req) {
        return adminEmojiRemove(req.configure(AdminEmojiRemoveRequest.builder()).build());
    }

    @Override
    public CompletableFuture<AdminEmojiRenameResponse> adminEmojiRename(AdminEmojiRenameRequest req) {
        return executor.execute(ADMIN_EMOJI_RENAME, toMap(req), () -> methods.adminEmojiRename(req));
    }

    @Override
    public CompletableFuture<AdminEmojiRenameResponse> adminEmojiRename(RequestConfigurator<AdminEmojiRenameRequest.AdminEmojiRenameRequestBuilder> req) {
        return adminEmojiRename(req.configure(AdminEmojiRenameRequest.builder()).build());
    }

    @Override
    public CompletableFuture<AdminInviteRequestsApproveResponse> adminInviteRequestsApprove(AdminInviteRequestsApproveRequest req) {
        return executor.execute(ADMIN_INVITE_REQUESTS_APPROVE, toMap(req), () -> methods.adminInviteRequestsApprove(req));
    }

    @Override
    public CompletableFuture<AdminInviteRequestsApproveResponse> adminInviteRequestsApprove(RequestConfigurator<AdminInviteRequestsApproveRequest.AdminInviteRequestsApproveRequestBuilder> req) {
        return adminInviteRequestsApprove(req.configure(AdminInviteRequestsApproveRequest.builder()).build());
    }

    @Override
    public CompletableFuture<AdminInviteRequestsDenyResponse> adminInviteRequestsDeny(AdminInviteRequestsDenyRequest req) {
        return executor.execute(ADMIN_INVITE_REQUESTS_DENY, toMap(req), () -> methods.adminInviteRequestsDeny(req));
    }

    @Override
    public CompletableFuture<AdminInviteRequestsDenyResponse> adminInviteRequestsDeny(RequestConfigurator<AdminInviteRequestsDenyRequest.AdminInviteRequestsDenyRequestBuilder> req) {
        return adminInviteRequestsDeny(req.configure(AdminInviteRequestsDenyRequest.builder()).build());
    }

    @Override
    public CompletableFuture<AdminInviteRequestsListResponse> adminInviteRequestsList(AdminInviteRequestsListRequest req) {
        return executor.execute(ADMIN_INVITE_REQUESTS_LIST, toMap(req), () -> methods.adminInviteRequestsList(req));
    }

    @Override
    public CompletableFuture<AdminInviteRequestsListResponse> adminInviteRequestsList(RequestConfigurator<AdminInviteRequestsListRequest.AdminInviteRequestsListRequestBuilder> req) {
        return adminInviteRequestsList(req.configure(AdminInviteRequestsListRequest.builder()).build());
    }

    @Override
    public CompletableFuture<AdminInviteRequestsApprovedListResponse> adminInviteRequestsApprovedList(AdminInviteRequestsApprovedListRequest req) {
        return executor.execute(ADMIN_INVITE_REQUESTS_APPROVED_LIST, toMap(req), () -> methods.adminInviteRequestsApprovedList(req));
    }

    @Override
    public CompletableFuture<AdminInviteRequestsApprovedListResponse> adminInviteRequestsApprovedList(RequestConfigurator<AdminInviteRequestsApprovedListRequest.AdminInviteRequestsApprovedListRequestBuilder> req) {
        return adminInviteRequestsApprovedList(req.configure(AdminInviteRequestsApprovedListRequest.builder()).build());
    }

    @Override
    public CompletableFuture<AdminInviteRequestsDeniedListResponse> adminInviteRequestsDeniedList(AdminInviteRequestsDeniedListRequest req) {
        return executor.execute(ADMIN_INVITE_REQUESTS_DENIED_LIST, toMap(req), () -> methods.adminInviteRequestsDeniedList(req));
    }

    @Override
    public CompletableFuture<AdminInviteRequestsDeniedListResponse> adminInviteRequestsDeniedList(RequestConfigurator<AdminInviteRequestsDeniedListRequest.AdminInviteRequestsDeniedListRequestBuilder> req) {
        return adminInviteRequestsDeniedList(req.configure(AdminInviteRequestsDeniedListRequest.builder()).build());
    }

    @Override
    public CompletableFuture<AdminTeamsAdminsListResponse> adminTeamsAdminsList(AdminTeamsAdminsListRequest req) {
        return executor.execute(ADMIN_TEAMS_ADMINS_LIST, toMap(req), () -> methods.adminTeamsAdminsList(req));
    }

    @Override
    public CompletableFuture<AdminTeamsAdminsListResponse> adminTeamsAdminsList(RequestConfigurator<AdminTeamsAdminsListRequest.AdminTeamsAdminsListRequestBuilder> req) {
        return adminTeamsAdminsList(req.configure(AdminTeamsAdminsListRequest.builder()).build());
    }

    @Override
    public CompletableFuture<AdminTeamsCreateResponse> adminTeamsCreate(AdminTeamsCreateRequest req) {
        return executor.execute(ADMIN_TEAMS_CREATE, toMap(req), () -> methods.adminTeamsCreate(req));
    }

    @Override
    public CompletableFuture<AdminTeamsCreateResponse> adminTeamsCreate(RequestConfigurator<AdminTeamsCreateRequest.AdminTeamsCreateRequestBuilder> req) {
        return adminTeamsCreate(req.configure(AdminTeamsCreateRequest.builder()).build());
    }

    @Override
    public CompletableFuture<AdminTeamsListResponse> adminTeamsList(AdminTeamsListRequest req) {
        return executor.execute(ADMIN_TEAMS_LIST, toMap(req), () -> methods.adminTeamsList(req));
    }

    @Override
    public CompletableFuture<AdminTeamsListResponse> adminTeamsList(RequestConfigurator<AdminTeamsListRequest.AdminTeamsListRequestBuilder> req) {
        return adminTeamsList(req.configure(AdminTeamsListRequest.builder()).build());
    }

    @Override
    public CompletableFuture<AdminTeamsOwnersListResponse> adminTeamsOwnersList(AdminTeamsOwnersListRequest req) {
        return executor.execute(ADMIN_TEAMS_OWNERS_LIST, toMap(req), () -> methods.adminTeamsOwnersList(req));
    }

    @Override
    public CompletableFuture<AdminTeamsOwnersListResponse> adminTeamsOwnersList(RequestConfigurator<AdminTeamsOwnersListRequest.AdminTeamsOwnersListRequestBuilder> req) {
        return adminTeamsOwnersList(req.configure(AdminTeamsOwnersListRequest.builder()).build());
    }

    @Override
    public CompletableFuture<AdminTeamsSettingsInfoResponse> adminTeamsSettingsInfo(AdminTeamsSettingsInfoRequest req) {
        return executor.execute(ADMIN_TEAMS_SETTINGS_INFO, toMap(req), () -> methods.adminTeamsSettingsInfo(req));
    }

    @Override
    public CompletableFuture<AdminTeamsSettingsInfoResponse> adminTeamsSettingsInfo(RequestConfigurator<AdminTeamsSettingsInfoRequest.AdminTeamsSettingsInfoRequestBuilder> req) {
        return adminTeamsSettingsInfo(req.configure(AdminTeamsSettingsInfoRequest.builder()).build());
    }

    @Override
    public CompletableFuture<AdminTeamsSettingsSetDefaultChannelsResponse> adminTeamsSettingsSetDefaultChannels(AdminTeamsSettingsSetDefaultChannelsRequest req) {
        return executor.execute(ADMIN_TEAMS_SETTINGS_SET_DEFAULT_CHANNELS, toMap(req), () -> methods.adminTeamsSettingsSetDefaultChannels(req));
    }

    @Override
    public CompletableFuture<AdminTeamsSettingsSetDefaultChannelsResponse> adminTeamsSettingsSetDefaultChannels(RequestConfigurator<AdminTeamsSettingsSetDefaultChannelsRequest.AdminTeamsSettingsSetDefaultChannelsRequestBuilder> req) {
        return adminTeamsSettingsSetDefaultChannels(req.configure(AdminTeamsSettingsSetDefaultChannelsRequest.builder()).build());
    }

    @Override
    public CompletableFuture<AdminTeamsSettingsSetDescriptionResponse> adminTeamsSettingsSetDescription(AdminTeamsSettingsSetDescriptionRequest req) {
        return executor.execute(ADMIN_TEAMS_SETTINGS_SET_DESCRIPTION, toMap(req), () -> methods.adminTeamsSettingsSetDescription(req));
    }

    @Override
    public CompletableFuture<AdminTeamsSettingsSetDescriptionResponse> adminTeamsSettingsSetDescription(RequestConfigurator<AdminTeamsSettingsSetDescriptionRequest.AdminTeamsSettingsSetDescriptionRequestBuilder> req) {
        return adminTeamsSettingsSetDescription(req.configure(AdminTeamsSettingsSetDescriptionRequest.builder()).build());
    }

    @Override
    public CompletableFuture<AdminTeamsSettingsSetDiscoverabilityResponse> adminTeamsSettingsSetDiscoverability(AdminTeamsSettingsSetDiscoverabilityRequest req) {
        return executor.execute(ADMIN_TEAMS_SETTINGS_SET_DISCOVERABILITY, toMap(req), () -> methods.adminTeamsSettingsSetDiscoverability(req));
    }

    @Override
    public CompletableFuture<AdminTeamsSettingsSetDiscoverabilityResponse> adminTeamsSettingsSetDiscoverability(RequestConfigurator<AdminTeamsSettingsSetDiscoverabilityRequest.AdminTeamsSettingsSetDiscoverabilityRequestBuilder> req) {
        return adminTeamsSettingsSetDiscoverability(req.configure(AdminTeamsSettingsSetDiscoverabilityRequest.builder()).build());
    }

    @Override
    public CompletableFuture<AdminTeamsSettingsSetIconResponse> adminTeamsSettingsSetIcon(AdminTeamsSettingsSetIconRequest req) {
        return executor.execute(ADMIN_TEAMS_SETTINGS_SET_ICON, toMap(req), () -> methods.adminTeamsSettingsSetIcon(req));
    }

    @Override
    public CompletableFuture<AdminTeamsSettingsSetIconResponse> adminTeamsSettingsSetIcon(RequestConfigurator<AdminTeamsSettingsSetIconRequest.AdminTeamsSettingsSetIconRequestBuilder> req) {
        return adminTeamsSettingsSetIcon(req.configure(AdminTeamsSettingsSetIconRequest.builder()).build());
    }

    @Override
    public CompletableFuture<AdminTeamsSettingsSetNameResponse> adminTeamsSettingsSetName(AdminTeamsSettingsSetNameRequest req) {
        return executor.execute(ADMIN_TEAMS_SETTINGS_SET_NAME, toMap(req), () -> methods.adminTeamsSettingsSetName(req));
    }

    @Override
    public CompletableFuture<AdminTeamsSettingsSetNameResponse> adminTeamsSettingsSetName(RequestConfigurator<AdminTeamsSettingsSetNameRequest.AdminTeamsSettingsSetNameRequestBuilder> req) {
        return adminTeamsSettingsSetName(req.configure(AdminTeamsSettingsSetNameRequest.builder()).build());
    }

    @Override
    public CompletableFuture<AdminUsersAssignResponse> adminUsersAssign(AdminUsersAssignRequest req) {
        return executor.execute(ADMIN_USERS_ASSIGN, toMap(req), () -> methods.adminUsersAssign(req));
    }

    @Override
    public CompletableFuture<AdminUsersAssignResponse> adminUsersAssign(RequestConfigurator<AdminUsersAssignRequest.AdminUsersAssignRequestBuilder> req) {
        return adminUsersAssign(req.configure(AdminUsersAssignRequest.builder()).build());
    }

    @Override
    public CompletableFuture<AdminUsersInviteResponse> adminUsersInvite(AdminUsersInviteRequest req) {
        return executor.execute(ADMIN_USERS_INVITE, toMap(req), () -> methods.adminUsersInvite(req));
    }

    @Override
    public CompletableFuture<AdminUsersInviteResponse> adminUsersInvite(RequestConfigurator<AdminUsersInviteRequest.AdminUsersInviteRequestBuilder> req) {
        return adminUsersInvite(req.configure(AdminUsersInviteRequest.builder()).build());
    }

    @Override
    public CompletableFuture<AdminUsersListResponse> adminUsersList(AdminUsersListRequest req) {
        return executor.execute(ADMIN_USERS_LIST, toMap(req), () -> methods.adminUsersList(req));
    }

    @Override
    public CompletableFuture<AdminUsersListResponse> adminUsersList(RequestConfigurator<AdminUsersListRequest.AdminUsersListRequestBuilder> req) {
        return adminUsersList(req.configure(AdminUsersListRequest.builder()).build());
    }

    @Override
    public CompletableFuture<AdminUsersRemoveResponse> adminUsersRemove(AdminUsersRemoveRequest req) {
        return executor.execute(ADMIN_USERS_REMOVE, toMap(req), () -> methods.adminUsersRemove(req));
    }

    @Override
    public CompletableFuture<AdminUsersRemoveResponse> adminUsersRemove(RequestConfigurator<AdminUsersRemoveRequest.AdminUsersRemoveRequestBuilder> req) {
        return adminUsersRemove(req.configure(AdminUsersRemoveRequest.builder()).build());
    }

    @Override
    public CompletableFuture<AdminUsersSetAdminResponse> adminUsersSetAdmin(AdminUsersSetAdminRequest req) {
        return executor.execute(ADMIN_USERS_SET_ADMIN, toMap(req), () -> methods.adminUsersSetAdmin(req));
    }

    @Override
    public CompletableFuture<AdminUsersSetAdminResponse> adminUsersSetAdmin(RequestConfigurator<AdminUsersSetAdminRequest.AdminUsersSetAdminRequestBuilder> req) {
        return adminUsersSetAdmin(req.configure(AdminUsersSetAdminRequest.builder()).build());
    }

    @Override
    public CompletableFuture<AdminUsersSetExpirationResponse> adminUsersSetExpiration(AdminUsersSetExpirationRequest req) {
        return executor.execute(ADMIN_USERS_SET_EXPIRATION, toMap(req), () -> methods.adminUsersSetExpiration(req));
    }

    @Override
    public CompletableFuture<AdminUsersSetExpirationResponse> adminUsersSetExpiration(RequestConfigurator<AdminUsersSetExpirationRequest.AdminUsersSetExpirationRequestBuilder> req) {
        return adminUsersSetExpiration(req.configure(AdminUsersSetExpirationRequest.builder()).build());
    }

    @Override
    public CompletableFuture<AdminUsersSetOwnerResponse> adminUsersSetOwner(AdminUsersSetOwnerRequest req) {
        return executor.execute(ADMIN_USERS_SET_OWNER, toMap(req), () -> methods.adminUsersSetOwner(req));
    }

    @Override
    public CompletableFuture<AdminUsersSetOwnerResponse> adminUsersSetOwner(RequestConfigurator<AdminUsersSetOwnerRequest.AdminUsersSetOwnerRequestBuilder> req) {
        return adminUsersSetOwner(req.configure(AdminUsersSetOwnerRequest.builder()).build());
    }

    @Override
    public CompletableFuture<AdminUsersSetRegularResponse> adminUsersSetRegular(AdminUsersSetRegularRequest req) {
        return executor.execute(ADMIN_USERS_SET_REGULAR, toMap(req), () -> methods.adminUsersSetRegular(req));
    }

    @Override
    public CompletableFuture<AdminUsersSetRegularResponse> adminUsersSetRegular(RequestConfigurator<AdminUsersSetRegularRequest.AdminUsersSetRegularRequestBuilder> req) {
        return adminUsersSetRegular(req.configure(AdminUsersSetRegularRequest.builder()).build());
    }

    @Override
    public CompletableFuture<AdminUsersSessionResetResponse> adminUsersSessionReset(AdminUsersSessionResetRequest req) {
        return executor.execute(ADMIN_USERS_SESSION_RESET, toMap(req), () -> methods.adminUsersSessionReset(req));
    }

    @Override
    public CompletableFuture<AdminUsersSessionResetResponse> adminUsersSessionReset(RequestConfigurator<AdminUsersSessionResetRequest.AdminUsersSessionResetRequestBuilder> req) {
        return adminUsersSessionReset(req.configure(AdminUsersSessionResetRequest.builder()).build());
    }

    @Override
    public CompletableFuture<ApiTestResponse> apiTest(ApiTestRequest req) {
        return executor.execute(API_TEST, toMap(req), () -> methods.apiTest(req));
    }

    @Override
    public CompletableFuture<ApiTestResponse> apiTest(RequestConfigurator<ApiTestRequest.ApiTestRequestBuilder> req) {
        return apiTest(req.configure(ApiTestRequest.builder()).build());
    }

    @Override
    public CompletableFuture<AppsUninstallResponse> appsUninstall(AppsUninstallRequest req) {
        return executor.execute(APPS_UNINSTALL, toMap(req), () -> methods.appsUninstall(req));
    }

    @Override
    public CompletableFuture<AppsUninstallResponse> appsUninstall(RequestConfigurator<AppsUninstallRequest.AppsUninstallRequestBuilder> req) {
        return appsUninstall(req.configure(AppsUninstallRequest.builder()).build());
    }

    @Override
    public CompletableFuture<AuthRevokeResponse> authRevoke(AuthRevokeRequest req) {
        return executor.execute(AUTH_REVOKE, toMap(req), () -> methods.authRevoke(req));
    }

    @Override
    public CompletableFuture<AuthRevokeResponse> authRevoke(RequestConfigurator<AuthRevokeRequest.AuthRevokeRequestBuilder> req) {
        return authRevoke(req.configure(AuthRevokeRequest.builder()).build());
    }

    @Override
    public CompletableFuture<AuthTestResponse> authTest(AuthTestRequest req) {
        return executor.execute(AUTH_TEST, toMap(req), () -> methods.authTest(req));
    }

    @Override
    public CompletableFuture<AuthTestResponse> authTest(RequestConfigurator<AuthTestRequest.AuthTestRequestBuilder> req) {
        return authTest(req.configure(AuthTestRequest.builder()).build());
    }

    @Override
    public CompletableFuture<BotsInfoResponse> botsInfo(BotsInfoRequest req) {
        return executor.execute(BOTS_INFO, toMap(req), () -> methods.botsInfo(req));
    }

    @Override
    public CompletableFuture<BotsInfoResponse> botsInfo(RequestConfigurator<BotsInfoRequest.BotsInfoRequestBuilder> req) {
        return botsInfo(req.configure(BotsInfoRequest.builder()).build());
    }

    @Override
    public CompletableFuture<ChatGetPermalinkResponse> chatGetPermalink(ChatGetPermalinkRequest req) {
        return executor.execute(CHAT_GET_PERMALINK, toMap(req), () -> methods.chatGetPermalink(req));
    }

    @Override
    public CompletableFuture<ChatGetPermalinkResponse> chatGetPermalink(RequestConfigurator<ChatGetPermalinkRequest.ChatGetPermalinkRequestBuilder> req) {
        return chatGetPermalink(req.configure(ChatGetPermalinkRequest.builder()).build());
    }

    @Override
    public CompletableFuture<ChatDeleteResponse> chatDelete(ChatDeleteRequest req) {
        return executor.execute(CHAT_DELETE, toMap(req), () -> methods.chatDelete(req));
    }

    @Override
    public CompletableFuture<ChatDeleteResponse> chatDelete(RequestConfigurator<ChatDeleteRequest.ChatDeleteRequestBuilder> req) {
        return chatDelete(req.configure(ChatDeleteRequest.builder()).build());
    }

    @Override
    public CompletableFuture<ChatDeleteScheduledMessageResponse> chatDeleteScheduledMessage(ChatDeleteScheduledMessageRequest req) {
        return executor.execute(CHAT_DELETE_SCHEDULED_MESSAGE, toMap(req), () -> methods.chatDeleteScheduledMessage(req));
    }

    @Override
    public CompletableFuture<ChatDeleteScheduledMessageResponse> chatDeleteScheduledMessage(RequestConfigurator<ChatDeleteScheduledMessageRequest.ChatDeleteScheduledMessageRequestBuilder> req) {
        return chatDeleteScheduledMessage(req.configure(ChatDeleteScheduledMessageRequest.builder()).build());
    }

    @Override
    public CompletableFuture<ChatMeMessageResponse> chatMeMessage(ChatMeMessageRequest req) {
        return executor.execute(CHAT_ME_MESSAGE, toMap(req), () -> methods.chatMeMessage(req));
    }

    @Override
    public CompletableFuture<ChatMeMessageResponse> chatMeMessage(RequestConfigurator<ChatMeMessageRequest.ChatMeMessageRequestBuilder> req) {
        return chatMeMessage(req.configure(ChatMeMessageRequest.builder()).build());
    }

    @Override
    public CompletableFuture<ChatPostEphemeralResponse> chatPostEphemeral(ChatPostEphemeralRequest req) {
        return executor.execute(CHAT_POST_EPHEMERAL, toMap(req), () -> methods.chatPostEphemeral(req));
    }

    @Override
    public CompletableFuture<ChatPostEphemeralResponse> chatPostEphemeral(RequestConfigurator<ChatPostEphemeralRequest.ChatPostEphemeralRequestBuilder> req) {
        return chatPostEphemeral(req.configure(ChatPostEphemeralRequest.builder()).build());
    }

    @Override
    public CompletableFuture<ChatPostMessageResponse> chatPostMessage(ChatPostMessageRequest req) {
        Map<String, String> params = new HashMap<>();
        params.put("token", token(req));
        params.put("channel", req.getChannel()); // for rate limiting
        return executor.execute(CHAT_POST_MESSAGE, params, () -> methods.chatPostMessage(req));
    }

    @Override
    public CompletableFuture<ChatPostMessageResponse> chatPostMessage(RequestConfigurator<ChatPostMessageRequest.ChatPostMessageRequestBuilder> req) {
        return chatPostMessage(req.configure(ChatPostMessageRequest.builder()).build());
    }

    @Override
    public CompletableFuture<ChatScheduleMessageResponse> chatScheduleMessage(ChatScheduleMessageRequest req) {
        return executor.execute(CHAT_SCHEDULE_MESSAGE, toMap(req), () -> methods.chatScheduleMessage(req));
    }

    @Override
    public CompletableFuture<ChatScheduleMessageResponse> chatScheduleMessage(RequestConfigurator<ChatScheduleMessageRequest.ChatScheduleMessageRequestBuilder> req) {
        return chatScheduleMessage(req.configure(ChatScheduleMessageRequest.builder()).build());
    }

    @Override
    public CompletableFuture<ChatUpdateResponse> chatUpdate(ChatUpdateRequest req) {
        return executor.execute(CHAT_UPDATE, toMap(req), () -> methods.chatUpdate(req));
    }

    @Override
    public CompletableFuture<ChatUpdateResponse> chatUpdate(RequestConfigurator<ChatUpdateRequest.ChatUpdateRequestBuilder> req) {
        return chatUpdate(req.configure(ChatUpdateRequest.builder()).build());
    }

    @Override
    public CompletableFuture<ChatUnfurlResponse> chatUnfurl(ChatUnfurlRequest req) {
        return executor.execute(CHAT_UNFURL, toMap(req), () -> methods.chatUnfurl(req));
    }

    @Override
    public CompletableFuture<ChatUnfurlResponse> chatUnfurl(RequestConfigurator<ChatUnfurlRequest.ChatUnfurlRequestBuilder> req) {
        return chatUnfurl(req.configure(ChatUnfurlRequest.builder()).build());
    }

    @Override
    public CompletableFuture<ChatScheduledMessagesListResponse> chatScheduledMessagesList(ChatScheduledMessagesListRequest req) {
        return executor.execute(CHAT_SCHEDULED_MESSAGES_LIST, toMap(req), () -> methods.chatScheduledMessagesList(req));
    }

    @Override
    public CompletableFuture<ChatScheduledMessagesListResponse> chatScheduledMessagesList(RequestConfigurator<ChatScheduledMessagesListRequest.ChatScheduledMessagesListRequestBuilder> req) {
        return chatScheduledMessagesList(req.configure(ChatScheduledMessagesListRequest.builder()).build());
    }

    @Override
    public CompletableFuture<ConversationsArchiveResponse> conversationsArchive(ConversationsArchiveRequest req) {
        return executor.execute(CONVERSATIONS_ARCHIVE, toMap(req), () -> methods.conversationsArchive(req));
    }

    @Override
    public CompletableFuture<ConversationsArchiveResponse> conversationsArchive(RequestConfigurator<ConversationsArchiveRequest.ConversationsArchiveRequestBuilder> req) {
        return conversationsArchive(req.configure(ConversationsArchiveRequest.builder()).build());
    }

    @Override
    public CompletableFuture<ConversationsCloseResponse> conversationsClose(ConversationsCloseRequest req) {
        return executor.execute(CONVERSATIONS_CLOSE, toMap(req), () -> methods.conversationsClose(req));
    }

    @Override
    public CompletableFuture<ConversationsCloseResponse> conversationsClose(RequestConfigurator<ConversationsCloseRequest.ConversationsCloseRequestBuilder> req) {
        return conversationsClose(req.configure(ConversationsCloseRequest.builder()).build());
    }

    @Override
    public CompletableFuture<ConversationsCreateResponse> conversationsCreate(ConversationsCreateRequest req) {
        return executor.execute(CONVERSATIONS_CREATE, toMap(req), () -> methods.conversationsCreate(req));
    }

    @Override
    public CompletableFuture<ConversationsCreateResponse> conversationsCreate(RequestConfigurator<ConversationsCreateRequest.ConversationsCreateRequestBuilder> req) {
        return conversationsCreate(req.configure(ConversationsCreateRequest.builder()).build());
    }

    @Override
    public CompletableFuture<ConversationsHistoryResponse> conversationsHistory(ConversationsHistoryRequest req) {
        return executor.execute(CONVERSATIONS_HISTORY, toMap(req), () -> methods.conversationsHistory(req));
    }

    @Override
    public CompletableFuture<ConversationsHistoryResponse> conversationsHistory(RequestConfigurator<ConversationsHistoryRequest.ConversationsHistoryRequestBuilder> req) {
        return conversationsHistory(req.configure(ConversationsHistoryRequest.builder()).build());
    }

    @Override
    public CompletableFuture<ConversationsInfoResponse> conversationsInfo(ConversationsInfoRequest req) {
        return executor.execute(CONVERSATIONS_INFO, toMap(req), () -> methods.conversationsInfo(req));
    }

    @Override
    public CompletableFuture<ConversationsInfoResponse> conversationsInfo(RequestConfigurator<ConversationsInfoRequest.ConversationsInfoRequestBuilder> req) {
        return conversationsInfo(req.configure(ConversationsInfoRequest.builder()).build());
    }

    @Override
    public CompletableFuture<ConversationsInviteResponse> conversationsInvite(ConversationsInviteRequest req) {
        return executor.execute(CONVERSATIONS_INVITE, toMap(req), () -> methods.conversationsInvite(req));
    }

    @Override
    public CompletableFuture<ConversationsInviteResponse> conversationsInvite(RequestConfigurator<ConversationsInviteRequest.ConversationsInviteRequestBuilder> req) {
        return conversationsInvite(req.configure(ConversationsInviteRequest.builder()).build());
    }

    @Override
    public CompletableFuture<ConversationsJoinResponse> conversationsJoin(ConversationsJoinRequest req) {
        return executor.execute(CONVERSATIONS_JOIN, toMap(req), () -> methods.conversationsJoin(req));
    }

    @Override
    public CompletableFuture<ConversationsJoinResponse> conversationsJoin(RequestConfigurator<ConversationsJoinRequest.ConversationsJoinRequestBuilder> req) {
        return conversationsJoin(req.configure(ConversationsJoinRequest.builder()).build());
    }

    @Override
    public CompletableFuture<ConversationsKickResponse> conversationsKick(ConversationsKickRequest req) {
        return executor.execute(CONVERSATIONS_KICK, toMap(req), () -> methods.conversationsKick(req));
    }

    @Override
    public CompletableFuture<ConversationsKickResponse> conversationsKick(RequestConfigurator<ConversationsKickRequest.ConversationsKickRequestBuilder> req) {
        return conversationsKick(req.configure(ConversationsKickRequest.builder()).build());
    }

    @Override
    public CompletableFuture<ConversationsLeaveResponse> conversationsLeave(ConversationsLeaveRequest req) {
        return executor.execute(CONVERSATIONS_LEAVE, toMap(req), () -> methods.conversationsLeave(req));
    }

    @Override
    public CompletableFuture<ConversationsLeaveResponse> conversationsLeave(RequestConfigurator<ConversationsLeaveRequest.ConversationsLeaveRequestBuilder> req) {
        return conversationsLeave(req.configure(ConversationsLeaveRequest.builder()).build());
    }

    @Override
    public CompletableFuture<ConversationsListResponse> conversationsList(ConversationsListRequest req) {
        return executor.execute(CONVERSATIONS_LIST, toMap(req), () -> methods.conversationsList(req));
    }

    @Override
    public CompletableFuture<ConversationsListResponse> conversationsList(RequestConfigurator<ConversationsListRequest.ConversationsListRequestBuilder> req) {
        return conversationsList(req.configure(ConversationsListRequest.builder()).build());
    }

    @Override
    public CompletableFuture<ConversationsMembersResponse> conversationsMembers(ConversationsMembersRequest req) {
        return executor.execute(CONVERSATIONS_MEMBERS, toMap(req), () -> methods.conversationsMembers(req));
    }

    @Override
    public CompletableFuture<ConversationsMembersResponse> conversationsMembers(RequestConfigurator<ConversationsMembersRequest.ConversationsMembersRequestBuilder> req) {
        return conversationsMembers(req.configure(ConversationsMembersRequest.builder()).build());
    }

    @Override
    public CompletableFuture<ConversationsOpenResponse> conversationsOpen(ConversationsOpenRequest req) {
        return executor.execute(CONVERSATIONS_OPEN, toMap(req), () -> methods.conversationsOpen(req));
    }

    @Override
    public CompletableFuture<ConversationsOpenResponse> conversationsOpen(RequestConfigurator<ConversationsOpenRequest.ConversationsOpenRequestBuilder> req) {
        return conversationsOpen(req.configure(ConversationsOpenRequest.builder()).build());
    }

    @Override
    public CompletableFuture<ConversationsRenameResponse> conversationsRename(ConversationsRenameRequest req) {
        return executor.execute(CONVERSATIONS_RENAME, toMap(req), () -> methods.conversationsRename(req));
    }

    @Override
    public CompletableFuture<ConversationsRenameResponse> conversationsRename(RequestConfigurator<ConversationsRenameRequest.ConversationsRenameRequestBuilder> req) {
        return conversationsRename(req.configure(ConversationsRenameRequest.builder()).build());
    }

    @Override
    public CompletableFuture<ConversationsRepliesResponse> conversationsReplies(ConversationsRepliesRequest req) {
        return executor.execute(CONVERSATIONS_REPLIES, toMap(req), () -> methods.conversationsReplies(req));
    }

    @Override
    public CompletableFuture<ConversationsRepliesResponse> conversationsReplies(RequestConfigurator<ConversationsRepliesRequest.ConversationsRepliesRequestBuilder> req) {
        return conversationsReplies(req.configure(ConversationsRepliesRequest.builder()).build());
    }

    @Override
    public CompletableFuture<ConversationsSetPurposeResponse> conversationsSetPurpose(ConversationsSetPurposeRequest req) {
        return executor.execute(CONVERSATIONS_SET_PURPOSE, toMap(req), () -> methods.conversationsSetPurpose(req));
    }

    @Override
    public CompletableFuture<ConversationsSetPurposeResponse> conversationsSetPurpose(RequestConfigurator<ConversationsSetPurposeRequest.ConversationsSetPurposeRequestBuilder> req) {
        return conversationsSetPurpose(req.configure(ConversationsSetPurposeRequest.builder()).build());
    }

    @Override
    public CompletableFuture<ConversationsSetTopicResponse> conversationsSetTopic(ConversationsSetTopicRequest req) {
        return executor.execute(CONVERSATIONS_SET_TOPIC, toMap(req), () -> methods.conversationsSetTopic(req));
    }

    @Override
    public CompletableFuture<ConversationsSetTopicResponse> conversationsSetTopic(RequestConfigurator<ConversationsSetTopicRequest.ConversationsSetTopicRequestBuilder> req) {
        return conversationsSetTopic(req.configure(ConversationsSetTopicRequest.builder()).build());
    }

    @Override
    public CompletableFuture<ConversationsUnarchiveResponse> conversationsUnarchive(ConversationsUnarchiveRequest req) {
        return executor.execute(CONVERSATIONS_UNARCHIVE, toMap(req), () -> methods.conversationsUnarchive(req));
    }

    @Override
    public CompletableFuture<ConversationsUnarchiveResponse> conversationsUnarchive(RequestConfigurator<ConversationsUnarchiveRequest.ConversationsUnarchiveRequestBuilder> req) {
        return conversationsUnarchive(req.configure(ConversationsUnarchiveRequest.builder()).build());
    }

    @Override
    public CompletableFuture<DialogOpenResponse> dialogOpen(DialogOpenRequest req) {
        return executor.execute(DIALOG_OPEN, toMap(req), () -> methods.dialogOpen(req));
    }

    @Override
    public CompletableFuture<DialogOpenResponse> dialogOpen(RequestConfigurator<DialogOpenRequest.DialogOpenRequestBuilder> req) {
        return dialogOpen(req.configure(DialogOpenRequest.builder()).build());
    }

    @Override
    public CompletableFuture<DndEndDndResponse> dndEndDnd(DndEndDndRequest req) {
        return executor.execute(DND_END_DND, toMap(req), () -> methods.dndEndDnd(req));
    }

    @Override
    public CompletableFuture<DndEndDndResponse> dndEndDnd(RequestConfigurator<DndEndDndRequest.DndEndDndRequestBuilder> req) {
        return dndEndDnd(req.configure(DndEndDndRequest.builder()).build());
    }

    @Override
    public CompletableFuture<DndEndSnoozeResponse> dndEndSnooze(DndEndSnoozeRequest req) {
        return executor.execute(DND_END_SNOOZE, toMap(req), () -> methods.dndEndSnooze(req));
    }

    @Override
    public CompletableFuture<DndEndSnoozeResponse> dndEndSnooze(RequestConfigurator<DndEndSnoozeRequest.DndEndSnoozeRequestBuilder> req) {
        return dndEndSnooze(req.configure(DndEndSnoozeRequest.builder()).build());
    }

    @Override
    public CompletableFuture<DndInfoResponse> dndInfo(DndInfoRequest req) {
        return executor.execute(DND_INFO, toMap(req), () -> methods.dndInfo(req));
    }

    @Override
    public CompletableFuture<DndInfoResponse> dndInfo(RequestConfigurator<DndInfoRequest.DndInfoRequestBuilder> req) {
        return dndInfo(req.configure(DndInfoRequest.builder()).build());
    }

    @Override
    public CompletableFuture<DndSetSnoozeResponse> dndSetSnooze(DndSetSnoozeRequest req) {
        return executor.execute(DND_SET_SNOOZE, toMap(req), () -> methods.dndSetSnooze(req));
    }

    @Override
    public CompletableFuture<DndSetSnoozeResponse> dndSetSnooze(RequestConfigurator<DndSetSnoozeRequest.DndSetSnoozeRequestBuilder> req) {
        return dndSetSnooze(req.configure(DndSetSnoozeRequest.builder()).build());
    }

    @Override
    public CompletableFuture<DndTeamInfoResponse> dndTeamInfo(DndTeamInfoRequest req) {
        return executor.execute(DND_TEAM_INFO, toMap(req), () -> methods.dndTeamInfo(req));
    }

    @Override
    public CompletableFuture<DndTeamInfoResponse> dndTeamInfo(RequestConfigurator<DndTeamInfoRequest.DndTeamInfoRequestBuilder> req) {
        return dndTeamInfo(req.configure(DndTeamInfoRequest.builder()).build());
    }

    @Override
    public CompletableFuture<EmojiListResponse> emojiList(EmojiListRequest req) {
        return executor.execute(EMOJI_LIST, toMap(req), () -> methods.emojiList(req));
    }

    @Override
    public CompletableFuture<EmojiListResponse> emojiList(RequestConfigurator<EmojiListRequest.EmojiListRequestBuilder> req) {
        return emojiList(req.configure(EmojiListRequest.builder()).build());
    }

    @Override
    public CompletableFuture<FilesDeleteResponse> filesDelete(FilesDeleteRequest req) {
        return executor.execute(FILES_DELETE, toMap(req), () -> methods.filesDelete(req));
    }

    @Override
    public CompletableFuture<FilesDeleteResponse> filesDelete(RequestConfigurator<FilesDeleteRequest.FilesDeleteRequestBuilder> req) {
        return filesDelete(req.configure(FilesDeleteRequest.builder()).build());
    }

    @Override
    public CompletableFuture<FilesInfoResponse> filesInfo(FilesInfoRequest req) {
        return executor.execute(FILES_INFO, toMap(req), () -> methods.filesInfo(req));
    }

    @Override
    public CompletableFuture<FilesInfoResponse> filesInfo(RequestConfigurator<FilesInfoRequest.FilesInfoRequestBuilder> req) {
        return filesInfo(req.configure(FilesInfoRequest.builder()).build());
    }

    @Override
    public CompletableFuture<FilesListResponse> filesList(FilesListRequest req) {
        return executor.execute(FILES_LIST, toMap(req), () -> methods.filesList(req));
    }

    @Override
    public CompletableFuture<FilesListResponse> filesList(RequestConfigurator<FilesListRequest.FilesListRequestBuilder> req) {
        return filesList(req.configure(FilesListRequest.builder()).build());
    }

    @Override
    public CompletableFuture<FilesRevokePublicURLResponse> filesRevokePublicURL(FilesRevokePublicURLRequest req) {
        return executor.execute(FILES_REVOKE_PUBLIC_URL, toMap(req), () -> methods.filesRevokePublicURL(req));
    }

    @Override
    public CompletableFuture<FilesRevokePublicURLResponse> filesRevokePublicURL(RequestConfigurator<FilesRevokePublicURLRequest.FilesRevokePublicURLRequestBuilder> req) {
        return filesRevokePublicURL(req.configure(FilesRevokePublicURLRequest.builder()).build());
    }

    @Override
    public CompletableFuture<FilesSharedPublicURLResponse> filesSharedPublicURL(FilesSharedPublicURLRequest req) {
        return executor.execute(FILES_SHARED_PUBLIC_URL, toMap(req), () -> methods.filesSharedPublicURL(req));
    }

    @Override
    public CompletableFuture<FilesSharedPublicURLResponse> filesSharedPublicURL(RequestConfigurator<FilesSharedPublicURLRequest.FilesSharedPublicURLRequestBuilder> req) {
        return filesSharedPublicURL(req.configure(FilesSharedPublicURLRequest.builder()).build());
    }

    @Override
    public CompletableFuture<FilesUploadResponse> filesUpload(FilesUploadRequest req) {
        return executor.execute(FILES_UPLOAD, toMap(req), () -> methods.filesUpload(req));
    }

    @Override
    public CompletableFuture<FilesUploadResponse> filesUpload(RequestConfigurator<FilesUploadRequest.FilesUploadRequestBuilder> req) {
        return filesUpload(req.configure(FilesUploadRequest.builder()).build());
    }

    @Override
    public CompletableFuture<FilesRemoteAddResponse> filesRemoteAdd(FilesRemoteAddRequest req) {
        return executor.execute(FILES_REMOTE_ADD, toMap(req), () -> methods.filesRemoteAdd(req));
    }

    @Override
    public CompletableFuture<FilesRemoteAddResponse> filesRemoteAdd(RequestConfigurator<FilesRemoteAddRequest.FilesRemoteAddRequestBuilder> req) {
        return filesRemoteAdd(req.configure(FilesRemoteAddRequest.builder()).build());
    }

    @Override
    public CompletableFuture<FilesRemoteInfoResponse> filesRemoteInfo(FilesRemoteInfoRequest req) {
        return executor.execute(FILES_REMOTE_INFO, toMap(req), () -> methods.filesRemoteInfo(req));
    }

    @Override
    public CompletableFuture<FilesRemoteInfoResponse> filesRemoteInfo(RequestConfigurator<FilesRemoteInfoRequest.FilesRemoteInfoRequestBuilder> req) {
        return filesRemoteInfo(req.configure(FilesRemoteInfoRequest.builder()).build());
    }

    @Override
    public CompletableFuture<FilesRemoteListResponse> filesRemoteList(FilesRemoteListRequest req) {
        return executor.execute(FILES_REMOTE_LIST, toMap(req), () -> methods.filesRemoteList(req));
    }

    @Override
    public CompletableFuture<FilesRemoteListResponse> filesRemoteList(RequestConfigurator<FilesRemoteListRequest.FilesRemoteListRequestBuilder> req) {
        return filesRemoteList(req.configure(FilesRemoteListRequest.builder()).build());
    }

    @Override
    public CompletableFuture<FilesRemoteRemoveResponse> filesRemoteRemove(FilesRemoteRemoveRequest req) {
        return executor.execute(FILES_REMOTE_REMOVE, toMap(req), () -> methods.filesRemoteRemove(req));
    }

    @Override
    public CompletableFuture<FilesRemoteRemoveResponse> filesRemoteRemove(RequestConfigurator<FilesRemoteRemoveRequest.FilesRemoteRemoveRequestBuilder> req) {
        return filesRemoteRemove(req.configure(FilesRemoteRemoveRequest.builder()).build());
    }

    @Override
    public CompletableFuture<FilesRemoteShareResponse> filesRemoteShare(FilesRemoteShareRequest req) {
        return executor.execute(FILES_REMOTE_SHARE, toMap(req), () -> methods.filesRemoteShare(req));
    }

    @Override
    public CompletableFuture<FilesRemoteShareResponse> filesRemoteShare(RequestConfigurator<FilesRemoteShareRequest.FilesRemoteShareRequestBuilder> req) {
        return filesRemoteShare(req.configure(FilesRemoteShareRequest.builder()).build());
    }

    @Override
    public CompletableFuture<FilesRemoteUpdateResponse> filesRemoteUpdate(FilesRemoteUpdateRequest req) {
        return executor.execute(FILES_REMOTE_UPDATE, toMap(req), () -> methods.filesRemoteUpdate(req));
    }

    @Override
    public CompletableFuture<FilesRemoteUpdateResponse> filesRemoteUpdate(RequestConfigurator<FilesRemoteUpdateRequest.FilesRemoteUpdateRequestBuilder> req) {
        return filesRemoteUpdate(req.configure(FilesRemoteUpdateRequest.builder()).build());
    }

    @Override
    public CompletableFuture<MigrationExchangeResponse> migrationExchange(MigrationExchangeRequest req) {
        return executor.execute(MIGRATION_EXCHANGE, toMap(req), () -> methods.migrationExchange(req));
    }

    @Override
    public CompletableFuture<MigrationExchangeResponse> migrationExchange(RequestConfigurator<MigrationExchangeRequest.MigrationExchangeRequestBuilder> req) {
        return migrationExchange(req.configure(MigrationExchangeRequest.builder()).build());
    }

    @Override
    public CompletableFuture<OAuthAccessResponse> oauthAccess(OAuthAccessRequest req) {
        return executor.execute(OAUTH_ACCESS, toMap(req), () -> methods.oauthAccess(req));
    }

    @Override
    public CompletableFuture<OAuthAccessResponse> oauthAccess(RequestConfigurator<OAuthAccessRequest.OAuthAccessRequestBuilder> req) {
        return oauthAccess(req.configure(OAuthAccessRequest.builder()).build());
    }

    @Override
    public CompletableFuture<OAuthV2AccessResponse> oauthV2Access(OAuthV2AccessRequest req) {
        return executor.execute(OAUTH_V2_ACCESS, toMap(req), () -> methods.oauthV2Access(req));
    }

    @Override
    public CompletableFuture<OAuthV2AccessResponse> oauthV2Access(RequestConfigurator<OAuthV2AccessRequest.OAuthV2AccessRequestBuilder> req) {
        return oauthV2Access(req.configure(OAuthV2AccessRequest.builder()).build());
    }

    @Override
    public CompletableFuture<OAuthTokenResponse> oauthToken(OAuthTokenRequest req) {
        return executor.execute(OAUTH_TOKEN, toMap(req), () -> methods.oauthToken(req));
    }

    @Override
    public CompletableFuture<OAuthTokenResponse> oauthToken(RequestConfigurator<OAuthTokenRequest.OAuthTokenRequestBuilder> req) {
        return oauthToken(req.configure(OAuthTokenRequest.builder()).build());
    }

    @Override
    public CompletableFuture<PinsAddResponse> pinsAdd(PinsAddRequest req) {
        return executor.execute(PINS_ADD, toMap(req), () -> methods.pinsAdd(req));
    }

    @Override
    public CompletableFuture<PinsAddResponse> pinsAdd(RequestConfigurator<PinsAddRequest.PinsAddRequestBuilder> req) {
        return pinsAdd(req.configure(PinsAddRequest.builder()).build());
    }

    @Override
    public CompletableFuture<PinsListResponse> pinsList(PinsListRequest req) {
        return executor.execute(PINS_LIST, toMap(req), () -> methods.pinsList(req));
    }

    @Override
    public CompletableFuture<PinsListResponse> pinsList(RequestConfigurator<PinsListRequest.PinsListRequestBuilder> req) {
        return pinsList(req.configure(PinsListRequest.builder()).build());
    }

    @Override
    public CompletableFuture<PinsRemoveResponse> pinsRemove(PinsRemoveRequest req) {
        return executor.execute(PINS_REMOVE, toMap(req), () -> methods.pinsRemove(req));
    }

    @Override
    public CompletableFuture<PinsRemoveResponse> pinsRemove(RequestConfigurator<PinsRemoveRequest.PinsRemoveRequestBuilder> req) {
        return pinsRemove(req.configure(PinsRemoveRequest.builder()).build());
    }

    @Override
    public CompletableFuture<ReactionsAddResponse> reactionsAdd(ReactionsAddRequest req) {
        return executor.execute(REACTIONS_ADD, toMap(req), () -> methods.reactionsAdd(req));
    }

    @Override
    public CompletableFuture<ReactionsAddResponse> reactionsAdd(RequestConfigurator<ReactionsAddRequest.ReactionsAddRequestBuilder> req) {
        return reactionsAdd(req.configure(ReactionsAddRequest.builder()).build());
    }

    @Override
    public CompletableFuture<ReactionsGetResponse> reactionsGet(ReactionsGetRequest req) {
        return executor.execute(REACTIONS_GET, toMap(req), () -> methods.reactionsGet(req));
    }

    @Override
    public CompletableFuture<ReactionsGetResponse> reactionsGet(RequestConfigurator<ReactionsGetRequest.ReactionsGetRequestBuilder> req) {
        return reactionsGet(req.configure(ReactionsGetRequest.builder()).build());
    }

    @Override
    public CompletableFuture<ReactionsListResponse> reactionsList(ReactionsListRequest req) {
        return executor.execute(REACTIONS_LIST, toMap(req), () -> methods.reactionsList(req));
    }

    @Override
    public CompletableFuture<ReactionsListResponse> reactionsList(RequestConfigurator<ReactionsListRequest.ReactionsListRequestBuilder> req) {
        return reactionsList(req.configure(ReactionsListRequest.builder()).build());
    }

    @Override
    public CompletableFuture<ReactionsRemoveResponse> reactionsRemove(ReactionsRemoveRequest req) {
        return executor.execute(REACTIONS_REMOVE, toMap(req), () -> methods.reactionsRemove(req));
    }

    @Override
    public CompletableFuture<ReactionsRemoveResponse> reactionsRemove(RequestConfigurator<ReactionsRemoveRequest.ReactionsRemoveRequestBuilder> req) {
        return reactionsRemove(req.configure(ReactionsRemoveRequest.builder()).build());
    }

    @Override
    public CompletableFuture<RemindersAddResponse> remindersAdd(RemindersAddRequest req) {
        return executor.execute(REMINDERS_ADD, toMap(req), () -> methods.remindersAdd(req));
    }

    @Override
    public CompletableFuture<RemindersAddResponse> remindersAdd(RequestConfigurator<RemindersAddRequest.RemindersAddRequestBuilder> req) {
        return remindersAdd(req.configure(RemindersAddRequest.builder()).build());
    }

    @Override
    public CompletableFuture<RemindersCompleteResponse> remindersComplete(RemindersCompleteRequest req) {
        return executor.execute(REMINDERS_COMPLETE, toMap(req), () -> methods.remindersComplete(req));
    }

    @Override
    public CompletableFuture<RemindersCompleteResponse> remindersComplete(RequestConfigurator<RemindersCompleteRequest.RemindersCompleteRequestBuilder> req) {
        return remindersComplete(req.configure(RemindersCompleteRequest.builder()).build());
    }

    @Override
    public CompletableFuture<RemindersDeleteResponse> remindersDelete(RemindersDeleteRequest req) {
        return executor.execute(REMINDERS_DELETE, toMap(req), () -> methods.remindersDelete(req));
    }

    @Override
    public CompletableFuture<RemindersDeleteResponse> remindersDelete(RequestConfigurator<RemindersDeleteRequest.RemindersDeleteRequestBuilder> req) {
        return remindersDelete(req.configure(RemindersDeleteRequest.builder()).build());
    }

    @Override
    public CompletableFuture<RemindersInfoResponse> remindersInfo(RemindersInfoRequest req) {
        return executor.execute(REMINDERS_INFO, toMap(req), () -> methods.remindersInfo(req));
    }

    @Override
    public CompletableFuture<RemindersInfoResponse> remindersInfo(RequestConfigurator<RemindersInfoRequest.RemindersInfoRequestBuilder> req) {
        return remindersInfo(req.configure(RemindersInfoRequest.builder()).build());
    }

    @Override
    public CompletableFuture<RemindersListResponse> remindersList(RemindersListRequest req) {
        return executor.execute(REMINDERS_LIST, toMap(req), () -> methods.remindersList(req));
    }

    @Override
    public CompletableFuture<RemindersListResponse> remindersList(RequestConfigurator<RemindersListRequest.RemindersListRequestBuilder> req) {
        return remindersList(req.configure(RemindersListRequest.builder()).build());
    }

    @Override
    public CompletableFuture<RTMConnectResponse> rtmConnect(RTMConnectRequest req) {
        return executor.execute(RTM_CONNECT, toMap(req), () -> methods.rtmConnect(req));
    }

    @Override
    public CompletableFuture<RTMConnectResponse> rtmConnect(RequestConfigurator<RTMConnectRequest.RTMConnectRequestBuilder> req) {
        return rtmConnect(req.configure(RTMConnectRequest.builder()).build());
    }

    @Override
    public CompletableFuture<RTMStartResponse> rtmStart(RTMStartRequest req) {
        return executor.execute(RTM_START, toMap(req), () -> methods.rtmStart(req));
    }

    @Override
    public CompletableFuture<RTMStartResponse> rtmStart(RequestConfigurator<RTMStartRequest.RTMStartRequestBuilder> req) {
        return rtmStart(req.configure(RTMStartRequest.builder()).build());
    }

    @Override
    public CompletableFuture<SearchAllResponse> searchAll(SearchAllRequest req) {
        return executor.execute(SEARCH_ALL, toMap(req), () -> methods.searchAll(req));
    }

    @Override
    public CompletableFuture<SearchAllResponse> searchAll(RequestConfigurator<SearchAllRequest.SearchAllRequestBuilder> req) {
        return searchAll(req.configure(SearchAllRequest.builder()).build());
    }

    @Override
    public CompletableFuture<SearchMessagesResponse> searchMessages(SearchMessagesRequest req) {
        return executor.execute(SEARCH_MESSAGES, toMap(req), () -> methods.searchMessages(req));
    }

    @Override
    public CompletableFuture<SearchMessagesResponse> searchMessages(RequestConfigurator<SearchMessagesRequest.SearchMessagesRequestBuilder> req) {
        return searchMessages(req.configure(SearchMessagesRequest.builder()).build());
    }

    @Override
    public CompletableFuture<SearchFilesResponse> searchFiles(SearchFilesRequest req) {
        return executor.execute(SEARCH_FILES, toMap(req), () -> methods.searchFiles(req));
    }

    @Override
    public CompletableFuture<SearchFilesResponse> searchFiles(RequestConfigurator<SearchFilesRequest.SearchFilesRequestBuilder> req) {
        return searchFiles(req.configure(SearchFilesRequest.builder()).build());
    }

    @Override
    public CompletableFuture<StarsAddResponse> starsAdd(StarsAddRequest req) {
        return executor.execute(STARS_ADD, toMap(req), () -> methods.starsAdd(req));
    }

    @Override
    public CompletableFuture<StarsAddResponse> starsAdd(RequestConfigurator<StarsAddRequest.StarsAddRequestBuilder> req) {
        return starsAdd(req.configure(StarsAddRequest.builder()).build());
    }

    @Override
    public CompletableFuture<StarsListResponse> starsList(StarsListRequest req) {
        return executor.execute(STARS_LIST, toMap(req), () -> methods.starsList(req));
    }

    @Override
    public CompletableFuture<StarsListResponse> starsList(RequestConfigurator<StarsListRequest.StarsListRequestBuilder> req) {
        return starsList(req.configure(StarsListRequest.builder()).build());
    }

    @Override
    public CompletableFuture<StarsRemoveResponse> starsRemove(StarsRemoveRequest req) {
        return executor.execute(STARS_REMOVE, toMap(req), () -> methods.starsRemove(req));
    }

    @Override
    public CompletableFuture<StarsRemoveResponse> starsRemove(RequestConfigurator<StarsRemoveRequest.StarsRemoveRequestBuilder> req) {
        return starsRemove(req.configure(StarsRemoveRequest.builder()).build());
    }

    @Override
    public CompletableFuture<TeamAccessLogsResponse> teamAccessLogs(TeamAccessLogsRequest req) {
        return executor.execute(TEAM_ACCESS_LOGS, toMap(req), () -> methods.teamAccessLogs(req));
    }

    @Override
    public CompletableFuture<TeamAccessLogsResponse> teamAccessLogs(RequestConfigurator<TeamAccessLogsRequest.TeamAccessLogsRequestBuilder> req) {
        return teamAccessLogs(req.configure(TeamAccessLogsRequest.builder()).build());
    }

    @Override
    public CompletableFuture<TeamBillableInfoResponse> teamBillableInfo(TeamBillableInfoRequest req) {
        return executor.execute(TEAM_BILLABLE_INFO, toMap(req), () -> methods.teamBillableInfo(req));
    }

    @Override
    public CompletableFuture<TeamBillableInfoResponse> teamBillableInfo(RequestConfigurator<TeamBillableInfoRequest.TeamBillableInfoRequestBuilder> req) {
        return teamBillableInfo(req.configure(TeamBillableInfoRequest.builder()).build());
    }

    @Override
    public CompletableFuture<TeamInfoResponse> teamInfo(TeamInfoRequest req) {
        return executor.execute(TEAM_INFO, toMap(req), () -> methods.teamInfo(req));
    }

    @Override
    public CompletableFuture<TeamInfoResponse> teamInfo(RequestConfigurator<TeamInfoRequest.TeamInfoRequestBuilder> req) {
        return teamInfo(req.configure(TeamInfoRequest.builder()).build());
    }

    @Override
    public CompletableFuture<TeamIntegrationLogsResponse> teamIntegrationLogs(TeamIntegrationLogsRequest req) {
        return executor.execute(TEAM_INTEGRATION_LOGS, toMap(req), () -> methods.teamIntegrationLogs(req));
    }

    @Override
    public CompletableFuture<TeamIntegrationLogsResponse> teamIntegrationLogs(RequestConfigurator<TeamIntegrationLogsRequest.TeamIntegrationLogsRequestBuilder> req) {
        return teamIntegrationLogs(req.configure(TeamIntegrationLogsRequest.builder()).build());
    }

    @Override
    public CompletableFuture<TeamProfileGetResponse> teamProfileGet(TeamProfileGetRequest req) {
        return executor.execute(TEAM_PROFILE_GET, toMap(req), () -> methods.teamProfileGet(req));
    }

    @Override
    public CompletableFuture<TeamProfileGetResponse> teamProfileGet(RequestConfigurator<TeamProfileGetRequest.TeamProfileGetRequestBuilder> req) {
        return teamProfileGet(req.configure(TeamProfileGetRequest.builder()).build());
    }

    @Override
    public CompletableFuture<UsergroupsCreateResponse> usergroupsCreate(UsergroupsCreateRequest req) {
        return executor.execute(USERGROUPS_CREATE, toMap(req), () -> methods.usergroupsCreate(req));
    }

    @Override
    public CompletableFuture<UsergroupsCreateResponse> usergroupsCreate(RequestConfigurator<UsergroupsCreateRequest.UsergroupsCreateRequestBuilder> req) {
        return usergroupsCreate(req.configure(UsergroupsCreateRequest.builder()).build());
    }

    @Override
    public CompletableFuture<UsergroupsDisableResponse> usergroupsDisable(UsergroupsDisableRequest req) {
        return executor.execute(USERGROUPS_DISABLE, toMap(req), () -> methods.usergroupsDisable(req));
    }

    @Override
    public CompletableFuture<UsergroupsDisableResponse> usergroupsDisable(RequestConfigurator<UsergroupsDisableRequest.UsergroupsDisableRequestBuilder> req) {
        return usergroupsDisable(req.configure(UsergroupsDisableRequest.builder()).build());
    }

    @Override
    public CompletableFuture<UsergroupsEnableResponse> usergroupsEnable(UsergroupsEnableRequest req) {
        return executor.execute(USERGROUPS_ENABLE, toMap(req), () -> methods.usergroupsEnable(req));
    }

    @Override
    public CompletableFuture<UsergroupsEnableResponse> usergroupsEnable(RequestConfigurator<UsergroupsEnableRequest.UsergroupsEnableRequestBuilder> req) {
        return usergroupsEnable(req.configure(UsergroupsEnableRequest.builder()).build());
    }

    @Override
    public CompletableFuture<UsergroupsListResponse> usergroupsList(UsergroupsListRequest req) {
        return executor.execute(USERGROUPS_LIST, toMap(req), () -> methods.usergroupsList(req));
    }

    @Override
    public CompletableFuture<UsergroupsListResponse> usergroupsList(RequestConfigurator<UsergroupsListRequest.UsergroupsListRequestBuilder> req) {
        return usergroupsList(req.configure(UsergroupsListRequest.builder()).build());
    }

    @Override
    public CompletableFuture<UsergroupsUpdateResponse> usergroupsUpdate(UsergroupsUpdateRequest req) {
        return executor.execute(USERGROUPS_UPDATE, toMap(req), () -> methods.usergroupsUpdate(req));
    }

    @Override
    public CompletableFuture<UsergroupsUpdateResponse> usergroupsUpdate(RequestConfigurator<UsergroupsUpdateRequest.UsergroupsUpdateRequestBuilder> req) {
        return usergroupsUpdate(req.configure(UsergroupsUpdateRequest.builder()).build());
    }

    @Override
    public CompletableFuture<UsergroupsUsersListResponse> usergroupsUsersList(UsergroupsUsersListRequest req) {
        return executor.execute(USERGROUPS_USERS_LIST, toMap(req), () -> methods.usergroupsUsersList(req));
    }

    @Override
    public CompletableFuture<UsergroupsUsersListResponse> usergroupsUsersList(RequestConfigurator<UsergroupsUsersListRequest.UsergroupsUsersListRequestBuilder> req) {
        return usergroupsUsersList(req.configure(UsergroupsUsersListRequest.builder()).build());
    }

    @Override
    public CompletableFuture<UsergroupsUsersUpdateResponse> usergroupsUsersUpdate(UsergroupsUsersUpdateRequest req) {
        return executor.execute(USERGROUPS_USERS_UPDATE, toMap(req), () -> methods.usergroupsUsersUpdate(req));
    }

    @Override
    public CompletableFuture<UsergroupsUsersUpdateResponse> usergroupsUsersUpdate(RequestConfigurator<UsergroupsUsersUpdateRequest.UsergroupsUsersUpdateRequestBuilder> req) {
        return usergroupsUsersUpdate(req.configure(UsergroupsUsersUpdateRequest.builder()).build());
    }

    @Override
    public CompletableFuture<UsersConversationsResponse> usersConversations(UsersConversationsRequest req) {
        return executor.execute(USERS_CONVERSATIONS, toMap(req), () -> methods.usersConversations(req));
    }

    @Override
    public CompletableFuture<UsersConversationsResponse> usersConversations(RequestConfigurator<UsersConversationsRequest.UsersConversationsRequestBuilder> req) {
        return usersConversations(req.configure(UsersConversationsRequest.builder()).build());
    }

    @Override
    public CompletableFuture<UsersDeletePhotoResponse> usersDeletePhoto(UsersDeletePhotoRequest req) {
        return executor.execute(USERS_DELETE_PHOTO, toMap(req), () -> methods.usersDeletePhoto(req));
    }

    @Override
    public CompletableFuture<UsersDeletePhotoResponse> usersDeletePhoto(RequestConfigurator<UsersDeletePhotoRequest.UsersDeletePhotoRequestBuilder> req) {
        return usersDeletePhoto(req.configure(UsersDeletePhotoRequest.builder()).build());
    }

    @Override
    public CompletableFuture<UsersGetPresenceResponse> usersGetPresence(UsersGetPresenceRequest req) {
        return executor.execute(USERS_GET_PRESENCE, toMap(req), () -> methods.usersGetPresence(req));
    }

    @Override
    public CompletableFuture<UsersGetPresenceResponse> usersGetPresence(RequestConfigurator<UsersGetPresenceRequest.UsersGetPresenceRequestBuilder> req) {
        return usersGetPresence(req.configure(UsersGetPresenceRequest.builder()).build());
    }

    @Override
    public CompletableFuture<UsersIdentityResponse> usersIdentity(UsersIdentityRequest req) {
        return executor.execute(USERS_IDENTITY, toMap(req), () -> methods.usersIdentity(req));
    }

    @Override
    public CompletableFuture<UsersIdentityResponse> usersIdentity(RequestConfigurator<UsersIdentityRequest.UsersIdentityRequestBuilder> req) {
        return usersIdentity(req.configure(UsersIdentityRequest.builder()).build());
    }

    @Override
    public CompletableFuture<UsersInfoResponse> usersInfo(UsersInfoRequest req) {
        return executor.execute(USERS_INFO, toMap(req), () -> methods.usersInfo(req));
    }

    @Override
    public CompletableFuture<UsersInfoResponse> usersInfo(RequestConfigurator<UsersInfoRequest.UsersInfoRequestBuilder> req) {
        return usersInfo(req.configure(UsersInfoRequest.builder()).build());
    }

    @Override
    public CompletableFuture<UsersListResponse> usersList(UsersListRequest req) {
        return executor.execute(USERS_LIST, toMap(req), () -> methods.usersList(req));
    }

    @Override
    public CompletableFuture<UsersListResponse> usersList(RequestConfigurator<UsersListRequest.UsersListRequestBuilder> req) {
        return usersList(req.configure(UsersListRequest.builder()).build());
    }

    @Override
    public CompletableFuture<UsersLookupByEmailResponse> usersLookupByEmail(UsersLookupByEmailRequest req) {
        return executor.execute(USERS_LOOKUP_BY_EMAIL, toMap(req), () -> methods.usersLookupByEmail(req));
    }

    @Override
    public CompletableFuture<UsersLookupByEmailResponse> usersLookupByEmail(RequestConfigurator<UsersLookupByEmailRequest.UsersLookupByEmailRequestBuilder> req) {
        return usersLookupByEmail(req.configure(UsersLookupByEmailRequest.builder()).build());
    }

    @Override
    public CompletableFuture<UsersSetActiveResponse> usersSetActive(UsersSetActiveRequest req) {
        return executor.execute(USERS_SET_ACTIVE, toMap(req), () -> methods.usersSetActive(req));
    }

    @Override
    public CompletableFuture<UsersSetActiveResponse> usersSetActive(RequestConfigurator<UsersSetActiveRequest.UsersSetActiveRequestBuilder> req) {
        return usersSetActive(req.configure(UsersSetActiveRequest.builder()).build());
    }

    @Override
    public CompletableFuture<UsersSetPhotoResponse> usersSetPhoto(UsersSetPhotoRequest req) {
        return executor.execute(USERS_SET_PHOTO, toMap(req), () -> methods.usersSetPhoto(req));
    }

    @Override
    public CompletableFuture<UsersSetPhotoResponse> usersSetPhoto(RequestConfigurator<UsersSetPhotoRequest.UsersSetPhotoRequestBuilder> req) {
        return usersSetPhoto(req.configure(UsersSetPhotoRequest.builder()).build());
    }

    @Override
    public CompletableFuture<UsersSetPresenceResponse> usersSetPresence(UsersSetPresenceRequest req) {
        return executor.execute(USERS_SET_PRESENCE, toMap(req), () -> methods.usersSetPresence(req));
    }

    @Override
    public CompletableFuture<UsersSetPresenceResponse> usersSetPresence(RequestConfigurator<UsersSetPresenceRequest.UsersSetPresenceRequestBuilder> req) {
        return usersSetPresence(req.configure(UsersSetPresenceRequest.builder()).build());
    }

    @Override
    public CompletableFuture<UsersProfileGetResponse> usersProfileGet(UsersProfileGetRequest req) {
        return executor.execute(USERS_PROFILE_GET, toMap(req), () -> methods.usersProfileGet(req));
    }

    @Override
    public CompletableFuture<UsersProfileGetResponse> usersProfileGet(RequestConfigurator<UsersProfileGetRequest.UsersProfileGetRequestBuilder> req) {
        return usersProfileGet(req.configure(UsersProfileGetRequest.builder()).build());
    }

    @Override
    public CompletableFuture<UsersProfileSetResponse> usersProfileSet(UsersProfileSetRequest req) {
        return executor.execute(USERS_PROFILE_SET, toMap(req), () -> methods.usersProfileSet(req));
    }

    @Override
    public CompletableFuture<UsersProfileSetResponse> usersProfileSet(RequestConfigurator<UsersProfileSetRequest.UsersProfileSetRequestBuilder> req) {
        return usersProfileSet(req.configure(UsersProfileSetRequest.builder()).build());
    }

    @Override
    public CompletableFuture<ViewsOpenResponse> viewsOpen(ViewsOpenRequest req) {
        return executor.execute(VIEWS_OPEN, toMap(req), () -> methods.viewsOpen(req));
    }

    @Override
    public CompletableFuture<ViewsOpenResponse> viewsOpen(RequestConfigurator<ViewsOpenRequest.ViewsOpenRequestBuilder> req) {
        return viewsOpen(req.configure(ViewsOpenRequest.builder()).build());
    }

    @Override
    public CompletableFuture<ViewsPushResponse> viewsPush(ViewsPushRequest req) {
        return executor.execute(VIEWS_PUSH, toMap(req), () -> methods.viewsPush(req));
    }

    @Override
    public CompletableFuture<ViewsPushResponse> viewsPush(RequestConfigurator<ViewsPushRequest.ViewsPushRequestBuilder> req) {
        return viewsPush(req.configure(ViewsPushRequest.builder()).build());
    }

    @Override
    public CompletableFuture<ViewsUpdateResponse> viewsUpdate(ViewsUpdateRequest req) {
        return executor.execute(VIEWS_UPDATE, toMap(req), () -> methods.viewsUpdate(req));
    }

    @Override
    public CompletableFuture<ViewsUpdateResponse> viewsUpdate(RequestConfigurator<ViewsUpdateRequest.ViewsUpdateRequestBuilder> req) {
        return viewsUpdate(req.configure(ViewsUpdateRequest.builder()).build());
    }

    @Override
    public CompletableFuture<ViewsPublishResponse> viewsPublish(ViewsPublishRequest req) {
        return executor.execute(VIEWS_PUBLISH, toMap(req), () -> methods.viewsPublish(req));
    }

    @Override
    public CompletableFuture<ViewsPublishResponse> viewsPublish(RequestConfigurator<ViewsPublishRequest.ViewsPublishRequestBuilder> req) {
        return viewsPublish(req.configure(ViewsPublishRequest.builder()).build());
    }

}