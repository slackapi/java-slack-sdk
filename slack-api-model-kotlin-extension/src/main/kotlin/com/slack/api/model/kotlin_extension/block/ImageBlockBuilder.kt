package com.slack.api.model.kotlin_extension.block

import com.slack.api.model.block.ImageBlock
import com.slack.api.model.block.composition.PlainTextObject
import com.slack.api.model.block.composition.SlackFileObject
import com.slack.api.model.kotlin_extension.block.composition.SlackFileObjectBuilder

@BlockLayoutBuilder
class ImageBlockBuilder : Builder<ImageBlock> {
    private var blockId: String? = null
    private var title: PlainTextObject? = null
    private var fallback: String? = null
    private var imageUrl: String? = null
    private var imageWidth: Int? = null
    private var imageHeight: Int? = null
    private var imageBytes: Int? = null
    private var slackFile: SlackFileObject? = null
    private var altText: String? = null

    /**
     * A string acting as a unique identifier for a block. You can use this block_id when you receive an interaction
     * payload to identify the source of the action. If not specified, a block_id will be generated. Maximum length for
     * this field is 255 characters.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/blocks#image">Image block documentation</a>
     */
    fun blockId(id: String) {
        blockId = id
    }

    /**
     * An optional title for the image in the form of a text object. Maximum length for the text in this field is 2000
     * characters.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/blocks#image">Image block documentation</a>
     */
    fun title(text: String, emoji: Boolean? = null) {
        title = PlainTextObject(text, emoji)
    }

    /**
     * Text that should be rendered in the event the image fails to load.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/blocks#image">Image block documentation</a>
     */
    fun fallback(text: String) {
        fallback = text
    }

    /**
     * The URL of the image to be displayed. Maximum length for this field is 3000 characters.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/blocks#image">Image block documentation</a>
     */
    fun imageUrl(url: String) {
        imageUrl = url
    }

    /**
     * The width of the image, in pixels.
     */
    fun imageWidth(width: Int) {
        imageWidth = width
    }

    /**
     * The height of the image, in pixels.
     */
    fun imageHeight(height: Int) {
        imageHeight = height
    }

    /**
     * The size of the image, in bytes.
     */
    fun imageBytes(bytes: Int) {
        imageBytes = bytes
    }

    /**
     * A plain-text summary of the image. This should not contain any markup. Maximum length for this field is 2000 characters.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/blocks#image">Image block documentation</a>
     */
    fun altText(text: String) {
        altText = text
    }

    fun slackFile(slackFile: SlackFileObject) {
        this.slackFile = slackFile
    }

    fun slackFile(builder: SlackFileObjectBuilder.() -> Unit) {
        this.slackFile = SlackFileObjectBuilder().apply(builder).build()
    }

    override fun build(): ImageBlock {
        return ImageBlock.builder()
            .fallback(fallback)
            .imageUrl(imageUrl)
            .imageWidth(imageWidth)
            .imageHeight(imageHeight)
            .imageBytes(imageBytes)
            .slackFile(slackFile)
            .altText(altText)
            .blockId(blockId)
            .title(title)
            .build()
    }
}