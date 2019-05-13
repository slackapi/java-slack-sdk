package com.github.seratch.jslack.api.model;

import lombok.Builder;
import lombok.Data;

/**
 * https://api.slack.com/methods/reminders.add
 */
@Data
@Builder
public class Reminder {

    private String id;
    private String creator;
    private String user;
    private String text;
    private boolean recurring;
    private Integer time;
    private Integer completeTs;
}