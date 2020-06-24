package com.slack.api.model.block.element

import com.slack.api.SlackAPIBuilder
import com.slack.api.model.block.composition.ConfirmationDialogBuilder
import com.slack.api.model.block.composition.ConfirmationDialogObject
import com.slack.api.model.block.composition.OptionObject

@SlackAPIBuilder
class CheckboxesBuilder(
        private val actionID: String?
) {
    private var options: List<OptionObject>? = null
    private var initialOptions: List<OptionObject>? = null
    private var confirmationDialog: ConfirmationDialogObject? = null

    fun options(addOptions: OptionContainer.() -> Unit) {
        options = MultiOptionContainerImpl().apply(addOptions).options
    }
    fun initialOptions(addOptions: OptionContainer.() -> Unit) {
        initialOptions = MultiOptionContainerImpl().apply(addOptions).options
    }
    fun confirmationDialog(buildDialog: ConfirmationDialogBuilder.() -> Unit) {
        confirmationDialog = ConfirmationDialogBuilder().apply(buildDialog).build()
    }

    fun build(): CheckboxesElement {
        return CheckboxesElement(actionID, options, initialOptions, confirmationDialog)
    }
}