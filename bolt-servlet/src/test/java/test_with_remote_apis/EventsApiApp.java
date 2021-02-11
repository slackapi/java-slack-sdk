package test_with_remote_apis;

import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.response.Response;
import com.slack.api.model.event.*;
import util.ResourceLoader;
import util.TestSlackAppServer;

public class EventsApiApp {

    public static void main(String[] args) throws Exception {
        AppConfig appConfig = ResourceLoader.loadAppConfig();
        App app = new App(appConfig);
        app.endpoint("/test", (req, ctx) -> Response.builder().body("Hi!").contentType("text/plain").build());
        TestSlackAppServer server = new TestSlackAppServer(app);
        app.event(MessageEvent.class, (req, ctx) -> ctx.ack());
        app.event(MessageBotEvent.class, (req, ctx) -> ctx.ack());
        app.event(MessageChangedEvent.class, (req, ctx) -> ctx.ack());
        app.event(MessageChannelJoinEvent.class, (req, ctx) -> ctx.ack());
        app.event(ChannelSharedEvent.class, (req, ctx) -> ctx.ack());
        app.event(ChannelJoinedEvent.class, (req, ctx) -> ctx.ack());
        app.event(ChannelIdChangedEvent.class, (req, ctx) -> ctx.ack());
        app.event(MemberJoinedChannelEvent.class, (req, ctx) -> ctx.ack());
        server.start();
    }
}
