package com.slack.api.token_rotation.tooling.store;

import com.google.gson.Gson;
import com.slack.api.SlackConfig;
import com.slack.api.token_rotation.tooling.ToolingToken;
import com.slack.api.token_rotation.tooling.ToolingTokenStore;
import com.slack.api.util.json.GsonFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import static java.util.stream.Collectors.joining;

@Slf4j
public class FileToolingTokenStore implements ToolingTokenStore {

    public static final String DEFAULT_BASE_DIR =
            System.getProperty("user.home") + File.separator + ".slack-tooling-token";

    private static final Gson GSON;

    static {
        SlackConfig config = new SlackConfig();
        config.setPrettyResponseLoggingEnabled(true);
        GSON = GsonFactory.createSnakeCase(config);
    }

    private final String baseDir;

    public FileToolingTokenStore() {
        this(DEFAULT_BASE_DIR);
    }


    private String toFilepath(String teamId, String userId) {
        return baseDir + File.separator + teamId + "-" + userId + ".json";
    }

    private String toFilepath(ToolingToken token) {
        return baseDir + File.separator + token.getTeamId() + "-" + token.getUserId() + ".json";
    }

    private static String loadFileContent(String filepath) throws IOException {
        String content = Files.readAllLines(Paths.get(filepath))
                .stream()
                .collect(joining());
        if (content == null || content.trim().isEmpty() || content.trim().equals("null")) {
            return null;
        }
        return content;
    }

    public FileToolingTokenStore(String baseDir) {
        this.baseDir = baseDir;
    }

    @Override
    public void save(ToolingToken token) {
        String path = toFilepath(token);
        String content = GSON.toJson(token);
        try {
            Files.write(Paths.get(path), content.getBytes());
        } catch (IOException e) {
            log.warn("Failed to load a config file (path: {}, content: {})", path, content, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<ToolingToken> find(String teamId, String userId) {
        try {
            String rawData = loadFileContent(toFilepath(teamId, userId));
            return Optional.ofNullable(GSON.fromJson(rawData, ToolingToken.class));
        } catch (IOException e) {
            log.warn("Failed to load a config file (team_id: {}, user_id: {})", teamId, userId, e);
            return Optional.empty();
        }
    }
}
