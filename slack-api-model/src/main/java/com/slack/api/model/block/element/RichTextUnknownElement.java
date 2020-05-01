package com.slack.api.model.block.element;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RichTextUnknownElement extends BlockElement implements RichTextElement {
    private String type;
}
