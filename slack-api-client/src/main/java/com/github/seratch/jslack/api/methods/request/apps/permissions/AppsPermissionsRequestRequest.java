package com.github.seratch.jslack.api.methods.request.apps.permissions;

import com.github.seratch.jslack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AppsPermissionsRequestRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `none`
     */
    private String token;

    /**
     * Token used to trigger the permissions API
     */
    private String triggerId;

    /**
     * A comma separated list of scopes to request for
     */
    private List<String> scopes;

}