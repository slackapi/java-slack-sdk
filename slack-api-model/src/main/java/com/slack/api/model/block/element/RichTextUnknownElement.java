package com.slack.api.model.block.element;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RichTextUnknownElement extends BlockElement implements RichTextElement {
    private String type;
}
