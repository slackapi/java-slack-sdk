package test_locally.api.model.event;

import com.github.seratch.jslack.api.model.event.SubteamSelfRemovedEvent;
import com.github.seratch.jslack.common.json.GsonFactory;
import com.google.gson.Gson;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SubteamSelfRemovedEventTest {

    @Test
    public void typeName() {
        assertThat(SubteamSelfRemovedEvent.TYPE_NAME, is("subteam_self_removed"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"subteam_self_removed\",\n" +
                "    \"subteam_id\": \"S0615G0KT\"\n" +
                "}";
        SubteamSelfRemovedEvent event = GsonFactory.createSnakeCase().fromJson(json, SubteamSelfRemovedEvent.class);
        assertThat(event.getType(), is("subteam_self_removed"));
        assertThat(event.getSubteamId(), is("S0615G0KT"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        SubteamSelfRemovedEvent event = new SubteamSelfRemovedEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"subteam_self_removed\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}