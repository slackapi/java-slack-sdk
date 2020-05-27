package com.slack.api.model.kotlin_extension.block.composition.dsl

import com.slack.api.model.block.composition.MarkdownTextObject
import com.slack.api.model.block.composition.PlainTextObject
import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder

/**
 * Interface for adding one to many "text" elements to this container
 */
@BlockLayoutBuilder
interface TextObjectDsl {

    fun text(type: String, text: String, emoji: Boolean? = null, verbatim: Boolean? = null) {
        when (type) {
            PlainTextObject.TYPE -> {
                plainText(text, emoji)
            }
            MarkdownTextObject.TYPE -> {
                markdownText(text, verbatim)
            }
            else -> {
                throw IllegalArgumentException("Unknown type: $type")
            }
        }
    }

    /**
     * Sets plain_text type text object with the given information.
     */
    fun plainText(text: String, emoji: Boolean? = null)

    /**
     * Sets mrkdwn type text object with the given information.
     */
    fun markdownText(text: String, verbatim: Boolean? = null)

}