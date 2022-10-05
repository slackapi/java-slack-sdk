package test_locally.request;

import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.request.builtin.ViewClosedRequest;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class ViewClosedRequestTest {

    String orgWideInstallationPayload = "{\n" +
            "  \"type\": \"view_closed\",\n" +
            "  \"team\": null,\n" +
            "  \"user\": {\n" +
            "    \"id\": \"W111\",\n" +
            "    \"username\": \"primary-owner\",\n" +
            "    \"name\": \"primary-owner\",\n" +
            "    \"team_id\": \"T_expected\"\n" +
            "  },\n" +
            "  \"api_app_id\": \"A111\",\n" +
            "  \"token\": \"fixed-value\",\n" +
            "  \"view\": {\n" +
            "    \"id\": \"V111\",\n" +
            "    \"team_id\": \"T_expected\",\n" +
            "    \"type\": \"modal\",\n" +
            "    \"blocks\": [\n" +
            "      {\n" +
            "        \"type\": \"input\",\n" +
            "        \"block_id\": \"M2r2p\",\n" +
            "        \"label\": {\n" +
            "          \"type\": \"plain_text\",\n" +
            "          \"text\": \"Label\"\n" +
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
            "          \"action_id\": \"xB+\"\n" +
            "        }\n" +
            "      }\n" +
            "    ],\n" +
            "    \"private_metadata\": \"\",\n" +
            "    \"callback_id\": \"view-id\",\n" +
            "    \"state\": {\n" +
            "      \"values\": {}\n" +
            "    },\n" +
            "    \"hash\": \"1643113987.gRY6ROtt\",\n" +
            "    \"title\": {\n" +
            "      \"type\": \"plain_text\",\n" +
            "      \"text\": \"My App\"\n" +
            "    },\n" +
            "    \"clear_on_close\": false,\n" +
            "    \"notify_on_close\": true,\n" +
            "    \"close\": {\n" +
            "      \"type\": \"plain_text\",\n" +
            "      \"text\": \"Cancel\"\n" +
            "    },\n" +
            "    \"submit\": {\n" +
            "      \"type\": \"plain_text\",\n" +
            "      \"text\": \"Submit\"\n" +
            "    },\n" +
            "    \"previous_view_id\": null,\n" +
            "    \"root_view_id\": \"V111\",\n" +
            "    \"app_id\": \"A111\",\n" +
            "    \"external_id\": \"\",\n" +
            "    \"app_installed_team_id\": \"E111\",\n" +
            "    \"bot_id\": \"B0302M47727\"\n" +
            "  },\n" +
            "  \"is_cleared\": false,\n" +
            "  \"is_enterprise_install\": true,\n" +
            "  \"enterprise\": {\n" +
            "    \"id\": \"E111\",\n" +
            "    \"name\": \"Sandbox Org\"\n" +
            "  }\n" +
            "}";

    @Test
    public void orgWideInstallation() throws UnsupportedEncodingException {
        RequestHeaders headers = new RequestHeaders(Collections.emptyMap());
        ViewClosedRequest req = new ViewClosedRequest("payload=" + URLEncoder.encode(orgWideInstallationPayload, "UTF-8"), orgWideInstallationPayload, headers);
        assertEquals("T_expected", req.getContext().getTeamId());
    }

    String sharedChannelPayload = "{\n" +
            "  \"type\": \"view_closed\",\n" +
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
        ViewClosedRequest req = new ViewClosedRequest("payload=" + URLEncoder.encode(sharedChannelPayload, "UTF-8"), sharedChannelPayload, headers);
        assertEquals("T-installed-workspace", req.getContext().getTeamId());
    }
}
