package com.github.seratch.jslack;

import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.request.channels.ChannelsInviteRequest;
import com.github.seratch.jslack.api.methods.response.channels.ChannelsInviteResponse;
import com.github.seratch.jslack.api.model.Conversation;
import com.github.seratch.jslack.api.model.User;
import com.github.seratch.jslack.api.rtm.RTMClient;
import com.github.seratch.jslack.api.rtm.RTMMessageHandler;
import com.github.seratch.jslack.api.rtm.message.Message;
import com.github.seratch.jslack.api.rtm.message.PresenceQuery;
import com.github.seratch.jslack.api.rtm.message.PresenceSub;
import com.github.seratch.jslack.api.rtm.message.Typing;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import javax.websocket.DeploymentException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.junit.Assert.assertThat;

@Slf4j
public class Slack_rtm_Test {

    Slack slack = Slack.getInstance();

    String botToken = System.getenv(Constants.SLACK_BOT_USER_TEST_OAUTH_ACCESS_TOKEN);
    String channelCreationToken = System.getenv(Constants.SLACK_TEST_OAUTH_ACCESS_TOKEN);
    User currentUser;

    JsonParser jsonParser = new JsonParser();

    @Before
    public void loadBotUser() throws IOException {
        if (currentUser == null) {
            RTMClient rtm = slack.rtmConnect(botToken);
            currentUser = rtm.getConnectedBotUser();
        }
    }

    @Test
    public void rtmStart() throws Exception {
        String channelName = "test" + System.currentTimeMillis();
        TestChannelGenerator channelGenerator = new TestChannelGenerator(channelCreationToken);
        Conversation channel = channelGenerator.createNewPublicChannel(channelName);
        String channelId = channel.getId();

        // need to invite the bot user to the created channel before starting an RTM session
        inviteBotUser(channelId);

        Thread.sleep(3000);

        try {
            try (RTMClient rtm = slack.rtmStart(botToken)) {
                verifyRTMClientBehavior(channelId, rtm);
            }
        } finally {
            channelGenerator.archiveChannel(channel);
        }
    }

    @Test
    public void rtmConnect() throws Exception {
        String channelName = "test" + System.currentTimeMillis();
        TestChannelGenerator channelGenerator = new TestChannelGenerator(channelCreationToken);
        Conversation channel = channelGenerator.createNewPublicChannel(channelName);
        String channelId = channel.getId();

        // need to invite the bot user to the created channel before starting an RTM session
        inviteBotUser(channelId);

        Thread.sleep(3000);

        try {
            try (RTMClient rtm = slack.rtmConnect(botToken)) {
                verifyRTMClientBehavior(channelId, rtm);
            }
        } finally {
            channelGenerator.archiveChannel(channel);
        }
    }

    private void inviteBotUser(String channelId) throws IOException, SlackApiException {
        ChannelsInviteResponse invitation = slack.methods().channelsInvite(ChannelsInviteRequest.builder()
                .token(channelCreationToken)
                .user(currentUser.getId())
                .channel(channelId)
                .build());
        assertThat(invitation.getError(), is(nullValue()));
    }

    private void verifyRTMClientBehavior(String channelId, RTMClient rtm)
            throws IOException, DeploymentException, InterruptedException, SlackApiException, URISyntaxException {

        AtomicInteger counter = new AtomicInteger(0);

        RTMMessageHandler handler1 = (message) -> {
            JsonObject json = jsonParser.parse(message).getAsJsonObject();
            if (json.get("error") == null) {
                counter.incrementAndGet();
            }
        };
        RTMMessageHandler handler2 = (message) -> {
            log.debug("handler 2 - " + message);
        };
        rtm.addMessageHandler(handler1);
        rtm.addMessageHandler(handler2);
        rtm.connect();

        // won't be captured by onMessage
        rtm.sendMessage(Typing.builder()
                .id(System.currentTimeMillis())
                .channel(channelId)
                .build().toJSONString());

        rtm.sendMessage(Message.builder()
                .id(System.currentTimeMillis())
                .channel(channelId)
                .text("Hi!")
                .build().toJSONString());

        // won't be captured by onMessage
        rtm.sendMessage(Typing.builder()
                .id(System.currentTimeMillis())
                .channel(channelId)
                .build().toJSONString());

        rtm.sendMessage(Message.builder()
                .id(System.currentTimeMillis())
                .channel(channelId)
                .text("Hi!!")
                .build().toJSONString());

        rtm.removeMessageHandler(handler2);

        Thread.sleep(5000);

        rtm.disconnect();


        // {"type":"error","error":{"msg":"Cannot create connection","code":3,"source":"gs-pdx-8k9l"}}
        // rtm.connect();

        // if you hit the rate limit, you need to wait for 1 min here.
        // https://api.slack.com/docs/rate-limits#rtm
        // Rate limits also apply to the rtm.start and rtm.connect methods for obtaining the URL needed to connect to a websocket.
        // Limit requests to these methods to no more than 1 per minute, with some bursting behavior allowed.
        // If you enter rate limit conditions when trying to fetch websocket URLs, you won't be able to reconnect until the window passes.
//        Thread.sleep(60000);
        Thread.sleep(3000);

        // refresh wss endpoint and start a new session
        rtm.reconnect();

        // returns {"type":"presence_change","presence":"active","user":"U8P5K4***"}
        rtm.sendMessage(PresenceQuery.builder().ids(Arrays.asList(currentUser.getId())).build().toJSONString());
        rtm.sendMessage(PresenceSub.builder().ids(Arrays.asList(currentUser.getId())).build().toJSONString());

        rtm.sendMessage(Message.builder()
                .id(System.currentTimeMillis())
                .channel(channelId)
                .text("Hi!!!")
                .build().toJSONString());

        Thread.sleep(3000);

        // two hello message + three messages
        // {"type": "hello"}
        // {"ok":true,"reply_to":1553324756489,"ts":"1553324756.000700","text":"Hi!"}
        // {"ok":true,"reply_to":1553324756490,"ts":"1553324756.000800","text":"Hi!!"}
        // {"type": "hello"}
        // {"ok":true,"reply_to":1553324822460,"ts":"1553324822.000900","text":"Hi!!!"}
        assertThat(counter.get(), is(greaterThanOrEqualTo(5)));
    }

}