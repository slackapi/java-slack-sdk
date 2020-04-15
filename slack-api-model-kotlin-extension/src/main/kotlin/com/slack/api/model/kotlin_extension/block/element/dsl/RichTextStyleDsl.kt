package com.slack.api.model.kotlin_extension.block.element.dsl

import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder

@BlockLayoutBuilder
interface RichTextStyleDsl {
    fun style(bold: Boolean = false, italic: Boolean = false, strike: Boolean = false, code: Boolean = false)
}