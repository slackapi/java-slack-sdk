package com.slack.api.bolt.google_cloud_functions;

import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;
import com.slack.api.bolt.App;
import com.slack.api.bolt.request.Request;
import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.response.Response;
import com.slack.api.bolt.util.SlackRequestParser;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.joining;

/**
 * An HttpFunction that handles incoming requests from Slack.
 */
public class SlackApiFunction implements HttpFunction {

    private final App app;
    private final SlackRequestParser requestParser;

    public SlackApiFunction(App app) {
        this.app = app;
        this.requestParser = new SlackRequestParser(this.app.config());
    }

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception {
        Request boltRequest = toBoltRequest(httpRequest);
        Response boltResponse = this.app.run(boltRequest);
        writeResponse(boltResponse, httpResponse);
    }

    private Request toBoltRequest(HttpRequest httpRequest) throws IOException {
        String body = httpRequest.getReader().lines().collect(joining());
        SlackRequestParser.HttpRequest req = SlackRequestParser.HttpRequest.builder()
                .requestUri(httpRequest.getPath())
                .queryString(httpRequest.getQueryParameters())
                .requestBody(body)
                .headers(new RequestHeaders(httpRequest.getHeaders()))
                .remoteAddress(httpRequest.getFirstHeader("X-Forwarded-For").orElse(null))
                .build();
        return this.requestParser.parse(req);
    }

    private void writeResponse(Response boltResponse, HttpResponse httpResponse) throws IOException {
        httpResponse.setStatusCode(boltResponse.getStatusCode());
        httpResponse.setContentType(boltResponse.getContentType());
        for (Map.Entry<String, List<String>> nameAndValues : boltResponse.getHeaders().entrySet()) {
            String headerName = nameAndValues.getKey();
            for (String value : nameAndValues.getValue()) {
                httpResponse.appendHeader(headerName, value);
            }
        }
        httpResponse.getWriter().write(boltResponse.getBody());
    }
}

