package com.slack.api.bolt.servlet;

import com.slack.api.bolt.App;
import com.slack.api.bolt.request.Request;
import com.slack.api.bolt.response.Response;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The default Servlet that handles incoming requests from the Slack API server.
 */
@Slf4j
public class SlackAppServlet extends HttpServlet {

    private final App app;
    private final SlackAppServletAdapter adapter;

    public App getApp() {
        return this.app;
    }

    public SlackAppServlet(App app) {
        this.app = app;
        this.adapter = new SlackAppServletAdapter(app.config());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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
        } else {
            adapter.writeResponse(resp, Response.builder().statusCode(400).body("Invalid Request").build());
        }
    }
}