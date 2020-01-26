package test_locally.api.model.event;

import com.google.gson.Gson;
import com.slack.api.model.event.ChannelLeftEvent;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ChannelLeftEventTest {

    @Test
    public void typeName() {
        assertThat(ChannelLeftEvent.TYPE_NAME, is("channel_left"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"channel_left\",\n" +
                "    \"channel\": \"C024BE91L\"\n" +
                "}";
        ChannelLeftEvent event = GsonFactory.createSnakeCase().fromJson(json, ChannelLeftEvent.class);
        assertThat(event.getType(), is("channel_left"));
        assertThat(event.getChannel(), is("C024BE91L"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        ChannelLeftEvent event = new ChannelLeftEvent();
        event.setChannel("C024BE91L");
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"channel_left\",\"channel\":\"C024BE91L\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}
