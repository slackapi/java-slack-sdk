package com.slack.api.model.admin;

import lombok.Data;

import java.util.List;

@Data
public class Invite {
    private String id;
    private String email;
    private String inviterId;
    private Integer dateCreated;
    private Integer dateResent;
    private Boolean isBouncing;
    private InvitePreferences invitePreferences;

    @Data
    public static class InvitePreferences {
        private Boolean isRestricted;
        private Boolean isUltraRestricted;
        private List<String> channelIds;
        private Boolean isDomainMatched;
        private Integer dateExpire;
        private String realName;
    }
}
