package test_locally.api.model.event;

import com.github.seratch.jslack.api.model.event.ReactionRemovedEvent;
import test_locally.unit.GsonFactory;
import com.google.gson.Gson;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ReactionRemovedEventTest {

    @Test
    public void typeName() {
        assertThat(ReactionRemovedEvent.TYPE_NAME, is("reaction_removed"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"reaction_removed\",\n" +
                "    \"user\": \"U024BE7LH\",\n" +
                "    \"reaction\": \"thumbsup\",\n" +
                "    \"item_user\": \"U0G9QF9C6\",\n" +
                "    \"item\": {\n" +
                "        \"type\": \"message\",\n" +
                "        \"channel\": \"C0G9QF9GZ\",\n" +
                "        \"ts\": \"1360782400.498405\"\n" +
                "    },\n" +
                "    \"event_ts\": \"1360782804.083113\"\n" +
                "}";
        ReactionRemovedEvent event = GsonFactory.createSnakeCase().fromJson(json, ReactionRemovedEvent.class);
        assertThat(event.getType(), is("reaction_removed"));
        assertThat(event.getUser(), is("U024BE7LH"));
        assertThat(event.getReaction(), is("thumbsup"));
        assertThat(event.getItemUser(), is("U0G9QF9C6"));
        assertThat(event.getItem().getType(), is("message"));
        assertThat(event.getItem().getChannel(), is("C0G9QF9GZ"));
        assertThat(event.getItem().getTs(), is("1360782400.498405"));
        assertThat(event.getEventTs(), is("1360782804.083113"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        ReactionRemovedEvent event = new ReactionRemovedEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"reaction_removed\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}