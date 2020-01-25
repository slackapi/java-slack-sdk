package test_locally.api.model.event;

import com.slack.api.model.event.ScopeDeniedEvent;
import com.google.gson.Gson;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ScopeDeniedEventTest {

    @Test
    public void typeName() {
        assertThat(ScopeDeniedEvent.TYPE_NAME, is("scope_denied"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "        \"type\": \"scope_denied\",\n" +
                "        \"scopes\": [\n" +
                "            \"files:read\",\n" +
                "            \"files:write\",\n" +
                "            \"chat:write\"\n" +
                "        ],\n" +
                "        \"trigger_id\": \"241582872337.47445629121.string\"\n" +
                "}";
        ScopeDeniedEvent event = GsonFactory.createSnakeCase().fromJson(json, ScopeDeniedEvent.class);
        assertThat(event.getType(), is("scope_denied"));
        assertThat(event.getScopes().size(), is(3));
        assertThat(event.getTriggerId(), is("241582872337.47445629121.string"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        ScopeDeniedEvent event = new ScopeDeniedEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"scope_denied\"}";
        assertThat(generatedJson, is(expectedJson));
    }
}
