package com.slack.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private String channel;
    private String text;
    private boolean recurring;
    private Integer time;
    private Integer completeTs;
    private Recurrence recurrence;
    // TODO: finalize the type
    private Object item;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Recurrence {
        private String frequency;
        private List<String> weekdays;
    }
}