package test_locally.app_backend.events;

import com.github.seratch.jslack.app_backend.events.EventsDispatcher;
import com.github.seratch.jslack.app_backend.events.EventsDispatcherImpl;
import com.github.seratch.jslack.app_backend.events.handler.GoodbyeHandler;
import com.github.seratch.jslack.app_backend.events.payload.GoodbyePayload;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class EventsDispatcherTest {

    @Test
    public void runHandlers() {
        EventsDispatcher dispatcher = new EventsDispatcherImpl();

        String payload = "{\n" +
                "        \"token\": \"XXYYZZ\",\n" +
                "        \"team_id\": \"TXXXXXXXX\",\n" +
                "        \"api_app_id\": \"AXXXXXXXXX\",\n" +
                "        \"event\": {\n" +
                "                \"type\": \"goodbye\"\n" +
                "        },\n" +
                "        \"type\": \"event_callback\",\n" +
                "        \"authed_users\": [\n" +
                "                \"UXXXXXXX1\",\n" +
                "                \"UXXXXXXX2\"\n" +
                "        ],\n" +
                "        \"event_id\": \"Ev08MFMKH6\",\n" +
                "        \"event_time\": 1234567890\n" +
                "}";

        GoodbyeEventCountHandler handler = new GoodbyeEventCountHandler();
        dispatcher.register(handler);
        dispatcher.dispatch(payload);
        assertThat(handler.getCurrentCount(), is(1));

        dispatcher.dispatch(payload);
        assertThat(handler.getCurrentCount(), is(2));
        dispatcher.dispatch(payload);
        dispatcher.dispatch(payload);
        assertThat(handler.getCurrentCount(), is(4));

        dispatcher.deregister(handler);
        dispatcher.dispatch(payload);
        assertThat(handler.getCurrentCount(), is(4));
    }

    @Slf4j
    public static class GoodbyeEventCountHandler extends GoodbyeHandler {

        private AtomicInteger counter = new AtomicInteger(0);

        public Integer getCurrentCount() {
            return counter.get();
        }

        @Override
        public void handle(GoodbyePayload event) {
            log.info("event: {}", event);
            counter.incrementAndGet();
        }
    }

}
