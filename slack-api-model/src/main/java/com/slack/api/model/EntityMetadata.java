package com.slack.api.model;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;
import com.slack.api.model.block.composition.PlainTextObject;
import com.slack.api.model.block.composition.TextObject;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntityMetadata {
    private String entityType;
    private ExternalRef externalRef;
    private String url;
    private EntityPayload entityPayload;
    // app_unfurl_url is only required when passing metadata to `chat.unfurl`
    private String appUnfurlUrl;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExternalRef {
        private String id;
        private String type;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EntityPayload {
        private Attributes attributes;

        private FileFields fileFields;
        private TaskFields taskFields;
        private IncidentFields incidentFields;
        private ContentItemFields contentItemFields;
        private JsonElement fields;

        private CustomField[] customFields;
        private FileEntitySlackFile slackFile;
        private String[] displayOrder;
        private Actions actions;

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class FileFields {
            private Image preview;
            private TypedField createdBy;
            private Timestamp dateCreated;
            private Timestamp dateUpdated;
            private TypedField lastModifiedBy;
            private StringField fileSize;
            private StringField mimeType;
        }

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class TaskFields {
            private StringField description;
            private TypedField createdBy;
            private Timestamp dateCreated;
            private Timestamp dateUpdated;
            private TypedField assignee;
            private StringField status;
            private TypedField dueDate;
            private StringField priority;
        }

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class ContentItemFields {
            private Image preview;
            private StringField description;
            private TypedField createdBy;
            private Timestamp dateCreated;
            private Timestamp dateUpdated;
            private TypedField lastModifiedBy;
        }

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class IncidentFields {
            private StringField status;
            private StringField priority;
            private StringField urgency;
            private TypedField createdBy;
            private TypedField assignedTo;
            private Timestamp dateCreated;
            private Timestamp dateUpdated;
            private StringField description;
            private StringField service;
        }

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class TypedField {
            private Object value;
            private String type;
            private String label;
            private Edit edit;

            // When type is 'string'
            private String link;
            private Icon icon;
            @SerializedName("long")
            private Boolean longStr;
            private String format;
            private String tagColor;

            // When type is 'slack#/types/image'
            private String altText;
            private String imageUrl;
            private SlackFile slackFile;

            // When type is 'slack#/types/user'
            private User user;
        }

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class User {
            // Slack user ID
            private String userId;

            // or
            private String text;
            private String url;
            private String email;
            private Icon icon;
        }

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class StringField {
            private String label;
            private String value;
            private String format;
            private String link;
            private Icon icon;
            @SerializedName("long")
            private Boolean longStr;
            private String type;
            private String tagColor;
            private Edit edit;
        }

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Timestamp {
            private String label;
            private Integer value;
            private String link;
            private Icon icon;
            private String type;
            private Edit edit;
        }

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Attributes {
            private Title title;
            private String displayType;
            private String displayId;
            private Icon productIcon;
            private String productName;
            private String locale;
            private FullSizePreview fullSizePreview;

            @Data
            @Builder
            @NoArgsConstructor
            @AllArgsConstructor
            public static class Title {
                private String text;
                private Edit edit;
            }
        }

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class CustomField {
            private String label;
            private String key;
            private Object value;
            private String type;
            private String link;
            private Icon icon;
            @SerializedName("long")
            private Boolean longStr;
            private String format;
            private String imageUrl;
            private SlackFile slackFile;
            private String altText;
            private String tagColor;
            private Edit edit;
            private String itemType;
            private User user;
        }

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Icon {
            private String altText;
            private String url;
            private SlackFile slackFile;
        }

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class FullSizePreview {
            private Boolean isSupported;
            private String previewUrl;
            private String mime_type;
            private Error error;

            @Data
            @Builder
            @NoArgsConstructor
            @AllArgsConstructor
            public static class Error {
                private String code;
                private String message;
            }
        }

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Image {
            private String label;
            private String altText;
            private String imageUrl;
            private SlackFile slackFile;
            private String title;
            private String type;
        }

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class FileEntitySlackFile {
            private String id;
            private String type;
        }

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class SlackFile {
            private String url;
            private String id;
        }

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Edit {
            private Boolean enabled;
            private PlainTextObject placeholder;
            private PlainTextObject hint;
            private Boolean optional;
            private Select select;
            private Number number;
            private Text text;

            @Data
            @Builder
            @NoArgsConstructor
            @AllArgsConstructor
            public static class Select {
                private String currentValue;
                private String[] currentValues;
                private TextObject[] staticOptions;
                private Boolean fetchOptionsDynamically;
                private Integer minQueryLength;
            }

            @Data
            @Builder
            @NoArgsConstructor
            @AllArgsConstructor
            public static class Number {
                private Boolean isDecimalAllowed;
                private Integer minValue;
                private Integer maxValue;
            }

            @Data
            @Builder
            @NoArgsConstructor
            @AllArgsConstructor
            public static class Text {
                private Integer minLength;
                private Integer maxLength;
            }
        }

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Actions {
            private ActionButton[] primaryActions;
            private ActionButton[] overflowActions;
        }

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
