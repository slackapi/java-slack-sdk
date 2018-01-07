package com.github.seratch.jslack.api.model;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Represents a <a href="https://api.slack.com/types/conversation">conversation</a>
 * as used with the {@code conversations} API<p>
 *
 * @see https://api.slack.com/docs/conversations-api
 */
@Data
@Builder
public class Conversation {

    private String id;
    private String name;
    private String created;
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
    private List<String> members;
    private Latest latest;
    private String locale;
    @SerializedName("unreadCount")
    private Integer unreadCount;
    @SerializedName("unreadCountDisplay")
    private Integer unreadCountDisplay;

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
    @SerializedName("is_shared")
    private boolean isShared;
    @SerializedName("is_ext_shared")
    private boolean isExtShared;
    @SerializedName("is_org_shared")
    private boolean isOrgShared;
    @SerializedName("is_pending_ext_shared")
    private boolean isPendingExtShared;
    @SerializedName("is_member")
    private boolean isMember;
    @SerializedName("is_private")
    private boolean isPrivate;
    @SerializedName("is_mpim")
    private boolean isMpim;
}
