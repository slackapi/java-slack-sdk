package com.slack.api.app_backend.events.servlet;

import com.slack.api.app_backend.SlackSignature;
import com.slack.api.app_backend.events.EventsDispatcher;
import com.slack.api.app_backend.events.EventsDispatcherFactory;
import com.slack.api.util.json.GsonFactory;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.stream.Collectors;

@Slf4j
public abstract class SlackEventsApiServlet extends HttpServlet {

    private EventsDispatcher dispatcher = EventsDispatcherFactory.getInstance();
    private SlackSignatureVerifier signatureVerifier;

    protected abstract void setupDispatcher(EventsDispatcher dispatcher);

    public void init() throws ServletException {
        super.init();
        setupDispatcher(dispatcher);
        dispatcher.start();
        signatureVerifier = new SlackSignatureVerifier(new SlackSignature.Generator(getSlackSigningSecret()));
    }

    public void destroy() {
        super.destroy();
        dispatcher.stop();
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String requestBody = doReadRequestBodyAsString(req);

        // NOTE: It's also possible to do the same in a servlet filter
        if (isSignatureVerifierEnabled()) {
            boolean validSignature = this.signatureVerifier.isValid(req, requestBody);
            if (!validSignature) { // invalid signature
                if (log.isDebugEnabled()) {
                    String signature = req.getHeader(SlackSignature.HeaderNames.X_SLACK_SIGNATURE);
                    log.debug("An invalid X-Slack-Signature detected - {}", signature);
                }
                resp.setStatus(401);
                return;
            }
        }

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
            log.warn("Unexpected request detected - Content-Type: {}", req.getHeader("Content-Type"));
            resp.setStatus(400);
        }

    }

    /**
     * Returns the signing secret supposed to be used for verifying requests from Slack.
     */
    protected String getSlackSigningSecret() {
        return System.getenv(SlackSignature.Secret.DEFAULT_ENV_NAME);
    }

    /**
     * If you'd like to do the same in a servlet filter, return false instead.
     */
    protected boolean isSignatureVerifierEnabled() {
        return true;
    }

    /**
     * Reads the request body and returns the value as a string.
     */
    protected String doReadRequestBodyAsString(HttpServletRequest req) throws IOException {
        return req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
    }

}
