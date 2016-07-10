package com.github.seratch.jslack.api.model;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * https://api.slack.com/types/usergroup
 */
@Data
@Builder
public class Usergroup {

    private String id;
    private String teamId;
    @SerializedName("is_usergroup")
    private boolean usergroup;
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
    private List<String> users;
    private Integer userCount;

    @Data
    public static class Prefs {
        private List<String> channels;
        private List<String> groups;
    }

}