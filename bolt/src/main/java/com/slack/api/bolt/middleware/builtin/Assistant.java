package com.slack.api.bolt.middleware.builtin;

import com.slack.api.app_backend.events.payload.EventsApiPayload;
import com.slack.api.bolt.context.builtin.EventContext;
import com.slack.api.bolt.handler.AssistantEventHandler;
import com.slack.api.bolt.middleware.Middleware;
import com.slack.api.bolt.middleware.MiddlewareChain;
import com.slack.api.bolt.request.Request;
import com.slack.api.bolt.request.RequestType;
import com.slack.api.bolt.request.builtin.EventRequest;
import com.slack.api.bolt.response.Response;
import com.slack.api.bolt.service.AssistantThreadContextService;
import com.slack.api.bolt.service.builtin.DefaultAssistantThreadContextService;
import com.slack.api.model.assistant.AssistantThreadContext;
import com.slack.api.model.event.*;
import com.slack.api.util.thread.DaemonThreadExecutorServiceProvider;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.concurrent.ExecutorService;

import static com.slack.api.bolt.util.EventsApiPayloadParser.buildEventPayload;

public class Assistant implements Middleware {

    @Getter
    @Setter
    private AssistantThreadContextService threadContextService;

    @Getter
    @Setter
    private boolean threadContextAutoSave;

    @Getter
    @Setter
    private ExecutorService executorService;

    @Getter
    @Setter
    private Logger logger = LoggerFactory.getLogger(Assistant.class);

    private AssistantEventHandler<AssistantThreadStartedEvent> threadStarted;
    private AssistantEventHandler<AssistantThreadContextChangedEvent> threadContextChanged;
    private AssistantEventHandler<MessageEvent> userMessage;
    private AssistantEventHandler<MessageFileShareEvent> userMessageWithFiles;
    private AssistantEventHandler<MessageEvent> botMessage;

    public Assistant() {
        this(null, buildDefaultExecutorService(), buildDefaultLogger());
    }

    public Assistant(ExecutorService executorService) {
        this(null, executorService, buildDefaultLogger());
    }

    public Assistant(ExecutorService executorService, Logger logger) {
        this(null, executorService, logger);
    }

    public Assistant(AssistantThreadContextService threadContextService) {
        this(threadContextService, buildDefaultExecutorService(), buildDefaultLogger());
    }

    public Assistant(AssistantThreadContextService threadContextService, ExecutorService executorService) {
        this(threadContextService, executorService, buildDefaultLogger());
    }

    public Assistant(AssistantThreadContextService threadContextService, ExecutorService executorService, Logger logger) {
        setThreadContextAutoSave(true);
        setThreadContextService(threadContextService);
        setExecutorService(executorService);
        setLogger(logger);
    }

    @Override
    public Response apply(Request req, Response resp, MiddlewareChain chain) throws Exception {
        if (req.getRequestType().equals(RequestType.Event) && ((EventContext) req.getContext()).isAssistantThreadEvent()) {
            // Handle only assistant thread events
            EventRequest request = (EventRequest) req;
            EventContext context = request.getContext();

            if (getThreadContextService() == null) {
                setThreadContextService(new DefaultAssistantThreadContextService(request.getContext()));
            }
            context.setThreadContextService(this.getThreadContextService());

            if (isThreadContextAutoSave() && context.getThreadContext() != null) {
                this.getThreadContextService().saveCurrentContext(context.getChannelId(), context.getThreadTs(), context.getThreadContext());
            }
            switch (request.getEventType()) {
                case AssistantThreadStartedEvent.TYPE_NAME:
                    if (this.threadStarted == null) {
                        // the default implementation automatically save the new context and does not do anything else
                        getExecutorService().submit(() -> {
                            try {
                                context.say(r -> r.text("Hi, how can I help you today?"));
                            } catch (Exception e) {
                                getLogger().error("Failed to send the first response: {e}", e);
                            }
                        });
                    } else {
                        getExecutorService().submit(() -> {
                            try {
                                this.threadStarted.apply(buildEventPayload(request), context);
                            } catch (Exception e) {
                                getLogger().error("Failed to run threadStarted handler: {e}", e);
                            }
                        });
                    }
                    return context.ack();
                case AssistantThreadContextChangedEvent.TYPE_NAME:
                    if (this.threadContextChanged == null) {
                        // the default implementation automatically save the new context and does not do anything else
                        getExecutorService().submit(() -> {
                            try {
                                this.getThreadContextService().saveCurrentContext(
                                        context.getChannelId(),
                                        context.getThreadTs(),
                                        context.getThreadContext()
                                );
                            } catch (Exception e) {
                                getLogger().error("Failed to save new thread context: {e}", e);
                            }
                        });
                    } else {
                        getExecutorService().submit(() -> {
                            try {
                                this.threadContextChanged.apply(buildEventPayload(request), context);
                            } catch (Exception e) {
                                getLogger().error("Failed to run threadContextChanged handler: {e}", e);
                            }
                        });
                    }
                    return context.ack();
                case MessageEvent.TYPE_NAME:
                    String[] elements = request.getEventTypeAndSubtype().split(":");
                    if (elements.length == 1) {
                        loadCurrentThreadContext(context);
                        getExecutorService().submit(() -> {
                            EventsApiPayload<MessageEvent> payload = buildEventPayload(request);
                            if (payload.getEvent().getBotId() != null) {
                                try {
                                    this.botMessage.apply(payload, context);
                                } catch (Exception e) {
                                    getLogger().error("Failed to run botMessage handler: {e}", e);
                                }
                            } else {
                                try {
                                    this.userMessage.apply(payload, context);
                                } catch (Exception e) {
                                    getLogger().error("Failed to run userMessage handler: {e}", e);
                                }
                            }
                        });
                        return context.ack();
                    } else if (elements.length == 2 && elements[1].equals(MessageFileShareEvent.SUBTYPE_NAME)) {
                        loadCurrentThreadContext(context);
                        getExecutorService().submit(() -> {
                            try {
                                this.userMessageWithFiles.apply(buildEventPayload(request), request.getContext());
                            } catch (Exception e) {
                                getLogger().error("Failed to run userMessageWithFiles handler: {e}", e);
                            }
                        });
                        return context.ack();
                    } else {
                        // message_changed etc.
                        return context.ack();
                    }
                default:
                    // noop
            }
        }
        return chain.next(req);
    }

    public Assistant threadStarted(AssistantEventHandler<AssistantThreadStartedEvent> handler) {
        this.threadStarted = handler;
        return this;
    }

    public Assistant threadContextChanged(AssistantEventHandler<AssistantThreadContextChangedEvent> handler) {
        this.threadContextChanged = handler;
        return this;
    }

    public Assistant userMessage(AssistantEventHandler<MessageEvent> handler) {
        this.userMessage = handler;
        return this;
    }

    public Assistant userMessageWithFiles(AssistantEventHandler<MessageFileShareEvent> handler) {
        this.userMessageWithFiles = handler;
        return this;
    }

    public Assistant botMessage(AssistantEventHandler<MessageEvent> handler) {
        this.botMessage = handler;
        return this;
    }

    // -------------------------------------------------------------------
    // Private methods
    // -------------------------------------------------------------------

    private void loadCurrentThreadContext(EventContext context) {
        Optional<AssistantThreadContext> threadContext = getThreadContextService().findCurrentContext(context.getChannelId(), context.getThreadTs());
        if (threadContext != null && threadContext.isPresent()) {
            context.setThreadContext(threadContext.get());
        }
    }

    private static ExecutorService buildDefaultExecutorService() {
        return DaemonThreadExecutorServiceProvider.getInstance()
                .createThreadPoolExecutor("bolt-assistant-app-threads", 10);
    }

    private static Logger buildDefaultLogger() {
        return LoggerFactory.getLogger(Assistant.class);
    }
}
