package com.slack.api.model.kotlin_extension.block.dsl

import com.slack.api.model.kotlin_extension.block.*

// same name with the object + "Dsl" suffix
@BlockLayoutBuilder
interface LayoutBlockDsl {
    /**
     * A section is one of the most flexible blocks available - it can be used as a simple text block, in combination
     * with text fields, or side-by-side with any of the available block elements.
     *
     * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/section-block">Section documentation</a>
     */
    fun section(builder: SectionBlockBuilder.() -> Unit)

    /**
     * A header is a plain-text block that displays in a larger, bold font.
     * Use it to delineate between different groups of content in your app's surfaces.
     *
     * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/header-block">Header documentation</a>
     */
    fun header(builder: HeaderBlockBuilder.() -> Unit)

    /**
     * A content divider, like an <hr>, to split up different blocks inside of a message. The divider block is nice
     * and neat, requiring only a type.
     *
     * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/divider-block">Divider documentation</a>
     */
    fun divider(blockId: String? = null)

    /**
     * A block that is used to hold interactive elements.
     *
     * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/actions-block">Actions documentation</a>
     */
    fun actions(builder: ActionsBlockBuilder.() -> Unit)

    /**
     * Displays message context, which can include both images and text.
     *
     * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/context-block">Context documentation</a>
     */
    fun context(builder: ContextBlockBuilder.() -> Unit)

    /**
     * Displays a remote file. This implementation uses a type-safe enum for the origin of the file source.
     *
     * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/file-block">File documentation</a>
     */
    fun file(externalId: String? = null, blockId: String? = null, source: FileSource? = null)

    /**
     * Displays a remote file. This implementation uses a string field for the origin of the file source in the
     * event the type-safe enum is out-of-date.
     *
     * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/file-block">File documentation</a>
     */
    fun file(externalId: String? = null, blockId: String? = null, source: String? = null)

    /**
     * A simple image block, designed to make those cat photos really pop.
     *
     * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/image-block">Image documentation</a>
     */
    fun image(builder: ImageBlockBuilder.() -> Unit)

    /**
     * A block that collects information from users - it can hold a plain-text input element, a select menu element, a multi-select menu element, or a datepicker.
     *
     * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/input-block">Input documentation</a>
     * @see <a href="https://docs.slack.dev/surfaces/modals#gathering_input">Using modals guide</a>
     */
    fun input(builder: InputBlockBuilder.() -> Unit)

    /**
     * Displays formatted, structured representation of text. It is also the output of the Slack client's WYSIWYG
     * message composer, so all messages sent by end-users will have this format. Use this block to include user-defined
     * formatted text in your Block Kit payload. While it is possible to format text with mrkdwn, rich_text is
     * strongly preferred and allows greater flexibility.
     *
     * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/rich-text-block">Rich text documentation</a>
     */
    fun richText(builder: RichTextBlockBuilder.() -> Unit)

    /**
     * A video block is designed to embed videos in all app surfaces (e.g. link unfurls, messages, modals, App Home)
     * — anywhere you can put blocks! To use the video block within your app, you must have the links.embed:write scope.
     *
     * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/video-block">Video documentation</a>
     */
    fun video(builder: VideoBlockBuilder.() -> Unit)

    /**
     * @see <a href="https://tools.slack.dev/deno-slack-sdk/">Next generation platform</a>
     */
    fun shareShortcut(builder: ShareShortcutBlockBuilder.() -> Unit)
}
