package test_locally.app_backend.views.payload;

import com.google.gson.Gson;
import com.slack.api.app_backend.views.payload.ViewClosedPayload;
import com.slack.api.util.json.GsonFactory;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ViewClosedPayloadTest {

    private Gson gson = GsonFactory.createSnakeCase();

    String json = "{\n" +
            "  \"type\": \"view_closed\",\n" +
            "  \"team\": {\n" +
            "    \"id\": \"T12345678\",\n" +
            "    \"domain\": \"domain\"\n" +
            "  },\n" +
            "  \"user\": {\n" +
            "    \"id\": \"U12345678\",\n" +
            "    \"username\": \"username\",\n" +
            "    \"name\": \"username\",\n" +
            "    \"team_id\": \"T12345678\"\n" +
            "  },\n" +
            "  \"api_app_id\": \"A12345678\",\n" +
            "  \"token\": \"random value\",\n" +
            "  \"view\": {\n" +
            "    \"id\": \"V12345678\",\n" +
            "    \"team_id\": \"T12345678\",\n" +
            "    \"type\": \"modal\",\n" +
            "    \"title\": {\n" +
            "      \"type\": \"plain_text\",\n" +
            "      \"text\": \"Title\",\n" +
            "      \"emoji\": true\n" +
            "    },\n" +
            "    \"close\": null,\n" +
            "    \"submit\": {\n" +
            "      \"type\": \"plain_text\",\n" +
            "      \"text\": \"Submit\",\n" +
            "      \"emoji\": true\n" +
            "    },\n" +
            "    \"blocks\": [\n" +
            "    ],\n" +
            "    \"private_metadata\": \"\",\n" +
            "    \"callback_id\": \"modal-callback-id\",\n" +
            "    \"state\": {\n" +
            "      \"values\": {\n" +
            "      }\n" +
            "    },\n" +
            "    \"hash\": \"random\",\n" +
            "    \"clear_on_close\": false,\n" +
            "    \"notify_on_close\": true,\n" +
            "    \"root_view_id\": \"V12345678\",\n" +
            "    \"previous_view_id\": null,\n" +
            "    \"app_id\": \"A12345678\",\n" +
            "    \"external_id\": \"\",\n" +
            "    \"bot_id\": \"B12345678\"\n" +
            "  },\n" +
            "  \"is_cleared\": false\n" +
            "}";

    @Test
    public void test() {
        ViewClosedPayload payload = gson.fromJson(json, ViewClosedPayload.class);
        assertThat(payload.getToken(), is("random value"));
    }
}
