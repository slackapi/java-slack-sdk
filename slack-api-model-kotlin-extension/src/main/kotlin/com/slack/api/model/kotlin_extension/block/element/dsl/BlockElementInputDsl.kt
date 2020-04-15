package com.slack.api.model.kotlin_extension.block.element.dsl

import com.slack.api.model.kotlin_extension.block.element.*

interface BlockElementInputDsl {
    fun channelsSelect(builder: ChannelsSelectElementBuilder.() -> Unit)

    fun conversationsSelect(builder: ConversationsSelectElementBuilder.() -> Unit)

    fun datePicker(builder: DatePickerElementBuilder.() -> Unit)

    fun externalSelect(builder: ExternalSelectElementBuilder.() -> Unit)

    fun multiChannelsSelect(builder: MultiChannelsSelectElementBuilder.() -> Unit)

    fun multiConversationsSelect(builder: MultiConversationsSelectElementBuilder.() -> Unit)

    fun multiExternalSelect(builder: MultiExternalSelectElementBuilder.() -> Unit)

    fun multiStaticSelect(builder: MultiStaticSelectElementBuilder.() -> Unit)

    fun multiUsersSelect(builder: MultiUsersSelectElementBuilder.() -> Unit)

    fun plainTextInput(builder: PlainTextInputElementBuilder.() -> Unit)

    fun staticSelect(builder: StaticSelectElementBuilder.() -> Unit)

    fun usersSelect(builder: UsersSelectElementBuilder.() -> Unit)
}