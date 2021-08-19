package com.slack.api.methods.request.reminders;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RemindersInfoRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `reminders:read`
     */
    private String token;

    /**
     * The ID of the reminder
     */
    private String reminder;

    private String teamId;

}