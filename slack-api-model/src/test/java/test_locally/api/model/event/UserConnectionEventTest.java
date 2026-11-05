package test_locally.api.model.event;

import com.google.gson.Gson;
import com.slack.api.model.event.UserConnectionEvent;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class UserConnectionEventTest {

    @Test
    public void typeName() {
        assertThat(UserConnectionEvent.TYPE_NAME, is("user_connection"));
    }

    @Test
    public void deserializeConnect() {
        String json = "{\"type\":\"user_connection\",\"subtype\":\"connect\",\"user\":\"U123ABC456\"," +
                "\"trigger_id\":\"1293638028594.1249184885746.0d121a0e01d2e7a795ecc7a62880a406\"," +
                "\"event_ts\":\"1764264284.841251\"}";
        UserConnectionEvent event = GsonFactory.createSnakeCase().fromJson(json, UserConnectionEvent.class);
        assertThat(event.getType(), is("user_connection"));
        assertThat(event.getSubtype(), is("connect"));
        assertThat(event.getUser(), is("U123ABC456"));
        assertThat(event.getTriggerId(), is("1293638028594.1249184885746.0d121a0e01d2e7a795ecc7a62880a406"));
        assertThat(event.getEnterpriseId(), is(nullValue()));
        assertThat(event.getEventTs(), is("1764264284.841251"));
    }

    @Test
    public void deserializeDisconnect() {
        String json = "{\"type\":\"user_connection\",\"subtype\":\"disconnect\",\"user\":\"U123ABC456\"," +
                "\"enterprise_id\":\"E123ABC456\",\"event_ts\":\"1764264317.061589\"}";
        UserConnectionEvent event = GsonFactory.createSnakeCase().fromJson(json, UserConnectionEvent.class);
        assertThat(event.getSubtype(), is("disconnect"));
        assertThat(event.getUser(), is("U123ABC456"));
        assertThat(event.getTriggerId(), is(nullValue()));
        assertThat(event.getEnterpriseId(), is("E123ABC456"));
        assertThat(event.getEventTs(), is("1764264317.061589"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        UserConnectionEvent event = new UserConnectionEvent();
        event.setUser("U123ABC456");
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"user_connection\",\"user\":\"U123ABC456\"}";
        assertThat(generatedJson, is(expectedJson));
    }
}
