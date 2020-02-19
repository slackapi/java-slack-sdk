package com.slack.api.lightning.servlet;

import com.slack.api.lightning.AppConfig;
import com.slack.api.lightning.request.Request;
import com.slack.api.lightning.request.RequestHeaders;
import com.slack.api.lightning.response.Response;
import com.slack.api.lightning.util.QueryStringParser;
import com.slack.api.lightning.util.SlackRequestParser;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.slack.api.lightning.servlet.ServletAdapterOps.toHeaderMap;

/**
 * An adapter that converts requests from the Slack API server
 * and translates the Response object to the actual Servlet HTTP response.
 */
@Slf4j
public class SlackAppServletAdapter {

    private SlackRequestParser requestParser;

    public SlackAppServletAdapter(AppConfig appConfig) {
        this.requestParser = new SlackRequestParser(appConfig);
    }

    public Request<?> buildSlackRequest(HttpServletRequest req) throws IOException {
        String requestBody = doReadRequestBodyAsString(req);
        RequestHeaders headers = new RequestHeaders(toHeaderMap(req));
        SlackRequestParser.HttpRequest rawRequest = SlackRequestParser.HttpRequest.builder()
                .requestUri(req.getRequestURI())
                .queryString(QueryStringParser.toMap(req.getQueryString()))
                .headers(headers)
                .requestBody(requestBody)
                .remoteAddress(req.getRemoteAddr())
                .build();
        return requestParser.parse(rawRequest);
    }

    protected String doReadRequestBodyAsString(HttpServletRequest req) throws IOException {
        return ServletAdapterOps.doReadRequestBodyAsString(req);
    }

    public void writeResponse(HttpServletResponse resp, Response slackResp) throws IOException {
        ServletAdapterOps.writeResponse(resp, slackResp);
    }
}
