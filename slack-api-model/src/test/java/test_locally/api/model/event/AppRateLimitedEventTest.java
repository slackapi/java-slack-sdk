package test_locally.api.model.event;

import com.slack.api.model.event.AppRateLimitedEvent;
import com.google.gson.Gson;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class AppRateLimitedEventTest {

    @Test
    public void typeName() {
        assertThat(AppRateLimitedEvent.TYPE_NAME, is("app_rate_limited"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"token\": \"Jhj5dZrVaK7ZwHHjRyZWjbDl\",\n" +
                "    \"type\": \"app_rate_limited\",\n" +
                "    \"team_id\": \"T123456\",\n" +
                "    \"minute_rate_limited\": 1518467820,\n" +
                "    \"api_app_id\": \"A123456\"\n" +
                "}";
        AppRateLimitedEvent event = GsonFactory.createSnakeCase().fromJson(json, AppRateLimitedEvent.class);
        assertThat(event.getType(), is("app_rate_limited"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        AppRateLimitedEvent event = new AppRateLimitedEvent();
        event.setTeamId("team-id");
        event.setApiAppId("api-app-id");
        event.setMinuteRateLimited(1518467820);
        event.setToken("token");
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"app_rate_limited\"," +
                "\"token\":\"token\"," +
                "\"team_id\":\"team-id\"," +
                "\"minute_rate_limited\":1518467820," +
                "\"api_app_id\":\"api-app-id\"" +
                "}";
        assertThat(generatedJson, is(expectedJson));
    }
}
