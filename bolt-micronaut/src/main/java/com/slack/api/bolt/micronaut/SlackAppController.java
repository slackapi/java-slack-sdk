package com.slack.api.bolt.micronaut;

import com.slack.api.bolt.App;
import com.slack.api.bolt.request.Request;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;

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
    public HttpResponse<String> events(HttpRequest<String> request) throws Exception {
        String body = request.getBody().orElse(null);
        return adapt(request, body);
    }

    @Get("/install")
    public HttpResponse<String> install(HttpRequest<String> request) throws Exception {
        if (!slackApp.config().isOAuthInstallPathEnabled()) {
            return HttpResponse.notFound();
        }
        return adapt(request, null);
    }

    @Get("/oauth_redirect")
    public HttpResponse<String> oauthRedirect(HttpRequest<String> request) throws Exception {
        if (!slackApp.config().isOAuthRedirectUriPathEnabled()) {
            return HttpResponse.notFound();
        }
        return adapt(request, null);
    }

    private HttpResponse<String> adapt(HttpRequest<String> request, String body) throws Exception {
        Request<?> slackRequest = adapter.toSlackRequest(request, body);
        return adapter.toMicronautResponse(slackApp.run(slackRequest));
    }

}
