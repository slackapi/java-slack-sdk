package test_locally.request;

import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.request.builtin.ViewSubmissionRequest;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ViewSubmissionRequestTest {

    String nullPayload = "{\n" +
            "  \"type\": \"view_submission\",\n" +
            "  \"team\": {\n" +
            "    \"id\": \"T123\",\n" +
            "    \"domain\": \"seratch\"\n" +
            "  },\n" +
            "  \"user\": {\n" +
            "    \"id\": \"U03E94MK0\",\n" +
            "    \"username\": \"seratch\",\n" +
            "    \"name\": \"seratch\",\n" +
            "    \"team_id\": \"T123\"\n" +
            "  },\n" +
            "  \"api_app_id\": \"A123\",\n" +
            "  \"token\": \"bpzxahAmfJQf5gdBvm05x64O\",\n" +
            "  \"trigger_id\": \"123.123.random\",\n" +
            "  \"view\": {\n" +
            "    \"id\": \"V123\",\n" +
            "    \"team_id\": \"T123\",\n" +
            "    \"type\": \"modal\",\n" +
            "    \"blocks\": [],\n" +
            "    \"private_metadata\": \"\",\n" +
            "    \"callback_id\": \"view-callback-id\",\n" +
            "    \"state\": {\n" +
            "      \"values\": {}\n" +
            "    },\n" +
            "    \"hash\": \"123.123\",\n" +
            "    \"title\": {\n" +
            "      \"type\": \"plain_text\",\n" +
            "      \"text\": \"My App\",\n" +
            "      \"emoji\": true\n" +
            "    },\n" +
            "    \"clear_on_close\": false,\n" +
            "    \"notify_on_close\": true,\n" +
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
            "  },\n" +
            "  \"response_urls\": []\n" +
            "}";

    @Test
    public void responseUrl_null() throws UnsupportedEncodingException {
        RequestHeaders headers = new RequestHeaders(Collections.emptyMap());
        ViewSubmissionRequest req = new ViewSubmissionRequest("payload=" + URLEncoder.encode(nullPayload, "UTF-8"), nullPayload, headers);
        assertNull(req.getResponseUrl());
    }

    String payload = "{\n" +
            "  \"type\": \"view_submission\",\n" +
            "  \"team\": {\n" +
            "    \"id\": \"T123\",\n" +
            "    \"domain\": \"seratch\"\n" +
            "  },\n" +
            "  \"user\": {\n" +
            "    \"id\": \"U03E94MK0\",\n" +
            "    \"username\": \"seratch\",\n" +
            "    \"name\": \"seratch\",\n" +
            "    \"team_id\": \"T123\"\n" +
            "  },\n" +
            "  \"api_app_id\": \"A123\",\n" +
            "  \"token\": \"bpzxahAmfJQf5gdBvm05x64O\",\n" +
            "  \"trigger_id\": \"123.123.random\",\n" +
            "  \"view\": {\n" +
            "    \"id\": \"V123\",\n" +
            "    \"team_id\": \"T123\",\n" +
            "    \"type\": \"modal\",\n" +
            "    \"blocks\": [\n" +
            "      {\n" +
            "        \"type\": \"input\",\n" +
            "        \"block_id\": \"Cuw\",\n" +
            "        \"label\": {\n" +
            "          \"type\": \"plain_text\",\n" +
            "          \"text\": \"Label\",\n" +
            "          \"emoji\": true\n" +
            "        },\n" +
            "        \"optional\": false,\n" +
            "        \"element\": {\n" +
            "          \"type\": \"channels_select\",\n" +
            "          \"response_url_enabled\": true,\n" +
            "          \"placeholder\": {\n" +
            "            \"type\": \"plain_text\",\n" +
            "            \"text\": \"Select a channel\",\n" +
            "            \"emoji\": true\n" +
            "          },\n" +
            "          \"action_id\": \"0HiH\"\n" +
            "        }\n" +
            "      }\n" +
            "    ],\n" +
            "    \"private_metadata\": \"\",\n" +
            "    \"callback_id\": \"view-callback-id\",\n" +
            "    \"state\": {\n" +
            "      \"values\": {\n" +
            "        \"Cuw\": {\n" +
            "          \"0HiH\": {\n" +
            "            \"type\": \"channels_select\",\n" +
            "            \"selected_channel\": \"C234\"\n" +
            "          }\n" +
            "        }\n" +
            "      }\n" +
            "    },\n" +
            "    \"hash\": \"123.123\",\n" +
            "    \"title\": {\n" +
            "      \"type\": \"plain_text\",\n" +
            "      \"text\": \"My App\",\n" +
            "      \"emoji\": true\n" +
            "    },\n" +
            "    \"clear_on_close\": false,\n" +
            "    \"notify_on_close\": true,\n" +
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
            "  },\n" +
            "  \"response_urls\": [\n" +
            "    {\n" +
            "      \"block_id\": \"Cuw\",\n" +
            "      \"action_id\": \"0HiH\",\n" +
            "      \"channel_id\": \"C234\",\n" +
            "      \"response_url\": \"https://hooks.slack.com/app/T123/123/random\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    @Test
    public void responseUrl_single() throws UnsupportedEncodingException {
        RequestHeaders headers = new RequestHeaders(Collections.emptyMap());
        ViewSubmissionRequest req = new ViewSubmissionRequest("payload=" + URLEncoder.encode(payload, "UTF-8"), payload, headers);
        assertEquals("https://hooks.slack.com/app/T123/123/random", req.getResponseUrl());
    }
}
