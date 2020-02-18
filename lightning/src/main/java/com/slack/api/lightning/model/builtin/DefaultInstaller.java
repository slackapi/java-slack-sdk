package com.slack.api.lightning.model.builtin;

import com.slack.api.lightning.model.Bot;
import com.slack.api.lightning.model.Installer;
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

    private String enterpriseId;
    private String teamId;
    private String teamName;

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
        bot.setEnterpriseId(enterpriseId);
        bot.setTeamId(teamId);
        bot.setTeamName(teamName);
        bot.setScope(botScope);
        bot.setBotId(botId);
        bot.setBotUserId(botUserId);
        bot.setBotAccessToken(botAccessToken);
        bot.setInstalledAt(installedAt);
        return bot;
    }
}
