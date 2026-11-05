package com.slack.api.model.kotlin_extension.block.element

import com.slack.api.model.block.composition.ConfirmationDialogObject
import com.slack.api.model.block.composition.PlainTextObject
import com.slack.api.model.block.element.ButtonElement
import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder
import com.slack.api.model.kotlin_extension.block.Builder
import com.slack.api.model.kotlin_extension.block.composition.ConfirmationDialogObjectBuilder

// same name with the object + "Builder" suffix
@BlockLayoutBuilder
class ButtonElementBuilder : Builder<ButtonElement> {
    private var actionId: String? = null
    private var text: PlainTextObject? = null
    private var url: String? = null
    private var value: String? = null
    private var style: String? = null
    private var confirm: ConfirmationDialogObject? = null
    private var accessibilityLabel: String? = null
    private var agentPrompt: String? = null
    private var agentPromptDisplay: String? = null
    private var visibleToUserIds: List<String>? = null

    /**
     * An identifier for this action. You can use this when you receive an interaction payload to identify the source
     * of the action. Should be unique among all other action_ids used elsewhere by your app. Maximum length for this
     * field is 255 characters.
     *
     * @see <a href="https://docs.slack.dev/reference/block-kit/block-elements/button-element">Button element documentation</a>
     */
    fun actionId(id: String) {
        actionId = id
    }

    /**
     * Inserts a plain text object in the text field for this button.
     *
     * Defines the button's text. Maximum length for the text in this field is 75 characters.
     *
     * @see <a href="https://docs.slack.dev/reference/block-kit/block-elements/button-element">Button element documentation</a>
     */
    fun text(text: String, emoji: Boolean? = null) {
        this.text = PlainTextObject(text, emoji)
    }

    /**
     * A URL to load in the user's browser when the button is clicked. Maximum length for this field is 3000
     * characters. If you're using url, you'll still receive an interaction payload and will need to send an
     * acknowledgement response.
     *
     * @see <a href="https://docs.slack.dev/reference/block-kit/block-elements/button-element">Button element documentation</a>
     */
    fun url(text: String) {
        url = text
    }

    /**
     * The value to send along with the interaction payload. Maximum length for this field is 2000 characters.
     *
     * @see <a href="https://docs.slack.dev/reference/block-kit/block-elements/button-element">Button element documentation</a>
     */
    fun value(text: String) {
        value = text
    }

    /**
     * Decorates buttons with alternative visual color schemes. Use this option with restraint.
     *
     * This implementation uses a type safe enum value for the button style.
     *
     * @see <a href="https://docs.slack.dev/reference/block-kit/block-elements/button-element">Button element documentation</a>
     */
    fun style(style: ButtonStyle) {
        this.style = style.value
    }

    /**
     * Decorates buttons with alternative visual color schemes. Use this option with restraint.
     *
     * This implementation uses a string for the button style. This might be used if new button styles are introduced
     * and the enum is insufficient.
     *
     * @see <a href="https://docs.slack.dev/reference/block-kit/block-elements/button-element">Button element documentation</a>
     */
    fun style(style: String) {
        this.style = style
    }

    /**
     * A confirm object that defines an optional confirmation dialog after the button is clicked.
     *
     * @see <a href="https://docs.slack.dev/reference/block-kit/block-elements/button-element">Button element documentation</a>
     */
    fun confirm(builder: ConfirmationDialogObjectBuilder.() -> Unit) {
        confirm = ConfirmationDialogObjectBuilder().apply(builder).build()
    }

    /**
     * A label for longer descriptive text about a button element.
     * This label will be read out by screen readers instead of the button text object.
     * Maximum length for this field is 75 characters.
     *
     * @see <a href="https://docs.slack.dev/reference/block-kit/block-elements/button-element">Button element documentation</a>
     */
    fun accessibilityLabel(accessibilityLabel: String) {
        this.accessibilityLabel = accessibilityLabel
    }

    /**
     * A prompt sent to Slackbot when the button is clicked, if Slackbot AI is available to the user.
     * Maximum length for this field is 4000 characters.
     *
     * @see <a href="https://docs.slack.dev/reference/block-kit/block-elements/button-element">Button element documentation</a>
     */
    fun agentPrompt(agentPrompt: String) {
        this.agentPrompt = agentPrompt
    }

    /**
     * Short text shown instead of the full agent_prompt in the user's Slackbot message.
     * Requires agent_prompt and defaults to the button's text.
     *
     * @see <a href="https://docs.slack.dev/reference/block-kit/block-elements/button-element">Button element documentation</a>
     */
    fun agentPromptDisplay(agentPromptDisplay: String) {
        this.agentPromptDisplay = agentPromptDisplay
    }

    /**
     * An array of user IDs for which the button appears. If not provided, the button is visible to all users.
     *
     * @see <a href="https://docs.slack.dev/reference/block-kit/block-elements/button-element">Button element documentation</a>
     */
    fun visibleToUserIds(visibleToUserIds: List<String>) {
        this.visibleToUserIds = visibleToUserIds
    }

    /**
     * An array of user IDs for which the button appears. If not provided, the button is visible to all users.
     *
     * @see <a href="https://docs.slack.dev/reference/block-kit/block-elements/button-element">Button element documentation</a>
     */
    fun visibleToUserIds(vararg visibleToUserIds: String) {
        this.visibleToUserIds = visibleToUserIds.toList()
    }

    override fun build(): ButtonElement {
        return ButtonElement.builder()
            .actionId(actionId)
            .url(url)
            .value(value)
            .text(text)
            .style(style)
            .confirm(confirm)
            .accessibilityLabel(accessibilityLabel)
            .agentPrompt(agentPrompt)
            .agentPromptDisplay(agentPromptDisplay)
            .visibleToUserIds(visibleToUserIds)
            .build()
    }
}