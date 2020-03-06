package com.slack.api.bolt.micronaut;

import com.slack.api.bolt.App;
import com.slack.api.bolt.request.Request;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;

import java.util.LinkedHashMap;

/**
 * The default Web controller that works in Micronaut apps.
 * This component requires singleton {@link App} instance managed by the Micronaut DI container.
 *
 * @see <a href="https://guides.micronaut.io/creating-your-first-micronaut-app/guide/index.html">The official tutorial</a>
 * @see <a href="https://docs.micronaut.io/latest/api/io/micronaut/http/annotation/Controller.html">@Controller annotation</a>
 */
@Controller("/slack")
public class SlackAppController {

    private final App slackApp;
    private final SlackAppMicronautAdapter adapter;

    public SlackAppController(App slackApp, SlackAppMicronautAdapter adapter) {
        this.slackApp = slackApp;
        this.adapter = adapter;
    }

    @Post(value = "/events", consumes = {MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON})
    public HttpResponse<String> dispatch(HttpRequest<String> request, @Body LinkedHashMap<String, String> body) throws Exception {
        Request<?> slackRequest = adapter.toSlackRequest(request, body);
        return adapter.toMicronautResponse(slackApp.run(slackRequest));
    }

}
