package com.slack.api.model.kotlin_extension.block

import com.slack.api.model.block.ShareShortcutBlock

@BlockLayoutBuilder
class ShareShortcutBlockBuilder : Builder<ShareShortcutBlock> {
    private var blockId: String? = null
    private var functionTriggerId: String? = null
    private var appId: String? = null
    private var isWorkflowApp: Boolean? = null
    private var appCollaborators: List<String>? = null
    private var _buttonLabel: String? = null
    private var _title: String? = null
    private var _description: String? = null
    private var _botUserId: String? = null
    private var _url: String? = null

    fun blockId(id: String) {
        blockId = id
    }

    fun functionTriggerId(id: String) {
        functionTriggerId = id
    }

    fun appId(id: String) {
        appId = id
    }

    fun isWorkflowApp(workflowApp: Boolean) {
        isWorkflowApp = workflowApp
    }

    fun appCollaborators(collaborators: List<String>) {
        appCollaborators = collaborators
    }

    fun buttonLabel(label: String) {
        _buttonLabel = label
    }

    fun title(title: String) {
        _title = title
    }

    fun description(description: String) {
        _description = description
    }

    fun botUserId(botUserId: String) {
        _botUserId = botUserId
    }

    fun url(url: String) {
        _url = url
    }

    override fun build(): ShareShortcutBlock {
        return ShareShortcutBlock.builder()
            .blockId(blockId)
            .functionTriggerId(functionTriggerId)
            .appId(appId)
            .isWorkflowApp(isWorkflowApp)
            .appCollaborators(appCollaborators)
            .buttonLabel(_buttonLabel)
            .title(_title)
            .description(_description)
            .botUserId(_botUserId)
            .url(_url)
            .build()
    }
}