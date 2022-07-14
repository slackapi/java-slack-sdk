package test_locally;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.app_backend.SlackSignature;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.http4k.Http4kSlackApp;
import org.http4k.core.Request;
import org.http4k.core.Response;
import org.http4k.core.Status;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.AuthTestMockServer;

import static com.slack.api.app_backend.SlackSignature.HeaderNames.X_SLACK_REQUEST_TIMESTAMP;
import static com.slack.api.app_backend.SlackSignature.HeaderNames.X_SLACK_SIGNATURE;
import static java.util.stream.Collectors.joining;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.http4k.core.Method.GET;
import static org.http4k.core.Method.POST;
import static org.junit.Assert.assertTrue;

public class Http4kSlackAppTest {

    private static final String signingSecret = "secret";

    private AuthTestMockServer slackApiServer;
    private Http4kSlackApp simpleSlackApp;
    private Http4kSlackApp oauthSlackApp;

    @Before
    public void setUp() throws Exception {
        this.slackApiServer = new AuthTestMockServer();
        this.slackApiServer.start();

        SlackConfig slackConfig = new SlackConfig();
        slackConfig.setMethodsEndpointUrlPrefix(this.slackApiServer.getMethodsEndpointPrefix());

        App app = new App(AppConfig.builder()
                .slack(Slack.getInstance(slackConfig))
                .singleTeamBotToken(AuthTestMockServer.ValidToken)
                .signingSecret(signingSecret)
                .build());

        app.command("echo", (req, ctx) -> ctx.ack(r -> {
            String query = req.getQueryString().keySet().stream().collect(joining(","));
            String headers = req.getHeaders().getNames().stream().collect(joining(","));
            String body = req.getRequestBodyAsString();
            return r.text("query: " + query + " headers: " + headers + " body: " + body);
        }));

        app.command("do-nothing", (req, ctx) -> ctx.ack());

        this.simpleSlackApp = new Http4kSlackApp(app);

        App oauthApp = new App(AppConfig.builder()
                .slack(Slack.getInstance(slackConfig))
                .signingSecret(signingSecret)
                .clientId("111.222")
                .clientSecret("cs")
                .scope("commands,chat:write")
                .oauthInstallPath("/slack/install")
                .oauthRedirectUriPath("/slack/oauth_redirect")
                .oauthCompletionUrl("https://www.example.com/success")
                .oauthCancellationUrl("https://www.example.com/failure")
                .oAuthInstallPageRenderingEnabled(false)
                .build()
        ).asOAuthApp(true);
        this.oauthSlackApp = new Http4kSlackApp(oauthApp);
    }

    @After
    public void tearDown() throws Exception {
        this.slackApiServer.stop();
    }

    private static Request slackEventRequest(String requestBody) {
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String signature = new SlackSignature.Generator(signingSecret).generate(timestamp, requestBody);

        return Request.Companion.create(POST, "/slack/events")
                .header(X_SLACK_REQUEST_TIMESTAMP, timestamp)
                .header(X_SLACK_SIGNATURE, signature)
                .header("Content-Type", "application/json")
                .query("query", "queryValue")
                .body(requestBody);
    }

    private static Request invalidPathSlackEventRequest(String requestBody) {
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String signature = new SlackSignature.Generator(signingSecret).generate(timestamp, requestBody);

        return Request.Companion.create(POST, "/slack/events/foo/bar")
                .header(X_SLACK_REQUEST_TIMESTAMP, timestamp)
                .header(X_SLACK_SIGNATURE, signature)
                .header("Content-Type", "application/json")
                .query("query", "queryValue")
                .body(requestBody);
    }

    @Test
    public void slackEvents() {
        Response expected = Response.Companion
                .create(Status.OK)
                .header("Content-Type", "application/json; charset=utf-8")
                .body("{\"text\":\"" +
                        "query: query " +
                        "headers: content-type,x-slack-request-timestamp,x-slack-signature " +
                        "body: command\\u003decho" +
                        "\"}");

        Response response = simpleSlackApp.invoke(slackEventRequest("command=echo"));
        assertThat(response, equalTo(expected));
    }

    @Test
    public void slackEvents_invalidPath() {
        Response expected = Response.Companion
                .create(Status.NOT_FOUND)
                .header("Content-Type", "text/plain; charset=utf-8")
                .body("Not Found");
        Response response = simpleSlackApp.invoke(invalidPathSlackEventRequest("command=echo"));
        assertThat(response, equalTo(expected));
    }

    @Test
    public void slackEvents_commandNotFound() {
        Response expected = Response.Companion
                .create(Status.NOT_FOUND)
                .header("Content-Type", "application/json; charset=utf-8")
                .body("{\"error\":\"no handler found\"}");

        Response response = simpleSlackApp.invoke(slackEventRequest("command=notaCommand"));
        assertThat(response, equalTo(expected));
    }

    @Test
    public void workWithEmptyOrNullResponseBody() {
        Response expected = Response.Companion.create(Status.OK).header("Content-Type", "text/plain").body("");
        Response response = simpleSlackApp.invoke(slackEventRequest("command=do-nothing"));
        assertThat(response, equalTo(expected));
    }

    @Test
    public void oauth() {
        Request installRequest = Request.Companion.create(GET, "/slack/install");

        Response notFound = simpleSlackApp.invoke(installRequest);
        assertThat(notFound.getStatus().getCode(), equalTo(404));

        Response installResponse = oauthSlackApp.invoke(installRequest);
        assertThat(installResponse.getStatus().getCode(), equalTo(302));
        assertTrue(installResponse.header("Location").startsWith(
                "https://slack.com/oauth/v2/authorize?client_id=111.222&scope=commands%2Cchat%3Awrite&user_scope=&state="));

        assertThat(installResponse.getStatus().getCode(), equalTo(302));

        String state = extractStateValue(installResponse.header("Location"));
        Request redirectRequest = Request.Companion.create(GET, "/slack/oauth_redirect")
                .query("code", "111.111.111")
                .query("state", state)
                .header("Cookie", "slack-app-oauth-state=" + state);
        Response redirectResponse = oauthSlackApp.invoke(redirectRequest);
        assertThat(redirectResponse.header("Location"), equalTo("https://www.example.com/success"));
    }

    private static String extractStateValue(String location) {
        for (String element: location.split("&")) {
            if (element.trim().startsWith("state=")) {
                return element.trim().replaceFirst("state=", "");
            }
        }
        return null;
    }
}
