package test_locally.api.model.event;

import com.google.gson.Gson;
import com.slack.api.model.event.TeamAccessRevokedEvent;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TeamAccessRevokedEventTest {

    @Test
    public void typeName() {
        assertThat(TeamAccessRevokedEvent.TYPE_NAME, is("team_access_revoked"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "  \"type\": \"team_access_revoked\",\n" +
                "  \"team_ids\": [\"TX123\", \"TX234\"]\n" +
                "}";
        TeamAccessRevokedEvent event = GsonFactory.createSnakeCase().fromJson(json, TeamAccessRevokedEvent.class);
        assertThat(event.getType(), is("team_access_revoked"));
        assertThat(event.getTeamIds(), is(Arrays.asList("TX123", "TX234")));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        TeamAccessRevokedEvent event = new TeamAccessRevokedEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"team_access_revoked\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}
