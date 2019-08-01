package com.github.seratch.jslack.app_backend.events.servlet;

import com.github.seratch.jslack.app_backend.events.EventsDispatcher;
import com.github.seratch.jslack.app_backend.events.EventsDispatcherFactory;
import com.github.seratch.jslack.common.json.GsonFactory;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public abstract class SlackEventsApiServlet extends HttpServlet {

    private EventsDispatcher dispatcher = EventsDispatcherFactory.getInstance();

    protected abstract void setupDispatcher(EventsDispatcher dispatcher);

    public void init() throws ServletException {
        super.init();
        setupDispatcher(dispatcher);
        dispatcher.start();
    }

    public void destroy() {
        super.destroy();
        dispatcher.stop();
    }



    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String requestBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        doPost(req,resp,requestBody);
    }

    /***
     * This function enables the client to extract the request body and use it for signed secret validation
     * @param req
     * @param resp
     * @param requestBody
     * @throws IOException
     */
    protected void doPost(HttpServletRequest req, HttpServletResponse resp,String requestBody) throws IOException {
        String contentType = req.getHeader("Content-Type");
        if (contentType != null && contentType.toLowerCase(Locale.ENGLISH).trim().startsWith("application/json")) {
            JsonObject payload = GsonFactory.createSnakeCase().fromJson(requestBody, JsonElement.class).getAsJsonObject();
            String eventType = payload.get("type").getAsString();
            if (eventType != null && eventType.equals("url_verification")) {
                String challenge = payload.get("challenge").getAsString();
                // url_verification: https://api.slack.com/events/url_verification
                resp.setStatus(200);
                resp.setHeader("Content-Type", "text/plain");
                resp.getOutputStream().write(challenge.getBytes(Charset.forName("UTF-8")));
            } else {
                dispatcher.enqueue(requestBody);
                resp.setStatus(200);
            }
        } else {
            log.warn("Unexpected request detected - Content-Type: {}", contentType);
        }

    }

}
