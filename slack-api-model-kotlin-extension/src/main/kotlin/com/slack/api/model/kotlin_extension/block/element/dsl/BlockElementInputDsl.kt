package com.slack.api.model.kotlin_extension.block.element.dsl

import com.slack.api.model.block.element.FileInputElement
import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder
import com.slack.api.model.kotlin_extension.block.element.*

@BlockLayoutBuilder
interface BlockElementInputDsl {
    /**
     * This select menu will populate its options with a list of public channels visible to the current user in the
     * active workspace.
     *
     * @see <a href="https://docs.slack.dev/reference/block-kit/block-elements/multi-select-menu-element#channel_multi_select">Channels select element documentation</a>
     */
    fun channelsSelect(builder: ChannelsSelectElementBuilder.() -> Unit)

    /**
     * This select menu will populate its options with a list of public and private channels, DMs, and MPIMs visible
     * to the current user in the active workspace.
     *
     * @see <a href="https://docs.slack.dev/reference/block-kit/block-elements/multi-select-menu-element#conversation_multi_select">Conversations select element documentation</a>
     */
    fun conversationsSelect(builder: ConversationsSelectElementBuilder.() -> Unit)

    /**
     * An element which lets users easily select a date from a calendar style UI.
     *
     * @see <a href="https://docs.slack.dev/interactivity/handling-user-interaction">Enabling interactivity guide</a>
     * @see <a href="https://docs.slack.dev/reference/block-kit/block-elements/date-picker-element">Date picker element documentation</a>
     */
    fun datePicker(builder: DatePickerElementBuilder.() -> Unit)

    /**
     * An element which allows selection of a time of day.
     *
     * On desktop clients, this time picker will take the form of a dropdown list with free-text entry for precise choices.
     * On mobile clients, the time picker will use native time picker UIs.
     *
     * @see <a href="https://docs.slack.dev/interactivity/handling-user-interaction">Enabling interactivity guide</a>
     * @see <a href="https://docs.slack.dev/reference/block-kit/block-elements/time-picker-element">Time picker element documentation</a>
     */
    fun timePicker(builder: TimePickerElementBuilder.() -> Unit)

    /**
     * @see <a href="https://docs.slack.dev/interactivity/handling-user-interaction">Enabling interactivity guide</a>
     * @see <a href="https://docs.slack.dev/reference/block-kit/block-elements/datetime-picker-element">Date time picker element documentation</a>
     */
    fun datetimePicker(builder: DatetimePickerElementBuilder.() -> Unit)

    /**
     * This select menu will load its options from an external data source, allowing for a dynamic list of options.
     *
     * @see <a href="https://docs.slack.dev/reference/block-kit/block-elements/multi-select-menu-element#external_multi_select">External select element documentation</a>
     */
    fun externalSelect(builder: ExternalSelectElementBuilder.() -> Unit)

    /**
     * This multi-select menu will populate its options with a list of public channels visible to the current user in
     * the active workspace.
     *
     * @see <a href="https://docs.slack.dev/reference/block-kit/block-elements/multi-select-menu-element#channel_multi_select">Multi channels select element documentation</a>
     */
    fun multiChannelsSelect(builder: MultiChannelsSelectElementBuilder.() -> Unit)

    /**
     * This multi-select menu will populate its options with a list of public and private channels, DMs, and MPIMs
     * visible to the current user in the active workspace.
     *
     * @see <a href="https://docs.slack.dev/reference/block-kit/block-elements/multi-select-menu-element#conversation_multi_select">Multi conversations select element documentation</a>
     */
    fun multiConversationsSelect(builder: MultiConversationsSelectElementBuilder.() -> Unit)

    /**
     * This menu will load its options from an external data source, allowing for a dynamic list of options.
     *
     * @see <a href="https://docs.slack.dev/reference/block-kit/block-elements/multi-select-menu-element#external_multi_select">Multi external select element documentation</a>
     */
    fun multiExternalSelect(builder: MultiExternalSelectElementBuilder.() -> Unit)

    /**
     * This is the simplest form of select menu, with a static list of options passed in when defining the element.
     *
     * @see <a href="https://docs.slack.dev/reference/block-kit/block-elements/multi-select-menu-element#static_multi_select">Multi static select element documentation</a>
     */
    fun multiStaticSelect(builder: MultiStaticSelectElementBuilder.() -> Unit)

    /**
     * This multi-select menu will populate its options with a list of Slack users visible to the current user in the
     * active workspace.
     *
     * @see <a href="https://docs.slack.dev/reference/block-kit/block-elements/multi-select-menu-element#users_multi_select">Multi users select element documentation</a>
     */
    fun multiUsersSelect(builder: MultiUsersSelectElementBuilder.() -> Unit)

    fun richTextInput(builder: RichTextInputElementBuilder.() -> Unit)

    /**
     * A plain-text input, similar to the HTML input tag, creates a field where a user can enter freeform data. It can
     * appear as a single-line field or a larger textarea using the multiline flag.
     *
     * @see <a href="https://docs.slack.dev/surfaces/modals#preparing_for_modals">Preparing your app for modals guide</a>
     * @see <a href="https://docs.slack.dev/reference/block-kit/block-elements/plain-text-input-element">Plain text input documentation</a>
     */
    fun plainTextInput(builder: PlainTextInputElementBuilder.() -> Unit)

    /**
     * @see <a href="https://docs.slack.dev/surfaces/modals#preparing_for_modals">Preparing your app for modals guide</a>
     * @see <a href="https://docs.slack.dev/reference/block-kit/block-elements">Documentation</a>
     */
    fun urlTextInput(builder: URLTextInputElementBuilder.() -> Unit)

    /**
     * @see <a href="https://docs.slack.dev/surfaces/modals#preparing_for_modals">Preparing your app for modals guide</a>
     * @see <a href="https://docs.slack.dev/reference/block-kit/block-elements/email-input-element">Documentation</a>
     */
    fun emailTextInput(builder: EmailTextInputElementBuilder.() -> Unit)

    /**
     * @see <a href="https://docs.slack.dev/surfaces/modals#preparing_for_modals">Preparing your app for modals guide</a>
     * @see <a href="https://docs.slack.dev/reference/block-kit/block-elements/number-input-element">Documentation</a>
     */
    fun numberInput(builder: NumberInputElementBuilder.() -> Unit)

    /**
     * @see <a href="https://docs.slack.dev/surfaces/modals#preparing_for_modals">Preparing your app for modals guide</a>
     * @see <a href="https://docs.slack.dev/reference/block-kit/block-elements/file-input-element">Documentation</a>
     */
    fun fileInput(builder: FileInputElementBuilder.() -> Unit)

    /**
     * This is the simplest form of select menu, with a static list of options passed in when defining the element.
     *
     * @see <a href="https://docs.slack.dev/reference/block-kit/block-elements/multi-select-menu-element#static_multi_select">Static select element documentation</a>
     */
    fun staticSelect(builder: StaticSelectElementBuilder.() -> Unit)

    /**
     * This select menu will populate its options with a list of Slack users visible to the current user in the active
     * workspace.
     *
     * @see <a href="https://docs.slack.dev/reference/block-kit/block-elements/multi-select-menu-element#users_multi_select">Users select element documentation</a>
     */
    fun usersSelect(builder: UsersSelectElementBuilder.() -> Unit)

    /**
     * A radio button group that allows a user to choose one item from a list of possible options.
     *
     * @see <a href="https://docs.slack.dev/interactivity/handling-user-interaction">Enabling interactivity guide</a>
     * @see <a href="https://docs.slack.dev/reference/block-kit/block-elements/radio-button-group-element">Radio buttons element documentation</a>
     */
    fun radioButtons(builder: RadioButtonsElementBuilder.() -> Unit)

    /**
     * A checkbox group that allows a user to choose multiple items from a list of possible options.
     *
     * @see <a href="https://docs.slack.dev/interactivity/handling-user-interaction">Enabling interactivity guide</a>
     * @see <a href="https://docs.slack.dev/reference/block-kit/block-elements/checkboxes-element">Checkboxes element documentation</a>
     */
    fun checkboxes(builder: CheckboxesElementBuilder.() -> Unit)
}