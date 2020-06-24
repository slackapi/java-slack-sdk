package com.slack.api.model.kotlin_extension.block.composition.dsl

import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder
import com.slack.api.model.kotlin_extension.block.composition.OptionGroupObjectBuilder

@BlockLayoutBuilder
interface OptionGroupObjectDsl {
    /**
     * Provides a way to group options.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/composition-objects#option_group">Option group object documentation</a>
     */
    fun optionGroup(builder: OptionGroupObjectBuilder.() -> Unit)
}