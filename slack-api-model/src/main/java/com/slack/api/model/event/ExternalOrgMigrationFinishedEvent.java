package com.slack.api.model.event;

import lombok.Data;

/**
 * The external_org_migration_finished event is sent to all connections when an external workspace completes to migrate to an Enterprise Grid.
 * <p>
 * The team indicates the external workspace that is migrating.
 * <p>
 * The date_started indicates the time the external workspace started to migrate.
 * <p>
 * The date_finished indicates the time the external workspace finished to migrate.
 * <p>
 * https://api.slack.com/events/external_org_migration_finished
 */
@Data
public class ExternalOrgMigrationFinishedEvent implements Event {

    public static final String TYPE_NAME = "external_org_migration_finished";

    private final String type = TYPE_NAME;
    private Team team;
    private Integer dateStarted;
    private Integer dateFinished;

    @Data
    public static class Team {
        private String id;
        private boolean isMigrating;
    }
}