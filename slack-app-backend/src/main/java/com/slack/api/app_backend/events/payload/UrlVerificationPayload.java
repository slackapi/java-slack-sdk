package com.slack.api.app_backend.events.payload;

import lombok.Data;

@Data
public class UrlVerificationPayload {
    public static final String TYPE = "url_verification";
    private final String type = TYPE;

    private String token;
    private String challenge;
}
