package com.slack.api.model.event;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

/**
 * The subteam_created event is sent to all connections for a workspace when a new User Group is created.
 * Clients can use this to update their local list of User Groups and group members.
 * <p>
 * https://api.slack.com/events/subteam_created
 */
@Data
public class SubteamCreatedEvent implements Event {

    public static final String TYPE_NAME = "subteam_created";

    private final String type = TYPE_NAME;
    private Subteam subteam;
    private String eventTs;

    @Data
    public static class Subteam {
        private String id;
        private String enterpriseSubteamId;
        private String teamId;
        private boolean isSubteam;
        private boolean isUsergroup;
        private boolean autoProvision;
        private String name;
        private String description;
        private String handle;
        @SerializedName("is_external")
        private boolean external;
        private Integer dateCreate;
        private Integer dateUpdate;
        private Integer dateDelete;
        private String autoType;
        private String createdBy;
        private String updatedBy;
        private String deletedBy;
        private Prefs prefs;
        private Integer userCount;
        private Integer channelCount;
        private List<String> users;
        private boolean isSection;
    }

    @Data
    public static class Prefs {
        private List<String> channels;
        private List<String> groups;
    }

}