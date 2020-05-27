package com.slack.api.model.kotlin_extension.block.composition.dsl

import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder
import com.slack.api.model.kotlin_extension.block.composition.OptionGroupObjectBuilder

@BlockLayoutBuilder
interface OptionGroupObjectDsl {
    fun optionGroup(builder: OptionGroupObjectBuilder.() -> Unit)
}