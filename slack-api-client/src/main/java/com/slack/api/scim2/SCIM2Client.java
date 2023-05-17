package com.slack.api.scim2;

import com.slack.api.RequestConfigurator;
import com.slack.api.scim2.request.*;
import com.slack.api.scim2.response.*;

import java.io.IOException;

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
 * @see <a href="https://api.slack.com/admins/scim2">Slack SCIM 2.0 API</a>
 */
public interface SCIM2Client {

    String ENDPOINT_URL_PREFIX = "https://api.slack.com/scim/v2/";

    String getEndpointUrlPrefix();

    void setEndpointUrlPrefix(String endpointUrlPrefix);

    // --------------------
    // ServiceProviderConfigs
    // --------------------

    ServiceProviderConfigsGetResponse getServiceProviderConfigs(ServiceProviderConfigsGetRequest req) throws IOException, SCIM2ApiException;

    ServiceProviderConfigsGetResponse getServiceProviderConfigs(RequestConfigurator<ServiceProviderConfigsGetRequest.ServiceProviderConfigsGetRequestBuilder> req) throws IOException, SCIM2ApiException;

    // --------------------
    // ResourceTypes
    // --------------------

    ResourceTypesGetResponse getResourceTypes(ResourceTypesGetRequest req) throws IOException, SCIM2ApiException;

    ResourceTypesGetResponse getResourceTypes(RequestConfigurator<ResourceTypesGetRequest.ResourceTypesGetRequestBuilder> req) throws IOException, SCIM2ApiException;

    // --------------------
    // Users
    // --------------------

    UsersSearchResponse searchUsers(UsersSearchRequest req) throws IOException, SCIM2ApiException;

    UsersSearchResponse searchUsers(RequestConfigurator<UsersSearchRequest.UsersSearchRequestBuilder> req) throws IOException, SCIM2ApiException;

    // ---

    UsersReadResponse readUser(UsersReadRequest req) throws IOException, SCIM2ApiException;

    UsersReadResponse readUser(RequestConfigurator<UsersReadRequest.UsersReadRequestBuilder> req) throws IOException, SCIM2ApiException;

    // ---

    UsersCreateResponse createUser(UsersCreateRequest req) throws IOException, SCIM2ApiException;

    UsersCreateResponse createUser(RequestConfigurator<UsersCreateRequest.UsersCreateRequestBuilder> req) throws IOException, SCIM2ApiException;

    // ---

    UsersPatchResponse patchUser(UsersPatchRequest req) throws IOException, SCIM2ApiException;

    UsersPatchResponse patchUser(RequestConfigurator<UsersPatchRequest.UsersPatchRequestBuilder> req) throws IOException, SCIM2ApiException;

    // ---

    UsersUpdateResponse updateUser(UsersUpdateRequest req) throws IOException, SCIM2ApiException;

    UsersUpdateResponse updateUser(RequestConfigurator<UsersUpdateRequest.UsersUpdateRequestBuilder> req) throws IOException, SCIM2ApiException;

    // ---

    UsersDeleteResponse deleteUser(UsersDeleteRequest req) throws IOException, SCIM2ApiException;

    UsersDeleteResponse deleteUser(RequestConfigurator<UsersDeleteRequest.UsersDeleteRequestBuilder> req) throws IOException, SCIM2ApiException;

    // --------------------
    // Groups
    // --------------------

    GroupsSearchResponse searchGroups(GroupsSearchRequest req) throws IOException, SCIM2ApiException;

    GroupsSearchResponse searchGroups(RequestConfigurator<GroupsSearchRequest.GroupsSearchRequestBuilder> req) throws IOException, SCIM2ApiException;

    // ---

    GroupsReadResponse readGroup(GroupsReadRequest req) throws IOException, SCIM2ApiException;

    GroupsReadResponse readGroup(RequestConfigurator<GroupsReadRequest.GroupsReadRequestBuilder> req) throws IOException, SCIM2ApiException;

    // ---

    GroupsCreateResponse createGroup(GroupsCreateRequest req) throws IOException, SCIM2ApiException;

    GroupsCreateResponse createGroup(RequestConfigurator<GroupsCreateRequest.GroupsCreateRequestBuilder> req) throws IOException, SCIM2ApiException;

    // ---

    GroupsPatchResponse patchGroup(GroupsPatchRequest req) throws IOException, SCIM2ApiException;

    GroupsPatchResponse patchGroup(RequestConfigurator<GroupsPatchRequest.GroupsPatchRequestBuilder> req) throws IOException, SCIM2ApiException;

    // ---

    GroupsUpdateResponse updateGroup(GroupsUpdateRequest req) throws IOException, SCIM2ApiException;

    GroupsUpdateResponse updateGroup(RequestConfigurator<GroupsUpdateRequest.GroupsUpdateRequestBuilder> req) throws IOException, SCIM2ApiException;

    // ---

    GroupsDeleteResponse deleteGroup(GroupsDeleteRequest req) throws IOException, SCIM2ApiException;

    GroupsDeleteResponse deleteGroup(RequestConfigurator<GroupsDeleteRequest.GroupsDeleteRequestBuilder> req) throws IOException, SCIM2ApiException;

}
