package com.github.seratch.jslack.common.http;

import com.github.seratch.jslack.SlackConfig;
import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.common.json.GsonFactory;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okio.Buffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@Slf4j
public class SlackHttpClient {

    private static final Logger JSON_RESPONSE_LOGGER = LoggerFactory.getLogger("com.github.seratach.jslack.maintainer.json");

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

    public static void debugLog(Response response, String body) throws IOException {
        debugLog(response, body, SlackConfig.DEFAULT);
    }

    public static void debugLog(Response response, String body, SlackConfig config) throws IOException {
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
                            "[Request Body]\n{}\n\n" +
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

            if (config.isPrettyResponseLoggingEnabled() && body != null && body.trim().startsWith("{")) {
                JsonParser parser = new JsonParser();
                JsonElement jsonObj = parser.parse(body);
                String prettifiedJson = GsonFactory.createSnakeCase(config).toJson(jsonObj);
                JSON_RESPONSE_LOGGER.debug("--- Pretty printing the response ---\n" +
                        prettifiedJson + "\n" +
                        "-----------------------------------------");
            }
        }
    }

    public <T> T parseJsonResponse(Response response, Class<T> clazz) throws IOException, SlackApiException {
        if (response.code() == 200) {
            String body = response.body().string();
            debugLog(response, body, config);
            return GsonFactory.createSnakeCase(config).fromJson(body, clazz);
        } else {
            String body = response.body().string();
            throw new SlackApiException(response, body);
        }
    }

    // use parseJsonResponse instead
    @Deprecated
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
