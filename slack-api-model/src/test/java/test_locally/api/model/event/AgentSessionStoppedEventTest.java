package test_locally.api.model.event;

import com.google.gson.Gson;
import com.slack.api.model.event.AgentSessionStoppedEvent;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class AgentSessionStoppedEventTest {

    @Test
    public void typeName() {
        assertThat(AgentSessionStoppedEvent.TYPE_NAME, is("agent_session_stopped"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"agent_session_stopped\",\n" +
                "    \"channel\": \"C0123ABC456\",\n" +
                "    \"user\": \"U0123ABC456\",\n" +
                "    \"streaming_message_ts\": [\"1787267140.336949\"],\n" +
                "    \"thread_ts\": \"1787267126.742989\",\n" +
                "    \"event_ts\": \"1787267141.948630\"\n" +
                "}";
        AgentSessionStoppedEvent event = GsonFactory.createSnakeCase().fromJson(json, AgentSessionStoppedEvent.class);
        assertThat(event.getType(), is("agent_session_stopped"));
        assertThat(event.getChannel(), is("C0123ABC456"));
        assertThat(event.getUser(), is("U0123ABC456"));
        assertThat(event.getStreamingMessageTs().size(), is(1));
        assertThat(event.getStreamingMessageTs().get(0), is("1787267140.336949"));
        assertThat(event.getThreadTs(), is("1787267126.742989"));
        assertThat(event.getEventTs(), is("1787267141.948630"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        AgentSessionStoppedEvent event = new AgentSessionStoppedEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"agent_session_stopped\"}";
        assertThat(generatedJson, is(expectedJson));
    }
}
