package com.slack.api.model.event;

import lombok.Data;

import java.util.List;

/**
 * User requested an invite
 * <p>
 * https://api.slack.com/events/invite_requested
 */
@Data
public class InviteRequestedEvent implements Event {

    public static final String TYPE_NAME = "invite_requested";

    private final String type = TYPE_NAME;
    private InviteRequest inviteRequest;

    @Data
    public static class InviteRequest {
        private String id;
        private String email;
        private Integer dateCreated;
        private List<String> requesterIds;
        private List<String> channelIds;
        private String inviteType;
        private String realName;
        private Integer dateExpire;
        private String requestReason;
        private Team team;
    }

    @Data
    public static class Team {
        private String id;
        private String name;
        private String domain;
    }
}