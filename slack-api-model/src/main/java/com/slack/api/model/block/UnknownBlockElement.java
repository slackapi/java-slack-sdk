package com.slack.api.model.block;

import com.slack.api.model.block.element.BlockElement;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UnknownBlockElement extends BlockElement {
    private String type;
}
