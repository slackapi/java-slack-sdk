package com.github.seratch.jslack.api.events;

import com.github.seratch.jslack.api.events.payload.EventsApiPayload;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class EventHandler<E extends EventsApiPayload<?>> {

    private Class<E> cachedPayloadClazz = null;

    public abstract String getEventType();

    public Class<E> getEventPayloadClass() {
        if (cachedPayloadClazz != null) {
            return cachedPayloadClazz;
        }
        Class<?> clazz = this.getClass();
        while (clazz != Object.class) {
            try {
                Type mySuperclass = clazz.getGenericSuperclass();
                Type tType = ((ParameterizedType)mySuperclass).getActualTypeArguments()[0];
                cachedPayloadClazz = (Class<E>) Class.forName(tType.getTypeName());
                return cachedPayloadClazz;
            } catch (Exception e) {
            }
            clazz = clazz.getSuperclass();
        }
        throw new IllegalStateException("Failed to load payload class for " + this.getClass().getCanonicalName());
    }

    public abstract void handle(E payload);

    public void acceptUntypedObject(Object payload) {
        handle((E) payload);
    }

}