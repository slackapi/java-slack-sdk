package test_locally.api.model.event;

import com.github.seratch.jslack.api.model.event.ChannelJoinedEvent;
import com.github.seratch.jslack.common.json.GsonFactory;
import com.google.gson.Gson;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class ChannelJoinedEventTest {

    @Test
    public void typeName() {
        assertThat(ChannelJoinedEvent.TYPE_NAME, is("channel_joined"));
    }

    // TODO: check the data in channel attribute
    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"channel_joined\",\n" +
                "    \"channel\": {\n" +
                "    }\n" +
                "}";
        ChannelJoinedEvent event = GsonFactory.createSnakeCase().fromJson(json, ChannelJoinedEvent.class);
        assertThat(event.getType(), is("channel_joined"));
        assertThat(event.getChannel(), is(notNullValue()));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        ChannelJoinedEvent event = new ChannelJoinedEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"channel_joined\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}