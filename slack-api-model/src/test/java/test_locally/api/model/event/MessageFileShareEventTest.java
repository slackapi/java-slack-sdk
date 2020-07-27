package test_locally.api.model.event;

import com.slack.api.model.event.MessageFileShareEvent;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class MessageFileShareEventTest {

    @Test
    public void typeName() {
        assertThat(MessageFileShareEvent.TYPE_NAME, is("message"));
        assertThat(MessageFileShareEvent.SUBTYPE_NAME, is("file_share"));
    }

    @Test
    public void deserialize_simple() {
        String json = "{\n" +
                "  \"type\": \"message\",\n" +
                "  \"text\": \"This is the text in the message\",\n" +
                "  \"files\": [\n" +
                "    {\n" +
                "      \"id\": \"F111\",\n" +
                "      \"created\": 1595552503,\n" +
                "      \"timestamp\": 1595552503,\n" +
                "      \"name\": \"sample.txt\",\n" +
                "      \"title\": \"title string\",\n" +
                "      \"mimetype\": \"text/plain\",\n" +
                "      \"filetype\": \"text\",\n" +
                "      \"pretty_type\": \"Plain Text\",\n" +
                "      \"user\": \"W111\",\n" +
                "      \"editable\": true,\n" +
                "      \"size\": 4,\n" +
                "      \"mode\": \"snippet\",\n" +
                "      \"is_external\": false,\n" +
                "      \"external_type\": \"\",\n" +
                "      \"is_public\": true,\n" +
                "      \"public_url_shared\": false,\n" +
                "      \"display_as_bot\": false,\n" +
                "      \"username\": \"\",\n" +
                "      \"url_private\": \"https://files.slack.com/files-pri/T11-F111/sample.txt\",\n" +
                "      \"url_private_download\": \"https://files.slack.com/files-pri/T11-F111/download/sample.txt\",\n" +
                "      \"permalink\": \"https://xxx.slack.com/files/W111/F111/sample.txt\",\n" +
                "      \"permalink_public\": \"https://slack-files.com/T11-F111-66a752587a\",\n" +
                "      \"edit_link\": \"https://xxx.slack.com/files/W111/F111/sample.txt/edit\",\n" +
                "      \"preview\": \"test\",\n" +
                "      \"lines\": 1,\n" +
                "      \"lines_more\": 0,\n" +
                "      \"preview_is_truncated\": false,\n" +
                "      \"has_rich_preview\": false\n" +
                "    }\n" +
                "  ],\n" +
                "  \"upload\": true,\n" +
                "  \"user\": \"W111\",\n" +
                "  \"display_as_bot\": false,\n" +
                "  \"ts\": \"1595552503.001300\",\n" +
                "  \"thread_ts\": \"1595552279.000600\",\n" +
                "  \"parent_user_id\": \"W222\",\n" +
                "  \"channel\": \"C111\",\n" +
                "  \"subtype\": \"file_share\",\n" +
                "  \"event_ts\": \"1595552503.001300\",\n" +
                "  \"channel_type\": \"channel\"\n" +
                "}";
        MessageFileShareEvent event = GsonFactory.createSnakeCase().fromJson(json, MessageFileShareEvent.class);
        assertThat(event.getType(), is("message"));
        assertThat(event.getSubtype(), is("file_share"));
    }

}
