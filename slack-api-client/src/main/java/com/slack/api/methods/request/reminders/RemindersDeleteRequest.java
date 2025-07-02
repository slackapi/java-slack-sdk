package com.slack.api.methods.request.reminders;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/reminders.delete
 */
@Data
@Builder
public class RemindersDeleteRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `reminders:write`
     */
    private String token;

    /**
     * The ID of the reminder
     */
    private String reminder;

    private String teamId;

}