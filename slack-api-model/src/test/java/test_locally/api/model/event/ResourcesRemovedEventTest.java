package test_locally.api.model.event;

import com.slack.api.model.event.ResourcesRemovedEvent;
import com.google.gson.Gson;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ResourcesRemovedEventTest {

    @Test
    public void typeName() {
        assertThat(ResourcesRemovedEvent.TYPE_NAME, is("resources_removed"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "                \"type\": \"resources_removed\",\n" +
                "                \"resources\": [\n" +
                "                        {\n" +
                "                                \"resource\": {\n" +
                "                                        \"type\": \"im\",\n" +
                "                                        \"grant\": {\n" +
                "                                                \"type\": \"specific\",\n" +
                "                                                \"resource_id\": \"DXXXXXXXX\"\n" +
                "                                        }\n" +
                "                                },\n" +
                "                                \"scopes\": [\n" +
                "                                        \"chat:write:user\",\n" +
                "                                        \"im:read\",\n" +
                "                                        \"im:history\",\n" +
                "                                        \"commands\"\n" +
                "                                ]\n" +
                "                        }\n" +
                "                ]\n" +
                "}";
        ResourcesRemovedEvent event = GsonFactory.createSnakeCase().fromJson(json, ResourcesRemovedEvent.class);
        assertThat(event.getType(), is("resources_removed"));
        assertThat(event.getResources().size(), is(1));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        ResourcesRemovedEvent event = new ResourcesRemovedEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"resources_removed\"}";
        assertThat(generatedJson, is(expectedJson));
    }
}
