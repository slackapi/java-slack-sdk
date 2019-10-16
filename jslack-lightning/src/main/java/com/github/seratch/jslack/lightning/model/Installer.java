package com.github.seratch.jslack.lightning.model;

public interface Installer {

    // ---------------------------------
    // Organization / Workspace
    // ---------------------------------

    String getEnterpriseId();

    void setEnterpriseId(String enterpriseId);

    String getTeamId();

    void setTeamId(String teamId);

    // ---------------------------------
    // Installer
    // ---------------------------------

    String getInstallerUserId();

    void setInstallerUserId(String userId);

    String getInstallerUserAccessToken();

    void setInstallerUserAccessToken(String userAccessToken);

    // ---------------------------------
    // Bot
    // ---------------------------------

    String getBotId();

    void setBotId(String botId);

    String getBotUserId();

    void setBotUserId(String botUserId);

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
