package com.slack.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * https://api.slack.com/apis/calls#users
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CallParticipant {

    private String slackId; // Slack's user ID

    private String externalId;
    private String displayName;
    private String avatarUrl;

}
