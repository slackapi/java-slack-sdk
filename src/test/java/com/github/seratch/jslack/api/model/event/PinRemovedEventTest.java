package com.github.seratch.jslack.api.model.event;

import com.github.seratch.jslack.common.json.GsonFactory;
import com.google.gson.Gson;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class PinRemovedEventTest {

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"pin_removed\",\n" +
                "    \"user\": \"U024BE7LH\",\n" +
                "    \"channel_id\": \"C02ELGNBH\",\n" +
                "    \"item\": {\n" +
                "    },\n" +
                "    \"event_ts\": \"1360782804.083113\"\n" +
                "}";
        PinRemovedEvent event = GsonFactory.createSnakeCase().fromJson(json, PinRemovedEvent.class);
        assertThat(event.getType(), is("pin_removed"));
        assertThat(event.getUser(), is("U024BE7LH"));
        assertThat(event.getChannelId(), is("C02ELGNBH"));
        assertThat(event.getItem(), is(notNullValue()));
        assertThat(event.getEventTs(), is("1360782804.083113"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        PinRemovedEvent event = new PinRemovedEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"pin_removed\",\"has_pins\":false}";
        assertThat(generatedJson, is(expectedJson));
    }

}