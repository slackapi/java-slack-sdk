package test_locally.api.model.event;

import com.github.seratch.jslack.api.model.event.TokensRevokedEvent;
import com.google.gson.Gson;
import org.junit.Test;
import test_locally.unit.GsonFactory;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TokensRevokedEventTest {

    @Test
    public void typeName() {
        assertThat(TokensRevokedEvent.TYPE_NAME, is("tokens_revoked"));
    }

    @Test
    public void deserialize() {
        String json = "{\n" +
                "        \"type\": \"tokens_revoked\",\n" +
                "        \"tokens\": {\n" +
                "            \"oauth\": [\n" +
                "                \"UXXXXXXXX\"\n" +
                "            ],\n" +
                "            \"bot\": [\n" +
                "                \"UXXXXXXX2\"\n" +
                "            ]\n" +
                "        }\n" +
                "}";
        TokensRevokedEvent event = GsonFactory.createSnakeCase().fromJson(json, TokensRevokedEvent.class);
        assertThat(event.getType(), is("tokens_revoked"));
        assertThat(event.getTokens().getOauth(), is(Arrays.asList("UXXXXXXXX")));
        assertThat(event.getTokens().getBot(), is(Arrays.asList("UXXXXXXX2")));
    }

    @Test
    public void serialize() {
        Gson gson = GsonFactory.createSnakeCase();
        TokensRevokedEvent event = new TokensRevokedEvent();
        String generatedJson = gson.toJson(event);
        String expectedJson = "{\"type\":\"tokens_revoked\"}";
        assertThat(generatedJson, is(expectedJson));
    }

}