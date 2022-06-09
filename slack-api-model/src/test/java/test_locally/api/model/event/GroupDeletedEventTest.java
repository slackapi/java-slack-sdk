package test_locally.api.model.event;

import com.google.gson.Gson;
import com.slack.api.model.event.GroupDeletedEvent;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupDeletedEventTest {

    @Test
    public void typeName() {
        assertThat(GroupDeletedEvent.TYPE_NAME, is("group_deleted"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "  \"channel\": \"G0QN9RGTT\",\n" +
                "  \"date_deleted\": 1654752053,\n" +
                "  \"actor_id\": \"U03E94MK0\",\n" +
                "  \"type\": \"group_deleted\",\n" +
                "  \"event_ts\": \"1654752053.000400\"\n" +
                "}";
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
