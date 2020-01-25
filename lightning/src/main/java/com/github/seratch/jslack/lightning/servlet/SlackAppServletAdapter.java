package com.github.seratch.jslack.lightning.servlet;

import com.github.seratch.jslack.lightning.AppConfig;
import com.github.seratch.jslack.lightning.request.Request;
import com.github.seratch.jslack.lightning.request.RequestHeaders;
import com.github.seratch.jslack.lightning.response.Response;
import com.github.seratch.jslack.lightning.util.QueryStringParser;
import com.github.seratch.jslack.lightning.util.SlackRequestParser;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

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

    protected Map<String, String> toHeaderMap(HttpServletRequest req) {
        return ServletAdapterOps.toHeaderMap(req);
    }

    public void writeResponse(HttpServletResponse resp, Response slackResp) throws IOException {
        ServletAdapterOps.writeResponse(resp, slackResp);
    }
}
