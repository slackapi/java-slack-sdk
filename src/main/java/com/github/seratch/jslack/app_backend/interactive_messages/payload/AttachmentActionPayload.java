package com.github.seratch.jslack.app_backend.interactive_messages.payload;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

/**
 * Interactive messages are much like other messages, only they contain buttons, a variety of menus types,
 * or they have some custom actions available.
 * Rather than remaining mostly static, interactive messages evolve over time.
 * <p>
 * see https://api.slack.com/interactive-messages
 */
@Data
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
    }

    @Data
    public static class Team {
        private String id;
        private String domain;
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
    }

    @Data
    public static class OriginalMessage {
        private String text;
        private String username;
        private String botId;
        private List<Attachment> attachments;
        private String type;
        private String subtype;
        private String ts;
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
        private List<AttachmentFiled> fields;
        private String authorName;
        private String authorIcon;
        private String imageUrl;
    }

    @Data
    public static class AttachmentFiled {
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
