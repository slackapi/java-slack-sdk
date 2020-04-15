package com.slack.api.model.kotlin_extension.block.composition.dsl

import com.slack.api.model.kotlin_extension.block.composition.OptionGroupObjectBuilder

interface OptionGroupObjectDsl {
    fun optionGroup(builder: OptionGroupObjectBuilder.() -> Unit)
}