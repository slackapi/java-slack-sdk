package test_locally.api.model.event;

import com.google.gson.Gson;
import com.slack.api.model.event.TeamAccessGrantedEvent;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TeamAccessGrantedEventTest {

    @Test
    public void typeName() {
        assertThat(TeamAccessGrantedEvent.TYPE_NAME, is("team_access_granted"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "  \"type\": \"team_access_granted\",\n" +
                "  \"team_ids\": [\"T1XX3\", \"TXX34\"]\n" +
                "}";
        TeamAccessGrantedEvent event = GsonFactory.createSnakeCase().fromJson(json, TeamAccessGrantedEvent.class);
        assertThat(event.getType(), is("team_access_granted"));
        assertThat(event.getTeamIds(), is(Arrays.asList("T1XX3", "TXX34")));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        TeamAccessGrantedEvent event = new TeamAccessGrantedEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"team_access_granted\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}
