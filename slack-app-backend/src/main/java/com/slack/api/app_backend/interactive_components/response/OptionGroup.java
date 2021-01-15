package com.slack.api.app_backend.interactive_components.response;

import com.slack.api.model.block.composition.TextObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OptionGroup {
    private TextObject label;
    private List<Option> options;
}
