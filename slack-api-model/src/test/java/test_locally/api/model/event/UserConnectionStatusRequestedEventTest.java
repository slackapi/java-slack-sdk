package test_locally.api.model.event;

import com.google.gson.Gson;
import com.slack.api.model.event.UserConnectionStatusRequestedEvent;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class UserConnectionStatusRequestedEventTest {

    @Test
    public void typeName() {
        assertThat(UserConnectionStatusRequestedEvent.TYPE_NAME, is("user_connection_status_requested"));
    }

    @Test
    public void deserialize() {
        String json = "{\"type\":\"user_connection_status_requested\",\"user\":\"U123ABC456\",\"event_ts\":\"1764264284.841251\"}";
        UserConnectionStatusRequestedEvent event =
                GsonFactory.createSnakeCase().fromJson(json, UserConnectionStatusRequestedEvent.class);
        assertThat(event.getType(), is("user_connection_status_requested"));
        assertThat(event.getUser(), is("U123ABC456"));
        assertThat(event.getEventTs(), is("1764264284.841251"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        UserConnectionStatusRequestedEvent event = new UserConnectionStatusRequestedEvent();
        event.setUser("U123ABC456");
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"user_connection_status_requested\",\"user\":\"U123ABC456\"}";
        assertThat(generatedJson, is(expectedJson));
    }
}
