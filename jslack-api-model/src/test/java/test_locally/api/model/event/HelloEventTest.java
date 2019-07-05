package test_locally.api.model.event;

import com.github.seratch.jslack.api.model.event.HelloEvent;
import com.google.gson.Gson;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class HelloEventTest {

    @Test
    public void typeName() {
        assertThat(HelloEvent.TYPE_NAME, is("hello"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"hello\"\n" +
                "}";
        HelloEvent event = GsonFactory.createSnakeCase().fromJson(json, HelloEvent.class);
        assertThat(event.getType(), is("hello"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        HelloEvent event = new HelloEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"hello\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}