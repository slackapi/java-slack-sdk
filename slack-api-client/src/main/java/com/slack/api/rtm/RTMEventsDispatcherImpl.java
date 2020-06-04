package com.slack.api.rtm;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.slack.api.model.event.Event;
import com.slack.api.util.json.GsonFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

@Slf4j
public class RTMEventsDispatcherImpl implements RTMEventsDispatcher {

    private final ConcurrentMap<String, List<RTMEventHandler<?>>> eventTypeAndHandlers = new ConcurrentHashMap<>();

    @Override
    public void register(RTMEventHandler<? extends Event> handler) {
        String eventType = handler.getEventType();
        List<RTMEventHandler<?>> handlers = eventTypeAndHandlers.get(eventType);
        if (handlers == null) handlers = new ArrayList<>();
        handlers.add(handler);
        eventTypeAndHandlers.put(eventType, handlers);
    }

    @Override
    public void deregister(RTMEventHandler<? extends Event> handler) {
        String eventType = handler.getEventType();
        List<RTMEventHandler<?>> handlers = eventTypeAndHandlers.get(eventType);
        if (handlers == null) handlers = new ArrayList<>();
        List<RTMEventHandler<?>> newHandlers = new ArrayList<>();
        for (RTMEventHandler<?> h : handlers) {
            if (!h.equals(handler)) {
                newHandlers.add(h);
            }
        }
        eventTypeAndHandlers.put(eventType, newHandlers);
    }

    @Override
    public void dispatch(String json) {
        JsonElement jsonMessage = JsonParser.parseString(json);
        if (jsonMessage.isJsonObject() == false)
            return;

        String eventType = detectEventType(jsonMessage.getAsJsonObject());
        String eventSubType = detectEventSubType(jsonMessage.getAsJsonObject());

        if (eventType == null) {
            log.debug("Failed to detect event type from the given JSON data: {}", json);
            return;
        }

        List<RTMEventHandler<?>> RTMEventHandlers = eventTypeAndHandlers.get(eventType);
        if (RTMEventHandlers == null || RTMEventHandlers.size() == 0) {
            log.debug("No event handler registered for type: {}", eventType);
        } else {
            List<RTMEventHandler<?>> rtmEventHandlers = RTMEventHandlers.stream()
                    .filter(e -> e.getEventSubType().equals(eventSubType))
                    .collect(Collectors.toList());

            if (rtmEventHandlers.isEmpty() == false) {
                Class<?> clazz = rtmEventHandlers.get(0).getEventClass();
                for (RTMEventHandler<?> handler : rtmEventHandlers) {
                    Event event = (Event) GsonFactory.createSnakeCase().fromJson(jsonMessage, clazz);
                    handler.acceptUntypedObject(event);
                }
            }
        }
    }

    @Override
    public RTMMessageHandler toMessageHandler() {
        final RTMEventsDispatcherImpl self = this;
        RTMMessageHandler messageHandler = new RTMMessageHandler() {
            @Override
            public void handle(String message) {
                self.dispatch(message);
            }
        };
        return messageHandler;
    }

    public static String detectEventType(JsonObject json) {
        JsonElement type = json.get("type");
        if (type == null || type.isJsonPrimitive() == false)
            return "";
        return type.getAsString();
    }


    public static String detectEventSubType(JsonObject json) {

        JsonElement type = json.get("subtype");
        if (type == null || type.isJsonPrimitive() == false)
            return "";
        return type.getAsString();
    }

}
