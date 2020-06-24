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

    /**
     * An identifier for the action triggered when the radio button group is changed. You can use this when you
     * receive an interaction payload to identify the source of the action. Should be unique among all other
     * action_ids used elsewhere by your app. Maximum length for this field is 255 characters.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#radio">Radio buttons element documentation</a>
     */
    fun actionId(id: String) {
        actionId = id
    }

    /**
     * An array of option objects.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#radio">Radio buttons element documentation</a>
     */
    fun options(builder: OptionObjectDsl.() -> Unit) {
        options = MultiOptionContainer().apply(builder).underlying
    }

    /**
     * An option object that exactly matches one of the options within options. This option will be selected when the
     * radio button group initially loads.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#radio">Radio buttons element documentation</a>
     */
    fun initialOption(builder: OptionObjectBuilder.() -> Unit) {
        initialOption = OptionObjectBuilder().apply(builder).build()
    }

    /**
     * A confirm object that defines an optional confirmation dialog that appears after clicking one of the radio
     * buttons in this element.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#radio">Radio buttons element documentation</a>
     */
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