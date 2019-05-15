package test_locally.api.model.event;

import com.github.seratch.jslack.api.model.event.ResourcesAddedEvent;
import test_locally.unit.GsonFactory;
import com.google.gson.Gson;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ResourcesAddedEventTest {

    @Test
    public void typeName() {
        assertThat(ResourcesAddedEvent.TYPE_NAME, is("resources_added"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "                \"type\": \"resources_added\",\n" +
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
        ResourcesAddedEvent event = GsonFactory.createSnakeCase().fromJson(json, ResourcesAddedEvent.class);
        assertThat(event.getType(), is("resources_added"));
        assertThat(event.getResources().size(), is(1));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        ResourcesAddedEvent event = new ResourcesAddedEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"resources_added\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}