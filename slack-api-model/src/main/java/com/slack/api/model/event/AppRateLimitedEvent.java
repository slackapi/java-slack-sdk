package com.slack.api.model.event;

import lombok.Data;

/**
 * https://docs.slack.dev/reference/events/app_rate_limited
 */
@Data
public class AppRateLimitedEvent implements Event {

    public static final String TYPE_NAME = "app_rate_limited";

    private final String type = TYPE_NAME;

    /**
     * the same shared token used to verify other events in the Events API
     */
    private String token;

    /**
     * subscriptions between your app and the workspace with this ID are being rate limited
     */
    private String teamId;

    /**
     * a rounded epoch time value indicating the minute
     * your application became rate limited for this workspace.
     * 1518467820 is at 2018-02-12 20:37:00 UTC.
     */
    private Integer minuteRateLimited;

    /**
     * your application's ID, especially useful
     * if you have multiple applications working with the Events API
     */
    private String apiAppId;

}
