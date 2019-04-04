package com.github.seratch.jslack.api.model.event;

import com.github.seratch.jslack.common.json.GsonFactory;
import com.google.gson.Gson;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ChannelUnarchiveEventTest {

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"channel_unarchive\",\n" +
                "    \"channel\": \"C024BE91L\",\n" +
                "    \"user\": \"U024BE7LH\"\n" +
                "}";
        ChannelUnarchiveEvent event = GsonFactory.createSnakeCase().fromJson(json, ChannelUnarchiveEvent.class);
        assertThat(event.getType(), is("channel_unarchive"));
        assertThat(event.getChannel(), is("C024BE91L"));
        assertThat(event.getUser(), is("U024BE7LH"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        ChannelUnarchiveEvent event = new ChannelUnarchiveEvent();
        event.setChannel("c");
        event.setUser("u");
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"channel_unarchive\",\"channel\":\"c\",\"user\":\"u\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}