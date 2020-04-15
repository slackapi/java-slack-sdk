package com.slack.api.methods.kotlin_extension.request.chat

import com.slack.api.methods.request.chat.ChatPostEphemeralRequest
import com.slack.api.methods.request.chat.ChatPostMessageRequest
import com.slack.api.methods.request.chat.ChatScheduleMessageRequest
import com.slack.api.methods.request.chat.ChatUpdateRequest
import com.slack.api.model.kotlin_extension.block.dsl.LayoutBlockDsl
import com.slack.api.model.kotlin_extension.block.withBlocks

fun ChatPostEphemeralRequest.ChatPostEphemeralRequestBuilder.buildBlocks(builder: LayoutBlockDsl.() -> Unit): ChatPostEphemeralRequest.ChatPostEphemeralRequestBuilder {
    return this.blocks(withBlocks(builder))
}

fun ChatPostMessageRequest.ChatPostMessageRequestBuilder.buildBlocks(builder: LayoutBlockDsl.() -> Unit): ChatPostMessageRequest.ChatPostMessageRequestBuilder {
    return this.blocks(withBlocks(builder))
}

fun ChatScheduleMessageRequest.ChatScheduleMessageRequestBuilder.buildBlocks(builder: LayoutBlockDsl.() -> Unit): ChatScheduleMessageRequest.ChatScheduleMessageRequestBuilder {
    return this.blocks(withBlocks(builder))
}

fun ChatUpdateRequest.ChatUpdateRequestBuilder.buildBlocks(builder: LayoutBlockDsl.() -> Unit): ChatUpdateRequest.ChatUpdateRequestBuilder {
    return this.blocks(withBlocks(builder))
}