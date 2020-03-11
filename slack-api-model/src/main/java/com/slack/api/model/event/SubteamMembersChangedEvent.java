package com.slack.api.model.event;

import lombok.Data;

import java.util.List;

/**
 * The subteam_members_changed event is sent to all connections for a workspace
 * when users are added or removed from an existing User Group.
 * Clients can use the timestamp information to detect if they are out of sync with the user list.
 * <p>
 * Unlike subteam_updated, this only shows the delta of added or removed members and does not include a snapshot of the User Group.
 * <p>
 * https://api.slack.com/events/subteam_members_changed
 */
@Data
public class SubteamMembersChangedEvent implements Event {

    public static final String TYPE_NAME = "subteam_members_changed";

    private final String type = TYPE_NAME;
    private String subteamId;
    private String teamId;
    private Integer datePreviousUpdate;
    private Integer dateUpdate;
    private List<String> addedUsers;
    private Integer addedUsersCount;
    private List<String> removedUsers;
    private Integer removedUsersCount;
    private String eventTs;

}