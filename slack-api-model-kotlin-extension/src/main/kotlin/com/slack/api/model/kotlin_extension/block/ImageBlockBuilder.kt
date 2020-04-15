package com.slack.api.model.kotlin_extension.block

import com.slack.api.model.block.ImageBlock
import com.slack.api.model.block.composition.PlainTextObject

@BlockLayoutBuilder
class ImageBlockBuilder : Builder<ImageBlock> {
    private var blockId: String? = null
    private var title: PlainTextObject? = null
    private var fallback: String? = null
    private var imageUrl: String? = null
    private var imageWidth: Int? = null
    private var imageHeight: Int? = null
    private var imageBytes: Int? = null
    private var altText: String? = null

    fun blockId(id: String) {
        blockId = id
    }

    fun title(text: String, emoji: Boolean? = null) {
        title = PlainTextObject(text, emoji)
    }

    fun fallback(text: String) {
        fallback = text
    }

    fun imageUrl(url: String) {
        imageUrl = url
    }

    fun imageWidth(width: Int) {
        imageWidth = width
    }

    fun imageHeight(height: Int) {
        imageHeight = height
    }

    fun imageBytes(bytes: Int) {
        imageBytes = bytes
    }

    fun altText(text: String) {
        altText = text
    }

    override fun build(): ImageBlock {
        return ImageBlock.builder()
                .fallback(fallback)
                .imageUrl(imageUrl)
                .imageWidth(imageWidth)
                .imageHeight(imageHeight)
                .imageBytes(imageBytes)
                .altText(altText)
                .blockId(blockId)
                .title(title)
                .build()
    }
}