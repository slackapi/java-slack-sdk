package com.github.seratch.jslack.lightning.micronaut;

import com.github.seratch.jslack.lightning.App;
import com.github.seratch.jslack.lightning.AppConfig;
import com.github.seratch.jslack.lightning.request.Request;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;

import java.util.LinkedHashMap;

@Controller("/slack")
public class SlackAppController {

    private final App slackApp;
    private final SlackAppMicronautAdapter adapter;

    public SlackAppController(App app, AppConfig config) {
        slackApp = app;
        adapter = new SlackAppMicronautAdapter(config);
    }

    @Post(value = "/events", consumes = {MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON})
    public HttpResponse<String> dispatch(HttpRequest<String> request, @Body LinkedHashMap<String, String> body) throws Exception {
        Request<?> slackRequest = adapter.toSlackRequest(request, body);
        return adapter.toMicronautResponse(slackApp.run(slackRequest));
    }

}
