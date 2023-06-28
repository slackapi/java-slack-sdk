package com.slack.api.model.admin;

import lombok.Data;

import java.util.List;

@Data
public class InviteRequest {
    private String id;
    private String email;
    private Integer dateCreated;
    private List<String> requesterIds;
    private List<String> channelIds;
    private String inviteType;
    private String realName;
    private Integer dateExpire;
    private String requestReason;
}
