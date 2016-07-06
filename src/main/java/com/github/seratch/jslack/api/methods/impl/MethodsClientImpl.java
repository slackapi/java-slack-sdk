package com.github.seratch.jslack.api.methods.impl;

import com.github.seratch.jslack.api.methods.Methods;
import com.github.seratch.jslack.api.methods.MethodsClient;
import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.request.*;
import com.github.seratch.jslack.api.methods.response.*;
import com.github.seratch.jslack.common.http.SlackHttpClient;
import com.github.seratch.jslack.common.json.GsonFactory;
import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Response;

import java.io.IOException;

import static java.util.stream.Collectors.joining;

@Slf4j
public class MethodsClientImpl implements MethodsClient {

    public static final String ENDPOINT_URL_PREFIX = "https://slack.com/api/";

    private final SlackHttpClient slackHttpClient = new SlackHttpClient();

    // ----------------------------------------------------------------------------------
    // public methods
    // ----------------------------------------------------------------------------------

    @Override
    public ApiTestResponse apiTest(ApiTestRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("foo", req.getFoo(), form);
        setIfNotNull("error", req.getError(), form);
        return doPostForm(form, Methods.API_TEST, ApiTestResponse.class);
    }

    @Override
    public AuthRevokeResponse authRevoke(AuthRevokeRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("test", req.getTest(), form);
        return doPostForm(form, Methods.AUTH_REVOKE, AuthRevokeResponse.class);
    }

    @Override
    public AuthTestResponse authTest(AuthTestRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        return doPostForm(form, Methods.AUTH_TEST, AuthTestResponse.class);
    }

    @Override
    public BotsInfoResponse botsInfo(BotsInfoRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("bot", req.getBot(), form);
        return doPostForm(form, Methods.BOTS_INFO, BotsInfoResponse.class);
    }

    @Override
    public ChannelsArchiveResponse channelsArchive(ChannelsArchiveRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("channel", req.getChannel(), form);
        return doPostForm(form, Methods.CHANNELS_ARCHIVE, ChannelsArchiveResponse.class);
    }

    @Override
    public ChannelsCreateResponse channelsCreate(ChannelsCreateRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("name", req.getName(), form);
        return doPostForm(form, Methods.CHANNELS_CREATE, ChannelsCreateResponse.class);
    }

    @Override
    public ChannelsHistoryResponse channelsHistory(ChannelsHistoryRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("latest", req.getLatest(), form);
        setIfNotNull("oldest", req.getOldest(), form);
        setIfNotNull("inclusive", req.getInclusive(), form);
        setIfNotNull("count", req.getCount(), form);
        setIfNotNull("unreads", req.getUnreads(), form);
        return doPostForm(form, Methods.CHANNELS_HISTORY, ChannelsHistoryResponse.class);
    }

    @Override
    public ChannelsInfoResponse channelsInfo(ChannelsInfoRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("channel", req.getChannel(), form);
        return doPostForm(form, Methods.CHANNELS_INFO, ChannelsInfoResponse.class);
    }

    @Override
    public ChannelsListResponse channelsList(ChannelsListRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("exclude_archived", req.getExcludeArchived(), form);
        return doPostForm(form, Methods.CHANNELS_LIST, ChannelsListResponse.class);
    }

    @Override
    public ChannelsInviteResponse channelsInvite(ChannelsInviteRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("user", req.getUser(), form);
        return doPostForm(form, Methods.CHANNELS_INVITE, ChannelsInviteResponse.class);
    }

    @Override
    public ChannelsJoinResponse channelsJoin(ChannelsJoinRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("name", req.getName(), form);
        return doPostForm(form, Methods.CHANNELS_JOIN, ChannelsJoinResponse.class);
    }

    @Override
    public ChannelsKickResponse channelsKick(ChannelsKickRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("user", req.getUser(), form);
        return doPostForm(form, Methods.CHANNELS_KICK, ChannelsKickResponse.class);
    }

    @Override
    public ChannelsLeaveResponse channelsLeave(ChannelsLeaveRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("channel", req.getChannel(), form);
        return doPostForm(form, Methods.CHANNELS_LEAVE, ChannelsLeaveResponse.class);
    }

    @Override
    public ChannelsMarkResponse channelsMark(ChannelsMarkRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("ts", req.getTs(), form);
        return doPostForm(form, Methods.CHANNELS_MARK, ChannelsMarkResponse.class);
    }

    @Override
    public ChannelsRenameResponse channelsRename(ChannelsRenameRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("name", req.getName(), form);
        return doPostForm(form, Methods.CHANNELS_RENAME, ChannelsRenameResponse.class);
    }

    @Override
    public ChannelsSetPurposeResponse channelsSetPurpose(ChannelsSetPurposeRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("purpose", req.getPurpose(), form);
        return doPostForm(form, Methods.CHANNELS_SET_PURPOSE, ChannelsSetPurposeResponse.class);
    }

    @Override
    public ChannelsSetTopicResponse channelsSetTopic(ChannelsSetTopicRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("topic", req.getTopic(), form);
        return doPostForm(form, Methods.CHANNELS_SET_TOPIC, ChannelsSetTopicResponse.class);
    }

    @Override
    public ChannelsUnarchiveResponse channelsUnarchive(ChannelsUnarchiveRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("channel", req.getChannel(), form);
        return doPostForm(form, Methods.CHANNELS_UNARCHIVE, ChannelsUnarchiveResponse.class);
    }

    @Override
    public ChatDeleteResponse chatDelete(ChatDeleteRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("ts", req.getTs(), form);
        return doPostForm(form, Methods.CHAT_DELETE, ChatDeleteResponse.class);
    }

    @Override
    public ChatMeMessageResponse chatMeMessage(ChatMeMessageRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("text", req.getText(), form);
        return doPostForm(form, Methods.CHAT_ME_MESSAGE, ChatMeMessageResponse.class);
    }

    @Override
    public ChatPostMessageResponse chatPostMessage(ChatPostMessageRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("text", req.getText(), form);
        setIfNotNull("parse", req.getParse(), form);
        setIfNotNull("link_names", req.getLinkNames(), form);
        if (req.getAttachments() != null) {
            String json = GsonFactory.createSnakeCase().toJson(req.getAttachments());
            form.add("attachments", json);
        }
        setIfNotNull("unfurl_links", req.isUnfurlLinks(), form);
        setIfNotNull("unfurl_media", req.isUnfurlMedia(), form);
        setIfNotNull("username", req.getUsername(), form);
        setIfNotNull("as_user", req.isAsUser(), form);
        setIfNotNull("icon_url", req.getIconUrl(), form);
        setIfNotNull("icon_emoji", req.getIconEmoji(), form);
        return doPostForm(form, Methods.CHAT_POST_MESSAGE, ChatPostMessageResponse.class);
    }

    @Override
    public ChatUpdateResponse chatUpdate(ChatUpdateRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("ts", req.getTs(), form);
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("text", req.getText(), form);
        setIfNotNull("parse", req.getParse(), form);
        setIfNotNull("link_names", req.getLinkNames(), form);
        if (req.getAttachments() != null) {
            String json = GsonFactory.createSnakeCase().toJson(req.getAttachments());
            form.add("attachments", json);
        }
        setIfNotNull("as_user", req.isAsUser(), form);
        return doPostForm(form, Methods.CHAT_UPDATE, ChatUpdateResponse.class);
    }

    @Override
    public DndEndDndResponse dndEndDnd(DndEndDndRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        return doPostForm(form, Methods.DND_END_DND, DndEndDndResponse.class);
    }

    @Override
    public DndEndSnoozeResponse dndEndSnooze(DndEndSnoozeRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        return doPostForm(form, Methods.DND_END_SNOOZE, DndEndSnoozeResponse.class);
    }

    @Override
    public DndInfoResponse dndInfo(DndInfoRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("user", req.getUser(), form);
        return doPostForm(form, Methods.DND_INFO, DndInfoResponse.class);
    }

    @Override
    public DndSetSnoozeResponse dndSetSnooze(DndSetSnoozeRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("num_minutes", req.getNumMinutes(), form);
        return doPostForm(form, Methods.DND_SET_SNOOZE, DndSetSnoozeResponse.class);
    }

    @Override
    public DndTeamInfoResponse dndTeamInfo(DndTeamInfoRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        if (req.getUsers() != null) {
            setIfNotNull("user", req.getUsers().stream().collect(joining(",")), form);
        }
        return doPostForm(form, Methods.DND_TEAM_INFO, DndTeamInfoResponse.class);
    }

    @Override
    public EmojiListResponse emojiList(EmojiListRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        return doPostForm(form, Methods.EMOJI_LIST, EmojiListResponse.class);
    }

    @Override
    public GroupsArchiveResponse groupsArchive(GroupsArchiveRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("channel", req.getChannel(), form);
        return doPostForm(form, Methods.GROUPS_ARCHIVE, GroupsArchiveResponse.class);
    }

    @Override
    public GroupsCloseResponse groupsClose(GroupsCloseRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("channel", req.getChannel(), form);
        return doPostForm(form, Methods.GROUPS_CLOSE, GroupsCloseResponse.class);
    }

    @Override
    public GroupsCreateChildResponse groupsCreateChild(GroupsCreateChildRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("channel", req.getChannel(), form);
        return doPostForm(form, Methods.GROUPS_CREATE_CHILD, GroupsCreateChildResponse.class);
    }

    @Override
    public GroupsCreateResponse groupsCreate(GroupsCreateRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("name", req.getName(), form);
        return doPostForm(form, Methods.GROUPS_CREATE, GroupsCreateResponse.class);
    }

    @Override
    public GroupsHistoryResponse groupsHistory(GroupsHistoryRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("latest", req.getLatest(), form);
        setIfNotNull("oldest", req.getOldest(), form);
        setIfNotNull("inclusive", req.getInclusive(), form);
        setIfNotNull("count", req.getCount(), form);
        setIfNotNull("unreads", req.getUnreads(), form);
        return doPostForm(form, Methods.GROUPS_HISTORY, GroupsHistoryResponse.class);
    }

    @Override
    public GroupsInfoResponse groupsInfo(GroupsInfoRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("channel", req.getChannel(), form);
        return doPostForm(form, Methods.GROUPS_INFO, GroupsInfoResponse.class);
    }

    @Override
    public GroupsInviteResponse groupsInvite(GroupsInviteRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("user", req.getUser(), form);
        return doPostForm(form, Methods.GROUPS_INVITE, GroupsInviteResponse.class);
    }

    @Override
    public GroupsKickResponse groupsKick(GroupsKickRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("user", req.getUser(), form);
        return doPostForm(form, Methods.GROUPS_KICK, GroupsKickResponse.class);
    }

    @Override
    public GroupsLeaveResponse groupsLeave(GroupsLeaveRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("channel", req.getChannel(), form);
        return doPostForm(form, Methods.GROUPS_LEAVE, GroupsLeaveResponse.class);
    }

    @Override
    public GroupsListResponse groupsList(GroupsListRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("exclude_archived", req.getExcludeArchived(), form);
        return doPostForm(form, Methods.GROUPS_LIST, GroupsListResponse.class);
    }

    @Override
    public RTMStartResponse rtmStart(RTMStartRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        return doPostForm(form, Methods.RTM_START, RTMStartResponse.class);
    }

    @Override
    public UsersGetPresenceResponse usersGetPresence(UsersGetPresenceRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("user", req.getUser(), form);
        return doPostForm(form, Methods.USERS_GET_PRESENCE, UsersGetPresenceResponse.class);
    }

    @Override
    public UsersIdentityResponse usersIdentity(UsersIdentityRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        return doPostForm(form, Methods.USERS_IDENTITY, UsersIdentityResponse.class);
    }

    @Override
    public UsersInfoResponse usersInfo(UsersInfoRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("user", req.getUser(), form);
        return doPostForm(form, Methods.USERS_INFO, UsersInfoResponse.class);
    }

    @Override
    public UsersListResponse usersList(UsersListRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("presence", req.getPresence(), form);
        return doPostForm(form, Methods.USERS_LIST, UsersListResponse.class);
    }

    @Override
    public UsersSetActiveResponse usersSetActive(UsersSetActiveRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        return doPostForm(form, Methods.USERS_SET_ACTIVE, UsersSetActiveResponse.class);
    }

    @Override
    public UsersSetPresenceResponse usersSetPresence(UsersSetPresenceRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("presence", req.getPresence(), form);
        return doPostForm(form, Methods.USERS_SET_PRESENCE, UsersSetPresenceResponse.class);
    }

    @Override
    public UsersProfileGetResponse usersProfileGet(UsersProfileGetRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("user", req.getUser(), form);
        setIfNotNull("include_labels", req.getIncludeLabels(), form);
        return doPostForm(form, Methods.USERS_PROFILE_GET, UsersProfileGetResponse.class);
    }

    @Override
    public UsersProfileSetResponse usersProfileSet(UsersProfileSetRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("user", req.getUser(), form);
        if (req.getProfile() != null) {
            setIfNotNull("profile", GsonFactory.createSnakeCase().toJson(req.getProfile()), form);
        } else {
            setIfNotNull("name", req.getName(), form);
            setIfNotNull("value", req.getValue(), form);
        }
        return doPostForm(form, Methods.USERS_PROFILE_SET, UsersProfileSetResponse.class);
    }

    // ----------------------------------------------------------------------------------
    // private methods
    // ----------------------------------------------------------------------------------

    private static void setIfNotNull(String name, Object value, FormBody.Builder form) {
        if (value != null) {
            form.add(name, String.valueOf(value));
        }
    }

    private <T> T doPostForm(FormBody.Builder form, String endpoint, Class<T> clazz) throws IOException, SlackApiException {
        Response response = slackHttpClient.postForm(ENDPOINT_URL_PREFIX + endpoint, form.build());
        return buildResponse(response, clazz);
    }

    private <T> T buildResponse(Response response, Class<T> clazz) throws IOException, SlackApiException {
        if (response.code() == 200) {
            String json = response.body().string();
            log.debug("url: {}, response body: {}", response.request().url(), json);
            return GsonFactory.createSnakeCase().fromJson(json, clazz);
        } else {
            String json = response.body().string();
            throw new SlackApiException(response, json);
        }
    }

}
