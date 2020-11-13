package com.slack.api.bolt.http4k;

import com.slack.api.bolt.App;
import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.util.SlackRequestParser;
import kotlin.Pair;
import kotlin.jvm.functions.Function1;
import org.http4k.core.Request;
import org.http4k.core.Response;
import org.http4k.core.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static org.http4k.core.ParametersKt.toParameters;
import static org.http4k.core.Status.INTERNAL_SERVER_ERROR;

public class Http4kSlackApp implements Function1<Request, Response> {

    private static final Logger LOGGER = LoggerFactory.getLogger(Http4kSlackApp.class);

    private final App app;
    private final SlackRequestParser requestParser;

    public Http4kSlackApp(App app) {
        this.app = app;
        requestParser = new SlackRequestParser(app.config());
    }

    @Override
    public Response invoke(Request request) {
        try {
            return toHttp4kResponse(app.run(toSlackRequest(request)));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return Response.Companion.create(INTERNAL_SERVER_ERROR)
                    .header("Content-type", "application/json; charset=utf-8")
                    .body("{\"error\":\"An error occurred during processing of the request\"}");
        }
    }

    private com.slack.api.bolt.request.Request<?> toSlackRequest(Request httpRequest) {
        Map<String, List<String>> queryString = toParameters(httpRequest.getUri().getQuery())
                .stream()
                .collect(toMap(Pair::component1, it -> singletonList(it.component2())));
        RequestHeaders headers = new RequestHeaders(httpRequest.getHeaders()
                .stream()
                .collect(toMap(Pair::component1, it -> singletonList(it.component2()))));
        return requestParser.parse(SlackRequestParser.HttpRequest.builder()
                .requestUri(httpRequest.getUri().getPath())
                .queryString(queryString)
                .requestBody(httpRequest.bodyString())
                .headers(headers)
                .remoteAddress(httpRequest.header("X-Forwarded-For"))
                .build());
    }

    private Response toHttp4kResponse(com.slack.api.bolt.response.Response boltResponse) {
        Status status = new Status(boltResponse.getStatusCode(), "");
        List<Pair<String, String>> headers = boltResponse.getHeaders()
                .entrySet()
                .stream()
                .flatMap(it -> it.getValue().stream().map(it2 -> new Pair<>(it.getKey(), it2)))
                .collect(toList());
        if (boltResponse.getContentType() != null) {
            headers.add(new Pair<>("Content-Type", boltResponse.getContentType()));
        }
        String body = boltResponse.getBody();
        return Response.Companion
                .create(status)
                .headers(headers)
                // null here is not allowed by http4k
                .body(body == null ? "" : body);
    }
}
