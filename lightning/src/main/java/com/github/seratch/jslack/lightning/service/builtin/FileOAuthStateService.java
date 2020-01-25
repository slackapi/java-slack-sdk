package com.github.seratch.jslack.lightning.service.builtin;

import com.github.seratch.jslack.lightning.service.OAuthStateService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static java.util.stream.Collectors.joining;

public class FileOAuthStateService implements OAuthStateService {

    public static final String DEFAULT_BASE_DIR = System.getProperty("user.home") + File.separator + ".jslack-lightning";
    public static final long DEFAULT_EXPIRATION_IN_MILLIS = 10 * 60 * 1000; // default 10 min

    private final String baseDir;
    private final long millisToExpire;

    public FileOAuthStateService() throws RuntimeException {
        this(DEFAULT_BASE_DIR, DEFAULT_EXPIRATION_IN_MILLIS);
    }

    public FileOAuthStateService(String baseDir, long millisToExpire) throws RuntimeException {
        this.baseDir = baseDir;
        this.millisToExpire = millisToExpire;
        initDirectoryIfAbsent();
    }

    @Override
    public String issueNewState() throws Exception {
        initDirectoryIfAbsent();
        String newState = UUID.randomUUID().toString();
        Path filepath = Paths.get(getPath(newState));
        String value = "" + (System.currentTimeMillis() + millisToExpire);
        Files.write(filepath, value.getBytes());
        return newState;
    }

    @Override
    public boolean isValid(String state) {
        Long millisToExpire = getMillisToExpire(state);
        return millisToExpire != null && millisToExpire > System.currentTimeMillis();
    }

    @Override
    public void consume(String state) throws Exception {
        initDirectoryIfAbsent();
        Path filepath = Paths.get(getPath(state));
        Files.delete(filepath);
    }

    private String getPath(String state) {
        return baseDir + File.separator + "state" + File.separator + state;
    }

    private Long getMillisToExpire(String state) {
        initDirectoryIfAbsent();
        try {
            String value = Files.readAllLines(Paths.get(getPath(state))).stream().collect(joining());
            return Long.valueOf(value);
        } catch (IOException e) {
            return null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private void initDirectoryIfAbsent() {
        String dir = baseDir + File.separator + "state";
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
