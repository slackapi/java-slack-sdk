package com.slack.api.model.kotlin_extension.block.composition.dsl

import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder
import com.slack.api.model.kotlin_extension.block.composition.TriggerActionOn

@BlockLayoutBuilder
interface DispatchActionConfigDsl {

    fun triggerActionsOn(vararg triggerActions: String)

    fun triggerActionsOn(vararg triggerActions: TriggerActionOn)

}