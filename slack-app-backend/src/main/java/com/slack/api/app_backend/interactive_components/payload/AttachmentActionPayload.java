package com.slack.api.app_backend.interactive_components.payload;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Interactive messages are much like other messages, only they contain buttons, a variety of menus types,
 * or they have some custom actions available.
 * Rather than remaining mostly static, interactive messages evolve over time.
 * <p>
 *
 * @see <a href="https://api.slack.com/interactive-messages">Interactive messages</a>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttachmentActionPayload {

    public static final String TYPE = "interactive_message";

    private final String type = TYPE;
    private List<Action> actions;
    private String callbackId;
    private Team team;
    private Channel channel;
    private User user;
    private String actionTs;
    private String messageTs;
    private String attachmentId;
    private String token;
    @SerializedName("is_app_unfurl")
    private boolean appUnfurl;
    private OriginalMessage originalMessage;
    private String responseUrl;
    private String triggerId;

    @Data
    public static class Action {
        private String name;
        private String type;
        private String value;
        private List<SelectedOption> selectedOptions;

        @Data
        public static class SelectedOption {
            private String value;
        }
    }

    @Data
    public static class Team {
        private String id;
        private String domain;
        private String enterpriseId; // https://github.com/slackapi/bolt/blob/dae21827c1c11720e5d6c8f23abcddb2d983b1f2/src/types/actions/interactive-message.ts#L39-L40
        private String enterpriseName;
    }

    @Data
    public static class Channel {
        private String id;
        private String name;
    }

    @Data
    public static class User {
        private String id;
        private String name;
        private String teamId; // https://github.com/slackapi/bolt/blob/dae21827c1c11720e5d6c8f23abcddb2d983b1f2/src/types/actions/interactive-message.ts#L45
    }

    @Data
    public static class OriginalMessage {
        private String botId;
        private String type; // "message"
        private String text;
        private String user;
        private String username;
        private String ts;
        private List<Attachment> attachments;
        private String subtype;
        private String threadTs;
        private String parentUserId;
    }

    @Data
    public static class Attachment {
        private Integer id;
        private String callbackId;
        private String title;
        private String text;
        private String fallback;
        private String color;
        private String attachmentType;
        private List<AttachmentAction> actions;
        private List<AttachmentField> fields;
        private String authorName;
        private String authorIcon;
        private String imageUrl;
    }

    @Data
    public static class AttachmentField {
        private String title;
        private String value;
        @SerializedName("short")
        private boolean shortField;
    }

    @Data
    public static class AttachmentAction {
        private String name;
        private String text;
        private String type;
        private String value;
    }

}
