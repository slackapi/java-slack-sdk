package com.github.seratch.jslack.api.audit;

import com.github.seratch.jslack.api.audit.request.ActionsRequest;
import com.github.seratch.jslack.api.audit.request.LogsRequest;
import com.github.seratch.jslack.api.audit.request.SchemasRequest;
import com.github.seratch.jslack.api.audit.response.ActionsResponse;
import com.github.seratch.jslack.api.audit.response.LogsResponse;
import com.github.seratch.jslack.api.audit.response.SchemasResponse;
import com.github.seratch.jslack.api.methods.RequestConfigurator;
import com.github.seratch.jslack.api.methods.SlackApiException;

import java.io.IOException;

/**
 * https://api.slack.com/docs/audit-logs-api
 */
public interface AuditClient {

    SchemasResponse getSchemas() throws IOException, SlackApiException;

    SchemasResponse getSchemas(SchemasRequest req) throws IOException, SlackApiException;

    SchemasResponse getSchemas(RequestConfigurator<SchemasRequest.SchemasRequestBuilder> req) throws IOException, SlackApiException;

    ActionsResponse getActions() throws IOException, SlackApiException;

    ActionsResponse getActions(ActionsRequest req) throws IOException, SlackApiException;

    ActionsResponse getActions(RequestConfigurator<ActionsRequest.ActionsRequestBuilder> req) throws IOException, SlackApiException;

    LogsResponse getLogs(LogsRequest req) throws IOException, SlackApiException;

    LogsResponse getLogs(RequestConfigurator<LogsRequest.LogsRequestBuilder> req) throws IOException, SlackApiException;

}
