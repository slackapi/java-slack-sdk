package com.slack.api.methods.response.conversations;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.ResponseMetadata;
import com.slack.api.model.connect.ConnectInvite;
import lombok.Data;

import java.util.List;

@Data
public class ConversationsListConnectInvitesResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private String arg; // for missing argument error (e.g., "team_id")

    private ResponseMetadata responseMetadata;

    private List<ConnectInvite> invites;
}
