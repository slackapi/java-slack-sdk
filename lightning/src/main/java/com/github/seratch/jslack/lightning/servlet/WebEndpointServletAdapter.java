package com.github.seratch.jslack.lightning.servlet;

import com.github.seratch.jslack.lightning.AppConfig;
import com.github.seratch.jslack.lightning.request.RequestHeaders;
import com.github.seratch.jslack.lightning.request.WebEndpointRequest;
import com.github.seratch.jslack.lightning.response.Response;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

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

    protected Map<String, String> toHeaderMap(HttpServletRequest req) {
        return ServletAdapterOps.toHeaderMap(req);
    }

    public void writeResponse(HttpServletResponse resp, Response slackResp) throws IOException {
        ServletAdapterOps.writeResponse(resp, slackResp);
    }
}
