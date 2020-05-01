package com.slack.api.model.event;

import lombok.Data;

import java.util.List;

/**
 * The subteam_updated event is sent to all connections for a workspace when an existing User Group is updated.
 * This event is triggered for changes to the User Group information (name, description, or handle)
 * as well as the members of the group.
 * <p>
 * Clients can use this to update their local list of groups and group members.
 * The users field is truncated at 500, however the user_count field will still show the actual count.
 * <p>
 * If you are only interested in User Group membership changes, consider using the subteam_members_changed event instead.
 * <p>
 * This event type may also arise when a subteam has been disabled.
 * <p>
 * https://api.slack.com/events/subteam_updated
 */
@Data
public class SubteamUpdatedEvent implements Event {

    public static final String TYPE_NAME = "subteam_updated";

    private final String type = TYPE_NAME;
    private Subteam subteam;
    private String eventTs;

    @Data
    public static class Subteam {
        private String id;
        private String teamId;
        private String enterpriseSubteamId;
        private boolean isSubteam;
        private boolean isUsergroup;
        private String name;
        private String description;
        private String handle;
        private boolean isExternal;
        private boolean autoProvision;
        private Integer dateCreate;
        private Integer dateUpdate;
        private Integer dateDelete;
        private String autoType;
        private String createdBy;
        private String updatedBy;
        private String deletedBy;
        private Prefs prefs;
        private List<String> users;
        private String userCount;
    }

    @Data
    public static class Prefs {
        private List<String> channels;
        private List<String> groups;
    }

}