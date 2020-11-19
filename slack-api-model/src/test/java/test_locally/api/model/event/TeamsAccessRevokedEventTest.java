package test_locally.api.model.event;

import com.google.gson.Gson;
import com.slack.api.model.event.TeamsAccessRevokedEvent;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TeamsAccessRevokedEventTest {

    @Test
    public void typeName() {
        assertThat(TeamsAccessRevokedEvent.TYPE_NAME, is("teams_access_revoked"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "  \"type\": \"teams_access_revoked\",\n" +
                "  \"team_ids\": [\"TX123\", \"TX234\"]\n" +
                "}";
        TeamsAccessRevokedEvent event = GsonFactory.createSnakeCase().fromJson(json, TeamsAccessRevokedEvent.class);
        assertThat(event.getType(), is("teams_access_revoked"));
        assertThat(event.getTeamIds(), is(Arrays.asList("TX123", "TX234")));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        TeamsAccessRevokedEvent event = new TeamsAccessRevokedEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"teams_access_revoked\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}
