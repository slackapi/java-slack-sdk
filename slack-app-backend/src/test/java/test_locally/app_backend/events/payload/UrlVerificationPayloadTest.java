package test_locally.app_backend.events.payload;

import com.github.seratch.jslack.app_backend.events.payload.UrlVerificationPayload;
import com.slack.api.util.json.GsonFactory;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class UrlVerificationPayloadTest {

    @Test
    public void deserialize() {
        String json = "{\n" +
                "    \"token\": \"Jhj5dZrVaK7ZwHHjRyZWjbDl\",\n" +
                "    \"challenge\": \"3eZbrw1aBm2rZgRNFdxV2595E9CY3gmdALWMmHkvFXO7tYXAYM8P\",\n" +
                "    \"type\": \"url_verification\"\n" +
                "}";
        UrlVerificationPayload payload = GsonFactory.createSnakeCase().fromJson(json, UrlVerificationPayload.class);
        assertThat(payload.getToken(), is("Jhj5dZrVaK7ZwHHjRyZWjbDl"));
        assertThat(payload.getChallenge(), is("3eZbrw1aBm2rZgRNFdxV2595E9CY3gmdALWMmHkvFXO7tYXAYM8P"));
        assertThat(payload.getType(), is("url_verification"));
    }

}
