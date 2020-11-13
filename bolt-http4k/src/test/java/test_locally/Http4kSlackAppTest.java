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
import static org.http4k.core.Method.POST;

public class Http4kSlackAppTest {

    private static final String signingSecret = "secret";

    private AuthTestMockServer slackApiServer;
    private Http4kSlackApp http4kSlackApp;

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

        this.http4kSlackApp = new Http4kSlackApp(app);
    }

    @After
    public void tearDown() throws Exception {
        this.slackApiServer.stop();
    }

    private static Request buildRequest(String requestBody) {
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String signature = new SlackSignature.Generator(signingSecret).generate(timestamp, requestBody);

        return Request.Companion.create(POST, "/")
                .header(X_SLACK_REQUEST_TIMESTAMP, timestamp)
                .header(X_SLACK_SIGNATURE, signature)
                .header("Content-type", "application/json")
                .query("query", "queryValue")
                .body(requestBody);
    }

    @Test
    public void adaptsHttp4kToSlackInterface() {
        Response expected = Response.Companion
                .create(Status.OK)
                .body("{\"text\":\"" +
                        "query: query " +
                        "headers: content-type,x-slack-request-timestamp,x-slack-signature " +
                        "body: command\\u003decho" +
                        "\"}");

        Response response = http4kSlackApp.invoke(buildRequest("command=echo"));
        assertThat(response, equalTo(expected));
    }

    @Test
    public void emptyResponse() {
        Response expected = Response.Companion.create(Status.OK).body("");
        Response response = http4kSlackApp.invoke(buildRequest("command=do-nothing"));
        assertThat(response, equalTo(expected));
    }

    @Test
    public void noHandlerRequestGets404() {
        Response expected = Response.Companion
                .create(Status.NOT_FOUND)
                .body("{\"error\":\"no handler found\"}");

        Response response = http4kSlackApp.invoke(buildRequest("command=notaCommand"));
        assertThat(response, equalTo(expected));
    }

}
