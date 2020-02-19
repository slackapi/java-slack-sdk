package com.slack.api.bolt.servlet;

import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.request.WebEndpointRequest;
import com.slack.api.bolt.response.Response;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.slack.api.bolt.servlet.ServletAdapterOps.toHeaderMap;

/**
 * An adapter that converts requests for additional web endpoints
 * and translates the Response object to the actual Servlet HTTP response.
 */
@Slf4j
public class WebEndpointServletAdapter {

    private AppConfig appConfig;

    public WebEndpointServletAdapter(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    public WebEndpointRequest buildSlackRequest(HttpServletRequest req) throws IOException {
        String queryString = req.getQueryString();
        String requestBody = doReadRequestBodyAsString(req);
        RequestHeaders headers = new RequestHeaders(toHeaderMap(req));
        WebEndpointRequest slackRequest = null;
        try {
            slackRequest = new WebEndpointRequest(queryString, requestBody, headers);
            return slackRequest;
        } finally {
            if (slackRequest != null) {
                String ipAddress = req.getHeader("X-FORWARDED-FOR");
                if (ipAddress == null) {
                    ipAddress = req.getRemoteAddr();
                }
                slackRequest.setClientIpAddress(ipAddress);
            }
        }
    }

    protected String doReadRequestBodyAsString(HttpServletRequest req) throws IOException {
        return ServletAdapterOps.doReadRequestBodyAsString(req);
    }

    public void writeResponse(HttpServletResponse resp, Response slackResp) throws IOException {
        ServletAdapterOps.writeResponse(resp, slackResp);
    }
}
