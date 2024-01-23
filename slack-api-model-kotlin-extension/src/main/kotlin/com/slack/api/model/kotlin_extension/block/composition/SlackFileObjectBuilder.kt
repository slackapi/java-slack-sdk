package com.slack.api.model.kotlin_extension.block.composition

import com.slack.api.model.block.composition.SlackFileObject
import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder
import com.slack.api.model.kotlin_extension.block.Builder
import com.slack.api.model.kotlin_extension.block.composition.dsl.SlackFileObjectDsl

// same name with the object + "Builder" suffix
@BlockLayoutBuilder
class SlackFileObjectBuilder() : Builder<SlackFileObject>, SlackFileObjectDsl {
    private var id: String? = null
    private var url: String? = null

    override fun id(id: String) {
        this.id = id
    }

    override fun url(url: String) {
        this.url = url
    }

    override fun build(): SlackFileObject {
        return SlackFileObject.builder()
            .id(id)
            .url(url)
            .build()
    }
}