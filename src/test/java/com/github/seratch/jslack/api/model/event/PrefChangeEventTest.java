package com.github.seratch.jslack.api.model.event;

import com.github.seratch.jslack.common.json.GsonFactory;
import com.google.gson.Gson;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PrefChangeEventTest {

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"pref_change\",\n" +
                "    \"name\": \"messages_theme\",\n" +
                "    \"value\": \"dense\"\n" +
                "}";
        PrefChangeEvent event = GsonFactory.createSnakeCase().fromJson(json, PrefChangeEvent.class);
        assertThat(event.getType(), is("pref_change"));
        assertThat(event.getName(), is("messages_theme"));
        assertThat(event.getValue(), is("dense"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        PrefChangeEvent event = new PrefChangeEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"pref_change\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}