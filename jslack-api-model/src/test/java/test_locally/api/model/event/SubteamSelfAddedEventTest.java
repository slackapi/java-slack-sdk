package test_locally.api.model.event;

import com.github.seratch.jslack.api.model.event.SubteamSelfAddedEvent;
import com.google.gson.Gson;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SubteamSelfAddedEventTest {

    @Test
    public void typeName() {
        assertThat(SubteamSelfAddedEvent.TYPE_NAME, is("subteam_self_added"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"subteam_self_added\",\n" +
                "    \"subteam_id\": \"S0615G0KT\"\n" +
                "}";
        SubteamSelfAddedEvent event = GsonFactory.createSnakeCase().fromJson(json, SubteamSelfAddedEvent.class);
        assertThat(event.getType(), is("subteam_self_added"));
        assertThat(event.getSubteamId(), is("S0615G0KT"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        SubteamSelfAddedEvent event = new SubteamSelfAddedEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"subteam_self_added\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}