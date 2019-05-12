package test_locally.api.model.event;

import com.github.seratch.jslack.api.model.event.GridMigrationStartedEvent;
import com.github.seratch.jslack.common.json.GsonFactory;
import com.google.gson.Gson;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GridMigrationStartedEventTest {

    @Test
    public void typeName() {
        assertThat(GridMigrationStartedEvent.TYPE_NAME, is("grid_migration_started"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "        \"type\": \"grid_migration_started\",\n" +
                "        \"enterprise_id\": \"EXXXXXXXX\"\n" +
                "}\n";
        GridMigrationStartedEvent event = GsonFactory.createSnakeCase().fromJson(json, GridMigrationStartedEvent.class);
        assertThat(event.getType(), is("grid_migration_started"));
        assertThat(event.getEnterpriseId(), is("EXXXXXXXX"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        GridMigrationStartedEvent event = new GridMigrationStartedEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"grid_migration_started\"}";
        assertThat(generatedJson, is(expectedJson));
    }
}