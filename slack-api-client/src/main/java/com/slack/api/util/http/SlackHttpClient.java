package com.slack.api.util.http;

import com.google.gson.Gson;
import com.slack.api.SlackConfig;
import com.slack.api.util.http.listener.HttpResponseListener;
import com.slack.api.util.json.GsonFactory;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * The Slack API HTTP Client.
 */
@Slf4j
public class SlackHttpClient implements AutoCloseable {

    private static final MediaType MEDIA_TYPE_APPLICATION_JSON = MediaType.parse("application/json; charset=utf-8");

    private final OkHttpClient okHttpClient;

    private SlackConfig config = SlackConfig.DEFAULT;

    public static SlackHttpClient buildSlackHttpClient(SlackConfig config) {
        return buildSlackHttpClient(config, Collections.emptyMap());
    }

    public static SlackHttpClient buildSlackHttpClient(SlackConfig config, Map<String, String> userAgentCustomInfo) {
        SlackHttpClient httpClient = new SlackHttpClient(buildOkHttpClient(config, userAgentCustomInfo));
        httpClient.setConfig(config);
        return httpClient;
    }

    public static OkHttpClient buildOkHttpClient(SlackConfig config) {
        return buildOkHttpClient(config, Collections.emptyMap());
    }

    public static OkHttpClient buildOkHttpClient(SlackConfig config, Map<String, String> userAgentCustomInfo) {
        final OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.addInterceptor(new UserAgentInterceptor(userAgentCustomInfo));
        if (config.getHttpClientReadTimeoutMillis() != null) {
            okHttpClient.readTimeout(config.getHttpClientReadTimeoutMillis(), TimeUnit.MILLISECONDS);
        }
        if (config.getHttpClientWriteTimeoutMillis() != null) {
            okHttpClient.writeTimeout(config.getHttpClientWriteTimeoutMillis(), TimeUnit.MILLISECONDS);
        }
        if (config.getHttpClientCallTimeoutMillis() != null) {
            okHttpClient.callTimeout(config.getHttpClientCallTimeoutMillis(), TimeUnit.MILLISECONDS);
        }
        Map<String, String> proxyHeaders = config.getProxyHeaders();
        if (config.getProxyUrl() != null && !config.getProxyUrl().trim().isEmpty()) {
            ProxyUrlUtil.ProxyUrl parsedProxy = ProxyUrlUtil.parse(config.getProxyUrl());
            InetSocketAddress address = new InetSocketAddress(parsedProxy.getHost(), parsedProxy.getPort());
            Proxy proxy = new Proxy(Proxy.Type.HTTP, address);
            if (parsedProxy.getUsername() != null && parsedProxy.getPassword() != null) {
                if (proxyHeaders == null) {
                    proxyHeaders = new HashMap<>();
                }
                ProxyUrlUtil.setProxyAuthorizationHeader(proxyHeaders, parsedProxy);
            }
            okHttpClient.proxy(proxy);
        }
        if (proxyHeaders != null && !proxyHeaders.isEmpty()) {
            final Map<String, String> _proxyHeaders = proxyHeaders;
            Authenticator authenticator = (route, response) -> {
                Headers headers = response.request().headers();
                Headers modifiedHeaders = headers.newBuilder()
                        .addAll(Headers.of(_proxyHeaders))
                        .build();
                return response.request().newBuilder()
                        .headers(modifiedHeaders)
                        .build();
            };
            okHttpClient.proxyAuthenticator(authenticator);
        }
        return okHttpClient.build();
    }

    public SlackHttpClient() {
        this(Collections.emptyMap());
    }

    public SlackHttpClient(Map<String, String> userAgentCustomInfo) {
        this(SlackConfig.DEFAULT, userAgentCustomInfo);
    }

    public SlackHttpClient(SlackConfig config, Map<String, String> userAgentCustomInfo) {
        this.okHttpClient = buildOkHttpClient(config, userAgentCustomInfo);
    }

    public SlackHttpClient(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    @Override
    public void close() throws Exception {
        this.okHttpClient.dispatcher().executorService().shutdown();
        this.okHttpClient.connectionPool().evictAll();
        if (this.okHttpClient.cache() != null) {
            this.okHttpClient.cache().close();
        }
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
            Request.Builder rb = new Request.Builder().url(url).get();
            try {
                // may throw an IllegalArgumentException saying
                // "Unexpected char 0x0a at 23 in Authorization value: Bearer ..."
                rb = rb.header("Authorization", bearerHeaderValue);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid value detected for Authorization header");
            }
            request = rb.build();
            return okHttpClient.newCall(request).execute();
        } else {
            request = new Request.Builder().url(url).get().build();
        }
        return okHttpClient.newCall(request).execute();
    }

    public Response postMultipart(String url, String token, MultipartBody multipartBody) throws IOException {
        String bearerHeaderValue = "Bearer " + token;
        Request.Builder rb = new Request.Builder().url(url).post(multipartBody);
        try {
            // may throw an IllegalArgumentException saying
            // "Unexpected char 0x0a at 23 in Authorization value: Bearer ..."
            rb = rb.header("Authorization", bearerHeaderValue);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid value detected for Authorization header");
        }
        Request request = rb.build();
        return okHttpClient.newCall(request).execute();
    }

    public Response postForm(String url, FormBody formBody) throws IOException {
        Request request = new Request.Builder().url(url).post(formBody).build();
        return okHttpClient.newCall(request).execute();
    }

    public Response postFormWithBearerHeader(String url, String token, FormBody formBody) throws IOException {
        String bearerHeaderValue = "Bearer " + token;
        return postFormWithAuthorizationHeader(url, bearerHeaderValue, formBody);
    }

    public Response postFormWithAuthorizationHeader(String url, String authorizationHeader, FormBody formBody) throws IOException {
        Request.Builder rb = new Request.Builder().url(url).post(formBody);
        try {
            // may throw an IllegalArgumentException saying
            // "Unexpected char 0x0a at 23 in Authorization value: Bearer ..."
            rb = rb.header("Authorization", authorizationHeader);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid value detected for Authorization header");
        }
        Request request = rb.build();
        return okHttpClient.newCall(request).execute();
    }

    public Response postJsonBody(String url, Object obj) throws IOException {
        RequestBody body = RequestBody.create(toSnakeCaseJsonString(obj), MEDIA_TYPE_APPLICATION_JSON);
        Request request = new Request.Builder().url(url).post(body).build();
        return okHttpClient.newCall(request).execute();
    }

    public Response postCamelCaseJsonBodyWithBearerHeader(String url, String token, Object obj) throws IOException {
        String bearerHeaderValue = "Bearer " + token;
        RequestBody body = RequestBody.create(toCamelCaseJsonString(obj), MEDIA_TYPE_APPLICATION_JSON);
        Request.Builder rb = new Request.Builder().url(url).post(body);
        try {
            // may throw an IllegalArgumentException saying
            // "Unexpected char 0x0a at 23 in Authorization value: Bearer ..."
            rb = rb.header("Authorization", bearerHeaderValue);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid value detected for Authorization header");
        }
        Request request = rb.build();
        return okHttpClient.newCall(request).execute();
    }

    public Response patchCamelCaseJsonBodyWithBearerHeader(String url, String token, Object obj) throws IOException {
        String bearerHeaderValue = "Bearer " + token;
        RequestBody body = RequestBody.create(toCamelCaseJsonString(obj), MEDIA_TYPE_APPLICATION_JSON);
        Request.Builder rb = new Request.Builder().url(url).patch(body);
        try {
            // may throw an IllegalArgumentException saying
            // "Unexpected char 0x0a at 23 in Authorization value: Bearer ..."
            rb = rb.header("Authorization", bearerHeaderValue);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid value detected for Authorization header");
        }
        Request request = rb.build();
        return okHttpClient.newCall(request).execute();
    }

    public Response putCamelCaseJsonBodyWithBearerHeader(String url, String token, Object obj) throws IOException {
        String bearerHeaderValue = "Bearer " + token;
        RequestBody body = RequestBody.create(toCamelCaseJsonString(obj), MEDIA_TYPE_APPLICATION_JSON);
        Request.Builder rb = new Request.Builder().url(url).put(body);
        try {
            // may throw an IllegalArgumentException saying
            // "Unexpected char 0x0a at 23 in Authorization value: Bearer ..."
            rb = rb.header("Authorization", bearerHeaderValue);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid value detected for Authorization header");
        }
        Request request = rb.build();
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
