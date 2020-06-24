package com.slack.api.model.kotlin_extension.block.element

import com.slack.api.model.block.composition.ConfirmationDialogObject
import com.slack.api.model.block.composition.PlainTextObject
import com.slack.api.model.block.element.ConversationsFilter
import com.slack.api.model.block.element.MultiConversationsSelectElement
import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder
import com.slack.api.model.kotlin_extension.block.Builder
import com.slack.api.model.kotlin_extension.block.composition.ConfirmationDialogObjectBuilder

@BlockLayoutBuilder
class MultiConversationsSelectElementBuilder : Builder<MultiConversationsSelectElement> {
    private var placeholder: PlainTextObject? = null
    private var actionId: String? = null
    private var initialConversations: List<String>? = null
    private var maxSelectedItems: Int? = null
    private var defaultToCurrentConversation: Boolean? = null
    private var filter: ConversationsFilter? = null
    private var confirm: ConfirmationDialogObject? = null

    /**
     * Adds a plain text object in the placeholder field.
     *
     * The placeholder text shown on the menu. Maximum length for the text in this field is 150 characters.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#conversation_multi_select">Multi conversations select element documentation</a>
     */
    fun placeholder(text: String, emoji: Boolean? = null) {
        placeholder = PlainTextObject(text, emoji)
    }

    /**
     * An identifier for the action triggered when a menu option is selected. You can use this when you receive an
     * interaction payload to identify the source of the action. Should be unique among all other action_ids used
     * elsewhere by your app. Maximum length for this field is 255 characters.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#conversation_multi_select">Multi conversations select element documentation</a>
     */
    fun actionId(id: String) {
        actionId = id
    }

    /**
     * An array of one or more IDs of any valid conversations to be pre-selected when the menu loads.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#conversation_multi_select">Multi conversations select element documentation</a>
     */
    fun initialConversations(vararg conversations: String) {
        initialConversations = conversations.toList()
    }

    /**
     * Specifies the maximum number of items that can be selected in the menu. Minimum number is 1.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#conversation_multi_select">Multi conversations select element documentation</a>
     */
    fun maxSelectedItems(items: Int) {
        maxSelectedItems = items
    }

    /**
     * Pre-populates the select menu with the conversation that the user was viewing when they opened the modal,
     * if available. If initial_conversations is also supplied, it will be ignored. Default is false.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#conversation_multi_select">Multi conversations select element documentation</a>
     */
    fun defaultToCurrentConversation(defaultToCurrentConversation: Boolean) {
        this.defaultToCurrentConversation = defaultToCurrentConversation
    }

    /**
     * A filter object that reduces the list of available conversations using the specified criteria.
     *
     * This implementation uses a type-safe enum to specify the conversation types to be filtered.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#conversation_multi_select">Multi conversations select element documentation</a>
     */
    fun filter(vararg include: ConversationType, excludeExternalSharedChannels: Boolean? = null, excludeBotUsers: Boolean? = null) {
        filter = ConversationsFilter.builder()
                .include(include.map { it.value })
                .excludeExternalSharedChannels(excludeExternalSharedChannels)
                .excludeBotUsers(excludeBotUsers)
                .build()
    }

    /**
     * A filter object that reduces the list of available conversations using the specified criteria.
     *
     * This implementation uses strings to specify the conversation types to be filtered. This may be preferable if
     * a new conversation type gets introduced and the enum class is not sufficient.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#conversation_multi_select">Multi conversations select element documentation</a>
     */
    fun filter(vararg include: String, excludeExternalSharedChannels: Boolean? = null, excludeBotUsers: Boolean? = null) {
        filter = ConversationsFilter.builder()
                .include(include.toList())
                .excludeExternalSharedChannels(excludeExternalSharedChannels)
                .excludeBotUsers(excludeBotUsers)
                .build()
    }

    /**
     * A confirm object that defines an optional confirmation dialog that appears before the multi-select choices are
     * submitted.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#conversation_multi_select">Multi conversations select element documentation</a>
     */
    fun confirm(builder: ConfirmationDialogObjectBuilder.() -> Unit) {
        confirm = ConfirmationDialogObjectBuilder().apply(builder).build()
    }

    override fun build(): MultiConversationsSelectElement {
        return MultiConversationsSelectElement.builder()
                .placeholder(placeholder)
                .actionId(actionId)
                .initialConversations(initialConversations)
                .confirm(confirm)
                .maxSelectedItems(maxSelectedItems)
                .defaultToCurrentConversation(defaultToCurrentConversation)
                .filter(filter)
                .build()
    }
}