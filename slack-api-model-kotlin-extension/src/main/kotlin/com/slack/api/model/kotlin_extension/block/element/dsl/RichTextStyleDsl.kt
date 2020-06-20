package com.slack.api.model.kotlin_extension.block.element.dsl

import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder

@BlockLayoutBuilder
interface RichTextStyleDsl {
    /**
     * Defines the text styling of this rich text element.
     */
    fun style(bold: Boolean = false, italic: Boolean = false, strike: Boolean = false, code: Boolean = false)
}