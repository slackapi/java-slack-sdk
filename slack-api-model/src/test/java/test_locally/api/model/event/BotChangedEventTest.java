package test_locally.api.model.event;

import com.slack.api.model.BotIcons;
import com.slack.api.model.event.BotChangedEvent;
import com.google.gson.Gson;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class BotChangedEventTest {

    @Test
    public void typeName() {
        assertThat(BotChangedEvent.TYPE_NAME, is("bot_changed"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"bot_changed\",\n" +
                "    \"bot\": {\n" +
                "        \"id\": \"B024BE7LH\",\n" +
                "        \"app_id\": \"A4H1JB4AZ\",\n" +
                "        \"name\": \"hugbot\",\n" +
                "        \"icons\": {\n" +
                "            \"image_48\": \"https:\\/\\/slack.com\\/path\\/to\\/hugbot_48.png\"\n" +
                "        }\n" +
                "    }\n" +
                "}";
        BotChangedEvent event = GsonFactory.createSnakeCase().fromJson(json, BotChangedEvent.class);
        assertThat(event.getType(), is("bot_changed"));
        assertThat(event.getBot(), is(notNullValue()));
        assertThat(event.getBot().getId(), is("B024BE7LH"));
        assertThat(event.getBot().getAppId(), is("A4H1JB4AZ"));
        assertThat(event.getBot().getName(), is("hugbot"));
        assertThat(event.getBot().getIcons().getImage36(), is(nullValue()));
        assertThat(event.getBot().getIcons().getImage48(), is("https://slack.com/path/to/hugbot_48.png"));
        assertThat(event.getBot().getIcons().getImage72(), is(nullValue()));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        BotChangedEvent event = new BotChangedEvent();
        event.setBot(new BotChangedEvent.Bot());
        event.getBot().setId("123");
        event.getBot().setIcons(new BotIcons());
        event.getBot().getIcons().setImage36("https://slack.com/path/to/hugbot_36.png");
        String generatedJson = gson.toJson(event);
        String expectedJson = "{" +
                "\"type\":\"bot_changed\"," +
                "\"bot\":{\"id\":\"123\",\"icons\":{\"image_36\":\"https://slack.com/path/to/hugbot_36.png\"}}" +
                "}";
        assertThat(generatedJson, is(expectedJson));
    }
}
