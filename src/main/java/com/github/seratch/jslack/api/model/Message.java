package com.github.seratch.jslack.api.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class Message {

    private String type;
    private String channel;
    private String user;
    private String text;
    private List<Attachment> attachments;
    private String ts;
    private String threadTs;
    @SerializedName("is_starred")
    private boolean starred;
    private boolean wibblr;
    private List<String> pinnedTo;
    private List<Reaction> reactions;
    private String username;
    private String subtype;
    private String botId;
    private Icon icons;
    private File file;

    // https://api.slack.com/docs/message-link-unfurling
    private boolean unfurlLinks;
    private boolean unfurlMedia;

    @SerializedName("is_thread_broadcast")
    private boolean threadBroadcast;

    // this field exists only when posting the message with "reply_broadcast": true
    private MessageRoot root;

    /**
     * The root message information of a "thread_broadcast" message.
     */
    @Data
    public static class MessageRoot {
        private String text;
        private String username;
        private String botId;
        private boolean mrkdwn;
        private String type;
        private String subtype;
        private String threadTs;
        private Integer replyCount;
        private List<MessageRootReply> replies;
        private boolean subscribed;
        private Integer unreadCount;
        private String ts;
    }

    /**
     * A reply message information in a MessageRoot.
     */
    @Data
    public static class MessageRootReply {
        private String user;
        private String ts;
    }

}
