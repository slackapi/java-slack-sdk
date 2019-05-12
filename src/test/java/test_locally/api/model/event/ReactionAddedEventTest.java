package test_locally.api.model.event;

import com.github.seratch.jslack.api.model.event.ReactionAddedEvent;
import com.github.seratch.jslack.common.json.GsonFactory;
import com.google.gson.Gson;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ReactionAddedEventTest {

    @Test
    public void typeName() {
        assertThat(ReactionAddedEvent.TYPE_NAME, is("reaction_added"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"reaction_added\",\n" +
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
        ReactionAddedEvent event = GsonFactory.createSnakeCase().fromJson(json, ReactionAddedEvent.class);
        assertThat(event.getType(), is("reaction_added"));
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
        ReactionAddedEvent event = new ReactionAddedEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"reaction_added\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}