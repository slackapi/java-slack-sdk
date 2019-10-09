package com.github.seratch.jslack.lightning.model.builtin;

import com.github.seratch.jslack.lightning.model.Bot;
import com.github.seratch.jslack.lightning.model.Installer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DefaultInstaller implements Installer {

    private String enterpriseId;
    private String teamId;
    private String teamName;

    private String installerUserId;
    private String installerUserAccessToken;

    private String scope;

    private String botId;
    private String botUserId;
    private String botAccessToken;

    private Long installedAt;

    @Override
    public Bot toBot() {
        DefaultBot bot = new DefaultBot();
        bot.setEnterpriseId(enterpriseId);
        bot.setTeamId(teamId);
        bot.setTeamName(teamName);
        bot.setScope(scope);
        bot.setBotId(botId);
        bot.setBotUserId(botUserId);
        bot.setBotAccessToken(botAccessToken);
        bot.setInstalledAt(installedAt);
        return bot;
    }
}
