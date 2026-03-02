package com.slack.api.util.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.slack.api.model.work_objects.Button;
import com.slack.api.model.work_objects.DefaultAction;
import com.slack.api.model.work_objects.PrimaryActions;
import com.slack.api.model.work_objects.UnknownAction;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Type;

/**
 * Factory for serializing and deserializing work object {@link PrimaryActions} into their appropriate concrete types,
 * namely {@link Button} and {@link DefaultAction}.
 * <p>
 * The discriminator is the {@code "type"} field in the JSON object. A value of {@code "button"} maps to
 * {@link Button}, while any other value is treated as a {@link DefaultAction}.
 */
@RequiredArgsConstructor
public class GsonWorkObjectPrimaryActionsFactory implements JsonDeserializer<PrimaryActions>, JsonSerializer<PrimaryActions> {
    private final boolean failOnUnknownProperties;

    @Override
    public PrimaryActions deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        final JsonObject jsonObject = json.getAsJsonObject();
        final JsonPrimitive typePrimitive = jsonObject.getAsJsonPrimitive("type");
        if (typePrimitive == null) {
            if (failOnUnknownProperties) {
                throw new JsonParseException("Missing 'type' field in PrimaryActions JSON object");
            }
            return new UnknownAction();
        }
        final String type = typePrimitive.getAsString();
        try {
            return context.deserialize(jsonObject, getClassForType(type));
        } catch (JsonParseException e) {
            if (failOnUnknownProperties) {
                throw e;
            }

            return new UnknownAction();
        }
    }

    @Override
    public JsonElement serialize(PrimaryActions src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src);
    }

    private Class<? extends PrimaryActions> getClassForType(String type) {
        if ("button".equals(type)) {
            return Button.class;
        }

        return DefaultAction.class;
    }
}
