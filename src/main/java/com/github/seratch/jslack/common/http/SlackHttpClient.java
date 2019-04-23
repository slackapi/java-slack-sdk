package com.github.seratch.jslack.common.http;

import com.github.seratch.jslack.SlackConfig;
import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.common.http.listener.DetailedLoggingListener;
import com.github.seratch.jslack.common.http.listener.HttpResponseListener;
import com.github.seratch.jslack.common.json.GsonFactory;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;

@Slf4j
public class SlackHttpClient {

    private final OkHttpClient okHttpClient;

    private SlackConfig config = SlackConfig.DEFAULT;

    public SlackHttpClient() {
        this.okHttpClient = new OkHttpClient.Builder().build();
    }

    public SlackHttpClient(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    public SlackConfig getConfig() {
        return config;
    }

    public void setConfig(SlackConfig config) {
        this.config = config;
    }

    public Response postMultipart(String url, String token, MultipartBody multipartBody) throws IOException {
        String bearerHeaderValue = "Bearer " + token;
        Request request = new Request.Builder().url(url).header("Authorization", bearerHeaderValue).post(multipartBody).build();
        return okHttpClient.newCall(request).execute();
    }

    public Response postForm(String url, FormBody formBody) throws IOException {
        Request request = new Request.Builder().url(url).post(formBody).build();
        return okHttpClient.newCall(request).execute();
    }

    public Response postFormWithBearerHeader(String url, String token, FormBody formBody) throws IOException {
        String bearerHeaderValue = "Bearer " + token;
        Request request = new Request.Builder().url(url).header("Authorization", bearerHeaderValue).post(formBody).build();
        return okHttpClient.newCall(request).execute();
    }

    public Response postJsonPostRequest(String url, Object obj) throws IOException {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), toJsonString(obj));
        Request request = new Request.Builder().url(url).post(body).build();
        return okHttpClient.newCall(request).execute();
    }

    public Response delete(Request.Builder requestBuilder) throws IOException {
        return okHttpClient.newCall(requestBuilder.method("DELETE", null).build()).execute();
    }

    private String toJsonString(Object obj) {
        Gson gson = GsonFactory.createSnakeCase();
        return gson.toJson(obj);
    }


    public void runHttpResponseListeners(Response response, String body) {
        HttpResponseListener.State state = new HttpResponseListener.State(config, response, body);
        for (HttpResponseListener responseListener : config.getHttpClientResponseHandlers()) {
            responseListener.accept(state);
        }
    }

    public <T> T parseJsonResponse(Response response, Class<T> clazz) throws IOException, SlackApiException {
        if (response.code() == 200) {
            String body = response.body().string();
            runHttpResponseListeners(response, body);
            return GsonFactory.createSnakeCase(config).fromJson(body, clazz);
        } else {
            String body = response.body().string();
            throw new SlackApiException(response, body);
        }
    }

    private static final DetailedLoggingListener DETAILED_LOGGER = new DetailedLoggingListener();

    // use parseJsonResponse instead
    @Deprecated
    public static <T> T buildJsonResponse(Response response, Class<T> clazz) throws IOException, SlackApiException {
        if (response.code() == 200) {
            String body = response.body().string();
            DETAILED_LOGGER.accept(new HttpResponseListener.State(SlackConfig.DEFAULT, response, body));
            return GsonFactory.createSnakeCase().fromJson(body, clazz);
        } else {
            String body = response.body().string();
            throw new SlackApiException(response, body);
        }
    }

}
