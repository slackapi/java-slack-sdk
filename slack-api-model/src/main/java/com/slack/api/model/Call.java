package com.slack.api.model;

import lombok.Data;

import java.util.List;

@Data
public class Call {
    private String id;
    private Integer dateStart;
    private String externalUniqueId;
    private String joinUrl;
    private Integer dateEnd;
    private List<String> channels;
    private String externalDisplayId;
    private String title;
    private String desktopAppJoinUrl;
    private List<CallParticipant> users;
}
