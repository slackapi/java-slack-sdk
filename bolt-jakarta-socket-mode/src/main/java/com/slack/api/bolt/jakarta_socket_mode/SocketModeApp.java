package com.slack.api.bolt.jakarta_socket_mode;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.slack.api.bolt.App;
import com.slack.api.bolt.request.Request;
import com.slack.api.bolt.response.Response;
import com.slack.api.bolt.jakarta_socket_mode.request.SocketModeRequest;
import com.slack.api.bolt.jakarta_socket_mode.request.SocketModeRequestParser;
import com.slack.api.jakarta_socket_mode.JakartaSocketModeClientFactory;
import com.slack.api.socket_mode.SocketModeClient;
import com.slack.api.socket_mode.response.AckResponse;
import com.slack.api.util.json.GsonFactory;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

@Slf4j
public class SocketModeApp {
    private boolean clientStopped = true;
    private final App app;
    private final Supplier<SocketModeClient> clientFactory;
    private SocketModeClient client;

    private static final Function<ErrorContext, Response> DEFAULT_ERROR_HANDLER = (context) -> {
        Exception e = context.getException();
        log.error("Failed to handle a request: {}", e.getMessage(), e);
        return null;
    };

    @Data
    @Builder
    public static class ErrorContext {
        private Request<?> request;
        private Exception exception;
    }

    // -------------------------------------------

    private static void sendSocketModeResponse(
            SocketModeClient client,
            Gson gson,
            SocketModeRequest req,
            Response boltResponse
    ) {
        if (boltResponse.getBody() != null) {
            Map<String, Object> response = new HashMap<>();
            if (boltResponse.getContentType().startsWith("application/json")) {
                response.put("envelope_id", req.getEnvelope().getEnvelopeId());
                response.put("payload", gson.fromJson(boltResponse.getBody(), JsonElement.class));
            } else {
                response.put("envelope_id", req.getEnvelope().getEnvelopeId());
                Map<String, Object> payload = new HashMap<>();
                payload.put("text", boltResponse.getBody());
                response.put("payload", payload);
            }
            client.sendSocketModeResponse(gson.toJson(response));
        } else {
            client.sendSocketModeResponse(new AckResponse(req.getEnvelope().getEnvelopeId()));
        }
    }

    private static Supplier<SocketModeClient> buildSocketModeClientFactory(
            App app,
            String appToken,
            Function<ErrorContext, Response> errorHandler
    ) {
        return () -> {
            try {
                final SocketModeClient client = JakartaSocketModeClientFactory.create(app.slack(), appToken);
                final SocketModeRequestParser requestParser = new SocketModeRequestParser(app.config());
                final Gson gson = GsonFactory.createSnakeCase(app.slack().getConfig());
                client.addWebSocketMessageListener(message -> {
                    long startMillis = System.currentTimeMillis();
                    SocketModeRequest req = requestParser.parse(message);
                    if (req != null) {
                        try {
                            Response boltResponse = app.run(req.getBoltRequest());
                            if (boltResponse.getStatusCode() != 200) {
                                log.warn("Unsuccessful Bolt app execution (status: {}, body: {})",
                                        boltResponse.getStatusCode(), boltResponse.getBody());
                                return;
                            }
                            sendSocketModeResponse(client, gson, req, boltResponse);
                        } catch (Exception e) {
                            ErrorContext context = ErrorContext.builder().request(req.getBoltRequest()).exception(e).build();
                            Response errorResponse = errorHandler.apply(context);
                            if (errorResponse != null) {
                                sendSocketModeResponse(client, gson, req, errorResponse);
                            }
                        } finally {
                            long spentMillis = System.currentTimeMillis() - startMillis;
                            log.debug("Response time: {} milliseconds", spentMillis);
                        }
                    }
                });
                return client;
            } catch (IOException e) {
                log.error("Failed to start a new Socket Mode client (error: {})", e.getMessage(), e);
                return null;
            }
        };
    }

    public SocketModeApp(App app) throws IOException {
        this(System.getenv("SLACK_APP_TOKEN"), app);
    }


    public SocketModeApp(String appToken, App app) throws IOException {
        this(appToken, DEFAULT_ERROR_HANDLER, app);
    }

    public SocketModeApp(
            String appToken,
            Function<ErrorContext, Response> errorHandler,
            App app
    ) throws IOException {
        this(buildSocketModeClientFactory(app, appToken, errorHandler), app);
    }

    public SocketModeApp(
            String appToken,
            App app,
            Function<ErrorContext, Response> errorHandler
    ) throws IOException {
        this(buildSocketModeClientFactory(app, appToken, errorHandler), app);
    }

    public SocketModeApp(Supplier<SocketModeClient> clientFactory, App app) {
        this.clientFactory = clientFactory;
        this.app = app;
    }

    /**
     * If you would like to synchronously detect the connection error as an exception when bootstrapping,
     * use this constructor. The first line can throw an exception
     * in the case where either the token or network settings are valid.
     *
     * <code>
     * SocketModeClient client = JakartaSocketModeClientFactory.create(appToken);
     * SocketModeApp socketModeApp = new SocketModeApp(client, app);
     * </code>
     */
    public SocketModeApp(SocketModeClient socketModeClient, App app) {
        this.client = socketModeClient;
        this.clientFactory = () -> socketModeClient;
        this.app = app;
    }

    // -------------------------------------------

    public void start() throws Exception {
        run(true);
    }

    public void startAsync() throws Exception {
        run(false);
    }

    public void run(boolean blockCurrentThread) throws Exception {
        this.app.start();
        if (this.client == null) {
            this.client = clientFactory.get();
        }
        if (this.isClientStopped()) {
            this.client.connectToNewEndpoint();
        } else {
            this.client.connect();
        }
        this.client.setAutoReconnectEnabled(true);
        this.clientStopped = false;
        if (blockCurrentThread) {
            Thread.sleep(Long.MAX_VALUE);
        }
    }

    public void stop() throws Exception {
        if (this.client != null && this.client.verifyConnection()) {
            this.client.disconnect();
        }
        this.clientStopped = true;
        this.app.stop();
    }

    public void close() throws Exception {
        this.stop();
        this.client = null;
    }

    // -------------------------------------------
    // Accessors
    // -------------------------------------------

    public boolean isClientStopped() {
        return clientStopped;
    }

    public SocketModeClient getClient() {
        return client;
    }

    public App getApp() {
        return app;
    }
}
