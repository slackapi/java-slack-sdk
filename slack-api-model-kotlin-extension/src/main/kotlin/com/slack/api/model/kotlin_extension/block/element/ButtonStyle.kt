package com.slack.api.model.kotlin_extension.block.element

enum class ButtonStyle {
    PRIMARY {
        override val value: String? = "primary"
    },
    DANGER {
        override val value: String? = "danger"
    };

    abstract val value: String?
}
