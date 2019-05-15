package test_locally.api.model.event;

import com.github.seratch.jslack.api.model.event.TeamJoinEvent;
import test_locally.unit.GsonFactory;
import com.google.gson.Gson;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class TeamJoinEventTest {

    @Test
    public void typeName() {
        assertThat(TeamJoinEvent.TYPE_NAME, is("team_join"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"team_join\",\n" +
                "    \"user\": {\n" +
                "    }\n" +
                "}\n";
        TeamJoinEvent event = GsonFactory.createSnakeCase().fromJson(json, TeamJoinEvent.class);
        assertThat(event.getType(), is("team_join"));
        assertThat(event.getUser(), is(notNullValue()));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        TeamJoinEvent event = new TeamJoinEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"team_join\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}