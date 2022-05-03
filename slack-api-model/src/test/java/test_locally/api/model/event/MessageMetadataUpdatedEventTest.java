package test_locally.api.model.event;

import com.google.gson.Gson;
import com.slack.api.model.event.MessageMetadataUpdatedEvent;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class MessageMetadataUpdatedEventTest {

    @Test
    public void typeName() {
        assertThat(MessageMetadataUpdatedEvent.TYPE_NAME, is("message_metadata_updated"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "  \"type\": \"message_metadata_updated\",\n" +
                "  \"channel_id\": \"C111\",\n" +
                "  \"event_ts\": \"1651474126.005400\",\n" +
                "  \"previous_metadata\": {\n" +
                "    \"event_type\": \"java-sdk-test-example\",\n" +
                "    \"event_payload\": {\n" +
                "      \"state\": \"initial\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"app_id\": \"A111\",\n" +
                "  \"bot_id\": \"B111\",\n" +
                "  \"user_id\": \"U111\",\n" +
                "  \"team_id\": \"T111\",\n" +
                "  \"message_ts\": \"1651474122.891839\",\n" +
                "  \"metadata\": {\n" +
                "    \"event_type\": \"java-sdk-test-example\",\n" +
                "    \"event_payload\": {\n" +
                "      \"state\": \"modified\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
        MessageMetadataUpdatedEvent event = GsonFactory.createSnakeCase().fromJson(json, MessageMetadataUpdatedEvent.class);
        assertThat(event.getType(), is("message_metadata_updated"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        MessageMetadataUpdatedEvent event = new MessageMetadataUpdatedEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"message_metadata_updated\"}";
        assertThat(generatedJson, is(expectedJson));
    }
}
