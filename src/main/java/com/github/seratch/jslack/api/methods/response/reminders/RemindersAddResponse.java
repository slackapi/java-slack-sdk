package com.github.seratch.jslack.api.methods.response.reminders;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import com.github.seratch.jslack.api.model.Reminder;
import lombok.Data;

@Data
public class RemindersAddResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;

    private Reminder reminder;
}