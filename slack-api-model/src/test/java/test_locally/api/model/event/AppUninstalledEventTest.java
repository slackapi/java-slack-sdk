package test_locally.api.model.event;

import com.google.gson.Gson;
import com.slack.api.model.event.AppUninstalledEvent;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class AppUninstalledEventTest {

    @Test
    public void typeName() {
        assertThat(AppUninstalledEvent.TYPE_NAME, is("app_uninstalled"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"app_uninstalled\"\n" +
                "}";
        AppUninstalledEvent event = GsonFactory.createSnakeCase().fromJson(json, AppUninstalledEvent.class);
        assertThat(event.getType(), is("app_uninstalled"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        AppUninstalledEvent event = new AppUninstalledEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"app_uninstalled\"}";
        assertThat(generatedJson, is(expectedJson));
    }
}
