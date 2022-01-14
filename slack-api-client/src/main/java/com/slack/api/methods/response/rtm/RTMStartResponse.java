package com.slack.api.methods.response.rtm;

import com.google.gson.annotations.SerializedName;
import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.*;
import com.slack.api.model.block.LayoutBlock;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @see <a href="https://api.slack.com/methods/rtm.start">rtm.start</a>
 * @deprecated Use `rtm.connect` API method instead
 */
@Data
@Deprecated
public class RTMStartResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private String url;
    private User self;
    private Team team;
    private List<User> users;
    private Prefs prefs;
    private List<Channel> channels;
    private List<Group> groups;
    private List<Im> ims;

    @Data
    public static class Prefs {
        // TODO
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Group {

        private String id;
        private String name;
        private String nameNormalized;
        @SerializedName("is_group")
        private boolean group;
        private Integer created;
        private String creator;
        @SerializedName("is_archived")
        private boolean archived;
        @SerializedName("is_mpim")
        private boolean mpim;
        @SerializedName("is_open")
        private boolean open;
        @SerializedName("is_read_only")
        private boolean readOnly;
        @SerializedName("is_thread_only")
        private boolean threadOnly;

        private List<String> members;
        private String parentGroup; // group id
        private Topic topic;
        private Purpose purpose;
        private String lastRead;
        private Latest latest;
        private Integer unreadCount;
        private Integer unreadCountDisplay;
        private Double priority;
    }

    @Data
    public static class Latest {

        private String clientMsgId;

        private String type;
        private String subtype;
        private String team;
        private String user;
        private String username;
        private String parentUserId;
        private String text;
        private String topic; // groups
        private List<Attachment> attachments;
        private List<LayoutBlock> blocks;
        private List<File> files;
        // NOTE: This is different form the standard "latest" object
        private List<String> reactions;
        private Message.MessageRoot root;
        private boolean upload;
        private boolean displayAsBot;
        private String botId;
        private String botLink;
        private BotProfile botProfile;
        private String threadTs;
        private String ts;
        private Message.Icons icons;
        private List<String> xFiles;
        private com.slack.api.model.Latest.Edited edited;

        @Data
        public static class Edited {
            private String user;
            private String ts;
        }
    }
}
