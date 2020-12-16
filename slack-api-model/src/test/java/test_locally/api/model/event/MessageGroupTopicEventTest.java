package test_locally.api.model.event;

import com.slack.api.model.event.MessageGroupTopicEvent;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class MessageGroupTopicEventTest {

    @Test
    public void typeName() {
        assertThat(MessageGroupTopicEvent.TYPE_NAME, is("message"));
        assertThat(MessageGroupTopicEvent.SUBTYPE_NAME, is("group_topic"));
    }

    @Test
    public void deserialize_simple() {
        String json = "{\n" +
                "  \"type\": \"message\",\n" +
                "  \"subtype\": \"group_topic\",\n" +
                "  \"ts\": \"1608102528.000300\",\n" +
                "  \"user\": \"W111\",\n" +
                "  \"text\": \"<@W111> set the topic: topic!\",\n" +
                "  \"topic\": \"topic!\",\n" +
                "  \"channel\": \"G111\",\n" +
                "  \"event_ts\": \"1608102528.000300\",\n" +
                "  \"channel_type\": \"group\"\n" +
                "}";
        MessageGroupTopicEvent event = GsonFactory.createSnakeCase().fromJson(json, MessageGroupTopicEvent.class);
        assertThat(event.getType(), is("message"));
        assertThat(event.getSubtype(), is("group_topic"));
    }

}
