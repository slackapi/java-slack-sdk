package com.github.seratch.jslack.rtm;

import com.github.seratch.jslack.http.SlackHttpClient;
import com.github.seratch.jslack.json.GsonFactory;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Response;

import java.io.IOException;
import java.util.Optional;

@Slf4j
public class RTMStart {

    public static final String ENDPOINT_URL = "https://slack.com/api/rtm.start";

    @Data
    public static class RTMStartResponse {
        private boolean ok;
        private String url;
    }

    public Optional<String> fetchWebSocketUrl(String token) throws IOException {
        SlackHttpClient slackHttpClient = new SlackHttpClient();
        FormBody formBody = new FormBody.Builder().add("token", token).build();
        Response response = slackHttpClient.postForm(ENDPOINT_URL, formBody);
        String json = response.body().string();
        RTMStartResponse startResponse = GsonFactory.createSnakeCase().fromJson(json, RTMStartResponse.class);
        if (startResponse.isOk()) {
            return Optional.of(startResponse.getUrl());
        } else {
            log.warn("Failed to fetch RTM wss URL. response: {}", startResponse);
            return Optional.empty();
        }
    }

}
