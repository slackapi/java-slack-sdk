package com.slack.api.model.event;

import lombok.Data;

/**
 * A call was rejected
 * <p>
 * https://api.slack.com/events/call_rejected
 */
@Data
public class CallRejectedEvent implements Event {

    public static final String TYPE_NAME = "call_rejected";

    private final String type = TYPE_NAME;

    // TODO: add more fields
}