package com.github.seratch.jslack.common.http.listener;

import com.github.seratch.jslack.SlackConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import okhttp3.Response;

import java.util.function.Consumer;

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
