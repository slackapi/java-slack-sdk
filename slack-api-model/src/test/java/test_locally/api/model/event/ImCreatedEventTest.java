package test_locally.api.model.event;

import com.google.gson.Gson;
import com.slack.api.model.event.ImCreatedEvent;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class ImCreatedEventTest {

    @Test
    public void typeName() {
        assertThat(ImCreatedEvent.TYPE_NAME, is("im_created"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"im_created\",\n" +
                "    \"user\": \"U024BE7LH\",\n" +
                "    \"channel\": {}\n" +
                "}";
        ImCreatedEvent event = GsonFactory.createSnakeCase().fromJson(json, ImCreatedEvent.class);
        assertThat(event.getType(), is("im_created"));
        assertThat(event.getUser(), is("U024BE7LH"));
        assertThat(event.getChannel(), is(notNullValue()));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        ImCreatedEvent event = new ImCreatedEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"im_created\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}
