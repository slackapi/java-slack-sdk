package com.slack.api.methods;

import com.slack.api.methods.metrics.MetricsDatastore;
import com.slack.api.methods.metrics.impl.MemoryMetricsDatastore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MethodsConfig {

    public static final String DEFAULT_SINGLETON_EXECUTOR_NAME = "DEFAULT_SINGLETON_EXECUTOR";

    public static final MethodsConfig DEFAULT_SINGLETON = new MethodsConfig() {
        void throwException() {
            throw new UnsupportedOperationException("This config is immutable");
        }

        @Override
        public void setStatsEnabled(boolean statsEnabled) {
            throwException();
        }

        @Override
        public void setExecutorName(String executorName) {
            throwException();
        }

        @Override
        public void setMaxIdleMills(int maxIdleMills) {
            throwException();
        }

        @Override
        public void setDefaultThreadPoolSize(int defaultThreadPoolSize) {
            throwException();
        }
    };

    @Builder.Default
    private boolean statsEnabled = true;
    @Builder.Default
    private String executorName = DEFAULT_SINGLETON_EXECUTOR_NAME;

    @Builder.Default
    private int maxIdleMills = 60 * 60 * 1000;

    @Builder.Default
    private int defaultThreadPoolSize = 5;

    /**
     * Team ID -> thread pool size
     */
    @Builder.Default
    private Map<String, Integer> customThreadPoolSizes = new HashMap<>();

    @Builder.Default
    private MetricsDatastore metricsDatastore = new MemoryMetricsDatastore(1);

}
