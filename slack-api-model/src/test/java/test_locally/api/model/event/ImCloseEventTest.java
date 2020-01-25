package test_locally.api.model.event;

import com.slack.api.model.event.ImCloseEvent;
import com.google.gson.Gson;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ImCloseEventTest {

    @Test
    public void typeName() {
        assertThat(ImCloseEvent.TYPE_NAME, is("im_close"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"im_close\",\n" +
                "    \"user\": \"U024BE7LH\",\n" +
                "    \"channel\": \"D024BE91L\"\n" +
                "}";
        ImCloseEvent event = GsonFactory.createSnakeCase().fromJson(json, ImCloseEvent.class);
        assertThat(event.getType(), is("im_close"));
        assertThat(event.getUser(), is("U024BE7LH"));
        assertThat(event.getChannel(), is("D024BE91L"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        ImCloseEvent event = new ImCloseEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"im_close\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}
