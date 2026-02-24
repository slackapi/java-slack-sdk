package com.slack.api.util.json;

import com.google.gson.JsonParseException;
import com.google.gson.Gson;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.slack.api.model.annotation.FieldPredicate;
import com.slack.api.model.annotation.Required;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.io.IOException;

/**
 * Adapter factory for processing objects annotated with {@link Required}.  This annotation signals what properties
 * of a model object are required, and thus should be expected to be initialized and non-null on every instance of
 * said object.
 * <p>
 * For deserialization (e.g. converting JSON --> POJO), it ensures that any fields marked as {@link Required} are
 * present in the constructed object and nonnull.
 * <p>
 * For serialization (e.g. converting POJO --> JSON), it ensures that any fields marked as {@link Required} are non-null
 * in the construct object prior to serialization.
 */
public class RequiredAdapterFactory implements TypeAdapterFactory {
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        TypeAdapter<T> delegate = gson.getDelegateAdapter(this, type);

        return new TypeAdapter<T>() {
            @Override
            public void write(JsonWriter out, T value) throws IOException {
                if (value != null) {
                    ensureFieldValidity(value);
                }
                delegate.write(out, value);
            }

            @Override
            public T read(JsonReader in) throws IOException {
                T result = delegate.read(in);
                if (result == null) {
                    return null;
                }

                ensureFieldValidity(result);
                return result;
            }
        };
    }

    private <T> void ensureFieldValidity(T obj) {
        Arrays.asList(obj.getClass().getDeclaredFields()).forEach(field -> {
            if (field.isAnnotationPresent(Required.class)) {
                field.setAccessible(true);
                try {
                    FieldPredicate predicate = field.getAnnotation(Required.class).validator().getDeclaredConstructor().newInstance();
                    if (!predicate.test(field.get(obj))) {
                        throw new JsonParseException("Required field '" + field.getName() + "' failed validation in "
                            + obj.getClass().getSimpleName() + " using predicate " + predicate.getClass().getSimpleName());
                    }
                } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                         InvocationTargetException e) {
                    throw new JsonParseException("Cannot parse field: " + field.getName(), e);
                }
            }
        });
    }
}
