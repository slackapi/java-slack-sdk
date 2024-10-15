package test_locally.api.model.event;

import com.slack.api.model.event.MessageChannelConvertToPublicEvent;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class MessageChannelConvertToPublicEventTest {

    @Test
    public void typeName() {
        assertThat(MessageChannelConvertToPublicEvent.TYPE_NAME, is("message"));
        assertThat(MessageChannelConvertToPublicEvent.SUBTYPE_NAME, is("channel_convert_to_public"));
    }

    @Test
    public void deserialize_simple() {
        String json = "{\n" +
                "  \"subtype\": \"channel_convert_to_public\",\n" +
                "  \"user\": \"W013QGS7BPF\",\n" +
                "  \"text\": \"made this channel *public*. Any member in this workspace can see and join it.\",\n" +
                "  \"type\": \"message\",\n" +
                "  \"ts\": \"1728976211.166339\",\n" +
                "  \"channel\": \"C07RP9QHR2S\",\n" +
                "  \"event_ts\": \"1728976211.166339\",\n" +
                "  \"channel_type\": \"channel\"\n" +
                "}";
        MessageChannelConvertToPublicEvent event = GsonFactory.createSnakeCase(true, true).fromJson(json, MessageChannelConvertToPublicEvent.class);
        assertThat(event.getType(), is("message"));
        assertThat(event.getSubtype(), is("channel_convert_to_public"));
        assertThat(event.getUser(), is("W013QGS7BPF"));
    }

    @Test
    public void deserialize_api_document() {
        String json = "{\n" +
                "  \"type\": \"message\",\n" +
                "  \"subtype\": \"channel_convert_to_public\",\n" +
                "  \"ts\": \"1723680078.026719\",\n" +
                "  \"text\": \"Made this channel public. Any member in this workspace can see and join it.\",\n" +
                "  \"user\": \"U123ABC456\",\n" +
                "  \"channel\": \"C123ABC456\",\n" +
                "  \"event_ts\": \"1614215651.001300\",\n" +
                "  \"channel_type\": \"channel\"\n" +
                "}";
        MessageChannelConvertToPublicEvent event = GsonFactory.createSnakeCase(true, true).fromJson(json, MessageChannelConvertToPublicEvent.class);
        assertThat(event.getType(), is("message"));
        assertThat(event.getSubtype(), is("channel_convert_to_public"));
        assertThat(event.getUser(), is("U123ABC456"));
    }
}
