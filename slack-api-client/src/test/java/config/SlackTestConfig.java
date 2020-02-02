package config;

import com.slack.api.SlackConfig;
import com.slack.api.methods.MethodsStats;
import com.slack.api.util.http.listener.HttpResponseListener;
import com.slack.api.util.json.GsonFactory;
import lombok.extern.slf4j.Slf4j;
import util.sample_json_generation.JsonDataRecordingListener;

@Slf4j
public class SlackTestConfig {

    private static final JsonDataRecordingListener JSON_DATA_RECORDING_LISTENER = new JsonDataRecordingListener();
    private static final SlackConfig CONFIG = new SlackConfig();

    public boolean areAllAsyncOperationsDone() {
        return JSON_DATA_RECORDING_LISTENER.isAllDone();
    }

    private final SlackConfig config;

    private SlackTestConfig(SlackConfig config) {
        this.config = config;
    }

    static {
        CONFIG.setLibraryMaintainerMode(true);
        CONFIG.setPrettyResponseLoggingEnabled(true);
        CONFIG.getHttpClientResponseHandlers().add(JSON_DATA_RECORDING_LISTENER);
        CONFIG.getHttpClientResponseHandlers().add(new HttpResponseListener() {
            @Override
            public void accept(State state) {
                log.info("--- (MethodsStats) ---\n" + GsonFactory.createSnakeCase(CONFIG).toJson(MethodsStats.getAllStats()));
            }
        });
    }

    public static SlackTestConfig getInstance() {
        return new SlackTestConfig(CONFIG);
    }

    public SlackConfig getConfig() {
        return config;
    }

    public static void awaitCompletion(SlackTestConfig testConfig) throws InterruptedException {
        while (!testConfig.areAllAsyncOperationsDone()) {
            Thread.sleep(1000);
        }
    }

}
