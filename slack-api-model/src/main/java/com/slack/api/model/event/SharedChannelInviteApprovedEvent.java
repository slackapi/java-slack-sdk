package com.slack.api.model.event;

import com.slack.api.model.connect.ConnectChannel;
import com.slack.api.model.connect.ConnectInviteDetail;
import com.slack.api.model.connect.ConnectTeam;
import com.slack.api.model.connect.ConnectUser;
import lombok.Data;

import java.util.List;

/**
 * https://docs.slack.dev/reference/events/shared_channel_invite_approved
 */
@Data
public class SharedChannelInviteApprovedEvent implements Event {

    public static final String TYPE_NAME = "shared_channel_invite_approved";

    private final String type = TYPE_NAME;
    private ConnectInviteDetail invite;
    private ConnectChannel channel;
    private String approvingTeamId;
    private List<ConnectTeam> teamsInChannel;
    private ConnectUser approvingUser;
    private String eventTs;

}