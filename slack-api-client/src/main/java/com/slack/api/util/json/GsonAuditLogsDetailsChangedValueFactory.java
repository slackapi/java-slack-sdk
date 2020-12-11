package com.slack.api.util.json;

import com.google.gson.*;
import com.slack.api.audit.response.LogsResponse;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class GsonAuditLogsDetailsChangedValueFactory implements JsonDeserializer<LogsResponse.DetailsChangedValue>, JsonSerializer<LogsResponse.DetailsChangedValue> {

    private static final String REPORT_THIS = "Please report this issue at https://github.com/slackapi/java-slack-sdk/issues";

    private final boolean failOnUnknownProperties;

    public GsonAuditLogsDetailsChangedValueFactory() {
        this(false);
    }

    public GsonAuditLogsDetailsChangedValueFactory(boolean failOnUnknownProperties) {
        this.failOnUnknownProperties = failOnUnknownProperties;
    }

    @Override
    public LogsResponse.DetailsChangedValue deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        LogsResponse.DetailsChangedValue result = new LogsResponse.DetailsChangedValue();
        if (json.isJsonPrimitive()) {
            result.setStringValue(json.getAsString());
            return result;
        } else if (json.isJsonArray()) {
            result.setStringValues(parseStringArray(json));
            return result;
        } else if (json.isJsonObject()) {
            Map<String, List<String>> namedValues = new HashMap<>();
            result.setNamedStringValues(namedValues);
            JsonObject obj = json.getAsJsonObject();
            for (String name : obj.keySet()) {
                JsonElement value = obj.get(name);
                if (value.isJsonArray()) {
                    namedValues.put(name, parseStringArray(value));
                } else {
                    if (failOnUnknownProperties) {
                        String message = "A non-array value (" + value + ") for " + name + " is detected. " + REPORT_THIS;
                        throw new JsonParseException(message);
                    }
                }
            }
            return result;
        } else {
            if (failOnUnknownProperties) {
                String message = "The whole value (" + json + ") is unsupported. " + REPORT_THIS;
                throw new JsonParseException(message);
            }
        }
        return result;
    }

    private List<String> parseStringArray(JsonElement json) throws JsonParseException {
        List<String> values = new ArrayList<>();
        for (JsonElement elem : json.getAsJsonArray()) {
            if (elem.isJsonPrimitive()) {
                values.add(elem.getAsString());
            } else {
                if (failOnUnknownProperties) {
                    String message = "An unexpected element (" + elem + ") in an array is detected. " + REPORT_THIS;
                    throw new JsonParseException(message);
                }
            }
        }
        return values;
    }

    @Override
    public JsonElement serialize(LogsResponse.DetailsChangedValue src, Type typeOfSrc, JsonSerializationContext context) {
        if (src.getStringValue() != null) {
            return new JsonPrimitive(src.getStringValue());
        } else if (src.getStringValues() != null) {
            JsonArray array = new JsonArray();
            for (String value : src.getStringValues()) {
                array.add(value);
            }
            return array;
        } else if (src.getNamedStringValues() != null) {
            JsonObject json = new JsonObject();
            for (Map.Entry<String, List<String>> each : src.getNamedStringValues().entrySet()) {
                JsonArray array = new JsonArray();
                for (String value : each.getValue()) {
                    array.add(value);
                }
                json.add(each.getKey(), array);
            }
            return json;
        } else {
            log.warn("Unsupported field in LogsResponse.DetailsChangedValue is detected ({})", src);
            return JsonNull.INSTANCE;
        }
    }
}
