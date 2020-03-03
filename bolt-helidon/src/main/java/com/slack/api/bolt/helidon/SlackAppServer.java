package com.slack.api.bolt.helidon;

import com.slack.api.bolt.App;
import io.helidon.config.Config;
import io.helidon.config.ConfigValue;
import io.helidon.webserver.Routing;
import io.helidon.webserver.ServerConfiguration;
import io.helidon.webserver.WebServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;

public class SlackAppServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(SlackAppServer.class);

    private final Config config;
    private final App apiApp;
    private final App oauthApp;

    private WebServer server;
    private int shutdownTimeoutSeconds = 10;
    private Function<Routing.Builder, Routing.Builder> additionalRoutingConfigurator = builder -> builder;

    public SlackAppServer(App apiApp) {
        this(Config.create(), apiApp, null);
    }

    public SlackAppServer(Config config, App apiApp) {
        this(config, apiApp, null);
    }

    public SlackAppServer(App apiApp, App oauthApp) {
        this(Config.create(), apiApp, oauthApp);
    }

    public SlackAppServer(Config config, App apiApp, App oauthApp) {
        this.config = config;
        this.apiApp = apiApp;
        this.oauthApp = oauthApp;

        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }

    public int getPort() {
        return server.configuration().port();
    }

    public WebServer start() {
        if (server == null) {
            ServerConfiguration serverConfig = ServerConfiguration.create(config.get("server"));
            server = WebServer.create(serverConfig, buildRouting(config));
        }
        server.start().thenAccept(ws -> {
            LOGGER.info("⚡️ Bolt app is running!");
            ws.whenShutdown().thenRun(() -> {
                LOGGER.info("⚡️ Your Bolt app has stopped...");
            });
        }).exceptionally(t -> {
            LOGGER.error("Failed to start the server: " + t.getMessage(), t);
            return null;
        });
        return server;
    }

    public void stop() throws ExecutionException, InterruptedException, TimeoutException {
        if (server.isRunning()) {
            server.shutdown().toCompletableFuture().get(shutdownTimeoutSeconds, TimeUnit.SECONDS);
        }
    }

    protected Routing buildRouting(Config config) {
        SlackAppService apiService = new SlackAppService(config, apiApp);
        ConfigValue<String> apiPathConfig = config.get("bolt.apiPath").asString();
        String apiPath = apiPathConfig.isPresent() ? apiPathConfig.get() : "/slack/events";
        Routing.Builder builder = Routing.builder().register(apiPath, apiService);
        if (oauthApp != null) {
            SlackAppService oauthService = new SlackAppService(config, oauthApp);
            builder = builder
                    .register(oauthApp.config().getOauthStartPath(), oauthService)
                    .register(oauthApp.config().getOauthCallbackPath(), oauthService);
        }
        return getAdditionalRoutingConfigurator().apply(builder).build();
    }

    // -----------------------

    public Function<Routing.Builder, Routing.Builder> getAdditionalRoutingConfigurator() {
        return additionalRoutingConfigurator;
    }

    public void setAdditionalRoutingConfigurator(Function<Routing.Builder, Routing.Builder> additionalRoutingConfigurator) {
        this.additionalRoutingConfigurator = additionalRoutingConfigurator;
    }

    public int getShutdownTimeoutSeconds() {
        return shutdownTimeoutSeconds;
    }

    public void setShutdownTimeoutSeconds(int shutdownTimeoutSeconds) {
        this.shutdownTimeoutSeconds = shutdownTimeoutSeconds;
    }
}
