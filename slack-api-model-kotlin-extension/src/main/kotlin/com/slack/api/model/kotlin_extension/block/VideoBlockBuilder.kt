package com.slack.api.model.kotlin_extension.block

import com.slack.api.model.block.VideoBlock
import com.slack.api.model.block.composition.PlainTextObject
import com.slack.api.model.kotlin_extension.block.composition.container.SinglePlainTextObjectContainer
import com.slack.api.model.kotlin_extension.block.composition.dsl.PlainTextObjectDsl

// same name with the object + "Builder" suffix
@BlockLayoutBuilder
class VideoBlockBuilder : Builder<VideoBlock> {
    private var _blockId: String? = null
    private var _title: PlainTextObject? = null
    private var _titleUrl: String? = null
    private var _description: PlainTextObject? = null
    private var _altText: String? = null
    private var _videoUrl: String? = null
    private var _thumbnailUrl: String? = null
    private var _authorName: String? = null
    private var _providerName: String? = null
    private var _providerIconUrl: String? = null

    /**
     * 	A string acting as a unique identifier for a block. You can use this block_id when you receive an interaction
     * 	payload to identify the source of the action. If not specified, one will be generated. Maximum length for
     * 	this field is 255 characters. block_id should be unique for each message and each iteration of a message.
     * 	If a message is updated, use a new block_id.
     *
     * 	@see <a href="https://docs.slack.dev/reference/block-kit/blocks/video-block">Section Block Documentation</a>
     */
    fun blockId(blockId: String) {
        _blockId = blockId
    }

    fun title(title: PlainTextObject) {
        _title = title
    }

    fun title(builder: PlainTextObjectDsl.() -> Unit) {
        _title = SinglePlainTextObjectContainer().apply(builder).underlying
    }

    fun titleUrl(titleUrl: String) {
        _titleUrl = titleUrl
    }

    fun description(description: PlainTextObject) {
        _description = description
    }

    fun description(builder: PlainTextObjectDsl.() -> Unit) {
        _description = SinglePlainTextObjectContainer().apply(builder).underlying
    }

    fun altText(altText: String) {
        _altText = altText
    }

    fun videoUrl(videoUrl: String) {
        _videoUrl = videoUrl
    }

    fun thumbnailUrl(thumbnailUrl: String) {
        _thumbnailUrl = thumbnailUrl
    }

    fun authorName(authorName: String) {
        _authorName = authorName
    }

    fun providerName(providerName: String) {
        _providerName = providerName
    }

    fun providerIconUrl(providerIconUrl: String) {
        _providerIconUrl = providerIconUrl
    }

    override fun build(): VideoBlock {
        return VideoBlock.builder()
            .blockId(_blockId)
            .title(_title)
            .titleUrl(_titleUrl)
            .description(_description)
            .altText(_altText)
            .videoUrl(_videoUrl)
            .thumbnailUrl(_thumbnailUrl)
            .authorName(_authorName)
            .providerName(_providerName)
            .providerIconUrl(_providerIconUrl)
            .build()
    }
}
