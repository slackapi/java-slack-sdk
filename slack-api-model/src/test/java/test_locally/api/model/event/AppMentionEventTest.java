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

}
