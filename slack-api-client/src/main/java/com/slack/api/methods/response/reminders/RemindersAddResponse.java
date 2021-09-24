package com.slack.api.methods.response.reminders;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.Reminder;
import com.slack.api.model.ResponseMetadata;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class RemindersAddResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private Reminder reminder;
    private ResponseMetadata responseMetadata;
}