package com.github.seratch.jslack.app_backend.events.payload;

import com.github.seratch.jslack.common.json.GsonFactory;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

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