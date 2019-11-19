package test_locally.app_backend.interactive_messages.payload;

import com.github.seratch.jslack.app_backend.interactive_messages.payload.BlockActionPayload;
import com.github.seratch.jslack.common.json.GsonFactory;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class BlockActionPayloadTest {

    // https://api.slack.com/messaging/interactivity/enabling
    String json = "{\n" +
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
    public void deserialize() {
        BlockActionPayload payload = GsonFactory.createSnakeCase().fromJson(json, BlockActionPayload.class);
        assertThat(payload.getType(), is("block_actions"));
        assertThat(payload.getToken(), is("9s8d9as89d8as9d8as989"));
        assertThat(payload.getUser(), is(notNullValue()));
        assertThat(payload.getTeam(), is(notNullValue()));
        assertThat(payload.getContainer(), is(notNullValue()));
        assertThat(payload.getChannel(), is(notNullValue()));
        assertThat(payload.getResponseUrl(), is("https://hooks.slack.com/actions/AABA1ABCD/1232321423432/D09sSasdasdAS9091209"));
        assertThat(payload.getActions().size(), is(1));
    }
}
