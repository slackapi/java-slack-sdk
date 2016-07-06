package com.github.seratch.jslack.api.methods;

import com.github.seratch.jslack.api.methods.request.*;
import com.github.seratch.jslack.api.methods.response.*;

import java.io.IOException;

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
    // groups
    // ------------------------------

    GroupsArchiveResponse groupsArchive(GroupsArchiveRequest req) throws IOException, SlackApiException;

    GroupsCloseResponse groupsClose(GroupsCloseRequest req) throws IOException, SlackApiException;

    GroupsCreateChildResponse groupsCreateChild(GroupsCreateChildRequest req) throws IOException, SlackApiException;

    GroupsCreateResponse groupsCreate(GroupsCreateRequest req) throws IOException, SlackApiException;

    GroupsHistoryResponse groupsHistory(GroupsHistoryRequest req) throws IOException, SlackApiException;

    GroupsInfoResponse groupsInfo(GroupsInfoRequest req) throws IOException, SlackApiException;

    // ------------------------------
    // rtm
    // ------------------------------

    RTMStartResponse rtmStart(RTMStartRequest req) throws IOException, SlackApiException;

    // ------------------------------
    // users
    // ------------------------------

    UsersGetPresenceResponse usersGetPresence(UsersGetPresenceRequest req) throws IOException, SlackApiException;

    UsersIdentityResponse usersIdentity(UsersIdentityRequest req) throws IOException, SlackApiException;

    UsersInfoResponse usersInfo(UsersInfoRequest req) throws IOException, SlackApiException;

    UsersListResponse usersList(UsersListRequest req) throws IOException, SlackApiException;

    UsersSetActiveResponse usersSetActive(UsersSetActiveRequest req) throws IOException, SlackApiException;

    UsersSetPresenceResponse usersSetPresence(UsersSetPresenceRequest req) throws IOException, SlackApiException;

    // ------------------------------
    // users.profile
    // ------------------------------

    UsersProfileGetResponse usersProfileGet(UsersProfileGetRequest req) throws IOException, SlackApiException;

    UsersProfileSetResponse usersProfileSet(UsersProfileSetRequest req) throws IOException, SlackApiException;

}
