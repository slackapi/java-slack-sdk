package com.slack.api.status.v2.impl;

import com.google.gson.reflect.TypeToken;
import com.slack.api.status.v2.StatusApiException;
import com.slack.api.status.v2.StatusClient;
import com.slack.api.status.v2.model.CurrentStatus;
import com.slack.api.status.v2.model.SlackIssue;
import com.slack.api.util.http.SlackHttpClient;
import com.slack.api.util.json.GsonFactory;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class StatusClientImpl implements StatusClient {

    private String endpointUrlPrefix = ENDPOINT_URL_PREFIX;
    private final SlackHttpClient slackHttpClient;

    public StatusClientImpl(SlackHttpClient slackHttpClient) {
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
    public CurrentStatus current() throws IOException, StatusApiException {
        Response response = slackHttpClient.get(endpointUrlPrefix + "current", null, null);
        String body = response.body().string();
        slackHttpClient.runHttpResponseListeners(response, body);
        if (response.isSuccessful()) {
            return GsonFactory.createSnakeCase(slackHttpClient.getConfig()).fromJson(body, CurrentStatus.class);
        } else {
            throw new StatusApiException(response, body);
        }
    }

    @Override
    public List<SlackIssue> history() throws IOException, StatusApiException {
        Response response = slackHttpClient.get(endpointUrlPrefix + "history", null, null);
        Type listType = new TypeToken<ArrayList<SlackIssue>>() {
        }.getType();
        String body = response.body().string();
        slackHttpClient.runHttpResponseListeners(response, body);
        if (response.isSuccessful()) {
            return GsonFactory.createSnakeCase(slackHttpClient.getConfig()).fromJson(body, listType);
        } else {
            throw new StatusApiException(response, body);
        }
    }

}