package com.github.seratch.jslack.api.rtm;

import lombok.extern.slf4j.Slf4j;

import javax.websocket.*;
import java.io.Closeable;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@ClientEndpoint
@Slf4j
public class RTMClient implements Closeable {

    private final URI wssUri;
    private Session currentSession = null;

    private final List<RTMMessageHandler> messageHandlers = new ArrayList<>();
    private final List<RTMErrorHandler> errorHandlers = new ArrayList<>();
    private final List<RTMCloseHandler> closeHandlers = new ArrayList<>();

    public RTMClient(String wssUrl) throws URISyntaxException {
        if (wssUrl == null) {
            throw new IllegalArgumentException("The wss URL to start Real Time Messaging API is absent.");
        }
        this.wssUri = new URI(wssUrl);
    }

    public void connect() throws IOException, DeploymentException {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        container.connectToServer(this, wssUri);
        log.debug("client connected to the server: {}", wssUri);
    }

    public void disconnect() throws IOException {
        if (currentSession != null) {
            currentSession.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, RTMClient.class.getCanonicalName() + " did it"));
        }
    }

    @Override
    public void close() throws IOException {
        disconnect();
    }

    @OnOpen
    public void onOpen(Session session) {
        log.debug("session opened: {}", session.getId());
        this.currentSession = session;
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        log.debug("session closed: {}, reason: {}", session.getId(), reason.getReasonPhrase());
        this.currentSession = null;

        closeHandlers.forEach(closeHandler -> {
            closeHandler.handle(reason);
        });
    }

    @OnError
    public void onError(Session session, Throwable reason) {
        log.error("session errored, exception is below", reason);
        this.currentSession = null;

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

}
