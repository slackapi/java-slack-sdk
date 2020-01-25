package com.github.seratch.jslack.api.scim;

import com.slack.api.RequestConfigurator;
import com.github.seratch.jslack.api.scim.request.*;
import com.github.seratch.jslack.api.scim.response.*;

import java.io.IOException;

/**
 * <a href="https://api.slack.com/scim">API Methods</a>
 */
public interface SCIMClient {

    String ENDPOINT_URL_PREFIX = "https://api.slack.com/scim/v1/";

    String getEndpointUrlPrefix();

    void setEndpointUrlPrefix(String endpointUrlPrefix);

    // --------------------
    // ServiceProviderConfigs
    // --------------------

    ServiceProviderConfigsGetResponse getServiceProviderConfigs(ServiceProviderConfigsGetRequest req) throws IOException, SCIMApiException;

    ServiceProviderConfigsGetResponse getServiceProviderConfigs(RequestConfigurator<ServiceProviderConfigsGetRequest.ServiceProviderConfigsGetRequestBuilder> req) throws IOException, SCIMApiException;

    // --------------------
    // Users
    // --------------------

    UsersSearchResponse searchUsers(UsersSearchRequest req) throws IOException, SCIMApiException;

    UsersSearchResponse searchUsers(RequestConfigurator<UsersSearchRequest.UsersSearchRequestBuilder> req) throws IOException, SCIMApiException;

    // ---

    UsersReadResponse readUser(UsersReadRequest req) throws IOException, SCIMApiException;

    UsersReadResponse readUser(RequestConfigurator<UsersReadRequest.UsersReadRequestBuilder> req) throws IOException, SCIMApiException;

    // ---

    UsersCreateResponse createUser(UsersCreateRequest req) throws IOException, SCIMApiException;

    UsersCreateResponse createUser(RequestConfigurator<UsersCreateRequest.UsersCreateRequestBuilder> req) throws IOException, SCIMApiException;

    // ---

    UsersPatchResponse patchUser(UsersPatchRequest req) throws IOException, SCIMApiException;

    UsersPatchResponse patchUser(RequestConfigurator<UsersPatchRequest.UsersPatchRequestBuilder> req) throws IOException, SCIMApiException;

    // ---

    UsersUpdateResponse updateUser(UsersUpdateRequest req) throws IOException, SCIMApiException;

    UsersUpdateResponse updateUser(RequestConfigurator<UsersUpdateRequest.UsersUpdateRequestBuilder> req) throws IOException, SCIMApiException;

    // ---

    UsersDeleteResponse deleteUser(UsersDeleteRequest req) throws IOException, SCIMApiException;

    UsersDeleteResponse deleteUser(RequestConfigurator<UsersDeleteRequest.UsersDeleteRequestBuilder> req) throws IOException, SCIMApiException;

    // ---

    // Use deleteUser instead
    @Deprecated
    UsersDeleteResponse delete(UsersDeleteRequest req) throws IOException, SCIMApiException;

    // Use deleteUser instead
    @Deprecated
    UsersDeleteResponse delete(RequestConfigurator<UsersDeleteRequest.UsersDeleteRequestBuilder> req) throws IOException, SCIMApiException;

    // --------------------
    // Groups
    // --------------------

    GroupsSearchResponse searchGroups(GroupsSearchRequest req) throws IOException, SCIMApiException;

    GroupsSearchResponse searchGroups(RequestConfigurator<GroupsSearchRequest.GroupsSearchRequestBuilder> req) throws IOException, SCIMApiException;

    // ---

    GroupsReadResponse readGroup(GroupsReadRequest req) throws IOException, SCIMApiException;

    GroupsReadResponse readGroup(RequestConfigurator<GroupsReadRequest.GroupsReadRequestBuilder> req) throws IOException, SCIMApiException;

    // ---

    GroupsCreateResponse createGroup(GroupsCreateRequest req) throws IOException, SCIMApiException;

    GroupsCreateResponse createGroup(RequestConfigurator<GroupsCreateRequest.GroupsCreateRequestBuilder> req) throws IOException, SCIMApiException;

    // ---

    GroupsPatchResponse patchGroup(GroupsPatchRequest req) throws IOException, SCIMApiException;

    GroupsPatchResponse patchGroup(RequestConfigurator<GroupsPatchRequest.GroupsPatchRequestBuilder> req) throws IOException, SCIMApiException;

    // ---

    GroupsUpdateResponse updateGroup(GroupsUpdateRequest req) throws IOException, SCIMApiException;

    GroupsUpdateResponse updateGroup(RequestConfigurator<GroupsUpdateRequest.GroupsUpdateRequestBuilder> req) throws IOException, SCIMApiException;

    // ---

    GroupsDeleteResponse deleteGroup(GroupsDeleteRequest req) throws IOException, SCIMApiException;

    GroupsDeleteResponse deleteGroup(RequestConfigurator<GroupsDeleteRequest.GroupsDeleteRequestBuilder> req) throws IOException, SCIMApiException;

}
