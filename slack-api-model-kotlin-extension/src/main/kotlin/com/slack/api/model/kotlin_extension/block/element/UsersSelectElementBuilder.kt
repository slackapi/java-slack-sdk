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

    /**
     * Adds a plain text object to the placeholder field.
     *
     * The placeholder text shown on the menu. Maximum length for the text in this field is 150.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#users_select">Users select element documentation</a>
     */
    fun placeholder(text: String, emoji: Boolean? = null) {
        placeholder = PlainTextObject(text, emoji)
    }

    /**
     * An identifier for the action triggered when a menu option is selected. You can use this when you receive an
     * interaction payload to identify the source of the action. Should be unique among all other action_ids used
     * elsewhere by your app. Maximum length for this field is 255 characters.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#users_select">Users select element documentation</a>
     */
    fun actionId(id: String) {
        actionId = id
    }

    /**
     * The user ID of any valid user to be pre-selected when the menu loads.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#users_select">Users select element documentation</a>
     */
    fun initialUser(user: String) {
        initialUser = user
    }

    /**
     * A confirm object that defines an optional confirmation dialog that appears after a menu item is selected.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#users_select">Users select element documentation</a>
     */
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