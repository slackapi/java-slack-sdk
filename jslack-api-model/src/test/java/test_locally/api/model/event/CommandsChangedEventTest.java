package test_locally.api.model.event;

import com.github.seratch.jslack.api.model.event.CommandsChangedEvent;
import com.google.gson.Gson;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CommandsChangedEventTest {

    @Test
    public void typeName() {
        assertThat(CommandsChangedEvent.TYPE_NAME, is("commands_changed"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"commands_changed\",\n" +
                "    \"event_ts\" : \"1361482916.000004\"\n" +
                "}";
        CommandsChangedEvent event = GsonFactory.createSnakeCase().fromJson(json, CommandsChangedEvent.class);
        assertThat(event.getType(), is("commands_changed"));
        assertThat(event.getEventTs(), is("1361482916.000004"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        CommandsChangedEvent event = new CommandsChangedEvent();
        event.setEventTs("123");
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"commands_changed\",\"event_ts\":\"123\"}";
        assertThat(generatedJson, is(expectedJson));
    }
}
