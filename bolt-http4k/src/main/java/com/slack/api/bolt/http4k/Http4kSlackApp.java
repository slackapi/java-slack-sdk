package com.slack.api.bolt.http4k;

import com.slack.api.bolt.App;
import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.util.SlackRequestParser;
import kotlin.Pair;
import kotlin.jvm.functions.Function1;
import org.http4k.core.Method;
import org.http4k.core.Request;
import org.http4k.core.Response;
import org.http4k.core.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static org.http4k.core.ParametersKt.toParameters;
import static org.http4k.core.Status.INTERNAL_SERVER_ERROR;
import static org.http4k.core.Status.NOT_FOUND;

public class Http4kSlackApp implements Function1<Request, Response> {

    protected static final Logger LOGGER = LoggerFactory.getLogger(Http4kSlackApp.class);

    protected final App app;
    protected final String path;
    protected final SlackRequestParser requestParser;

    public Http4kSlackApp(App app) {
        this(app, "/slack/events");
    }

    public Http4kSlackApp(App app, String path) {
        this.app = app;
        this.path = path;
        requestParser = new SlackRequestParser(app.config());
    }

    @Override
    public Response invoke(Request request) {
        Method requestMethod = request.getMethod();
        String requestPath = request.getUri().getPath();
        if (requestMethod == null) {
            // checking this, just in case
            return notFound(request);
        }
        if (requestMethod == Method.POST && requestPath.equals(getSlackEventsRequestURI())) {
            return handleEventRequest(request);
        } else if (requestMethod == Method.GET) {
            if (app.config().isOAuthInstallPathEnabled()
                    && requestPath.equals(app.config().getOauthInstallRequestURI())) {
                return handleOAuthRequest(request);
            } else if (app.config().isOAuthInstallPathEnabled()
                    && requestPath.equals(app.config().getOauthRedirectUriRequestURI())) {
                return handleOAuthRequest(request);
            }
        }
        return notFound(request);
    }

    // ----------------------------------------------------------

    protected String getSlackEventsRequestURI() {
        if (app.config().getAppPath() != null) {
            return (app.config().getAppPath() + this.path).replaceAll("//", "/");
        }
        return this.path;
    }

    protected Response handleEventRequest(Request request) {
        try {
            return toHttp4kResponse(app.run(toSlackRequest(request)));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return buildEventErrorResponse(request, e);
        }
    }

    protected Response handleOAuthRequest(Request request) {
        try {
            return toHttp4kResponse(app.run(toSlackRequest(request)));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return buildOAuthPageErrorResponse(request, e);
        }
    }

    protected Response buildEventErrorResponse(Request request, Exception e) {
        return Response.Companion.create(INTERNAL_SERVER_ERROR)
                .header("Content-Type", "application/json; charset=utf-8")
                .body("{\"error\":\"An error occurred during processing of the request\"}");
    }

    protected Response buildOAuthPageErrorResponse(Request request, Exception e) {
        return Response.Companion.create(INTERNAL_SERVER_ERROR)
                .header("Content-Type", "text/plain; charset=utf-8")
                .body("Internal Server Error");
    }

    protected Response notFound(Request request) {
        return Response.Companion.create(NOT_FOUND)
                .header("Content-Type", "text/plain; charset=utf-8")
                .body("Not Found");
    }

    protected com.slack.api.bolt.request.Request<?> toSlackRequest(Request httpRequest) {
        Map<String, List<String>> queryString = toParameters(httpRequest.getUri().getQuery())
                .stream()
                .collect(toMap(Pair::component1, it -> singletonList(it.component2())));

        Map<String, List<String>> underlyingHeaders = new HashMap<>();
        for (Pair<String, String> header : httpRequest.getHeaders()) {
            String name = header.getFirst();
            List<String> values = underlyingHeaders.getOrDefault(name, new ArrayList<>());
            values.add(header.getSecond());
            underlyingHeaders.put(name, values);
        }
        RequestHeaders headers = new RequestHeaders(underlyingHeaders);
        return requestParser.parse(SlackRequestParser.HttpRequest.builder()
                .requestUri(httpRequest.getUri().getPath())
                .queryString(queryString)
                .requestBody(httpRequest.bodyString())
                .headers(headers)
                .remoteAddress(httpRequest.header("X-Forwarded-For"))
                .build());
    }

    protected Response toHttp4kResponse(com.slack.api.bolt.response.Response boltResponse) {
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
