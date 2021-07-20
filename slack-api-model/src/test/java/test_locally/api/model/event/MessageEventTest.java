package test_locally.api.model.event;

import com.google.gson.Gson;
import com.slack.api.model.File;
import com.slack.api.model.event.MessageBotEvent;
import com.slack.api.model.event.MessageChangedEvent;
import com.slack.api.model.event.MessageEvent;
import com.slack.api.model.event.MessageFileShareEvent;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class MessageEventTest {

    @Test
    public void typeName() {
        assertThat(MessageEvent.TYPE_NAME, is("message"));
    }

    @Test
    public void deserialize_simple() {
        String json = "{\n" +
                "    \"type\": \"message\",\n" +
                "    \"channel\": \"C2147483705\",\n" +
                "    \"user\": \"U2147483697\",\n" +
                "    \"text\": \"Hello world\",\n" +
                "    \"ts\": \"1355517523.000005\"\n" +
                "}";
        MessageEvent event = GsonFactory.createSnakeCase().fromJson(json, MessageEvent.class);
        assertThat(event.getType(), is("message"));
        assertThat(event.getChannel(), is("C2147483705"));
        assertThat(event.getUser(), is("U2147483697"));
        assertThat(event.getText(), is("Hello world"));
        assertThat(event.getTs(), is("1355517523.000005"));
    }

    @Test
    public void deserialize_edited() {
        String json = "{\n" +
                "    \"type\": \"message\",\n" +
                "    \"channel\": \"C2147483705\",\n" +
                "    \"user\": \"U2147483697\",\n" +
                "    \"text\": \"Hello, world!\",\n" +
                "    \"ts\": \"1355517523.000005\",\n" +
                "    \"edited\": {\n" +
                "        \"user\": \"U2147483697\",\n" +
                "        \"ts\": \"1355517536.000001\"\n" +
                "    }\n" +
                "}";
        MessageEvent event = GsonFactory.createSnakeCase().fromJson(json, MessageEvent.class);
        assertThat(event.getType(), is("message"));
        assertThat(event.getChannel(), is("C2147483705"));
        assertThat(event.getUser(), is("U2147483697"));
        assertThat(event.getText(), is("Hello, world!"));
        assertThat(event.getTs(), is("1355517523.000005"));
        assertThat(event.getEdited().getUser(), is("U2147483697"));
        assertThat(event.getEdited().getTs(), is("1355517536.000001"));
    }

    // What's an app_home? It's like a virtual house for your Slack app.
    // Today, it's a handy way of knowing which users you can have open direct message conversations with
    // — the ones users can find under Apps in the sidebar.
    @Test
    public void deserialize_app_home() {
        String json = "{\n" +
                "        \"type\": \"message\",\n" +
                "        \"user\": \"U061F7AUR\",\n" +
                "        \"text\": \"How many cats did we herd yesterday?\",\n" +
                "        \"ts\": \"1525215129.000001\",\n" +
                "        \"channel\": \"D0PNCRP9N\",\n" +
                "        \"event_ts\": \"1525215129.000001\",\n" +
                "        \"channel_type\": \"app_home\"\n" +
                "}";
        MessageEvent event = GsonFactory.createSnakeCase().fromJson(json, MessageEvent.class);
        assertThat(event.getType(), is("message"));
        assertThat(event.getChannel(), is("D0PNCRP9N"));
        assertThat(event.getUser(), is("U061F7AUR"));
        assertThat(event.getText(), is("How many cats did we herd yesterday?"));
        assertThat(event.getTs(), is("1525215129.000001"));
        assertThat(event.getEventTs(), is("1525215129.000001"));
        assertThat(event.getChannelType(), is("app_home"));
    }

    @Test
    public void deserialize_channel() {
        String json = "{\n" +
                "        \"type\": \"message\",\n" +
                "        \"channel\": \"C024BE91L\",\n" +
                "        \"user\": \"U2147483697\",\n" +
                "        \"text\": \"Live long and prospect.\",\n" +
                "        \"ts\": \"1355517523.000005\",\n" +
                "        \"event_ts\": \"1355517523.000005\",\n" +
                "        \"channel_type\": \"channel\"\n" +
                "}";
        MessageEvent event = GsonFactory.createSnakeCase().fromJson(json, MessageEvent.class);
        assertThat(event.getType(), is("message"));
        assertThat(event.getChannel(), is("C024BE91L"));
        assertThat(event.getUser(), is("U2147483697"));
        assertThat(event.getText(), is("Live long and prospect."));
        assertThat(event.getTs(), is("1355517523.000005"));
        assertThat(event.getEventTs(), is("1355517523.000005"));
        assertThat(event.getChannelType(), is("channel"));
    }

    @Test
    public void deserialize_group() {
        String json = "{\n" +
                "        \"type\": \"message\",\n" +
                "        \"channel\": \"G024BE91L\",\n" +
                "        \"user\": \"U2147483697\",\n" +
                "        \"text\": \"One cannot programmatically detect the difference between `message.mpim` and `message.groups`.\",\n" +
                "        \"ts\": \"1355517523.000005\",\n" +
                "        \"event_ts\": \"1355517523.000005\",\n" +
                "        \"channel_type\": \"group\"\n" +
                "}";
        MessageEvent event = GsonFactory.createSnakeCase().fromJson(json, MessageEvent.class);
        assertThat(event.getType(), is("message"));
        assertThat(event.getChannel(), is("G024BE91L"));
        assertThat(event.getUser(), is("U2147483697"));
        assertThat(event.getText(), is("One cannot programmatically detect the difference between `message.mpim` and `message.groups`."));
        assertThat(event.getTs(), is("1355517523.000005"));
        assertThat(event.getEventTs(), is("1355517523.000005"));
        assertThat(event.getChannelType(), is("group"));
    }

    @Test
    public void deserialize_im() {
        String json = "{\n" +
                "        \"type\": \"message\",\n" +
                "        \"channel\": \"D024BE91L\",\n" +
                "        \"user\": \"U2147483697\",\n" +
                "        \"text\": \"Hello hello can you hear me?\",\n" +
                "        \"ts\": \"1355517523.000005\",\n" +
                "        \"event_ts\": \"1355517523.000005\",\n" +
                "        \"channel_type\": \"im\"\n" +
                "}";
        MessageEvent event = GsonFactory.createSnakeCase().fromJson(json, MessageEvent.class);
        assertThat(event.getType(), is("message"));
        assertThat(event.getChannel(), is("D024BE91L"));
        assertThat(event.getUser(), is("U2147483697"));
        assertThat(event.getText(), is("Hello hello can you hear me?"));
        assertThat(event.getTs(), is("1355517523.000005"));
        assertThat(event.getEventTs(), is("1355517523.000005"));
        assertThat(event.getChannelType(), is("im"));
    }

    @Test
    public void deserialize_mpim() {
        String json = "{\n" +
                "        \"type\": \"message\",\n" +
                "        \"channel\": \"G024BE91L\",\n" +
                "        \"user\": \"U2147483697\",\n" +
                "        \"text\": \"Let's make a pact.\",\n" +
                "        \"ts\": \"1355517523.000005\",\n" +
                "        \"event_ts\": \"1355517523.000005\",\n" +
                "        \"channel_type\": \"mpim\"\n" +
                "}";
        MessageEvent event = GsonFactory.createSnakeCase().fromJson(json, MessageEvent.class);
        assertThat(event.getType(), is("message"));
        assertThat(event.getChannel(), is("G024BE91L"));
        assertThat(event.getUser(), is("U2147483697"));
        assertThat(event.getText(), is("Let's make a pact."));
        assertThat(event.getTs(), is("1355517523.000005"));
        assertThat(event.getEventTs(), is("1355517523.000005"));
        assertThat(event.getChannelType(), is("mpim"));
    }

    @Test
    public void deserialize_botMessage() {
        String json = "{\n" +
                "        \"type\": \"message\",\n" +
                "        \"subtype\": \"bot_message\",\n" +
                "        \"channel\": \"G024BE91L\",\n" +
                "        \"text\": \"Let's make a pact.\",\n" +
                "        \"username\": \"seanbot\",\n" +
                "        \"bot_id\": \"ABC1234\",\n" +
                "        \"icons\":{\"emoji\":\":smile:\",\"image_64\":\"https:\\/\\/a.slack-edge.com\\/37d58\\/img\\/emoji_2017_12_06\\/apple\\/1f604.png\"}," +
                "        \"ts\": \"1355517523.000005\",\n" +
                "        \"event_ts\": \"1355517523.000005\",\n" +
                "        \"channel_type\": \"mpim\"\n" +
                "}";
        MessageBotEvent event = GsonFactory.createSnakeCase().fromJson(json, MessageBotEvent.class);
        assertThat(event.getType(), is("message"));
        assertThat(event.getSubtype(), is("bot_message"));
        assertThat(event.getChannel(), is("G024BE91L"));
        assertThat(event.getText(), is("Let's make a pact."));
        assertThat(event.getTs(), is("1355517523.000005"));
        assertThat(event.getEventTs(), is("1355517523.000005"));
        assertThat(event.getChannelType(), is("mpim"));
        assertThat(event.getUsername(), is("seanbot"));
        assertThat(event.getBotId(), is("ABC1234"));
        assertThat(event.getIcons().getEmoji(), is(":smile:"));
        assertThat(event.getIcons().getImage64(), is("https://a.slack-edge.com/37d58/img/emoji_2017_12_06/apple/1f604.png"));
    }

    @Test
    public void deserialize_botMessage_thread_ts() {
        String json = "{\n" +
                "  \"type\": \"message\",\n" +
                "  \"subtype\": \"bot_message\",\n" +
                "  \"text\": \"Let's make a pact.\",\n" +
                "  \"ts\": \"1610402265.000300\",\n" +
                "  \"username\": \"classic-app-test\",\n" +
                "  \"bot_id\": \"B111\",\n" +
                "  \"thread_ts\": \"1610402224.000200\",\n" +
                "  \"channel\": \"C111\",\n" +
                "  \"event_ts\": \"1610402265.000300\",\n" +
                "  \"channel_type\": \"channel\"\n" +
                "}\n";
        MessageBotEvent event = GsonFactory.createSnakeCase().fromJson(json, MessageBotEvent.class);
        assertThat(event.getType(), is("message"));
        assertThat(event.getSubtype(), is("bot_message"));
        assertThat(event.getChannel(), is("C111"));
        assertThat(event.getText(), is("Let's make a pact."));
        assertThat(event.getTs(), is("1610402265.000300"));
        assertThat(event.getEventTs(), is("1610402265.000300"));
        assertThat(event.getChannelType(), is("channel"));
        assertThat(event.getUsername(), is("classic-app-test"));
        assertThat(event.getBotId(), is("B111"));
        assertThat(event.getThreadTs(), is("1610402224.000200"));
    }

    @Test
    public void serialize_simple() {
        Gson gson = GsonFactory.createSnakeCase();
        MessageEvent event = new MessageEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"message\"}";
        assertThat(generatedJson, is(expectedJson));
    }

    @Test
    public void deserialize_file_share() {
        String json = "{\n" +
                "  \"type\": \"message\",\n" +
                "  \"text\": \"Here you are\",\n" +
                "  \"files\": [\n" +
                "    {\n" +
                "      \"id\": \"F111\",\n" +
                "      \"created\": 1592795432,\n" +
                "      \"timestamp\": 1592795432,\n" +
                "      \"name\": \"test_text.txt\",\n" +
                "      \"title\": \"test text\",\n" +
                "      \"mimetype\": \"text/plain\",\n" +
                "      \"filetype\": \"text\",\n" +
                "      \"pretty_type\": \"Plain Text\",\n" +
                "      \"user\": \"U111\",\n" +
                "      \"editable\": true,\n" +
                "      \"size\": 14,\n" +
                "      \"mode\": \"snippet\",\n" +
                "      \"is_external\": false,\n" +
                "      \"external_type\": \"\",\n" +
                "      \"is_public\": true,\n" +
                "      \"public_url_shared\": false,\n" +
                "      \"display_as_bot\": false,\n" +
                "      \"username\": \"\",\n" +
                "      \"url_private\": \"https://files.slack.com/files-pri/T111-F111/test_text.txt\",\n" +
                "      \"url_private_download\": \"https://files.slack.com/files-pri/T111-F111/download/test_text.txt\",\n" +
                "      \"permalink\": \"https://org-domain.enterprise.slack.com/files/U111/F111/test_text.txt\",\n" +
                "      \"permalink_public\": \"https://slack-files.com/T111-F111-817003e111\",\n" +
                "      \"edit_link\": \"https://org-domain.enterprise.slack.com/files/U111/F111/test_text.txt/edit\",\n" +
                "      \"preview\": \"test test test\",\n" +
                "      \"preview_highlight\": \"<div class=\\\"CodeMirror cm-s-default CodeMirrorServer\\\" oncopy=\\\"if(event.clipboardData){event.clipboardData.setData('text/plain',window.getSelection().toString().replace(/\\\\u200b/g,''));event.preventDefault();event.stopPropagation();}\\\">\\n<div class=\\\"CodeMirror-code\\\">\\n<div><pre>test test test</pre></div>\\n</div>\\n</div>\\n\",\n" +
                "      \"lines\": 1,\n" +
                "      \"lines_more\": 0,\n" +
                "      \"preview_is_truncated\": false,\n" +
                "      \"has_rich_preview\": false\n" +
                "    }\n" +
                "  ],\n" +
                "  \"upload\": true,\n" +
                "  \"user\": \"U111\",\n" +
                "  \"display_as_bot\": false,\n" +
                "  \"ts\": \"1592795432.000500\",\n" +
                "  \"channel\": \"C111\",\n" +
                "  \"subtype\": \"file_share\",\n" +
                "  \"event_ts\": \"1592795432.000500\",\n" +
                "  \"channel_type\": \"channel\"\n" +
                "}\n";
        MessageFileShareEvent event = GsonFactory.createSnakeCase().fromJson(json, MessageFileShareEvent.class);
        List<File> files = event.getFiles();
        assertThat(files, is(notNullValue()));
        assertThat(files.size(), is(1));
        assertThat(files.get(0).getUrlPrivate(), is("https://files.slack.com/files-pri/T111-F111/test_text.txt"));
    }

    @Test
    public void deserialize_message_changed_with_files() {
        String json = "{\n" +
                "  \"type\": \"message\",\n" +
                "  \"subtype\": \"message_changed\",\n" +
                "  \"hidden\": true,\n" +
                "  \"message\": {\n" +
                "    \"type\": \"message\",\n" +
                "    \"text\": \"Here you are - let me know if you need other files as well\",\n" +
                "    \"files\": [\n" +
                "      {\n" +
                "        \"id\": \"F111\",\n" +
                "        \"created\": 1592796885,\n" +
                "        \"timestamp\": 1592796885,\n" +
                "        \"name\": \"test_text.txt\",\n" +
                "        \"title\": \"test text\",\n" +
                "        \"mimetype\": \"text/plain\",\n" +
                "        \"filetype\": \"text\",\n" +
                "        \"pretty_type\": \"プレーンテキスト\",\n" +
                "        \"user\": \"U111\",\n" +
                "        \"editable\": true,\n" +
                "        \"size\": 14,\n" +
                "        \"mode\": \"snippet\",\n" +
                "        \"is_external\": false,\n" +
                "        \"external_type\": \"\",\n" +
                "        \"is_public\": true,\n" +
                "        \"public_url_shared\": false,\n" +
                "        \"display_as_bot\": false,\n" +
                "        \"username\": \"\",\n" +
                "        \"url_private\": \"https://files.slack.com/files-pri/T111-F111/test_text.txt\",\n" +
                "        \"url_private_download\": \"https://files.slack.com/files-pri/T111-F111/download/test_text.txt\",\n" +
                "        \"permalink\": \"https://org-domain.enterprise.slack.com/files/U111/F111/test_text.txt\",\n" +
                "        \"permalink_public\": \"https://slack-files.com/T111-F111-34cde344b2\",\n" +
                "        \"edit_link\": \"https://org-domain.enterprise.slack.com/files/U111/F111/test_text.txt/edit\",\n" +
                "        \"preview\": \"test test test\",\n" +
                "        \"preview_highlight\": \"\\u003cdiv class\\u003d\\\"CodeMirror cm-s-default CodeMirrorServer\\\" oncopy\\u003d\\\"if(event.clipboardData){event.clipboardData.setData(\\u0027text/plain\\u0027,window.getSelection().toString().replace(/\\\\u200b/g,\\u0027\\u0027));event.preventDefault();event.stopPropagation();}\\\"\\u003e\\n\\u003cdiv class\\u003d\\\"CodeMirror-code\\\"\\u003e\\n\\u003cdiv\\u003e\\u003cpre\\u003etest test test\\u003c/pre\\u003e\\u003c/div\\u003e\\n\\u003c/div\\u003e\\n\\u003c/div\\u003e\\n\",\n" +
                "        \"lines\": 1,\n" +
                "        \"lines_more\": 0,\n" +
                "        \"preview_is_truncated\": false,\n" +
                "        \"has_rich_preview\": false\n" +
                "      }\n" +
                "    ],\n" +
                "    \"upload\": true,\n" +
                "    \"user\": \"U111\",\n" +
                "    \"display_as_bot\": false,\n" +
                "    \"edited\": {\n" +
                "      \"user\": \"U111\",\n" +
                "      \"ts\": \"1592796886.000000\"\n" +
                "    },\n" +
                "    \"ts\": \"1592796885.000500\",\n" +
                "    \"source_team\": \"E111\",\n" +
                "    \"user_team\": \"E111\"\n" +
                "  },\n" +
                "  \"channel\": \"C111\",\n" +
                "  \"previous_message\": {\n" +
                "    \"type\": \"message\",\n" +
                "    \"text\": \"Here you are\",\n" +
                "    \"files\": [\n" +
                "      {\n" +
                "        \"id\": \"F111\",\n" +
                "        \"created\": 1592796885,\n" +
                "        \"timestamp\": 1592796885,\n" +
                "        \"name\": \"test_text.txt\",\n" +
                "        \"title\": \"test text\",\n" +
                "        \"mimetype\": \"text/plain\",\n" +
                "        \"filetype\": \"text\",\n" +
                "        \"pretty_type\": \"プレーンテキスト\",\n" +
                "        \"user\": \"U111\",\n" +
                "        \"editable\": true,\n" +
                "        \"size\": 14,\n" +
                "        \"mode\": \"snippet\",\n" +
                "        \"is_external\": false,\n" +
                "        \"external_type\": \"\",\n" +
                "        \"is_public\": true,\n" +
                "        \"public_url_shared\": false,\n" +
                "        \"display_as_bot\": false,\n" +
                "        \"username\": \"\",\n" +
                "        \"url_private\": \"https://files.slack.com/files-pri/T111-F111/test_text.txt\",\n" +
                "        \"url_private_download\": \"https://files.slack.com/files-pri/T111-F111/download/test_text.txt\",\n" +
                "        \"permalink\": \"https://org-domain.enterprise.slack.com/files/U111/F111/test_text.txt\",\n" +
                "        \"permalink_public\": \"https://slack-files.com/T111-F111-34cde344b2\",\n" +
                "        \"edit_link\": \"https://org-domain.enterprise.slack.com/files/U111/F111/test_text.txt/edit\",\n" +
                "        \"preview\": \"test test test\",\n" +
                "        \"preview_highlight\": \"\\u003cdiv class\\u003d\\\"CodeMirror cm-s-default CodeMirrorServer\\\" oncopy\\u003d\\\"if(event.clipboardData){event.clipboardData.setData(\\u0027text/plain\\u0027,window.getSelection().toString().replace(/\\\\u200b/g,\\u0027\\u0027));event.preventDefault();event.stopPropagation();}\\\"\\u003e\\n\\u003cdiv class\\u003d\\\"CodeMirror-code\\\"\\u003e\\n\\u003cdiv\\u003e\\u003cpre\\u003etest test test\\u003c/pre\\u003e\\u003c/div\\u003e\\n\\u003c/div\\u003e\\n\\u003c/div\\u003e\\n\",\n" +
                "        \"lines\": 1,\n" +
                "        \"lines_more\": 0,\n" +
                "        \"preview_is_truncated\": false,\n" +
                "        \"is_starred\": false,\n" +
                "        \"has_rich_preview\": false\n" +
                "      }\n" +
                "    ],\n" +
                "    \"upload\": true,\n" +
                "    \"user\": \"U111\",\n" +
                "    \"display_as_bot\": false,\n" +
                "    \"ts\": \"1592796885.000500\"\n" +
                "  },\n" +
                "  \"event_ts\": \"1592796886.000800\",\n" +
                "  \"ts\": \"1592796886.000800\",\n" +
                "  \"channel_type\": \"channel\"\n" +
                "}";
        MessageChangedEvent event = GsonFactory.createSnakeCase().fromJson(json, MessageChangedEvent.class);
        assertThat(event.getMessage().getFiles(), is(notNullValue()));
        assertThat(event.getPreviousMessage().getMessage().getFiles(), is(notNullValue()));
        assertThat(event.getMessage().getFiles(), is(equalTo(event.getPreviousMessage().getMessage().getFiles())));
    }

    @Test
    public void deserialize_message_with_text_snippet() {
        String json = "{\n" +
                "  \"type\": \"message\",\n" +
                "  \"text\": \"Check this snippet\",\n" +
                "  \"files\": [\n" +
                "    {\n" +
                "      \"id\": \"F111\",\n" +
                "      \"created\": 1592795432,\n" +
                "      \"timestamp\": 1592795432,\n" +
                "      \"name\": \"test_text.txt\",\n" +
                "      \"title\": \"test text\",\n" +
                "      \"mimetype\": \"text/plain\",\n" +
                "      \"filetype\": \"text\",\n" +
                "      \"pretty_type\": \"Plain Text\",\n" +
                "      \"user\": \"U111\",\n" +
                "      \"editable\": true,\n" +
                "      \"size\": 14,\n" +
                "      \"mode\": \"snippet\",\n" +
                "      \"is_external\": false,\n" +
                "      \"external_type\": \"\",\n" +
                "      \"is_public\": true,\n" +
                "      \"public_url_shared\": false,\n" +
                "      \"display_as_bot\": false,\n" +
                "      \"username\": \"\",\n" +
                "      \"url_private\": \"https://files.slack.com/files-pri/T111-F111/test_text.txt\",\n" +
                "      \"url_private_download\": \"https://files.slack.com/files-pri/T111-F111/download/test_text.txt\",\n" +
                "      \"permalink\": \"https://org-domain.enterprise.slack.com/files/U111/F111/test_text.txt\",\n" +
                "      \"permalink_public\": \"https://slack-files.com/T111-F111-817003e111\",\n" +
                "      \"edit_link\": \"https://org-domain.enterprise.slack.com/files/U111/F111/test_text.txt/edit\",\n" +
                "      \"preview\": \"test test test\",\n" +
                "      \"preview_highlight\": \"<div class=\\\"CodeMirror cm-s-default CodeMirrorServer\\\" oncopy=\\\"if(event.clipboardData){event.clipboardData.setData('text/plain',window.getSelection().toString().replace(/\\\\u200b/g,''));event.preventDefault();event.stopPropagation();}\\\">\\n<div class=\\\"CodeMirror-code\\\">\\n<div><pre>test test test</pre></div>\\n</div>\\n</div>\\n\",\n" +
                "      \"lines\": 1,\n" +
                "      \"lines_more\": 0,\n" +
                "      \"preview_is_truncated\": false,\n" +
                "      \"has_rich_preview\": false\n" +
                "    }\n" +
                "  ],\n" +
                "  \"user\": \"U111\",\n" +
                "  \"ts\": \"1592795432.000500\",\n" +
                "  \"channel\": \"C111\",\n" +
                "  \"event_ts\": \"1592795432.000500\",\n" +
                "  \"channel_type\": \"channel\"\n" +
                "}\n";
        MessageEvent event = GsonFactory.createSnakeCase().fromJson(json, MessageEvent.class);
        List<File> files = event.getFiles();
        assertThat(files, is(notNullValue()));
        assertThat(files.size(), is(1));
        assertThat(files.get(0).getUrlPrivate(), is("https://files.slack.com/files-pri/T111-F111/test_text.txt"));
        assertThat(event.getType(), is("message"));
        assertThat(event.getChannel(), is("C111"));
        assertThat(event.getUser(), is("U111"));
        assertThat(event.getText(), is("Check this snippet"));
        assertThat(event.getTs(), is("1592795432.000500"));
    }
}
