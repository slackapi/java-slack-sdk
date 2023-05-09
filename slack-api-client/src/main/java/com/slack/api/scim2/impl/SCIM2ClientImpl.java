package com.slack.api.scim2.impl;

import com.google.gson.annotations.SerializedName;
import com.slack.api.RequestConfigurator;
import com.slack.api.SlackConfig;
import com.slack.api.methods.impl.TeamIdCache;
import com.slack.api.rate_limits.metrics.MetricsDatastore;
import com.slack.api.scim2.*;
import com.slack.api.scim2.request.*;
import com.slack.api.scim2.response.*;
import com.slack.api.util.http.SlackHttpClient;
import com.slack.api.util.json.GsonFactory;
import lombok.Builder;
import lombok.Data;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.slack.api.scim2.SCIM2EndpointName.*;

public class SCIM2ClientImpl implements SCIM2Client {

    private String endpointUrlPrefix = ENDPOINT_URL_PREFIX;

    private final SlackHttpClient slackHttpClient;
    private final String token;
    private final SCIM2Config config;
    private final String executorName;
    private final TeamIdCache teamIdCache;

    public SCIM2ClientImpl(
            SlackConfig config,
            SlackHttpClient slackHttpClient,
            TeamIdCache teamIdCache
    ) {
        this(config, slackHttpClient, teamIdCache, null);
    }

    public SCIM2ClientImpl(
            SlackConfig config,
            SlackHttpClient slackHttpClient,
            TeamIdCache teamIdCache,
            String token
    ) {
        this.slackHttpClient = slackHttpClient;
        this.token = token;
        this.config = config.getSCIM2Config();
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
    public ServiceProviderConfigsGetResponse getServiceProviderConfigs(ServiceProviderConfigsGetRequest req) throws IOException, SCIM2ApiException {
        return doGet(getServiceProviderConfigs, endpointUrlPrefix + "ServiceProviderConfigs", null, getToken(req), ServiceProviderConfigsGetResponse.class);
    }

    @Override
    public ServiceProviderConfigsGetResponse getServiceProviderConfigs(RequestConfigurator<ServiceProviderConfigsGetRequest.ServiceProviderConfigsGetRequestBuilder> req) throws IOException, SCIM2ApiException {
        return getServiceProviderConfigs(req.configure(ServiceProviderConfigsGetRequest.builder()).build());
    }

    @Override
    public ResourceTypesGetResponse getResourceTypes(ResourceTypesGetRequest req) throws IOException, SCIM2ApiException {
        return doGet(getResourceTypes, endpointUrlPrefix + "ResourceTypes", null, getToken(req), ResourceTypesGetResponse.class);
    }

    @Override
    public ResourceTypesGetResponse getResourceTypes(RequestConfigurator<ResourceTypesGetRequest.ResourceTypesGetRequestBuilder> req) throws IOException, SCIM2ApiException {
        return getResourceTypes(req.configure(ResourceTypesGetRequest.builder()).build());
    }

    @Override
    public UsersSearchResponse searchUsers(UsersSearchRequest req) throws IOException, SCIM2ApiException {
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

    public UsersSearchResponse searchUsers(RequestConfigurator<UsersSearchRequest.UsersSearchRequestBuilder> req) throws IOException, SCIM2ApiException {
        return searchUsers(req.configure(UsersSearchRequest.builder()).build());
    }

    @Override
    public UsersReadResponse readUser(UsersReadRequest req) throws IOException, SCIM2ApiException {
        return doGet(readUser, getUsersResourceURL() + "/" + req.getId(), null, getToken(req), UsersReadResponse.class);
    }

    public UsersReadResponse readUser(RequestConfigurator<UsersReadRequest.UsersReadRequestBuilder> req) throws IOException, SCIM2ApiException {
        return readUser(req.configure(UsersReadRequest.builder()).build());
    }

    @Override
    public UsersCreateResponse createUser(UsersCreateRequest req) throws IOException, SCIM2ApiException {
        return doPost(createUser, getUsersResourceURL(), req.getUser(), getToken(req), UsersCreateResponse.class);
    }

    @Override
    public UsersCreateResponse createUser(RequestConfigurator<UsersCreateRequest.UsersCreateRequestBuilder> req) throws IOException, SCIM2ApiException {
        return createUser(req.configure(UsersCreateRequest.builder()).build());
    }

    @Data
    @Builder
    public static class UsersPatchRequestBody {
        private List<String> schemas;
        @SerializedName("Operations")
        private List<UsersPatchOperation.Serializable> operations;
    }

    @Override
    public UsersPatchResponse patchUser(UsersPatchRequest req) throws IOException, SCIM2ApiException {
        UsersPatchRequestBody body = UsersPatchRequestBody.builder()
                .schemas(req.getSchemas())
                .operations(req.getOperations().stream().map(op -> op.toSerializable()).collect(Collectors.toList()))
                .build();
        return doPatch(patchUser, getUsersResourceURL() + "/" + req.getId(), body, getToken(req), UsersPatchResponse.class);
    }

    @Override
    public UsersPatchResponse patchUser(RequestConfigurator<UsersPatchRequest.UsersPatchRequestBuilder> req) throws IOException, SCIM2ApiException {
        return patchUser(req.configure(UsersPatchRequest.builder()).build());
    }

    @Override
    public UsersUpdateResponse updateUser(UsersUpdateRequest req) throws IOException, SCIM2ApiException {
        return doPut(updateUser, getUsersResourceURL() + "/" + req.getId(), req.getUser(), getToken(req), UsersUpdateResponse.class);
    }

    @Override
    public UsersUpdateResponse updateUser(RequestConfigurator<UsersUpdateRequest.UsersUpdateRequestBuilder> req) throws IOException, SCIM2ApiException {
        return updateUser(req.configure(UsersUpdateRequest.builder()).build());
    }

    @Override
    public UsersDeleteResponse deleteUser(UsersDeleteRequest req) throws IOException, SCIM2ApiException {
        Request.Builder requestBuilder = withAuthorizationHeader(new Request.Builder(), getToken(req))
                .url(getUsersResourceURL() + "/" + req.getId());
        return doDelete(deleteUser, requestBuilder, UsersDeleteResponse.class);
    }

    @Override
    public UsersDeleteResponse deleteUser(RequestConfigurator<UsersDeleteRequest.UsersDeleteRequestBuilder> req) throws IOException, SCIM2ApiException {
        return deleteUser(req.configure(UsersDeleteRequest.builder()).build());
    }

    @Override
    public GroupsSearchResponse searchGroups(GroupsSearchRequest req) throws IOException, SCIM2ApiException {
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
    public GroupsSearchResponse searchGroups(RequestConfigurator<GroupsSearchRequest.GroupsSearchRequestBuilder> req) throws IOException, SCIM2ApiException {
        return searchGroups(req.configure(GroupsSearchRequest.builder()).build());
    }

    @Override
    public GroupsReadResponse readGroup(GroupsReadRequest req) throws IOException, SCIM2ApiException {
        return doGet(readGroup, getGroupsResourceURL() + "/" + req.getId(), null, getToken(req), GroupsReadResponse.class);
    }

    @Override
    public GroupsReadResponse readGroup(RequestConfigurator<GroupsReadRequest.GroupsReadRequestBuilder> req) throws IOException, SCIM2ApiException {
        return readGroup(req.configure(GroupsReadRequest.builder()).build());
    }

    @Override
    public GroupsCreateResponse createGroup(GroupsCreateRequest req) throws IOException, SCIM2ApiException {
        return doPost(createGroup, getGroupsResourceURL(), req.getGroup(), getToken(req), GroupsCreateResponse.class);
    }

    @Override
    public GroupsCreateResponse createGroup(RequestConfigurator<GroupsCreateRequest.GroupsCreateRequestBuilder> req) throws IOException, SCIM2ApiException {
        return createGroup(req.configure(GroupsCreateRequest.builder()).build());
    }

    @Data
    @Builder
    public static class GroupsPatchRequestBody {
        private List<String> schemas;
        @SerializedName("Operations")
        private List<GroupsPatchOperation.Serializable> operations;
    }

    @Override
    public GroupsPatchResponse patchGroup(GroupsPatchRequest req) throws IOException, SCIM2ApiException {
        GroupsPatchRequestBody body = GroupsPatchRequestBody.builder()
                .schemas(req.getSchemas())
                .operations(req.getOperations().stream().map(op -> op.toSerializable()).collect(Collectors.toList()))
                .build();
        return doPatch(patchGroup, getGroupsResourceURL() + "/" + req.getId(), body, getToken(req), GroupsPatchResponse.class);
    }

    @Override
    public GroupsPatchResponse patchGroup(RequestConfigurator<GroupsPatchRequest.GroupsPatchRequestBuilder> req) throws IOException, SCIM2ApiException {
        return patchGroup(req.configure(GroupsPatchRequest.builder()).build());
    }

    @Override
    public GroupsUpdateResponse updateGroup(GroupsUpdateRequest req) throws IOException, SCIM2ApiException {
        return doPut(updateGroup, getGroupsResourceURL() + "/" + req.getId(), req.getGroup(), getToken(req), GroupsUpdateResponse.class);
    }

    @Override
    public GroupsUpdateResponse updateGroup(RequestConfigurator<GroupsUpdateRequest.GroupsUpdateRequestBuilder> req) throws IOException, SCIM2ApiException {
        return updateGroup(req.configure(GroupsUpdateRequest.builder()).build());
    }

    @Override
    public GroupsDeleteResponse deleteGroup(GroupsDeleteRequest req) throws IOException, SCIM2ApiException {
        Request.Builder requestBuilder = withAuthorizationHeader(new Request.Builder(), getToken(req))
                .url(getGroupsResourceURL() + "/" + req.getId());
        return doDelete(deleteGroup, requestBuilder, GroupsDeleteResponse.class);
    }

    @Override
    public GroupsDeleteResponse deleteGroup(RequestConfigurator<GroupsDeleteRequest.GroupsDeleteRequestBuilder> req) throws IOException, SCIM2ApiException {
        return deleteGroup(req.configure(GroupsDeleteRequest.builder()).build());
    }

    // ------------------------------------------
    // private methods
    // ------------------------------------------

    private String getToken(SCIM2ApiRequest req) {
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
            SCIM2EndpointName name,
            Class<T> clazz,
            Supplier<Response> performRequest
    ) throws IOException, SCIM2ApiException {
        String enterpriseId = getEnterpriseIdForMetrics();
        MetricsDatastore datastore = config.getMetricsDatastore();
        try {
            Response response = performRequest.get();
            T result = parseCamelCaseJsonResponseAndRunListeners(response, clazz);
            if (enterpriseId != null) {
                datastore.incrementSuccessfulCalls(executorName, enterpriseId, name.name());
            }
            return result;
        } catch (SCIM2ApiException e) {
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
            SCIM2EndpointName name,
            String url,
            Map<String, String> query,
            String token,
            Class<T> clazz
    ) throws IOException, SCIM2ApiException {
        return handle(name, clazz, () -> {
            try {
                return slackHttpClient.get(url, query, token);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private <T> T doPost(
            SCIM2EndpointName name,
            String url,
            Object body,
            String token,
            Class<T> clazz
    ) throws IOException, SCIM2ApiException {
        return handle(name, clazz, () -> {
            try {
                return slackHttpClient.postCamelCaseJsonBodyWithBearerHeader(url, token, body);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private <T> T doPatch(
            SCIM2EndpointName name,
            String url,
            Object body,
            String token,
            Class<T> clazz
    ) throws IOException, SCIM2ApiException {
        return handle(name, clazz, () -> {
            try {
                return slackHttpClient.patchCamelCaseJsonBodyWithBearerHeader(url, token, body);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private <T> T doPut(
            SCIM2EndpointName name,
            String url,
            Object body,
            String token,
            Class<T> clazz
    ) throws IOException, SCIM2ApiException {
        return handle(name, clazz, () -> {
            try {
                return slackHttpClient.putCamelCaseJsonBodyWithBearerHeader(url, token, body);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private <T> T doDelete(
            SCIM2EndpointName name,
            Request.Builder requestBuilder,
            Class<T> clazz
    ) throws IOException, SCIM2ApiException {
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
    ) throws IOException, SCIM2ApiException {
        String body = response.body().string();
        try {
            if (response.isSuccessful()) {
                return GsonFactory.createCamelCase(slackHttpClient.getConfig()).fromJson(body, clazz);
            } else {
                throw new SCIM2ApiException(slackHttpClient.getConfig(), response, body);
            }
        } finally {
            slackHttpClient.runHttpResponseListeners(response, body);
        }
    }


}
