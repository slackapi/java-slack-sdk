package com.slack.api.model.kotlin_extension.block.element

import com.slack.api.model.block.composition.ConfirmationDialogObject
import com.slack.api.model.block.composition.OptionGroupObject
import com.slack.api.model.block.composition.OptionObject
import com.slack.api.model.block.composition.PlainTextObject
import com.slack.api.model.block.element.MultiStaticSelectElement
import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder
import com.slack.api.model.kotlin_extension.block.Builder
import com.slack.api.model.kotlin_extension.block.composition.ConfirmationDialogObjectBuilder
import com.slack.api.model.kotlin_extension.block.composition.container.MultiOptionContainer
import com.slack.api.model.kotlin_extension.block.composition.container.MultiOptionGroupObjectContainer
import com.slack.api.model.kotlin_extension.block.composition.dsl.OptionGroupObjectDsl
import com.slack.api.model.kotlin_extension.block.composition.dsl.OptionObjectDsl

@BlockLayoutBuilder
class MultiStaticSelectElementBuilder : Builder<MultiStaticSelectElement> {
    var placeholder: PlainTextObject? = null
    var actionId: String? = null
    var confirm: ConfirmationDialogObject? = null
    var options: List<OptionObject>? = null
    var optionGroups: List<OptionGroupObject>? = null
    var initialOptions: List<OptionObject>? = null
    var maxSelectedItems: Int? = null

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

    fun initialOptions(builder: OptionObjectDsl.() -> Unit) {
        initialOptions = MultiOptionContainer().apply(builder).underlying
    }

    fun maxSelectedItems(max: Int) {
        maxSelectedItems = max
    }

    fun confirm(builder: ConfirmationDialogObjectBuilder.() -> Unit) {
        confirm = ConfirmationDialogObjectBuilder().apply(builder).build()
    }

    override fun build(): MultiStaticSelectElement {
        return MultiStaticSelectElement.builder()
                .placeholder(placeholder)
                .actionId(actionId)
                .options(options)
                .optionGroups(optionGroups)
                .initialOptions(initialOptions)
                .confirm(confirm)
                .maxSelectedItems(maxSelectedItems)
                .build()
    }
}