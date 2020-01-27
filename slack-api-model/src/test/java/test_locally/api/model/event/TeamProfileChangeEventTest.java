package test_locally.api.model.event;

import com.google.gson.Gson;
import com.slack.api.model.event.TeamProfileChangeEvent;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class TeamProfileChangeEventTest {

    @Test
    public void typeName() {
        assertThat(TeamProfileChangeEvent.TYPE_NAME, is("team_profile_change"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"team_profile_change\",\n" +
                "    \"profile\": {\n" +
                "        \"fields\": [\n" +
                "            {\n" +
                "                \"id\": \"Xf06054AAA\"\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}\n";
        TeamProfileChangeEvent event = GsonFactory.createSnakeCase().fromJson(json, TeamProfileChangeEvent.class);
        assertThat(event.getType(), is("team_profile_change"));
        assertThat(event.getProfile(), is(notNullValue()));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        TeamProfileChangeEvent event = new TeamProfileChangeEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"team_profile_change\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}
