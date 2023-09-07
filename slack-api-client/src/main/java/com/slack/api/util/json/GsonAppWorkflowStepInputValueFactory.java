package com.slack.api.util.json;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.slack.api.model.admin.AppWorkflow;
import com.slack.api.model.block.LayoutBlock;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class GsonAppWorkflowStepInputValueFactory
        implements JsonDeserializer<AppWorkflow.StepInputValue>, JsonSerializer<AppWorkflow.StepInputValue> {

    private static final String REPORT_THIS = "Please report this issue at https://github.com/slackapi/java-slack-sdk/issues";

    private final boolean failOnUnknownProperties;

    private static final Gson GSON = GsonFactory.createSnakeCase();
    private static final Type TYPE = new TypeToken<List<String>>(){}.getType();

    public GsonAppWorkflowStepInputValueFactory() {
        this(false);
    }

    public GsonAppWorkflowStepInputValueFactory(boolean failOnUnknownProperties) {
        this.failOnUnknownProperties = failOnUnknownProperties;
    }

    @Override
    public AppWorkflow.StepInputValue deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        AppWorkflow.StepInputValue result = new AppWorkflow.StepInputValue();
        if (json.isJsonPrimitive()) {
            result.setStringValue(json.getAsString());
            return result;
        } else if (json.isJsonObject()) {
            JsonObject obj = json.getAsJsonObject();
            if (obj.get("required") != null && obj.get("required").isJsonArray()) {
                result.setRequired(parseStringArray(obj.get("required")));
            }
            if (obj.get("elements") != null && obj.get("elements").isJsonArray()) {
                result.setElements(parseElements(obj.get("elements")));
            }
            return result;
        } else if (json.isJsonArray()) {
            JsonArray array = json.getAsJsonArray();
            if (array == null || array.size() == 0) {
                return result;
            }
            if (array.get(0).isJsonObject()) {
                result.setInteractiveBlocks(parseInteractiveBlocks(json));
            } else if (array.get(0).isJsonPrimitive()) {
                result.setStringValues(parseStringArray(json));
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

    private List<AppWorkflow.StepInputValueElement> parseElements(JsonElement json) throws JsonParseException {
        List<AppWorkflow.StepInputValueElement> values = new ArrayList<>();
        for (JsonElement elem : json.getAsJsonArray()) {
            if (elem.isJsonObject()) {
                values.add(GSON.fromJson(elem, AppWorkflow.StepInputValueElement.class));
            } else {
                if (failOnUnknownProperties) {
                    String message = "An unexpected element (" + elem + ") in an array is detected. " + REPORT_THIS;
                    throw new JsonParseException(message);
                }
            }
        }
        return values;
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

    private List<LayoutBlock> parseInteractiveBlocks(JsonElement json) throws JsonParseException {
        List<LayoutBlock> values = new ArrayList<>();
        for (JsonElement elem : json.getAsJsonArray()) {
            if (elem.isJsonObject()) {
                values.add(GSON.fromJson(elem, LayoutBlock.class));
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
    public JsonElement serialize(AppWorkflow.StepInputValue src, Type typeOfSrc, JsonSerializationContext context) {
        JsonArray array = new JsonArray();
        for (AppWorkflow.StepInputValueElement value : src.getElements()) {
            array.add(GSON.toJson(value));
        }
        return array;
    }
}
