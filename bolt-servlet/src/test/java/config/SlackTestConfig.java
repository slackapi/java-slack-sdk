package config;

import com.slack.api.SlackConfig;
import com.slack.api.methods.metrics.MetricsDatastore;
import com.slack.api.util.http.listener.HttpResponseListener;
import com.slack.api.util.json.GsonFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SlackTestConfig {

    private static final SlackConfig CONFIG = new SlackConfig();

    private final SlackConfig config;

    public MetricsDatastore getMetricsDatastore() {
        return getConfig().getMethodsConfig().getMetricsDatastore();
    }

    private SlackTestConfig(SlackConfig config) {
        this.config = config;
        CONFIG.getHttpClientResponseHandlers().add(new HttpResponseListener() {
            @Override
            public void accept(State state) {
                String json = GsonFactory.createSnakeCase(CONFIG).toJson(getMetricsDatastore().getAllStats());
                log.debug("--- (MethodsStats) ---\n" + json);
            }
        });
    }

    static {
        CONFIG.setLibraryMaintainerMode(true);
        CONFIG.setPrettyResponseLoggingEnabled(true);
    }

    public static SlackTestConfig getInstance() {
        return new SlackTestConfig(CONFIG);
    }

    public SlackConfig getConfig() {
        return config;
    }

}
