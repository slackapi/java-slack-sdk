package com.slack.api.model.kotlin_extension.block.element

import com.slack.api.model.block.composition.ConfirmationDialogObject
import com.slack.api.model.block.composition.PlainTextObject
import com.slack.api.model.block.element.MultiUsersSelectElement
import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder
import com.slack.api.model.kotlin_extension.block.Builder
import com.slack.api.model.kotlin_extension.block.composition.ConfirmationDialogObjectBuilder

@BlockLayoutBuilder
class MultiUsersSelectElementBuilder : Builder<MultiUsersSelectElement> {
    private var placeholder: PlainTextObject? = null
    private var actionId: String? = null
    private var initialUsers: List<String>? = null
    private var confirm: ConfirmationDialogObject? = null
    private var maxSelectedItems: Int? = null

    fun placeholder(text: String, emoji: Boolean? = null) {
        placeholder = PlainTextObject(text, emoji)
    }

    fun actionId(id: String) {
        actionId = id
    }

    fun initialUsers(vararg users: String) {
        initialUsers = users.toList()
    }

    fun confirm(builder: ConfirmationDialogObjectBuilder.() -> Unit) {
        confirm = ConfirmationDialogObjectBuilder().apply(builder).build()
    }

    fun maxSelectedItems(max: Int) {
        maxSelectedItems = max
    }

    override fun build(): MultiUsersSelectElement {
        return MultiUsersSelectElement.builder()
                .placeholder(placeholder)
                .actionId(actionId)
                .initialUsers(initialUsers)
                .confirm(confirm)
                .maxSelectedItems(maxSelectedItems)
                .build()
    }
}