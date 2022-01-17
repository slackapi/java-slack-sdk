package com.slack.api.bolt.util;

import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
public class UrlEncodingOps {

    private UrlEncodingOps() {
    }

    public static String urlEncode(String value) {
        if (value == null) {
            return null;
        }
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            log.warn("Unexpectedly UnsupportedEncodingException was thrown while URL-encoding a string value.");
            return value;
        }
    }
}
