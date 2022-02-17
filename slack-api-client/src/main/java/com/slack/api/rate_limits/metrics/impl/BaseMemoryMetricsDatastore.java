package com.slack.api.rate_limits.metrics.impl;

import com.google.gson.Gson;
import com.slack.api.rate_limits.RateLimiter;
import com.slack.api.rate_limits.metrics.LastMinuteRequests;
import com.slack.api.rate_limits.metrics.LiveRequestStats;
import com.slack.api.rate_limits.metrics.MetricsDatastore;
import com.slack.api.rate_limits.metrics.RequestStats;
import com.slack.api.rate_limits.queue.QueueMessage;
import com.slack.api.rate_limits.queue.RateLimitQueue;
import com.slack.api.rate_limits.queue.WaitingMessageIds;
import com.slack.api.util.json.GsonFactory;
import com.slack.api.util.thread.DaemonThreadExecutorServiceProvider;
import com.slack.api.util.thread.ExecutorServiceProvider;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

public abstract class BaseMemoryMetricsDatastore<SUPPLIER, MSG extends QueueMessage>
        implements MetricsDatastore, AutoCloseable {

    private final int numberOfNodes;
    private ExecutorServiceProvider executorServiceProvider;
    private ScheduledExecutorService rateLimiterBackgroundJob;
    private boolean traceMode;
    private boolean statsEnabled;
    private long rateLimiterBackgroundJobIntervalMillis;

    public BaseMemoryMetricsDatastore(int numberOfNodes) {
        this(numberOfNodes, DaemonThreadExecutorServiceProvider.getInstance());
    }

    public BaseMemoryMetricsDatastore(int numberOfNodes, ExecutorServiceProvider executorServiceProvider) {
        this(
                numberOfNodes,
                executorServiceProvider,
                true,
                RateLimiter.DEFAULT_BACKGROUND_JOB_INTERVAL_MILLIS
        );
    }

    public BaseMemoryMetricsDatastore(
            int numberOfNodes,
            ExecutorServiceProvider executorServiceProvider,
            boolean statsEnabled,
            long rateLimiterBackgroundJobIntervalMillis
    ) {
        this.numberOfNodes = numberOfNodes;
        this.setStatsEnabled(statsEnabled);
        // intentionally avoiding to call setters to run the initialization only once
        this.executorServiceProvider = executorServiceProvider;
        this.rateLimiterBackgroundJobIntervalMillis = rateLimiterBackgroundJobIntervalMillis;
        if (this.isStatsEnabled()) {
            this.initializeRateLimiterBackgroundJob();
        }
    }

    protected void initializeRateLimiterBackgroundJob() {
        if (!this.isStatsEnabled()) {
            if (this.rateLimiterBackgroundJob != null) {
                // Abandon the running one first
                this.rateLimiterBackgroundJob.shutdown();
            }
            this.rateLimiterBackgroundJob = null;
            return;
        }
        if (this.rateLimiterBackgroundJob != null) {
            // Abandon the running one first
            this.rateLimiterBackgroundJob.shutdown();
        }
        this.rateLimiterBackgroundJob = getExecutorServiceProvider().createThreadScheduledExecutor(getThreadGroupName());
        this.rateLimiterBackgroundJob.scheduleAtFixedRate(
                new MaintenanceJob(this),
                1000,
                this.rateLimiterBackgroundJobIntervalMillis,
                TimeUnit.MILLISECONDS
        );
    }

    @Override
    public void close() throws Exception {
        rateLimiterBackgroundJob.shutdown();
    }

    protected abstract String getMetricsType();

    public String getThreadGroupName() {
        return "slack-api-metrics:" + Integer.toHexString(hashCode());
    }

    // -----------------------------------------------------------

    private static final Gson GSON = GsonFactory.createSnakeCase();

    @Override
    public int getNumberOfNodes() {
        return this.numberOfNodes;
    }

    @Override
    public Map<String, Map<String, RequestStats>> getAllStats() {
        Map<String, Map<String, RequestStats>> result = new HashMap<>();
        for (Map.Entry<String, ConcurrentMap<String, LiveRequestStats>> executor : ALL_LIVE_STATS.entrySet()) {
            Map<String, RequestStats> allTeams = new HashMap<>();
            for (Map.Entry<String, LiveRequestStats> team : executor.getValue().entrySet()) {
                RequestStats stats = GSON.fromJson(GSON.toJson(team.getValue()), RequestStats.class);
                allTeams.put(team.getKey(), stats);
            }
            result.put(executor.getKey(), allTeams);
        }
        return result;
    }

    @Override
    public RequestStats getStats(String executorName, String teamId) {
        LiveRequestStats internal = getOrCreateTeamLiveStats(executorName, teamId);
        RequestStats stats = GSON.fromJson(GSON.toJson(internal), RequestStats.class);
        return stats;
    }

    @Override
    public ExecutorServiceProvider getExecutorServiceProvider() {
        return this.executorServiceProvider;
    }

    @Override
    public void setExecutorServiceProvider(ExecutorServiceProvider executorServiceProvider) {
        this.executorServiceProvider = executorServiceProvider;
        if (this.isStatsEnabled()) {
            initializeRateLimiterBackgroundJob();
        }
    }

    @Override
    public boolean isTraceMode() {
        return this.traceMode;
    }

    @Override
    public void setTraceMode(boolean traceMode) {
        this.traceMode = traceMode;
    }

    @Override
    public boolean isStatsEnabled() {
        return this.statsEnabled;
    }

    @Override
    public void setStatsEnabled(boolean statsEnabled) {
        this.statsEnabled = statsEnabled;
        if (!this.statsEnabled && this.rateLimiterBackgroundJob != null) {
            this.rateLimiterBackgroundJob.shutdown();
            this.rateLimiterBackgroundJob = null;
        }
    }

    @Override
    public long getRateLimiterBackgroundJobIntervalMillis() {
        return this.rateLimiterBackgroundJobIntervalMillis;
    }

    @Override
    public void setRateLimiterBackgroundJobIntervalMillis(long rateLimiterBackgroundJobIntervalMillis) {
        this.rateLimiterBackgroundJobIntervalMillis = rateLimiterBackgroundJobIntervalMillis;
        if (this.isStatsEnabled()) {
            initializeRateLimiterBackgroundJob();
        }
    }

    // -----------------------------------------------------------

    @Override
    public void incrementAllCompletedCalls(String executorName, String teamId, String methodName) {
        if (this.isStatsEnabled()) {
            LiveRequestStats stats = getOrCreateTeamLiveStats(executorName, teamId);
            stats.setLastRequestTimestampMillis(System.currentTimeMillis());
            if (stats.getAllCompletedCalls().get(methodName) == null) {
                stats.getAllCompletedCalls().putIfAbsent(methodName, new AtomicLong(0));
            }
            stats.getAllCompletedCalls().get(methodName).incrementAndGet();
        }
    }

    @Override
    public void incrementSuccessfulCalls(String executorName, String teamId, String methodName) {
        if (this.isStatsEnabled()) {
            LiveRequestStats stats = getOrCreateTeamLiveStats(executorName, teamId);
            stats.setLastRequestTimestampMillis(System.currentTimeMillis());
            if (stats.getSuccessfulCalls().get(methodName) == null) {
                stats.getSuccessfulCalls().putIfAbsent(methodName, new AtomicLong(0));
            }
            stats.getSuccessfulCalls().get(methodName).incrementAndGet();
        }
    }

    @Override
    public void incrementUnsuccessfulCalls(String executorName, String teamId, String methodName) {
        if (this.isStatsEnabled()) {
            LiveRequestStats stats = getOrCreateTeamLiveStats(executorName, teamId);
            stats.setLastRequestTimestampMillis(System.currentTimeMillis());
            if (stats.getUnsuccessfulCalls().get(methodName) == null) {
                stats.getUnsuccessfulCalls().putIfAbsent(methodName, new AtomicLong(0));
            }
            stats.getUnsuccessfulCalls().get(methodName).incrementAndGet();
        }
    }

    @Override
    public void incrementFailedCalls(String executorName, String teamId, String methodName) {
        if (this.isStatsEnabled()) {
            LiveRequestStats stats = getOrCreateTeamLiveStats(executorName, teamId);
            stats.setLastRequestTimestampMillis(System.currentTimeMillis());
            if (stats.getFailedCalls().get(methodName) == null) {
                stats.getFailedCalls().putIfAbsent(methodName, new AtomicLong(0));
            }
            stats.getFailedCalls().get(methodName).incrementAndGet();
        }
    }

    public abstract RateLimitQueue<SUPPLIER, MSG> getRateLimitQueue(String executorName, String teamId);

    @Override
    public void updateCurrentQueueSize(String executorName, String teamId, String methodName) {
        if (this.isStatsEnabled()) {
            CopyOnWriteArrayList<String> messageIds = getOrCreateMessageIds(executorName, teamId, methodName);
            Integer totalSize = messageIds.size();
            RateLimitQueue<SUPPLIER, MSG> queue = getRateLimitQueue(executorName, teamId);
            if (queue != null) {
                totalSize += queue.getCurrentActiveQueueSize(methodName);
            }
            setCurrentQueueSize(executorName, teamId, methodName, totalSize);
        }
    }

    @Override
    public void setCurrentQueueSize(String executorName, String teamId, String methodName, Integer size) {
        if (this.isStatsEnabled()) {
            CopyOnWriteArrayList<String> messageIds = getOrCreateMessageIds(executorName, teamId, methodName);
            Integer totalSize = messageIds.size();
            RateLimitQueue<SUPPLIER, MSG> queue = getRateLimitQueue(executorName, teamId);
            if (queue != null) {
                totalSize += queue.getCurrentActiveQueueSize(methodName);
            }
            getOrCreateTeamLiveStats(executorName, teamId).getCurrentQueueSize().put(methodName, totalSize);
            getOrCreateTeamLiveStats(executorName, teamId).getCurrentQueueSize().put(methodName, size);
        }
    }

    @Override
    public Integer getNumberOfLastMinuteRequests(String executorName, String teamId, String methodName) {
        return getOrCreateLastMinuteRequests(executorName, teamId, methodName).size();
    }

    @Override
    public void updateNumberOfLastMinuteRequests(String executorName, String teamId, String methodName) {
        if (this.isStatsEnabled()) {
            LastMinuteRequests requests = getOrCreateLastMinuteRequests(executorName, teamId, methodName);
            long oneMinuteAgo = System.currentTimeMillis() - 60000L;
            for (Long millis : requests) {
                if (millis < oneMinuteAgo) {
                    requests.remove(millis);
                }
            }
            setNumberOfLastMinuteRequests(executorName, teamId, methodName, requests.size());
        }
    }

    @Override
    public void setNumberOfLastMinuteRequests(String executorName, String teamId, String methodName, Integer value) {
        if (this.isStatsEnabled()) {
            ConcurrentMap<String, Integer> lastMinuteRequests = getOrCreateTeamLiveStats(executorName, teamId).getLastMinuteRequests();
            lastMinuteRequests.put(methodName, value);
        }
    }

    @Override
    public Long getRateLimitedMethodRetryEpochMillis(String executorName, String teamId, String methodName) {
        return getOrCreateTeamLiveStats(executorName, teamId).getRateLimitedMethods().get(methodName);
    }
    // -----------------------------------------------------------

    @Override
    public void setRateLimitedMethodRetryEpochMillis(String executorName, String teamId, String methodName, Long epochTimeMillis) {
        if (this.isStatsEnabled()) {
            getOrCreateTeamLiveStats(executorName, teamId)
                    .getRateLimitedMethods()
                    .put(methodName, epochTimeMillis);
        }
    }

    // -----------------------------------------------------------

    @Override
    public void addToLastMinuteRequests(String executorName, String teamId, String methodName, Long currentMillis) {
        if (this.isStatsEnabled()) {
            getOrCreateLastMinuteRequests(executorName, teamId, methodName).add(currentMillis);
            updateNumberOfLastMinuteRequests(executorName, teamId, methodName);
        }
    }

    @Override
    public LastMinuteRequests getLastMinuteRequests(String executorName, String teamId, String methodName) {
        return getOrCreateLastMinuteRequests(executorName, teamId, methodName);
    }

    // -----------------------------------------------------------

    @Override
    public void addToWaitingMessageIds(String executorName, String teamId, String methodName, String messageId) {
        if (this.isStatsEnabled()) {
            if (teamId == null) {
                teamId = "none";
            }
            WaitingMessageIds messageIds = getOrCreateMessageIds(executorName, teamId, methodName);
            messageIds.add(messageId);
        }
    }

    @Override
    public void deleteFromWaitingMessageIds(String executorName, String teamId, String methodName, String messageId) {
        if (this.isStatsEnabled()) {
            if (teamId == null) {
                teamId = "none";
            }
            WaitingMessageIds messageIds = getOrCreateMessageIds(executorName, teamId, methodName);
            messageIds.remove(messageId);
        }
    }

    // -----------------------------------------------------------

    // Executor Name -> Team ID -> Stats
    private static final ConcurrentMap<String, ConcurrentMap<String, LiveRequestStats>>
            ALL_LIVE_STATS = new ConcurrentHashMap<>();

    private ConcurrentMap<String, LiveRequestStats> getOrCreateExecutorLiveStats(String executorName) {
        String key = getMetricsType() + "/" + executorName;
        if (ALL_LIVE_STATS.get(key) == null) {
            ALL_LIVE_STATS.putIfAbsent(key, new ConcurrentHashMap<>());
        }
        return ALL_LIVE_STATS.get(key);
    }

    private LiveRequestStats getOrCreateTeamLiveStats(String executorName, String teamId) {
        ConcurrentMap<String, LiveRequestStats> executor = getOrCreateExecutorLiveStats(executorName);
        if (teamId == null) {
            teamId = "-";
        }
        if (executor.get(teamId) == null) {
            executor.putIfAbsent(teamId, new LiveRequestStats());
        }
        return executor.get(teamId);
    }

    // -----------------------------------------------------------

    // Executor name -> Team ID -> Method name -> Message Ids
    private static final ConcurrentMap<String, ConcurrentMap<String, ConcurrentMap<String, WaitingMessageIds>>>
            ALL_MESSAGE_IDS = new ConcurrentHashMap<>();

    private WaitingMessageIds getOrCreateMessageIds(String executorName, String teamId, String methodName) {
        if (ALL_MESSAGE_IDS.get(executorName) == null) {
            ALL_MESSAGE_IDS.putIfAbsent(executorName, new ConcurrentHashMap<>());
        }
        if (ALL_MESSAGE_IDS.get(executorName).get(teamId) == null) {
            ALL_MESSAGE_IDS.get(executorName).putIfAbsent(teamId, new ConcurrentHashMap<>());
        }
        if (ALL_MESSAGE_IDS.get(executorName).get(teamId).get(methodName) == null) {
            ALL_MESSAGE_IDS.get(executorName).get(teamId).putIfAbsent(methodName, new WaitingMessageIds());
        }
        return ALL_MESSAGE_IDS.get(executorName).get(teamId).get(methodName);
    }

    // -----------------------------------------------------------

    // Executor name -> Team ID -> Method name -> Requests
    private static final ConcurrentMap<String, ConcurrentMap<String, ConcurrentMap<String, LastMinuteRequests>>>
            ALL_LAST_MINUTE_REQUESTS = new ConcurrentHashMap<>();


    private LastMinuteRequests getOrCreateLastMinuteRequests(String executorName, String teamId, String methodName) {
        if (ALL_LAST_MINUTE_REQUESTS.get(executorName) == null) {
            ALL_LAST_MINUTE_REQUESTS.putIfAbsent(executorName, new ConcurrentHashMap<>());
        }
        if (ALL_LAST_MINUTE_REQUESTS.get(executorName).get(teamId) == null) {
            ALL_LAST_MINUTE_REQUESTS.get(executorName).putIfAbsent(teamId, new ConcurrentHashMap<>());
        }
        if (ALL_LAST_MINUTE_REQUESTS.get(executorName).get(teamId).get(methodName) == null) {
            ALL_LAST_MINUTE_REQUESTS.get(executorName).get(teamId).putIfAbsent(methodName, new LastMinuteRequests());
        }
        return ALL_LAST_MINUTE_REQUESTS.get(executorName).get(teamId).get(methodName);
    }

    // -----------------------------------------------------------

    @Slf4j
    public static class MaintenanceJob implements Runnable {
        private final BaseMemoryMetricsDatastore store;
        private long lastExecutionTimestampMillis;

        public MaintenanceJob(BaseMemoryMetricsDatastore store) {
            this.store = store;
            this.lastExecutionTimestampMillis = 0L;
        }

        @Override
        public void run() {
            if (!this.store.isStatsEnabled()) {
                return;
            }
            Long startMillis = null;
            if (this.store.isTraceMode()) {
                startMillis = System.currentTimeMillis();
            }
            for (ConcurrentMap.Entry<String, ConcurrentMap<String, LiveRequestStats>> executor : ALL_LIVE_STATS.entrySet()) {
                String[] elements = executor.getKey().split("/");
                if (elements.length < 2) {
                    continue;
                }
                String type = elements[0];
                if (type == null || !type.equals(store.getMetricsType())) {
                    continue;
                }
                String executorName = executor.getKey().replaceFirst("^" + type + "/", "");
                ConcurrentMap<String, LiveRequestStats> teams = executor.getValue();
                if (teams == null) {
                    continue;
                }
                int numOfTeams = teams.size();

                if (this.store.isTraceMode()) {
                    log.debug("Going to maintain {} metrics (executor: {}, teams: {})",
                            this.store.getMetricsType(),
                            executorName,
                            numOfTeams
                    );
                }
                boolean isScalableMode = numOfTeams >= 100;
                for (ConcurrentMap.Entry<String, LiveRequestStats> team : teams.entrySet()) {
                    String teamId = team.getKey();
                    LiveRequestStats stats = team.getValue();
                    if (stats == null) {
                        continue;
                    }
                    // For the case where this app handles a small number of workspaces,
                    // this job tries to maintain the metrics as accurate as possible.
                    // If it needs to handle hundreds, thousands of workspaces,
                    // It automatically switches to the less CPU intensive mode.
                    if (isScalableMode
                            && stats.getLastRequestTimestampMillis() != null
                            && stats.getLastRequestTimestampMillis() <= this.lastExecutionTimestampMillis) {
                        if (this.store.isTraceMode()) {
                            log.debug("No request for team: {} since the last maintenance", teamId);
                        }
                        continue;
                    }
                    if (this.store.isTraceMode()) {
                        log.debug("Going to maintain the data for team: {}", teamId);
                    }
                    // Last Minute Requests
                    for (String methodName : stats.getLastMinuteRequests().keySet()) {
                        store.updateNumberOfLastMinuteRequests(executorName, teamId, methodName);
                    }
                    // Current Queue Size
                    for (String methodName : stats.getCurrentQueueSize().keySet()) {
                        store.updateCurrentQueueSize(executorName, teamId, methodName);
                    }
                    // Remove rate limited methods if already recovered
                    List<String> methodNamesToRemove = new ArrayList<>();
                    for (Map.Entry<String, Long> each : stats.getRateLimitedMethods().entrySet()) {
                        String methodName = each.getKey();
                        Long millisToRetry = each.getValue();
                        long nowMillis = System.currentTimeMillis();
                        if (millisToRetry < nowMillis) {
                            methodNamesToRemove.add(methodName);
                        }
                    }
                    for (String methodName : methodNamesToRemove) {
                        stats.getRateLimitedMethods().remove(methodName);
                    }
                }
            }

            this.lastExecutionTimestampMillis = System.currentTimeMillis();

            if (this.store.isTraceMode()) {
                long spentMillis = System.currentTimeMillis() - startMillis;
                log.debug("{} metrics maintenance completed ({} ms)", this.store.getMetricsType(), spentMillis);
            }
        }
    }
}