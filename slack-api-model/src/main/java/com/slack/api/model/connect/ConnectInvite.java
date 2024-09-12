package com.slack.api.model.connect;

import lombok.Data;

import java.util.List;

@Data
public class ConnectInvite {
    private String id;
    private String direction; // outgoing, etc.
    private String status; // revoked, etc.
    private Integer dateCreated;
    private Integer dateInvalid;
    private Integer dateLastUpdated;
    private String inviteType; // channel, etc.
    private ConnectInviteDetail invite;
    private ConnectTeam invitingTeam;
    private ConnectUser invitingUser;
    private InvitePayload invitePayload;
    private ConnectChannel channel;
    private List<ConnectInviteAcceptance> acceptances;
    private String sig;
    private String link; // https://join.slack.com/share/...
    private String recipientUserId; // W12345, U12345, etc.
    private String recipientEmail;
}
