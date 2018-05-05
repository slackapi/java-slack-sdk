package com.github.seratch.jslack;

import com.github.seratch.jslack.api.rtm.RTMClient;
import com.github.seratch.jslack.api.rtm.RTMMessageHandler;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.websocket.DeploymentException;
import java.io.IOException;

@Slf4j
public class Slack_rtm_Test {

    Slack slack = Slack.getInstance();

    //    private static int SLEEP_MILLIS = 10000;
    private static int SLEEP_MILLIS = 100;

    @Test
    public void rtmStart() throws Exception {
        JsonParser jsonParser = new JsonParser();
        String token = System.getenv(Constants.SLACK_BOT_USER_TEST_OAUTH_ACCESS_TOKEN);
        try (RTMClient rtm = slack.rtmStart(token)) {
            verifyRTMClientBehavior(jsonParser, rtm);
        }
    }

    @Test
    public void rtmConnect() throws Exception {
        JsonParser jsonParser = new JsonParser();
        String token = System.getenv(Constants.SLACK_BOT_USER_TEST_OAUTH_ACCESS_TOKEN);
        try (RTMClient rtm = slack.rtmConnect(token)) {
            verifyRTMClientBehavior(jsonParser, rtm);
        }
    }

    private void verifyRTMClientBehavior(JsonParser jsonParser, RTMClient rtm)
            throws IOException, DeploymentException, InterruptedException {
        RTMMessageHandler handler1 = (message) -> {
            JsonObject json = jsonParser.parse(message).getAsJsonObject();
            if (json.get("type") != null) {
                log.info("Handled type: {}", json.get("type").getAsString());
            }
        };
        RTMMessageHandler handler2 = (message) -> {
            log.info("Hello!");
        };
        rtm.addMessageHandler(handler1);
        rtm.addMessageHandler(handler1);
        rtm.addMessageHandler(handler2);
        rtm.connect();

        Thread.sleep(SLEEP_MILLIS);
        // Try anything on the channel...

        rtm.removeMessageHandler(handler2);

        Thread.sleep(SLEEP_MILLIS);
        // Try anything on the channel...
    }

}