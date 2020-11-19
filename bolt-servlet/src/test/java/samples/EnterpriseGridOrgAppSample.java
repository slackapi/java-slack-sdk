package samples;

import com.slack.api.SlackConfig;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.util.json.GsonFactory;
import util.TestSlackAppServer;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class EnterpriseGridOrgAppSample {

    public static void main(String[] args) throws Exception {
        String jsonString = Files.readAllLines(Paths.get("bolt-servlet/src/test/resources/appConfig_org_app.json"))
                .stream().collect(Collectors.joining("\n"));
        AppConfig appConfig = GsonFactory.createCamelCase(SlackConfig.DEFAULT).fromJson(jsonString, AppConfig.class);
        App app = new App(appConfig).asOAuthApp(true);
        TestSlackAppServer server = new TestSlackAppServer(app, "/slack/oauth/");
        server.start();
    }
}
