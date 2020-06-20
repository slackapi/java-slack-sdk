package com.slack.api.model.kotlin_extension.block.element

enum class RichTextListStyle {
    /**
     * A bulleted list, i.e. one that has dots for each list item.
     */
    BULLETED {
        override val value = "bullet"
    },

    /**
     * An ordered list, i.e. one that has an increasing number next to each list item.
     */
    ORDERED {
        override val value = "ordered"
    };

    abstract val value: String
}
