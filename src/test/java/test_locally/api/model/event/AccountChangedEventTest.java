package test_locally.api.model.event;

import com.github.seratch.jslack.api.model.event.AccountChangedEvent;
import com.github.seratch.jslack.common.json.GsonFactory;
import com.google.gson.Gson;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

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