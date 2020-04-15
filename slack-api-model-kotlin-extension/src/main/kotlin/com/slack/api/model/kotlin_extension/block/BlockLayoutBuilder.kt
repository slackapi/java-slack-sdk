package com.slack.api.model.kotlin_extension.block

/**
 * Classes marked with this annotation make it so that invoking functions with a receiver outside
 * the most immediate block are illegal without an explicit labeled `this`
 */
@DslMarker
annotation class BlockLayoutBuilder
