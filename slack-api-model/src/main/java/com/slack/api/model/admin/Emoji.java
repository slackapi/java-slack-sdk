package com.slack.api.model.admin;

import lombok.Data;

@Data
public class Emoji {
    private String url;
    private Integer dateCreated;
    private String uploadedBy;
}
