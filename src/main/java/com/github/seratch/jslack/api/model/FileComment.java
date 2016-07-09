package com.github.seratch.jslack.api.model;

import lombok.Builder;
import lombok.Data;

/**
 * https://api.slack.com/methods/files.comments.add
 */
@Data
@Builder
public class FileComment {

    private String id;
    private Integer created;
    private Integer timestamp;
    private String user;
    private String comment;
    private String channel;
}