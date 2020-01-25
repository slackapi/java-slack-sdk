package com.github.seratch.jslack.api.model.block.element;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RichTextUnknownElement extends BlockElement implements RichTextElement {
    private String type;
}
