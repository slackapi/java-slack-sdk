package test_locally.api.model.event;

import com.google.gson.Gson;
import com.slack.api.model.event.GroupMarkedEvent;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupMarkedEventTest {

    @Test
    public void typeName() {
        assertThat(GroupMarkedEvent.TYPE_NAME, is("group_marked"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"group_marked\",\n" +
                "    \"channel\": \"G024BE91L\",\n" +
                "    \"ts\": \"1401383885.000061\"\n" +
                "}";
        GroupMarkedEvent event = GsonFactory.createSnakeCase().fromJson(json, GroupMarkedEvent.class);
        assertThat(event.getType(), is("group_marked"));
        assertThat(event.getChannel(), is("G024BE91L"));
        assertThat(event.getTs(), is("1401383885.000061"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        GroupMarkedEvent event = new GroupMarkedEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"group_marked\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}
