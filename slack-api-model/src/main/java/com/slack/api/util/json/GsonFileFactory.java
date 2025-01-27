package com.slack.api.util.json;

import com.google.gson.*;
import com.slack.api.model.File;

import java.lang.reflect.Type;

public class GsonFileFactory implements JsonDeserializer<File>, JsonSerializer<File> {

    // This is just a workaround to customize Gson library behavior
    // You don't need to edit this class at all
    static class NormalizedFile extends File {
    }

    private boolean failOnUnknownProperties;

    public GsonFileFactory() {
        this(false);
    }

    public GsonFileFactory(boolean failOnUnknownProperties) {
        this.failOnUnknownProperties = failOnUnknownProperties;
    }

    @Override
    public File deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        final JsonObject jsonObject = json.getAsJsonObject();
        // Remove unusual data structure form Slack API server
        // See https://github.com/slackapi/java-slack-sdk/issues/1426 for more details
        if (jsonObject.has("groups") && !jsonObject.get("groups").isJsonArray()) {
            // As the starting point in Jan 2025, we just ignore this property,
            // but we may want to assign to a different field if it's necessary for some use cases
            jsonObject.remove("groups");
        }
        if (jsonObject.has("shares")) {
            JsonObject shares = jsonObject.get("shares").getAsJsonObject();
            if (shares.has("public")) {
                adjustSharesObjects(shares.get("public").getAsJsonObject());
            }
            if (shares.has("private")) {
                adjustSharesObjects(shares.get("private").getAsJsonObject());
            }
        }
        // To prevent StackOverflowError here, run the deserialize method for File's subclass.
        // If we want to attach the above unusual data, you can add it to File class
        return context.deserialize(jsonObject, NormalizedFile.class);
    }

    private void adjustSharesObjects(JsonObject shares) {
        for (String channelId : shares.keySet()) {
            for (JsonElement elem : shares.get(channelId).getAsJsonArray()) {
                JsonObject e = elem.getAsJsonObject();
                if (e.has("reply_users") && !e.get("reply_users").isJsonArray()) {
                    // As the starting point in Jan 2025, we just ignore this property,
                    // but we may want to assign to a different field if it's necessary for some use cases
                    e.remove("reply_users");
                }
            }
        }
    }

    @Override
    public JsonElement serialize(File src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src);
    }
}
