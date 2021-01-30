package com.slack.api.scim.impl;

import com.slack.api.RequestConfigurator;
import com.slack.api.SlackConfig;
import com.slack.api.methods.impl.MethodsClientImpl;
import com.slack.api.scim.AsyncSCIMClient;
import com.slack.api.scim.SCIMApiRequest;
import com.slack.api.scim.request.*;
import com.slack.api.scim.response.*;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static com.slack.api.scim.SCIMEndpointName.*;

@Slf4j
public class AsyncSCIMClientImpl implements AsyncSCIMClient {

    private final String token;
    private final SCIMClientImpl underlying;
    private final AsyncRateLimitExecutor executor;

    public AsyncSCIMClientImpl(
            String token,
            SCIMClientImpl scim,
            MethodsClientImpl methods,
            SlackConfig config
    ) {
        this.token = token;
        this.underlying = scim;
        this.executor = AsyncRateLimitExecutor.getOrCreate(methods, config);
    }

    private String token(SCIMApiRequest req) {
        if (req.getToken() != null) {
            return req.getToken();
        } else {
            return this.token;
        }
    }

    private Map<String, String> toMap(SCIMApiRequest req) {
        Map<String, String> params = new HashMap<>();
        params.put("token", token(req));
        return params;
    }

    // ----------------------------------------------------------------------------------
    // public methods
    // ----------------------------------------------------------------------------------

    @Override
    public String getEndpointUrlPrefix() {
        return this.underlying.getEndpointUrlPrefix();
    }

    @Override
    public void setEndpointUrlPrefix(String endpointUrlPrefix) {
        this.underlying.setEndpointUrlPrefix(endpointUrlPrefix);
    }

    @Override
    public CompletableFuture<ServiceProviderConfigsGetResponse> getServiceProviderConfigs(ServiceProviderConfigsGetRequest req) {
        return executor.execute(
                getServiceProviderConfigs,
                toMap(req),
                () -> this.underlying.getServiceProviderConfigs(req)
        );
    }

    @Override
    public CompletableFuture<ServiceProviderConfigsGetResponse> getServiceProviderConfigs(RequestConfigurator<ServiceProviderConfigsGetRequest.ServiceProviderConfigsGetRequestBuilder> req) {
        return getServiceProviderConfigs(req.configure(ServiceProviderConfigsGetRequest.builder()).build());
    }

    @Override
    public CompletableFuture<UsersSearchResponse> searchUsers(UsersSearchRequest req) {
        return executor.execute(
                searchUsers,
                toMap(req),
                () -> this.underlying.searchUsers(req)
        );
    }

    @Override
    public CompletableFuture<UsersSearchResponse> searchUsers(RequestConfigurator<UsersSearchRequest.UsersSearchRequestBuilder> req) {
        return searchUsers(req.configure(UsersSearchRequest.builder()).build());
    }

    @Override
    public CompletableFuture<UsersReadResponse> readUser(UsersReadRequest req) {
        return executor.execute(
                readUser,
                toMap(req),
                () -> this.underlying.readUser(req)
        );
    }

    @Override
    public CompletableFuture<UsersReadResponse> readUser(RequestConfigurator<UsersReadRequest.UsersReadRequestBuilder> req) {
        return readUser(req.configure(UsersReadRequest.builder()).build());
    }

    @Override
    public CompletableFuture<UsersCreateResponse> createUser(UsersCreateRequest req) {
        return executor.execute(
                createUser,
                toMap(req),
                () -> this.underlying.createUser(req)
        );
    }

    @Override
    public CompletableFuture<UsersCreateResponse> createUser(RequestConfigurator<UsersCreateRequest.UsersCreateRequestBuilder> req) {
        return createUser(req.configure(UsersCreateRequest.builder()).build());
    }

    @Override
    public CompletableFuture<UsersPatchResponse> patchUser(UsersPatchRequest req) {
        return executor.execute(
                patchUser,
                toMap(req),
                () -> this.underlying.patchUser(req)
        );
    }

    @Override
    public CompletableFuture<UsersPatchResponse> patchUser(RequestConfigurator<UsersPatchRequest.UsersPatchRequestBuilder> req) {
        return patchUser(req.configure(UsersPatchRequest.builder()).build());
    }

    @Override
    public CompletableFuture<UsersUpdateResponse> updateUser(UsersUpdateRequest req) {
        return executor.execute(
                updateUser,
                toMap(req),
                () -> this.underlying.updateUser(req)
        );
    }

    @Override
    public CompletableFuture<UsersUpdateResponse> updateUser(RequestConfigurator<UsersUpdateRequest.UsersUpdateRequestBuilder> req) {
        return updateUser(req.configure(UsersUpdateRequest.builder()).build());
    }

    @Override
    public CompletableFuture<UsersDeleteResponse> deleteUser(UsersDeleteRequest req) {
        return executor.execute(
                deleteUser,
                toMap(req),
                () -> this.underlying.deleteUser(req)
        );
    }

    @Override
    public CompletableFuture<UsersDeleteResponse> deleteUser(RequestConfigurator<UsersDeleteRequest.UsersDeleteRequestBuilder> req) {
        return deleteUser(req.configure(UsersDeleteRequest.builder()).build());
    }

    @Override
    public CompletableFuture<GroupsSearchResponse> searchGroups(GroupsSearchRequest req) {
        return executor.execute(
                searchGroups,
                toMap(req),
                () -> this.underlying.searchGroups(req)
        );
    }

    @Override
    public CompletableFuture<GroupsSearchResponse> searchGroups(RequestConfigurator<GroupsSearchRequest.GroupsSearchRequestBuilder> req) {
        return searchGroups(req.configure(GroupsSearchRequest.builder()).build());
    }

    @Override
    public CompletableFuture<GroupsReadResponse> readGroup(GroupsReadRequest req) {
        return executor.execute(
                readGroup,
                toMap(req),
                () -> this.underlying.readGroup(req)
        );
    }

    @Override
    public CompletableFuture<GroupsReadResponse> readGroup(RequestConfigurator<GroupsReadRequest.GroupsReadRequestBuilder> req) {
        return readGroup(req.configure(GroupsReadRequest.builder()).build());
    }

    @Override
    public CompletableFuture<GroupsCreateResponse> createGroup(GroupsCreateRequest req) {
        return executor.execute(
                createGroup,
                toMap(req),
                () -> this.underlying.createGroup(req)
        );
    }

    @Override
    public CompletableFuture<GroupsCreateResponse> createGroup(RequestConfigurator<GroupsCreateRequest.GroupsCreateRequestBuilder> req) {
        return createGroup(req.configure(GroupsCreateRequest.builder()).build());
    }

    @Override
    public CompletableFuture<GroupsPatchResponse> patchGroup(GroupsPatchRequest req) {
        return executor.execute(
                patchGroup,
                toMap(req),
                () -> this.underlying.patchGroup(req)
        );
    }

    @Override
    public CompletableFuture<GroupsPatchResponse> patchGroup(RequestConfigurator<GroupsPatchRequest.GroupsPatchRequestBuilder> req) {
        return patchGroup(req.configure(GroupsPatchRequest.builder()).build());
    }

    @Override
    public CompletableFuture<GroupsUpdateResponse> updateGroup(GroupsUpdateRequest req) {
        return executor.execute(
                updateGroup,
                toMap(req),
                () -> this.underlying.updateGroup(req)
        );
    }

    @Override
    public CompletableFuture<GroupsUpdateResponse> updateGroup(RequestConfigurator<GroupsUpdateRequest.GroupsUpdateRequestBuilder> req) {
        return updateGroup(req.configure(GroupsUpdateRequest.builder()).build());
    }

    @Override
    public CompletableFuture<GroupsDeleteResponse> deleteGroup(GroupsDeleteRequest req) {
        return executor.execute(
                deleteGroup,
                toMap(req),
                () -> this.underlying.deleteGroup(req)
        );
    }

    @Override
    public CompletableFuture<GroupsDeleteResponse> deleteGroup(RequestConfigurator<GroupsDeleteRequest.GroupsDeleteRequestBuilder> req) {
        return deleteGroup(req.configure(GroupsDeleteRequest.builder()).build());
    }

}
