package com.slack.api.model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents a <a href="https://api.slack.com/types/conversation">conversation</a>
 * as used with the {@code conversations} API
 *
 * @see <a href="https://api.slack.com/docs/conversations-api">Conversations API</a>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Conversation {

    private String enterpriseId;
    private String id;
    private String name;
    private Integer created;
    private String creator;
    private Integer unlinked;
    @SerializedName("name_normalized")
    private String nameNormalized;
    @SerializedName("pending_shared")
    private List<String> pendingShared;
    @SerializedName("last_read")
    private String lastRead;
    private Topic topic;
    private Purpose purpose;
    @SerializedName("previous_names")
    private List<String> previousNames;
    @SerializedName("num_members")
    private Integer numOfMembers;
    private Latest latest;
    private String locale;
    @SerializedName("unread_count")
    private Integer unreadCount;
    @SerializedName("unread_count_display")
    private Integer unreadCountDisplay;
    private String user; // conversations.open
    private Boolean isUserDeleted; // users.conversations
    private Double priority;

    private Integer dateConnected;

    private List<String> sharedTeamIds;

    private String parentConversation;
    private List<String> pendingConnectedTeamIds;

    // shared channels
    private String conversationHostId;
    private List<String> internalTeamIds;
    private List<String> connectedTeamIds;
    private List<String> connectedLimitedTeamIds;

    private String contextTeamId;

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
    @SerializedName("is_starred")
    private boolean isStarred;
}
