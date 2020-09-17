package com.slack.api.bolt.helidon;

import com.slack.api.bolt.App;
import com.slack.api.bolt.request.Request;
import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.response.Response;
import com.slack.api.bolt.util.SlackRequestParser;
import io.helidon.config.Config;
import io.helidon.webserver.Routing;
import io.helidon.webserver.ServerRequest;
import io.helidon.webserver.ServerResponse;
import io.helidon.webserver.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * A {@link io.helidon.webserver.Service} that runs a Bolt app on Helidon SE.
 *
 * <pre>
 * Config config = Config.create();
 * ServerConfiguration serverConfig = ServerConfiguration.create(config.get("server"));
 * SlackAppService apiService = new SlackAppService(config, buildApiApp());
 * SlackAppService oauthService = new SlackAppService(config, buildOAuthApp());
 * Routing routes = Routing.builder()
 *     .register("/slack/events", apiService)
 *     .register(oauth.config().getOauthStartPath(), oauthService)
 *     .register(oauth.config().getOauthCallbackPath(), oauthService)
 *     .build();
 * WebServer server = WebServer.create(serverConfig, routes);
 * server.start();
 * </pre>
 */
public class SlackAppService implements Service {

    private static final Logger LOGGER = LoggerFactory.getLogger(SlackAppService.class);

    private final Config config;
    private final App app;
    private final SlackRequestParser requestParser;

    public SlackAppService(Config config, App app) {
        this.config = config;
        this.app = app;
        this.requestParser = new SlackRequestParser(app.config());
    }

    @Override
    public void update(Routing.Rules rules) {
        if (app.config().isDistributedApp()) {
            // for OAuth flow
            rules.get("/", this::perform);
        }
        // for handling incoming requests from Slack API
        rules.post("/", this::perform);
    }

    public void perform(ServerRequest request, ServerResponse response) {
        request.content().as(String.class)
                .thenAccept(requestBody -> runSlackApp(request, requestBody, response));
    }

    public void runSlackApp(ServerRequest request, String requestBody, ServerResponse response) {
        try {
            Request<?> slackRequest = buildSlackRequest(request, requestBody);
            Response slackResponse = app.run(slackRequest);
            writeToHelidonResponse(response, slackResponse);
        } catch (Exception e) {
            LOGGER.error("Failed to handle a request (error: {})", e.getMessage(), e);
            response.status(500);
            response.headers().put("Content-Type", "application/json");
            response.send("{\"error\":\"Something is wrong\"}");
        }
    }

    public Request<?> buildSlackRequest(ServerRequest request, String requestBody) {
        String requestUri = request.uri() != null ? request.uri().getRawPath() : "/";
        Map<String, List<String>> query = request.queryParams() != null
                ? request.queryParams().toMap() : Collections.emptyMap();
        RequestHeaders headers = request.headers() != null
                ? new RequestHeaders(request.headers().toMap()) : new RequestHeaders(Collections.emptyMap());

        SlackRequestParser.HttpRequest httpRequest = SlackRequestParser.HttpRequest.builder()
                .requestUri(requestUri)
                .queryString(query)
                .requestBody(requestBody)
                .headers(headers)
                .remoteAddress(request.remoteAddress())
                .build();
        return requestParser.parse(httpRequest);
    }

    public void writeToHelidonResponse(ServerResponse response, Response slackResponse) {
        response.status(slackResponse.getStatusCode());
        for (Map.Entry<String, List<String>> header : slackResponse.getHeaders().entrySet()) {
            response.headers().add(header.getKey(), header.getValue());
        }
        response.headers().put("Content-Type", slackResponse.getContentType());
        String body = slackResponse.getBody();
        if (body == null) {
            body = "";
        }
        response.send(body);
    }
}
