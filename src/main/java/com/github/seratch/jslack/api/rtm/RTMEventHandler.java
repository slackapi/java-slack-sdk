package com.github.seratch.jslack.api.rtm;

import com.github.seratch.jslack.api.model.event.Event;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class RTMEventHandler<E extends Event> {

    private String cachedEventName;
    private Class<E> cachedClazz;

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
            throw new IllegalStateException("Static field TYPE_NAME is required in " + clazz.getCanonicalName());
        }
    }

    public Class<E> getEventClass() {
        if (cachedClazz != null) {
            return cachedClazz;
        }
        Type mySuperclass = this.getClass().getGenericSuperclass();
        Type tType = ((ParameterizedType)mySuperclass).getActualTypeArguments()[0];
        try {
            cachedClazz = (Class<E>) Class.forName(tType.getTypeName());
            return cachedClazz;
        } catch (Exception e) {
            throw new IllegalStateException("Failed to load event class - " + e.getMessage());
        }
    }

    public abstract void handle(E event);

    public void acceptUntypedObject(Object event) {
        handle((E) event);
    }

}