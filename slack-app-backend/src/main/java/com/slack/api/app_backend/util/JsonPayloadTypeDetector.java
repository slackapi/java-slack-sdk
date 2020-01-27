package com.slack.api.app_backend.util;

import com.slack.api.util.json.GsonFactory;
import lombok.Data;

public class JsonPayloadTypeDetector {

    @Data
    public static class Payload {
        private String type;
    }

    public String detectType(String json) {
        Payload payload = GsonFactory.createSnakeCase().fromJson(json, Payload.class);
        return payload.getType();
    }

}
