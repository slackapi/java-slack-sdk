package com.slack.api.model.event;

import com.slack.api.model.connect.ConnectChannel;
import com.slack.api.model.connect.ConnectInviteDetail;
import com.slack.api.model.connect.ConnectTeam;
import com.slack.api.model.connect.ConnectUser;
import lombok.Data;

import java.util.List;

/**
 * https://api.slack.com/events/shared_channel_invite_accepted
 */
@Data
public class SharedChannelInviteAcceptedEvent implements Event {

    public static final String TYPE_NAME = "shared_channel_invite_accepted";

    private final String type = TYPE_NAME;
    private boolean approvalRequired;
    private ConnectInviteDetail invite;
    private ConnectChannel channel;
    private List<ConnectTeam> teamsInChannel;
    private ConnectUser acceptingUser;
    private String eventTs;

}