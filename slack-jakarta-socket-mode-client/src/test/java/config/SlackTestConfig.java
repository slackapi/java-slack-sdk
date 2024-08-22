package config;

import com.slack.api.SlackConfig;
import com.slack.api.rate_limits.metrics.MetricsDatastore;
import com.slack.api.util.http.listener.HttpResponseListener;
import com.slack.api.util.json.GsonFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SlackTestConfig {

    private static final SlackConfig CONFIG = new SlackConfig();

    private final SlackConfig config;

    public MetricsDatastore getMethodsMetricsDatastore() {
        return getConfig().getMethodsConfig().getMetricsDatastore();
    }

    public MetricsDatastore getAuditMetricsDatastore() {
        return getConfig().getAuditConfig().getMetricsDatastore();
    }

    private SlackTestConfig(SlackConfig config) {
        this.config = config;
        CONFIG.getHttpClientResponseHandlers().add(new HttpResponseListener() {
            @Override
            public void accept(State state) {
                String json = GsonFactory.createSnakeCase(CONFIG).toJson(getMethodsMetricsDatastore().getAllStats());
                log.debug("--- (API Methods Stats) ---\n" + json);
            }
        });
        CONFIG.getHttpClientResponseHandlers().add(new HttpResponseListener() {
            @Override
            public void accept(State state) {
                String json = GsonFactory.createSnakeCase(CONFIG).toJson(getAuditMetricsDatastore().getAllStats());
                log.debug("--- (Audit Logs Stats) ---\n" + json);
            }
        });
        CONFIG.getHttpClientResponseHandlers().add(new HttpResponseListener() {
            @Override
            public void accept(State state) {
                String json = GsonFactory.createSnakeCase(CONFIG).toJson(getAuditMetricsDatastore().getAllStats());
                log.debug("--- (Audit Logs Stats) ---\n" + json);
            }
        });
    }

    static {
        CONFIG.setLibraryMaintainerMode(true);
        CONFIG.setPrettyResponseLoggingEnabled(true);
        CONFIG.setFailOnUnknownProperties(true);
        CONFIG.setHttpClientReadTimeoutMillis(30000);
    }

    public SlackConfig getConfig() {
        return config;
    }

}
