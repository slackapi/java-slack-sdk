package test_locally.api.model.event;

import com.github.seratch.jslack.api.model.event.AppMentionEvent;
import com.google.gson.Gson;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
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

}
