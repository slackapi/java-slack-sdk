package test_locally.api.model.event;

import com.slack.api.model.event.UserResourceRemovedEvent;
import com.google.gson.Gson;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class UserResourceRemovedEventTest {

    @Test
    public void typeName() {
        assertThat(UserResourceRemovedEvent.TYPE_NAME, is("user_resource_removed"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "        \"type\": \"user_resource_removed\",\n" +
                "        \"user\": \"WXXXXXXXX\",\n" +
                "        \"trigger_id\": \"27082968880.6048553856.5eb9c671f75c636135fdb6bb9e87b606\"\n" +
                "}";
        UserResourceRemovedEvent event = GsonFactory.createSnakeCase().fromJson(json, UserResourceRemovedEvent.class);
        assertThat(event.getType(), is("user_resource_removed"));
        assertThat(event.getUser(), is("WXXXXXXXX"));
        assertThat(event.getTriggerId(), is("27082968880.6048553856.5eb9c671f75c636135fdb6bb9e87b606"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        UserResourceRemovedEvent event = new UserResourceRemovedEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"user_resource_removed\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}
