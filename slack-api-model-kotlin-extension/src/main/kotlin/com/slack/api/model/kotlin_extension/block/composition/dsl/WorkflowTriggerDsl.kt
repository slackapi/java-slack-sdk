package com.slack.api.model.kotlin_extension.block.composition.dsl

import com.slack.api.model.block.composition.WorkflowObject.Trigger.InputParameter
import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder
import com.slack.api.model.kotlin_extension.block.composition.WorkflowTriggerInputParameterBuilder

@BlockLayoutBuilder
interface WorkflowTriggerDsl {
    fun url(url: String)
    fun customizableInputParameter(customizableInputParameter: InputParameter)
    fun customizableInputParameter(builder: WorkflowTriggerInputParameterBuilder.() -> Unit)
}