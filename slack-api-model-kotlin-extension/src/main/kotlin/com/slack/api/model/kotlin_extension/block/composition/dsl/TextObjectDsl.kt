package com.slack.api.model.kotlin_extension.block.composition.dsl

import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder

/**
 * Interface for adding one to many "text" elements to this container
 */
@BlockLayoutBuilder
interface TextObjectDsl {

    /**
     * Sets plain_text type text object with the given information.
     */
    fun plainText(text: String, emoji: Boolean? = null)

    /**
     * Sets mrkdwn type text object with the given information.
     */
    fun markdownText(text: String, verbatim: Boolean? = null)

}