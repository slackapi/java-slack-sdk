package com.github.seratch.jslack.common.http;

import com.github.seratch.jslack.SlackConfig;
import com.github.seratch.jslack.common.http.listener.HttpResponseListener;
import com.github.seratch.jslack.common.json.GsonFactory;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;

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

    public Response get(String url, Map<String, String> query, String token) throws IOException {
        if (query != null) {
            HttpUrl.Builder u = HttpUrl.parse(url).newBuilder();
            for (Map.Entry<String, String> each : query.entrySet()) {
                u = u.addQueryParameter(each.getKey(), each.getValue());
            }
            url = u.build().toString();
        }
        final Request request;
        if (token != null) {
            String bearerHeaderValue = "Bearer " + token;
            request = new Request.Builder().url(url).header("Authorization", bearerHeaderValue).get().build();
        } else {
            request = new Request.Builder().url(url).get().build();
        }
        return okHttpClient.newCall(request).execute();
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

    // Use postJsonBody instead
    @Deprecated
    public Response postJsonPostRequest(String url, Object obj) throws IOException {
        return postJsonBody(url, obj);
    }

    public Response postJsonBody(String url, Object obj) throws IOException {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), toSnakeCaseJsonString(obj));
        Request request = new Request.Builder().url(url).post(body).build();
        return okHttpClient.newCall(request).execute();
    }

    public Response postJsonBodyWithBearerHeader(String url, String token, Object obj) throws IOException {
        String bearerHeaderValue = "Bearer " + token;
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), toSnakeCaseJsonString(obj));
        Request request = new Request.Builder().url(url).header("Authorization", bearerHeaderValue).post(body).build();
        return okHttpClient.newCall(request).execute();
    }

    public Response patchJsonBodyWithBearerHeader(String url, String token, Object obj) throws IOException {
        String bearerHeaderValue = "Bearer " + token;
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), toSnakeCaseJsonString(obj));
        Request request = new Request.Builder().url(url).header("Authorization", bearerHeaderValue).patch(body).build();
        return okHttpClient.newCall(request).execute();
    }

    public Response putJsonBodyWithBearerHeader(String url, String token, Object obj) throws IOException {
        String bearerHeaderValue = "Bearer " + token;
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), toSnakeCaseJsonString(obj));
        Request request = new Request.Builder().url(url).header("Authorization", bearerHeaderValue).put(body).build();
        return okHttpClient.newCall(request).execute();
    }

    public Response postCamelCaseJsonBodyWithBearerHeader(String url, String token, Object obj) throws IOException {
        String bearerHeaderValue = "Bearer " + token;
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), toCamelCaseJsonString(obj));
        Request request = new Request.Builder().url(url).header("Authorization", bearerHeaderValue).post(body).build();
        return okHttpClient.newCall(request).execute();
    }

    public Response patchCamelCaseJsonBodyWithBearerHeader(String url, String token, Object obj) throws IOException {
        String bearerHeaderValue = "Bearer " + token;
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), toCamelCaseJsonString(obj));
        Request request = new Request.Builder().url(url).header("Authorization", bearerHeaderValue).patch(body).build();
        return okHttpClient.newCall(request).execute();
    }

    public Response putCamelCaseJsonBodyWithBearerHeader(String url, String token, Object obj) throws IOException {
        String bearerHeaderValue = "Bearer " + token;
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), toCamelCaseJsonString(obj));
        Request request = new Request.Builder().url(url).header("Authorization", bearerHeaderValue).put(body).build();
        return okHttpClient.newCall(request).execute();
    }

    public Response delete(Request.Builder requestBuilder) throws IOException {
        return okHttpClient.newCall(requestBuilder.method("DELETE", null).build()).execute();
    }

    private String toSnakeCaseJsonString(Object obj) {
        if (obj instanceof String) {
            return (String) obj;
        } else {
            Gson gson = GsonFactory.createSnakeCase(config);
            return gson.toJson(obj);
        }
    }

    private String toCamelCaseJsonString(Object obj) {
        if (obj instanceof String) {
            return (String) obj;
        } else {
            Gson gson = GsonFactory.createCamelCase(config);
            return gson.toJson(obj);
        }
    }

    public void runHttpResponseListeners(Response response, String body) {
        HttpResponseListener.State state = new HttpResponseListener.State(config, response, body);
        for (HttpResponseListener responseListener : config.getHttpClientResponseHandlers()) {
            responseListener.accept(state);
        }
    }

}
