package com.slack.api.scim2.impl;

import com.slack.api.SlackConfig;
import com.slack.api.methods.impl.MethodsClientImpl;
import com.slack.api.methods.impl.TeamIdCache;
import com.slack.api.rate_limits.metrics.MetricsDatastore;
import com.slack.api.rate_limits.queue.MessageIdGenerator;
import com.slack.api.rate_limits.queue.MessageIdGeneratorUUIDImpl;
import com.slack.api.scim2.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Slf4j
public class AsyncRateLimitExecutor {

    private static final ConcurrentMap<String, AsyncRateLimitExecutor> ALL_EXECUTORS = new ConcurrentHashMap<>();

    private SCIM2Config config;
    private MetricsDatastore metricsDatastore; // intentionally mutable
    private final TeamIdCache teamIdCache;
    private final MessageIdGenerator messageIdGenerator;

    private AsyncRateLimitExecutor(MethodsClientImpl methods, SlackConfig config) {
        this.config = config.getSCIM2Config();
        this.metricsDatastore = config.getSCIM2Config().getMetricsDatastore();
        this.teamIdCache = new TeamIdCache(methods);
        this.messageIdGenerator = new MessageIdGeneratorUUIDImpl();
    }

    public static AsyncRateLimitExecutor get(String executorName) {
        return ALL_EXECUTORS.get(executorName);
    }

    public static AsyncRateLimitExecutor getOrCreate(MethodsClientImpl methods, SlackConfig config) {
        AsyncRateLimitExecutor executor = ALL_EXECUTORS.get(config.getSCIM2Config().getExecutorName());
        if (executor != null && executor.metricsDatastore != config.getSCIM2Config().getMetricsDatastore()) {
            // As the metrics datastore has been changed, we should replace the executor
            executor.config = config.getSCIM2Config();
            executor.metricsDatastore = config.getSCIM2Config().getMetricsDatastore();
        }
        if (executor == null) {
            executor = new AsyncRateLimitExecutor(methods, config);
            ALL_EXECUTORS.putIfAbsent(config.getSCIM2Config().getExecutorName(), executor);
        }
        return executor;
    }

    private static final List<String> NO_TOKEN_METHOD_NAMES = Collections.emptyList();

    public <T extends SCIM2ApiResponse> CompletableFuture<T> execute(
            SCIM2EndpointName endpointName,
            Map<String, String> params,
            AsyncExecutionSupplier<T> methodsSupplier) {
        String token = params.get("token");
        final String teamId = token != null ? teamIdCache.lookupOrResolve(token) : null;
        final ExecutorService executorService = teamId != null ? ThreadPools.getOrCreate(config, teamId) : ThreadPools.getDefault(config);
        return CompletableFuture.supplyAsync(() -> {
            String messageId = messageIdGenerator.generate();
            addMessageId(teamId, endpointName, messageId);
            syncCurrentQueueSizeStats(teamId, endpointName);
            if (NO_TOKEN_METHOD_NAMES.contains(endpointName) || teamId == null) {
                return runWithoutQueue(teamId, endpointName, methodsSupplier);
            } else {
                return enqueueThenRun(
                        messageId,
                        teamId,
                        endpointName,
                        params,
                        methodsSupplier
                );
            }
        }, executorService);
    }

    private void syncCurrentQueueSizeStats(String teamId, SCIM2EndpointName endpointName) {
        if (teamId != null) {
            metricsDatastore.updateCurrentQueueSize(config.getExecutorName(), teamId, endpointName.name());
        }
    }

    private void addMessageId(
            String teamId,
            SCIM2EndpointName endpointName,
            String messageId) {
        metricsDatastore.addToWaitingMessageIds(
                config.getExecutorName(), teamId, endpointName.name(), messageId);
    }

    private void removeMessageId(
            String teamId,
            SCIM2EndpointName endpointName,
            String messageId) {
        metricsDatastore.deleteFromWaitingMessageIds(
                config.getExecutorName(), teamId, endpointName.name(), messageId);
    }

    public <T extends SCIM2ApiResponse> T runWithoutQueue(
            String teamId,
            SCIM2EndpointName endpointName,
            AsyncExecutionSupplier<T> methodsSupplier) {
        try {
            return methodsSupplier.execute();
        } catch (RuntimeException e) {
            return handleRuntimeException(teamId, endpointName, e);
        } catch (IOException e) {
            return handleIOException(teamId, endpointName, e);
        } catch (SCIM2ApiException e) {
            logSCIMApiException(teamId, endpointName, e);
            throw new SCIM2ApiCompletionException(null, e, null);
        }
    }

    private <T extends SCIM2ApiResponse> T enqueueThenRun(
            String messageId,
            String teamId,
            SCIM2EndpointName endpointName,
            Map<String, String> params,
            AsyncExecutionSupplier<T> methodsSupplier) {
        try {
            AsyncRateLimitQueue activeQueue = AsyncRateLimitQueue.getOrCreate(config, teamId);
            if (activeQueue == null) {
                log.warn("Queue for teamId: {} was not found. Going to run the API call immediately.", teamId);
            }
            AsyncExecutionSupplier<T> supplier = null;
            activeQueue.enqueue(messageId, teamId, endpointName.name(), params, methodsSupplier);
            long consumedMillis = 0L;
            while (supplier == null && consumedMillis < config.getMaxIdleMills()) {
                Thread.sleep(10);
                consumedMillis += 10;
                supplier = (AsyncExecutionSupplier<T>) activeQueue.dequeueIfReady(
                        messageId, teamId, endpointName.name(), params);
                removeMessageId(teamId, endpointName, messageId);
            }
            if (supplier == null) {
                activeQueue.remove(endpointName.name(), messageId);
                throw new RejectedExecutionException("Gave up executing the message after " + config.getMaxIdleMills() + " milliseconds.");
            }
            T response = supplier.execute();
            return response;

        } catch (RuntimeException e) {
            return handleRuntimeException(teamId, endpointName, e);
        } catch (IOException e) {
            return handleIOException(teamId, endpointName, e);
        } catch (SCIM2ApiException e) {
            logSCIMApiException(teamId, endpointName, e);
            if (e.getResponse().code() == 429) {
                return enqueueThenRun(messageId, teamId, endpointName, params, methodsSupplier);
            }
            throw new SCIM2ApiCompletionException(null, e, null);
        } catch (InterruptedException e) {
            log.error("Got an InterruptedException (error: {})", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private static <T extends SCIM2ApiResponse> T handleRuntimeException(
            String teamId, SCIM2EndpointName endpointName, RuntimeException e) {
        log.error("Got an exception while calling {} API (team: {}, error: {})", endpointName, teamId, e.getMessage(), e);
        throw new SCIM2ApiCompletionException(null, null, e);
    }

    private static <T extends SCIM2ApiResponse> T handleIOException(
            String teamId, SCIM2EndpointName endpointName, IOException e) {
        log.error("Failed to connect to {} API (team: {}, error: {})", endpointName, teamId, e.getMessage(), e);
        throw new SCIM2ApiCompletionException(e, null, null);
    }

    private static void logSCIMApiException(String teamId, SCIM2EndpointName endpointName, SCIM2ApiException e) {
        if (e.getResponse().code() == 429) {
            String retryAfterSeconds = e.getResponse().header("Retry-After");
            log.error("Got a rate-limited response from {} API (team: {}, error: {}, retry-after: {})",
                    endpointName,
                    teamId,
                    e.getMessage(),
                    retryAfterSeconds,
                    e
            );
        } else {
            log.error("Got an unsuccessful response from {} API (team: {}, error: {}, status code: {})",
                    endpointName,
                    teamId,
                    e.getMessage(),
                    e.getResponse().code(),
                    e
            );
        }
    }

}
