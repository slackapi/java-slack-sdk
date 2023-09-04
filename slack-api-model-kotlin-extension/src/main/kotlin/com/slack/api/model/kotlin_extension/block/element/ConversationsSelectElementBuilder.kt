package com.slack.api.model.kotlin_extension.block.element

import com.slack.api.model.block.composition.ConfirmationDialogObject
import com.slack.api.model.block.composition.PlainTextObject
import com.slack.api.model.block.element.ConversationsFilter
import com.slack.api.model.block.element.ConversationsSelectElement
import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder
import com.slack.api.model.kotlin_extension.block.Builder
import com.slack.api.model.kotlin_extension.block.composition.ConfirmationDialogObjectBuilder

@BlockLayoutBuilder
class ConversationsSelectElementBuilder : Builder<ConversationsSelectElement> {
    private var placeholder: PlainTextObject? = null
    private var actionId: String? = null
    private var initialConversation: String? = null
    private var responseUrlEnabled: Boolean? = null
    private var defaultToCurrentConversation: Boolean? = null
    private var filter: ConversationsFilter? = null
    private var confirm: ConfirmationDialogObject? = null
    private var _focusOnLoad: Boolean? = null

    /**
     * Fills the placeholder field with a plain text object.
     *
     * The placeholder text shown on the menu. Maximum length for the text in this field is 150 characters.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#conversation_select">Conversations select element documentation</a>
     */
    fun placeholder(text: String, emoji: Boolean? = null) {
        placeholder = PlainTextObject(text, emoji)
    }

    /**
     * An identifier for the action triggered when a menu option is selected. You can use this when you receive an
     * interaction payload to identify the source of the action. Should be unique among all other action_ids used
     * elsewhere by your app. Maximum length for this field is 255 characters.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#conversation_select">Conversations select element documentation</a>
     */
    fun actionId(id: String) {
        actionId = id
    }

    /**
     * The ID of any valid conversation to be pre-selected when the menu loads.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#conversation_select">Conversations select element documentation</a>
     */
    fun initialConversation(conversation: String) {
        initialConversation = conversation
    }

    /**
     * <b>This field only works with menus in input blocks in modals.</b>
     * When set to true, the view_submission payload from the menu's parent view will contain a response_url. This
     * response_url can be used for message responses. The target conversation for the message will be determined by
     * the value of this select menu.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#conversation_select">Conversations select element documentation</a>
     */
    fun responseUrlEnabled(enabled: Boolean) {
        responseUrlEnabled = enabled
    }

    /**
     * Pre-populates the select menu with the conversation that the user was viewing when they opened the modal,
     * if available. If initial_conversation is also supplied, it will be ignored. Default is false.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#conversation_select">Conversations select element documentation</a>
     */
    fun defaultToCurrentConversation(defaultToCurrentConversation: Boolean) {
        this.defaultToCurrentConversation = defaultToCurrentConversation
    }

    /**
     * A filter object that reduces the list of available conversations using the specified criteria.
     *
     * This implementation uses a type-safe enum for the allowable conversation types.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#conversation_select">Conversations select element documentation</a>
     */
    fun filter(
        vararg include: ConversationType,
        excludeExternalSharedChannels: Boolean = false,
        excludeBotUsers: Boolean = false
    ) {
        filter = ConversationsFilter.builder()
            .include(include.map { it.value })
            .excludeExternalSharedChannels(excludeExternalSharedChannels)
            .excludeBotUsers(excludeBotUsers)
            .build()
    }

    /**
     * A filter object that reduces the list of available conversations using the specified criteria.
     *
     * This implementation uses strings for the allowable conversation types. This may be preferable if new filters
     * are released and the type-safe enum is insufficient.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#conversation_select">Conversations select element documentation</a>
     */
    fun filter(
        vararg include: String,
        excludeExternalSharedChannels: Boolean = false,
        excludeBotUsers: Boolean = false
    ) {
        filter = ConversationsFilter.builder()
            .include(include.toList())
            .excludeExternalSharedChannels(excludeExternalSharedChannels)
            .excludeBotUsers(excludeBotUsers)
            .build()
    }

    /**
     * A confirm object that defines an optional confirmation dialog that appears after a menu item is selected.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#conversation_select">Conversations select element documentation</a>
     */
    fun confirm(builder: ConfirmationDialogObjectBuilder.() -> Unit) {
        confirm = ConfirmationDialogObjectBuilder().apply(builder).build()
    }

    /**
     * Indicates whether the element will be set to autofocus within the view object.
     * Only one element can be set to true. Defaults to false.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#conversation_select">Conversations select element documentation</a>
     */
    fun focusOnLoad(focusOnLoad: Boolean) {
        _focusOnLoad = focusOnLoad
    }

    override fun build(): ConversationsSelectElement {
        return ConversationsSelectElement.builder()
            .placeholder(placeholder)
            .actionId(actionId)
            .initialConversation(initialConversation)
            .confirm(confirm)
            .responseUrlEnabled(responseUrlEnabled)
            .defaultToCurrentConversation(defaultToCurrentConversation)
            .filter(filter)
            .focusOnLoad(_focusOnLoad)
            .build()
    }
}
