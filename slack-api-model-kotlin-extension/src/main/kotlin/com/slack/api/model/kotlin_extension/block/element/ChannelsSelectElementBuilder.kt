package com.slack.api.model.kotlin_extension.block.element

import com.slack.api.model.block.composition.ConfirmationDialogObject
import com.slack.api.model.block.composition.PlainTextObject
import com.slack.api.model.block.element.ChannelsSelectElement
import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder
import com.slack.api.model.kotlin_extension.block.Builder
import com.slack.api.model.kotlin_extension.block.composition.ConfirmationDialogObjectBuilder

// same name with the object + "Builder" suffix
@BlockLayoutBuilder
class ChannelsSelectElementBuilder : Builder<ChannelsSelectElement> {
    private var placeholder: PlainTextObject? = null
    private var actionId: String? = null
    private var initialChannel: String? = null
    private var responseUrlEnabled: Boolean? = null
    private var confirm: ConfirmationDialogObject? = null

    /**
     * Fills the placeholder field with a plain text object.
     *
     * The placeholder text shown on the menu. Maximum length for the text in this field is 150 characters.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#channel_select">Channels select element documentation</a>
     */
    fun placeholder(text: String, emoji: Boolean? = null) {
        placeholder = PlainTextObject(text, emoji)
    }

    /**
     * An identifier for the action triggered when a menu option is selected. You can use this when you receive an
     * interaction payload to identify the source of the action. Should be unique among all other action_ids used
     * elsewhere by your app. Maximum length for this field is 255 characters.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#channel_select">Channels select element documentation</a>
     */
    fun actionId(id: String) {
        actionId = id
    }

    /**
     * The ID of any valid public channel to be pre-selected when the menu loads.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#channel_select">Channels select element documentation</a>
     */
    fun initialChannel(channel: String) {
        initialChannel = channel
    }

    /**
     * <b>This field only works with menus in input blocks in modals.</b>
     *
     * When set to true, the view_submission payload from the menu's parent view will contain a response_url. This
     * response_url can be used for message responses. The target channel for the message will be determined by the
     * value of this select menu.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#channel_select">Channels select element documentation</a>
     */
    fun responseUrlEnabled(enabled: Boolean) {
        responseUrlEnabled = enabled
    }

    /**
     * A confirm object that defines an optional confirmation dialog that appears after a menu item is selected.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#channel_select">Channels select element documentation</a>
     */
    fun confirm(builder: ConfirmationDialogObjectBuilder.() -> Unit) {
        confirm = ConfirmationDialogObjectBuilder().apply(builder).build()
    }

    override fun build(): ChannelsSelectElement {
        return ChannelsSelectElement.builder()
                .actionId(actionId)
                .placeholder(placeholder)
                .initialChannel(initialChannel)
                .confirm(confirm)
                .responseUrlEnabled(responseUrlEnabled)
                .build()
    }
}