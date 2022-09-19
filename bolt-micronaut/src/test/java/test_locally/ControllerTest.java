package test_locally;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.micronaut.SlackAppController;
import com.slack.api.bolt.micronaut.SlackAppMicronautAdapter;
import io.micronaut.core.convert.DefaultConversionService;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpParameters;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.simple.SimpleHttpHeaders;
import io.micronaut.http.simple.SimpleHttpParameters;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.AuthTestMockServer;

import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControllerTest {

    private AuthTestMockServer slackApiServer;

    @Before
    public void setupServer() throws Exception {
        slackApiServer = new AuthTestMockServer();
        slackApiServer.start();
    }

    @After
    public void stopServer() throws Exception {
        if (slackApiServer != null) {
            slackApiServer.stop();
        }
    }

    @Test
    public void test() throws Exception {
        SlackConfig slackConfig = new SlackConfig();
        slackConfig.setMethodsEndpointUrlPrefix(slackApiServer.getMethodsEndpointPrefix());
        Slack slack = Slack.getInstance(slackConfig);
        AppConfig config = AppConfig.builder().slack(slack)
                .singleTeamBotToken(AuthTestMockServer.ValidToken)
                .signingSecret("secret")
                .build();

        App app = new App(config);
        SlackAppController controller = new SlackAppController(app, new SlackAppMicronautAdapter(config));

        assertNotNull(controller);

        HttpRequest<String> req = mock(HttpRequest.class);
        SimpleHttpHeaders headers = new SimpleHttpHeaders(new HashMap<>(), new DefaultConversionService());
        when(req.getHeaders()).thenReturn(headers);
        SimpleHttpParameters parameters = new SimpleHttpParameters(new HashMap<>(), new DefaultConversionService());
        when(req.getParameters()).thenReturn(parameters);

        HttpResponse<String> response = controller.events(req, "token=random&ssl_check=1");
        assertEquals(200, response.getStatus().getCode());
    }

    @Test
    public void oauthNotAvailableForStandardApp() throws Exception {
        SlackConfig slackConfig = new SlackConfig();
        slackConfig.setMethodsEndpointUrlPrefix(slackApiServer.getMethodsEndpointPrefix());
        Slack slack = Slack.getInstance(slackConfig);
        AppConfig config = AppConfig.builder().slack(slack)
                .singleTeamBotToken(AuthTestMockServer.ValidToken)
                .signingSecret("secret")
                .build();

        App app = new App(config);
        SlackAppController controller = new SlackAppController(app, new SlackAppMicronautAdapter(config));

        assertNotNull(controller);

        HttpRequest<String> req = HttpRequest.GET("/slack/install");

        HttpResponse<String> notFound = controller.oauth(req);
        assertThat(notFound.getStatus().getCode(), equalTo(404));
    }

    @Test
    public void oauth() throws Exception {
        SlackConfig slackConfig = new SlackConfig();
        slackConfig.setMethodsEndpointUrlPrefix(slackApiServer.getMethodsEndpointPrefix());
        Slack slack = Slack.getInstance(slackConfig);

        AppConfig config = AppConfig.builder()
                .slack(slack)
                .signingSecret("secret")
                .clientId("111.222")
                .clientSecret("cs")
                .scope("commands,chat:write")
                .oauthInstallPath("/slack/install")
                .oauthRedirectUriPath("/slack/oauth_redirect")
                .oauthCompletionUrl("https://www.example.com/success")
                .oauthCancellationUrl("https://www.example.com/failure")
                .oAuthInstallPageRenderingEnabled(false)
                .build();

        App oauthSlackApp = new App(config).asOAuthApp(true);
        SlackAppController controller = new SlackAppController(oauthSlackApp, new SlackAppMicronautAdapter(config));

        assertNotNull(controller);

        HttpRequest<String> req = HttpRequest.GET("/slack/install");

        HttpResponse<String> installResponse = controller.oauth(req);

        assertThat(installResponse.getStatus().getCode(), equalTo(302));

        String location = installResponse.header("Location");
        assertNotNull(location);
        assertTrue(location.startsWith(
                "https://slack.com/oauth/v2/authorize?client_id=111.222&scope=commands%2Cchat%3Awrite&user_scope=&state="));

        assertThat(installResponse.getStatus().getCode(), equalTo(302));

        String state = extractStateValue(location);

        MutableHttpRequest<String> redirectRequest = HttpRequest.GET("/slack/oauth_redirect");
        MutableHttpParameters parameters = redirectRequest.getParameters();
        parameters
                .add("code", "111.111.111")
                .add("state", state)
                .add("Cookie", "slack-app-oauth-state=" + state);

        HttpResponse<String> redirectResponse = controller.oauth(redirectRequest);
        assertThat(redirectResponse.header("Location"), equalTo("https://www.example.com/success"));
    }

    private static String extractStateValue(String location) {
        for (String element : location.split("&")) {
            if (element.trim().startsWith("state=")) {
                return element.trim().replaceFirst("state=", "");
            }
        }
        return null;
    }

}
