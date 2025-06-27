package test_locally.app_backend.interactive_components.payload;

import com.google.gson.Gson;
import com.slack.api.SlackConfig;
import com.slack.api.app_backend.interactive_components.payload.BlockActionPayload;
import com.slack.api.util.json.GsonFactory;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class BlockActionPayloadTest {

    static final SlackConfig TEST_CONFIG = new SlackConfig();
    static {
        TEST_CONFIG.setFailOnUnknownProperties(true);
    }
    static final Gson GSON = GsonFactory.createSnakeCase(TEST_CONFIG);

    // https://docs.slack.dev/messaging/creating-interactive-messages
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
        BlockActionPayload payload = GSON.fromJson(json, BlockActionPayload.class);
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
        BlockActionPayload payload = GSON.fromJson(jsonWithConfirm, BlockActionPayload.class);
        assertThat(payload.getType(), is("block_actions"));
        assertThat(payload.getActions().size(), is(1));

        assertThat(payload.getToken(), is("9s8d9as89d8as9d8as989"));
        assertThat(payload.getUser(), is(notNullValue()));
        assertThat(payload.getTeam(), is(notNullValue()));
        assertThat(payload.getContainer(), is(notNullValue()));
    }

    String jsonWithState = "{\n" +
            "  \"type\": \"block_actions\",\n" +
            "  \"user\": {\n" +
            "    \"id\": \"U111\",\n" +
            "    \"username\": \"seratch\",\n" +
            "    \"name\": \"seratch\",\n" +
            "    \"team_id\": \"T111\"\n" +
            "  },\n" +
            "  \"api_app_id\": \"A111\",\n" +
            "  \"token\": \"xxx\",\n" +
            "  \"container\": {\n" +
            "    \"type\": \"message\",\n" +
            "    \"message_ts\": \"1606455372.001200\",\n" +
            "    \"channel_id\": \"C111\",\n" +
            "    \"is_ephemeral\": true\n" +
            "  },\n" +
            "  \"trigger_id\": \"111.222.xxx\",\n" +
            "  \"team\": {\n" +
            "    \"id\": \"T111\",\n" +
            "    \"domain\": \"xxx\"\n" +
            "  },\n" +
            "  \"channel\": {\n" +
            "    \"id\": \"C111\",\n" +
            "    \"name\": \"random\"\n" +
            "  },\n" +
            "  \"state\": {\n" +
            "    \"values\": {\n" +
            "      \"block_1\": {\n" +
            "        \"action_1\": {\n" +
            "          \"type\": \"external_select\",\n" +
            "          \"selected_option\": {\n" +
            "            \"text\": {\n" +
            "              \"type\": \"plain_text\",\n" +
            "              \"text\": \"Schedule\",\n" +
            "              \"emoji\": true\n" +
            "            },\n" +
            "            \"value\": \"schedule\"\n" +
            "          }\n" +
            "        }\n" +
            "      }\n" +
            "    }\n" +
            "  },\n" +
            "  \"response_url\": \"https://hooks.slack.com/actions/T111/111/xxx\",\n" +
            "  \"actions\": [\n" +
            "    {\n" +
            "      \"action_id\": \"save\",\n" +
            "      \"block_id\": \"block_1\",\n" +
            "      \"text\": {\n" +
            "        \"type\": \"plain_text\",\n" +
            "        \"text\": \"Save\",\n" +
            "        \"emoji\": true\n" +
            "      },\n" +
            "      \"value\": \"1\",\n" +
            "      \"type\": \"button\",\n" +
            "      \"action_ts\": \"1606455407.603639\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    @Test
    public void state_in_messages() {
        BlockActionPayload payload = GSON.fromJson(jsonWithState, BlockActionPayload.class);
        assertThat(payload.getType(), is("block_actions"));
        assertThat(payload.getActions().size(), is(1));

        assertThat(payload.getState().getValues()
                        .get("block_1")
                        .get("action_1")
                        .getSelectedOption().getValue(),
                is("schedule"));
    }

    @Test
    public void deserialize_app_unfurl() {
        String jsonData = "{\n" +
                "  \"type\": \"block_actions\",\n" +
                "  \"user\": {\n" +
                "    \"id\": \"U111\",\n" +
                "    \"username\": \"seratch\",\n" +
                "    \"name\": \"seratch\",\n" +
                "    \"team_id\": \"T111\"\n" +
                "  },\n" +
                "  \"api_app_id\": \"A111\",\n" +
                "  \"token\": \"xxx\",\n" +
                "  \"container\": {\n" +
                "    \"type\": \"message_attachment\",\n" +
                "    \"message_ts\": \"1661488735.191299\",\n" +
                "    \"attachment_id\": 1,\n" +
                "    \"channel_id\": \"C111\",\n" +
                "    \"is_ephemeral\": false,\n" +
                "    \"is_app_unfurl\": true,\n" +
                "    \"app_unfurl_url\": \"https://example.com/foo\"\n" +
                "  },\n" +
                "  \"trigger_id\": \"111.222.xxx\",\n" +
                "  \"team\": {\n" +
                "    \"id\": \"T111\",\n" +
                "    \"domain\": \"seratch\"\n" +
                "  },\n" +
                "  \"is_enterprise_install\": false,\n" +
                "  \"channel\": {\n" +
                "    \"id\": \"C111\",\n" +
                "    \"name\": \"general\"\n" +
                "  },\n" +
                "  \"app_unfurl\": {\n" +
                "    \"id\": 1,\n" +
                "    \"blocks\": [\n" +
                "      {\n" +
                "        \"type\": \"actions\",\n" +
                "        \"block_id\": \"b\",\n" +
                "        \"elements\": [\n" +
                "          {\n" +
                "            \"type\": \"button\",\n" +
                "            \"action_id\": \"a\",\n" +
                "            \"text\": {\n" +
                "              \"type\": \"plain_text\",\n" +
                "              \"text\": \"Click this!\",\n" +
                "              \"emoji\": true\n" +
                "            },\n" +
                "            \"value\": \"clicked\"\n" +
                "          }\n" +
                "        ]\n" +
                "      }\n" +
                "    ],\n" +
                "    \"fallback\": \"[no preview available]\",\n" +
                "    \"bot_id\": \"B01K9P888EL\",\n" +
                "    \"app_unfurl_url\": \"https://example.com/foo\",\n" +
                "    \"is_app_unfurl\": true,\n" +
                "    \"app_id\": \"A111\"\n" +
                "  },\n" +
                "  \"state\": {\n" +
                "    \"values\": {}\n" +
                "  },\n" +
                "  \"response_url\": \"https://hooks.slack.com/actions/T111/111/222\",\n" +
                "  \"actions\": [\n" +
                "    {\n" +
                "      \"action_id\": \"a\",\n" +
                "      \"block_id\": \"b\",\n" +
                "      \"text\": {\n" +
                "        \"type\": \"plain_text\",\n" +
                "        \"text\": \"Click this!\",\n" +
                "        \"emoji\": true\n" +
                "      },\n" +
                "      \"value\": \"clicked\",\n" +
                "      \"type\": \"button\",\n" +
                "      \"action_ts\": \"1661488741.407859\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        BlockActionPayload payload = GSON.fromJson(jsonData, BlockActionPayload.class);
        assertThat(payload.getType(), is("block_actions"));
        assertThat(payload.getActions().size(), is(1));
    }



    String jsonInThread = "{\n" +
            "  \"type\": \"block_actions\",\n" +
            "  \"user\": {\n" +
            "    \"id\": \"U111\",\n" +
            "    \"username\": \"seratch\",\n" +
            "    \"name\": \"seratch\",\n" +
            "    \"team_id\": \"T111\"\n" +
            "  },\n" +
            "  \"api_app_id\": \"A111\",\n" +
            "  \"token\": \"xxx\",\n" +
            "  \"container\": {\n" +
            "    \"type\": \"message\",\n" +
            "    \"message_ts\": \"1693635296.000500\",\n" +
            "    \"thread_ts\": \"1693616242.466609\",\n" +
            "    \"channel_id\": \"C111\",\n" +
            "    \"is_ephemeral\": true\n" +
            "  },\n" +
            "  \"trigger_id\": \"111.222.xxx\",\n" +
            "  \"team\": {\n" +
            "    \"id\": \"T111\",\n" +
            "    \"domain\": \"xxx\"\n" +
            "  },\n" +
            "  \"channel\": {\n" +
            "    \"id\": \"C111\",\n" +
            "    \"name\": \"random\"\n" +
            "  },\n" +
            "  \"state\": {\n" +
            "    \"values\": {\n" +
            "      \"block_1\": {\n" +
            "        \"action_1\": {\n" +
            "          \"type\": \"external_select\",\n" +
            "          \"selected_option\": {\n" +
            "            \"text\": {\n" +
            "              \"type\": \"plain_text\",\n" +
            "              \"text\": \"Schedule\",\n" +
            "              \"emoji\": true\n" +
            "            },\n" +
            "            \"value\": \"schedule\"\n" +
            "          }\n" +
            "        }\n" +
            "      }\n" +
            "    }\n" +
            "  },\n" +
            "  \"response_url\": \"https://hooks.slack.com/actions/T111/111/xxx\",\n" +
            "  \"actions\": [\n" +
            "    {\n" +
            "      \"action_id\": \"save\",\n" +
            "      \"block_id\": \"block_1\",\n" +
            "      \"text\": {\n" +
            "        \"type\": \"plain_text\",\n" +
            "        \"text\": \"Save\",\n" +
            "        \"emoji\": true\n" +
            "      },\n" +
            "      \"value\": \"1\",\n" +
            "      \"type\": \"button\",\n" +
            "      \"action_ts\": \"1606455407.603639\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    @Test
    public void threaded_message() {
        BlockActionPayload payload = GSON.fromJson(jsonInThread, BlockActionPayload.class);
        assertThat(payload.getType(), is("block_actions"));
        assertThat(payload.getActions().size(), is(1));
        assertThat(payload.getContainer().getMessageTs(), is("1693635296.000500"));
        assertThat(payload.getContainer().getThreadTs(), is("1693616242.466609"));

        assertThat(payload.getState().getValues()
                        .get("block_1")
                        .get("action_1")
                        .getSelectedOption().getValue(),
                is("schedule"));
    }

    String jsonInteractionsFromRemoteFunction = "{\n" +
            "  \"type\": \"block_actions\",\n" +
            "  \"team\": {\n" +
            "    \"id\": \"T03E94MJU\",\n" +
            "    \"domain\": \"test\"\n" +
            "  },\n" +
            "  \"user\": {\n" +
            "    \"id\": \"U03E94MK0\",\n" +
            "    \"name\": \"kaz\",\n" +
            "    \"team_id\": \"T03E94MJU\"\n" +
            "  },\n" +
            "  \"channel\": {\n" +
            "    \"id\": \"D065ZJQQQAE\",\n" +
            "    \"name\": \"directmessage\"\n" +
            "  },\n" +
            "  \"message\": {\n" +
            "    \"bot_id\": \"B065SV9Q70W\",\n" +
            "    \"type\": \"message\",\n" +
            "    \"text\": \"hey!\",\n" +
            "    \"user\": \"U066C7XNE6M\",\n" +
            "    \"ts\": \"1700455285.968429\",\n" +
            "    \"app_id\": \"A065ZJM410S\",\n" +
            "    \"blocks\": [\n" +
            "      {\n" +
            "        \"type\": \"actions\",\n" +
            "        \"block_id\": \"b\",\n" +
            "        \"elements\": [\n" +
            "          {\n" +
            "            \"type\": \"button\",\n" +
            "            \"action_id\": \"a\",\n" +
            "            \"text\": {\n" +
            "              \"type\": \"plain_text\",\n" +
            "              \"text\": \"Click this!\",\n" +
            "              \"emoji\": true\n" +
            "            },\n" +
            "            \"value\": \"clicked\"\n" +
            "          }\n" +
            "        ]\n" +
            "      }\n" +
            "    ],\n" +
            "    \"team\": \"T03E94MJU\"\n" +
            "  },\n" +
            "  \"container\": {\n" +
            "    \"type\": \"message\",\n" +
            "    \"message_ts\": \"1700455285.968429\",\n" +
            "    \"channel_id\": \"D065ZJQQQAE\",\n" +
            "    \"is_ephemeral\": false\n" +
            "  },\n" +
            "  \"actions\": [\n" +
            "    {\n" +
            "      \"block_id\": \"b\",\n" +
            "      \"action_id\": \"a\",\n" +
            "      \"type\": \"button\",\n" +
            "      \"text\": {\n" +
            "        \"type\": \"plain_text\",\n" +
            "        \"text\": \"Click this!\",\n" +
            "        \"emoji\": true\n" +
            "      },\n" +
            "      \"value\": \"clicked\",\n" +
            "      \"action_ts\": \"1700455293.945608\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"api_app_id\": \"A065ZJM410S\",\n" +
            "  \"state\": {\n" +
            "    \"values\": {}\n" +
            "  },\n" +
            "  \"bot_access_token\": \"xwfp-valid\",\n" +
            "  \"function_data\": {\n" +
            "    \"execution_id\": \"Fx066J3N9ME0\",\n" +
            "    \"function\": {\n" +
            "      \"callback_id\": \"hello\"\n" +
            "    },\n" +
            "    \"inputs\": {\n" +
            "      \"amount\": 1,\n" +
            "      \"message\": \"hey\",\n" +
            "      \"user_id\": \"U03E94MK0\"\n" +
            "    }\n" +
            "  },\n" +
            "  \"interactivity\": {\n" +
            "    \"interactor\": {\n" +
            "      \"secret\": \"interactor-secret\",\n" +
            "      \"id\": \"U03E94MK0\"\n" +
            "    },\n" +
            "    \"interactivity_pointer\": \"111.222.333\"\n" +
            "  }\n" +
            "}\n";

    @Test
    public void interactionsFromRemoteFunction() {
        BlockActionPayload payload = GSON.fromJson(jsonInteractionsFromRemoteFunction, BlockActionPayload.class);
        assertThat(payload.getType(), is("block_actions"));
        assertThat(payload.getActions().size(), is(1));
    }

}
