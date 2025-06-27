package test_locally.app_backend.interactive_components.payload;

import com.slack.api.app_backend.interactive_components.payload.BlockSuggestionPayload;
import com.slack.api.util.json.GsonFactory;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class BlockSuggestionPayloadTest {

    // https://docs.slack.dev/messaging/creating-interactive-messages
    String json = "{\n" +
            "  \"type\": \"block_suggestion\",\n" +
            "  \"user\": {\n" +
            "    \"id\": \"U123\",\n" +
            "    \"username\": \"seratch\",\n" +
            "    \"name\": \"seratch\",\n" +
            "    \"team_id\": \"T123\"\n" +
            "  },\n" +
            "  \"container\": {\n" +
            "    \"type\": \"view\",\n" +
            "    \"view_id\": \"V123\"\n" +
            "  },\n" +
            "  \"api_app_id\": \"A123\",\n" +
            "  \"token\": \"legacy-fixed-value\",\n" +
            "  \"action_id\": \"a\",\n" +
            "  \"block_id\": \"b\",\n" +
            "  \"value\": \"This is a test\",\n" +
            "  \"team\": {\n" +
            "    \"id\": \"T123\",\n" +
            "    \"domain\": \"seratch\"\n" +
            "  },\n" +
            "  \"view\": {\n" +
            "    \"id\": \"V123\",\n" +
            "    \"team_id\": \"T123\",\n" +
            "    \"type\": \"modal\",\n" +
            "    \"blocks\": [\n" +
            "      {\n" +
            "        \"type\": \"input\",\n" +
            "        \"block_id\": \"b\",\n" +
            "        \"label\": {\n" +
            "          \"type\": \"plain_text\",\n" +
            "          \"text\": \"Label\",\n" +
            "          \"emoji\": true\n" +
            "        },\n" +
            "        \"optional\": false,\n" +
            "        \"element\": {\n" +
            "          \"type\": \"external_select\",\n" +
            "          \"action_id\": \"a\"\n" +
            "        }\n" +
            "      }\n" +
            "    ],\n" +
            "    \"private_metadata\": \"This is a secret\",\n" +
            "    \"callback_id\": \"\",\n" +
            "    \"state\": {\n" +
            "      \"values\": {}\n" +
            "    },\n" +
            "    \"hash\": \"123.random\",\n" +
            "    \"title\": {\n" +
            "      \"type\": \"plain_text\",\n" +
            "      \"text\": \"My App\",\n" +
            "      \"emoji\": true\n" +
            "    },\n" +
            "    \"clear_on_close\": false,\n" +
            "    \"notify_on_close\": false,\n" +
            "    \"close\": {\n" +
            "      \"type\": \"plain_text\",\n" +
            "      \"text\": \"Cancel\",\n" +
            "      \"emoji\": true\n" +
            "    },\n" +
            "    \"submit\": {\n" +
            "      \"type\": \"plain_text\",\n" +
            "      \"text\": \"Submit\",\n" +
            "      \"emoji\": true\n" +
            "    },\n" +
            "    \"previous_view_id\": null,\n" +
            "    \"root_view_id\": \"V123\",\n" +
            "    \"app_id\": \"A123\",\n" +
            "    \"external_id\": \"\",\n" +
            "    \"app_installed_team_id\": \"T123\",\n" +
            "    \"bot_id\": \"B123\"\n" +
            "  }\n" +
            "}";

    @Test
    public void deserialize() {
        BlockSuggestionPayload payload = GsonFactory.createSnakeCase().fromJson(json, BlockSuggestionPayload.class);
        assertThat(payload.getType(), is("block_suggestion"));
        assertThat(payload.getUser(), is(notNullValue()));
        assertThat(payload.getTeam(), is(notNullValue()));
        assertThat(payload.getContainer(), is(notNullValue()));
        assertThat(payload.getChannel(), is(nullValue()));

        assertThat(payload.getView(), is(notNullValue()));
        assertThat(payload.getView().getPrivateMetadata(), is("This is a secret"));
    }

}
