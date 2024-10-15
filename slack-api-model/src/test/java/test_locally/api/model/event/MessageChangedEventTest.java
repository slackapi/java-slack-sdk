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
        assertThat(event.getPreviousMessage().getMessage().getText(), is("test"));
        assertThat(event.getPreviousMessage().getMessage().getThreadTs(), is("1626342092.008500"));
    }

    @Test
    public void deserialize_issue_1384() {
        // https://github.com/slackapi/java-slack-sdk/issues/1384
        String json = "{\n" +
                "  \"type\": \"message\",\n" +
                "  \"subtype\": \"message_changed\",\n" +
                "  \"message\": {\n" +
                "    \"subtype\": \"tombstone\",\n" +
                "    \"text\": \"This message was deleted.\",\n" +
                "    \"user\": \"USLACKBOT\",\n" +
                "    \"hidden\": true,\n" +
                "    \"type\": \"message\",\n" +
                "    \"thread_ts\": \"1728639839.746989\",\n" +
                "    \"reply_count\": 1,\n" +
                "    \"reply_users_count\": 1,\n" +
                "    \"latest_reply\": \"1728639845.335099\",\n" +
                "    \"reply_users\": [\n" +
                "      \"U06SSK\"\n" +
                "    ],\n" +
                "    \"is_locked\": false,\n" +
                "    \"ts\": \"1728639839.746989\"\n" +
                "  },\n" +
                "  \"previous_message\": {\n" +
                "    \"user\": \"U06TFGNC\",\n" +
                "    \"type\": \"message\",\n" +
                "    \"ts\": \"1728639839.746989\",\n" +
                "    \"client_msg_id\": \"22b850d7_f2f3bd548f9e\",\n" +
                "    \"text\": \"need help with VPN\",\n" +
                "    \"team\": \"T06SCC\",\n" +
                "    \"thread_ts\": \"1728639839.746989\",\n" +
                "    \"reply_count\": 1,\n" +
                "    \"reply_users_count\": 1,\n" +
                "    \"latest_reply\": \"1728639845.335099\",\n" +
                "    \"reply_users\": [\n" +
                "      \"U0UK\"\n" +
                "    ],\n" +
                "    \"is_locked\": false,\n" +
                "    \"subscribed\": true,\n" +
                "    \"last_read\": \"1728639845.335099\",\n" +
                "    \"blocks\": [\n" +
                "      {\n" +
                "        \"type\": \"rich_text\",\n" +
                "        \"block_id\": \"x18EO\",\n" +
                "        \"elements\": [\n" +
                "          {\n" +
                "            \"type\": \"rich_text_section\",\n" +
                "            \"elements\": [\n" +
                "              {\n" +
                "                \"type\": \"text\",\n" +
                "                \"text\": \"need help with VPN\"\n" +
                "              }\n" +
                "            ]\n" +
                "          }\n" +
                "        ]\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  \"channel\": \"D0697V\",\n" +
                "  \"hidden\": true,\n" +
                "  \"ts\": \"1728639909.003400\",\n" +
                "  \"event_ts\": \"1728639909.003400\",\n" +
                "  \"channel_type\": \"im\"\n" +
                "}\n";
        MessageChangedEvent event = GsonFactory.createSnakeCase().fromJson(json, MessageChangedEvent.class);
        assertThat(event.getType(), is("message"));
        assertThat(event.getSubtype(), is("message_changed"));
        assertThat(event.getMessage().getUser(), is("USLACKBOT"));
        assertThat(event.getPreviousMessage().getMessage().getUser(), is("U06TFGNC"));
    }

    @Test
    public void with_tombstone() {
        String json = "{\n" +
                "  \"type\": \"message\",\n" +
                "  \"subtype\": \"message_changed\",\n" +
                "  \"hidden\": true,\n" +
                "  \"message\": {\n" +
                "    \"type\": \"message\",\n" +
                "    \"subtype\": \"tombstone\",\n" +
                "    \"text\": \"This message was deleted.\",\n" +
                "    \"user\": \"USLACKBOT\",\n" +
                "    \"hidden\": true,\n" +
                "    \"thread_ts\": \"1626774922.004400\",\n" +
                "    \"reply_count\": 1,\n" +
                "    \"reply_users_count\": 1,\n" +
                "    \"latest_reply\": \"1626774934.004500\",\n" +
                "    \"reply_users\": [\n" +
                "      \"U111\"\n" +
                "    ],\n" +
                "    \"is_locked\": false,\n" +
                "    \"ts\": \"1626774922.004400\"\n" +
                "  },\n" +
                "  \"channel\": \"C111\",\n" +
                "  \"previous_message\": {\n" +
                "    \"client_msg_id\": \"5a47c3eb-29a5-475f-8946-8677938c39a8\",\n" +
                "    \"type\": \"message\",\n" +
                "    \"text\": \"hi hi!\",\n" +
                "    \"user\": \"U111\",\n" +
                "    \"ts\": \"1626774922.004400\",\n" +
                "    \"team\": \"T111\",\n" +
                "    \"blocks\": [\n" +
                "    ],\n" +
                "    \"thread_ts\": \"1626774922.004400\",\n" +
                "    \"reply_count\": 1,\n" +
                "    \"reply_users_count\": 1,\n" +
                "    \"latest_reply\": \"1626774934.004500\",\n" +
                "    \"reply_users\": [\n" +
                "      \"U111\"\n" +
                "    ],\n" +
                "    \"is_locked\": false,\n" +
                "    \"subscribed\": true,\n" +
                "    \"last_read\": \"1626774934.004500\"\n" +
                "  },\n" +
                "  \"event_ts\": \"1626774940.004700\",\n" +
                "  \"ts\": \"1626774940.004700\",\n" +
                "  \"channel_type\": \"channel\"\n" +
                "}\n";
        MessageChangedEvent event = GsonFactory.createSnakeCase().fromJson(json, MessageChangedEvent.class);
        assertThat(event.getType(), is("message"));
        assertThat(event.getSubtype(), is("message_changed"));
    }

    // https://github.com/slackapi/java-slack-sdk/issues/784
    @Test
    public void issue_784_deleteParentMessageFirstAndThenDeleteAllMessagesInThread() {
        String json = "{\n" +
                "  \"type\": \"message\",\n" +
                "  \"subtype\": \"message_changed\",\n" +
                "  \"hidden\": true,\n" +
                "  \"message\": {\n" +
                "    \"type\": \"message\",\n" +
                "    \"subtype\": \"tombstone\",\n" +
                "    \"text\": \"This message was deleted.\",\n" +
                "    \"user\": \"USLACKBOT\",\n" +
                "    \"hidden\": true,\n" +
                "    \"ts\": \"1626775604.004900\"\n" +
                "  },\n" +
                "  \"channel\": \"C03E94MKU\",\n" +
                "  \"previous_message\": [],\n" +
                "  \"event_ts\": \"1626775673.006300\",\n" +
                "  \"ts\": \"1626775673.006300\",\n" +
                "  \"channel_type\": \"channel\"\n" +
                "}";
        MessageChangedEvent event = GsonFactory.createSnakeCase().fromJson(json, MessageChangedEvent.class);
        assertThat(event.getType(), is("message"));
    }
}
