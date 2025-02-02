package com.slack.api.bolt.service.builtin;

import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.service.OAuthStateService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.util.stream.Collectors.joining;

/**
 * OAuthStateService implementation using local file system.
 */
public class FileOAuthStateService implements OAuthStateService {

    public static final String DEFAULT_ROOT_DIR = System.getProperty("user.home") + File.separator + ".slack-app";

    private final AppConfig config;
    private final String rootDir;

    public FileOAuthStateService(AppConfig config) throws RuntimeException {
        this(config, DEFAULT_ROOT_DIR);
    }

    public FileOAuthStateService(AppConfig config, String rootDir) throws RuntimeException {
        this.config = config;
        this.rootDir = rootDir;
    }

    @Override
    public void addNewStateToDatastore(String state) throws Exception {
        initDirectoryIfAbsent();
        Path filepath = Paths.get(getPath(state));
        String value = "" + (System.currentTimeMillis() + getExpirationInSeconds() * 1000);
        Files.write(filepath, value.getBytes());
    }

    @Override
    public boolean isAvailableInDatabase(String state) {
        Long millisToExpire = findExpirationMillisFor(state);
        return millisToExpire != null && millisToExpire > System.currentTimeMillis();
    }

    @Override
    public void deleteStateFromDatastore(String state) throws Exception {
        initDirectoryIfAbsent();
        Path filepath = Paths.get(getPath(state));
        Files.delete(filepath);
    }

    private String getBaseDir() {
        return rootDir + File.separator + config.getClientId();
    }

    private String getPath(String state) {
        return getBaseDir() + File.separator + "state" + File.separator + state;
    }

    private Long findExpirationMillisFor(String state) {
        initDirectoryIfAbsent();
        try {
            String value = Files.readAllLines(Paths.get(getPath(state))).stream().collect(joining());
            return Long.valueOf(value);
        } catch (IOException e) {
            return null;
        }
    }

    private void initDirectoryIfAbsent() {
        String dir = getBaseDir() + File.separator + "state";
        Path dirPath = Paths.get(dir);
        if (!Files.exists(dirPath)) {
            try {
                Files.createDirectories(dirPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
