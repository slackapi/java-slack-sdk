package test_locally;

import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.helidon.SlackAppServer;
import com.slack.api.model.event.AppMentionEvent;
import io.helidon.health.HealthSupport;
import io.helidon.health.checks.HealthChecks;
import io.helidon.metrics.MetricsSupport;

public final class Main {

    public static void main(final String[] args) {
        startServer(new AppConfig());
    }

    public static SlackAppServer startServer(AppConfig config) {
        SlackAppServer server = new SlackAppServer(apiApp(config), oauthApp(config));
        server.setAdditionalRoutingConfigurator(builder -> builder
                .register(MetricsSupport.create())
                .register(HealthSupport.builder().addLiveness(HealthChecks.healthChecks()).build()));
        server.start();
        return server;
    }

    public static App apiApp(AppConfig config) {
        App app = new App(config);
        app.command("/hello", (req, ctx) -> ctx.ack(":wave: Hi!"));
        app.event(AppMentionEvent.class, (event, ctx) -> {
            ctx.say("May I help you?");
            return ctx.ack();
        });
        return app;
    }

    public static App oauthApp(AppConfig config) {
        App app = new App(config).asOAuthApp(true);
        return app;
    }
}
