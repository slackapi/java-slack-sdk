package com.slack.api.model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * https://api.slack.com/methods/files.comments.add
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileComment {

    private String id;
    private Integer created;
    private Integer timestamp;
    private String user;
    private String comment;
    private String channel;

    @SerializedName("is_intro")
    private boolean intro;
}