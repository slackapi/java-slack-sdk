package com.slack.api.token_rotation.tooling;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.tooling.tokens.ToolingTokensRotateResponse;

import java.io.IOException;
import java.util.Optional;

public class ToolingTokenRotator {
    private final ToolingTokenStore store;
    private final MethodsClient client;

    public ToolingTokenRotator(ToolingTokenStore store) {
        this(store, Slack.getInstance().methods());
    }

    public ToolingTokenRotator(ToolingTokenStore store, MethodsClient client) {
        this.store = store;
        this.client = client;
    }

    public Optional<ToolingToken> find(String teamId, String userId) throws SlackApiException, IOException {
        Optional<ToolingToken> maybeToken = this.store.find(teamId, userId);
        if (maybeToken.isPresent()) {
            ToolingToken token = maybeToken.get();
            if (token.isExpired()) {
                ToolingTokensRotateResponse response = this.client.toolingTokensRotate(r -> r
                        .token(token.getAccessToken())
                        .refreshToken(token.getRefreshToken())
                );
                if (!response.isOk()) {
                    String error = "Failed to rotate a tooling token due to " + response.getError();
                    throw new IllegalStateException(error);
                }
                ToolingToken refreshed = ToolingToken.builder()
                        .accessToken(response.getToken())
                        .refreshToken(response.getRefreshToken())
                        .teamId(teamId)
                        .userId(userId)
                        .expireAt(response.getExp())
                        .build();
                this.store.save(refreshed);
                return Optional.ofNullable(refreshed);
            }
        }
        return maybeToken;
    }

    public void save(ToolingToken token) {
        this.store.save(token);
    }
}
