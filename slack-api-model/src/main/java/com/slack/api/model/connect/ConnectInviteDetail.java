package com.slack.api.model.connect;

import lombok.Data;

@Data
public class ConnectInviteDetail {
    private String id; // I12345
    private Integer dateCreated;
    private Integer dateInvalid;
    private ConnectTeam invitingTeam;
    private ConnectUser invitingUser;
    private String link; // https://join.slack.com/share/...
    private String recipientUserId; // W12345, U12345, etc.
    private String recipientEmail;
}
