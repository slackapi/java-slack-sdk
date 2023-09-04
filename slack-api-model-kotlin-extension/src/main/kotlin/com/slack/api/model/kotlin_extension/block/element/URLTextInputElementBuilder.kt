package com.slack.api.model.kotlin_extension.block.element

import com.slack.api.model.block.composition.DispatchActionConfig
import com.slack.api.model.block.composition.PlainTextObject
import com.slack.api.model.block.element.URLTextInputElement
import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder
import com.slack.api.model.kotlin_extension.block.Builder
import com.slack.api.model.kotlin_extension.block.composition.DispatchActionConfigBuilder

@BlockLayoutBuilder
class URLTextInputElementBuilder : Builder<URLTextInputElement> {
    private var actionId: String? = null
    private var placeholder: PlainTextObject? = null
    private var initialValue: String? = null
    private var dispatchActionConfig: DispatchActionConfig? = null
    private var _focusOnLoad: Boolean? = null

    /**
     * An identifier for the input value when the parent modal is submitted. You can use this when you receive a
     * view_submission payload to identify the value of the input element. Should be unique among all other action_ids
     * used elsewhere by your app. Maximum length for this field is 255 characters.*
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#input">Plain text input documentation</a>
     */
    fun actionId(id: String) {
        actionId = id
    }

    /**
     * Adds a plain text object to the placeholder field.
     *
     * The placeholder text shown in the plain-text input. Maximum length for the text in this field is 150 characters.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#input">Plain text input documentation</a>
     */
    fun placeholder(text: String, emoji: Boolean? = null) {
        placeholder = PlainTextObject(text, emoji)
    }

    /**
     * The initial value in the plain-text input when it is loaded.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#input">Plain text input documentation</a>
     */
    fun initialValue(value: String) {
        initialValue = value
    }

    /**
     * Determines when a plain-text input element will return a block_actions interaction payload.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/composition-objects#dispatch_action_config">The document</a>
     */
    fun dispatchActionConfig(builder: DispatchActionConfigBuilder.() -> Unit) {
        dispatchActionConfig = DispatchActionConfigBuilder().apply(builder).build()
    }

    /**
     * Indicates whether the element will be set to autofocus within the view object.
     * Only one element can be set to true. Defaults to false.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/composition-objects#dispatch_action_config">The document</a>
     */
    fun focusOnLoad(focusOnLoad: Boolean) {
        _focusOnLoad = focusOnLoad
    }

    override fun build(): URLTextInputElement {
        return URLTextInputElement.builder()
            .actionId(actionId)
            .placeholder(placeholder)
            .initialValue(initialValue)
            .dispatchActionConfig(dispatchActionConfig)
            .focusOnLoad(_focusOnLoad)
            .build()
    }
}
