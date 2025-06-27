package com.slack.api.audit;

import com.slack.api.RequestConfigurator;
import com.slack.api.audit.request.ActionsRequest;
import com.slack.api.audit.request.LogsRequest;
import com.slack.api.audit.request.SchemasRequest;
import com.slack.api.audit.response.ActionsResponse;
import com.slack.api.audit.response.LogsResponse;
import com.slack.api.audit.response.SchemasResponse;

import java.util.concurrent.CompletableFuture;

/**
 * Audit Logs API client.
 * <p>
 * Monitor what's happening in your Enterprise Grid organization using Slack's Audit Logs API.
 * The Audit Logs API can be used by security information and event management (SIEM) tools
 * to provide analysis of how your Slack organization is being accessed.
 * <p>
 * You can also use this API to write your own applications to see how members of your organization are using Slack.
 *
 * @see <a href="https://docs.slack.dev/admins/audit-logs-api">Slack Audit Logs API</a>
 */
public interface AsyncAuditClient {

    AsyncAuditClient attachRawBody(boolean attachRawBody);

    CompletableFuture<SchemasResponse> getSchemas();

    CompletableFuture<SchemasResponse> getSchemas(SchemasRequest req);

    CompletableFuture<SchemasResponse> getSchemas(RequestConfigurator<SchemasRequest.SchemasRequestBuilder> req);

    CompletableFuture<ActionsResponse> getActions();

    CompletableFuture<ActionsResponse> getActions(ActionsRequest req);

    CompletableFuture<ActionsResponse> getActions(RequestConfigurator<ActionsRequest.ActionsRequestBuilder> req);

    CompletableFuture<LogsResponse> getLogs(LogsRequest req);

    CompletableFuture<LogsResponse> getLogs(RequestConfigurator<LogsRequest.LogsRequestBuilder> req);

}
