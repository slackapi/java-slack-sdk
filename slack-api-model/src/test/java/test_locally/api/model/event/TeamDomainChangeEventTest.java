package test_locally.api.model.event;

import com.google.gson.Gson;
import com.slack.api.model.event.TeamDomainChangeEvent;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TeamDomainChangeEventTest {

    @Test
    public void typeName() {
        assertThat(TeamDomainChangeEvent.TYPE_NAME, is("team_domain_change"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"team_domain_change\",\n" +
                "    \"url\": \"https://my.slack.com\",\n" +
                "    \"domain\": \"my\"\n" +
                "}";
        TeamDomainChangeEvent event = GsonFactory.createSnakeCase().fromJson(json, TeamDomainChangeEvent.class);
        assertThat(event.getType(), is("team_domain_change"));
        assertThat(event.getUrl(), is("https://my.slack.com"));
        assertThat(event.getDomain(), is("my"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        TeamDomainChangeEvent event = new TeamDomainChangeEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"team_domain_change\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}
