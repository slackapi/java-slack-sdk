package test_locally.api.model.event;

import com.github.seratch.jslack.api.model.event.TeamMigrationStartedEvent;
import test_locally.unit.GsonFactory;
import com.google.gson.Gson;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TeamMigrationStartedEventTest {

    @Test
    public void typeName() {
        assertThat(TeamMigrationStartedEvent.TYPE_NAME, is("team_migration_started"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"team_migration_started\"\n" +
                "}";
        TeamMigrationStartedEvent event = GsonFactory.createSnakeCase().fromJson(json, TeamMigrationStartedEvent.class);
        assertThat(event.getType(), is("team_migration_started"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        TeamMigrationStartedEvent event = new TeamMigrationStartedEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"team_migration_started\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}