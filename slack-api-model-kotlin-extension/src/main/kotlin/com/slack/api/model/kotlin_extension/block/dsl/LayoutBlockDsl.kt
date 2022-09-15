package com.slack.api.model.kotlin_extension.block.dsl

import com.slack.api.model.kotlin_extension.block.*

// same name with the object + "Dsl" suffix
@BlockLayoutBuilder
interface LayoutBlockDsl {
    /**
     * A section is one of the most flexible blocks available - it can be used as a simple text block, in combination
     * with text fields, or side-by-side with any of the available block elements.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/blocks#section">Section documentation</a>
     */
    fun section(builder: SectionBlockBuilder.() -> Unit)

    /**
     * A header is a plain-text block that displays in a larger, bold font.
     * Use it to delineate between different groups of content in your app's surfaces.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/blocks#header">Header documentation</a>
     */
    fun header(builder: HeaderBlockBuilder.() -> Unit)

    /**
     * A content divider, like an <hr>, to split up different blocks inside of a message. The divider block is nice
     * and neat, requiring only a type.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/blocks#divider">Divider documentation</a>
     */
    fun divider(blockId: String? = null)

    /**
     * A block that is used to hold interactive elements.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/blocks#actions">Actions documentation</a>
     */
    fun actions(builder: ActionsBlockBuilder.() -> Unit)

    /**
     * Displays message context, which can include both images and text.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/blocks#context">Context documentation</a>
     */
    fun context(builder: ContextBlockBuilder.() -> Unit)

    /**
     * Displays a remote file. This implementation uses a type-safe enum for the origin of the file source.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/blocks#file">File documentation</a>
     */
    fun file(externalId: String? = null, blockId: String? = null, source: FileSource? = null)

    /**
     * Displays a remote file. This implementation uses a string field for the origin of the file source in the
     * event the type-safe enum is out-of-date.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/blocks#file">File documentation</a>
     */
    fun file(externalId: String? = null, blockId: String? = null, source: String? = null)

    /**
     * A simple image block, designed to make those cat photos really pop.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/blocks#image">Image documentation</a>
     */
    fun image(builder: ImageBlockBuilder.() -> Unit)

    /**
     * A block that collects information from users - it can hold a plain-text input element, a select menu element, a multi-select menu element, or a datepicker.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/blocks#input">Input documentation</a>
     * @see <a href="https://api.slack.com/surfaces/modals/using#gathering_input">Using modals guide</a>
     */
    fun input(builder: InputBlockBuilder.() -> Unit)

    /**
     * A video block is designed to embed videos in all app surfaces (e.g. link unfurls, messages, modals, App Home)
     * — anywhere you can put blocks! To use the video block within your app, you must have the links.embed:write scope.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/blocks#video">Video documentation</a>
     */
    fun video(builder: VideoBlockBuilder.() -> Unit)

    /**
     * @see <a href="https://api.slack.com/future">Next generation platform</a>
     */
    fun shareShortcut(builder: ShareShortcutBlockBuilder.() -> Unit)
}
