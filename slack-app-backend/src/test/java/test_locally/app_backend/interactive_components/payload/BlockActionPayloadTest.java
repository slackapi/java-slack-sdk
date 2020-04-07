package test_locally.app_backend.interactive_components.payload;

import com.slack.api.app_backend.interactive_components.payload.BlockActionPayload;
import com.slack.api.util.json.GsonFactory;
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

    String jsonWithConfirm = "{\n" +
            "  \"type\": \"block_actions\",\n" +
            "  \"team\": {\n" +
            "    \"id\": \"T12345678\",\n" +
            "    \"domain\": \"seratch\"\n" +
            "  },\n" +
            "  \"user\": {\n" +
            "    \"id\": \"U12345678\",\n" +
            "    \"username\": \"seratch\",\n" +
            "    \"name\": \"seratch\",\n" +
            "    \"team_id\": \"T12345678\"\n" +
            "  },\n" +
            "  \"api_app_id\": \"A12345678\",\n" +
            "  \"token\": \"9s8d9as89d8as9d8as989\",\n" +
            "  \"container\": {\n" +
            "    \"type\": \"view\",\n" +
            "    \"view_id\": \"V12345678\"\n" +
            "  },\n" +
            "  \"trigger_id\": \"123123.123123.abcabc\",\n" +
            "  \"view\": {\n" +
            "    \"id\": \"V12345678\",\n" +
            "    \"team_id\": \"T12345678\",\n" +
            "    \"type\": \"home\",\n" +
            "    \"blocks\": [],\n" +
            "    \"private_metadata\": \"\",\n" +
            "    \"callback_id\": \"\",\n" +
            "    \"state\": {\n" +
            "      \"values\": {}\n" +
            "    },\n" +
            "    \"hash\": \"1574381430.230cf862\",\n" +
            "    \"title\": {\n" +
            "      \"type\": \"plain_text\",\n" +
            "      \"text\": \"View Title\",\n" +
            "      \"emoji\": true\n" +
            "    },\n" +
            "    \"clear_on_close\": false,\n" +
            "    \"notify_on_close\": false,\n" +
            "    \"close\": null,\n" +
            "    \"submit\": null,\n" +
            "    \"previous_view_id\": null,\n" +
            "    \"root_view_id\": \"V12345678\",\n" +
            "    \"app_id\": \"A12345678\",\n" +
            "    \"external_id\": \"\",\n" +
            "    \"app_installed_team_id\": \"T12345678\",\n" +
            "    \"bot_id\": \"BQQ8V0V6H\"\n" +
            "  },\n" +
            "  \"actions\": [\n" +
            "    {\n" +
            "      \"confirm\": {\n" +
            "        \"title\": {\n" +
            "          \"type\": \"plain_text\",\n" +
            "          \"text\": \"Clear Settings\",\n" +
            "          \"emoji\": true\n" +
            "        },\n" +
            "        \"text\": {\n" +
            "          \"type\": \"plain_text\",\n" +
            "          \"text\": \"Are you sure?\",\n" +
            "          \"emoji\": true\n" +
            "        }\n" +
            "      },\n" +
            "      \"action_id\": \"clear-settings-button\",\n" +
            "      \"block_id\": \"lMr\",\n" +
            "      \"text\": {\n" +
            "        \"type\": \"plain_text\",\n" +
            "        \"text\": \"Clear Settings\",\n" +
            "        \"emoji\": true\n" +
            "      },\n" +
            "      \"style\": \"danger\",\n" +
            "      \"type\": \"button\",\n" +
            "      \"action_ts\": \"1574383474.049185\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    @Test
    public void deserialize_confirm() {
        BlockActionPayload payload = GsonFactory.createSnakeCase().fromJson(jsonWithConfirm, BlockActionPayload.class);
        assertThat(payload.getType(), is("block_actions"));
        assertThat(payload.getActions().size(), is(1));

        assertThat(payload.getToken(), is("9s8d9as89d8as9d8as989"));
        assertThat(payload.getUser(), is(notNullValue()));
        assertThat(payload.getTeam(), is(notNullValue()));
        assertThat(payload.getContainer(), is(notNullValue()));
    }

}
