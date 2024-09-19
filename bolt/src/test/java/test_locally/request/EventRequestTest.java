package test_locally.request;

import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.request.builtin.EventRequest;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class EventRequestTest {

    String appMentionPayload = "{\n" +
            "  \"token\": \"legacy-fixed-value\",\n" +
            "  \"team_id\": \"T123\",\n" +
            "  \"api_app_id\": \"A123\",\n" +
            "  \"event\": {\n" +
            "    \"client_msg_id\": \"3fd13273-5a6a-4b5c-bd6f-109fd697038c\",\n" +
            "    \"type\": \"app_mention\",\n" +
            "    \"text\": \"<@U123> test\",\n" +
            "    \"user\": \"UUUUUXXXXX\",\n" +
            "    \"ts\": \"1583636399.000700\",\n" +
            "    \"team\": \"T123\",\n" +
            "    \"blocks\": [\n" +
            "      {\n" +
            "        \"type\": \"rich_text\",\n" +
            "        \"block_id\": \"FMAzp\",\n" +
            "        \"elements\": [\n" +
            "          {\n" +
            "            \"type\": \"rich_text_section\",\n" +
            "            \"elements\": [\n" +
            "              {\n" +
            "                \"type\": \"user\",\n" +
            "                \"user_id\": \"U123\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"type\": \"text\",\n" +
            "                \"text\": \" test\"\n" +
            "              }\n" +
            "            ]\n" +
            "          }\n" +
            "        ]\n" +
            "      }\n" +
            "    ],\n" +
            "    \"channel\": \"C123\",\n" +
            "    \"event_ts\": \"1583636399.000700\"\n" +
            "  },\n" +
            "  \"type\": \"event_callback\",\n" +
            "  \"event_id\": \"EvV1KV8BM3\",\n" +
            "  \"event_time\": 1583636399,\n" +
            "  \"authed_users\": [\n" +
            "    \"U123\"\n" +
            "  ]\n" +
            "}";
    String appMentionPayloadWithSubtype = "{\n" +
            "  \"token\": \"legacy-fixed-value\",\n" +
            "  \"team_id\": \"T123\",\n" +
            "  \"api_app_id\": \"A123\",\n" +
            "  \"event\": {\n" +
            "    \"client_msg_id\": \"3fd13273-5a6a-4b5c-bd6f-109fd697038c\",\n" +
            "    \"type\": \"app_mention\",\n" +
            "    \"subtype\": \"bot_message\",\n" +
            "    \"text\": \"<@U123> test\",\n" +
            "    \"bot_id\": \"B123\",\n" +
            "    \"ts\": \"1583636399.000700\",\n" +
            "    \"team\": \"T123\",\n" +
            "    \"blocks\": [\n" +
            "      {\n" +
            "        \"type\": \"rich_text\",\n" +
            "        \"block_id\": \"FMAzp\",\n" +
            "        \"elements\": [\n" +
            "          {\n" +
            "            \"type\": \"rich_text_section\",\n" +
            "            \"elements\": [\n" +
            "              {\n" +
            "                \"type\": \"user\",\n" +
            "                \"user_id\": \"U123\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"type\": \"text\",\n" +
            "                \"text\": \" test\"\n" +
            "              }\n" +
            "            ]\n" +
            "          }\n" +
            "        ]\n" +
            "      }\n" +
            "    ],\n" +
            "    \"channel\": \"C123\",\n" +
            "    \"event_ts\": \"1583636399.000700\"\n" +
            "  },\n" +
            "  \"type\": \"event_callback\",\n" +
            "  \"event_id\": \"EvV1KV8BM3\",\n" +
            "  \"event_time\": 1583636399,\n" +
            "  \"authed_users\": [\n" +
            "    \"U123\"\n" +
            "  ]\n" +
            "}";
    String messagePayload = "{\n" +
            "  \"token\": \"legacy-fixed-value\",\n" +
            "  \"team_id\": \"T123\",\n" +
            "  \"api_app_id\": \"A123\",\n" +
            "  \"event\": {\n" +
            "    \"client_msg_id\": \"3fd13273-5a6a-4b5c-bd6f-109fd697038c\",\n" +
            "    \"type\": \"message\",\n" +
            "    \"text\": \"<@U123> test\",\n" +
            "    \"user\": \"UUUUUXXXXX\",\n" +
            "    \"ts\": \"1583636399.000700\",\n" +
            "    \"team\": \"T123\",\n" +
            "    \"blocks\": [\n" +
            "      {\n" +
            "        \"type\": \"rich_text\",\n" +
            "        \"block_id\": \"FMAzp\",\n" +
            "        \"elements\": [\n" +
            "          {\n" +
            "            \"type\": \"rich_text_section\",\n" +
            "            \"elements\": [\n" +
            "              {\n" +
            "                \"type\": \"user\",\n" +
            "                \"user_id\": \"U123\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"type\": \"text\",\n" +
            "                \"text\": \" test\"\n" +
            "              }\n" +
            "            ]\n" +
            "          }\n" +
            "        ]\n" +
            "      }\n" +
            "    ],\n" +
            "    \"channel\": \"C123\",\n" +
            "    \"event_ts\": \"1583636399.000700\",\n" +
            "    \"channel_type\": \"channel\"\n" +
            "  },\n" +
            "  \"type\": \"event_callback\",\n" +
            "  \"event_id\": \"EvV1KA7U3A\",\n" +
            "  \"event_time\": 1583636399,\n" +
            "  \"authed_users\": [\n" +
            "    \"U123\"\n" +
            "  ]\n" +
            "}";

    String appMentionPayloadInSlackConnect = "{\n" +
            "    \"token\": \"fixed-value\",\n" +
            "    \"team_id\": \"T_INSTALLED\",\n" +
            "    \"enterprise_id\": \"E_INSTALLED\",\n" +
            "    \"api_app_id\": \"A111\",\n" +
            "    \"event\": {\n" +
            "        \"client_msg_id\": \"ae6d418a-ff0e-433f-ba10-915cebb1bb94\",\n" +
            "        \"type\": \"app_mention\",\n" +
            "        \"text\": \"<@U123> hey\",\n" +
            "        \"user\": \"UUUUUXXXXX\",\n" +
            "        \"ts\": \"1605835902.008000\",\n" +
            "        \"team\": \"T_INSTALLED\",\n" +
            "        \"channel\": \"C111\",\n" +
            "        \"event_ts\": \"1605835902.008000\"\n" +
            "    },\n" +
            "    \"type\": \"event_callback\",\n" +
            "    \"event_id\": \"Ev01FC50TXB6\",\n" +
            "    \"event_time\": 1605835902,\n" +
            "    \"authorizations\": [\n" +
            "        {\n" +
            "            \"enterprise_id\": \"E_INSTALLED\",\n" +
            "            \"team_id\": \"T_INSTALLED\",\n" +
            "            \"user_id\": \"U234\",\n" +
            "            \"is_bot\": true,\n" +
            "            \"is_enterprise_install\": false\n" +
            "        }\n" +
            "    ],\n" +
            "    \"is_ext_shared_channel\": true,\n" +
            "    \"event_context\": \"1-app_mention-T_INSTALLED-C111\"\n" +
            "}";

    String messagePayloadInSlackConnect = "{\n" +
            "    \"token\": \"fixed-value\",\n" +
            "    \"team_id\": \"T_SOURCE\",\n" +
            "    \"api_app_id\": \"A111\",\n" +
            "    \"event\": {\n" +
            "        \"bot_id\": \"B123\",\n" +
            "        \"type\": \"message\",\n" +
            "        \"text\": \"Hi there!\",\n" +
            "        \"user\": \"UUUUUXXXXX\",\n" +
            "        \"ts\": \"1605835904.008100\",\n" +
            "        \"team\": \"T_INSTALLED\",\n" +
            "        \"bot_profile\": {\n" +
            "            \"id\": \"B123\",\n" +
            "            \"deleted\": false,\n" +
            "            \"name\": \"events-api-oauth-app\",\n" +
            "            \"updated\": 1605835851,\n" +
            "            \"app_id\": \"A111\",\n" +
            "            \"icons\": {\n" +
            "                \"image_36\": \"https://a.slack-edge.com/80588/img/plugins/app/bot_36.png\",\n" +
            "                \"image_48\": \"https://a.slack-edge.com/80588/img/plugins/app/bot_48.png\",\n" +
            "                \"image_72\": \"https://a.slack-edge.com/80588/img/plugins/app/service_72.png\"\n" +
            "            },\n" +
            "            \"team_id\": \"T_INSTALLED\"\n" +
            "        },\n" +
            "        \"channel\": \"C111\",\n" +
            "        \"event_ts\": \"1605835904.008100\",\n" +
            "        \"channel_type\": \"channel\"\n" +
            "    },\n" +
            "    \"type\": \"event_callback\",\n" +
            "    \"event_id\": \"Ev01F66ATNKV\",\n" +
            "    \"event_time\": 1605835904,\n" +
            "    \"authorizations\": [\n" +
            "        {\n" +
            "            \"enterprise_id\": \"E_INSTALLED\",\n" +
            "            \"team_id\": \"T_INSTALLED\",\n" +
            "            \"user_id\": \"U234\",\n" +
            "            \"is_bot\": true,\n" +
            "            \"is_enterprise_install\": false\n" +
            "        }\n" +
            "    ],\n" +
            "    \"is_ext_shared_channel\": true,\n" +
            "    \"event_context\": \"1-message-T_SOURCE-C111\"\n" +
            "}";

    String userTokenRevokedEvent = "{\n" +
            "    \"token\": \"fixed-value\",\n" +
            "    \"team_id\": \"T111\",\n" +
            "    \"enterprise_id\": \"E111\",\n" +
            "    \"api_app_id\": \"A111\",\n" +
            "    \"event\": {\n" +
            "        \"type\": \"tokens_revoked\",\n" +
            "        \"tokens\": {\n" +
            "            \"oauth\": [\n" +
            "                \"W111\"\n" +
            "            ],\n" +
            "            \"bot\": []\n" +
            "        },\n" +
            "        \"event_ts\": \"1614847360.131900\"\n" +
            "    },\n" +
            "    \"type\": \"event_callback\",\n" +
            "    \"event_id\": \"Ev111\",\n" +
            "    \"event_time\": 1614847360\n" +
            "}";

    @Test
    public void test() {
        RequestHeaders headers = new RequestHeaders(new HashMap<>());
        assertThat(new EventRequest(appMentionPayload, headers).getContext().getRequestUserId(), is("UUUUUXXXXX"));
        assertThat(new EventRequest(appMentionPayloadWithSubtype, headers).getContext().getRequestUserId(), is(nullValue()));
        assertThat(new EventRequest(messagePayload, headers).getContext().getRequestUserId(), is("UUUUUXXXXX"));
        assertThat(new EventRequest(appMentionPayloadInSlackConnect, headers).getContext().getRequestUserId(), is("UUUUUXXXXX"));
        assertThat(new EventRequest(messagePayloadInSlackConnect, headers).getContext().getRequestUserId(), is("UUUUUXXXXX"));
        assertThat(new EventRequest(userTokenRevokedEvent, headers).getContext().getRequestUserId(), is(nullValue()));
    }
}
