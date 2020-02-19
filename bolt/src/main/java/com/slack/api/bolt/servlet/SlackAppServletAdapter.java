package com.slack.api.bolt.servlet;

import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.request.Request;
import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.response.Response;
import com.slack.api.bolt.util.QueryStringParser;
import com.slack.api.bolt.util.SlackRequestParser;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.slack.api.bolt.servlet.ServletAdapterOps.toHeaderMap;

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
