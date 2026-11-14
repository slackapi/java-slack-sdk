package com.slack.api.util.json;

import com.google.gson.*;
import com.slack.api.audit.response.LogsResponse;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class GsonAuditLogsDetailsActionFactory implements JsonDeserializer<LogsResponse.DetailsAction>, JsonSerializer<LogsResponse.DetailsAction> {

    private static final String REPORT_THIS = "Please report this issue at https://github.com/slackapi/java-slack-sdk/issues";

    private final boolean failOnUnknownProperties;

    public GsonAuditLogsDetailsActionFactory() {
        this(false);
    }

    public GsonAuditLogsDetailsActionFactory(boolean failOnUnknownProperties) {
        this.failOnUnknownProperties = failOnUnknownProperties;
    }

    @Override
    public LogsResponse.DetailsAction deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        LogsResponse.DetailsAction result = new LogsResponse.DetailsAction();
        if (json.isJsonPrimitive()) {
            result.setStringValue(json.getAsString());
            return result;
        } else if (json.isJsonObject()) {
            JsonObject obj = json.getAsJsonObject();
            if (failOnUnknownProperties) {
                for (String name : obj.keySet()) {
                    if (!name.equals("resolution") && !name.equals("notify")) {
                        String message = "An unknown property (" + name + ") is detected. " + REPORT_THIS;
                        throw new JsonParseException(message);
                    }
                }
            }
            JsonElement resolution = obj.get("resolution");
            if (resolution != null && !resolution.isJsonNull()) {
                result.setResolution(context.deserialize(resolution, LogsResponse.AAARuleActionResolution.class));
            }
            JsonElement notify = obj.get("notify");
            if (notify != null && !notify.isJsonNull()) {
                result.setNotify(parseNotify(notify, context));
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

    private List<LogsResponse.AAARuleActionNotify> parseNotify(JsonElement json, JsonDeserializationContext context)
            throws JsonParseException {
        List<LogsResponse.AAARuleActionNotify> values = new ArrayList<>();
        if (!json.isJsonArray()) {
            if (failOnUnknownProperties) {
                String message = "A non-array value (" + json + ") for notify is detected. " + REPORT_THIS;
                throw new JsonParseException(message);
            }
            return values;
        }
        for (JsonElement elem : json.getAsJsonArray()) {
            if (elem.isJsonObject()) {
                values.add(context.deserialize(elem, LogsResponse.AAARuleActionNotify.class));
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
    public JsonElement serialize(LogsResponse.DetailsAction src, Type typeOfSrc, JsonSerializationContext context) {
        if (src.getStringValue() != null) {
            return new JsonPrimitive(src.getStringValue());
        } else if (src.getResolution() != null || src.getNotify() != null) {
            JsonObject json = new JsonObject();
            if (src.getResolution() != null) {
                json.add("resolution", context.serialize(src.getResolution()));
            }
            if (src.getNotify() != null) {
                json.add("notify", context.serialize(src.getNotify()));
            }
            return json;
        } else {
            log.warn("Unsupported field in LogsResponse.DetailsAction is detected ({})", src);
            return JsonNull.INSTANCE;
        }
    }
}
