package samples;

import com.slack.api.SlackConfig;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.response.Response;
import com.slack.api.bolt.service.builtin.oauth.OAuthV2AccessErrorHandler;
import com.slack.api.model.event.AppMentionEvent;
import com.slack.api.util.json.GsonFactory;
import lombok.extern.slf4j.Slf4j;
import util.TestSlackAppServer;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class OAuthCallbacksSample {

    public static void main(String[] args) throws Exception {
        String jsonString = Files.readAllLines(Paths.get("bolt-servlet/src/test/resources/appConfig_oauth.json"))
                .stream().collect(Collectors.joining("\n"));
        AppConfig config = GsonFactory.createCamelCase(SlackConfig.DEFAULT).fromJson(jsonString, AppConfig.class);
        App app = new App(config).asOAuthApp(true);
        app.initialize();

        app.event(AppMentionEvent.class, (event, ctx) -> {
            ctx.logger.info("new mention! - {}", event);
            ctx.say("Hi there!");
            return ctx.ack();
        });

        Map<String, App> apps = new HashMap<>();
        apps.put("/slack/events", app);

        App oauthApp = new App(config);

        // in the case where the "error" parameter is passed from the Slack OAuth flow
        oauthApp.oauthCallbackError((request, response) -> {
            response.setStatusCode(401);
            response.setContentType("text/plain; charset=utf-8");
            response.setBody("Something wrong! (" + request.getPayload().getError() + ")");
            return response;
        });

        // in the case where the "state" parameter is invalid
        oauthApp.oauthCallbackStateError((request, response) -> {
            response.setStatusCode(401);
            response.setContentType("text/plain; charset=utf-8");
            response.setBody("Something wrong! (state is invalid)");
            return response;
        });

        // in the case where an error code is returned from the oauth.v2.access API
        oauthApp.oauthCallbackAccessError((OAuthV2AccessErrorHandler) (request, response, apiResponse) -> {
            response.setStatusCode(401);
            response.setContentType("text/plain; charset=utf-8");
            response.setBody("Something wrong! (" + apiResponse.getError() + ")");
            return response;
        });

        // To customize the behavior after the installation persistence:
        // The default behavior is to display the default completion web page
        // or to redirect the installer to the pre-given completion URL.
        oauthApp.oauthPersistenceCallback(arguments -> {
            Response response = arguments.getResponse();
            response.setStatusCode(200);
            response.setContentType("text/plain; charset=utf-8");
            response.setBody("OK!");
        });

        // To customize the behavior when the installation persistence fails:
        // The default behavior is to display the default error web page
        // or to redirect the installer to the pre-given failure URL.
        oauthApp.oauthPersistenceErrorCallback(arguments -> {
            Response response = arguments.getResponse();
            response.setStatusCode(500);
            response.setContentType("text/plain; charset=utf-8");
            response.setBody("Something wrong! (" + arguments.getError().getMessage() + ")");
        });

        apps.put("/slack/oauth/", oauthApp.asOAuthApp(true));
        TestSlackAppServer server = new TestSlackAppServer(apps);
        server.start();
    }

}
