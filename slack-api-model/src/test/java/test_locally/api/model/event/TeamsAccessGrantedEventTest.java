package test_locally.api.model.event;

import com.google.gson.Gson;
import com.slack.api.model.event.TeamsAccessGrantedEvent;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TeamsAccessGrantedEventTest {

    @Test
    public void typeName() {
        assertThat(TeamsAccessGrantedEvent.TYPE_NAME, is("teams_access_granted"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "  \"type\": \"teams_access_granted\",\n" +
                "  \"team_ids\": [\"T1XX3\", \"TXX34\"]\n" +
                "}";
        TeamsAccessGrantedEvent event = GsonFactory.createSnakeCase().fromJson(json, TeamsAccessGrantedEvent.class);
        assertThat(event.getType(), is("teams_access_granted"));
        assertThat(event.getTeamIds(), is(Arrays.asList("T1XX3", "TXX34")));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        TeamsAccessGrantedEvent event = new TeamsAccessGrantedEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"teams_access_granted\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}
