package com.slack.api.app_backend.ssl_check;

import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@Slf4j
public class SSLCheckPayloadDetector {

    public boolean isSSLCheckRequest(String requestBody) {
        if (requestBody == null) {
            return false;
        }
        String[] pairs = requestBody.split("\\&");
        for (String pair : pairs) {
            String[] fields = pair.split("=");
            if (fields.length == 2) {
                try {
                    String name = URLDecoder.decode(fields[0].trim().replaceAll("\\n+", ""), "UTF-8");
                    String value = URLDecoder.decode(fields[1], "UTF-8");
                    if (name.equals("ssl_check") && value.equals("1")) {
                        return true;
                    }
                } catch (UnsupportedEncodingException e) {
                    log.error("Failed to decode URL-encoded string values - {}", e.getMessage(), e);
                }
            }
        }
        return false;
    }

}
