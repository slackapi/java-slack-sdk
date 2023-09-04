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
    private var _focusOnLoad: Boolean? = null

    /**
     * An identifier for the action triggered when the checkbox group is changed. You can use this when you receive
     * an interaction payload to identify the source of the action. Should be unique among all other action_ids used
     * elsewhere by your app. Maximum length for this field is 255 characters.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#checkboxes">Checkboxes element documentation</a>
     */
    fun actionId(id: String) {
        actionId = id
    }

    /**
     * An array of option objects.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#checkboxes">Checkboxes element documentation</a>
     */
    fun options(builder: OptionObjectDsl.() -> Unit) {
        options = MultiOptionContainer().apply(builder).underlying
    }

    /**
     *An array of option objects that exactly matches one or more of the options within options. These options will be selected when the checkbox group initially loads.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#checkboxes">Checkboxes element documentation</a>
     */
    fun initialOptions(builder: OptionObjectDsl.() -> Unit) {
        initialOptions = MultiOptionContainer().apply(builder).underlying
    }

    /**
     * A confirm object that defines an optional confirmation dialog that appears after clicking one of the checkboxes in this element.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#checkboxes">Checkboxes element documentation</a>
     */
    fun confirm(builder: ConfirmationDialogObjectBuilder.() -> Unit) {
        confirm = ConfirmationDialogObjectBuilder().apply(builder).build()
    }

    /**
     * Indicates whether the element will be set to autofocus within the view object.
     * Only one element can be set to true. Defaults to false.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#checkboxes">Checkboxes element documentation</a>
     */
    fun focusOnLoad(focusOnLoad: Boolean) {
        _focusOnLoad = focusOnLoad
    }

    override fun build(): CheckboxesElement {
        return CheckboxesElement.builder()
            .actionId(actionId)
            .options(options)
            .initialOptions(initialOptions)
            .confirm(confirm)
            .focusOnLoad(_focusOnLoad)
            .build()
    }
}
