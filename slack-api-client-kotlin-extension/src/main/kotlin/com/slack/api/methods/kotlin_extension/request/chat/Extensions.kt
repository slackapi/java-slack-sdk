package com.slack.api.methods.kotlin_extension.request.chat

import com.slack.api.methods.request.chat.ChatPostEphemeralRequest.ChatPostEphemeralRequestBuilder
import com.slack.api.methods.request.chat.ChatPostMessageRequest.ChatPostMessageRequestBuilder
import com.slack.api.methods.request.chat.ChatScheduleMessageRequest.ChatScheduleMessageRequestBuilder
import com.slack.api.methods.request.chat.ChatUpdateRequest.ChatUpdateRequestBuilder
import com.slack.api.model.kotlin_extension.block.dsl.LayoutBlockDsl
import com.slack.api.model.kotlin_extension.block.withBlocks


fun ChatPostEphemeralRequestBuilder.blocks(builder: LayoutBlockDsl.() -> Unit): ChatPostEphemeralRequestBuilder {
    return this.blocks(withBlocks(builder))
}

fun ChatPostMessageRequestBuilder.blocks(builder: LayoutBlockDsl.() -> Unit): ChatPostMessageRequestBuilder {
    return this.blocks(withBlocks(builder))
}

fun ChatScheduleMessageRequestBuilder.blocks(builder: LayoutBlockDsl.() -> Unit): ChatScheduleMessageRequestBuilder {
    return this.blocks(withBlocks(builder))
}

fun ChatUpdateRequestBuilder.blocks(builder: LayoutBlockDsl.() -> Unit): ChatUpdateRequestBuilder {
    return this.blocks(withBlocks(builder))
}