package com.slack.api.model.block.element

import com.slack.api.SlackAPIBuilder

@SlackAPIBuilder
interface BlockElementContainer {
    fun button(
            actionID: String? = null,
            url: String? = null,
            value: String? = null,
            style: ButtonBuilder.ButtonStyle = ButtonBuilder.ButtonStyle.NO_STYLINGS,
            buildButton: ButtonBuilder.() -> Unit
    )
    fun checkboxes(actionID: String? = null, buildCheckboxes: CheckboxesBuilder.() -> Unit)
    fun channelsSelect(
            initialChannel: String? = null,
            actionID: String? = null,
            responseURLEnabled: Boolean? = null,
            buildChannelsSelect: ChannelsSelectBuilder.() -> Unit
    )
}

class SingleBlockElementContainerImpl : BlockElementContainer {
    var constructedBlockElement: BlockElement? = null

    override fun button(actionID: String?, url: String?, value: String?, style: ButtonBuilder.ButtonStyle, buildButton: ButtonBuilder.() -> Unit) {
        constructedBlockElement = ButtonBuilder(actionID, url, value, style).apply(buildButton).build()
    }
    override fun checkboxes(actionID: String?, buildCheckboxes: CheckboxesBuilder.() -> Unit) {
        constructedBlockElement = CheckboxesBuilder(actionID).apply(buildCheckboxes).build()
    }
    override fun channelsSelect(initialChannel: String?, actionID: String?, responseURLEnabled: Boolean?, buildChannelsSelect: ChannelsSelectBuilder.() -> Unit) {
        constructedBlockElement = ChannelsSelectBuilder(initialChannel, actionID, responseURLEnabled).apply(buildChannelsSelect).build()
    }
}