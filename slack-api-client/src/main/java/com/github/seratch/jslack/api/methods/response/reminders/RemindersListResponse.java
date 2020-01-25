package com.github.seratch.jslack.api.methods.response.reminders;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import com.slack.api.model.Reminder;
import lombok.Data;

import java.util.List;

@Data
public class RemindersListResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private List<Reminder> reminders;
}