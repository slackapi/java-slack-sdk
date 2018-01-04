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
    @SerializedName("is_group")
    private boolean group;
    private Integer created;
    private String creator;
    @SerializedName("is_archived")
    private boolean archived;
    @SerializedName("is_mpim")
    private boolean mpim;
    private List<String> members;
    private Topic topic;
    private Purpose purpose;
    private String lastRead;
    private Latest latest;
    private Integer unreadCount;
    private Integer unreadCountDisplay;
}