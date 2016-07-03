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
        Response response = slackHttpClient.postForm(ENDPOINT_URL_PREFIX + Methods.API_TEST, form.build());
        return buildResponse(response, ApiTestResponse.class);
    }

    @Override
    public RTMStartResponse rtmStart(RTMStartRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        if (req.getToken() != null) form.add("token", req.getToken());
        Response response = slackHttpClient.postForm(ENDPOINT_URL_PREFIX + Methods.RTM_START, form.build());
        return buildResponse(response, RTMStartResponse.class);
    }

    @Override
    public AuthRevokeResponse authRevoke(AuthRevokeRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        if (req.getToken() != null) form.add("token", req.getToken());
        if (req.getTest() != null) form.add("test", req.getTest());
        Response response = slackHttpClient.postForm(ENDPOINT_URL_PREFIX + Methods.AUTH_REVOKE, form.build());
        return buildResponse(response, AuthRevokeResponse.class);
    }

    @Override
    public AuthTestResponse authTest(AuthTestRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        if (req.getToken() != null) form.add("token", req.getToken());
        Response response = slackHttpClient.postForm(ENDPOINT_URL_PREFIX + Methods.AUTH_TEST, form.build());
        return buildResponse(response, AuthTestResponse.class);
    }

    @Override
    public BotsInfoResponse botsInfo(BotsInfoRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        if (req.getToken() != null) form.add("token", req.getToken());
        if (req.getBot() != null) form.add("bot", req.getBot());
        Response response = slackHttpClient.postForm(ENDPOINT_URL_PREFIX + Methods.BOTS_INFO, form.build());
        return buildResponse(response, BotsInfoResponse.class);
    }

    @Override
    public ChannelsArchiveResponse channelsArchive(ChannelsArchiveRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        if (req.getToken() != null) form.add("token", req.getToken());
        if (req.getChannel() != null) form.add("channel", req.getChannel());
        Response response = slackHttpClient.postForm(ENDPOINT_URL_PREFIX + Methods.CHANNELS_ARCHIVE, form.build());
        return buildResponse(response, ChannelsArchiveResponse.class);
    }

    @Override
    public ChannelsCreateResponse channelsCreate(ChannelsCreateRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        if (req.getToken() != null) form.add("token", req.getToken());
        if (req.getName() != null) form.add("name", req.getName());
        Response response = slackHttpClient.postForm(ENDPOINT_URL_PREFIX + Methods.CHANNELS_CREATE, form.build());
        return buildResponse(response, ChannelsCreateResponse.class);
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
        Response response = slackHttpClient.postForm(ENDPOINT_URL_PREFIX + Methods.CHANNELS_HISTORY, form.build());
        return buildResponse(response, ChannelsHistoryResponse.class);
    }

    @Override
    public ChannelsInfoResponse channelsInfo(ChannelsInfoRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        if (req.getToken() != null) form.add("token", req.getToken());
        if (req.getChannel() != null) form.add("channel", req.getChannel());
        Response response = slackHttpClient.postForm(ENDPOINT_URL_PREFIX + Methods.CHANNELS_INFO, form.build());
        return buildResponse(response, ChannelsInfoResponse.class);
    }

    @Override
    public ChannelsListResponse channelsList(ChannelsListRequest req) throws IOException, SlackApiException {
        FormBody.Builder form = new FormBody.Builder();
        if (req.getToken() != null) form.add("token", req.getToken());
        if (req.getExcludeArchived() != null) form.add("exclude_archived", String.valueOf(req.getExcludeArchived()));
        Response response = slackHttpClient.postForm(ENDPOINT_URL_PREFIX + Methods.CHANNELS_LIST, form.build());
        return buildResponse(response, ChannelsListResponse.class);
    }

    // ----------------------------------------------------------------------------------
    // private methods
    // ----------------------------------------------------------------------------------

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
