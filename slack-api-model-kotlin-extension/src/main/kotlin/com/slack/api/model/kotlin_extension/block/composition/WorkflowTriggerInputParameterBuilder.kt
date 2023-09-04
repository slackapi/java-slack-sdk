package com.slack.api.model.kotlin_extension.block.composition

import com.slack.api.model.block.composition.WorkflowObject.Trigger.InputParameter
import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder
import com.slack.api.model.kotlin_extension.block.Builder
import com.slack.api.model.kotlin_extension.block.composition.dsl.WorkflowTriggerInputParameterDsl

// same name with the object + "Builder" suffix
@BlockLayoutBuilder
class WorkflowTriggerInputParameterBuilder : Builder<InputParameter>, WorkflowTriggerInputParameterDsl {
    private var name: String? = null
    private var value: String? = null

    override fun name(name: String) {
        this.name = name
    }

    override fun value(value: String) {
        this.value = value
    }

    override fun build(): InputParameter {
        return InputParameter.builder()
            .name(name)
            .value(value)
            .build()
    }
}