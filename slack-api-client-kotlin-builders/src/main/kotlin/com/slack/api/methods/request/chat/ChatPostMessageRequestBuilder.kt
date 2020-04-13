package com.slack.api.methods.request.chat

import com.slack.api.SlackAPIBuilder
import com.slack.api.model.block.LayoutBlockContainer
import com.slack.api.model.block.MultiLayoutBlockContainerImpl

/**
 * Builder for a ChatPostMessageRequest.
 */
@SlackAPIBuilder
class ChatPostMessageRequestBuilder(
        private val token: String?,
        private val username: String?,
        private val threadTs: String?,
        private val channel: String?,
        private val text: String?,
        private val parse: String = "none",
        private val linkNames: Boolean = false,
        private val blocksAsString: String? = null,
        private val unfurlLinks: Boolean = false,
        private val unfurlMedia: Boolean = false,
        private val isAsUser: Boolean? = null,
        private val markdown: Boolean = true,
        private val iconURL: String? = null,
        private val iconEmoji: String? = null,
        private val replyBroadcast: Boolean = false
) {
    private val blocksDelegate = MultiLayoutBlockContainerImpl()

    fun blocks(buildBlocks: LayoutBlockContainer.() -> Unit) {
        blocksDelegate.apply(buildBlocks)
    }

    // TODO implement the rest of this if POC is accepted

    /**
     * Currently a placeholder, will be updated later once this builder gets more fleshed out
     */
    fun build() : ChatPostMessageRequest {
        return ChatPostMessageRequest(
                token,
                username,
                threadTs,
                channel,
                text,
                parse,
                linkNames,
                blocksDelegate.layoutBlocks,
                blocksAsString,
                null,
                null,
                unfurlLinks,
                unfurlMedia,
                isAsUser,
                markdown,
                iconURL,
                iconEmoji,
                replyBroadcast
        )
    }
}