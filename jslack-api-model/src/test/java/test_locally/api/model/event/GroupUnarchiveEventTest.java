package test_locally.api.model.event;

import com.github.seratch.jslack.api.model.event.GroupUnarchiveEvent;
import test_locally.unit.GsonFactory;
import com.google.gson.Gson;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GroupUnarchiveEventTest {

    @Test
    public void typeName() {
        assertThat(GroupUnarchiveEvent.TYPE_NAME, is("group_unarchive"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"group_unarchive\",\n" +
                "    \"channel\": \"G024BE91L\"\n" +
                "}";
        GroupUnarchiveEvent event = GsonFactory.createSnakeCase().fromJson(json, GroupUnarchiveEvent.class);
        assertThat(event.getType(), is("group_unarchive"));
        assertThat(event.getChannel(), is("G024BE91L"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        GroupUnarchiveEvent event = new GroupUnarchiveEvent();
        event.setChannel("c");
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"group_unarchive\",\"channel\":\"c\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}