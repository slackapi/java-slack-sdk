package com.slack.api.scim;

import com.slack.api.RequestConfigurator;
import com.slack.api.scim.request.*;
import com.slack.api.scim.response.*;

import java.util.concurrent.CompletableFuture;

/**
 * Slack SCIM API client.
 * <p>
 * Provision and manage user accounts and groups with the Slack SCIM API.
 * SCIM is used by Single Sign-On (SSO) services and identity providers to manage people
 * across a variety of tools, including Slack.
 * <p>
 * It's also possible to write your own apps
 * and scripts using the SCIM API to programmatically manage the members of your workspace.
 *
 * @see <a href="https://api.slack.com/scim">Slack SCIM API</a>
 */
public interface AsyncSCIMClient {

    String ENDPOINT_URL_PREFIX = "https://api.slack.com/scim/v1/";

    String getEndpointUrlPrefix();

    void setEndpointUrlPrefix(String endpointUrlPrefix);

    // --------------------
    // ServiceProviderConfigs
    // --------------------

    CompletableFuture<ServiceProviderConfigsGetResponse> getServiceProviderConfigs(ServiceProviderConfigsGetRequest req);

    CompletableFuture<ServiceProviderConfigsGetResponse> getServiceProviderConfigs(RequestConfigurator<ServiceProviderConfigsGetRequest.ServiceProviderConfigsGetRequestBuilder> req);

    // --------------------
    // Users
    // --------------------

    CompletableFuture<UsersSearchResponse> searchUsers(UsersSearchRequest req);

    CompletableFuture<UsersSearchResponse> searchUsers(RequestConfigurator<UsersSearchRequest.UsersSearchRequestBuilder> req);

    // ---

    CompletableFuture<UsersReadResponse> readUser(UsersReadRequest req);

    CompletableFuture<UsersReadResponse> readUser(RequestConfigurator<UsersReadRequest.UsersReadRequestBuilder> req);

    // ---

    CompletableFuture<UsersCreateResponse> createUser(UsersCreateRequest req);

    CompletableFuture<UsersCreateResponse> createUser(RequestConfigurator<UsersCreateRequest.UsersCreateRequestBuilder> req);

    // ---

    CompletableFuture<UsersPatchResponse> patchUser(UsersPatchRequest req);

    CompletableFuture<UsersPatchResponse> patchUser(RequestConfigurator<UsersPatchRequest.UsersPatchRequestBuilder> req);

    // ---

    CompletableFuture<UsersUpdateResponse> updateUser(UsersUpdateRequest req);

    CompletableFuture<UsersUpdateResponse> updateUser(RequestConfigurator<UsersUpdateRequest.UsersUpdateRequestBuilder> req);

    // ---

    CompletableFuture<UsersDeleteResponse> deleteUser(UsersDeleteRequest req);

    CompletableFuture<UsersDeleteResponse> deleteUser(RequestConfigurator<UsersDeleteRequest.UsersDeleteRequestBuilder> req);

    // --------------------
    // Groups
    // --------------------

    CompletableFuture<GroupsSearchResponse> searchGroups(GroupsSearchRequest req);

    CompletableFuture<GroupsSearchResponse> searchGroups(RequestConfigurator<GroupsSearchRequest.GroupsSearchRequestBuilder> req);

    // ---

    CompletableFuture<GroupsReadResponse> readGroup(GroupsReadRequest req);

    CompletableFuture<GroupsReadResponse> readGroup(RequestConfigurator<GroupsReadRequest.GroupsReadRequestBuilder> req);

    // ---

    CompletableFuture<GroupsCreateResponse> createGroup(GroupsCreateRequest req);

    CompletableFuture<GroupsCreateResponse> createGroup(RequestConfigurator<GroupsCreateRequest.GroupsCreateRequestBuilder> req);

    // ---

    CompletableFuture<GroupsPatchResponse> patchGroup(GroupsPatchRequest req);

    CompletableFuture<GroupsPatchResponse> patchGroup(RequestConfigurator<GroupsPatchRequest.GroupsPatchRequestBuilder> req);

    // ---

    CompletableFuture<GroupsUpdateResponse> updateGroup(GroupsUpdateRequest req);

    CompletableFuture<GroupsUpdateResponse> updateGroup(RequestConfigurator<GroupsUpdateRequest.GroupsUpdateRequestBuilder> req);

    // ---

    CompletableFuture<GroupsDeleteResponse> deleteGroup(GroupsDeleteRequest req);

    CompletableFuture<GroupsDeleteResponse> deleteGroup(RequestConfigurator<GroupsDeleteRequest.GroupsDeleteRequestBuilder> req);

}
