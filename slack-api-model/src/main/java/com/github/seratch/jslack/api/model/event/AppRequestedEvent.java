package com.github.seratch.jslack.api.model.event;

import com.github.seratch.jslack.api.model.admin.AppRequest;
import lombok.Data;

/**
 * https://api.slack.com/events/app_requested
 */
@Data
public class AppRequestedEvent implements Event {

    public static final String TYPE_NAME = "app_requested";

    private final String type = TYPE_NAME;
    private AppRequest appRequest;
}