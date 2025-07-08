package com.slack.api.methods.request.apps;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * This method uninstalls an app. Unlike auth.revoke, which revokes a single token,
 * this method revokes all tokens associated with a single installation of an app.
 * <p>
 * https://docs.slack.dev/reference/methods/apps.uninstall
 */
@Data
@Builder
public class AppsUninstallRequest implements SlackApiRequest {

    private String token;

    /**
     * Issued when you created your application.
     */
    private String clientId;

    /**
     * Issued when you created your application.
     */
    private String clientSecret;

}