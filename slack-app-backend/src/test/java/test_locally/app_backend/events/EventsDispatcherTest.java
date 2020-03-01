package test_locally.app_backend.events;

import com.slack.api.app_backend.events.EventsDispatcher;
import com.slack.api.app_backend.events.EventsDispatcherImpl;
import com.slack.api.app_backend.events.handler.GoodbyeHandler;
import com.slack.api.app_backend.events.payload.GoodbyePayload;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

public class EventsDispatcherTest {

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

    @Test
    public void dispatch_empty() {
        EventsDispatcherImpl dispatcher = new EventsDispatcherImpl();
        dispatcher.start();
        dispatcher.dispatch("{}");
    }

    @Test
    public void maxTerminationDelayMillis() {
        EventsDispatcherImpl dispatcher = new EventsDispatcherImpl();
        assertEquals(10000L, dispatcher.getMaxTerminationDelayMillis());
        dispatcher.setMaxTerminationDelayMillis(100L);
        assertEquals(100L, dispatcher.getMaxTerminationDelayMillis());
    }

    @Test
    public void status() {
        EventsDispatcherImpl dispatcher = new EventsDispatcherImpl();
        assertFalse(dispatcher.isRunning());
        dispatcher.stop();
        assertFalse(dispatcher.isRunning());
        dispatcher.start();
        assertTrue(dispatcher.isRunning());
        dispatcher.stop();
        assertFalse(dispatcher.isRunning());

        assertTrue(dispatcher.isEmpty());
    }

    @Test(expected = IllegalStateException.class)
    public void enqueue_stopped() {
        EventsDispatcherImpl dispatcher = new EventsDispatcherImpl();
        dispatcher.enqueue("{}"); // stopped
    }

    @Test
    public void enqueue() {
        EventsDispatcherImpl dispatcher = new EventsDispatcherImpl();
        dispatcher.start();
        dispatcher.enqueue("{}");
    }

}
