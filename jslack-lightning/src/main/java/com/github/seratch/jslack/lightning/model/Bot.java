package com.github.seratch.jslack.lightning.model;

public interface Bot {

    String getEnterpriseId();

    void setEnterpriseId(String enterpriseId);

    String getTeamId();

    void setTeamId(String teamId);

    String getBotId();

    void setBotId(String botId);

    String getBotUserId();

    void setBotUserId(String botUserId);

    String getBotAccessToken();

    void setBotAccessToken(String botAccessToken);

    Long getInstalledAt();

    void setInstalledAt(Long installedAt);

}
