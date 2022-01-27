package com.slack.api.bolt.jakarta_servlet;

import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.WebEndpoint;
import com.slack.api.bolt.handler.WebEndpointHandler;
import com.slack.api.bolt.request.WebEndpointRequest;
import com.slack.api.bolt.response.Response;
import lombok.extern.slf4j.Slf4j;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The default Servlet for additional web endpoints.
 */
@Slf4j
public class WebEndpointServlet extends HttpServlet {

    private final WebEndpoint endpoint;
    private final WebEndpointHandler handler;
    private final WebEndpointServletAdapter adapter;

    public WebEndpointServlet(WebEndpoint endpoint, WebEndpointHandler handler, AppConfig config) {
        this.endpoint = endpoint;
        this.handler = handler;
        this.adapter = new WebEndpointServletAdapter(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (endpoint.getMethod() == WebEndpoint.Method.GET && endpoint.getPath().equals(req.getRequestURI())) {
            runHandler(req, resp);
        } else {
            respondAsError(resp, 404, "{\"error\":\"Not found\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (endpoint.getMethod() == WebEndpoint.Method.POST && endpoint.getPath().equals(req.getRequestURI())) {
            runHandler(req, resp);
        } else {
            respondAsError(resp, 404, "{\"error\":\"Not found\"}");
        }
    }

    private void runHandler(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        WebEndpointRequest slackReq = adapter.buildSlackRequest(req);
        if (slackReq != null) {
            try {
                Response slackResp = handler.apply(slackReq, slackReq.getContext());
                adapter.writeResponse(resp, slackResp);
            } catch (Exception e) {
                log.error("Failed to handle a request - {}", e.getMessage(), e);
                respondAsError(resp, 500, "{\"error\":\"Something is wrong\"}");
            }
        }
    }

    protected void respondAsError(HttpServletResponse resp, int status, String json) throws IOException {
        resp.setStatus(status);
        resp.setContentType("application/json");
        resp.getWriter().write(json);
    }

}