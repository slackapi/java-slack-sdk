package com.slack.api.model.kotlin_extension.block.dsl

import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder
import com.slack.api.model.kotlin_extension.block.composition.dsl.TextObjectDsl

@BlockLayoutBuilder
interface ContextBlockElementDsl : TextObjectDsl {
    /**
     * An element to insert an image as part of a larger block of content. If you want a block with only an image in
     * it, you're looking for the image block.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#image">Image element documentation</a>
     */
    fun image(
        imageUrl: String? = null,
        altText: String? = null,
        fallback: String? = null,
        imageWidth: Int? = null,
        imageHeight: Int? = null,
        imageBytes: Int? = null
    )
}