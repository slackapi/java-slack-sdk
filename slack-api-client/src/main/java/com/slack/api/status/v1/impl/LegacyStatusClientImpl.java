package com.slack.api.status.v1.impl;

import com.slack.api.status.v1.LegacyStatusApiException;
import com.slack.api.status.v1.LegacyStatusClient;
import com.slack.api.status.v1.model.LegacyCurrentStatus;
import com.slack.api.status.v1.model.LegacySlackIssue;
import com.slack.api.util.http.SlackHttpClient;
import com.slack.api.util.json.GsonFactory;
import com.google.gson.reflect.TypeToken;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class LegacyStatusClientImpl implements LegacyStatusClient {

    private String endpointUrlPrefix = ENDPOINT_URL_PREFIX;
    private final SlackHttpClient slackHttpClient;

    public LegacyStatusClientImpl(SlackHttpClient slackHttpClient) {
        this.slackHttpClient = slackHttpClient;
    }

    @Override
    public String getEndpointUrlPrefix() {
        return this.endpointUrlPrefix;
    }

    @Override
    public void setEndpointUrlPrefix(String endpointUrlPrefix) {
        this.endpointUrlPrefix = endpointUrlPrefix;
    }

    @Override
    public LegacyCurrentStatus current() throws IOException, LegacyStatusApiException {
        Response response = slackHttpClient.get(endpointUrlPrefix + "current", null, null);
        String body = response.body().string();
        slackHttpClient.runHttpResponseListeners(response, body);
        if (response.isSuccessful()) {
            return GsonFactory.createSnakeCase(slackHttpClient.getConfig()).fromJson(body, LegacyCurrentStatus.class);
        } else {
            throw new LegacyStatusApiException(response, body);
        }
    }

    @Override
    public List<LegacySlackIssue> history() throws IOException, LegacyStatusApiException {
        Response response = slackHttpClient.get(endpointUrlPrefix + "history", null, null);
        Type listType = new TypeToken<ArrayList<LegacySlackIssue>>() {
        }.getType();
        String body = response.body().string();
        slackHttpClient.runHttpResponseListeners(response, body);
        if (response.isSuccessful()) {
            return GsonFactory.createSnakeCase(slackHttpClient.getConfig()).fromJson(body, listType);
        } else {
            throw new LegacyStatusApiException(response, body);
        }
    }

}