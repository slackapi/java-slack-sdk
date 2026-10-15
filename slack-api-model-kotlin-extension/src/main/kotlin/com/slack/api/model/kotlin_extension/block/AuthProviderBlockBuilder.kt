package com.slack.api.model.kotlin_extension.block

import com.slack.api.model.block.AuthProviderBlock
import com.slack.api.model.block.LayoutBlock
import com.slack.api.model.block.composition.PlainTextObject
import com.slack.api.model.block.element.OverflowMenuElement
import com.slack.api.model.kotlin_extension.block.container.MultiLayoutBlockContainer
import com.slack.api.model.kotlin_extension.block.element.OverflowMenuElementBuilder

@BlockLayoutBuilder
class AuthProviderBlockBuilder : Builder<AuthProviderBlock> {
    private var blockId: String? = null
    private var value: String? = null
    private var name: PlainTextObject? = null
    private var isConnected: Boolean? = null
    private var description: PlainTextObject? = null
    private var iconUrl: String? = null
    private var authUrl: String? = null
    private var disconnectUrl: String? = null
    private var connectedIdentity: PlainTextObject? = null
    private var connectedContext: PlainTextObject? = null
    private var accessory: OverflowMenuElement? = null
    private var fallback: List<LayoutBlock>? = null

    fun blockId(id: String) {
        blockId = id
    }

    fun value(value: String) {
        this.value = value
    }

    fun name(text: String, emoji: Boolean? = null) {
        name = PlainTextObject(text, emoji)
    }

    fun isConnected(connected: Boolean) {
        isConnected = connected
    }

    fun description(text: String, emoji: Boolean? = null) {
        description = PlainTextObject(text, emoji)
    }

    fun iconUrl(url: String) {
        iconUrl = url
    }

    fun authUrl(url: String) {
        authUrl = url
    }

    fun disconnectUrl(url: String) {
        disconnectUrl = url
    }

    fun connectedIdentity(text: String, emoji: Boolean? = null) {
        connectedIdentity = PlainTextObject(text, emoji)
    }

    fun connectedContext(text: String, emoji: Boolean? = null) {
        connectedContext = PlainTextObject(text, emoji)
    }

    fun accessory(builder: OverflowMenuElementBuilder.() -> Unit) {
        accessory = OverflowMenuElementBuilder().apply(builder).build()
    }

    fun fallback(builder: MultiLayoutBlockContainer.() -> Unit) {
        fallback = MultiLayoutBlockContainer().apply(builder).underlying
    }

    override fun build(): AuthProviderBlock {
        return AuthProviderBlock.builder()
            .blockId(blockId)
            .value(value)
            .name(name)
            .isConnected(isConnected)
            .description(description)
            .iconUrl(iconUrl)
            .authUrl(authUrl)
            .disconnectUrl(disconnectUrl)
            .connectedIdentity(connectedIdentity)
            .connectedContext(connectedContext)
            .accessory(accessory)
            .fallback(fallback)
            .build()
    }
}
