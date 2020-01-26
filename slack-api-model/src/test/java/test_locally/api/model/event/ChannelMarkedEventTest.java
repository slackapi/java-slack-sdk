package test_locally.api.model.event;

import com.google.gson.Gson;
import com.slack.api.model.event.ChannelMarkedEvent;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ChannelMarkedEventTest {

    @Test
    public void typeName() {
        assertThat(ChannelMarkedEvent.TYPE_NAME, is("channel_marked"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"channel_marked\",\n" +
                "    \"channel\": \"C024BE91L\",\n" +
                "    \"ts\": \"1401383885.000061\"\n" +
                "}";
        ChannelMarkedEvent event = GsonFactory.createSnakeCase().fromJson(json, ChannelMarkedEvent.class);
        assertThat(event.getType(), is("channel_marked"));
        assertThat(event.getChannel(), is("C024BE91L"));
        assertThat(event.getTs(), is("1401383885.000061"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        ChannelMarkedEvent event = new ChannelMarkedEvent();
        event.setChannel("C024BE91L");
        event.setTs("1401383885.000061");
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"channel_marked\",\"channel\":\"C024BE91L\",\"ts\":\"1401383885.000061\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}
