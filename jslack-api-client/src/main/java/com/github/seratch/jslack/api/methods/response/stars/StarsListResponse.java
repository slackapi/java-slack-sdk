package com.github.seratch.jslack.api.methods.response.stars;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import com.github.seratch.jslack.api.model.*;
import com.github.seratch.jslack.api.model.block.LayoutBlock;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class StarsListResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private List<Item> items;
    private Paging paging;

    @Data
    public static class Item {
        private String type;
        private String channel;
        private Message message;
        private Integer dateCreate;
        private File file;
        private Comment comment;
    }

    @Data
    public static class Message {

        private String type;
        private String subtype;
        private String text;
        private String ts;
        private String botId;
        private String team;

        private List<Attachment> attachments;
        private List<LayoutBlock> blocks;

        private String permalink;
        @SerializedName("is_starred")
        private boolean starred;

        private String clientMsgId;
        private String user;
        private String username;
        private String threadTs;
        private Integer replyCount;
        private Integer replyUsersCount;
        private String latestReply;
        private List<String> replyUsers;

        // https://api.slack.com/messaging/retrieving#threading
        // Parent messages in a thread will no longer explicitly list their replies.
        // Instead of a large replies array containing threaded message replies,
        // we'll provide a lighter-weight list of reply_users,
        // plus a reply_users_count and the latest_reply message.
        // These new fields are already available. We'll remove the replies array on October 18, 2019.
        @Deprecated
        private List<MessageRootReply> replies;
        private boolean subscribed;
        private String lastRead;
        private List<Reaction> reactions;

        /**
         * A reply message information in a MessageRoot.
         */
        @Data
        public static class MessageRootReply {
            private String user;
            private String ts;
        }
    }

    @Data
    public static class Comment {
        private String id;
        private Integer created;
        private Integer timestamp;
        private String user;
        @SerializedName("is_intro")
        private boolean intro;
        private String comment;
        private Integer numStars;
        @SerializedName("is_starred")
        private boolean starred;
    }
}
