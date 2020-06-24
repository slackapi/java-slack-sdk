package com.slack.api.model.kotlin_extension.block

enum class FileSource {
    /**
     * Signifies that the file is coming from a remote origin server.
     */
    REMOTE {
        override val value = "remote"
    };

    abstract val value: String
}