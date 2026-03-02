package com.slack.api.model.work_objects;

import com.google.common.base.Preconditions;
import com.slack.api.util.annotation.Required;
import com.slack.api.util.predicate.IsValidChannelIdPredicate;
import lombok.Builder;
import lombok.Value;

import static com.slack.api.util.predicate.IsValidChannelIdPredicate.CHANNEL_ID_REGEX;

/**
 * Representation of a Slack channel on a work object.
 */
@Value
public class Channel {
    private static final IsValidChannelIdPredicate isValidChannelId = new IsValidChannelIdPredicate();

    @Required(validator = IsValidChannelIdPredicate.class)
    String channelId;

    @Builder
    public Channel(String channelId) {
        Preconditions.checkArgument(
                isValidChannelId.test(channelId),
                String.format("Invalid slack channelId %s, required format %s", channelId, CHANNEL_ID_REGEX)
        );
        this.channelId = channelId;
    }
}
