package com.github.seratch.jslack.api.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class Channel {

    private String id;
    private String name;
    private String nameNormalized;
    private Integer created;
    private String creator;

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
    @SerializedName("is_channel")
    private boolean channel;
    @SerializedName("is_group")
    private boolean group;
    @SerializedName("is_im")
    private boolean im;
    @SerializedName("is_private")
    private boolean privateChannel;
    @SerializedName("is_mpim")
    private boolean mpim;
    @SerializedName("is_shared")
    private boolean shared;
    @SerializedName("is_org_shared")
    private boolean orgShared;

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
}
