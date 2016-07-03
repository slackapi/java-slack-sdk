package com.github.seratch.jslack.common.http;

import com.github.seratch.jslack.common.json.GsonFactory;
import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;

public class SlackHttpClient {

    private final OkHttpClient okHttpClient;

    public SlackHttpClient() {
        this.okHttpClient = new OkHttpClient();
    }

    public SlackHttpClient(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    public Response postForm(String url, FormBody formBody) throws IOException {
        Request request = new Request.Builder().url(url).post(formBody).build();
        return okHttpClient.newCall(request).execute();
    }

    public Response postJsonPostRequest(String url, Object obj) throws IOException {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), toJsonString(obj));
        Request request = new Request.Builder().url(url).post(body).build();
        return okHttpClient.newCall(request).execute();
    }

    private String toJsonString(Object obj) {
        Gson gson = GsonFactory.createSnakeCase();
        return gson.toJson(obj);
    }

}
