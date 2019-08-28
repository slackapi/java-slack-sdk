package com.github.seratch.jslack.api.status.v2.impl;

import com.github.seratch.jslack.api.status.v2.StatusApiException;
import com.github.seratch.jslack.api.status.v2.StatusClient;
import com.github.seratch.jslack.api.status.v2.model.CurrentStatus;
import com.github.seratch.jslack.api.status.v2.model.SlackIssue;
import com.github.seratch.jslack.common.http.SlackHttpClient;
import com.github.seratch.jslack.common.json.GsonFactory;
import com.google.gson.reflect.TypeToken;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class StatusClientImpl implements StatusClient {

    private static final String BASE_URL = "https://status.slack.com/api/v2.0.0/";

    private final SlackHttpClient slackHttpClient;

    public StatusClientImpl(SlackHttpClient slackHttpClient) {
        this.slackHttpClient = slackHttpClient;
    }

    @Override
    public CurrentStatus current() throws IOException, StatusApiException {
        Response response = slackHttpClient.get(BASE_URL + "current", null, null);
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
        Response response = slackHttpClient.get(BASE_URL + "history", null, null);
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