package com.slack.api.model.kotlin_extension.block.composition.dsl

import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder

@BlockLayoutBuilder
interface PlainTextObjectDsl {

    /**
     * General purpose text field.
     *
     * @see PlainTextObjectDsl.plainText
     */
    fun text(text: String, emoji: Boolean? = null, verbatim: Boolean? = null) {
        plainText(text, emoji)
    }

    /**
     * Sets plain_text type text object in the text field with the given information.
     */
    fun plainText(text: String, emoji: Boolean? = null)
}