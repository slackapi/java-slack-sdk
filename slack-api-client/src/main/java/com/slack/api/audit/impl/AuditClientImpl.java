package com.slack.api.audit.impl;

import com.slack.api.RequestConfigurator;
import com.slack.api.SlackConfig;
import com.slack.api.audit.*;
import com.slack.api.audit.request.ActionsRequest;
import com.slack.api.audit.request.LogsRequest;
import com.slack.api.audit.request.SchemasRequest;
import com.slack.api.audit.response.ActionsResponse;
import com.slack.api.audit.response.LogsResponse;
import com.slack.api.audit.response.SchemasResponse;
import com.slack.api.methods.impl.TeamIdCache;
import com.slack.api.rate_limits.metrics.MetricsDatastore;
import com.slack.api.util.http.SlackHttpClient;
import com.slack.api.util.json.GsonFactory;
import okhttp3.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AuditClientImpl implements AuditClient {

    private String endpointUrlPrefix = getEndpointUrlPrefix();

    private final AuditConfig config;
    private final SlackHttpClient slackHttpClient;
    private final String token;
    private final boolean statsEnabled;
    private final TeamIdCache teamIdCache;
    private boolean attachRawBody = false;

    public AuditClientImpl(SlackConfig config, SlackHttpClient slackHttpClient, TeamIdCache teamIdCache) {
        this(config, slackHttpClient, teamIdCache, null);
    }

    public AuditClientImpl(SlackConfig config, SlackHttpClient slackHttpClient, TeamIdCache teamIdCache, String token) {
        this.config = config.getAuditConfig();
        this.slackHttpClient = slackHttpClient;
        this.token = token;
        this.statsEnabled = config.getAuditConfig().isStatsEnabled();
        this.teamIdCache = teamIdCache;
    }

    public String getEndpointUrlPrefix() {
        return endpointUrlPrefix;
    }

    public void setEndpointUrlPrefix(String endpointUrlPrefix) {
        this.endpointUrlPrefix = endpointUrlPrefix;
    }

    @Override
    public AuditClient attachRawBody(boolean attachRawBody) {
        this.attachRawBody = attachRawBody;
        return this;
    }

    @Override
    public SchemasResponse getSchemas() throws IOException, AuditApiException {
        return getSchemas(SchemasRequest.builder().build());
    }

    @Override
    public SchemasResponse getSchemas(SchemasRequest req) throws IOException, AuditApiException {
        return doGet(getEndpointUrlPrefix() + "schemas", null, getToken(req), SchemasResponse.class);
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
        return doGet(getEndpointUrlPrefix() + "actions", null, getToken(req), ActionsResponse.class);
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
        if (req.getCursor() != null) {
            query.put("cursor", req.getCursor());
        }
        return doGet(getEndpointUrlPrefix() + "logs", query, getToken(req), LogsResponse.class);
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

    private <T extends AuditApiResponse> T doGet(String url, Map<String, String> query, String token, Class<T> clazz) throws IOException, AuditApiException {
        String enterpriseId = null;
        if (statsEnabled) {
            // In the case where you verify org admin user's token,
            // the team_id in an auth.test API response is an enterprise_id value
            enterpriseId = teamIdCache.lookupOrResolve(token);
        }
        MetricsDatastore datastore = config.getMetricsDatastore();
        String executorName = config.getExecutorName();
        String[] elements = url.split("/");
        String key = elements[elements.length - 1];
        try {
            Response response = slackHttpClient.get(url, query, token);
            T result = parseJsonResponseAndRunListeners(response, clazz);
            datastore.incrementSuccessfulCalls(executorName, enterpriseId, key);
            return result;
        } catch (AuditApiException e) {
            if (enterpriseId != null) {
                datastore.incrementUnsuccessfulCalls(executorName, enterpriseId, key);
            }
            if (e.getResponse().code() == 429) {
                // rate limited
                final String retryAfterSeconds = e.getResponse().header("Retry-After");
                if (retryAfterSeconds != null) {
                    long secondsToWait = Long.valueOf(retryAfterSeconds);
                    long epochMillisToRetry = System.currentTimeMillis() + (secondsToWait * 1000L);
                    if (enterpriseId != null) {
                        datastore.setRateLimitedMethodRetryEpochMillis(executorName, enterpriseId, key, epochMillisToRetry);
                    }
                }
            }
            throw e;
        } catch (IOException e) {
            if (enterpriseId != null) {
                datastore.incrementFailedCalls(executorName, enterpriseId, key);
            }
            throw e;
        } finally {
            if (enterpriseId != null) {
                datastore.incrementAllCompletedCalls(executorName, enterpriseId, key);
                datastore.addToLastMinuteRequests(executorName, enterpriseId, key, System.currentTimeMillis());
            }
        }
    }

    private <T extends AuditApiResponse> T parseJsonResponseAndRunListeners(Response response, Class<T> clazz) throws IOException, AuditApiException {
        String body = response.body().string();
        slackHttpClient.runHttpResponseListeners(response, body);
        if (response.isSuccessful()) {
            T apiResponse = GsonFactory.createSnakeCase(slackHttpClient.getConfig()).fromJson(body, clazz);
            if (attachRawBody) {
                apiResponse.setRawBody(body);
            }
            return apiResponse;
        } else {
            throw new AuditApiException(slackHttpClient.getConfig(), response, body);
        }
    }

}
