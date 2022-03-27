package com.slack.api.methods.request.reminders;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://api.slack.com/methods/reminders.list
 */
@Data
@Builder
public class RemindersListRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `reminders:read`
     */
    private String token;

    private String teamId;
}