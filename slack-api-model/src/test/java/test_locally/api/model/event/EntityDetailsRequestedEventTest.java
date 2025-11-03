package test_locally.api.model.event;

import com.google.gson.Gson;
import com.slack.api.model.event.EntityDetailsRequestedEvent;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class EntityDetailsRequestedEventTest {

    @Test
    public void typeName() {
        assertThat(EntityDetailsRequestedEvent.TYPE_NAME, is("entity_details_requested"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" + //
                        "    type: 'entity_details_requested',\n" + //
                        "    user: 'U014KLZE350',\n" + //
                        "    trigger_id: '1285887690115.1156727510051.43015732ffe3fc75a3e8e22ad7562efe',\n" + //
                        "    user_locale: 'en-US',\n" + //
                        "    entity_url: 'https://myappdomain.com/id/123',\n" + //
                        "    external_ref: { id: 'myappdomain_incident_id_05' },\n" + //
                        "    link: { url: 'https://myappdomain.com', domain: 'myappdomain.com' },\n" + //
                        "    app_unfurl_url: 'https://myappdomain.com/id/123?myquery=param',\n" + //
                        "    channel: 'C123ABC456',\n" + //
                        "    message_ts: '1759856283.212899',\n" + //
                        "    thread_ts: '1759856283.212899',\n" + //
                        "    event_ts: '1759880869.953553'\n" + //
                        "  }";
        EntityDetailsRequestedEvent event = GsonFactory.createSnakeCase().fromJson(json, EntityDetailsRequestedEvent.class);
        assertThat(event.getType(), is("entity_details_requested"));
        assertThat(event.getAppUnfurlUrl(), is("https://myappdomain.com/id/123?myquery=param"));
        assertThat(event.getEntityUrl(), is("https://myappdomain.com/id/123"));
        assertThat(event.getMessageTs(), is("1759856283.212899"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        EntityDetailsRequestedEvent event = new EntityDetailsRequestedEvent();
        event.setEventTs("123");
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"entity_details_requested\",\"event_ts\":\"123\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}
