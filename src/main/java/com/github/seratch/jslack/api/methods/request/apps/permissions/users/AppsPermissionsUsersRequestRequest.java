package com.github.seratch.jslack.api.methods.request.apps.permissions.users;

import com.github.seratch.jslack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AppsPermissionsUsersRequestRequest implements SlackApiRequest {

    private String token;

    /**
     * A comma separated list of user scopes to request for
     */
    private List<String> scopes;

    /**
     * Token used to trigger the request
     */
    private String triggerId;

    /**
     * The user this scope is being requested for
     */
    private String user;

}