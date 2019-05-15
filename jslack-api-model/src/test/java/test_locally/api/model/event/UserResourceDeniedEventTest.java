package test_locally.api.model.event;

import com.github.seratch.jslack.api.model.event.UserResourceDeniedEvent;
import test_locally.unit.GsonFactory;
import com.google.gson.Gson;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class UserResourceDeniedEventTest {

    @Test
    public void typeName() {
        assertThat(UserResourceDeniedEvent.TYPE_NAME, is("user_resource_denied"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "        \"type\": \"user_resource_denied\",\n" +
                "        \"user\": \"WXXXXXXXX\",\n" +
                "        \"scopes\": [\n" +
                "            \"reminders:write:user\",\n" +
                "            \"reminders:read:user\"\n" +
                "        ],\n" +
                "        \"trigger_id\": \"27082968880.6048553856.5eb9c671f75c636135fdb6bb9e87b606\"\n" +
                "}\n";
        UserResourceDeniedEvent event = GsonFactory.createSnakeCase().fromJson(json, UserResourceDeniedEvent.class);
        assertThat(event.getType(), is("user_resource_denied"));
        assertThat(event.getUser(), is("WXXXXXXXX"));
        assertThat(event.getScopes(), is(Arrays.asList("reminders:write:user", "reminders:read:user")));
        assertThat(event.getTriggerId(), is("27082968880.6048553856.5eb9c671f75c636135fdb6bb9e87b606"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        UserResourceDeniedEvent event = new UserResourceDeniedEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"user_resource_denied\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}