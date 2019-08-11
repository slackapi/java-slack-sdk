package com.github.seratch.jslack.api.scim;

import com.github.seratch.jslack.api.methods.RequestConfigurator;
import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.scim.request.*;
import com.github.seratch.jslack.api.scim.response.*;

import java.io.IOException;

/**
 * <a href="https://api.slack.com/scim">API Methods</a>
 */
public interface SCIMClient {

    void setEndpointUrlPrefix(String endpointUrlPrefix);

    // --------------------
    // ServiceProviderConfigs
    // --------------------

    ServiceProviderConfigsGetResponse getServiceProviderConfigs(ServiceProviderConfigsGetRequest req) throws IOException, SlackApiException;

    ServiceProviderConfigsGetResponse getServiceProviderConfigs(RequestConfigurator<ServiceProviderConfigsGetRequest.ServiceProviderConfigsGetRequestBuilder> req) throws IOException, SlackApiException;

    // --------------------
    // Users
    // --------------------

    UsersSearchResponse searchUsers(UsersSearchRequest req) throws IOException, SlackApiException;

    UsersSearchResponse searchUsers(RequestConfigurator<UsersSearchRequest.UsersSearchRequestBuilder> req) throws IOException, SlackApiException;

    // ---

    UsersReadResponse readUser(UsersReadRequest req) throws IOException, SlackApiException;

    UsersReadResponse readUser(RequestConfigurator<UsersReadRequest.UsersReadRequestBuilder> req) throws IOException, SlackApiException;

    // ---

    UsersCreateResponse createUser(UsersCreateRequest req) throws IOException, SlackApiException;

    UsersCreateResponse createUser(RequestConfigurator<UsersCreateRequest.UsersCreateRequestBuilder> req) throws IOException, SlackApiException;

    // ---

    UsersPatchResponse patchUser(UsersPatchRequest req) throws IOException, SlackApiException;

    UsersPatchResponse patchUser(RequestConfigurator<UsersPatchRequest.UsersPatchRequestBuilder> req) throws IOException, SlackApiException;

    // ---

    UsersUpdateResponse updateUser(UsersUpdateRequest req) throws IOException, SlackApiException;

    UsersUpdateResponse updateUser(RequestConfigurator<UsersUpdateRequest.UsersUpdateRequestBuilder> req) throws IOException, SlackApiException;

    // ---

    UsersDeleteResponse deleteUser(UsersDeleteRequest req) throws IOException, SlackApiException;

    UsersDeleteResponse deleteUser(RequestConfigurator<UsersDeleteRequest.UsersDeleteRequestBuilder> req) throws IOException, SlackApiException;

    // ---

    // Use deleteUser instead
    @Deprecated
    UsersDeleteResponse delete(UsersDeleteRequest req) throws IOException, SlackApiException;

    // Use deleteUser instead
    @Deprecated
    UsersDeleteResponse delete(RequestConfigurator<UsersDeleteRequest.UsersDeleteRequestBuilder> req) throws IOException, SlackApiException;

    // --------------------
    // Groups
    // --------------------

    GroupsSearchResponse searchGroups(GroupsSearchRequest req) throws IOException, SlackApiException;

    GroupsSearchResponse searchGroups(RequestConfigurator<GroupsSearchRequest.GroupsSearchRequestBuilder> req) throws IOException, SlackApiException;

    // ---

    GroupsReadResponse readGroup(GroupsReadRequest req) throws IOException, SlackApiException;

    GroupsReadResponse readGroup(RequestConfigurator<GroupsReadRequest.GroupsReadRequestBuilder> req) throws IOException, SlackApiException;

    // ---

    GroupsCreateResponse createGroup(GroupsCreateRequest req) throws IOException, SlackApiException;

    GroupsCreateResponse createGroup(RequestConfigurator<GroupsCreateRequest.GroupsCreateRequestBuilder> req) throws IOException, SlackApiException;

    // ---

    GroupsPatchResponse patchGroup(GroupsPatchRequest req) throws IOException, SlackApiException;

    GroupsPatchResponse patchGroup(RequestConfigurator<GroupsPatchRequest.GroupsPatchRequestBuilder> req) throws IOException, SlackApiException;

    // ---

    GroupsUpdateResponse updateGroup(GroupsUpdateRequest req) throws IOException, SlackApiException;

    GroupsUpdateResponse updateGroup(RequestConfigurator<GroupsUpdateRequest.GroupsUpdateRequestBuilder> req) throws IOException, SlackApiException;

    // ---

    GroupsDeleteResponse deleteGroup(GroupsDeleteRequest req) throws IOException, SlackApiException;

    GroupsDeleteResponse deleteGroup(RequestConfigurator<GroupsDeleteRequest.GroupsDeleteRequestBuilder> req) throws IOException, SlackApiException;

}
