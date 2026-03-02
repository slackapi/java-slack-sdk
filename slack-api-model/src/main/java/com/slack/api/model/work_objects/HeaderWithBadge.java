package com.slack.api.model.work_objects;

import com.slack.api.util.annotation.Required;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class HeaderWithBadge {
    /**
     * Plain text displayed as the header.
     */
    @Required String text;
    Badge badge;

    @Value
    @Builder
    public static class Badge {
        /**
         * Plain text displayed inside the badge.
         */
        @Required String text;
        /**
         * Color of the text.
         */
        String Color;
        /**
         * Color of the badge.
         */
        String backgroundColor;
    }
}
