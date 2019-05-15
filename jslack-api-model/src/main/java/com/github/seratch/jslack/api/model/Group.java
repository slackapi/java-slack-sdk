package com.github.seratch.jslack.api.model;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * https://api.slack.com/types/group
 */
@Data
@Builder
public class Group {

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