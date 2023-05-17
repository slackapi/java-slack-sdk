package com.slack.api.audit.impl;

import com.slack.api.SlackConfig;
import com.slack.api.audit.AuditApiCompletionException;
import com.slack.api.audit.AuditApiException;
import com.slack.api.audit.AuditApiResponse;
import com.slack.api.audit.AuditConfig;
import com.slack.api.methods.impl.MethodsClientImpl;
import com.slack.api.methods.impl.TeamIdCache;
import com.slack.api.rate_limits.metrics.MetricsDatastore;
import com.slack.api.rate_limits.queue.MessageIdGenerator;
import com.slack.api.rate_limits.queue.MessageIdGeneratorUUIDImpl;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Slf4j
public class AsyncRateLimitExecutor {

    private static final ConcurrentMap<String, AsyncRateLimitExecutor> ALL_EXECUTORS = new ConcurrentHashMap<>();

    private AuditConfig config;
    private MetricsDatastore metricsDatastore; // intentionally mutable
    private final TeamIdCache teamIdCache;
    private final MessageIdGenerator messageIdGenerator;

    private AsyncRateLimitExecutor(MethodsClientImpl methods, SlackConfig config) {
        this.config = config.getAuditConfig();
        this.metricsDatastore = config.getAuditConfig().getMetricsDatastore();
        this.teamIdCache = new TeamIdCache(methods);
        this.messageIdGenerator = new MessageIdGeneratorUUIDImpl();
    }

    public static AsyncRateLimitExecutor get(String executorName) {
        return ALL_EXECUTORS.get(executorName);
    }

    public static AsyncRateLimitExecutor getOrCreate(MethodsClientImpl methods, SlackConfig config) {
        AsyncRateLimitExecutor executor = ALL_EXECUTORS.get(config.getAuditConfig().getExecutorName());
        if (executor != null && executor.metricsDatastore != config.getAuditConfig().getMetricsDatastore()) {
            // As the metrics datastore has been changed, we should replace the executor
            executor.config = config.getAuditConfig();
            executor.metricsDatastore = config.getAuditConfig().getMetricsDatastore();
        }
        if (executor == null) {
            executor = new AsyncRateLimitExecutor(methods, config);
            ALL_EXECUTORS.putIfAbsent(config.getAuditConfig().getExecutorName(), executor);
        }
        return executor;
    }

    private static final List<String> NO_TOKEN_METHOD_NAMES = Arrays.asList("schemas", "actions");

    public <T extends AuditApiResponse> CompletableFuture<T> execute(
            String methodName,
            Map<String, String> params,
            AsyncExecutionSupplier<T> methodsSupplier) {
        String token = params.get("token");
        final String teamId = token != null ? teamIdCache.lookupOrResolve(token) : null;
        final ExecutorService executorService = teamId != null ? ThreadPools.getOrCreate(config, teamId) : ThreadPools.getDefault(config);
        return CompletableFuture.supplyAsync(() -> {
            String messageId = messageIdGenerator.generate();
            addMessageId(teamId, methodName, messageId);
            initCurrentQueueSizeStatsIfAbsent(teamId, methodName);
            if (NO_TOKEN_METHOD_NAMES.contains(methodName) || teamId == null) {
                return runWithoutQueue(teamId, methodName, methodsSupplier);
            } else {
                return enqueueThenRun(
                        messageId,
                        teamId,
                        methodName,
                        params,
                        methodsSupplier
                );
            }
        }, executorService);
    }

    private void initCurrentQueueSizeStatsIfAbsent(String teamId, String methodNameWithSuffix) {
        if (teamId != null) {
            metricsDatastore.setCurrentQueueSize(config.getExecutorName(), teamId, methodNameWithSuffix, 0);
        }
    }

    private void addMessageId(
            String teamId,
            String methodNameWithSuffix,
            String messageId) {
        metricsDatastore.addToWaitingMessageIds(
                config.getExecutorName(), teamId, methodNameWithSuffix, messageId);
    }

    private void removeMessageId(
            String teamId,
            String methodNameWithSuffix,
            String messageId) {
        metricsDatastore.deleteFromWaitingMessageIds(
                config.getExecutorName(), teamId, methodNameWithSuffix, messageId);
    }

    public <T extends AuditApiResponse> T runWithoutQueue(
            String teamId,
            String methodName,
            AsyncExecutionSupplier<T> methodsSupplier) {
        try {
            return methodsSupplier.execute();
        } catch (RuntimeException e) {
            return handleRuntimeException(teamId, methodName, e);
        } catch (IOException e) {
            return handleIOException(teamId, methodName, e);
        } catch (AuditApiException e) {
            logAuditApiException(teamId, methodName, e);
            throw new AuditApiCompletionException(null, e, null);
        }
    }

    private <T extends AuditApiResponse> T enqueueThenRun(
            String messageId,
            String teamId,
            String methodName,
            Map<String, String> params,
            AsyncExecutionSupplier<T> methodsSupplier) {
        try {
            AsyncRateLimitQueue activeQueue = AsyncRateLimitQueue.getOrCreate(config, teamId);
            if (activeQueue == null) {
                log.warn("Queue for teamId: {} was not found. Going to run the API call immediately.", teamId);
            }
            AsyncExecutionSupplier<T> supplier = null;
            activeQueue.enqueue(messageId, teamId, methodName, params, methodsSupplier);
            long consumedMillis = 0L;
            while (supplier == null && consumedMillis < config.getMaxIdleMills()) {
                Thread.sleep(10);
                consumedMillis += 10;
                supplier = (AsyncExecutionSupplier<T>) activeQueue.dequeueIfReady(
                        messageId, teamId, methodName, params);
                removeMessageId(teamId, methodName, messageId);
            }
            if (supplier == null) {
                activeQueue.remove(methodName, messageId);
                throw new RejectedExecutionException("Gave up executing the message after " + config.getMaxIdleMills() + " milliseconds.");
            }
            T response = supplier.execute();
            return response;

        } catch (RuntimeException e) {
            return handleRuntimeException(teamId, methodName, e);
        } catch (IOException e) {
            return handleIOException(teamId, methodName, e);
        } catch (AuditApiException e) {
            logAuditApiException(teamId, methodName, e);
            if (e.getResponse().code() == 429) {
                return enqueueThenRun(messageId, teamId, methodName, params, methodsSupplier);
            }
            throw new AuditApiCompletionException(null, e, null);
        } catch (InterruptedException e) {
            log.error("Got an InterruptedException (error: {})", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private static <T extends AuditApiResponse> T handleRuntimeException(String teamId, String methodName, RuntimeException e) {
        log.error("Got an exception while calling {} API (team: {}, error: {})", methodName, teamId, e.getMessage(), e);
        throw new AuditApiCompletionException(null, null, e);
    }

    private static <T extends AuditApiResponse> T handleIOException(String teamId, String methodName, IOException e) {
        log.error("Failed to connect to {} API (team: {}, error: {})", methodName, teamId, e.getMessage(), e);
        throw new AuditApiCompletionException(e, null, null);
    }

    private static void logAuditApiException(String teamId, String methodName, AuditApiException e) {
        if (e.getResponse().code() == 429) {
            String retryAfterSeconds = e.getResponse().header("Retry-After");
            log.error("Got a rate-limited response from {} API (team: {}, error: {}, retry-after: {})",
                    methodName,
                    teamId,
                    e.getMessage(),
                    retryAfterSeconds,
                    e
            );
        } else {
            log.error("Got an unsuccessful response from {} API (team: {}, error: {}, status code: {})",
                    methodName,
                    teamId,
                    e.getMessage(),
                    e.getResponse().code(),
                    e
            );
        }
    }

}
