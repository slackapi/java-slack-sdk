package com.slack.api.model.event;

import lombok.Data;

/**
 * The external_org_migration_started event is sent to all connections when an external workspace begins to migrate to an Enterprise Grid.
 * <p>
 * The team indicates the external workspace that is migrating.
 * <p>
 * The date_started indicates the time the external workspace started to migrate.
 * <p>
 * https://api.slack.com/events/external_org_migration_started
 */
@Data
public class ExternalOrgMigrationStartedEvent implements Event {

    public static final String TYPE_NAME = "external_org_migration_started";

    private final String type = TYPE_NAME;
    private Team team;
    private Integer dateStarted;

    @Data
    public static class Team {
        private String id;
        private boolean isMigrating;
    }
}