package com.github.seratch.jslack.app_backend.events;

import com.github.seratch.jslack.app_backend.events.payload.EventsApiPayload;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Events API handler base class.
 *
 * @param <E> The type of an events API Payload
 */
public abstract class EventHandler<E extends EventsApiPayload<?>> {

    private Class<E> cachedPayloadClazz = null;

    /**
     * Returns the type value of the event (e.g., MessageEvent.TYPE_NAME)
     */
    public abstract String getEventType();

    /**
     * Returns the Class object of the EventApiPayload implementation.
     */
    public Class<E> getEventPayloadClass() {
        if (cachedPayloadClazz != null) {
            return cachedPayloadClazz;
        }
        Class<?> clazz = this.getClass();
        while (clazz != Object.class) {
            try {
                Type mySuperclass = clazz.getGenericSuperclass();
                Type tType = ((ParameterizedType) mySuperclass).getActualTypeArguments()[0];
                cachedPayloadClazz = (Class<E>) Class.forName(tType.getTypeName());
                return cachedPayloadClazz;
            } catch (Exception e) {
            }
            clazz = clazz.getSuperclass();
        }
        throw new IllegalStateException("Failed to load payload class for " + this.getClass().getCanonicalName());
    }

    /**
     * Implement your logic in this method.
     *
     * @param payload Events API payload
     */
    public abstract void handle(E payload);

    /**
     * Used only internally.
     *
     * @param payload Events API payload
     */
    public void acceptUntypedObject(Object payload) {
        handle((E) payload);
    }

}