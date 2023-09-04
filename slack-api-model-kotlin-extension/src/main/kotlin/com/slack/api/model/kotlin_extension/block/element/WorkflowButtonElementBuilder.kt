package com.slack.api.model.kotlin_extension.block.element

import com.slack.api.model.block.composition.PlainTextObject
import com.slack.api.model.block.composition.WorkflowObject
import com.slack.api.model.block.element.WorkflowButtonElement
import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder
import com.slack.api.model.kotlin_extension.block.Builder
import com.slack.api.model.kotlin_extension.block.composition.WorkflowObjectBuilder

@BlockLayoutBuilder
class WorkflowButtonElementBuilder : Builder<WorkflowButtonElement> {
    private var actionId: String? = null
    private var text: PlainTextObject? = null
    private var workflow: WorkflowObject? = null
    private var style: String? = null
    private var accessibilityLabel: String? = null

    fun actionId(id: String) {
        this.actionId = id
    }

    fun text(text: PlainTextObject) {
        this.text = text
    }

    fun text(text: String, emoji: Boolean? = null) {
        this.text = PlainTextObject(text, emoji)
    }

    fun workflow(workflow: WorkflowObject) {
        this.workflow = workflow
    }

    fun workflow(builder: WorkflowObjectBuilder.() -> Unit) {
        this.workflow = WorkflowObjectBuilder().apply(builder).build()
    }

    fun style(style: String) {
        this.style = style
    }

    fun accessibilityLabel(accessibilityLabel: String) {
        this.accessibilityLabel = accessibilityLabel
    }

    override fun build(): WorkflowButtonElement {
        return WorkflowButtonElement.builder()
            .actionId(actionId)
            .text(text)
            .workflow(workflow)
            .style(style)
            .accessibilityLabel(accessibilityLabel)
            .build()
    }
}
