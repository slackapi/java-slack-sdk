package com.slack.api.model.kotlin_extension.block

enum class FileSource {
    REMOTE {
        override val value = "remote"
    };

    abstract val value: String
}