package com.slack.api.model.kotlin_extension.block.composition.dsl

import com.slack.api.model.block.composition.WorkflowObject
import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder
import com.slack.api.model.kotlin_extension.block.composition.WorkflowTriggerBuilder

@BlockLayoutBuilder
interface WorkflowObjectDsl {
    fun trigger(trigger: WorkflowObject.Trigger)
    fun trigger(builder: WorkflowTriggerBuilder.() -> Unit)
}