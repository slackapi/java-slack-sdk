package com.slack.api.model.block

import com.slack.api.model.block.composition.TextObject
import com.slack.api.model.block.SectionBlock
import com.slack.api.model.block.element.*

/**
 * Builder for a section block element.
 */
@SlackAPIBuilder
class SectionBlockBuilder(
        private val blockID: String?
) : TextObjectContainer, BlockElementContainer {
    // Need to separate "fields" and "fieldsDelegate" because the delegate makes the list non-null by default
    private var fields: MutableList<TextObject>? = null
    private val fieldsDelegate = MultiTextObjectContainerImpl()
    private val textContentDelegate = SingleTextObjectContainerImpl()
    private val accessoryDelegate = SingleBlockElementContainerImpl()

    fun fields(buildFields: TextObjectContainer.() -> Unit) {
        fields = fieldsDelegate.apply(buildFields).addedTextObjects
    }

    override fun plainText(text: String, emoji: Boolean?) = textContentDelegate.plainText(text, emoji)
    override fun markdownText(text: String, verbatim: Boolean?) = textContentDelegate.markdownText(text, verbatim)
    override fun button(actionID: String?, url: String?, value: String?, style: ButtonBuilder.ButtonStyle, buildButton: ButtonBuilder.() -> Unit) =
            accessoryDelegate.button(actionID, url, value, style, buildButton)
    override fun checkboxes(actionID: String?, buildCheckboxes: CheckboxesBuilder.() -> Unit) =
            accessoryDelegate.checkboxes(actionID, buildCheckboxes)
    override fun channelsSelect(initialChannel: String?, actionID: String?, responseURLEnabled: Boolean?, buildChannelsSelect: ChannelsSelectBuilder.() -> Unit) =
            accessoryDelegate.channelsSelect(initialChannel, actionID, responseURLEnabled, buildChannelsSelect)

    fun build(): SectionBlock {
        return SectionBlock(textContentDelegate.constructedTextObject, blockID, fields, accessoryDelegate.constructedBlockElement)
    }
}
