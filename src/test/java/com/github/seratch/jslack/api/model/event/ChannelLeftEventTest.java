package com.github.seratch.jslack.api.model.event;

import com.github.seratch.jslack.common.json.GsonFactory;
import com.google.gson.Gson;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ChannelLeftEventTest {

    @Test
    public void typeName() {
        assertThat(ChannelLeftEvent.TYPE_NAME, is("channel_left"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"channel_left\",\n" +
                "    \"channel\": \"C024BE91L\"\n" +
                "}";
        ChannelLeftEvent event = GsonFactory.createSnakeCase().fromJson(json, ChannelLeftEvent.class);
        assertThat(event.getType(), is("channel_left"));
        assertThat(event.getChannel(), is("C024BE91L"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        ChannelLeftEvent event = new ChannelLeftEvent();
        event.setChannel("C024BE91L");
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"channel_left\",\"channel\":\"C024BE91L\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}