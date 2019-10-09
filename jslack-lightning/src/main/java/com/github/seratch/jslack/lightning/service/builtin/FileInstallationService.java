package com.github.seratch.jslack.lightning.service.builtin;

import com.github.seratch.jslack.lightning.model.Bot;
import com.github.seratch.jslack.lightning.model.Installer;
import com.github.seratch.jslack.lightning.model.builtin.DefaultBot;
import com.github.seratch.jslack.lightning.model.builtin.DefaultInstaller;
import com.github.seratch.jslack.lightning.service.InstallationService;
import com.github.seratch.jslack.lightning.util.JsonOps;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.util.stream.Collectors.joining;

@Slf4j
public class FileInstallationService implements InstallationService {

    public static final String DEFAULT_BASE_DIR = System.getProperty("user.home") + File.separator + ".jslack-lightning";

    private final String baseDir;

    public FileInstallationService() {
        this(DEFAULT_BASE_DIR);
    }

    public FileInstallationService(String baseDir) {
        this.baseDir = baseDir;
    }

    @Override
    public void saveInstallerAndBot(Installer installer) throws Exception {
        write(getInstallerPath(installer), JsonOps.toJsonString(installer));
        write(getBotPath(installer.getEnterpriseId(), installer.getTeamId()), JsonOps.toJsonString(installer.toBot()));
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
                    write(getBotPath(enterpriseId, teamId), JsonOps.toJsonString(bot));
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
                    write(getInstallerPath(enterpriseId, teamId, userId), JsonOps.toJsonString(i));
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
        return dir + File.separator + key;
    }

    private String getBotPath(String enterpriseId, String teamId) throws IOException {
        String dir = getBaseDir() + File.separator + "bot";
        Path dirPath = Paths.get(dir);
        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
        }
        String key = ((enterpriseId == null) ? "none" : enterpriseId) + "-" + teamId;
        return dir + File.separator + key;
    }

    private String getBaseDir() {
        return baseDir + File.separator + "installation";
    }

    private void write(String path, String json) throws IOException {
        Files.write(Paths.get(path), json.getBytes());
    }

    private String loadFileContent(String filepath) throws IOException {
        return Files.readAllLines(Paths.get(filepath))
                .stream()
                .collect(joining());
    }

}
