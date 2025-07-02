package com.slack.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * https://docs.slack.dev/apis/web-api/using-the-calls-api
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
