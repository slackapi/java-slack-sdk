package com.slack.api.util.json;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * https://github.com/google/gson/issues/188#issuecomment-282746095
 */
public class UnknownPropertyDetectionAdapterFactory implements TypeAdapterFactory {

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        // If the type adapter is a reflective type adapter, we want to modify the implementation using reflection. The
        // trick is to replace the Map object used to look up the property name. Instead of returning null if the
        // property is not found, we throw a Json exception to terminate the deserialization.
        TypeAdapter<T> delegate = gson.getDelegateAdapter(this, type);

        // Check if the type adapter is a reflective, cause this solution only work for reflection.
        if (delegate instanceof ReflectiveTypeAdapterFactory.Adapter) {

            try {
                // Get reference to the existing boundFields.
                Class<?> adaptorClass = delegate.getClass();
                Field boundFieldsField = null;
                while (boundFieldsField == null && !adaptorClass.equals(Object.class)) {
                    try {
                        boundFieldsField = adaptorClass.getDeclaredField("boundFields");
                    } catch (NoSuchFieldException _ignore) {
                        // Since GSON v3.10, the internal class hierarchy has been changed
                        // 1) com.google.gson.internal.bind.ReflectiveTypeAdapterFactory$FieldReflectionAdapter
                        // 2) com.google.gson.internal.bind.ReflectiveTypeAdapterFactory$Adapter
                        adaptorClass = adaptorClass.getSuperclass();
                    }
                }
                if (boundFieldsField == null) {
                    String message = "Failed to find bound fields inside GSON";
                    throw new IllegalStateException(message);
                }
                boundFieldsField.setAccessible(true);
                Map boundFields = (Map) boundFieldsField.get(delegate);
                StringBuilder sb = new StringBuilder();
                for (Object key : boundFields.keySet()) {
                    sb.append(key + ", ");
                }
                final String boundFieldsStr = sb.append("...").toString();

                // Then replace it with our implementation throwing exception if the value is null.
                boundFields = new LinkedHashMap(boundFields) {

                    @Override
                    public Object get(Object key) {

                        Object value = super.get(key);
                        if (value == null) {
                            throw new JsonParseException("Unknown property detected: " + key + " in " + boundFieldsStr);
                        }
                        return value;

                    }

                };
                // Finally, push our custom map back using reflection.
                boundFieldsField.set(delegate, boundFields);

            } catch (Exception e) {
                // Should never happen if the implementation doesn't change.
                throw new IllegalStateException(e);
            }

        }
        return delegate;
    }

}
