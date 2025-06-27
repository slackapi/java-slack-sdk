package com.slack.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * https://docs.slack.dev/reference/methods/reactions.get
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