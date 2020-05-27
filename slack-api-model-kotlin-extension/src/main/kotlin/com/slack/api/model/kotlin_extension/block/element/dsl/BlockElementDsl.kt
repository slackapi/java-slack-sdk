package com.slack.api.model.kotlin_extension.block.element.dsl

import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder
import com.slack.api.model.kotlin_extension.block.element.ButtonElementBuilder
import com.slack.api.model.kotlin_extension.block.element.CheckboxesElementBuilder
import com.slack.api.model.kotlin_extension.block.element.OverflowMenuElementBuilder
import com.slack.api.model.kotlin_extension.block.element.RadioButtonsElementBuilder

// same name with the object + "Dsl" suffix
@BlockLayoutBuilder
interface BlockElementDsl : BlockElementInputDsl, BlockElementRichTextDsl {
    fun button(builder: ButtonElementBuilder.() -> Unit)

    fun overflowMenu(builder: OverflowMenuElementBuilder.() -> Unit)

    fun image(imageUrl: String? = null, altText: String? = null, fallback: String? = null, imageWidth: Int? = null, imageHeight: Int? = null, imageBytes: Int? = null)
}
