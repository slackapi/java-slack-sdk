package com.slack.api.bolt.http4k;

import com.slack.api.app_backend.SlackSignature;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import org.http4k.core.Request;
import org.http4k.core.Response;
import org.http4k.core.Status;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.stream.Collectors;

import static com.slack.api.app_backend.SlackSignature.HeaderNames.X_SLACK_REQUEST_TIMESTAMP;
import static com.slack.api.app_backend.SlackSignature.HeaderNames.X_SLACK_SIGNATURE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.http4k.core.Method.POST;

public class Http4kSlackAppTest {

    private final String signingSecret = "secret";
    private final Http4kSlackApp http4kSlackApp = new Http4kSlackApp(createApp());

    @Test
    public void adaptsHttp4kToSlackInterface() {
        Response invoke = http4kSlackApp.invoke(request("command=echo"));

        assertThat(invoke, equalTo(Response.Companion.create(Status.OK).body("" +
                "{\"text\":\"querycontent-typex-slack-request-timestampx-slack-signaturecommand\\u003decho\"}")));
    }

    @NotNull
    private Request request(String requestBody) {
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);

        return Request.Companion.create(POST, "/")
                .header(X_SLACK_REQUEST_TIMESTAMP, timestamp)
                .header(X_SLACK_SIGNATURE, new SlackSignature.Generator(signingSecret)
                        .generate(timestamp, requestBody))
                .header("Content-type", "application/json")
                .query("query", "queryValue")
                .body(requestBody);
    }

    @Test
    public void noHandlerRequestGets404() {
        Response invoke = http4kSlackApp.invoke(request("command=notaCommand"));

        assertThat(invoke, equalTo(
                Response.Companion.create(Status.NOT_FOUND)
                        .body("{\"error\":\"no handler found\"}")
                )
        );
    }

    private App createApp() {
        App app = new App(
                AppConfig.builder()
                        .signingSecret(signingSecret)
                        .build()
        );
        app.command("echo", (req, ctx) -> ctx.ack(r -> {
            String query = req.getQueryString().keySet().stream().collect(Collectors.joining());
            String headers = req.getHeaders().getNames().stream().collect(Collectors.joining());
            String body = req.getRequestBodyAsString();
            return r.text(query + headers + body);
        }));
        return app;
    }
}
