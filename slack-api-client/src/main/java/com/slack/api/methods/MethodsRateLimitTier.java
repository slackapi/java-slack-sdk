package com.slack.api.methods;

import java.util.HashMap;
import java.util.Map;

/**
 * @see <a href="https://api.slack.com/docs/rate-limits">Slack Rate Limits</a>
 */
public enum MethodsRateLimitTier {

    /**
     * 1+ per minute
     * <p>
     * Access tier 1 methods infrequently. A small amount of burst behavior is tolerated.
     */
    Tier1,

    /**
     * 20+ per minute
     * <p>
     * Most methods allow at least 20 requests per minute, while allowing for occasional bursts of more requests.
     */
    Tier2,

    /**
     * 50+ per minute
     * <p>
     * Tier 3 methods allow a larger number of requests and are typically attached to methods with paginating collections of conversations or users. Sporadic bursts are welcome.
     */
    Tier3,

    /**
     * 100+ per minute
     * <p>
     * Enjoy a large request quota for Tier 4 methods, including generous burst behavior.
     */
    Tier4,

    /**
     * This method allows hundreds of requests per minute. Use it as often as is reasonably required.
     */
    SpecialTier_auth_test,

    /**
     * assistant.threads.setStatus has the similar tier with chat.postMessage API.
     */
    SpecialTier_assistant_threads_setStatus,

    /**
     * chat.postMessage has special rate limiting conditions.
     * It will generally allow an app to post 1 message per second to a specific channel.
     * There are limits governing your app's relationship with the entire workspace above that,
     * limiting posting to several hundred messages per minute. Generous burst behavior is also granted.
     */
    SpecialTier_chat_postMessage,

    /**
     * This method allows hundreds of requests per minute. Use it as often as is reasonably required.
     */
    SpecialTier_chat_getPermalink;

    // --------------------------------------------------------------------------------------------

    private static final Map<MethodsRateLimitTier, Integer> allowedRequestsPerMinute = new HashMap<>();

    static {
        allowedRequestsPerMinute.put(MethodsRateLimitTier.Tier1, 1);
        allowedRequestsPerMinute.put(MethodsRateLimitTier.Tier2, 20);
        allowedRequestsPerMinute.put(MethodsRateLimitTier.Tier3, 50);
        allowedRequestsPerMinute.put(MethodsRateLimitTier.Tier4, 100);
        allowedRequestsPerMinute.put(MethodsRateLimitTier.SpecialTier_auth_test, 600);
        allowedRequestsPerMinute.put(MethodsRateLimitTier.SpecialTier_chat_getPermalink, 600);
        allowedRequestsPerMinute.put(MethodsRateLimitTier.SpecialTier_chat_postMessage, 60); // per channel
        allowedRequestsPerMinute.put(MethodsRateLimitTier.SpecialTier_assistant_threads_setStatus, 60); // per DM
    }

    public static Integer getAllowedRequestsPerMinute(MethodsRateLimitTier tier) {
        if (tier == null) {
            // Just in case, this method returns Tier2 when the given Tier is null to avoid runtime errors
            // Tier2 may not be optimal in the case but it works for most cases
            return allowedRequestsPerMinute.get(MethodsRateLimitTier.Tier2);
        }
        return allowedRequestsPerMinute.get(tier);
    }

}
