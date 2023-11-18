package com.slack.api.model.kotlin_extension.block.element

import com.slack.api.model.block.element.FileInputElement
import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder
import com.slack.api.model.kotlin_extension.block.Builder

@BlockLayoutBuilder
class FileInputElementBuilder : Builder<FileInputElement> {
    private var actionId: String? = null
    private var filetypes: List<String>? = null
    private var maxFiles: Int? = null

    fun actionId(id: String) {
        actionId = id
    }

    fun filetypes(filetypes: List<String>) {
        this.filetypes = filetypes
    }
    fun filetypes(vararg filetypes: String) {
        this.filetypes = filetypes.toList()
    }

    fun maxFiles(maxFiles: Int) {
        this.maxFiles = maxFiles
    }

    override fun build(): FileInputElement {
        return FileInputElement.builder()
            .actionId(actionId)
            .filetypes(filetypes)
            .maxFiles(maxFiles)
            .build()
    }
}
