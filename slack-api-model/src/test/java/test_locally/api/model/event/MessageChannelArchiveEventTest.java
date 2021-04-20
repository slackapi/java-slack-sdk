package test_locally.api.model.event;

import com.slack.api.model.event.MessageChannelArchiveEvent;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class MessageChannelArchiveEventTest {

    @Test
    public void typeName() {
        assertThat(MessageChannelArchiveEvent.TYPE_NAME, is("message"));
        assertThat(MessageChannelArchiveEvent.SUBTYPE_NAME, is("channel_archive"));
    }

    @Test
    public void deserialize_simple() {
        String json = "{\n" +
                "  \"type\": \"message\",\n" +
                "  \"subtype\": \"channel_archive\",\n" +
                "  \"ts\": \"1618886318.001500\",\n" +
                "  \"user\": \"USLACKBOT\",\n" +
                "  \"text\": \"<@USLACKBOT>xxx\",\n" +
                "  \"channel\": \"C111\",\n" +
                "  \"event_ts\": \"1618886318.001500\",\n" +
                "  \"channel_type\": \"group\"\n" +
                "}";
        MessageChannelArchiveEvent event = GsonFactory.createSnakeCase().fromJson(json, MessageChannelArchiveEvent.class);
        assertThat(event.getType(), is("message"));
        assertThat(event.getSubtype(), is("channel_archive"));
    }

}
