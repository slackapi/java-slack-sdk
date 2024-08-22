package test_locally.jakarta_socket_mode;

import com.google.gson.Gson;
import com.slack.api.socket_mode.request.EventsApiEnvelope;
import com.slack.api.socket_mode.request.HelloMessage;
import com.slack.api.socket_mode.request.InteractiveEnvelope;
import com.slack.api.socket_mode.request.SlashCommandsEnvelope;
import com.slack.api.util.json.GsonFactory;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

public class EnvelopeTest {

    static final Gson GSON = GsonFactory.createSnakeCase();

    @Test
    public void hello() {
        String message = "{\n" +
                "  \"type\": \"hello\",\n" +
                "  \"num_connections\": 1,\n" +
                "  \"debug_info\": {\n" +
                "    \"host\": \"applink-xxx-yyy\",\n" +
                "    \"build_number\": 999,\n" +
                "    \"approximate_connection_time\": 18060\n" +
                "  },\n" +
                "  \"connection_info\": {\n" +
                "    \"app_id\": \"A111\"\n" +
                "  }\n" +
                "}\n";
        HelloMessage hello = GSON.fromJson(message, HelloMessage.class);
        assertThat(hello.getType(), is("hello"));
        assertThat(hello.getDebugInfo().getApproximateConnectionTime(), is(18060));
    }

    @Test
    public void interactive() {
        String message = "{\n" +
                "  \"envelope_id\": \"xxx-11-222-yyy-zzz\",\n" +
                "  \"payload\": {\n" +
                "    \"type\": \"block_actions\",\n" +
                "    \"user\": {\n" +
                "      \"id\": \"U111\",\n" +
                "      \"username\": \"test-test-test\",\n" +
                "      \"name\": \"test-test-test\",\n" +
                "      \"team_id\": \"T111\"\n" +
                "    },\n" +
                "    \"api_app_id\": \"A111\",\n" +
                "    \"token\": \"fixed-value\",\n" +
                "    \"container\": {\n" +
                "      \"type\": \"message\",\n" +
                "      \"message_ts\": \"1605853634.000400\",\n" +
                "      \"channel_id\": \"C111\",\n" +
                "      \"is_ephemeral\": false\n" +
                "    },\n" +
                "    \"trigger_id\": \"111.222.xxx\",\n" +
                "    \"team\": {\n" +
                "      \"id\": \"T111\",\n" +
                "      \"domain\": \"test-test-test\"\n" +
                "    },\n" +
                "    \"channel\": {\n" +
                "      \"id\": \"C111\",\n" +
                "      \"name\": \"random\"\n" +
                "    },\n" +
                "    \"message\": {\n" +
                "      \"bot_id\": \"B111\",\n" +
                "      \"type\": \"message\",\n" +
                "      \"text\": \"This content can't be displayed.\",\n" +
                "      \"user\": \"U222\",\n" +
                "      \"ts\": \"1605853634.000400\",\n" +
                "      \"team\": \"T111\",\n" +
                "      \"blocks\": [\n" +
                "        {\n" +
                "          \"type\": \"actions\",\n" +
                "          \"block_id\": \"b\",\n" +
                "          \"elements\": [\n" +
                "            {\n" +
                "              \"type\": \"button\",\n" +
                "              \"action_id\": \"a\",\n" +
                "              \"text\": {\n" +
                "                \"type\": \"plain_text\",\n" +
                "                \"text\": \"Click Me!\",\n" +
                "                \"emoji\": true\n" +
                "              },\n" +
                "              \"value\": \"underlying\"\n" +
                "            }\n" +
                "          ]\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    \"response_url\": \"https://hooks.slack.com/actions/T111/111/xxx\",\n" +
                "    \"actions\": [\n" +
                "      {\n" +
                "        \"action_id\": \"a\",\n" +
                "        \"block_id\": \"b\",\n" +
                "        \"text\": {\n" +
                "          \"type\": \"plain_text\",\n" +
                "          \"text\": \"Click Me!\",\n" +
                "          \"emoji\": true\n" +
                "        },\n" +
                "        \"value\": \"underlying\",\n" +
                "        \"type\": \"button\",\n" +
                "        \"action_ts\": \"1605853645.582706\"\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  \"type\": \"interactive\",\n" +
                "  \"accepts_response_payload\": false\n" +
                "}\n";
        InteractiveEnvelope envelope = GSON.fromJson(message, InteractiveEnvelope.class);
        assertThat(envelope.getType(), is("interactive"));
        assertNotNull(envelope.getPayload().getAsJsonObject().get("user"));
    }

    @Test
    public void events_api() {
        String message = "{\n" +
                "  \"envelope_id\": \"xxx-11-222-yyy-zzz\",\n" +
                "  \"payload\": {\n" +
                "    \"token\": \"fixed-value\",\n" +
                "    \"team_id\": \"T111\",\n" +
                "    \"api_app_id\": \"A111\",\n" +
                "    \"event\": {\n" +
                "      \"client_msg_id\": \"1748313e-912c-4942-a562-99754707692c\",\n" +
                "      \"type\": \"app_mention\",\n" +
                "      \"text\": \"<@U222> hey\",\n" +
                "      \"user\": \"U111\",\n" +
                "      \"ts\": \"1605853844.000800\",\n" +
                "      \"team\": \"T111\",\n" +
                "      \"blocks\": [\n" +
                "        {\n" +
                "          \"type\": \"rich_text\",\n" +
                "          \"block_id\": \"K8xp\",\n" +
                "          \"elements\": [\n" +
                "            {\n" +
                "              \"type\": \"rich_text_section\",\n" +
                "              \"elements\": [\n" +
                "                {\n" +
                "                  \"type\": \"user\",\n" +
                "                  \"user_id\": \"U222\"\n" +
                "                },\n" +
                "                {\n" +
                "                  \"type\": \"text\",\n" +
                "                  \"text\": \" hey\"\n" +
                "                }\n" +
                "              ]\n" +
                "            }\n" +
                "          ]\n" +
                "        }\n" +
                "      ],\n" +
                "      \"channel\": \"C111\",\n" +
                "      \"event_ts\": \"1605853844.000800\"\n" +
                "    },\n" +
                "    \"type\": \"event_callback\",\n" +
                "    \"event_id\": \"Ev01ERKCFKK9\",\n" +
                "    \"event_time\": 1605853844,\n" +
                "    \"authorizations\": [\n" +
                "      {\n" +
                "        \"enterprise_id\": null,\n" +
                "        \"team_id\": \"T111\",\n" +
                "        \"user_id\": \"U222\",\n" +
                "        \"is_bot\": true,\n" +
                "        \"is_enterprise_install\": false\n" +
                "      }\n" +
                "    ],\n" +
                "    \"is_ext_shared_channel\": false,\n" +
                "    \"event_context\": \"1-app_mention-T111-C111\"\n" +
                "  },\n" +
                "  \"type\": \"events_api\",\n" +
                "  \"accepts_response_payload\": false,\n" +
                "  \"retry_attempt\": 0,\n" +
                "  \"retry_reason\": \"\"\n" +
                "}\n";
        EventsApiEnvelope envelope = GSON.fromJson(message, EventsApiEnvelope.class);
        assertThat(envelope.getType(), is("events_api"));
        assertNotNull(envelope.getPayload().getAsJsonObject().get("event"));
    }

    @Test
    public void slash_commands() {
        String message = "{\n" +
                "  \"envelope_id\": \"xxx-11-222-yyy-zzz\",\n" +
                "  \"payload\": {\n" +
                "    \"token\": \"fixed-value\",\n" +
                "    \"team_id\": \"T111\",\n" +
                "    \"team_domain\": \"test-test-test\",\n" +
                "    \"channel_id\": \"C111\",\n" +
                "    \"channel_name\": \"random\",\n" +
                "    \"user_id\": \"U111\",\n" +
                "    \"user_name\": \"test-test-test\",\n" +
                "    \"command\": \"/hi-socket-mode\",\n" +
                "    \"text\": \"\",\n" +
                "    \"api_app_id\": \"A111\",\n" +
                "    \"response_url\": \"https://hooks.slack.com/commands/T111/111/xxx\",\n" +
                "    \"trigger_id\": \"111.222.xxx\"\n" +
                "  },\n" +
                "  \"type\": \"slash_commands\",\n" +
                "  \"accepts_response_payload\": true\n" +
                "}\n";
        SlashCommandsEnvelope envelope = GSON.fromJson(message, SlashCommandsEnvelope.class);
        assertThat(envelope.getType(), is("slash_commands"));
        assertThat(envelope.getPayload().getAsJsonObject().get("team_id").getAsString(), is("T111"));
    }
}
