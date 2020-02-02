package test_with_remote_apis.methods;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.channels.ChannelsInviteRequest;
import com.slack.api.methods.response.channels.ChannelsInviteResponse;
import com.slack.api.model.Conversation;
import com.slack.api.model.User;
import com.slack.api.model.event.HelloEvent;
import com.slack.api.model.event.UserTypingEvent;
import com.slack.api.rtm.*;
import com.slack.api.rtm.message.Message;
import com.slack.api.rtm.message.PresenceQuery;
import com.slack.api.rtm.message.PresenceSub;
import com.slack.api.rtm.message.Typing;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import util.TestChannelGenerator;
import util.sample_json_generation.JsonDataRecordingListener;

import javax.websocket.DeploymentException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

@Slf4j
public class rtm_Test {

    String botToken = System.getenv(Constants.SLACK_SDK_TEST_BOT_TOKEN);
    String channelCreationToken = System.getenv(Constants.SLACK_SDK_TEST_USER_TOKEN);
    User currentUser;

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

    @AfterClass
    public static void tearDown() throws InterruptedException {
        SlackTestConfig.awaitCompletion(testConfig);
    }

    JsonParser jsonParser = new JsonParser();

    @Before
    public void loadBotUser() throws IOException {
        if (currentUser == null) {
            RTMClient rtm = slack.rtmConnect(botToken);
            currentUser = rtm.getConnectedBotUser();
        }
    }

    @Slf4j
    public static class HelloHandler extends RTMEventHandler<HelloEvent> {
        public final AtomicInteger counter = new AtomicInteger(0);

        @Override
        public void handle(HelloEvent event) {
            counter.incrementAndGet();
        }
    }

    public static class SubHelloHandler extends HelloHandler {
    }

    @Slf4j
    public static class UserTypingHandler extends RTMEventHandler<UserTypingEvent> {
        @Override
        public void handle(UserTypingEvent event) {
        }
    }

    @Test
    public void test() throws Exception {
        String channelName = "test" + System.currentTimeMillis();
        TestChannelGenerator channelGenerator = new TestChannelGenerator(testConfig, channelCreationToken);
        Conversation channel = channelGenerator.createNewPublicChannel(channelName);
        try {
            String channelId = channel.getId();

            // need to invite the bot user to the created channel before starting an RTM session
            inviteBotUser(channelId);

            String botToken = System.getenv(Constants.SLACK_SDK_TEST_BOT_TOKEN);

            RTMEventsDispatcher dispatcher = RTMEventsDispatcherFactory.getInstance();
            HelloHandler hello = new HelloHandler();
            dispatcher.register(hello);

            SubHelloHandler hello2 = new SubHelloHandler();
            dispatcher.register(hello2);

            dispatcher.register(new UserTypingHandler());

            SlackTestConfig testConfig = SlackTestConfig.getInstance();
            Slack slack = Slack.getInstance(testConfig.getConfig());

            try (RTMClient rtm = slack.rtmConnect(botToken)) {
                rtm.addMessageHandler(dispatcher.toMessageHandler());

                rtm.connect();
                Thread.sleep(1000L);
                assertThat(hello.counter.get(), is(1));
                assertThat(hello2.counter.get(), is(1));

                rtm.reconnect();
                Thread.sleep(1000L);
                assertThat(hello.counter.get(), is(2));
                assertThat(hello2.counter.get(), is(2));

                dispatcher.deregister(hello);

                rtm.reconnect();
                Thread.sleep(1000L);
                assertThat(hello.counter.get(), is(2)); // should not be incremented
                assertThat(hello2.counter.get(), is(3));
            }

        } finally {
            channelGenerator.archiveChannel(channel);
        }
    }

    @Test
    public void rtmStart() throws Exception {
        // TODO: "prefs" support
        SlackConfig config = new SlackConfig();
        config.setLibraryMaintainerMode(false);
        config.getHttpClientResponseHandlers().add(new JsonDataRecordingListener());
        Slack slack = Slack.getInstance(config);

        String channelName = "test" + System.currentTimeMillis();
        TestChannelGenerator channelGenerator = new TestChannelGenerator(testConfig, channelCreationToken);
        Conversation channel = channelGenerator.createNewPublicChannel(channelName);
        try {
            String channelId = channel.getId();

            // need to invite the bot user to the created channel before starting an RTM session
            inviteBotUser(channelId);

            Thread.sleep(3000);

            try (RTMClient rtm = slack.rtmStart(botToken)) {
                User user = rtm.getConnectedBotUser();

                assertThat(user.getId(), is(notNullValue()));
                assertThat(user.getTeamId(), is(notNullValue()));
                assertThat(user.getName(), is(notNullValue()));
                assertThat(user.getProfile(), is(notNullValue()));
                assertThat(user.getProfile().getBotId(), is(notNullValue()));

                verifyRTMClientBehavior(channelId, rtm);
            }
        } finally {
            channelGenerator.archiveChannel(channel);
        }
    }

    @Ignore
    @Test
    public void rtmConnect_withoutFullConnectedUserInfo() throws Exception {
        try (RTMClient rtm = slack.rtmConnect(botToken, false)) {
            User user = rtm.getConnectedBotUser();
            assertThat(user.getId(), is(notNullValue()));
            assertThat(user.getName(), is(notNullValue()));

            assertThat(user.getTeamId(), is(nullValue()));
            assertThat(user.getProfile(), is(nullValue()));
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

        final AtomicInteger counter = new AtomicInteger(0);

        RTMMessageHandler handler1 = new RTMMessageHandler() {
            @Override
            public void handle(String message) {
                JsonObject json = jsonParser.parse(message).getAsJsonObject();
                if (json.get("error") == null) {
                    counter.incrementAndGet();
                }
            }
        };
        RTMMessageHandler handler2 = new RTMMessageHandler() {
            @Override
            public void handle(String message) {
                log.debug("handler 2 - " + message);
            }
        };
        rtm.addMessageHandler(handler1);
        rtm.addMessageHandler(handler2);
        rtm.connect();

        Thread.sleep(3000);

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

        Thread.sleep(3000);

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

        Thread.sleep(3000);

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
