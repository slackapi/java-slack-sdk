package com.slack.api.model.kotlin_extension.block.composition.dsl

import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder

@BlockLayoutBuilder
interface WorkflowTriggerInputParameterDsl {
    fun name(name: String)
    fun value(value: String)
}