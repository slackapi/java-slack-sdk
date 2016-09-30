package com.github.seratch.jslack.api.methods.impl;

import com.github.seratch.jslack.api.methods.Methods;
import com.github.seratch.jslack.api.methods.MethodsClient;
import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.request.api.ApiTestRequest;
import com.github.seratch.jslack.api.methods.request.auth.AuthRevokeRequest;
import com.github.seratch.jslack.api.methods.request.auth.AuthTestRequest;
import com.github.seratch.jslack.api.methods.request.bots.BotsInfoRequest;
import com.github.seratch.jslack.api.methods.request.channels.*;
import com.github.seratch.jslack.api.methods.request.chat.ChatDeleteRequest;
import com.github.seratch.jslack.api.methods.request.chat.ChatMeMessageRequest;
import com.github.seratch.jslack.api.methods.request.chat.ChatPostMessageRequest;
import com.github.seratch.jslack.api.methods.request.chat.ChatUpdateRequest;
import com.github.seratch.jslack.api.methods.request.dnd.*;
import com.github.seratch.jslack.api.methods.request.emoji.EmojiListRequest;
import com.github.seratch.jslack.api.methods.request.files.*;
import com.github.seratch.jslack.api.methods.request.files.comments.FilesCommentsAddRequest;
import com.github.seratch.jslack.api.methods.request.files.comments.FilesCommentsDeleteRequest;
import com.github.seratch.jslack.api.methods.request.files.comments.FilesCommentsEditRequest;
import com.github.seratch.jslack.api.methods.request.groups.*;
import com.github.seratch.jslack.api.methods.request.im.*;
import com.github.seratch.jslack.api.methods.request.mpim.*;
import com.github.seratch.jslack.api.methods.request.oauth.OAuthAccessRequest;
import com.github.seratch.jslack.api.methods.request.pins.PinsAddRequest;
import com.github.seratch.jslack.api.methods.request.pins.PinsListRequest;
import com.github.seratch.jslack.api.methods.request.pins.PinsRemoveRequest;
import com.github.seratch.jslack.api.methods.request.reactions.ReactionsAddRequest;
import com.github.seratch.jslack.api.methods.request.reactions.ReactionsGetRequest;
import com.github.seratch.jslack.api.methods.request.reactions.ReactionsListRequest;
import com.github.seratch.jslack.api.methods.request.reactions.ReactionsRemoveRequest;
import com.github.seratch.jslack.api.methods.request.reminders.*;
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
import com.github.seratch.jslack.api.methods.response.auth.AuthRevokeResponse;
import com.github.seratch.jslack.api.methods.response.auth.AuthTestResponse;
import com.github.seratch.jslack.api.methods.response.bots.BotsInfoResponse;
import com.github.seratch.jslack.api.methods.response.channels.*;
import com.github.seratch.jslack.api.methods.response.chat.ChatDeleteResponse;
import com.github.seratch.jslack.api.methods.response.chat.ChatMeMessageResponse;
import com.github.seratch.jslack.api.methods.response.chat.ChatPostMessageResponse;
import com.github.seratch.jslack.api.methods.response.chat.ChatUpdateResponse;
import com.github.seratch.jslack.api.methods.response.dnd.*;
import com.github.seratch.jslack.api.methods.response.emoji.EmojiListResponse;
import com.github.seratch.jslack.api.methods.response.files.*;
import com.github.seratch.jslack.api.methods.response.files.comments.FilesCommentsAddResponse;
import com.github.seratch.jslack.api.methods.response.files.comments.FilesCommentsDeleteResponse;
import com.github.seratch.jslack.api.methods.response.files.comments.FilesCommentsEditResponse;
import com.github.seratch.jslack.api.methods.response.groups.*;
import com.github.seratch.jslack.api.methods.response.im.*;
import com.github.seratch.jslack.api.methods.response.mpim.*;
import com.github.seratch.jslack.api.methods.response.oauth.OAuthAccessResponse;
import com.github.seratch.jslack.api.methods.response.pins.PinsAddResponse;
import com.github.seratch.jslack.api.methods.response.pins.PinsListResponse;
import com.github.seratch.jslack.api.methods.response.pins.PinsRemoveResponse;
import com.github.seratch.jslack.api.methods.response.reactions.ReactionsAddResponse;
import com.github.seratch.jslack.api.methods.response.reactions.ReactionsGetResponse;
import com.github.seratch.jslack.api.methods.response.reactions.ReactionsListResponse;
import com.github.seratch.jslack.api.methods.response.reactions.ReactionsRemoveResponse;
import com.github.seratch.jslack.api.methods.response.reminders.*;
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
import com.github.seratch.jslack.common.http.SlackHttpClient;
import com.github.seratch.jslack.common.json.GsonFactory;
import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

import static java.util.stream.Collectors.joining;

@Slf4j
public class MethodsClientImpl implements MethodsClient {

    public static final String ENDPOINT_URL_PREFIX = "https://slack.com/api/";

    private final SlackHttpClient slackHttpClient;

    public MethodsClientImpl(SlackHttpClient slackHttpClient) {
        this.slackHttpClient = slackHttpClient;
    }

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
    public FilesDeleteResponse filesDelete(FilesDeleteRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("file", req.getFile(), form);
        return doPostForm(form, Methods.FILES_DELETE, FilesDeleteResponse.class);
    }

    @Override
    public FilesInfoResponse filesInfo(FilesInfoRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("file", req.getFile(), form);
        setIfNotNull("count", req.getCount(), form);
        setIfNotNull("page", req.getPage(), form);
        return doPostForm(form, Methods.FILES_INFO, FilesInfoResponse.class);
    }

    @Override
    public FilesListResponse filesList(FilesListRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("user", req.getUser(), form);
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("ts_from", req.getTsFrom(), form);
        setIfNotNull("ts_to", req.getTsTo(), form);
        if (req.getTypes() != null) {
            setIfNotNull("types", req.getTypes().stream().collect(joining(",")), form);
        }
        setIfNotNull("count", req.getCount(), form);
        setIfNotNull("page", req.getPage(), form);
        return doPostForm(form, Methods.FILES_LIST, FilesListResponse.class);
    }

    @Override
    public FilesRevokePublicURLResponse filesRevokePublicURL(FilesRevokePublicURLRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("file", req.getFile(), form);
        return doPostForm(form, Methods.FILES_REVOKE_PUBLIC_URL, FilesRevokePublicURLResponse.class);
    }

    @Override
    public FilesSharedPublicURLResponse filesSharedPublicURL(FilesSharedPublicURLRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("file", req.getFile(), form);
        return doPostForm(form, Methods.FILES_SHARED_PUBLIC_URL, FilesSharedPublicURLResponse.class);
    }

    @Override
    public FilesUploadResponse filesUpload(FilesUploadRequest req) throws IOException, SlackApiException {
        if (req.getFile() != null) {
            MultipartBody.Builder form = new MultipartBody.Builder();
            setIfNotNull("token", req.getToken(), form);

            RequestBody file = RequestBody.create(MultipartBody.FORM, req.getFile());
            form.addFormDataPart("file", req.getFilename(), file);

            setIfNotNull("filetype", req.getFiletype(), form);
            setIfNotNull("filename", req.getFilename(), form);
            setIfNotNull("title", req.getTitle(), form);
            setIfNotNull("initial_comment", req.getInitialComment(), form);
            if (req.getChannels() != null) {
                setIfNotNull("channels", req.getChannels().stream().collect(joining(",")), form);
            }
            return doPostMultipart(form, Methods.FILES_UPLOAD, FilesUploadResponse.class);
        } else {
            FormBody.Builder form = new FormBody.Builder();
            setIfNotNull("token", req.getToken(), form);
            setIfNotNull("content", req.getContent(), form);
            setIfNotNull("filetype", req.getFiletype(), form);
            setIfNotNull("filename", req.getFilename(), form);
            setIfNotNull("title", req.getTitle(), form);
            setIfNotNull("initial_comment", req.getInitialComment(), form);
            if (req.getChannels() != null) {
                setIfNotNull("channels", req.getChannels().stream().collect(joining(",")), form);
            }
            return doPostForm(form, Methods.FILES_UPLOAD, FilesUploadResponse.class);
        }
    }

    @Override
    public FilesCommentsAddResponse filesCommentsAdd(FilesCommentsAddRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("file", req.getFile(), form);
        setIfNotNull("comment", req.getComment(), form);
        setIfNotNull("channel", req.getChannel(), form);
        return doPostForm(form, Methods.FILES_COMMENTS_ADD, FilesCommentsAddResponse.class);
    }

    @Override
    public FilesCommentsDeleteResponse filesCommentsDelete(FilesCommentsDeleteRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("file", req.getFile(), form);
        setIfNotNull("id", req.getId(), form);
        return doPostForm(form, Methods.FILES_COMMENTS_DELETE, FilesCommentsDeleteResponse.class);
    }

    @Override
    public FilesCommentsEditResponse filesCommentEdit(FilesCommentsEditRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("file", req.getFile(), form);
        setIfNotNull("comment", req.getComment(), form);
        setIfNotNull("id", req.getId(), form);
        return doPostForm(form, Methods.FILES_COMMENTS_EDIT, FilesCommentsEditResponse.class);
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
    public GroupsMarkResponse groupsMark(GroupsMarkRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("ts", req.getTs(), form);
        return doPostForm(form, Methods.GROUPS_MARK, GroupsMarkResponse.class);
    }

    @Override
    public GroupsOpenResponse groupsOpen(GroupsOpenRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("channel", req.getChannel(), form);
        return doPostForm(form, Methods.GROUPS_OPEN, GroupsOpenResponse.class);
    }

    @Override
    public GroupsRenameResponse groupsRename(GroupsRenameRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("name", req.getName(), form);
        return doPostForm(form, Methods.GROUPS_RENAME, GroupsRenameResponse.class);
    }

    @Override
    public GroupsSetPurposeResponse groupsSetPurpose(GroupsSetPurposeRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("purpose", req.getPurpose(), form);
        return doPostForm(form, Methods.GROUPS_SET_PURPOSE, GroupsSetPurposeResponse.class);
    }

    @Override
    public GroupsSetTopicResponse groupsSetTopic(GroupsSetTopicRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("topic", req.getTopic(), form);
        return doPostForm(form, Methods.GROUPS_SET_TOPIC, GroupsSetTopicResponse.class);
    }

    @Override
    public GroupsUnarchiveResponse groupsUnarchive(GroupsUnarchiveRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("channel", req.getChannel(), form);
        return doPostForm(form, Methods.GROUPS_UNARCHIVE, GroupsUnarchiveResponse.class);
    }

    @Override
    public ImCloseResponse imClose(ImCloseRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("channel", req.getChannel(), form);
        return doPostForm(form, Methods.IM_CLOSE, ImCloseResponse.class);
    }

    @Override
    public ImHistoryResponse imHistory(ImHistoryRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("latest", req.getLatest(), form);
        setIfNotNull("oldest", req.getOldest(), form);
        setIfNotNull("inclusive", req.getInclusive(), form);
        setIfNotNull("count", req.getCount(), form);
        setIfNotNull("unreads", req.getUnreads(), form);
        return doPostForm(form, Methods.IM_HISTORY, ImHistoryResponse.class);
    }

    @Override
    public ImListResponse imList(ImListRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        return doPostForm(form, Methods.IM_LIST, ImListResponse.class);
    }

    @Override
    public ImMarkResponse imMark(ImMarkRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("ts", req.getTs(), form);
        return doPostForm(form, Methods.IM_MARK, ImMarkResponse.class);
    }

    @Override
    public ImOpenResponse imOpen(ImOpenRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("user", req.getUser(), form);
        setIfNotNull("return_im", req.isReturnIm(), form);
        return doPostForm(form, Methods.IM_OPEN, ImOpenResponse.class);
    }

    @Override
    public MpimCloseResponse mpimClose(MpimCloseRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("channel", req.getChannel(), form);
        return doPostForm(form, Methods.MPIM_CLOSE, MpimCloseResponse.class);
    }

    @Override
    public MpimHistoryResponse mpimHistory(MpimHistoryRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("latest", req.getLatest(), form);
        setIfNotNull("oldest", req.getOldest(), form);
        setIfNotNull("inclusive", req.getInclusive(), form);
        setIfNotNull("count", req.getCount(), form);
        setIfNotNull("unreads", req.getUnreads(), form);
        return doPostForm(form, Methods.MPIM_HISTORY, MpimHistoryResponse.class);
    }

    @Override
    public MpimListResponse mpimList(MpimListRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        return doPostForm(form, Methods.MPIM_LIST, MpimListResponse.class);
    }

    @Override
    public MpimMarkResponse mpimMark(MpimMarkRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("ts", req.getTs(), form);
        return doPostForm(form, Methods.MPIM_MARK, MpimMarkResponse.class);
    }

    @Override
    public MpimOpenResponse mpimOpen(MpimOpenRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        if (req.getUsers() != null) {
            setIfNotNull("users", req.getUsers().stream().collect(joining(",")), form);
        }
        return doPostForm(form, Methods.MPIM_OPEN, MpimOpenResponse.class);
    }

    @Override
    public OAuthAccessResponse oauthAccess(OAuthAccessRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("client_id", req.getClientId(), form);
        setIfNotNull("client_secret", req.getClientSecret(), form);
        setIfNotNull("code", req.getCode(), form);
        setIfNotNull("redirect_uri", req.getRedirectUri(), form);
        return doPostForm(form, Methods.OAUTH_ACCESS, OAuthAccessResponse.class);
    }

    @Override
    public PinsAddResponse pinsAdd(PinsAddRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("file", req.getFile(), form);
        setIfNotNull("file_comment", req.getFileComment(), form);
        setIfNotNull("timestamp", req.getTimestamp(), form);
        return doPostForm(form, Methods.PINS_ADD, PinsAddResponse.class);
    }

    @Override
    public PinsListResponse pinsList(PinsListRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("channel", req.getChannel(), form);
        return doPostForm(form, Methods.PINS_LIST, PinsListResponse.class);
    }

    @Override
    public PinsRemoveResponse pinsRemove(PinsRemoveRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("file", req.getFile(), form);
        setIfNotNull("file_comment", req.getFileComment(), form);
        setIfNotNull("timestamp", req.getTimestamp(), form);
        return doPostForm(form, Methods.PINS_REMOVE, PinsRemoveResponse.class);
    }

    @Override
    public ReactionsAddResponse reactionsAdd(ReactionsAddRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("name", req.getName(), form);
        setIfNotNull("file", req.getFile(), form);
        setIfNotNull("file_comment", req.getFileComment(), form);
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("timestamp", req.getTimestamp(), form);
        return doPostForm(form, Methods.REACTIONS_ADD, ReactionsAddResponse.class);
    }

    @Override
    public ReactionsGetResponse reactionsGet(ReactionsGetRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("file", req.getFile(), form);
        setIfNotNull("file_comment", req.getFileComment(), form);
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("timestamp", req.getTimestamp(), form);
        setIfNotNull("full", req.isFull(), form);
        return doPostForm(form, Methods.REACTIONS_GET, ReactionsGetResponse.class);
    }

    @Override
    public ReactionsListResponse reactionsList(ReactionsListRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("user", req.getUser(), form);
        setIfNotNull("full", req.isFull(), form);
        setIfNotNull("count", req.getCount(), form);
        setIfNotNull("page", req.getPage(), form);
        return doPostForm(form, Methods.REACTIONS_LIST, ReactionsListResponse.class);
    }

    @Override
    public ReactionsRemoveResponse reactionsRemove(ReactionsRemoveRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("name", req.getName(), form);
        setIfNotNull("file", req.getFile(), form);
        setIfNotNull("file_comment", req.getFileComment(), form);
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("timestamp", req.getTimestamp(), form);
        return doPostForm(form, Methods.REACTIONS_REMOVE, ReactionsRemoveResponse.class);
    }

    @Override
    public RemindersAddResponse remindersAdd(RemindersAddRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("text", req.getText(), form);
        setIfNotNull("time", req.getTime(), form);
        setIfNotNull("user", req.getUser(), form);
        return doPostForm(form, Methods.REMINDERS_ADD, RemindersAddResponse.class);
    }

    @Override
    public RemindersCompleteResponse remindersComplete(RemindersCompleteRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("reminder", req.getReminder(), form);
        return doPostForm(form, Methods.REMINDERS_COMPLETE, RemindersCompleteResponse.class);
    }

    @Override
    public RemindersDeleteResponse remindersDelete(RemindersDeleteRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("reminder", req.getReminder(), form);
        return doPostForm(form, Methods.REMINDERS_DELETE, RemindersDeleteResponse.class);
    }

    @Override
    public RemindersInfoResponse remindersInfo(RemindersInfoRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("reminder", req.getReminder(), form);
        return doPostForm(form, Methods.REMINDERS_INFO, RemindersInfoResponse.class);
    }

    @Override
    public RemindersListResponse remindersList(RemindersListRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        return doPostForm(form, Methods.REMINDERS_LIST, RemindersListResponse.class);
    }

    @Override
    public RTMStartResponse rtmStart(RTMStartRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        return doPostForm(form, Methods.RTM_START, RTMStartResponse.class);
    }

    @Override
    public SearchAllResponse searchAll(SearchAllRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("query", req.getQuery(), form);
        setIfNotNull("sort", req.getSort(), form);
        setIfNotNull("sort_dir", req.getSortDir(), form);
        setIfNotNull("highlight", req.getHighlight(), form);
        setIfNotNull("count", req.getCount(), form);
        setIfNotNull("page", req.getPage(), form);
        return doPostForm(form, Methods.SEARCH_ALL, SearchAllResponse.class);
    }

    @Override
    public SearchMessagesResponse searchMessages(SearchMessagesRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("query", req.getQuery(), form);
        setIfNotNull("sort", req.getSort(), form);
        setIfNotNull("sort_dir", req.getSortDir(), form);
        setIfNotNull("highlight", req.getHighlight(), form);
        setIfNotNull("count", req.getCount(), form);
        setIfNotNull("page", req.getPage(), form);
        return doPostForm(form, Methods.SEARCH_MESSAGES, SearchMessagesResponse.class);
    }

    @Override
    public SearchFilesResponse searchFiles(SearchFilesRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("query", req.getQuery(), form);
        setIfNotNull("sort", req.getSort(), form);
        setIfNotNull("sort_dir", req.getSortDir(), form);
        setIfNotNull("highlight", req.getHighlight(), form);
        setIfNotNull("count", req.getCount(), form);
        setIfNotNull("page", req.getPage(), form);
        return doPostForm(form, Methods.SEARCH_FILES, SearchFilesResponse.class);
    }

    @Override
    public StarsAddResponse starsAdd(StarsAddRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("file", req.getFile(), form);
        setIfNotNull("file_comment", req.getFileComment(), form);
        setIfNotNull("timestamp", req.getTimestamp(), form);
        return doPostForm(form, Methods.STARS_ADD, StarsAddResponse.class);
    }

    @Override
    public StarsListResponse starsList(StarsListRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("count", req.getCount(), form);
        setIfNotNull("page", req.getPage(), form);
        return doPostForm(form, Methods.STARS_LIST, StarsListResponse.class);
    }

    @Override
    public StarsRemoveResponse starsRemove(StarsRemoveRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("file", req.getFile(), form);
        setIfNotNull("file_comment", req.getFileComment(), form);
        setIfNotNull("timestamp", req.getTimestamp(), form);
        return doPostForm(form, Methods.STARS_REMOVE, StarsRemoveResponse.class);
    }

    @Override
    public TeamAccessLogsResponse teamAccessLogs(TeamAccessLogsRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("count", req.getCount(), form);
        setIfNotNull("page", req.getPage(), form);
        return doPostForm(form, Methods.TEAM_ACCESS_LOGS, TeamAccessLogsResponse.class);
    }

    @Override
    public TeamBillableInfoResponse teamBillableInfo(TeamBillableInfoRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("user", req.getUser(), form);
        return doPostForm(form, Methods.TEAM_BILLABLE_INFO, TeamBillableInfoResponse.class);
    }

    @Override
    public TeamInfoResponse teamInfo(TeamInfoRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        return doPostForm(form, Methods.TEAM_INFO, TeamInfoResponse.class);
    }

    @Override
    public TeamIntegrationLogsResponse teamIntegrationLogs(TeamIntegrationLogsRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("service_id", req.getServiceId(), form);
        setIfNotNull("user", req.getUser(), form);
        setIfNotNull("change_type", req.getChangeType(), form);
        setIfNotNull("count", req.getCount(), form);
        setIfNotNull("page", req.getPage(), form);
        return doPostForm(form, Methods.TEAM_INTEGRATION_LOGS, TeamIntegrationLogsResponse.class);
    }

    @Override
    public TeamProfileGetResponse teamProfileGet(TeamProfileGetRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("visibility", req.getVisibility(), form);
        return doPostForm(form, Methods.TEAM_PROFILE_GET, TeamProfileGetResponse.class);
    }

    @Override
    public UsergroupsCreateResponse usergroupsCreate(UsergroupsCreateRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("name", req.getName(), form);
        setIfNotNull("handle", req.getHandle(), form);
        setIfNotNull("description", req.getDescription(), form);
        if (req.getChannels() != null) {
            setIfNotNull("channels", req.getChannels().stream().collect(joining(",")), form);
        }
        setIfNotNull("include_count", req.getIncludeCount(), form);
        return doPostForm(form, Methods.USERGROUPS_CREATE, UsergroupsCreateResponse.class);
    }

    @Override
    public UsergroupsDisableResponse usergroupsDisable(UsergroupsDisableRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("include_count", req.getIncludeCount(), form);
        return doPostForm(form, Methods.USERGROUPS_DISABLE, UsergroupsDisableResponse.class);
    }

    @Override
    public UsergroupsEnableResponse usergroupsEnable(UsergroupsEnableRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("include_count", req.getIncludeCount(), form);
        return doPostForm(form, Methods.USERGROUPS_ENABLE, UsergroupsEnableResponse.class);
    }

    @Override
    public UsergroupsListResponse usergroupsList(UsergroupsListRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("include_disabled", req.getIncludeDisabled(), form);
        setIfNotNull("include_count", req.getIncludeCount(), form);
        setIfNotNull("include_users", req.getIncludeUsers(), form);
        return doPostForm(form, Methods.USERGROUPS_LIST, UsergroupsListResponse.class);
    }

    @Override
    public UsergroupsUpdateResponse usergroupsUpdate(UsergroupsUpdateRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("usergroup", req.getUsergroup(), form);
        setIfNotNull("name", req.getName(), form);
        setIfNotNull("handle", req.getHandle(), form);
        setIfNotNull("description", req.getDescription(), form);
        if (req.getChannels() != null) {
            setIfNotNull("channels", req.getChannels().stream().collect(joining(",")), form);
        }
        setIfNotNull("include_count", req.getIncludeCount(), form);
        return doPostForm(form, Methods.USERGROUPS_UPDATE, UsergroupsUpdateResponse.class);
    }

    @Override
    public UsergroupUsersListResponse usergroupUsersList(UsergroupUsersListRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("usergroup", req.getUsergroup(), form);
        setIfNotNull("include_disabled", req.getIncludeDisabled(), form);
        return doPostForm(form, Methods.USERGROUPS_USERS_LIST, UsergroupUsersListResponse.class);
    }

    @Override
    public UsergroupUsersUpdateResponse usergroupUsersUpdate(UsergroupUsersUpdateRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("token", req.getToken(), form);
        setIfNotNull("usergroup", req.getUsergroup(), form);
        if (req.getUsers() != null) {
            setIfNotNull("users", req.getUsers().stream().collect(joining(",")), form);
        }
        setIfNotNull("include_count", req.getIncludeCount(), form);
        return doPostForm(form, Methods.USERGROUPS_USERS_UPDATE, UsergroupUsersUpdateResponse.class);
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

    private static void setIfNotNull(String name, Object value, MultipartBody.Builder form) {
        if (value != null) {
            form.addFormDataPart(name, String.valueOf(value));
        }
    }

    private <T> T doPostForm(FormBody.Builder form, String endpoint, Class<T> clazz) throws IOException, SlackApiException {
        Response response = slackHttpClient.postForm(ENDPOINT_URL_PREFIX + endpoint, form.build());
        return SlackHttpClient.buildJsonResponse(response, clazz);
    }

    private <T> T doPostMultipart(MultipartBody.Builder form, String endpoint, Class<T> clazz) throws IOException, SlackApiException {
        form.setType(MultipartBody.FORM);
        Response response = slackHttpClient.postMultipart(ENDPOINT_URL_PREFIX + endpoint, form.build());
        return SlackHttpClient.buildJsonResponse(response, clazz);
    }


}
