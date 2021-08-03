package com.slack.api.bolt;

import com.google.gson.Gson;
import com.slack.api.Slack;
import com.slack.api.app_backend.SlackSignature;
import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.EventsDispatcher;
import com.slack.api.app_backend.events.EventsDispatcherFactory;
import com.slack.api.app_backend.events.payload.EventsApiPayload;
import com.slack.api.app_backend.interactive_components.payload.BlockActionPayload;
import com.slack.api.bolt.handler.BoltEventHandler;
import com.slack.api.bolt.handler.WebEndpointHandler;
import com.slack.api.bolt.handler.builtin.*;
import com.slack.api.bolt.middleware.Middleware;
import com.slack.api.bolt.middleware.builtin.*;
import com.slack.api.bolt.request.Request;
import com.slack.api.bolt.request.builtin.*;
import com.slack.api.bolt.response.Response;
import com.slack.api.bolt.service.*;
import com.slack.api.bolt.service.builtin.ClientOnlyOAuthStateService;
import com.slack.api.bolt.service.builtin.DefaultOAuthCallbackService;
import com.slack.api.bolt.service.builtin.FileInstallationService;
import com.slack.api.bolt.service.builtin.NullOpenIDConnectNonceService;
import com.slack.api.bolt.service.builtin.oauth.*;
import com.slack.api.bolt.service.builtin.oauth.default_impl.*;
import com.slack.api.bolt.util.ListenerCodeSuggestion;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.auth.AuthTestResponse;
import com.slack.api.model.event.AppUninstalledEvent;
import com.slack.api.model.event.Event;
import com.slack.api.model.event.MessageEvent;
import com.slack.api.model.event.TokensRevokedEvent;
import com.slack.api.util.json.GsonFactory;
import com.slack.api.util.thread.ExecutorServiceFactory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

import static com.slack.api.bolt.util.EventsApiPayloadParser.buildEventPayload;
import static com.slack.api.bolt.util.EventsApiPayloadParser.getEventTypeAndSubtype;

/**
 * A Slack App instance.
 */
@Slf4j
@AllArgsConstructor
@Builder(toBuilder = true) // This builder is used for creating another App instance based on this
public class App {

    /**
     * The default JSON utility.
     */
    private static final Gson GSON = GsonFactory.createSnakeCase();

    /**
     * The given AppConfig in this App.
     */
    private final AppConfig appConfig;

    /**
     * Slack instance and its configurations to be used for API calls.
     */
    private final Slack slack;

    /**
     * The default Web API client without any tokens.
     */
    private final MethodsClient client;

    /**
     * The built-in handy way to run operations asynchronously. It's totally fine to use your own one instead.
     */
    private final ExecutorService executorService;

    // --------------------------------------
    // Middleware
    // --------------------------------------

    /**
     * Registered middleware.
     */
    private List<Middleware> middlewareList;

    protected List<Middleware> buildDefaultMiddlewareList(AppConfig config) {
        List<Middleware> middlewareList = new ArrayList<>();

        // ssl_check (slash command etc)
        if (config.isSslCheckEnabled()) {
            middlewareList.add(new SSLCheck(config.getVerificationToken()));
        }

        // request verification
        if (config.isRequestVerificationEnabled()) {
            // https://api.slack.com/docs/verifying-requests-from-slack
            String signingSecret = config.getSigningSecret();
            if (signingSecret == null || signingSecret.trim().isEmpty()) {
                // This is just a random value to avoid SlackSignature.Generator's initialization error.
                // When this App runs through Socket Mode connections, it skips request signature verification.
                signingSecret = "---";
            }
            SlackSignature.Verifier verifier = new SlackSignature.Verifier(new SlackSignature.Generator(signingSecret));
            RequestVerification requestVerification = new RequestVerification(verifier);
            middlewareList.add(requestVerification);
        }

        // single team authorization
        if (config.isDistributedApp()) {
            middlewareList.add(new MultiTeamsAuthorization(config, installationService));
        } else if (config.getSingleTeamBotToken() != null) {
            try {
                AuthTestResponse initialAuthTest = client().authTest(r -> r.token(config.getSingleTeamBotToken()));
                if (initialAuthTest == null || !initialAuthTest.isOk()) {
                    String error = initialAuthTest != null ? initialAuthTest.getError() : "";
                    String message = "The token is invalid (auth.test error: " + error + ")";
                    throw new IllegalArgumentException(message);
                }
                middlewareList.add(new SingleTeamAuthorization(config, initialAuthTest, installationService));
            } catch (IOException | SlackApiException e) {
                String message = "The token is invalid (error: " + e.getMessage() + ")";
                throw new IllegalArgumentException(message);
            }
        } else {
            log.warn("Skipped adding any authorization middleware - you need to call `app.use(new YourOwnMultiTeamsAuthorization())`");
        }

        // ignoring the events generated by this bot user
        if (config.isIgnoringSelfEventsEnabled()) {
            middlewareList.add(new IgnoringSelfEvents(config.getSlack().getConfig()));
        }

        return middlewareList;
    }

    // --------------------------------------
    // App Status
    // --------------------------------------

    public enum Status {
        Running,
        Stopped
    }

    /**
     * Current status of this App.
     */
    private Status status; // will be initialized in the constructor

    // -------------------------------------
    // Slash Commands
    // https://api.slack.com/interactivity/slash-commands
    // -------------------------------------

    /**
     * Registered slash command handlers.
     */
    private final Map<Pattern, SlashCommandHandler> slashCommandHandlers = new HashMap<>();

    // -------------------------------------
    // Events API
    // https://api.slack.com/events-api
    // -------------------------------------

    /**
     * Registered Event API handlers.
     */
    private final Map<String, BoltEventHandler<Event>> eventHandlers = new HashMap<>();

    /**
     * Primitive Event API handler.
     */
    private final EventsDispatcher eventsDispatcher = EventsDispatcherFactory.getInstance();

    // -------------------------------------
    // Block Kit
    // https://api.slack.com/block-kit
    // -------------------------------------

    /**
     * Registered handlers for block actions (type: "block_actions") in Block Kit.
     */
    private final Map<Pattern, BlockActionHandler> blockActionHandlers = new HashMap<>();
    /**
     * Registered handlers for external data source select options (type: "block_suggestions") in Block Kit.
     */
    private final Map<Pattern, BlockSuggestionHandler> blockSuggestionHandlers = new HashMap<>();

    // -------------------------------------
    // Modal Views
    // https://api.slack.com/surfaces/modals/using
    // -------------------------------------

    /**
     * Registered handlers for modal submissions.
     */
    private final Map<Pattern, ViewSubmissionHandler> viewSubmissionHandlers = new HashMap<>();
    /**
     * Registered handlers for modal cancellations.
     */
    private final Map<Pattern, ViewClosedHandler> viewClosedHandlers = new HashMap<>();

    // -------------------------------------
    // Shortcuts
    // https://api.slack.com/interactivity
    // -------------------------------------

    /**
     * Registered handlers for global shortcuts.
     */
    private final Map<Pattern, GlobalShortcutHandler> globalShortcutHandlers = new HashMap<>();

    /**
     * Registered handlers for message shortcuts (formerly message actions).
     */
    private final Map<Pattern, MessageShortcutHandler> messageShortcutHandlers = new HashMap<>();

    // -------------------------------------
    // Workflow Steps
    // https://api.slack.com/workflows/steps
    // -------------------------------------

    /**
     * Registered handlers for Workflow Steps from Apps.
     */
    private final Map<Pattern, WorkflowStepEditHandler> workflowStepEditHandlers = new HashMap<>();
    private final Map<Pattern, WorkflowStepSaveHandler> workflowStepSaveHandlers = new HashMap<>();
    private final Map<Pattern, WorkflowStepExecuteHandler> workflowStepExecuteHandlers = new HashMap<>();

    // -------------------------------------
    // Attachments
    // https://api.slack.com/messaging/composing/layouts#attachments
    // -------------------------------------

    /**
     * Registered attachment action (type: "interactive_message"") handlers.
     */
    private final Map<Pattern, AttachmentActionHandler> attachmentActionHandlers = new HashMap<>();

    // -------------------------------------
    // Dialogs
    // https://api.slack.com/dialogs
    // -------------------------------------

    /**
     * Registered handlers for dialog submissions.
     */
    private final Map<Pattern, DialogSubmissionHandler> dialogSubmissionHandlers = new HashMap<>();
    /**
     * Registered handlers for external data source select options in dialogs.
     */
    private final Map<Pattern, DialogSuggestionHandler> dialogSuggestionHandlers = new HashMap<>();
    /**
     * Registered handlers for dialog cancellations.
     */
    private final Map<Pattern, DialogCancellationHandler> dialogCancellationHandlers = new HashMap<>();

    // -------------------------------------
    // Installation information / OAuth access tokens
    // -------------------------------------

    private InstallationService installationService; // will be initialized in the constructor

    private DefaultTokensRevokedEventHandler tokensRevokedEventHandler; // will be initialized in the constructor
    private DefaultAppUninstalledEventHandler appUninstalledEventHandler; // will be initialized in the constructor

    public BoltEventHandler<TokensRevokedEvent> defaultTokensRevokedEventHandler() {
        return tokensRevokedEventHandler;
    }

    public BoltEventHandler<AppUninstalledEvent> defaultAppUninstalledEventHandler() {
        return appUninstalledEventHandler;
    }

    public App enableTokenRevocationHandlers() {
        this.event(TokensRevokedEvent.class, defaultTokensRevokedEventHandler());
        this.event(AppUninstalledEvent.class, defaultAppUninstalledEventHandler());
        return this;
    }

    // -------------------------------------
    // OAuth Flow
    // -------------------------------------

    private OAuthStateService oAuthStateService; // will be initialized in the constructor
    private OpenIDConnectNonceService openIDConnectNonceService; // will be initialized in the constructor
    private OAuthSuccessHandler oAuthSuccessHandler; // will be initialized in the constructor
    private OAuthV2SuccessHandler oAuthV2SuccessHandler; // will be initialized in the constructor
    private OpenIDConnectSuccessHandler openIDConnectSuccessHandler; // will be initialized in the constructor
    private OAuthErrorHandler oAuthErrorHandler; // will be initialized in the constructor
    private OAuthAccessErrorHandler oAuthAccessErrorHandler; // will be initialized in the constructor
    private OAuthV2AccessErrorHandler oAuthV2AccessErrorHandler; // will be initialized in the constructor
    private OpenIDConnectErrorHandler openIDConnectErrorHandler; // will be initialized in the constructor
    private OAuthStateErrorHandler oAuthStateErrorHandler; // will be initialized in the constructor
    private OAuthExceptionHandler oAuthExceptionHandler; // will be initialized in the constructor

    private OAuthCallbackService oAuthCallbackService; // will be initialized by initOAuthServicesIfNecessary()

    private void initOAuthServicesIfNecessary() {
        if (appConfig.isDistributedApp() && appConfig.isOAuthRedirectUriPathEnabled()) {
            if (this.oAuthCallbackService == null) {
                this.oAuthCallbackService = new DefaultOAuthCallbackService(
                        config(),
                        oAuthStateService,
                        oAuthSuccessHandler,
                        oAuthV2SuccessHandler,
                        oAuthErrorHandler,
                        oAuthStateErrorHandler,
                        oAuthAccessErrorHandler,
                        oAuthV2AccessErrorHandler,
                        oAuthExceptionHandler,
                        openIDConnectSuccessHandler,
                        openIDConnectErrorHandler
                );
            }
        }
    }

    /**
     * Get the Slack URL for beginning the OAuth flow, including the query
     * params necessary to identify this application to Slack.
     * <p>
     * Appends the optional `redirect_uri` query param based on the provided
     * AppConfig to ensure that the correct OAuth redirect URI is selected in
     * cases where a Slack application may have multiple redirect URIs
     * associated with it.
     *
     * @param state The OAuth state param
     * @return The Slack URL to redirect users to for beginning the OAuth flow
     */
    public String buildAuthorizeUrl(String state) {
        return buildAuthorizeUrl(state, null);
    }

    /**
     * @param state The OAuth state param
     * @param nonce The OAUth nonce param
     * @return
     */
    public String buildAuthorizeUrl(String state, String nonce) {
        AppConfig config = config();

        if (config.getClientId() == null
                // OpenID Connect does not use require the bot scopes
                || (!config.isOpenIDConnectEnabled() && config.getScope() == null)
                || state == null) {
            return null;
        }

        String scope = config.getScope() == null ? "" : config.getScope();
        String redirectUriParam = redirectUriQueryParam(appConfig);

        if (config.isClassicAppPermissionsEnabled()) {
            // https://api.slack.com/authentication/migration
            return "https://slack.com/oauth/authorize" +
                    "?client_id=" + config.getClientId() +
                    "&scope=" + scope +
                    "&state=" + state +
                    redirectUriParam;
        } else if (config.isOpenIDConnectEnabled()) {
            return "https://slack.com/openid/connect/authorize" +
                    "?client_id=" + config.getClientId() +
                    "&response_type=code" +
                    "&scope=" + (config.getUserScope() != null ? config.getUserScope() : scope) +
                    "&state=" + state +
                    redirectUriParam +
                    // The nonce parameter is an optional one in the OpenID Connect Spec
                    // https://openid.net/specs/openid-connect-core-1_0.html#AuthRequest
                    (nonce != null ? "&nonce=" + nonce : "");
        } else {
            String userScope = config.getUserScope() == null ? "" : config.getUserScope();
            return "https://slack.com/oauth/v2/authorize" +
                    "?client_id=" + config.getClientId() +
                    "&scope=" + scope +
                    "&user_scope=" + userScope +
                    "&state=" + state +
                    redirectUriParam;
        }
    }

    /**
     * Use #buildAuthorizeUrl(String) instead (this method will be removed in v2.0)
     */
    @Deprecated
    public String getOauthInstallationUrl(String state) {
        return buildAuthorizeUrl(state);
    }

    private String redirectUriQueryParam(AppConfig appConfig) {
        if (appConfig.getRedirectUri() == null) {
            return "";
        }

        try {
            String urlEncodedRedirectUri = URLEncoder.encode(
                    appConfig.getRedirectUri(),
                    StandardCharsets.UTF_8.name()
            );

            return String.format("&redirect_uri=%s", urlEncodedRedirectUri);
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    // -------------------------------------
    // Additional Web Endpoints
    // -------------------------------------

    private final Map<WebEndpoint, WebEndpointHandler> webEndpointHandlers = new HashMap<>();

    public Map<WebEndpoint, WebEndpointHandler> getWebEndpointHandlers() {
        return webEndpointHandlers;
    }

    // --------------------------------------
    // constructors
    // --------------------------------------

    public App() {
        this(new AppConfig());
    }

    public App(AppConfig appConfig) {
        this(appConfig, null);
        asOAuthApp(false); // disabled by default as the default mode would be for single team app
    }

    public App(AppConfig appConfig, List<Middleware> middlewareList) {
        this(appConfig, appConfig.getSlack() != null ? appConfig.getSlack() : Slack.getInstance(), middlewareList);
        this.status = Status.Stopped;
    }

    public App(AppConfig appConfig, Slack slack, List<Middleware> middlewareList) {
        this.appConfig = appConfig;
        this.slack = slack;
        this.client = this.slack.methods();
        this.executorService = ExecutorServiceFactory.createDaemonThreadPoolExecutor(
                "bolt-app-threads",
                this.appConfig.getThreadPoolSize());
        this.middlewareList = middlewareList;

        this.oAuthStateService = new ClientOnlyOAuthStateService();
        this.openIDConnectNonceService = new NullOpenIDConnectNonceService();

        this.installationService = new FileInstallationService(this.appConfig);
        this.tokensRevokedEventHandler = new DefaultTokensRevokedEventHandler(this.installationService, this.executorService);
        this.appUninstalledEventHandler = new DefaultAppUninstalledEventHandler(this.installationService, this.executorService);
        this.oAuthSuccessHandler = new OAuthDefaultSuccessHandler(this.appConfig, this.installationService);
        this.oAuthV2SuccessHandler = new OAuthV2DefaultSuccessHandler(config(), this.installationService);
        this.openIDConnectSuccessHandler = (request, response, apiResponse) -> {
            log.warn("This OpenIDConnectSuccessHandler does nothing. " +
                    "Implement your own handler and register it " +
                    "by calling App#openIDConnectSuccess(handler)");
            return response;
        };

        this.oAuthErrorHandler = new OAuthDefaultErrorHandler(config());
        this.oAuthAccessErrorHandler = new OAuthDefaultAccessErrorHandler(config());
        this.oAuthV2AccessErrorHandler = new OAuthV2DefaultAccessErrorHandler(config());
        this.openIDConnectErrorHandler = new OpenIDConnectDefaultErrorHandler(config());
        this.oAuthStateErrorHandler = new OAuthDefaultStateErrorHandler(config());
        this.oAuthExceptionHandler = new OAuthDefaultExceptionHandler(config());

        this.oAuthCallbackService = null; // will be initialized by initOAuthServicesIfNecessary()
    }

    // --------------------------------------
    // public methods
    // --------------------------------------

    public AppConfig config() {
        return this.appConfig;
    }

    public Slack slack() {
        return this.slack;
    }

    public MethodsClient client() {
        return this.client;
    }

    /**
     * The built-in handy way to run operations asynchronously. It's totally fine to use your own one instead.
     */
    public ExecutorService executorService() {
        return this.executorService;
    }

    // ----------------------
    // App Status and Initializers

    public App.Status status() {
        return this.status;
    }

    private final Map<String, Initializer> componentNameToInitializer = new HashMap<>();

    private void putServiceInitializer(Class<? extends Service> clazz, Initializer initializer) {
        String canonicalName = clazz.getCanonicalName();
        this.initializer(canonicalName, initializer);
    }

    public App initializer(String name, Initializer initializer) {
        componentNameToInitializer.put(name, initializer);
        return this;
    }

    public void initialize() {
        initOAuthServicesIfNecessary();
        if (neverStarted.get() && config().isAppInitializersEnabled()) {
            for (Initializer initializer : componentNameToInitializer.values()) {
                initializer.accept(this);
            }
        }
    }

    private final AtomicBoolean neverStarted = new AtomicBoolean(true);

    public App start() {
        synchronized (status) {
            if (status == Status.Stopped) {
                if (middlewareList == null) {
                    middlewareList = buildDefaultMiddlewareList(appConfig);
                }
                initialize();

                if (!this.eventsDispatcher.isEmpty()) {
                    this.eventsDispatcher.start();
                }
                neverStarted.set(false);
            }
            status = Status.Running;
        }
        return this;
    }

    public App stop() {
        synchronized (status) {
            if (status == Status.Running) {
                if (this.eventsDispatcher.isRunning()) {
                    this.eventsDispatcher.stop();
                }
            }
            status = Status.Stopped;
        }
        return this;
    }

    public Response run(Request request) throws Exception {
        if (request == null || request.getContext() == null) {
            return Response.builder().statusCode(400).body("Invalid Request").build();
        }
        request.getContext().setSlack(this.slack); // use the properly configured API client

        if (neverStarted.get()) {
            start();
        }
        LinkedList<Middleware> remaining = new LinkedList<>(middlewareList);
        if (remaining.isEmpty()) {
            return runHandler(request);
        } else {
            Middleware firstMiddleware = remaining.pop();
            return runMiddleware(request, Response.ok(), firstMiddleware, remaining);
        }
    }

    // ----------------------
    // Middleware registration methods

    public App use(Middleware middleware) {
        if (this.middlewareList == null) {
            this.middlewareList = buildDefaultMiddlewareList(config());
        }
        this.middlewareList.add(middleware);
        return this;
    }

    // ----------------------
    // App routing methods

    // -------------
    // Events API
    // https://api.slack.com/events-api

    public <E extends Event> App event(Class<E> eventClass, BoltEventHandler<E> handler) {
        String eventTypeAndSubtype = getEventTypeAndSubtype(eventClass);
        if (eventTypeAndSubtype == null) {
            throw new IllegalArgumentException("Unexpectedly failed to register the handler");
        }
        if (eventHandlers.get(eventTypeAndSubtype) != null) {
            log.warn("Replaced the handler for {}", eventTypeAndSubtype);
        }
        eventHandlers.put(eventTypeAndSubtype, (BoltEventHandler<Event>) handler);
        return this;
    }

    public App event(EventHandler<?> handler) {
        eventsDispatcher.register(handler);
        return this;
    }

    public App message(String pattern, BoltEventHandler<MessageEvent> messageHandler) {
        return message(Pattern.compile("^.*" + Pattern.quote(pattern) + ".*$"), messageHandler);
    }

    public App message(Pattern pattern, BoltEventHandler<MessageEvent> messageHandler) {
        event(MessageEvent.class, (event, ctx) -> {
            String text = event.getEvent().getText();
            if (log.isDebugEnabled()) {
                log.debug("Run a message event handler (pattern: {}, text: {})", pattern, text);
            }
            if (text != null && pattern.matcher(text).matches()) {
                return messageHandler.apply(event, ctx);
            } else {
                return ctx.ack();
            }
        });
        return this;
    }

    // -------------
    // Slash Commands
    // https://api.slack.com/interactivity/slash-commands

    public App command(String command, SlashCommandHandler handler) {
        return command(Pattern.compile("^" + Pattern.quote(command) + "$"), handler);
    }

    public App command(Pattern command, SlashCommandHandler handler) {
        if (slashCommandHandlers.get(command) != null) {
            log.warn("Replaced the handler for {}", command);
        }
        slashCommandHandlers.put(command, handler);
        return this;
    }

    // -------------
    // Block Kit
    // https://api.slack.com/block-kit

    public App blockAction(String actionId, BlockActionHandler handler) {
        return blockAction(Pattern.compile("^" + Pattern.quote(actionId) + "$"), handler);
    }

    public App blockAction(Pattern actionId, BlockActionHandler handler) {
        if (blockActionHandlers.get(actionId) != null) {
            log.warn("Replaced the handler for {}", actionId);
        }
        blockActionHandlers.put(actionId, handler);
        return this;
    }

    public App blockSuggestion(String actionId, BlockSuggestionHandler handler) {
        return blockSuggestion(Pattern.compile("^" + Pattern.quote(actionId) + "$"), handler);
    }

    public App blockSuggestion(Pattern actionId, BlockSuggestionHandler handler) {
        if (blockSuggestionHandlers.get(actionId) != null) {
            log.warn("Replaced the handler for {}", actionId);
        }
        blockSuggestionHandlers.put(actionId, handler);
        return this;
    }

    // -------------
    // Shortcuts
    // https://api.slack.com/interactivity
    // https://api.slack.com/interactivity/actions

    public App globalShortcut(String callbackId, GlobalShortcutHandler handler) {
        return globalShortcut(Pattern.compile("^" + Pattern.quote(callbackId) + "$"), handler);
    }

    public App globalShortcut(Pattern callbackId, GlobalShortcutHandler handler) {
        if (globalShortcutHandlers.get(callbackId) != null) {
            log.warn("Replaced the handler for {}", callbackId);
        }
        globalShortcutHandlers.put(callbackId, handler);
        return this;
    }

    public App messageShortcut(String callbackId, MessageShortcutHandler handler) {
        return messageShortcut(Pattern.compile("^" + Pattern.quote(callbackId) + "$"), handler);
    }

    public App messageShortcut(Pattern callbackId, MessageShortcutHandler handler) {
        if (messageShortcutHandlers.get(callbackId) != null) {
            log.warn("Replaced the handler for {}", callbackId);
        }
        messageShortcutHandlers.put(callbackId, handler);
        return this;
    }

    // -------------
    // Modal Views
    // https://api.slack.com/surfaces/modals/using

    public App viewSubmission(String callbackId, ViewSubmissionHandler handler) {
        return viewSubmission(Pattern.compile("^" + Pattern.quote(callbackId) + "$"), handler);
    }

    public App viewSubmission(Pattern callbackId, ViewSubmissionHandler handler) {
        if (viewSubmissionHandlers.get(callbackId) != null) {
            log.warn("Replaced the handler for {}", callbackId);
        }
        viewSubmissionHandlers.put(callbackId, handler);
        return this;
    }

    public App viewClosed(String callbackId, ViewClosedHandler handler) {
        return viewClosed(Pattern.compile("^" + Pattern.quote(callbackId) + "$"), handler);
    }

    public App viewClosed(Pattern callbackId, ViewClosedHandler handler) {
        if (viewClosedHandlers.get(callbackId) != null) {
            log.warn("Replaced the handler for {}", callbackId);
        }
        viewClosedHandlers.put(callbackId, handler);
        return this;
    }

    // -------------
    // Workflows: Steps from Apps
    // https://api.slack.com/workflows/steps

    public App step(WorkflowStep step) {
        return this.use(step);
    }

    public App workflowStepEdit(String callbackId, WorkflowStepEditHandler handler) {
        return workflowStepEdit(Pattern.compile("^" + Pattern.quote(callbackId) + "$"), handler);
    }

    public App workflowStepEdit(Pattern callbackId, WorkflowStepEditHandler handler) {
        if (workflowStepEditHandlers.get(callbackId) != null) {
            log.warn("Replaced the handler for {}", callbackId);
        }
        workflowStepEditHandlers.put(callbackId, handler);
        return this;
    }

    public App workflowStepSave(String callbackId, WorkflowStepSaveHandler handler) {
        return workflowStepSave(Pattern.compile("^" + Pattern.quote(callbackId) + "$"), handler);
    }

    public App workflowStepSave(Pattern callbackId, WorkflowStepSaveHandler handler) {
        if (workflowStepSaveHandlers.get(callbackId) != null) {
            log.warn("Replaced the handler for {}", callbackId);
        }
        workflowStepSaveHandlers.put(callbackId, handler);
        return this;
    }

    public App workflowStepExecute(String pattern, WorkflowStepExecuteHandler handler) {
        return workflowStepExecute(Pattern.compile("^.*" + Pattern.quote(pattern) + ".*$"), handler);
    }

    public App workflowStepExecute(Pattern pattern, WorkflowStepExecuteHandler handler) {
        if (workflowStepExecuteHandlers.get(pattern) != null) {
            log.warn("Replaced the handler for {}", pattern);
        }
        workflowStepExecuteHandlers.put(pattern, handler);
        return this;
    }

    // -------------
    // Attachments
    // https://api.slack.com/messaging/composing/layouts#attachments

    public App attachmentAction(String callbackId, AttachmentActionHandler handler) {
        return attachmentAction(Pattern.compile("^" + Pattern.quote(callbackId) + "$"), handler);
    }

    public App attachmentAction(Pattern callbackId, AttachmentActionHandler handler) {
        if (attachmentActionHandlers.get(callbackId) != null) {
            log.warn("Replaced the handler for {}", callbackId);
        }
        attachmentActionHandlers.put(callbackId, handler);
        return this;
    }

    // -------------
    // Dialogs
    // https://api.slack.com/dialogs

    public App dialogSubmission(String callbackId, DialogSubmissionHandler handler) {
        return dialogSubmission(Pattern.compile("^" + Pattern.quote(callbackId) + "$"), handler);
    }

    public App dialogSubmission(Pattern callbackId, DialogSubmissionHandler handler) {
        if (dialogSubmissionHandlers.get(callbackId) != null) {
            log.warn("Replaced the handler for {}", callbackId);
        }
        dialogSubmissionHandlers.put(callbackId, handler);
        return this;
    }

    public App dialogSuggestion(String callbackId, DialogSuggestionHandler handler) {
        return dialogSuggestion(Pattern.compile("^" + Pattern.quote(callbackId) + "$"), handler);
    }

    public App dialogSuggestion(Pattern callbackId, DialogSuggestionHandler handler) {
        if (dialogSuggestionHandlers.get(callbackId) != null) {
            log.warn("Replaced the handler for {}", callbackId);
        }
        dialogSuggestionHandlers.put(callbackId, handler);
        return this;
    }

    public App dialogCancellation(String callbackId, DialogCancellationHandler handler) {
        return dialogCancellation(Pattern.compile("^" + Pattern.quote(callbackId) + "$"), handler);
    }

    public App dialogCancellation(Pattern callbackId, DialogCancellationHandler handler) {
        if (dialogCancellationHandlers.get(callbackId) != null) {
            log.warn("Replaced the handler for {}", callbackId);
        }
        dialogCancellationHandlers.put(callbackId, handler);
        return this;
    }

    // ----------------------
    // OAuth App configuration methods

    public App asOAuthApp(boolean enabled) {
        config().setOAuthInstallPathEnabled(enabled);
        config().setOAuthRedirectUriPathEnabled(enabled);
        return this;
    }

    public App asOpenIDConnectApp(boolean enabled) {
        config().setOpenIDConnectEnabled(enabled);
        this.asOAuthApp(enabled);
        return this;
    }

    public App service(OAuthCallbackService oAuthCallbackService) {
        this.oAuthCallbackService = oAuthCallbackService;
        putServiceInitializer(OAuthCallbackService.class, oAuthCallbackService.initializer());
        return this;
    }

    public App service(OAuthStateService oAuthStateService) {
        this.oAuthStateService = oAuthStateService;
        putServiceInitializer(OAuthStateService.class, oAuthStateService.initializer());
        return this;
    }

    public App service(OpenIDConnectNonceService openIDConnectNonceService) {
        this.openIDConnectNonceService = openIDConnectNonceService;
        putServiceInitializer(OpenIDConnectNonceService.class, openIDConnectNonceService.initializer());
        return this;
    }

    public App service(InstallationService installationService) {
        this.installationService = installationService;
        this.tokensRevokedEventHandler = new DefaultTokensRevokedEventHandler(this.installationService, this.executorService);
        this.appUninstalledEventHandler = new DefaultAppUninstalledEventHandler(this.installationService, this.executorService);
        putServiceInitializer(InstallationService.class, installationService.initializer());
        if (config().isClassicAppPermissionsEnabled()) {
            return oauthCallback(new OAuthDefaultSuccessHandler(config(), installationService));
        } else {
            return oauthCallback(new OAuthV2DefaultSuccessHandler(config(), installationService));
        }
    }

    public App oauthCallback(OAuthSuccessHandler handler) {
        oAuthSuccessHandler = handler;
        return this;
    }

    public App oauthCallback(OAuthV2SuccessHandler handler) {
        oAuthV2SuccessHandler = handler;
        return this;
    }

    public App oauthCallbackError(OAuthErrorHandler handler) {
        oAuthErrorHandler = handler;
        return this;
    }

    public App oauthCallbackStateError(OAuthStateErrorHandler handler) {
        oAuthStateErrorHandler = handler;
        return this;
    }

    public App oauthCallbackAccessError(OAuthAccessErrorHandler handler) {
        oAuthAccessErrorHandler = handler;
        return this;
    }

    public App oauthCallbackAccessError(OAuthV2AccessErrorHandler handler) {
        oAuthV2AccessErrorHandler = handler;
        return this;
    }

    public App openIDConnectSuccess(OpenIDConnectSuccessHandler handler) {
        openIDConnectSuccessHandler = handler;
        return this;
    }

    public App openIDConnectError(OpenIDConnectErrorHandler handler) {
        openIDConnectErrorHandler = handler;
        return this;
    }

    public App oauthCallbackException(OAuthExceptionHandler handler) {
        oAuthExceptionHandler = handler;
        return this;
    }

    @Deprecated
    public App toOAuthStartApp() {
        return toOAuthInstallPathEnabledApp();
    }

    public App toOAuthInstallPathEnabledApp() {
        App newApp = toBuilder().appConfig(config().toBuilder().build()).build();
        newApp.config().setOAuthInstallPathEnabled(true);
        newApp.config().setOAuthRedirectUriPathEnabled(false);
        return newApp;
    }

    @Deprecated
    public App toOAuthCallbackApp() {
        return toOAuthRedirectUriPathEnabledApp();
    }

    public App toOAuthRedirectUriPathEnabledApp() {
        App newApp = toBuilder().appConfig(config().toBuilder().build()).build();
        newApp.config().setOAuthInstallPathEnabled(false);
        newApp.config().setOAuthRedirectUriPathEnabled(true);
        return newApp;
    }

    // ----------------------
    // Additional endpoint support (e.g., health check endpoints)

    public App endpoint(String path, WebEndpointHandler handler) {
        return endpoint(WebEndpoint.Method.GET, path, handler);
    }

    public App endpoint(String method, String path, WebEndpointHandler handler) {
        return endpoint(WebEndpoint.Method.valueOf(method), path, handler);
    }

    public App endpoint(WebEndpoint.Method method, String path, WebEndpointHandler handler) {
        webEndpointHandlers.put(new WebEndpoint(method, path), handler);
        return this;
    }

    // --------------------------------------
    // Internal Runner Methods
    // --------------------------------------

    // recursively runs the remaining middleware
    protected Response runMiddleware(
            Request request,
            Response response,
            Middleware current,
            LinkedList<Middleware> remaining) throws Exception {
        if (log.isDebugEnabled()) {
            String middlewareName = current.getClass().getCanonicalName();
            if (middlewareName == null) {
                // In Kotlin, `app.use { req, resp, chain -> chain.next(req) }` doesn't have its class name here
                middlewareName = current.toString();
            }
            log.debug("Applying a middleware (name: {})", middlewareName);
        }
        if (remaining.isEmpty()) {
            return current.apply(request, response, (req) -> runHandler(req));
        } else {
            Middleware next = remaining.pop();
            return current.apply(request, response, (req) -> runMiddleware(request, response, next, remaining));
        }
    }

    protected Response runHandler(Request slackRequest) throws IOException, SlackApiException {
        if (log.isDebugEnabled()) {
            log.debug("The handler started (request type: {})", slackRequest.getRequestType());
        }
        try {
            switch (slackRequest.getRequestType()) {
                case OAuthStart: {
                    if (config().isDistributedApp()) {
                        try {
                            Map<String, List<String>> responseHeaders = new HashMap<>();
                            Response response = Response.builder().statusCode(200).headers(responseHeaders).build();
                            String state = oAuthStateService.issueNewState(slackRequest, response);
                            String nonce = openIDConnectNonceService.issueNewNonce(slackRequest, response);
                            String authorizeUrl = buildAuthorizeUrl(state, nonce);
                            if (authorizeUrl == null) {
                                log.error("App#buildAuthorizeUrl(String) returned null due to some missing settings");
                                if (config().getOauthCancellationUrl() == null) {
                                    response.setContentType("text/html; charset=utf-8");
                                    String installPath = config().getOauthInstallRequestURI();
                                    String html = config().getOAuthRedirectUriPageRenderer()
                                            .renderFailurePage(installPath, "invalid_app_config");
                                    response.setBody(html);
                                } else {
                                    response.setStatusCode(302);
                                    responseHeaders.put("Location", Arrays.asList(config().getOauthCancellationUrl()));
                                }
                            } else {
                                if (config().isOAuthInstallPageRenderingEnabled()) {
                                    response.setStatusCode(200);
                                    response.setContentType("text/html; charset=utf-8");
                                    response.setBody(config().getOAuthInstallPageRenderer().render(authorizeUrl));
                                } else {
                                    //As with v1.0 - 1.3, this directly sends the installer to Slack's authorize URL
                                    response.setStatusCode(302);
                                    responseHeaders.put("Location", Arrays.asList(authorizeUrl));
                                }
                            }
                            return response;
                        } catch (Exception e) {
                            log.error("Failed to run the operation (error: {})", e.getMessage(), e);
                        }
                    }
                    log.warn("Skipped to handle an OAuth callback request as this Bolt app is not ready for it");
                    return Response.builder().statusCode(500).body("something wrong").build();
                }
                case OAuthCallback: {
                    if (config().isDistributedApp()) {
                        if (oAuthCallbackService != null) {
                            OAuthCallbackRequest request = (OAuthCallbackRequest) slackRequest;
                            return oAuthCallbackService.handle(request);
                        }
                    }
                    log.warn("Skipped to handle an OAuth callback request as this Bolt app is not ready for it");
                    return Response.builder().statusCode(500).body("something wrong").build();
                }
                case Command: {
                    SlashCommandRequest request = (SlashCommandRequest) slackRequest;
                    String command = request.getPayload().getCommand();
                    if (command != null) {
                        for (Pattern pattern : slashCommandHandlers.keySet()) {
                            if (pattern.matcher(command).matches()) {
                                SlashCommandHandler handler = slashCommandHandlers.get(pattern);
                                return handler.apply(request, request.getContext());
                            }
                        }
                    }
                    command = request.getPayload().getCommand();
                    log.warn("No SlashCommandHandler registered for command: {}\n{}",
                            command, ListenerCodeSuggestion.command(command));
                    break;
                }
                case Event: {
                    if (eventsDispatcher.isRunning()) {
                        eventsDispatcher.enqueue(slackRequest.getRequestBodyAsString());
                        return Response.ok();
                    }
                    EventRequest request = (EventRequest) slackRequest;
                    BoltEventHandler<Event> handler = eventHandlers.get(request.getEventTypeAndSubtype());
                    if (handler != null) {
                        EventsApiPayload<Event> payload = buildEventPayload(request);
                        return handler.apply(payload, request.getContext());
                    }
                    log.warn("No BoltEventHandler registered for event: {}\n{}",
                            request.getEventTypeAndSubtype(), ListenerCodeSuggestion.event(request.getEventTypeAndSubtype()));
                    break;
                }
                case UrlVerification: {
                    // https://api.slack.com/events/url_verification
                    return Response.builder()
                            .statusCode(200)
                            .contentType("text/plain")
                            .body(((UrlVerificationRequest) slackRequest).getChallenge())
                            .build();
                }
                case AttachmentAction: {
                    AttachmentActionRequest request = (AttachmentActionRequest) slackRequest;
                    String callbackId = request.getPayload().getCallbackId();
                    if (callbackId != null) {
                        for (Pattern pattern : attachmentActionHandlers.keySet()) {
                            if (pattern.matcher(callbackId).matches()) {
                                AttachmentActionHandler handler = attachmentActionHandlers.get(pattern);
                                return handler.apply(request, request.getContext());
                            }
                        }
                    }
                    callbackId = request.getPayload().getCallbackId();
                    log.warn("No AttachmentActionHandler registered for callback_id: {}\n{}",
                            callbackId, ListenerCodeSuggestion.attachmentAction(callbackId));
                    break;
                }
                case BlockAction: {
                    BlockActionRequest request = (BlockActionRequest) slackRequest;
                    List<BlockActionPayload.Action> actions = request.getPayload().getActions();
                    if (actions == null) {
                        return Response.json(400, "{\"error\":\"No `actions` property found\"}");
                    }
                    if (actions.size() == 1) {
                        String actionId = actions.get(0).getActionId();
                        if (actionId != null) {
                            for (Pattern pattern : blockActionHandlers.keySet()) {
                                if (pattern.matcher(actionId).matches()) {
                                    BlockActionHandler handler = blockActionHandlers.get(pattern);
                                    return handler.apply(request, request.getContext());
                                }
                            }
                        }
                        actionId = actions.get(0).getActionId();
                        log.warn("No BlockActionHandler registered for action_id: {}\n{}",
                                actionId, ListenerCodeSuggestion.blockAction(actionId));
                    } else {
                        for (BlockActionPayload.Action action : request.getPayload().getActions()) {
                            // Returned response values will be ignored
                            if (action != null && action.getActionId() != null) {
                                blockActionHandlers.get(action.getActionId());
                            }
                        }
                    }
                    break;
                }
                case BlockSuggestion: {
                    BlockSuggestionRequest request = (BlockSuggestionRequest) slackRequest;
                    String actionId = request.getPayload().getActionId();
                    if (actionId != null) {
                        for (Pattern pattern : blockSuggestionHandlers.keySet()) {
                            if (pattern.matcher(actionId).matches()) {
                                BlockSuggestionHandler handler = blockSuggestionHandlers.get(pattern);
                                return handler.apply(request, request.getContext());
                            }
                        }
                    }
                    log.warn("No BlockSuggestionHandler registered for action_id: {}\n{}",
                            actionId, ListenerCodeSuggestion.blockSuggestion(actionId));
                    break;
                }
                case GlobalShortcut: {
                    GlobalShortcutRequest request = (GlobalShortcutRequest) slackRequest;
                    String callbackId = request.getPayload().getCallbackId();
                    if (callbackId != null) {
                        for (Pattern pattern : globalShortcutHandlers.keySet()) {
                            if (pattern.matcher(callbackId).matches()) {
                                GlobalShortcutHandler handler = globalShortcutHandlers.get(pattern);
                                return handler.apply(request, request.getContext());
                            }
                        }
                    }
                    log.warn("No GlobalShortcutHandler registered for callback_id: {}\n{}",
                            callbackId, ListenerCodeSuggestion.globalShortcut(callbackId));
                    break;
                }
                case MessageShortcut: {
                    MessageShortcutRequest request = (MessageShortcutRequest) slackRequest;
                    String callbackId = request.getPayload().getCallbackId();
                    if (callbackId != null) {
                        for (Pattern pattern : messageShortcutHandlers.keySet()) {
                            if (pattern.matcher(callbackId).matches()) {
                                MessageShortcutHandler handler = messageShortcutHandlers.get(pattern);
                                return handler.apply(request, request.getContext());
                            }
                        }
                    }
                    log.warn("No MessageShortcutHandler registered for callback_id: {}\n{}",
                            callbackId, ListenerCodeSuggestion.messageShortcut(callbackId));
                    break;
                }
                case DialogSubmission: {
                    DialogSubmissionRequest request = (DialogSubmissionRequest) slackRequest;
                    String callbackId = request.getPayload().getCallbackId();
                    if (callbackId != null) {
                        for (Pattern pattern : dialogSubmissionHandlers.keySet()) {
                            if (pattern.matcher(callbackId).matches()) {
                                DialogSubmissionHandler handler = dialogSubmissionHandlers.get(pattern);
                                return handler.apply(request, request.getContext());
                            }
                        }
                    }
                    log.warn("No DialogSubmissionHandler registered for callback_id: {}\n{}",
                            callbackId, ListenerCodeSuggestion.dialogSubmission(callbackId));
                    break;
                }
                case DialogCancellation: {
                    DialogCancellationRequest request = (DialogCancellationRequest) slackRequest;
                    String callbackId = request.getPayload().getCallbackId();
                    if (callbackId != null) {
                        for (Pattern pattern : dialogCancellationHandlers.keySet()) {
                            if (pattern.matcher(callbackId).matches()) {
                                DialogCancellationHandler handler = dialogCancellationHandlers.get(pattern);
                                return handler.apply(request, request.getContext());
                            }
                        }
                    }
                    log.warn("No DialogCancellationHandler registered for callback_id: {}\n{}",
                            callbackId, ListenerCodeSuggestion.dialogCancellation(callbackId));
                    break;
                }
                case DialogSuggestion: {
                    DialogSuggestionRequest request = (DialogSuggestionRequest) slackRequest;
                    String callbackId = request.getPayload().getCallbackId();
                    if (callbackId != null) {
                        for (Pattern pattern : dialogSuggestionHandlers.keySet()) {
                            if (pattern.matcher(callbackId).matches()) {
                                DialogSuggestionHandler handler = dialogSuggestionHandlers.get(pattern);
                                return handler.apply(request, request.getContext());
                            }
                        }
                    }
                    log.warn("No DialogSuggestionHandler registered for callback_id: {}\n{}",
                            callbackId, ListenerCodeSuggestion.dialogSuggestion(callbackId));
                    break;
                }
                case ViewSubmission: {
                    ViewSubmissionRequest request = (ViewSubmissionRequest) slackRequest;
                    String callbackId = request.getPayload().getView().getCallbackId();
                    if (callbackId != null) {
                        for (Pattern pattern : viewSubmissionHandlers.keySet()) {
                            if (pattern.matcher(callbackId).matches()) {
                                ViewSubmissionHandler handler = viewSubmissionHandlers.get(pattern);
                                return handler.apply(request, request.getContext());
                            }
                        }
                    }
                    log.warn("No ViewSubmissionHandler registered for callback_id: {}\n{}",
                            callbackId, ListenerCodeSuggestion.viewSubmission(callbackId));
                    break;
                }
                case ViewClosed: {
                    ViewClosedRequest request = (ViewClosedRequest) slackRequest;
                    String callbackId = request.getPayload().getView().getCallbackId();
                    if (callbackId != null) {
                        for (Pattern pattern : viewClosedHandlers.keySet()) {
                            if (pattern.matcher(callbackId).matches()) {
                                ViewClosedHandler handler = viewClosedHandlers.get(pattern);
                                return handler.apply(request, request.getContext());
                            }
                        }
                    }
                    log.warn("No ViewClosedHandler registered for callback_id: {}\n{}",
                            callbackId, ListenerCodeSuggestion.viewClosed(callbackId));
                    break;
                }
                case WorkflowStepEdit: {
                    WorkflowStepEditRequest request = (WorkflowStepEditRequest) slackRequest;
                    String callbackId = request.getPayload().getCallbackId();
                    if (callbackId != null) {
                        for (Pattern pattern : workflowStepEditHandlers.keySet()) {
                            if (pattern.matcher(callbackId).matches()) {
                                WorkflowStepEditHandler handler = workflowStepEditHandlers.get(pattern);
                                return handler.apply(request, request.getContext());
                            }
                        }
                    }
                    log.warn("No WorkflowStepEditHandler registered for callback_id: {}\n{}",
                            callbackId, ListenerCodeSuggestion.WORKFLOW_STEP);
                    break;
                }
                case WorkflowStepSave: {
                    WorkflowStepSaveRequest request = (WorkflowStepSaveRequest) slackRequest;
                    String callbackId = request.getPayload().getView().getCallbackId();
                    if (callbackId != null) {
                        for (Pattern pattern : workflowStepSaveHandlers.keySet()) {
                            if (pattern.matcher(callbackId).matches()) {
                                WorkflowStepSaveHandler handler = workflowStepSaveHandlers.get(pattern);
                                return handler.apply(request, request.getContext());
                            }
                        }
                    }
                    log.warn("No WorkflowStepSaveHandler registered for callback_id: {}\n{}",
                            callbackId, ListenerCodeSuggestion.WORKFLOW_STEP);
                    break;
                }
                case WorkflowStepExecute: {
                    WorkflowStepExecuteRequest stepRequest = (WorkflowStepExecuteRequest) slackRequest;
                    String callbackId = stepRequest.getContext().getCallbackId();
                    if (callbackId != null) {
                        for (Pattern pattern : workflowStepExecuteHandlers.keySet()) {
                            if (pattern.matcher(callbackId).matches()) {
                                WorkflowStepExecuteHandler handler = workflowStepExecuteHandlers.get(pattern);
                                return handler.apply(stepRequest, stepRequest.getContext());
                            }
                        }
                    }
                    // Fallback to Events API handlers
                    if (eventsDispatcher.isRunning()) {
                        eventsDispatcher.enqueue(slackRequest.getRequestBodyAsString());
                        return Response.ok();
                    }
                    EventRequest request = new EventRequest(stepRequest.getRequestBodyAsString(), stepRequest.getHeaders());
                    BoltEventHandler<Event> handler = eventHandlers.get(request.getEventTypeAndSubtype());
                    if (handler != null) {
                        EventsApiPayload<Event> payload = buildEventPayload(request);
                        return handler.apply(payload, request.getContext());
                    }
                    log.warn("No BoltEventHandler registered for event: {}\n{}",
                            request.getEventTypeAndSubtype(), ListenerCodeSuggestion.WORKFLOW_STEP);
                    break;
                }
                default:
            }
            return appConfig.getUnmatchedRequestHandler().handle(slackRequest);
        } finally {
            if (log.isDebugEnabled()) {
                log.debug("The handler completed (request type: {})", slackRequest.getRequestType());
            }
        }
    }

}
