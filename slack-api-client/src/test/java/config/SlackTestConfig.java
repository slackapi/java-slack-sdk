package config;

import com.slack.api.SlackConfig;
import util.sample_json_generation.JsonDataRecordingListener;

public class SlackTestConfig {

    private SlackTestConfig() {
    }

    private static final SlackConfig CONFIG = new SlackConfig();

    static {
        CONFIG.setLibraryMaintainerMode(true);
        CONFIG.setPrettyResponseLoggingEnabled(true);
        String enabled = System.getenv(Constants.SLACK_SDK_SAMPLE_JSON_GENERATION_ENABLED);
        if (enabled != null && enabled.equals("1")) {
            CONFIG.getHttpClientResponseHandlers().add(new JsonDataRecordingListener());
        }
    }

    public static SlackConfig get() {
        return CONFIG;
    }

}
