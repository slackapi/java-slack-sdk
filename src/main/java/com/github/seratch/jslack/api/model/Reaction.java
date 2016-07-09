package com.github.seratch.jslack.api.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * https://api.slack.com/methods/reactions.get
 */
@Data
@Builder
public class Reaction {

    private String name;
    private Integer count;
    private List<String> users;
}