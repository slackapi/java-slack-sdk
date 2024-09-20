package com.slack.api.model.kotlin_extension.block.element

enum class BroadcastRange {
    /**
     * Notifies only the active members of a channel.
     */
    HERE {
        override val value = "here"
    },

    /**
     * Notifies all members of a channel.
     */
    CHANNEL {
        override val value = "channel"
    },

    /**
     * Notifies every person in the #general channel.
     */
    EVERYONE {
        override val value = "everyone"
    };

    abstract val value: String
}
