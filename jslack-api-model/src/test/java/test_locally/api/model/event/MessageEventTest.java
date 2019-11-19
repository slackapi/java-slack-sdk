package test_locally.api.model.event;

import com.github.seratch.jslack.api.model.event.MessageEvent;
import com.google.gson.Gson;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
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
                "        \"user\": \"U2147483697\",\n" +
                "        \"text\": \"Let's make a pact.\",\n" +
                "        \"username\": \"seanbot\",\n" +
                "        \"bot_id\": \"ABC1234\",\n" +
                "        \"icons\":{\"emoji\":\":smile:\",\"image_64\":\"https:\\/\\/a.slack-edge.com\\/37d58\\/img\\/emoji_2017_12_06\\/apple\\/1f604.png\"}," +
                "        \"ts\": \"1355517523.000005\",\n" +
                "        \"event_ts\": \"1355517523.000005\",\n" +
                "        \"channel_type\": \"mpim\"\n" +
                "}";
        MessageEvent event = GsonFactory.createSnakeCase().fromJson(json, MessageEvent.class);
        assertThat(event.getType(), is("message"));
        assertThat(event.getSubtype(), is("bot_message"));
        assertThat(event.getChannel(), is("G024BE91L"));
        assertThat(event.getUser(), is("U2147483697"));
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
    public void serialize_simple() {
        Gson gson = GsonFactory.createSnakeCase();
        MessageEvent event = new MessageEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"message\",\"hidden\":false,\"is_starred\":false}";
        assertThat(generatedJson, is(expectedJson));
    }

}
