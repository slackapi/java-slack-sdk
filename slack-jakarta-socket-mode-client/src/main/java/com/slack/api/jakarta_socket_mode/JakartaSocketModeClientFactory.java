package com.slack.api.jakarta_socket_mode;

import com.slack.api.Slack;
import com.slack.api.jakarta_socket_mode.impl.JakartaSocketModeClientTyrusImpl;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.apps.connections.AppsConnectionsOpenResponse;
import com.slack.api.socket_mode.SocketModeClient;

import java.io.IOException;
import java.net.URISyntaxException;

public class JakartaSocketModeClientFactory {
    private JakartaSocketModeClientFactory() {
    }

    public static SocketModeClient create(String appToken) throws IOException {
        return create(Slack.getInstance(), appToken);
    }

    public static SocketModeClient create(Slack slack, String appToken) throws IOException {
        String url = issueSocketModeUrl(slack, appToken);
        try {
            return new JakartaSocketModeClientTyrusImpl(slack, appToken, url);
        } catch (URISyntaxException e) {
            String message = "Failed to connect to the Socket Mode API endpoint. (message: " + e.getMessage() + ")";
            throw new IOException(message, e);
        }
    }

    private static String issueSocketModeUrl(Slack slack, String appToken) throws IOException {
        try {
            AppsConnectionsOpenResponse response = slack.methods().appsConnectionsOpen(r -> r.token(appToken));
            if (response.isOk()) {
                return response.getUrl();
            } else {
                String message = "Failed to connect to the Socket Mode endpoint URL (error: " + response.getError() + ")";
                throw new IllegalStateException(message);
            }
        } catch (SlackApiException e) {
            String message = "Failed to connect to the Socket Mode API endpoint. (" +
                    "status: " + e.getResponse().code() + ", " +
                    "error: " + (e.getError() != null ? e.getError().getError() : "") +
                    ")";
            throw new IOException(message, e);
        }
    }
}
