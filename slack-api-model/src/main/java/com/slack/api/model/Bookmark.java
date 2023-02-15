package com.slack.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bookmark {
    private String id;
    private String channelId;
    private String title;
    private String link;
    private String emoji;
    private String iconUrl;
    private String entityId;
    private String type;
    private Integer dateCreated;
    private Integer dateUpdated;
    private String rank;
    private String lastUpdatedByUserId;
    private String lastUpdatedByTeamId;
    private String shortcutId;
    private String appId;
    private String appActionId;
}
