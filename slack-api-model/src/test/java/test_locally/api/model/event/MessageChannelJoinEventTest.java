package test_locally.api.model.event;

import com.slack.api.model.event.MessageChannelJoinEvent;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class MessageChannelJoinEventTest {

    @Test
    public void typeName() {
        assertThat(MessageChannelJoinEvent.TYPE_NAME, is("message"));
        assertThat(MessageChannelJoinEvent.SUBTYPE_NAME, is("channel_join"));
    }

    @Test
    public void deserialize_simple() {
        String json = "{\n" +
                "  \"type\": \"message\",\n" +
                "  \"subtype\": \"channel_join\",\n" +
                "  \"ts\": \"1613004710.000800\",\n" +
                "  \"user\": \"W111\",\n" +
                "  \"text\": \"<@W111> joined\",\n" +
                "  \"team\": \"T111\",\n" +
                "  \"inviter\": \"U111\",\n" +
                "  \"channel\": \"C111\",\n" +
                "  \"event_ts\": \"1613004710.000800\",\n" +
                "  \"channel_type\": \"channel\"\n" +
                "}";
        MessageChannelJoinEvent event = GsonFactory.createSnakeCase().fromJson(json, MessageChannelJoinEvent.class);
        assertThat(event.getType(), is("message"));
        assertThat(event.getSubtype(), is("channel_join"));
    }

}
