package test_locally.app_backend.message_actions.payload;

import com.google.gson.Gson;
import com.slack.api.app_backend.interactive_components.payload.MessageShortcutPayload;
import com.slack.api.util.json.GsonFactory;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class MessageShortcutPayloadTest {

    public static final String JSON = "{\n" +
            "  \"token\": \"Nj2rfC2hU8mAfgaJLemZgO7H\",\n" +
            "  \"callback_id\": \"chirp_message\",\n" +
            "  \"type\": \"message_action\",\n" +
            "  \"trigger_id\": \"13345224609.8534564800.6f8ab1f53e13d0cd15f96106292d5536\",\n" +
            "  \"response_url\": \"https://hooks.slack.com/app-actions/T0MJR11A4/21974584944/yk1S9ndf35Q1flupVG5JbpM6\",\n" +
            "  \"team\": {\n" +
            "    \"id\": \"T0MJRM1A7\",\n" +
            "    \"domain\": \"pandamonium\"\n" +
            "  },\n" +
            "  \"channel\": {\n" +
            "    \"id\": \"D0LFFBKLZ\",\n" +
            "    \"name\": \"cats\"\n" +
            "  },\n" +
            "  \"user\": {\n" +
            "    \"id\": \"U0D15K92L\",\n" +
            "    \"name\": \"dr_maomao\"\n" +
            "  },\n" +
            "  \"message\": {\n" +
            "    \"type\": \"message\",\n" +
            "    \"user\": \"U0MJRG1AL\",\n" +
            "    \"ts\": \"1516229207.000133\",\n" +
            "    \"text\": \"World's smallest big cat! <https://youtube.com/watch?v=W86cTIoMv2U>\"\n" +
            "  }\n" +
            "}";

    private Gson gson = GsonFactory.createSnakeCase();

    @Test
    public void test() {
        MessageShortcutPayload payload = gson.fromJson(JSON, MessageShortcutPayload.class);
        assertThat(payload, is(notNullValue()));
        assertThat(payload.getType(), is("message_action"));
        assertThat(payload.getCallbackId(), is("chirp_message"));
        assertThat(payload.getToken(), is("Nj2rfC2hU8mAfgaJLemZgO7H"));
        assertThat(payload.getTriggerId(), is("13345224609.8534564800.6f8ab1f53e13d0cd15f96106292d5536"));
        assertThat(payload.getResponseUrl(), is("https://hooks.slack.com/app-actions/T0MJR11A4/21974584944/yk1S9ndf35Q1flupVG5JbpM6"));
        assertThat(payload.getTeam(), is(notNullValue()));
        assertThat(payload.getUser(), is(notNullValue()));
        assertThat(payload.getChannel(), is(notNullValue()));
        assertThat(payload.getMessage(), is(notNullValue()));
    }

    public static final String payload_with_files_in_an_attachment = "{\n" +
            "  \"type\": \"message_action\",\n" +
            "  \"token\": \"verification-token\",\n" +
            "  \"action_ts\": \"1608094019.550995\",\n" +
            "  \"team\": {\n" +
            "    \"id\": \"T111\",\n" +
            "    \"domain\": \"seratch\"\n" +
            "  },\n" +
            "  \"user\": {\n" +
            "    \"id\": \"U111\",\n" +
            "    \"username\": \"seratch\",\n" +
            "    \"team_id\": \"T111\",\n" +
            "    \"name\": \"seratch\"\n" +
            "  },\n" +
            "  \"channel\": {\n" +
            "    \"id\": \"C111\",\n" +
            "    \"name\": \"random\"\n" +
            "  },\n" +
            "  \"is_enterprise_install\": false,\n" +
            "  \"enterprise\": null,\n" +
            "  \"callback_id\": \"test-message-action\",\n" +
            "  \"trigger_id\": \"111.222.xxx\",\n" +
            "  \"response_url\": \"https://hooks.slack.com/app/T111/111/xxx\",\n" +
            "  \"message_ts\": \"1608093938.001200\",\n" +
            "  \"message\": {\n" +
            "    \"bot_id\": \"B111\",\n" +
            "    \"type\": \"message\",\n" +
            "    \"text\": \"Here is the uploaded file: <https://xxx.slack.com/archives/C111/xxx>\",\n" +
            "    \"user\": \"U111\",\n" +
            "    \"ts\": \"1608093938.001200\",\n" +
            "    \"team\": \"T111\",\n" +
            "    \"bot_profile\": {\n" +
            "      \"id\": \"B111\",\n" +
            "      \"deleted\": false,\n" +
            "      \"name\": \"Testing App\",\n" +
            "      \"updated\": 1584342729,\n" +
            "      \"app_id\": \"A111\",\n" +
            "      \"icons\": {\n" +
            "        \"image_36\": \"https://avatars.slack-edge.com/2019-02-23/xxx_yyy_36.jpg\",\n" +
            "        \"image_48\": \"https://avatars.slack-edge.com/2019-02-23/xxx_yyy_48.jpg\",\n" +
            "        \"image_72\": \"https://avatars.slack-edge.com/2019-02-23/xxx_yyy_72.jpg\"\n" +
            "      },\n" +
            "      \"team_id\": \"T111\"\n" +
            "    },\n" +
            "    \"edited\": {\n" +
            "      \"user\": \"B111\",\n" +
            "      \"ts\": \"1608093939.000000\"\n" +
            "    },\n" +
            "    \"attachments\": [\n" +
            "      {\n" +
            "        \"from_url\": \"https://xxx.slack.com/archives/C111/xxx\",\n" +
            "        \"fallback\": \"[December 15th, 2020 8:45 PM] testing_bot: Uploading a file...\",\n" +
            "        \"ts\": \"1608093937.000900\",\n" +
            "        \"author_id\": \"U111\",\n" +
            "        \"author_subname\": \"Testing Bot\",\n" +
            "        \"channel_id\": \"C111\",\n" +
            "        \"channel_name\": \"random\",\n" +
            "        \"is_msg_unfurl\": true,\n" +
            "        \"text\": \"Uploading a file...\",\n" +
            "        \"author_name\": \"Testing Bot\",\n" +
            "        \"author_link\": \"https://seratch.slack.com/team/U111\",\n" +
            "        \"author_icon\": \"https://avatars.slack-edge.com/2020-03-16/xxx.jpg\",\n" +
            "        \"mrkdwn_in\": [\n" +
            "          \"text\"\n" +
            "        ],\n" +
            "        \"files\": [\n" +
            "          {\n" +
            "            \"id\": \"F111\",\n" +
            "            \"created\": 1608093937,\n" +
            "            \"timestamp\": 1608093937,\n" +
            "            \"name\": \"-.txt\",\n" +
            "            \"title\": \"Untitled\",\n" +
            "            \"mimetype\": \"text/plain\",\n" +
            "            \"filetype\": \"text\",\n" +
            "            \"pretty_type\": \"Plain Text\",\n" +
            "            \"user\": \"U111\",\n" +
            "            \"editable\": true,\n" +
            "            \"size\": 14,\n" +
            "            \"mode\": \"snippet\",\n" +
            "            \"is_external\": false,\n" +
            "            \"external_type\": \"\",\n" +
            "            \"is_public\": true,\n" +
            "            \"public_url_shared\": false,\n" +
            "            \"display_as_bot\": false,\n" +
            "            \"username\": \"\",\n" +
            "            \"url_private\": \"https://files.slack.com/files-pri/T111-F111/-.txt\",\n" +
            "            \"url_private_download\": \"https://files.slack.com/files-pri/T111-F111/download/-.txt\",\n" +
            "            \"permalink\": \"https://seratch.slack.com/files/U111/F111/-.txt\",\n" +
            "            \"permalink_public\": \"https://slack-files.com/T111-F111-3c6c6b7323\",\n" +
            "            \"edit_link\": \"https://seratch.slack.com/files/U111/F111/-.txt/edit\",\n" +
            "            \"preview\": \"This is a test\",\n" +
            "            \"preview_highlight\": \"<div class=\\\"CodeMirror cm-s-default CodeMirrorServer\\\" oncopy=\\\"if(event.clipboardData){event.clipboardData.setData('text/plain',window.getSelection().toString().replace(/\\\\u200b/g,''));event.preventDefault();event.stopPropagation();}\\\">\\n<div class=\\\"CodeMirror-code\\\">\\n<div><pre>This is a test</pre></div>\\n</div>\\n</div>\\n\",\n" +
            "            \"lines\": 1,\n" +
            "            \"lines_more\": 0,\n" +
            "            \"preview_is_truncated\": false,\n" +
            "            \"has_rich_preview\": false\n" +
            "          }\n" +
            "        ],\n" +
            "        \"id\": 1,\n" +
            "        \"original_url\": \"https://xxx.slack.com/archives/C111/xxx\",\n" +
            "        \"footer\": \"Posted in #random\"\n" +
            "      }\n" +
            "    ]\n" +
            "  }\n" +
            "}";

    // https://github.com/slackapi/java-slack-sdk/issues/647
    @Test
    public void test_with_files_in_an_attachment_issue_647() {
        MessageShortcutPayload payload = gson.fromJson(payload_with_files_in_an_attachment, MessageShortcutPayload.class);
        assertThat(payload, is(notNullValue()));
        assertThat(payload.getType(), is("message_action"));
        assertThat(payload.getMessage().getFiles(), is(nullValue()));
        assertThat(payload.getMessage().getAttachments().get(0).getFiles(), is(notNullValue()));
    }
}
