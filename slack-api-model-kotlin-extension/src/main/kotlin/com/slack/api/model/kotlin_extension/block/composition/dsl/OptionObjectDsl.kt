package com.slack.api.model.kotlin_extension.block.composition.dsl

import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder
import com.slack.api.model.kotlin_extension.block.composition.OptionObjectBuilder

// same name with the object + "Dsl" suffix
@BlockLayoutBuilder
interface OptionObjectDsl {
    /**
     * An object that represents a single selectable item.
     *
     * @see <a href="https://docs.slack.dev/reference/block-kit/composition-objects/option-object">Option object documentation</a>
     */
    fun option(builder: OptionObjectBuilder.() -> Unit)
}
