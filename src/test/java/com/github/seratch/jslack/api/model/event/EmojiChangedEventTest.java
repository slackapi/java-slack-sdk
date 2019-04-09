package com.github.seratch.jslack.api.model.event;

import com.github.seratch.jslack.common.json.GsonFactory;
import com.google.gson.Gson;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class EmojiChangedEventTest {

    @Test
    public void typeName() {
        assertThat(EmojiChangedEvent.TYPE_NAME, is("emoji_changed"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"emoji_changed\",\n" +
                "    \"subtype\": \"remove\",\n" +
                "    \"names\": [\"picard_facepalm\"],\n" +
                "    \"event_ts\" : \"1361482916.000004\"\n" +
                "}";
        EmojiChangedEvent event = GsonFactory.createSnakeCase().fromJson(json, EmojiChangedEvent.class);
        assertThat(event.getType(), is("emoji_changed"));
        assertThat(event.getSubtype(), is("remove"));
        assertThat(event.getNames(), is(Arrays.asList("picard_facepalm")));
        assertThat(event.getEventTs(), is("1361482916.000004"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        EmojiChangedEvent event = new EmojiChangedEvent();
        event.setEventTs("123");
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"emoji_changed\",\"event_ts\":\"123\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}