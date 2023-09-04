package com.slack.api.model.kotlin_extension.block.composition

import com.slack.api.model.block.composition.DispatchActionConfig
import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder
import com.slack.api.model.kotlin_extension.block.Builder
import com.slack.api.model.kotlin_extension.block.composition.dsl.DispatchActionConfigDsl

// same name with the object + "Builder" suffix
@BlockLayoutBuilder
class DispatchActionConfigBuilder() : Builder<DispatchActionConfig>, DispatchActionConfigDsl {
    private var triggerActionsOn: MutableList<String> = mutableListOf()

    override fun triggerActionsOn(vararg triggerActions: String) {
        this.triggerActionsOn = triggerActions.toMutableList()
    }

    override fun triggerActionsOn(vararg triggerActions: TriggerActionOn) {
        this.triggerActionsOn = triggerActions.mapNotNull { it.value }.toMutableList()
    }

    override fun build(): DispatchActionConfig {
        return DispatchActionConfig.builder()
            .triggerActionsOn(triggerActionsOn)
            .build()
    }
}