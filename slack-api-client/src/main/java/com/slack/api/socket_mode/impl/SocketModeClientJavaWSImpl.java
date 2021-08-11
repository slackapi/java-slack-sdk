package com.slack.api.socket_mode.impl;

import com.google.gson.Gson;
import com.slack.api.Slack;
import com.slack.api.SlackConfig;
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
import okhttp3.Credentials;
import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ServerHandshake;

import java.io.IOException;
import java.net.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicLong;

public class SocketModeClientJavaWSImpl implements SocketModeClient {

    private Slack slack;
    private String appToken;
    private final Gson gson;
    private URI wssUri;
    private boolean autoReconnectEnabled;
    private SocketModeMessageQueue messageQueue;
    private ScheduledExecutorService messageProcessorExecutor;
    private boolean sessionMonitorEnabled;
    private Optional<ScheduledExecutorService> sessionMonitorExecutor;

    private final List<WebSocketMessageListener> webSocketMessageListeners = new CopyOnWriteArrayList<>();
    private final List<EnvelopeListener<EventsApiEnvelope>> eventsApiEnvelopeListeners = new CopyOnWriteArrayList<>();

    private final List<EnvelopeListener<SlashCommandsEnvelope>> slashCommandsEnvelopeListeners = new CopyOnWriteArrayList<>();
    private final List<EnvelopeListener<InteractiveEnvelope>> interactiveEnvelopeListeners = new CopyOnWriteArrayList<>();

    private final List<WebSocketErrorListener> webSocketErrorListeners = new CopyOnWriteArrayList<>();
    private final List<WebSocketCloseListener> webSocketCloseListeners = new CopyOnWriteArrayList<>();

    private UnderlyingWebSocketSession currentSession;

    public SocketModeClientJavaWSImpl(String appToken) throws IOException, SlackApiException, URISyntaxException {
        this(Slack.getInstance(), appToken);
    }

    public SocketModeClientJavaWSImpl(Slack slack, String appToken) throws IOException, SlackApiException, URISyntaxException {
        this(slack, appToken, slack.methods(appToken).appsConnectionsOpen(r -> r).getUrl());
    }

    public SocketModeClientJavaWSImpl(
            Slack slack,
            String appToken,
            String wssUrl) throws URISyntaxException {
        this(slack, appToken, wssUrl, DEFAULT_MESSAGE_PROCESSOR_CONCURRENCY);
    }

    public SocketModeClientJavaWSImpl(
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

    public SocketModeClientJavaWSImpl(
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
        setSessionMonitorEnabled(sessionMonitorEnabled);
        initializeSessionMonitorExecutor(sessionMonitorIntervalMillis);
        initializeMessageProcessorExecutor(concurrency);
        this.currentSession = new UnderlyingWebSocketSession(getWssUri(), this);
    }

    // ----------------------------------------------------

    @Override
    public void connect() {
        this.currentSession.connect();
    }

    @Override
    public boolean verifyConnection() {
        if (this.currentSession != null && this.currentSession.isOpen()) {
            try {
                if (getLogger().isDebugEnabled()) {
                    getLogger().debug("Sending a ping message");
                }
                this.currentSession.sendPing();
                long waitMillis = 0L;
                while (waitMillis <= 3_000L) {
                    if (this.currentSession.isPongReceived()) {
                        if (getLogger().isDebugEnabled()) {
                            getLogger().debug("Received a pong message");
                        }
                        return true;
                    }
                    this.currentSession.sendPing();
                    Thread.sleep(100L);
                    waitMillis += 100L;
                }
            } catch (Exception e) {
                getLogger().info("Failed to send a ping message (exception: {}, message: {})",
                        e.getClass().getCanonicalName(),
                        e.getMessage()
                );
            }
            if (getLogger().isDebugEnabled()) {
                getLogger().debug("Failed to receive a pong message");
            }
        }
        return false;
    }

    @Override
    public void disconnect() {
        setAutoReconnectEnabled(false);
        this.currentSession.close();
    }

    @Override
    public void connectToNewEndpoint() throws IOException {
        try {
            setWssUri(new URI(getSlack().issueSocketModeUrl(getAppToken())));
        } catch (URISyntaxException e) {
            throw new IOException(e);
        }
        if (this.currentSession == null) {
            this.currentSession = new UnderlyingWebSocketSession(getWssUri(), this);
            connect();
        } else {
            synchronized (this.currentSession) {
                WebSocketClient oldSession = this.currentSession;
                this.currentSession = new UnderlyingWebSocketSession(getWssUri(), this);
                connect();
                oldSession.close();
            }
        }
    }

    @Override
    public void sendWebSocketMessage(String message) {
        this.currentSession.send(message);
    }

    @Override
    public long maintainCurrentSession() {
        if (isAutoReconnectEnabled() && !verifyConnection()) {
            try {
                connectToNewEndpoint();
            } catch (IOException e) {
                getLogger().error(
                        "Failed to establish a new connection to the Socket Mode server: {}",
                        e.getMessage(), e);
                return System.currentTimeMillis() + 10_000L;
            }
        }
        return System.currentTimeMillis();
    }

    static class UnderlyingWebSocketSession extends WebSocketClient {

        private final SocketModeClient smc;
        private final AtomicLong lastPongReceived = new AtomicLong();

        public boolean isPongReceived() {
            return Math.abs(System.currentTimeMillis() - lastPongReceived.get()) < 1_000L;
        }
        public UnderlyingWebSocketSession(URI serverUri, SocketModeClient smc) {
            this(serverUri, new HashMap<>(), smc);
        }

        public UnderlyingWebSocketSession(URI serverUri, Map<String, String> httpHeaders, SocketModeClient smc) {
            super(serverUri, httpHeaders);
            this.smc = smc;

            // FIXME: the proxy settings here may not work
            SlackConfig slackConfig = smc.getSlack().getHttpClient().getConfig();
            Map<String, String> proxyHeaders = slackConfig.getProxyHeaders();
            if (proxyHeaders == null) {
                proxyHeaders = new HashMap<>();
            }

            String proxyUrl = slackConfig.getProxyUrl();
            if (proxyUrl != null) {
                if (smc.getLogger().isDebugEnabled()) {
                    smc.getLogger().debug("The SocketMode client's going to use an HTTP proxy: {}", proxyUrl);
                }
                ProxyUrlUtil.ProxyUrl parsedProxy = ProxyUrlUtil.parse(proxyUrl);
                if (parsedProxy.getUsername() != null && parsedProxy.getPassword() != null) {
                    // see also: https://github.com/slackapi/java-slack-sdk/issues/792#issuecomment-895961176
                    String message = "Unfortunately, " +
                            "having username:password with the Java-WebSocket library is not yet supported. " +
                            "Consider using other implementations such SocketModeClient.Backend.Tyrus.";
                    throw new UnsupportedOperationException(message);
                }

                InetSocketAddress proxyAddress = new InetSocketAddress(parsedProxy.getHost(), parsedProxy.getPort());
                this.setProxy(new Proxy(Proxy.Type.HTTP, proxyAddress));
                ProxyUrlUtil.setProxyAuthorizationHeader(proxyHeaders, parsedProxy);
            }
            if (proxyHeaders != null && !proxyHeaders.isEmpty()) {
                for (Map.Entry<String, String> each : proxyHeaders.entrySet()) {
                    this.addHeader(each.getKey(), each.getValue());
                }
            }
        }

        @Override
        public void onOpen(ServerHandshake serverHandshake) {
            byte[] bytes = serverHandshake.getContent();
            if (bytes != null) {
                smc.getLogger().info("New session is open (content: {})", new String(bytes));
                smc.setAutoReconnectEnabled(true);
            } else {
                smc.getLogger().info("New session is open");
            }
        }

        @Override
        public void onMessage(String message) {
            smc.enqueueMessage(message);
        }

        @Override
        public void onClose(int code, String reason, boolean remote) {
            smc.getLogger().info("onClose listener is called (code: {}, reason: {})", code, reason);
            // https://www.iana.org/assignments/websocket/websocket.xml#close-code-number
            if (code >= 1000) {
                smc.runCloseListenersAndAutoReconnectAsNecessary(code, reason);
            }
        }

        @Override
        public void onError(Exception reason) {
            smc.getLogger().error("onError listener is called (reason: {})", reason);
            smc.runErrorListeners(reason);
        }

        @Override
        public void onWebsocketPong(WebSocket conn, Framedata f) {
            lastPongReceived.set(System.currentTimeMillis());
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
    public URI getWssUri() {
        return this.wssUri;
    }

    @Override
    public void setWssUri(URI wssUri) {
        this.wssUri = wssUri;
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
    public void setSessionMonitorExecutor(Optional<ScheduledExecutorService> executorService) {
        this.sessionMonitorExecutor = executorService;
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
    public List<WebSocketErrorListener> getWebSocketErrorListeners() {
        return this.webSocketErrorListeners;
    }

    @Override
    public List<WebSocketCloseListener> getWebSocketCloseListeners() {
        return this.webSocketCloseListeners;
    }

    @Override
    public List<WebSocketMessageListener> getWebSocketMessageListeners() {
        return this.webSocketMessageListeners;
    }

    @Override
    public List<EnvelopeListener<EventsApiEnvelope>> getEventsApiEnvelopeListeners() {
        return this.eventsApiEnvelopeListeners;
    }

    @Override
    public List<EnvelopeListener<InteractiveEnvelope>> getInteractiveEnvelopeListeners() {
        return this.interactiveEnvelopeListeners;
    }

    @Override
    public List<EnvelopeListener<SlashCommandsEnvelope>> getSlashCommandsEnvelopeListeners() {
        return this.slashCommandsEnvelopeListeners;
    }

}
