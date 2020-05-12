package com.slack.api.rtm;

import com.slack.api.model.event.Event;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Real Time Messaging API event handler base class.
 *
 * @param <E> The type of an events API Payload
 */
public abstract class RTMEventHandler<E extends Event> {

    private String cachedEventName;
    private String cachedEventSubName;
    private Class<E> cachedClazz;

    /**
     * Returns the type value of the event (e.g., MessageEvent.TYPE_NAME)
     */
    public String getEventType() {
        if (cachedEventName != null) {
            return cachedEventName;
        }
        Class<E> clazz = getEventClass();
        try {
            Field field = clazz.getField("TYPE_NAME");
            field.setAccessible(true);
            cachedEventName = (String) field.get(null);
            return cachedEventName;
        } catch (Exception e) {
            throw new IllegalStateException("A static field TYPE_NAME in " + clazz.getCanonicalName() + " is required");
        }
    }

    /**
     * Returns the subtype value of the event (e.g., MessageEvent.TYPE_NAME)
     */
    public String getEventSubType()
    {
        if (cachedEventSubName != null) {
            return cachedEventSubName;
        }
        Class<E> clazz = getEventClass();
        try {
            Field field = clazz.getField("SUBTYPE_NAME");
            field.setAccessible(true);
            cachedEventSubName = (String) field.get(null);
            return cachedEventSubName;
        } catch (Exception e) {
            cachedEventSubName = "";
            return cachedEventSubName;
        }
    }

    /**
     * Returns the Class object of the Event implementation.
     */
    public Class<E> getEventClass() {
        if (cachedClazz != null) {
            return cachedClazz;
        }
        Class<?> clazz = this.getClass();
        while (clazz != Object.class) {
            try {
                Type mySuperclass = clazz.getGenericSuperclass();
                Type tType = ((ParameterizedType) mySuperclass).getActualTypeArguments()[0];
                cachedClazz = (Class<E>) Class.forName(tType.getTypeName());
                return cachedClazz;
            } catch (Exception e) {
            }
            clazz = clazz.getSuperclass();
        }
        throw new IllegalStateException("Failed to load event class - " + this.getClass().getCanonicalName());
    }

    /**
     * Implement your logic in this method.
     *
     * @param event event data
     */
    public abstract void handle(E event);

    /**
     * Used only internally.
     *
     * @param event event data
     */
    public void acceptUntypedObject(Object event) {
        handle((E) event);
    }
}
