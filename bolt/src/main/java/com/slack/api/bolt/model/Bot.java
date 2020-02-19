package com.slack.api.bolt.model;

/**
 * Persisted bot permissions.
 */
public interface Bot {

    String getEnterpriseId();

    void setEnterpriseId(String enterpriseId);

    String getTeamId();

    void setTeamId(String teamId);

    String getBotId();

    void setBotId(String botId);

    String getBotUserId();

    void setBotUserId(String botUserId);

    String getBotScope();

    void setBotScope(String scope);

    String getBotAccessToken();

    void setBotAccessToken(String botAccessToken);

    Long getInstalledAt();

    void setInstalledAt(Long installedAt);

}
