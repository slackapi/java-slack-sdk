package com.slack.api.model;

import com.google.gson.annotations.SerializedName;
import com.slack.api.model.block.LayoutBlock;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class Message {

    private String type;
    private String subtype;

    private String team; // team id
    private String channel;

    private String user;
    private String username;

    private String text;
    private List<LayoutBlock> blocks;
    private List<Attachment> attachments;

    private String ts;
    private String threadTs;

    @SerializedName("is_intro")
    private boolean intro;
    @SerializedName("is_starred")
    private boolean starred;
    private boolean wibblr;
    private List<String> pinnedTo;
    private List<Reaction> reactions;

    private String botId;
    private String botLink;
    private boolean displayAsBot;

    private BotProfile botProfile;

    private Icons icons;

    private File file;
    private List<File> files;
    private boolean upload;

    private String parentUserId;
    private String inviter;
    private String clientMsgId;

    private MessageItem comment;
    private String topic; // "subtype":"channel_topic"
    private String purpose; // "subtype":"channel_topic"

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

    private List<String> xFiles; // remote file ids
    private boolean hidden;

    private String lastRead;

    // this field exists only when posting the message with "reply_broadcast": true
    private MessageRoot root;

    private String itemType;
    private MessageItem item;

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
        private String user;
        private String parentUserId;
        private String username;
        private String team;
        private String botId;
        private boolean mrkdwn;
        private String type;
        private String subtype;
        private String threadTs;

        private Icons icons;
        private BotProfile botProfile;
        private Edited edited;

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
        private String lastRead;
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
        @SerializedName("image_64")
        private String image64;
        @SerializedName("image_72")
        private String image72;
    }

    @Data
    public static class MessageItem {
        private String id;
        private String name;
        private String title;
        private String created;
        private String timestamp;
        private String user;
        private String username;
        @SerializedName("is_intro")
        private boolean intro;

        @Getter(AccessLevel.NONE)
        @Setter(AccessLevel.NONE)
        @SerializedName("is_public")
        private boolean _public;
        @SerializedName("is_starred")
        private boolean starred;

        public boolean isPublic() {
            return _public;
        }

        public void setPublic(boolean isPublic) {
            this._public = isPublic;
        }

        private boolean publicUrlShared;
        private String urlPrivate;
        private boolean urlPrivateDownload;

        private String permalink;
        private boolean permalinkPublic;

        private String editLink;
        private String preview;
        private String previewHighlight;

        private Integer lines;
        private Integer linesMore;
        @SerializedName("preview_is_truncated")
        private boolean previewTruncated;
        private boolean hasRichPreview;

        private String mimetype;
        private String filetype;
        private String prettyType; // "Plain Text"
        @SerializedName("is_external")
        private boolean external;
        private String externalType;
        private boolean editable;
        private boolean displayAsBot;
        private Integer size;
        private String mode;
        private String comment;
    }

}
