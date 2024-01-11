package com.slack.api.socket_mode.impl;

import com.google.gson.Gson;
import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.socket_mode.SocketModeClient;
import com.slack.api.socket_mode.listener.EnvelopeListener;
import com.slack.api.socket_mode.listener.WebSocketCloseListener;
import com.slack.api.socket_mode.listener.WebSocketErrorListener;
import com.slack.api.socket_mode.listener.WebSocketMessageListener;
import com.slack.api.socket_mode.queue.SocketModeMessageQueue;
import com.slack.api.socket_mode.queue.impl.ConcurrentLinkedMessageQueue;
import com.slack.api.socket_mode.request.EventsApiEnvelope;
import com.slack.api.socket_mode.request.InteractiveEnvelope;
import com.slack.api.socket_mode.request.SlashCommandsEnvelope;
import com.slack.api.util.http.ProxyUrlUtil;
import com.slack.api.util.json.GsonFactory;
import org.glassfish.tyrus.client.ClientManager;
import org.glassfish.tyrus.client.ClientProperties;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Socket Mode Client
 */
@ClientEndpoint
public class SocketModeClientTyrusImpl implements SocketModeClient {

    private Slack slack;
    private String appToken;
    private final Gson gson;
    private URI wssUri;
    private boolean autoReconnectEnabled;
    private boolean autoReconnectOnCloseEnabled;
    private SocketModeMessageQueue messageQueue;
    private ScheduledExecutorService messageProcessorExecutor;
    private boolean sessionMonitorEnabled;
    private Optional<ScheduledExecutorService> sessionMonitorExecutor;
    private AtomicReference<String> latestPong = new AtomicReference<>();

    private final List<WebSocketMessageListener> webSocketMessageListeners = new CopyOnWriteArrayList<>();
    private final List<EnvelopeListener<EventsApiEnvelope>> eventsApiEnvelopeListeners = new CopyOnWriteArrayList<>();

    private final List<EnvelopeListener<SlashCommandsEnvelope>> slashCommandsEnvelopeListeners = new CopyOnWriteArrayList<>();
    private final List<EnvelopeListener<InteractiveEnvelope>> interactiveEnvelopeListeners = new CopyOnWriteArrayList<>();

    private final List<WebSocketErrorListener> webSocketErrorListeners = new CopyOnWriteArrayList<>();
    private final List<WebSocketCloseListener> webSocketCloseListeners = new CopyOnWriteArrayList<>();

    /**
     * Current WebSocket session. This field is null when disconnected.
     */
    private Session currentSession;

    /**
     * Provides asynchronous clean up for old sessions.
     */
    private final ExecutorService sessionCleanerExecutor;

    public SocketModeClientTyrusImpl(String appToken) throws URISyntaxException, IOException, SlackApiException {
        this(Slack.getInstance(), appToken);
    }

    public SocketModeClientTyrusImpl(Slack slack, String appToken) throws URISyntaxException, IOException, SlackApiException {
        this(slack, appToken, slack.methods(appToken).appsConnectionsOpen(r -> r).getUrl());
    }

    public SocketModeClientTyrusImpl(
            Slack slack,
            String appToken,
            String wssUrl) throws URISyntaxException {
        this(slack, appToken, wssUrl, DEFAULT_MESSAGE_PROCESSOR_CONCURRENCY);
    }

    public SocketModeClientTyrusImpl(
            Slack slack,
            String appToken,
            String wssUrl,
            int concurrency
    ) throws URISyntaxException {
        this(
                slack,
                appToken,
                wssUrl,
                concurrency,
                new ConcurrentLinkedMessageQueue(),
                true,
                true,
                DEFAULT_SESSION_MONITOR_INTERVAL_MILLISECONDS
        );
    }

    public SocketModeClientTyrusImpl(
            Slack slack,
            String appToken,
            String wssUrl,
            int concurrency,
            SocketModeMessageQueue messageQueue,
            boolean autoReconnectEnabled,
            boolean sessionMonitorEnabled,
            long sessionMonitorIntervalMillis
    ) throws URISyntaxException {
        if (wssUrl == null) {
            throw new IllegalArgumentException("The wss URL for using Socket Mode is absent.");
        }
        setSlack(slack);
        setAppToken(appToken);
        setWssUri(new URI(wssUrl));
        this.gson = GsonFactory.createSnakeCase(slack.getConfig());

        setMessageQueue(messageQueue);
        setAutoReconnectEnabled(autoReconnectEnabled);
        // You can use the setter method if you set the value to true
        setAutoReconnectOnCloseEnabled(false);
        setSessionMonitorEnabled(sessionMonitorEnabled);
        initializeSessionMonitorExecutor(sessionMonitorIntervalMillis);
        initializeMessageProcessorExecutor(concurrency);
        sessionCleanerExecutor = slack.getConfig()
                .getExecutorServiceProvider()
                .createThreadPoolExecutor(getExecutorGroupNamePrefix() + "-session-cleaner", 3);
    }

    @Override
    public long maintainCurrentSession() {
        if (isAutoReconnectEnabled() && !verifyConnection()) {
            getLogger().info("The current session is no longer active. Going to reconnect to the Socket Mode server.");
            try {
                connectToNewEndpoint();
            } catch (Exception e) {
                getLogger().warn("Failed to connect to a new Socket Mode server endpoint: {}", e.getMessage(), e);
                return System.currentTimeMillis() + 10_000L;
            }
        }
        return System.currentTimeMillis();
    }

    @Override
    public void connect() {
        try {
            ClientManager clientManager = ClientManager.createClient();
            Map<String, String> proxyHeaders = getSlack().getHttpClient().getConfig().getProxyHeaders();
            String proxyUrl = getSlack().getHttpClient().getConfig().getProxyUrl();
            if (proxyUrl != null) {
                if (getLogger().isDebugEnabled()) {
                    getLogger().debug("The SocketMode client's going to use an HTTP proxy: {}", proxyUrl);
                }
                ProxyUrlUtil.ProxyUrl parsedProxy = ProxyUrlUtil.parse(proxyUrl);
                clientManager.getProperties().put(ClientProperties.PROXY_URI, parsedProxy.toUrlWithoutUserAndPassword());
                if (parsedProxy.getUsername() != null && parsedProxy.getPassword() != null) {
                    if (proxyHeaders == null) {
                        proxyHeaders = new HashMap<>();
                    }
                    ProxyUrlUtil.setProxyAuthorizationHeader(proxyHeaders, parsedProxy);
                }
            }
            if (proxyHeaders != null && !proxyHeaders.isEmpty()) {
                clientManager.getProperties().put(ClientProperties.PROXY_HEADERS, proxyHeaders);
            }
            try {
                setAutoReconnectEnabled(true);
                Session newSession = clientManager.connectToServer(this, getWssUri());
                setCurrentSession(newSession);
            } catch (DeploymentException e) {
                throw new IOException(e);
            }
            if (getLogger().isDebugEnabled()) {
                getLogger().debug("This Socket Mode client is successfully connected to the server: {}", getWssUri());
            }
        } catch (IOException e) {
            getLogger().error("Failed to reconnect to Socket Mode server: {}", e.getMessage(), e);
        }
    }

    @Override
    public boolean verifyConnection() {
        if (this.currentSession != null && this.currentSession.isOpen()) {
            String ping = "ping-pong_" + currentSession.getId();
            if (getLogger().isDebugEnabled()) {
                getLogger().debug("Sending a ping message: {}", ping);
            }
            ByteBuffer pingBytes = ByteBuffer.wrap(ping.getBytes());
            try {
                RemoteEndpoint.Basic basicRemote = this.currentSession.getBasicRemote();
                latestPong.set(null);
                basicRemote.sendPing(pingBytes);
                long waitMillis = 0L;
                while (waitMillis <= 3_000L) {
                    String pong = latestPong.getAndSet(null);
                    if (pong != null && pong.equals(ping)) {
                        if (getLogger().isDebugEnabled()) {
                            getLogger().debug("Received a pong message: {}", ping);
                        }
                        return true;
                    }
                    basicRemote.sendPing(pingBytes);
                    Thread.sleep(100L);
                    waitMillis += 100L;
                }
            } catch (Exception e) {
                getLogger().warn("Failed to send a ping message (session id: {}, error: {})",
                        this.currentSession.getId(),
                        e.getMessage());
            }
            if (getLogger().isDebugEnabled()) {
                getLogger().debug("Failed to receive a pong message: {}", ping);
            }
        }
        return false;
    }

    @Override
    public boolean isAutoReconnectOnCloseEnabled() {
        return this.autoReconnectOnCloseEnabled;
    }

    @Override
    public void setAutoReconnectOnCloseEnabled(boolean autoReconnectOnCloseEnabled) {
        this.autoReconnectOnCloseEnabled = autoReconnectOnCloseEnabled;
    }

    @Override
    public void disconnect() throws IOException {
        setAutoReconnectEnabled(false);
        if (currentSession != null) {
            synchronized (currentSession) {
                closeSession(currentSession);
            }
        }
    }

    @OnOpen
    public void onOpen(Session session) {
        getLogger().info("New session is open (session id: {})", session.getId());
        if (verifyConnection()) {
            setCurrentSession(session);
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        getLogger().info("onClose listener is called (session id: {}, reason: {})",
                session.getId(), reason.getReasonPhrase());
        runCloseListenersAndAutoReconnectAsNecessary(
                reason.getCloseCode().getCode(),
                reason.getReasonPhrase()
        );
    }

    @OnError
    public void onError(Session session, Throwable reason) {
        getLogger().error("onError listener is called (session id: {}, reason: {})", session.getId(), reason);
        runErrorListeners(reason);
    }

    @OnMessage
    public void onMessage(String message) {
        enqueueMessage(message);
    }

    @OnMessage
    public void onPong(PongMessage message) {
        latestPong.set(new String(message.getApplicationData().array()));
    }

    /**
     * Overwrites the underlying WebSocket session.
     */
    private void setCurrentSession(Session newSession) {
        if (this.currentSession == null) {
            this.currentSession = newSession;
        } else {
            synchronized (this.currentSession) {
                if (this.currentSession.getId().equals(newSession.getId())) {
                    return;
                }
                final Session oldSession = this.currentSession;
                sessionCleanerExecutor.execute(() -> {
                    try {
                        closeSession(oldSession);
                    } catch (Exception e) {
                        getLogger().error("Failed to close an old session (session id: {}, exception: {})",
                                oldSession.getId(), e.getMessage(), e);
                    }
                });
                this.currentSession = newSession;
            }
        }
    }

    /**
     * Closes the given session.
     */
    private static void closeSession(Session session) throws IOException {
        if (session.isOpen()) {
            CloseReason.CloseCodes code = CloseReason.CloseCodes.NORMAL_CLOSURE;
            String phrase = SocketModeClientTyrusImpl.class.getCanonicalName() + " did it";
            session.close(new CloseReason(code, phrase));
        }
    }

    // ----------------------------------------------------

    @Override
    public Slack getSlack() {
        return this.slack;
    }

    @Override
    public void setSlack(Slack slack) {
        this.slack = slack;
    }

    @Override
    public Gson getGson() {
        return this.gson;
    }

    @Override
    public String getAppToken() {
        return this.appToken;
    }

    @Override
    public void setAppToken(String appToken) {
        this.appToken = appToken;
    }

    @Override
    public boolean isAutoReconnectEnabled() {
        return this.autoReconnectEnabled;
    }

    @Override
    public void setAutoReconnectEnabled(boolean autoReconnectEnabled) {
        this.autoReconnectEnabled = autoReconnectEnabled;
    }

    @Override
    public boolean isSessionMonitorEnabled() {
        return this.sessionMonitorEnabled;
    }

    @Override
    public void setSessionMonitorEnabled(boolean sessionMonitorEnabled) {
        this.sessionMonitorEnabled = sessionMonitorEnabled;
    }

    @Override
    public Optional<ScheduledExecutorService> getSessionMonitorExecutor() {
        return this.sessionMonitorExecutor;
    }

    @Override
    public void sendWebSocketMessage(String message) {
        this.currentSession.getAsyncRemote().sendText(message);
    }

    @Override
    public URI getWssUri() {
        return this.wssUri;
    }

    @Override
    public void setWssUri(URI wssUri) {
        this.wssUri = wssUri;
    }

    @Override
    public SocketModeMessageQueue getMessageQueue() {
        return this.messageQueue;
    }

    @Override
    public void setMessageQueue(SocketModeMessageQueue messageQueue) {
        this.messageQueue = messageQueue;
    }

    @Override
    public ScheduledExecutorService getMessageProcessorExecutor() {
        return this.messageProcessorExecutor;
    }

    @Override
    public void setMessageProcessorExecutor(ScheduledExecutorService executorService) {
        this.messageProcessorExecutor = executorService;
    }

    @Override
    public void setSessionMonitorExecutor(Optional<ScheduledExecutorService> executorService) {
        this.sessionMonitorExecutor = executorService;
    }

    @Override
    public List<WebSocketMessageListener> getWebSocketMessageListeners() {
        return this.webSocketMessageListeners;
    }

    @Override
    public List<WebSocketErrorListener> getWebSocketErrorListeners() {
        return this.webSocketErrorListeners;
    }

    @Override
    public List<WebSocketCloseListener> getWebSocketCloseListeners() {
        return this.webSocketCloseListeners;
    }

    @Override
    public List<EnvelopeListener<InteractiveEnvelope>> getInteractiveEnvelopeListeners() {
        return this.interactiveEnvelopeListeners;
    }

    @Override
    public List<EnvelopeListener<SlashCommandsEnvelope>> getSlashCommandsEnvelopeListeners() {
        return this.slashCommandsEnvelopeListeners;
    }

    @Override
    public List<EnvelopeListener<EventsApiEnvelope>> getEventsApiEnvelopeListeners() {
        return this.eventsApiEnvelopeListeners;
    }

}
