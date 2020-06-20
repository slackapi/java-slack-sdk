package com.slack.api.model.kotlin_extension.block.element

enum class ConversationType {
    /**
     * A private message conversation directly between two people.
     */
    IM {
        override val value = "im"
    },

    /**
     * A private message conversation created explicitly with a group of people.
     */
    MULTIPARTY_IM {
        override val value = "mpim"
    },

    /**
     * A private slack channel.
     */
    PRIVATE {
        override val value = "private"
    },

    /**
     * A public slack channel.
     */
    PUBLIC {
        override val value = "public"
    };

    abstract val value: String
}