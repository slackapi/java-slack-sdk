package com.github.seratch.jslack.app_backend.util;

import com.github.seratch.jslack.app_backend.interactive_messages.payload.AttachmentActionPayload;
import com.github.seratch.jslack.app_backend.interactive_messages.payload.BlockActionPayload;
import com.github.seratch.jslack.app_backend.slash_commands.payload.SlashCommandPayload;

public class RequestTokenVerifier {

    // https://api.slack.com/apps/{apiAppId}
    // App Credentials > Verification Token
    public static final String ENV_VARIABLE_NAME = "SLACK_VERIFICATION_TOKEN";

    private final String verificationToken;

    public RequestTokenVerifier() {
        this.verificationToken = System.getenv(RequestTokenVerifier.ENV_VARIABLE_NAME);
    }

    public RequestTokenVerifier(String verificationToken) {
        this.verificationToken = verificationToken;
    }

    public boolean isValid(AttachmentActionPayload payload) {
        return payload != null && isValid(payload.getToken());
    }

    public boolean isValid(BlockActionPayload payload) {
        return payload != null && isValid(payload.getToken());
    }

    public boolean isValid(SlashCommandPayload payload) {
        return payload != null && isValid(payload.getToken());
    }

    public boolean isValid(String actualToken) {
        if (actualToken == null) {
            return false;
        }
        return actualToken.equals(this.verificationToken);
    }
}
