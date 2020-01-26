package test_locally.api.model.event;

import com.google.gson.Gson;
import com.slack.api.model.event.EmailDomainChangedEvent;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class EmailDomainChangedEventTest {

    @Test
    public void typeName() {
        assertThat(EmailDomainChangedEvent.TYPE_NAME, is("email_domain_changed"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"email_domain_changed\",\n" +
                "    \"email_domain\":\"example.com\",\n" +
                "    \"event_ts\": \"1360782804.083113\"\n" +
                "}\n";
        EmailDomainChangedEvent event = GsonFactory.createSnakeCase().fromJson(json, EmailDomainChangedEvent.class);
        assertThat(event.getType(), is("email_domain_changed"));
        assertThat(event.getEmailDomain(), is("example.com"));
        assertThat(event.getEventTs(), is("1360782804.083113"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        EmailDomainChangedEvent event = new EmailDomainChangedEvent();
        event.setEventTs("123");
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"email_domain_changed\",\"event_ts\":\"123\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}
