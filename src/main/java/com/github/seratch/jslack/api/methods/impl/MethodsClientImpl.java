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
        if (req.getFoo() != null) form.add("foo", req.getFoo());
        if (req.getError() != null) form.add("error", req.getError());
        return doPostForm(form, Methods.API_TEST, ApiTestResponse.class);
    }

    @Override
    public RTMStartResponse rtmStart(RTMStartRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        if (req.getToken() != null) form.add("token", req.getToken());
        return doPostForm(form, Methods.RTM_START, RTMStartResponse.class);
    }

    @Override
    public AuthRevokeResponse authRevoke(AuthRevokeRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        if (req.getToken() != null) form.add("token", req.getToken());
        if (req.getTest() != null) form.add("test", req.getTest());
        return doPostForm(form, Methods.AUTH_REVOKE, AuthRevokeResponse.class);
    }

    @Override
    public AuthTestResponse authTest(AuthTestRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        if (req.getToken() != null) form.add("token", req.getToken());
        return doPostForm(form, Methods.AUTH_TEST, AuthTestResponse.class);
    }

    @Override
    public BotsInfoResponse botsInfo(BotsInfoRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        if (req.getToken() != null) form.add("token", req.getToken());
        if (req.getBot() != null) form.add("bot", req.getBot());
        return doPostForm(form, Methods.BOTS_INFO, BotsInfoResponse.class);
    }

    @Override
    public ChannelsArchiveResponse channelsArchive(ChannelsArchiveRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        if (req.getToken() != null) form.add("token", req.getToken());
        if (req.getChannel() != null) form.add("channel", req.getChannel());
        return doPostForm(form, Methods.CHANNELS_ARCHIVE, ChannelsArchiveResponse.class);
    }

    @Override
    public ChannelsCreateResponse channelsCreate(ChannelsCreateRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        if (req.getToken() != null) form.add("token", req.getToken());
        if (req.getName() != null) form.add("name", req.getName());
        return doPostForm(form, Methods.CHANNELS_CREATE, ChannelsCreateResponse.class);
    }

    @Override
    public ChannelsHistoryResponse channelsHistory(ChannelsHistoryRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        if (req.getToken() != null) form.add("token", req.getToken());
        if (req.getChannel() != null) form.add("channel", req.getChannel());
        if (req.getLatest() != null) form.add("latest", req.getLatest());
        if (req.getOldest() != null) form.add("oldest", req.getOldest());
        if (req.getInclusive() != null) form.add("inclusive", String.valueOf(req.getInclusive()));
        if (req.getCount() != null) form.add("count", String.valueOf(req.getCount()));
        if (req.getUnreads() != null) form.add("unreads", String.valueOf(req.getUnreads()));
        return doPostForm(form, Methods.CHANNELS_HISTORY, ChannelsHistoryResponse.class);
    }

    @Override
    public ChannelsInfoResponse channelsInfo(ChannelsInfoRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        if (req.getToken() != null) form.add("token", req.getToken());
        if (req.getChannel() != null) form.add("channel", req.getChannel());
        return doPostForm(form, Methods.CHANNELS_INFO, ChannelsInfoResponse.class);
    }

    @Override
    public ChannelsListResponse channelsList(ChannelsListRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        if (req.getToken() != null) form.add("token", req.getToken());
        if (req.getExcludeArchived() != null) form.add("exclude_archived", String.valueOf(req.getExcludeArchived()));
        return doPostForm(form, Methods.CHANNELS_LIST, ChannelsListResponse.class);
    }

    @Override
    public ChannelsInviteResponse channelsInvite(ChannelsInviteRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        if (req.getToken() != null) form.add("token", req.getToken());
        if (req.getChannel() != null) form.add("channel", req.getChannel());
        if (req.getUser() != null) form.add("user", req.getUser());
        return doPostForm(form, Methods.CHANNELS_INVITE, ChannelsInviteResponse.class);
    }

    @Override
    public ChannelsJoinResponse channelsJoin(ChannelsJoinRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        if (req.getToken() != null) form.add("token", req.getToken());
        if (req.getName() != null) form.add("name", req.getName());
        return doPostForm(form, Methods.CHANNELS_JOIN, ChannelsJoinResponse.class);
    }

    @Override
    public ChannelsKickResponse channelsKick(ChannelsKickRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        if (req.getToken() != null) form.add("token", req.getToken());
        if (req.getChannel() != null) form.add("channel", req.getChannel());
        if (req.getUser() != null) form.add("user", req.getUser());
        return doPostForm(form, Methods.CHANNELS_KICK, ChannelsKickResponse.class);
    }

    @Override
    public ChannelsLeaveResponse channelsLeave(ChannelsLeaveRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        if (req.getToken() != null) form.add("token", req.getToken());
        if (req.getChannel() != null) form.add("channel", req.getChannel());
        return doPostForm(form, Methods.CHANNELS_LEAVE, ChannelsLeaveResponse.class);
    }

    @Override
    public ChatDeleteResponse chatDelete(ChatDeleteRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        if (req.getToken() != null) form.add("token", req.getToken());
        if (req.getChannel() != null) form.add("channel", req.getChannel());
        if (req.getTs() != null) form.add("ts", req.getTs());
        return doPostForm(form, Methods.CHAT_DELETE, ChatDeleteResponse.class);
    }

    @Override
    public ChatMeMessageResponse chatMeMessage(ChatMeMessageRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        if (req.getToken() != null) form.add("token", req.getToken());
        if (req.getChannel() != null) form.add("channel", req.getChannel());
        if (req.getText() != null) form.add("text", req.getText());
        return doPostForm(form, Methods.CHAT_ME_MESSAGE, ChatMeMessageResponse.class);
    }

    @Override
    public ChatPostMessageResponse chatPostMessage(ChatPostMessageRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        if (req.getToken() != null) form.add("token", req.getToken());
        if (req.getChannel() != null) form.add("channel", req.getChannel());
        if (req.getText() != null) form.add("text", req.getText());
        if (req.getParse() != null) form.add("parse", req.getParse());
        if (req.getLinkNames() != null) form.add("link_names", String.valueOf(req.getLinkNames()));
        if (req.getAttachments() != null) {
            String json = GsonFactory.createSnakeCase().toJson(req.getAttachments());
            form.add("attachments", json);
        }
        form.add("unfurl_links", String.valueOf(req.isUnfurlLinks()));
        form.add("unfurl_media", String.valueOf(req.isUnfurlMedia()));
        if (req.getUsername() != null) form.add("username", req.getUsername());
        form.add("as_user", String.valueOf(req.isAsUser()));
        if (req.getIconUrl() != null) form.add("icon_url", req.getIconUrl());
        if (req.getIconEmoji() != null) form.add("icon_emoji", req.getIconEmoji());
        return doPostForm(form, Methods.CHAT_POST_MESSAGE, ChatPostMessageResponse.class);
    }

    @Override
    public ChatUpdateResponse chatUpdate(ChatUpdateRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        if (req.getToken() != null) form.add("token", req.getToken());
        if (req.getTs() != null) form.add("ts", req.getTs());
        if (req.getChannel() != null) form.add("channel", req.getChannel());
        if (req.getText() != null) form.add("text", req.getText());
        if (req.getParse() != null) form.add("parse", req.getParse());
        if (req.getLinkNames() != null) form.add("link_names", String.valueOf(req.getLinkNames()));
        if (req.getAttachments() != null) {
            String json = GsonFactory.createSnakeCase().toJson(req.getAttachments());
            form.add("attachments", json);
        }
        form.add("as_user", String.valueOf(req.isAsUser()));
        return doPostForm(form, Methods.CHAT_UPDATE, ChatUpdateResponse.class);
    }

    @Override
    public EmojiListResponse emojiList(EmojiListRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        if (req.getToken() != null) form.add("token", req.getToken());
        return doPostForm(form, Methods.EMOJI_LIST, EmojiListResponse.class);
    }

    // ----------------------------------------------------------------------------------
    // private methods
    // ----------------------------------------------------------------------------------

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
