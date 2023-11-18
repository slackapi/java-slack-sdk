package com.slack.api.app_backend.events.payload;

public interface AuthenticationProperties {
    String getToken();

    void setToken(String token);

    String getTeamId();

    void setTeamId(String teamId);

    String getApiAppId();

    void setApiAppId(String apiAppId);
}
