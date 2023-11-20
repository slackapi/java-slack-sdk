package com.slack.api.util.json;

import com.google.gson.*;
import com.slack.api.model.event.FunctionExecutedEvent;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GsonFunctionExecutedEventInputValueFactory
        implements JsonDeserializer<FunctionExecutedEvent.InputValue>,
        JsonSerializer<FunctionExecutedEvent.InputValue> {

    private static final String REPORT_THIS = "Please report this issue at https://github.com/slackapi/java-slack-sdk/issues";

    private final boolean failOnUnknownProperties;

    public GsonFunctionExecutedEventInputValueFactory() {
        this(false);
    }

    public GsonFunctionExecutedEventInputValueFactory(boolean failOnUnknownProperties) {
        this.failOnUnknownProperties = failOnUnknownProperties;
    }

    @Override
    public FunctionExecutedEvent.InputValue deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        FunctionExecutedEvent.InputValue result = new FunctionExecutedEvent.InputValue();
        if (json.isJsonPrimitive()) {
            result.setStringValue(json.getAsString());
            return result;
        } else if (json.isJsonArray()) {
            result.setStringValues(parseStringArray(json));
            return result;
        // TODO: } else if (json.isJsonObject()) {
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
    public JsonElement serialize(FunctionExecutedEvent.InputValue src, Type typeOfSrc, JsonSerializationContext context) {
        if (src.getStringValue() != null) {
            return new JsonPrimitive(src.getStringValue());
        } else if (src.getStringValues() != null) {
            JsonArray array = new JsonArray();
            for (String value : src.getStringValues()) {
                array.add(value);
            }
            return array;
        } else {
            return JsonNull.INSTANCE;
        }
    }
}
