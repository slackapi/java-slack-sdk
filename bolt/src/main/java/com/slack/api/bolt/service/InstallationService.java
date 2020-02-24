package com.slack.api.bolt.service;

import com.slack.api.bolt.Initializer;
import com.slack.api.bolt.model.Bot;
import com.slack.api.bolt.model.Installer;
import com.slack.api.model.block.LayoutBlock;

import java.util.List;

/**
 * A service that manages Slack app installations.
 */
public interface InstallationService extends Service {

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

    /**
     * Returns a message text to inform unknown users.
     */
    default String getInstallationGuideText(String enterpriseId, String teamId, String userId) {
        return "This app was not able to respond to your action. Please install this Slack app :bow:";
    }

    /**
     * Returns a message (Block Kit) to inform unknown users.
     */
    default List<LayoutBlock> getInstallationGuideBlocks(String enterpriseId, String teamId, String userId) {
        return null;
    }

}
