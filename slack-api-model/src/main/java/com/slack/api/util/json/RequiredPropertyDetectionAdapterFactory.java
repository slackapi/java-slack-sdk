package com.slack.api.util.json;

import com.google.gson.JsonParseException;
import com.google.gson.Gson;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.slack.api.util.predicate.FieldPredicate;
import com.slack.api.util.annotation.Required;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.IOException;

/**
 * Adapter factory for processing objects annotated with {@link Required}.  This annotation signals what properties
 * of a model object are required, and thus should be expected to be initialized on instantiated instances. For all
 * fields on the model objected annotated with {@link Required} applies the {@link FieldPredicate#validate(Object)} via the
 * specified {@link Required#validator()}.
 * <p>
 * Note that this adapter handles both deserialization (JSON --> POJO) and serialization (POJO --> JSON).
 */
public class RequiredPropertyDetectionAdapterFactory implements TypeAdapterFactory {
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        TypeAdapter<T> delegate = gson.getDelegateAdapter(this, type);
        List<RequiredFieldEntry> entries = buildRequiredFieldEntries(type.getRawType());

        if (entries.isEmpty()) {
            return delegate;
        }

        return new TypeAdapter<T>() {
            @Override
            public void write(JsonWriter out, T value) throws IOException {
                if (value != null) {
                    ensureFieldValidity(value, entries);
                }
                delegate.write(out, value);
            }

            @Override
            public T read(JsonReader in) throws IOException {
                T result = delegate.read(in);
                if (result == null) {
                    return null;
                }

                ensureFieldValidity(result, entries);
                return result;
            }
        };
    }

    /**
     * Scans the given class for fields annotated with {@link Required}, pre-resolves each field's
     * accessibility and {@link FieldPredicate} instance, and returns an immutable list of entries.
     * This is called once per type during Gson adapter creation.
     */
    private List<RequiredFieldEntry> buildRequiredFieldEntries(Class<?> clazz) {
        List<RequiredFieldEntry> entries = new ArrayList<>();
        for (Class<?> current = clazz; current != null && current != Object.class; current = current.getSuperclass()) {
            for (Field field : current.getDeclaredFields()) {
                Required annotation = field.getAnnotation(Required.class);
                if (annotation != null) {
                    field.setAccessible(true);
                    try {
                        FieldPredicate predicate = annotation.validator().getDeclaredConstructor().newInstance();
                        entries.add(new RequiredFieldEntry(field, predicate));
                    } catch (NoSuchMethodException | InstantiationException |
                             IllegalAccessException | InvocationTargetException e) {
                        throw new JsonParseException(
                                "Cannot instantiate validator for field: " + field.getName(), e);
                    }
                }
            }
        }
        return Collections.unmodifiableList(entries);
    }

    private <T> void ensureFieldValidity(T obj, List<RequiredFieldEntry> entries) {
        for (RequiredFieldEntry entry : entries) {
            try {
                Object value = entry.field.get(obj);
                if (!entry.predicate.validate(value)) {
                    throw new JsonParseException("Required field '" + entry.field.getName()
                            + "' failed validation in " + obj.getClass().getSimpleName()
                            + " using predicate " + entry.predicate.getClass().getSimpleName());
                }
            } catch (IllegalAccessException e) {
                throw new JsonParseException(
                        "Cannot access field: " + entry.field.getName(), e);
            }
        }
    }

    /**
     * Class holding the accessible {@link Field} handle and the cached instance of {@link FieldPredicate}.
     */
    @RequiredArgsConstructor
    @EqualsAndHashCode
    private static class RequiredFieldEntry {
        final Field field;
        final FieldPredicate predicate;
    }
}
