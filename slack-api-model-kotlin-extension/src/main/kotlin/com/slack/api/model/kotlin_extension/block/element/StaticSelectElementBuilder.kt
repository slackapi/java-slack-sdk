package com.slack.api.model.kotlin_extension.block.element

import com.slack.api.model.block.composition.ConfirmationDialogObject
import com.slack.api.model.block.composition.OptionGroupObject
import com.slack.api.model.block.composition.OptionObject
import com.slack.api.model.block.composition.PlainTextObject
import com.slack.api.model.block.element.StaticSelectElement
import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder
import com.slack.api.model.kotlin_extension.block.Builder
import com.slack.api.model.kotlin_extension.block.composition.ConfirmationDialogObjectBuilder
import com.slack.api.model.kotlin_extension.block.composition.OptionObjectBuilder
import com.slack.api.model.kotlin_extension.block.composition.container.MultiOptionContainer
import com.slack.api.model.kotlin_extension.block.composition.container.MultiOptionGroupObjectContainer
import com.slack.api.model.kotlin_extension.block.composition.dsl.OptionGroupObjectDsl
import com.slack.api.model.kotlin_extension.block.composition.dsl.OptionObjectDsl

@BlockLayoutBuilder
class StaticSelectElementBuilder : Builder<StaticSelectElement> {
    private var placeholder: PlainTextObject? = null
    private var actionId: String? = null
    private var options: List<OptionObject>? = null
    private var optionGroups: List<OptionGroupObject>? = null
    private var initialOption: OptionObject? = null
    private var confirm: ConfirmationDialogObject? = null

    fun placeholder(text: String, emoji: Boolean? = null) {
        placeholder = PlainTextObject(text, emoji)
    }

    fun actionId(id: String) {
        actionId = id
    }

    fun options(builder: OptionObjectDsl.() -> Unit) {
        options = MultiOptionContainer().apply(builder).underlying
    }

    fun optionGroups(builder: OptionGroupObjectDsl.() -> Unit) {
        optionGroups = MultiOptionGroupObjectContainer().apply(builder).underlying
    }

    fun initialOption(builder: OptionObjectBuilder.() -> Unit) {
        initialOption = OptionObjectBuilder().apply(builder).build()
    }

    fun confirm(builder: ConfirmationDialogObjectBuilder.() -> Unit) {
        confirm = ConfirmationDialogObjectBuilder().apply(builder).build()
    }

    override fun build(): StaticSelectElement {
        return StaticSelectElement.builder()
                .placeholder(placeholder)
                .actionId(actionId)
                .options(options)
                .optionGroups(optionGroups)
                .initialOption(initialOption)
                .confirm(confirm)
                .build()
    }
}