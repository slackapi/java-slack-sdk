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
     * Opens a new authorization modal. Required unless user_id is provided.
     */
    private String triggerId;

    /**
     * Updates the authorization modal already open for this user. Ignored when trigger_id is provided.
     */
    private String userId;

    /**
     * The provider the user is asked to connect.
     */
    private PrimaryProvider primaryProvider;
    private String primaryProviderAsString;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PrimaryProvider {
        private String value;
        private PlainTextObject name;
        private Boolean isConnected;
        private PlainTextObject description;
        private String iconUrl;
        private String authUrl;
        private String disconnectUrl;
        private PlainTextObject connectedIdentity;
        private PlainTextObject connectedContext;
        private OverflowMenuElement accessory;
    }
}
