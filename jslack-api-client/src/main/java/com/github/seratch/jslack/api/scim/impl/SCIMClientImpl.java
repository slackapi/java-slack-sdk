package com.github.seratch.jslack.api.scim.impl;

import com.github.seratch.jslack.api.RequestConfigurator;
import com.github.seratch.jslack.api.scim.SCIMApiException;
import com.github.seratch.jslack.api.scim.SCIMApiRequest;
import com.github.seratch.jslack.api.scim.SCIMClient;
import com.github.seratch.jslack.api.scim.request.*;
import com.github.seratch.jslack.api.scim.response.*;
import com.github.seratch.jslack.common.http.SlackHttpClient;
import com.github.seratch.jslack.common.json.GsonFactory;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SCIMClientImpl implements SCIMClient {

    private String endpointUrlPrefix = ENDPOINT_URL_PREFIX;

    private final SlackHttpClient slackHttpClient;
    private final String token;

    public SCIMClientImpl(SlackHttpClient slackHttpClient) {
        this(slackHttpClient, null);
    }

    public SCIMClientImpl(SlackHttpClient slackHttpClient, String token) {
        this.slackHttpClient = slackHttpClient;
        this.token = token;
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
        return doGet(endpointUrlPrefix + "ServiceProviderConfigs", null, getToken(req), ServiceProviderConfigsGetResponse.class);
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
        return doGet(getUsersResourceURL(), query, getToken(req), UsersSearchResponse.class);
    }

    public UsersSearchResponse searchUsers(RequestConfigurator<UsersSearchRequest.UsersSearchRequestBuilder> req) throws IOException, SCIMApiException {
        return searchUsers(req.configure(UsersSearchRequest.builder()).build());
    }

    @Override
    public UsersReadResponse readUser(UsersReadRequest req) throws IOException, SCIMApiException {
        return doGet(getUsersResourceURL() + "/" + req.getId(), null, getToken(req), UsersReadResponse.class);
    }

    public UsersReadResponse readUser(RequestConfigurator<UsersReadRequest.UsersReadRequestBuilder> req) throws IOException, SCIMApiException {
        return readUser(req.configure(UsersReadRequest.builder()).build());
    }

    @Override
    public UsersCreateResponse createUser(UsersCreateRequest req) throws IOException, SCIMApiException {
        return doPost(getUsersResourceURL(), req.getUser(), getToken(req), UsersCreateResponse.class);
    }

    @Override
    public UsersCreateResponse createUser(RequestConfigurator<UsersCreateRequest.UsersCreateRequestBuilder> req) throws IOException, SCIMApiException {
        return createUser(req.configure(UsersCreateRequest.builder()).build());
    }

    @Override
    public UsersPatchResponse patchUser(UsersPatchRequest req) throws IOException, SCIMApiException {
        return doPatch(getUsersResourceURL() + "/" + req.getId(), req.getUser(), getToken(req), UsersPatchResponse.class);
    }

    @Override
    public UsersPatchResponse patchUser(RequestConfigurator<UsersPatchRequest.UsersPatchRequestBuilder> req) throws IOException, SCIMApiException {
        return patchUser(req.configure(UsersPatchRequest.builder()).build());
    }

    @Override
    public UsersUpdateResponse updateUser(UsersUpdateRequest req) throws IOException, SCIMApiException {
        return doPut(getUsersResourceURL() + "/" + req.getId(), req.getUser(), getToken(req), UsersUpdateResponse.class);
    }

    @Override
    public UsersUpdateResponse updateUser(RequestConfigurator<UsersUpdateRequest.UsersUpdateRequestBuilder> req) throws IOException, SCIMApiException {
        return updateUser(req.configure(UsersUpdateRequest.builder()).build());
    }

    @Override
    public UsersDeleteResponse deleteUser(UsersDeleteRequest req) throws IOException, SCIMApiException {
        Request.Builder requestBuilder = withAuthorizationHeader(new Request.Builder(), getToken(req))
                .url(getUsersResourceURL() + "/" + req.getId());
        return doDelete(requestBuilder, UsersDeleteResponse.class);
    }

    @Override
    public UsersDeleteResponse deleteUser(RequestConfigurator<UsersDeleteRequest.UsersDeleteRequestBuilder> req) throws IOException, SCIMApiException {
        return deleteUser(req.configure(UsersDeleteRequest.builder()).build());
    }

    @Deprecated
    @Override
    public UsersDeleteResponse delete(UsersDeleteRequest req) throws IOException, SCIMApiException {
        return deleteUser(req);
    }

    @Deprecated
    @Override
    public UsersDeleteResponse delete(RequestConfigurator<UsersDeleteRequest.UsersDeleteRequestBuilder> req) throws IOException, SCIMApiException {
        return deleteUser(req);
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
        return doGet(getGroupsResourceURL(), query, getToken(req), GroupsSearchResponse.class);
    }

    @Override
    public GroupsSearchResponse searchGroups(RequestConfigurator<GroupsSearchRequest.GroupsSearchRequestBuilder> req) throws IOException, SCIMApiException {
        return searchGroups(req.configure(GroupsSearchRequest.builder()).build());
    }

    @Override
    public GroupsReadResponse readGroup(GroupsReadRequest req) throws IOException, SCIMApiException {
        return doGet(getGroupsResourceURL() + "/" + req.getId(), null, getToken(req), GroupsReadResponse.class);
    }

    @Override
    public GroupsReadResponse readGroup(RequestConfigurator<GroupsReadRequest.GroupsReadRequestBuilder> req) throws IOException, SCIMApiException {
        return readGroup(req.configure(GroupsReadRequest.builder()).build());
    }

    @Override
    public GroupsCreateResponse createGroup(GroupsCreateRequest req) throws IOException, SCIMApiException {
        return doPost(getGroupsResourceURL(), req.getGroup(), getToken(req), GroupsCreateResponse.class);
    }

    @Override
    public GroupsCreateResponse createGroup(RequestConfigurator<GroupsCreateRequest.GroupsCreateRequestBuilder> req) throws IOException, SCIMApiException {
        return createGroup(req.configure(GroupsCreateRequest.builder()).build());
    }

    @Override
    public GroupsPatchResponse patchGroup(GroupsPatchRequest req) throws IOException, SCIMApiException {
        return doPatch(getGroupsResourceURL() + "/" + req.getId(), req.getGroup(), getToken(req), GroupsPatchResponse.class);
    }

    @Override
    public GroupsPatchResponse patchGroup(RequestConfigurator<GroupsPatchRequest.GroupsPatchRequestBuilder> req) throws IOException, SCIMApiException {
        return patchGroup(req.configure(GroupsPatchRequest.builder()).build());
    }

    @Override
    public GroupsUpdateResponse updateGroup(GroupsUpdateRequest req) throws IOException, SCIMApiException {
        return doPut(getGroupsResourceURL() + "/" + req.getId(), req.getGroup(), getToken(req), GroupsUpdateResponse.class);
    }

    @Override
    public GroupsUpdateResponse updateGroup(RequestConfigurator<GroupsUpdateRequest.GroupsUpdateRequestBuilder> req) throws IOException, SCIMApiException {
        return updateGroup(req.configure(GroupsUpdateRequest.builder()).build());
    }

    @Override
    public GroupsDeleteResponse deleteGroup(GroupsDeleteRequest req) throws IOException, SCIMApiException {
        Request.Builder requestBuilder = withAuthorizationHeader(new Request.Builder(), getToken(req))
                .url(getGroupsResourceURL() + "/" + req.getId());
        return doDelete(requestBuilder, GroupsDeleteResponse.class);
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

    private <T> T doGet(String url, Map<String, String> query, String token, Class<T> clazz) throws IOException, SCIMApiException {
        Response response = slackHttpClient.get(url, query, token);
        return parseCamelCaseJsonResponseAndRunListeners(response, clazz);
    }

    private <T> T doPost(String url, Object body, String token, Class<T> clazz) throws IOException, SCIMApiException {
        Response response = slackHttpClient.postCamelCaseJsonBodyWithBearerHeader(url, token, body);
        return parseCamelCaseJsonResponseAndRunListeners(response, clazz);
    }

    private <T> T doPatch(String url, Object body, String token, Class<T> clazz) throws IOException, SCIMApiException {
        Response response = slackHttpClient.patchCamelCaseJsonBodyWithBearerHeader(url, token, body);
        return parseCamelCaseJsonResponseAndRunListeners(response, clazz);
    }

    private <T> T doPut(String url, Object body, String token, Class<T> clazz) throws IOException, SCIMApiException {
        Response response = slackHttpClient.putCamelCaseJsonBodyWithBearerHeader(url, token, body);
        return parseCamelCaseJsonResponseAndRunListeners(response, clazz);
    }

    private <T> T doDelete(Request.Builder requestBuilder, Class<T> clazz) throws IOException, SCIMApiException {
        Response response = slackHttpClient.delete(requestBuilder);
        return parseCamelCaseJsonResponseAndRunListeners(response, clazz);
    }

    private <T> T parseCamelCaseJsonResponseAndRunListeners(Response response, Class<T> clazz) throws IOException, SCIMApiException {
        String body = response.body().string();
        slackHttpClient.runHttpResponseListeners(response, body);
        if (response.isSuccessful()) {
            return GsonFactory.createCamelCase(slackHttpClient.getConfig()).fromJson(body, clazz);
        } else {
            throw new SCIMApiException(slackHttpClient.getConfig(), response, body);
        }
    }


}
