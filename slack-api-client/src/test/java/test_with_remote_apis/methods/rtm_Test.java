package test_with_remote_apis.methods;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.methods.response.conversations.ConversationsInviteResponse;
import com.slack.api.model.Conversation;
import com.slack.api.model.User;
import com.slack.api.model.event.HelloEvent;
import com.slack.api.model.event.MessageBotEvent;
import com.slack.api.model.event.PongEvent;
import com.slack.api.model.event.UserTypingEvent;
import com.slack.api.rtm.*;
import com.slack.api.rtm.message.*;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.*;
import util.TestChannelGenerator;
import util.sample_json_generation.JsonDataRecordingListener;

import javax.websocket.DeploymentException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

@Slf4j
public class rtm_Test {

    String classicAppBotToken = System.getenv(Constants.SLACK_SDK_TEST_CLASSIC_APP_BOT_TOKEN);
    String channelCreationToken = System.getenv(Constants.SLACK_SDK_TEST_USER_TOKEN);
    User currentUser;

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

    @BeforeClass
    public static void setUp() throws Exception {
        SlackTestConfig.initializeRawJSONDataFiles("rtm.*");
    }

    @AfterClass
    public static void tearDown() throws InterruptedException {
        SlackTestConfig.awaitCompletion(testConfig);
    }

    @Before
    public void loadBotUser() throws IOException {
        if (currentUser == null) {
            RTMClient rtm = slack.rtmConnect(classicAppBotToken);
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

    @Slf4j
    public static class BotMessageHandler extends RTMEventHandler<MessageBotEvent> {
        public final AtomicInteger counter = new AtomicInteger(0);

        @Override
        public void handle(MessageBotEvent event) {
            log.info("bot message event: {}", event);
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

    @Ignore // Skip running this regularly as the rate limit errors can arise more easily than before
    @Test
    public void test() throws Exception {
        String channelName = "test" + System.currentTimeMillis();
        TestChannelGenerator channelGenerator = new TestChannelGenerator(testConfig, channelCreationToken);
        Conversation channel = channelGenerator.createNewPublicChannel(channelName);
        try {
            String channelId = channel.getId();

            // need to invite the bot user to the created channel before starting an RTM session
            inviteBotUser(channelId);

            RTMEventsDispatcher dispatcher = RTMEventsDispatcherFactory.getInstance();
            HelloHandler hello = new HelloHandler();
            dispatcher.register(hello);

            SubHelloHandler hello2 = new SubHelloHandler();
            dispatcher.register(hello2);

            dispatcher.register(new UserTypingHandler());

            SlackTestConfig testConfig = SlackTestConfig.getInstance();
            Slack slack = Slack.getInstance(testConfig.getConfig());

            try (RTMClient rtm = slack.rtmConnect(classicAppBotToken)) {
                rtm.addMessageHandler(dispatcher.toMessageHandler());

                rtm.connect();
                Thread.sleep(1000L);
                assertThat(hello.counter.get(), is(1));
                assertThat(hello2.counter.get(), is(1));

                BotMessageHandler bot = new BotMessageHandler();
                dispatcher.register(bot);

                ChatPostMessageResponse chatPostMessage = slack.methods(classicAppBotToken)
                        .chatPostMessage(r -> r.channel(channelId).text("Hi!"));
                assertThat(chatPostMessage.getError(), is(nullValue()));

                Thread.sleep(1000L);
                assertThat(bot.counter.get(), is(1));

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

    @Ignore // Skip running this regularly as the rate limit errors can arise more easily than before
    @Test
    public void rtmStart() throws Exception {
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

            try (RTMClient rtm = slack.rtmStart(classicAppBotToken)) {
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
        try (RTMClient rtm = slack.rtmConnect(classicAppBotToken, false)) {
            User user = rtm.getConnectedBotUser();
            assertThat(user.getId(), is(notNullValue()));
            assertThat(user.getName(), is(notNullValue()));

            assertThat(user.getTeamId(), is(nullValue()));
            assertThat(user.getProfile(), is(nullValue()));
        }
    }

    private void inviteBotUser(String channelId) throws IOException, SlackApiException {
        ConversationsInviteResponse invitation = slack.methods(channelCreationToken).conversationsInvite(r -> r
                .users(Arrays.asList(currentUser.getId()))
                .channel(channelId)
        );
        assertThat(invitation.getError(), is(nullValue()));
    }

    private void verifyRTMClientBehavior(String channelId, RTMClient rtm)
            throws IOException, DeploymentException, InterruptedException, SlackApiException, URISyntaxException {

        final AtomicInteger counter = new AtomicInteger(0);

        RTMMessageHandler handler1 = new RTMMessageHandler() {
            @Override
            public void handle(String message) {
                JsonObject json = JsonParser.parseString(message).getAsJsonObject();
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
        // https://docs.slack.dev/apis/web-api/rate-limits#rtm
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


    @Test
    public void ping_pong() throws Exception {

        // given
        SlackConfig config = new SlackConfig();
        config.setLibraryMaintainerMode(false);
        config.getHttpClientResponseHandlers().add(new JsonDataRecordingListener());
        Slack slack = Slack.getInstance(config);

        final Instant now = Instant.now();
        final long pingId = now.toEpochMilli();

        class PongReceived {
            PongEvent event = null;
        }
        final PongReceived pongReceived = new PongReceived();

        RTMEventsDispatcher dispatcher = RTMEventsDispatcherFactory.getInstance();
        dispatcher.register(new RTMEventHandler<PongEvent>() {
            @Override
            public void handle(PongEvent event) {
                if (Objects.equals(event.getReplyTo(), pingId)) {
                    pongReceived.event = event;
                    pongReceived.notifyAll();
                }
            }
        });

        try (RTMClient rtm = slack.rtmStart(classicAppBotToken)) {
            rtm.addMessageHandler(dispatcher.toMessageHandler());
            rtm.connect();

            Thread.sleep(3000);

            // when
            rtm.sendMessage(PingMessage.builder().id(pingId).time(now).build().toJSONString());

            // ensure
            long millis = 0L;
            while (pongReceived.event == null && millis < 30_000L) {
                Thread.sleep(1000L);
                millis += 1000;
            }
        }
        assertThat(pongReceived.event, notNullValue());
        assertThat(pongReceived.event.getReplyTo(), equalTo(pingId));
        assertThat(pongReceived.event.getTime().toEpochMilli(), equalTo(now.toEpochMilli()));

    }
}
