package test_locally;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.app_backend.SlackSignature;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.helidon.SlackAppService;
import io.helidon.config.Config;
import io.helidon.webserver.*;
import org.junit.Test;
import util.HashRequestHeaders;
import util.PortProvider;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class SlackAppServiceTest {

    static int randomPort() {
        return PortProvider.getPort(MainTest.class.getCanonicalName());
    }

    static class AuthTestService implements Service {
        @Override
        public void update(Routing.Rules rules) {
            rules.post("/auth.test", (req, res) -> {
                res.status(200);
                res.headers().put("Content-Type", "application/json");
                res.send("{\n" +
                        "  \"ok\": true,\n" +
                        "  \"url\": \"https://java-slack-sdk-test.slack.com/\",\n" +
                        "  \"team\": \"java-slack-sdk-test\",\n" +
                        "  \"user\": \"test_user\",\n" +
                        "  \"team_id\": \"T1234567\",\n" +
                        "  \"user_id\": \"U1234567\",\n" +
                        "  \"bot_id\": \"B12345678\",\n" +
                        "  \"enterprise_id\": \"E12345678\"\n" +
                        "}");
            });
        }
    }

    WebServer slackApiServer() {
        return WebServer
                .builder(Routing.builder().register("/api", new AuthTestService()).build())
                .config(ServerConfiguration.builder().port(randomPort()).build())
                .build();
    }

    @Test
    public void sslCheck() {
        App app = new App(AppConfig.builder()
                .singleTeamBotToken("xoxb-xxxx")
                .signingSecret("secret")
                .build());
        SlackAppService service = new SlackAppService(Config.create(), app);
        assertNotNull(service);

        ServerRequest request = mock(ServerRequest.class);
        ServerResponse response = mock(ServerResponse.class);
        ResponseHeaders headers = mock(ResponseHeaders.class);
        when(response.headers()).thenReturn(headers);

        service.runSlackApp(request, "token=xxx&ssl_check=1", response);

        verify(response, times(1)).status(200);
    }

    String slashCommandPayload = "token=gIkuvaNzQIHg97ATvDxqgjtO" +
            "&team_id=T0001" +
            "&team_domain=example" +
            "&enterprise_id=E0001" +
            "&enterprise_name=Globular%20Construct%20Inc" +
            "&channel_id=C2147483705" +
            "&channel_name=test" +
            "&user_id=U2147483697" +
            "&user_name=Steve" +
            "&command=/weather" +
            "&text=94070" +
            "&response_url=https://hooks.slack.com/commands/1234/5678" +
            "&trigger_id=13345224609.738474920.8088930838d88f008e0";

    @Test
    public void error() throws InterruptedException, ExecutionException, TimeoutException {
        WebServer slackApiServer = slackApiServer();
        slackApiServer.start();

        try {
            SlackConfig config = new SlackConfig();
            config.setMethodsEndpointUrlPrefix("http://localhost:" + slackApiServer.configuration().port() + "/api");
            App app = new App(AppConfig.builder()
                    .singleTeamBotToken("xoxb-xxxx")
                    .signingSecret("secret")
                    .slack(Slack.getInstance(config))
                    .build());
            app.command("/weather", (req, ctx) -> {
                throw new RuntimeException("intentional error here");
            });

            SlackAppService service = new SlackAppService(Config.create(), app);
            assertNotNull(service);


            ServerRequest request = mock(ServerRequest.class);
            Map<String, List<String>> rawRequestHeaders = new HashMap<>();
            String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
            String signature = new SlackSignature.Generator("secret").generate(timestamp, slashCommandPayload);
            rawRequestHeaders.put(SlackSignature.HeaderNames.X_SLACK_REQUEST_TIMESTAMP, Arrays.asList(timestamp));
            rawRequestHeaders.put(SlackSignature.HeaderNames.X_SLACK_SIGNATURE, Arrays.asList(signature));
            RequestHeaders requestHeaders = new HashRequestHeaders(rawRequestHeaders);
            when(request.headers()).thenReturn(requestHeaders);
            ServerResponse response = mock(ServerResponse.class);
            ResponseHeaders headers = mock(ResponseHeaders.class);
            when(response.headers()).thenReturn(headers);

            service.runSlackApp(request, slashCommandPayload, response);

            verify(response, times(1)).status(500);

        } finally {
            slackApiServer.shutdown().toCompletableFuture().get(10, TimeUnit.SECONDS);
        }
    }

    @Test
    public void invalid() {
        App app = new App(AppConfig.builder()
                .singleTeamBotToken("xoxb-xxxx")
                .signingSecret("secret")
                .build());
        SlackAppService service = new SlackAppService(Config.create(), app);
        assertNotNull(service);

        ServerRequest request = mock(ServerRequest.class);
        ServerResponse response = mock(ServerResponse.class);
        ResponseHeaders headers = mock(ResponseHeaders.class);
        when(response.headers()).thenReturn(headers);

        service.runSlackApp(request, "something invalid", response);

        verify(response, times(1)).status(400);
    }

}
