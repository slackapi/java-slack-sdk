package test_locally.api.model;

import com.slack.api.model.manifest.AppManifest;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AppManifestTest {

    @Test
    public void parser() {
        String json = "{\n" +
                "    \"display_information\": {\n" +
                "        \"name\": \"manifest-sandbox\"\n" +
                "    },\n" +
                "    \"features\": {\n" +
                "        \"app_home\": {\n" +
                "            \"home_tab_enabled\": true,\n" +
                "            \"messages_tab_enabled\": false,\n" +
                "            \"messages_tab_read_only_enabled\": false\n" +
                "        },\n" +
                "        \"bot_user\": {\n" +
                "            \"display_name\": \"manifest-sandbox\",\n" +
                "            \"always_online\": true\n" +
                "        },\n" +
                "        \"shortcuts\": [\n" +
                "            {\n" +
                "                \"name\": \"message one\",\n" +
                "                \"type\": \"message\",\n" +
                "                \"callback_id\": \"m\",\n" +
                "                \"description\": \"message\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\": \"global one\",\n" +
                "                \"type\": \"global\",\n" +
                "                \"callback_id\": \"g\",\n" +
                "                \"description\": \"global\"\n" +
                "            }\n" +
                "        ],\n" +
                "        \"slash_commands\": [\n" +
                "            {\n" +
                "                \"command\": \"/hey\",\n" +
                "                \"url\": \"https://www.example.com/\",\n" +
                "                \"description\": \"What's up?\",\n" +
                "                \"usage_hint\": \"What's up?\",\n" +
                "                \"should_escape\": true\n" +
                "            }\n" +
                "        ],\n" +
                "        \"unfurl_domains\": [\n" +
                "            \"example.com\"\n" +
                "        ]\n" +
                "    },\n" +
                "    \"oauth_config\": {\n" +
                "        \"redirect_urls\": [\n" +
                "            \"https://www.example.com/foo\"\n" +
                "        ],\n" +
                "        \"scopes\": {\n" +
                "            \"user\": [\n" +
                "                \"search:read\",\n" +
                "                \"channels:read\",\n" +
                "                \"groups:read\",\n" +
                "                \"mpim:read\"\n" +
                "            ],\n" +
                "            \"bot\": [\n" +
                "                \"commands\",\n" +
                "                \"incoming-webhook\",\n" +
                "                \"app_mentions:read\",\n" +
                "                \"links:read\"\n" +
                "            ]\n" +
                "        }\n" +
                "    },\n" +
                "    \"settings\": {\n" +
                "        \"allowed_ip_address_ranges\": [\n" +
                "            \"123.123.123.123/32\"\n" +
                "        ],\n" +
                "        \"event_subscriptions\": {\n" +
                "            \"request_url\": \"https://www.example.com/slack/events\",\n" +
                "            \"user_events\": [\n" +
                "                \"member_joined_channel\"\n" +
                "            ],\n" +
                "            \"bot_events\": [\n" +
                "                \"app_mention\",\n" +
                "                \"link_shared\"\n" +
                "            ]\n" +
                "        },\n" +
                "        \"interactivity\": {\n" +
                "            \"is_enabled\": true,\n" +
                "            \"request_url\": \"https://www.example.com/\",\n" +
                "            \"message_menu_options_url\": \"https://www.example.com/\"\n" +
                "        },\n" +
                "        \"org_deploy_enabled\": true,\n" +
                "        \"socket_mode_enabled\": false,\n" +
                "        \"token_rotation_enabled\": true\n" +
                "    }\n" +
                "}";

        AppManifest appManifest = GsonFactory.createSnakeCase(true, true)
                .fromJson(json, AppManifest.class);
        assertThat(appManifest.getSettings(), is(notNullValue()));
    }
}
