package com.slack.api.model.kotlin_extension.block.element

import com.slack.api.model.block.composition.ConfirmationDialogObject
import com.slack.api.model.block.composition.OptionObject
import com.slack.api.model.block.element.OverflowMenuElement
import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder
import com.slack.api.model.kotlin_extension.block.Builder
import com.slack.api.model.kotlin_extension.block.composition.ConfirmationDialogObjectBuilder
import com.slack.api.model.kotlin_extension.block.composition.container.MultiOptionContainer
import com.slack.api.model.kotlin_extension.block.composition.dsl.OptionObjectDsl

@BlockLayoutBuilder
class OverflowMenuElementBuilder : Builder<OverflowMenuElement> {
    private var actionId: String? = null
    private var options: List<OptionObject>? = null
    private var confirm: ConfirmationDialogObject? = null

    /**
     * 	An identifier for the action triggered when a menu option is selected. You can use this when you receive an
     * 	interaction payload to identify the source of the action. Should be unique among all other action_ids used
     * 	elsewhere by your app. Maximum length for this field is 255 characters.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#overflow">Overflow menu element documentation</a>
     */
    fun actionId(id: String) {
        actionId = id
    }

    /**
     * An array of option objects to display in the menu. Maximum number of options is 5, minimum is 2.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#overflow">Overflow menu element documentation</a>
     */
    fun options(builder: OptionObjectDsl.() -> Unit) {
        options = MultiOptionContainer().apply(builder).underlying
    }

    /**
     * A confirm object that defines an optional confirmation dialog that appears after a menu item is selected.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#overflow">Overflow menu element documentation</a>
     */
    fun confirm(builder: ConfirmationDialogObjectBuilder.() -> Unit) {
        confirm = ConfirmationDialogObjectBuilder().apply(builder).build()
    }

    override fun build(): OverflowMenuElement {
        return OverflowMenuElement.builder()
            .actionId(actionId)
            .options(options)
            .confirm(confirm)
            .build()
    }
}