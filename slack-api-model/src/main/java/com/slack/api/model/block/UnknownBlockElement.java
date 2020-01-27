package com.slack.api.model.block;

import com.slack.api.model.block.element.BlockElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnknownBlockElement extends BlockElement {
    private String type;
}
