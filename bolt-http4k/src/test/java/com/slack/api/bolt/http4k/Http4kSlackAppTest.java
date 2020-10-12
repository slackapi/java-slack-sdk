package com.slack.api.bolt.http4k;

import com.slack.api.bolt.App;
import org.http4k.core.Request;
import org.http4k.core.Response;
import org.http4k.core.Status;
import org.junit.Test;

import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.http4k.core.Method.GET;
import static org.http4k.core.Method.POST;

public class Http4kSlackAppTest {

    @Test
    public void adaptsHttp4kToSlackInterface() {
        Http4kSlackApp http4kSlackApp = new Http4kSlackApp(createApp());
        Response invoke = http4kSlackApp.invoke(
                Request.Companion.create(POST, "/echo")
                        .header("header1", "headerValue1")
                        .query("query1", "queryValue1")
                        .body("{\"foo\":true}")
        );

        assertThat(invoke, equalTo(Response.Companion.create(Status.OK).body("{}")));
    }

    @Test
    public void invalidRequestGets400() {
        Http4kSlackApp http4kSlackApp = new Http4kSlackApp(createApp());
        Response invoke = http4kSlackApp.invoke(
                Request.Companion.create(GET, "/not a thing")
                        .body("{}")
        );

        assertThat(invoke, equalTo(
                Response.Companion.create(Status.BAD_REQUEST)
                        .body("Invalid Request")
                )
        );
    }

    private App createApp() {
        App app = new App();
        app.command("/echo", (req, ctx) -> ctx.ack(r -> {
            String query = req.getQueryString().keySet().stream().collect(Collectors.joining());
            String headers = req.getHeaders().getNames().stream().collect(Collectors.joining());
            String body = req.getRequestBodyAsString();
            return r.text(query + headers + body);
        }));
        return app;
    }
}
