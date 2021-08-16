package com.slack.api.model.event;

import com.slack.api.model.User;
import com.slack.api.model.connect.ConnectChannel;
import com.slack.api.model.connect.ConnectInvite;
import com.slack.api.model.connect.ConnectInviteDetail;
import lombok.Data;

/**
 * https://api.slack.com/events/shared_channel_invite_received
 */
@Data
public class SharedChannelInviteReceivedEvent implements Event {

    public static final String TYPE_NAME = "shared_channel_invite_received";

    private final String type = TYPE_NAME;
    private ConnectInviteDetail invite;
    private ConnectChannel channel;
    private String eventTs;

}