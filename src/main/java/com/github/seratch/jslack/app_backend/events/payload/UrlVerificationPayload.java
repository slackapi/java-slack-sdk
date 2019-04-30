package com.github.seratch.jslack.app_backend.events.payload;

import lombok.Data;

@Data
public class UrlVerificationPayload {
    public static final String TYPE = "url_verification";

    private String token;
    private String challenge;
    private String type = TYPE;

}
