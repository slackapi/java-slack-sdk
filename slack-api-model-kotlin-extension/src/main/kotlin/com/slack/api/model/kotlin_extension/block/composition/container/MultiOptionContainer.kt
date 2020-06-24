package com.slack.api.model.kotlin_extension.block.composition.container

import com.slack.api.model.block.composition.OptionObject
import com.slack.api.model.kotlin_extension.block.composition.OptionObjectBuilder
import com.slack.api.model.kotlin_extension.block.composition.dsl.OptionObjectDsl

/**
 * Supports an OptionContainer that can have one to many options.
 */
class MultiOptionContainer : OptionObjectDsl {
    val underlying = mutableListOf<OptionObject>()

    override fun option(builder: OptionObjectBuilder.() -> Unit) {
        underlying += OptionObjectBuilder().apply(builder).build()
    }
}