package com.slack.api.model.kotlin_extension.block.composition

import com.slack.api.model.block.composition.WorkflowObject.Trigger
import com.slack.api.model.block.composition.WorkflowObject.Trigger.InputParameter
import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder
import com.slack.api.model.kotlin_extension.block.Builder
import com.slack.api.model.kotlin_extension.block.composition.dsl.WorkflowTriggerDsl

// same name with the object + "Builder" suffix
@BlockLayoutBuilder
class WorkflowTriggerBuilder : Builder<Trigger>, WorkflowTriggerDsl {
    private var url: String? = null
    private var customizableInputParameters: MutableList<InputParameter> = mutableListOf()

    override fun url(url: String) {
        this.url = url
    }

    override fun customizableInputParameter(customizableInputParameter: InputParameter) {
        this.customizableInputParameters.add(customizableInputParameter)
    }

    override fun customizableInputParameter(builder: WorkflowTriggerInputParameterBuilder.() -> Unit) {
        this.customizableInputParameters.add(WorkflowTriggerInputParameterBuilder().apply(builder).build())
    }

    override fun build(): Trigger {
        return Trigger.builder()
            .url(url)
            .customizableInputParameters(this.customizableInputParameters)
            .build()
    }
}