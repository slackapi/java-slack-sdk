package com.slack.api.model.kotlin_extension.block.element.dsl

import com.slack.api.model.block.composition.SlackFileObject
import com.slack.api.model.kotlin_extension.block.BlockLayoutBuilder
import com.slack.api.model.kotlin_extension.block.element.ButtonElementBuilder
import com.slack.api.model.kotlin_extension.block.element.OverflowMenuElementBuilder
import com.slack.api.model.kotlin_extension.block.element.WorkflowButtonElementBuilder

// same name with the object + "Dsl" suffix
@BlockLayoutBuilder
interface BlockElementDsl : BlockElementInputDsl {
    /**
     * An interactive component that inserts a button. The button can be a trigger for anything from opening a
     * simple link to starting a complex workflow.
     *
     * @see <a href="https://api.slack.com/interactivity/handling">Enabling interactivity guide</a>
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#button">Button element documentation</a>
     */
    fun button(builder: ButtonElementBuilder.() -> Unit)

    /**
     * Allows users to run a link trigger with customizable inputs
     * Interactive component - but interactions with workflow button elements will not send block_actions events,
     * since these are used to start new workflow runs.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#workflow_button">Documentation</a>
     */
    fun workflowButton(builder: WorkflowButtonElementBuilder.() -> Unit)

    /**
     * This is like a cross between a button and a select menu - when a user clicks on this overflow button, they will
     * be presented with a list of options to choose from. Unlike the select menu, there is no typeahead field, and the
     * button always appears with an ellipsis ("…") rather than customisable text.
     *
     * As such, it is usually used if you want a more compact layout than a select menu, or to supply a list of less
     * visually important actions after a row of buttons. You can also specify simple URL links as overflow menu
     * options, instead of actions.
     *
     * @see <a href="https://api.slack.com/interactivity/handling">Enabling interactivity guide</a>
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#overflow">Overflow menu element documentation</a>
     */
    fun overflowMenu(builder: OverflowMenuElementBuilder.() -> Unit)

    /**
     * An element to insert an image as part of a larger block of content. If you want a block with only an image in
     * it, you're looking for the image block.
     *
     * @see <a href="https://api.slack.com/reference/block-kit/block-elements#image">Image element documentation</a>
     */
    fun image(
        imageUrl: String? = null,
        altText: String? = null,
        fallback: String? = null,
        imageWidth: Int? = null,
        imageHeight: Int? = null,
        imageBytes: Int? = null,
        slackFile: SlackFileObject? = null,
    )

}
