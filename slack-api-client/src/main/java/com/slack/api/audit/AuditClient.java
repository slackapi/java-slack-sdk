package com.slack.api.audit;

import com.slack.api.RequestConfigurator;
import com.slack.api.audit.request.ActionsRequest;
import com.slack.api.audit.request.LogsRequest;
import com.slack.api.audit.request.SchemasRequest;
import com.slack.api.audit.response.ActionsResponse;
import com.slack.api.audit.response.LogsResponse;
import com.slack.api.audit.response.SchemasResponse;

import java.io.IOException;

/**
 * Audit Logs API client.
 * <p>
 * Monitor what's happening in your Enterprise Grid organization using Slack's Audit Logs API.
 * The Audit Logs API can be used by security information and event management (SIEM) tools
 * to provide analysis of how your Slack organization is being accessed.
 * <p>
 * You can also use this API to write your own applications to see how members of your organization are using Slack.
 *
 * @see <a href="https://api.slack.com/docs/audit-logs-api>Slack Audit Logs API</a>
 */
public interface AuditClient {

    String ENDPOINT_URL_PREFIX = "https://api.slack.com/audit/v1/";

    AuditClient attachRawBody(boolean attachRawBody);

    SchemasResponse getSchemas() throws IOException, AuditApiException;

    SchemasResponse getSchemas(SchemasRequest req) throws IOException, AuditApiException;

    SchemasResponse getSchemas(RequestConfigurator<SchemasRequest.SchemasRequestBuilder> req) throws IOException, AuditApiException;

    ActionsResponse getActions() throws IOException, AuditApiException;

    ActionsResponse getActions(ActionsRequest req) throws IOException, AuditApiException;

    ActionsResponse getActions(RequestConfigurator<ActionsRequest.ActionsRequestBuilder> req) throws IOException, AuditApiException;

    LogsResponse getLogs(LogsRequest req) throws IOException, AuditApiException;

    LogsResponse getLogs(RequestConfigurator<LogsRequest.LogsRequestBuilder> req) throws IOException, AuditApiException;

}
