package com.github.seratch.jslack.app_backend.interactive_messages.response;

import com.github.seratch.jslack.api.model.block.composition.TextObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlockSuggestionResponse {

    private List<Option> options;
    private List<OptionGroup> optionGroups;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Option {
        private TextObject text;
        private String value;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OptionGroup {
        private String label;
        private List<Option> options;
    }

}
