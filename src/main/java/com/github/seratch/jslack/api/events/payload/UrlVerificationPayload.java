package com.github.seratch.jslack.api.events.payload;

import lombok.Data;

@Data
public class UrlVerificationPayload {

    private String token;
    private String challenge;
    private String type = "url_verification";

}
