package com.slack.api.model.kotlin_extension.block.element

enum class ConversationType {
    IM {
        override val value = "im"
    },
    MULTIPARTY_IM {
        override val value = "mpim"
    },
    PRIVATE {
        override val value = "private"
    },
    PUBLIC {
        override val value = "public"
    };

    abstract val value: String
}