package test_locally.api.model.event;

import com.slack.api.model.event.UserChangeEvent;
import com.google.gson.Gson;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class UserChangeEventTest {

    @Test
    public void typeName() {
        assertThat(UserChangeEvent.TYPE_NAME, is("user_change"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"user_change\",\n" +
                "    \"user\": {\n" +
                "    }\n" +
                "}";
        UserChangeEvent event = GsonFactory.createSnakeCase().fromJson(json, UserChangeEvent.class);
        assertThat(event.getType(), is("user_change"));
        assertThat(event.getUser(), is(notNullValue()));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        UserChangeEvent event = new UserChangeEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"user_change\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}
