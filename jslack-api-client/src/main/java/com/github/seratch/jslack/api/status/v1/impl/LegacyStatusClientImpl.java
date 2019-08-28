package com.github.seratch.jslack.api.status.v1.impl;

import com.github.seratch.jslack.api.status.v1.LegacyStatusApiException;
import com.github.seratch.jslack.api.status.v1.LegacyStatusClient;
import com.github.seratch.jslack.api.status.v1.model.LegacyCurrentStatus;
import com.github.seratch.jslack.api.status.v1.model.LegacySlackIssue;
import com.github.seratch.jslack.common.http.SlackHttpClient;
import com.github.seratch.jslack.common.json.GsonFactory;
import com.google.gson.reflect.TypeToken;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class LegacyStatusClientImpl implements LegacyStatusClient {

    private static final String BASE_URL = "https://status.slack.com/api/v1.0.0/";

    private final SlackHttpClient slackHttpClient;

    public LegacyStatusClientImpl(SlackHttpClient slackHttpClient) {
        this.slackHttpClient = slackHttpClient;
    }

    @Override
    public LegacyCurrentStatus current() throws IOException, LegacyStatusApiException {
        Response response = slackHttpClient.get(BASE_URL + "current", null, null);
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
        Response response = slackHttpClient.get(BASE_URL + "history", null, null);
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