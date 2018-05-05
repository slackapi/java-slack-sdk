package com.github.seratch.jslack.api.methods.request.rtm;

import com.github.seratch.jslack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * @see <a href="https://api.slack.com/methods/rtm.start">rtm.start</a>
 */
@Data
@Builder
public class RTMStartRequest implements SlackApiRequest {


    /**
     * Authentication token. Requires scope: `rtm:stream`
     */
    private String token;

    /**
     * Exclude latest timestamps for channels, groups, mpims, and ims. Automatically sets `no_unreads` to `1`
     */
    private boolean noLatest;

    /**
     * Return timestamp only for latest message object of each channel (improves performance).
     */
    private boolean simpleLatest;

    /**
     * Set this to `true` to receive the locale for users and channels. Defaults to `false`
     */
    private boolean includeLocale;

    /**
     * Only deliver presence events when requested by subscription.
     * See [presence subscriptions](/docs/presence-and-status#subscriptions).
     */
    private boolean presenceSub;

    /**
     * Skip unread counts for each channel (improves performance).
     */
    private boolean noUnreads;

    /**
     * Batch presence deliveries via subscription.
     * Enabling changes the shape of `presence_change` events. See [batch presence](/docs/presence-and-status#batching).
     */
    private boolean batchPresenceAware;

    /**
     * Returns MPIMs to the client in the API response.
     */
    private boolean mpimAware;

}