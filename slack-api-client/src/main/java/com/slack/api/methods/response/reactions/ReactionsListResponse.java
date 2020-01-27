package com.slack.api.methods.response.reactions;

import com.google.gson.annotations.SerializedName;
import com.slack.api.methods.SlackApiResponse;
import com.slack.api.model.Attachment;
import com.slack.api.model.BotProfile;
import com.slack.api.model.Paging;
import com.slack.api.model.Reaction;
import com.slack.api.model.block.LayoutBlock;
import lombok.Data;

import java.util.List;

@Data
public class ReactionsListResponse implements SlackApiResponse {

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

        @Data
        public static class Message {
            private String type;
            private String subtype;
            private String text;
            private List<Attachment> attachments;
            private List<LayoutBlock> blocks;
            private String ts;
            private String team;
            private String user;
            private String username;
            private String botId;
            private String permalink;
            private List<Reaction> reactions;
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

            /**
             * A reply message information in a MessageRoot.
             */
            @Data
            public static class MessageRootReply {
                private String user;
                private String ts;
            }

            private boolean subscribed;
            private String lastRead;
            private String clientMsgId;
            private String inviter;
            private String userTeam;
            private String sourceTeam;
            private Icons icons;
            private UserProfile userProfile;
            private BotProfile botProfile;

            @Data
            public static class Icons {
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
            public static class UserProfile {
                private String avatarHash;
                @SerializedName("image_72")
                private String image72;
                private String firstName;
                private String realName;
                private String displayName;
                private String team;
                private String name;
                /**
                 * is_restricted indicates the user is a multi-channel guest.
                 * see also: https://get.slack.help/hc/en-us/articles/201314026-roles-and-permissions-in-slack
                 */
                @SerializedName("is_restricted")
                private boolean restricted;
                /**
                 * is_ultra_restricted indicates they are a single channel guest.
                 * see also: https://get.slack.help/hc/en-us/articles/201314026-roles-and-permissions-in-slack
                 */
                @SerializedName("is_ultra_restricted")
                private boolean ultraRestricted;
            }
        }
    }
}