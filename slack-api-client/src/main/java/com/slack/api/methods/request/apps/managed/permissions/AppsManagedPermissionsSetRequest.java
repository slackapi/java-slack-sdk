package com.slack.api.methods.request.apps.managed.permissions;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/apps.managed.permissions.set
 */
@Data
@Builder
public class AppsManagedPermissionsSetRequest implements SlackApiRequest {

    /**
     * A user token or bot token issued to the manager app. Either way, this method can only set permissions for apps that were created by that manager app.
     */
    private String token;

    /**
     * Encoded ID of the managed app to configure
     */
    private String appId;

    /**
     * Who can interact with the app. Use everyone to allow all members, or app_owner to restrict access to the app's owner.
     */
    private String permissions;

}
