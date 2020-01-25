package com.slack.api.app_backend.events;

public interface EventTypeExtractor {

    String extractEventType(String json);

    String extractEventSubtype(String json);

}
