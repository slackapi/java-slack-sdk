package com.slack.api.model.kotlin_extension.block.composition

import com.slack.api.model.block.composition.WorkflowObject
import com.slack.api.model.block.composition.WorkflowObject.Trigger
import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder
import com.slack.api.model.kotlin_extension.block.Builder
import com.slack.api.model.kotlin_extension.block.composition.dsl.WorkflowObjectDsl

// same name with the object + "Builder" suffix
@BlockLayoutBuilder
class WorkflowObjectBuilder() : Builder<WorkflowObject>, WorkflowObjectDsl {
    private var trigger: Trigger? = null

    override fun trigger(trigger: Trigger) {
        this.trigger = trigger
    }

    override fun trigger(builder: WorkflowTriggerBuilder.() -> Unit) {
        this.trigger = WorkflowTriggerBuilder().apply(builder).build()
    }


    override fun build(): WorkflowObject {
        return WorkflowObject.builder()
            .trigger(trigger)
            .build()
    }
}