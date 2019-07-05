package test_locally.api.model.event;

import com.github.seratch.jslack.api.model.event.StarRemovedEvent;
import com.google.gson.Gson;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class StarRemovedEventTest {

    @Test
    public void typeName() {
        assertThat(StarRemovedEvent.TYPE_NAME, is("star_removed"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"star_removed\",\n" +
                "    \"user\": \"U024BE7LH\",\n" +
                "    \"item\": {\n" +
                "    },\n" +
                "    \"event_ts\": \"1360782804.083113\"\n" +
                "}";
        StarRemovedEvent event = GsonFactory.createSnakeCase().fromJson(json, StarRemovedEvent.class);
        assertThat(event.getType(), is("star_removed"));
        assertThat(event.getUser(), is("U024BE7LH"));
        assertThat(event.getEventTs(), is("1360782804.083113"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        StarRemovedEvent event = new StarRemovedEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"star_removed\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}