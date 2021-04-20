package test_locally.api.model.event;

import com.slack.api.model.event.MessageChannelUnarchiveEvent;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class MessageChannelUnarchiveEventTest {

    @Test
    public void typeName() {
        assertThat(MessageChannelUnarchiveEvent.TYPE_NAME, is("message"));
        assertThat(MessageChannelUnarchiveEvent.SUBTYPE_NAME, is("channel_unarchive"));
    }

    @Test
    public void deserialize_simple() {
        String json = "{\n" +
                "  \"type\": \"message\",\n" +
                "  \"subtype\": \"channel_unarchive\",\n" +
                "  \"ts\": \"1618886317.000800\",\n" +
                "  \"user\": \"W111\",\n" +
                "  \"text\": \"<@W111>xxx\",\n" +
                "  \"channel\": \"C111\",\n" +
                "  \"event_ts\": \"1618886317.000800\",\n" +
                "  \"channel_type\": \"group\"\n" +
                "}";
        MessageChannelUnarchiveEvent event = GsonFactory.createSnakeCase().fromJson(json, MessageChannelUnarchiveEvent.class);
        assertThat(event.getType(), is("message"));
        assertThat(event.getSubtype(), is("channel_unarchive"));
    }

}
