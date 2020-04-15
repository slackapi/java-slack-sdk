package com.slack.api.model.kotlin_extension.block.element

import com.slack.api.model.block.composition.ConfirmationDialogObject
import com.slack.api.model.block.composition.OptionObject
import com.slack.api.model.block.element.RadioButtonsElement
import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder
import com.slack.api.model.kotlin_extension.block.Builder
import com.slack.api.model.kotlin_extension.block.composition.ConfirmationDialogObjectBuilder
import com.slack.api.model.kotlin_extension.block.composition.OptionObjectBuilder
import com.slack.api.model.kotlin_extension.block.composition.container.MultiOptionContainer
import com.slack.api.model.kotlin_extension.block.composition.dsl.OptionObjectDsl

@BlockLayoutBuilder
class RadioButtonsElementBuilder : Builder<RadioButtonsElement> {
    private var actionId: String? = null
    private var options: List<OptionObject>? = null
    private var initialOption: OptionObject? = null
    private var confirm: ConfirmationDialogObject? = null

    fun actionId(id: String) {
        actionId = id
    }

    fun options(builder: OptionObjectDsl.() -> Unit) {
        options = MultiOptionContainer().apply(builder).underlying
    }

    fun initialOption(builder: OptionObjectBuilder.() -> Unit) {
        initialOption = OptionObjectBuilder().apply(builder).build()
    }

    fun confirm(builder: ConfirmationDialogObjectBuilder.() -> Unit) {
        confirm = ConfirmationDialogObjectBuilder().apply(builder).build()
    }

    override fun build(): RadioButtonsElement {
        return RadioButtonsElement.builder()
                .actionId(actionId)
                .options(options)
                .initialOption(initialOption)
                .confirm(confirm)
                .build()
    }
}