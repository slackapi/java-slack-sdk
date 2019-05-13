package com.github.seratch.jslack.api.methods.request.reminders;

import com.github.seratch.jslack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RemindersCompleteRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `reminders:write`
     */
    private String token;

    /**
     * The ID of the reminder to be marked as complete
     */
    private String reminder;
}