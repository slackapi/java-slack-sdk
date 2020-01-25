package com.slack.api.util.http.listener;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.slack.api.SlackConfig;
import com.slack.api.util.json.GsonFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResponsePrettyPrintingListener extends HttpResponseListener {

    private static final Logger JSON_RESPONSE_LOGGER = LoggerFactory.getLogger("com.github.seratach.jslack.maintainer.json");
    private static final JsonParser JSON_PARSER = new JsonParser();

    @Override
    public void accept(State state) {
        SlackConfig config = state.getConfig();
        String body = state.getParsedResponseBody();
        if (config.isPrettyResponseLoggingEnabled() && body != null && body.trim().startsWith("{")) {
            JsonElement jsonObj = JSON_PARSER.parse(body);
            String prettifiedJson = GsonFactory.createSnakeCase(config).toJson(jsonObj);

            JSON_RESPONSE_LOGGER.debug("--- Pretty printing the response ---\n" +
                    prettifiedJson + "\n" +
                    "-----------------------------------------");
        }
    }
}
