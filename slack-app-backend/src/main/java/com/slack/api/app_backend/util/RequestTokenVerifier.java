package com.slack.api.app_backend.util;

import com.slack.api.app_backend.interactive_components.payload.AttachmentActionPayload;
import com.slack.api.app_backend.interactive_components.payload.BlockActionPayload;
import com.slack.api.app_backend.outgoing_webhooks.payload.WebhookPayload;
import com.slack.api.app_backend.slash_commands.payload.SlashCommandPayload;

// Use SlackSignature instead
@Deprecated
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

    public boolean isValid(WebhookPayload payload) {
        return payload != null && isValid(payload.getToken());
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
