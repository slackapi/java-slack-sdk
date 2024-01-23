package com.slack.api.model.kotlin_extension.block.composition.dsl

import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder

@BlockLayoutBuilder
interface SlackFileObjectDsl {
    fun id(id: String)
    fun url(url: String)
}