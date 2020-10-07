package com.slack.api.model.kotlin_extension.block.composition

enum class TriggerActionOn {

    /**
     * payload is dispatched when user presses the enter key while the input is in focus.
     * Hint text will appear underneath the input explaining to the user to press enter to submit.
     */
    ON_ENTER_PRESSED {
        override val value: String? = "on_enter_pressed"
    },

    /**
     * payload is dispatched when a character is entered (or removed) in the input.
     */
    ON_CHARACTER_ENTERED {
        override val value: String? = "on_character_entered"
    };

    abstract val value: String?
}
