package com.slack.api.bolt.socket_mode;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.slack.api.bolt.App;
import com.slack.api.bolt.response.Response;
import com.slack.api.bolt.socket_mode.request.SocketModeRequest;
import com.slack.api.bolt.socket_mode.request.SocketModeRequestParser;
import com.slack.api.socket_mode.SocketModeClient;
import com.slack.api.socket_mode.response.AckResponse;
import com.slack.api.util.json.GsonFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class SocketModeApp {
    private boolean clientStopped = false;
    private final SocketModeClient client;
    private final App app;

    private static SocketModeClient buildSocketModeClient(
            App app,
            String appToken,
            SocketModeClient.Backend backend
    ) throws IOException {
        final SocketModeClient client = app.slack().socketMode(appToken, backend);
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
                    long spentMillis = System.currentTimeMillis() - startMillis;
                    log.debug("Response time: {} milliseconds", spentMillis);
                    return;
                } catch (Exception e) {
                    log.error("Failed to handle a request: {}", e.getMessage(), e);
                    return;
                }
            }
        });
        return client;
    }

    public SocketModeApp(String appToken, App app) throws IOException {
        this(appToken, SocketModeClient.Backend.Tyrus, app);
    }

    public SocketModeApp(
            String appToken,
            SocketModeClient.Backend backend,
            App app
    ) throws IOException {
        this(buildSocketModeClient(app, appToken, backend), app);
    }

    public SocketModeApp(SocketModeClient socketModeClient, App app) {
        this.client = socketModeClient;
        this.app = app;
    }

    public void start() throws Exception {
        run(true);
    }

    public void startAsync() throws Exception {
        run(false);
    }

    public void run(boolean blockCurrentThread) throws Exception {
        app.start();
        if (clientStopped) {
            client.connectToNewEndpoint();
        } else {
            client.connect();
        }
        client.setAutoReconnectEnabled(true);
        if (blockCurrentThread) {
            Thread.sleep(Long.MAX_VALUE);
        }
    }

    public void stop() throws Exception {
        client.disconnect();
        clientStopped = true;
        app.stop();
    }
}
