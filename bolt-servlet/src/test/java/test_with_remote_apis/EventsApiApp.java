package test_with_remote_apis;

import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.response.Response;
import util.ResourceLoader;
import util.TestSlackAppServer;

public class EventsApiApp {

    public static void main(String[] args) throws Exception {
        AppConfig appConfig = ResourceLoader.loadAppConfig();
        App app = new App(appConfig);
        app.endpoint("/test", (req, ctx) -> Response.builder().body("Hi!").contentType("text/plain").build());
        TestSlackAppServer server = new TestSlackAppServer(app);
        server.start();
    }
}
