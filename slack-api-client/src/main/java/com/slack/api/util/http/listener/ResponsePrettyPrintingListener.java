package com.slack.api.util.http.listener;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.slack.api.SlackConfig;
import com.slack.api.util.json.GsonFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An HTTP response listener that prints the response body in a prettified JSON format.
 */
public class ResponsePrettyPrintingListener extends HttpResponseListener {

    private static final Logger JSON_RESPONSE_LOGGER = LoggerFactory.getLogger(ResponsePrettyPrintingListener.class);

    @Override
    public void accept(State state) {
        SlackConfig config = state.getConfig();
        String body = state.getParsedResponseBody();
        if (config.isPrettyResponseLoggingEnabled() && body != null && body.trim().startsWith("{")) {
            JsonElement jsonObj = JsonParser.parseString(body);
            String prettifiedJson = GsonFactory.createSnakeCase(config).toJson(jsonObj);

            JSON_RESPONSE_LOGGER.debug("--- Pretty printing the response ---\n" +
                    prettifiedJson + "\n" +
                    "-----------------------------------------");
        }
    }
}
