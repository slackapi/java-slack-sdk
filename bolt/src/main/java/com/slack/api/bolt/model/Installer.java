package com.slack.api.bolt.model;

/**
 * Persisted installer(Slack user)'s permissions.
 */
public interface Installer {

    // ---------------------------------
    // App
    // ---------------------------------

    String getAppId();

    void setAppId(String appId);

    // ---------------------------------
    // Organization / Workspace
    // ---------------------------------

    String getEnterpriseId();

    void setEnterpriseId(String enterpriseId);

    String getTeamId();

    void setTeamId(String teamId);

    // ---------------------------------
    // Org-level installation
    // ---------------------------------

    Boolean getIsEnterpriseInstall();

    void setIsEnterpriseInstall(Boolean isEnterpriseInstall);

    String getEnterpriseUrl();

    void setEnterpriseUrl(String enterpriseUrl);

    String getTokenType();

    void setTokenType(String tokenType);

    // ---------------------------------
    // Installer
    // ---------------------------------

    String getInstallerUserId();

    void setInstallerUserId(String userId);

    String getInstallerUserScope();

    void setInstallerUserScope(String scope);

    String getInstallerUserAccessToken();

    void setInstallerUserAccessToken(String userAccessToken);

    // ---------------------------------
    // Bot
    // ---------------------------------

    String getBotId();

    void setBotId(String botId);

    String getBotUserId();

    void setBotUserId(String botUserId);

    String getBotScope();

    void setBotScope(String scope);

    String getBotAccessToken();

    void setBotAccessToken(String botAccessToken);

    Bot toBot();

    // ---------------------------------
    // Incoming Webhooks
    // ---------------------------------

    String getIncomingWebhookUrl();

    void setIncomingWebhookUrl(String incomingWebhookUrl);

    String getIncomingWebhookChannelId();

    void setIncomingWebhookChannelId(String incomingWebhookChannelId);

    String getIncomingWebhookConfigurationUrl();

    void setIncomingWebhookConfigurationUrl(String incomingWebhookConfigurationUrl);

    Long getInstalledAt();

    void setInstalledAt(Long installedAt);

}
