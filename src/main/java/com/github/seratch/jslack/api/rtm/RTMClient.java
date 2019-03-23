package com.github.seratch.jslack.api.rtm;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.request.rtm.RTMConnectRequest;
import com.github.seratch.jslack.api.methods.response.rtm.RTMConnectResponse;
import com.github.seratch.jslack.api.model.User;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.*;
import java.io.Closeable;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Real Time Messaging API
 * <p>
 * https://api.slack.com/rtm
 */
@ClientEndpoint
@Slf4j
public class RTMClient implements Closeable {

    /**
     * Slack instance used for building this RTMClient. Used when calling #reconnect()
     */
    private final Slack slack;

    /**
     * Bot access token used for building this RTMClient. Used when calling #reconnect()
     */
    private final String botApiToken;

    /**
     * WebSocket URL to connect to.
     */
    private URI wssUri;

    /**
     * The bot user used for this session.
     */
    private User connectedBotUser;

    /**
     * Current WebSocket session. This field is null when disconnected.
     */
    private Session currentSession;

    // NOTE: Needless to say, manipulating these List objects is not thread-safe
    private final List<RTMMessageHandler> messageHandlers = new ArrayList<>();
    private final List<RTMErrorHandler> errorHandlers = new ArrayList<>();
    private final List<RTMCloseHandler> closeHandlers = new ArrayList<>();

    public RTMClient(Slack slack,
                     String botApiToken,
                     String wssUrl,
                     User connectedBotUser) throws URISyntaxException {
        if (wssUrl == null) {
            throw new IllegalArgumentException("The wss URL to start Real Time Messaging API is absent.");
        }
        if (connectedBotUser == null) {
            throw new IllegalArgumentException("The self user data is absent.");
        }

        this.slack = slack;
        this.botApiToken = botApiToken;
        this.wssUri = new URI(wssUrl);
        this.connectedBotUser = connectedBotUser;
    }

    /**
     * Connects to the wss endpoint and starts a new WebSocket session.
     * If you'd like to reconnect to the endpoint with this instance, call #reconnect() instead.
     * Calling this method won't work as you expect.
     */
    public void connect() throws IOException, DeploymentException {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        container.connectToServer(this, wssUri);
        log.debug("client connected to the server: {}", wssUri);
    }

    /**
     * Disconnects from the wss endpoint.
     * After calling this method, calling #connect() doesn't work.
     * You need to call #reconnect() or instantiate a new RTMClient instead.
     */
    public void disconnect() throws IOException {
        if (currentSession != null && currentSession.isOpen()) {
            synchronized (currentSession) {
                this.currentSession.close(new CloseReason(
                        CloseReason.CloseCodes.NORMAL_CLOSURE,
                        RTMClient.class.getCanonicalName() + " did it"
                ));
            }
        }
    }

    /**
     * Re-connects to a new wss endpoint and starts a new WebSocket session.
     * This method calls rtm.connect API. Please be aware of the rate limit.
     * https://api.slack.com/docs/rate-limits#rtm
     */
    public void reconnect() throws IOException, SlackApiException, URISyntaxException, DeploymentException {
        // Call rtm.connect again to refresh wss URL
        RTMConnectResponse response = slack.methods().rtmConnect(RTMConnectRequest.builder().token(botApiToken).build());
        if (response.isOk()) {
            this.wssUri = new URI(response.getUrl());
            this.connectedBotUser = response.getSelf();
        } else {
            throw new IllegalStateException("Failed to the RTM endpoint URL (error: " + response.getError() + ")");
        }
        // start a WebSocket session
        connect();
    }

    @Override
    public void close() throws IOException {
        disconnect();
    }

    @OnOpen
    public void onOpen(Session session) {
        updateSession(session);
        log.debug("session opened: {}", session.getId());
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        updateSession(session);
        log.debug("session closed: {}, reason: {}", session.getId(), reason.getReasonPhrase());

        closeHandlers.forEach(closeHandler -> {
            closeHandler.handle(reason);
        });
    }

    @OnError
    public void onError(Session session, Throwable reason) {
        log.error("session error occurred, exception is below", reason);

        errorHandlers.forEach(errorHandler -> {
            errorHandler.handle(reason);
        });
    }

    @OnMessage
    public void onMessage(String message) {
        log.debug("message: {}", message);
        messageHandlers.forEach(messageHandler -> {
            messageHandler.handle(message);
        });
    }

    public void addMessageHandler(RTMMessageHandler messageHandler) {
        messageHandlers.add(messageHandler);
    }

    public void removeMessageHandler(RTMMessageHandler messageHandler) {
        messageHandlers.remove(messageHandler);
    }

    public void addErrorHandler(RTMErrorHandler errorHandler) {
        errorHandlers.add(errorHandler);
    }

    public void removeErrorHandler(RTMErrorHandler errorHandler) {
        errorHandlers.remove(errorHandler);
    }

    public void addCloseHandler(RTMCloseHandler closeHandler) {
        closeHandlers.add(closeHandler);
    }

    public void removeCloseHandler(RTMCloseHandler closeHandler) {
        closeHandlers.remove(closeHandler);
    }

    public void sendMessage(String message) {
        this.currentSession.getAsyncRemote().sendText(message);
    }

    public URI getWssUri() {
        return wssUri;
    }

    public User getConnectedBotUser() {
        return connectedBotUser;
    }

    /**
     * Overwrites the underlying WebSocket session.
     * @param newSession new session
     */
    private void updateSession(Session newSession) {
        if (this.currentSession == null) {
            this.currentSession = newSession;
        } else {
            synchronized (this.currentSession) {
                this.currentSession = newSession;
            }
        }
    }
}
