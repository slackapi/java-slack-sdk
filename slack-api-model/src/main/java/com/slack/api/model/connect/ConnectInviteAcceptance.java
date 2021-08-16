package com.slack.api.model.connect;

import lombok.Data;

import java.util.List;

@Data
public class ConnectInviteAcceptance {
    private String approvalStatus;
    private Integer dateAccepted;
    private Integer dateInvalid;
    private Integer dateLastUpdated;
    private ConnectTeam acceptingTeam;
    private ConnectUser acceptingUser;
    private List<ConnectInviteReview> reviews;
}
