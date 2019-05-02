package com.github.seratch.jslack.app_backend.dialogs.response;

import lombok.Data;

import java.util.List;

/**
 * Response data to react to an action where a user clicked on a dialog which has
 * "type": "select" and "data_source": "external"
 * <p>
 * see https://api.slack.com/dialogs
 */
@Data
public class DialogSuggestionResponse {

    private List<Option> options;
    private List<OptionGroup> optionGroups;

    @Data
    public static class Option {
        private String label;
        private String value;
    }

    @Data
    public static class OptionGroup {
        private String label;
        private List<Option> options;
    }

}
