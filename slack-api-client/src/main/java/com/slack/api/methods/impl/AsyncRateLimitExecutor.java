package com.slack.api.methods.impl;

import com.slack.api.SlackConfig;
import com.slack.api.methods.*;
import com.slack.api.methods.metrics.MetricsDatastore;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;

@Slf4j
public class AsyncRateLimitExecutor {

    private static final ConcurrentMap<String, AsyncRateLimitExecutor> ALL_EXECUTORS = new ConcurrentHashMap<>();

    private MethodsConfig config;
    private MetricsDatastore metricsDatastore; // intentionally mutable
    private final TeamIdCache teamIdCache;

    private AsyncRateLimitExecutor(MethodsClientImpl clientImpl, SlackConfig config) {
        this.config = config.getMethodsConfig();
        this.metricsDatastore = config.getMethodsConfig().getMetricsDatastore();
        this.teamIdCache = new TeamIdCache(clientImpl);
    }

    public static AsyncRateLimitExecutor get(String executorName) {
        return ALL_EXECUTORS.get(executorName);
    }

    public static AsyncRateLimitExecutor getOrCreate(MethodsClientImpl client, SlackConfig config) {
        AsyncRateLimitExecutor executor = ALL_EXECUTORS.get(config.getMethodsConfig().getExecutorName());
        if (executor != null && executor.metricsDatastore != config.getMethodsConfig().getMetricsDatastore()) {
            // As the metrics datastore has been changed, we should replace the executor
            executor.config = config.getMethodsConfig();
            executor.metricsDatastore = config.getMethodsConfig().getMetricsDatastore();
        }
        if (executor == null) {
            executor = new AsyncRateLimitExecutor(client, config);
            ALL_EXECUTORS.putIfAbsent(config.getMethodsConfig().getExecutorName(), executor);
        }
        return executor;
    }

    private static final List<String> NO_TOKEN_METHOD_NAMES = Arrays.asList(
            Methods.API_TEST,
            Methods.OAUTH_ACCESS,
            Methods.OAUTH_TOKEN,
            Methods.OAUTH_V2_ACCESS
    );

    public <T extends SlackApiResponse> CompletableFuture<T> execute(
            String methodName,
            Map<String, String> params,
            AsyncExecutionSupplier<T> methodsSupplier) {
        String token = params.get("token");
        final String teamId = token != null ? teamIdCache.lookupOrResolve(token) : null;
        final ExecutorService executorService = teamId != null ? ThreadPools.getOrCreate(config, teamId) : ThreadPools.getDefault(config);
        return CompletableFuture.supplyAsync(() -> {
            if (NO_TOKEN_METHOD_NAMES.contains(methodName) || teamId == null) {
                return runWithoutQueue(teamId, methodName, methodsSupplier);
            } else {
                String messageId = generateMessageId();
                String methodNameWithSuffix = toMethodNameWithSuffix(methodName, params);
                addMessageId(teamId, methodNameWithSuffix, messageId);
                initCurrentQueueSizeStatsIfAbsent(teamId, methodNameWithSuffix);
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

    private String toMethodNameWithSuffix(String methodName, Map<String, String> params) {
        String methodNameWithSuffix = methodName;
        if (methodName.equals(Methods.CHAT_POST_MESSAGE)) {
            methodNameWithSuffix = Methods.CHAT_POST_MESSAGE + "_" + params.get("channel");
        }
        return methodNameWithSuffix;
    }

    private <T extends SlackApiResponse> T runWithoutQueue(
            String teamId,
            String methodName,
            AsyncExecutionSupplier<T> methodsSupplier) {
        try {
            return methodsSupplier.execute();
        } catch (RuntimeException e) {
            return handleRuntimeException(teamId, methodName, e);
        } catch (IOException e) {
            return handleIOException(teamId, methodName, e);
        } catch (SlackApiException e) {
            logSlackApiException(teamId, methodName, e);
            throw new MethodsCompletionException(null, e, null);
        }
    }

    private <T extends SlackApiResponse> T enqueueThenRun(
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
                supplier = activeQueue.dequeueIfReady(messageId, teamId, methodName, params);
                removeMessageId(teamId, toMethodNameWithSuffix(methodName, params), messageId);
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
        } catch (SlackApiException e) {
            logSlackApiException(teamId, methodName, e);
            if (e.getResponse().code() == 429) {
                return enqueueThenRun(messageId, teamId, methodName, params, methodsSupplier);
            }
            throw new MethodsCompletionException(null, e, null);
        } catch (InterruptedException e) {
            log.error("Got an InterruptedException (error: {})", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private static String generateMessageId() {
        return UUID.randomUUID().toString();
    }

    private static <T extends SlackApiTextResponse> T handleRuntimeException(String teamId, String methodName, RuntimeException e) {
        log.error("Got an exception while calling {} API (team: {}, error: {})", methodName, teamId, e.getMessage(), e);
        throw new MethodsCompletionException(null, null, e);
    }

    private static <T extends SlackApiTextResponse> T handleIOException(String teamId, String methodName, IOException e) {
        log.error("Failed to connect to {} API (team: {}, error: {})", methodName, teamId, e.getMessage(), e);
        throw new MethodsCompletionException(e, null, null);
    }

    private static void logSlackApiException(String teamId, String methodName, SlackApiException e) {
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
