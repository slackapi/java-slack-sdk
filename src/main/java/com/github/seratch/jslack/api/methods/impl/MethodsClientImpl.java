package com.github.seratch.jslack.api.methods.impl;

import com.github.seratch.jslack.api.methods.Methods;
import com.github.seratch.jslack.api.methods.MethodsClient;
import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.request.api.ApiTestRequest;
import com.github.seratch.jslack.api.methods.request.apps.permissions.AppsPermissionsInfoRequest;
import com.github.seratch.jslack.api.methods.request.apps.permissions.AppsPermissionsRequestRequest;
import com.github.seratch.jslack.api.methods.request.auth.AuthRevokeRequest;
import com.github.seratch.jslack.api.methods.request.auth.AuthTestRequest;
import com.github.seratch.jslack.api.methods.request.bots.BotsInfoRequest;
import com.github.seratch.jslack.api.methods.request.channels.*;
import com.github.seratch.jslack.api.methods.request.chat.*;
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
import com.github.seratch.jslack.api.methods.response.apps.permissions.AppsPermissionsInfoResponse;
import com.github.seratch.jslack.api.methods.response.apps.permissions.AppsPermissionsRequestResponse;
import com.github.seratch.jslack.api.methods.response.auth.AuthRevokeResponse;
import com.github.seratch.jslack.api.methods.response.auth.AuthTestResponse;
import com.github.seratch.jslack.api.methods.response.bots.BotsInfoResponse;
import com.github.seratch.jslack.api.methods.response.channels.*;
import com.github.seratch.jslack.api.methods.response.chat.*;
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
import com.github.seratch.jslack.api.model.ConversationType;
import com.github.seratch.jslack.common.http.SlackHttpClient;
import com.github.seratch.jslack.common.json.GsonFactory;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.*;

@Slf4j
public class MethodsClientImpl implements MethodsClient {

    private String endpointUrlPrefix = "https://slack.com/api/";

    private final SlackHttpClient slackHttpClient;

    public MethodsClientImpl(SlackHttpClient slackHttpClient) {
        this.slackHttpClient = slackHttpClient;
    }

    @Override
    public void setEndpointUrlPrefix(String endpointUrlPrefix) {
        this.endpointUrlPrefix = endpointUrlPrefix;
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
    public AppsPermissionsInfoResponse appsPermissionsInfo(AppsPermissionsInfoRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        return doPostFormWithToken(form, Methods.APPS_PERMISSIONS_INFO, req.getToken(), AppsPermissionsInfoResponse.class);
    }

    @Override
    public AppsPermissionsRequestResponse appsPermissionsRequest(AppsPermissionsRequestRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("trigger_id", req.getTriggerId(), form);
        if (req.getScopes() != null) {
            setIfNotNull("scopes", req.getScopes().stream().collect(joining(",")), form);
        }
        return doPostFormWithToken(form, Methods.APPS_PERMISSIONS_REQUEST, req.getToken(), AppsPermissionsRequestResponse.class);
    }

    @Override
    public AuthRevokeResponse authRevoke(AuthRevokeRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("test", req.isTest(), form);
        return doPostFormWithToken(form, Methods.AUTH_REVOKE, req.getToken(), AuthRevokeResponse.class);
    }

    @Override
    public AuthTestResponse authTest(AuthTestRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        return doPostFormWithToken(form, Methods.AUTH_TEST, req.getToken(), AuthTestResponse.class);
    }

    @Override
    public BotsInfoResponse botsInfo(BotsInfoRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("bot", req.getBot(), form);
        return doPostFormWithToken(form, Methods.BOTS_INFO, req.getToken(), BotsInfoResponse.class);
    }

    @Override
    public ChannelsArchiveResponse channelsArchive(ChannelsArchiveRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        return doPostFormWithToken(form, Methods.CHANNELS_ARCHIVE, req.getToken(), ChannelsArchiveResponse.class);
    }

    @Override
    public ChannelsCreateResponse channelsCreate(ChannelsCreateRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("name", req.getName(), form);
        setIfNotNull("validate", req.isValidate(), form);
        return doPostFormWithToken(form, Methods.CHANNELS_CREATE, req.getToken(), ChannelsCreateResponse.class);
    }

    @Override
    public ChannelsHistoryResponse channelsHistory(ChannelsHistoryRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("latest", req.getLatest(), form);
        setIfNotNull("oldest", req.getOldest(), form);
        setIfNotNull("inclusive", req.isInclusive(), form);
        setIfNotNull("count", req.getCount(), form);
        setIfNotNull("unreads", req.isUnreads(), form);
        return doPostFormWithToken(form, Methods.CHANNELS_HISTORY, req.getToken(), ChannelsHistoryResponse.class);
    }

    @Override
    public ChannelsRepliesResponse channelsReplies(ChannelsRepliesRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("thread_ts", req.getThreadTs(), form);
        return doPostFormWithToken(form, Methods.CHANNELS_REPLIES, req.getToken(), ChannelsRepliesResponse.class);
    }

    @Override
    public ChannelsInfoResponse channelsInfo(ChannelsInfoRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("include_locale", req.isIncludeLocale(), form);
        return doPostFormWithToken(form, Methods.CHANNELS_INFO, req.getToken(), ChannelsInfoResponse.class);
    }

    @Override
    public ChannelsListResponse channelsList(ChannelsListRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("limit", req.getLimit(), form);
        setIfNotNull("cursor", req.getCursor(), form);
        setIfNotNull("exclude_members", req.isExcludeMembers(), form);
        setIfNotNull("exclude_archived", req.isExcludeArchived(), form);
        return doPostFormWithToken(form, Methods.CHANNELS_LIST, req.getToken(), ChannelsListResponse.class);
    }

    @Override
    public ChannelsInviteResponse channelsInvite(ChannelsInviteRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("user", req.getUser(), form);
        return doPostFormWithToken(form, Methods.CHANNELS_INVITE, req.getToken(), ChannelsInviteResponse.class);
    }

    @Override
    public ChannelsJoinResponse channelsJoin(ChannelsJoinRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("name", req.getName(), form);
        setIfNotNull("validate", req.isValidate(), form);
        return doPostFormWithToken(form, Methods.CHANNELS_JOIN, req.getToken(), ChannelsJoinResponse.class);
    }

    @Override
    public ChannelsKickResponse channelsKick(ChannelsKickRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("user", req.getUser(), form);
        return doPostFormWithToken(form, Methods.CHANNELS_KICK, req.getToken(), ChannelsKickResponse.class);
    }

    @Override
    public ChannelsLeaveResponse channelsLeave(ChannelsLeaveRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        return doPostFormWithToken(form, Methods.CHANNELS_LEAVE, req.getToken(), ChannelsLeaveResponse.class);
    }

    @Override
    public ChannelsMarkResponse channelsMark(ChannelsMarkRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("ts", req.getTs(), form);
        return doPostFormWithToken(form, Methods.CHANNELS_MARK, req.getToken(), ChannelsMarkResponse.class);
    }

    @Override
    public ChannelsRenameResponse channelsRename(ChannelsRenameRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("name", req.getName(), form);
        setIfNotNull("validate", req.isValidate(), form);
        return doPostFormWithToken(form, Methods.CHANNELS_RENAME, req.getToken(), ChannelsRenameResponse.class);
    }

    @Override
    public ChannelsSetPurposeResponse channelsSetPurpose(ChannelsSetPurposeRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("purpose", req.getPurpose(), form);
        return doPostFormWithToken(form, Methods.CHANNELS_SET_PURPOSE, req.getToken(), ChannelsSetPurposeResponse.class);
    }

    @Override
    public ChannelsSetTopicResponse channelsSetTopic(ChannelsSetTopicRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("topic", req.getTopic(), form);
        return doPostFormWithToken(form, Methods.CHANNELS_SET_TOPIC, req.getToken(), ChannelsSetTopicResponse.class);
    }

    @Override
    public ChannelsUnarchiveResponse channelsUnarchive(ChannelsUnarchiveRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        return doPostFormWithToken(form, Methods.CHANNELS_UNARCHIVE, req.getToken(), ChannelsUnarchiveResponse.class);
    }

    @Override
    public ChatGetPermalinkResponse chatGetPermalink(ChatGetPermalinkRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("message_ts", req.getMessageTs(), form);
        return doPostFormWithToken(form, Methods.CHAT_GET_PERMALINK, req.getToken(), ChatGetPermalinkResponse.class);
    }

    @Override
    public ChatDeleteResponse chatDelete(ChatDeleteRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("ts", req.getTs(), form);
        return doPostFormWithToken(form, Methods.CHAT_DELETE, req.getToken(), ChatDeleteResponse.class);
    }

    @Override
    public ChatMeMessageResponse chatMeMessage(ChatMeMessageRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("text", req.getText(), form);
        return doPostFormWithToken(form, Methods.CHAT_ME_MESSAGE, req.getToken(), ChatMeMessageResponse.class);
    }

    @Override
    public ChatPostEphemeralResponse chatPostEphemeral(ChatPostEphemeralRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("text", req.getText(), form);
        setIfNotNull("user", req.getUser(), form);
        setIfNotNull("as_user", req.isAsUser(), form);
        if (req.getAttachments() != null) {
            String json = GsonFactory.createSnakeCase().toJson(req.getAttachments());
            form.add("attachments", json);
        }
        setIfNotNull("link_names", req.isLinkNames(), form);
        setIfNotNull("parse", req.getParse(), form);
        return doPostFormWithToken(form, Methods.CHAT_POST_EPHEMERAL, req.getToken(), ChatPostEphemeralResponse.class);
    }

    @Override
    public ChatPostMessageResponse chatPostMessage(ChatPostMessageRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("thread_ts", req.getThreadTs(), form);
        setIfNotNull("text", req.getText(), form);
        setIfNotNull("parse", req.getParse(), form);
        setIfNotNull("link_names", req.isLinkNames(), form);
        setIfNotNull("mrkdwn", req.isMrkdwn(), form);
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
        setIfNotNull("thread_ts", req.getThreadTs(), form);
        setIfNotNull("reply_broadcast", req.isReplyBroadcast(), form);
        return doPostFormWithToken(form, Methods.CHAT_POST_MESSAGE, req.getToken(), ChatPostMessageResponse.class);
    }

    @Override
    public ChatUpdateResponse chatUpdate(ChatUpdateRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("ts", req.getTs(), form);
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("text", req.getText(), form);
        setIfNotNull("parse", req.getParse(), form);
        setIfNotNull("link_names", req.isLinkNames(), form);
        if (req.getAttachments() != null) {
            String json = GsonFactory.createSnakeCase().toJson(req.getAttachments());
            form.add("attachments", json);
        }
        setIfNotNull("as_user", req.isAsUser(), form);
        return doPostFormWithToken(form, Methods.CHAT_UPDATE, req.getToken(), ChatUpdateResponse.class);
    }

    @Override
    public ChatUnfurlResponse chatUnfurl(ChatUnfurlRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("ts", req.getTs(), form);
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("unfurls", req.getUnfurls(), form);
        setIfNotNull("user_auth_required", req.isUserAuthRequired(), form);
        setIfNotNull("user_auth_message", req.getUserAuthMessage(), form);
        setIfNotNull("user_auth_url", req.getUserAuthUrl(), form);
        return doPostFormWithToken(form, Methods.CHAT_UNFURL, req.getToken(), ChatUnfurlResponse.class);
    }

    @Override
    public ConversationsArchiveResponse conversationsArchive(ConversationsArchiveRequest req)
            throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        return doPostFormWithToken(form, Methods.CONVERSATIONS_ARCHIVE, req.getToken(), ConversationsArchiveResponse.class);
    }

    @Override
    public ConversationsCloseResponse conversationsClose(ConversationsCloseRequest req)
            throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        return doPostFormWithToken(form, Methods.CONVERSATIONS_CLOSE, req.getToken(), ConversationsCloseResponse.class);
    }

    @Override
    public ConversationsCreateResponse conversationsCreate(ConversationsCreateRequest req)
            throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("name", req.getName(), form);
        setIfNotNull("is_private", req.isPrivate(), form);
        return doPostFormWithToken(form, Methods.CONVERSATIONS_CREATE, req.getToken(), ConversationsCreateResponse.class);
    }

    @Override
    public ConversationsHistoryResponse conversationsHistory(ConversationsHistoryRequest req)
            throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("cursor", req.getCursor(), form);
        setIfNotNull("latest", req.getLatest(), form);
        setIfNotNull("limit", req.getLimit(), form);
        setIfNotNull("oldest", req.getOldest(), form);
        setIfNotNull("inclusive", req.isInclusive(), form);
        return doPostFormWithToken(form, Methods.CONVERSATIONS_HISTORY, req.getToken(), ConversationsHistoryResponse.class);
    }

    @Override
    public ConversationsInfoResponse conversationsInfo(ConversationsInfoRequest req)
            throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("include_locale", req.isIncludeLocale(), form);
        return doPostFormWithToken(form, Methods.CONVERSATIONS_INFO, req.getToken(), ConversationsInfoResponse.class);
    }

    @Override
    public ConversationsInviteResponse conversationsInvite(ConversationsInviteRequest req)
            throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        if (req.getUsers() != null) {
            setIfNotNull("users", req.getUsers().stream().collect(joining(",")), form);
        }
        return doPostFormWithToken(form, Methods.CONVERSATIONS_INVITE, req.getToken(), ConversationsInviteResponse.class);
    }

    @Override
    public ConversationsJoinResponse conversationsJoin(ConversationsJoinRequest req)
            throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        return doPostFormWithToken(form, Methods.CONVERSATIONS_JOIN, req.getToken(), ConversationsJoinResponse.class);
    }

    @Override
    public ConversationsKickResponse conversationsKick(ConversationsKickRequest req)
            throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("user", req.getUser(), form);
        return doPostFormWithToken(form, Methods.CONVERSATIONS_KICK, req.getToken(), ConversationsKickResponse.class);
    }

    @Override
    public ConversationsLeaveResponse conversationsLeave(ConversationsLeaveRequest req)
            throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        return doPostFormWithToken(form, Methods.CONVERSATIONS_LEAVE, req.getToken(), ConversationsLeaveResponse.class);
    }

    @Override
    public ConversationsListResponse conversationsList(ConversationsListRequest req)
            throws IOException, SlackApiException {
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
        return doPostFormWithToken(form, Methods.CONVERSATIONS_LIST, req.getToken(), ConversationsListResponse.class);
    }

    @Override
    public ConversationsMembersResponse conversationsMembers(ConversationsMembersRequest req)
            throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("cursor", req.getCursor(), form);
        setIfNotNull("limit", req.getLimit(), form);
        return doPostFormWithToken(form, Methods.CONVERSATIONS_MEMBERS, req.getToken(), ConversationsMembersResponse.class);
    }

    @Override
    public ConversationsOpenResponse conversationsOpen(ConversationsOpenRequest req)
            throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("return_im", req.isReturnIm(), form);
        if (req.getUsers() != null) {
            setIfNotNull("users", req.getUsers().stream().collect(joining(",")), form);
        }
        return doPostFormWithToken(form, Methods.CONVERSATIONS_OPEN, req.getToken(), ConversationsOpenResponse.class);
    }

    @Override
    public ConversationsRenameResponse conversationsRename(ConversationsRenameRequest req)
            throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("name", req.getName(), form);
        return doPostFormWithToken(form, Methods.CONVERSATIONS_RENAME, req.getToken(), ConversationsRenameResponse.class);
    }

    @Override
    public ConversationsRepliesResponse conversationsReplies(ConversationsRepliesRequest req)
            throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("ts", req.getTs(), form);
        setIfNotNull("cursor", req.getCursor(), form);
        setIfNotNull("limit", req.getLimit(), form);
        return doPostFormWithToken(form, Methods.CONVERSATIONS_REPLIES, req.getToken(), ConversationsRepliesResponse.class);
    }

    @Override
    public ConversationsSetPurposeResponse conversationsSetPurpose(ConversationsSetPurposeRequest req)
            throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("purpose", req.getPurpose(), form);
        return doPostFormWithToken(form, Methods.CONVERSATIONS_SET_PURPOSE, req.getToken(), ConversationsSetPurposeResponse.class);
    }

    @Override
    public ConversationsSetTopicResponse conversationsSetTopic(ConversationsSetTopicRequest req)
            throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("topic", req.getTopic(), form);
        return doPostFormWithToken(form, Methods.CONVERSATIONS_SET_TOPIC, req.getToken(), ConversationsSetTopicResponse.class);
    }

    @Override
    public ConversationsUnarchiveResponse conversationsUnarchive(ConversationsUnarchiveRequest req)
            throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        return doPostFormWithToken(form, Methods.CONVERSATIONS_UNARCHIVE, req.getToken(), ConversationsUnarchiveResponse.class);
    }

    @Override
    public DialogOpenResponse dialogOpen(DialogOpenRequest req)
            throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("trigger_id", req.getTriggerId(), form);
        if (req.getDialog() != null) {
            String json = GsonFactory.createSnakeCase().toJson(req.getDialog());
            form.add("dialog", json);
        }

        return doPostFormWithToken(form, Methods.DIALOG_OPEN, req.getToken(), DialogOpenResponse.class);
    }

    @Override
    public DndEndDndResponse dndEndDnd(DndEndDndRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        return doPostFormWithToken(form, Methods.DND_END_DND, req.getToken(), DndEndDndResponse.class);
    }

    @Override
    public DndEndSnoozeResponse dndEndSnooze(DndEndSnoozeRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        return doPostFormWithToken(form, Methods.DND_END_SNOOZE, req.getToken(), DndEndSnoozeResponse.class);
    }

    @Override
    public DndInfoResponse dndInfo(DndInfoRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("user", req.getUser(), form);
        return doPostFormWithToken(form, Methods.DND_INFO, req.getToken(), DndInfoResponse.class);
    }

    @Override
    public DndSetSnoozeResponse dndSetSnooze(DndSetSnoozeRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("num_minutes", req.getNumMinutes(), form);
        return doPostFormWithToken(form, Methods.DND_SET_SNOOZE, req.getToken(), DndSetSnoozeResponse.class);
    }

    @Override
    public DndTeamInfoResponse dndTeamInfo(DndTeamInfoRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        if (req.getUsers() != null) {
            setIfNotNull("user", req.getUsers().stream().collect(joining(",")), form);
        }
        return doPostFormWithToken(form, Methods.DND_TEAM_INFO, req.getToken(), DndTeamInfoResponse.class);
    }

    @Override
    public EmojiListResponse emojiList(EmojiListRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        return doPostFormWithToken(form, Methods.EMOJI_LIST, req.getToken(), EmojiListResponse.class);
    }

    @Override
    public FilesDeleteResponse filesDelete(FilesDeleteRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("file", req.getFile(), form);
        return doPostFormWithToken(form, Methods.FILES_DELETE, req.getToken(), FilesDeleteResponse.class);
    }

    @Override
    public FilesInfoResponse filesInfo(FilesInfoRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("file", req.getFile(), form);
        setIfNotNull("count", req.getCount(), form);
        setIfNotNull("page", req.getPage(), form);
        return doPostFormWithToken(form, Methods.FILES_INFO, req.getToken(), FilesInfoResponse.class);
    }

    @Override
    public FilesListResponse filesList(FilesListRequest req) throws IOException, SlackApiException {
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
        return doPostFormWithToken(form, Methods.FILES_LIST, req.getToken(), FilesListResponse.class);
    }

    @Override
    public FilesRevokePublicURLResponse filesRevokePublicURL(FilesRevokePublicURLRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("file", req.getFile(), form);
        return doPostFormWithToken(form, Methods.FILES_REVOKE_PUBLIC_URL, req.getToken(), FilesRevokePublicURLResponse.class);
    }

    @Override
    public FilesSharedPublicURLResponse filesSharedPublicURL(FilesSharedPublicURLRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("file", req.getFile(), form);
        return doPostFormWithToken(form, Methods.FILES_SHARED_PUBLIC_URL, req.getToken(), FilesSharedPublicURLResponse.class);
    }

    @Override
    public FilesUploadResponse filesUpload(FilesUploadRequest req) throws IOException, SlackApiException {
        if (req.getFile() != null) {
            MultipartBody.Builder form = new MultipartBody.Builder();

            RequestBody file = RequestBody.create(MultipartBody.FORM, req.getFile());
            form.addFormDataPart("file", req.getFilename(), file);

            setIfNotNull("filetype", req.getFiletype(), form);
            setIfNotNull("filename", req.getFilename(), form);
            setIfNotNull("title", req.getTitle(), form);
            setIfNotNull("initial_comment", req.getInitialComment(), form);
            if (req.getChannels() != null) {
                setIfNotNull("channels", req.getChannels().stream().collect(joining(",")), form);
            }
            return doPostMultipart(form, Methods.FILES_UPLOAD, req.getToken(), FilesUploadResponse.class);
        } else {
            FormBody.Builder form = new FormBody.Builder();

            setIfNotNull("content", req.getContent(), form);
            setIfNotNull("filetype", req.getFiletype(), form);
            setIfNotNull("filename", req.getFilename(), form);
            setIfNotNull("title", req.getTitle(), form);
            setIfNotNull("initial_comment", req.getInitialComment(), form);
            if (req.getChannels() != null) {
                setIfNotNull("channels", req.getChannels().stream().collect(joining(",")), form);
            }
            return doPostFormWithToken(form, Methods.FILES_UPLOAD, req.getToken(), FilesUploadResponse.class);
        }
    }

    @Override
    public FilesCommentsAddResponse filesCommentsAdd(FilesCommentsAddRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("file", req.getFile(), form);
        setIfNotNull("comment", req.getComment(), form);
        return doPostFormWithToken(form, Methods.FILES_COMMENTS_ADD, req.getToken(), FilesCommentsAddResponse.class);
    }

    @Override
    public FilesCommentsDeleteResponse filesCommentsDelete(FilesCommentsDeleteRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("file", req.getFile(), form);
        setIfNotNull("id", req.getId(), form);
        return doPostFormWithToken(form, Methods.FILES_COMMENTS_DELETE, req.getToken(), FilesCommentsDeleteResponse.class);
    }

    @Override
    public FilesCommentsEditResponse filesCommentEdit(FilesCommentsEditRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("file", req.getFile(), form);
        setIfNotNull("comment", req.getComment(), form);
        setIfNotNull("id", req.getId(), form);
        return doPostFormWithToken(form, Methods.FILES_COMMENTS_EDIT, req.getToken(), FilesCommentsEditResponse.class);
    }

    @Override
    public GroupsArchiveResponse groupsArchive(GroupsArchiveRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        return doPostFormWithToken(form, Methods.GROUPS_ARCHIVE, req.getToken(), GroupsArchiveResponse.class);
    }

    @Override
    public GroupsCloseResponse groupsClose(GroupsCloseRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        return doPostFormWithToken(form, Methods.GROUPS_CLOSE, req.getToken(), GroupsCloseResponse.class);
    }

    @Override
    public GroupsCreateChildResponse groupsCreateChild(GroupsCreateChildRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        return doPostFormWithToken(form, Methods.GROUPS_CREATE_CHILD, req.getToken(), GroupsCreateChildResponse.class);
    }

    @Override
    public GroupsCreateResponse groupsCreate(GroupsCreateRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("name", req.getName(), form);
        setIfNotNull("validate", req.isValidate(), form);
        return doPostFormWithToken(form, Methods.GROUPS_CREATE, req.getToken(), GroupsCreateResponse.class);
    }

    @Override
    public GroupsHistoryResponse groupsHistory(GroupsHistoryRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("latest", req.getLatest(), form);
        setIfNotNull("oldest", req.getOldest(), form);
        setIfNotNull("inclusive", req.isInclusive(), form);
        setIfNotNull("count", req.getCount(), form);
        setIfNotNull("unreads", req.isUnreads(), form);
        return doPostFormWithToken(form, Methods.GROUPS_HISTORY, req.getToken(), GroupsHistoryResponse.class);
    }

    @Override
    public GroupsRepliesResponse groupsReplies(GroupsRepliesRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("thread_ts", req.getThreadTs(), form);
        return doPostFormWithToken(form, Methods.GROUPS_REPLIES, req.getToken(), GroupsRepliesResponse.class);
    }

    @Override
    public GroupsInfoResponse groupsInfo(GroupsInfoRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("include_locale", req.isIncludeLocale(), form);
        return doPostFormWithToken(form, Methods.GROUPS_INFO, req.getToken(), GroupsInfoResponse.class);
    }

    @Override
    public GroupsInviteResponse groupsInvite(GroupsInviteRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("user", req.getUser(), form);
        return doPostFormWithToken(form, Methods.GROUPS_INVITE, req.getToken(), GroupsInviteResponse.class);
    }

    @Override
    public GroupsKickResponse groupsKick(GroupsKickRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("user", req.getUser(), form);
        return doPostFormWithToken(form, Methods.GROUPS_KICK, req.getToken(), GroupsKickResponse.class);
    }

    @Override
    public GroupsLeaveResponse groupsLeave(GroupsLeaveRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        return doPostFormWithToken(form, Methods.GROUPS_LEAVE, req.getToken(), GroupsLeaveResponse.class);
    }

    @Override
    public GroupsListResponse groupsList(GroupsListRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("exclude_archived", req.isExcludeArchived(), form);
        setIfNotNull("exclude_members", req.isExcludeMembers(), form);
        return doPostFormWithToken(form, Methods.GROUPS_LIST, req.getToken(), GroupsListResponse.class);
    }

    @Override
    public GroupsMarkResponse groupsMark(GroupsMarkRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("ts", req.getTs(), form);
        return doPostFormWithToken(form, Methods.GROUPS_MARK, req.getToken(), GroupsMarkResponse.class);
    }

    @Override
    public GroupsOpenResponse groupsOpen(GroupsOpenRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        return doPostFormWithToken(form, Methods.GROUPS_OPEN, req.getToken(), GroupsOpenResponse.class);
    }

    @Override
    public GroupsRenameResponse groupsRename(GroupsRenameRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("name", req.getName(), form);
        return doPostFormWithToken(form, Methods.GROUPS_RENAME, req.getToken(), GroupsRenameResponse.class);
    }

    @Override
    public GroupsSetPurposeResponse groupsSetPurpose(GroupsSetPurposeRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("purpose", req.getPurpose(), form);
        return doPostFormWithToken(form, Methods.GROUPS_SET_PURPOSE, req.getToken(), GroupsSetPurposeResponse.class);
    }

    @Override
    public GroupsSetTopicResponse groupsSetTopic(GroupsSetTopicRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("topic", req.getTopic(), form);
        return doPostFormWithToken(form, Methods.GROUPS_SET_TOPIC, req.getToken(), GroupsSetTopicResponse.class);
    }

    @Override
    public GroupsUnarchiveResponse groupsUnarchive(GroupsUnarchiveRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();

        setIfNotNull("channel", req.getChannel(), form);
        return doPostFormWithToken(form, Methods.GROUPS_UNARCHIVE, req.getToken(), GroupsUnarchiveResponse.class);
    }

    @Override
    public ImCloseResponse imClose(ImCloseRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        return doPostFormWithToken(form, Methods.IM_CLOSE, req.getToken(), ImCloseResponse.class);
    }

    @Override
    public ImHistoryResponse imHistory(ImHistoryRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("latest", req.getLatest(), form);
        setIfNotNull("oldest", req.getOldest(), form);
        setIfNotNull("inclusive", req.isInclusive(), form);
        setIfNotNull("count", req.getCount(), form);
        setIfNotNull("unreads", req.isUnreads(), form);
        return doPostFormWithToken(form, Methods.IM_HISTORY, req.getToken(), ImHistoryResponse.class);
    }

    @Override
    public ImListResponse imList(ImListRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("cursor", req.getCursor(), form);
        setIfNotNull("limit", req.getLimit(), form);
        return doPostFormWithToken(form, Methods.IM_LIST, req.getToken(), ImListResponse.class);
    }

    @Override
    public ImMarkResponse imMark(ImMarkRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("ts", req.getTs(), form);
        return doPostFormWithToken(form, Methods.IM_MARK, req.getToken(), ImMarkResponse.class);
    }

    @Override
    public ImOpenResponse imOpen(ImOpenRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("user", req.getUser(), form);
        setIfNotNull("return_im", req.isReturnIm(), form);
        setIfNotNull("include_locale", req.isIncludeLocale(), form);
        return doPostFormWithToken(form, Methods.IM_OPEN, req.getToken(), ImOpenResponse.class);
    }

    @Override
    public ImRepliesResponse imReplies(ImRepliesRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("thread_ts", req.getThreadTs(), form);
        return doPostFormWithToken(form, Methods.IM_REPLIES, req.getToken(), ImRepliesResponse.class);
    }

    @Override
    public MigrationExchangeResponse migrationExchange(MigrationExchangeRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("to_old", req.isToOld(), form);
        if (req.getUsers() != null) {
            setIfNotNull("users", req.getUsers().stream().collect(joining(",")), form);
        }
        return doPostFormWithToken(form, Methods.MIGRATION_EXCHANGE, req.getToken(), MigrationExchangeResponse.class);
    }

    @Override
    public MpimCloseResponse mpimClose(MpimCloseRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        return doPostFormWithToken(form, Methods.MPIM_CLOSE, req.getToken(), MpimCloseResponse.class);
    }

    @Override
    public MpimHistoryResponse mpimHistory(MpimHistoryRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("latest", req.getLatest(), form);
        setIfNotNull("oldest", req.getOldest(), form);
        setIfNotNull("inclusive", req.isInclusive(), form);
        setIfNotNull("count", req.getCount(), form);
        setIfNotNull("unreads", req.isUnreads(), form);
        return doPostFormWithToken(form, Methods.MPIM_HISTORY, req.getToken(), MpimHistoryResponse.class);
    }

    @Override
    public MpimListResponse mpimList(MpimListRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        return doPostFormWithToken(form, Methods.MPIM_LIST, req.getToken(), MpimListResponse.class);
    }

    @Override
    public MpimRepliesResponse mpimReplies(MpimRepliesRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("thread_ts", req.getThreadTs(), form);
        return doPostFormWithToken(form, Methods.MPIM_LIST, req.getToken(), MpimRepliesResponse.class);
    }

    @Override
    public MpimMarkResponse mpimMark(MpimMarkRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("ts", req.getTs(), form);
        return doPostFormWithToken(form, Methods.MPIM_MARK, req.getToken(), MpimMarkResponse.class);
    }

    @Override
    public MpimOpenResponse mpimOpen(MpimOpenRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        if (req.getUsers() != null) {
            setIfNotNull("users", req.getUsers().stream().collect(joining(",")), form);
        }
        return doPostFormWithToken(form, Methods.MPIM_OPEN, req.getToken(), MpimOpenResponse.class);
    }

    @Override
    public OAuthAccessResponse oauthAccess(OAuthAccessRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("client_id", req.getClientId(), form);
        setIfNotNull("client_secret", req.getClientSecret(), form);
        setIfNotNull("code", req.getCode(), form);
        setIfNotNull("redirect_uri", req.getRedirectUri(), form);
        setIfNotNull("single_channel", req.isSingleChannel(), form);
        return doPostForm(form, Methods.OAUTH_ACCESS, OAuthAccessResponse.class);
    }

    @Override
    public OAuthTokenResponse oauthToken(OAuthTokenRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("client_id", req.getClientId(), form);
        setIfNotNull("client_secret", req.getClientSecret(), form);
        setIfNotNull("code", req.getCode(), form);
        setIfNotNull("redirect_uri", req.getRedirectUri(), form);
        setIfNotNull("single_channel", req.isSingleChannel(), form);
        return doPostForm(form, Methods.OAUTH_TOKEN, OAuthTokenResponse.class);
    }

    @Override
    public PinsAddResponse pinsAdd(PinsAddRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("file", req.getFile(), form);
        setIfNotNull("file_comment", req.getFileComment(), form);
        setIfNotNull("timestamp", req.getTimestamp(), form);
        return doPostFormWithToken(form, Methods.PINS_ADD, req.getToken(), PinsAddResponse.class);
    }

    @Override
    public PinsListResponse pinsList(PinsListRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        return doPostFormWithToken(form, Methods.PINS_LIST, req.getToken(), PinsListResponse.class);
    }

    @Override
    public PinsRemoveResponse pinsRemove(PinsRemoveRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("file", req.getFile(), form);
        setIfNotNull("file_comment", req.getFileComment(), form);
        setIfNotNull("timestamp", req.getTimestamp(), form);
        return doPostFormWithToken(form, Methods.PINS_REMOVE, req.getToken(), PinsRemoveResponse.class);
    }

    @Override
    public ReactionsAddResponse reactionsAdd(ReactionsAddRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("name", req.getName(), form);
        setIfNotNull("file", req.getFile(), form);
        setIfNotNull("file_comment", req.getFileComment(), form);
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("timestamp", req.getTimestamp(), form);
        return doPostFormWithToken(form, Methods.REACTIONS_ADD, req.getToken(), ReactionsAddResponse.class);
    }

    @Override
    public ReactionsGetResponse reactionsGet(ReactionsGetRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("file", req.getFile(), form);
        setIfNotNull("file_comment", req.getFileComment(), form);
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("timestamp", req.getTimestamp(), form);
        setIfNotNull("full", req.isFull(), form);
        return doPostFormWithToken(form, Methods.REACTIONS_GET, req.getToken(), ReactionsGetResponse.class);
    }

    @Override
    public ReactionsListResponse reactionsList(ReactionsListRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("user", req.getUser(), form);
        setIfNotNull("full", req.isFull(), form);
        setIfNotNull("count", req.getCount(), form);
        setIfNotNull("page", req.getPage(), form);
        return doPostFormWithToken(form, Methods.REACTIONS_LIST, req.getToken(), ReactionsListResponse.class);
    }

    @Override
    public ReactionsRemoveResponse reactionsRemove(ReactionsRemoveRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("name", req.getName(), form);
        setIfNotNull("file", req.getFile(), form);
        setIfNotNull("file_comment", req.getFileComment(), form);
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("timestamp", req.getTimestamp(), form);
        return doPostFormWithToken(form, Methods.REACTIONS_REMOVE, req.getToken(), ReactionsRemoveResponse.class);
    }

    @Override
    public RemindersAddResponse remindersAdd(RemindersAddRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("text", req.getText(), form);
        setIfNotNull("time", req.getTime(), form);
        setIfNotNull("user", req.getUser(), form);
        return doPostFormWithToken(form, Methods.REMINDERS_ADD, req.getToken(), RemindersAddResponse.class);
    }

    @Override
    public RemindersCompleteResponse remindersComplete(RemindersCompleteRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("reminder", req.getReminder(), form);
        return doPostFormWithToken(form, Methods.REMINDERS_COMPLETE, req.getToken(), RemindersCompleteResponse.class);
    }

    @Override
    public RemindersDeleteResponse remindersDelete(RemindersDeleteRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("reminder", req.getReminder(), form);
        return doPostFormWithToken(form, Methods.REMINDERS_DELETE, req.getToken(), RemindersDeleteResponse.class);
    }

    @Override
    public RemindersInfoResponse remindersInfo(RemindersInfoRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("reminder", req.getReminder(), form);
        return doPostFormWithToken(form, Methods.REMINDERS_INFO, req.getToken(), RemindersInfoResponse.class);
    }

    @Override
    public RemindersListResponse remindersList(RemindersListRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        return doPostFormWithToken(form, Methods.REMINDERS_LIST, req.getToken(), RemindersListResponse.class);
    }

    @Override
    public RTMConnectResponse rtmConnect(RTMConnectRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("batch_presence_aware", req.isBatchPresenceAware(), form);
        setIfNotNull("presence_sub", req.isPresenceSub(), form);
        return doPostFormWithToken(form, Methods.RTM_CONNECT, req.getToken(), RTMConnectResponse.class);
    }

    @Override
    public RTMStartResponse rtmStart(RTMStartRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("include_locale", req.isIncludeLocale(), form);
        setIfNotNull("batch_presence_aware", req.isBatchPresenceAware(), form);
        setIfNotNull("no_latest", req.isNoLatest(), form);
        setIfNotNull("no_unreads", req.isNoUnreads(), form);
        setIfNotNull("presence_sub", req.isPresenceSub(), form);
        setIfNotNull("simple_latest", req.isSimpleLatest(), form);
        setIfNotNull("mpim_aware", req.isMpimAware(), form);
        return doPostFormWithToken(form, Methods.RTM_START, req.getToken(), RTMStartResponse.class);
    }

    @Override
    public SearchAllResponse searchAll(SearchAllRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("query", req.getQuery(), form);
        setIfNotNull("sort", req.getSort(), form);
        setIfNotNull("sort_dir", req.getSortDir(), form);
        setIfNotNull("highlight", req.isHighlight(), form);
        setIfNotNull("count", req.getCount(), form);
        setIfNotNull("page", req.getPage(), form);
        return doPostFormWithToken(form, Methods.SEARCH_ALL, req.getToken(), SearchAllResponse.class);
    }

    @Override
    public SearchMessagesResponse searchMessages(SearchMessagesRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("query", req.getQuery(), form);
        setIfNotNull("sort", req.getSort(), form);
        setIfNotNull("sort_dir", req.getSortDir(), form);
        setIfNotNull("highlight", req.isHighlight(), form);
        setIfNotNull("count", req.getCount(), form);
        setIfNotNull("page", req.getPage(), form);
        return doPostFormWithToken(form, Methods.SEARCH_MESSAGES, req.getToken(), SearchMessagesResponse.class);
    }

    @Override
    public SearchFilesResponse searchFiles(SearchFilesRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("query", req.getQuery(), form);
        setIfNotNull("sort", req.getSort(), form);
        setIfNotNull("sort_dir", req.getSortDir(), form);
        setIfNotNull("highlight", req.isHighlight(), form);
        setIfNotNull("count", req.getCount(), form);
        setIfNotNull("page", req.getPage(), form);
        return doPostFormWithToken(form, Methods.SEARCH_FILES, req.getToken(), SearchFilesResponse.class);
    }

    @Override
    public StarsAddResponse starsAdd(StarsAddRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("file", req.getFile(), form);
        setIfNotNull("file_comment", req.getFileComment(), form);
        setIfNotNull("timestamp", req.getTimestamp(), form);
        return doPostFormWithToken(form, Methods.STARS_ADD, req.getToken(), StarsAddResponse.class);
    }

    @Override
    public StarsListResponse starsList(StarsListRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("count", req.getCount(), form);
        setIfNotNull("page", req.getPage(), form);
        return doPostFormWithToken(form, Methods.STARS_LIST, req.getToken(), StarsListResponse.class);
    }

    @Override
    public StarsRemoveResponse starsRemove(StarsRemoveRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("channel", req.getChannel(), form);
        setIfNotNull("file", req.getFile(), form);
        setIfNotNull("file_comment", req.getFileComment(), form);
        setIfNotNull("timestamp", req.getTimestamp(), form);
        return doPostFormWithToken(form, Methods.STARS_REMOVE, req.getToken(), StarsRemoveResponse.class);
    }

    @Override
    public TeamAccessLogsResponse teamAccessLogs(TeamAccessLogsRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("before", req.getBefore(), form);
        setIfNotNull("count", req.getCount(), form);
        setIfNotNull("page", req.getPage(), form);
        return doPostFormWithToken(form, Methods.TEAM_ACCESS_LOGS, req.getToken(), TeamAccessLogsResponse.class);
    }

    @Override
    public TeamBillableInfoResponse teamBillableInfo(TeamBillableInfoRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("user", req.getUser(), form);
        return doPostFormWithToken(form, Methods.TEAM_BILLABLE_INFO, req.getToken(), TeamBillableInfoResponse.class);
    }

    @Override
    public TeamInfoResponse teamInfo(TeamInfoRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        return doPostFormWithToken(form, Methods.TEAM_INFO, req.getToken(), TeamInfoResponse.class);
    }

    @Override
    public TeamIntegrationLogsResponse teamIntegrationLogs(TeamIntegrationLogsRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("service_id", req.getServiceId(), form);
        setIfNotNull("user", req.getUser(), form);
        setIfNotNull("change_type", req.getChangeType(), form);
        setIfNotNull("count", req.getCount(), form);
        setIfNotNull("page", req.getPage(), form);
        return doPostFormWithToken(form, Methods.TEAM_INTEGRATION_LOGS, req.getToken(), TeamIntegrationLogsResponse.class);
    }

    @Override
    public TeamProfileGetResponse teamProfileGet(TeamProfileGetRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("visibility", req.getVisibility(), form);
        return doPostFormWithToken(form, Methods.TEAM_PROFILE_GET, req.getToken(), TeamProfileGetResponse.class);
    }

    @Override
    public UsergroupsCreateResponse usergroupsCreate(UsergroupsCreateRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("name", req.getName(), form);
        setIfNotNull("handle", req.getHandle(), form);
        setIfNotNull("description", req.getDescription(), form);
        if (req.getChannels() != null) {
            setIfNotNull("channels", req.getChannels().stream().collect(joining(",")), form);
        }
        setIfNotNull("include_count", req.isIncludeCount(), form);
        return doPostFormWithToken(form, Methods.USERGROUPS_CREATE, req.getToken(), UsergroupsCreateResponse.class);
    }

    @Override
    public UsergroupsDisableResponse usergroupsDisable(UsergroupsDisableRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("usergroup", req.getUsergroup(), form);
        setIfNotNull("include_count", req.isIncludeCount(), form);
        return doPostFormWithToken(form, Methods.USERGROUPS_DISABLE, req.getToken(), UsergroupsDisableResponse.class);
    }

    @Override
    public UsergroupsEnableResponse usergroupsEnable(UsergroupsEnableRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("usergroup", req.getUsergroup(), form);
        setIfNotNull("include_count", req.isIncludeCount(), form);
        return doPostFormWithToken(form, Methods.USERGROUPS_ENABLE, req.getToken(), UsergroupsEnableResponse.class);
    }

    @Override
    public UsergroupsListResponse usergroupsList(UsergroupsListRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("include_disabled", req.isIncludeDisabled(), form);
        setIfNotNull("include_count", req.isIncludeCount(), form);
        setIfNotNull("include_users", req.isIncludeUsers(), form);
        return doPostFormWithToken(form, Methods.USERGROUPS_LIST, req.getToken(), UsergroupsListResponse.class);
    }

    @Override
    public UsergroupsUpdateResponse usergroupsUpdate(UsergroupsUpdateRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("usergroup", req.getUsergroup(), form);
        setIfNotNull("name", req.getName(), form);
        setIfNotNull("handle", req.getHandle(), form);
        setIfNotNull("description", req.getDescription(), form);
        if (req.getChannels() != null) {
            setIfNotNull("channels", req.getChannels().stream().collect(joining(",")), form);
        }
        setIfNotNull("include_count", req.isIncludeCount(), form);
        return doPostFormWithToken(form, Methods.USERGROUPS_UPDATE, req.getToken(), UsergroupsUpdateResponse.class);
    }

    @Override
    public UsergroupUsersListResponse usergroupUsersList(UsergroupUsersListRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("usergroup", req.getUsergroup(), form);
        setIfNotNull("include_disabled", req.isIncludeDisabled(), form);
        return doPostFormWithToken(form, Methods.USERGROUPS_USERS_LIST, req.getToken(), UsergroupUsersListResponse.class);
    }

    @Override
    public UsergroupUsersUpdateResponse usergroupUsersUpdate(UsergroupUsersUpdateRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("usergroup", req.getUsergroup(), form);
        if (req.getUsers() != null) {
            setIfNotNull("users", req.getUsers().stream().collect(joining(",")), form);
        }
        setIfNotNull("include_count", req.isIncludeCount(), form);
        return doPostFormWithToken(form, Methods.USERGROUPS_USERS_UPDATE, req.getToken(), UsergroupUsersUpdateResponse.class);
    }

    @Override
    public UsersConversationsResponse usersConversations(UsersConversationsRequest req) throws IOException, SlackApiException {
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
        return doPostFormWithToken(form, Methods.USERS_CONVERSATIONS, req.getToken(), UsersConversationsResponse.class);
    }

    @Override
    public UsersDeletePhotoResponse usersDeletePhoto(UsersDeletePhotoRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        return doPostFormWithToken(form, Methods.USERS_DELETE_PHOTO, req.getToken(), UsersDeletePhotoResponse.class);
    }

    @Override
    public UsersGetPresenceResponse usersGetPresence(UsersGetPresenceRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("user", req.getUser(), form);
        return doPostFormWithToken(form, Methods.USERS_GET_PRESENCE, req.getToken(), UsersGetPresenceResponse.class);
    }

    @Override
    public UsersIdentityResponse usersIdentity(UsersIdentityRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        return doPostFormWithToken(form, Methods.USERS_IDENTITY, req.getToken(), UsersIdentityResponse.class);
    }

    @Override
    public UsersInfoResponse usersInfo(UsersInfoRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("user", req.getUser(), form);
        setIfNotNull("include_locale", req.isIncludeLocale(), form);
        return doPostFormWithToken(form, Methods.USERS_INFO, req.getToken(), UsersInfoResponse.class);
    }

    @Override
    public UsersListResponse usersList(UsersListRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("cursor", req.getCursor(), form);
        setIfNotNull("limit", req.getLimit(), form);
        setIfNotNull("include_locale", req.isIncludeLocale(), form);
        setIfNotNull("presence", req.isPresence(), form);
        return doPostFormWithToken(form, Methods.USERS_LIST, req.getToken(), UsersListResponse.class);
    }

    @Override
    public UsersLookupByEmailResponse usersLookupByEmail(UsersLookupByEmailRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("email", req.getEmail(), form);
        return doPostFormWithToken(form, Methods.USERS_LOOKUP_BY_EMAIL, req.getToken(), UsersLookupByEmailResponse.class);
    }

    @Override
    public UsersSetActiveResponse usersSetActive(UsersSetActiveRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        return doPostFormWithToken(form, Methods.USERS_SET_ACTIVE, req.getToken(), UsersSetActiveResponse.class);
    }

    @Override
    public UsersSetPhotoResponse usersSetPhoto(UsersSetPhotoRequest req) throws IOException, SlackApiException {
        MultipartBody.Builder form = new MultipartBody.Builder();
        RequestBody image = RequestBody.create(MediaType.parse("image/*"), req.getImage());
        form.addFormDataPart("image", "image", image);
        setIfNotNull("crop_x", req.getCropX(), form);
        setIfNotNull("crop_y", req.getCropY(), form);
        setIfNotNull("crop_w", req.getCropW(), form);
        return doPostMultipart(form, Methods.USERS_SET_PHOTO, req.getToken(), UsersSetPhotoResponse.class);
    }

    @Override
    public UsersSetPresenceResponse usersSetPresence(UsersSetPresenceRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("presence", req.getPresence(), form);
        return doPostFormWithToken(form, Methods.USERS_SET_PRESENCE, req.getToken(), UsersSetPresenceResponse.class);
    }

    @Override
    public UsersProfileGetResponse usersProfileGet(UsersProfileGetRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("user", req.getUser(), form);
        setIfNotNull("include_labels", req.isIncludeLabels(), form);
        return doPostFormWithToken(form, Methods.USERS_PROFILE_GET, req.getToken(), UsersProfileGetResponse.class);
    }

    @Override
    public UsersProfileSetResponse usersProfileSet(UsersProfileSetRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        setIfNotNull("user", req.getUser(), form);
        if (req.getProfile() != null) {
            setIfNotNull("profile", GsonFactory.createSnakeCase().toJson(req.getProfile()), form);
        } else {
            setIfNotNull("name", req.getName(), form);
            setIfNotNull("value", req.getValue(), form);
        }
        return doPostFormWithToken(form, Methods.USERS_PROFILE_SET, req.getToken(), UsersProfileSetResponse.class);
    }

    // ----------------------------------------------------------------------------------
    // private methods
    // ----------------------------------------------------------------------------------

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

    protected <T> T doPostForm(
            FormBody.Builder form,
            String endpoint,
            Class<T> clazz) throws IOException, SlackApiException {
        Response response = slackHttpClient.postForm(endpointUrlPrefix + endpoint, form.build());
        return SlackHttpClient.buildJsonResponse(response, clazz);
    }

    protected <T> T doPostFormWithToken(
            FormBody.Builder form,
            String endpoint,
            String token,
            Class<T> clazz) throws IOException, SlackApiException {
        Response response = slackHttpClient.postFormWithBearerHeader(endpointUrlPrefix + endpoint, token, form.build());
        return SlackHttpClient.buildJsonResponse(response, clazz);
    }

    protected <T> T doPostMultipart(
            MultipartBody.Builder form,
            String endpoint,
            String token,
            Class<T> clazz) throws IOException, SlackApiException {
        form.setType(MultipartBody.FORM);
        Response response = slackHttpClient.postMultipart(endpointUrlPrefix + endpoint, token, form.build());
        return SlackHttpClient.buildJsonResponse(response, clazz);
    }

}