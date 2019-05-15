package test_locally.api.model.event;

import com.github.seratch.jslack.api.model.event.TeamPlanChangeEvent;
import test_locally.unit.GsonFactory;
import com.google.gson.Gson;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TeamPlanChangeEventTest {

    @Test
    public void typeName() {
        assertThat(TeamPlanChangeEvent.TYPE_NAME, is("team_plan_change"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"team_plan_change\",\n" +
                "    \"plan\": \"std\",\n" +
                "    \"can_add_ura\": false,\n" +
                "    \"paid_features\": [\"feature1\", \"feature2\"]\n" +
                "}";
        TeamPlanChangeEvent event = GsonFactory.createSnakeCase().fromJson(json, TeamPlanChangeEvent.class);
        assertThat(event.getType(), is("team_plan_change"));
        assertThat(event.getPlan(), is("std"));
        assertThat(event.isCanAddUra(), is(false));
        assertThat(event.getPaidFeatures(), is(Arrays.asList("feature1", "feature2")));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        TeamPlanChangeEvent event = new TeamPlanChangeEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"team_plan_change\",\"can_add_ura\":false}";
        assertThat(generatedJson, is(expectedJson));
    }

}