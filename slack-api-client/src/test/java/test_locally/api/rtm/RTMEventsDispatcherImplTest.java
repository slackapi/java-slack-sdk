package test_locally.api.rtm;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.slack.api.model.event.Event;
import com.slack.api.model.event.MessageEvent;
import com.slack.api.rtm.RTMEventHandler;
import com.slack.api.rtm.RTMEventsDispatcher;
import com.slack.api.rtm.RTMEventsDispatcherFactory;
import com.slack.api.rtm.RTMEventsDispatcherImpl;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RTMEventsDispatcherImplTest {

    @Test
    public void eventDetection() {
        JsonObject jsonMessage = JsonParser.parseString("{\"type\": \"hello\"}").getAsJsonObject();
        String type = RTMEventsDispatcherImpl.detectEventType(jsonMessage);
        assertThat(type, is("hello"));
    }

    @Test
    public void eventSubtypeDetection() {
        JsonObject jsonMessage = JsonParser.parseString("{ \"type\": \"message\", \"subtype\": \"bot_message\" }").getAsJsonObject();
        String subtype = RTMEventsDispatcherImpl.detectEventSubType(jsonMessage);
        assertThat(subtype, is("bot_message"));
    }

    @Test
    public void eventInnerSubtypeDetection() {
        JsonObject jsonMessage = JsonParser.parseString("{\"type\": \"pin_added\", \"item\": { \"type\": \"message\", \"subtype\": \"bot_message\" } }").getAsJsonObject();
        String subtype = RTMEventsDispatcherImpl.detectEventSubType(jsonMessage);
        assertThat(subtype, is(""));
    }

    @Test
    public void factory() {
        RTMEventsDispatcher dispatcher = RTMEventsDispatcherFactory.getInstance();
        RTMEventHandler<MessageEvent> handler = new RTMEventHandler<MessageEvent>() {
            @Override
            public void handle(MessageEvent event) {
            }
        };
        dispatcher.register(handler);
        dispatcher.deregister(handler);
        dispatcher.dispatch("{}");
    }

    @Test
    public void instantiation() {
        RTMEventsDispatcherImpl dispatcher = new RTMEventsDispatcherImpl();
        RTMEventHandler<MessageEvent> handler = new RTMEventHandler<MessageEvent>() {
            @Override
            public void handle(MessageEvent event) {
            }
        };
        dispatcher.register(handler);
        dispatcher.deregister(handler);
        dispatcher.dispatch("{}");
    }

    @Test(expected = IllegalStateException.class)
    public void registration_failure() {
        RTMEventsDispatcherImpl dispatcher = new RTMEventsDispatcherImpl();
        dispatcher.register(new RTMEventHandler<Event>() {
            @Override
            public void handle(Event event) {
            }
        });
    }
}
