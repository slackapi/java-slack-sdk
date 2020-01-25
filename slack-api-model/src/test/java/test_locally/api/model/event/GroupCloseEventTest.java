package test_locally.api.model.event;

import com.slack.api.model.event.GroupCloseEvent;
import com.google.gson.Gson;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCloseEventTest {

    @Test
    public void typeName() {
        assertThat(GroupCloseEvent.TYPE_NAME, is("group_close"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"group_close\",\n" +
                "    \"user\": \"U024BE7LH\",\n" +
                "    \"channel\": \"G024BE91L\"\n" +
                "}";
        GroupCloseEvent event = GsonFactory.createSnakeCase().fromJson(json, GroupCloseEvent.class);
        assertThat(event.getType(), is("group_close"));
        assertThat(event.getUser(), is("U024BE7LH"));
        assertThat(event.getChannel(), is("G024BE91L"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        GroupCloseEvent event = new GroupCloseEvent();
        event.setChannel("c");
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"group_close\",\"channel\":\"c\"}";
        assertThat(generatedJson, is(expectedJson));
    }
}
