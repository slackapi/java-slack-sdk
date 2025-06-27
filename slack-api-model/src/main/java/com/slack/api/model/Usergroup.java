package com.slack.api.model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * https://docs.slack.dev/reference/objects/usergroup-object
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usergroup {

    private String id;
    private String teamId;
    private String enterpriseSubteamId;
    @SerializedName("is_usergroup")
    private boolean usergroup;
    @SerializedName("is_subteam")
    private boolean subteam;
    @SerializedName("is_section")
    private boolean section;
    private String name;
    private String description;
    private String handle;
    @SerializedName("is_external")
    private boolean external;
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
    private Integer userCount;
    private Integer channelCount;

    @Data
    public static class Prefs {
        private List<String> channels;
        private List<String> groups;
    }

}