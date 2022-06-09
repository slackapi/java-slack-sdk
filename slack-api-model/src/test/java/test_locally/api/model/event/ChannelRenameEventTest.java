package test_locally.api.model.event;

import com.google.gson.Gson;
import com.slack.api.model.event.ChannelRenameEvent;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class ChannelRenameEventTest {

    @Test
    public void typeName() {
        assertThat(ChannelRenameEvent.TYPE_NAME, is("channel_rename"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "  \"type\": \"channel_rename\",\n" +
                "  \"channel\": {\n" +
                "    \"id\": \"C02ELGNBH\",\n" +
                "    \"is_channel\": true,\n" +
                "    \"is_mpim\": false,\n" +
                "    \"name\": \"channel-renamed\",\n" +
                "    \"name_normalized\": \"channel-renamed\",\n" +
                "    \"created\": 1360782804\n" +
                "  },\n" +
                "  \"event_ts\": \"1654751422.047200\"\n" +
                "}";
        ChannelRenameEvent event = GsonFactory.createSnakeCase().fromJson(json, ChannelRenameEvent.class);
        assertThat(event.getType(), is("channel_rename"));
        assertThat(event.getChannel(), is(notNullValue()));
        assertThat(event.getChannel().getId(), is("C02ELGNBH"));
        assertThat(event.getChannel().getName(), is("channel-renamed"));
        assertThat(event.getChannel().getCreated(), is(1360782804));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        ChannelRenameEvent event = new ChannelRenameEvent();
        event.setChannel(new ChannelRenameEvent.Channel());
        event.getChannel().setName("foo");
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"channel_rename\",\"channel\":{\"name\":\"foo\",\"is_channel\":false,\"is_mpim\":false}}";
        assertThat(generatedJson, is(expectedJson));
    }
}
