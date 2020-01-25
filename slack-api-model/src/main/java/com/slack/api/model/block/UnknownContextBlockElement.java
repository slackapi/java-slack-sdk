package com.slack.api.model.block;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnknownContextBlockElement implements ContextBlockElement {
    private String type;
}
