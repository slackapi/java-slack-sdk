package com.slack.api.model.kotlin_extension.block.dsl

import com.slack.api.model.kotlin_extension.block.composition.dsl.TextObjectDsl

interface ContextBlockElementDsl : TextObjectDsl {
    fun image(
            imageUrl: String? = null,
            altText: String? = null,
            fallback: String? = null,
            imageWidth: Int? = null,
            imageHeight: Int? = null,
            imageBytes: Int? = null
    )
}