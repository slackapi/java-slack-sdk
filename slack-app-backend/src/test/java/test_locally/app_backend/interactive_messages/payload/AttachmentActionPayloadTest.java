package test_locally.app_backend.interactive_messages.payload;

import com.github.seratch.jslack.app_backend.interactive_messages.payload.AttachmentActionPayload;
import com.slack.api.util.json.GsonFactory;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class AttachmentActionPayloadTest {

    // https://api.slack.com/interactive-messages
    String json = "{\n" +
            "  \"type\": \"interactive_message\",\n" +
            "  \"actions\": [\n" +
            "    {\n" +
            "      \"name\": \"channel_list\",\n" +
            "      \"type\": \"select\",\n" +
            "      \"selected_options\":[\n" +
            "        {\n" +
            "          \"value\": \"C24BTKDQW\"\n" +
            "        }\n" +
            "      ]\n" +
            "    }\n" +
            "  ],\n" +
            "  \"callback_id\": \"pick_channel_for_fun\",\n" +
            "  \"team\": {\n" +
            "    \"id\": \"T1ABCD2E12\",\n" +
            "    \"domain\": \"hooli-hq\"\n" +
            "  },\n" +
            "  \"channel\": {\n" +
            "    \"id\": \"C9C2VHR7D\",\n" +
            "    \"name\": \"triage-random\"\n" +
            "  },\n" +
            "  \"user\": {\n" +
            "    \"id\": \"U900MV5U7\",\n" +
            "    \"name\": \"gbelson\"\n" +
            "  },\n" +
            "  \"action_ts\": \"1520966872.245369\",\n" +
            "  \"message_ts\": \"1520965348.000538\",\n" +
            "  \"attachment_id\": \"1\",\n" +
            "  \"token\": \"lbAZE0ckwoSNJcsGWE7sqX5j\",\n" +
            "  \"is_app_unfurl\": false,\n" +
            "  \"original_message\": {\n" +
            "    \"text\": \"\",\n" +
            "    \"username\": \"Belson Bot\",\n" +
            "    \"bot_id\": \"B9DKHFZ1E\",\n" +
            "    \"attachments\":[\n" +
            "      {\n" +
            "        \"callback_id\": \"pick_channel_for_fun\",\n" +
            "        \"text\": \"Choose a channel\",\n" +
            "        \"id\": 1,\n" +
            "        \"color\": \"2b72cb\",\n" +
            "        \"actions\": [\n" +
            "          {\n" +
            "            \"id\": \"1\",\n" +
            "            \"name\": \"channel_list\",\n" +
            "            \"text\": \"Public channels\",\n" +
            "            \"type\": \"select\",\n" +
            "            \"data_source\": \"channels\"\n" +
            "          }\n" +
            "        ],\n" +
            "        \"fallback\":\"Choose a channel\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"type\": \"message\",\n" +
            "    \"subtype\": \"bot_message\",\n" +
            "    \"ts\": \"1520965348.000538\"\n" +
            "  },\n" +
            "  \"response_url\": \"https://hooks.slack.com/actions/T1ABCD2E12/330361579271/0dAEyLY19ofpLwxqozy3firz\",\n" +
            "  \"trigger_id\": \"328654886736.72393107734.9a0f78bccc3c64093f4b12fe82ccd51e\"\n" +
            "}";

    @Test
    public void deserialize() {
        AttachmentActionPayload payload = GsonFactory.createSnakeCase().fromJson(json, AttachmentActionPayload.class);
        assertThat(payload.getType(), is("interactive_message"));
        assertThat(payload.getOriginalMessage(), is(notNullValue()));
        assertThat(payload.getResponseUrl(), is("https://hooks.slack.com/actions/T1ABCD2E12/330361579271/0dAEyLY19ofpLwxqozy3firz"));
    }
}
