package com.github.seratch.jslack.api.model;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Represents a <a href="https://api.slack.com/types/conversation">conversation</a>
 * as used with the {@code conversations} API
 *
 * @see "https://api.slack.com/docs/conversations-api"
 */
@Data
@Builder
public class Conversation {

    private String enterpriseId;
    private String id;
    private String name;
    private String created;
    private String creator;
    private Integer unlinked;
    @SerializedName("name_normalized")
    private String nameNormalized;
    @SerializedName("pending_shared")
    private List<String> pendingShared;
    @SerializedName("pending_connected_team_ids")
    private List<String> pendingConnectedTeamIds;
    @SerializedName("last_read")
    private String lastRead;
    private Topic topic;
    private Purpose purpose;
    @SerializedName("previous_names")
    private List<String> previousNames;
    @SerializedName("num_members")
    private Integer numOfMembers;
    private List<String> members;
    private Latest latest;
    private String locale;
    @SerializedName("unread_count")
    private Integer unreadCount;
    @SerializedName("unread_count_display")
    private Integer unreadCountDisplay;
    private String user; // conversations.open
    private Double priority;

    @SerializedName("shared_team_ids")
    private List<String> sharedTeamIds;
    @SerializedName("internal_team_ids")
    private List<String> internalTeamIds;
    @SerializedName("connected_team_ids")
    private List<String> connectedTeamIds;

    private String parentConversation;
    private List<String> pendingConnectedTeamIds;

    // shared channels
    private String conversationHostId;
    private List<String> internalTeamIds;
    private List<String> connectedTeamIds;

    @SerializedName("is_channel")
    private boolean isChannel;
    @SerializedName("is_group")
    private boolean isGroup;
    @SerializedName("is_im")
    private boolean isIm;
    @SerializedName("is_archived")
    private boolean isArchived;
    @SerializedName("is_general")
    private boolean isGeneral;
    @SerializedName("is_read_only")
    private boolean isReadOnly;
    @SerializedName("is_thread_only")
    private boolean isThreadOnly;
    @SerializedName("is_non_threadable")
    private boolean isNonThreadable;
    @SerializedName("is_shared")
    private boolean isShared;
    @SerializedName("is_ext_shared")
    private boolean isExtShared;
    @SerializedName("is_org_shared")
    private boolean isOrgShared;
    @SerializedName("is_pending_ext_shared")
    private boolean isPendingExtShared;
    @SerializedName("is_global_shared")
    private boolean globalShared;
    @SerializedName("is_org_default")
    private boolean orgDefault;
    @SerializedName("is_org_mandatory")
    private boolean orgMandatory;
    @SerializedName("is_moved")
    private Integer isMoved;
    @SerializedName("is_member")
    private boolean isMember;
    @SerializedName("is_open")
    private boolean open;
    @SerializedName("is_private")
    private boolean isPrivate;
    @SerializedName("is_mpim")
    private boolean isMpim;
}
