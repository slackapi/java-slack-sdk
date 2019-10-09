package com.github.seratch.jslack.lightning.model;

public interface Installer {

    String getEnterpriseId();

    void setEnterpriseId(String enterpriseId);

    String getTeamId();

    void setTeamId(String teamId);

    String getInstallerUserId();

    void setInstallerUserId(String userId);

    String getInstallerUserAccessToken();

    void setInstallerUserAccessToken(String userAccessToken);

    String getBotId();

    void setBotId(String botId);

    String getBotUserId();

    void setBotUserId(String botUserId);

    String getBotAccessToken();

    void setBotAccessToken(String botAccessToken);

    Bot toBot();

}
