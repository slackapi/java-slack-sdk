package com.slack.api.model;

import lombok.Data;

@Data
public class Topic {
    private String value;
    private String creator;
    private Integer lastSet;
}
