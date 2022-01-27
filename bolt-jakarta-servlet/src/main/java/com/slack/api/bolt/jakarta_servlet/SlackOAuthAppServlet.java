package com.slack.api.bolt.jakarta_servlet;

import com.slack.api.bolt.App;
import com.slack.api.bolt.request.Request;
import com.slack.api.bolt.response.Response;
import lombok.extern.slf4j.Slf4j;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The default Slack OAuth flow Servlet.
 */
@Slf4j
public class SlackOAuthAppServlet extends HttpServlet {

    private final App app;
    private final SlackAppServletAdapter adapter;

    public App getApp() {
        return this.app;
    }

    public SlackOAuthAppServlet(App app) {
        this.app = app;
        this.adapter = new SlackAppServletAdapter(app.config());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Request slackReq = adapter.buildSlackRequest(req);
        if (slackReq != null) {
            try {
                Response slackResp = app.run(slackReq);
                adapter.writeResponse(resp, slackResp);
            } catch (Exception e) {
                log.error("Failed to handle a request - {}", e.getMessage(), e);
                resp.setStatus(500);
                resp.setContentType("application/json");
                resp.getWriter().write("{\"error\":\"Something is wrong\"}");
            }
        }
    }
}