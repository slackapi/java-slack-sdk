package com.slack.api.model.kotlin_extension.block.container

import com.slack.api.model.block.ContextBlockElement
import com.slack.api.model.block.composition.MarkdownTextObject
import com.slack.api.model.block.composition.PlainTextObject
import com.slack.api.model.block.element.ImageElement
import com.slack.api.model.kotlin_extension.block.dsl.ContextBlockElementDsl

class MultiContextBlockElementContainer : ContextBlockElementDsl {
    val underlying = mutableListOf<ContextBlockElement>()

    override fun image(
        imageUrl: String?,
        altText: String?,
        fallback: String?,
        imageWidth: Int?,
        imageHeight: Int?,
        imageBytes: Int?
    ) {
        underlying += ImageElement.builder()
            .imageUrl(imageUrl)
            .altText(altText)
            .fallback(fallback)
            .imageWidth(imageWidth)
            .imageHeight(imageHeight)
            .imageBytes(imageBytes)
            .build()
    }

    override fun plainText(text: String, emoji: Boolean?) {
        underlying += PlainTextObject(text, emoji)
    }

    override fun markdownText(text: String, verbatim: Boolean?) {
        underlying += MarkdownTextObject(text, verbatim)
    }
}