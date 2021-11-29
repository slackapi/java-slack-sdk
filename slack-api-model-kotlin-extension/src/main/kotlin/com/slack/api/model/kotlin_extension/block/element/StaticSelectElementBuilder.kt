package com.slack.api.model.kotlin_extension.block.element

import com.slack.api.model.block.composition.ConfirmationDialogObject
import com.slack.api.model.block.composition.OptionGroupObject
import com.slack.api.model.block.composition.OptionObject
import com.slack.api.model.block.composition.PlainTextObject
import com.slack.api.model.block.element.StaticSelectElement
import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder
import com.slack.api.model.kotlin_extension.block.Builder
import com.slack.api.model.kotlin_extension.block.composition.ConfirmationDialogObjectBuilder
import com.slack.api.model.kotlin_extension.block.composition.OptionObjectBuilder
import com.slack.api.model.kotlin_extension.block.composition.container.MultiOptionContainer
import com.slack.api.model.kotlin_extension.block.composition.container.MultiOptionGroupObjectContainer
import com.slack.api.model.kotlin_extension.block.composition.dsl.OptionGroupObjectDsl
import com.slack.api.model.kotlin_extension.block.composition.dsl.OptionObjectDsl

@BlockLayoutBuilder
class StaticSelectElementBuilder : Builder<StaticSelectElement> {
    private var placeholder: PlainTextObject? = null
    private var actionId: String? = null
    private var options: List<OptionObject>? = null
    private var optionGroups: List<OptionGroupObject>? = null
    private var initialOption: OptionObject? = null
    private var confirm: ConfirmationDialogObject? = null
    private var _focusOnLoad: Boolean? = null

    /**
     * Adds a plain text object to the placeholder field.
     *
     * The placeholder text shown on the menu. Maximum length for the text in this field is 150 characters.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#static_select">Static select element documentation</a>
     */
    fun placeholder(text: String, emoji: Boolean? = null) {
        placeholder = PlainTextObject(text, emoji)
    }

    /**
     * An identifier for the action triggered when a menu option is selected. You can use this when you receive an
     * interaction payload to identify the source of the action. Should be unique among all other action_ids used
     * elsewhere by your app. Maximum length for this field is 255 characters.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#static_select">Static select element documentation</a>
     */
    fun actionId(id: String) {
        actionId = id
    }

    /**
     * An array of option objects. Maximum number of options is 100. If option_groups is specified, this field should
     * not be.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#static_select">Static select element documentation</a>
     */
    fun options(builder: OptionObjectDsl.() -> Unit) {
        options = MultiOptionContainer().apply(builder).underlying
    }

    /**
     * An array of option group objects. Maximum number of option groups is 100. If options is specified, this field
     * should not be.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#static_select">Static select element documentation</a>
     */
    fun optionGroups(builder: OptionGroupObjectDsl.() -> Unit) {
        optionGroups = MultiOptionGroupObjectContainer().apply(builder).underlying
    }

    /**
     * A single option that exactly matches one of the options within options or option_groups. This option will be
     * selected when the menu initially loads.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#static_select">Static select element documentation</a>
     */
    fun initialOption(builder: OptionObjectBuilder.() -> Unit) {
        initialOption = OptionObjectBuilder().apply(builder).build()
    }

    /**
     * A confirm object that defines an optional confirmation dialog that appears after a menu item is selected.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#static_select">Static select element documentation</a>
     */
    fun confirm(builder: ConfirmationDialogObjectBuilder.() -> Unit) {
        confirm = ConfirmationDialogObjectBuilder().apply(builder).build()
    }

    /**
     * Indicates whether the element will be set to auto focus within the view object.
     * Only one element can be set to true. Defaults to false.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#static_select">Static select element documentation</a>
     */
    fun focusOnLoad(focusOnLoad: Boolean) {
        _focusOnLoad = focusOnLoad
    }

    override fun build(): StaticSelectElement {
        return StaticSelectElement.builder()
                .placeholder(placeholder)
                .actionId(actionId)
                .options(options)
                .optionGroups(optionGroups)
                .initialOption(initialOption)
                .confirm(confirm)
                .focusOnLoad(_focusOnLoad)
                .build()
    }
}