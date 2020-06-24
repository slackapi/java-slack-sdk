package com.slack.api.model.kotlin_extension.block.element

enum class ButtonStyle {
    /**
     * Primary gives buttons a green outline and text, ideal for affirmation or confirmation actions. primary should
     * only be used for one button within a set.
     */
    PRIMARY {
        override val value: String? = "primary"
    },

    /**
     * Danger gives buttons a red outline and text, and should be used when the action is destructive. Use danger even
     * more sparingly than primary.
     */
    DANGER {
        override val value: String? = "danger"
    };

    abstract val value: String?
}
