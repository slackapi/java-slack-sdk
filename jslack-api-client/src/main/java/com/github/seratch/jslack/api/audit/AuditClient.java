package com.github.seratch.jslack.api.audit;

import com.github.seratch.jslack.api.RequestConfigurator;
import com.github.seratch.jslack.api.audit.request.ActionsRequest;
import com.github.seratch.jslack.api.audit.request.LogsRequest;
import com.github.seratch.jslack.api.audit.request.SchemasRequest;
import com.github.seratch.jslack.api.audit.response.ActionsResponse;
import com.github.seratch.jslack.api.audit.response.LogsResponse;
import com.github.seratch.jslack.api.audit.response.SchemasResponse;

import java.io.IOException;

/**
 * https://api.slack.com/docs/audit-logs-api
 */
public interface AuditClient {

    String ENDPOINT_URL_PREFIX = "https://api.slack.com/audit/v1/";

    SchemasResponse getSchemas() throws IOException, AuditApiException;

    SchemasResponse getSchemas(SchemasRequest req) throws IOException, AuditApiException;

    SchemasResponse getSchemas(RequestConfigurator<SchemasRequest.SchemasRequestBuilder> req) throws IOException, AuditApiException;

    ActionsResponse getActions() throws IOException, AuditApiException;

    ActionsResponse getActions(ActionsRequest req) throws IOException, AuditApiException;

    ActionsResponse getActions(RequestConfigurator<ActionsRequest.ActionsRequestBuilder> req) throws IOException, AuditApiException;

    LogsResponse getLogs(LogsRequest req) throws IOException, AuditApiException;

    LogsResponse getLogs(RequestConfigurator<LogsRequest.LogsRequestBuilder> req) throws IOException, AuditApiException;

}
