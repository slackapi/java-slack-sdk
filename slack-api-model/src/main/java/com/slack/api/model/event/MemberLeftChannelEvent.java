package com.slack.api.model.event;

import lombok.Data;

/**
 * The member_left_channel event is sent to all websocket connections and event subscriptions
 * when users leave public or private channels.
 * <p>
 * The provided user value is a user ID belonging to the user that joined the channel.
 * <p>
 * The channel value is the ID for a public channel or private channel (AKA group).
 * <p>
 * The channel_type value is a single letter indicating the type of channel used in channel:
 * <p>
 * C - typically a public channel
 * G - private channels (or groups) return this channel_type
 * The team identifies which workspace the user is from.
 * <p>
 * This event is supported as a bot user subscription in the Events API.
 * Workspace event subscriptions are also available for tokens holding
 * at least one of the channels:read or groups:read scopes.
 * Which events your app will receive depends on the scopes and their context.
 * For instance, you'll only receive member_left_channel events for private channels if your app has the groups:read permission.
 * <p>
 * https://api.slack.com/events/member_left_channel
 */
@Data
public class MemberLeftChannelEvent implements Event {

    public static final String TYPE_NAME = "member_left_channel";

    private final String type = TYPE_NAME;
    private String user;
    private String channel;
    private String channelType;
    private String team;

}