package com.slack.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * https://api.slack.com/methods/reminders.add
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reminder {

    private String id;
    private String creator;
    private String user;
    private String text;
    private boolean recurring;
    private Integer time;
    private Integer completeTs;
}