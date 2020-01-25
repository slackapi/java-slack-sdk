package com.slack.api.app_backend.interactive_components.response;

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

}
