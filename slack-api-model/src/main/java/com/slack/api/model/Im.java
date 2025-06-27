package com.slack.api.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/objects/im-object
 */
@Data
public class Im {

    private String id;
    @SerializedName("is_im")
    private boolean im;
    private String user;
    private Integer created;
    @SerializedName("is_org_shared")
    private boolean orgShared;
    @SerializedName("is_user_deleted")
    private boolean user_deleted;
    @SerializedName("is_archived")
    private boolean archived;
    @SerializedName("is_shared")
    private boolean shared;
    @SerializedName("is_ext_shared")
    private boolean extShared;
    private Double priority;
}
