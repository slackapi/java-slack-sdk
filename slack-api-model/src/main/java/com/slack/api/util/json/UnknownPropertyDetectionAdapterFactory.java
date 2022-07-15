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
                Field f = delegate.getClass().getDeclaredField("boundFields");
                f.setAccessible(true);
                Map boundFields = (Map) f.get(delegate);
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
                f.set(delegate, boundFields);

            } catch (Exception e) {
                // Should never happen if the implementation doesn't change.
                throw new IllegalStateException(e);
            }

        }
        return delegate;
    }

}
