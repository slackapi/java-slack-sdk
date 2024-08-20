package com.slack.api.app_backend.interactive_components.response;

import com.slack.api.model.block.composition.TextObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Option {
    private TextObject text;
    private String value;
    private TextObject description;
}
