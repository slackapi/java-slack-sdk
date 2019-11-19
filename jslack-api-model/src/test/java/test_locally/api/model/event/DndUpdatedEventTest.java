package test_locally.api.model.event;

import com.github.seratch.jslack.api.model.event.DndUpdatedEvent;
import com.google.gson.Gson;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class DndUpdatedEventTest {

    @Test
    public void typeName() {
        assertThat(DndUpdatedEvent.TYPE_NAME, is("dnd_updated"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"dnd_updated\",\n" +
                "    \"user\": \"U1234\",\n" +
                "    \"dnd_status\": {\n" +
                "        \"dnd_enabled\": true,\n" +
                "        \"next_dnd_start_ts\": 1450387800,\n" +
                "        \"next_dnd_end_ts\": 1450423800,\n" +
                "        \"snooze_enabled\": true,\n" +
                "        \"snooze_endtime\": 1450373897\n" +
                "    }\n" +
                "}";
        DndUpdatedEvent event = GsonFactory.createSnakeCase().fromJson(json, DndUpdatedEvent.class);
        assertThat(event.getType(), is("dnd_updated"));
        assertThat(event.getUser(), is("U1234"));
        assertThat(event.getDndStatus(), is(notNullValue()));
        assertThat(event.getDndStatus().isDndEnabled(), is(true));
        assertThat(event.getDndStatus().getNextDndStartTs(), is(1450387800));
        assertThat(event.getDndStatus().getNextDndEndTs(), is(1450423800));
        assertThat(event.getDndStatus().isSnoozeEnabled(), is(true));
        assertThat(event.getDndStatus().getSnoozeEndtime(), is(1450373897));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        DndUpdatedEvent event = new DndUpdatedEvent();
        event.setUser("u");
        event.setDndStatus(new DndUpdatedEvent.DndStatus());
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"dnd_updated\",\"user\":\"u\",\"dnd_status\":{\"dnd_enabled\":false,\"snooze_enabled\":false}}";
        assertThat(generatedJson, is(expectedJson));
    }

}
