package test_locally.request;

import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.request.builtin.BlockActionRequest;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class BlockActionRequestTest {

    @Test
    public void test_null_payload() {
        Map<String, List<String>> rawHeaders = new HashMap<>();
        String signature = "v0=a2114d57b48eac39b9ad189dd8316235a7b4a8d21a10bd27519666489c69b503";
        String requestBody = "payload=";
        String remoteAddress = "127.0.0.1";
        rawHeaders.put("X-Slack-Signature", Arrays.asList(signature));
        RequestHeaders headers = new RequestHeaders(rawHeaders);
        BlockActionRequest request = new BlockActionRequest(requestBody, null, headers);
        request.setClientIpAddress(remoteAddress);

        assertEquals(remoteAddress, request.getClientIpAddress());
        assertEquals(0, request.getQueryString().size());
        assertEquals(requestBody, request.getRequestBodyAsString());
        assertEquals(1, request.getHeaders().getNames().size());
        assertEquals("x-slack-signature", request.getHeaders().getNames().iterator().next());
        assertEquals(signature, request.getHeaders().getFirstValue("X-Slack-Signature"));
    }

    String payload = "{\n" +
            "  \"type\": \"block_actions\",\n" +
            "  \"team\": {\n" +
            "    \"id\": \"T9TK3CUKW\",\n" +
            "    \"domain\": \"example\"\n" +
            "  },\n" +
            "  \"user\": {\n" +
            "    \"id\": \"UA8RXUSPL\",\n" +
            "    \"username\": \"jtorrance\",\n" +
            "    \"team_id\": \"T9TK3CUKW\"\n" +
            "  },\n" +
            "  \"api_app_id\": \"AABA1ABCD\",\n" +
            "  \"token\": \"legacy-fixed-value\",\n" +
            "  \"container\": {\n" +
            "    \"type\": \"message_attachment\",\n" +
            "    \"message_ts\": \"1548261231.000200\",\n" +
            "    \"attachment_id\": 1,\n" +
            "    \"channel_id\": \"CBR2V3XEX\",\n" +
            "    \"is_ephemeral\": false,\n" +
            "    \"is_app_unfurl\": false\n" +
            "  },\n" +
            "  \"trigger_id\": \"12321423423.333649436676.d8c1bb837935619ccad0f624c448ffb3\",\n" +
            "  \"channel\": {\n" +
            "    \"id\": \"CBR2V3XEX\",\n" +
            "    \"name\": \"review-updates\"\n" +
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
            "      \"text\": {\n" +
            "        \"type\": \"plain_text\",\n" +
            "        \"text\": \"View\",\n" +
            "        \"emoji\": true\n" +
            "      },\n" +
            "      \"value\": \"click_me_123\",\n" +
            "      \"type\": \"button\",\n" +
            "      \"action_ts\": \"1548426417.840180\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    @Test
    public void test_validPayload_single_workspace_mode() throws UnsupportedEncodingException {
        Map<String, List<String>> rawHeaders = new HashMap<>();
        String signature = "v0=a2114d57b48eac39b9ad189dd8316235a7b4a8d21a10bd27519666489c69b503";
        String requestBody = "payload=" + URLEncoder.encode(payload, "UTF-8");
        String remoteAddress = "127.0.0.1";
        rawHeaders.put("X-Slack-Signature", Arrays.asList(signature));
        RequestHeaders headers = new RequestHeaders(rawHeaders);
        BlockActionRequest request = new BlockActionRequest(requestBody, payload, headers);
        request.setClientIpAddress(remoteAddress);
        request.updateContext(AppConfig.builder().singleTeamBotToken("xoxb-123-123").build());

        assertEquals(remoteAddress, request.getClientIpAddress());
        assertEquals(0, request.getQueryString().size());
        assertEquals(requestBody, request.getRequestBodyAsString());
        assertEquals(1, request.getHeaders().getNames().size());
        assertEquals("x-slack-signature", request.getHeaders().getNames().iterator().next());
        assertEquals(signature, request.getHeaders().getFirstValue("X-Slack-Signature"));
    }
}
