package com.github.seratch.jslack.app_backend.events;

import com.github.seratch.jslack.app_backend.events.payload.EventsApiPayload;
import com.github.seratch.jslack.common.json.GsonFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
public class EventsDispatcherImpl implements EventsDispatcher {

    private final ConcurrentMap<String, List<EventHandler<?>>> eventTypeAndHandlers = new ConcurrentHashMap<>();

    private final Queue<String> queue = new LinkedList<>();

    private final Thread eventLoopThread = new Thread(() -> {
        while (true) {
            String json = queue.poll();
            if (json != null) {
                log.debug("New message found: {}", json);
                dispatch(json);
            }
            try {
                Thread.sleep(10L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    });

    @Override
    public void register(EventHandler<? extends EventsApiPayload<?>> handler) {
        String eventType = handler.getEventType();
        List<EventHandler<?>> handlers = eventTypeAndHandlers.getOrDefault(eventType, new ArrayList<>());
        handlers.add(handler);
        eventTypeAndHandlers.put(eventType, handlers);
    }

    @Override
    public void deregister(EventHandler<? extends EventsApiPayload<?>> handler) {
        String eventType = handler.getEventType();
        List<EventHandler<?>> handlers = eventTypeAndHandlers.getOrDefault(eventType, new ArrayList<>());
        List<EventHandler<?>> newHandlers = new ArrayList<>();
        for (EventHandler<?> h : handlers) {
            if (!h.equals(handler)) {
                newHandlers.add(h);
            }
        }
        eventTypeAndHandlers.put(eventType, newHandlers);
    }

    @Override
    public void dispatch(String json) {
        String eventType = detectEventType(json);
        if (eventType == null) {
            log.debug("Failed to detect event type from the given JSON data: {}", json);
            return;
        }

        List<EventHandler<?>> eventHandlers = eventTypeAndHandlers.get(eventType);
        if (eventHandlers == null || eventHandlers.size() == 0) {
            log.debug("No event handler registered for type: {}", eventType);
        } else {
            try {
                Class<?> clazz = eventHandlers.get(0).getEventPayloadClass();
                EventsApiPayload<?> event = (EventsApiPayload) GsonFactory.createSnakeCase().fromJson(json, clazz);
                for (EventHandler<?> handler : eventHandlers) {
                    handler.acceptUntypedObject(event);
                }
            } catch (Exception ex) {
                log.error("Exception handling event with type: {}", eventType, ex);
            }
        }
    }

    @Override
    public void enqueue(String json) {
        queue.add(json);
    }

    @Override
    public void start() {
        eventLoopThread.start();
    }

    @Override
    public void stop() {
        eventLoopThread.interrupt();
    }

    static String detectEventType(String json) {
        StringBuilder sb = new StringBuilder();
        char[] chars = json.toCharArray();
        boolean isInsideEventData = false;
        for (int idx = 0; idx < (chars.length - 7); idx++) {
            if (!isInsideEventData && chars[idx] == '"'
                    && chars[idx + 1] == 'e'
                    && chars[idx + 2] == 'v'
                    && chars[idx + 3] == 'e'
                    && chars[idx + 4] == 'n'
                    && chars[idx + 5] == 't'
                    && chars[idx + 6] == '"'
                    && chars[idx + 7] == ':') {
                idx = idx + 8;
                isInsideEventData = true;
            }

            if (isInsideEventData && chars[idx] == '"'
                    && chars[idx + 1] == 't'
                    && chars[idx + 2] == 'y'
                    && chars[idx + 3] == 'p'
                    && chars[idx + 4] == 'e'
                    && chars[idx + 5] == '"'
                    && chars[idx + 6] == ':') {
                idx = idx + 7;
                int doubleQuoteCount = 0;
                boolean isPreviousCharEscape = false;
                while (doubleQuoteCount < 2 && idx < chars.length) {
                    char c = chars[idx];
                    if (c == '"' && !isPreviousCharEscape) {
                        doubleQuoteCount++;
                    } else {
                        if (doubleQuoteCount == 1) {
                            sb.append(c);
                        }
                    }
                    isPreviousCharEscape = c == '\\';
                    idx++;
                }
                break;
            }
        }
        return sb.toString();
    }

}
