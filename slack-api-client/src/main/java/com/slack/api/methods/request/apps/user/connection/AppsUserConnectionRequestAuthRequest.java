package com.slack.api.methods.request.apps.user.connection;

import com.slack.api.methods.SlackApiRequest;
import com.slack.api.model.block.composition.PlainTextObject;
import com.slack.api.model.block.element.OverflowMenuElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * https://docs.slack.dev/reference/methods/apps.user.connection.requestAuth
 */
@Data
@Builder
public class AppsUserConnectionRequestAuthRequest implements SlackApiRequest {

    private String token;

    /**
     * Opens a new authorization modal. Required unless `user_id` is provided.
     */
    private String triggerId;

    /**
     * Updates the authorization modal already open for this user. Ignored when `trigger_id` is provided.
     * Slack records `is_connected` for the user even when no modal is open, and reports that in `response_metadata.warnings`.
     */
    private String userId;

    /**
     * The provider the user is asked to connect. Required.
     */
    private PrimaryProvider primaryProvider;
    private String primaryProviderAsString;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PrimaryProvider {

        /**
         * Unique identifier for this provider within the app, up to 150 characters.
         * The connect button's `action_id` is derived from it (`auth_provider_connect_{value}`). Required.
         */
        private String value;

        /**
         * Human-readable name of the provider, up to 75 characters. Required.
         */
        private PlainTextObject name;

        /**
         * Whether this provider is currently connected. Required: Slack records this state against the user,
         * so an omitted value is rejected with `invalid_arguments` instead of defaulting to false.
         */
        private Boolean isConnected;

        /**
         * Brief description of what this connection enables, up to 150 characters.
         */
        private PlainTextObject description;

        /**
         * URL for the provider icon image. Must be https. Defaults to the app's avatar.
         */
        private String iconUrl;

        /**
         * OAuth redirect URL opened when the user clicks Connect.
         * Must be https, on a host (or a subdomain of one) registered in the app manifest's `features.user_connections.auth_hosts`.
         */
        private String authUrl;

        /**
         * URL opened when the user clicks Disconnect.
         * Must be https, on a host (or a subdomain of one) registered in the app manifest's `features.user_connections.auth_hosts`.
         */
        private String disconnectUrl;

        /**
         * Display name of the connected account, up to 75 characters. Shown when `is_connected` is true.
         */
        private PlainTextObject connectedIdentity;

        /**
         * Additional context about the connection (e.g., org or workspace name), up to 75 characters.
         */
        private PlainTextObject connectedContext;

        /**
         * Overflow menu shown when connected (e.g., Switch accounts, Disconnect). Requires 2 to 5 `options`.
         */
        private OverflowMenuElement accessory;
    }
}
