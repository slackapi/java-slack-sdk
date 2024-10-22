package com.slack.api.bolt.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.slack.api.app_backend.events.payload.Authorization;
import com.slack.api.app_backend.events.payload.EventsApiPayload;
import com.slack.api.bolt.request.builtin.EventRequest;
import com.slack.api.model.event.Event;
import com.slack.api.util.json.GsonFactory;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class EventsApiPayloadParser {

    private static final Gson GSON = GsonFactory.createSnakeCase();

    /**
     * Cached mapping between Event data types and their "{type}:{subtype}" values.
     */
    private final static Map<Class<? extends Event>, String> eventTypeAndSubtypeValues = new HashMap<>();

    public static final String getEventTypeAndSubtype(Class<? extends Event> clazz) {
        String cached = eventTypeAndSubtypeValues.get(clazz);
        if (cached != null) {
            return cached;
        } else {
            for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
                if (constructor.getParameterCount() == 0) {
                    try {
                        Event event = (Event) constructor.newInstance();
                        String typeAndSubtype = event.getType();
                        if (event.getSubtype() != null) {
                            typeAndSubtype = event.getType() + ":" + event.getSubtype();
                        }
                        eventTypeAndSubtypeValues.put(clazz, typeAndSubtype);
                        return typeAndSubtype;
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                        log.error("Unexpectedly failed to load event type for the class {}", clazz.getCanonicalName());
                        break;
                    }
                }
            }
        }
        return null;
    }

    public static <E extends Event> EventsApiPayload<E> buildEventPayload(EventRequest request) {
        BoltEventPayload<E> payload = GSON.fromJson(request.getRequestBodyAsString(), BoltEventPayload.class);
        Class<E> eventClass = getEventClass(request.getEventTypeAndSubtype());
        if (eventClass != null) {
            Event event = GSON.fromJson(GSON.fromJson(request.getRequestBodyAsString(), JsonElement.class).getAsJsonObject().get("event").getAsJsonObject(), eventClass);
            payload.setEvent((E) event);
        }
        return payload;
    }

    public static final <E extends Event> Class<E> getEventClass(String eventType) {
        for (Map.Entry<Class<? extends Event>, String> entry : eventTypeAndSubtypeValues.entrySet()) {
            if (entry.getValue().equals(eventType)) {
                return (Class<E>) entry.getKey();
            }
        }
        return null;
    }

    public static boolean isAssistantThreadStartedOrContextChangedEvent(JsonObject event) {
        return event.get("assistant_thread") != null
                && event.get("assistant_thread").getAsJsonObject().get("channel_id") != null
                && event.get("assistant_thread").getAsJsonObject().get("thread_ts") != null;
    }

    public static boolean isMessageEventInAssistantThread(JsonObject event) {
        if (event.get("channel_type") != null && event.get("channel_type").getAsString().equals("im")) {
            if (event.get("thread_ts") != null) return true;
            if (event.get("message") != null) {
                // message_changed
                return isAssistantThreadMessageSubEvent(event, "message");
            } else if (event.get("previous_message") != null) {
                // message_deleted
                return isAssistantThreadMessageSubEvent(event, "previous_message");
            }
        }
        return false;
    }

    public static boolean isAssistantThreadMessageSubEvent(JsonObject event, String message) {
        JsonElement subtype = event.get(message).getAsJsonObject().get("subtype");
        return (subtype != null && subtype.getAsString().equals("assistant_app_thread"))
                || event.get(message).getAsJsonObject().get("thread_ts") != null;
    }

    @Data
    private static class BoltEventPayload<E extends Event> implements EventsApiPayload<E> {
        private String token;
        private String enterpriseId;
        private String teamId;
        private String apiAppId;
        private String type;
        private List<String> authedUsers;
        private List<String> authedTeams;
        private List<Authorization> authorizations;
        private boolean isExtSharedChannel;
        private String eventId;
        private Integer eventTime;
        private String eventContext;

        private transient E event;
    }

}
