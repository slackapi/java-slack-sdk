package com.slack.api.scim.impl;

import com.slack.api.RequestConfigurator;
import com.slack.api.SlackConfig;
import com.slack.api.methods.impl.TeamIdCache;
import com.slack.api.rate_limits.metrics.MetricsDatastore;
import com.slack.api.scim.*;
import com.slack.api.scim.request.*;
import com.slack.api.scim.response.*;
import com.slack.api.util.http.SlackHttpClient;
import com.slack.api.util.json.GsonFactory;
import kotlin.jvm.functions.Function0;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.slack.api.scim.SCIMEndpointName.*;

public class SCIMClientImpl implements SCIMClient {

    private String endpointUrlPrefix = ENDPOINT_URL_PREFIX;

    private final SlackHttpClient slackHttpClient;
    private final String token;
    private final SCIMConfig config;
    private final String executorName;
    private final TeamIdCache teamIdCache;

    public SCIMClientImpl(
            SlackConfig config,
            SlackHttpClient slackHttpClient,
            TeamIdCache teamIdCache
    ) {
        this(config, slackHttpClient, teamIdCache, null);
    }

    public SCIMClientImpl(
            SlackConfig config,
            SlackHttpClient slackHttpClient,
            TeamIdCache teamIdCache,
            String token
    ) {
        this.slackHttpClient = slackHttpClient;
        this.token = token;
        this.config = config.getSCIMConfig();
        this.executorName = this.config.getExecutorName();
        this.teamIdCache = teamIdCache;
    }

    // ------------------------------------------
    // public methods
    // ------------------------------------------

    @Override
    public String getEndpointUrlPrefix() {
        return this.endpointUrlPrefix;
    }

    @Override
    public void setEndpointUrlPrefix(String endpointUrlPrefix) {
        this.endpointUrlPrefix = endpointUrlPrefix;
    }

    @Override
    public ServiceProviderConfigsGetResponse getServiceProviderConfigs(ServiceProviderConfigsGetRequest req) throws IOException, SCIMApiException {
        return doGet(getServiceProviderConfigs, endpointUrlPrefix + "ServiceProviderConfigs", null, getToken(req), ServiceProviderConfigsGetResponse.class);
    }

    @Override
    public ServiceProviderConfigsGetResponse getServiceProviderConfigs(RequestConfigurator<ServiceProviderConfigsGetRequest.ServiceProviderConfigsGetRequestBuilder> req) throws IOException, SCIMApiException {
        return getServiceProviderConfigs(req.configure(ServiceProviderConfigsGetRequest.builder()).build());
    }

    @Override
    public UsersSearchResponse searchUsers(UsersSearchRequest req) throws IOException, SCIMApiException {
        Map<String, String> query = new HashMap<>();
        if (req.getFilter() != null) {
            query.put("filter", req.getFilter());
        }
        if (req.getCount() != null) {
            query.put("count", String.valueOf(req.getCount()));
        }
        if (req.getStartIndex() != null) {
            query.put("startIndex", String.valueOf(req.getStartIndex()));
        }
        return doGet(searchUsers, getUsersResourceURL(), query, getToken(req), UsersSearchResponse.class);
    }

    public UsersSearchResponse searchUsers(RequestConfigurator<UsersSearchRequest.UsersSearchRequestBuilder> req) throws IOException, SCIMApiException {
        return searchUsers(req.configure(UsersSearchRequest.builder()).build());
    }

    @Override
    public UsersReadResponse readUser(UsersReadRequest req) throws IOException, SCIMApiException {
        return doGet(readUser, getUsersResourceURL() + "/" + req.getId(), null, getToken(req), UsersReadResponse.class);
    }

    public UsersReadResponse readUser(RequestConfigurator<UsersReadRequest.UsersReadRequestBuilder> req) throws IOException, SCIMApiException {
        return readUser(req.configure(UsersReadRequest.builder()).build());
    }

    @Override
    public UsersCreateResponse createUser(UsersCreateRequest req) throws IOException, SCIMApiException {
        return doPost(createUser, getUsersResourceURL(), req.getUser(), getToken(req), UsersCreateResponse.class);
    }

    @Override
    public UsersCreateResponse createUser(RequestConfigurator<UsersCreateRequest.UsersCreateRequestBuilder> req) throws IOException, SCIMApiException {
        return createUser(req.configure(UsersCreateRequest.builder()).build());
    }

    @Override
    public UsersPatchResponse patchUser(UsersPatchRequest req) throws IOException, SCIMApiException {
        return doPatch(patchUser, getUsersResourceURL() + "/" + req.getId(), req.getUser(), getToken(req), UsersPatchResponse.class);
    }

    @Override
    public UsersPatchResponse patchUser(RequestConfigurator<UsersPatchRequest.UsersPatchRequestBuilder> req) throws IOException, SCIMApiException {
        return patchUser(req.configure(UsersPatchRequest.builder()).build());
    }

    @Override
    public UsersUpdateResponse updateUser(UsersUpdateRequest req) throws IOException, SCIMApiException {
        return doPut(updateUser, getUsersResourceURL() + "/" + req.getId(), req.getUser(), getToken(req), UsersUpdateResponse.class);
    }

    @Override
    public UsersUpdateResponse updateUser(RequestConfigurator<UsersUpdateRequest.UsersUpdateRequestBuilder> req) throws IOException, SCIMApiException {
        return updateUser(req.configure(UsersUpdateRequest.builder()).build());
    }

    @Override
    public UsersDeleteResponse deleteUser(UsersDeleteRequest req) throws IOException, SCIMApiException {
        Request.Builder requestBuilder = withAuthorizationHeader(new Request.Builder(), getToken(req))
                .url(getUsersResourceURL() + "/" + req.getId());
        return doDelete(deleteUser, requestBuilder, UsersDeleteResponse.class);
    }

    @Override
    public UsersDeleteResponse deleteUser(RequestConfigurator<UsersDeleteRequest.UsersDeleteRequestBuilder> req) throws IOException, SCIMApiException {
        return deleteUser(req.configure(UsersDeleteRequest.builder()).build());
    }

    @Override
    public GroupsSearchResponse searchGroups(GroupsSearchRequest req) throws IOException, SCIMApiException {
        Map<String, String> query = new HashMap<>();
        if (req.getFilter() != null) {
            query.put("filter", req.getFilter());
        }
        if (req.getCount() != null) {
            query.put("count", String.valueOf(req.getCount()));
        }
        if (req.getStartIndex() != null) {
            query.put("startIndex", String.valueOf(req.getStartIndex()));
        }
        return doGet(searchGroups, getGroupsResourceURL(), query, getToken(req), GroupsSearchResponse.class);
    }

    @Override
    public GroupsSearchResponse searchGroups(RequestConfigurator<GroupsSearchRequest.GroupsSearchRequestBuilder> req) throws IOException, SCIMApiException {
        return searchGroups(req.configure(GroupsSearchRequest.builder()).build());
    }

    @Override
    public GroupsReadResponse readGroup(GroupsReadRequest req) throws IOException, SCIMApiException {
        return doGet(readGroup, getGroupsResourceURL() + "/" + req.getId(), null, getToken(req), GroupsReadResponse.class);
    }

    @Override
    public GroupsReadResponse readGroup(RequestConfigurator<GroupsReadRequest.GroupsReadRequestBuilder> req) throws IOException, SCIMApiException {
        return readGroup(req.configure(GroupsReadRequest.builder()).build());
    }

    @Override
    public GroupsCreateResponse createGroup(GroupsCreateRequest req) throws IOException, SCIMApiException {
        return doPost(createGroup, getGroupsResourceURL(), req.getGroup(), getToken(req), GroupsCreateResponse.class);
    }

    @Override
    public GroupsCreateResponse createGroup(RequestConfigurator<GroupsCreateRequest.GroupsCreateRequestBuilder> req) throws IOException, SCIMApiException {
        return createGroup(req.configure(GroupsCreateRequest.builder()).build());
    }

    @Override
    public GroupsPatchResponse patchGroup(GroupsPatchRequest req) throws IOException, SCIMApiException {
        return doPatch(patchGroup, getGroupsResourceURL() + "/" + req.getId(), req.getGroup(), getToken(req), GroupsPatchResponse.class);
    }

    @Override
    public GroupsPatchResponse patchGroup(RequestConfigurator<GroupsPatchRequest.GroupsPatchRequestBuilder> req) throws IOException, SCIMApiException {
        return patchGroup(req.configure(GroupsPatchRequest.builder()).build());
    }

    @Override
    public GroupsUpdateResponse updateGroup(GroupsUpdateRequest req) throws IOException, SCIMApiException {
        return doPut(updateGroup, getGroupsResourceURL() + "/" + req.getId(), req.getGroup(), getToken(req), GroupsUpdateResponse.class);
    }

    @Override
    public GroupsUpdateResponse updateGroup(RequestConfigurator<GroupsUpdateRequest.GroupsUpdateRequestBuilder> req) throws IOException, SCIMApiException {
        return updateGroup(req.configure(GroupsUpdateRequest.builder()).build());
    }

    @Override
    public GroupsDeleteResponse deleteGroup(GroupsDeleteRequest req) throws IOException, SCIMApiException {
        Request.Builder requestBuilder = withAuthorizationHeader(new Request.Builder(), getToken(req))
                .url(getGroupsResourceURL() + "/" + req.getId());
        return doDelete(deleteGroup, requestBuilder, GroupsDeleteResponse.class);
    }

    @Override
    public GroupsDeleteResponse deleteGroup(RequestConfigurator<GroupsDeleteRequest.GroupsDeleteRequestBuilder> req) throws IOException, SCIMApiException {
        return deleteGroup(req.configure(GroupsDeleteRequest.builder()).build());
    }

    // ------------------------------------------
    // private methods
    // ------------------------------------------

    private String getToken(SCIMApiRequest req) {
        if (req.getToken() != null) {
            return req.getToken();
        } else if (this.token != null) {
            return this.token;
        } else {
            throw new IllegalStateException("Slack OAuth token is missing! Set token in either SCIMClient or request object.");
        }
    }

    private Request.Builder withAuthorizationHeader(Request.Builder req, String token) {
        return req.addHeader("Authorization", "Bearer " + token);
    }

    private String getUsersResourceURL() {
        return endpointUrlPrefix + "Users";
    }

    private String getGroupsResourceURL() {
        return endpointUrlPrefix + "Groups";
    }

    private String getEnterpriseIdForMetrics() {
        String enterpriseId = null;
        if (config.isStatsEnabled()) {
            // In the case where you verify org admin user's token,
            // the team_id in an auth.test API response is an enterprise_id value
            enterpriseId = teamIdCache.lookupOrResolve(token);
        }
        return enterpriseId;
    }

    private <T> T handle(
            SCIMEndpointName name,
            Class<T> clazz,
            Function0<Response> performRequest
    ) throws IOException, SCIMApiException {
        String enterpriseId = getEnterpriseIdForMetrics();
        MetricsDatastore datastore = config.getMetricsDatastore();
        try {
            Response response = performRequest.invoke();
            T result = parseCamelCaseJsonResponseAndRunListeners(response, clazz);
            if (enterpriseId != null) {
                datastore.incrementSuccessfulCalls(executorName, enterpriseId, name.name());
            }
            return result;
        } catch (SCIMApiException e) {
            if (enterpriseId != null) {
                datastore.incrementUnsuccessfulCalls(executorName, enterpriseId, name.name());
            }
            if (e.getResponse().code() == 429) {
                // rate limited
                final String retryAfterSeconds = e.getResponse().header("Retry-After");
                if (retryAfterSeconds != null) {
                    long secondsToWait = Long.valueOf(retryAfterSeconds);
                    long epochMillisToRetry = System.currentTimeMillis() + (secondsToWait * 1000L);
                    if (enterpriseId != null) {
                        datastore.setRateLimitedMethodRetryEpochMillis(
                                executorName, enterpriseId, name.name(), epochMillisToRetry);
                    }
                }
            }
            throw e;

        } catch (RuntimeException e) {
            if (enterpriseId != null) {
                datastore.incrementFailedCalls(executorName, enterpriseId, name.name());
            }
            if (e.getCause() instanceof IOException) {
                IOException ioe = (IOException) e.getCause();
                throw ioe;
            } else {
                throw e;
            }
        } finally {
            if (enterpriseId != null) {
                datastore.incrementAllCompletedCalls(executorName, enterpriseId, name.name());
                datastore.addToLastMinuteRequests(
                        executorName, enterpriseId, name.name(), System.currentTimeMillis());
            }
        }
    }

    private <T> T doGet(
            SCIMEndpointName name,
            String url,
            Map<String, String> query,
            String token,
            Class<T> clazz
    ) throws IOException, SCIMApiException {
        return handle(name, clazz, () -> {
            try {
                return slackHttpClient.get(url, query, token);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private <T> T doPost(
            SCIMEndpointName name,
            String url,
            Object body,
            String token,
            Class<T> clazz
    ) throws IOException, SCIMApiException {
        return handle(name, clazz, () -> {
            try {
                return slackHttpClient.postCamelCaseJsonBodyWithBearerHeader(url, token, body);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private <T> T doPatch(
            SCIMEndpointName name,
            String url,
            Object body,
            String token,
            Class<T> clazz
    ) throws IOException, SCIMApiException {
        return handle(name, clazz, () -> {
            try {
                return slackHttpClient.patchCamelCaseJsonBodyWithBearerHeader(url, token, body);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private <T> T doPut(
            SCIMEndpointName name,
            String url,
            Object body,
            String token,
            Class<T> clazz
    ) throws IOException, SCIMApiException {
        return handle(name, clazz, () -> {
            try {
                return slackHttpClient.putCamelCaseJsonBodyWithBearerHeader(url, token, body);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private <T> T doDelete(
            SCIMEndpointName name,
            Request.Builder requestBuilder,
            Class<T> clazz
    ) throws IOException, SCIMApiException {
        return handle(name, clazz, () -> {
            try {
                return slackHttpClient.delete(requestBuilder);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private <T> T parseCamelCaseJsonResponseAndRunListeners(
            Response response,
            Class<T> clazz
    ) throws IOException, SCIMApiException {
        String body = response.body().string();
        try {
            if (response.isSuccessful()) {
                return GsonFactory.createCamelCase(slackHttpClient.getConfig()).fromJson(body, clazz);
            } else {
                throw new SCIMApiException(slackHttpClient.getConfig(), response, body);
            }
        } finally {
            slackHttpClient.runHttpResponseListeners(response, body);
        }
    }


}
