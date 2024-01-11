package com.slack.api.socket_mode;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.slack.api.Slack;
import com.slack.api.socket_mode.listener.EnvelopeListener;
import com.slack.api.socket_mode.listener.WebSocketCloseListener;
import com.slack.api.socket_mode.listener.WebSocketErrorListener;
import com.slack.api.socket_mode.listener.WebSocketMessageListener;
import com.slack.api.socket_mode.queue.SocketModeMessageQueue;
import com.slack.api.socket_mode.request.EventsApiEnvelope;
import com.slack.api.socket_mode.request.InteractiveEnvelope;
import com.slack.api.socket_mode.request.SlashCommandsEnvelope;
import com.slack.api.socket_mode.response.SocketModeResponse;
import com.slack.api.util.json.GsonFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Socket Mode Client
 */
public interface SocketModeClient extends Closeable {

    /**
     * Built-in backend supports. The default os Tyrus.
     */
    enum Backend {
        /**
         * org.glassfish.tyrus.bundles:tyrus-standalone-client
         */
        Tyrus,
        /**
         * org.java-websocket:Java-WebSocket
         */
        JavaWebSocket
    }

    /**
     * Connects to the current WSS endpoint and starts a new WebSocket session.
     */
    void connect() throws IOException;

    /**
     * Returns true if this client is connected to the Socket Mode server.
     */
    boolean verifyConnection();

    /**
     * Returns true if the client tries to reconnect when onClose listeners are called
     * plus isAutoReconnectEnabled() is true. Default: false
     */
    boolean isAutoReconnectOnCloseEnabled();

    void setAutoReconnectOnCloseEnabled(boolean autoReconnectOnCloseEnabled);

    /**
     * Connects to a new WSS endpoint and starts a new WebSocket session.
     */
    default void connectToNewEndpoint() throws IOException {
        try {
            setWssUri(new URI(getSlack().issueSocketModeUrl(getAppToken())));
            connect();
        } catch (URISyntaxException e) {
            throw new IOException(e);
        }
    }

    /**
     * Disconnects from the wss endpoint and abandons the current session.
     */
    void disconnect() throws IOException;

    /**
     * Closes this Socket Mode client.
     * After calling this method, the instance is no longer available to use.
     */
    @Override
    default void close() throws IOException {
        setAutoReconnectEnabled(false);
        disconnect();
        for (Runnable neverCommencedExecution : getMessageProcessorExecutor().shutdownNow()) {
            neverCommencedExecution.run();
        }
        if (getSessionMonitorExecutor().isPresent()) {
            List<Runnable> neverCommencedExecutions = getSessionMonitorExecutor().get().shutdownNow();
            if (neverCommencedExecutions != null && neverCommencedExecutions.size() > 0) {
                getLogger().info("This client is going to be terminated. {} executions in SessionStateMonitorExecutor did not begin.", neverCommencedExecutions.size());
            }
        }
    }

    // ---------------------------------
    // Configurable attributes
    // ---------------------------------

    Slack getSlack();

    void setSlack(Slack slack);

    String getAppToken();

    void setAppToken(String appToken);

    /**
     * Returns the current WSS URI.
     */
    URI getWssUri();

    void setWssUri(URI wssUri);

    /**
     * Tries to reconnect to the Socket Mode server if true.
     */
    boolean isAutoReconnectEnabled();

    void setAutoReconnectEnabled(boolean autoReconnectEnabled);

    /**
     * A background job for session maintenance works if true.
     */
    boolean isSessionMonitorEnabled();

    void setSessionMonitorEnabled(boolean sessionMonitorEnabled);

    Optional<ScheduledExecutorService> getSessionMonitorExecutor();

    void setSessionMonitorExecutor(Optional<ScheduledExecutorService> executorService);

    /**
     * Returns the message queue for message processor workers.
     */
    SocketModeMessageQueue getMessageQueue();

    void setMessageQueue(SocketModeMessageQueue messageQueue);

    ScheduledExecutorService getMessageProcessorExecutor();

    void setMessageProcessorExecutor(ScheduledExecutorService executorService);

    int DEFAULT_MESSAGE_PROCESSOR_CONCURRENCY = 10;

    default void initializeMessageProcessorExecutor(int concurrency) {
        String processorName = getExecutorGroupNamePrefix() + "-message-processor";
        ScheduledExecutorService messageProcessorExecutor = getSlack()
                .getConfig()
                .getExecutorServiceProvider()
                .createThreadScheduledExecutor(processorName);
        for (int i = 0; i < concurrency; i++) {
            int num = i;
            messageProcessorExecutor.scheduleAtFixedRate(() -> {
                try {
                    String message = getMessageQueue().poll();
                    if (message != null) {
                        processMessage(message);
                    }
                } catch (Exception e) {
                    getLogger().error("Failed to poll a message or run processMessage (error: {})", e.getMessage(), e);
                }
            }, 0, 10, TimeUnit.MILLISECONDS);
        }
        setMessageProcessorExecutor(messageProcessorExecutor);
    }

    long DEFAULT_SESSION_MONITOR_INTERVAL_MILLISECONDS = 5_000L;

    default void initializeSessionMonitorExecutor(long intervalMillis) {
        if (isSessionMonitorEnabled()) {
            String groupName = getExecutorGroupNamePrefix() + "-session-monitor";
            ScheduledExecutorService executorService = getSlack()
                    .getConfig()
                    .getExecutorServiceProvider()
                    .createThreadScheduledExecutor(groupName);
            final AtomicLong nextInvocationMillis = new AtomicLong(System.currentTimeMillis());
            executorService.scheduleWithFixedDelay(() -> {
                long startMillis = System.currentTimeMillis();
                if (getLogger().isDebugEnabled()) {
                    getLogger().debug("Checking the current session...");
                }
                if (isAutoReconnectEnabled() && nextInvocationMillis.get() <= System.currentTimeMillis()) {
                    nextInvocationMillis.set(maintainCurrentSession());
                }
                if (getLogger().isDebugEnabled()) {
                    long spentMillis = System.currentTimeMillis() - startMillis;
                    getLogger().debug("The session maintenance completed in {} milliseconds", spentMillis);
                }
            }, 5_000L, intervalMillis, TimeUnit.MILLISECONDS);
            if (getLogger().isDebugEnabled()) {
                getLogger().debug("SessionStateMonitorExecutor started.");
            }
            setSessionMonitorExecutor(Optional.of(executorService));
        } else {
            if (getLogger().isDebugEnabled()) {
                getLogger().debug("SessionStateMonitorExecutor is disabled.");
            }
            setSessionMonitorExecutor(Optional.empty());
        }
    }

    // ---------------------------------
    // Sending Messages
    // ---------------------------------

    default void sendSocketModeResponse(SocketModeResponse response) {
        sendSocketModeResponse(getGson().toJson(response));
    }

    default void sendSocketModeResponse(String message) {
        debugLogResponse(message);
        sendWebSocketMessage(message);
    }

    /**
     * Sends a text message to the Socket Mode server via the current WebSocket connection.
     */
    void sendWebSocketMessage(String message);

    // ---------------------------------
    // onError
    // ---------------------------------

    List<WebSocketErrorListener> getWebSocketErrorListeners();

    default void addWebSocketErrorListener(WebSocketErrorListener listener) {
        getWebSocketErrorListeners().add(listener);
    }

    default void removeWebSocketErrorListener(WebSocketErrorListener listener) {
        getWebSocketErrorListeners().remove(listener);
    }

    default void runErrorListeners(Throwable reason) {
        for (WebSocketErrorListener listener : getWebSocketErrorListeners()) {
            listener.handle(reason);
        }
    }

    // ---------------------------------
    // onClose
    // ---------------------------------

    List<WebSocketCloseListener> getWebSocketCloseListeners();

    default void addWebSocketCloseListener(WebSocketCloseListener listener) {
        getWebSocketCloseListeners().add(listener);
    }

    default void removeWebSocketCloseListener(WebSocketCloseListener listener) {
        getWebSocketCloseListeners().remove(listener);
    }

    default void runCloseListenersAndAutoReconnectAsNecessary(Integer code, String reason) {
        for (WebSocketCloseListener listener : getWebSocketCloseListeners()) {
            listener.handle(code, reason);
        }
        if (isAutoReconnectOnCloseEnabled()) {
            // Starting in December 2023, the Socket Mode server changed its behavior.
            // As a result, the logic here can result in a rate-limited error.
            //
            // Reported issues:
            // * https://github.com/slackapi/java-slack-sdk/issues/1256
            // * https://github.com/slackapi/java-slack-sdk/issues/1223
            //
            // Originally, this logic was added to let this client get back online even more quickly when something is wrong.
            // However, it seems that the logic has been relying on the server's past behavior characteristics.
            // Note that, even when the underlying connection is closed due to "reason: Closed abnormally." or similar,
            // the session monitor under the hood continues to try to reconnect for you.
            // For this reason, disabling this logic should be safe for any situations.
            // If there is an exception this assumption does not cover, we may need to improve the logic to ensure better stability.
            //
            // Therefore, in v1.37.0, this SDK disabled this by default.
            // If you want to turn it on again, set the isAutoReconnectOnCloseEnabled to true.

            if (isAutoReconnectEnabled() && !verifyConnection()) {
                try {
                    connectToNewEndpoint();
                } catch (IOException e) {
                    getLogger().error("Failed to reconnect to the Socket Mode server: {}", e.getMessage(), e);
                }
            }
        }
    }

    // ---------------------------------
    // onMessage
    // ---------------------------------

    default void enqueueMessage(String message) {
        debugLogRequest(message);
        if (message.startsWith("{")) {
            getMessageQueue().add(message);
        }
    }

    List<WebSocketMessageListener> getWebSocketMessageListeners();

    default void addWebSocketMessageListener(WebSocketMessageListener listener) {
        getWebSocketMessageListeners().add(listener);
    }

    default void removeWebSocketMessageListener(WebSocketMessageListener listener) {
        getWebSocketMessageListeners().remove(listener);
    }

    // ---------------------------------
    // Typed Envelope Listeners
    // ---------------------------------

    List<EnvelopeListener<EventsApiEnvelope>> getEventsApiEnvelopeListeners();

    List<EnvelopeListener<InteractiveEnvelope>> getInteractiveEnvelopeListeners();

    List<EnvelopeListener<SlashCommandsEnvelope>> getSlashCommandsEnvelopeListeners();

    default void addEventsApiEnvelopeListener(EnvelopeListener<EventsApiEnvelope> listener) {
        getEventsApiEnvelopeListeners().add(listener);
    }

    default void removeEventsApiEnvelopeListener(EnvelopeListener<EventsApiEnvelope> listener) {
        getEventsApiEnvelopeListeners().remove(listener);
    }

    default void addInteractiveEnvelopeListener(EnvelopeListener<InteractiveEnvelope> listener) {
        getInteractiveEnvelopeListeners().add(listener);
    }

    default void removeInteractiveEnvelopeListener(EnvelopeListener<InteractiveEnvelope> listener) {
        getInteractiveEnvelopeListeners().remove(listener);
    }

    default void addSlashCommandsEnvelopeListener(EnvelopeListener<SlashCommandsEnvelope> listener) {
        getSlashCommandsEnvelopeListeners().add(listener);
    }

    default void removeSlashCommandsEnvelopeListener(EnvelopeListener<SlashCommandsEnvelope> listener) {
        getSlashCommandsEnvelopeListeners().remove(listener);
    }

    // ---------------------------------
    // Other Internals
    // ---------------------------------

    Logger LOGGER = LoggerFactory.getLogger(SocketModeClient.class);
    Gson GSON = GsonFactory.createSnakeCase();

    default Logger getLogger() {
        return LOGGER;
    }

    default Gson getGson() {
        return GSON;
    }

    default void processMessage(String message) throws IOException {
        if (!message.startsWith("{")) {
            return;
        }
        Gson gson = getGson();
        JsonElement messageObj = gson.fromJson(message, JsonElement.class);
        if (!messageObj.isJsonObject()) {
            return;
        }
        JsonObject messageJson = messageObj.getAsJsonObject();
        JsonElement typeJson = messageJson.get("type");
        if (typeJson == null || typeJson.isJsonNull()) {
            return;
        }
        String type = typeJson.getAsString();
        if ("disconnect".equals(type)) {
            connectToNewEndpoint();
            return;
        }

        for (WebSocketMessageListener listener : getWebSocketMessageListeners()) {
            listener.handle(message);
        }
        if (type.equals(EventsApiEnvelope.TYPE)) {
            for (EnvelopeListener<EventsApiEnvelope> listener : getEventsApiEnvelopeListeners()) {
                listener.handle(gson.fromJson(message, EventsApiEnvelope.class));
            }
        }
        if (type.equals(InteractiveEnvelope.TYPE)) {
            for (EnvelopeListener<InteractiveEnvelope> listener : getInteractiveEnvelopeListeners()) {
                listener.handle(gson.fromJson(message, InteractiveEnvelope.class));
            }
        }
        if (type.equals(SlashCommandsEnvelope.TYPE)) {
            for (EnvelopeListener<SlashCommandsEnvelope> listener : getSlashCommandsEnvelopeListeners()) {
                listener.handle(gson.fromJson(message, SlashCommandsEnvelope.class));
            }
        }
    }

    Gson PRETTY_PRINTING = new GsonBuilder().setPrettyPrinting().create();

    default void debugLogRequest(String message) {
        if (getLogger().isDebugEnabled() && message != null) {
            if (message.startsWith("{")) {
                JsonElement json = getGson().fromJson(message, JsonElement.class);
                getLogger().debug("Socket Mode Request:\n\n{}\n", PRETTY_PRINTING.toJson(json));
            } else {
                getLogger().debug("Socket Mode Request:\n\n{}\n", message);
            }
        }
    }

    default void debugLogResponse(String message) {
        if (getLogger().isDebugEnabled()) {
            if (message.startsWith("{")) {
                JsonElement json = getGson().fromJson(message, JsonElement.class);
                getLogger().debug("Socket Mode Response:\n\n{}\n", PRETTY_PRINTING.toJson(json));
            } else {
                getLogger().debug("Socket Mode Response:\n\n{}\n", message);
            }
        }
    }

    String EXECUTOR_GROUP_NAME_PREFIX = "socket-mode";

    default String getExecutorGroupNamePrefix() {
        return EXECUTOR_GROUP_NAME_PREFIX;
    }

    /**
     * Maintains the current session in a background job.
     * <p>
     * see also: initializeSessionMonitorExecutor
     *
     * @return unix time to check next time
     */
    long maintainCurrentSession();

}
