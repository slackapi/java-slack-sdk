package test_locally.app_backend.interactive_messages.payload;

import com.github.seratch.jslack.app_backend.interactive_messages.payload.BlockActionPayload;
import com.github.seratch.jslack.app_backend.interactive_messages.payload.PayloadTypeDetector;
import com.github.seratch.jslack.common.json.GsonFactory;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PayloadTypeDetectorTest {

    // https://api.slack.com/interactive-messages
    String attachmentActionPayload = "{\n" +
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
    public void interactive_message() throws UnsupportedEncodingException {
        String encoded = URLEncoder.encode(attachmentActionPayload, "UTF-8");
        PayloadTypeDetector detector = new PayloadTypeDetector();
        String type = detector.detectType(URLDecoder.decode(encoded, "UTF-8"));
        assertThat(type, is("interactive_message"));
    }

    // https://api.slack.com/messaging/interactivity/enabling
    String blockActionJson = "{\n" +
            "  \"type\": \"block_actions\",\n" +
            "  \"team\": { \n" +
            "    \"id\": \"T9TK3CUKW\", \n" +
            "    \"domain\": \"example\" \n" +
            "  },\n" +
            "  \"user\": {\n" +
            "    \"id\": \"UA8RXUSPL\",\n" +
            "    \"username\": \"jtorrance\",\n" +
            "    \"team_id\": \"T9TK3CUKW\"\n" +
            "  },\n" +
            "  \"api_app_id\": \"AABA1ABCD\",\n" +
            "  \"token\": \"9s8d9as89d8as9d8as989\",\n" +
            "  \"container\": {\n" +
            "    \"type\": \"message_attachment\",\n" +
            "    \"message_ts\": \"1548261231.000200\",\n" +
            "    \"attachment_id\": 1,\n" +
            "    \"channel_id\": \"CBR2V3XEX\",\n" +
            "    \"is_ephemeral\": false,\n" +
            "    \"is_app_unfurl\": false\n" +
            "  },\n" +
            "  \"trigger_id\": \"12321423423.333649436676.d8c1bb837935619ccad0f624c448ffb3\",\n" +
            "  \"channel\": { \n" +
            "    \"id\": \"CBR2V3XEX\", \n" +
            "    \"name\": \"review-updates\" \n" +
            "  },\n" +
            "  \"message\": {\n" +
            "    \"bot_id\": \"BAH5CA16Z\",\n" +
            "    \"type\": \"message\",\n" +
            "    \"text\": \"This content can't be displayed.\",\n" +
            "    \"user\": \"UAJ2RU415\",\n" +
            "    \"ts\": \"1548261231.000200\"\n" +
            "  },\n" +
            "  \"response_url\": \"https://hooks.slack.com/actions/AABA1ABCD/1232321423432/D09sSasdasdAS9091209\",\n" +
            "  \"actions\": [\n" +
            "    {\n" +
            "      \"action_id\": \"WaXA\",\n" +
            "      \"block_id\": \"=qXel\",\n" +
            "      \"text\": { \n" +
            "        \"type\": \"plain_text\", \n" +
            "        \"text\": \"View\", \n" +
            "        \"emoji\": true \n" +
            "      },\n" +
            "      \"value\": \"click_me_123\",\n" +
            "      \"type\": \"button\",\n" +
            "      \"action_ts\": \"1548426417.840180\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    @Test
    public void block_actions() throws UnsupportedEncodingException {
        String encoded = URLEncoder.encode(blockActionJson, "UTF-8");
        String json = URLDecoder.decode(encoded, "UTF-8");
        BlockActionPayload payload = GsonFactory.createSnakeCase().fromJson(json, BlockActionPayload.class);
        assertThat(payload.getType(), is("block_actions"));
    }
}
