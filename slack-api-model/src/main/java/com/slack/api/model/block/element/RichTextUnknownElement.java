package com.slack.api.model.block.element;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RichTextUnknownElement extends BlockElement implements RichTextElement {
    private String type;
}
