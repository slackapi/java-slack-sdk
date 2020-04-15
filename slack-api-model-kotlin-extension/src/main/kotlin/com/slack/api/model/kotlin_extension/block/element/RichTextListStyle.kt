package com.slack.api.model.kotlin_extension.block.element

enum class RichTextListStyle {
    BULLETED {
        override val value = "bullet"
    },
    ORDERED {
        override val value = "ordered"
    };

    abstract val value: String
}
