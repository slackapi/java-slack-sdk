package test_locally.api.model.event;

import com.slack.api.model.event.ChannelDeletedEvent;
import com.google.gson.Gson;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ChannelDeletedEventTest {

    @Test
    public void typeName() {
        assertThat(ChannelDeletedEvent.TYPE_NAME, is("channel_deleted"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"channel_deleted\",\n" +
                "    \"channel\": \"C024BE91L\"\n" +
                "}";
        ChannelDeletedEvent event = GsonFactory.createSnakeCase().fromJson(json, ChannelDeletedEvent.class);
        assertThat(event.getType(), is("channel_deleted"));
        assertThat(event.getChannel(), is("C024BE91L"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        ChannelDeletedEvent event = new ChannelDeletedEvent();
        event.setChannel("c");
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"channel_deleted\",\"channel\":\"c\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}
