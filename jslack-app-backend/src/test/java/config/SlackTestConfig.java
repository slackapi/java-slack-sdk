package config;

import com.github.seratch.jslack.SlackConfig;
import util.sample_json_generation.JsonDataRecordingListener;

public class SlackTestConfig {

    private SlackTestConfig() {
    }

    private static final SlackConfig CONFIG = new SlackConfig();

    static {
        CONFIG.setLibraryMaintainerMode(true);
        CONFIG.setPrettyResponseLoggingEnabled(true);
        CONFIG.getHttpClientResponseHandlers().add(new JsonDataRecordingListener());
    }

    public static SlackConfig get() {
        return CONFIG;
    }

}