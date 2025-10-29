package com.slack.api.methods.request.entity;

import com.slack.api.methods.SlackApiRequest;
import com.slack.api.model.EntityMetadata;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * https://docs.slack.dev/reference/methods/entity.presentDetails
 */
@Data
@Builder
public class EntityPresentDetailsRequest implements SlackApiRequest {

    /**
     * Authentication token.
     */
    private String token;

    /**
     * JSON object containing the data that will be displayed in the flexpane for
     * the entity.
     */
    private String rawMetadata;
    private EntityMetadata metadata;

    /**
     * A reference to the original user action that initated the request.
     * Find this value in the event payload of the `entity_details_requested` event.
     */
    private String triggerId;

    /**
     * Set to true to indicate that the user must authenticate to view the full the
     * flexpane data.
     */
    private boolean userAuthRequired;

    /**
     * Send users to this custom URL where they will complete authentication in your
     * app if required. Value should be properly URL-encoded.
     */
    private String userAuthUrl;

    /**
     * Error indicating why the flexpane details cannot be provided.
     */
    private Error error;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Error {
        /**
         * Standardized error status
         */
        private String status;
        /**
         * Used when status is 'custom' to provide a specific message to the client
         */
        private String customMessage;
        /**
         * Format of the message
         */
        private String messageFormat;
        /**
         * Used when status is 'custom' to provide a specific title to the client
         */
        private String customTitle;
        /**
         * An action button to be shown in case of a specific error
         */
        private ActionButton[] actions;

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class ActionButton {
            private String text;
            private String actionId;
            private String value;
            private String style;
            private String url;
            private String accessibilityLabel;
            private ButtonProcessingState processingState;

            @Data
            @Builder
            @NoArgsConstructor
            @AllArgsConstructor
            public static class ButtonProcessingState {
                private Boolean enabled;
                private String interstitialText;
            }
        }
    }
}
