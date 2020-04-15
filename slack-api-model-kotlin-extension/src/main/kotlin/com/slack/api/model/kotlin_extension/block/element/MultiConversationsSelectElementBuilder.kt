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
    private var filter: ConversationsFilter? = null
    private var confirm: ConfirmationDialogObject? = null

    fun placeholder(text: String, emoji: Boolean? = null) {
        placeholder = PlainTextObject(text, emoji)
    }

    fun actionId(id: String) {
        actionId = id
    }

    fun initialConversations(vararg conversations: String) {
        initialConversations = conversations.toList()
    }

    fun maxSelectedItems(items: Int) {
        maxSelectedItems = items
    }

    fun filter(vararg include: ConversationType, excludeExternalSharedChannels: Boolean? = null, excludeBotUsers: Boolean? = null) {
        filter = ConversationsFilter(include.map { it.value }, excludeExternalSharedChannels, excludeBotUsers)
    }

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
                .filter(filter)
                .build()
    }
}