package com.slack.api.util.json;

import com.google.gson.*;
import com.slack.api.model.admin.AppActivity;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class GsonAppActivityFunctionInputValueFactory implements JsonDeserializer<AppActivity.FunctionInputValue>, JsonSerializer<AppActivity.FunctionInputValue> {

    private static final String REPORT_THIS = "Please report this issue at https://github.com/slackapi/java-slack-sdk/issues";

    private static final List<String> CREDENTIAL_PROPERTY_NAMES = Arrays.asList(
            "credential_source",
            "external_token_id",
            "user_context_source"
    );

    private final boolean failOnUnknownProperties;

    public GsonAppActivityFunctionInputValueFactory() {
        this(false);
    }

    public GsonAppActivityFunctionInputValueFactory(boolean failOnUnknownProperties) {
        this.failOnUnknownProperties = failOnUnknownProperties;
    }

    @Override
    public AppActivity.FunctionInputValue deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        AppActivity.FunctionInputValue result = new AppActivity.FunctionInputValue();
        if (json.isJsonPrimitive()) {
            JsonPrimitive primitive = json.getAsJsonPrimitive();
            if (primitive.isString()) {
                result.setStringValue(primitive.getAsString());
            } else {
                result.setRawValue(json);
            }
            return result;
        } else if (json.isJsonArray()) {
            List<String> values = new ArrayList<>();
            for (JsonElement elem : json.getAsJsonArray()) {
                if (!elem.isJsonPrimitive() || !elem.getAsJsonPrimitive().isString()) {
                    result.setRawValue(json);
                    return result;
                }
                values.add(elem.getAsString());
            }
            result.setStringValues(values);
            return result;
        } else if (json.isJsonObject()) {
            JsonObject obj = json.getAsJsonObject();
            if (!obj.has("credential_source") || !hasStringCredentials(obj)) {
                result.setRawValue(json);
                return result;
            }
            if (failOnUnknownProperties) {
                for (String name : obj.keySet()) {
                    if (!CREDENTIAL_PROPERTY_NAMES.contains(name)) {
                        String message = "An unknown property (" + name + ") is detected. " + REPORT_THIS;
                        throw new JsonParseException(message);
                    }
                }
            }
            result.setCredentialSource(getString(obj, "credential_source"));
            result.setExternalTokenId(getString(obj, "external_token_id"));
            result.setUserContextSource(getString(obj, "user_context_source"));
            return result;
        }
        return result;
    }

    private static boolean hasStringCredentials(JsonObject obj) {
        for (String name : CREDENTIAL_PROPERTY_NAMES) {
            JsonElement value = obj.get(name);
            if (value != null && !value.isJsonNull()
                    && !(value.isJsonPrimitive() && value.getAsJsonPrimitive().isString())) {
                return false;
            }
        }
        return true;
    }

    private static String getString(JsonObject obj, String name) {
        JsonElement value = obj.get(name);
        if (value == null || value.isJsonNull()) {
            return null;
        }
        return value.getAsString();
    }

    @Override
    public JsonElement serialize(AppActivity.FunctionInputValue src, Type typeOfSrc, JsonSerializationContext context) {
        if (src.getStringValue() != null) {
            return new JsonPrimitive(src.getStringValue());
        } else if (src.getStringValues() != null) {
            return context.serialize(src.getStringValues());
        } else if (src.getRawValue() != null) {
            return src.getRawValue();
        } else if (src.getCredentialSource() != null
                || src.getExternalTokenId() != null
                || src.getUserContextSource() != null) {
            JsonObject json = new JsonObject();
            if (src.getCredentialSource() != null) {
                json.addProperty("credential_source", src.getCredentialSource());
            }
            if (src.getExternalTokenId() != null) {
                json.addProperty("external_token_id", src.getExternalTokenId());
            }
            if (src.getUserContextSource() != null) {
                json.addProperty("user_context_source", src.getUserContextSource());
            }
            return json;
        } else {
            log.warn("Unsupported field in AppActivity.FunctionInputValue is detected ({})", src);
            return JsonNull.INSTANCE;
        }
    }
}
