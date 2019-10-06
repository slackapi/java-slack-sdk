package com.github.seratch.jslack.lightning;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.model.event.Event;
import com.github.seratch.jslack.app_backend.SlackSignature;
import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.EventsDispatcher;
import com.github.seratch.jslack.app_backend.events.EventsDispatcherFactory;
import com.github.seratch.jslack.app_backend.events.payload.EventsApiPayload;
import com.github.seratch.jslack.app_backend.interactive_messages.payload.BlockActionPayload;
import com.github.seratch.jslack.common.json.GsonFactory;
import com.github.seratch.jslack.lightning.handler.LightningEventHandler;
import com.github.seratch.jslack.lightning.handler.builtin.*;
import com.github.seratch.jslack.lightning.middleware.Middleware;
import com.github.seratch.jslack.lightning.middleware.builtin.RequestVerification;
import com.github.seratch.jslack.lightning.middleware.builtin.SingleTeamAuthorization;
import com.github.seratch.jslack.lightning.request.Request;
import com.github.seratch.jslack.lightning.request.builtin.*;
import com.github.seratch.jslack.lightning.response.Response;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Slf4j
public class App {

    private final AppConfig appConfig;
    private final Slack slack;
    private final List<Middleware> middlewareList;

    private final Map<String, SlashCommandHandler> slashCommandHandlers = new HashMap<>();
    private final Map<String, AttachmentActionHandler> attachmentActionHandlers = new HashMap<>();
    private final Map<String, LightningEventHandler<Event>> eventHandlers = new HashMap<>();
    private final Map<String, BlockActionHandler> blockActionHandlers = new HashMap<>();
    private final Map<String, BlockSuggestionHandler> blockSuggestionHandlers = new HashMap<>();
    private final Map<String, DialogSubmissionHandler> dialogSubmissionHandlers = new HashMap<>();
    private final Map<String, DialogSuggestionHandler> dialogSuggestionHandlers = new HashMap<>();
    private final Map<String, DialogCancellationHandler> dialogCancellationHandlers = new HashMap<>();
    private final Map<String, MessageActionHandler> messageActionHandlers = new HashMap<>();
    private final Map<String, ViewClosedHandler> viewClosedHandlers = new HashMap<>();
    private final Map<String, ViewSubmissionHandler> viewSubmissionHandlers = new HashMap<>();
    private final Map<String, OutgoingWebhooksHandler> outgoingWebhooksHandlers = new HashMap<>();
    private final EventsDispatcher eventsDispatcher = EventsDispatcherFactory.getInstance();

    private static final Gson gson = GsonFactory.createSnakeCase();

    // --------------------------------------
    // constructors
    // --------------------------------------

    public App() {
        this(new AppConfig());
    }

    public App(AppConfig appConfig) {
        this(appConfig, buildDefaultMiddlewareList(appConfig));
    }

    public App(AppConfig appConfig, List<Middleware> middlewareList) {
        this(appConfig, Slack.getInstance(), middlewareList);
    }

    public App(AppConfig appConfig, Slack slack, List<Middleware> middlewareList) {
        this.appConfig = appConfig;
        this.slack = slack;
        this.middlewareList = middlewareList;
    }

    // --------------------------------------
    // public methods
    // --------------------------------------

    public App start() {
        log.info("⚡️ Your Lightning app is running!");
        this.eventsDispatcher.start();
        return this;
    }

    public App stop() {
        log.info("⚡️ Your Lightning app has stopped.");
        this.eventsDispatcher.stop();
        return this;
    }

    public AppConfig config() {
        return this.appConfig;
    }

    public App use(Middleware middleware) {
        this.middlewareList.add(middleware);
        return this;
    }

    public <E extends Event> App event(Class<E> eventClass, LightningEventHandler<E> handler) {
        String eventType = getEventType(eventClass);
        if (eventType == null) {
            throw new IllegalArgumentException("Unexpectedly failed to register the handler");
        }
        if (eventHandlers.get(eventType) != null) {
            log.warn("Replaced the handler for {}", eventType);
        }
        eventHandlers.put(eventType, (LightningEventHandler<Event>) handler);
        return this;
    }

    public App event(EventHandler<?> handler) {
        eventsDispatcher.register(handler);
        return this;
    }

    public App command(String command, SlashCommandHandler handler) {
        if (slashCommandHandlers.get(command) != null) {
            log.warn("Replaced the handler for {}", command);
        }
        slashCommandHandlers.put(command, handler);
        return this;
    }

    public App attachmentAction(String callbackId, AttachmentActionHandler handler) {
        if (attachmentActionHandlers.get(callbackId) != null) {
            log.warn("Replaced the handler for {}", callbackId);
        }
        attachmentActionHandlers.put(callbackId, handler);
        return this;
    }

    public App blockAction(String actionId, BlockActionHandler handler) {
        if (blockActionHandlers.get(actionId) != null) {
            log.warn("Replaced the handler for {}", actionId);
        }
        blockActionHandlers.put(actionId, handler);
        return this;
    }

    public App blockSuggestion(String actionId, BlockSuggestionHandler handler) {
        if (blockSuggestionHandlers.get(actionId) != null) {
            log.warn("Replaced the handler for {}", actionId);
        }
        blockSuggestionHandlers.put(actionId, handler);
        return this;
    }

    public App messageAction(String callbackId, MessageActionHandler handler) {
        if (messageActionHandlers.get(callbackId) != null) {
            log.warn("Replaced the handler for {}", callbackId);
        }
        messageActionHandlers.put(callbackId, handler);
        return this;
    }


    public App dialogSubmission(String callbackId, DialogSubmissionHandler handler) {
        if (dialogSubmissionHandlers.get(callbackId) != null) {
            log.warn("Replaced the handler for {}", callbackId);
        }
        dialogSubmissionHandlers.put(callbackId, handler);
        return this;
    }

    public App dialogCancellation(String callbackId, DialogCancellationHandler handler) {
        if (dialogCancellationHandlers.get(callbackId) != null) {
            log.warn("Replaced the handler for {}", callbackId);
        }
        dialogCancellationHandlers.put(callbackId, handler);
        return this;
    }

    public App dialogSuggestion(String callbackId, DialogSuggestionHandler handler) {
        if (dialogSuggestionHandlers.get(callbackId) != null) {
            log.warn("Replaced the handler for {}", callbackId);
        }
        dialogSuggestionHandlers.put(callbackId, handler);
        return this;
    }

    public App viewClosed(String callbackId, ViewClosedHandler handler) {
        if (viewClosedHandlers.get(callbackId) != null) {
            log.warn("Replaced the handler for {}", callbackId);
        }
        viewClosedHandlers.put(callbackId, handler);
        return this;
    }

    public App viewSubmission(String callbackId, ViewSubmissionHandler handler) {
        if (viewSubmissionHandlers.get(callbackId) != null) {
            log.warn("Replaced the handler for {}", callbackId);
        }
        viewSubmissionHandlers.put(callbackId, handler);
        return this;
    }

    public App webhook(String triggerWord, OutgoingWebhooksHandler handler) {
        if (outgoingWebhooksHandlers.get(triggerWord) != null) {
            log.warn("Replaced the handler for {}", triggerWord);
        }
        outgoingWebhooksHandlers.put(triggerWord, handler);
        return this;
    }

    public Response run(Request request) throws Exception {
        LinkedList<Middleware> remaining = new LinkedList<>(middlewareList);
        if (remaining.isEmpty()) {
            return runHandler(request);
        } else {
            Middleware firstMiddleware = remaining.pop();
            return runMiddleware(request, firstMiddleware, remaining);
        }
    }

    // --------------------------------------
    // internal methods
    // --------------------------------------

    protected static List<Middleware> buildDefaultMiddlewareList(AppConfig appConfig) {
        List<Middleware> middlewareList = new ArrayList<>();

        // request verification
        // https://api.slack.com/docs/verifying-requests-from-slack
        SlackSignature.Verifier verifier = new SlackSignature.Verifier(new SlackSignature.Generator(appConfig.getSigningSecret()));
        RequestVerification requestVerification = new RequestVerification(verifier);
        middlewareList.add(requestVerification);

        // single team authorization
        if (appConfig.getSingleTeamBotToken() != null) {
            middlewareList.add(new SingleTeamAuthorization(appConfig));
        } else {
            log.debug("Skipped adding SingleTeamAuthorization - you need to call `app.use(new YourOwnMultiTeamsAuthorization())`");
        }

        return middlewareList;
    }

    protected Response runMiddleware(
            Request request,
            Middleware current,
            LinkedList<Middleware> remaining) throws Exception {
        if (remaining.isEmpty()) {
            return current.apply(request, (req) -> runHandler(req));
        } else {
            Middleware next = remaining.pop();
            return current.apply(request, (req) -> runMiddleware(request, next, remaining));
        }
    }

    protected Response runHandler(Request req) throws IOException, SlackApiException {
        switch (req.getRequestType()) {
            case Command: {
                SlashCommandRequest request = (SlashCommandRequest) req;
                SlashCommandHandler handler = slashCommandHandlers.get(request.getPayload().getCommand());
                if (handler != null) {
                    return handler.apply(request, request.getContext());
                } else {
                    log.warn("No SlashCommandHandler registered for command: {}", request.getPayload().getCommand());
                }
                break;
            }
            case OutgoingWebhooks: {
                OutgoingWebhooksRequest request = (OutgoingWebhooksRequest) req;
                OutgoingWebhooksHandler handler = outgoingWebhooksHandlers.get(request.getPayload().getTriggerWord());
                if (handler != null) {
                    return handler.apply(request, request.getContext());
                } else {
                    log.warn("No handler OutgoingWebhooksHandler for trigger_word: {}", request.getPayload().getTriggerWord());
                }
                break;
            }
            case Event: {
                eventsDispatcher.enqueue(req.getRequestBodyAsString());
                EventRequest request = (EventRequest) req;
                LightningEventHandler<Event> handler = eventHandlers.get(request.getEventType());
                if (handler != null) {
                    LightningEventPayload payload = buildEventPayload(request);
                    return handler.apply(payload, request.getContext());
                } else {
                    return Response.ok();
                }
            }
            case UrlVerification: {
                // https://api.slack.com/events/url_verification
                return Response.builder()
                        .statusCode(200)
                        .contentType("text/plain")
                        .body(req.getRequestBodyAsString())
                        .build();
            }
            case AttachmentAction: {
                AttachmentActionRequest request = (AttachmentActionRequest) req;
                AttachmentActionHandler handler = attachmentActionHandlers.get(request.getPayload().getCallbackId());
                if (handler != null) {
                    return handler.apply(request, request.getContext());
                } else {
                    log.warn("No AttachmentActionHandler registered for callback_id: {}", request.getPayload().getCallbackId());
                }
                break;
            }
            case BlockAction: {
                BlockActionRequest request = (BlockActionRequest) req;
                List<BlockActionPayload.Action> actions = request.getPayload().getActions();
                if (actions.size() == 1) {
                    BlockActionHandler handler = blockActionHandlers.get(actions.get(0).getActionId());
                    if (handler != null) {
                        return handler.apply(request, request.getContext());
                    } else {
                        log.warn("No BlockActionHandler registered for action_id: {}", actions.get(0).getActionId());
                    }
                } else {
                    for (BlockActionPayload.Action action : request.getPayload().getActions()) {
                        // Returned response values will be ignored
                        blockActionHandlers.get(actions.get(0).getActionId());
                    }
                }
                break;
            }
            case BlockSuggestion: {
                BlockSuggestionRequest request = (BlockSuggestionRequest) req;
                BlockSuggestionHandler handler = blockSuggestionHandlers.get(request.getPayload().getActionId());
                if (handler != null) {
                    return handler.apply(request, request.getContext());
                }
                break;
            }
            case MessageAction: {
                MessageActionRequest request = (MessageActionRequest) req;
                MessageActionHandler handler = messageActionHandlers.get(request.getPayload().getCallbackId());
                if (handler != null) {
                    return handler.apply(request, request.getContext());
                } else {
                    log.warn("No MessageActionHandler registered for callback_id: {}", request.getPayload().getCallbackId());
                }
                break;
            }
            case DialogSubmission: {
                DialogSubmissionRequest request = (DialogSubmissionRequest) req;
                DialogSubmissionHandler handler = dialogSubmissionHandlers.get(request.getPayload().getCallbackId());
                if (handler != null) {
                    return handler.apply(request, request.getContext());
                } else {
                    log.warn("No DialogSubmissionHandler registered for callback_id: {}", request.getPayload().getCallbackId());
                }
                break;
            }
            case DialogCancellation: {
                DialogCancellationRequest request = (DialogCancellationRequest) req;
                DialogCancellationHandler handler = dialogCancellationHandlers.get(request.getPayload().getCallbackId());
                if (handler != null) {
                    return handler.apply(request, request.getContext());
                } else {
                    log.warn("No DialogCancellationHandler registered for callback_id: {}", request.getPayload().getCallbackId());
                }
                break;
            }
            case DialogSuggestion: {
                DialogSuggestionRequest request = (DialogSuggestionRequest) req;
                DialogSuggestionHandler handler = dialogSuggestionHandlers.get(request.getPayload().getCallbackId());
                if (handler != null) {
                    return handler.apply(request, request.getContext());
                } else {
                    log.warn("No DialogSuggestionHandler registered for callback_id: {}", request.getPayload().getCallbackId());
                }
                break;
            }
            case ViewSubmission: {
                ViewSubmissionRequest request = (ViewSubmissionRequest) req;
                ViewSubmissionHandler handler = viewSubmissionHandlers.get(request.getPayload().getView().getCallbackId());
                if (handler != null) {
                    return handler.apply(request, request.getContext());
                } else {
                    log.warn("No ViewSubmissionHandler registered for callback_id: {}", request.getPayload().getView().getCallbackId());
                }
                break;
            }
            case ViewClosed: {
                ViewClosedRequest request = (ViewClosedRequest) req;
                ViewClosedHandler handler = viewClosedHandlers.get(request.getPayload().getView().getCallbackId());
                if (handler != null) {
                    return handler.apply(request, request.getContext());
                } else {
                    log.warn("No ViewClosedHandler registered for callback_id: {}", request.getPayload().getView().getCallbackId());
                }
                break;
            }
            default:
        }
        return Response.json(404, "{\"error\":\"no handler found\"}");
    }

    private Map<Class<? extends Event>, String> eventTypes = new HashMap<>();

    private Class<? extends Event> getEventClass(String eventType) {
        for (Map.Entry<Class<? extends Event>, String> entry : eventTypes.entrySet()) {
            if (entry.getValue().equals(eventType)) {
                return entry.getKey();
            }
        }
        return null;
    }

    private String getEventType(Class<? extends Event> clazz) {
        String cached = eventTypes.get(clazz);
        if (cached != null) {
            return cached;
        } else {
            for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
                if (constructor.getParameterCount() == 0) {
                    try {
                        Event event = (Event) constructor.newInstance();
                        eventTypes.put(clazz, event.getType());
                        return event.getType();
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                        log.error("Unexpectedly failed to load event type for the class {}", clazz.getCanonicalName());
                        break;
                    }
                }
            }
        }
        return null;
    }

    private LightningEventPayload buildEventPayload(EventRequest request) {
        LightningEventPayload payload = gson.fromJson(request.getRequestBodyAsString(), LightningEventPayload.class);
        Class<? extends Event> eventClass = getEventClass(request.getEventType());
        if (eventClass != null) {
            Event event = gson.fromJson(gson.fromJson(request.getRequestBodyAsString(), JsonElement.class).getAsJsonObject().get("event").getAsJsonObject(), eventClass);
            payload.setEvent(event);
        }
        return payload;
    }

    @Data
    static class LightningEventPayload implements EventsApiPayload<Event> {
        private String token;
        private String enterpriseId;
        private String teamId;
        private String apiAppId;
        private String type;
        private List<String> authedUsers;
        private List<String> authedTeams;
        private String eventId;
        private Integer eventTime;

        private transient Event event;
    }

}
