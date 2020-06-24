package com.slack.api.model.kotlin_extension.block

/**
 * This annotation signifies that the class is a builder for the Block Kit DSL.
 *
 * Classes marked with this annotation make it so that invoking functions with a receiver outside
 * the most immediate block are illegal without an explicit labeled `this`
 */
@DslMarker
annotation class BlockLayoutBuilder
