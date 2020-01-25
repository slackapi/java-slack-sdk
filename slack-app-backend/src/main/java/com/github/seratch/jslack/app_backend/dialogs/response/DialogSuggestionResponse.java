package com.github.seratch.jslack.app_backend.dialogs.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Response data to react to an action where a user clicked on a dialog which has
 * "type": "select" and "data_source": "external"
 * <p>
 * see https://api.slack.com/dialogs
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DialogSuggestionResponse {

    private List<Option> options;
    private List<OptionGroup> optionGroups;
}
