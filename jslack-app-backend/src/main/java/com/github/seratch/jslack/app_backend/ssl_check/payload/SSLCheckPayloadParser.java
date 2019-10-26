package com.github.seratch.jslack.app_backend.ssl_check.payload;

import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@Slf4j
public class SSLCheckPayloadParser {

    public SSLCheckPayload parse(String requestBody) {
        if (requestBody == null) {
            return null;
        }
        SSLCheckPayload payload = new SSLCheckPayload();
        String[] pairs = requestBody.split("\\&");
        for (String pair : pairs) {
            String[] fields = pair.split("=");
            if (fields.length == 2) {
                try {
                    String name = URLDecoder.decode(fields[0].trim().replaceAll("\\n+", ""), "UTF-8");
                    String value = URLDecoder.decode(fields[1], "UTF-8");
                    switch (name) {
                        case "token":
                            payload.setToken(value);
                            break;
                        case "ssl_check":
                            payload.setSslCheck(value);
                            break;
                        default:
                    }
                } catch (UnsupportedEncodingException e) {
                    log.error("Failed to decode URL-encoded string values - {}", e.getMessage(), e);
                }
            }
        }
        return payload;
    }
}
