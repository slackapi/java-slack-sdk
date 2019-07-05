package test_locally.api.model.event;

import com.github.seratch.jslack.api.model.event.GroupJoinedEvent;
import com.google.gson.Gson;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GroupJoinedEventTest {

    @Test
    public void typeName() {
        assertThat(GroupJoinedEvent.TYPE_NAME, is("group_joined"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"group_joined\",\n" +
                "    \"channel\": {\n" +
                "    }\n" +
                "}";
        GroupJoinedEvent event = GsonFactory.createSnakeCase().fromJson(json, GroupJoinedEvent.class);
        assertThat(event.getType(), is("group_joined"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        GroupJoinedEvent event = new GroupJoinedEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"group_joined\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}