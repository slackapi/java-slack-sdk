package test_locally.api.model.event;

import com.github.seratch.jslack.api.model.event.AppHomeOpenedEvent;
import com.google.gson.Gson;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class AppHomeOpenedEventTest {

    @Test
    public void typeName() {
        assertThat(AppHomeOpenedEvent.TYPE_NAME, is("app_home_opened"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"app_home_opened\",\n" +
                "    \"user\": \"U061F7AUR\",\n" +
                "    \"channel\": \"D0LAN2Q65\",\n" +
                "    \"event_ts\": \"1515449522000016\"\n" +
                "}";
        AppHomeOpenedEvent event = GsonFactory.createSnakeCase().fromJson(json, AppHomeOpenedEvent.class);
        assertThat(event.getType(), is("app_home_opened"));
        assertThat(event.getUser(), is("U061F7AUR"));
        assertThat(event.getChannel(), is("D0LAN2Q65"));
        assertThat(event.getEventTs(), is("1515449522000016"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        AppHomeOpenedEvent event = new AppHomeOpenedEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"app_home_opened\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}
