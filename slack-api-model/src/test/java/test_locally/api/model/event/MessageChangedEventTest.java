package test_locally.api.model.event;

import com.slack.api.model.event.MessageChangedEvent;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class MessageChangedEventTest {

    @Test
    public void typeName() {
        assertThat(MessageChangedEvent.TYPE_NAME, is("message"));
        assertThat(MessageChangedEvent.SUBTYPE_NAME, is("message_changed"));
    }

    @Test
    public void deserialize_simple() {
        String json = "{\n" +
                "  \"type\": \"message\",\n" +
                "  \"subtype\": \"message_changed\",\n" +
                "  \"hidden\": true,\n" +
                "  \"message\": {\n" +
                "    \"client_msg_id\": \"332295b5-cfb1-4884-8091-c16be84690ba\",\n" +
                "    \"type\": \"message\",\n" +
                "    \"text\": \"updated message\",\n" +
                "    \"user\": \"U111\",\n" +
                "    \"team\": \"T111\",\n" +
                "    \"edited\": {\n" +
                "      \"user\": \"U111\",\n" +
                "      \"ts\": \"1626342137.000000\"\n" +
                "    },\n" +
                "    \"blocks\": [],\n" +
                "    \"thread_ts\": \"1626342092.008500\",\n" +
                "    \"parent_user_id\": \"U111\",\n" +
                "    \"ts\": \"1626342128.008700\",\n" +
                "    \"source_team\": \"T111\",\n" +
                "    \"user_team\": \"T111\"\n" +
                "  },\n" +
                "  \"channel\": \"C111\",\n" +
                "  \"previous_message\": {\n" +
                "    \"client_msg_id\": \"332295b5-cfb1-4884-8091-c16be84690ba\",\n" +
                "    \"type\": \"message\",\n" +
                "    \"text\": \"test\",\n" +
                "    \"user\": \"U111\",\n" +
                "    \"ts\": \"1626342128.008700\",\n" +
                "    \"team\": \"T111\",\n" +
                "    \"blocks\": [],\n" +
                "    \"thread_ts\": \"1626342092.008500\",\n" +
                "    \"parent_user_id\": \"U111\"\n" +
                "  },\n" +
                "  \"event_ts\": \"1626342137.008900\",\n" +
                "  \"ts\": \"1626342137.008900\",\n" +
                "  \"channel_type\": \"channel\"\n" +
                "}";
        MessageChangedEvent event = GsonFactory.createSnakeCase().fromJson(json, MessageChangedEvent.class);
        assertThat(event.getType(), is("message"));
        assertThat(event.getSubtype(), is("message_changed"));
        assertThat(event.getMessage().getThreadTs(), is("1626342092.008500"));
        assertThat(event.getPreviousMessage().getThreadTs(), is("1626342092.008500"));
    }

}
