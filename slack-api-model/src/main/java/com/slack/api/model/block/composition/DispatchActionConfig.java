package com.slack.api.model.block.composition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * https://docs.slack.dev/reference/block-kit/composition-objects/dispatch-action-configuration-object
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DispatchActionConfig {

    /**
     * An array of interaction types that you would like to receive a block_actions payload for.
     * <p>
     * Should be one or both of:
     * - on_enter_pressed — payload is dispatched when user presses the enter key while the input is in focus.
     * Hint text will appear underneath the input explaining to the user to press enter to submit.
     * - on_character_entered — payload is dispatched when a character is entered (or removed) in the input.
     */
    private List<String> triggerActionsOn;
}
