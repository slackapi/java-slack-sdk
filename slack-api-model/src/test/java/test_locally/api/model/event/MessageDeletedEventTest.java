package test_locally.api.model.event;

import com.slack.api.model.event.MessageDeletedEvent;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class MessageDeletedEventTest {

    @Test
    public void typeName() {
        assertThat(MessageDeletedEvent.TYPE_NAME, is("message"));
        assertThat(MessageDeletedEvent.SUBTYPE_NAME, is("message_deleted"));
    }

    @Test
    public void with_tombstone() {
        String json = "{\n" +
                "  \"type\": \"message\",\n" +
                "  \"subtype\": \"message_deleted\",\n" +
                "  \"hidden\": true,\n" +
                "  \"deleted_ts\": \"1626387317.008600\",\n" +
                "  \"channel\": \"C111\",\n" +
                "  \"previous_message\": {\n" +
                "    \"bot_id\": \"B111\",\n" +
                "    \"type\": \"message\",\n" +
                "    \"text\": \"test prep\",\n" +
                "    \"user\": \"U111\",\n" +
                "    \"ts\": \"1626387317.008600\",\n" +
                "    \"team\": \"T111\",\n" +
                "    \"bot_profile\": {\n" +
                "      \"id\": \"B111\",\n" +
                "      \"deleted\": false,\n" +
                "      \"name\": \"SDK Testing App\",\n" +
                "      \"updated\": 1622167148,\n" +
                "      \"app_id\": \"A111\",\n" +
                "      \"icons\": {\n" +
                "        \"image_36\": \"https://avatars.slack-edge.com/2021-05-27/xxx.png\",\n" +
                "        \"image_48\": \"https://avatars.slack-edge.com/2021-05-27/xxx.png\",\n" +
                "        \"image_72\": \"https://avatars.slack-edge.com/2021-05-27/xxx.png\"\n" +
                "      },\n" +
                "      \"team_id\": \"T111\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"event_ts\": \"1626387318.008700\",\n" +
                "  \"ts\": \"1626387318.008700\",\n" +
                "  \"channel_type\": \"channel\"\n" +
                "}";
        MessageDeletedEvent event = GsonFactory.createSnakeCase().fromJson(json, MessageDeletedEvent.class);
        assertThat(event.getType(), is("message"));
        assertThat(event.getSubtype(), is("message_deleted"));
    }
}
