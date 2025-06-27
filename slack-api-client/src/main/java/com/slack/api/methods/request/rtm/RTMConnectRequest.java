package com.slack.api.methods.request.rtm;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * @see <a href="https://docs.slack.dev/reference/methods/rtm.connect">rtm.connect</a>
 */
@Data
@Builder
public class RTMConnectRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `rtm:stream`
     */
    private String token;

    /**
     * Only deliver presence events when requested by subscription.
     * See [presence subscriptions](/docs/presence-and-status#subscriptions).
     */
    private boolean presenceSub;

    /**
     * Batch presence deliveries via subscription.
     * Enabling changes the shape of `presence_change` events. See [batch presence](/docs/presence-and-status#batching).
     */
    private boolean batchPresenceAware;

}