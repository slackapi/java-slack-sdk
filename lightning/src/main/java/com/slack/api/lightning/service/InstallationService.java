package com.slack.api.lightning.service;

import com.slack.api.lightning.model.Bot;
import com.slack.api.lightning.model.Installer;

public interface InstallationService {

    boolean isHistoricalDataEnabled();

    void setHistoricalDataEnabled(boolean isHistoricalDataEnabled);

    void saveInstallerAndBot(Installer installer) throws Exception;

    void deleteBot(Bot bot) throws Exception;

    void deleteInstaller(Installer installer) throws Exception;

    Bot findBot(String enterpriseId, String teamId);

    Installer findInstaller(String enterpriseId, String teamId, String userId);

}
