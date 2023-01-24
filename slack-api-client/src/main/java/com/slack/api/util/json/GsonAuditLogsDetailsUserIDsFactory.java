package com.slack.api.util.json;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.slack.api.audit.response.LogsResponse;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class GsonAuditLogsDetailsUserIDsFactory implements JsonDeserializer<LogsResponse.UserIDs>, JsonSerializer<LogsResponse.UserIDs> {

    private static final String REPORT_THIS = "Please report this issue at https://github.com/slackapi/java-slack-sdk/issues";

    private final boolean failOnUnknownProperties;

    private static final Gson GSON = GsonFactory.createSnakeCase();
    private static final Type TYPE = new TypeToken<List<String>>(){}.getType();

    public GsonAuditLogsDetailsUserIDsFactory() {
        this(false);
    }

    public GsonAuditLogsDetailsUserIDsFactory(boolean failOnUnknownProperties) {
        this.failOnUnknownProperties = failOnUnknownProperties;
    }

    @Override
    public LogsResponse.UserIDs deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        LogsResponse.UserIDs result = new LogsResponse.UserIDs();
        if (json.isJsonPrimitive()) {
            result.setUsers(GSON.fromJson(json.getAsString(), TYPE));
            return result;
        } else if (json.isJsonArray()) {
            result.setUsers(parseStringArray(json));
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
    public JsonElement serialize(LogsResponse.UserIDs src, Type typeOfSrc, JsonSerializationContext context) {
        JsonArray array = new JsonArray();
        for (String value : src.getUsers()) {
            array.add(value);
        }
        return array;
    }
}
