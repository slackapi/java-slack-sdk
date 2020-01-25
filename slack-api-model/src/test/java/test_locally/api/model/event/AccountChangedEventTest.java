package test_locally.api.model.event;

import com.slack.api.model.event.AccountChangedEvent;
import com.google.gson.Gson;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class AccountChangedEventTest {

    @Test
    public void typeName() {
        assertThat(AccountChangedEvent.TYPE_NAME, is("accounts_changed"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"type\": \"accounts_changed\"\n" +
                "}";
        AccountChangedEvent event = GsonFactory.createSnakeCase().fromJson(json, AccountChangedEvent.class);
        assertThat(event.getType(), is("accounts_changed"));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        AccountChangedEvent event = new AccountChangedEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"accounts_changed\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}
