package com.slack.api.methods;

import java.util.Optional;

/**
 * Custom rate limit resolver.
 * <p>
 * When you need to adjust the rate limits for some reason,
 * you can implement this interface and pass it to MethodsConfig#setCustomRateLimitResolver() method.
 * With that, you can override the allowed requests per minute for selected methods.
 */
public interface MethodsCustomRateLimitResolver {

    /**
     * Return a present value only when you want to override the allowed requests per minute.
     * Otherwise, returning Optional.empty() will result in falling back to the built-in calculation.
     *
     * @param teamId     the workspace ID
     * @param methodName the method name such as "auth.test"
     * @return the number of allowed requests per minute
     */
    Optional<Integer> getCustomAllowedRequestsPerMinute(String teamId, String methodName);

    /**
     * Return a present value only when you want to override the allowed requests per minute.
     * Otherwise, returning Optional.empty() will result in falling back to the built-in calculation.
     *
     * @param teamId  the workspace ID
     * @param channel either a channel ID or channel name
     * @return the number of allowed requests per minute
     */
    Optional<Integer> getCustomAllowedRequestsForChatPostMessagePerMinute(String teamId, String channel);

    class Default implements MethodsCustomRateLimitResolver {
        @Override
        public Optional<Integer> getCustomAllowedRequestsPerMinute(String teamId, String methodName) {
            return Optional.empty();
        }

        @Override
        public Optional<Integer> getCustomAllowedRequestsForChatPostMessagePerMinute(String teamId, String channel) {
            return Optional.empty();
        }
    }

    MethodsCustomRateLimitResolver DEFAULT = new Default();

}
