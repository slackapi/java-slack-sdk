package com.slack.api.model;

import com.slack.api.model.admin.AppIcons;
import lombok.Data;

import java.util.List;

@Data
public class Call {
    private String id;
    private String appId;
    private AppIcons appIconUrls;
    private Integer dateStart;
    private Integer dateEnd;
    private List<CallParticipant> activeParticipants;
    private List<CallParticipant> allParticipants;
    private String displayId;
    private String externalUniqueId;
    private String joinUrl;
    private String title;
    private String name;
    private String createdBy;
    private List<String> channels;
    private Boolean isDmCall;
    private Boolean wasRejected;
    private Boolean wasMissed;
    private Boolean wasAccepted;
    private Boolean hasEnded;
    private String desktopAppJoinUrl;
    private String externalDisplayId;
    private List<CallParticipant> users;
}
