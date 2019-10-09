package com.github.seratch.jslack.lightning.service;

import com.github.seratch.jslack.lightning.model.Bot;
import com.github.seratch.jslack.lightning.model.Installer;

public interface InstallationService {

    void saveInstallerAndBot(Installer installer) throws Exception;

    void deleteBot(Bot bot) throws Exception;

    void deleteInstaller(Installer installer) throws Exception;

    Bot findBot(String enterpriseId, String teamId);

    Installer findInstaller(String enterpriseId, String teamId, String userId);

}
