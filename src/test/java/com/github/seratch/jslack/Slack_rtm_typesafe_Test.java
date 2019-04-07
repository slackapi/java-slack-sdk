package com.github.seratch.jslack;

import com.github.seratch.jslack.api.model.event.HelloEvent;
import com.github.seratch.jslack.api.rtm.RTMClient;
import com.github.seratch.jslack.api.rtm.RTMEventHandler;
import com.github.seratch.jslack.api.rtm.RTMEventsDispatcher;
import com.github.seratch.jslack.api.rtm.RTMEventsDispatcherFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Slack_rtm_typesafe_Test {

    @Slf4j
    public static class HelloHandler extends RTMEventHandler<HelloEvent> {
        public final AtomicInteger counter = new AtomicInteger(0);
        @Override
        public void handle(HelloEvent event) {
            counter.incrementAndGet();
        }
    }

    @Test
    public void test() throws Exception {

        Slack slack = Slack.getInstance();

        String botToken = System.getenv(Constants.SLACK_BOT_USER_TEST_OAUTH_ACCESS_TOKEN);

        RTMEventsDispatcher dispatcher = RTMEventsDispatcherFactory.getInstance();
        HelloHandler hello = new HelloHandler();
        dispatcher.register(hello);

        try (RTMClient rtm = slack.rtmStart(botToken)) {
            rtm.addMessageHandler(dispatcher.toMessageHandler());

            rtm.connect();
            Thread.sleep(2000L);
            assertThat(hello.counter.get(), is(1));

            rtm.reconnect();
            Thread.sleep(2000L);
            assertThat(hello.counter.get(), is(2));
        }
    }

}