package com.slack.api.bolt.model;

/**
 * Persisted bot permissions.
 */
public interface Bot {

    String getAppId();

    void setAppId(String appId);

    Boolean getIsEnterpriseInstall();

    void setIsEnterpriseInstall(Boolean isEnterpriseInstall);

    String getEnterpriseUrl();

    void setEnterpriseUrl(String enterpriseUrl);

    String getTokenType();

    void setTokenType(String tokenType);

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
