package com.slack.api.model.block

import com.slack.api.SlackAPIBuilder
import com.slack.api.model.block.element.*

/**
 * Builder for an "actions" layout block element.
 */
@SlackAPIBuilder
class ActionsBlockBuilder(
        private val blockID: String?
) : BlockElementContainer {
    private val elementsDelegate = MultiBlockElementContainerImpl()

    override fun button(actionID: String?, url: String?, value: String?, style: ButtonBuilder.ButtonStyle, buildButton: ButtonBuilder.() -> Unit) =
            elementsDelegate.button(actionID, url, value, style, buildButton)
    override fun checkboxes(actionID: String?, buildCheckboxes: CheckboxesBuilder.() -> Unit)  =
            elementsDelegate.checkboxes(actionID, buildCheckboxes)
    override fun channelsSelect(initialChannel: String?, actionID: String?, responseURLEnabled: Boolean?, buildChannelsSelect: ChannelsSelectBuilder.() -> Unit) =
            elementsDelegate.channelsSelect(initialChannel, actionID, responseURLEnabled, buildChannelsSelect)

    fun build(): ActionsBlock {
        return ActionsBlock(elementsDelegate.blockElements, blockID)
    }
}