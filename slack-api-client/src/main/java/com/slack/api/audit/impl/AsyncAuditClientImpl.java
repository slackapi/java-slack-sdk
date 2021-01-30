package com.slack.api.audit.impl;

import com.slack.api.RequestConfigurator;
import com.slack.api.SlackConfig;
import com.slack.api.audit.AsyncAuditClient;
import com.slack.api.audit.AuditApiRequest;
import com.slack.api.audit.request.ActionsRequest;
import com.slack.api.audit.request.LogsRequest;
import com.slack.api.audit.request.SchemasRequest;
import com.slack.api.audit.response.ActionsResponse;
import com.slack.api.audit.response.LogsResponse;
import com.slack.api.audit.response.SchemasResponse;
import com.slack.api.methods.impl.MethodsClientImpl;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
public class AsyncAuditClientImpl implements AsyncAuditClient {

    private final String token;
    private final AuditClientImpl underlying;
    private final AsyncRateLimitExecutor executor;

    public AsyncAuditClientImpl(
            String token,
            AuditClientImpl audit,
            MethodsClientImpl methods,
            SlackConfig config
    ) {
        this.token = token;
        this.underlying = audit;
        this.executor = AsyncRateLimitExecutor.getOrCreate(methods, config);
    }

    private String token(AuditApiRequest req) {
        if (req.getToken() != null) {
            return req.getToken();
        } else {
            return this.token;
        }
    }

    private Map<String, String> toMap(AuditApiRequest req) {
        Map<String, String> params = new HashMap<>();
        params.put("token", token(req));
        return params;
    }

    @Override
    public AsyncAuditClient attachRawBody(boolean attachRawBody) {
        this.underlying.attachRawBody(attachRawBody);
        return this;
    }

    // ----------------------------------------------------------------------------------
    // public methods
    // ----------------------------------------------------------------------------------

    @Override
    public CompletableFuture<SchemasResponse> getSchemas() {
        return executor.execute("schemas", Collections.emptyMap(), () -> this.underlying.getSchemas());
    }

    @Override
    public CompletableFuture<SchemasResponse> getSchemas(SchemasRequest req) {
        return executor.execute("schemas", toMap(req), () -> this.underlying.getSchemas(req));
    }

    @Override
    public CompletableFuture<SchemasResponse> getSchemas(RequestConfigurator<SchemasRequest.SchemasRequestBuilder> req) {
        return getSchemas(req.configure(SchemasRequest.builder()).build());
    }

    @Override
    public CompletableFuture<ActionsResponse> getActions() {
        return executor.execute("actions", Collections.emptyMap(), () -> this.underlying.getActions());
    }

    @Override
    public CompletableFuture<ActionsResponse> getActions(ActionsRequest req) {
        return executor.execute("actions", toMap(req), () -> this.underlying.getActions(req));
    }

    @Override
    public CompletableFuture<ActionsResponse> getActions(RequestConfigurator<ActionsRequest.ActionsRequestBuilder> req) {
        return getActions(req.configure(ActionsRequest.builder()).build());
    }

    @Override
    public CompletableFuture<LogsResponse> getLogs(LogsRequest req) {
        return executor.execute("logs", toMap(req), () -> this.underlying.getLogs(req));
    }

    @Override
    public CompletableFuture<LogsResponse> getLogs(RequestConfigurator<LogsRequest.LogsRequestBuilder> req) {
        return getLogs(req.configure(LogsRequest.builder()).build());
    }

}
