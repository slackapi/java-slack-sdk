package test_locally.api.model.event;

import com.github.seratch.jslack.api.model.event.ChannelUnarchiveEvent;
import com.google.gson.Gson;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ChannelUnarchiveEventTest {

    @Test
    public void typeName() {
        assertThat(ChannelUnarchiveEvent.TYPE_NAME, is("channel_unarchive"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"channel_unarchive\",\n" +
                "    \"channel\": \"C024BE91L\",\n" +
                "    \"user\": \"U024BE7LH\"\n" +
                "}";
        ChannelUnarchiveEvent event = GsonFactory.createSnakeCase().fromJson(json, ChannelUnarchiveEvent.class);
        assertThat(event.getType(), is("channel_unarchive"));
        assertThat(event.getChannel(), is("C024BE91L"));
        assertThat(event.getUser(), is("U024BE7LH"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        ChannelUnarchiveEvent event = new ChannelUnarchiveEvent();
        event.setChannel("c");
        event.setUser("u");
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"channel_unarchive\",\"channel\":\"c\",\"user\":\"u\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}
