package com.github.seratch.jslack.app_backend.ssl_check.payload;

import lombok.Data;

@Data
public class SSLCheckPayload {
    private String sslCheck;
    private String token;
}
