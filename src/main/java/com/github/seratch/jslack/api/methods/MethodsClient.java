package com.github.seratch.jslack.api.methods;

import java.io.IOException;

import com.github.seratch.jslack.api.methods.request.api.ApiTestRequest;
import com.github.seratch.jslack.api.methods.request.auth.AuthRevokeRequest;
import com.github.seratch.jslack.api.methods.request.auth.AuthTestRequest;
import com.github.seratch.jslack.api.methods.request.bots.BotsInfoRequest;
import com.github.seratch.jslack.api.methods.request.channels.ChannelsArchiveRequest;
import com.github.seratch.jslack.api.methods.request.channels.ChannelsCreateRequest;
import com.github.seratch.jslack.api.methods.request.channels.ChannelsHistoryRequest;
import com.github.seratch.jslack.api.methods.request.channels.ChannelsInfoRequest;
import com.github.seratch.jslack.api.methods.request.channels.ChannelsInviteRequest;
import com.github.seratch.jslack.api.methods.request.channels.ChannelsJoinRequest;
import com.github.seratch.jslack.api.methods.request.channels.ChannelsKickRequest;
import com.github.seratch.jslack.api.methods.request.channels.ChannelsLeaveRequest;
import com.github.seratch.jslack.api.methods.request.channels.ChannelsListRequest;
import com.github.seratch.jslack.api.methods.request.channels.ChannelsMarkRequest;
import com.github.seratch.jslack.api.methods.request.channels.ChannelsRenameRequest;
import com.github.seratch.jslack.api.methods.request.channels.ChannelsRepliesRequest;
import com.github.seratch.jslack.api.methods.request.channels.ChannelsSetPurposeRequest;
import com.github.seratch.jslack.api.methods.request.channels.ChannelsSetTopicRequest;
import com.github.seratch.jslack.api.methods.request.channels.ChannelsUnarchiveRequest;
import com.github.seratch.jslack.api.methods.request.chat.ChatDeleteRequest;
import com.github.seratch.jslack.api.methods.request.chat.ChatMeMessageRequest;
import com.github.seratch.jslack.api.methods.request.chat.ChatPostMessageRequest;
import com.github.seratch.jslack.api.methods.request.chat.ChatUpdateRequest;
import com.github.seratch.jslack.api.methods.request.conversations.ConversationsArchiveRequest;
import com.github.seratch.jslack.api.methods.request.conversations.ConversationsCloseRequest;
import com.github.seratch.jslack.api.methods.request.conversations.ConversationsCreateRequest;
import com.github.seratch.jslack.api.methods.request.conversations.ConversationsHistoryRequest;
import com.github.seratch.jslack.api.methods.request.conversations.ConversationsInfoRequest;
import com.github.seratch.jslack.api.methods.request.conversations.ConversationsInviteRequest;
import com.github.seratch.jslack.api.methods.request.conversations.ConversationsJoinRequest;
import com.github.seratch.jslack.api.methods.request.conversations.ConversationsKickRequest;
import com.github.seratch.jslack.api.methods.request.conversations.ConversationsLeaveRequest;
import com.github.seratch.jslack.api.methods.request.conversations.ConversationsListRequest;
import com.github.seratch.jslack.api.methods.request.conversations.ConversationsMembersRequest;
import com.github.seratch.jslack.api.methods.request.conversations.ConversationsOpenRequest;
import com.github.seratch.jslack.api.methods.request.conversations.ConversationsRenameRequest;
import com.github.seratch.jslack.api.methods.request.conversations.ConversationsRepliesRequest;
import com.github.seratch.jslack.api.methods.request.conversations.ConversationsSetPurposeRequest;
import com.github.seratch.jslack.api.methods.request.conversations.ConversationsSetTopicRequest;
import com.github.seratch.jslack.api.methods.request.conversations.ConversationsUnarchiveRequest;
import com.github.seratch.jslack.api.methods.request.dialog.DialogOpenRequest;
import com.github.seratch.jslack.api.methods.request.dnd.DndEndDndRequest;
import com.github.seratch.jslack.api.methods.request.dnd.DndEndSnoozeRequest;
import com.github.seratch.jslack.api.methods.request.dnd.DndInfoRequest;
import com.github.seratch.jslack.api.methods.request.dnd.DndSetSnoozeRequest;
import com.github.seratch.jslack.api.methods.request.dnd.DndTeamInfoRequest;
import com.github.seratch.jslack.api.methods.request.emoji.EmojiListRequest;
import com.github.seratch.jslack.api.methods.request.files.FilesDeleteRequest;
import com.github.seratch.jslack.api.methods.request.files.FilesInfoRequest;
import com.github.seratch.jslack.api.methods.request.files.FilesListRequest;
import com.github.seratch.jslack.api.methods.request.files.FilesRevokePublicURLRequest;
import com.github.seratch.jslack.api.methods.request.files.FilesSharedPublicURLRequest;
import com.github.seratch.jslack.api.methods.request.files.FilesUploadRequest;
import com.github.seratch.jslack.api.methods.request.files.comments.FilesCommentsAddRequest;
import com.github.seratch.jslack.api.methods.request.files.comments.FilesCommentsDeleteRequest;
import com.github.seratch.jslack.api.methods.request.files.comments.FilesCommentsEditRequest;
import com.github.seratch.jslack.api.methods.request.groups.GroupsArchiveRequest;
import com.github.seratch.jslack.api.methods.request.groups.GroupsCloseRequest;
import com.github.seratch.jslack.api.methods.request.groups.GroupsCreateChildRequest;
import com.github.seratch.jslack.api.methods.request.groups.GroupsCreateRequest;
import com.github.seratch.jslack.api.methods.request.groups.GroupsHistoryRequest;
import com.github.seratch.jslack.api.methods.request.groups.GroupsInfoRequest;
import com.github.seratch.jslack.api.methods.request.groups.GroupsInviteRequest;
import com.github.seratch.jslack.api.methods.request.groups.GroupsKickRequest;
import com.github.seratch.jslack.api.methods.request.groups.GroupsLeaveRequest;
import com.github.seratch.jslack.api.methods.request.groups.GroupsListRequest;
import com.github.seratch.jslack.api.methods.request.groups.GroupsMarkRequest;
import com.github.seratch.jslack.api.methods.request.groups.GroupsOpenRequest;
import com.github.seratch.jslack.api.methods.request.groups.GroupsRenameRequest;
import com.github.seratch.jslack.api.methods.request.groups.GroupsSetPurposeRequest;
import com.github.seratch.jslack.api.methods.request.groups.GroupsSetTopicRequest;
import com.github.seratch.jslack.api.methods.request.groups.GroupsUnarchiveRequest;
import com.github.seratch.jslack.api.methods.request.im.ImCloseRequest;
import com.github.seratch.jslack.api.methods.request.im.ImHistoryRequest;
import com.github.seratch.jslack.api.methods.request.im.ImListRequest;
import com.github.seratch.jslack.api.methods.request.im.ImMarkRequest;
import com.github.seratch.jslack.api.methods.request.im.ImOpenRequest;
import com.github.seratch.jslack.api.methods.request.mpim.MpimCloseRequest;
import com.github.seratch.jslack.api.methods.request.mpim.MpimHistoryRequest;
import com.github.seratch.jslack.api.methods.request.mpim.MpimListRequest;
import com.github.seratch.jslack.api.methods.request.mpim.MpimMarkRequest;
import com.github.seratch.jslack.api.methods.request.mpim.MpimOpenRequest;
import com.github.seratch.jslack.api.methods.request.oauth.OAuthAccessRequest;
import com.github.seratch.jslack.api.methods.request.pins.PinsAddRequest;
import com.github.seratch.jslack.api.methods.request.pins.PinsListRequest;
import com.github.seratch.jslack.api.methods.request.pins.PinsRemoveRequest;
import com.github.seratch.jslack.api.methods.request.reactions.ReactionsAddRequest;
import com.github.seratch.jslack.api.methods.request.reactions.ReactionsGetRequest;
import com.github.seratch.jslack.api.methods.request.reactions.ReactionsListRequest;
import com.github.seratch.jslack.api.methods.request.reactions.ReactionsRemoveRequest;
import com.github.seratch.jslack.api.methods.request.reminders.RemindersAddRequest;
import com.github.seratch.jslack.api.methods.request.reminders.RemindersCompleteRequest;
import com.github.seratch.jslack.api.methods.request.reminders.RemindersDeleteRequest;
import com.github.seratch.jslack.api.methods.request.reminders.RemindersInfoRequest;
import com.github.seratch.jslack.api.methods.request.reminders.RemindersListRequest;
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
import com.github.seratch.jslack.api.methods.request.usergroups.UsergroupsCreateRequest;
import com.github.seratch.jslack.api.methods.request.usergroups.UsergroupsDisableRequest;
import com.github.seratch.jslack.api.methods.request.usergroups.UsergroupsEnableRequest;
import com.github.seratch.jslack.api.methods.request.usergroups.UsergroupsListRequest;
import com.github.seratch.jslack.api.methods.request.usergroups.UsergroupsUpdateRequest;
import com.github.seratch.jslack.api.methods.request.usergroups.users.UsergroupUsersListRequest;
import com.github.seratch.jslack.api.methods.request.usergroups.users.UsergroupUsersUpdateRequest;
import com.github.seratch.jslack.api.methods.request.users.UsersDeletePhotoRequest;
import com.github.seratch.jslack.api.methods.request.users.UsersGetPresenceRequest;
import com.github.seratch.jslack.api.methods.request.users.UsersIdentityRequest;
import com.github.seratch.jslack.api.methods.request.users.UsersInfoRequest;
import com.github.seratch.jslack.api.methods.request.users.UsersListRequest;
import com.github.seratch.jslack.api.methods.request.users.UsersSetActiveRequest;
import com.github.seratch.jslack.api.methods.request.users.UsersSetPhotoRequest;
import com.github.seratch.jslack.api.methods.request.users.UsersSetPresenceRequest;
import com.github.seratch.jslack.api.methods.request.users.profile.UsersProfileGetRequest;
import com.github.seratch.jslack.api.methods.request.users.profile.UsersProfileSetRequest;
import com.github.seratch.jslack.api.methods.response.api.ApiTestResponse;
import com.github.seratch.jslack.api.methods.response.auth.AuthRevokeResponse;
import com.github.seratch.jslack.api.methods.response.auth.AuthTestResponse;
import com.github.seratch.jslack.api.methods.response.bots.BotsInfoResponse;
import com.github.seratch.jslack.api.methods.response.channels.ChannelsArchiveResponse;
import com.github.seratch.jslack.api.methods.response.channels.ChannelsCreateResponse;
import com.github.seratch.jslack.api.methods.response.channels.ChannelsHistoryResponse;
import com.github.seratch.jslack.api.methods.response.channels.ChannelsInfoResponse;
import com.github.seratch.jslack.api.methods.response.channels.ChannelsInviteResponse;
import com.github.seratch.jslack.api.methods.response.channels.ChannelsJoinResponse;
import com.github.seratch.jslack.api.methods.response.channels.ChannelsKickResponse;
import com.github.seratch.jslack.api.methods.response.channels.ChannelsLeaveResponse;
import com.github.seratch.jslack.api.methods.response.channels.ChannelsListResponse;
import com.github.seratch.jslack.api.methods.response.channels.ChannelsMarkResponse;
import com.github.seratch.jslack.api.methods.response.channels.ChannelsRenameResponse;
import com.github.seratch.jslack.api.methods.response.channels.ChannelsRepliesResponse;
import com.github.seratch.jslack.api.methods.response.channels.ChannelsSetPurposeResponse;
import com.github.seratch.jslack.api.methods.response.channels.ChannelsSetTopicResponse;
import com.github.seratch.jslack.api.methods.response.channels.ChannelsUnarchiveResponse;
import com.github.seratch.jslack.api.methods.response.chat.ChatDeleteResponse;
import com.github.seratch.jslack.api.methods.response.chat.ChatMeMessageResponse;
import com.github.seratch.jslack.api.methods.response.chat.ChatPostMessageResponse;
import com.github.seratch.jslack.api.methods.response.chat.ChatUpdateResponse;
import com.github.seratch.jslack.api.methods.response.conversations.ConversationsArchiveResponse;
import com.github.seratch.jslack.api.methods.response.conversations.ConversationsCloseResponse;
import com.github.seratch.jslack.api.methods.response.conversations.ConversationsCreateResponse;
import com.github.seratch.jslack.api.methods.response.conversations.ConversationsHistoryResponse;
import com.github.seratch.jslack.api.methods.response.conversations.ConversationsInfoResponse;
import com.github.seratch.jslack.api.methods.response.conversations.ConversationsInviteResponse;
import com.github.seratch.jslack.api.methods.response.conversations.ConversationsJoinResponse;
import com.github.seratch.jslack.api.methods.response.conversations.ConversationsKickResponse;
import com.github.seratch.jslack.api.methods.response.conversations.ConversationsLeaveResponse;
import com.github.seratch.jslack.api.methods.response.conversations.ConversationsListResponse;
import com.github.seratch.jslack.api.methods.response.conversations.ConversationsMembersResponse;
import com.github.seratch.jslack.api.methods.response.conversations.ConversationsOpenResponse;
import com.github.seratch.jslack.api.methods.response.conversations.ConversationsRenameResponse;
import com.github.seratch.jslack.api.methods.response.conversations.ConversationsRepliesResponse;
import com.github.seratch.jslack.api.methods.response.conversations.ConversationsSetPurposeResponse;
import com.github.seratch.jslack.api.methods.response.conversations.ConversationsSetTopicResponse;
import com.github.seratch.jslack.api.methods.response.conversations.ConversationsUnarchiveResponse;
import com.github.seratch.jslack.api.methods.response.dialog.DialogOpenResponse;
import com.github.seratch.jslack.api.methods.response.dnd.DndEndDndResponse;
import com.github.seratch.jslack.api.methods.response.dnd.DndEndSnoozeResponse;
import com.github.seratch.jslack.api.methods.response.dnd.DndInfoResponse;
import com.github.seratch.jslack.api.methods.response.dnd.DndSetSnoozeResponse;
import com.github.seratch.jslack.api.methods.response.dnd.DndTeamInfoResponse;
import com.github.seratch.jslack.api.methods.response.emoji.EmojiListResponse;
import com.github.seratch.jslack.api.methods.response.files.FilesDeleteResponse;
import com.github.seratch.jslack.api.methods.response.files.FilesInfoResponse;
import com.github.seratch.jslack.api.methods.response.files.FilesListResponse;
import com.github.seratch.jslack.api.methods.response.files.FilesRevokePublicURLResponse;
import com.github.seratch.jslack.api.methods.response.files.FilesSharedPublicURLResponse;
import com.github.seratch.jslack.api.methods.response.files.FilesUploadResponse;
import com.github.seratch.jslack.api.methods.response.files.comments.FilesCommentsAddResponse;
import com.github.seratch.jslack.api.methods.response.files.comments.FilesCommentsDeleteResponse;
import com.github.seratch.jslack.api.methods.response.files.comments.FilesCommentsEditResponse;
import com.github.seratch.jslack.api.methods.response.groups.GroupsArchiveResponse;
import com.github.seratch.jslack.api.methods.response.groups.GroupsCloseResponse;
import com.github.seratch.jslack.api.methods.response.groups.GroupsCreateChildResponse;
import com.github.seratch.jslack.api.methods.response.groups.GroupsCreateResponse;
import com.github.seratch.jslack.api.methods.response.groups.GroupsHistoryResponse;
import com.github.seratch.jslack.api.methods.response.groups.GroupsInfoResponse;
import com.github.seratch.jslack.api.methods.response.groups.GroupsInviteResponse;
import com.github.seratch.jslack.api.methods.response.groups.GroupsKickResponse;
import com.github.seratch.jslack.api.methods.response.groups.GroupsLeaveResponse;
import com.github.seratch.jslack.api.methods.response.groups.GroupsListResponse;
import com.github.seratch.jslack.api.methods.response.groups.GroupsMarkResponse;
import com.github.seratch.jslack.api.methods.response.groups.GroupsOpenResponse;
import com.github.seratch.jslack.api.methods.response.groups.GroupsRenameResponse;
import com.github.seratch.jslack.api.methods.response.groups.GroupsSetPurposeResponse;
import com.github.seratch.jslack.api.methods.response.groups.GroupsSetTopicResponse;
import com.github.seratch.jslack.api.methods.response.groups.GroupsUnarchiveResponse;
import com.github.seratch.jslack.api.methods.response.im.ImCloseResponse;
import com.github.seratch.jslack.api.methods.response.im.ImHistoryResponse;
import com.github.seratch.jslack.api.methods.response.im.ImListResponse;
import com.github.seratch.jslack.api.methods.response.im.ImMarkResponse;
import com.github.seratch.jslack.api.methods.response.im.ImOpenResponse;
import com.github.seratch.jslack.api.methods.response.mpim.MpimCloseResponse;
import com.github.seratch.jslack.api.methods.response.mpim.MpimHistoryResponse;
import com.github.seratch.jslack.api.methods.response.mpim.MpimListResponse;
import com.github.seratch.jslack.api.methods.response.mpim.MpimMarkResponse;
import com.github.seratch.jslack.api.methods.response.mpim.MpimOpenResponse;
import com.github.seratch.jslack.api.methods.response.oauth.OAuthAccessResponse;
import com.github.seratch.jslack.api.methods.response.pins.PinsAddResponse;
import com.github.seratch.jslack.api.methods.response.pins.PinsListResponse;
import com.github.seratch.jslack.api.methods.response.pins.PinsRemoveResponse;
import com.github.seratch.jslack.api.methods.response.reactions.ReactionsAddResponse;
import com.github.seratch.jslack.api.methods.response.reactions.ReactionsGetResponse;
import com.github.seratch.jslack.api.methods.response.reactions.ReactionsListResponse;
import com.github.seratch.jslack.api.methods.response.reactions.ReactionsRemoveResponse;
import com.github.seratch.jslack.api.methods.response.reminders.RemindersAddResponse;
import com.github.seratch.jslack.api.methods.response.reminders.RemindersCompleteResponse;
import com.github.seratch.jslack.api.methods.response.reminders.RemindersDeleteResponse;
import com.github.seratch.jslack.api.methods.response.reminders.RemindersInfoResponse;
import com.github.seratch.jslack.api.methods.response.reminders.RemindersListResponse;
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
import com.github.seratch.jslack.api.methods.response.usergroups.UsergroupsCreateResponse;
import com.github.seratch.jslack.api.methods.response.usergroups.UsergroupsDisableResponse;
import com.github.seratch.jslack.api.methods.response.usergroups.UsergroupsEnableResponse;
import com.github.seratch.jslack.api.methods.response.usergroups.UsergroupsListResponse;
import com.github.seratch.jslack.api.methods.response.usergroups.UsergroupsUpdateResponse;
import com.github.seratch.jslack.api.methods.response.usergroups.users.UsergroupUsersListResponse;
import com.github.seratch.jslack.api.methods.response.usergroups.users.UsergroupUsersUpdateResponse;
import com.github.seratch.jslack.api.methods.response.users.UsersDeletePhotoResponse;
import com.github.seratch.jslack.api.methods.response.users.UsersGetPresenceResponse;
import com.github.seratch.jslack.api.methods.response.users.UsersIdentityResponse;
import com.github.seratch.jslack.api.methods.response.users.UsersInfoResponse;
import com.github.seratch.jslack.api.methods.response.users.UsersListResponse;
import com.github.seratch.jslack.api.methods.response.users.UsersSetActiveResponse;
import com.github.seratch.jslack.api.methods.response.users.UsersSetPhotoResponse;
import com.github.seratch.jslack.api.methods.response.users.UsersSetPresenceResponse;
import com.github.seratch.jslack.api.methods.response.users.profile.UsersProfileGetResponse;
import com.github.seratch.jslack.api.methods.response.users.profile.UsersProfileSetResponse;

/**
 * API Methods.
 * https://api.slack.com/methods
 */
public interface MethodsClient {

    // ------------------------------
    // api
    // ------------------------------

    ApiTestResponse apiTest(ApiTestRequest req) throws IOException, SlackApiException;

    // ------------------------------
    // auth
    // ------------------------------

    AuthRevokeResponse authRevoke(AuthRevokeRequest req) throws IOException, SlackApiException;

    AuthTestResponse authTest(AuthTestRequest req) throws IOException, SlackApiException;

    // ------------------------------
    // bots
    // ------------------------------

    BotsInfoResponse botsInfo(BotsInfoRequest req) throws IOException, SlackApiException;

    // ------------------------------
    // channels
    // ------------------------------

    ChannelsArchiveResponse channelsArchive(ChannelsArchiveRequest req) throws IOException, SlackApiException;

    ChannelsCreateResponse channelsCreate(ChannelsCreateRequest req) throws IOException, SlackApiException;

    ChannelsHistoryResponse channelsHistory(ChannelsHistoryRequest req) throws IOException, SlackApiException;

    ChannelsRepliesResponse channelsReplies(ChannelsRepliesRequest req) throws IOException, SlackApiException;

    ChannelsInfoResponse channelsInfo(ChannelsInfoRequest req) throws IOException, SlackApiException;

    ChannelsListResponse channelsList(ChannelsListRequest req) throws IOException, SlackApiException;

    ChannelsInviteResponse channelsInvite(ChannelsInviteRequest req) throws IOException, SlackApiException;

    ChannelsJoinResponse channelsJoin(ChannelsJoinRequest req) throws IOException, SlackApiException;

    ChannelsKickResponse channelsKick(ChannelsKickRequest req) throws IOException, SlackApiException;

    ChannelsLeaveResponse channelsLeave(ChannelsLeaveRequest req) throws IOException, SlackApiException;

    ChannelsMarkResponse channelsMark(ChannelsMarkRequest req) throws IOException, SlackApiException;

    ChannelsRenameResponse channelsRename(ChannelsRenameRequest req) throws IOException, SlackApiException;

    ChannelsSetPurposeResponse channelsSetPurpose(ChannelsSetPurposeRequest req) throws IOException, SlackApiException;

    ChannelsSetTopicResponse channelsSetTopic(ChannelsSetTopicRequest req) throws IOException, SlackApiException;

    ChannelsUnarchiveResponse channelsUnarchive(ChannelsUnarchiveRequest req) throws IOException, SlackApiException;

    // ------------------------------
    // chat
    // ------------------------------

    ChatDeleteResponse chatDelete(ChatDeleteRequest req) throws IOException, SlackApiException;

    ChatMeMessageResponse chatMeMessage(ChatMeMessageRequest req) throws IOException, SlackApiException;

    ChatPostMessageResponse chatPostMessage(ChatPostMessageRequest req) throws IOException, SlackApiException;

    ChatUpdateResponse chatUpdate(ChatUpdateRequest req) throws IOException, SlackApiException;
    
    // ------------------------------
    // conversations
    // ------------------------------
    
    ConversationsArchiveResponse conversationsArchive(ConversationsArchiveRequest req) throws IOException, SlackApiException;
    
    ConversationsCloseResponse conversationsClose(ConversationsCloseRequest req) throws IOException, SlackApiException;
    
    ConversationsCreateResponse conversationsCreate(ConversationsCreateRequest req) throws IOException, SlackApiException;
    
    ConversationsHistoryResponse conversationsHistory(ConversationsHistoryRequest req) throws IOException, SlackApiException;
    
    ConversationsInfoResponse conversationsInfo(ConversationsInfoRequest req) throws IOException, SlackApiException;
    
    ConversationsInviteResponse conversationsInvite(ConversationsInviteRequest req) throws IOException, SlackApiException;
    
    ConversationsJoinResponse conversationsJoin(ConversationsJoinRequest req) throws IOException, SlackApiException;
    
    ConversationsKickResponse conversationsKick(ConversationsKickRequest req) throws IOException, SlackApiException;
    
    ConversationsLeaveResponse conversationsLeave(ConversationsLeaveRequest req) throws IOException, SlackApiException;
    
    ConversationsListResponse conversationsList(ConversationsListRequest req) throws IOException, SlackApiException;
    
    ConversationsMembersResponse conversationsMembers(ConversationsMembersRequest req) throws IOException, SlackApiException;
    
    ConversationsOpenResponse conversationsOpen(ConversationsOpenRequest req) throws IOException, SlackApiException;
    
    ConversationsRenameResponse conversationsRename(ConversationsRenameRequest req) throws IOException, SlackApiException;
    
    ConversationsRepliesResponse conversationsReplies(ConversationsRepliesRequest req) throws IOException, SlackApiException;
    
    ConversationsSetPurposeResponse conversationsSetPurpose(ConversationsSetPurposeRequest req) throws IOException, SlackApiException;
    
    ConversationsSetTopicResponse conversationsSetTopic(ConversationsSetTopicRequest req) throws IOException, SlackApiException;
    
    ConversationsUnarchiveResponse conversationsUnarchive(ConversationsUnarchiveRequest req) throws IOException, SlackApiException;
    
    // ------------------------------
    // dialog
    // ------------------------------
    
    DialogOpenResponse dialogOpen(DialogOpenRequest req) throws IOException, SlackApiException;

    // ------------------------------
    // dnd
    // ------------------------------

    DndEndDndResponse dndEndDnd(DndEndDndRequest req) throws IOException, SlackApiException;

    DndEndSnoozeResponse dndEndSnooze(DndEndSnoozeRequest req) throws IOException, SlackApiException;

    DndInfoResponse dndInfo(DndInfoRequest req) throws IOException, SlackApiException;

    DndSetSnoozeResponse dndSetSnooze(DndSetSnoozeRequest req) throws IOException, SlackApiException;

    DndTeamInfoResponse dndTeamInfo(DndTeamInfoRequest req) throws IOException, SlackApiException;

    // ------------------------------
    // emoji
    // ------------------------------

    EmojiListResponse emojiList(EmojiListRequest req) throws IOException, SlackApiException;

    // ------------------------------
    // files
    // ------------------------------

    FilesDeleteResponse filesDelete(FilesDeleteRequest req) throws IOException, SlackApiException;

    FilesInfoResponse filesInfo(FilesInfoRequest req) throws IOException, SlackApiException;

    FilesListResponse filesList(FilesListRequest req) throws IOException, SlackApiException;

    FilesRevokePublicURLResponse filesRevokePublicURL(FilesRevokePublicURLRequest req) throws IOException, SlackApiException;

    FilesSharedPublicURLResponse filesSharedPublicURL(FilesSharedPublicURLRequest req) throws IOException, SlackApiException;

    FilesUploadResponse filesUpload(FilesUploadRequest req) throws IOException, SlackApiException;

    // ------------------------------
    // files.comments
    // ------------------------------

    FilesCommentsAddResponse filesCommentsAdd(FilesCommentsAddRequest req) throws IOException, SlackApiException;

    FilesCommentsDeleteResponse filesCommentsDelete(FilesCommentsDeleteRequest req) throws IOException, SlackApiException;

    FilesCommentsEditResponse filesCommentEdit(FilesCommentsEditRequest req) throws IOException, SlackApiException;

    // ------------------------------
    // groups
    // ------------------------------

    GroupsArchiveResponse groupsArchive(GroupsArchiveRequest req) throws IOException, SlackApiException;

    GroupsCloseResponse groupsClose(GroupsCloseRequest req) throws IOException, SlackApiException;

    GroupsCreateChildResponse groupsCreateChild(GroupsCreateChildRequest req) throws IOException, SlackApiException;

    GroupsCreateResponse groupsCreate(GroupsCreateRequest req) throws IOException, SlackApiException;

    GroupsHistoryResponse groupsHistory(GroupsHistoryRequest req) throws IOException, SlackApiException;

    GroupsInfoResponse groupsInfo(GroupsInfoRequest req) throws IOException, SlackApiException;

    GroupsInviteResponse groupsInvite(GroupsInviteRequest req) throws IOException, SlackApiException;

    GroupsKickResponse groupsKick(GroupsKickRequest req) throws IOException, SlackApiException;

    GroupsLeaveResponse groupsLeave(GroupsLeaveRequest req) throws IOException, SlackApiException;

    GroupsListResponse groupsList(GroupsListRequest req) throws IOException, SlackApiException;

    GroupsMarkResponse groupsMark(GroupsMarkRequest req) throws IOException, SlackApiException;

    GroupsOpenResponse groupsOpen(GroupsOpenRequest req) throws IOException, SlackApiException;

    GroupsRenameResponse groupsRename(GroupsRenameRequest req) throws IOException, SlackApiException;

    GroupsSetPurposeResponse groupsSetPurpose(GroupsSetPurposeRequest req) throws IOException, SlackApiException;

    GroupsSetTopicResponse groupsSetTopic(GroupsSetTopicRequest req) throws IOException, SlackApiException;

    GroupsUnarchiveResponse groupsUnarchive(GroupsUnarchiveRequest req) throws IOException, SlackApiException;

    // ------------------------------
    // im
    // ------------------------------

    ImCloseResponse imClose(ImCloseRequest req) throws IOException, SlackApiException;

    ImHistoryResponse imHistory(ImHistoryRequest req) throws IOException, SlackApiException;

    ImListResponse imList(ImListRequest req) throws IOException, SlackApiException;

    ImMarkResponse imMark(ImMarkRequest req) throws IOException, SlackApiException;

    ImOpenResponse imOpen(ImOpenRequest req) throws IOException, SlackApiException;

    // ------------------------------
    // mpim
    // ------------------------------

    MpimCloseResponse mpimClose(MpimCloseRequest req) throws IOException, SlackApiException;

    MpimHistoryResponse mpimHistory(MpimHistoryRequest req) throws IOException, SlackApiException;

    MpimListResponse mpimList(MpimListRequest req) throws IOException, SlackApiException;

    MpimMarkResponse mpimMark(MpimMarkRequest req) throws IOException, SlackApiException;

    MpimOpenResponse mpimOpen(MpimOpenRequest req) throws IOException, SlackApiException;

    // ------------------------------
    // oauth
    // ------------------------------

    OAuthAccessResponse oauthAccess(OAuthAccessRequest req) throws IOException, SlackApiException;

    // ------------------------------
    // pins
    // ------------------------------

    PinsAddResponse pinsAdd(PinsAddRequest req) throws IOException, SlackApiException;

    PinsListResponse pinsList(PinsListRequest req) throws IOException, SlackApiException;

    PinsRemoveResponse pinsRemove(PinsRemoveRequest req) throws IOException, SlackApiException;

    // ------------------------------
    // reactions
    // ------------------------------

    ReactionsAddResponse reactionsAdd(ReactionsAddRequest req) throws IOException, SlackApiException;

    ReactionsGetResponse reactionsGet(ReactionsGetRequest req) throws IOException, SlackApiException;

    ReactionsListResponse reactionsList(ReactionsListRequest req) throws IOException, SlackApiException;

    ReactionsRemoveResponse reactionsRemove(ReactionsRemoveRequest req) throws IOException, SlackApiException;

    // ------------------------------
    // reminders
    // ------------------------------

    RemindersAddResponse remindersAdd(RemindersAddRequest req) throws IOException, SlackApiException;

    RemindersCompleteResponse remindersComplete(RemindersCompleteRequest req) throws IOException, SlackApiException;

    RemindersDeleteResponse remindersDelete(RemindersDeleteRequest req) throws IOException, SlackApiException;

    RemindersInfoResponse remindersInfo(RemindersInfoRequest req) throws IOException, SlackApiException;

    RemindersListResponse remindersList(RemindersListRequest req) throws IOException, SlackApiException;

    // ------------------------------
    // rtm
    // ------------------------------

    RTMStartResponse rtmStart(RTMStartRequest req) throws IOException, SlackApiException;

    // ------------------------------
    // search
    // ------------------------------

    SearchAllResponse searchAll(SearchAllRequest req) throws IOException, SlackApiException;

    SearchMessagesResponse searchMessages(SearchMessagesRequest req) throws IOException, SlackApiException;

    SearchFilesResponse searchFiles(SearchFilesRequest req) throws IOException, SlackApiException;

    // ------------------------------
    // stars
    // ------------------------------

    StarsAddResponse starsAdd(StarsAddRequest req) throws IOException, SlackApiException;

    StarsListResponse starsList(StarsListRequest req) throws IOException, SlackApiException;

    StarsRemoveResponse starsRemove(StarsRemoveRequest req) throws IOException, SlackApiException;

    // ------------------------------
    // team
    // ------------------------------

    TeamAccessLogsResponse teamAccessLogs(TeamAccessLogsRequest req) throws IOException, SlackApiException;

    TeamBillableInfoResponse teamBillableInfo(TeamBillableInfoRequest req) throws IOException, SlackApiException;

    TeamInfoResponse teamInfo(TeamInfoRequest req) throws IOException, SlackApiException;

    TeamIntegrationLogsResponse teamIntegrationLogs(TeamIntegrationLogsRequest req) throws IOException, SlackApiException;

    TeamProfileGetResponse teamProfileGet(TeamProfileGetRequest req) throws IOException, SlackApiException;

    // ------------------------------
    // usergroups
    // ------------------------------

    UsergroupsCreateResponse usergroupsCreate(UsergroupsCreateRequest req) throws IOException, SlackApiException;

    UsergroupsDisableResponse usergroupsDisable(UsergroupsDisableRequest req) throws IOException, SlackApiException;

    UsergroupsEnableResponse usergroupsEnable(UsergroupsEnableRequest req) throws IOException, SlackApiException;

    UsergroupsListResponse usergroupsList(UsergroupsListRequest req) throws IOException, SlackApiException;

    UsergroupsUpdateResponse usergroupsUpdate(UsergroupsUpdateRequest req) throws IOException, SlackApiException;

    UsergroupUsersListResponse usergroupUsersList(UsergroupUsersListRequest req) throws IOException, SlackApiException;

    UsergroupUsersUpdateResponse usergroupUsersUpdate(UsergroupUsersUpdateRequest req) throws IOException, SlackApiException;

    // ------------------------------
    // users
    // ------------------------------

    UsersDeletePhotoResponse usersDeletePhoto(UsersDeletePhotoRequest req) throws IOException, SlackApiException;

    UsersGetPresenceResponse usersGetPresence(UsersGetPresenceRequest req) throws IOException, SlackApiException;

    UsersIdentityResponse usersIdentity(UsersIdentityRequest req) throws IOException, SlackApiException;

    UsersInfoResponse usersInfo(UsersInfoRequest req) throws IOException, SlackApiException;

    UsersListResponse usersList(UsersListRequest req) throws IOException, SlackApiException;

    UsersSetActiveResponse usersSetActive(UsersSetActiveRequest req) throws IOException, SlackApiException;

    UsersSetPhotoResponse usersSetPhoto(UsersSetPhotoRequest req) throws IOException, SlackApiException;

    UsersSetPresenceResponse usersSetPresence(UsersSetPresenceRequest req) throws IOException, SlackApiException;

    // ------------------------------
    // users.profile
    // ------------------------------

    UsersProfileGetResponse usersProfileGet(UsersProfileGetRequest req) throws IOException, SlackApiException;

    UsersProfileSetResponse usersProfileSet(UsersProfileSetRequest req) throws IOException, SlackApiException;

}
