package test_locally.api.model.event;

import com.google.gson.Gson;
import com.slack.api.model.event.AppMentionEvent;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class AppMentionEventTest {

    @Test
    public void typeName() {
        assertThat(AppMentionEvent.TYPE_NAME, is("app_mention"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"app_mention\",\n" +
                "    \"user\": \"U061F7AUR\",\n" +
                "    \"subtype\": \"bot_message\",\n" +
                "    \"text\": \"<@U0LAN0Z89> is it everything a river should be?\",\n" +
                "    \"ts\": \"1515449522.000016\",\n" +
                "    \"channel\": \"C0LAN2Q65\",\n" +
                "    \"thread_ts\": \"1515449522000019\",\n" +
                "    \"event_ts\": \"1515449522000016\"\n" +
                "}";
        AppMentionEvent event = GsonFactory.createSnakeCase().fromJson(json, AppMentionEvent.class);
        assertThat(event.getType(), is("app_mention"));
    }

    @Test
    public void deserialize_edited() {
        String json = "{\n" +
                "  \"client_msg_id\": \"xxx\",\n" +
                "  \"type\": \"app_mention\",\n" +
                "  \"text\": \"<@U111> edited message\",\n" +
                "  \"user\": \"U222\",\n" +
                "  \"ts\": \"1622877872.008800\",\n" +
                "  \"team\": \"T111\",\n" +
                "  \"edited\": {\n" +
                "    \"user\": \"U222\",\n" +
                "    \"ts\": \"1622877902.000000\"\n" +
                "  },\n" +
                "  \"blocks\": [\n" +
                "    {\n" +
                "      \"type\": \"rich_text\",\n" +
                "      \"block_id\": \"3B2\",\n" +
                "      \"elements\": [\n" +
                "        {\n" +
                "          \"type\": \"rich_text_section\",\n" +
                "          \"elements\": [\n" +
                "            {\n" +
                "              \"type\": \"user\",\n" +
                "              \"user_id\": \"U111\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"type\": \"text\",\n" +
                "              \"text\": \" edited message\"\n" +
                "            }\n" +
                "          ]\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ],\n" +
                "  \"channel\": \"C111\",\n" +
                "  \"event_ts\": \"1622877872.008800\"\n" +
                "}";
        AppMentionEvent event = GsonFactory.createSnakeCase().fromJson(json, AppMentionEvent.class);
        assertThat(event.getType(), is("app_mention"));
        assertThat(event.getEdited().getTs(), is("1622877902.000000"));
        assertThat(event.getEdited().getUser(), is("U222"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        AppMentionEvent event = new AppMentionEvent();
        event.setUser("U061F7AUR");
        event.setSubtype("bot_message");
        event.setText("Hi!");
        event.setTs("1515449522.000016");
        event.setChannel("C0LAN2Q65");
        event.setThreadTs("1515449522000019");
        event.setEventTs("1515449522000016");
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"app_mention\"," +
                "\"user\":\"U061F7AUR\"," +
                "\"subtype\":\"bot_message\"," +
                "\"text\":\"Hi!\"," +
                "\"ts\":\"1515449522.000016\"," +
                "\"channel\":\"C0LAN2Q65\"," +
                "\"event_ts\":\"1515449522000016\"," +
                "\"thread_ts\":\"1515449522000019\"" +
                "}";
        assertThat(generatedJson, is(expectedJson));
    }

    @Test
    public void botMessage() {
        String json = "{\n" +
                "  \"type\": \"app_mention\",\n" +
                "  \"subtype\": \"bot_message\",\n" +
                "  \"text\": \"<@U111> hey\",\n" +
                "  \"ts\": \"1707288078.718469\",\n" +
                "  \"username\": \"Hey bot\",\n" +
                "  \"bot_id\": \"B111\",\n" +
                "  \"app_id\": \"A111\",\n" +
                "  \"blocks\": [\n" +
                "    {\n" +
                "      \"type\": \"rich_text\",\n" +
                "      \"block_id\": \"9fyvt\",\n" +
                "      \"elements\": [\n" +
                "        {\n" +
                "          \"type\": \"rich_text_section\",\n" +
                "          \"elements\": [\n" +
                "            {\n" +
                "              \"type\": \"user\",\n" +
                "              \"user_id\": \"U111\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"type\": \"text\",\n" +
                "              \"text\": \" hey\"\n" +
                "            }\n" +
                "          ]\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ],\n" +
                "  \"channel\": \"C111\",\n" +
                "  \"event_ts\": \"1707288078.718469\"\n" +
                "}";
        AppMentionEvent event = GsonFactory.createSnakeCase().fromJson(json, AppMentionEvent.class);
        assertThat(event.getType(), is("app_mention"));
        assertThat(event.getUser(), is(nullValue()));
    }

    @Test
    public void withFiles() throws Exception {
        String json = "{\n" +
                "  \"type\": \"app_mention\",\n" +
                "  \"text\": \"<@U111> hey\",\n" +
                "  \"files\": [\n" +
                "    {\n" +
                "      \"id\": \"F111\",\n" +
                "      \"created\": 1707806342,\n" +
                "      \"timestamp\": 1707806342,\n" +
                "      \"name\": \"test.png\",\n" +
                "      \"title\": \"test.png\",\n" +
                "      \"mimetype\": \"image/png\",\n" +
                "      \"filetype\": \"png\",\n" +
                "      \"pretty_type\": \"PNG\",\n" +
                "      \"user\": \"U03E94MK0\",\n" +
                "      \"user_team\": \"T111\",\n" +
                "      \"editable\": false,\n" +
                "      \"size\": 1312009,\n" +
                "      \"mode\": \"hosted\",\n" +
                "      \"is_external\": false,\n" +
                "      \"external_type\": \"\",\n" +
                "      \"is_public\": true,\n" +
                "      \"public_url_shared\": false,\n" +
                "      \"display_as_bot\": false,\n" +
                "      \"username\": \"\",\n" +
                "      \"url_private\": \"https://files.slack.com/files-pri/T111-F111/test.png\",\n" +
                "      \"url_private_download\": \"https://files.slack.com/files-pri/T111-F111/download/test.png\",\n" +
                "      \"media_display_type\": \"unknown\",\n" +
                "      \"thumb_64\": \"https://files.slack.com/files-tmb/T111-F111-xxx/test_64.png\",\n" +
                "      \"thumb_80\": \"https://files.slack.com/files-tmb/T111-F111-xxx/test_80.png\",\n" +
                "      \"thumb_360\": \"https://files.slack.com/files-tmb/T111-F111-xxx/test_360.png\",\n" +
                "      \"thumb_360_w\": 360,\n" +
                "      \"thumb_360_h\": 262,\n" +
                "      \"thumb_480\": \"https://files.slack.com/files-tmb/T111-F111-xxx/test_480.png\",\n" +
                "      \"thumb_480_w\": 480,\n" +
                "      \"thumb_480_h\": 350,\n" +
                "      \"thumb_160\": \"https://files.slack.com/files-tmb/T111-F111-xxx/test_160.png\",\n" +
                "      \"thumb_720\": \"https://files.slack.com/files-tmb/T111-F111-xxx/test_720.png\",\n" +
                "      \"thumb_720_w\": 720,\n" +
                "      \"thumb_720_h\": 525,\n" +
                "      \"thumb_800\": \"https://files.slack.com/files-tmb/T111-F111-xxx/test_800.png\",\n" +
                "      \"thumb_800_w\": 800,\n" +
                "      \"thumb_800_h\": 583,\n" +
                "      \"thumb_960\": \"https://files.slack.com/files-tmb/T111-F111-xxx/test_960.png\",\n" +
                "      \"thumb_960_w\": 960,\n" +
                "      \"thumb_960_h\": 700,\n" +
                "      \"thumb_1024\": \"https://files.slack.com/files-tmb/T111-F111-xxx/test_1024.png\",\n" +
                "      \"thumb_1024_w\": 1024,\n" +
                "      \"thumb_1024_h\": 747,\n" +
                "      \"original_w\": 1772,\n" +
                "      \"original_h\": 1292,\n" +
                "      \"thumb_tiny\": \"xxx\",\n" +
                "      \"permalink\": \"https://xxx.slack.com/files/U03E94MK0/F111/test.png\",\n" +
                "      \"permalink_public\": \"https://slack-files.com/T111-F111-a0770c9e47\",\n" +
                "      \"is_starred\": false,\n" +
                "      \"has_rich_preview\": false,\n" +
                "      \"file_access\": \"visible\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"upload\": false,\n" +
                "  \"user\": \"U03E94MK0\",\n" +
                "  \"display_as_bot\": false,\n" +
                "  \"ts\": \"1707806347.397809\",\n" +
                "  \"blocks\": [\n" +
                "    {\n" +
                "      \"type\": \"rich_text\",\n" +
                "      \"block_id\": \"t9D3L\",\n" +
                "      \"elements\": [\n" +
                "        {\n" +
                "          \"type\": \"rich_text_section\",\n" +
                "          \"elements\": [\n" +
                "            {\n" +
                "              \"type\": \"user\",\n" +
                "              \"user_id\": \"U111\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"type\": \"text\",\n" +
                "              \"text\": \" hey\"\n" +
                "            }\n" +
                "          ]\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ],\n" +
                "  \"client_msg_id\": \"883e5317-28e3-4ef8-9385-b88343560de6\",\n" +
                "  \"channel\": \"C111\",\n" +
                "  \"event_ts\": \"1707806347.397809\"\n" +
                "}\n";
        AppMentionEvent event = GsonFactory.createSnakeCase().fromJson(json, AppMentionEvent.class);
        assertThat(event.getType(), is("app_mention"));
    }

}
