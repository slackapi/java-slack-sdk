package test_locally.api.model.event;

import com.google.gson.Gson;
import com.slack.api.model.event.MessageMetadataPostedEvent;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class MessageMetadataPostedEventTest {

    @Test
    public void typeName() {
        assertThat(MessageMetadataPostedEvent.TYPE_NAME, is("message_metadata_posted"));
    }

    @Test
    public void deserialize_simple() {
        String json = "{\n" +
                "  \"type\": \"message_metadata_posted\",\n" +
                "  \"app_id\": \"A111\",\n" +
                "  \"bot_id\": \"B111\",\n" +
                "  \"user_id\": \"U111\",\n" +
                "  \"team_id\": \"T111\",\n" +
                "  \"channel_id\": \"C111\",\n" +
                "  \"metadata\": {\n" +
                "    \"event_type\": \"java-sdk-test-example\",\n" +
                "    \"event_payload\": {\n" +
                "      \"state\": \"initial\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"message_ts\": \"1651474122.891839\",\n" +
                "  \"event_ts\": \"1651474122.891839\"\n" +
                "}";
        MessageMetadataPostedEvent event = GsonFactory.createSnakeCase().fromJson(json, MessageMetadataPostedEvent.class);
        assertThat(event.getType(), is("message_metadata_posted"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        MessageMetadataPostedEvent event = new MessageMetadataPostedEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"message_metadata_posted\"}";
        assertThat(generatedJson, is(expectedJson));
    }
}
