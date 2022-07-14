package com.slack.api.model.kotlin_extension.block.composition.container

import com.slack.api.model.block.composition.PlainTextObject
import com.slack.api.model.kotlin_extension.block.composition.dsl.PlainTextObjectDsl

/**
 * Supports a TextObjectContainer holding exactly one text object
 */
class SinglePlainTextObjectContainer : PlainTextObjectDsl {
    var underlying: PlainTextObject? = null

    override fun plainText(text: String, emoji: Boolean?) {
        underlying = PlainTextObject(text, emoji)
    }
}
