package test_locally.api.model.event;

import com.github.seratch.jslack.api.model.event.GroupDeletedEvent;
import com.github.seratch.jslack.common.json.GsonFactory;
import com.google.gson.Gson;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GroupDeletedEventTest {

    @Test
    public void typeName() {
        assertThat(GroupDeletedEvent.TYPE_NAME, is("group_deleted"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"group_deleted\",\n" +
                "    \"channel\": \"G0QN9RGTT\"\n" +
                "}\n";
        GroupDeletedEvent event = GsonFactory.createSnakeCase().fromJson(json, GroupDeletedEvent.class);
        assertThat(event.getType(), is("group_deleted"));
        assertThat(event.getChannel(), is("G0QN9RGTT"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        GroupDeletedEvent event = new GroupDeletedEvent();
        event.setChannel("c");
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"group_deleted\",\"channel\":\"c\"}";
        assertThat(generatedJson, is(expectedJson));
    }
}