package com.slack.api.app_backend.util;

import com.github.seratch.jslack.app_backend.events.payload.EventsApiPayload;
import com.slack.api.util.json.GsonFactory;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class JsonPayloadExtractor {

    private static final List<String> EVENTS_API_TYPES = Arrays.asList("url_verification", EventsApiPayload.TYPE);

    public String extractIfExists(String requestBody) {

        if (requestBody == null || requestBody.trim().length() == 0) {
            return null;
        }

        char firstChar = requestBody.trim().charAt(0);
        // payload={url encoded}
        if (firstChar == '{' || firstChar == '[') {
            // Events API
            try {
                JsonElement json = GsonFactory.createSnakeCase().fromJson(requestBody, JsonElement.class);
                if (json != null) {
                    JsonObject payload = json.getAsJsonObject();
                    if (payload != null && payload.get("type") != null) {
                        String type = payload.get("type").getAsString();
                        if (type != null) {
                            if (EVENTS_API_TYPES.contains(type)) {
                                return requestBody;
                            }
                        }
                    }
                }
            } catch (JsonSyntaxException e) {
                // the request body is not a JSON data
            }

        } else {
            String[] pairs = requestBody.split("\\&");
            for (String pair : pairs) {
                String[] fields = pair.split("=");
                if (fields.length == 2) {
                    try {
                        String name = URLDecoder.decode(fields[0].trim().replaceAll("\\n+", ""), "UTF-8");
                        if (name.equals("payload")) {
                            String value = URLDecoder.decode(fields[1], "UTF-8");
                            return value;
                        }
                    } catch (UnsupportedEncodingException e) {
                        log.error("Failed to decode URL-encoded string values - {}", e.getMessage(), e);
                    }
                }
            }
        }

        return null;
    }

}
