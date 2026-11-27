package com.slack.api.model.slackbot;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * https://docs.slack.dev/reference/methods/slackbot.skillSets.info
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkillSet {
    private String access;
    private String creatorId;
    private Integer dateCreated;
    private Integer dateUpdated;
    private String description;
    private String displayName;
    private String id;
    private Boolean isInLibrary;
    private String permalink;
    private Integer skillCount;
}
