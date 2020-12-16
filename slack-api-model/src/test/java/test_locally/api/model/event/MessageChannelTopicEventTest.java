package test_locally.api.model.event;

import com.slack.api.model.event.MessageChannelTopicEvent;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class MessageChannelTopicEventTest {

    @Test
    public void typeName() {
        assertThat(MessageChannelTopicEvent.TYPE_NAME, is("message"));
        assertThat(MessageChannelTopicEvent.SUBTYPE_NAME, is("channel_topic"));
    }

    @Test
    public void deserialize_simple() {
        String json = "{\n" +
                "  \"type\": \"message\",\n" +
                "  \"subtype\": \"channel_topic\",\n" +
                "  \"ts\": \"1608102495.000300\",\n" +
                "  \"user\": \"W111\",\n" +
                "  \"text\": \"<@W111> set the topic: Non-work banter and water cooler conversation\",\n" +
                "  \"topic\": \"Non-work banter and water cooler conversation\",\n" +
                "  \"channel\": \"C111\",\n" +
                "  \"event_ts\": \"1608102495.000300\",\n" +
                "  \"channel_type\": \"channel\"\n" +
                "}";
        MessageChannelTopicEvent event = GsonFactory.createSnakeCase().fromJson(json, MessageChannelTopicEvent.class);
        assertThat(event.getType(), is("message"));
        assertThat(event.getSubtype(), is("channel_topic"));
    }

}
