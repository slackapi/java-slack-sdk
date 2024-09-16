package config;

import com.slack.api.SlackConfig;
import com.slack.api.methods.metrics.RedisMetricsDatastore;
import com.slack.api.rate_limits.metrics.MetricsDatastore;
import com.slack.api.util.http.listener.HttpResponseListener;
import com.slack.api.util.json.GsonFactory;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.JedisPool;
import util.sample_json_generation.JsonDataRecordingListener;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

@Slf4j
public class SlackTestConfig {

    private static final JsonDataRecordingListener JSON_DATA_RECORDING_LISTENER = new JsonDataRecordingListener();
    private static final SlackConfig CONFIG = new SlackConfig();

    public boolean areAllAsyncOperationsDone() {
        return JSON_DATA_RECORDING_LISTENER.isAllDone();
    }

    public int getRemainingBackgroundJobCount() {
        return JSON_DATA_RECORDING_LISTENER.getRemainingBackgroundJobCount();
    }

    public void clearRemainingBackgroundJobCount() {
        JSON_DATA_RECORDING_LISTENER.clearRemainingBackgroundJobCount();
    }

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

        // Testing with Redis
        String redisEnabled = System.getenv(Constants.SLACK_SDK_TEST_REDIS_ENABLED);
        if (redisEnabled != null && redisEnabled.equals("1")) {
            // brew install redis
            // redis-server /usr/local/etc/redis.conf --loglevel verbose
            JedisPool jedis = new JedisPool("127.0.0.1");
            CONFIG.getMethodsConfig().setMetricsDatastore(new RedisMetricsDatastore("test", jedis));
        }
    }

    static {
        CONFIG.setLibraryMaintainerMode(true);
        CONFIG.setPrettyResponseLoggingEnabled(true);
        CONFIG.setFailOnUnknownProperties(true);
        CONFIG.getHttpClientResponseHandlers().add(JSON_DATA_RECORDING_LISTENER);
        CONFIG.setHttpClientReadTimeoutMillis(30000);
    }

    public static SlackTestConfig getInstance() {
        return new SlackTestConfig(CONFIG);
    }

    public SlackConfig getConfig() {
        return config;
    }

    public static void initializeRawJSONDataFiles(String prefix) throws Exception {
        String baseDir = "../json-logs/raw/api/";
        String glob = "glob:" + baseDir + prefix;
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher(glob);
        Path baseDirPath = Paths.get(baseDir);
        Files.createDirectories(baseDirPath);
        Files.walkFileTree(baseDirPath, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (matcher.matches(file)) {
                    Files.delete(file);
                }
                return FileVisitResult.CONTINUE;
            }
        });
    }

    public static void awaitCompletion(SlackTestConfig testConfig) throws InterruptedException {
        int seconds = 0;
        while (!testConfig.areAllAsyncOperationsDone()) {
            Thread.sleep(1000);
            seconds += 1;

            if (seconds > 30) {
                String error = "The background processes did not complete within " + seconds + " seconds " +
                        "(remaining: " + testConfig.getRemainingBackgroundJobCount()  + ")";
                log.error(error);
                testConfig.clearRemainingBackgroundJobCount();
                throw new IllegalStateException(error);
            }
        }
    }

}
