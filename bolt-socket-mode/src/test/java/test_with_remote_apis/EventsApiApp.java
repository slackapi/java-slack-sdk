package test_with_remote_apis;

import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.socket_mode.SocketModeApp;
import com.slack.api.model.event.*;
import util.ResourceLoader;

public class EventsApiApp {

    public static void main(String[] args) throws Exception {
        AppConfig appConfig = ResourceLoader.loadAppConfig();
        App app = new App(appConfig);
        SocketModeApp socketModeClient = new SocketModeApp(app);
        app.event(MessageEvent.class, (req, ctx) -> ctx.ack());
        app.event(MessageBotEvent.class, (req, ctx) -> ctx.ack());
        app.event(MessageChangedEvent.class, (req, ctx) -> ctx.ack());
        app.event(MessageChannelJoinEvent.class, (req, ctx) -> ctx.ack());
        app.event(ChannelSharedEvent.class, (req, ctx) -> ctx.ack());
        app.event(ChannelJoinedEvent.class, (req, ctx) -> ctx.ack());
        app.event(ChannelIdChangedEvent.class, (req, ctx) -> ctx.ack());
        app.event(MemberJoinedChannelEvent.class, (req, ctx) -> ctx.ack());
        socketModeClient.start();
    }
}
