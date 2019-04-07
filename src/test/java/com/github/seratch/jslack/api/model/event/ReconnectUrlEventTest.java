package com.github.seratch.jslack.api.model.event;

import com.github.seratch.jslack.common.json.GsonFactory;
import com.google.gson.Gson;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ReconnectUrlEventTest {

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"reconnect_url\"\n" +
                "}";
        ReconnectUrlEvent event = GsonFactory.createSnakeCase().fromJson(json, ReconnectUrlEvent.class);
        assertThat(event.getType(), is("reconnect_url"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        ReconnectUrlEvent event = new ReconnectUrlEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"reconnect_url\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}