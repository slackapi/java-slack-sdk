package com.github.seratch.jslack.api.model.event;

import lombok.Data;

import java.util.List;

/**
 * The team_profile_change event is sent to all connections for a workspace
 * when Workspace Admin updates the field definitions in the profile.
 * Only the modified field definitions are included in the payload.
 * Where appropriate, clients should update to reflect new changes immediately.
 * <p>
 * https://api.slack.com/events/team_profile_change
 */
@Data
public class TeamProfileChangeEvent implements Event {

    public static final String TYPE_NAME = "team_profile_change";

    private final String type = TYPE_NAME;
    private Profile profile;

    @Data
    public static class Profile {
        private List<Field> fields;
    }

    @Data
    public static class Field {
        private String id;
        // TODO: other attributes
    }

}