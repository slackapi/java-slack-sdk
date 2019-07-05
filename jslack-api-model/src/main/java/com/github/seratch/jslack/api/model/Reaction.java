package com.github.seratch.jslack.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * https://api.slack.com/methods/reactions.get
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reaction {

    private String name;
    private Integer count;
    private List<String> users;
    private String url;
}