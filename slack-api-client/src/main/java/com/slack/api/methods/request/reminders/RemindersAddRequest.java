package com.slack.api.methods.request.reminders;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://api.slack.com/methods/reminders.add
 */
@Data
@Builder
public class RemindersAddRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `reminders:write`
     */
    private String token;

    /**
     * The content of the reminder
     */
    private String text;

    /**
     * When this reminder should happen: the Unix timestamp (up to five years from now),
     * the number of seconds until the reminder (if within 24 hours), or a natural language description
     * (Ex. \"in 15 minutes,\" or \"every Thursday\")
     */
    private String time;

    /**
     * The user who will receive the reminder. If no user is specified, the reminder will go to user who created it.
     */
    private String user;

    private String teamId;

}