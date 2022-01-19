package com.slack.api.rate_limits.metrics.impl;

import com.google.gson.Gson;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

public abstract class BaseMemoryMetricsDatastore<SUPPLIER, MSG extends QueueMessage> implements MetricsDatastore, AutoCloseable {

    private final ScheduledExecutorService cleanerExecutor;
    private final int numberOfNodes;
    private ExecutorServiceProvider executorServiceProvider;

    public BaseMemoryMetricsDatastore(int numberOfNodes) {
        this(numberOfNodes, DaemonThreadExecutorServiceProvider.getInstance());
    }

    public BaseMemoryMetricsDatastore(int numberOfNodes, ExecutorServiceProvider executorServiceProvider) {
        this.numberOfNodes = numberOfNodes;
        this.executorServiceProvider = executorServiceProvider;
        this.cleanerExecutor = executorServiceProvider.createThreadScheduledExecutor(getThreadGroupName());
        this.cleanerExecutor.scheduleAtFixedRate(new MaintenanceJob(this), 1000, 50, TimeUnit.MILLISECONDS);
    }

    @Override
    public void close() throws Exception {
        cleanerExecutor.shutdown();
    }

    protected abstract String getMetricsType();

    public String getThreadGroupName() {
        return "slack-api-client-metrics-memory:" + Integer.toHexString(hashCode());
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
    }

    // -----------------------------------------------------------

    @Override
    public void incrementAllCompletedCalls(String executorName, String teamId, String methodName) {
        LiveRequestStats stats = getOrCreateTeamLiveStats(executorName, teamId);
        if (stats.getAllCompletedCalls().get(methodName) == null) {
            stats.getAllCompletedCalls().putIfAbsent(methodName, new AtomicLong(0));
        }
        stats.getAllCompletedCalls().get(methodName).incrementAndGet();
    }

    @Override
    public void incrementSuccessfulCalls(String executorName, String teamId, String methodName) {
        LiveRequestStats stats = getOrCreateTeamLiveStats(executorName, teamId);
        if (stats.getSuccessfulCalls().get(methodName) == null) {
            stats.getSuccessfulCalls().putIfAbsent(methodName, new AtomicLong(0));
        }
        stats.getSuccessfulCalls().get(methodName).incrementAndGet();
    }

    @Override
    public void incrementUnsuccessfulCalls(String executorName, String teamId, String methodName) {
        LiveRequestStats stats = getOrCreateTeamLiveStats(executorName, teamId);
        if (stats.getUnsuccessfulCalls().get(methodName) == null) {
            stats.getUnsuccessfulCalls().putIfAbsent(methodName, new AtomicLong(0));
        }
        stats.getUnsuccessfulCalls().get(methodName).incrementAndGet();
    }

    @Override
    public void incrementFailedCalls(String executorName, String teamId, String methodName) {
        LiveRequestStats stats = getOrCreateTeamLiveStats(executorName, teamId);
        if (stats.getFailedCalls().get(methodName) == null) {
            stats.getFailedCalls().putIfAbsent(methodName, new AtomicLong(0));
        }
        stats.getFailedCalls().get(methodName).incrementAndGet();
    }

    public abstract RateLimitQueue<SUPPLIER, MSG> getRateLimitQueue(String executorName, String teamId);

    @Override
    public void updateCurrentQueueSize(String executorName, String teamId, String methodName) {
        CopyOnWriteArrayList<String> messageIds = getOrCreateMessageIds(executorName, teamId, methodName);
        Integer totalSize = messageIds.size();
        RateLimitQueue<SUPPLIER, MSG> queue = getRateLimitQueue(executorName, teamId);
        if (queue != null) {
            totalSize += queue.getCurrentActiveQueueSize(methodName);
        }
        setCurrentQueueSize(executorName, teamId, methodName, totalSize);
    }

    @Override
    public void setCurrentQueueSize(String executorName, String teamId, String methodName, Integer size) {
        CopyOnWriteArrayList<String> messageIds = getOrCreateMessageIds(executorName, teamId, methodName);
        Integer totalSize = messageIds.size();
        RateLimitQueue<SUPPLIER, MSG> queue = getRateLimitQueue(executorName, teamId);
        if (queue != null) {
            totalSize += queue.getCurrentActiveQueueSize(methodName);
        }
        getOrCreateTeamLiveStats(executorName, teamId).getCurrentQueueSize().put(methodName, totalSize);
        getOrCreateTeamLiveStats(executorName, teamId).getCurrentQueueSize().put(methodName, size);
    }

    @Override
    public Integer getNumberOfLastMinuteRequests(String executorName, String teamId, String methodName) {
        return getOrCreateLastMinuteRequests(executorName, teamId, methodName).size();
    }

    @Override
    public void updateNumberOfLastMinuteRequests(String executorName, String teamId, String methodName) {
        LastMinuteRequests requests = getOrCreateLastMinuteRequests(executorName, teamId, methodName);
        long oneMinuteAgo = System.currentTimeMillis() - 60000L;
        for (Long millis : requests) {
            if (millis < oneMinuteAgo) {
                requests.remove(millis);
            }
        }
        setNumberOfLastMinuteRequests(executorName, teamId, methodName, requests.size());
    }

    @Override
    public void setNumberOfLastMinuteRequests(String executorName, String teamId, String methodName, Integer value) {
        ConcurrentMap<String, Integer> lastMinuteRequests = getOrCreateTeamLiveStats(executorName, teamId).getLastMinuteRequests();
        lastMinuteRequests.put(methodName, value);
    }

    @Override
    public Long getRateLimitedMethodRetryEpochMillis(String executorName, String teamId, String methodName) {
        return getOrCreateTeamLiveStats(executorName, teamId).getRateLimitedMethods().get(methodName);
    }
    // -----------------------------------------------------------

    @Override
    public void setRateLimitedMethodRetryEpochMillis(String executorName, String teamId, String methodName, Long epochTimeMillis) {
        getOrCreateTeamLiveStats(executorName, teamId)
                .getRateLimitedMethods()
                .put(methodName, epochTimeMillis);
    }

    // -----------------------------------------------------------

    @Override
    public void addToLastMinuteRequests(String executorName, String teamId, String methodName, Long currentMillis) {
        getOrCreateLastMinuteRequests(executorName, teamId, methodName).add(currentMillis);
        updateNumberOfLastMinuteRequests(executorName, teamId, methodName);
    }

    @Override
    public LastMinuteRequests getLastMinuteRequests(String executorName, String teamId, String methodName) {
        return getOrCreateLastMinuteRequests(executorName, teamId, methodName);
    }

    // -----------------------------------------------------------

    @Override
    public void addToWaitingMessageIds(String executorName, String teamId, String methodName, String messageId) {
        if (teamId == null) {
            teamId = "none";
        }
        WaitingMessageIds messageIds = getOrCreateMessageIds(executorName, teamId, methodName);
        messageIds.add(messageId);
    }

    @Override
    public void deleteFromWaitingMessageIds(String executorName, String teamId, String methodName, String messageId) {
        if (teamId == null) {
            teamId = "none";
        }
        WaitingMessageIds messageIds = getOrCreateMessageIds(executorName, teamId, methodName);
        messageIds.remove(messageId);
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

    private static class MaintenanceJob implements Runnable {
        private final BaseMemoryMetricsDatastore store;

        MaintenanceJob(BaseMemoryMetricsDatastore store) {
            this.store = store;
        }

        @Override
        public void run() {
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
                for (ConcurrentMap.Entry<String, LiveRequestStats> team : executor.getValue().entrySet()) {
                    String teamId = team.getKey();
                    LiveRequestStats stats = team.getValue();
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
        }
    }

}