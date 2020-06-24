package com.slack.api.model.kotlin_extension.block

/**
 * Builder is the interface for any Kotlin DSL builder in the kotlin extension. It requires that a build() function be
 * present so that the builder can return the constructed object.
 */
interface Builder<A> {
    fun build(): A
}