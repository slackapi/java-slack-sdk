package com.github.seratch.jslack.app_backend.util;

import com.github.seratch.jslack.app_backend.slash_commands.payload.SlashCommandPayload;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class RequestTokenVerifierTest {

    RequestTokenVerifier validator = new RequestTokenVerifier("expected");

    @Test
    public void slashCommandPayload() {
        {
            assertThat(validator.isValid((String) null), is(false));
        }

        {
            SlashCommandPayload payload = new SlashCommandPayload();
            assertThat(validator.isValid(payload), is(false));
        }

        {
            SlashCommandPayload payload = new SlashCommandPayload();
            payload.setToken("expected");
            assertThat(validator.isValid(payload), is(true));
        }

        {
            SlashCommandPayload payload = new SlashCommandPayload();
            payload.setToken("expected ");
            assertThat(validator.isValid(payload), is(false));
        }
    }
}