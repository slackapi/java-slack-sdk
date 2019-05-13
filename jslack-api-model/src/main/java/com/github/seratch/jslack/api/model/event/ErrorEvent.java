package com.github.seratch.jslack.api.model.event;

import lombok.Data;

/**
 * If there was a problem connecting an error will be returned,
 * including a descriptive error message:
 *
 * <pre>
 * {
 *     "type": "error",
 *     "error": {
 *         "code": 1,
 *         "msg": "Socket URL has expired"
 *     }
 * }
 * </pre>
 * <p>
 * https://api.slack.com/rtm
 */
@Data
public class ErrorEvent implements Event {

    public static final String TYPE_NAME = "error";

    private final String type = TYPE_NAME;
    private Error error;

    @Data
    public static class Error {
        private Integer code;
        private String msg;
    }
}