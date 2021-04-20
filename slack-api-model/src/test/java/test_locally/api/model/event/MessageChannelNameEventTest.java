package test_locally.api.model.event;

import com.slack.api.model.event.MessageChannelNameEvent;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class MessageChannelNameEventTest {

    @Test
    public void typeName() {
        assertThat(MessageChannelNameEvent.TYPE_NAME, is("message"));
        assertThat(MessageChannelNameEvent.SUBTYPE_NAME, is("channel_name"));
    }

    @Test
    public void deserialize_simple() {
        String json = "{\n" +
                "  \"type\": \"message\",\n" +
                "  \"subtype\": \"channel_name\",\n" +
                "  \"ts\": \"1618886318.001300\",\n" +
                "  \"user\": \"USLACKBOT\",\n" +
                "  \"text\": \"<@USLACKBOT>xxx\",\n" +
                "  \"old_name\": \"private-test-1618886315905-2\",\n" +
                "  \"name\": \"private-test-1618886315905-2-5430\",\n" +
                "  \"channel\": \"C111\",\n" +
                "  \"event_ts\": \"1618886318.001300\",\n" +
                "  \"channel_type\": \"group\"\n" +
                "}";
        MessageChannelNameEvent event = GsonFactory.createSnakeCase().fromJson(json, MessageChannelNameEvent.class);
        assertThat(event.getType(), is("message"));
        assertThat(event.getSubtype(), is("channel_name"));
    }

}
