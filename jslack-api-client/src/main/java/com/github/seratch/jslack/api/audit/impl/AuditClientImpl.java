package com.github.seratch.jslack.api.audit.impl;

import com.github.seratch.jslack.api.RequestConfigurator;
import com.github.seratch.jslack.api.audit.AuditApiException;
import com.github.seratch.jslack.api.audit.AuditApiRequest;
import com.github.seratch.jslack.api.audit.AuditClient;
import com.github.seratch.jslack.api.audit.request.ActionsRequest;
import com.github.seratch.jslack.api.audit.request.LogsRequest;
import com.github.seratch.jslack.api.audit.request.SchemasRequest;
import com.github.seratch.jslack.api.audit.response.ActionsResponse;
import com.github.seratch.jslack.api.audit.response.LogsResponse;
import com.github.seratch.jslack.api.audit.response.SchemasResponse;
import com.github.seratch.jslack.common.http.SlackHttpClient;
import com.github.seratch.jslack.common.json.GsonFactory;
import okhttp3.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AuditClientImpl implements AuditClient {

    private static final String BASE_URL = "https://api.slack.com/audit/v1/";

    private final SlackHttpClient slackHttpClient;
    private final String token;

    public AuditClientImpl(SlackHttpClient slackHttpClient) {
        this(slackHttpClient, null);
    }

    public AuditClientImpl(SlackHttpClient slackHttpClient, String token) {
        this.slackHttpClient = slackHttpClient;
        this.token = token;
    }

    @Override
    public SchemasResponse getSchemas() throws IOException, AuditApiException {
        return getSchemas(SchemasRequest.builder().build());
    }

    @Override
    public SchemasResponse getSchemas(SchemasRequest req) throws IOException, AuditApiException {
        return doGet(BASE_URL + "schemas", null, getToken(req), SchemasResponse.class);
    }

    @Override
    public SchemasResponse getSchemas(RequestConfigurator<SchemasRequest.SchemasRequestBuilder> req) throws IOException, AuditApiException {
        return getSchemas(req.configure(SchemasRequest.builder()).build());
    }

    @Override
    public ActionsResponse getActions() throws IOException, AuditApiException {
        return getActions(ActionsRequest.builder().build());
    }

    @Override
    public ActionsResponse getActions(ActionsRequest req) throws IOException, AuditApiException {
        return doGet(BASE_URL + "actions", null, getToken(req), ActionsResponse.class);
    }

    @Override
    public ActionsResponse getActions(RequestConfigurator<ActionsRequest.ActionsRequestBuilder> req) throws IOException, AuditApiException {
        return getActions(req.configure(ActionsRequest.builder()).build());
    }

    @Override
    public LogsResponse getLogs(LogsRequest req) throws IOException, AuditApiException {
        Map<String, String> query = new HashMap<>();
        if (req.getLatest() != null) {
            query.put("latest", String.valueOf(req.getLatest()));
        }
        if (req.getOldest() != null) {
            query.put("oldest", String.valueOf(req.getOldest()));
        }
        if (req.getLimit() != null) {
            query.put("limit", String.valueOf(req.getLimit()));
        }
        if (req.getAction() != null) {
            query.put("action", req.getAction());
        }
        if (req.getActor() != null) {
            query.put("actor", req.getActor());
        }
        if (req.getEntity() != null) {
            query.put("entity", req.getEntity());
        }
        return doGet(BASE_URL + "logs", query, getToken(req), LogsResponse.class);
    }

    @Override
    public LogsResponse getLogs(RequestConfigurator<LogsRequest.LogsRequestBuilder> req) throws IOException, AuditApiException {
        return getLogs(req.configure(LogsRequest.builder()).build());
    }

    private String getToken(AuditApiRequest req) {
        if (req.getToken() != null) {
            return req.getToken();
        } else if (this.token != null) {
            return this.token;
        } else {
            throw new IllegalStateException("Slack OAuth token is missing! Set token in either SCIMClient or request object.");
        }
    }

    private <T> T doGet(String url, Map<String, String> query, String token, Class<T> clazz) throws IOException, AuditApiException {
        Response response = slackHttpClient.get(url, query, token);
        return parseJsonResponseAndRunListeners(response, clazz);
    }

    private <T> T parseJsonResponseAndRunListeners(Response response, Class<T> clazz) throws IOException, AuditApiException {
        String body = response.body().string();
        slackHttpClient.runHttpResponseListeners(response, body);
        if (response.isSuccessful()) {
            return GsonFactory.createSnakeCase(slackHttpClient.getConfig()).fromJson(body, clazz);
        } else {
            throw new AuditApiException(slackHttpClient.getConfig(), response, body);
        }
    }

}
