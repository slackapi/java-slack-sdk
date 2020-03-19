package com.slack.api.model.block.element;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Filter object for conversation lists
 * Provides a way to filter the list of options in a conversations select menu or conversations multi-select menu.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConversationsFilter {

    /**
     * Indicates which type of conversations should be included in the list.
     * When this field is provided, any conversations that do not match will be excluded.
     * You should provide an array of strings from the following options: im, mpim, private, and public.
     * The array cannot be empty.
     */
    private List<String> include;

    /**
     * Indicates whether to exclude external shared channels from conversation lists. Defaults to false.
     */
    private Boolean excludeExternalSharedChannels;

    /**
     * Indicates whether to exclude bot users from conversation lists. Defaults to false.
     */
    private Boolean excludeBotUsers;

}
