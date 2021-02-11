package test_locally.api.model.event;

import com.google.gson.Gson;
import com.slack.api.model.event.ChannelIdChangedEvent;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ChannelIdChangedEventTest {

    @Test
    public void typeName() {
        assertThat(ChannelIdChangedEvent.TYPE_NAME, is("channel_id_changed"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "  \"type\": \"channel_id_changed\",\n" +
                "  \"old_channel_id\": \"G111\",\n" +
                "  \"new_channel_id\": \"C111\",\n" +
                "  \"event_ts\": \"1613005411.000000\"\n" +
                "}\n";
        ChannelIdChangedEvent event = GsonFactory.createSnakeCase().fromJson(json, ChannelIdChangedEvent.class);
        assertThat(event.getType(), is("channel_id_changed"));
        assertThat(event.getOldChannelId(), is("G111"));
        assertThat(event.getNewChannelId(), is("C111"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        ChannelIdChangedEvent event = new ChannelIdChangedEvent();
        event.setOldChannelId("G");
        event.setNewChannelId("C");
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"channel_id_changed\",\"old_channel_id\":\"G\",\"new_channel_id\":\"C\"}";
        assertThat(generatedJson, is(expectedJson));
    }
}
