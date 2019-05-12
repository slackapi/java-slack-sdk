package test_locally.api.model.event;

import com.github.seratch.jslack.api.model.event.GroupArchiveEvent;
import com.github.seratch.jslack.common.json.GsonFactory;
import com.google.gson.Gson;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GroupArchiveEventTest {

    @Test
    public void typeName() {
        assertThat(GroupArchiveEvent.TYPE_NAME, is("group_archive"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"group_archive\",\n" +
                "    \"channel\": \"G024BE91L\"\n" +
                "}";
        GroupArchiveEvent event = GsonFactory.createSnakeCase().fromJson(json, GroupArchiveEvent.class);
        assertThat(event.getType(), is("group_archive"));
        assertThat(event.getChannel(), is("G024BE91L"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        GroupArchiveEvent event = new GroupArchiveEvent();
        event.setChannel("c");
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"group_archive\",\"channel\":\"c\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}