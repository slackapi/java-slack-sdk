package test_locally.api.model.event;

import com.github.seratch.jslack.api.model.event.ManualPresenceChangeEvent;
import com.google.gson.Gson;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ManualPresenceChangeEventTest {

    @Test
    public void typeName() {
        assertThat(ManualPresenceChangeEvent.TYPE_NAME, is("manual_presence_change"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"manual_presence_change\",\n" +
                "    \"presence\": \"away\"\n" +
                "}";
        ManualPresenceChangeEvent event = GsonFactory.createSnakeCase().fromJson(json, ManualPresenceChangeEvent.class);
        assertThat(event.getType(), is("manual_presence_change"));
        assertThat(event.getPresence(), is("away"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        ManualPresenceChangeEvent event = new ManualPresenceChangeEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"manual_presence_change\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}