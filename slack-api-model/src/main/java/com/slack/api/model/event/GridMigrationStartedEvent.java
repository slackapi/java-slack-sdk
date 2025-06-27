package com.slack.api.model.event;

import lombok.Data;

/**
 * This Events API-only event is sent via subscription whenever your app is installed by completes migration to Enterprise Grid.
 * <p>
 * The example above details the complete Events API payload, including the event wrapper.
 * The team_id indicates which workspace is migrating.
 * <p>
 * By also subscribing to the grid_migration_started event, you'll receive notice when the migration began.
 * <p>
 * It's best to cease API and platform operations for a workspace during migration.
 * While duration varies workspace-to-workspace, expect an extended period of unavailability.
 * <p>
 * https://docs.slack.dev/reference/events/grid_migration_started
 */
@Data
public class GridMigrationStartedEvent implements Event {

    public static final String TYPE_NAME = "grid_migration_started";

    private final String type = TYPE_NAME;
    private String enterpriseId;

}