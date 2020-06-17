package com.slack.api.app_backend.events;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EventTypeExtractorImpl implements EventTypeExtractor {

    @Override
    public String extractEventType(String json) {
        return extractFieldUnderEvent(json, "type");
    }

    @Override
    public String extractEventSubtype(String json) {
        return extractFieldUnderEvent(json, "subtype");
    }

    private static String extractFieldUnderEvent(String json, String fieldName) {
        try {
            JsonElement root = JsonParser.parseString(json);
            if (root != null && root.isJsonObject() && root.getAsJsonObject().has("event")) {
                JsonElement event = root.getAsJsonObject().get("event");
                if (event.isJsonObject() && event.getAsJsonObject().has(fieldName)) {
                    JsonElement eventType = event.getAsJsonObject().get(fieldName);
                    if (eventType.isJsonPrimitive()) {
                        return eventType.getAsString();
                    }
                }
            }
        } catch (JsonSyntaxException e) {
            log.debug("Failed to parse {} as a JSON data", json, e);
        }
        return "";
    }

}
