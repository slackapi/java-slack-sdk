package com.github.seratch.jslack.api.methods;

import com.github.seratch.jslack.api.methods.request.*;
import com.github.seratch.jslack.api.methods.response.*;

import java.io.IOException;

public interface MethodsClient {

    ApiTestResponse apiTest(ApiTestRequest req) throws IOException, SlackApiException;

    RTMStartResponse rtmStart(RTMStartRequest req) throws IOException, SlackApiException;

    AuthRevokeResponse authRevoke(AuthRevokeRequest req) throws IOException, SlackApiException;

    AuthTestResponse authTest(AuthTestRequest req) throws IOException, SlackApiException;

    BotsInfoResponse botsInfo(BotsInfoRequest req) throws IOException, SlackApiException;

    ChannelsArchiveResponse channelsArchive(ChannelsArchiveRequest req) throws IOException, SlackApiException;

    ChannelsCreateResponse channelsCreate(ChannelsCreateRequest req) throws IOException, SlackApiException;

    ChannelsHistoryResponse channelsHistory(ChannelsHistoryRequest req) throws IOException, SlackApiException;

    ChannelsInfoResponse channelsInfo(ChannelsInfoRequest req) throws IOException, SlackApiException;

    ChannelsListResponse channelsList(ChannelsListRequest req) throws IOException, SlackApiException;

}
