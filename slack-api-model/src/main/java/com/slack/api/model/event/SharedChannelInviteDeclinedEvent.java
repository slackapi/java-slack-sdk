package com.slack.api.model.event;

import com.slack.api.model.connect.ConnectChannel;
import com.slack.api.model.connect.ConnectInviteDetail;
import com.slack.api.model.connect.ConnectTeam;
import com.slack.api.model.connect.ConnectUser;
import lombok.Data;

import java.util.List;

/**
 * https://docs.slack.dev/reference/events/shared_channel_invite_declined
 */
@Data
public class SharedChannelInviteDeclinedEvent implements Event {

    public static final String TYPE_NAME = "shared_channel_invite_declined";

    private final String type = TYPE_NAME;
    private ConnectInviteDetail invite;
    private ConnectChannel channel;
    private String decliningTeamId;
    private List<ConnectTeam> teamsInChannel;
    private ConnectUser decliningUser;
    private String eventTs;

}