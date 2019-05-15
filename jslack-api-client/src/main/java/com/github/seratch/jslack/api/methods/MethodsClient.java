package com.github.seratch.jslack.api.methods;

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
import com.github.seratch.jslack.api.methods.response.api.ApiTestResponse;
import com.github.seratch.jslack.api.methods.response.apps.AppsUninstallResponse;
import com.github.seratch.jslack.api.methods.response.apps.permissions.AppsPermissionsInfoResponse;
import com.github.seratch.jslack.api.methods.response.apps.permissions.AppsPermissionsRequestResponse;
import com.github.seratch.jslack.api.methods.response.apps.permissions.resources.AppsPermissionsResourcesListResponse;
import com.github.seratch.jslack.api.methods.response.apps.permissions.scopes.AppsPermissionsScopesListResponse;
import com.github.seratch.jslack.api.methods.response.apps.permissions.users.AppsPermissionsUsersListResponse;
import com.github.seratch.jslack.api.methods.response.apps.permissions.users.AppsPermissionsUsersRequestResponse;
import com.github.seratch.jslack.api.methods.response.auth.AuthRevokeResponse;
import com.github.seratch.jslack.api.methods.response.auth.AuthTestResponse;
import com.github.seratch.jslack.api.methods.response.bots.BotsInfoResponse;
import com.github.seratch.jslack.api.methods.response.channels.*;
import com.github.seratch.jslack.api.methods.response.chat.*;
import com.github.seratch.jslack.api.methods.response.chat.scheduled_messages.ChatScheduleMessagesListResponse;
import com.github.seratch.jslack.api.methods.response.conversations.*;
import com.github.seratch.jslack.api.methods.response.dialog.DialogOpenResponse;
import com.github.seratch.jslack.api.methods.response.dnd.*;
import com.github.seratch.jslack.api.methods.response.emoji.EmojiListResponse;
import com.github.seratch.jslack.api.methods.response.files.*;
import com.github.seratch.jslack.api.methods.response.files.comments.FilesCommentsAddResponse;
import com.github.seratch.jslack.api.methods.response.files.comments.FilesCommentsDeleteResponse;
import com.github.seratch.jslack.api.methods.response.files.comments.FilesCommentsEditResponse;
import com.github.seratch.jslack.api.methods.response.groups.*;
import com.github.seratch.jslack.api.methods.response.im.*;
import com.github.seratch.jslack.api.methods.response.migration.MigrationExchangeResponse;
import com.github.seratch.jslack.api.methods.response.mpim.*;
import com.github.seratch.jslack.api.methods.response.oauth.OAuthAccessResponse;
import com.github.seratch.jslack.api.methods.response.oauth.OAuthTokenResponse;
import com.github.seratch.jslack.api.methods.response.pins.PinsAddResponse;
import com.github.seratch.jslack.api.methods.response.pins.PinsListResponse;
import com.github.seratch.jslack.api.methods.response.pins.PinsRemoveResponse;
import com.github.seratch.jslack.api.methods.response.reactions.ReactionsAddResponse;
import com.github.seratch.jslack.api.methods.response.reactions.ReactionsGetResponse;
import com.github.seratch.jslack.api.methods.response.reactions.ReactionsListResponse;
import com.github.seratch.jslack.api.methods.response.reactions.ReactionsRemoveResponse;
import com.github.seratch.jslack.api.methods.response.reminders.*;
import com.github.seratch.jslack.api.methods.response.rtm.RTMConnectResponse;
import com.github.seratch.jslack.api.methods.response.rtm.RTMStartResponse;
import com.github.seratch.jslack.api.methods.response.search.SearchAllResponse;
import com.github.seratch.jslack.api.methods.response.search.SearchFilesResponse;
import com.github.seratch.jslack.api.methods.response.search.SearchMessagesResponse;
import com.github.seratch.jslack.api.methods.response.stars.StarsAddResponse;
import com.github.seratch.jslack.api.methods.response.stars.StarsListResponse;
import com.github.seratch.jslack.api.methods.response.stars.StarsRemoveResponse;
import com.github.seratch.jslack.api.methods.response.team.TeamAccessLogsResponse;
import com.github.seratch.jslack.api.methods.response.team.TeamBillableInfoResponse;
import com.github.seratch.jslack.api.methods.response.team.TeamInfoResponse;
import com.github.seratch.jslack.api.methods.response.team.TeamIntegrationLogsResponse;
import com.github.seratch.jslack.api.methods.response.team.profile.TeamProfileGetResponse;
import com.github.seratch.jslack.api.methods.response.usergroups.*;
import com.github.seratch.jslack.api.methods.response.usergroups.users.UsergroupUsersListResponse;
import com.github.seratch.jslack.api.methods.response.usergroups.users.UsergroupUsersUpdateResponse;
import com.github.seratch.jslack.api.methods.response.users.*;
import com.github.seratch.jslack.api.methods.response.users.profile.UsersProfileGetResponse;
import com.github.seratch.jslack.api.methods.response.users.profile.UsersProfileSetResponse;
import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.Response;

import java.io.IOException;

/**
 * API Methods.
 * https://api.slack.com/methods
 */
public interface MethodsClient {

    void setEndpointUrlPrefix(String endpointUrlPrefix);

    Response runPostForm(
            FormBody.Builder form,
            String endpoint) throws IOException;

    Response runPostFormWithToken(
            FormBody.Builder form,
            String endpoint,
            String token) throws IOException;

    Response runPostMultipart(
            MultipartBody.Builder form,
            String endpoint,
            String token) throws IOException;

    // ------------------------------
    // api
    // ------------------------------

    ApiTestResponse apiTest(ApiTestRequest req) throws IOException, SlackApiException;

    ApiTestResponse apiTest(RequestBuilder<ApiTestRequest, ApiTestRequest.ApiTestRequestBuilder> req) throws IOException, SlackApiException;

    // ------------------------------
    // apps
    // ------------------------------

    AppsUninstallResponse appsUninstall(AppsUninstallRequest req) throws IOException, SlackApiException;

    AppsUninstallResponse appsUninstall(RequestBuilder<AppsUninstallRequest, AppsUninstallRequest.AppsUninstallRequestBuilder> req) throws IOException, SlackApiException;

    // ------------------------------
    // apps.permissions
    // ------------------------------

    AppsPermissionsInfoResponse appsPermissionsInfo(AppsPermissionsInfoRequest req) throws IOException, SlackApiException;

    AppsPermissionsInfoResponse appsPermissionsInfo(RequestBuilder<AppsPermissionsInfoRequest, AppsPermissionsInfoRequest.AppsPermissionsInfoRequestBuilder> req) throws IOException, SlackApiException;

    AppsPermissionsRequestResponse appsPermissionsRequest(AppsPermissionsRequestRequest req) throws IOException, SlackApiException;

    AppsPermissionsRequestResponse appsPermissionsRequest(RequestBuilder<AppsPermissionsRequestRequest, AppsPermissionsRequestRequest.AppsPermissionsRequestRequestBuilder> req) throws IOException, SlackApiException;

    // ------------------------------
    // apps.permissions.resources
    // ------------------------------

    // Developer preview has ended
    // This feature was exclusive to our workspace apps developer preview.
    // The preview has now ended, but fan-favorite features such as token rotation
    // and the Conversations API will become available to classic Slack apps over the coming months.
    @Deprecated
    AppsPermissionsResourcesListResponse appsPermissionsResourcesList(AppsPermissionsResourcesListRequest req) throws IOException, SlackApiException;

    // ------------------------------
    // apps.permissions.scopes
    // ------------------------------

    // Developer preview has ended
    // This feature was exclusive to our workspace apps developer preview.
    // The preview has now ended, but fan-favorite features such as token rotation
    // and the Conversations API will become available to classic Slack apps over the coming months.
    @Deprecated
    AppsPermissionsScopesListResponse appsPermissionsScopesList(AppsPermissionsScopesListRequest req) throws IOException, SlackApiException;

    // ------------------------------
    // apps.permissions.users
    // ------------------------------

    // Developer preview has ended
    // This feature was exclusive to our workspace apps developer preview.
    // The preview has now ended, but fan-favorite features such as token rotation
    // and the Conversations API will become available to classic Slack apps over the coming months.
    @Deprecated
    AppsPermissionsUsersListResponse appsPermissionsUsersList(AppsPermissionsUsersListRequest req) throws IOException, SlackApiException;

    // Developer preview has ended
    // This feature was exclusive to our workspace apps developer preview.
    // The preview has now ended, but fan-favorite features such as token rotation
    // and the Conversations API will become available to classic Slack apps over the coming months.
    @Deprecated
    AppsPermissionsUsersRequestResponse appsPermissionsUsersRequest(AppsPermissionsUsersRequestRequest req) throws IOException, SlackApiException;

    // ------------------------------
    // auth
    // ------------------------------

    AuthRevokeResponse authRevoke(AuthRevokeRequest req) throws IOException, SlackApiException;

    AuthRevokeResponse authRevoke(RequestBuilder<AuthRevokeRequest, AuthRevokeRequest.AuthRevokeRequestBuilder> req) throws IOException, SlackApiException;

    AuthTestResponse authTest(AuthTestRequest req) throws IOException, SlackApiException;

    AuthTestResponse authTest(RequestBuilder<AuthTestRequest, AuthTestRequest.AuthTestRequestBuilder> req) throws IOException, SlackApiException;

    // ------------------------------
    // bots
    // ------------------------------

    BotsInfoResponse botsInfo(BotsInfoRequest req) throws IOException, SlackApiException;

    BotsInfoResponse botsInfo(RequestBuilder<BotsInfoRequest, BotsInfoRequest.BotsInfoRequestBuilder> req) throws IOException, SlackApiException;

    // ------------------------------
    // channels
    // ------------------------------

    ChannelsArchiveResponse channelsArchive(ChannelsArchiveRequest req) throws IOException, SlackApiException;

    ChannelsArchiveResponse channelsArchive(RequestBuilder<ChannelsArchiveRequest, ChannelsArchiveRequest.ChannelsArchiveRequestBuilder> req) throws IOException, SlackApiException;

    ChannelsCreateResponse channelsCreate(ChannelsCreateRequest req) throws IOException, SlackApiException;

    ChannelsCreateResponse channelsCreate(RequestBuilder<ChannelsCreateRequest, ChannelsCreateRequest.ChannelsCreateRequestBuilder> req) throws IOException, SlackApiException;

    ChannelsHistoryResponse channelsHistory(ChannelsHistoryRequest req) throws IOException, SlackApiException;

    ChannelsHistoryResponse channelsHistory(RequestBuilder<ChannelsHistoryRequest, ChannelsHistoryRequest.ChannelsHistoryRequestBuilder> req) throws IOException, SlackApiException;

    ChannelsRepliesResponse channelsReplies(ChannelsRepliesRequest req) throws IOException, SlackApiException;

    ChannelsRepliesResponse channelsReplies(RequestBuilder<ChannelsRepliesRequest, ChannelsRepliesRequest.ChannelsRepliesRequestBuilder> req) throws IOException, SlackApiException;

    ChannelsInfoResponse channelsInfo(ChannelsInfoRequest req) throws IOException, SlackApiException;

    ChannelsInfoResponse channelsInfo(RequestBuilder<ChannelsInfoRequest, ChannelsInfoRequest.ChannelsInfoRequestBuilder> req) throws IOException, SlackApiException;

    ChannelsListResponse channelsList(ChannelsListRequest req) throws IOException, SlackApiException;

    ChannelsListResponse channelsList(RequestBuilder<ChannelsListRequest, ChannelsListRequest.ChannelsListRequestBuilder> req) throws IOException, SlackApiException;

    ChannelsInviteResponse channelsInvite(ChannelsInviteRequest req) throws IOException, SlackApiException;

    ChannelsInviteResponse channelsInvite(RequestBuilder<ChannelsInviteRequest, ChannelsInviteRequest.ChannelsInviteRequestBuilder> req) throws IOException, SlackApiException;

    ChannelsJoinResponse channelsJoin(ChannelsJoinRequest req) throws IOException, SlackApiException;

    ChannelsJoinResponse channelsJoin(RequestBuilder<ChannelsJoinRequest, ChannelsJoinRequest.ChannelsJoinRequestBuilder> req) throws IOException, SlackApiException;

    ChannelsKickResponse channelsKick(ChannelsKickRequest req) throws IOException, SlackApiException;

    ChannelsKickResponse channelsKick(RequestBuilder<ChannelsKickRequest, ChannelsKickRequest.ChannelsKickRequestBuilder> req) throws IOException, SlackApiException;

    ChannelsLeaveResponse channelsLeave(ChannelsLeaveRequest req) throws IOException, SlackApiException;

    ChannelsLeaveResponse channelsLeave(RequestBuilder<ChannelsLeaveRequest, ChannelsLeaveRequest.ChannelsLeaveRequestBuilder> req) throws IOException, SlackApiException;

    ChannelsMarkResponse channelsMark(ChannelsMarkRequest req) throws IOException, SlackApiException;

    ChannelsMarkResponse channelsMark(RequestBuilder<ChannelsMarkRequest, ChannelsMarkRequest.ChannelsMarkRequestBuilder> req) throws IOException, SlackApiException;

    ChannelsRenameResponse channelsRename(ChannelsRenameRequest req) throws IOException, SlackApiException;

    ChannelsRenameResponse channelsRename(RequestBuilder<ChannelsRenameRequest, ChannelsRenameRequest.ChannelsRenameRequestBuilder> req) throws IOException, SlackApiException;

    ChannelsSetPurposeResponse channelsSetPurpose(ChannelsSetPurposeRequest req) throws IOException, SlackApiException;

    ChannelsSetPurposeResponse channelsSetPurpose(RequestBuilder<ChannelsSetPurposeRequest, ChannelsSetPurposeRequest.ChannelsSetPurposeRequestBuilder> req) throws IOException, SlackApiException;

    ChannelsSetTopicResponse channelsSetTopic(ChannelsSetTopicRequest req) throws IOException, SlackApiException;

    ChannelsSetTopicResponse channelsSetTopic(RequestBuilder<ChannelsSetTopicRequest, ChannelsSetTopicRequest.ChannelsSetTopicRequestBuilder> req) throws IOException, SlackApiException;

    ChannelsUnarchiveResponse channelsUnarchive(ChannelsUnarchiveRequest req) throws IOException, SlackApiException;

    ChannelsUnarchiveResponse channelsUnarchive(RequestBuilder<ChannelsUnarchiveRequest, ChannelsUnarchiveRequest.ChannelsUnarchiveRequestBuilder> req) throws IOException, SlackApiException;

    // ------------------------------
    // chat
    // ------------------------------

    ChatGetPermalinkResponse chatGetPermalink(ChatGetPermalinkRequest req) throws IOException, SlackApiException;

    ChatGetPermalinkResponse chatGetPermalink(RequestBuilder<ChatGetPermalinkRequest, ChatGetPermalinkRequest.ChatGetPermalinkRequestBuilder> req) throws IOException, SlackApiException;

    ChatDeleteResponse chatDelete(ChatDeleteRequest req) throws IOException, SlackApiException;

    ChatDeleteResponse chatDelete(RequestBuilder<ChatDeleteRequest, ChatDeleteRequest.ChatDeleteRequestBuilder> req) throws IOException, SlackApiException;

    ChatDeleteScheduledMessageResponse chatDeleteScheduledMessage(ChatDeleteScheduledMessageRequest req) throws IOException, SlackApiException;

    ChatDeleteScheduledMessageResponse chatDeleteScheduledMessage(RequestBuilder<ChatDeleteScheduledMessageRequest, ChatDeleteScheduledMessageRequest.ChatDeleteScheduledMessageRequestBuilder> req) throws IOException, SlackApiException;

    ChatMeMessageResponse chatMeMessage(ChatMeMessageRequest req) throws IOException, SlackApiException;

    ChatMeMessageResponse chatMeMessage(RequestBuilder<ChatMeMessageRequest, ChatMeMessageRequest.ChatMeMessageRequestBuilder> req) throws IOException, SlackApiException;

    ChatPostEphemeralResponse chatPostEphemeral(ChatPostEphemeralRequest req) throws IOException, SlackApiException;

    ChatPostEphemeralResponse chatPostEphemeral(RequestBuilder<ChatPostEphemeralRequest, ChatPostEphemeralRequest.ChatPostEphemeralRequestBuilder> req) throws IOException, SlackApiException;

    ChatPostMessageResponse chatPostMessage(ChatPostMessageRequest req) throws IOException, SlackApiException;

    ChatPostMessageResponse chatPostMessage(RequestBuilder<ChatPostMessageRequest, ChatPostMessageRequest.ChatPostMessageRequestBuilder> req) throws IOException, SlackApiException;

    ChatScheduleMessageResponse chatScheduleMessage(ChatScheduleMessageRequest req) throws IOException, SlackApiException;

    ChatScheduleMessageResponse chatScheduleMessage(RequestBuilder<ChatScheduleMessageRequest, ChatScheduleMessageRequest.ChatScheduleMessageRequestBuilder> req) throws IOException, SlackApiException;

    ChatUpdateResponse chatUpdate(ChatUpdateRequest req) throws IOException, SlackApiException;

    ChatUpdateResponse chatUpdate(RequestBuilder<ChatUpdateRequest, ChatUpdateRequest.ChatUpdateRequestBuilder> req) throws IOException, SlackApiException;

    ChatUnfurlResponse chatUnfurl(ChatUnfurlRequest req) throws IOException, SlackApiException;

    ChatUnfurlResponse chatUnfurl(RequestBuilder<ChatUnfurlRequest, ChatUnfurlRequest.ChatUnfurlRequestBuilder> req) throws IOException, SlackApiException;

    // ------------------------------
    // chat.scheduledMessages
    // ------------------------------

    ChatScheduleMessagesListResponse chatScheduleMessagesListMessage(ChatScheduleMessagesListRequest req) throws IOException, SlackApiException;

    ChatScheduleMessagesListResponse chatScheduleMessagesListMessage(RequestBuilder<ChatScheduleMessagesListRequest, ChatScheduleMessagesListRequest.ChatScheduleMessagesListRequestBuilder> req) throws IOException, SlackApiException;

    // ------------------------------
    // conversations
    // ------------------------------

    ConversationsArchiveResponse conversationsArchive(ConversationsArchiveRequest req) throws IOException, SlackApiException;

    ConversationsArchiveResponse conversationsArchive(RequestBuilder<ConversationsArchiveRequest, ConversationsArchiveRequest.ConversationsArchiveRequestBuilder> req) throws IOException, SlackApiException;

    ConversationsCloseResponse conversationsClose(ConversationsCloseRequest req) throws IOException, SlackApiException;

    ConversationsCloseResponse conversationsClose(RequestBuilder<ConversationsCloseRequest, ConversationsCloseRequest.ConversationsCloseRequestBuilder> req) throws IOException, SlackApiException;

    ConversationsCreateResponse conversationsCreate(ConversationsCreateRequest req) throws IOException, SlackApiException;

    ConversationsCreateResponse conversationsCreate(RequestBuilder<ConversationsCreateRequest, ConversationsCreateRequest.ConversationsCreateRequestBuilder> req) throws IOException, SlackApiException;

    ConversationsHistoryResponse conversationsHistory(ConversationsHistoryRequest req) throws IOException, SlackApiException;

    ConversationsHistoryResponse conversationsHistory(RequestBuilder<ConversationsHistoryRequest, ConversationsHistoryRequest.ConversationsHistoryRequestBuilder> req) throws IOException, SlackApiException;

    ConversationsInfoResponse conversationsInfo(ConversationsInfoRequest req) throws IOException, SlackApiException;

    ConversationsInfoResponse conversationsInfo(RequestBuilder<ConversationsInfoRequest, ConversationsInfoRequest.ConversationsInfoRequestBuilder> req) throws IOException, SlackApiException;

    ConversationsInviteResponse conversationsInvite(ConversationsInviteRequest req) throws IOException, SlackApiException;

    ConversationsInviteResponse conversationsInvite(RequestBuilder<ConversationsInviteRequest, ConversationsInviteRequest.ConversationsInviteRequestBuilder> req) throws IOException, SlackApiException;

    ConversationsJoinResponse conversationsJoin(ConversationsJoinRequest req) throws IOException, SlackApiException;

    ConversationsJoinResponse conversationsJoin(RequestBuilder<ConversationsJoinRequest, ConversationsJoinRequest.ConversationsJoinRequestBuilder> req) throws IOException, SlackApiException;

    ConversationsKickResponse conversationsKick(ConversationsKickRequest req) throws IOException, SlackApiException;

    ConversationsKickResponse conversationsKick(RequestBuilder<ConversationsKickRequest, ConversationsKickRequest.ConversationsKickRequestBuilder> req) throws IOException, SlackApiException;

    ConversationsLeaveResponse conversationsLeave(ConversationsLeaveRequest req) throws IOException, SlackApiException;

    ConversationsLeaveResponse conversationsLeave(RequestBuilder<ConversationsLeaveRequest, ConversationsLeaveRequest.ConversationsLeaveRequestBuilder> req) throws IOException, SlackApiException;

    ConversationsListResponse conversationsList(ConversationsListRequest req) throws IOException, SlackApiException;

    ConversationsListResponse conversationsList(RequestBuilder<ConversationsListRequest, ConversationsListRequest.ConversationsListRequestBuilder> req) throws IOException, SlackApiException;

    ConversationsMembersResponse conversationsMembers(ConversationsMembersRequest req) throws IOException, SlackApiException;

    ConversationsMembersResponse conversationsMembers(RequestBuilder<ConversationsMembersRequest, ConversationsMembersRequest.ConversationsMembersRequestBuilder> req) throws IOException, SlackApiException;

    ConversationsOpenResponse conversationsOpen(ConversationsOpenRequest req) throws IOException, SlackApiException;

    ConversationsOpenResponse conversationsOpen(RequestBuilder<ConversationsOpenRequest, ConversationsOpenRequest.ConversationsOpenRequestBuilder> req) throws IOException, SlackApiException;

    ConversationsRenameResponse conversationsRename(ConversationsRenameRequest req) throws IOException, SlackApiException;

    ConversationsRenameResponse conversationsRename(RequestBuilder<ConversationsRenameRequest, ConversationsRenameRequest.ConversationsRenameRequestBuilder> req) throws IOException, SlackApiException;

    ConversationsRepliesResponse conversationsReplies(ConversationsRepliesRequest req) throws IOException, SlackApiException;

    ConversationsRepliesResponse conversationsReplies(RequestBuilder<ConversationsRepliesRequest, ConversationsRepliesRequest.ConversationsRepliesRequestBuilder> req) throws IOException, SlackApiException;

    ConversationsSetPurposeResponse conversationsSetPurpose(ConversationsSetPurposeRequest req) throws IOException, SlackApiException;

    ConversationsSetPurposeResponse conversationsSetPurpose(RequestBuilder<ConversationsSetPurposeRequest, ConversationsSetPurposeRequest.ConversationsSetPurposeRequestBuilder> req) throws IOException, SlackApiException;

    ConversationsSetTopicResponse conversationsSetTopic(ConversationsSetTopicRequest req) throws IOException, SlackApiException;

    ConversationsSetTopicResponse conversationsSetTopic(RequestBuilder<ConversationsSetTopicRequest, ConversationsSetTopicRequest.ConversationsSetTopicRequestBuilder> req) throws IOException, SlackApiException;

    ConversationsUnarchiveResponse conversationsUnarchive(ConversationsUnarchiveRequest req) throws IOException, SlackApiException;

    ConversationsUnarchiveResponse conversationsUnarchive(RequestBuilder<ConversationsUnarchiveRequest, ConversationsUnarchiveRequest.ConversationsUnarchiveRequestBuilder> req) throws IOException, SlackApiException;

    // ------------------------------
    // dialog
    // ------------------------------

    DialogOpenResponse dialogOpen(DialogOpenRequest req) throws IOException, SlackApiException;

    DialogOpenResponse dialogOpen(RequestBuilder<DialogOpenRequest, DialogOpenRequest.DialogOpenRequestBuilder> req) throws IOException, SlackApiException;

    // ------------------------------
    // dnd
    // ------------------------------

    DndEndDndResponse dndEndDnd(DndEndDndRequest req) throws IOException, SlackApiException;

    DndEndDndResponse dndEndDnd(RequestBuilder<DndEndDndRequest, DndEndDndRequest.DndEndDndRequestBuilder> req) throws IOException, SlackApiException;

    DndEndSnoozeResponse dndEndSnooze(DndEndSnoozeRequest req) throws IOException, SlackApiException;

    DndEndSnoozeResponse dndEndSnooze(RequestBuilder<DndEndSnoozeRequest, DndEndSnoozeRequest.DndEndSnoozeRequestBuilder> req) throws IOException, SlackApiException;

    DndInfoResponse dndInfo(DndInfoRequest req) throws IOException, SlackApiException;

    DndInfoResponse dndInfo(RequestBuilder<DndInfoRequest, DndInfoRequest.DndInfoRequestBuilder> req) throws IOException, SlackApiException;

    DndSetSnoozeResponse dndSetSnooze(DndSetSnoozeRequest req) throws IOException, SlackApiException;

    DndSetSnoozeResponse dndSetSnooze(RequestBuilder<DndSetSnoozeRequest, DndSetSnoozeRequest.DndSetSnoozeRequestBuilder> req) throws IOException, SlackApiException;

    DndTeamInfoResponse dndTeamInfo(DndTeamInfoRequest req) throws IOException, SlackApiException;

    DndTeamInfoResponse dndTeamInfo(RequestBuilder<DndTeamInfoRequest, DndTeamInfoRequest.DndTeamInfoRequestBuilder> req) throws IOException, SlackApiException;

    // ------------------------------
    // emoji
    // ------------------------------

    EmojiListResponse emojiList(EmojiListRequest req) throws IOException, SlackApiException;

    EmojiListResponse emojiList(RequestBuilder<EmojiListRequest, EmojiListRequest.EmojiListRequestBuilder> req) throws IOException, SlackApiException;

    // ------------------------------
    // files
    // ------------------------------

    FilesDeleteResponse filesDelete(FilesDeleteRequest req) throws IOException, SlackApiException;

    FilesDeleteResponse filesDelete(RequestBuilder<FilesDeleteRequest, FilesDeleteRequest.FilesDeleteRequestBuilder> req) throws IOException, SlackApiException;

    FilesInfoResponse filesInfo(FilesInfoRequest req) throws IOException, SlackApiException;

    FilesInfoResponse filesInfo(RequestBuilder<FilesInfoRequest, FilesInfoRequest.FilesInfoRequestBuilder> req) throws IOException, SlackApiException;

    FilesListResponse filesList(FilesListRequest req) throws IOException, SlackApiException;

    FilesListResponse filesList(RequestBuilder<FilesListRequest, FilesListRequest.FilesListRequestBuilder> req) throws IOException, SlackApiException;

    FilesRevokePublicURLResponse filesRevokePublicURL(FilesRevokePublicURLRequest req) throws IOException, SlackApiException;

    FilesRevokePublicURLResponse filesRevokePublicURL(RequestBuilder<FilesRevokePublicURLRequest, FilesRevokePublicURLRequest.FilesRevokePublicURLRequestBuilder> req) throws IOException, SlackApiException;

    FilesSharedPublicURLResponse filesSharedPublicURL(FilesSharedPublicURLRequest req) throws IOException, SlackApiException;

    FilesSharedPublicURLResponse filesSharedPublicURL(RequestBuilder<FilesSharedPublicURLRequest, FilesSharedPublicURLRequest.FilesSharedPublicURLRequestBuilder> req) throws IOException, SlackApiException;

    FilesUploadResponse filesUpload(FilesUploadRequest req) throws IOException, SlackApiException;

    FilesUploadResponse filesUpload(RequestBuilder<FilesUploadRequest, FilesUploadRequest.FilesUploadRequestBuilder> req) throws IOException, SlackApiException;

    // ------------------------------
    // files.comments
    // ------------------------------

    // https://api.slack.com/changelog/2018-05-file-threads-soon-tread
    @Deprecated
    FilesCommentsAddResponse filesCommentsAdd(FilesCommentsAddRequest req) throws IOException, SlackApiException;

    // https://api.slack.com/changelog/2018-05-file-threads-soon-tread
    @Deprecated
    FilesCommentsDeleteResponse filesCommentsDelete(FilesCommentsDeleteRequest req) throws IOException, SlackApiException;

    // https://api.slack.com/changelog/2018-05-file-threads-soon-tread
    @Deprecated
    FilesCommentsEditResponse filesCommentEdit(FilesCommentsEditRequest req) throws IOException, SlackApiException;

    // ------------------------------
    // groups
    // ------------------------------

    GroupsArchiveResponse groupsArchive(GroupsArchiveRequest req) throws IOException, SlackApiException;

    GroupsArchiveResponse groupsArchive(RequestBuilder<GroupsArchiveRequest, GroupsArchiveRequest.GroupsArchiveRequestBuilder> req) throws IOException, SlackApiException;

    // https://github.com/slackapi/slack-api-specs/issues/12
    @Deprecated
    GroupsCloseResponse groupsClose(GroupsCloseRequest req) throws IOException, SlackApiException;

    GroupsCreateChildResponse groupsCreateChild(GroupsCreateChildRequest req) throws IOException, SlackApiException;

    GroupsCreateChildResponse groupsCreateChild(RequestBuilder<GroupsCreateChildRequest, GroupsCreateChildRequest.GroupsCreateChildRequestBuilder> req) throws IOException, SlackApiException;

    GroupsCreateResponse groupsCreate(GroupsCreateRequest req) throws IOException, SlackApiException;

    GroupsCreateResponse groupsCreate(RequestBuilder<GroupsCreateRequest, GroupsCreateRequest.GroupsCreateRequestBuilder> req) throws IOException, SlackApiException;

    GroupsHistoryResponse groupsHistory(GroupsHistoryRequest req) throws IOException, SlackApiException;

    GroupsHistoryResponse groupsHistory(RequestBuilder<GroupsHistoryRequest, GroupsHistoryRequest.GroupsHistoryRequestBuilder> req) throws IOException, SlackApiException;

    GroupsInfoResponse groupsInfo(GroupsInfoRequest req) throws IOException, SlackApiException;

    GroupsInfoResponse groupsInfo(RequestBuilder<GroupsInfoRequest, GroupsInfoRequest.GroupsInfoRequestBuilder> req) throws IOException, SlackApiException;

    GroupsInviteResponse groupsInvite(GroupsInviteRequest req) throws IOException, SlackApiException;

    GroupsInviteResponse groupsInvite(RequestBuilder<GroupsInviteRequest, GroupsInviteRequest.GroupsInviteRequestBuilder> req) throws IOException, SlackApiException;

    GroupsKickResponse groupsKick(GroupsKickRequest req) throws IOException, SlackApiException;

    GroupsKickResponse groupsKick(RequestBuilder<GroupsKickRequest, GroupsKickRequest.GroupsKickRequestBuilder> req) throws IOException, SlackApiException;

    GroupsLeaveResponse groupsLeave(GroupsLeaveRequest req) throws IOException, SlackApiException;

    GroupsLeaveResponse groupsLeave(RequestBuilder<GroupsLeaveRequest, GroupsLeaveRequest.GroupsLeaveRequestBuilder> req) throws IOException, SlackApiException;

    GroupsListResponse groupsList(GroupsListRequest req) throws IOException, SlackApiException;

    GroupsListResponse groupsList(RequestBuilder<GroupsListRequest, GroupsListRequest.GroupsListRequestBuilder> req) throws IOException, SlackApiException;

    GroupsMarkResponse groupsMark(GroupsMarkRequest req) throws IOException, SlackApiException;

    GroupsMarkResponse groupsMark(RequestBuilder<GroupsMarkRequest, GroupsMarkRequest.GroupsMarkRequestBuilder> req) throws IOException, SlackApiException;

    GroupsOpenResponse groupsOpen(GroupsOpenRequest req) throws IOException, SlackApiException;

    GroupsOpenResponse groupsOpen(RequestBuilder<GroupsOpenRequest, GroupsOpenRequest.GroupsOpenRequestBuilder> req) throws IOException, SlackApiException;

    GroupsRenameResponse groupsRename(GroupsRenameRequest req) throws IOException, SlackApiException;

    GroupsRenameResponse groupsRename(RequestBuilder<GroupsRenameRequest, GroupsRenameRequest.GroupsRenameRequestBuilder> req) throws IOException, SlackApiException;

    GroupsSetPurposeResponse groupsSetPurpose(GroupsSetPurposeRequest req) throws IOException, SlackApiException;

    GroupsSetPurposeResponse groupsSetPurpose(RequestBuilder<GroupsSetPurposeRequest, GroupsSetPurposeRequest.GroupsSetPurposeRequestBuilder> req) throws IOException, SlackApiException;

    GroupsSetTopicResponse groupsSetTopic(GroupsSetTopicRequest req) throws IOException, SlackApiException;

    GroupsSetTopicResponse groupsSetTopic(RequestBuilder<GroupsSetTopicRequest, GroupsSetTopicRequest.GroupsSetTopicRequestBuilder> req) throws IOException, SlackApiException;

    GroupsUnarchiveResponse groupsUnarchive(GroupsUnarchiveRequest req) throws IOException, SlackApiException;

    GroupsUnarchiveResponse groupsUnarchive(RequestBuilder<GroupsUnarchiveRequest, GroupsUnarchiveRequest.GroupsUnarchiveRequestBuilder> req) throws IOException, SlackApiException;

    GroupsRepliesResponse groupsReplies(GroupsRepliesRequest req) throws IOException, SlackApiException;

    GroupsRepliesResponse groupsReplies(RequestBuilder<GroupsRepliesRequest, GroupsRepliesRequest.GroupsRepliesRequestBuilder> req) throws IOException, SlackApiException;

    // ------------------------------
    // im
    // ------------------------------

    ImCloseResponse imClose(ImCloseRequest req) throws IOException, SlackApiException;

    ImCloseResponse imClose(RequestBuilder<ImCloseRequest, ImCloseRequest.ImCloseRequestBuilder> req) throws IOException, SlackApiException;

    ImHistoryResponse imHistory(ImHistoryRequest req) throws IOException, SlackApiException;

    ImHistoryResponse imHistory(RequestBuilder<ImHistoryRequest, ImHistoryRequest.ImHistoryRequestBuilder> req) throws IOException, SlackApiException;

    ImListResponse imList(ImListRequest req) throws IOException, SlackApiException;

    ImListResponse imList(RequestBuilder<ImListRequest, ImListRequest.ImListRequestBuilder> req) throws IOException, SlackApiException;

    ImMarkResponse imMark(ImMarkRequest req) throws IOException, SlackApiException;

    ImMarkResponse imMark(RequestBuilder<ImMarkRequest, ImMarkRequest.ImMarkRequestBuilder> req) throws IOException, SlackApiException;

    ImOpenResponse imOpen(ImOpenRequest req) throws IOException, SlackApiException;

    ImOpenResponse imOpen(RequestBuilder<ImOpenRequest, ImOpenRequest.ImOpenRequestBuilder> req) throws IOException, SlackApiException;

    ImRepliesResponse imReplies(ImRepliesRequest req) throws IOException, SlackApiException;

    ImRepliesResponse imReplies(RequestBuilder<ImRepliesRequest, ImRepliesRequest.ImRepliesRequestBuilder> req) throws IOException, SlackApiException;

    // ------------------------------
    // migration
    // ------------------------------

    MigrationExchangeResponse migrationExchange(MigrationExchangeRequest req) throws IOException, SlackApiException;

    MigrationExchangeResponse migrationExchange(RequestBuilder<MigrationExchangeRequest, MigrationExchangeRequest.MigrationExchangeRequestBuilder> req) throws IOException, SlackApiException;

    // ------------------------------
    // mpim
    // ------------------------------

    MpimCloseResponse mpimClose(MpimCloseRequest req) throws IOException, SlackApiException;

    MpimCloseResponse mpimClose(RequestBuilder<MpimCloseRequest, MpimCloseRequest.MpimCloseRequestBuilder> req) throws IOException, SlackApiException;

    MpimHistoryResponse mpimHistory(MpimHistoryRequest req) throws IOException, SlackApiException;

    MpimHistoryResponse mpimHistory(RequestBuilder<MpimHistoryRequest, MpimHistoryRequest.MpimHistoryRequestBuilder> req) throws IOException, SlackApiException;

    MpimListResponse mpimList(MpimListRequest req) throws IOException, SlackApiException;

    MpimListResponse mpimList(RequestBuilder<MpimListRequest, MpimListRequest.MpimListRequestBuilder> req) throws IOException, SlackApiException;

    MpimRepliesResponse mpimReplies(MpimRepliesRequest req) throws IOException, SlackApiException;

    MpimRepliesResponse mpimReplies(RequestBuilder<MpimRepliesRequest, MpimRepliesRequest.MpimRepliesRequestBuilder> req) throws IOException, SlackApiException;

    MpimMarkResponse mpimMark(MpimMarkRequest req) throws IOException, SlackApiException;

    MpimMarkResponse mpimMark(RequestBuilder<MpimMarkRequest, MpimMarkRequest.MpimMarkRequestBuilder> req) throws IOException, SlackApiException;

    MpimOpenResponse mpimOpen(MpimOpenRequest req) throws IOException, SlackApiException;

    MpimOpenResponse mpimOpen(RequestBuilder<MpimOpenRequest, MpimOpenRequest.MpimOpenRequestBuilder> req) throws IOException, SlackApiException;

    // ------------------------------
    // oauth
    // ------------------------------

    OAuthAccessResponse oauthAccess(OAuthAccessRequest req) throws IOException, SlackApiException;

    OAuthAccessResponse oauthAccess(RequestBuilder<OAuthAccessRequest, OAuthAccessRequest.OAuthAccessRequestBuilder> req) throws IOException, SlackApiException;

    OAuthTokenResponse oauthToken(OAuthTokenRequest req) throws IOException, SlackApiException;

    OAuthTokenResponse oauthToken(RequestBuilder<OAuthTokenRequest, OAuthTokenRequest.OAuthTokenRequestBuilder> req) throws IOException, SlackApiException;

    // ------------------------------
    // pins
    // ------------------------------

    PinsAddResponse pinsAdd(PinsAddRequest req) throws IOException, SlackApiException;

    PinsAddResponse pinsAdd(RequestBuilder<PinsAddRequest, PinsAddRequest.PinsAddRequestBuilder> req) throws IOException, SlackApiException;

    PinsListResponse pinsList(PinsListRequest req) throws IOException, SlackApiException;

    PinsListResponse pinsList(RequestBuilder<PinsListRequest, PinsListRequest.PinsListRequestBuilder> req) throws IOException, SlackApiException;

    PinsRemoveResponse pinsRemove(PinsRemoveRequest req) throws IOException, SlackApiException;

    PinsRemoveResponse pinsRemove(RequestBuilder<PinsRemoveRequest, PinsRemoveRequest.PinsRemoveRequestBuilder> req) throws IOException, SlackApiException;

    // ------------------------------
    // reactions
    // ------------------------------

    ReactionsAddResponse reactionsAdd(ReactionsAddRequest req) throws IOException, SlackApiException;

    ReactionsAddResponse reactionsAdd(RequestBuilder<ReactionsAddRequest, ReactionsAddRequest.ReactionsAddRequestBuilder> req) throws IOException, SlackApiException;

    ReactionsGetResponse reactionsGet(ReactionsGetRequest req) throws IOException, SlackApiException;

    ReactionsGetResponse reactionsGet(RequestBuilder<ReactionsGetRequest, ReactionsGetRequest.ReactionsGetRequestBuilder> req) throws IOException, SlackApiException;

    ReactionsListResponse reactionsList(ReactionsListRequest req) throws IOException, SlackApiException;

    ReactionsListResponse reactionsList(RequestBuilder<ReactionsListRequest, ReactionsListRequest.ReactionsListRequestBuilder> req) throws IOException, SlackApiException;

    ReactionsRemoveResponse reactionsRemove(ReactionsRemoveRequest req) throws IOException, SlackApiException;

    ReactionsRemoveResponse reactionsRemove(RequestBuilder<ReactionsRemoveRequest, ReactionsRemoveRequest.ReactionsRemoveRequestBuilder> req) throws IOException, SlackApiException;

    // ------------------------------
    // reminders
    // ------------------------------

    RemindersAddResponse remindersAdd(RemindersAddRequest req) throws IOException, SlackApiException;

    RemindersAddResponse remindersAdd(RequestBuilder<RemindersAddRequest, RemindersAddRequest.RemindersAddRequestBuilder> req) throws IOException, SlackApiException;

    RemindersCompleteResponse remindersComplete(RemindersCompleteRequest req) throws IOException, SlackApiException;

    RemindersCompleteResponse remindersComplete(RequestBuilder<RemindersCompleteRequest, RemindersCompleteRequest.RemindersCompleteRequestBuilder> req) throws IOException, SlackApiException;

    RemindersDeleteResponse remindersDelete(RemindersDeleteRequest req) throws IOException, SlackApiException;

    RemindersDeleteResponse remindersDelete(RequestBuilder<RemindersDeleteRequest, RemindersDeleteRequest.RemindersDeleteRequestBuilder> req) throws IOException, SlackApiException;

    RemindersInfoResponse remindersInfo(RemindersInfoRequest req) throws IOException, SlackApiException;

    RemindersInfoResponse remindersInfo(RequestBuilder<RemindersInfoRequest, RemindersInfoRequest.RemindersInfoRequestBuilder> req) throws IOException, SlackApiException;

    RemindersListResponse remindersList(RemindersListRequest req) throws IOException, SlackApiException;

    RemindersListResponse remindersList(RequestBuilder<RemindersListRequest, RemindersListRequest.RemindersListRequestBuilder> req) throws IOException, SlackApiException;

    // ------------------------------
    // rtm
    // ------------------------------

    RTMConnectResponse rtmConnect(RTMConnectRequest req) throws IOException, SlackApiException;

    RTMConnectResponse rtmConnect(RequestBuilder<RTMConnectRequest, RTMConnectRequest.RTMConnectRequestBuilder> req) throws IOException, SlackApiException;

    RTMStartResponse rtmStart(RTMStartRequest req) throws IOException, SlackApiException;

    RTMStartResponse rtmStart(RequestBuilder<RTMStartRequest, RTMStartRequest.RTMStartRequestBuilder> req) throws IOException, SlackApiException;

    // ------------------------------
    // search
    // ------------------------------

    SearchAllResponse searchAll(SearchAllRequest req) throws IOException, SlackApiException;

    SearchAllResponse searchAll(RequestBuilder<SearchAllRequest, SearchAllRequest.SearchAllRequestBuilder> req) throws IOException, SlackApiException;

    SearchMessagesResponse searchMessages(SearchMessagesRequest req) throws IOException, SlackApiException;

    SearchMessagesResponse searchMessages(RequestBuilder<SearchMessagesRequest, SearchMessagesRequest.SearchMessagesRequestBuilder> req) throws IOException, SlackApiException;

    SearchFilesResponse searchFiles(SearchFilesRequest req) throws IOException, SlackApiException;

    SearchFilesResponse searchFiles(RequestBuilder<SearchFilesRequest, SearchFilesRequest.SearchFilesRequestBuilder> req) throws IOException, SlackApiException;

    // ------------------------------
    // stars
    // ------------------------------

    StarsAddResponse starsAdd(StarsAddRequest req) throws IOException, SlackApiException;

    StarsAddResponse starsAdd(RequestBuilder<StarsAddRequest, StarsAddRequest.StarsAddRequestBuilder> req) throws IOException, SlackApiException;

    StarsListResponse starsList(StarsListRequest req) throws IOException, SlackApiException;

    StarsListResponse starsList(RequestBuilder<StarsListRequest, StarsListRequest.StarsListRequestBuilder> req) throws IOException, SlackApiException;

    StarsRemoveResponse starsRemove(StarsRemoveRequest req) throws IOException, SlackApiException;

    StarsRemoveResponse starsRemove(RequestBuilder<StarsRemoveRequest, StarsRemoveRequest.StarsRemoveRequestBuilder> req) throws IOException, SlackApiException;

    // ------------------------------
    // team
    // ------------------------------

    TeamAccessLogsResponse teamAccessLogs(TeamAccessLogsRequest req) throws IOException, SlackApiException;

    TeamAccessLogsResponse teamAccessLogs(RequestBuilder<TeamAccessLogsRequest, TeamAccessLogsRequest.TeamAccessLogsRequestBuilder> req) throws IOException, SlackApiException;

    TeamBillableInfoResponse teamBillableInfo(TeamBillableInfoRequest req) throws IOException, SlackApiException;

    TeamBillableInfoResponse teamBillableInfo(RequestBuilder<TeamBillableInfoRequest, TeamBillableInfoRequest.TeamBillableInfoRequestBuilder> req) throws IOException, SlackApiException;

    TeamInfoResponse teamInfo(TeamInfoRequest req) throws IOException, SlackApiException;

    TeamInfoResponse teamInfo(RequestBuilder<TeamInfoRequest, TeamInfoRequest.TeamInfoRequestBuilder> req) throws IOException, SlackApiException;

    TeamIntegrationLogsResponse teamIntegrationLogs(TeamIntegrationLogsRequest req) throws IOException, SlackApiException;

    TeamIntegrationLogsResponse teamIntegrationLogs(RequestBuilder<TeamIntegrationLogsRequest, TeamIntegrationLogsRequest.TeamIntegrationLogsRequestBuilder> req) throws IOException, SlackApiException;

    TeamProfileGetResponse teamProfileGet(TeamProfileGetRequest req) throws IOException, SlackApiException;

    TeamProfileGetResponse teamProfileGet(RequestBuilder<TeamProfileGetRequest, TeamProfileGetRequest.TeamProfileGetRequestBuilder> req) throws IOException, SlackApiException;

    // ------------------------------
    // usergroups
    // ------------------------------

    UsergroupsCreateResponse usergroupsCreate(UsergroupsCreateRequest req) throws IOException, SlackApiException;

    UsergroupsCreateResponse usergroupsCreate(RequestBuilder<UsergroupsCreateRequest, UsergroupsCreateRequest.UsergroupsCreateRequestBuilder> req) throws IOException, SlackApiException;

    UsergroupsDisableResponse usergroupsDisable(UsergroupsDisableRequest req) throws IOException, SlackApiException;

    UsergroupsDisableResponse usergroupsDisable(RequestBuilder<UsergroupsDisableRequest, UsergroupsDisableRequest.UsergroupsDisableRequestBuilder> req) throws IOException, SlackApiException;

    UsergroupsEnableResponse usergroupsEnable(UsergroupsEnableRequest req) throws IOException, SlackApiException;

    UsergroupsEnableResponse usergroupsEnable(RequestBuilder<UsergroupsEnableRequest, UsergroupsEnableRequest.UsergroupsEnableRequestBuilder> req) throws IOException, SlackApiException;

    UsergroupsListResponse usergroupsList(UsergroupsListRequest req) throws IOException, SlackApiException;

    UsergroupsListResponse usergroupsList(RequestBuilder<UsergroupsListRequest, UsergroupsListRequest.UsergroupsListRequestBuilder> req) throws IOException, SlackApiException;

    UsergroupsUpdateResponse usergroupsUpdate(UsergroupsUpdateRequest req) throws IOException, SlackApiException;

    UsergroupsUpdateResponse usergroupsUpdate(RequestBuilder<UsergroupsUpdateRequest, UsergroupsUpdateRequest.UsergroupsUpdateRequestBuilder> req) throws IOException, SlackApiException;

    UsergroupUsersListResponse usergroupUsersList(UsergroupUsersListRequest req) throws IOException, SlackApiException;

    UsergroupUsersListResponse usergroupUsersList(RequestBuilder<UsergroupUsersListRequest, UsergroupUsersListRequest.UsergroupUsersListRequestBuilder> req) throws IOException, SlackApiException;

    UsergroupUsersUpdateResponse usergroupUsersUpdate(UsergroupUsersUpdateRequest req) throws IOException, SlackApiException;

    UsergroupUsersUpdateResponse usergroupUsersUpdate(RequestBuilder<UsergroupUsersUpdateRequest, UsergroupUsersUpdateRequest.UsergroupUsersUpdateRequestBuilder> req) throws IOException, SlackApiException;

    // ------------------------------
    // users
    // ------------------------------

    UsersConversationsResponse usersConversations(UsersConversationsRequest req) throws IOException, SlackApiException;

    UsersConversationsResponse usersConversations(RequestBuilder<UsersConversationsRequest, UsersConversationsRequest.UsersConversationsRequestBuilder> req) throws IOException, SlackApiException;

    UsersDeletePhotoResponse usersDeletePhoto(UsersDeletePhotoRequest req) throws IOException, SlackApiException;

    UsersDeletePhotoResponse usersDeletePhoto(RequestBuilder<UsersDeletePhotoRequest, UsersDeletePhotoRequest.UsersDeletePhotoRequestBuilder> req) throws IOException, SlackApiException;

    UsersGetPresenceResponse usersGetPresence(UsersGetPresenceRequest req) throws IOException, SlackApiException;

    UsersGetPresenceResponse usersGetPresence(RequestBuilder<UsersGetPresenceRequest, UsersGetPresenceRequest.UsersGetPresenceRequestBuilder> req) throws IOException, SlackApiException;

    UsersIdentityResponse usersIdentity(UsersIdentityRequest req) throws IOException, SlackApiException;

    UsersIdentityResponse usersIdentity(RequestBuilder<UsersIdentityRequest, UsersIdentityRequest.UsersIdentityRequestBuilder> req) throws IOException, SlackApiException;

    UsersInfoResponse usersInfo(UsersInfoRequest req) throws IOException, SlackApiException;

    UsersInfoResponse usersInfo(RequestBuilder<UsersInfoRequest, UsersInfoRequest.UsersInfoRequestBuilder> req) throws IOException, SlackApiException;

    UsersListResponse usersList(UsersListRequest req) throws IOException, SlackApiException;

    UsersListResponse usersList(RequestBuilder<UsersListRequest, UsersListRequest.UsersListRequestBuilder> req) throws IOException, SlackApiException;

    UsersLookupByEmailResponse usersLookupByEmail(UsersLookupByEmailRequest req) throws IOException, SlackApiException;

    UsersLookupByEmailResponse usersLookupByEmail(RequestBuilder<UsersLookupByEmailRequest, UsersLookupByEmailRequest.UsersLookupByEmailRequestBuilder> req) throws IOException, SlackApiException;

    UsersSetActiveResponse usersSetActive(UsersSetActiveRequest req) throws IOException, SlackApiException;

    UsersSetActiveResponse usersSetActive(RequestBuilder<UsersSetActiveRequest, UsersSetActiveRequest.UsersSetActiveRequestBuilder> req) throws IOException, SlackApiException;

    UsersSetPhotoResponse usersSetPhoto(UsersSetPhotoRequest req) throws IOException, SlackApiException;

    UsersSetPhotoResponse usersSetPhoto(RequestBuilder<UsersSetPhotoRequest, UsersSetPhotoRequest.UsersSetPhotoRequestBuilder> req) throws IOException, SlackApiException;

    UsersSetPresenceResponse usersSetPresence(UsersSetPresenceRequest req) throws IOException, SlackApiException;

    UsersSetPresenceResponse usersSetPresence(RequestBuilder<UsersSetPresenceRequest, UsersSetPresenceRequest.UsersSetPresenceRequestBuilder> req) throws IOException, SlackApiException;

    // ------------------------------
    // users.profile
    // ------------------------------

    UsersProfileGetResponse usersProfileGet(UsersProfileGetRequest req) throws IOException, SlackApiException;

    UsersProfileGetResponse usersProfileGet(RequestBuilder<UsersProfileGetRequest, UsersProfileGetRequest.UsersProfileGetRequestBuilder> req) throws IOException, SlackApiException;

    UsersProfileSetResponse usersProfileSet(UsersProfileSetRequest req) throws IOException, SlackApiException;

    UsersProfileSetResponse usersProfileSet(RequestBuilder<UsersProfileSetRequest, UsersProfileSetRequest.UsersProfileSetRequestBuilder> req) throws IOException, SlackApiException;

}
