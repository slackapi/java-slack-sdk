package com.github.seratch.jslack.api.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * https://api.slack.com/types/im
 */
@Data
public class Im {

    private String id;
    @SerializedName("is_im")
    private boolean im;
    private String user;
    private Integer created;
    @SerializedName("is_user_deleted")
    private boolean user_deleted;
}
