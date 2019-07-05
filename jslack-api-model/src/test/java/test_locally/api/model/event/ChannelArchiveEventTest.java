package test_locally.api.model.event;

import com.github.seratch.jslack.api.model.event.ChannelArchiveEvent;
import com.google.gson.Gson;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ChannelArchiveEventTest {

    @Test
    public void typeName() {
        assertThat(ChannelArchiveEvent.TYPE_NAME, is("channel_archive"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"channel_archive\",\n" +
                "    \"channel\": \"C024BE91L\",\n" +
                "    \"user\": \"U024BE7LH\"\n" +
                "}\n";
        ChannelArchiveEvent event = GsonFactory.createSnakeCase().fromJson(json, ChannelArchiveEvent.class);
        assertThat(event.getType(), is("channel_archive"));
        assertThat(event.getChannel(), is("C024BE91L"));
        assertThat(event.getUser(), is("U024BE7LH"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        ChannelArchiveEvent event = new ChannelArchiveEvent();
        event.setChannel("c");
        event.setUser("u");
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"channel_archive\",\"channel\":\"c\",\"user\":\"u\"}";
        assertThat(generatedJson, is(expectedJson));
    }
}