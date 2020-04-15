package com.slack.api.model.kotlin_extension.block.element

import com.slack.api.model.block.composition.ConfirmationDialogObject
import com.slack.api.model.block.composition.OptionObject
import com.slack.api.model.block.element.CheckboxesElement
import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder
import com.slack.api.model.kotlin_extension.block.Builder
import com.slack.api.model.kotlin_extension.block.composition.ConfirmationDialogObjectBuilder
import com.slack.api.model.kotlin_extension.block.composition.container.MultiOptionContainer
import com.slack.api.model.kotlin_extension.block.composition.dsl.OptionObjectDsl

// same name with the object + "Builder" suffix
@BlockLayoutBuilder
class CheckboxesElementBuilder : Builder<CheckboxesElement> {
    private var actionId: String? = null
    private var options: List<OptionObject>? = null
    private var initialOptions: List<OptionObject>? = null
    private var confirm: ConfirmationDialogObject? = null

    fun actionId(id: String) {
        actionId = id
    }

    fun options(builder: OptionObjectDsl.() -> Unit) {
        options = MultiOptionContainer().apply(builder).underlying
    }

    fun initialOptions(builder: OptionObjectDsl.() -> Unit) {
        initialOptions = MultiOptionContainer().apply(builder).underlying
    }

    fun confirm(builder: ConfirmationDialogObjectBuilder.() -> Unit) {
        confirm = ConfirmationDialogObjectBuilder().apply(builder).build()
    }

    override fun build(): CheckboxesElement {
        return CheckboxesElement.builder()
                .actionId(actionId)
                .options(options)
                .initialOptions(initialOptions)
                .confirm(confirm)
                .build()
    }
}