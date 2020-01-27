package test_locally.api.model.event;

import com.google.gson.Gson;
import com.slack.api.model.event.ImOpenEvent;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ImOpenEventTest {

    @Test
    public void typeName() {
        assertThat(ImOpenEvent.TYPE_NAME, is("im_open"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"im_open\",\n" +
                "    \"user\": \"U024BE7LH\",\n" +
                "    \"channel\": \"D024BE91L\"\n" +
                "}";
        ImOpenEvent event = GsonFactory.createSnakeCase().fromJson(json, ImOpenEvent.class);
        assertThat(event.getType(), is("im_open"));
        assertThat(event.getChannel(), is("D024BE91L"));
        assertThat(event.getUser(), is("U024BE7LH"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        ImOpenEvent event = new ImOpenEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"im_open\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}
