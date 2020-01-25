package com.slack.api.app_backend.slash_commands;

import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@Slf4j
public class SlashCommandPayloadDetector {

    public boolean isCommand(String requestBody) {
        String[] pairs = requestBody.split("\\&");
        for (String pair : pairs) {
            String[] fields = pair.split("=");
            if (fields.length == 2) {
                try {
                    String name = URLDecoder.decode(fields[0].trim().replaceAll("\\n+", ""), "UTF-8");
                    String value = URLDecoder.decode(fields[1], "UTF-8");
                    if (name.equals("command")) {
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
