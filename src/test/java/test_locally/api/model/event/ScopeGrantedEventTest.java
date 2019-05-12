package test_locally.api.model.event;

import com.github.seratch.jslack.api.model.event.ScopeGrantedEvent;
import com.github.seratch.jslack.common.json.GsonFactory;
import com.google.gson.Gson;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ScopeGrantedEventTest {

    @Test
    public void typeName() {
        assertThat(ScopeGrantedEvent.TYPE_NAME, is("scope_granted"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "        \"type\": \"scope_granted\",\n" +
                "        \"scopes\": [\n" +
                "            \"files:read\",\n" +
                "            \"files:write\",\n" +
                "            \"chat:write\"\n" +
                "        ],\n" +
                "        \"trigger_id\": \"241582872337.47445629121.string\"\n" +
                "}";
        ScopeGrantedEvent event = GsonFactory.createSnakeCase().fromJson(json, ScopeGrantedEvent.class);
        assertThat(event.getType(), is("scope_granted"));
        assertThat(event.getScopes().size(), is(3));
        assertThat(event.getTriggerId(), is("241582872337.47445629121.string"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        ScopeGrantedEvent event = new ScopeGrantedEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"scope_granted\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}