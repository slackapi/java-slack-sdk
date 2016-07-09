package com.github.seratch.jslack.api.methods.response.reminders;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import lombok.Data;

@Data
public class RemindersCompleteResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
}