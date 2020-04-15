package com.slack.api.model.kotlin_extension.block.element.container

import com.slack.api.model.block.element.RichTextSectionElement
import com.slack.api.model.kotlin_extension.block.element.dsl.RichTextStyleDsl

class SingleRichTextStyleContainer : RichTextStyleDsl {
    var underlying: RichTextSectionElement.TextStyle? = null

    override fun style(bold: Boolean, italic: Boolean, strike: Boolean, code: Boolean) {
        underlying = RichTextSectionElement.TextStyle.builder()
                .bold(bold)
                .italic(italic)
                .strike(strike)
                .code(code)
                .build()
    }
}