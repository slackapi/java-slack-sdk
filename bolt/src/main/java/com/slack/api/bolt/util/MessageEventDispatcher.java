package com.slack.api.bolt.util;

import com.slack.api.app_backend.events.payload.EventsApiPayload;
import com.slack.api.bolt.context.builtin.EventContext;
import com.slack.api.bolt.handler.BoltEventHandler;
import com.slack.api.bolt.response.Response;
import com.slack.api.methods.SlackApiException;
import com.slack.api.model.event.MessageEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class MessageEventDispatcher {

    @Nullable private MessageEventDispatcher nextDispatcher;

    abstract boolean isQualify(EventsApiPayload<MessageEvent> event, EventContext ctx);

    abstract Response execute(EventsApiPayload<MessageEvent> event, EventContext ctx) throws SlackApiException, IOException;

    public @Nullable Response dispatch(EventsApiPayload<MessageEvent> event, EventContext ctx) throws SlackApiException, IOException {
        if (isQualify(event, ctx)) {
            return execute(event, ctx);
        } else if (nextDispatcher != null) {
            return nextDispatcher.dispatch(event, ctx);
        } else {
            return null;
        }
    }

    public static class PatternDispatcher extends MessageEventDispatcher {
        private final @NotNull Pattern pattern;
        private final @NotNull BoltEventHandler<MessageEvent> handler;

        public PatternDispatcher(@NotNull Pattern pattern, @NotNull BoltEventHandler<MessageEvent> handler) {
            this.pattern = pattern;
            this.handler = handler;
        }

        @Override boolean isQualify(EventsApiPayload<MessageEvent> event, EventContext ctx) {
            String text = event.getEvent().getText();
            return pattern.matcher(text).matches();
        }

        @Override Response execute(EventsApiPayload<MessageEvent> event, EventContext ctx) throws SlackApiException, IOException {
            return handler.apply(event, ctx);
        }
    }

    public static class Chain {
        private final List<MessageEventDispatcher> dispatchers = new ArrayList<>();

        public Chain addDispatcher(MessageEventDispatcher dispatcher) {
            if (!dispatchers.isEmpty()) {
                int lastIndex = dispatchers.size() - 1;
                MessageEventDispatcher lastDispatcher = dispatchers.get(lastIndex);
                lastDispatcher.nextDispatcher = dispatcher;
            }
            dispatchers.add(dispatcher);
            return this;
        }

        public MessageEventDispatcher head() throws IllegalStateException {
            if (dispatchers.isEmpty()) {
                throw new IllegalStateException("The size of handlers must > 0");
            }
            return dispatchers.get(0);
        }
    }
}
