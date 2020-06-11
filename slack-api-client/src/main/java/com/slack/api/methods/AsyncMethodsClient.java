package com.slack.api.methods;

import com.slack.api.RequestConfigurator;
import com.slack.api.methods.request.admin.apps.*;
import com.slack.api.methods.request.admin.conversations.AdminConversationsSetTeamsRequest;
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
import com.slack.api.methods.request.admin.usergroups.AdminUsergroupsListChannelsRequest;
import com.slack.api.methods.request.admin.usergroups.AdminUsergroupsRemoveChannelsRequest;
import com.slack.api.methods.request.admin.users.*;
import com.slack.api.methods.request.api.ApiTestRequest;
import com.slack.api.methods.request.apps.AppsUninstallRequest;
import com.slack.api.methods.request.auth.AuthRevokeRequest;
import com.slack.api.methods.request.auth.AuthTestRequest;
import com.slack.api.methods.request.bots.BotsInfoRequest;
import com.slack.api.methods.request.calls.CallsAddRequest;
import com.slack.api.methods.request.calls.CallsEndRequest;
import com.slack.api.methods.request.calls.CallsInfoRequest;
import com.slack.api.methods.request.calls.CallsUpdateRequest;
import com.slack.api.methods.request.calls.participants.CallsParticipantsAddRequest;
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
import com.slack.api.methods.response.admin.conversations.whitelist.AdminConversationsWhitelistAddResponse;
import com.slack.api.methods.response.admin.conversations.whitelist.AdminConversationsWhitelistListGroupsLinkedToChannelResponse;
import com.slack.api.methods.response.admin.conversations.whitelist.AdminConversationsWhitelistRemoveResponse;
import com.slack.api.methods.response.admin.emoji.*;
import com.slack.api.methods.response.admin.invite_requests.*;
import com.slack.api.methods.response.admin.teams.AdminTeamsAdminsListResponse;
import com.slack.api.methods.response.admin.teams.AdminTeamsCreateResponse;
import com.slack.api.methods.response.admin.teams.AdminTeamsListResponse;
import com.slack.api.methods.response.admin.teams.owners.AdminTeamsOwnersListResponse;
import com.slack.api.methods.response.admin.teams.settings.*;
import com.slack.api.methods.response.admin.usergroups.AdminUsergroupsAddChannelsResponse;
import com.slack.api.methods.response.admin.usergroups.AdminUsergroupsListChannelsResponse;
import com.slack.api.methods.response.admin.usergroups.AdminUsergroupsRemoveChannelsResponse;
import com.slack.api.methods.response.admin.users.*;
import com.slack.api.methods.response.api.ApiTestResponse;
import com.slack.api.methods.response.apps.AppsUninstallResponse;
import com.slack.api.methods.response.auth.AuthRevokeResponse;
import com.slack.api.methods.response.auth.AuthTestResponse;
import com.slack.api.methods.response.bots.BotsInfoResponse;
import com.slack.api.methods.response.calls.CallsAddResponse;
import com.slack.api.methods.response.calls.CallsEndResponse;
import com.slack.api.methods.response.calls.CallsInfoResponse;
import com.slack.api.methods.response.calls.CallsUpdateResponse;
import com.slack.api.methods.response.calls.participants.CallsParticipantsAddResponse;
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

import java.util.concurrent.CompletableFuture;

/**
 * API Methods.
 * https://api.slack.com/methods
 */
public interface AsyncMethodsClient {

    MethodsClient underlying();

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

    // ------------------------------
    // admin.apps.requests
    // ------------------------------

    CompletableFuture<AdminAppsRequestsListResponse> adminAppsRequestsList(AdminAppsRequestsListRequest req);

    CompletableFuture<AdminAppsRequestsListResponse> adminAppsRequestsList(RequestConfigurator<AdminAppsRequestsListRequest.AdminAppsRequestsListRequestBuilder> req);

    // ------------------------------
    // admin.conversations
    // ------------------------------

    CompletableFuture<AdminConversationsSetTeamsResponse> adminConversationsSetTeams(AdminConversationsSetTeamsRequest req);

    CompletableFuture<AdminConversationsSetTeamsResponse> adminConversationsSetTeams(RequestConfigurator<AdminConversationsSetTeamsRequest.AdminConversationsSetTeamsRequestBuilder> req);

    // ------------------------------
    // admin.conversations.whitelist
    // ------------------------------

    CompletableFuture<AdminConversationsWhitelistAddResponse> adminConversationsWhitelistAdd(
            AdminConversationsWhitelistAddRequest req);

    CompletableFuture<AdminConversationsWhitelistAddResponse> adminConversationsWhitelistAdd(
            RequestConfigurator<AdminConversationsWhitelistAddRequest.AdminConversationsWhitelistAddRequestBuilder> req);

    CompletableFuture<AdminConversationsWhitelistRemoveResponse> adminConversationsWhitelistRemove(
            AdminConversationsWhitelistRemoveRequest req);

    CompletableFuture<AdminConversationsWhitelistRemoveResponse> adminConversationsWhitelistRemove(
            RequestConfigurator<AdminConversationsWhitelistRemoveRequest.AdminConversationsWhitelistRemoveRequestBuilder> req);

    CompletableFuture<AdminConversationsWhitelistListGroupsLinkedToChannelResponse> adminConversationsWhitelistListGroupsLinkedToChannel(
            AdminConversationsWhitelistListGroupsLinkedToChannelRequest req);

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

    CompletableFuture<AdminUsersSessionResetResponse> adminUsersSessionReset(AdminUsersSessionResetRequest req);

    CompletableFuture<AdminUsersSessionResetResponse> adminUsersSessionReset(RequestConfigurator<AdminUsersSessionResetRequest.AdminUsersSessionResetRequestBuilder> req);

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
    // auth
    // ------------------------------

    CompletableFuture<AuthRevokeResponse> authRevoke(AuthRevokeRequest req);

    CompletableFuture<AuthRevokeResponse> authRevoke(RequestConfigurator<AuthRevokeRequest.AuthRevokeRequestBuilder> req);

    CompletableFuture<AuthTestResponse> authTest(AuthTestRequest req);

    CompletableFuture<AuthTestResponse> authTest(RequestConfigurator<AuthTestRequest.AuthTestRequestBuilder> req);

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

    CompletableFuture<FilesUploadResponse> filesUpload(FilesUploadRequest req);

    CompletableFuture<FilesUploadResponse> filesUpload(RequestConfigurator<FilesUploadRequest.FilesUploadRequestBuilder> req);

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

    CompletableFuture<OAuthTokenResponse> oauthToken(OAuthTokenRequest req);

    CompletableFuture<OAuthTokenResponse> oauthToken(RequestConfigurator<OAuthTokenRequest.OAuthTokenRequestBuilder> req);

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

    CompletableFuture<RTMConnectResponse> rtmConnect(RTMConnectRequest req);

    CompletableFuture<RTMConnectResponse> rtmConnect(RequestConfigurator<RTMConnectRequest.RTMConnectRequestBuilder> req);

    CompletableFuture<RTMStartResponse> rtmStart(RTMStartRequest req);

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

    CompletableFuture<StarsAddResponse> starsAdd(StarsAddRequest req);

    CompletableFuture<StarsAddResponse> starsAdd(RequestConfigurator<StarsAddRequest.StarsAddRequestBuilder> req);

    CompletableFuture<StarsListResponse> starsList(StarsListRequest req);

    CompletableFuture<StarsListResponse> starsList(RequestConfigurator<StarsListRequest.StarsListRequestBuilder> req);

    CompletableFuture<StarsRemoveResponse> starsRemove(StarsRemoveRequest req);

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

}
