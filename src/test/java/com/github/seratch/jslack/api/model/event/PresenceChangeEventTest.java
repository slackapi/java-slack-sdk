package com.github.seratch.jslack.api.model.event;

import com.github.seratch.jslack.common.json.GsonFactory;
import com.google.gson.Gson;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PresenceChangeEventTest {

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"presence_change\",\n" +
                "    \"user\": \"U024BE7LH\",\n" +
                "    \"presence\": \"away\"\n" +
                "}\n";
        PresenceChangeEvent event = GsonFactory.createSnakeCase().fromJson(json, PresenceChangeEvent.class);
        assertThat(event.getType(), is("presence_change"));
        assertThat(event.getUser(), is("U024BE7LH"));
        assertThat(event.getPresence(), is("away"));
    }

    @Test
    public void deserialize_multi() {
        String json = "{\n" +
                "    \"type\": \"presence_change\",\n" +
                "    \"users\": [\"U024BE7LH\", \"U024BE7LA\"] ,\n" +
                "    \"presence\": \"away\"\n" +
                "}\n";
        PresenceChangeEvent event = GsonFactory.createSnakeCase().fromJson(json, PresenceChangeEvent.class);
        assertThat(event.getType(), is("presence_change"));
        assertThat(event.getUsers(), is(Arrays.asList("U024BE7LH", "U024BE7LA")));
        assertThat(event.getPresence(), is("away"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        PresenceChangeEvent event = new PresenceChangeEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"presence_change\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}