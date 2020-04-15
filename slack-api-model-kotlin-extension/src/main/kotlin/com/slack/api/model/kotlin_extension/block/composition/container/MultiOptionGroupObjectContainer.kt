package com.slack.api.model.kotlin_extension.block.composition.container

import com.slack.api.model.block.composition.OptionGroupObject
import com.slack.api.model.kotlin_extension.block.composition.OptionGroupObjectBuilder
import com.slack.api.model.kotlin_extension.block.composition.dsl.OptionGroupObjectDsl

class MultiOptionGroupObjectContainer : OptionGroupObjectDsl {
    val underlying = mutableListOf<OptionGroupObject>()

    override fun optionGroup(builder: OptionGroupObjectBuilder.() -> Unit) {
        underlying += OptionGroupObjectBuilder().apply(builder).build()
    }
}