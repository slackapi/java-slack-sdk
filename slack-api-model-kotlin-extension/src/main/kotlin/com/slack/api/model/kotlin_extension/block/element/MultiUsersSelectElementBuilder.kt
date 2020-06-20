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

    /**
     * Adds a plain text element to the placeholder field.
     *
     * The placeholder text shown on the menu. Maximum length for the text in this field is 150 characters.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#users_multi_select">Multi users select element documentation</a>
     */
    fun placeholder(text: String, emoji: Boolean? = null) {
        placeholder = PlainTextObject(text, emoji)
    }

    /**
     * An identifier for the action triggered when a menu option is selected. You can use this when you receive an
     * interaction payload to identify the source of the action. Should be unique among all other action_ids used
     * elsewhere by your app. Maximum length for this field is 255 characters.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#users_multi_select">Multi users select element documentation</a>
     */
    fun actionId(id: String) {
        actionId = id
    }

    /**
     * An array of user IDs of any valid users to be pre-selected when the menu loads.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#users_multi_select">Multi users select element documentation</a>
     */
    fun initialUsers(vararg users: String) {
        initialUsers = users.toList()
    }

    /**
     * A confirm object that defines an optional confirmation dialog that appears before the multi-select choices are submitted.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#users_multi_select">Multi users select element documentation</a>
     */
    fun confirm(builder: ConfirmationDialogObjectBuilder.() -> Unit) {
        confirm = ConfirmationDialogObjectBuilder().apply(builder).build()
    }

    /**
     * Specifies the maximum number of items that can be selected in the menu. Minimum number is 1.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#users_multi_select">Multi users select element documentation</a>
     */
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