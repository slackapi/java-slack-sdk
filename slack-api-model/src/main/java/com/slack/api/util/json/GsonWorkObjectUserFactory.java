package com.slack.api.util.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.slack.api.model.work_objects.ExternalUser;
import com.slack.api.model.work_objects.SlackUser;
import com.slack.api.model.work_objects.UnknownUser;
import com.slack.api.model.work_objects.User;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Type;

/**
 * Factory for serializing and deserializing work object {@link User} into their appropriate concrete types, namely
 * {@link com.slack.api.model.work_objects.SlackUser} and {@link com.slack.api.model.work_objects.ExternalUser}.
 */
@RequiredArgsConstructor
public class GsonWorkObjectUserFactory implements JsonDeserializer<User>, JsonSerializer<User> {
    private final boolean failOnUnknownProperties;

    @Override
    public User deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        final JsonObject jsonObject = json.getAsJsonObject();
        final String userType = jsonObject.getAsJsonPrimitive("user_type").getAsString();
        return context.deserialize(jsonObject, getUserClassForType(userType));
    }

    @Override
    public JsonElement serialize(User src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src);
    }

    private Class<? extends User> getUserClassForType(String userType) {
        switch (userType) {
            case SlackUser.USER_TYPE:
                return SlackUser.class;
            case ExternalUser.USER_TYPE:
                return ExternalUser.class;
            default:
                if (failOnUnknownProperties) {
                    throw new JsonParseException("User type " + userType + " is not recognized");
                }
                return UnknownUser.class;
        }
    }
}
