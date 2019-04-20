package com.github.seratch.jslack.api.model;

import com.github.seratch.jslack.api.model.block.LayoutBlock;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class Message {

    private String type;
    private String subtype;
    private String channel;
    private String user;
    private String username;
    private String text;
    private List<LayoutBlock> blocks;
    private List<Attachment> attachments;
    private String ts;
    private String threadTs;
    @SerializedName("is_starred")
    private boolean starred;
    private boolean wibblr;
    private List<String> pinnedTo;
    private List<Reaction> reactions;
    private String botId;
    private Icons icons;
    private File file;

    // field exists only if the message was edited
    private Edited edited;

    // https://api.slack.com/docs/message-link-unfurling
    private boolean unfurlLinks;
    private boolean unfurlMedia;

    @SerializedName("is_thread_broadcast")
    private boolean threadBroadcast;

    // https://api.slack.com/messaging/retrieving#threading
    // Parent messages in a thread will no longer explicitly list their replies.
    // Instead of a large replies array containing threaded message replies,
    // we'll provide a lighter-weight list of reply_users,
    // plus a reply_users_count and the latest_reply message.
    // These new fields are already available. We'll remove the replies array on October 18, 2019.
    @Deprecated
    private List<MessageRootReply> replies;
    private Integer replyCount;

    private List<String> replyUsers;
    private Integer replyUsersCount;

    private String latestReply; // ts

    private boolean subscribed;

    // this field exists only when posting the message with "reply_broadcast": true
    private MessageRoot root;

    @Data
    public static class Edited {
        private String user;
        private String ts;
    }

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

        // https://api.slack.com/messaging/retrieving#threading
        // Parent messages in a thread will no longer explicitly list their replies.
        // Instead of a large replies array containing threaded message replies,
        // we'll provide a lighter-weight list of reply_users,
        // plus a reply_users_count and the latest_reply message.
        // These new fields are already available. We'll remove the replies array on October 18, 2019.
        @Deprecated
        private List<MessageRootReply> replies;
        private Integer replyCount;
        private List<String> replyUsers;
        private Integer replyUsersCount;
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

    // https://raw.githubusercontent.com/slackapi/slack-api-specs/master/web-api/slack_web_openapi_v2.json
    // TODO: confirm the actual behavior
    @Data
    public static class Icons {
        private String emoji;

        @SerializedName("image_36")
        private String image36;
        @SerializedName("image_48")
        private String image48;
        @SerializedName("image_72")
        private String image72;
    }

}
