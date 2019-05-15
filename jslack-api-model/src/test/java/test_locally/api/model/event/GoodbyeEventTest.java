package test_locally.api.model.event;

import com.github.seratch.jslack.api.model.event.GoodbyeEvent;
import test_locally.unit.GsonFactory;
import com.google.gson.Gson;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GoodbyeEventTest {

    @Test
    public void typeName() {
        assertThat(GoodbyeEvent.TYPE_NAME, is("goodbye"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"goodbye\"\n" +
                "}";
        GoodbyeEvent event = GsonFactory.createSnakeCase().fromJson(json, GoodbyeEvent.class);
        assertThat(event.getType(), is("goodbye"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        GoodbyeEvent event = new GoodbyeEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"goodbye\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}