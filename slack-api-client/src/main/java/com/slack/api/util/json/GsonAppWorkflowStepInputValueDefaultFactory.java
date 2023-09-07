package com.slack.api.util.json;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.slack.api.model.admin.AppWorkflow;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class GsonAppWorkflowStepInputValueDefaultFactory implements
        JsonDeserializer<AppWorkflow.StepInputValueElementDefault>,
        JsonSerializer<AppWorkflow.StepInputValueElementDefault> {

    private static final String REPORT_THIS = "Please report this issue at https://github.com/slackapi/java-slack-sdk/issues";

    private final boolean failOnUnknownProperties;

    private static final Gson GSON = GsonFactory.createSnakeCase();
    private static final Type TYPE = new TypeToken<List<String>>(){}.getType();

    public GsonAppWorkflowStepInputValueDefaultFactory() {
        this(false);
    }

    public GsonAppWorkflowStepInputValueDefaultFactory(boolean failOnUnknownProperties) {
        this.failOnUnknownProperties = failOnUnknownProperties;
    }

    @Override
    public AppWorkflow.StepInputValueElementDefault deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        AppWorkflow.StepInputValueElementDefault result = new AppWorkflow.StepInputValueElementDefault();
        if (json.isJsonPrimitive()) {
            result.setStringValue(json.getAsString());
            return result;
        } else if (json.isJsonArray()) {
            List<String> values = new ArrayList<>();
            for (JsonElement j : json.getAsJsonArray()) {
                values.add(j.getAsString());
            }
            result.setStringValues(values);
            return result;
        } else {
            if (failOnUnknownProperties) {
                String message = "The whole value (" + json + ") is unsupported. " + REPORT_THIS;
                throw new JsonParseException(message);
            }
        }
        return result;
    }

    private List<String> parseRequired(JsonElement json) throws JsonParseException {
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
    public JsonElement serialize(AppWorkflow.StepInputValueElementDefault src, Type typeOfSrc, JsonSerializationContext context) {
        if (src.getStringValues() != null) {
            JsonArray array = new JsonArray();
            for (String value : src.getStringValues()) {
                array.add(GSON.toJson(value));
            }
            return array;
        } else if (src.getStringValue() != null) {
            return new JsonPrimitive(src.getStringValue());
        }
        return JsonNull.INSTANCE;
    }
}
