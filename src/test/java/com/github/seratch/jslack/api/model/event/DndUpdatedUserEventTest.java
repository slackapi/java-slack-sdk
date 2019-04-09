package com.github.seratch.jslack.api.model.event;

import com.github.seratch.jslack.common.json.GsonFactory;
import com.google.gson.Gson;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class DndUpdatedUserEventTest {

    @Test
    public void typeName() {
        assertThat(DndUpdatedUserEvent.TYPE_NAME, is("dnd_updated_user"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"dnd_updated_user\",\n" +
                "    \"user\": \"U1234\",\n" +
                "    \"dnd_status\": {\n" +
                "        \"dnd_enabled\": true,\n" +
                "        \"next_dnd_start_ts\": 1450387800,\n" +
                "        \"next_dnd_end_ts\": 1450423800\n" +
                "    }\n" +
                "}";
        DndUpdatedUserEvent event = GsonFactory.createSnakeCase().fromJson(json, DndUpdatedUserEvent.class);
        assertThat(event.getType(), is("dnd_updated_user"));
        assertThat(event.getUser(), is("U1234"));
        assertThat(event.getDndStatus(), is(notNullValue()));
        assertThat(event.getDndStatus().isDndEnabled(), is(true));
        assertThat(event.getDndStatus().getNextDndStartTs(), is(1450387800));
        assertThat(event.getDndStatus().getNextDndEndTs(), is(1450423800));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        DndUpdatedUserEvent event = new DndUpdatedUserEvent();
        event.setUser("u");
        event.setDndStatus(new DndUpdatedUserEvent.DndStatus());
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"dnd_updated_user\",\"user\":\"u\",\"dnd_status\":{\"dnd_enabled\":false}}";
        assertThat(generatedJson, is(expectedJson));
    }

}