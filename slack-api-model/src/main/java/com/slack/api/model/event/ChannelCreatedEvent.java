package com.slack.api.model.event;

import com.google.gson.annotations.SerializedName;
import com.slack.api.model.Latest;
import com.slack.api.model.Purpose;
import com.slack.api.model.Topic;
import lombok.Data;

import java.util.List;

/**
 * The channel_created event is sent to all connections for a workspace when a new channel is created.
 * Clients can use this to update their local cache of non-joined channels.
 * <p>
 * https://api.slack.com/events/channel_created
 */
@Data
public class ChannelCreatedEvent implements Event {

    public static final String TYPE_NAME = "channel_created";

    private final String type = TYPE_NAME;
    private Channel channel;
    private String eventTs;

    @Data
    public static class Channel {
        private String id;
        private String name;
        private String nameNormalized;
        private boolean isChannel;
        private boolean isShared;
        private boolean isOrgShared;
        private Integer created;
        private String creator;
        private String contextTeamId;
        private String enterpriseId;

        @SerializedName("is_read_only")
        private boolean readOnly;
        @SerializedName("is_thread_only")
        private boolean threadOnly;
        @SerializedName("is_archived")
        private boolean archived;
        @SerializedName("is_member")
        private boolean member;
        @SerializedName("is_general")
        private boolean general;
        @SerializedName("is_group")
        private boolean group;
        @SerializedName("is_im")
        private boolean im;
        @SerializedName("is_private")
        private boolean privateChannel;
        @SerializedName("is_mpim")
        private boolean mpim;
        @SerializedName("is_file")
        private boolean file;
        @SerializedName("is_global_shared")
        private boolean globalShared;
        @SerializedName("is_org_default")
        private boolean orgDefault;
        @SerializedName("is_org_mandatory")
        private boolean orgMandatory;
        @SerializedName("is_moved")
        private Integer isMoved;

        @SerializedName("is_ext_shared") // search result
        private boolean extShared;
        @SerializedName("is_pending_ext_shared") // search result
        private boolean pendingExtShared;
        private List<String> pendingShared; // search result

        private String lastRead;
        private Latest latest;
        private Integer unreadCount;
        private Integer unreadCountDisplay;
        private Integer unlinked;
        private List<String> members;
        private Topic topic;
        private Purpose purpose;
        private String user;
        private List<String> previousNames;
        private Integer numMembers;
        private Double priority;
        private Integer updated;
        private List<String> sharedTeamIds;
        private List<String> internalTeamIds;
        private List<String> pendingConnectedTeamIds;
    }
}