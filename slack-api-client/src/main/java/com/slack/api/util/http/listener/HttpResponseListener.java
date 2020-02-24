package com.slack.api.util.http.listener;

import com.slack.api.SlackConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import okhttp3.Response;

import java.util.function.Consumer;

/**
 * An HTTP response listener that enables developers to add additional operations
 * after any Slack API calls.
 */
public abstract class HttpResponseListener implements Consumer<HttpResponseListener.State> {

    public abstract void accept(State state);

    @AllArgsConstructor
    @Data
    public static class State {
        private SlackConfig config;
        private Response response;
        private String parsedResponseBody;
    }

}
