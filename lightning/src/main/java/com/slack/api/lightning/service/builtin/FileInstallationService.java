package com.slack.api.lightning.service.builtin;

import com.slack.api.lightning.AppConfig;
import com.slack.api.lightning.model.Bot;
import com.slack.api.lightning.model.Installer;
import com.slack.api.lightning.model.builtin.DefaultBot;
import com.slack.api.lightning.model.builtin.DefaultInstaller;
import com.slack.api.lightning.service.InstallationService;
import com.slack.api.lightning.util.JsonOps;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.util.stream.Collectors.joining;

@Slf4j
public class FileInstallationService implements InstallationService {

    public static final String DEFAULT_ROOT_DIR = System.getProperty("user.home") + File.separator + ".slack-app";

    private final AppConfig config;
    private final String rootDir;
    private boolean historicalDataEnabled;

    public FileInstallationService(AppConfig config) {
        this(config, DEFAULT_ROOT_DIR);
    }

    public FileInstallationService(AppConfig config, String rootDir) {
        this.config = config;
        this.rootDir = rootDir;
    }

    @Override
    public boolean isHistoricalDataEnabled() {
        return historicalDataEnabled;
    }

    @Override
    public void setHistoricalDataEnabled(boolean isHistoricalDataEnabled) {
        this.historicalDataEnabled = isHistoricalDataEnabled;
    }

    @Override
    public void saveInstallerAndBot(Installer installer) throws Exception {
        save(getInstallerPath(installer),
                installer.getInstalledAt(),
                JsonOps.toJsonString(installer));
        save(getBotPath(installer.getEnterpriseId(), installer.getTeamId()),
                installer.getInstalledAt(),
                JsonOps.toJsonString(installer.toBot()));
    }

    @Override
    public void deleteBot(Bot bot) throws Exception {
        Files.deleteIfExists(Paths.get(getBotPath(bot.getEnterpriseId(), bot.getTeamId())));
    }

    @Override
    public void deleteInstaller(Installer installer) throws Exception {
        Files.deleteIfExists(Paths.get(getInstallerPath(installer)));
        Files.deleteIfExists(Paths.get(getBotPath(installer.getEnterpriseId(), installer.getTeamId())));
    }

    @Override
    public Bot findBot(String enterpriseId, String teamId) {
        try {
            String json = loadFileContent(getBotPath(enterpriseId, teamId));
            if (json == null && enterpriseId != null) {
                json = loadFileContent(getBotPath(null, teamId));
                if (json != null) {
                    Bot bot = JsonOps.fromJson(json, DefaultBot.class);
                    bot.setEnterpriseId(enterpriseId);
                    save(getBotPath(enterpriseId, teamId), bot.getInstalledAt(), JsonOps.toJsonString(bot));
                    return bot;
                }
            }
            if (json != null) {
                return JsonOps.fromJson(json, DefaultBot.class);
            } else {
                return null;
            }
        } catch (IOException e) {
            log.warn("Failed to load a bot user (enterprise_id: {}, team_id: {})", enterpriseId, teamId);
            return null;
        }
    }

    @Override
    public Installer findInstaller(String enterpriseId, String teamId, String userId) {
        try {
            String json = loadFileContent(getInstallerPath(enterpriseId, teamId, userId));
            if (json == null && enterpriseId != null) {
                json = loadFileContent(getInstallerPath(null, teamId, userId));
                if (json != null) {
                    Installer i = JsonOps.fromJson(json, DefaultInstaller.class);
                    i.setEnterpriseId(enterpriseId);
                    save(getInstallerPath(enterpriseId, teamId, userId), i.getInstalledAt(), JsonOps.toJsonString(i));
                    return i;
                }
            }
            if (json != null) {
                return JsonOps.fromJson(json, DefaultInstaller.class);
            } else {
                return null;
            }
        } catch (IOException e) {
            log.warn("Failed to load an installer user (enterprise_id: {}, team_id: {})", enterpriseId, teamId);
            return null;
        }
    }

    private String getInstallerPath(Installer i) throws IOException {
        return getInstallerPath(i.getEnterpriseId(), i.getTeamId(), i.getInstallerUserId());
    }

    private String getInstallerPath(String enterpriseId, String teamId, String userId) throws IOException {
        String dir = getBaseDir() + File.separator + "installer";
        Path dirPath = Paths.get(dir);
        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
        }
        String key = ((enterpriseId == null) ? "none" : enterpriseId) + "-" + teamId + "-" + userId;
        if (isHistoricalDataEnabled()) {
            key = key + "-latest";
        }
        return dir + File.separator + key;
    }

    private String getBotPath(String enterpriseId, String teamId) throws IOException {
        String dir = getBaseDir() + File.separator + "bot";
        Path dirPath = Paths.get(dir);
        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
        }
        String key = ((enterpriseId == null) ? "none" : enterpriseId) + "-" + teamId;
        if (isHistoricalDataEnabled()) {
            key = key + "-latest";
        }
        return dir + File.separator + key;
    }

    private String getBaseDir() {
        return rootDir + File.separator + config.getClientId() + File.separator + "installation";
    }

    private void save(String path, Long installedAt, String json) throws IOException {
        // latest
        Files.write(Paths.get(path), json.getBytes());
        if (isHistoricalDataEnabled()) {
            // the historical data
            Files.write(Paths.get(path.replaceFirst("-latest$", "-" + installedAt)), json.getBytes());
        }
    }

    private String loadFileContent(String filepath) throws IOException {
        return Files.readAllLines(Paths.get(filepath))
                .stream()
                .collect(joining());
    }

}
