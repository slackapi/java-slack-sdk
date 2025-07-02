package com.slack.api.model.event;

import lombok.Data;

/**
 * The team_migration_started event is sent when a Slack workspace is about to be migrated between servers.
 * The websocket connection will close immediately after it is sent.
 * <p>
 * Occasionally we need to move Slack workspaces to a new server.
 * To avoid any data synchronization bugs or race conditions we disconnect all clients
 * from a workspace before starting this process. By the time a client has reconnected the process is usually complete,
 * so the impact is minimal.
 * <p>
 * When clients receive this event, immediately start a reconnection process by calling rtm.start again.
 * You may receive occasional migration_in_progress errors when re-calling rtm.start.
 * If this happens you should wait a few seconds and try again.
 * If the error continues you should wait longer before retrying, and so on.
 * <p>
 * https://docs.slack.dev/reference/events/team_migration_started
 */
@Data
public class TeamMigrationStartedEvent implements Event {

    public static final String TYPE_NAME = "team_migration_started";

    private final String type = TYPE_NAME;

}