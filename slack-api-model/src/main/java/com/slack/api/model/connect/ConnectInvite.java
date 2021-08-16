package com.slack.api.model.connect;

import lombok.Data;

import java.util.List;

@Data
public class ConnectInvite {
    private String direction; // outgoing, etc.
    private String status; // revoked, etc.
    private Integer dateLastUpdated;
    private String inviteType; // channel, etc.
    private ConnectInviteDetail invite;
    private ConnectChannel channel;
    private List<ConnectInviteAcceptance> acceptances;
}
