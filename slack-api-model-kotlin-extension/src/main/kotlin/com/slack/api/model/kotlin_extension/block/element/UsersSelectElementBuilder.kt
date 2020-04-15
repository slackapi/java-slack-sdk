package com.slack.api.model.kotlin_extension.block.element

import com.slack.api.model.block.composition.ConfirmationDialogObject
import com.slack.api.model.block.composition.PlainTextObject
import com.slack.api.model.block.element.UsersSelectElement
import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder
import com.slack.api.model.kotlin_extension.block.Builder
import com.slack.api.model.kotlin_extension.block.composition.ConfirmationDialogObjectBuilder

@BlockLayoutBuilder
class UsersSelectElementBuilder : Builder<UsersSelectElement> {
    private var placeholder: PlainTextObject? = null
    private var actionId: String? = null
    private var initialUser: String? = null
    private var confirm: ConfirmationDialogObject? = null

    fun placeholder(text: String, emoji: Boolean? = null) {
        placeholder = PlainTextObject(text, emoji)
    }

    fun actionId(id: String) {
        actionId = id
    }

    fun initialUser(user: String) {
        initialUser = user
    }

    fun confirm(builder: ConfirmationDialogObjectBuilder.() -> Unit) {
        confirm = ConfirmationDialogObjectBuilder().apply(builder).build()
    }

    override fun build(): UsersSelectElement {
        return UsersSelectElement.builder()
                .placeholder(placeholder)
                .actionId(actionId)
                .initialUser(initialUser)
                .confirm(confirm)
                .build()
    }
}