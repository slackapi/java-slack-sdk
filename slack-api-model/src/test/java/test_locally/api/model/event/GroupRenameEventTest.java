package test_locally.api.model.event;

import com.slack.api.model.event.GroupRenameEvent;
import com.google.gson.Gson;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupRenameEventTest {

    @Test
    public void typeName() {
        assertThat(GroupRenameEvent.TYPE_NAME, is("group_rename"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"group_rename\",\n" +
                "    \"channel\": {\n" +
                "        \"id\":\"G02ELGNBH\",\n" +
                "        \"name\":\"new_name\",\n" +
                "        \"created\":1360782804\n" +
                "    }\n" +
                "}";
        GroupRenameEvent event = GsonFactory.createSnakeCase().fromJson(json, GroupRenameEvent.class);
        assertThat(event.getType(), is("group_rename"));
        assertThat(event.getChannel().getId(), is("G02ELGNBH"));
        assertThat(event.getChannel().getName(), is("new_name"));
        assertThat(event.getChannel().getCreated(), is(1360782804));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        GroupRenameEvent event = new GroupRenameEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"group_rename\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}
