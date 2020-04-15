package com.slack.api.model.kotlin_extension.block.composition.dsl

import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder
import com.slack.api.model.kotlin_extension.block.composition.OptionObjectBuilder

// same name with the object + "Dsl" suffix
@BlockLayoutBuilder
interface OptionObjectDsl {
    fun option(builder: OptionObjectBuilder.() -> Unit)
}
