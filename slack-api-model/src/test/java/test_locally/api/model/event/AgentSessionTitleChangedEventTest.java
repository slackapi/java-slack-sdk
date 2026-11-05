package test_locally.api.model.event;

import com.google.gson.Gson;
import com.slack.api.model.event.AgentSessionTitleChangedEvent;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class AgentSessionTitleChangedEventTest {

    @Test
    public void typeName() {
        assertThat(AgentSessionTitleChangedEvent.TYPE_NAME, is("agent_session_title_changed"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"agent_session_title_changed\",\n" +
                "    \"channel\": \"C0123ABC456\",\n" +
                "    \"user\": \"U0123ABC456\",\n" +
                "    \"team_id\": \"T0123ABC456\",\n" +
                "    \"thread_ts\": \"1787267269.063879\",\n" +
                "    \"title\": \"New title\",\n" +
                "    \"previous_title\": \"Old title\",\n" +
                "    \"event_ts\": \"1787267281.002810\"\n" +
                "}";
        AgentSessionTitleChangedEvent event = GsonFactory.createSnakeCase().fromJson(json, AgentSessionTitleChangedEvent.class);
        assertThat(event.getType(), is("agent_session_title_changed"));
        assertThat(event.getChannel(), is("C0123ABC456"));
        assertThat(event.getUser(), is("U0123ABC456"));
        assertThat(event.getTeamId(), is("T0123ABC456"));
        assertThat(event.getThreadTs(), is("1787267269.063879"));
        assertThat(event.getTitle(), is("New title"));
        assertThat(event.getPreviousTitle(), is("Old title"));
        assertThat(event.getEventTs(), is("1787267281.002810"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        AgentSessionTitleChangedEvent event = new AgentSessionTitleChangedEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"agent_session_title_changed\"}";
        assertThat(generatedJson, is(expectedJson));
    }
}
