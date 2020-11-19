package com.slack.api.bolt.model.builtin;

import com.slack.api.bolt.model.Bot;
import com.slack.api.bolt.model.Installer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The default data class for the Installer interface.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DefaultInstaller implements Installer {

    private String appId;
    private String enterpriseId;
    private String enterpriseName;
    private String teamId;
    private String teamName;

    private Boolean isEnterpriseInstall;
    private String enterpriseUrl;
    private String tokenType;

    private String installerUserId;
    private String installerUserScope;
    private String installerUserAccessToken;

    @Deprecated
    private String scope;
    private String botScope;

    private String botId;
    private String botUserId;

    private String botAccessToken;

    private String incomingWebhookUrl;
    private String incomingWebhookChannelId;
    private String incomingWebhookConfigurationUrl;

    private Long installedAt;

    @Override
    public Bot toBot() {
        DefaultBot bot = new DefaultBot();
        bot.setAppId(appId);

        bot.setEnterpriseId(enterpriseId);
        bot.setEnterpriseName(enterpriseName);
        bot.setTeamId(teamId);
        bot.setTeamName(teamName);

        bot.setIsEnterpriseInstall(isEnterpriseInstall);
        bot.setEnterpriseUrl(enterpriseUrl);
        bot.setTokenType(tokenType);

        bot.setScope(botScope);
        bot.setBotId(botId);
        bot.setBotUserId(botUserId);
        bot.setBotAccessToken(botAccessToken);
        bot.setInstalledAt(installedAt);
        return bot;
    }
}
