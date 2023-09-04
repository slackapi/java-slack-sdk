package com.slack.api.model.kotlin_extension.block.element.container

import com.slack.api.model.block.element.BlockElement
import com.slack.api.model.block.element.ImageElement
import com.slack.api.model.kotlin_extension.block.element.*
import com.slack.api.model.kotlin_extension.block.element.dsl.BlockElementDsl

/**
 * Supports a BlockElementContainer holding exactly one block element
 */
class SingleBlockElementContainer() : BlockElementDsl {
    var underlying: BlockElement? = null

    override fun button(builder: ButtonElementBuilder.() -> Unit) {
        underlying = ButtonElementBuilder().apply(builder).build()
    }

    override fun workflowButton(builder: WorkflowButtonElementBuilder.() -> Unit) {
        underlying = WorkflowButtonElementBuilder().apply(builder).build()
    }

    override fun checkboxes(builder: CheckboxesElementBuilder.() -> Unit) {
        underlying = CheckboxesElementBuilder().apply(builder).build()
    }

    override fun channelsSelect(builder: ChannelsSelectElementBuilder.() -> Unit) {
        underlying = ChannelsSelectElementBuilder().apply(builder).build()
    }

    override fun conversationsSelect(builder: ConversationsSelectElementBuilder.() -> Unit) {
        underlying = ConversationsSelectElementBuilder().apply(builder).build()
    }

    override fun datePicker(builder: DatePickerElementBuilder.() -> Unit) {
        underlying = DatePickerElementBuilder().apply(builder).build()
    }

    override fun timePicker(builder: TimePickerElementBuilder.() -> Unit) {
        underlying = TimePickerElementBuilder().apply(builder).build()
    }

    override fun datetimePicker(builder: DatetimePickerElementBuilder.() -> Unit) {
        underlying = DatetimePickerElementBuilder().apply(builder).build()
    }

    override fun externalSelect(builder: ExternalSelectElementBuilder.() -> Unit) {
        underlying = ExternalSelectElementBuilder().apply(builder).build()
    }

    override fun image(
        imageUrl: String?,
        altText: String?,
        fallback: String?,
        imageWidth: Int?,
        imageHeight: Int?,
        imageBytes: Int?
    ) {
        underlying = ImageElement.builder()
            .imageUrl(imageUrl)
            .altText(altText)
            .fallback(fallback)
            .imageWidth(imageWidth)
            .imageHeight(imageHeight)
            .imageBytes(imageBytes)
            .build()
    }

    override fun multiChannelsSelect(builder: MultiChannelsSelectElementBuilder.() -> Unit) {
        underlying = MultiChannelsSelectElementBuilder().apply(builder).build()
    }

    override fun multiConversationsSelect(builder: MultiConversationsSelectElementBuilder.() -> Unit) {
        underlying = MultiConversationsSelectElementBuilder().apply(builder).build()
    }

    override fun multiExternalSelect(builder: MultiExternalSelectElementBuilder.() -> Unit) {
        underlying = MultiExternalSelectElementBuilder().apply(builder).build()
    }

    override fun multiStaticSelect(builder: MultiStaticSelectElementBuilder.() -> Unit) {
        underlying = MultiStaticSelectElementBuilder().apply(builder).build()
    }

    override fun multiUsersSelect(builder: MultiUsersSelectElementBuilder.() -> Unit) {
        underlying = MultiUsersSelectElementBuilder().apply(builder).build()
    }

    override fun overflowMenu(builder: OverflowMenuElementBuilder.() -> Unit) {
        underlying = OverflowMenuElementBuilder().apply(builder).build()
    }

    override fun plainTextInput(builder: PlainTextInputElementBuilder.() -> Unit) {
        underlying = PlainTextInputElementBuilder().apply(builder).build()
    }

    override fun urlTextInput(builder: URLTextInputElementBuilder.() -> Unit) {
        underlying = URLTextInputElementBuilder().apply(builder).build()
    }

    override fun emailTextInput(builder: EmailTextInputElementBuilder.() -> Unit) {
        underlying = EmailTextInputElementBuilder().apply(builder).build()
    }

    override fun numberInput(builder: NumberInputElementBuilder.() -> Unit) {
        underlying = NumberInputElementBuilder().apply(builder).build()
    }

    override fun radioButtons(builder: RadioButtonsElementBuilder.() -> Unit) {
        underlying = RadioButtonsElementBuilder().apply(builder).build()
    }

    override fun staticSelect(builder: StaticSelectElementBuilder.() -> Unit) {
        underlying = StaticSelectElementBuilder().apply(builder).build()
    }

    override fun usersSelect(builder: UsersSelectElementBuilder.() -> Unit) {
        underlying = UsersSelectElementBuilder().apply(builder).build()
    }
}