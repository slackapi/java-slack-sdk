package com.slack.api.model.block.element

import com.slack.api.model.block.composition.ConfirmationDialogBuilder
import com.slack.api.SlackAPIBuilder
import com.slack.api.model.block.composition.ConfirmationDialogObject
import com.slack.api.model.block.composition.PlainTextObject

/**
 * Builder for a button block element
 */
@SlackAPIBuilder
class ButtonBuilder(
        private val actionID: String?,
        private val url: String?,
        private val value: String?,
        private val style: ButtonStyle
) {
    enum class ButtonStyle {
        NO_STYLINGS {
            override val apiValue: String? = null
        },
        PRIMARY {
            override val apiValue: String? = "primary"
        },
        DANGER {
            override val apiValue: String? = "danger"
        };
        abstract val apiValue: String?
    }
    private var text = PlainTextObject()
    private var confirmDialog: ConfirmationDialogObject? = null

    init {
        if (actionID?.length ?: 0 > 255) throw IllegalArgumentException("Button actionID cannot be longer than 255 characters.")
        if (url?.length ?: 0 > 3000) throw java.lang.IllegalArgumentException("Button URL cannot be longer than 3000 characters.")
        if (value?.length ?: 0 > 2000) throw IllegalArgumentException("Button value cannot be longer than 2000 characters.")
    }

    fun plainText(buttonText: String, emoji: Boolean? = null) {
        if (buttonText.length > 75) throw IllegalArgumentException("Button text length cannot be longer than 75 characters")
        text = PlainTextObject(buttonText, emoji)
    }
    fun confirmationDialog(confirmBuilder: ConfirmationDialogBuilder.() -> Unit) {
        confirmDialog = ConfirmationDialogBuilder().apply(confirmBuilder).build()
    }

    fun build(): ButtonElement {
        return ButtonElement(text, actionID, url, value, style.apiValue, confirmDialog)
    }
}