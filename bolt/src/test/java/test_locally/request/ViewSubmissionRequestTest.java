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

    String orgWideInstallationPayload = "{\n" +
            "  \"type\": \"view_submission\",\n" +
            "  \"team\": null,\n" +
            "  \"user\": {\n" +
            "    \"id\": \"W111\",\n" +
            "    \"username\": \"primary-owner\",\n" +
            "    \"name\": \"primary-owner\",\n" +
            "    \"team_id\": \"T_expected\"\n" +
            "  },\n" +
            "  \"api_app_id\": \"A111\",\n" +
            "  \"token\": \"fixed-value\",\n" +
            "  \"trigger_id\": \"1111.222.xxx\",\n" +
            "  \"view\": {\n" +
            "    \"id\": \"V111\",\n" +
            "    \"team_id\": \"T_expected\",\n" +
            "    \"type\": \"modal\",\n" +
            "    \"blocks\": [\n" +
            "      {\n" +
            "        \"type\": \"input\",\n" +
            "        \"block_id\": \"+5B\",\n" +
            "        \"label\": {\n" +
            "          \"type\": \"plain_text\",\n" +
            "          \"text\": \"Label\",\n" +
            "          \"emoji\": true\n" +
            "        },\n" +
            "        \"optional\": false,\n" +
            "        \"dispatch_action\": false,\n" +
            "        \"element\": {\n" +
            "          \"type\": \"plain_text_input\",\n" +
            "          \"dispatch_action_config\": {\n" +
            "            \"trigger_actions_on\": [\n" +
            "              \"on_enter_pressed\"\n" +
            "            ]\n" +
            "          },\n" +
            "          \"action_id\": \"MMKH\"\n" +
            "        }\n" +
            "      }\n" +
            "    ],\n" +
            "    \"private_metadata\": \"\",\n" +
            "    \"callback_id\": \"view-id\",\n" +
            "    \"state\": {\n" +
            "      \"values\": {\n" +
            "        \"+5B\": {\n" +
            "          \"MMKH\": {\n" +
            "            \"type\": \"plain_text_input\",\n" +
            "            \"value\": \"test\"\n" +
            "          }\n" +
            "        }\n" +
            "      }\n" +
            "    },\n" +
            "    \"hash\": \"111.xxx\",\n" +
            "    \"title\": {\n" +
            "      \"type\": \"plain_text\",\n" +
            "      \"text\": \"My App\"\n" +
            "    },\n" +
            "    \"clear_on_close\": false,\n" +
            "    \"notify_on_close\": false,\n" +
            "    \"close\": {\n" +
            "      \"type\": \"plain_text\",\n" +
            "      \"text\": \"Cancel\"\n" +
            "    },\n" +
            "    \"submit\": {\n" +
            "      \"type\": \"plain_text\",\n" +
            "      \"text\": \"Submit\",\n" +
            "      \"emoji\": true\n" +
            "    },\n" +
            "    \"previous_view_id\": null,\n" +
            "    \"root_view_id\": \"V111\",\n" +
            "    \"app_id\": \"A111\",\n" +
            "    \"external_id\": \"\",\n" +
            "    \"app_installed_team_id\": \"E111\",\n" +
            "    \"bot_id\": \"B111\"\n" +
            "  },\n" +
            "  \"response_urls\": [],\n" +
            "  \"is_enterprise_install\": true,\n" +
            "  \"enterprise\": {\n" +
            "    \"id\": \"E111\",\n" +
            "    \"name\": \"Sandbox Org\"\n" +
            "  }\n" +
            "}";

    @Test
    public void orgWideInstallation() throws UnsupportedEncodingException {
        RequestHeaders headers = new RequestHeaders(Collections.emptyMap());
        ViewSubmissionRequest req = new ViewSubmissionRequest("payload=" + URLEncoder.encode(orgWideInstallationPayload, "UTF-8"), orgWideInstallationPayload, headers);
        assertEquals("T_expected", req.getContext().getTeamId());
    }

    String sharedChannelPayload = "{\n" +
            "  \"type\": \"view_submission\",\n" +
            "  \"team\": {\n" +
            "    \"id\": \"T-other-side\",\n" +
            "    \"domain\": \"other-side\",\n" +
            "    \"enterprise_id\": \"E-other-side\",\n" +
            "    \"enterprise_name\": \"Kaz Sandbox Org\"\n" +
            "  },\n" +
            "  \"user\": {\n" +
            "    \"id\": \"W111\",\n" +
            "    \"username\": \"kaz\",\n" +
            "    \"name\": \"kaz\",\n" +
            "    \"team_id\": \"T-other-side\"\n" +
            "  },\n" +
            "  \"api_app_id\": \"A1111\",\n" +
            "  \"token\": \"legacy-fixed-token\",\n" +
            "  \"trigger_id\": \"111.222.xxx\",\n" +
            "  \"view\": {\n" +
            "    \"id\": \"V11111\",\n" +
            "    \"team_id\": \"T-other-side\",\n" +
            "    \"type\": \"modal\",\n" +
            "    \"blocks\": [\n" +
            "      {\n" +
            "        \"type\": \"input\",\n" +
            "        \"block_id\": \"zniAM\",\n" +
            "        \"label\": {\n" +
            "          \"type\": \"plain_text\",\n" +
            "          \"text\": \"Label\"\n" +
            "        },\n" +
            "        \"element\": {\n" +
            "          \"type\": \"plain_text_input\",\n" +
            "          \"dispatch_action_config\": {\n" +
            "            \"trigger_actions_on\": [\n" +
            "              \"on_enter_pressed\"\n" +
            "            ]\n" +
            "          },\n" +
            "          \"action_id\": \"qEJr\"\n" +
            "        }\n" +
            "      }\n" +
            "    ],\n" +
            "    \"private_metadata\": \"\",\n" +
            "    \"callback_id\": \"view-id\",\n" +
            "    \"state\": {\n" +
            "      \"values\": {\n" +
            "        \"zniAM\": {\n" +
            "          \"qEJr\": {\n" +
            "            \"type\": \"plain_text_input\",\n" +
            "            \"value\": \"Hi there!\"\n" +
            "          }\n" +
            "        }\n" +
            "      }\n" +
            "    },\n" +
            "    \"hash\": \"1664950703.CmTS8F7U\",\n" +
            "    \"title\": {\n" +
            "      \"type\": \"plain_text\",\n" +
            "      \"text\": \"My App\"\n" +
            "    },\n" +
            "    \"close\": {\n" +
            "      \"type\": \"plain_text\",\n" +
            "      \"text\": \"Cancel\"\n" +
            "    },\n" +
            "    \"submit\": {\n" +
            "      \"type\": \"plain_text\",\n" +
            "      \"text\": \"Submit\"\n" +
            "    },\n" +
            "    \"root_view_id\": \"V00000\",\n" +
            "    \"app_id\": \"A1111\",\n" +
            "    \"external_id\": \"\",\n" +
            "    \"app_installed_team_id\": \"T-installed-workspace\",\n" +
            "    \"bot_id\": \"B1111\"\n" +
            "  },\n" +
            "  \"enterprise\": {\n" +
            "    \"id\": \"E-other-side\",\n" +
            "    \"name\": \"Kaz Sandbox Org\"\n" +
            "  }\n" +
            "}\n";

    @Test
    public void sharedChannels() throws UnsupportedEncodingException {
        RequestHeaders headers = new RequestHeaders(Collections.emptyMap());
        ViewSubmissionRequest req = new ViewSubmissionRequest("payload=" + URLEncoder.encode(sharedChannelPayload, "UTF-8"), sharedChannelPayload, headers);
        assertEquals("T-installed-workspace", req.getContext().getTeamId());
    }
}
