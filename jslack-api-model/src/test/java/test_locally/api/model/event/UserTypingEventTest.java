package test_locally.api.model.event;

import com.github.seratch.jslack.api.model.event.UserTypingEvent;
import com.google.gson.Gson;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class UserTypingEventTest {

    @Test
    public void typeName() {
        assertThat(UserTypingEvent.TYPE_NAME, is("user_typing"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"user_typing\",\n" +
                "    \"channel\": \"C02ELGNBH\",\n" +
                "    \"user\": \"U024BE7LH\"\n" +
                "}";
        UserTypingEvent event = GsonFactory.createSnakeCase().fromJson(json, UserTypingEvent.class);
        assertThat(event.getType(), is("user_typing"));
        assertThat(event.getChannel(), is("C02ELGNBH"));
        assertThat(event.getUser(), is("U024BE7LH"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        UserTypingEvent event = new UserTypingEvent();
        event.setChannel("c");
        event.setUser("u");
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"user_typing\",\"channel\":\"c\",\"user\":\"u\"}";
        assertThat(generatedJson, is(expectedJson));
    }
}