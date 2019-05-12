package test_locally.api.model.event;

import com.github.seratch.jslack.api.model.event.UserResourceGrantedEvent;
import com.github.seratch.jslack.common.json.GsonFactory;
import com.google.gson.Gson;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class UserResourceGrantedEventTest {

    @Test
    public void typeName() {
        assertThat(UserResourceGrantedEvent.TYPE_NAME, is("user_resource_granted"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "        \"type\": \"user_resource_granted\",\n" +
                "        \"user\": \"WXXXXXXXX\",\n" +
                "        \"scopes\": [\n" +
                "            \"reminders:write:user\",\n" +
                "            \"reminders:read:user\"\n" +
                "        ],\n" +
                "        \"trigger_id\": \"27082968880.6048553856.5eb9c671f75c636135fdb6bb9e87b606\"\n" +
                "}";
        UserResourceGrantedEvent event = GsonFactory.createSnakeCase().fromJson(json, UserResourceGrantedEvent.class);
        assertThat(event.getType(), is("user_resource_granted"));
        assertThat(event.getUser(), is("WXXXXXXXX"));
        assertThat(event.getScopes(), is(Arrays.asList("reminders:write:user", "reminders:read:user")));
        assertThat(event.getTriggerId(), is("27082968880.6048553856.5eb9c671f75c636135fdb6bb9e87b606"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        UserResourceGrantedEvent event = new UserResourceGrantedEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"user_resource_granted\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}