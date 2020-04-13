package com.slack.api

/**
 * Classes marked with @SlackAPIBuilder make it so that invoking functions with a receiver outside
 * the most immediate block are illegal without an explicit labeled `this`
 */
@DslMarker
annotation class SlackAPIBuilder
