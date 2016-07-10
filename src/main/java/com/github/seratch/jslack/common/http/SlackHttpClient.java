package com.github.seratch.jslack.common.http;

import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.common.json.GsonFactory;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okio.Buffer;

import java.io.IOException;

@Slf4j
public class SlackHttpClient {

    private final OkHttpClient okHttpClient;

    public SlackHttpClient() {
        this.okHttpClient = new OkHttpClient.Builder().build();
    }

    public SlackHttpClient(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    public Response postMultipart(String url, MultipartBody multipartBody) throws IOException {
        Request request = new Request.Builder().url(url).post(multipartBody).build();
        return okHttpClient.newCall(request).execute();
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

    public static void debugLog(Response response, String body) throws IOException {
        if (log.isDebugEnabled()) {
            Buffer requestBody = new Buffer();
            response.request().body().writeTo(requestBody);
            String textRequestBody = null;
            try {
                textRequestBody = requestBody.buffer().readUtf8();
            } catch (Exception e) {
                log.debug("Failed to read request body because {}, error: {}", e.getMessage(), e.getClass().getCanonicalName());
            }

            log.debug("\n[Request URL]\n{} {}\n" +
                            "[Specified Request Headers]\n{}" +
                            "[Request Body]\n{}\n" +
                            "Content-Type: {}\n" +
                            "Content Length: {}\n" +
                            "\n" +
                            "[Response Status]\n{} {}\n" +
                            "[Response Headers]\n{}" +
                            "[Response Body]\n{}\n",
                    response.request().method(),
                    response.request().url(),
                    response.request().headers(),
                    textRequestBody,
                    response.request().body().contentType(),
                    response.request().body().contentLength(),
                    response.code(),
                    response.message(),
                    response.headers(),
                    body);
        }
    }

    public static <T> T buildJsonResponse(Response response, Class<T> clazz) throws IOException, SlackApiException {
        if (response.code() == 200) {
            String body = response.body().string();
            debugLog(response, body);
            return GsonFactory.createSnakeCase().fromJson(body, clazz);
        } else {
            String body = response.body().string();
            throw new SlackApiException(response, body);
        }
    }
}
