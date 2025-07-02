package com.slack.api.model.event;

import lombok.Data;

/**
 * The channel_shared event is sent to all event subscriptions
 * when a new shared channel is created or a channel is converted into a shared channel.
 * It's also triggered when an external workspace is added to an existing shared channel.
 * <p>
 * https://docs.slack.dev/reference/events/channel_shared
 */
@Data
public class ChannelSharedEvent implements Event {

    public static final String TYPE_NAME = "channel_shared";

    private final String type = TYPE_NAME;

    // The connected_team_id value is the team ID of the workspace that has joined the channel.
    // Note that this ID may start with E, indicating that it is the ID of the organization
    // that has been removed from the channel.
    private String connectedTeamId;

    // The channel value is the ID for the public or private channel.
    private String channel;

    private String eventTs;

}