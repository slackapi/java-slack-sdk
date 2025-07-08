package com.slack.api.model.event;

import lombok.Data;

/**
 * The channel_unshared event is sent to all event subscriptions
 * when an external workspace has been removed from an existing shared channel.
 * <p>
 * https://docs.slack.dev/reference/events/channel_unshared
 */
@Data
public class ChannelUnsharedEvent implements Event {

    public static final String TYPE_NAME = "channel_unshared";

    private final String type = TYPE_NAME;

    // The previously_connected_team_id value is the team ID of the workspace
    // that has been removed from the channel.
    // Note that this ID may start with E, indicating that it is the ID of the organization
    // that has been removed from the channel.
    private String previouslyConnectedTeamId;

    // The channel value is the ID for the public or private channel.
    private String channel;

    // The is_ext_shared value is true if the channel is still externally shared, and false otherwise.
    private boolean isExtShared;

    private String eventTs;

}