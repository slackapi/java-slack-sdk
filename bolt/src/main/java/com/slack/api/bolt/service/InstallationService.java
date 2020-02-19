package com.slack.api.bolt.service;

import com.slack.api.bolt.model.Bot;
import com.slack.api.bolt.model.Installer;

/**
 * A service that manages Slack app installations.
 */
public interface InstallationService {

    /**
     * Returns true if the historical data management is enabled.
     */
    boolean isHistoricalDataEnabled();

    /**
     * Set true if the historical data management is enabled.
     */
    void setHistoricalDataEnabled(boolean isHistoricalDataEnabled);

    /**
     * Saves an installation.
     */
    void saveInstallerAndBot(Installer installer) throws Exception;

    /**
     * Deletes a bot permission data only.
     */
    void deleteBot(Bot bot) throws Exception;

    /**
     * Deletes a user permission data only.
     */
    void deleteInstaller(Installer installer) throws Exception;

    /**
     * Returns a bot permission data if exists.
     */
    Bot findBot(String enterpriseId, String teamId);

    /**
     * Returns a user permission data if exists.
     */
    Installer findInstaller(String enterpriseId, String teamId, String userId);

}
