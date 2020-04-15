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
    private var conversationsFilter: ConversationsFilter? = null
    private var confirm: ConfirmationDialogObject? = null

    fun placeholder(text: String, emoji: Boolean? = null) {
        placeholder = PlainTextObject(text, emoji)
    }

    fun actionId(id: String) {
        actionId = id
    }

    fun initialConversation(conversation: String) {
        initialConversation = conversation
    }

    fun responseUrlEnabled(enabled: Boolean) {
        responseUrlEnabled = enabled
    }

    fun conversationsFilter(vararg include: ConversationType, excludeExternalSharedChannels: Boolean = false, excludeBotUsers: Boolean = false) {
        conversationsFilter = ConversationsFilter.builder()
                .include(include.map { it.value })
                .excludeExternalSharedChannels(excludeExternalSharedChannels)
                .excludeBotUsers(excludeBotUsers)
                .build()
    }

    fun confirm(builder: ConfirmationDialogObjectBuilder.() -> Unit) {
        confirm = ConfirmationDialogObjectBuilder().apply(builder).build()
    }

    override fun build(): ConversationsSelectElement {
        return ConversationsSelectElement.builder()
                .placeholder(placeholder)
                .actionId(actionId)
                .initialConversation(initialConversation)
                .confirm(confirm)
                .responseUrlEnabled(responseUrlEnabled)
                .filter(conversationsFilter)
                .build()
    }
}