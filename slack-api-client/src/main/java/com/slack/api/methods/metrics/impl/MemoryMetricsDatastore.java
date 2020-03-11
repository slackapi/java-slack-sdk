package com.slack.api.methods.metrics.impl;

import com.google.gson.Gson;
import com.slack.api.methods.MethodsStats;
import com.slack.api.methods.impl.AsyncRateLimitQueue;
import com.slack.api.methods.metrics.LastMinuteRequests;
import com.slack.api.methods.metrics.MetricsDatastore;
import com.slack.api.methods.metrics.WaitingMessageIds;
import com.slack.api.util.json.GsonFactory;
import com.slack.api.util.thread.ExecutorServiceFactory;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

public class MemoryMetricsDatastore implements MetricsDatastore {

    private final ScheduledExecutorService cleanerExecutor;
    private final int numberOfNodes;

    public MemoryMetricsDatastore(int numberOfNodes) {
        this.numberOfNodes = numberOfNodes;
        String threadGroupName = "slack-methods-metrics-memory";
        this.cleanerExecutor = ExecutorServiceFactory.createDaemonThreadScheduledExecutor(threadGroupName);
        this.cleanerExecutor.scheduleAtFixedRate(new MaintenanceJob(this), 1000, 50, TimeUnit.MILLISECONDS);
    }

    @Override
    protected void finalize() throws Throwable {
        cleanerExecutor.shutdown();
        super.finalize();
    }

    // -----------------------------------------------------------

    private static final Gson GSON = GsonFactory.createSnakeCase();

    @Override
    public int getNumberOfNodes() {
        return this.numberOfNodes;
    }

    @Override
    public Map<String, Map<String, MethodsStats>> getAllStats() {
        Map<String, Map<String, MethodsStats>> result = new HashMap<>();
        for (Map.Entry<String, ConcurrentMap<String, LiveMethodsStats>> executor : ALL_LIVE_STATS.entrySet()) {
            Map<String, MethodsStats> allTeams = new HashMap<>();
            for (Map.Entry<String, LiveMethodsStats> team : executor.getValue().entrySet()) {
                MethodsStats stats = GSON.fromJson(GSON.toJson(team.getValue()), MethodsStats.class);
                allTeams.put(team.getKey(), stats);
            }
            result.put(executor.getKey(), allTeams);
        }
        return result;
    }

    @Override
    public MethodsStats getStats(String executorName, String teamId) {
        LiveMethodsStats internal = getOrCreateTeamLiveStats(executorName, teamId);
        MethodsStats stats = GSON.fromJson(GSON.toJson(internal), MethodsStats.class);
        return stats;
    }

    // -----------------------------------------------------------

    @Override
    public void incrementAllCompletedCalls(String executorName, String teamId, String methodName) {
        LiveMethodsStats stats = getOrCreateTeamLiveStats(executorName, teamId);
        if (stats.getAllCompletedCalls().get(methodName) == null) {
            stats.getAllCompletedCalls().putIfAbsent(methodName, new AtomicLong(0));
        }
        stats.getAllCompletedCalls().get(methodName).incrementAndGet();
    }

    @Override
    public void incrementSuccessfulCalls(String executorName, String teamId, String methodName) {
        LiveMethodsStats stats = getOrCreateTeamLiveStats(executorName, teamId);
        if (stats.getSuccessfulCalls().get(methodName) == null) {
            stats.getSuccessfulCalls().putIfAbsent(methodName, new AtomicLong(0));
        }
        stats.getSuccessfulCalls().get(methodName).incrementAndGet();
    }

    @Override
    public void incrementUnsuccessfulCalls(String executorName, String teamId, String methodName) {
        LiveMethodsStats stats = getOrCreateTeamLiveStats(executorName, teamId);
        if (stats.getUnsuccessfulCalls().get(methodName) == null) {
            stats.getUnsuccessfulCalls().putIfAbsent(methodName, new AtomicLong(0));
        }
        stats.getUnsuccessfulCalls().get(methodName).incrementAndGet();
    }

    @Override
    public void incrementFailedCalls(String executorName, String teamId, String methodName) {
        LiveMethodsStats stats = getOrCreateTeamLiveStats(executorName, teamId);
        if (stats.getFailedCalls().get(methodName) == null) {
            stats.getFailedCalls().putIfAbsent(methodName, new AtomicLong(0));
        }
        stats.getFailedCalls().get(methodName).incrementAndGet();
    }

    @Override
    public void updateCurrentQueueSize(String executorName, String teamId, String methodName) {
        CopyOnWriteArrayList<String> messageIds = getOrCreateMessageIds(executorName, teamId, methodName);
        Integer totalSize = messageIds.size();
        AsyncRateLimitQueue queue = AsyncRateLimitQueue.get(executorName, teamId);
        if (queue != null) {
            totalSize += queue.getCurrentActiveQueueSize(methodName);
        }
        setCurrentQueueSize(executorName, teamId, methodName, totalSize);
    }

    @Override
    public void setCurrentQueueSize(String executorName, String teamId, String methodName, Integer size) {
        CopyOnWriteArrayList<String> messageIds = getOrCreateMessageIds(executorName, teamId, methodName);
        Integer totalSize = messageIds.size();
        AsyncRateLimitQueue queue = AsyncRateLimitQueue.get(executorName, teamId);
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
    private static final ConcurrentMap<String, ConcurrentMap<String, LiveMethodsStats>>
            ALL_LIVE_STATS = new ConcurrentHashMap<>();

    @Data
    public class LiveMethodsStats {
        private final ConcurrentMap<String, AtomicLong> allCompletedCalls = new ConcurrentHashMap<>();
        private final ConcurrentMap<String, AtomicLong> successfulCalls = new ConcurrentHashMap<>();
        private final ConcurrentMap<String, AtomicLong> unsuccessfulCalls = new ConcurrentHashMap<>();
        private final ConcurrentMap<String, AtomicLong> failedCalls = new ConcurrentHashMap<>();
        private final ConcurrentMap<String, Integer> currentQueueSize = new ConcurrentHashMap<>();
        private final ConcurrentMap<String, Integer> lastMinuteRequests = new ConcurrentHashMap<>();
        private final ConcurrentMap<String, Long> rateLimitedMethods = new ConcurrentHashMap<>();
    }

    private ConcurrentMap<String, LiveMethodsStats> getOrCreateExecutorLiveStats(String executorName) {
        if (ALL_LIVE_STATS.get(executorName) == null) {
            ALL_LIVE_STATS.putIfAbsent(executorName, new ConcurrentHashMap<>());
        }
        return ALL_LIVE_STATS.get(executorName);
    }

    private LiveMethodsStats getOrCreateTeamLiveStats(String executorName, String teamId) {
        ConcurrentMap<String, LiveMethodsStats> executor = getOrCreateExecutorLiveStats(executorName);
        if (executor.get(teamId) == null) {
            executor.putIfAbsent(teamId, new LiveMethodsStats());
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
        private final MemoryMetricsDatastore store;

        MaintenanceJob(MemoryMetricsDatastore store) {
            this.store = store;
        }

        @Override
        public void run() {
            for (ConcurrentMap.Entry<String, ConcurrentMap<String, LiveMethodsStats>> executor : ALL_LIVE_STATS.entrySet()) {
                String executorName = executor.getKey();
                for (ConcurrentMap.Entry<String, LiveMethodsStats> team : executor.getValue().entrySet()) {
                    String teamId = team.getKey();
                    LiveMethodsStats stats = team.getValue();
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