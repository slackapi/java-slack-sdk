package test_locally.api.model.event;

import com.github.seratch.jslack.api.model.event.GroupOpenEvent;
import test_locally.unit.GsonFactory;
import com.google.gson.Gson;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GroupOpenEventTest {

    @Test
    public void typeName() {
        assertThat(GroupOpenEvent.TYPE_NAME, is("group_open"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"group_open\",\n" +
                "    \"user\": \"U024BE7LH\",\n" +
                "    \"channel\": \"G024BE91L\"\n" +
                "}";
        GroupOpenEvent event = GsonFactory.createSnakeCase().fromJson(json, GroupOpenEvent.class);
        assertThat(event.getType(), is("group_open"));
        assertThat(event.getChannel(), is("G024BE91L"));
        assertThat(event.getUser(), is("U024BE7LH"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        GroupOpenEvent event = new GroupOpenEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"group_open\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}